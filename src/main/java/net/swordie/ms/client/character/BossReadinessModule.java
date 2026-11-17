package net.swordie.ms.client.character;

import net.swordie.ms.client.character.damage.DamageCalc;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.life.mob.Mob;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @bosses: estimates which bosses a character can beat with its current damage.
 * <p>
 * Measured (preferred): the character's real damage per second on bosses and the training dummy, from its last fights
 * ({@link DpsTracker}), worked back to "no defense" and then applied to each boss's defense, level and Arcane Force.
 * <p>
 * Guessed (no fights measured yet): the average of the character's minimum and maximum base damage, with damage and
 * boss damage, final damage and average boss criticals, then the boss's defense, level and Arcane Force. Since no
 * active attack skill is known, {@link #RANGE_TO_DPS} is a cautious bossing-rate estimate, not a measured rotation.
 * <p>
 * Either way, kill times allow for {@link #UPTIME} (dodging, deaths, phase changes).
 * <p>
 * HP are the HP this server's bosses have (all phases and parts together).
 */
public final class BossReadinessModule {

    private BossReadinessModule() {
    }

    /**
     * Estimated damage per second as a multiple of average base damage (only used until a character's real damage is
     * measured). Skill damage and attack cadence vary by class, so this remains a cautious rate estimate. It was 100,
     * which only the strongest classes reach: Cygnus (Easy) showed as a few minutes for characters that couldn't beat it.
     */
    public static final double RANGE_TO_DPS = 30;
    /** Part of a fight spent actually attacking (dodging, deaths and phase changes take the rest). */
    public static final double UPTIME = 0.75;
    /** The usual boss time limit (seconds); each boss has its own in {@link Boss#timeLimitSeconds}. */
    public static final int TIME_LIMIT_SECONDS = 30 * 60;
    /** "Comfortable" kills take at most this part of the time limit. */
    public static final double COMFORTABLE_PART = 1 / 3D;

    public static final class Boss {
        public final String name;
        public final String difficulty;
        /** The boss monster's level (for the level-difference damage penalty). */
        public final int level;
        /** The lowest character level that may enter (Etc.wz/BossParty.img levelMin). */
        public final int entryLevel;
        /** The fight's time limit (seconds). */
        public final int timeLimitSeconds;
        public final long hp;
        public final int pdr;
        /** Arcane Force needed for full damage (0: none). */
        public final int arcaneForce;
        /** Sacred (Authentic) Force of the fight's maps, Map.wz barrierAut (0: none); the highest phase's. */
        public int authenticForce;

        Boss(String name, String difficulty, int entryLevel, int level, long hp, int pdr, int arcaneForce,
             int timeLimitSeconds) {
            this.name = name;
            this.difficulty = difficulty;
            this.entryLevel = entryLevel;
            this.timeLimitSeconds = timeLimitSeconds;
            this.level = level;
            this.hp = hp;
            this.pdr = pdr;
            this.arcaneForce = arcaneForce;
        }

        public String getName() {
            return name;
        }

        public String getDifficulty() {
            return difficulty;
        }

        public int getLevel() {
            return level;
        }

        public int getEntryLevel() {
            return entryLevel;
        }

        public int getTimeLimitSeconds() {
            return timeLimitSeconds;
        }

        public long getHp() {
            return hp;
        }
    }

    private static final List<Boss> BOSSES = new ArrayList<>();

    /** GMS entry levels (Etc.wz/BossParty.img), by "name/difficulty". */
    private static final java.util.Map<String, Integer> ENTRY_LEVEL = new java.util.HashMap<>();
    /** GMS time limits (minutes) that differ from 30. */
    private static final java.util.Map<String, Integer> TIME_LIMIT_MINUTES = java.util.Map.ofEntries(
            java.util.Map.entry("Zakum/Easy", 20), java.util.Map.entry("Papulatus/Easy", 20),
            java.util.Map.entry("Root Abyss (each)/Normal", 15), java.util.Map.entry("Pierre/Chaos", 20),
            java.util.Map.entry("Crimson Queen/Chaos", 20), java.util.Map.entry("Vellum/Chaos", 20),
            java.util.Map.entry("Von Bon/Chaos", 10), java.util.Map.entry("Mori Ranmaru/Normal", 60),
            java.util.Map.entry("Mori Ranmaru/Hard", 60),
            java.util.Map.entry("Black Mage/Hard", 60), java.util.Map.entry("Black Mage/Extreme", 60),
            java.util.Map.entry("Balrog/Easy", 20));

    static {
        // name, Easy, Normal, Hard (Hard also stands for Chaos and Extreme); 0 = no such mode
        Object[][] entry = {
                {"Balrog", 65, 0, 0}, {"Zakum", 50, 90, 90}, {"Magnus", 115, 155, 175}, {"Papulatus", 115, 155, 190},
                {"Horntail", 130, 130, 135}, {"Hilla", 0, 85, 170}, {"Von Leon", 125, 125, 125},
                {"Root Abyss (each)", 0, 125, 180}, {"Pierre", 0, 125, 180}, {"Von Bon", 0, 125, 180},
                {"Crimson Queen", 0, 125, 180}, {"Vellum", 0, 125, 180}, {"Mori Ranmaru", 0, 120, 180},
                {"Pink Bean", 0, 140, 170}, {"Princess No", 0, 180, 0}, {"Arkarium", 140, 140, 0}, {"Cygnus", 165, 165, 0},
                {"Gollux", 180, 180, 180}, {"OMNI-CLN", 0, 180, 0}, {"Akechi Mitsuhide", 0, 200, 0},
                {"Damien", 0, 190, 190}, {"Lotus", 0, 190, 190}, {"Guardian Angel Slime", 0, 210, 210},
                {"Lucid", 220, 220, 220}, {"Will", 235, 235, 235}, {"Gloom", 0, 245, 245},
                {"Verus Hilla", 0, 250, 250}, {"Guard Captain Darknell", 0, 255, 255}, {"Black Mage", 0, 0, 255},
                {"Chosen Seren", 0, 260, 260}, {"Kalos the Guardian", 265, 265, 265}, {"Kaling", 275, 275, 275},
                {"First Adversary", 270, 270, 270}, {"Limbo", 0, 285, 285}, {"Baldrix", 0, 290, 290},
                {"Malefic Star", 0, 280, 280}, {"Jupiter", 0, 295, 295}, {"Kai", 0, 270, 280},
        };
        String[] modes = {"Easy", "Normal", "Hard"};
        for (Object[] e : entry) {
            for (int i = 0; i < 3; i++) {
                ENTRY_LEVEL.put(e[0] + "/" + modes[i], (Integer) e[i + 1]);
            }
            ENTRY_LEVEL.put(e[0] + "/Chaos", (Integer) e[3]);
            ENTRY_LEVEL.put(e[0] + "/Extreme", (Integer) e[3]);
            ENTRY_LEVEL.put(e[0] + "/Hell", (Integer) e[3]);
        }
    }

    private static Boss b(String name, String difficulty, int level, long hp, int pdr, int af) {
        String key = name + "/" + difficulty;
        int entry = ENTRY_LEVEL.getOrDefault(key, 0);
        Boss boss = new Boss(name, difficulty, entry > 0 ? entry : level, level, hp, pdr, af,
                TIME_LIMIT_MINUTES.getOrDefault(key, TIME_LIMIT_SECONDS / 60) * 60);
        BOSSES.add(boss);
        return boss;
    }

    /** A Grandis boss: its Sacred Force need instead of Arcane Force. */
    private static void g(String name, String difficulty, int level, long hp, int pdr, int aut) {
        b(name, difficulty, level, hp, pdr, 0).authenticForce = aut;
    }

    static {
        long b = 1_000_000_000L;
        long t = 1_000_000_000_000L;
        b("Balrog", "Easy", 50, 9_575_040L, 25, 0); // its four parts
        b("Zakum", "Easy", 55, 3_832_000L, 30, 0);
        b("Zakum", "Normal", 110, 12_600_000L, 40, 0);
        b("Magnus", "Easy", 110, 400_000_000L, 50, 0);
        b("Hilla", "Normal", 110, 500_000_000L, 50, 0);
        b("Von Leon", "Easy", 120, 700_000_000L, 50, 0);
        b("Root Abyss (each)", "Normal", 130, 550_000_000L, 55, 0); // Vellum, the strongest of the four
        b("Horntail", "Easy", 130, 817_600_000L, 40, 0);
        b("Mori Ranmaru", "Normal", 129, b, 55, 0);
        b("Pink Bean", "Normal", 140, BossConstants.PINK_BEAN_HP_P1 + BossConstants.PINK_BEAN_HP_P2, 70, 0);
        b("Horntail", "Normal", 160, 2_090_000_000L, 40, 0);
        b("Arkarium", "Easy", 130, BossConstants.ARKARIUM_EASY_HP, 60, 0);
        b("Von Leon", "Normal", 129, 6_300_000_000L, 80, 0);
        b("Magnus", "Normal", 130, 6 * b, 50, 0);
        b("Cygnus", "Easy", 140, 10_500_000_000L, 100, 0);
        b("Von Leon", "Hard", 150, 10_500_000_000L, 90, 0);
        // two bodies of RANMARU_HARD_HP each (the second, which the first revives into, has the higher defense)
        b("Mori Ranmaru", "Hard", 195, 2 * BossConstants.RANMARU_HARD_HP, 90, 0);
        b("Arkarium", "Normal", 170, BossConstants.ARKARIUM_NORMAL_HP, 90, 0);
        b("Hilla", "Hard", 190, 16_800_000_000L, 100, 0);
        b("Zakum", "Chaos", 180, 168 * b, 100, 0); // body 84b + 8 arms of 10.5b
        b("Horntail", "Chaos", 160, 26_600_000_000L, 50, 0);
        b("Cygnus", "Normal", 190, 63 * b, 100, 0);
        b("Pierre", "Chaos", 190, 80 * b, 80, 0);
        b("Von Bon", "Chaos", 190, 100 * b, 100, 0);
        b("Magnus", "Hard", 190, 120 * b, 120, 0);
        b("Crimson Queen", "Chaos", 190, 140 * b, 120, 0);
        b("Pink Bean", "Chaos", 190, BossConstants.CHAOS_PINK_BEAN_HP_P1 + BossConstants.CHAOS_PINK_BEAN_HP_P2, 100, 0);
        b("Vellum", "Chaos", 190, 200 * b, 200, 0);
        b("Damien", "Normal", 210, BossConstants.DEMIAN_HP_NORMAL_1 + BossConstants.DEMIAN_HP_NORMAL_2, 300, 0);
        b("Lotus", "Normal", 210, lotusHp(0), 300, 0);
        b("Guardian Angel Slime", "Normal", 220, BossConstants.GUARDIAN_ANGEL_SLIME_NORMAL_HP, 300, 0);
        b("Lucid", "Easy", 230, BossConstants.LUCID_EASY_HP_1 + BossConstants.LUCID_EASY_HP_2, 300, 250);
        b("Lucid", "Normal", 230, BossConstants.LUCID_HP_1 + BossConstants.LUCID_HP_2, 300, 360);
        b("Will", "Normal", 250, 2 * BossConstants.WILL_P1_HP + BossConstants.WILL_P2_HP + BossConstants.WILL_P3_HP,
                300, 760);
        b("Gloom", "Normal", 255, BossConstants.GLOOM_HP, 300, 730);
        b("Guard Captain Darknell", "Normal", 265, BossConstants.DARKNELL_HP, 300, 850);
        b("Lotus", "Hard", 210, lotusHp(1), 300, 0);
        b("Lotus", "Extreme", 285, lotusHp(2), 380, 0);
        b("Damien", "Hard", 210, BossConstants.DEMIAN_HP_HARD_1 + BossConstants.DEMIAN_HP_HARD_2, 300, 0);
        b("Will", "Hard", 250, 2 * BossConstants.HARD_WILL_P1_HP + BossConstants.HARD_WILL_P2_HP
                + BossConstants.HARD_WILL_P3_HP, 300, 760);
        b("Gloom", "Chaos", 255, BossConstants.GLOOM_HARD_HP, 300, 730);
        b("Guard Captain Darknell", "Hard", 265, BossConstants.DARKNELL_HARD_HP, 300, 850);
        b("Guardian Angel Slime", "Chaos", 250, BossConstants.GUARDIAN_ANGEL_SLIME_CHAOS_HP, 300, 0);
        b("Verus Hilla", "Normal", 250, BossConstants.VERUS_HILLA_HP, 300, 900); // both modes use the Hard map (900)
        b("Verus Hilla", "Hard", 250, BossConstants.VERUS_HILLA_HARD_HP, 300, 900);
        b("Black Mage", "Hard", 275, blackMageHp(0), 300, 1320);
        b("Black Mage", "Extreme", 275, blackMageHp(1), 300, 1320);
        // Grandis bosses: Sacred (Authentic) Force of their maps (Map.wz barrierAut, the highest phase)
        int[] kalosAut = {200, 300, 330, 440};
        int[] kalingAut = {230, 330, 350, 480};
        int[] adversaryAut = {220, 320, 340, 460};
        int[] kalosLevel = {270, 275, 285, 285};
        int[] kalingLevel = {275, 285, 285, 285};
        int[] kalosPdr = {330, 330, 380, 380};
        int[] adversaryLevel = {270, 280, 285, 290};
        for (int m = 0; m < 4; m++) {
            g("Kalos the Guardian", BossConstants.KALOS_MODE_NAMES[m], kalosLevel[m],
                    BossConstants.KALOS_P1_HP[m] + BossConstants.KALOS_P2_HP[m], kalosPdr[m], kalosAut[m]);
            g("Kaling", BossConstants.KALING_MODE_NAMES[m], kalingLevel[m], 3 * BossConstants.KALING_PERIL_HP[m]
                    + BossConstants.KALING_P2_HP[m] + BossConstants.KALING_P3_HP[m], 380, kalingAut[m]);
            long[] hp = BossConstants.ADVERSARY_HP[m];
            g("First Adversary", BossConstants.ADVERSARY_MODE_NAMES[m], adversaryLevel[m], hp[0] + hp[1] + hp[2], 380,
                    adversaryAut[m]);
        }
        for (int m = 0; m < 2; m++) {
            g("Limbo", BossConstants.LIMBO_MODE_NAMES[m], 285, BossConstants.LIMBO_P1_HP[m]
                    + 2 * BossConstants.LIMBO_P2_HALF_HP[m] + BossConstants.LIMBO_P3_HP[m], 380, 500);
            long[] hp = BossConstants.BALDRIX_HP[m];
            g("Baldrix", BossConstants.BALDRIX_MODE_NAMES[m], 290, hp[0] + hp[1] + hp[2], 380, 700);
            g("Malefic Star", BossConstants.MALEFIC_MODE_NAMES[m], 280, BossConstants.MALEFIC_HP[m], 380,
                    m == 0 ? 400 : 550);
            long[] jhp = BossConstants.JUPITER_HP[m];
            g("Jupiter", BossConstants.JUPITER_MODE_NAMES[m], 295, jhp[0] + jhp[1] + jhp[2], 380, 810);
            g("Kai", BossConstants.KAI_MODE_NAMES[m], BossConstants.KAI_REQ_LEVEL[m], 2 * BossConstants.KAI_HP[m], 380, 0);
        }
        b("Papulatus", "Easy", 125, 400_000_000L, 50, 0);
        b("Papulatus", "Normal", 155, 16_800_000_000L, 90, 0);
        b("Papulatus", "Chaos", 190, 504 * b, 250, 0);
        b("OMNI-CLN", "Normal", 180, 1_680_000_000L, 60, 0);
        // Gollux: the head's three phases (the arms, legs and body before it are much smaller). The mode is set by the
        // parts left standing; the head keeps its data's level and defense in every mode.
        b("Gollux", "Easy", 52, golluxHp(BossConstants.EASY_GOLLUX_HEAD_JAW, BossConstants.EASY_GOLLUX_HEAD_EYES,
                BossConstants.EASY_GOLLUX_HEAD_JEWEL), 10, 0);
        b("Gollux", "Normal", 52, golluxHp(BossConstants.NORMAL_GOLLUX_HEAD_JAW, BossConstants.NORMAL_GOLLUX_HEAD_EYES,
                BossConstants.NORMAL_GOLLUX_HEAD_JEWEL), 10, 0);
        b("Gollux", "Hard", 52, golluxHp(BossConstants.HARD_GOLLUX_HEAD_JAW, BossConstants.HARD_GOLLUX_HEAD_EYES,
                BossConstants.HARD_GOLLUX_HEAD_JEWEL), 10, 0);
        b("Gollux", "Hell", 52, golluxHp(BossConstants.HELL_GOLLUX_HEAD_JAW, BossConstants.HELL_GOLLUX_HEAD_EYES,
                BossConstants.HELL_GOLLUX_HEAD_JEWEL), 10, 0);
        b("Princess No", "Normal", 180, BossConstants.getOfficialMaxHp(BossConstants.PRINCESS_NO), 100, 0);
        b("Akechi Mitsuhide", "Normal", 210, BossConstants.AKECHI_HP, 300, 0);
        b("Lucid", "Hard", 230, BossConstants.LUCID_HARD_HP_1 + BossConstants.LUCID_HARD_HP_2
                + BossConstants.LUCID_HARD_HP_3, 300, 360);
        b("Will", "Easy", 235, 2 * BossConstants.EASY_WILL_P1_HP + BossConstants.EASY_WILL_P2_HP
                + BossConstants.EASY_WILL_P3_HP, 300, 560);
        // Chosen Seren: Sacred Force 150 in phase 1, 200 in phase 2
        g("Chosen Seren", "Normal", 270, BossConstants.SEREN_HP[0][0] + BossConstants.SEREN_HP[0][1], 380, 200);
        g("Chosen Seren", "Hard", 275, BossConstants.SEREN_HP[1][0] + BossConstants.SEREN_HP[1][1], 380, 200);
        g("Chosen Seren", "Extreme", 275, BossConstants.SEREN_HP[2][0] + BossConstants.SEREN_HP[2][1], 380, 200);
        BOSSES.sort((x, y) -> Long.compare(x.hp, y.hp));
    }

    private static long lotusHp(int mode) {
        long hp = 0;
        for (long part : BossConstants.LOTUS_V265_HP[mode]) {
            hp += part;
        }
        return hp;
    }

    private static long blackMageHp(int mode) {
        long hp = 0;
        for (long part : BossConstants.BLACK_MAGE_HP[mode]) {
            hp += part;
        }
        return hp;
    }

    private static long golluxHp(long... phases) {
        long hp = 0;
        for (long p : phases) {
            hp += p;
        }
        return hp;
    }

    public static List<Boss> getBosses() {
        return BOSSES;
    }

    /** @return the top of the character's stat window damage range */
    public static long getRangeMax(Char chr) {
        DamageCalc dc = chr.getDamageCalc() != null ? chr.getDamageCalc() : new DamageCalc(chr, 0, 0, 0);
        return Math.max(1, (long) dc.getStatRangeMax());
    }

    /**
     * @return how much of the character's damage gets through to a target of this level, defense (PDR %) and Arcane
     * Force need (0: none): 1 = all of it
     */
    public static double getHitMultiplier(Char chr, int level, int pdr, int arcaneForce) {
        double ied = chr.getTotalStat(BaseStat.ied) / 100D;
        double defense = Math.max(0, 1 - pdr / 100D * (1 - ied));
        double levelMult = Math.max(0, 1 + GameConstants.getDamageBonusFromLevelDifference(chr.getLevel(), level));
        double afMult = 1;
        if (arcaneForce > 0) {
            int arcPercent = DamageCalc.getArcPercent(chr, arcaneForce);
            afMult = Math.max(0, 1 + GameConstants.getArcaneForceMultiplier(arcPercent) / 100D);
        }
        return defense * levelMult * afMult;
    }

    /** @return whether the character's damage was measured in real fights (otherwise it's guessed from the range) */
    public static boolean isMeasured(Char chr) {
        return chr.getDpsTracker().getMeasuredDps() > 0;
    }

    /** @return the measured damage per second against a target with no defense, or 0 if not measured yet */
    public static double getMeasuredDps(Char chr) {
        return chr.getDpsTracker().getMeasuredDps();
    }

    /** @return seconds of boss fighting the measurement comes from */
    public static long getMeasuredSeconds(Char chr) {
        return chr.getDpsTracker().getMeasuredMillis() / 1000;
    }

    /** @return the guessed damage per second against a target with no defense, from the stat window's range */
    public static double getGuessedDps(Char chr) {
        DamageCalc dc = chr.getDamageCalc() != null ? chr.getDamageCalc() : new DamageCalc(chr, 0, 0, 0);
        double range = dc.getStatRangeAverage();
        double damage = Math.max(0, 1 + (chr.getTotalStat(BaseStat.damR) + chr.getTotalStat(BaseStat.bd)) / 100D);
        double critRate = chr.getTotalStat(BaseStat.cr) + chr.getTotalStat(BaseStat.addCrOnBoss);
        double crit = Math.min(1, Math.max(0, critRate / 100D));
        double critMult = 1 + crit * (0.35 + chr.getTotalStat(BaseStat.crDmg) / 100D);
        double fd = Math.max(0, 1 + chr.getTotalStat(BaseStat.fd) / 100D);
        return range * damage * critMult * fd * RANGE_TO_DPS;
    }

    /**
     * @return estimated damage per second against this boss (0 if the character can't hurt it): measured when
     * possible, guessed otherwise
     */
    public static double getDamagePerSecond(Char chr, Boss boss) {
        return getDamagePerSecond(chr, boss, getBaseDps(chr));
    }

    /**
     * The character's damage per second before any boss's defense: measured when possible, guessed otherwise.
     * Working it out reads the character's whole stat window, so a loop over the bosses gets it once and passes it on.
     */
    public static double getBaseDps(Char chr) {
        double measured = getMeasuredDps(chr);
        return measured > 0 ? measured : getGuessedDps(chr);
    }

    public static double getDamagePerSecond(Char chr, Boss boss, double baseDps) {
        double dps = baseDps * getHitMultiplier(chr, boss.level, boss.pdr, boss.arcaneForce);
        if (boss.authenticForce > 0) {
            dps *= 1 + DamageCalc.getAuthenticForceMultiplier(chr.getTotalStat(BaseStat.authForce)
                    - boss.authenticForce) / 100D;
        }
        return dps;
    }

    /** @return estimated seconds for this character alone to kill the boss (Long.MAX_VALUE: can't) */
    public static long getKillSeconds(Char chr, Boss boss) {
        return getKillSeconds(chr, boss, getBaseDps(chr));
    }

    /** As {@link #getKillSeconds(Char, Boss)}, with the character's base damage per second already known. */
    public static long getKillSeconds(Char chr, Boss boss, double baseDps) {
        double dps = getDamagePerSecond(chr, boss, baseDps) * UPTIME;
        if (dps <= 0) {
            return Long.MAX_VALUE;
        }
        return (long) Math.ceil(boss.hp / dps);
    }

    /** @return 2 = comfortable, 1 = possible within the time limit, 0 = not yet */
    public static int getVerdict(Char chr, Boss boss) {
        return getVerdict(chr, boss, getBaseDps(chr));
    }

    public static int getVerdict(Char chr, Boss boss, double baseDps) {
        if (chr.getLevel() < boss.entryLevel) {
            return 0;
        }
        long s = getKillSeconds(chr, boss, baseDps);
        if (s <= boss.timeLimitSeconds * COMFORTABLE_PART) {
            return 2;
        }
        return s <= boss.timeLimitSeconds ? 1 : 0;
    }

    /**
     * @return why the boss isn't beatable yet, worst first (level, Arcane/Sacred Force, damage for the time limit), or
     * an empty string
     */
    public static String getReason(Char chr, Boss boss, double baseDps) {
        List<String> res = new ArrayList<>();
        if (chr.getLevel() < boss.entryLevel) {
            res.add("needs Lv. " + boss.entryLevel);
        }
        if (boss.arcaneForce > 0) {
            int arc = chr.getTotalStat(BaseStat.arc);
            arc += net.swordie.ms.handlers.social.module.GuildModule.getArcaneForceBonus(chr, arc);
            if (arc < boss.arcaneForce) {
                res.add("Arcane Force " + arc + "/" + boss.arcaneForce);
            }
        }
        if (boss.authenticForce > 0 && chr.getTotalStat(BaseStat.authForce) < boss.authenticForce) {
            res.add("Sacred Force " + chr.getTotalStat(BaseStat.authForce) + "/" + boss.authenticForce);
        }
        long s = getKillSeconds(chr, boss, baseDps);
        if (s > boss.timeLimitSeconds) {
            res.add("damage: " + formatSeconds(s) + " for a " + boss.timeLimitSeconds / 60 + " min limit");
        }
        return String.join(", ", res);
    }

    public static String formatSeconds(long s) {
        if (s == Long.MAX_VALUE) {
            return "never";
        }
        if (s < 60) {
            return s + "s";
        }
        if (s < 3600) {
            return (s / 60) + "m " + (s % 60) + "s";
        }
        long h = s / 3600;
        return h > 99 ? "99h+" : h + "h " + (s % 3600) / 60 + "m";
    }

    public static String formatNumber(double n) {
        if (n >= 1e12) {
            return String.format("%.1fT", n / 1e12);
        }
        if (n >= 1e9) {
            return String.format("%.1fB", n / 1e9);
        }
        if (n >= 1e6) {
            return String.format("%.1fM", n / 1e6);
        }
        if (n >= 1e3) {
            return String.format("%.1fK", n / 1e3);
        }
        return String.format("%.0f", n);
    }

    /**
     * Measures a character's real damage per second on bosses (and the training dummy): its hits on the bosses of one
     * map with no break longer than {@link #GAP_MS} make a fight (all parts of a boss together, like the boss list's
     * HP); fights of at least {@link #MIN_FIGHT_MS} count. The damage is
     * worked back to what it would be against no defense (the target's defense, level), so fights on any boss can be
     * compared. The last {@link #MAX_FIGHTS} fights are kept (also over logins, in a server-only record).
     */
    public static final class DpsTracker {
        /** Longer breaks between hits on a target end the fight (deaths, moving, waiting for a phase). */
        public static final long GAP_MS = 4_000;
        /** Shorter fights don't count: a few big hits would look like more damage than a real fight has. */
        public static final long MIN_FIGHT_MS = 10_000;
        /** Damage is only trusted once fights add up to this long. */
        public static final long MIN_TOTAL_MS = 20_000;
        public static final int MAX_FIGHTS = 5;
        /** Added to a fight's length for the time the last attack takes. */
        private static final long LAST_ATTACK_MS = 600;
        /** Old records lack the field's force multiplier needed to normalize their damage accurately. */
        private static final String RECORD_PREFIX = "v3|"; // v2 records predate the final damage / IED fix

        private final Char chr;
        private final Deque<double[]> fights = new ArrayDeque<>(); // {damage at no defense, milliseconds}
        private Object target; // the map of the current fight (null: none)
        private long start;
        private long last;
        private double damage;

        public DpsTracker(Char chr) {
            this.chr = chr;
            load();
        }

        /** Counts a hit on a boss (or the training dummy). */
        public synchronized void record(Mob mob, long hitDamage) {
            var stat = mob.getForcedMobStat();
            double targetMult = stat == null ? 1 : getHitMultiplier(chr, stat.getLevel(), stat.getPdr(), 0);
            double mult = targetMult * DamageCalc.getFieldForceFactor(chr);
            if (mult < 0.02) {
                return; // the target's defense blocks nearly everything: says nothing about the character's damage
            }
            long now = System.currentTimeMillis();
            Object field = mob.getField();
            if (field != target || now - last > GAP_MS) {
                finishFight();
                target = field;
                start = now;
                damage = 0;
            }
            damage += hitDamage / mult;
            last = now;
        }

        private void finishFight() {
            if (target != null && last - start >= MIN_FIGHT_MS) {
                fights.addLast(new double[]{damage, last - start + LAST_ATTACK_MS});
                while (fights.size() > MAX_FIGHTS) {
                    fights.removeFirst();
                }
                save();
            }
            target = null;
            damage = 0;
        }

        /** @return milliseconds of fighting measured (finished fights, and the current one if long enough) */
        public synchronized long getMeasuredMillis() {
            long ms = 0;
            for (double[] f : fights) {
                ms += (long) f[1];
            }
            if (target != null && last - start >= MIN_FIGHT_MS) {
                ms += last - start + LAST_ATTACK_MS;
            }
            return ms;
        }

        /** @return damage per second at no defense over the measured fights, or 0 if they're too short so far */
        public synchronized double getMeasuredDps() {
            if (target != null && System.currentTimeMillis() - last > GAP_MS) {
                finishFight();
            }
            double dmg = 0;
            double ms = 0;
            for (double[] f : fights) {
                dmg += f[0];
                ms += f[1];
            }
            if (target != null && last - start >= MIN_FIGHT_MS) {
                dmg += damage;
                ms += last - start + LAST_ATTACK_MS;
            }
            return ms >= MIN_TOTAL_MS ? dmg / (ms / 1000D) : 0;
        }

        private void load() {
            try {
                var qm = chr.getQuestManager();
                String rec = qm == null ? null : qm.getHiddenRecord(GameConstants.BOSS_DPS_QR);
                if (rec == null || rec.isEmpty()) {
                    return;
                }
                if (!rec.startsWith(RECORD_PREFIX)) {
                    return; // Re-measure: old records did not account for map Star Force / Arcane Force
                }
                for (String part : rec.substring(RECORD_PREFIX.length()).split(",")) {
                    String[] v = part.split(":");
                    if (v.length == 2) {
                        fights.addLast(new double[]{Double.parseDouble(v[0]), Double.parseDouble(v[1])});
                    }
                }
                while (fights.size() > MAX_FIGHTS) {
                    fights.removeFirst();
                }
            } catch (Exception e) {
                fights.clear(); // a broken record: measure again
            }
        }

        private void save() {
            var qm = chr.getQuestManager();
            if (qm == null) {
                return;
            }
            StringBuilder sb = new StringBuilder(RECORD_PREFIX);
            for (double[] f : fights) {
                if (sb.length() > RECORD_PREFIX.length()) {
                    sb.append(',');
                }
                sb.append((long) f[0]).append(':').append((long) f[1]);
            }
            qm.setHiddenRecord(GameConstants.BOSS_DPS_QR, sb.toString());
        }
    }
}
