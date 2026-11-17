package net.swordie.ms.client.character.skills.temp;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.atom.forceatom.StopForceAtom;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Local buff packets for the v271 client (TEMPORARY_STAT_SET / TEMPORARY_STAT_RESET).
 * Mask: 40 little-endian ints, index i = word i / 32, bit 1 << (i % 32). Stats come from resources/v271-cts-local.tsv;
 * a stat that is not listed there is left out of the packet entirely. Remote packets keep the old layout.
 */
public final class V271LocalStats {

    private static final Logger log = LogManager.getLogger(V271LocalStats.class);
    private static final String FILE = ServerConstants.RESOURCES_DIR + "/v271-cts-local.tsv";

    public static final int MASK_INTS = 40;
    /** Indices past this are storage only in the client (its bit test errors on them). */
    public static final int LAST_INDEX = 1263;

    /** Any of these in the mask makes every generic value an int (otherwise a short), for the whole packet. */
    private static final int[] WIDE = {202, 206, 325, 388, 394, 447, 467, 481, 484, 498, 513, 515, 518, 541, 556, 585,
            587, 592, 606, 607, 611, 645, 698, 732, 810, 1030, 1056, 1077};
    /** Any of these in the mask adds one byte after the set packet's six flag bytes, and one to a reset. */
    private static final int[] MOVING = {9, 10, 45, 46, 57, 200, 201, 202, 206, 306, 307, 315, 329, 331, 332, 334, 338,
            349, 378, 379, 383, 388, 399, 423, 429, 464, 472, 483, 484, 498, 511, 521, 522, 558, 585, 586, 591, 617, 660,
            662, 758, 759, 812, 813, 830, 915};
    /**
     * Grouped (two-state) stats: wire id = mask index - 200 (ids 0-8 are indices 200-208; 202/206 seed the client's
     * mount mask, 200/201/202/206 are moving stats as in the old order). Each payload starts with B4 value, B4 reason,
     * D1 sign, D4 relative time (13 bytes); ids 0, 1, 5, 6 add D2 expire seconds, 3 adds D1 D4 D2. Ids 0-7 follow the
     * old two-state order (bits 928-935, same payload widths), so 7 is Pathfinder's Relic Gauge (base only, value =
     * gauge). 4 (GuidedBullet, four D4) and 8 are not sent.
     */
    private static final Map<CharacterTemporaryStat, Integer> GROUP = new EnumMap<>(Map.of(
            DashSpeed, 0, DashJump, 1, RideVehicle, 2, PartyBooster, 3, Undead, 5, RideVehicleExpire, 6,
            RelicGauge, 7));
    private static final int GROUP_BASE = 200;
    /** The mount groups: a reset of either reads one more byte at the end. */
    private static final int[] MOUNT = {202, 206};
    /** Indie indices whose removal makes the reset carry the counted Indie section. */
    private static final int INDIE_LAST = 90;
    private static final int INDIE_NO_RESET_SECTION = 47;

    /** Explicit fields read after the generic values and before the group count, in client order. */
    private static final CharacterTemporaryStat[] EXTRAS_EARLY = {
            Dice, CurseOfCreation, CurseOfDestruction, KeyDownMoving, Judgement, StackBuff, Trinity, ElementalCharge,
            LifeTidal, AntiMagicShell, IgnoreTargetDEF, StopForceAtomInfo, SmashStack, MobZoneState, RadiantOrb, Slow,
            IgnoreMobpdpR, BdR, DropRIncrease, PoseType, Beholder, CrossOverChain, ImmuneBarrier, Stance, SharpEyes,
            AdvancedBless, UsefulAdvancedBless, Poison, Bless, DotHealHPPerSecond, DotHealMPPerSecond, SpiritGuard,
            MastemasMark, ShieldAttack, SSFShootingAttack, BattlePvPHelenaMark, PinkbeanAttackBuff, RoyalGuardState,
            MichaelSoulLink, RWCylinder, HitStackDamR, RWMagnumBlow, BladeStance, DarkSight, Stigma, TempSecondaryStat,
            CriticalGrowing, PickPocket, DivineEcho, DemonFrenzy, ShadowSpear, RhoAias, VampDeath, HolyMagicShell,
    };
    /** Explicit fields read after the Indie section, in client order. */
    private static final CharacterTemporaryStat[] EXTRAS_LATE = {
            SylvidiaFlight, WingsOfGlory, LefBuffMastery, TrickBladeMobStat, SixthAssassination, MuscleMemory,
            SpecterEnergy, SpecterState, BasicCast, ScarletCast, GustCast, AbyssalCast, WillPoison, NightWalkerBat,
            PMdReduce, ComboCounter, FifthGoddessBless, AncientGuidance, HolySymbol, AT_Attributes, AT_Energy,
            LW_Nobility, Revenant, RevenantDamage, SilhouetteMirage, HastyAura, DrainingAura, BlueAura, DarkAura,
            WeakeningAura, AuraScythe, IceAura, ParashockGuard, DivineForce, IncarnationAura, BlizzardTempest, PhotonRay,
            AbyssalLightning, CrystalGate, HolyWater, MuscleMemoryFinale, DarknessAura, SerpentScrew, ThanatosDescent,
            NABrutalPang, Magnet, YetiFuryGauge, YetiFuryMode, AT_Clone, YetiCook, NewFlying, ReincarnationMission,
            QuiverBarrage, AdrenalinBoost, Stun, BMageDeath, LamourCarte, AdrenalinMaximum, RapidFire, Nightmare, SixthJavelinStack,
            SixthGloryWingJavelinStack, DslayerMetamorphosis, RenPlumSwordEx, SpiritAwakeningStack,
            MitsuhideDebuff, ShineMageHourglass, ShineWarriorEternalGuardian,
    };

    private static final Map<CharacterTemporaryStat, Integer> INDEX = new EnumMap<>(CharacterTemporaryStat.class);
    private static final List<CharacterTemporaryStat> GENERIC = new ArrayList<>();
    private static final BitSet WIDE_SET = bits(WIDE);
    private static final BitSet MOVING_SET = bits(MOVING);
    private static final BitSet MOUNT_SET = bits(MOUNT);

    static {
        load();
    }

    private V271LocalStats() {
    }

    private static BitSet bits(int[] idx) {
        BitSet b = new BitSet(MASK_INTS * 32);
        for (int i : idx) {
            b.set(i);
        }
        return b;
    }

    private static void load() {
        Map<CharacterTemporaryStat, Integer> order = new EnumMap<>(CharacterTemporaryStat.class);
        try {
            for (String line : Files.readAllLines(Path.of(FILE), StandardCharsets.UTF_8)) {
                if (line.isBlank() || line.startsWith("#")) {
                    continue;
                }
                String[] p = line.split("\t", -1);
                CharacterTemporaryStat cts;
                try {
                    cts = CharacterTemporaryStat.valueOf(p[0]);
                } catch (IllegalArgumentException e) {
                    log.warn("v271 buffs: unknown stat " + p[0]);
                    continue;
                }
                INDEX.put(cts, Integer.parseInt(p[1]));
                if (p.length > 2 && !p[2].isEmpty()) {
                    order.put(cts, Integer.parseInt(p[2]));
                }
            }
        } catch (Exception e) {
            log.error("v271 buffs: cannot read " + FILE + "; no buff will be sent", e);
        }
        order.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(e -> GENERIC.add(e.getKey()));
        if (!INDEX.isEmpty()) {
            GROUP.forEach((cts, id) -> INDEX.putIfAbsent(cts, GROUP_BASE + id));
        }
    }

    /** Whether the v271 client gets this stat in its local buff packets. */
    public static boolean isSent(CharacterTemporaryStat cts) {
        return INDEX.containsKey(cts);
    }

    public static int getIndex(CharacterTemporaryStat cts) {
        return INDEX.getOrDefault(cts, -1);
    }

    /** The server stats sent at this client index (several can share one). */
    public static List<CharacterTemporaryStat> getStatsAt(int index) {
        List<CharacterTemporaryStat> res = new ArrayList<>();
        INDEX.forEach((cts, i) -> {
            if (i == index) {
                res.add(cts);
            }
        });
        return res;
    }

    /** Stats already warned about once (dropped from local buff packets). */
    private static final Set<CharacterTemporaryStat> DROPPED = java.util.concurrent.ConcurrentHashMap.newKeySet();

    private static Map<CharacterTemporaryStat, List<Option>> sentOnly(Map<CharacterTemporaryStat, List<Option>> stats) {
        Map<CharacterTemporaryStat, List<Option>> res = new EnumMap<>(CharacterTemporaryStat.class);
        for (Map.Entry<CharacterTemporaryStat, List<Option>> e : stats.entrySet()) {
            if (e.getValue() != null && isSent(e.getKey())) {
                res.put(e.getKey(), e.getValue());
            } else if (e.getValue() != null && e.getKey().getBitPos() >= 0 && DROPPED.add(e.getKey())) {
                // a client-side buff with no v271 slot: its skill shows nothing (plans/v271-cts-missing.tsv)
                log.warn("[Buffs] " + e.getKey() + " has no v271 slot: left out of the buff packet");
            }
        }
        return res;
    }

    public static int[] getMask(Collection<CharacterTemporaryStat> stats) {
        int[] mask = new int[MASK_INTS];
        for (CharacterTemporaryStat cts : stats) {
            int i = getIndex(cts);
            if (i >= 0) {
                mask[i >>> 5] |= 1 << (i & 31);
            }
        }
        return mask;
    }

    private static boolean intersects(Collection<CharacterTemporaryStat> stats, BitSet set) {
        for (CharacterTemporaryStat cts : stats) {
            int i = getIndex(cts);
            if (i >= 0 && set.get(i)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasMoving(Collection<CharacterTemporaryStat> stats) {
        return intersects(stats, MOVING_SET);
    }

    private static boolean isIndieIndex(int i) {
        return i >= 0 && i <= INDIE_LAST;
    }

    /** Active stats the client knows about (the count byte of both packets). */
    public static int getSentCount(TemporaryStatManager tsm) {
        return (int) tsm.getCurrentStats().keySet().stream().filter(V271LocalStats::isSent)
                .mapToInt(V271LocalStats::getIndex).distinct().count();
    }

    private static Option opt(Map<CharacterTemporaryStat, List<Option>> stats, CharacterTemporaryStat cts) {
        List<Option> options = stats.get(cts);
        return options != null && !options.isEmpty() && options.get(0) != null ? options.get(0) : new Option();
    }

    /**
     * The local set block: mask, generic values, explicit fields, groups, Indie stats, more explicit fields.
     * Returns the stats that were written (the caller's collection is not changed).
     */
    public static Set<CharacterTemporaryStat> encodeSet(OutPacket out, TemporaryStatManager tsm,
                                                        Map<CharacterTemporaryStat, List<Option>> collection) {
        Map<CharacterTemporaryStat, List<Option>> stats = sentOnly(collection);
        for (int m : getMask(stats.keySet())) {
            out.encodeInt(m);
        }
        boolean wide = intersects(stats.keySet(), WIDE_SET);
        BitSet written = new BitSet(MASK_INTS * 32);
        for (CharacterTemporaryStat cts : GENERIC) {
            // several server stats can share one client stat: one value per index
            if (!stats.containsKey(cts) || written.get(getIndex(cts))) {
                continue;
            }
            written.set(getIndex(cts));
            Option o = opt(stats, cts);
            Char chr = tsm.getChr();
            if (cts == ReturnTeleport && chr != null) {
                out.encodeShort(chr.getPosition().getY());
                out.encodeShort(chr.getPosition().getX());
            } else if (wide) {
                out.encodeInt(o.nOption);
            } else {
                out.encodeShort(o.nOption);
            }
            out.encodeInt(o.rOption);
            out.encodeInt(o.tOption);
        }
        if (stats.containsKey(SoulMP)) {
            out.encodeInt(opt(stats, SoulMP).xOption);
            out.encodeInt(opt(stats, SoulMP).rOption);
        }
        if (stats.containsKey(FullSoulMP)) {
            out.encodeInt(opt(stats, FullSoulMP).xOption);
        }
        out.encodeShort(0); // key list
        out.encodeByte(tsm.getDefenseAtt());
        out.encodeByte(tsm.getDefenseState());
        out.encodeByte(tsm.getPvpDamage());
        out.encodeInt(opt(stats, EtherealForm).xOption);
        for (CharacterTemporaryStat cts : EXTRAS_EARLY) {
            if (stats.containsKey(cts)) {
                encodeExtra(out, tsm, stats, cts);
            }
        }
        encodeGroups(out, stats);
        encodeIndie(out, stats.keySet().stream().filter(c -> isIndieIndex(getIndex(c))).toList(), stats::get);
        for (CharacterTemporaryStat cts : EXTRAS_LATE) {
            if (stats.containsKey(cts)) {
                encodeExtra(out, tsm, stats, cts);
            }
        }
        return stats.keySet();
    }

    /** Counted Indie section: count, then per stat its index and its options. */
    private static void encodeIndie(OutPacket out, List<CharacterTemporaryStat> indies,
                                    java.util.function.Function<CharacterTemporaryStat, List<Option>> options) {
        List<CharacterTemporaryStat> sorted = new ArrayList<>(indies);
        sorted.sort(Comparator.comparingInt(V271LocalStats::getIndex));
        int curTime = Util.getCurrentTime();
        out.encodeInt(sorted.size());
        for (CharacterTemporaryStat cts : sorted) {
            List<Option> list = new ArrayList<>(options.apply(cts) == null ? List.of() : options.apply(cts));
            list.removeIf(Objects::isNull);
            out.encodeInt(getIndex(cts));
            out.encodeInt(list.size());
            for (Option o : list) {
                out.encodeInt(o.rOption);
                out.encodeInt(o.nOption);
                out.encodeInt(o.nKey);
                out.encodeInt(curTime - o.tStart);
                out.encodeInt(o.tOption);
                out.encodeInt(0);
                out.encodeInt(0); // value map
                out.encodeInt(0); // value map 2
                out.encodeInt(0); // keyed list map
            }
        }
    }

    /**
     * The local reset block: mask of the removed stats, the Indie section when a removed Indie stat needs it (remaining
     * options), then the moving and pose bytes and the Hourglass int.
     */
    public static void encodeReset(OutPacket out, TemporaryStatManager tsm,
                                   Map<CharacterTemporaryStat, List<Option>> removed) {
        Map<CharacterTemporaryStat, List<Option>> stats = sentOnly(removed);
        // a client stat shared by several server stats stays while one of them is still on
        stats.keySet().removeIf(c -> tsm.getCurrentStats().keySet().stream()
                .anyMatch(o -> o != c && isSent(o) && getIndex(o) == getIndex(c)));
        for (int m : getMask(stats.keySet())) {
            out.encodeInt(m);
        }
        List<CharacterTemporaryStat> indies = stats.keySet().stream()
                .filter(c -> isIndieIndex(getIndex(c))).toList();
        if (indies.stream().anyMatch(c -> getIndex(c) != INDIE_NO_RESET_SECTION)) {
            encodeIndie(out, indies, tsm::getOptions);
        }
        if (hasMoving(stats.keySet())) {
            out.encodeByte(1);
        }
        if (stats.containsKey(PoseType)) {
            out.encodeByte(1);
        }
        if (stats.containsKey(ShineMageHourglass)) {
            out.encodeInt(0);
        }
        if (stats.containsKey(ShineWarriorEternalGuardian)) {
            out.encodeInt(0);
        }
        if (intersects(stats.keySet(), MOUNT_SET)) {
            out.encodeByte(1); // the mount groups' byte, last
        }
    }

    /** The counted group section: 4 n {4 id, payload}. Shared with the remote block (same readers). */
    static void encodeGroups(OutPacket out, Map<CharacterTemporaryStat, List<Option>> stats) {
        List<CharacterTemporaryStat> groups = new ArrayList<>();
        for (CharacterTemporaryStat cts : stats.keySet()) {
            if (GROUP.containsKey(cts)) {
                groups.add(cts);
            }
        }
        groups.sort(Comparator.comparingInt(GROUP::get));
        out.encodeInt(groups.size());
        for (CharacterTemporaryStat cts : groups) {
            int id = GROUP.get(cts);
            Option o = opt(stats, cts);
            out.encodeInt(id);
            out.encodeInt(o.nOption);
            out.encodeInt(o.rOption);
            out.encodeByte(0); // sign: the time below is added to the client's clock
            out.encodeInt(0); // relative time: now
            int expire = Math.max(0, o.tOption) / 1000;
            switch (id) {
                case 0, 1, 5, 6 -> out.encodeShort(Math.min(expire, Short.MAX_VALUE));
                case 3 -> {
                    out.encodeByte(0);
                    out.encodeInt(0);
                    out.encodeShort(Math.min(expire, Short.MAX_VALUE));
                }
                default -> {
                }
            }
        }
    }

    /** The grouped stats among these (both packets put them in the mask at 200 + id). */
    static boolean isGroup(CharacterTemporaryStat cts) {
        return GROUP.containsKey(cts);
    }

    static int groupIndex(CharacterTemporaryStat cts) {
        return GROUP_BASE + GROUP.get(cts);
    }

    static boolean isMount(int index) {
        return MOUNT_SET.get(index);
    }

    private static void encodeExtra(OutPacket out, TemporaryStatManager tsm,
                                    Map<CharacterTemporaryStat, List<Option>> stats, CharacterTemporaryStat cts) {
        Option o = opt(stats, cts);
        switch (cts) {
            case Dice -> {
                int[] dice = tsm.getDiceInfo();
                for (int i = 0; i < 21; i++) {
                    out.encodeInt(dice != null && i < dice.length ? dice[i] : 0);
                }
            }
            case StopForceAtomInfo -> {
                StopForceAtom sfa = tsm.getStopForceAtom();
                if (sfa != null) {
                    sfa.encode(out);
                } else {
                    out.encodeInt(0);
                    out.encodeInt(0);
                    out.encodeInt(0);
                    out.encodeInt(0);
                }
            }
            case MobZoneState -> {
                for (int zoneState : tsm.getMobZoneStates()) {
                    if (zoneState > 0) {
                        out.encodeInt(zoneState);
                    }
                }
                out.encodeInt(0);
            }
            case RadiantOrb -> out.encodeInt(0); // skill id list
            case StackBuff, Trinity -> out.encodeByte(o.mOption);
            case LifeTidal, IgnoreTargetDEF, SharpEyes -> out.encodeInt(o.mOption);
            case ElementalCharge -> {
                out.encodeByte(o.xOption);
                out.encodeShort(o.yOption);
                out.encodeByte(o.uOption);
                out.encodeByte(o.wOption);
            }
            case AntiMagicShell -> {
                out.encodeByte(o.bOption);
                out.encodeInt(o.tOption);
            }
            case SmashStack -> {
                out.encodeInt(o.xOption);
                out.encodeInt(o.yOption);
                out.encodeInt(o.tOption);
            }
            case Slow, IgnoreMobpdpR, BdR -> out.encodeByte(o.bOption);
            case DropRIncrease, YetiCook -> {
                out.encodeInt(o.xOption);
                out.encodeByte(o.bOption);
            }
            case PoseType -> {
                out.encodeByte(o.bOption);
                out.encodeByte(o.sOption);
            }
            case Beholder -> out.encodeInt(o.sOption);
            case SpiritGuard -> out.encodeInt(o.nOption);
            case MastemasMark -> out.encodeInt(tsm.getOption(MastemasMark).xOption);
            case BattlePvPHelenaMark -> out.encodeInt(o.cOption);
            case PinkbeanAttackBuff, Stigma -> out.encodeInt(o.bOption);
            case RoyalGuardState -> {
                out.encodeInt(o.bOption);
                out.encodeInt(o.xOption);
            }
            case MichaelSoulLink -> {
                out.encodeInt(o.xOption);
                out.encodeByte(o.bOption);
                out.encodeInt(o.cOption);
                out.encodeInt(o.yOption);
            }
            case RWCylinder -> {
                out.encodeByte(o.bOption);
                out.encodeShort(o.cOption);
                out.encodeByte(o.xOption);
            }
            case RWMagnumBlow -> {
                out.encodeShort(o.bOption);
                out.encodeByte(o.xOption);
            }
            case DarkSight -> {
                out.encodeInt(o.cOption);
                out.encodeInt(o.xOption);
                out.encodeInt(o.yOption);
            }
            case DivineEcho, DemonFrenzy, ShadowSpear -> out.encodeShort(o.xOption);
            case RhoAias -> {
                out.encodeInt(o.xOption);
                out.encodeInt(o.bOption);
                out.encodeInt(o.cOption);
                out.encodeInt(o.yOption);
            }
            case WingsOfGlory, LefBuffMastery, BasicCast, ScarletCast, GustCast, AbyssalCast -> {
                out.encodeInt(o.xOption);
                out.encodeInt(o.cOption);
            }
            case AdvancedBless, ComboCounter, AncientGuidance, AT_Attributes, LW_Nobility, HastyAura, DrainingAura,
                    DarkAura, WeakeningAura, AuraScythe, IceAura, ParashockGuard, DivineForce, IncarnationAura,
                    SerpentScrew, NABrutalPang, Stun, AdrenalinMaximum, RenPlumSwordEx -> {
                out.encodeInt(o.xOption);
                out.encodeInt(o.yOption);
            }
            case FifthGoddessBless, RevenantDamage, BlueAura -> {
                out.encodeInt(o.xOption);
                out.encodeInt(o.yOption);
                out.encodeInt(o.zOption);
            }
            case HolySymbol -> {
                out.encodeInt(o.xOption);
                out.encodeInt(o.yOption);
                out.encodeInt(o.zOption);
                out.encodeInt(o.wOption);
                out.encodeByte(o.bOption);
                out.encodeByte(0);
                out.encodeInt(o.pOption);
            }
            case Revenant -> out.encodeInt(o.zOption);
            case LamourCarte -> {
                out.encodeInt(o.xOption);
                out.encodeInt(0); // second field unknown
            }
            default -> out.encodeInt(o.xOption);
        }
    }
}
