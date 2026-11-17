package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.connection.packet.field.*;
import net.swordie.ms.connection.packet.field.kaling.*;
import net.swordie.ms.world.field.fieldskill.KalingSpiritParade;
import net.swordie.ms.world.field.fieldskill.HundunDarkCloud;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.util.Position;

/**
 * Baldrix (maplestorywiki.net, GMS): Normal, Hard; level 290, 30 minutes, 1-3 players.
 * Phase 1 Baldrix, phase 2 Magic-assimilating Baldrix, phase 3 Godslayer Baldrix, then the Concentrated Magical
 * Essence. Instead of a death count the party shares a Magic gauge: a death adds 200 (solo), 100 (2) or 70 (3) and
 * the fight fails at 1000 (PatternSystem.img bossData/baldrix failGauge). The client shows what is left of 1000 in the
 * pattern field's death count number (BOSS_PATTERN_FIELD_RESULT type 7). Sleipnir, the possessed statues, the Magic
 * Mirror and the pattern hits' incFailGauge aren't run.
 */
public class BaldrixInstanceHelper extends StagedBossHelper {

    private volatile int mode = -1;
    private Stage[] stages;

    private int mode() {
        if (mode < 0) {
            Object prop = getInstance() == null ? null : getInstance().getProperty(BossConstants.BALDRIX_MODE_KEY);
            int m = prop instanceof Number n ? n.intValue() : -1;
            if (m < 0 && getInstance() != null) {
                m = getInstance().getEnterFieldId() >= BossConstants.BALDRIX_FIELDS[1][0] ? 1 : 0;
            }
            mode = Math.max(0, Math.min(1, m));
        }
        return mode;
    }

    @Override
    protected synchronized Stage[] stages() {
        if (stages == null) {
            int m = mode();
            int[] f = BossConstants.BALDRIX_FIELDS[m];
            int b = BossConstants.BALDRIX_BASE[m];
            long[] hp = BossConstants.BALDRIX_HP[m];
            stages = new Stage[]{
                    new Stage(f[0], b + 1, hp[0], new int[0], 0, 0, 18),
                    new Stage(f[1], b + 14, hp[1], new int[0], 0, -87, -16),
                    new Stage(f[2], b + 21, hp[2], new int[0], 0, 12, -9)};
        }
        return stages;
    }

    /**
     * Fallback attacks, from PatternSystem.img (fixDamR % max HP, cooltimeMS): Baldrix category 1041 (also used for
     * phase 2, whose own patterns have no data), Godslayer Baldrix 1060; Normal / Hard.
     */
    @Override
    protected FallbackAttack[] fallbackAttacks(int stage, int fighterId) {
        boolean hard = mode() == 1;
        if (stage < 2) {
            return new FallbackAttack[]{new FallbackAttack(20, hard ? 15_000 : 18_000, 1100, 350),
                    new FallbackAttack(30, 10_000, 1200, 300), new FallbackAttack(40, 10_000, 1200, 350),
                    new FallbackAttack(hard ? 80 : 60, 20_000, 3000, 400)};
        }
        return new FallbackAttack[]{new FallbackAttack(hard ? 30 : 25, 10_000, 1400, 300),
                new FallbackAttack(40, 24_000, 1700, 350), new FallbackAttack(30, 12_000, 1600, 300),
                new FallbackAttack(hard ? 35 : 25, 20_000, 1000, 300), new FallbackAttack(hard ? 60 : 50, 35_000, 2000, 500),
                new FallbackAttack(hard ? 80 : 70, 50_000, 2500, 600), new FallbackAttack(hard ? 35 : 25, 20_000, 1200, 300)};
    }

    @Override
    protected int rewardFieldId() {
        return BossConstants.BALDRIX_FIELDS[mode()][3];
    }

    @Override
    protected int chestId() {
        return BossConstants.BALDRIX_BASE[mode()] + 24;
    }

    @Override
    protected Position chestPos() {
        return new Position(-98, 498);
    }

    @Override
    protected int[] fieldSkills() {
        return new int[]{SkillConstants.BIFROST}; // Bifrost
    }

    @Override
    protected int fieldSkillCooldown() {
        return 10000;
    }

    @Override
    protected int fieldSkillSet() {
        return 85;
    }

    @Override
    protected String bossName() {
        return "Baldrix";
    }

    @Override
    protected Gauge gauge() {
        return Gauge.SHARED;
    }

    @Override
    protected String gaugeName() {
        return "Magic";
    }

    @Override
    protected int[] gaugeCost() {
        return new int[]{200, 100, 70};
    }

    @Override
    protected void sendGauge(Char chr, int value) {
        chr.write(BossPatternFieldPacket.deathCount(Math.max(0, gaugeMax() - value), false));
    }

    @Override
    protected void sendGaugeChange(Char chr, int value) {
        chr.write(BossPatternFieldPacket.deathCount(Math.max(0, gaugeMax() - value), true));
    }
}
