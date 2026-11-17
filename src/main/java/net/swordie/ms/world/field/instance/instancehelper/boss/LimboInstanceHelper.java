package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.connection.packet.field.*;
import net.swordie.ms.connection.packet.field.kaling.*;
import net.swordie.ms.world.field.fieldskill.KalingSpiritParade;
import net.swordie.ms.world.field.fieldskill.HundunDarkCloud;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.util.Position;

/**
 * Limbo (maplestorywiki.net, GMS): Normal, Hard; level 285, 30 minutes, 1-3 players, no death count.
 * <ul>
 *     <li>Phase 1: Specter A and Specter B take turns every 100 seconds; they share Test Subject AB's HP.</li>
 *     <li>Phase 2: Specter C for half of Test Subject CD's HP, then Specter D for the other half.</li>
 *     <li>Phase 3: Limbo Enlightened by Dark Truth and by Light Truth take turns every 100 seconds (one HP).</li>
 * </ul>
 * Erosion is kept per player (PatternSystem.img bossData/limbo failGauge): a death adds 150 (incDeath), falling into
 * a pit portal 50 (incPortal), and 1000 takes the player out. The client shows what is left of 1000 (LIMBO_FIELD_RESULT,
 * one packet per player). Not run: the pattern hits' incFailGauge (the hit packet doesn't say which pattern hit),
 * the risk ticks per space, Spirit Purification, the Soul Blossoms, the Shadow of the Source and the polluted areas.
 */
public class LimboInstanceHelper extends StagedBossHelper {

    private static final int PIT_EROSION = 50;

    private volatile int mode = -1;
    private Stage[] stages;

    private int mode() {
        if (mode < 0) {
            Object prop = getInstance() == null ? null : getInstance().getProperty(BossConstants.LIMBO_MODE_KEY);
            int m = prop instanceof Number n ? n.intValue() : -1;
            if (m < 0 && getInstance() != null) {
                m = BossConstants.modeByFieldBase(BossConstants.LIMBO_FIELD_BASE, getInstance().getEnterFieldId());
            }
            mode = Math.max(0, Math.min(1, m));
        }
        return mode;
    }

    @Override
    protected synchronized Stage[] stages() {
        if (stages == null) {
            int m = mode();
            int f = BossConstants.LIMBO_FIELD_BASE[m];
            int b = BossConstants.LIMBO_BASE[m];
            stages = new Stage[]{
                    new Stage(f + 100, b + 6, BossConstants.LIMBO_P1_HP[m], new int[]{b, b + 2}, 100_000, 2, 270),
                    new Stage(f + 300, b + 35, BossConstants.LIMBO_P2_HALF_HP[m], new int[]{b + 13}, 0, 5, 1),
                    new Stage(f + 500, b + 35, BossConstants.LIMBO_P2_HALF_HP[m], new int[]{b + 15}, 0, 4, -14),
                    new Stage(f + 700, b + 32, BossConstants.LIMBO_P3_HP[m], new int[]{b + 20, b + 25}, 100_000, 4,
                            -18)};
        }
        return stages;
    }

    /**
     * Fallback attacks, from PatternSystem.img (fixDamR % max HP, cooltimeMS) of each Specter's pattern category:
     * A 1010, B 1012, C 1018, D 1021, Dark 1023, Light 1029; Normal / Hard.
     */
    @Override
    protected FallbackAttack[] fallbackAttacks(int stage, int fighterId) {
        boolean hard = mode() == 1;
        return switch (fighterId - BossConstants.LIMBO_BASE[mode()]) {
            case 0 -> new FallbackAttack[]{new FallbackAttack(20, 10_000, 1400, 350),
                    new FallbackAttack(30, 10_000, 1500, 300), new FallbackAttack(30, 10_000, 1200, 300),
                    new FallbackAttack(20, 25_000, 1500, 600)};
            case 2 -> new FallbackAttack[]{new FallbackAttack(hard ? 60 : 50, 8_000, 1200, 300),
                    new FallbackAttack(40, 60_000, 1500, 500), new FallbackAttack(80, 45_000, 1500, 250)};
            case 13 -> new FallbackAttack[]{new FallbackAttack(30, 6_000, 1200, 300),
                    new FallbackAttack(25, 12_000, 1600, 400), new FallbackAttack(25, 12_000, 1600, 400)};
            case 15 -> new FallbackAttack[]{new FallbackAttack(hard ? 45 : 40, 20_000, 1200, 500),
                    new FallbackAttack(hard ? 45 : 40, 8_000, 2000, 300)};
            case 20 -> new FallbackAttack[]{new FallbackAttack(hard ? 25 : 20, 8_000, 1100, 300),
                    new FallbackAttack(hard ? 35 : 30, 13_000, 1500, 300), new FallbackAttack(20, 23_000, 1200, 250),
                    new FallbackAttack(50, 30_000, 2100, 400)};
            case 25 -> new FallbackAttack[]{new FallbackAttack(hard ? 30 : 20, 6_000, 1400, 300),
                    new FallbackAttack(30, 28_000, 1200, 500)};
            default -> new FallbackAttack[0];
        };
    }

    @Override
    protected int rewardFieldId() {
        return BossConstants.LIMBO_FIELD_BASE[mode()] + 800;
    }

    @Override
    protected int chestId() {
        return BossConstants.LIMBO_BASE[mode()] + 34;
    }

    @Override
    protected Position chestPos() {
        return new Position(331, 754);
    }

    @Override
    protected String bossName() {
        return "Limbo";
    }

    @Override
    protected Gauge gauge() {
        return Gauge.PER_PLAYER_RISING;
    }

    @Override
    protected String gaugeName() {
        return "Erosion";
    }

    @Override
    protected int[] gaugeCost() {
        return new int[]{150};
    }

    /** A fall into a pit portal (the platforms Specter B breaks, Specter D's pit). */
    public void onPitFall(Char chr) {
        long now = System.currentTimeMillis();
        Long last = lastFall.put(chr.getId(), now);
        if (last != null && now - last < 3000) {
            return; // the collision portal fires again while the player is still inside it
        }
        changePlayerGauge(chr, PIT_EROSION, "fall");
    }

    private final java.util.Map<Integer, Long> lastFall = new java.util.concurrent.ConcurrentHashMap<>();

    /** The client's phase number for each stage's field type (380 / 381 / 382 / 383). */
    private int phase() {
        return switch (stageIndex()) {
            case 0 -> 0;
            case 1, 2 -> 1;
            default -> 2;
        };
    }

    @Override
    protected void sendGauge(Char chr, int value) {
        chr.write(LimboFieldPacket.erosion(phase(), gaugeMax() - value, holderHpPermille()));
    }

    @Override
    protected void sendGaugeChange(Char chr, int value) {
        chr.write(LimboFieldPacket.erosionChange(phase(), gaugeMax() - value, true));
    }
}
