package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.enums.ProgressMessageColourType;
import net.swordie.ms.enums.ProgressMessageFontType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A boss fought over several maps in a row: each stage has an HP holder (the mob whose HP counts) and optionally
 * forms that fight for it and take its hits (HpLinkMob), swapping every few seconds. When the holder of a stage
 * dies the party goes to the next stage's map; after the last one, to the treasure map. Instead of a death count a
 * boss can use a per-player or a shared gauge.
 * <p>
 * Bosses whose attacks come only from the client's pattern system (Mob.wz info/patternSys) can list fallback
 * attacks: if nobody in the fight has been hit by a monster for a while, the server runs those instead (a warning,
 * then a hit of a share of max HP where the target stood). One monster hit from the client turns them off for the
 * rest of the run.
 */
public abstract class StagedBossHelper extends BossMechanicsHelper {

    /** A server-run attack: damage in % of max HP, its cooldown, the warning before it lands, and its radius (0: map). */
    protected record FallbackAttack(int damR, long cooldownMs, long warnMs, int radius) {
    }

    private static final long FALLBACK_AFTER_MS = 45_000;
    private static final long FALLBACK_GLOBAL_COOLDOWN_MS = 3_000;
    private volatile boolean patternsSeen;
    private volatile boolean fallbackOn;
    private volatile long quietSince = System.currentTimeMillis();
    private long fallbackNextAt;
    private final Map<FallbackAttack, Long> fallbackReadyAt = new ConcurrentHashMap<>();

    /** One map of the fight. */
    protected record Stage(int fieldId, int holderId, long hp, int[] forms, long swapMs, int x, int y) {
    }

    /**
     * How deaths count: NONE; PER_PLAYER (each player starts at max, a death costs cost[0], 0 = out); PER_PLAYER_RISING
     * (each player starts at 0, a death adds cost[0], max = out); SHARED (party starts at 0, a death adds the cost for
     * the party size, max = the fight fails).
     */
    protected enum Gauge {NONE, PER_PLAYER, PER_PLAYER_RISING, SHARED}

    private volatile int stage = 0;
    private volatile Mob form;
    private int formIndex;
    private long nextSwap;
    private final Map<Integer, Integer> playerGauge = new ConcurrentHashMap<>();
    private volatile int sharedGauge;

    protected abstract Stage[] stages();

    protected abstract int rewardFieldId();

    public boolean isRewardField(int fieldId) {
        return fieldId == rewardFieldId();
    }

    protected abstract int chestId();

    protected abstract Position chestPos();

    protected abstract String bossName();

    protected Gauge gauge() {
        return Gauge.NONE;
    }

    protected String gaugeName() {
        return "Will";
    }

    /** NONE: unused. PER_PLAYER: {cost}. SHARED: cost for 1, 2, 3... members. */
    protected int[] gaugeCost() {
        return new int[]{0};
    }

    protected int gaugeMax() {
        return 1000;
    }

    /**
     * Server-run attacks for a stage while the given form (or the holder) fights; empty: none. Used only while the
     * client shows no monster hits (see the class comment).
     */
    protected FallbackAttack[] fallbackAttacks(int stage, int fighterId) {
        return new FallbackAttack[0];
    }

    /** The fight's Harvest-key skills, put on the temporary skill bar in its maps. */
    protected int[] fieldSkills() {
        return new int[0];
    }

    /** The cooldown GMS puts in the bar's skill record (ms); 0 = the plain record. */
    protected int fieldSkillCooldown() {
        return 0;
    }

    /** The UITemporarySkillBar set that holds fieldSkills(); 13 holds only Brand of Sacrifice. */
    protected int fieldSkillSet() {
        return 13;
    }

    private boolean isFightField(Field field) {
        if (field == null) {
            return false;
        }
        for (Stage s : stages()) {
            if (s.fieldId() == field.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        super.onWarp(chr, oldField, newField);
        if (gauge() != Gauge.NONE && isFightField(newField)) {
            EventManager.addEvent(() -> sendGauge(chr, gaugeValue(chr)), 1000); // after the client sets the field up
        }
        if (fieldSkills().length == 0) {
            return;
        }
        if (isFightField(newField)) {
            EventManager.addEvent(() -> chr.write(
                    net.swordie.ms.connection.packet.field.FieldPacket.setTemporarySkillSet(fieldSkillSet(), fieldSkillCooldown(), false, fieldSkills())), 1000);
        } else if (isFightField(oldField)) {
            chr.write(net.swordie.ms.connection.packet.field.FieldPacket.setTemporarySkillSet(0));
        }
    }

    @Override
    protected Field getField() {
        Stage[] st = stages();
        return getInstance() == null ? null : getInstance().getField(st[Math.min(stage, st.length - 1)].fieldId());
    }

    @Override
    protected Set<Integer> getBossIds() {
        Stage[] st = stages();
        return Set.of(st[st.length - 1].holderId());
    }

    @Override
    protected void startMechanics() {
        later(500, () -> spawnStage(0));
        every(1000, this::tick);
    }

    private void spawnStage(int index) {
        Field field = getField();
        Stage s = stages()[index];
        if (field == null || field.getId() != s.fieldId()) {
            return;
        }
        for (Mob mob : new java.util.ArrayList<>(field.getMobs())) {
            if (mob.getTemplateId() == s.holderId() && !mob.isDead()) {
                return; // already there
            }
        }
        field.spawnMob(s.holderId(), s.x(), s.y(), false, s.hp());
        formIndex = 0;
        form = null;
        long now = System.currentTimeMillis();
        quietSince = now;
        fallbackOn = false;
        fallbackReadyAt.clear();
        fallbackNextAt = now + 2_500;
        if (s.forms().length > 0) {
            spawnForm(s, new Position(s.x(), s.y()));
            nextSwap = System.currentTimeMillis() + s.swapMs();
        }
    }

    private Mob holder() {
        Field field = getField();
        if (field == null) {
            return null;
        }
        int id = stages()[Math.min(stage, stages().length - 1)].holderId();
        for (Mob mob : new java.util.ArrayList<>(field.getMobs())) {
            if (mob.getTemplateId() == id && !mob.isDead()) {
                return mob;
            }
        }
        return null;
    }

    private void spawnForm(Stage s, Position pos) {
        Mob h = holder();
        if (h == null) {
            return;
        }
        Mob next = spawnAdd(s.forms()[formIndex % s.forms().length], pos, h.getMaxHp());
        if (next != null) {
            next.setHpLinkMob(h);
            form = next;
        }
    }

    private void tick() {
        Stage[] st = stages();
        if (stage >= st.length) {
            return;
        }
        Stage s = st[stage];
        Field field = getField();
        Mob h = holder();
        if (field == null || h == null) {
            return;
        }
        Mob fighter = form;
        fallbackTick(field, fighter != null && !fighter.isDead() ? fighter : h);
        if (s.forms().length == 0) {
            return;
        }
        boolean lost = form == null || form.isDead() || field.getLifeByObjectID(form.getObjectId()) == null;
        boolean swap = s.forms().length > 1 && s.swapMs() > 0 && System.currentTimeMillis() >= nextSwap;
        if (lost || swap) {
            Position pos = form == null || lost ? new Position(s.x(), s.y()) : form.getPosition().deepCopy();
            if (!lost) {
                despawn(form);
            }
            if (swap) {
                formIndex++;
                nextSwap = System.currentTimeMillis() + s.swapMs();
            }
            spawnForm(s, pos);
        }
    }

    private void fallbackTick(Field field, Mob fighter) {
        if (patternsSeen) {
            return;
        }
        FallbackAttack[] attacks = fallbackAttacks(stage, fighter.getTemplateId());
        long now = System.currentTimeMillis();
        if (attacks.length == 0 || getLivingChars().isEmpty()) {
            quietSince = now; // only time with someone to hit counts
            return;
        }
        if (!fallbackOn) {
            if (now - quietSince < FALLBACK_AFTER_MS) {
                return;
            }
            fallbackOn = true;
            log.info(bossName() + ": no monster hits from the client for " + FALLBACK_AFTER_MS / 1000
                    + " s, the server runs its attacks");
        }
        if (now < fallbackNextAt) {
            return;
        }
        List<FallbackAttack> ready = new ArrayList<>();
        for (FallbackAttack a : attacks) {
            if (fallbackReadyAt.getOrDefault(a, 0L) <= now) {
                ready.add(a);
            }
        }
        List<Char> living = getLivingChars();
        if (ready.isEmpty() || living.isEmpty()) {
            return;
        }
        FallbackAttack a = Util.getRandomFromCollection(ready);
        Char target = Util.getRandomFromCollection(living);
        fallbackReadyAt.put(a, now + a.cooldownMs());
        fallbackNextAt = now + a.warnMs() + FALLBACK_GLOBAL_COOLDOWN_MS;
        Position at = target.getPosition() == null ? null : target.getPosition().deepCopy();
        String text = a.radius() <= 0 ? bossName() + " unleashes a powerful attack!"
                : bossName() + " takes aim at " + target.getName() + "!";
        field.broadcastPacket(UserPacket.progressMessageFont(ProgressMessageFontType.Normal, 16,
                ProgressMessageColourType.Red, (int) Math.max(1000, a.warnMs()), text));
        int at0 = stage;
        later(a.warnMs(), () -> {
            if (stage != at0 || patternsSeen || field != getField()) {
                return;
            }
            for (Char chr : new ArrayList<>(field.getChars())) {
                if (chr.getHP() <= 0 || chr.isHidden() || chr.getPosition() == null) {
                    continue;
                }
                if (a.radius() > 0 && (at == null || chr.getPosition().distanceTo(at) > a.radius())) {
                    continue;
                }
                chr.damage(Math.max(1, chr.getHPPerc(a.damR())), true);
                onFallbackHit(chr, a);
            }
        });
    }

    /** A server-run attack hit this player (the boss mechanics that count pattern hits use it). */
    protected void onFallbackHit(Char chr, FallbackAttack attack) {
    }

    @Override
    public void onChrHitByMob(Char chr, int mobTemplateId, int dmgAmount) {
        super.onChrHitByMob(chr, mobTemplateId, dmgAmount);
        // a real pattern hit (not a touch): the client's pattern system runs
        if (!isFightField(chr.getField()) || dmgAmount < chr.getHPPerc(8)) {
            return;
        }
        if (!patternsSeen) {
            patternsSeen = true;
            fallbackOn = false;
            log.info(bossName() + ": the client's patterns hit (" + mobTemplateId + "), server attacks off");
        }
        onPatternHit(chr, mobTemplateId);
    }

    /** A client pattern attack hit this player (same role as {@link #onFallbackHit} for the server's attacks). */
    protected void onPatternHit(Char chr, int mobTemplateId) {
    }

    @Override
    public void onMobDeath(Field field, Mob mob) {
        Stage[] st = stages();
        int cur = stage;
        if (cur < st.length - 1 && mob.getTemplateId() == st[cur].holderId() && field.getId() == st[cur].fieldId()) {
            if (form != null) {
                despawn(form);
                form = null;
            }
            notice(bossName() + " retreats deeper...");
            EventManager.addEvent(() -> {
                stage = cur + 1;
                moveToStage(cur + 1);
                later(2000, () -> spawnStage(cur + 1));
            }, 3000);
            return;
        }
        boolean bossDied = getBossIds().contains(mob.getTemplateId()) && !isBossDead();
        super.onMobDeath(field, mob);
        if (bossDied) {
            notice(bossName() + " has been defeated! The treasure awaits.");
            EventManager.addEvent(() -> {
                stage = st.length;
                getInstance().warp(rewardFieldId());
                EventManager.addEvent(() -> {
                    Field reward = getInstance() == null ? null : getInstance().getField(rewardFieldId());
                    if (reward != null) {
                        reward.spawnMob(chestId(), chestPos().getX(), chestPos().getY(), false, -1);
                    }
                }, 1500);
            }, 4000);
        }
    }

    @Override
    public void onChrDeath(Char chr) {
        if (gauge() == Gauge.PER_PLAYER || gauge() == Gauge.PER_PLAYER_RISING) {
            changePlayerGauge(chr, gaugeCost()[0], "death");
        } else if (gauge() == Gauge.SHARED) {
            int[] costs = gaugeCost();
            int members = Math.max(1, getInstance() == null ? 1 : getInstance().getChars().size());
            sharedGauge += costs[Math.min(members, costs.length) - 1];
            if (sharedGauge >= gaugeMax() && !isBossDead()) {
                notice(gaugeName() + " overflows. The fight is lost.");
                EventManager.addEvent(() -> {
                    if (getInstance() != null) {
                        getInstance().clear();
                    }
                }, 3000);
            } else {
                notice(gaugeName() + ": " + sharedGauge + " / " + gaugeMax());
            }
            if (getInstance() != null) {
                for (Char c : new ArrayList<>(getInstance().getChars())) {
                    sendGaugeChange(c, sharedGauge);
                }
            }
        }
        super.onChrDeath(chr);
    }

    /**
     * Uses up a per-player gauge by cost (a death, or another drain such as a fall); at the end the player leaves
     * the fight on the next revive, or at once when still alive.
     */
    protected synchronized void changePlayerGauge(Char chr, int cost, String why) {
        if (cost == 0 || isBossDead()) {
            return;
        }
        boolean rising = gauge() == Gauge.PER_PLAYER_RISING;
        int value = rising ? Math.min(gaugeMax(), playerGauge.getOrDefault(chr.getId(), 0) + cost)
                : Math.max(0, playerGauge.getOrDefault(chr.getId(), gaugeMax()) - cost);
        playerGauge.put(chr.getId(), value);
        int left = rising ? gaugeMax() - value : value;
        if (left <= 0) {
            // the revive takes the player out (canReviveInFight is false now)
            chr.chatMessage("Your " + gaugeName() + " is spent: you leave the fight"
                    + (chr.getHP() > 0 ? "." : " when you revive."));
            if (chr.getHP() > 0) {
                EventManager.addEvent(() -> {
                    if (getInstance() != null && chr.getInstance() == getInstance() && isFightField(chr.getField())) {
                        getInstance().removeCharNoReentrance(chr);
                    }
                }, 2000);
            }
        } else {
            chr.chatMessage(gaugeName() + " (" + why + "): " + value + " / " + gaugeMax());
        }
        sendGaugeChange(chr, value);
    }

    @Override
    public boolean canReviveInFight(Char chr) {
        return switch (gauge()) {
            case PER_PLAYER -> playerGauge.getOrDefault(chr.getId(), gaugeMax()) > 0;
            case PER_PLAYER_RISING -> playerGauge.getOrDefault(chr.getId(), 0) < gaugeMax();
            case SHARED -> sharedGauge < gaugeMax();
            case NONE -> false;
        };
    }

    /** The gauge value a character sees: its own for the per-player gauges, the party's for SHARED. */
    protected int gaugeValue(Char chr) {
        return switch (gauge()) {
            case PER_PLAYER -> playerGauge.getOrDefault(chr.getId(), gaugeMax());
            case PER_PLAYER_RISING -> playerGauge.getOrDefault(chr.getId(), 0);
            case SHARED -> sharedGauge;
            case NONE -> 0;
        };
    }

    /** Shows the gauge on the client (on entering a fight map); no packet by default (the chat lines stand in). */
    protected void sendGauge(Char chr, int value) {
    }

    /** Shows a gauge change; by default the same as {@link #sendGauge}. */
    protected void sendGaugeChange(Char chr, int value) {
        sendGauge(chr, value);
    }

    /** Takes the party to a stage; by default the stage's map. Stages that share a map override this. */
    protected void moveToStage(int index) {
        if (getInstance() != null) {
            getInstance().warp(stages()[index].fieldId());
        }
    }

    /** The stage the fight is on (0 = first map). */
    protected int stageIndex() {
        return stage;
    }

    /** The current HP holder's HP in permille (1000 when there is none). */
    protected int holderHpPermille() {
        Mob h = holder();
        // double: hp * 1000 overflows a long at Jupiter's HP
        return h == null || h.getMaxHp() <= 0 ? 1000 : (int) Math.max(0, Math.min(1000, h.getHp() * 1000.0 / h.getMaxHp()));
    }
}
