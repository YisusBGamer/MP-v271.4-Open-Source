package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Malefic Star (maplestorywiki.net, GMS): Normal, Hard; level 280, 30 minutes, 1-3 players, death count 5.
 * One HP for the whole fight, phase 2 at 80%, phase 3 at 40%. The party and Malefic Star (keeping her HP) move
 * between the illusion (Banquet Hall, 410025100 / 410026100, where the fight starts) and reality (Unglamoured
 * Ruins, 410025200 / 410026200) every 220 s, or earlier when the Awakened uses Hard Realities / Fictious Visions.
 * <p>
 * The meters are the field-set contexts of Mob.wz BossFieldSetInfo.img/bossYmir, sent with Out 971; the v271
 * client draws them in field type 453 (star gauge: Star, Risk, FinalDamR, Illusion; predation window: Prey,
 * IsPenalty). The rules below follow that file's event graph:
 * <ul>
 * <li>Prey (Hunger) 0-1000; at 1000 the fight fails. +2/s in the last 60 s of an illusion stay, +1/s in reality
 * while IsPenalty is set; each pattern hit adds 30/20/10 (1/2/3 players) once Risk is 1 or more.</li>
 * <li>Star (illusory magic) 0-1000, reset to 1000 on entering the illusion; each pattern hit adds 120/60/40. In
 * reality it sets Risk: 0 below 250, 1 below 500, 2 below 750, 3 up to 1000 (DamageLv 1 at Risk 3); reaching
 * 1000 there sets IsPenalty (no state change, Hunger rises).</li>
 * <li>Radiant pillars (illusion cores) in reality: left = Star -250 and Final Damage -5/-10/-15, right = Star +250
 * and Final Damage +5/+10/+15 (by Star below 500 / below 750 / up to 1000); FinalDamR -10..30, -10 on entering
 * reality with Star above 0.</li>
 * </ul>
 * The patterns (Mob.wz patternSys 1091-1094) are not in the v271 client data; the Out 679 pattern blob
 * (test.patternFile) brings rebuilt ones. Malefic Star's own attacks (1091) are gated by the Illusion and Phase
 * contexts in the pattern data; her invincible parts are spawned by room: 1092 (illusion spells) in the Banquet
 * Hall, 1093 (reality spells) in the Ruins, and 1094 (Floating Disaster) in the Ruins while Risk is 2 or more.
 * Without the blob the fallback attacks run. Client hits and fallback hits feed the meters alike. The pillar and
 * orb objects (1107, 1109) are not run: the pillar is picked at random (the client's own text says an unpicked
 * pillar is chosen at random), the first player is the Awakened, and the Essences / Detect gauge do not run.
 */
public class MaleficStarInstanceHelper extends StagedBossHelper {

    private static final long ROOM_MS = 220_000; // BossYmir.img realityAlert loopTime
    private static final long ILLUSION_HUNGER_MS = 60_000; // the PreyIncrease timer's last 60 s
    private static final long PILLAR_AT_MS = 20_000; // inferred
    private static final long PILLAR_PICK_MS = 15_000; // inferred
    private static final long STATE_SKILL_COOLDOWN_MS = 30_000; // Skill.wz 80004046/80004047 cooltime
    private static final int HARD_REALITIES = 80004046;
    private static final int FICTIOUS_VISIONS = 80004047;
    // UITemporarySkillBar: 114 holds only Hard Realities, 115 only Fictious Visions (both on the Interact key)
    private static final int ILLUSION_BAR = 114;
    private static final int REALITY_BAR = 115;
    private static final int[] HIT_STAR = {120, 60, 40};
    private static final int[] HIT_PREY = {30, 20, 10};
    private static final Position BOSS_POS = new Position(250, 0);
    private static final Position PART_POS = new Position(0, 0);
    // + base: the invincible parts with pattern categories 1092, 1093, 1094
    private static final int ILLUSION_PART = 1;
    private static final int REALITY_PART = 2;
    private static final int DISASTER_PART = 3;

    private volatile int mode = -1;
    private Stage[] stages;
    private volatile int room; // 0 illusion, 1 reality
    private volatile boolean swapping;
    private volatile boolean failed;
    private volatile long roomSince = System.currentTimeMillis();
    private volatile int pillarStep; // 0 none yet, 1 shown, 2 picked (this reality stay)
    private volatile boolean illusionHungerSaid;
    private volatile int awakened;
    private final List<Mob> parts = new java.util.concurrent.CopyOnWriteArrayList<>();
    private volatile Mob disaster;
    private final FieldSetContext ctx = new FieldSetContext()
            .field("Prey", 0, 0, 1000).field("Star", 1000, 0, 1000).field("Detect", 0, 0, 1000)
            .field("Phase", 1, 1, 3).field("Risk", 0, 0, 3).field("Illusion", 1, 0, 1)
            .field("DamageLv", 0, 0, 2).field("FinalDamR", 0, -10, 30).field("IsAltarAlive", 0, 0, 1)
            .field("RandomAltarActive", 0, 0, 1).field("SkillCount", 0, 0, 1).field("IsPenalty", 0, 0, 1)
            .field("IllusionPenalty", 0, 0, 15).field("blockByPenalty", 0, 0, 1)
            .user("Leader", 0, -1, 1).user("RefluxOfLight_Attack", 0, 0, 1).user("RefluxOfLight_Fly", 0, 0, 1)
            .user("RefluxOfLight_UserIdx", 0, 0, 1).user("EssenceCount", 0, 0, 2).user("Abyss_Fly", 0, 0, 1);

    private int mode() {
        if (mode < 0) {
            Object prop = getInstance() == null ? null : getInstance().getProperty(BossConstants.MALEFIC_MODE_KEY);
            int m = prop instanceof Number n ? n.intValue() : -1;
            if (m < 0 && getInstance() != null) {
                m = getInstance().getEnterFieldId() >= BossConstants.MALEFIC_FIELDS[1][0] ? 1 : 0;
            }
            mode = Math.max(0, Math.min(1, m));
        }
        return mode;
    }

    private int base() {
        return BossConstants.MALEFIC_BASE[mode()];
    }

    @Override
    protected synchronized Stage[] stages() {
        if (stages == null) {
            stages = new Stage[]{stageIn(0)};
        }
        return stages;
    }

    private Stage stageIn(int r) {
        int m = mode();
        return new Stage(BossConstants.MALEFIC_FIELDS[m][r], BossConstants.MALEFIC_BASE[m],
                BossConstants.MALEFIC_HP[m], new int[0], 0, BOSS_POS.getX(), BOSS_POS.getY());
    }

    private boolean inFight(Field field) {
        if (field == null) {
            return false;
        }
        int[] f = BossConstants.MALEFIC_FIELDS[mode()];
        return field.getId() == f[0] || field.getId() == f[1];
    }

    @Override
    protected void startMechanics() {
        super.startMechanics();
        roomSince = System.currentTimeMillis();
        later(1500, this::chooseAwakened);
        later(1000, this::spawnParts); // after her (spawned at 500 ms)
        every(1000, this::tick);
    }

    /** The parts of the room the fight is in. */
    private void spawnParts() {
        despawnParts();
        Mob part = spawnAdd(base() + (room == 0 ? ILLUSION_PART : REALITY_PART), PART_POS, -1);
        if (part != null) {
            parts.add(part);
        }
        updateDisaster();
    }

    private void despawnParts() {
        for (Mob part : parts) {
            despawn(part);
        }
        parts.clear();
        if (disaster != null) {
            despawn(disaster);
            disaster = null;
        }
    }

    /** Floating Disaster (1094) runs in reality while Risk is 2 or more. */
    private void updateDisaster() {
        boolean on = room == 1 && !swapping && ctx.get("Risk") >= 2;
        if (on && disaster == null) {
            disaster = spawnAdd(base() + DISASTER_PART, PART_POS, -1);
        } else if (!on && disaster != null) {
            despawn(disaster);
            disaster = null;
        }
    }

    private void tick() {
        Field field = getField();
        if (field == null || failed) {
            return;
        }
        if (crossedThreshold(80)) {
            ctx.set("Phase", 2);
            notice("Malefic Star's hunger deepens. (Phase 2)");
        }
        if (crossedThreshold(40)) {
            ctx.set("Phase", 3);
            notice("Malefic Star unleashes her full radiance. (Phase 3)");
        }
        long in = System.currentTimeMillis() - roomSince;
        if (!swapping) {
            if (room == 0) {
                if (in >= ROOM_MS - ILLUSION_HUNGER_MS) {
                    ctx.add("Prey", 2);
                    if (!illusionHungerSaid) {
                        illusionHungerSaid = true;
                        notice("Staying too long in the illusory world has caused Hunger to rise.");
                    }
                }
            } else {
                if (ctx.get("IsPenalty") == 1) {
                    ctx.add("Prey", 1);
                }
                pillarTick(in);
            }
        }
        if (ctx.get("Prey") >= 1000) {
            fail();
            return;
        }
        if (in >= ROOM_MS) {
            swapRoom();
        }
        ctx.flush(field, false);
    }

    private void fail() {
        failed = true;
        ctx.flush(getField(), false);
        notice("Malefic Star's Hunger is sated. The fight is lost.");
        EventManager.addEvent(() -> {
            if (getInstance() != null && !isBossDead()) {
                getInstance().clear();
            }
        }, 3000);
    }

    // ---- the Awakened ----

    private Char awakenedChar() {
        Field field = getField();
        if (field == null) {
            return null;
        }
        for (Char chr : field.getChars()) {
            if (chr.getId() == awakened) {
                return chr;
            }
        }
        return null;
    }

    private void chooseAwakened() {
        Field field = getField();
        if (field == null) {
            return;
        }
        List<Char> living = getLivingChars();
        if (living.isEmpty()) {
            return;
        }
        Char old = awakenedChar();
        Char next = living.contains(old) ? old : living.get(0);
        awakened = next.getId();
        for (Char chr : new ArrayList<>(field.getChars())) {
            ctx.join(chr);
            ctx.set(chr, "Leader", chr == next ? 1 : 0);
            if (chr != next) {
                chr.write(FieldPacket.setTemporarySkillSet(0));
            }
        }
        notice(next.getName() + " is now the Awakened.");
        next.chatMessage("Use the Awakened's power to dispel illusion cores when they are weak. Interacting with "
                + "the orb will return the Awakened power to it.");
        sendBar(next);
        ctx.flush(field, false);
    }

    /** The Awakened's bar: Hard Realities in the illusion, Fictious Visions in reality. */
    private void sendBar(Char chr) {
        chr.write(room == 0 ? FieldPacket.setTemporarySkillSet(ILLUSION_BAR, HARD_REALITIES)
                : FieldPacket.setTemporarySkillSet(REALITY_BAR, FICTIOUS_VISIONS));
    }

    @Override
    public void onSkillUse(Char chr, int skillId, int slv) {
        super.onSkillUse(chr, skillId, slv);
        if ((skillId != HARD_REALITIES && skillId != FICTIOUS_VISIONS) || !inFight(chr.getField())) {
            return;
        }
        if (chr.getId() != awakened || swapping || failed) {
            return;
        }
        if (ctx.get("IsPenalty") == 1) {
            chr.chatMessage("The illusion cannot be dispelled because Malefic Star's too powerful. Destroy the core "
                    + "and use the radiant pillars to weaken her illusory magic.");
            return;
        }
        boolean toReality = skillId == HARD_REALITIES;
        if (toReality != (room == 0) || System.currentTimeMillis() - roomSince < STATE_SKILL_COOLDOWN_MS) {
            return;
        }
        swapRoom();
    }

    // ---- reality and illusion ----

    /** Reality and illusion trade places: the party and Malefic Star (with her HP) move to the other room. */
    private void swapRoom() {
        Field from = getField();
        Mob boss = getBoss();
        if (from == null || boss == null || swapping || getInstance() == null || failed) {
            return;
        }
        swapping = true;
        long hp = boss.getHp();
        int next = 1 - room;
        if (next == 1) {
            notice(ctx.get("Star") == 0
                    ? "By going to reality due to weakened illusory magic, Final Damage isn't reduced."
                    : "The illusion shatters. You return to reality.");
        } else {
            notice("The reality around you dissolves into illusion...");
        }
        EventManager.addEvent(() -> {
            try {
                if (isBossDead() || getInstance() == null) {
                    return;
                }
                Mob cur = getBoss();
                long left = cur == null ? hp : cur.getHp();
                despawn(cur);
                despawnParts();
                synchronized (this) {
                    room = next;
                    stages[0] = stageIn(next);
                }
                enterRoom(next);
                moveToStage(0);
                EventManager.addEvent(() -> {
                    respawn(left);
                    spawnParts();
                }, 1500);
            } finally {
                roomSince = System.currentTimeMillis();
                swapping = false;
            }
        }, 2000);
    }

    private void enterRoom(int r) {
        pillarStep = 0;
        illusionHungerSaid = false;
        if (r == 0) {
            ctx.set("Illusion", 1);
            ctx.set("Star", 1000);
            ctx.set("FinalDamR", 0);
            ctx.set("Risk", 0);
            ctx.set("DamageLv", 0);
            ctx.set("IsPenalty", 0);
            ctx.set("blockByPenalty", 0);
        } else {
            ctx.set("Illusion", 0);
            ctx.set("FinalDamR", ctx.get("Star") > 0 ? -10 : 0);
            updateRisk();
        }
    }

    private void updateRisk() {
        int star = ctx.get("Star");
        int risk = star < 250 ? 0 : star < 500 ? 1 : star < 750 ? 2 : 3;
        ctx.set("Risk", risk);
        ctx.set("DamageLv", risk == 3 ? 1 : 0);
        if (room == 1) {
            if (star >= 1000 && ctx.get("IsPenalty") == 0) {
                ctx.set("IsPenalty", 1);
                ctx.set("blockByPenalty", 1);
                notice("Malefic Star prepares a powerful attack, increasing her Hunger. Soon, moving between states "
                        + "will be impossible. Weaken the illusory magic before it's too late.");
            } else if (star < 1000 && ctx.get("IsPenalty") == 1) {
                ctx.set("IsPenalty", 0);
                ctx.set("blockByPenalty", 0);
            }
        }
        updateDisaster();
    }

    private void pillarTick(long in) {
        if (pillarStep == 0 && in >= PILLAR_AT_MS) {
            pillarStep = 1;
            ctx.set("IsAltarAlive", 1);
            notice("Radiant pillars have appeared. The left pillar weakens Malefic Star and reduces Final Damage. "
                    + "The right pillar strengthens Malefic Star and increases Final Damage.");
            notice("If a radiant pillar is not chosen in time, one will be chosen at random.");
        } else if (pillarStep == 1 && in >= PILLAR_AT_MS + PILLAR_PICK_MS) {
            pillarStep = 2;
            boolean right = Util.succeedProp(50);
            ctx.set("RandomAltarActive", right ? 1 : 0);
            ctx.set("IsAltarAlive", 0);
            pickPillar(right);
        }
    }

    private static int pillarDamR(int star) {
        return star < 500 ? 5 : star < 750 ? 10 : 15;
    }

    private void pickPillar(boolean right) {
        if (right) {
            int star = ctx.add("Star", 250);
            int dr = ctx.add("FinalDamR", pillarDamR(star));
            updateRisk();
            notice(star >= 1000 && dr >= 30 ? "The illusory magic has been enhanced."
                    : "The illusory magic has strengthened, increasing Final Damage.");
        } else {
            ctx.add("FinalDamR", -pillarDamR(ctx.get("Star")));
            ctx.add("Star", -250);
            updateRisk();
            if (ctx.get("IsPenalty") == 1) {
                notice("The illusory magic has weakened, preventing Malefic Star's powerful attacks.");
            } else if (ctx.get("FinalDamR") <= -10) {
                notice("The illusory magic grows weaker.");
            } else {
                notice("The illusory magic has weakened, reducing Final Damage.");
            }
        }
    }

    // ---- hits and damage ----

    @Override
    protected void onFallbackHit(Char chr, FallbackAttack attack) {
        countHit();
    }

    @Override
    protected void onPatternHit(Char chr, int mobTemplateId) {
        if (!swapping && !failed) {
            countHit();
            ctx.flush(getField(), false);
        }
    }

    /** A pattern hit: Star +120/60/40 and, once Risk is 1 or more, Hunger +30/20/10 (1/2/3 players). */
    private void countHit() {
        Field field = getField();
        int n = Math.max(1, Math.min(3, field == null ? 1 : field.getChars().size()));
        ctx.add("Star", HIT_STAR[n - 1]);
        if (ctx.get("Risk") >= 1) {
            ctx.add("Prey", HIT_PREY[n - 1]);
        }
        if (room == 1) {
            updateRisk();
        }
    }

    /** Final Damage from the pillars (BossFieldSetInfo: buff 80004058 = FinalDamR %), applied to her HP. */
    @Override
    public void onMobDamaged(Char attacker, Mob mob, long damage, long oldHp, long newHp) {
        super.onMobDamaged(attacker, mob, damage, oldHp, newHp);
        int dr = ctx.get("FinalDamR");
        if (dr == 0 || damage <= 0 || newHp <= 0 || mob.getTemplateId() != base()) {
            return;
        }
        long change = damage / 100 * dr;
        mob.setHp(Math.max(1, Math.min(mob.getMaxHp(), newHp - change)));
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        super.onWarp(chr, oldField, newField);
        if (inFight(newField)) {
            ctx.join(chr);
            EventManager.addEvent(() -> {
                ctx.send(chr);
                if (chr.getId() == awakened) {
                    sendBar(chr);
                }
            }, 1000); // after the client sets the field up
        } else if (inFight(oldField) && chr.getId() == awakened) {
            chr.write(FieldPacket.setTemporarySkillSet(0));
        }
    }

    @Override
    public void onChrDeath(Char chr) {
        super.onChrDeath(chr);
        if (chr.getId() == awakened) {
            ctx.set(chr, "Leader", -1);
            notice("The Awakened power has vanished.");
            later(1000, this::chooseAwakened);
        }
    }

    @Override
    public void onChrRemoved(Char chr) {
        super.onChrRemoved(chr);
        if (chr.getId() == awakened) {
            awakened = 0;
            later(1000, this::chooseAwakened);
        }
    }

    private void respawn(long hp) {
        Field field = getField();
        if (field == null || isBossDead()) {
            return;
        }
        for (Mob mob : new ArrayList<>(field.getMobs())) {
            if (mob.getTemplateId() == base() && !mob.isDead()) {
                return;
            }
        }
        Mob boss = field.spawnMob(base(), BOSS_POS.getX(), BOSS_POS.getY(), false, BossConstants.MALEFIC_HP[mode()]);
        if (boss != null) {
            boss.setHp(Math.max(1, hp));
        }
    }

    /**
     * Fallback attacks (no client data: estimates in % of max HP; GMS: every attack is a share of max HP), harder
     * from phase 2 on and by a quarter at DamageLv 1 (inferred).
     */
    @Override
    protected FallbackAttack[] fallbackAttacks(int stage, int fighterId) {
        boolean hard = mode() == 1;
        int permille = holderHpPermille();
        FallbackAttack[] a;
        if (permille > 800) {
            a = new FallbackAttack[]{new FallbackAttack(hard ? 25 : 20, 10_000, 1300, 350),
                    new FallbackAttack(30, 14_000, 1500, 300), new FallbackAttack(hard ? 50 : 40, 25_000, 2000, 0)};
        } else if (permille > 400) {
            a = new FallbackAttack[]{new FallbackAttack(hard ? 30 : 25, 9_000, 1200, 350),
                    new FallbackAttack(35, 12_000, 1400, 300), new FallbackAttack(hard ? 60 : 50, 22_000, 2000, 0),
                    new FallbackAttack(20, 16_000, 1000, 450)};
        } else {
            a = new FallbackAttack[]{new FallbackAttack(hard ? 35 : 30, 8_000, 1100, 350),
                    new FallbackAttack(40, 11_000, 1300, 300), new FallbackAttack(hard ? 70 : 60, 20_000, 2000, 0),
                    new FallbackAttack(hard ? 30 : 25, 14_000, 1000, 500)};
        }
        if (ctx.get("DamageLv") < 1) {
            return a;
        }
        FallbackAttack[] up = new FallbackAttack[a.length];
        for (int i = 0; i < a.length; i++) {
            up[i] = new FallbackAttack(Math.min(99, a[i].damR() * 5 / 4), a[i].cooldownMs(), a[i].warnMs(),
                    a[i].radius());
        }
        return up;
    }

    @Override
    protected int rewardFieldId() {
        return BossConstants.MALEFIC_FIELDS[mode()][2];
    }

    @Override
    public boolean usesField(int fieldId) {
        for (int[] mode : BossConstants.MALEFIC_FIELDS) {
            for (int id : mode) {
                if (id == fieldId) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected int chestId() {
        return base() + BossConstants.MALEFIC_CHEST;
    }

    @Override
    protected Position chestPos() {
        return new Position(150, 0);
    }

    @Override
    protected String bossName() {
        return "Malefic Star";
    }
}
