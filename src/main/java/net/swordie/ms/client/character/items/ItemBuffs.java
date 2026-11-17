package net.swordie.ms.client.character.items;


import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.SpecStat;
import net.swordie.ms.loaders.ItemData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

public class ItemBuffs {
    private static final int EXP_ACCUMULATION_POTION = 2003550;
    private static final int SPARKLING_BLUE_STAR_POTION = 2003586;
    private static final int SMALL_EXP_ACCUMULATION_POTION = 2003607;

    private static final Logger log = LogManager.getLogger();

    /**
     * Boss-drop potions whose effect is missing from items.dat (the loader didn't know the stat's name).
     */
    private static final Map<Integer, Map<SpecStat, Integer>> MISSING_SPECS = Map.of(
            SPARKLING_BLUE_STAR_POTION, Map.of(SpecStat.ignoreMobpdpR, 20), // Sparkling Blue Star Potion: ignore 20% DEF, 2 hours
            EXP_ACCUMULATION_POTION, Map.of(SpecStat.plusExpRate, 10), // EXP Accumulation Potion: +10% EXP, 2 hours
            SMALL_EXP_ACCUMULATION_POTION, Map.of(SpecStat.plusExpRate, 10)); // Small EXP Accumulation Potion: +10% EXP, 30 minutes

    public static void giveItemBuffsFromItemID(Char chr, TemporaryStatManager tsm, int itemID) {
        giveItemBuffsFromItemID(chr, tsm, itemID, true);
    }

    private static void giveItemBuffsFromItemID(Char chr, TemporaryStatManager tsm, int itemID, boolean shareWithParty) {
        Map<SpecStat, Integer> specStats = ItemData.getItemInfoByID(itemID).getSpecStats();
        if (MISSING_SPECS.containsKey(itemID)) {
            specStats = new java.util.HashMap<>(specStats);
            for (var e : MISSING_SPECS.get(itemID).entrySet()) {
                specStats.putIfAbsent(e.getKey(), e.getValue());
            }
        }
        long time = specStats.getOrDefault(SpecStat.time, 0) / 1000;
        if (ItemConstants.isFamiliarSkillItem(itemID)) {
            time = 20;
        }
        for (Map.Entry<SpecStat, Integer> entry : specStats.entrySet()) {
            SpecStat ss = entry.getKey();
            int value = entry.getValue();
            Option o = new Option(-itemID, time);
            o.nOption = value;
            switch (ss) {
                case hp:
                    chr.heal(value);
                    break;
                case hpR:
                    chr.heal((int) ((value / 100D) * chr.getMaxHP()));
                    break;
                case mp:
                    if (!JobConstants.isDemonSlayer(chr.getJob())) {
                        chr.healMP(value);
                    }
                    break;
                case mpR:
                    if (!JobConstants.isDemonSlayer(chr.getJob())) {
                        chr.healMP((int) ((value / 100D) * chr.getMaxMP()));
                    }
                    break;
                case eva:
                    tsm.putCharacterStatValue(EVA, o);
                    break;
                case speed:
                    tsm.putCharacterStatValue(Speed, o);
                    break;
                case pad:
                    tsm.putCharacterStatValue(PAD, o);
                    break;
                case mad:
                    tsm.putCharacterStatValue(MAD, o);
                    break;
                case pdd:
                    tsm.putCharacterStatValue(DEF, o);
                    break;
                case acc:
                    tsm.putCharacterStatValue(ACC, o);
                    break;
                case jump:
                    tsm.putCharacterStatValue(Jump, o);
                    break;
                case imhp:
                    tsm.putCharacterStatValue(EMHP, o);
                    break;
                case immp:
                    tsm.putCharacterStatValue(EMMP, o);
                    break;
                case indieAllStat:
                    tsm.putCharacterStatValue(IndieAllStat, o);
                    break;
                case indieSpeed:
                    tsm.putCharacterStatValue(IndieSpeed, o);
                    break;
                case indieJump:
                    tsm.putCharacterStatValue(IndieJump, o);
                    break;
                case indieSTR:
                    tsm.putCharacterStatValue(IndieSTR, o);
                    break;
                case indieDEX:
                    tsm.putCharacterStatValue(IndieDEX, o);
                    break;
                case indieINT:
                    tsm.putCharacterStatValue(IndieINT, o);
                    break;
                case indieLUK:
                    tsm.putCharacterStatValue(IndieLUK, o);
                    break;
                case indiePad:
                    tsm.putCharacterStatValue(IndiePAD, o);
                    break;
                case indiePdd:
                    tsm.putCharacterStatValue(IndieDEF, o);
                    break;
                case indieMad:
                    tsm.putCharacterStatValue(IndieMAD, o);
                    break;
                case indieBDR:
                    tsm.putCharacterStatValue(IndieBDR, o);
                    break;
                case indieDamR:
                    tsm.putCharacterStatValue(IndieDamR, o);
                    break;
                case indieScriptBuff:
                    // the effect itself is event-scripted; icon and timer only (a value would add buff duration)
                    o.nOption = 0;
                    tsm.putCharacterStatValue(IndieScriptBuff, o);
                    break;
                case incEffectHPPotion:
                    // the potion recovery % is in prob; icon only, potion healing doesn't read it yet
                    o.nOption = specStats.getOrDefault(SpecStat.prob, 0);
                    tsm.putCharacterStatValue(IncEffectHPPotion, o);
                    break;
                case indieIgnoreMobpdpR:
                case ignoreMobpdpR:
                    tsm.putCharacterStatValue(IndieIgnoreMobpdpR, o);
                    break;
                case plusExpRate: // +N% EXP
                    tsm.putCharacterStatValue(IndieEXP, o, true);
                    break;
                case indieStatR:
                    tsm.putCharacterStatValue(IndieStatR, o);
                    break;
                case indieMhp:
                    tsm.putCharacterStatValue(IndieMHP, o);
                    break;
                case indieMmp:
                    tsm.putCharacterStatValue(IndieMMP, o);
                    break;
                case indieBooster:
                    tsm.putCharacterStatValue(IndieBooster, o);
                    break;
                case indieAcc:
                    tsm.putCharacterStatValue(IndieACC, o);
                    break;
                case indieEva:
                    tsm.putCharacterStatValue(IndieEVA, o);
                    break;
                case indieAllSkill:
                    tsm.putCharacterStatValue(CombatOrders, o);
                    break;
                case indieMhpR:
                    tsm.putCharacterStatValue(IndieMHPR, o);
                    break;
                case indieMmpR:
                    tsm.putCharacterStatValue(IndieMMPR, o);
                    break;
                case indieStance:
                    tsm.putCharacterStatValue(IndieStance, o);
                    break;
                case indieForceSpeed:
                    tsm.putCharacterStatValue(IndieForceSpeed, o);
                    break;
                case indieForceJump:
                    tsm.putCharacterStatValue(IndieForceJump, o);
                    break;
                case indieQrPointTerm:
                    tsm.putCharacterStatValue(IndieQrPointTerm, o);
                    break;
                case indieWaterSmashBuff:
                    tsm.putCharacterStatValue(IndieKeyDownTime, o);
                    break;
                case indieExp:
                    tsm.putCharacterStatValue(IndieEXP, o, true);
                    break;
                case padRate:
                    tsm.putCharacterStatValue(IndiePADR, o);
                    break;
                case madRate:
                    tsm.putCharacterStatValue(IndieMADR, o);
                    break;
                case pddRate:
                    tsm.putCharacterStatValue(IndiePDDR, o);
                    break;
                case accRate:
                    tsm.putCharacterStatValue(ACCR, o);
                    break;
                case evaRate:
                    tsm.putCharacterStatValue(EVAR, o);
                    break;
                case mhpR:
                case mhpRRate:
                    tsm.putCharacterStatValue(IndieMHPR, o);
                    break;
                case mmpR:
                case mmpRRate:
                    tsm.putCharacterStatValue(IndieMMPR, o);
                    break;
                case booster:
                    tsm.putCharacterStatValue(Booster, o);
                    break;
                case expinc:
                case expBuff:
                    tsm.putCharacterStatValue(ExpBuffRate, o);
                    break;
                case dropPer:
                    tsm.putCharacterStatValue(ItemUpByItem, o);
                    break;
                case mesoAmountRate:
                    tsm.putCharacterStatValue(MesoUpByItem, o);
                    break;
                case str:
                    tsm.putCharacterStatValue(STR, o);
                    break;
                case dex:
                    tsm.putCharacterStatValue(DEX, o);
                    break;
                case inte:
                    tsm.putCharacterStatValue(INT, o);
                    break;
                case luk:
                    tsm.putCharacterStatValue(LUK, o);
                    break;
                case asrR:
                    tsm.putCharacterStatValue(AsrRByItem, o);
                    break;
                case bdR:
                    tsm.putCharacterStatValue(BdR, o);
                    break;
                case prob:
                    if (specStats.containsKey(SpecStat.incEffectHPPotion)) {
                        break; // prob is the potion recovery %, not a drop/meso rate
                    }
                    boolean mesoExist = specStats.containsKey(SpecStat.mesoupbyitem);
                    boolean itemExist = specStats.containsKey(SpecStat.itemupbyitem);
                    if (!mesoExist && !itemExist) {
                        tsm.putCharacterStatValue(ItemUpByItem, o);
                        tsm.putCharacterStatValue(MesoUpByItem, o);
                        break;
                    }
                    if (specStats.getOrDefault(SpecStat.mesoupbyitem, 0) != 0) {
                        tsm.putCharacterStatValue(MesoUpByItem, o);
                    }
                    if (specStats.getOrDefault(SpecStat.itemupbyitem, 0) != 0) {
                        tsm.putCharacterStatValue(ItemUpByItem, o);
                    }
                    break;
                case mesoupbyitem:
                    if (value > 0) {
                        o.nOption = specStats.getOrDefault(SpecStat.prob, 0);
                        tsm.putCharacterStatValue(MesoUpByItem, o);
                    }
                    break;
                case itemupbyitem:
                    if (value > 0) {
                        o.nOption = specStats.getOrDefault(SpecStat.prob, 0);
                        tsm.putCharacterStatValue(ItemUpByItem, o);
                    }
                    break;
                case consumeOnPickup:
                    break;
                case inflation:
                    tsm.putCharacterStatValue(Inflation, o);
                    break;
                case morph:
                    tsm.putCharacterStatValue(Morph, o);
                    break;
                case repeatEffect:
                    tsm.putCharacterStatValue(RepeatEffect, o);
                    break;
                case time:
                    // handled in the option above
                    break;
                case charColor:
                    break;

                case startTime:
                case familiarPassiveSkillTarget:
                case interval:
                case indieMdd: // magic defense was merged into DEF
                case mddRate:
                case speedRate: // rate fields of pickup buffs; the stat itself is applied above
                case thaw: // no cold/underwater field damage on this server
                case party: // handled by the caller below
                case mdd: // old
                    // Used in familiars, but is used by client.
                    break;
                case deathmark:
                    tsm.removeStat(DeathMark);
                    break;
                case charmEXP:
                    chr.addStatAndSendPacket(net.swordie.ms.enums.Stat.charmEXP,
                            value * net.swordie.ms.constants.ServerFeatures.getInt(net.swordie.ms.constants.ServerFeatures.Feature.CHARM_EXP_MULTIPLIER));
                    break;
                case charismaEXP:
                    chr.addTraitExp(net.swordie.ms.enums.Stat.charismaEXP, value);
                    break;
                case insightEXP:
                    chr.addTraitExp(net.swordie.ms.enums.Stat.insightEXP, value);
                    break;
                case willEXP:
                    chr.addTraitExp(net.swordie.ms.enums.Stat.willEXP, value);
                    break;
                case craftEXP:
                    chr.addTraitExp(net.swordie.ms.enums.Stat.craftEXP, value);
                    break;
                case senseEXP:
                    chr.addTraitExp(net.swordie.ms.enums.Stat.senseEXP, value);
                    break;
                default:
                    log.warn(String.format("Unhandled spec stat %s, value %d.", ss, value));
            }
        }
        tsm.sendSetStatPacket();
        if (shareWithParty && specStats.getOrDefault(SpecStat.party, 0) != 0 && chr.getParty() != null) {
            for (Char member : chr.getParty().getPartyMembersInSameField(chr)) {
                giveItemBuffsFromItemID(member, member.getTemporaryStatManager(), itemID, false);
            }
        }
    }
}
