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
 * First Adversary (maplestorywiki.net, GMS): Easy, Normal, Hard, Extreme; level 270, 30 minutes, 1-3 players.
 * Phase 1 Adversary's Valor, phase 2 Adversary's Conviction, phase 3 First Adversary, then the World Heart. Each
 * player has 1000 Adversarial Will; a death costs 200 and a player at 0 leaves the fight. The client keeps and shows
 * the Will in a user context (Mob.wz BossPattern/BossFieldSetInfo.img bossFirstAdversary: UserContext DeathCount,
 * FuncType DeathCountKey); the server fills it on entry and after each death, or the orb starts at 0. The patterns (elements, parries, Power of
 * Order) are drawn and judged by the client and aren't run here; the Power Cycle's -100/-200 Will needs the client's
 * Power of Order, which the server doesn't see.
 */
public class FirstAdversaryInstanceHelper extends StagedBossHelper {

    private volatile int mode = -1;
    private Stage[] stages;
    private final FieldSetContext ctx = new FieldSetContext()
            .field("Gauge", GAUGE_DEFAULT, 0, 1000)
            .field("Level", LEVEL_DEFAULT, 0, 5)
            .user("DeathCount", 1000, 0, 1000)
            .user("Element", -1, 0, 2)
            .user("ElementGuardCount", 0, 0, 3)
            .user("UseGuard", 0, 0, 1)
            .user("UserIndex", 0, 0, 0)
            .userAlias("DeathCountKey", "DeathCount");

    // Power of Order (BossFieldSetInfo.img bossFirstAdversary): the field Gauge (0-1000, starts at 500) drains 5 a
    // second down to its Level's floor (Level n holds at n * 200); Level is the Gauge band (<200 0, <400 1, <600 2,
    // <800 3, else 4) and sets everyone's Final Damage (-40/-20/0/+10/+20, buff 80003897). A pattern hit (In 661
    // with the pattern's sub attack) costs Gauge; a parried one (Enduring Will, 80003894) adds it. GMS resends the
    // contexts about every 1.08 s.
    private static final int GAUGE_DEFAULT = 500;
    private static final int LEVEL_DEFAULT = 2;
    private static final int GAUGE_DRAIN = 5;
    private static final int RESEND_MS = 1080;
    private static final int LEVEL_BUFF = 80003897;
    private static final int GUARD_SKILL = 80003894;
    private static final int[] LEVEL_FD = {-40, -20, 0, 10, 20, 20};
    private static final int PATTERN_CATEGORY = 1069;
    /** Pattern id -> the sub attack that counts (patternSubSkillIndex). */
    private static final int[] PATTERN_SUB = {-1, 1, 2, 1, 1, 0, 1};
    /** Gauge lost on an unparried hit, per pattern, for Easy / Normal / Hard / Extreme. */
    private static final int[][] HIT_LOSS = {null, {200, 200, 200, 300}, {200, 200, 200, 300}, {200, 200, 400, 500},
            {200, 200, 200, 300}, {200, 200, 200, 300}, {200, 200, 400, 500}};
    /** Gauge gained on a parry by a party of 1 / 2 / 3, per pattern: Easy first, then Normal-Extreme. */
    private static final int[][][] PARRY_GAIN = {null,
            {{100, 50, 35}, {75, 40, 25}}, {{100, 50, 35}, {75, 40, 25}}, {{200, 100, 70}, {200, 100, 70}},
            {{100, 50, 35}, {75, 40, 25}}, {{100, 50, 35}, {75, 40, 25}}, {{200, 100, 70}, {200, 100, 70}}};
    /** How long after pressing Enduring Will a hit counts as parried (not in the data; the client judges it). */
    private static final long PARRY_WINDOW_MS = 1000;
    private static final long PARRY_COOLTIME_MS = 4500;

    private final java.util.Map<Integer, Long> parryUntil = new java.util.concurrent.ConcurrentHashMap<>();
    private final java.util.Map<Integer, Long> parryCooldown = new java.util.concurrent.ConcurrentHashMap<>();
    private volatile int lastStage = -1;
    private volatile int lastLevel = LEVEL_DEFAULT;
    private volatile long lastLevelEffect;

    private int mode() {
        if (mode < 0) {
            Object prop = getInstance() == null ? null : getInstance().getProperty(BossConstants.ADVERSARY_MODE_KEY);
            int m = prop instanceof Number n ? n.intValue() : -1;
            if (m < 0 && getInstance() != null) {
                m = BossConstants.modeByFieldBase(BossConstants.ADVERSARY_FIELD_BASE, getInstance().getEnterFieldId());
            }
            mode = Math.max(0, Math.min(3, m));
        }
        return mode;
    }

    @Override
    protected synchronized Stage[] stages() {
        if (stages == null) {
            int m = mode();
            int f = BossConstants.ADVERSARY_FIELD_BASE[m];
            int b = BossConstants.ADVERSARY_BASE[m];
            long[] hp = BossConstants.ADVERSARY_HP[m];
            stages = new Stage[]{
                    new Stage(f + 100, b, hp[0], new int[0], 0, -1, 55),
                    new Stage(f + 300, b + 5, hp[1], new int[0], 0, -1, 244),
                    new Stage(f + 500, b + 10, hp[2], new int[0], 0, -40, 108)};
        }
        return stages;
    }

    /**
     * Fallback attacks: damage from maplestorywiki.net (Easy/Normal/Hard, Extreme), cooldowns from PatternSystem.img
     * categories 1069 / 1074 / 1079. The 999% / 1000% patterns (Powerful Strike, Sword Storm, Snipe's elemental shot)
     * aren't run without their client warnings.
     */
    @Override
    protected FallbackAttack[] fallbackAttacks(int stage, int fighterId) {
        boolean x = mode() == 3;
        return switch (stage) {
            case 0 -> new FallbackAttack[]{new FallbackAttack(10, 5_000, 1200, 400), // Lasers
                    new FallbackAttack(15, 10_000, 2700, 150), // Warrior's Mark
                    new FallbackAttack(x ? 80 : 30, 10_000, 1200, 250), // Teleport Slash
                    new FallbackAttack(x ? 80 : 50, 12_000, 1500, 300), // Double Slash
                    new FallbackAttack(x ? 80 : 50, 20_000, 1500, 400)}; // Dash
            case 1 -> new FallbackAttack[]{new FallbackAttack(x ? 50 : 30, 20_000, 1500, 300), // Infinity Pillars
                    new FallbackAttack(x ? 50 : 30, 20_000, 1000, 250), // Debris
                    new FallbackAttack(x ? 20 : 10, 35_000, 1500, 0), // Homing Arrows
                    new FallbackAttack(x ? 40 : 20, 45_000, 1500, 200), // Snipe arrows
                    new FallbackAttack(x ? 80 : 15, 65_000, 2000, 500)}; // Elemental Vortex
            default -> new FallbackAttack[]{new FallbackAttack(15, 5_000, 1000, 400), // Lasers
                    new FallbackAttack(x ? 80 : 30, 10_000, 1200, 250), // Teleport Slash
                    new FallbackAttack(x ? 80 : 50, 10_000, 1500, 300), // Triple Slash
                    new FallbackAttack(x ? 80 : 50, 20_000, 1500, 400), // Triple Dash
                    new FallbackAttack(x ? 60 : 45, 55_000, 1500, 300), // Arrow Shower
                    new FallbackAttack(x ? 50 : 20, 55_000, 2000, 250)}; // Elemental Beams
        };
    }

    @Override
    protected int rewardFieldId() {
        return BossConstants.ADVERSARY_FIELD_BASE[mode()] + 600;
    }

    @Override
    protected int chestId() {
        return BossConstants.ADVERSARY_BASE[mode()] + 15;
    }

    @Override
    protected Position chestPos() {
        return new Position(-1, 303);
    }

    @Override
    protected String bossName() {
        return "The First Adversary";
    }

    @Override
    protected int[] fieldSkills() {
        return new int[]{SkillConstants.ENDURING_WILL}; // Enduring Will (parry)
    }

    @Override
    protected int fieldSkillCooldown() {
        return 6000;
    }

    @Override
    protected int fieldSkillSet() {
        return 86;
    }

    @Override
    protected Gauge gauge() {
        return Gauge.PER_PLAYER;
    }

    @Override
    protected String gaugeName() {
        return "Adversarial Will";
    }

    @Override
    protected int[] gaugeCost() {
        return new int[]{200};
    }

    @Override
    protected void sendGauge(Char chr, int value) {
        ctx.set(chr, "DeathCount", value);
        ctx.send(chr);
        if (value <= 0 && chr.getField() != null) {
            chr.getField().broadcastPacket(BossFieldSetInfoPacket.end());
        }
    }

    @Override
    protected void startMechanics() {
        super.startMechanics();
        every(1000, this::drainTick);
        every(RESEND_MS, this::resendTick);
    }

    private void drainTick() {
        var field = getField();
        if (field == null) {
            return;
        }
        if (stageIndex() != lastStage) {
            // GMS: every phase map starts the Power of Order over (Gauge 500, Level 2)
            lastStage = stageIndex();
            ctx.set("Gauge", GAUGE_DEFAULT);
            ctx.set("Level", LEVEL_DEFAULT);
            lastLevel = LEVEL_DEFAULT;
            if (lastStage > 0) {
                // GMS sends this at each later phase's start (and when a player's Will runs out)
                field.broadcastPacket(BossFieldSetInfoPacket.end());
            }
            applyLevelBuff(field);
        }
        int level = ctx.get("Level");
        int floor = Math.min(4, level) * 200;
        int gauge = ctx.get("Gauge");
        if (gauge > floor) {
            changeGauge(field, Math.max(floor, gauge - GAUGE_DRAIN), null);
        }
    }

    private void resendTick() {
        var field = getField();
        if (field == null) {
            return;
        }
        for (Char c : field.getChars()) {
            ctx.send(c);
            c.write(BossFieldSetInfoPacket.timerTick(500, "weather"));
        }
    }

    private static int levelOf(int gauge) {
        return gauge < 200 ? 0 : gauge < 400 ? 1 : gauge < 600 ? 2 : gauge < 800 ? 3 : 4;
    }

    private synchronized void changeGauge(net.swordie.ms.world.field.Field field, int gauge, Char cause) {
        ctx.set("Gauge", gauge);
        int level = levelOf(ctx.get("Gauge"));
        if (level == lastLevel) {
            return;
        }
        boolean up = level > lastLevel;
        lastLevel = level;
        ctx.set("Level", level);
        applyLevelBuff(field);
        long now = System.currentTimeMillis();
        if (now - lastLevelEffect >= 3000) {
            // handlers 12/13 (3 s cooldown): the gauge effect and the system message
            lastLevelEffect = now;
            int who = cause != null ? cause.getId() : field.getChars().isEmpty() ? 0 : field.getChars().get(0).getId();
            field.broadcastPacket(BossFieldSetInfoPacket.levelEffect(up ? "Levelnc" : "LeveDec", up ? 0 : 1, who));
            if (!up) {
                notice("The Power of Order dwindles. Final Damage has decreased.");
            } else if (level == 4) {
                notice("The Power of Order swells. Final Damage has increased, and the First Adversary grows stronger.");
                field.broadcastPacket(net.swordie.ms.connection.packet.WvsContext.weatherEffectNotice(
                        net.swordie.ms.enums.WeatherEffNoticeType.FirstAdversary,
                        "The Godsphere's unbridled energy permeates your entire body.", 3000));
            } else {
                notice("The Power of Order strengthens. Final Damage has increased.");
            }
        }
    }

    private void applyLevelBuff(net.swordie.ms.world.field.Field field) {
        int fd = LEVEL_FD[Math.max(0, Math.min(5, ctx.get("Level")))];
        for (Char c : field.getChars()) {
            var tsm = c.getTemporaryStatManager();
            var opts = tsm.getOptions(net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.IndiePMdR);
            if (opts != null) {
                for (var old : new java.util.ArrayList<>(opts)) {
                    if (old.rOption == LEVEL_BUFF) {
                        tsm.removeIndieStat(net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.IndiePMdR, old);
                    }
                }
                tsm.sendResetStatPacket();
            }
            if (fd != 0) {
                var o = new net.swordie.ms.client.character.skills.Option();
                o.nOption = fd;
                o.rOption = LEVEL_BUFF;
                o.tOption = 0;
                tsm.putCharacterStatValue(net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.IndiePMdR, o);
                tsm.sendSetStatPacket();
            }
        }
    }

    /** In 1772 type 4: Enduring Will pressed (GMS: UseGuard becomes 1). */
    public void onGuardUsed(Char chr, int skillId) {
        if (skillId != GUARD_SKILL || getField() == null || chr.getField() != getField()) {
            return;
        }
        ctx.set(chr, "UseGuard", 1);
        long now = System.currentTimeMillis();
        if (parryCooldown.getOrDefault(chr.getId(), 0L) <= now) {
            parryUntil.put(chr.getId(), now + PARRY_WINDOW_MS);
        }
    }

    /** In 661: the client says a boss pattern's attack hit this player (pattern key = category * 100000 + id). */
    public void onPatternAttackHit(Char chr, int patternKey, int subIndex) {
        var field = getField();
        if (field == null || chr.getField() != field || patternKey / 100000 != PATTERN_CATEGORY) {
            return;
        }
        int pattern = patternKey % 100000;
        if (pattern < 1 || pattern >= PATTERN_SUB.length || PATTERN_SUB[pattern] != subIndex) {
            return;
        }
        int m = mode();
        long now = System.currentTimeMillis();
        int gauge = ctx.get("Gauge");
        if (parryUntil.getOrDefault(chr.getId(), 0L) >= now) {
            parryUntil.remove(chr.getId());
            parryCooldown.put(chr.getId(), now + PARRY_COOLTIME_MS);
            int party = Math.max(1, Math.min(3, field.getChars().size()));
            int gain = PARRY_GAIN[pattern][m == 0 ? 0 : 1][party - 1];
            changeGauge(field, Math.min(1000, gauge + gain), chr);
        } else {
            changeGauge(field, Math.max(0, gauge - HIT_LOSS[pattern][Math.min(3, m)]), chr);
        }
    }
}
