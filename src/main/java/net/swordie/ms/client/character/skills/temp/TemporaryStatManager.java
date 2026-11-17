package net.swordie.ms.client.character.skills.temp;

import net.swordie.ms.client.jobs.sengoku.Kanna;
import io.netty.util.internal.ConcurrentSet;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.GuidedBullet;
import net.swordie.ms.client.character.skills.PartyBooster;
import net.swordie.ms.client.character.skills.*;
import net.swordie.ms.client.character.skills.atom.forceatom.StopForceAtom;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.client.jobs.resistance.demon.DemonAvenger;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.enums.TSIndex;
import net.swordie.ms.events.Events;
import net.swordie.ms.life.AffectedArea;
import net.swordie.ms.life.Summon;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.Util;
import net.swordie.ms.util.container.Tuple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Created on 1/3/2018.
 */
public class TemporaryStatManager {
    private static final Logger log = LogManager.getRootLogger();

    public final Map<CharacterTemporaryStat, List<Option>> currentStats = new ConcurrentHashMap<>();
    public final Map<CharacterTemporaryStat, List<Option>> newStats = new ConcurrentHashMap<>();
    public final Map<CharacterTemporaryStat, List<Option>> removedStats = new ConcurrentHashMap<>();

    private final Lock statLock = new ReentrantLock();

    private int pvpDamage;
    private byte defenseState;
    private byte defenseAtt;
    private int[] diceInfo = new int[22];
    private int[] diceOption = new int[8];
    private Set<Integer> mobZoneStates = new HashSet<>();
    private int viperEnergyCharge;
    private StopForceAtom stopForceAtom;
    private LarknessManager larknessManager;
    private Char chr;
    private List<TemporaryStatBase> twoStates = new ArrayList<>();
    private Set<AffectedArea> affectedAreas = new ConcurrentSet<>();
    private Map<BaseStat, Integer> baseStats = new HashMap<>();
    private Map<BaseStat, List<Integer>> nonAddBaseStats = new HashMap<>();
    private Set<OutPacket> resetStatsUpdate = new HashSet<>();

    private long lastStun = Long.MIN_VALUE;
    private int putCount;

    public TemporaryStatManager(Char chr) {
        this.chr = chr;
        for (CharacterTemporaryStat cts : TSIndex.getAllCTS()) {
            switch (cts) {
                case PartyBooster:
                    twoStates.add(new PartyBooster());
                    break;
                case GuidedBullet:
                    twoStates.add(new GuidedBullet());
                    break;
                case EnergyCharged:
                    twoStates.add(new TemporaryStatBase(true));
                    break;
                case RideVehicle:
                case RelicGauge:
                    twoStates.add(new TwoStateTemporaryStat(false));
                    break;
                case Grave:
                    twoStates.add(new GraveProclomation(false));
                    break;
                default:
                    twoStates.add(new TwoStateTemporaryStat(true));
                    break;
            }
        }
    }

    public List<TemporaryStatBase> getTwoStates() {
        return twoStates;
    }

    public TemporaryStatBase getTSBByTSIndex(TSIndex tsi) {
        return getTwoStates().get(tsi.getIndex());
    }

    public void putCharacterStatValue(CharacterTemporaryStat cts, Option option) {
        putCharacterStatValue(cts, option, false);
    }

    /**
     * GMS sends a booster skill whose data has x (and no indieBooster) as the plain Booster stat, and only skills with
     * indieBooster as IndieBooster. Handlers put IndieBooster for both; the data decides here.
     */
    private CharacterTemporaryStat boosterStatFor(CharacterTemporaryStat cts, Option option) {
        if (cts != IndieBooster || option.byMob || option.rOption <= 0 || getChr() == null) {
            return cts;
        }
        var bsi = net.swordie.ms.loaders.SkillData.getSkillInfoById(option.rOption);
        if (bsi == null || bsi.getSkillStatInfo().containsKey(net.swordie.ms.client.character.skills.SkillStat.indieBooster)
                || !bsi.getSkillStatInfo().containsKey(net.swordie.ms.client.character.skills.SkillStat.x)) {
            return cts;
        }
        int slv = Math.max(1, getChr().getSkillLevel(option.rOption));
        int x = bsi.getValue(net.swordie.ms.client.character.skills.SkillStat.x, slv);
        return x < 0 && option.nOption == x ? Booster : cts;
    }

    /** How many stats were put so far: lets a caller see whether a skill handler gave anything. */
    public int getPutCount() {
        return putCount;
    }

    public void putCharacterStatValue(CharacterTemporaryStat cts, Option option, boolean noBuffDurationInc) { // reapply is used when the buff's Options have to be changed whilst the duration stays the same (e.g. Infinity)
        putCount++;
        cts = boosterStatFor(cts, option);
        boolean indie = cts.isIndie();
        option.setTimeToMillis();
        int skillId = option.rOption;
        // items (rOption = -item id: EXP coupons, potions) last their own time; buff duration is for skills,
        // not for monster debuffs
        if (!SkillConstants.isNoBuffDurationAppliedSkill(skillId) && !noBuffDurationInc && skillId >= 0 && !option.byMob
                && option.tOption != option.stretchedTime) {
            long buffTimeR = getChr().getTotalStat(BaseStat.buffTimeR); // includes the 100% base
            option.tOption = (int) ((buffTimeR * option.tOption) / 100D);
            option.stretchedTime = option.tOption;
        }

        if (cts == CombatOrders) {
            chr.setCombatOrders(option.nOption);
        }

        if (TSIndex.isTwoState(cts) && TSIndex.getTSEFromCTS(cts) != null) {
            var twoState = getTSBByTSIndex(TSIndex.getTSEFromCTS(cts));
            if (twoState != null) {
                twoState.setLastUpdated(FileTime.currentTime());
            }
        }

        if (!indie) {
            List<Option> optList = new ArrayList<>();
            optList.add(option);
            if (hasStat(cts)) {
                Option oldOption = getCurrentStats().get(cts).get(0);
                // remove old base stats from map
                for (Map.Entry<BaseStat, Integer> stats : BaseStat.getFromCTS(chr, cts, oldOption).entrySet()) {
                    removeBaseStat(stats.getKey(), stats.getValue());
                }
            }
            getNewStats().put(cts, optList);
            getCurrentStats().put(cts, optList);
            for (Map.Entry<BaseStat, Integer> stats : BaseStat.getFromCTS(chr, cts, option).entrySet()) {
                addBaseStat(stats.getKey(), stats.getValue());
            }
        } else {
            List<Option> optList;
            if (hasStat(cts)) {
                optList = getCurrentStats().get(cts);
            } else {
                optList = new ArrayList<>();
            }
            if (optList.contains(option)) {
                // remove old option of the same skill (an equal option without a skill isn't found by skill)
                Option oldOption = getOptByCTSAndSkill(cts, option.rOption);
                if (oldOption == null) {
                    oldOption = optList.get(optList.indexOf(option));
                }
                for (Map.Entry<BaseStat, Integer> stats : BaseStat.getFromCTS(chr, cts, oldOption).entrySet()) {
                    removeBaseStat(stats.getKey(), stats.getValue());
                }
                optList.remove(oldOption);
            }
            optList.add(option);
            getNewStats().put(cts, optList);
            getCurrentStats().put(cts, optList);
            // Add stats to basestat
            for (Map.Entry<BaseStat, Integer> stats : BaseStat.getFromCTS(chr, cts, option).entrySet()) {
                addBaseStat(stats.getKey(), stats.getValue());
            }
        }
        if (cts != LifeTidal && chr.getJobHandler() instanceof DemonAvenger) {
            ((DemonAvenger) chr.getJobHandler()).sendHpUpdate();
        }
        // Handler for specific CTSs
        if (getChr() != null && getChr().getJobHandler() != null) {
            Events.onPutCTS(getChr(), cts, option);
        }
        if (needsIconOnly(cts, option)) {
            Option icon = new Option(1, option.rOption, option.tOption);
            icon.setInMillis(true);
            icon.tStart = option.tStart;
            icon.tStartLong = option.tStartLong;
            putCharacterStatValue(IndieBuffIcon, icon, true);
        }
    }

    /**
     * A timed skill buff whose stat the client no longer has: its icon comes from an IndieBuffIcon option of the same
     * skill (removed with the skill's stats).
     */
    private static boolean needsIconOnly(CharacterTemporaryStat cts, Option o) {
        return cts.isSent() && !V271LocalStats.isSent(cts) && o.rOption > 0 && o.tOption > 0 && !o.byMob;
    }

    public Option getOptByCTSAndSkill(CharacterTemporaryStat cts, int skillID) {
        Option res = null;
        if (getCurrentStats().containsKey(cts)) {
            for (Option o : getCurrentStats().get(cts)) {
                if (o.rOption == skillID) {
                    res = o;
                    break;
                }
            }
        }
        return res;
    }

    public boolean hasAOptByCTSAndSkill(CharacterTemporaryStat cts, int skillId) {
        return getOptByCTSAndSkill(cts, skillId) != null;
    }

    public void removeStat(CharacterTemporaryStat cts) {
        removeStat(cts, true);
    }

    public void removeStat(CharacterTemporaryStat cts, boolean removeByTimer) {
        if (cts == null || getRemovedStats() == null || getCurrentStats() == null || getCurrentStats().get(cts) == null) {
            return;
        }

        if (!hasStat(cts)) {
            return;
        }

        synchronized (statLock) {
            // another thread (timer expiry) may have removed it since the check above
            List<Option> currentOpts = getCurrentStats().get(cts);
            if (currentOpts == null) {
                return;
            }
            if (cts == CombatOrders) {
                chr.setCombatOrders(0);
            }
            Option opt = getOption(cts);

            Summon summon = opt.summon;
            if (summon != null && !SkillConstants.isExplodeOnDeathSummon(summon.getSkillID()) && !(summon.getSkillID() >= Kanna.TENGU_STRIKE && summon.getSkillID() <= 42111103)) { // Not null and not Tengu Strike; hardcode-ok
                if (summon.getField() != null) {
                    summon.getField().removeSummon(summon);
                }
                opt.summon = null;
            }

            for (var sum : opt.summons) {
                if (sum != null && sum.getField() != null) {
                    sum.getField().removeSummon(sum);
                }
            }
            opt.summons = new ArrayList<>();


            for (Map.Entry<BaseStat, Integer> stats : BaseStat.getFromCTS(chr, cts, opt).entrySet()) {
                removeBaseStat(stats.getKey(), stats.getValue());
            }
            getRemovedStats().put(cts, currentOpts);
            getCurrentStats().remove(cts);
            if (needsIconOnly(cts, opt)) {
                Option icon = getOptByCTSAndSkill(IndieBuffIcon, opt.rOption);
                if (icon != null) {
                    removeIndieStat(IndieBuffIcon, icon);
                }
            }
            sendResetStatPacket(cts == RideVehicle || cts == RideVehicleExpire);

            // Handler for specific CTSs
            if (getChr() != null && getChr().getJobHandler() != null) {
                Events.onRemoveCTS(getChr(), cts, opt);

                if (SkillConstants.isLowerStackCTS(cts) && SkillConstants.getStackByCTS(cts, opt) > 0) {
                    return;
                }
            }

            if (TSIndex.isTwoState(cts)) {
                getTSBByTSIndex(TSIndex.getTSEFromCTS(cts)).reset();
            }

            if (!chr.isUnloaded()) {
                chr.capHpMpToMax();
            }

            if (cts == Lapidification && removeByTimer) { //Kill player because lapidification ran out
                getChr().die();
            }
        }
    }

    public void removeIndieStat(CharacterTemporaryStat cts, Option option) {
        synchronized (statLock) {
            // the timer and the client's expiry notice can both reach the same option; only the first removes it
            List<Option> held = getCurrentStats().get(cts);
            if (held == null || !held.remove(option)) {
                return;
            }
            if (held.isEmpty()) {
                getCurrentStats().remove(cts);
            }
            List<Option> optList = new ArrayList<>();
            optList.add(option);
            getRemovedStats().put(cts, optList);

            for (Map.Entry<BaseStat, Integer> stats : BaseStat.getFromCTS(chr, cts, option).entrySet()) {
                removeBaseStat(stats.getKey(), stats.getValue());
            }
        }

        Summon summon = option.summon;
        if (summon != null && !SkillConstants.isExplodeOnDeathSummon(summon.getSkillID()) && !(summon.getSkillID() >= Kanna.TENGU_STRIKE && summon.getSkillID() <= 42111103)) { // Not null and not Tengu Strike; hardcode-ok
            if (summon.getField() != null) {
                summon.getField().removeSummon(summon);
            }
            option.summon = null;
        }

        for (var sum : option.summons) {
            if (sum != null && sum.getField() != null) {
                sum.getField().removeSummon(sum);
            }
        }
        option.summons = new ArrayList<>();

        sendResetStatPacket();

        // Handler for specific CTSs
        if (getChr() != null && getChr().getJobHandler() != null) {
            Events.onRemoveCTS(getChr(), cts, option);
        }

        if (!chr.isUnloaded()) {
            chr.capHpMpToMax();
        }
    }

    public boolean hasNewStat(CharacterTemporaryStat cts) {
        return newStats.containsKey(cts);
    }

    public boolean hasStat(CharacterTemporaryStat cts) {
        return getCurrentStats().containsKey(cts);
    }

    public boolean hasStat(Map<CharacterTemporaryStat, List<Option>> collection, CharacterTemporaryStat cts) {
        return collection.containsKey(cts);
    }

    public Option getOption(CharacterTemporaryStat cts) {
        if (getCurrentStats() == null) {
            return new Option();
        }

        var options = getCurrentStats().get(cts);
        if (options != null && options.size() > 0) {
            return options.get(0);
        }

        return new Option();
    }

    public Option getOptionElseNull(CharacterTemporaryStat cts) {
        if (hasStat(cts)) {
            return getCurrentStats().get(cts).get(0);
        }
        return null;
    }

    public List<Option> getOptions(CharacterTemporaryStat cts) {
        if (hasStat(cts)) {
            return getCurrentStats().get(cts);
        }
        return new ArrayList<>();
    }

    public long getRemainingTime(CharacterTemporaryStat cts, int skillId) {
        if (getOptByCTSAndSkill(cts, skillId) != null) {
            Option opt = getOptByCTSAndSkill(cts, skillId);
            return opt.getRemainingTime();
        }
        return 0;
    }

    public int[] getMaskByCollection(Map<CharacterTemporaryStat, List<Option>> map) {
        int[] res = new int[CharacterTemporaryStat.length];
        for (CharacterTemporaryStat cts : map.keySet()) {
            res[cts.getPos()] |= cts.getVal();
        }
        return res;
    }

    public int[] getTotalMask() {
        return getMaskByCollection(getCurrentStats());
    }

    public int[] getNewMask() {
        return getMaskByCollection(getNewStats());
    }

    public int[] getRemovedMask() {
        return getMaskByCollection(getRemovedStats());
    }

    public void encodeForLocal(OutPacket outPacket) {
        encodeForLocal(outPacket, getNewStats());
        getNewStats().clear();
    }

    /** The local buff block with no stats, for packets that carry one without sending buffs */
    public void encodeEmptyForLocal(OutPacket outPacket) {
        encodeForLocal(outPacket, new HashMap<>());
    }

    private void encodeForLocal(OutPacket outPacket, Map<CharacterTemporaryStat, List<Option>> collection) {
        Map<CharacterTemporaryStat, List<Option>> newStats = sentOnly(collection);
        int[] mask = getMaskByCollection(newStats);
        for (int m : mask) {
            outPacket.encodeInt(m);
        }
        List<CharacterTemporaryStat> orderedAndFilteredCtsList = newStats.keySet().stream()
                .filter(cts -> cts.getOrder() != -1)
                .sorted(Comparator.comparingInt(CharacterTemporaryStat::getOrder)).toList();
        // the client picks one n width for the whole packet from the flag
        boolean intWidth = isLocalEncodeInt(newStats.keySet());
        for (CharacterTemporaryStat cts : orderedAndFilteredCtsList) {
            Option o = getOption(newStats, cts);
            if (cts == ReturnTeleport) {
                outPacket.encodeShort(chr.getPosition().getY());
                outPacket.encodeShort(chr.getPosition().getX());
            } else if (intWidth) {
                outPacket.encodeInt(o.nOption);
            } else {
                outPacket.encodeShort(o.nOption);
            }
            outPacket.encodeInt(o.rOption);
            outPacket.encodeInt(o.tOption);
        }
        if (hasNewStat(newStats, SoulMP)) {
            outPacket.encodeInt(getOption(newStats, SoulMP).xOption);
            outPacket.encodeInt(getOption(newStats, SoulMP).rOption);
        }
        if (hasNewStat(newStats, FullSoulMP)) {
            outPacket.encodeInt(getOption(newStats, FullSoulMP).xOption);
        }
        short size = 0;
        outPacket.encodeShort(size);
        for (int i = 0; i < size; i++) {
            outPacket.encodeInt(0); // nKey
            outPacket.encodeByte(0); // bEnable
        }
        if (hasNewStat(newStats, HayatoStance)) {
            outPacket.encodeInt(getOption(newStats, HayatoStance).xOption);
        }
        outPacket.encodeByte(getDefenseAtt());
        outPacket.encodeByte(getDefenseState());
        outPacket.encodeByte(getPvpDamage());
        outPacket.encodeInt(getOption(newStats, EtherealForm).xOption); // new 199  Red Blue Green, used in Ethereal Form  so far
        if (hasNewStat(newStats, Dice)) {
            for (int i = 0; i < getDiceInfo().length; i++) {
                outPacket.encodeInt(diceInfo[i]);
            }
        }
        if (hasNewStat(newStats, CurseOfCreation)) {
            outPacket.encodeInt(getOption(newStats, CurseOfCreation).xOption);
        }
        if (hasNewStat(newStats, CurseOfDestruction)) {
            outPacket.encodeInt(getOption(newStats, CurseOfDestruction).xOption);
        }
        if (hasNewStat(newStats, BigBangAttackCharge)) {
            outPacket.encodeInt(getOption(newStats, BigBangAttackCharge).xOption);
        }
        if (hasNewStat(newStats, KeyDownMoving)) {
            outPacket.encodeInt(getOption(newStats, KeyDownMoving).xOption);
        }
        if (hasNewStat(newStats, PinkbeanRollingGrade)) {
            outPacket.encodeByte(getOption(newStats, PinkbeanRollingGrade).nOption);
        }
        if (hasNewStat(newStats, Judgement)) {
            outPacket.encodeInt(getOption(newStats, Judgement).xOption);
        }
        if (hasNewStat(newStats, Infinity)) { // v265 client tail reader: Decode4
            outPacket.encodeInt(getOption(newStats, Infinity).xOption);
        }
        if (hasNewStat(newStats, StackBuff)) {
            outPacket.encodeByte(getOption(newStats, StackBuff).mOption);
        }
        if (hasNewStat(newStats, Trinity)) {
            outPacket.encodeByte(getOption(newStats, Trinity).mOption);
        }
        if (hasNewStat(newStats, ElementalCharge)) {
            outPacket.encodeByte(getOption(newStats, ElementalCharge).xOption);
            outPacket.encodeShort(getOption(newStats, ElementalCharge).yOption);
            outPacket.encodeByte(getOption(newStats, ElementalCharge).uOption);
            outPacket.encodeByte(getOption(newStats, ElementalCharge).wOption);
        }
        if (hasNewStat(newStats, LifeTidal)) {
            outPacket.encodeInt(getOption(newStats, LifeTidal).mOption);
        }
        if (hasNewStat(newStats, AntiMagicShell)) {
            outPacket.encodeByte(getOption(newStats, AntiMagicShell).bOption);
            outPacket.encodeInt(getOption(newStats, AntiMagicShell).tOption);
        }
        if (hasNewStat(newStats, IgnoreTargetDEF)) {
            outPacket.encodeInt(getOption(newStats, IgnoreTargetDEF).mOption);
        }
        if (hasNewStat(newStats, StopForceAtomInfo)) {
            getStopForceAtom().encode(outPacket);
        }
        if (hasNewStat(newStats, SmashStack)) {
            outPacket.encodeInt(getOption(newStats, SmashStack).xOption);
            outPacket.encodeInt(getOption(newStats, SmashStack).yOption);
            outPacket.encodeInt(getOption(newStats, SmashStack).tOption); // duration? (new v210?)
        }
        if (hasNewStat(newStats, MobZoneState)) {
            for (int zoneState : getMobZoneStates()) {
                outPacket.encodeInt(zoneState);
            }
            outPacket.encodeInt(0); // notify end
        }
        if (hasNewStat(newStats, RadiantOrb)) {
            outPacket.encodeInt(0); // skill id list
        }
        if (hasNewStat(newStats, Slow)) {
            outPacket.encodeByte(getOption(newStats, Slow).bOption);
        }
        if (hasNewStat(newStats, IgnoreMobpdpR)) {
            outPacket.encodeByte(getOption(newStats, IgnoreMobpdpR).bOption);
        }
        if (hasNewStat(newStats, BdR)) {
            outPacket.encodeByte(getOption(newStats, BdR).bOption);
        }
        if (hasNewStat(newStats, DropRIncrease)) {
            outPacket.encodeInt(getOption(newStats, DropRIncrease).xOption);
            outPacket.encodeByte(getOption(newStats, DropRIncrease).bOption);
        }
        if (hasNewStat(newStats, PoseType)) {
            outPacket.encodeByte(getOption(newStats, PoseType).bOption);
            outPacket.encodeByte(getOption(newStats, PoseType).sOption);
        }
        if (hasNewStat(newStats, Beholder)) {
            outPacket.encodeInt(getOption(newStats, Beholder).sOption);
        }
        if (hasNewStat(newStats, CrossOverChain)) {
            outPacket.encodeInt(getOption(newStats, CrossOverChain).xOption);
        }
        if (hasNewStat(newStats, ImmuneBarrier)) {
            outPacket.encodeInt(getOption(newStats, ImmuneBarrier).xOption);
        }
        if (hasNewStat(newStats, Stance)) {
            outPacket.encodeInt(getOption(newStats, Stance).xOption);
        }
        if (hasNewStat(newStats, SharpEyes)) {
            outPacket.encodeInt(getOption(newStats, SharpEyes).mOption);
        }
        if (hasNewStat(newStats, AdvancedBless)) {
            outPacket.encodeInt(getOption(newStats, AdvancedBless).xOption);
            outPacket.encodeInt(getOption(newStats, AdvancedBless).yOption);
        }
        if (hasNewStat(newStats, UsefulAdvancedBless)) {
            outPacket.encodeInt(getOption(newStats, UsefulAdvancedBless).xOption);
        }
        if (hasNewStat(newStats, Poison)) {
            outPacket.encodeInt(getOption(newStats, Poison).xOption);
        }
        if (hasNewStat(newStats, SoulExalt)) {
            outPacket.encodeInt(getOption(newStats, SoulExalt).xOption);
        }
        if (hasNewStat(newStats, Bless)) {
            outPacket.encodeInt(getOption(newStats, Bless).xOption);
        }
        if (hasNewStat(newStats, DotHealHPPerSecond)) {
            outPacket.encodeInt(getOption(newStats, DotHealHPPerSecond).xOption);
        }
        if (hasNewStat(newStats, DotHealMPPerSecond)) {
            outPacket.encodeInt(getOption(newStats, DotHealMPPerSecond).xOption);
        }
        if (hasNewStat(newStats, SpiritGuard)) {
            outPacket.encodeInt(getOption(newStats, SpiritGuard).nOption);
        }
        if (hasNewStat(newStats, MastemasMark)) {
            outPacket.encodeInt(getOption(MastemasMark).xOption);
        }
        if (hasNewStat(newStats, KnockBack)) {
            outPacket.encodeInt(getOption(newStats, KnockBack).nOption);
            outPacket.encodeInt(getOption(newStats, KnockBack).bOption);
        }
        if (hasNewStat(newStats, ShieldAttack)) {
            outPacket.encodeInt(getOption(newStats, ShieldAttack).xOption);
        }
        if (hasNewStat(newStats, SSFShootingAttack)) {
            outPacket.encodeInt(getOption(newStats, SSFShootingAttack).xOption);
        }
        if (hasNewStat(newStats, BattlePvPHelenaMark)) {
            outPacket.encodeInt(getOption(newStats, BattlePvPHelenaMark).cOption);
        }
        if (hasNewStat(newStats, PinkbeanAttackBuff)) {
            outPacket.encodeInt(getOption(newStats, PinkbeanAttackBuff).bOption);
        }
        if (hasNewStat(newStats, RoyalGuardState)) {
            outPacket.encodeInt(getOption(newStats, RoyalGuardState).bOption);
            outPacket.encodeInt(getOption(newStats, RoyalGuardState).xOption);
        }
        if (hasNewStat(newStats, MichaelSoulLink)) {
            outPacket.encodeInt(getOption(newStats, MichaelSoulLink).xOption);
            outPacket.encodeByte(getOption(newStats, MichaelSoulLink).bOption);
            outPacket.encodeInt(getOption(newStats, MichaelSoulLink).cOption);
            outPacket.encodeInt(getOption(newStats, MichaelSoulLink).yOption);
        }
        if (hasNewStat(newStats, RWCylinder)) {
            outPacket.encodeByte(getOption(newStats, RWCylinder).bOption);
            outPacket.encodeShort(getOption(newStats, RWCylinder).cOption);
            outPacket.encodeByte(getOption(newStats, RWCylinder).xOption);
        }
        if (hasNewStat(newStats, HitStackDamR)) {
            outPacket.encodeInt(getOption(newStats, HitStackDamR).xOption);
        }
        if (hasNewStat(newStats, RWMagnumBlow)) {
            outPacket.encodeShort(getOption(newStats, RWMagnumBlow).bOption);
            outPacket.encodeByte(getOption(newStats, RWMagnumBlow).xOption);
        }
        if (hasNewStat(newStats, BladeStance)) {
            outPacket.encodeInt(getOption(newStats, BladeStance).xOption);
        }
        if (hasNewStat(newStats, DarkSight)) {
            outPacket.encodeInt(getOption(newStats, DarkSight).cOption);
            outPacket.encodeInt(getOption(newStats, DarkSight).xOption);
            outPacket.encodeInt(getOption(newStats, DarkSight).yOption);
        }
        if (hasNewStat(newStats, Stigma)) {
            outPacket.encodeInt(getOption(newStats, Stigma).bOption);
        }
        if (hasNewStat(newStats, TempSecondaryStat)) {
            outPacket.encodeInt(getOption(newStats, TempSecondaryStat).xOption);
        }
        if (hasNewStat(newStats, CriticalGrowing)) {
            outPacket.encodeInt(getOption(newStats, CriticalGrowing).xOption);
        }
        if (hasNewStat(newStats, Ember)) {
            outPacket.encodeInt(getOption(newStats, Ember).xOption);
        }
        if (hasNewStat(newStats, PickPocket)) {
            outPacket.encodeInt(getOption(newStats, PickPocket).xOption);
        }
        if (hasNewStat(newStats, DivineEcho)) {
            outPacket.encodeShort(getOption(newStats, DivineEcho).xOption);
        }
        if (hasNewStat(newStats, DemonFrenzy)) {
            outPacket.encodeShort(getOption(newStats, DemonFrenzy).xOption);
        }
        if (hasNewStat(newStats, ShadowSpear)) {
            outPacket.encodeShort(getOption(newStats, ShadowSpear).xOption);
        }
        if (hasNewStat(newStats, RhoAias)) {
            outPacket.encodeInt(getOption(newStats, RhoAias).xOption);
            outPacket.encodeInt(getOption(newStats, RhoAias).bOption);
            outPacket.encodeInt(getOption(newStats, RhoAias).cOption);
            outPacket.encodeInt(getOption(newStats, RhoAias).yOption);
        }
        if (hasNewStat(newStats, VampDeath)) {
            outPacket.encodeInt(getOption(newStats, VampDeath).xOption);
        }
        if (hasNewStat(newStats, HolyMagicShell)) {
            outPacket.encodeInt(getOption(newStats, HolyMagicShell).xOption);
        }
        for (int i = 0; i < TSIndex.values().length; i++) {
            if (hasNewStat(newStats, TSIndex.getCTSFromTwoStatIndex(i))) {
                getTwoStates().get(i).encode(TSIndex.getCTSFromTwoStatIndex(i), outPacket);
            }
        }
        encodeIndieTempStat(outPacket, newStats);
        if (hasNewStat(newStats, UsingScouter)) {
            outPacket.encodeInt(getOption(newStats, UsingScouter).nOption);
            outPacket.encodeInt(getOption(newStats, UsingScouter).xOption);
        }
        if (hasNewStat(newStats, SylvidiaFlight)) {
            outPacket.encodeInt(getOption(newStats, SylvidiaFlight).xOption);
        }
        if (hasNewStat(newStats, WingsOfGlory)) {
            outPacket.encodeInt(getOption(newStats, WingsOfGlory).xOption);
            outPacket.encodeInt(getOption(newStats, WingsOfGlory).cOption);
        }
        if (hasNewStat(newStats, LefBuffMastery)) {
            outPacket.encodeInt(getOption(newStats, LefBuffMastery).xOption);
            outPacket.encodeInt(getOption(newStats, LefBuffMastery).cOption);
        }
        if (hasNewStat(newStats, TrickBladeMobStat)) {
            outPacket.encodeInt(getOption(newStats, TrickBladeMobStat).xOption);
        }
        if (hasNewStat(newStats, SixthAssassination)) {
            outPacket.encodeInt(getOption(newStats, SixthAssassination).xOption);
        }
        if (hasNewStat(newStats, MuscleMemory)) {
            outPacket.encodeInt(getOption(newStats, MuscleMemory).xOption); // flag
        }
        if (hasNewStat(newStats, OverloadMode)) {
            outPacket.encodeInt(getOption(newStats, OverloadMode).xOption);
        }
        if (hasNewStat(newStats, SpecterEnergy)) {
            outPacket.encodeInt(getOption(newStats, SpecterEnergy).xOption); // energy (out of 1000)
        }
        if (hasNewStat(newStats, SpecterState)) {
            outPacket.encodeInt(getOption(newStats, SpecterState).xOption); // energy (out of 1000)
        }
        if (hasNewStat(newStats, BasicCast)) {
            outPacket.encodeInt(getOption(newStats, BasicCast).xOption);
            outPacket.encodeInt(getOption(newStats, BasicCast).cOption);
        }
        if (hasNewStat(newStats, ScarletCast)) {
            outPacket.encodeInt(getOption(newStats, ScarletCast).xOption);
            outPacket.encodeInt(getOption(newStats, ScarletCast).cOption);
        }
        if (hasNewStat(newStats, GustCast)) {
            outPacket.encodeInt(getOption(newStats, GustCast).xOption);
            outPacket.encodeInt(getOption(newStats, GustCast).cOption);
        }
        if (hasNewStat(newStats, AbyssalCast)) {
            outPacket.encodeInt(getOption(newStats, AbyssalCast).xOption);
            outPacket.encodeInt(getOption(newStats, AbyssalCast).cOption);
        }
        if (hasNewStat(newStats, WillPoison)) {
            outPacket.encodeInt(getOption(newStats, WillPoison).xOption);
        }
        if (hasNewStat(newStats, InfernoSphere)) {
            outPacket.encodeInt(getOption(newStats, InfernoSphere).xOption);
        }
        if (hasNewStat(newStats, PhantomMark)) {
            outPacket.encodeInt(getOption(newStats, PhantomMark).xOption);
        }
        if (hasNewStat(newStats, PhantomMarkMobStat)) {
            outPacket.encodeInt(getOption(newStats, PhantomMarkMobStat).xOption);
        }
        if (hasNewStat(newStats, NightWalkerBat)) {
            outPacket.encodeInt(getOption(newStats, NightWalkerBat).xOption);
        }
        if (hasNewStat(newStats, PMdReduce)) {
            outPacket.encodeInt(getOption(newStats, PMdReduce).xOption);
        }
        if (hasNewStat(newStats, ForbidEquipChange)) {
            outPacket.encodeInt(getOption(newStats, ForbidEquipChange).xOption);
        }
        if (hasNewStat(newStats, ComboCounter)) {
            outPacket.encodeInt(getOption(newStats, ComboCounter).xOption);
            outPacket.encodeInt(getOption(newStats, ComboCounter).yOption);
        }
        if (hasNewStat(newStats, FifthGoddessBless)) {
            outPacket.encodeInt(getOption(newStats, FifthGoddessBless).xOption);
            outPacket.encodeInt(getOption(newStats, FifthGoddessBless).yOption);
            outPacket.encodeInt(getOption(newStats, FifthGoddessBless).zOption);
        }
        if (hasNewStat(newStats, AncientGuidance)) {
            outPacket.encodeInt(getOption(newStats, AncientGuidance).xOption);
            outPacket.encodeInt(getOption(newStats, AncientGuidance).yOption);
        }
        if (hasNewStat(newStats, HolySymbol)) {
            outPacket.encodeInt(getOption(newStats, HolySymbol).xOption);
            outPacket.encodeInt(getOption(newStats, HolySymbol).yOption);
            outPacket.encodeInt(getOption(newStats, HolySymbol).zOption);
            outPacket.encodeInt(getOption(newStats, HolySymbol).wOption);
            outPacket.encodeByte(getOption(newStats, HolySymbol).bOption);
            outPacket.encodeByte(0); // v265 client reads two bytes here
            outPacket.encodeInt(getOption(newStats, HolySymbol).pOption);
        }
        if (hasNewStat(newStats, AT_Attributes)) {
            outPacket.encodeInt(getOption(newStats, AT_Attributes).xOption);
            outPacket.encodeInt(getOption(newStats, AT_Attributes).yOption);
        }
        if (hasNewStat(newStats, AT_Energy)) {
            outPacket.encodeInt(getOption(newStats, AT_Energy).xOption); // Tallisman Gauge 35->70->100?
        }
        if (hasNewStat(newStats, LW_Nobility)) {
            outPacket.encodeInt(getOption(newStats, LW_Nobility).xOption);
            outPacket.encodeInt(getOption(newStats, LW_Nobility).yOption);
        }
        if (hasNewStat(newStats, Revenant)) {
            outPacket.encodeInt(getOption(newStats, Revenant).zOption);
        }
        if (hasNewStat(newStats, RevenantDamage)) {
            outPacket.encodeInt(getOption(newStats, RevenantDamage).xOption);
            outPacket.encodeInt(getOption(newStats, RevenantDamage).yOption);
            outPacket.encodeInt(getOption(newStats, RevenantDamage).zOption);
        }
        if (hasNewStat(newStats, SilhouetteMirage)) {
            outPacket.encodeInt(getOption(newStats, SilhouetteMirage).xOption);
        }
        if (hasNewStat(newStats, HastyAura)) {
            outPacket.encodeInt(getOption(newStats, HastyAura).xOption);
            outPacket.encodeInt(getOption(newStats, HastyAura).yOption);
        }
        if (hasNewStat(newStats, DrainingAura)) {
            outPacket.encodeInt(getOption(newStats, DrainingAura).xOption);
            outPacket.encodeInt(getOption(newStats, DrainingAura).yOption);
        }
        if (hasNewStat(newStats, BlueAura)) {
            outPacket.encodeInt(getOption(newStats, BlueAura).xOption);
            outPacket.encodeInt(getOption(newStats, BlueAura).yOption);
            outPacket.encodeInt(getOption(newStats, BlueAura).zOption);
        }
        if (hasNewStat(newStats, DarkAura)) {
            outPacket.encodeInt(getOption(newStats, DarkAura).xOption);
            outPacket.encodeInt(getOption(newStats, DarkAura).yOption);
        }
        if (hasNewStat(newStats, WeakeningAura)) {
            outPacket.encodeInt(getOption(newStats, WeakeningAura).xOption);
            outPacket.encodeInt(getOption(newStats, WeakeningAura).yOption);
        }
        if (hasNewStat(newStats, AuraScythe)) {
            outPacket.encodeInt(getOption(newStats, AuraScythe).xOption);
            outPacket.encodeInt(getOption(newStats, AuraScythe).yOption);
        }
        if (hasNewStat(newStats, IceAura)) {
            outPacket.encodeInt(getOption(newStats, IceAura).xOption);
            outPacket.encodeInt(getOption(newStats, IceAura).yOption);
        }
        if (hasNewStat(newStats, ParashockGuard)) {
            outPacket.encodeInt(getOption(newStats, ParashockGuard).xOption);
            outPacket.encodeInt(getOption(newStats, ParashockGuard).yOption);
        }
        if (hasNewStat(newStats, DivineForce)) {
            outPacket.encodeInt(getOption(newStats, DivineForce).xOption);
            outPacket.encodeInt(getOption(newStats, DivineForce).yOption);
        }
        if (hasNewStat(newStats, IncarnationAura)) {
            outPacket.encodeInt(getOption(newStats, IncarnationAura).xOption);
            outPacket.encodeInt(getOption(newStats, IncarnationAura).yOption);
        }
        if (hasNewStat(newStats, BlizzardTempest)) {
            outPacket.encodeInt(getOption(newStats, BlizzardTempest).xOption);
        }
        if (hasNewStat(newStats, PhotonRay)) {
            outPacket.encodeInt(getOption(newStats, PhotonRay).xOption);
        }
        if (hasNewStat(newStats, AbyssalLightning)) {
            outPacket.encodeInt(getOption(newStats, AbyssalLightning).xOption);
        }
        if (hasNewStat(newStats, LawOfGravity)) {
            outPacket.encodeInt(getOption(newStats, LawOfGravity).xOption);
        }
        if (hasNewStat(newStats, CrystalGate)) {
            outPacket.encodeInt(getOption(newStats, CrystalGate).xOption);
        }
        if (hasNewStat(newStats, HolyWater)) {
            outPacket.encodeInt(getOption(newStats, HolyWater).xOption);
        }
        if (hasNewStat(newStats, MuscleMemoryFinale)) {
            outPacket.encodeInt(getOption(newStats, MuscleMemoryFinale).xOption);
        }
        if (hasNewStat(newStats, LiberationOrbPassive)) {
            outPacket.encodeInt(getOption(newStats, LiberationOrbPassive).xOption);
            outPacket.encodeInt(getOption(newStats, LiberationOrbPassive).yOption);
        }
        if (hasNewStat(newStats, DarknessAura)) {
            outPacket.encodeInt(getOption(newStats, DarknessAura).xOption);
        }
        if (hasNewStat(newStats, SerpentScrew)) {
            outPacket.encodeInt(getOption(newStats, SerpentScrew).xOption);
            outPacket.encodeInt(getOption(newStats, SerpentScrew).yOption);
        }
        if (hasNewStat(newStats, LiberationOrb)) {
            outPacket.encodeInt(getOption(newStats, LiberationOrb).xOption);
        }
        if (hasNewStat(newStats, ThanatosDescent)) {
            outPacket.encodeInt(getOption(newStats, ThanatosDescent).xOption);
        }
        if (hasNewStat(newStats, NABrutalPang)) {
            outPacket.encodeInt(getOption(newStats, NABrutalPang).xOption);
            outPacket.encodeInt(getOption(newStats, NABrutalPang).yOption);
        }
        if (hasNewStat(newStats, Magnet)) {
            outPacket.encodeInt(getOption(newStats, Magnet).xOption);
        }
        if (hasNewStat(newStats, YetiFuryGauge)) {
            outPacket.encodeInt(getOption(newStats, YetiFuryGauge).xOption);
        }
        if (hasNewStat(newStats, YetiFuryMode)) {
            outPacket.encodeInt(getOption(newStats, YetiFuryMode).xOption);
        }
        if (hasNewStat(newStats, AT_Clone)) {
            outPacket.encodeInt(getOption(newStats, AT_Clone).xOption);
        }
        if (hasNewStat(newStats, YetiCook)) {
            outPacket.encodeInt(getOption(newStats, YetiCook).xOption);
            outPacket.encodeByte(getOption(newStats, YetiCook).bOption);
        }
        if (hasNewStat(newStats, NewFlying)) {
            outPacket.encodeInt(getOption(newStats, NewFlying).xOption);
        }
        if (hasNewStat(newStats, ReincarnationMission)) {
            outPacket.encodeInt(getOption(newStats, ReincarnationMission).xOption);
        }
        if (hasNewStat(newStats, QuiverBarrage)) {
            outPacket.encodeInt(getOption(newStats, QuiverBarrage).xOption);
        }
        if (hasNewStat(newStats, AdrenalinBoost)) {
            outPacket.encodeInt(getOption(newStats, AdrenalinBoost).xOption);
        }
        if (hasNewStat(newStats, ElementSoul)) {
            outPacket.encodeInt(getOption(newStats, ElementSoul).xOption);
            outPacket.encodeInt(getOption(newStats, ElementSoul).yOption);
        }
        if (hasNewStat(newStats, Stun)) {
            outPacket.encodeInt(getOption(newStats, Stun).xOption);
            outPacket.encodeInt(getOption(newStats, Stun).yOption);
        }
        if (hasNewStat(newStats, BMageDeath)) {
            outPacket.encodeInt(getOption(newStats, BMageDeath).xOption);
        }
        if (hasNewStat(newStats, LamourCarte)) {
            outPacket.encodeInt(getOption(newStats, LamourCarte).xOption);
        }
        if (hasNewStat(newStats, AdrenalinMaximum)) {
            outPacket.encodeInt(getOption(newStats, AdrenalinMaximum).xOption);
            outPacket.encodeInt(getOption(newStats, AdrenalinMaximum).yOption);
        }
        if (hasNewStat(newStats, RapidFire)) {
            outPacket.encodeInt(getOption(newStats, RapidFire).xOption);
        }
        if (hasNewStat(newStats, Nightmare)) {
            outPacket.encodeInt(getOption(newStats, Nightmare).xOption);
        }
        if (hasNewStat(newStats, SixthJavelinStack)) {
            outPacket.encodeInt(getOption(newStats, SixthJavelinStack).xOption);
        }
        if (hasNewStat(newStats, SixthGloryWingJavelinStack)) {
            outPacket.encodeInt(getOption(newStats, SixthGloryWingJavelinStack).xOption);
        }
        if (hasNewStat(newStats, DslayerMetamorphosis)) {
            outPacket.encodeInt(getOption(newStats, DslayerMetamorphosis).xOption);
        }
        if (hasNewStat(newStats, RenPlumSwordEx)) {
            outPacket.encodeInt(getOption(newStats, RenPlumSwordEx).xOption);
            outPacket.encodeInt(getOption(newStats, RenPlumSwordEx).yOption);
        }
        if (hasNewStat(newStats, SpiritAwakeningStack)) {
            outPacket.encodeInt(getOption(newStats, SpiritAwakeningStack).xOption);
        }
        if (hasNewStat(newStats, SummonProp)) {
            outPacket.encodeInt(getOption(newStats, SummonProp).nOption);
            outPacket.encodeInt(getOption(newStats, SummonProp).yOption);
            outPacket.encodeInt(getOption(newStats, SummonProp).zOption);
        }
        if (hasNewStat(newStats, ShineMageHourglass)) {
            outPacket.encodeInt(getOption(newStats, ShineMageHourglass).xOption);
        }
    }


    public void encodeForRemote(OutPacket outPacket, Map<CharacterTemporaryStat, List<Option>> collection) {
        Map<CharacterTemporaryStat, List<Option>> newStats = sentOnly(collection);
        int[] mask = getMaskByCollection(newStats);
        for (int maskElem : mask) {
            outPacket.encodeInt(maskElem);
        }
        RemoteSecondaryStat.encode(outPacket, newStats);
        outPacket.encodeByte(getDefenseAtt());
        outPacket.encodeByte(getDefenseState());
        outPacket.encodeByte(getPvpDamage());
        outPacket.encodeInt(getOption(newStats, EtherealForm).xOption); // Red Blue Green, used in Ethereal Form  so far
        Set<CharacterTemporaryStat> ctsSet = newStats.keySet();
        if (ctsSet.contains(CurseOfCreation)) {
            outPacket.encodeInt(newStats.get(CurseOfCreation).getFirst().xOption);
        }
        if (ctsSet.contains(CurseOfDestruction)) {
            outPacket.encodeInt(newStats.get(CurseOfDestruction).getFirst().xOption);
        }
        if (ctsSet.contains(PoseType)) { // v265 remote tail reader: two bytes (as the local tail)
            var o = newStats.get(PoseType).getFirst();
            outPacket.encodeByte(o.bOption);
            outPacket.encodeByte(o.uOption);
        }
        if (ctsSet.contains(BattlePvPHelenaMark)) {
            var o = newStats.get(BattlePvPHelenaMark).getFirst();
            outPacket.encodeInt(o.xOption);
            outPacket.encodeInt(o.yOption);
            outPacket.encodeInt(o.zOption);
        }
        if (ctsSet.contains(BattlePvPLangEProtection)) {
            var o = newStats.get(BattlePvPLangEProtection).getFirst();
            outPacket.encodeInt(o.xOption);
            outPacket.encodeInt(o.yOption);
        }
        if (ctsSet.contains(MichaelSoulLink)) {
            var o = newStats.get(MichaelSoulLink).getFirst();
            outPacket.encodeInt(o.xOption);
            outPacket.encodeByte(o.bOption);
            outPacket.encodeInt(o.cOption);
            outPacket.encodeInt(o.yOption);
        }
        if (ctsSet.contains(Stigma)) {
            outPacket.encodeInt(newStats.get(Stigma).getFirst().bOption);
        }
        if (ctsSet.contains(DivineEcho)) {
            outPacket.encodeShort(newStats.get(DivineEcho).getFirst().xOption);
        }
        if (ctsSet.contains(DemonFrenzy)) {
            outPacket.encodeShort(newStats.get(DemonFrenzy).getFirst().xOption);
        }
        if (ctsSet.contains(SerpentScrew)) {
            outPacket.encodeShort(newStats.get(SerpentScrew).getFirst().xOption);
        }
        if (ctsSet.contains(MesoGuard)) {
            outPacket.encodeShort(newStats.get(MesoGuard).getFirst().xOption);
        }
        if (ctsSet.contains(ShadowSpear)) {
            outPacket.encodeShort(newStats.get(ShadowSpear).getFirst().xOption);
        }
        if (ctsSet.contains(RhoAias)) {
            var o = newStats.get(RhoAias).getFirst();
            outPacket.encodeInt(o.xOption);
            outPacket.encodeInt(o.bOption);
            outPacket.encodeInt(o.cOption);
            outPacket.encodeInt(o.yOption);
        }
        if (ctsSet.contains(VampDeath)) {
            outPacket.encodeInt(newStats.get(VampDeath).getFirst().xOption);
        }
        if (ctsSet.contains(WingsOfGlory)) {
            var o = newStats.get(WingsOfGlory).getFirst();
            outPacket.encodeInt(o.xOption);
            outPacket.encodeInt(o.cOption);
        }
        if (ctsSet.contains(SpecterState)) { // client remote tail reader: one int (after LefBuffMastery 278)
            outPacket.encodeInt(newStats.get(SpecterState).getFirst().xOption);
        }
        if (ctsSet.contains(BattlePvPLangEClaw)) {
            var o = newStats.get(BattlePvPLangEClaw).getFirst();
            outPacket.encodeInt(o.xOption);
            outPacket.encodeInt(o.yOption);
            outPacket.encodeInt(o.zOption);
        }
        if (ctsSet.contains(RenPlumSwordEx)) {
            var o = newStats.get(RenPlumSwordEx).getFirst();
            outPacket.encodeInt(o.xOption);
            outPacket.encodeInt(o.yOption);
        }
        if (getStopForceAtom() != null) {
            getStopForceAtom().encode(outPacket);
        } else {
            new StopForceAtom().encode(outPacket);
        }
        for (int i = 0; i < TSIndex.values().length; i++) {
            if (ctsSet.contains(TSIndex.getCTSFromTwoStatIndex(i))) {
                getTwoStates().get(i).encode(TSIndex.getCTSFromTwoStatIndex(i), outPacket);
            }
        }
        encodeIndieTempStat(outPacket, newStats);
        if (ctsSet.contains(SylvidiaFlight)) {
            outPacket.encodeInt(newStats.get(SylvidiaFlight).getFirst().xOption);
        }
        if (ctsSet.contains(KeyDownMoving)) {
            outPacket.encodeInt(newStats.get(KeyDownMoving).getFirst().xOption);
        }
        if (ctsSet.contains(WillPoison)) {
            outPacket.encodeInt(newStats.get(WillPoison).getFirst().xOption);
        }
        if (ctsSet.contains(ComboCounter)) {
            var o = newStats.get(ComboCounter).getFirst();
            outPacket.encodeInt(o.xOption);
            outPacket.encodeInt(o.yOption);
        }
        outPacket.encodeByte(0);
        if (ctsSet.contains(LW_Nobility)) {
            var o = newStats.get(LW_Nobility).getFirst();
            outPacket.encodeInt(o.xOption);
            outPacket.encodeInt(o.yOption);
        }
        if (ctsSet.contains(HastyAura)) {
            var o = newStats.get(HastyAura).getFirst();
            outPacket.encodeInt(o.xOption);
            outPacket.encodeInt(o.yOption);
        }
        if (ctsSet.contains(DrainingAura)) {
            var o = newStats.get(DrainingAura).getFirst();
            outPacket.encodeInt(o.xOption);
            outPacket.encodeInt(o.yOption);
        }
        if (ctsSet.contains(BlueAura)) {
            var o = newStats.get(BlueAura).getFirst();
            outPacket.encodeInt(o.xOption);
            outPacket.encodeInt(o.yOption);
            outPacket.encodeInt(o.zOption);
        }
        if (ctsSet.contains(DarkAura)) {
            var o = newStats.get(DarkAura).getFirst();
            outPacket.encodeInt(o.xOption);
            outPacket.encodeInt(o.yOption);
        }
        if (ctsSet.contains(WeakeningAura)) {
            var o = newStats.get(WeakeningAura).getFirst();
            outPacket.encodeInt(o.xOption);
            outPacket.encodeInt(o.yOption);
        }
        if (ctsSet.contains(AuraScythe)) {
            var o = newStats.get(AuraScythe).getFirst();
            outPacket.encodeInt(o.xOption);
            outPacket.encodeInt(o.yOption);
        }
        if (ctsSet.contains(IceAura)) {
            var o = newStats.get(IceAura).getFirst();
            outPacket.encodeInt(o.xOption);
            outPacket.encodeInt(o.yOption);
        }
        if (ctsSet.contains(ParashockGuard)) {
            var o = newStats.get(ParashockGuard).getFirst();
            outPacket.encodeInt(o.xOption);
            outPacket.encodeInt(o.yOption);
        }
        if (ctsSet.contains(DivineForce)) {
            var o = newStats.get(DivineForce).getFirst();
            outPacket.encodeInt(o.xOption);
            outPacket.encodeInt(o.yOption);
        }
        if (ctsSet.contains(IncarnationAura)) {
            var o = newStats.get(IncarnationAura).getFirst();
            outPacket.encodeInt(o.xOption);
            outPacket.encodeInt(o.yOption);
        }
        if (ctsSet.contains(PhotonRay)) {
            outPacket.encodeInt(newStats.get(PhotonRay).getFirst().xOption);
        }
        if (ctsSet.contains(SilhouetteMirage)) {
            outPacket.encodeInt(newStats.get(SilhouetteMirage).getFirst().xOption);
        }
        if (ctsSet.contains(Infinity)) {
            var o = newStats.get(Infinity).getFirst();
            outPacket.encodeInt(o.xOption);
            outPacket.encodeInt(o.yOption);
        }
        if (ctsSet.contains(YetiFuryMode)) {
            outPacket.encodeInt(newStats.get(YetiFuryMode).getFirst().xOption);
        }
        if (ctsSet.contains(AT_Clone)) {
            outPacket.encodeInt(newStats.get(AT_Clone).getFirst().xOption);
        }
        // no AbsorptionWind (730) tail: the client's 5-int tail here is bit 793 (GrabAndThrow, a boss stat we don't send)
        if (ctsSet.contains(Stun)) {
            var o = newStats.get(Stun).getFirst();
            outPacket.encodeInt(o.xOption);
            outPacket.encodeInt(o.yOption);
        }
        if (ctsSet.contains(NewFlying)) {
            outPacket.encodeInt(newStats.get(NewFlying).getFirst().xOption);
        }
    }

    private void encodeIndieTempStat(OutPacket outPacket, Map<CharacterTemporaryStat, List<Option>> collection) {
        List<CharacterTemporaryStat> indies = collection.keySet().stream()
                .filter(cts -> cts.isIndie() && cts.isSent())
                .sorted(Comparator.comparingInt(CharacterTemporaryStat::getBitPos))
                .toList();
        int curTime = Util.getCurrentTime();
        for (CharacterTemporaryStat cts : indies) {
            List<Option> options = collection.get(cts);
            if (options == null) {
                outPacket.encodeInt(0);
                continue;
            }
            outPacket.encodeInt(options.size());
            for (Option option : options) {
                outPacket.encodeInt(option.rOption);
                outPacket.encodeInt(option.nOption);
                outPacket.encodeInt(option.nKey);
                outPacket.encodeInt(curTime - option.tStart); // elapsedTime
                outPacket.encodeInt(option.tOption);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0); // MValue list
                outPacket.encodeInt(0); // MValue2 list
            }
        }
    }

    /**
     * @return the stats of the collection that have a v265 bit (the rest are server-side only)
     */
    private static Map<CharacterTemporaryStat, List<Option>> sentOnly(Map<CharacterTemporaryStat, List<Option>> collection) {
        Map<CharacterTemporaryStat, List<Option>> res = new EnumMap<>(CharacterTemporaryStat.class);
        for (Map.Entry<CharacterTemporaryStat, List<Option>> e : collection.entrySet()) {
            if (e.getKey().isSent() && e.getValue() != null) {
                res.put(e.getKey(), e.getValue());
            }
        }
        return res;
    }

    private boolean hasNewStat(Map<CharacterTemporaryStat, List<Option>> newStats, CharacterTemporaryStat cts) {
        return newStats.containsKey(cts);
    }

    private Option getOption(Map<CharacterTemporaryStat, List<Option>> stats, CharacterTemporaryStat cts) {
        List<Option> options = stats.get(cts);
        return options != null && !options.isEmpty() ? options.get(0) : new Option();
    }

    public void encodeRemovedIndieTempStat(OutPacket outPacket) {
        List<CharacterTemporaryStat> removedIndies = getRemovedStats().entrySet().stream()
                .filter(e -> e.getKey().isIndie() && e.getKey().isSent() && e.getValue() != null)
                .map(Map.Entry::getKey)
                .sorted(Comparator.comparingInt(CharacterTemporaryStat::getBitPos))
                .toList();

        for (CharacterTemporaryStat key : removedIndies) {
            int curTime = Util.getCurrentTime();
            // encode remaining stats
            List<Option> options = getOptions(key);
            options.remove(null);
            if (options == null) {
                outPacket.encodeInt(0);
                continue;
            }
            outPacket.encodeInt(options.size());
            for (Option option : options) {
                outPacket.encodeInt(option.rOption);
                outPacket.encodeInt(option.nOption);
                outPacket.encodeInt(option.nKey); // nKey
                outPacket.encodeInt(curTime - option.tStart);
                outPacket.encodeInt(option.tOption); // tTerm
                outPacket.encodeInt(0); // new 218
                int size = 0;
                outPacket.encodeInt(size);
                for (int i = 0; i < size; i++) {
                    outPacket.encodeInt(0); // MValueKey
                    outPacket.encodeInt(0); // MValue
                }
                size = 0;
                outPacket.encodeInt(size);
                for (int i = 0; i < size; i++) {
                    outPacket.encodeInt(0); // MValueKey2
                    outPacket.encodeInt(0); // MValue2
                }
            }
        }
    }

    public boolean hasNewMovingEffectingStat() {
        return getNewStats().keySet().stream().anyMatch(cts -> cts.isSent() && cts.isMovingEffectingStat());
    }

    public boolean hasNewStatOf(CharacterTemporaryStat... ctsList) {
        for (CharacterTemporaryStat cts : ctsList) {
            if (cts.isSent() && getNewStats().containsKey(cts)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRemovedStat(CharacterTemporaryStat cts) {
        return cts.isSent() && getRemovedStats().containsKey(cts);
    }

    /**
     * @return the number of active stats the client knows (the buff count it shows)
     */
    public int getSentStatCount() {
        return (int) getCurrentStats().keySet().stream().filter(CharacterTemporaryStat::isSent).count();
    }

    public boolean hasMovingEffectingStat() {
        return getCurrentStats().keySet().stream().anyMatch(CharacterTemporaryStat::isMovingEffectingStat);
    }

    public boolean hasRemovedMovingEffectingStat() {
        return getRemovedStats().keySet().stream().anyMatch(cts -> cts.isSent() && cts.isMovingEffectingStat());
    }

    public Map<CharacterTemporaryStat, List<Option>> getCurrentStats() {
        return currentStats;
    }

    public Map<CharacterTemporaryStat, List<Option>> getNewStats() {
        return newStats;
    }

    public Map<CharacterTemporaryStat, List<Option>> getRemovedStats() {
        return removedStats;
    }

    public int getPvpDamage() {
        return pvpDamage;
    }

    public void setPvpDamage(int pvpDamage) {
        this.pvpDamage = pvpDamage;
    }

    public byte getDefenseState() {
        return defenseState;
    }

    public void setDefenseState(byte defenseState) {
        this.defenseState = defenseState;
    }

    public byte getDefenseAtt() {
        return defenseAtt;
    }

    public void setDefenseAtt(byte defenseAtt) {
        this.defenseAtt = defenseAtt;
    }

    public int[] getDiceInfo() {
        return diceInfo;
    }

    public void setDiceInfo(int[] diceInfo) {
        this.diceInfo = diceInfo;
    }

    public int[] throwDice(int roll) {
        return throwDice(roll, 0);
    }

    public int[] throwDice(int dice1, int dice2) {
        return throwDice(dice1, dice2, 0);
    }

    public static int[] array = {0, 0, 30, 20, 15, 20, 30, 20};   // Stats for Normal Rolls
    public static int[] arrayDD = {0, 0, 40, 30, 25, 30, 40, 30}; // Stats for 2 identical numbers
    public static int[] arrayLD = {0, 0, 50, 40, 40, 40, 50, 40}; // Stats for 3 identical numbers

    public int[] throwDice(int dice1, int dice2, int dice3) {
        for (int i = 0; i < diceOption.length; i++) {
            diceOption[i] = 0;
        }

        if (dice1 == dice2 && dice1 == dice3) { // 3 identical numbers
            diceOption[dice1] = arrayLD[dice1];
        } else if (dice1 == dice2) {
            diceOption[dice1] = arrayDD[dice1];
            diceOption[dice3] = array[dice3];
        } else if (dice1 == dice3) {
            diceOption[dice1] = arrayDD[dice1];
            diceOption[dice2] = array[dice2];
        } else if (dice2 == dice3) {
            diceOption[dice2] = arrayDD[dice2];
            diceOption[dice1] = array[dice1];
        } else {                                // 3 non-identical numbers
            diceOption[dice1] = array[dice1];
            diceOption[dice2] = array[dice2];
            diceOption[dice3] = array[dice3];
        }

        int[] diceinfo = new int[]{
                diceOption[3],  //nOption 3 (MHPR)
                diceOption[3],  //nOption 3 (MMPR)
                diceOption[4],  //nOption 4 (Cr)
                0,  // CritDamage Min
                0,  // ???  ( CritDamage Max (?) )
                0,  // EVAR
                0,  // AR
                0,  // ER
                diceOption[2],  //nOption 2 (PDDR)
                diceOption[2],  //nOption 2 (MDDR)
                0,  // PDR
                0,  // MDR
                diceOption[5],  //nOption 5 (PIDR)
                0,  // PDamR
                0,  // MDamR
                0,  // PADR
                0,  // MADR
                diceOption[6], //nOption 6 (EXP)
                diceOption[7], //nOption 7 (IED)
                0,  // ASRR
                0,  // TERR
                0,  // MesoRate
                0,
        };
        setDiceInfo(diceinfo);
        return diceinfo;
    }

    public Set<Integer> getMobZoneStates() {
        return mobZoneStates;
    }

    public void setMobZoneStates(Set<Integer> mobZoneOwnerObjId) {
        this.mobZoneStates = mobZoneOwnerObjId;
    }

    public void addMobZoneState(int mobZoneOwnerObjId) {
        getMobZoneStates().add(mobZoneOwnerObjId);
    }

    public void removeMobZoneState(int mobZoneOwnerObjId) {
        getMobZoneStates().removeIf(i -> i == mobZoneOwnerObjId);
    }

    public int getViperEnergyCharge() {
        return viperEnergyCharge;
    }

    public void setViperEnergyCharge(int viperEnergyCharge) {
        this.viperEnergyCharge = viperEnergyCharge;
    }

    public StopForceAtom getStopForceAtom() {
        return stopForceAtom;
    }

    public void setStopForceAtom(StopForceAtom stopForceAtom) {
        this.stopForceAtom = stopForceAtom;
    }

    public LarknessManager getLarknessManager() {
        return larknessManager;
    }

    public Char getChr() {
        return chr;
    }

    public void sendSetStatPacket() {
        Char copy = chr.getCopy();
        if (copy != null) {
            chr.write(UserRemote.setTemporaryStat(copy, (short) 0));
        }
        synchronized (statLock) {
            if (newStats.size() != 0) {
                if (chr.getField() != null) {
                    getChr().getField().broadcastPacket(UserRemote.setTemporaryStat(getChr(), (short) 0), getChr());
                }
                getChr().write(WvsContext.temporaryStatSet(this));
            }
        }
    }

    public void sendResetStatPacket() {
        sendResetStatPacket(false);
    }

    public void sendResetStatPacket(boolean demount) {
        synchronized (statLock) {
            if (getRemovedStats() != null && getRemovedStats().size() > 0) {
                var localPacket = WvsContext.temporaryStatReset(this, demount);
                var chr = getChr();

                if (chr.getField() != null) {
                    chr.getField().broadcastPacket(UserRemote.resetTemporaryStat(getChr()), getChr());
                }

                chr.write(localPacket);
                var copy = chr.getCopy();
                if (copy != null) {
                    chr.write(UserRemote.resetTemporaryStat(copy));
                }

                if (chr.isChangingChannel() || chr.isInCashShop() || chr.isInAuctionHouse()) {
                    chr.getTemporaryStatManager().getResetStatsUpdate().add(localPacket);
                }

                getRemovedStats().clear();
            }
        }
    }

    public void removeAllDebuffs() {
        if (hasStat(Stun)) {
            removeStat(CharacterTemporaryStat.Stun);
        }
        if (hasStat(Poison)) {
            removeStat(CharacterTemporaryStat.Poison);
        }
        if (hasStat(Seal)) {
            removeStat(CharacterTemporaryStat.Seal);
        }
        if (hasStat(Darkness)) {
            removeStat(CharacterTemporaryStat.Darkness);
        }
        if (hasStat(Thaw)) {
            removeStat(CharacterTemporaryStat.Thaw);
        }
        if (hasStat(Weakness)) {
            removeStat(CharacterTemporaryStat.Weakness);
        }
        if (hasStat(Curse)) {
            removeStat(CharacterTemporaryStat.Curse);
        }
        if (hasStat(Slow)) {
            removeStat(CharacterTemporaryStat.Slow);
        }
        if (hasStat(Blind)) {
            removeStat(CharacterTemporaryStat.Blind);
        }
        if (hasStat(Attract)) {
            removeStat(Attract);
        }
        if (hasStat(FireBomb)) {
            removeStat(FireBomb);
        }
        if (hasStat(PainMark)) {
            removeStat(PainMark);
        }
        if (hasStat(DeathMark)) {
            removeStat(DeathMark);
        }
        sendResetStatPacket();
    }

    public void setLarknessManager(LarknessManager larknessManager) {
        this.larknessManager = larknessManager;
    }

    public Set<AffectedArea> getAffectedAreas() {
        return affectedAreas;
    }

    public void addAffectedArea(AffectedArea aa) {
        getAffectedAreas().add(aa);
    }

    public void removeAffectedArea(AffectedArea aa) {
        getAffectedAreas().remove(aa);
        if (!SkillConstants.isNoRemoveAA(aa.getSkillID())) {
            removeStatsBySkill(aa.getSkillID());
        }
    }

    public boolean hasAffectedArea(AffectedArea affectedArea) {
        return getAffectedAreas().contains(affectedArea);
    }

    public boolean hasStatBySkillId(int skillId) {
        for (List<Option> options : getCurrentStats().values()) {
            for (Option option : options) {
                if (option.rOption == skillId) {
                    return true;
                }
            }
        }
        return false;
    }

    public Map<CharacterTemporaryStat, Option> removeStatsBySkill(int skillId) {
        Map<CharacterTemporaryStat, Option> removedMap = new HashMap<>();
        for (CharacterTemporaryStat cts : getCurrentStats().keySet()) {
            var option = getOption(cts);

            Option checkOpt = new Option();
            checkOpt.rOption = skillId;
            if (cts.isIndie() && getOptions(cts) != null && getOptions(cts).contains(checkOpt)) {
                Option o = Util.findWithPred(getOptions(cts), opt -> opt.equals(checkOpt));
                if (o == null) {
                    log.error("Found option null, yet the options contained it?");
                } else {
                    removedMap.put(cts, o);
                }
            } else if (option.rOption == skillId) {
                removedMap.put(cts, getOption(cts));
            }
        }
        removedMap.forEach((cts, opt) -> {
            if (cts.isIndie()) {
                removeIndieStat(cts, opt);
            } else {
                removeStat(cts);
            }
        });

        return removedMap;
    }

    public void addSoulMPFromMobDeath() {
        addSoulMPFromMobDeath(ItemConstants.MOB_DEATH_SOUL_MP_COUNT);
    }

    public void addSoulMPFromMobDeath(int amount) {
        if (hasStat(SoulMP)) {
            Option o = getOption(SoulMP);

            if (o.nOption < ItemConstants.MAX_SOUL_CAPACITY) {
                o.nOption = Math.min(ItemConstants.MAX_SOUL_CAPACITY, o.nOption + amount);
                putCharacterStatValue(SoulMP, o);
/*                if (o.nOption >= ItemConstants.MAX_SOUL_CAPACITY && !hasStat(FullSoulMP)) {
                    Option o2 = new Option();
                    o2.rOption = ItemConstants.getSoulSkillFromSoulID(((Equip) chr.getEquippedItemByBodyPart(BodyPart.Weapon)).getSoulOptionId());
                    if (o2.rOption == 0) {
                        log.info(String.format("Unknown corresponding skill for soul socket id %d!",
                                ((Equip) chr.getEquippedItemByBodyPart(BodyPart.Weapon)).getSoulOptionId()));
                    }
                    o2.nOption = ItemConstants.MAX_SOUL_CAPACITY;
                    o2.xOption = ItemConstants.MAX_SOUL_CAPACITY;
                    putCharacterStatValue(FullSoulMP, o2);
                }*/
            }
            sendSetStatPacket();
        }
    }

    public void updateSoulMP(int nOpt) {
        if (hasStat(SoulMP)) {
            Option prev = getOption(SoulMP);
            Option o = new Option();
            o.nOption = Math.min(1000, Math.max(0, nOpt));
            o.rOption = prev.rOption;
            o.xOption = prev.xOption;
            putCharacterStatValue(SoulMP, o);
            removeStat(FullSoulMP);
            sendSetStatPacket();
        }
    }

    public void updateBuff(CharacterTemporaryStat cts, Option o) {
        Option oldOpt = getOptByCTSAndSkill(cts, o.rOption);
        if (oldOpt != null) {
            Option newOpt = oldOpt.deepCopy();
            newOpt.nKey = Util.random.nextInt();
            newOpt.tOption = (int) getRemainingTime(cts, o.rOption);
            newOpt.setInMillis(true);

            putCharacterStatValue(cts, newOpt, true);
            sendSetStatPacket();
        }
    }

    public void updateBuffExtendTime(CharacterTemporaryStat cts, Option o) {
        Option oldOpt = getOptByCTSAndSkill(cts, o.rOption);
        if (oldOpt != null) {
            Option newOpt = oldOpt.deepCopy();
            newOpt.nKey = Util.random.nextInt();

            putCharacterStatValue(cts, newOpt, true);
            sendSetStatPacket();
        }
    }

    public void putCharacterStatValueFromMobSkill(CharacterTemporaryStat cts, Option o) {
        if (cts == null) {
            return;
        }

        o.byMob = true;
        o.rOption |= o.slv << 16; // mob skills are encoded differently: not an int, but short (skill ID), then short (slv).

        // a Will skill's icon is up: no monster debuffs
        for (Option icon : getOptions(IndieBuffIcon)) {
            if (SkillConstants.isHeroWillSkill(icon.rOption)) {
                return;
            }
        }

        // Transcendent's Life Blessing blocks a number of debuffs
        Option block = getOptByCTSAndSkill(IndieAntiMagicShell, net.swordie.ms.client.jobs.Job.TRANSCENDENT_LIFE);
        if (block != null && block.nOption > 0) {
            if (block.nOption <= 1) {
                removeIndieStat(IndieAntiMagicShell, block);
                sendResetStatPacket();
            } else {
                Option left = new Option(block.nOption - 1, block.rOption, (int) block.getRemainingTime());
                left.setInMillis(true);
                putCharacterStatValue(IndieAntiMagicShell, left, true);
                sendSetStatPacket();
            }
            return;
        }

        if (cts.equals(Stun) && Util.getCurrentTime() <= lastStun + (o.tOption * 1000) + GameConstants.STUN_COOLDOWN * 1000) {
            return;
        }
        putCharacterStatValue(cts, o);
        if (cts.equals(Stun)) {
            lastStun = Util.getCurrentTime();
        }
        Job sourceJobHandler = chr.getJobHandler();
        sourceJobHandler.handleMobDebuffSkill(chr);
    }

    public void removeAllStats() {
        Map<CharacterTemporaryStat, List<Option>> stats = new HashMap<>();
        for (Map.Entry<CharacterTemporaryStat, List<Option>> entry : getNewStats().entrySet()) {
            stats.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<CharacterTemporaryStat, List<Option>> entry : getCurrentStats().entrySet()) {
            stats.put(entry.getKey(), entry.getValue());
        }

        for (var entry : stats.entrySet()) {
            var cts = entry.getKey();
            var options = new HashSet<>(entry.getValue());
            if (!cts.isNotRemovedOnDeath()) {
                if (cts.isIndie()) {
                    for (var o : options) {
                        removeIndieStat(cts, o);
                    }
                } else {
                    removeStat(cts);
                }
            }
        }
    }

    public Map<BaseStat, Integer> getBaseStats() {
        return baseStats;
    }

    public Map<BaseStat, List<Integer>> getNonAddBaseStats() {
        return nonAddBaseStats;
    }

    public void addBaseStat(BaseStat bs, int value) {
        if (bs.isNonAdditiveStat()) {
            if (!getNonAddBaseStats().containsKey(bs)) {
                getNonAddBaseStats().put(bs, new ArrayList<>());
            }
            getNonAddBaseStats().get(bs).add(value);
        } else {
            getBaseStats().put(bs, getBaseStats().getOrDefault(bs, 0) + value);
        }
    }

    public void removeBaseStat(BaseStat bs, int value) {
        if (bs.isNonAdditiveStat()) {
            List<Integer> values = getNonAddBaseStats().get(bs);
            if (values != null) {
                values.remove(Integer.valueOf(value)); // a -value entry would still multiply in
            }
            return;
        }
        addBaseStat(bs, -value);
    }

    public long getTotalNOptionOfStat(CharacterTemporaryStat cts) {
        if (cts.isIndie()) {
            return getOptions(cts).stream().mapToLong(o -> o.nOption).sum();
        } else {
            return getOptions(cts).stream().mapToLong(o -> o.nOption).sum();
        }
    }

    public void increaseGolluxStack() {
        int maxStack = BossConstants.MAX_GOLLUX_STACKS;
        int stack = getCurrentStats().get(Stigma) != null ? getCurrentStats().get(Stigma).get(0).nOption : 0;
        stack++;
        Option o = new Option();
        if (stack >= maxStack) {
            chr.damage(chr.getHP());
            stack = maxStack;
        }
        o.nOption = stack;
        o.rOption = 800;
        o.bOption = maxStack;
        putCharacterStatValue(Stigma, o);
        // no tOption  as it would probably be permanent (till death)
        sendSetStatPacket();
    }

    public Set<OutPacket> getResetStatsUpdate() {
        return resetStatsUpdate;
    }

    /** Sends every active stat again, for a client that dropped them (a SET_FIELD with character data). */
    public void resendAllToClient() {
        synchronized (statLock) {
            if (getCurrentStats().isEmpty()) {
                return;
            }
            for (var e : getCurrentStats().entrySet()) {
                if (e.getValue() != null && !e.getValue().isEmpty()) {
                    getNewStats().put(e.getKey(), e.getValue());
                }
            }
        }
        sendSetStatPacket();
    }

    public void resendLostStatResetPackets() {
        for (var packet : getResetStatsUpdate()) {
            chr.write(packet);
        }
        getResetStatsUpdate().clear();
    }

    public boolean isInvincible() {
        return hasStat(IndieNotDamaged) || hasStat(NotDamaged);
    }

    public List<CharacterTemporaryStat> getProtectiveShields() {
        return getCurrentStats().keySet().stream().filter(CharacterTemporaryStat::isProtectiveShield).collect(Collectors.toList());
    }

    public void update() {
        var toRemove = new HashSet<CharacterTemporaryStat>();
        var toRemoveIndie = new HashSet<Tuple<CharacterTemporaryStat, Option>>();

        synchronized (statLock) {
            for (var entry : getCurrentStats().entrySet()) {
                var cts = entry.getKey();
                if (cts.isIndie()) {
                    for (var opt : entry.getValue()) {
                        var endTime = opt.tStartLong + opt.tOption;
                        if (opt.tOption != 0 && FileTime.isExpired(endTime)) {
                            toRemoveIndie.add(new Tuple<>(cts, opt));
                        }
                    }
                } else if (TSIndex.isTwoState(cts)) {
                    TemporaryStatBase tsb = getTSBByTSIndex(TSIndex.getTSEFromCTS(cts));
                    if (tsb.hasExpired()) {
                        removeStat(cts);
                    }
                } else {
                    var opt = entry.getValue().get(0);
                    var endTime = opt.tStartLong + opt.tOption;
                    if (opt.tOption != 0 && FileTime.isExpired(endTime)) {
                        toRemove.add(cts);
                    }
                }
            }
        }
        toRemove.forEach(this::removeStat);
        toRemoveIndie.forEach(tup -> removeIndieStat(tup.getLeft(), tup.getRight()));
    }

    /** How far the client's timers may run ahead of ours before a reported expiry is not believed. */
    private static final long CLIENT_EXPIRY_GRACE = 1500;
    /** A reported stat we don't have is reset only if the client still reports it this long after (a reset may be in flight). */
    private static final long ORPHAN_RESET_DELAY = 2000;
    private static final long MISMATCH_LOG_INTERVAL = 60_000;
    private final Map<Integer, Long> orphanSince = new ConcurrentHashMap<>();
    private final Map<Integer, Long> mismatchLogged = new ConcurrentHashMap<>();

    /**
     * The client reports local stats whose time ran out (v271 In 413, client stat indices). Our own expiry goes first;
     * what is left at those indices is removed if it ends within the grace time, and a stat the client still shows but
     * we no longer have gets a plain reset. Stats with time left here stay (the timer is ours).
     */
    public void onClientExpired(BitSet reported) {
        update();
        long now = System.currentTimeMillis();
        List<CharacterTemporaryStat> expired = new ArrayList<>();
        List<Tuple<CharacterTemporaryStat, Option>> expiredIndie = new ArrayList<>();
        List<CharacterTemporaryStat> orphans = new ArrayList<>();
        for (int i = reported.nextSetBit(0); i >= 0 && i <= V271LocalStats.LAST_INDEX; i = reported.nextSetBit(i + 1)) {
            List<CharacterTemporaryStat> atIndex = V271LocalStats.getStatsAt(i);
            List<CharacterTemporaryStat> held = atIndex.stream().filter(this::hasStat).toList();
            if (held.isEmpty()) {
                if (atIndex.isEmpty()) {
                    continue;
                }
                long since = orphanSince.computeIfAbsent(i, k -> now);
                if (now - since >= ORPHAN_RESET_DELAY) {
                    orphans.add(atIndex.get(0));
                    orphanSince.put(i, now);
                }
                continue;
            }
            orphanSince.remove(i);
            for (CharacterTemporaryStat cts : held) {
                for (Option o : new ArrayList<>(getOptions(cts))) {
                    long left = o.getRemainingTime();
                    if (o.tOption != 0 && !TSIndex.isTwoState(cts) && left <= CLIENT_EXPIRY_GRACE) {
                        if (cts.isIndie()) {
                            expiredIndie.add(new Tuple<>(cts, o));
                        } else {
                            expired.add(cts);
                        }
                    } else if (now - mismatchLogged.getOrDefault(i, 0L) >= MISMATCH_LOG_INTERVAL) {
                        mismatchLogged.put(i, now);
                        log.info(String.format("[Buffs] %s: client says %s (index %d, skill %d) ran out, %s here",
                                chr.getName(), cts, i, o.rOption,
                                o.tOption == 0 ? "no end time" : (left / 1000) + " s left"));
                    }
                }
            }
        }
        expired.forEach(this::removeStat);
        expiredIndie.forEach(t -> removeIndieStat(t.getLeft(), t.getRight()));
        if (!orphans.isEmpty()) {
            synchronized (statLock) {
                // only the client's view changes: we no longer hold these
                Map<CharacterTemporaryStat, List<Option>> pending = new HashMap<>(getRemovedStats());
                getRemovedStats().clear();
                orphans.forEach(cts -> getRemovedStats().put(cts, new ArrayList<>()));
                chr.write(WvsContext.temporaryStatReset(this, false));
                getRemovedStats().clear();
                getRemovedStats().putAll(pending);
            }
            log.info("[Buffs] " + chr.getName() + ": cleared client-only stats " + orphans);
        }
    }
}
