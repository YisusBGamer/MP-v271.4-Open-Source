package net.swordie.ms.enums;

import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.constants.OzConstants;
import net.swordie.ms.util.Util;

import java.util.Arrays;
import java.util.Set;

public enum BossCooldown {
    // GMS clear limits (maplestorywiki.net): how often a clear counts, and which difficulties share one clear (same
    // group). An entry locks only the group's next entry for BossConstants.ENTRY_COOLDOWN minutes.

    // Easy
    ZakumEasy(Limit.DAILY, "Zakum", BossConstants.EASY_ZAKUM_BODY),
    HorntailEasy(Limit.DAILY, "Horntail", BossConstants.HORNTAIL_EASY),
    ScarlionTarga(Limit.DAILY, null, 9420544, 9420549), // the Furious forms are the clear (Dapsy, from the Discord)
    MagnusEasy(Limit.DAILY, "Magnus", BossConstants.EASY_MAGNUS_TEMPLATE), // for 5th job quest
    CygnusEasy(Limit.WEEKLY, "Cygnus", BossConstants.CYGNUS_EASY),
    VonLeonEasy(Limit.DAILY, "VonLeon", BossConstants.VON_LEON_EASY),
    ArkariumEasy(Limit.DAILY, "Arkarium", BossConstants.ARKARIUM_EASY),
    PapulatusEasy(Limit.DAILY, "Papulatus", BossConstants.PAPULATUS_EASY_P2),
    LucidEasy(Limit.WEEKLY, "Lucid", BossConstants.LUCID_REWARD_BOX_EASY),
    WillEasy(Limit.WEEKLY, "Will", BossConstants.EASY_WILL_P3, BossConstants.WILL_MIRROR[0]),

    // Normal
    Hilla(Limit.DAILY, null, 8870000),
    Horntail(Limit.DAILY, "Horntail", BossConstants.HORNTAIL_NORMAL),
    Zakum(Limit.DAILY, "Zakum", 8800002),
    VonLeon(Limit.DAILY, "VonLeon", BossConstants.VON_LEON_NORMAL),
    PinkBean(Limit.DAILY, null, BossConstants.PINK_BEAN + 1),
    Queen(Limit.DAILY, null, BossConstants.CRIMSON_QUEEN_NORMAL_BOX),
    Pierre(Limit.DAILY, null, BossConstants.PIERRE_NORMAL_BOX),
    VonBon(Limit.DAILY, null, BossConstants.VON_BON_NORMAL),
    Vellum(Limit.DAILY, null, BossConstants.VELLUM_NORMAL),
    Ranmaru(Limit.DAILY, "Ranmaru", BossConstants.RANMARU_NORMAL_BODY),
    Arkarium(Limit.DAILY, "Arkarium", 8860000),
    Magnus(Limit.DAILY, "Magnus", 8880002),
    Cygnus(Limit.WEEKLY, "Cygnus", 8850011),
    Gollux(Limit.DAILY, null, BossConstants.GOLLUX_REWARD_EASY, BossConstants.GOLLUX_REWARD_NORMAL, BossConstants.GOLLUX_REWARD_HARD, BossConstants.GOLLUX_REWARD_HELL),
    Papulatus(Limit.DAILY, "Papulatus", 8500012),
    Lotus(Limit.WEEKLY, "Lotus", 8950002, BossConstants.lotusV265Core(0)),
    Damien(Limit.WEEKLY, "Damien", 8880111),
    Lucid(Limit.WEEKLY, "Lucid", 8880167),
    Will(Limit.WEEKLY, "Will", BossConstants.WILL_P3, BossConstants.WILL_MIRROR[1]),
    VerusHilla(Limit.WEEKLY, "VerusHilla", BossConstants.VERUS_HILLA),
    Gloom(Limit.WEEKLY, "Gloom", 8644650),
    Darknell(Limit.WEEKLY, "Darknell", BossConstants.DARKNELL_NORMAL),
    OmniCln(Limit.DAILY, null, BossConstants.OMNI_CLN),

    // Hard (Chaos)
    ZakumHard(Limit.WEEKLY, null, 8800102),
    HillaHard(Limit.WEEKLY, null, 8870100),
    HorntailHard(Limit.DAILY, "Horntail", 8810118, 8810122), // GMS spawns 8810122 (the boss list id)
    VonLeonHard(Limit.DAILY, "VonLeon", BossConstants.VON_LEON_HARD),
    PinkBeanHard(Limit.WEEKLY, null, BossConstants.CHAOS_PINK_BEAN_P2),
    QueenHard(Limit.WEEKLY, null, BossConstants.CRIMSON_QUEEN_CHAOS_BOX),
    PierreHard(Limit.WEEKLY, null, BossConstants.PIERRE_CHAOS_BOX),
    VonBonHard(Limit.WEEKLY, null, BossConstants.VON_BON_CHAOS),
    VellumHard(Limit.WEEKLY, null, BossConstants.VELLUM_CHAOS),
    RanmaruHard(Limit.DAILY, "Ranmaru", BossConstants.RANMARU_HARD_BODY_5),
    MagnusHard(Limit.WEEKLY, null, 8880000),
    PapulatusHard(Limit.WEEKLY, null, 8500022),
    LotusHard(Limit.WEEKLY, "Lotus", 8950102, BossConstants.lotusV265Core(1)),
    LotusExtreme(Limit.WEEKLY, "Lotus", BossConstants.lotusV265Core(2)),
    Dorothy(Limit.DAILY, null, OzConstants.DOROTHY),
    DamienHard(Limit.WEEKLY, "Damien", 8880101),
    LucidHard(Limit.WEEKLY, "Lucid", 8880177),
    WillHard(Limit.WEEKLY, "Will", BossConstants.HARD_WILL_P3, BossConstants.WILL_MIRROR[2]),
    VerusHillaHard(Limit.WEEKLY, "VerusHilla", BossConstants.VERUS_HILLA_HARD),
    GloomHard(Limit.WEEKLY, "Gloom", BossConstants.GLOOM_HARD),
    DarknellHard(Limit.WEEKLY, "Darknell", BossConstants.DARKNELL_HARD),
    BlackMage(Limit.MONTHLY, null),
    GuardianAngelSlime(Limit.WEEKLY, "GuardianAngelSlime", BossConstants.GUARDIAN_ANGEL_SLIME_NORMAL_MOB, BossConstants.GUARDIAN_ANGEL_SLIME_NORMAL_CHEST),
    GuardianAngelSlimeChaos(Limit.WEEKLY, "GuardianAngelSlime", BossConstants.GUARDIAN_ANGEL_SLIME_CHAOS_MOB, BossConstants.GUARDIAN_ANGEL_SLIME_CHAOS_CHEST),
    Seren(Limit.WEEKLY, "Seren", BossConstants.serenMob(0, BossConstants.SEREN_TREASURE_CHEST)),
    SerenHard(Limit.WEEKLY, "Seren", BossConstants.serenMob(1, BossConstants.SEREN_TREASURE_CHEST)),
    SerenExtreme(Limit.WEEKLY, "Seren", BossConstants.serenMob(2, BossConstants.SEREN_TREASURE_CHEST)),
    Akechi(Limit.WEEKLY, null, BossConstants.AKECHI),
    Kalos(Limit.WEEKLY, null, BossConstants.KALOS_CHEST), // one clear a week for all modes
    Kaling(Limit.WEEKLY, null, BossConstants.getKalingChests()),
    FirstAdversary(Limit.WEEKLY, null, BossConstants.chestsOf(BossConstants.ADVERSARY_BASE, 15)),
    Limbo(Limit.WEEKLY, null, BossConstants.chestsOf(BossConstants.LIMBO_BASE, 34)),
    Baldrix(Limit.WEEKLY, null, BossConstants.chestsOf(BossConstants.BALDRIX_BASE, 24)),
    MaleficStar(Limit.WEEKLY, null, BossConstants.chestsOf(BossConstants.MALEFIC_BASE, BossConstants.MALEFIC_CHEST)),
    Jupiter(Limit.WEEKLY, null, BossConstants.chestsOf(BossConstants.JUPITER_BASE, BossConstants.JUPITER_CHEST)),
    Kai(Limit.WEEKLY, null, BossConstants.chestsOf(BossConstants.KAI_BASE, BossConstants.KAI_CHEST)),
    ;

    /** How often a clear counts: GMS resets daily at 00:00 UTC, weekly on Thursday 00:00 UTC, monthly on the 1st. */
    public enum Limit {
        DAILY, WEEKLY, MONTHLY
    }

    private final Limit limit;
    private final String group;
    private final Set<Integer> templateIds;

    BossCooldown(Limit limit, String group, int... templateIds) {
        this.limit = limit;
        this.group = group;
        this.templateIds = Util.makeSet(templateIds);
    }

    public Limit getLimit() {
        return limit;
    }

    /** The difficulties GMS counts as one clear share a group (a boss without one counts alone). */
    public String getGroup() {
        return group == null ? name() : group;
    }

    public boolean sharesWith(BossCooldown other) {
        return other != null && getGroup().equals(other.getGroup());
    }

    /** When the clear made now stops counting: the next daily/weekly/monthly reset. */
    public long nextResetMillis(long now) {
        var t = net.swordie.ms.util.ResetClock.dayOf(now);
        var next = switch (limit) {
            case DAILY -> t.plusDays(1);
            case WEEKLY -> t.with(java.time.temporal.TemporalAdjusters.next(java.time.DayOfWeek.THURSDAY));
            case MONTHLY -> t.withDayOfMonth(1).plusMonths(1);
        };
        return net.swordie.ms.util.ResetClock.startOfDay(next);
    }

    public Set<Integer> getTemplateIds() {
        return templateIds;
    }

    public static BossCooldown byTemplateId(int templateId) {
        return Arrays.stream(values()).filter(bc -> bc.templateIds.contains(templateId)).findAny().orElse(null);
    }

}
