package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.Field;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Kai, the Season boss (maplestorywiki.net, GMS; BossParty.img 36): Normal (level 270), Hard (280), solo only,
 * 20 minutes, death count 5. One HP (the wiki's two phases of 63T / 241.5T each); at 50% Kai becomes Transcended
 * Kai with the HP he has left (BossFieldSetInfo.img/bossChrono: HP 50% -> mob change 8881860 -> 8881864, Phase 2).
 * Then the Chrono Module Type-F on the Terrace. The clear pays BossParty.img's seasonBossReward: Golden Meso Pouches
 * (30 / 60) and 10,000,000 mesos.
 * <p>
 * Chrono Gauge (user context Gauge 0-1000, drawn by the client from Out 971): +24/s from 8 s after entering while
 * Chrono Rend and Time Distortion are off and the player lives; Chrono Rend (80004040, Skill.wz t 0.6 / v 30 /
 * v2 1000) costs 90, then 30 every second after 3 s, and goes off when used again or when the gauge runs out
 * (2 s cooldown after). Each pattern hit costs 30 (Normal) / 60 (Hard). Chrono Step (80004039: 3 charges, one per
 * 5 s, 0.5 s dodge) is the client's own skill.
 * <p>
 * The patterns (Mob.wz patternSys 1088-1090) are not in the v271 client data; the Out 679 pattern blob
 * (test.patternFile) brings rebuilt ones. Chrono Energy (1089) runs Time Distortion and the field hazards: the
 * distortion opens Kai's distortion attacks (1088/1090 004-006) and blocks his normal ones for 20 s, 58 s after it
 * spawns and then every 70 s. The server spawns Chrono Energy with Kai and again at the transformation (so the next
 * distortion comes ~59 s later), and mirrors the window in TimeDistortionOn (no gauge regen, hits cost no gauge).
 * Without the blob the fallback attacks run and count as the pattern hits. FeverCount and the Sequencer outcomes
 * are not run.
 */
public class KaiInstanceHelper extends StagedBossHelper {

    private static final int CHRONO_STEP = 80004039;
    private static final int CHRONO_REND = 80004040;
    private static final int REND_COST = 90;
    private static final int REND_DRAIN = 30;
    private static final long REND_DRAIN_AFTER_MS = 3_000;
    private static final long REND_COOLDOWN_MS = 2_000;
    private static final int GAUGE_REGEN = 24;
    private static final long REGEN_AFTER_MS = 8_000;
    private static final Position BOSS_POS = new Position(300, 45);
    private static final int CHRONO_ENERGY = 1; // + base: the invincible part with pattern category 1089
    private static final long DISTORTION_FIRST_MS = 58_000; // 1089/000 startCooltimeMS
    private static final long DISTORTION_CYCLE_MS = 70_000; // 1089/000 cooltimeMS
    private static final long DISTORTION_MS = 20_000;

    private volatile int mode = -1;
    private Stage[] stages;
    private volatile boolean rewarded;
    private volatile boolean transcended;
    private volatile Mob energy;
    private volatile long energySince;
    private volatile boolean distorted;
    private final Map<Integer, Long> entered = new ConcurrentHashMap<>();
    private final Map<Integer, Long> rendSince = new ConcurrentHashMap<>();
    private final Map<Integer, Long> rendReadyAt = new ConcurrentHashMap<>();
    private final FieldSetContext ctx = new FieldSetContext()
            .field("Phase", 1, 1, 2)
            .user("Gauge", 0, 0, 1000).user("BulletTime", 0, 0, 1).user("TimeDistortionOn", 0, 0, 1)
            .user("FeverCount", 0, 0, 10000).user("Alive", 1, 0, 1).user("YaChaFeverCount", 0, 0, 5)
            .user("0", 0, 0, 3);

    private int mode() {
        if (mode < 0) {
            Object prop = getInstance() == null ? null : getInstance().getProperty(BossConstants.KAI_MODE_KEY);
            int m = prop instanceof Number n ? n.intValue() : -1;
            if (m < 0 && getInstance() != null) {
                m = getInstance().getEnterFieldId() == BossConstants.KAI_FIELDS[1][0] ? 1 : 0;
            }
            mode = Math.max(0, Math.min(1, m));
        }
        return mode;
    }

    private int base() {
        return BossConstants.KAI_BASE[mode()];
    }

    private long totalHp() {
        return BossConstants.KAI_HP[mode()] * 2;
    }

    @Override
    protected synchronized Stage[] stages() {
        if (stages == null) {
            stages = new Stage[]{stageOf(base())};
        }
        return stages;
    }

    private Stage stageOf(int holder) {
        return new Stage(BossConstants.KAI_FIELDS[mode()][0], holder, totalHp(), new int[0], 0, BOSS_POS.getX(),
                BOSS_POS.getY());
    }

    @Override
    protected Set<Integer> getBossIds() {
        return Set.of(base(), base() + 4);
    }

    private boolean inFight(Field field) {
        return field != null && field.getId() == BossConstants.KAI_FIELDS[mode()][0];
    }

    @Override
    protected void startMechanics() {
        super.startMechanics();
        later(1000, this::spawnEnergy); // after Kai (spawned at 500 ms)
        every(1000, this::tick);
    }

    private void spawnEnergy() {
        if (energy != null) {
            despawn(energy);
        }
        energy = spawnAdd(base() + CHRONO_ENERGY, BOSS_POS, -1);
        energySince = System.currentTimeMillis();
        setDistortion(false);
    }

    /** Whether Chrono Energy's Time Distortion runs now (its 1089/000 timing). */
    private boolean inDistortion(long now) {
        if (energy == null) {
            return false;
        }
        long t = now - energySince - DISTORTION_FIRST_MS;
        return t >= 0 && t % DISTORTION_CYCLE_MS < DISTORTION_MS;
    }

    private void setDistortion(boolean on) {
        if (distorted == on) {
            return;
        }
        distorted = on;
        Field field = getField();
        if (field == null) {
            return;
        }
        for (Char chr : new ArrayList<>(field.getChars())) {
            ctx.set(chr, "TimeDistortionOn", on ? 1 : 0);
        }
    }

    private void tick() {
        Field field = getField();
        if (field == null) {
            return;
        }
        if (!transcended && holderHpPermille() <= 500 && getBoss() != null) {
            transcend();
        }
        long now = System.currentTimeMillis();
        setDistortion(inDistortion(now));
        for (Char chr : new ArrayList<>(field.getChars())) {
            boolean alive = chr.getHP() > 0;
            ctx.set(chr, "Alive", alive ? 1 : 0);
            Long since = rendSince.get(chr.getId());
            if (since != null) {
                if (now - since >= REND_DRAIN_AFTER_MS && ctx.add(chr, "Gauge", -REND_DRAIN) <= 0) {
                    rendOff(chr);
                }
            } else if (alive && ctx.get(chr, "TimeDistortionOn") == 0
                    && now - entered.getOrDefault(chr.getId(), now) >= REGEN_AFTER_MS) {
                ctx.add(chr, "Gauge", GAUGE_REGEN);
            }
        }
        ctx.flush(field, false);
    }

    /** Kai at 50%: Transcended Kai takes his place with the HP left. */
    private void transcend() {
        Field field = getField();
        Mob kai = getBoss();
        if (field == null || kai == null || kai.getTemplateId() != base()) {
            return;
        }
        transcended = true;
        long hp = kai.getHp();
        Position pos = kai.getPosition() == null ? BOSS_POS : kai.getPosition().deepCopy();
        synchronized (this) {
            stages[0] = stageOf(base() + 4);
        }
        despawn(kai);
        if (energy != null) {
            despawn(energy);
            energy = null;
        }
        setDistortion(false);
        ctx.set("Phase", 2);
        notice("Kai transcends time itself...");
        EventManager.addEvent(() -> {
            if (isBossDead() || getField() == null) {
                return;
            }
            Mob next = getField().spawnMob(base() + 4, pos.getX(), pos.getY(), false, totalHp());
            if (next != null) {
                next.setHp(Math.max(1, hp));
            }
            spawnEnergy(); // its Time Distortion restarts from 58 s
        }, 1500);
    }

    @Override
    public void onSkillUse(Char chr, int skillId, int slv) {
        super.onSkillUse(chr, skillId, slv);
        if (skillId != CHRONO_REND || !inFight(chr.getField())) {
            return;
        }
        if (rendSince.containsKey(chr.getId())) {
            rendOff(chr);
        } else if (System.currentTimeMillis() >= rendReadyAt.getOrDefault(chr.getId(), 0L)
                && ctx.get(chr, "Gauge") >= REND_COST) {
            ctx.add(chr, "Gauge", -REND_COST);
            ctx.set(chr, "BulletTime", 1);
            rendSince.put(chr.getId(), System.currentTimeMillis());
        }
        ctx.flush(getField(), false);
    }

    private void rendOff(Char chr) {
        if (rendSince.remove(chr.getId()) != null) {
            ctx.set(chr, "BulletTime", 0);
            rendReadyAt.put(chr.getId(), System.currentTimeMillis() + REND_COOLDOWN_MS);
        }
    }

    @Override
    protected void onFallbackHit(Char chr, FallbackAttack attack) {
        ctx.add(chr, "Gauge", mode() == 1 ? -60 : -30);
    }

    /** Most of Kai's attacks cost 30 / 60 gauge; distortion attacks cost FeverCount instead (not run). */
    @Override
    protected void onPatternHit(Char chr, int mobTemplateId) {
        if (ctx.get(chr, "TimeDistortionOn") == 0) {
            ctx.add(chr, "Gauge", mode() == 1 ? -60 : -30);
            ctx.flush(getField(), false);
        }
    }

    @Override
    public void onChrDeath(Char chr) {
        super.onChrDeath(chr);
        rendOff(chr);
        ctx.set(chr, "Alive", 0);
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        super.onWarp(chr, oldField, newField);
        if (inFight(newField)) {
            entered.putIfAbsent(chr.getId(), System.currentTimeMillis());
            ctx.join(chr);
            ctx.set(chr, "TimeDistortionOn", distorted ? 1 : 0);
            EventManager.addEvent(() -> ctx.send(chr), 1000); // after the client sets the field up
        } else if (inFight(oldField)) {
            rendOff(chr);
        }
    }

    @Override
    protected int[] fieldSkills() {
        return new int[]{CHRONO_STEP, CHRONO_REND};
    }

    @Override
    protected int fieldSkillSet() {
        return 113;
    }

    /** Fallback attacks (no client data: estimates in % of max HP, GMS: every attack is a share of max HP). */
    @Override
    protected FallbackAttack[] fallbackAttacks(int stage, int fighterId) {
        boolean hard = mode() == 1;
        if (fighterId == base()) {
            return new FallbackAttack[]{new FallbackAttack(hard ? 25 : 20, 9_000, 1200, 350),
                    new FallbackAttack(30, 14_000, 1500, 300), new FallbackAttack(hard ? 50 : 40, 24_000, 2000, 0)};
        }
        return new FallbackAttack[]{new FallbackAttack(hard ? 30 : 25, 8_000, 1100, 350),
                new FallbackAttack(35, 12_000, 1400, 300), new FallbackAttack(20, 15_000, 1000, 500),
                new FallbackAttack(hard ? 60 : 50, 22_000, 2000, 0)};
    }

    @Override
    protected int rewardFieldId() {
        return BossConstants.KAI_FIELDS[mode()][1];
    }

    @Override
    public boolean usesField(int fieldId) {
        for (int[] mode : BossConstants.KAI_FIELDS) {
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
        return BossConstants.KAI_BASE[mode()] + BossConstants.KAI_CHEST;
    }

    @Override
    protected Position chestPos() {
        return new Position(100, 165);
    }

    @Override
    protected String bossName() {
        return "Kai";
    }

    @Override
    public void onMobDeath(Field field, Mob mob) {
        super.onMobDeath(field, mob);
        if (mob.getTemplateId() != chestId() || rewarded) {
            return;
        }
        rewarded = true;
        int m = mode();
        for (Char chr : new ArrayList<>(field.getChars())) {
            chr.addMoney(BossConstants.KAI_REWARD_MESO);
            if (chr.canHold(BossConstants.KAI_REWARD_ITEM, BossConstants.KAI_REWARD_COUNT[m])) {
                chr.addItemToInventory(BossConstants.KAI_REWARD_ITEM, BossConstants.KAI_REWARD_COUNT[m]);
            } else {
                chr.chatMessage("Your Use inventory is full: the Golden Meso Pouches could not be given.");
            }
        }
    }
}
