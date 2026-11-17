package net.swordie.ms.client.jobs.flora;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.CharacterStat;
import net.swordie.ms.client.character.skills.BypassCooldownCheckType;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.SkillStat;
import net.swordie.ms.client.character.skills.SkillUseSource;
import net.swordie.ms.client.character.skills.atom.forceatom.ForceAtom;
import net.swordie.ms.client.character.skills.debuff.DebuffUtil;
import net.swordie.ms.client.character.skills.info.*;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.client.party.Party;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.Effect;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.MobConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.ForceAtomEnum;
import net.swordie.ms.life.AffectedArea;
import net.swordie.ms.life.Wreckage;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.*;
import java.util.stream.Collectors;

import static net.swordie.ms.client.character.skills.BypassCooldownCheckType.BypassCheck;
import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * @author Sjonnie
 * Created on 6/25/2018.
 */
public class Ark extends Job {

    // Beginner Job
    public static final int MAGIC_CONVERSION = 150010079;
    public static final int CONTACT_CARAVAN = 150011074;
    public static final int CONTACT_CARAVAN_ZIPPY = 150011075;
    public static final int CONTACT_CARAVAN_FERRET = 150011076;
    public static final int CONTACT_CARAVAN_SALVO = 150011077;
    public static final int CONTACT_CARAVAN_MAR = 150011078;

    // 1st Job
    public static final int SPELL_BULLETS = 155001103;
    public static final int SPECTER_STATE = 155000007;
    public static final int CORRUPTION_COOLDOWN = 155001008;
    public static final int BASIC_CHARGE_DRIVE_ATTACK = 155001100;
    public static final int BASIC_CHARGE_DRIVE_ATOM = 155001000;
    public static final int BASIC_CHARGE_DRIVE_BUFF = 155001001;
    public static final int OMINOUS_NIGHTMARE = 155001102;
    public static final int OMINOUS_DREAM = 155001202;
    public static final int FLOAT = 155001205;


    // 2nd Job
    public static final int MASTER_CORRUPTION = 155101006;
    public static final int SCARLET_CHARGE_DRIVE_ATTACK_1 = 155101100;
    public static final int SCARLET_CHARGE_DRIVE_ATTACK_2 = 155101013;
    public static final int SCARLET_CHARGE_DRIVE_AIR = 155101101;
    public static final int SCARLET_CHARGE_DRIVE_ATTACK_COMBO_1 = 155101112;
    public static final int SCARLET_CHARGE_DRIVE_ATTACK_COMBO_2 = 155101015;
    public static final int SCARLET_CHARGE_DRIVE_ATOM = 155101002;
    public static final int SCARLET_CHARGE_DRIVE_BUFF = 155101003;
    public static final int KNUCKLE_BOOSTER_ARK = 155101005;
    public static final int IMPENDING_DEATH_ATOM = 155100009;
    public static final int IMPENDING_DEATH = 155100008; // v271 id, always on (v265 155101008, a toggle)
    public static final int UNENDING_HATRED = 155121016; // v271 buff: Impending Death leaves Abyssal Marks more often
    public static final int GRIEVOUS_WOUND = 155101200;
    public static final int GRIEVOUS_WOUND_AIR = 155101201;
    public static final int GRIEVOUS_WOUND_COMBO = 155101212;
    public static final int UNSTOPPABLE_IMPULSE = 155101104;
    public static final int UNSTOPPABLE_IMPULSE_COMBO = 155101114;
    public static final int TENACIOUS_INSTINCT = 155101204;
    public static final int TENACIOUS_INSTINCT_COMBO = 155101214;


    // 3rd Job
    public static final int GUST_CHARGE_DRIVE_ATTACK = 155111102;
    public static final int GUST_CHARGE_DRIVE_ATTACK_COMBO = 155111111;
    public static final int GUST_CHARGE_DRIVE_ATOM = 155111003;
    public static final int GUST_CHARGE_DRIVE_BUFF = 155111005;
    public static final int VIVID_NIGHTMARE = 155110000;
    public static final int VIVID_DREAM = 155110001;
    public static final int VENGEFUL_HATE = 155111207;
    public static final int CREEPING_TERROR = 155111006;
    public static final int CREEPING_TERROR_HELD_DOWN = 155111306;
    public static final int INSATIABLE_HUNGER = 155111202;
    public static final int INSATIABLE_HUNGER_FINISHER = 155111212;
    public static final int INSATIABLE_HUNGER_COMBO = 155111211;


    // 4th Job
    public static final int ABYSSAL_CHARGE_DRIVE_ATTACK = 155121102;
    public static final int ABYSSAL_CHARGE_DRIVE_ATOM = 155121003;
    public static final int ABYSSAL_CHARGE_DRIVE_TILE = 155121004;
    public static final int ABYSSAL_CHARGE_DRIVE_BUFF = 155121005;
    public static final int ENDLESS_NIGHTMARE = 155120000;
    public static final int ENDLESS_DREAM = 155120001;
    public static final int HERO_OF_THE_FLORA = 155121008;
    public static final int FLORAN_HEROS_WILL = 155121009;
    public static final int BLISSFUL_RESTRAINT_TILE = 155121006;
    public static final int BLISSFUL_RESTRAINT_ATTACK = 155121306;
    public static final int UNBRIDLED_CHAOS = 155121202;
    public static final int UNBRIDLED_CHAOS_COMBO = 155121215;
    public static final int BATTLE_FRENZY = 155120014;


    // Hyper Passives
    public static final int ENHANCED_SPECTRA_HEALING = 155120034;

    // Hyper Skills
    public static final int DIVINE_WRATH = 155121042;
    public static final int CHARGE_SPELL_AMPLIFIER = 155121043;
    public static final int ENDLESS_AGONY = 155121341;


    // V skills
    public static final int ABYSSAL_RECALL = 400051334;
    public static final int ABYSSAL_RECALL_2 = 400051035;
    public static final int INFINITY_SPELL = 400051036;
    public static final int NIGHTMARES_ESCAPE = 400051047;
    public static final int DREAMS_ESCAPE = 400051048;
    public static final int ENDLESSLY_STARVING_BEAST = 400051080;
    public static final int HEXA_BASIC_CHARGE_DRIVE_ATTACK = 155141000; // HEXA Basic Charge Drive
    public static final int HEXA_SCARLET_CHARGE_DRIVE_ATTACK_1 = 155141004; // HEXA Scarlet Charge Drive
    public static final int HEXA_GUST_CHARGE_DRIVE_ATTACK = 155141011; // HEXA Gust Charge Drive
    public static final int HEXA_ABYSSAL_CHARGE_DRIVE_ATTACK = 155141016; // HEXA Abyssal Charge Drive
    public static final int HEXA_ENDLESS_AGONY = 155141029; // HEXA Endless Agony
    public static final int HEXA_BLISSFUL_RESTRAINT_ATTACK = 155141031; // HEXA Blissful Restraint
    public static final int HEXA_VENGEFUL_HATE = 155141034; // HEXA Vengeful Hate
    public static final int HEXA_OMINOUS_NIGHTMARE = 155141035;
    public static final int HEXA_OMINOUS_DREAM = 155141036;
    public static final int HEXA_BASIC_CAST = 155141002;
    public static final int HEXA_BASIC_BUFF = 155141003;
    public static final int HEXA_SCARLET_CAST = 155141009;
    public static final int HEXA_SCARLET_BUFF = 155141010;
    public static final int HEXA_GUST_CAST = 155141013;
    public static final int HEXA_GUST_CAST_2 = 155141014;
    public static final int HEXA_GUST_BUFF = 155141015;
    public static final int HEXA_ABYSSAL_CAST = 155141018;
    public static final int HEXA_ABYSSAL_TILE = 155141019;
    public static final int HEXA_ABYSSAL_BUFF = 155141020;
    public static final int HEXA_BLISSFUL_RESTRAINT_TILE = 155141032;
    public static final int HEXA_BLISSFUL_RESTRAINT_TILE_ATTACK = 155141033;

    // the Indie stats a charge cast buff can carry
    private static final Map<SkillStat, CharacterTemporaryStat> CAST_BUFF_STATS = new LinkedHashMap<>();
    // no Skill.wz stat for these
    private static final int SPECTRA_MAX = 1000; // hardcode-ok
    private static final int CREEPING_TERROR_HIT_INVINCIBLE_SEC = 2; // hardcode-ok
    private static final int BLISSFUL_RESTRAINT_FREEZE_SEC = 2; // hardcode-ok
    private static final int ABYSSAL_RECALL_SEC = 30; // hardcode-ok: data time=18 is the no-Spectra window
    private static final int ABYSSAL_RECALL_INVINCIBLE_SEC = 15; // hardcode-ok
    public static final int SOLUS = 150010241;
    public static final int INFILTRATE = 150011001;
    public static final int BLISSFUL_RESTRAINT = 155121007;
    public static final int HEXA_GRIEVOUS_WOUND = 155141021;
    public static final int HEXA_GRIEVOUS_WOUND_2 = 155141022;
    public static final int HEXA_GRIEVOUS_WOUND_3 = 155141023;
    public static final int HEXA_INSATIABLE_HUNGER = 155141024;
    public static final int HEXA_INSATIABLE_HUNGER_2 = 155141025;
    public static final int HEXA_INSATIABLE_HUNGER_3 = 155141026;
    public static final int HEXA_UNBRIDLED_CHAOS = 155141027;
    public static final int HEXA_UNBRIDLED_CHAOS_2 = 155141028;
    public static final int INITIAL_PATH = 1353600;
    public static final int SANDSTORM_DESERT_FIELD = 402090000;
    static {
        CAST_BUFF_STATS.put(indieSpeed, IndieSpeed);
        CAST_BUFF_STATS.put(indieStance, IndieStance);
        CAST_BUFF_STATS.put(indiePad, IndiePAD);
        CAST_BUFF_STATS.put(indieCr, IndieCr);
        CAST_BUFF_STATS.put(indieBooster, IndieBooster);
        CAST_BUFF_STATS.put(indieEvaR, IndieEVAR);
        CAST_BUFF_STATS.put(indieDamR, IndieDamR);
        CAST_BUFF_STATS.put(indieBDR, IndieBDR);
        CAST_BUFF_STATS.put(indieIgnoreMobpdpR, IndieIgnoreMobpdpR);
    }

    List<CharacterTemporaryStat> spellCasts = Arrays.asList(AbyssalCast, GustCast, ScarletCast, BasicCast);
    private static int[] addedSkills = new int[]{
            MAGIC_CONVERSION,
            CONTACT_CARAVAN
    };

    private static int[] comboSkills = new int[] {
            SCARLET_CHARGE_DRIVE_ATTACK_COMBO_1,
            UNSTOPPABLE_IMPULSE_COMBO,
            GUST_CHARGE_DRIVE_ATTACK_COMBO,

            GRIEVOUS_WOUND_COMBO,
            TENACIOUS_INSTINCT_COMBO,
            INSATIABLE_HUNGER_COMBO,
            UNBRIDLED_CHAOS_COMBO,
    };


    public enum SpellChargeType {
        Basic(15500),
        Scarlet(15510),
        Gust(15511),
        Abyssal(15512),
        ;
        private int val;

        SpellChargeType(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public static SpellChargeType getByVal(int val) {
            return Arrays.stream(values()).filter(sct -> sct.getVal() == val).findAny().orElse(null);
        }
    }

    public Ark(Char chr) {
        super(chr);
        if (isHandlerOfJob(chr.getJob())) {
            for (int id : addedSkills) {
                if (!chr.hasSkill(id)) {
                    Skill skill = SkillData.getSkillDeepCopyById(id);
                    skill.setCurrentLevel(skill.getMasterLevel());
                    chr.addSkill(skill);
                }
            }
            if (!chr.getQuestManager().hasQuestCompleted(34944)) {
                chr.getQuestManager().completeQuest(34944); // required to get quests
            }
        }
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isArk(id);
    }

    private int getCurrentChargeCount() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int count = 0;
        if (tsm.hasStat(BasicCast)) {
            count += (tsm.getOption(BasicCast).xOption / 2);
        }
        if (tsm.hasStat(ScarletCast)) {
            count += tsm.getOption(ScarletCast).xOption;
        }
        if (tsm.hasStat(GustCast)) {
            count += tsm.getOption(GustCast).xOption;
        }
        if (tsm.hasStat(AbyssalCast)) {
            count += tsm.getOption(AbyssalCast).xOption;
        }
        return count;
    }

    private void resetCharges() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option();
        o.xOption = 0;
        tsm.putCharacterStatValue(BasicCast, o);
        tsm.putCharacterStatValue(ScarletCast, o);
        tsm.putCharacterStatValue(GustCast, o);
        tsm.putCharacterStatValue(AbyssalCast, o);
        tsm.sendSetStatPacket();
    }

    public void modifySpectraEnergy() {
        if (chr == null || chr.getField() == null) {
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int delta = chr.hasSkill(ENHANCED_SPECTRA_HEALING) ? 12 : 11;
        int currentEnergy = tsm.getOption(SpecterEnergy).xOption;
        if (tsm.hasStat(SpecterState)) {
            if (System.currentTimeMillis() < whisperUntil) {
                return;
            }
            delta = -18;
        }
        if (chr.hasSkillOnCooldown(CORRUPTION_COOLDOWN) || tsm.hasStat(AbyssalRecall)) { // if Player has Spectra Fatigue
            return;
        }
        updateSpectraEnergy(currentEnergy + delta);
    }

    private void updateSpectraEnergy(int spectraEnergy) {
        if (chr == null) {
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option();
        o.nOption = 1;
        o.xOption = Math.max(0, Math.min(SPECTRA_MAX, spectraEnergy));
        tsm.putCharacterStatValue(SpecterEnergy, o);
        if (o.xOption <= 0) { // Spectra Fatigue
            SkillInfo si = SkillData.getSkillInfoById(CORRUPTION_COOLDOWN);
            tsm.removeStatsBySkill(SPECTER_STATE);
            chr.addSkillCoolTime(CORRUPTION_COOLDOWN, si.getValue(cooltime, 1) * 1000);
        } else if (o.xOption >= SPECTRA_MAX && !chr.hasSkill(MASTER_CORRUPTION)) { // If Player doesn't have Control over their Specter State yet.
            changeSpecterState();
        }
        tsm.sendSetStatPacket();
    }

    public static final int PRIMORDIAL_ABYSS = 155141500; // 6th job Origin
    public static final int PRIMORDIAL_ABYSS_GRIPS = 155141501;
    public static final int WHISPER_OF_DEEPEST_ABYSS = 155141502; // 6th job

    /** Spectra is not consumed while Whisper of Deepest Abyss calls the Abyss (until this time). */
    private volatile long whisperUntil;

    private void changeSpecterState() {
        Skill skill = chr.getSkill(SPECTER_STATE);
        if (skill == null) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        int slv = skill.getCurrentLevel();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();

        // a Whisper of Deepest Abyss call is not the real Specter State: drop it and switch for real
        if (tsm.getOptByCTSAndSkill(SpecterState, WHISPER_OF_DEEPEST_ABYSS) != null) {
            tsm.removeStatsBySkill(WHISPER_OF_DEEPEST_ABYSS);
        }
        if (!tsm.hasStat(SpecterState)) {
            o1.rOption = SPECTER_STATE;
            o1.nOption = si.getValue(indiePad, slv);
            tsm.putCharacterStatValue(IndiePAD, o1);
            o2.rOption = SPECTER_STATE;
            o2.nOption = si.getValue(indieStance, slv);
            tsm.putCharacterStatValue(IndieStance, o2);
            o3.nOption = 1;
            o3.rOption = SPECTER_STATE;
            tsm.putCharacterStatValue(SpecterState, o3);
        } else {
            tsm.removeStatsBySkill(SPECTER_STATE);
        }
    }

    private int getLefSkillBySpecterSkill(int skillID) {
        switch (skillID) {
            case GRIEVOUS_WOUND:
                return SCARLET_CHARGE_DRIVE_ATTACK_1;
            case INSATIABLE_HUNGER:
                return GUST_CHARGE_DRIVE_ATTACK;
            case UNBRIDLED_CHAOS:
                return ABYSSAL_CHARGE_DRIVE_ATTACK;
            default:
                return skillID;
        }
    }

    private boolean isComboSkill(int skillId) {
        return Arrays.stream(comboSkills).anyMatch(s -> s == skillId);
    }

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        Char chr = c.getChr();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (attackInfo.skillId == WHISPER_OF_DEEPEST_ABYSS) {
            var wsi = SkillData.getSkillInfoById(WHISPER_OF_DEEPEST_ABYSS);
            int lv = Math.max(1, chr.getSkillLevel(WHISPER_OF_DEEPEST_ABYSS));
            int ms = wsi == null ? 0 : wsi.getValue(updatableTime, lv);
            whisperUntil = Math.max(whisperUntil, System.currentTimeMillis() + (ms > 0 ? ms : 5000));
            // the Abyss call shows the Specter form without the transform effect: the client checks SpecterState
            // with r 155141502 and then skips the transform sound/flash
            if (!tsm.hasStat(SpecterState)) {
                Option wo = new Option(1, WHISPER_OF_DEEPEST_ABYSS, ms > 0 ? ms : 5000);
                wo.setInMillis(true);
                tsm.putCharacterStatValue(SpecterState, wo, true);
                tsm.sendSetStatPacket();
            }
        }
        if (attackInfo.skillId == PRIMORDIAL_ABYSS && tsm.getOptByCTSAndSkill(AbyssalRecall, PRIMORDIAL_ABYSS_GRIPS) == null) {
            // the Abyssal Grips for time sec: the client checks AbyssalRecall with r 155141501
            if (!tsm.hasStat(SpecterState)) {
                changeSpecterState();
            }
            int lv = Math.max(1, chr.getSkillLevel(PRIMORDIAL_ABYSS));
            tsm.putCharacterStatValue(AbyssalRecall, new Option(1, PRIMORDIAL_ABYSS_GRIPS,
                    SkillData.getSkillInfoById(PRIMORDIAL_ABYSS).getValue(time, lv)), true);
            tsm.sendSetStatPacket();
        }
        Skill skill = chr.getSkill(attackInfo.skillId);
        int skillID = 0;
        SkillInfo si = null;
        boolean hasHitMobs = attackInfo.mobAttackInfo.size() > 0;
        int slv = 0;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skill.getSkillId());
            slv = skill.getCurrentLevel();
            skillID = skill.getSkillId();
        }
        if (hasHitMobs
                && !SkillConstants.isArkForceAtomAttack(attackInfo.skillId)
                && !isHexaCastOrTile(attackInfo.skillId)
                && attackInfo.skillId != CONVERSION_OVERDRIVE_ATTACK
                && attackInfo.skillId != BLISSFUL_RESTRAINT_TILE
                && attackInfo.skillId != ABYSSAL_RECALL
                && attackInfo.skillId != ABYSSAL_RECALL_2
                && attackInfo.skillId != CREEPING_TERROR_HELD_DOWN
                && attackInfo.skillId != CREEPING_TERROR
                && attackInfo.skillId != BLISSFUL_RESTRAINT_ATTACK
        ) {
            createImpendingDeathForceAtom();
            bonusConversionOverdriveAttack();
        }
        if (isComboSkill(attackInfo.skillId)) {
            incBattleFrenzy();
        }
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Field field = chr.getField();
        switch (attackInfo.skillId) {
            case BASIC_CHARGE_DRIVE_ATOM:
                if (hasHitMobs) {
                    Skill skill2 = chr.getSkill(BASIC_CHARGE_DRIVE_ATTACK);
                    SkillInfo si2 = SkillData.getSkillInfoById(BASIC_CHARGE_DRIVE_BUFF);
                    int slv2 = (byte) skill2.getCurrentLevel();

                    var hasChargeSpellAmp = tsm.hasStat(ChargeSpellAmplifier);

                    if (hasChargeSpellAmp && chr.getParty() != null) {
                        SkillInfo csmSI = SkillData.getSkillInfoById(CHARGE_SPELL_AMPLIFIER);
                        Rect rect = csmSI.getFirstRect();
                        Party party = chr.getParty();
                        for (Char pChr : party.getPartyMembersInField(chr)) {
                            if (!pChr.equals(chr) && chr.getRectAround(rect).hasPositionInside(pChr.getPosition())) {
                                TemporaryStatManager pTsm = pChr.getTemporaryStatManager();
                                Option o4 = new Option();
                                Option o5 = new Option();
                                o4.rOption = BASIC_CHARGE_DRIVE_BUFF;
                                o4.nOption = si2.getValue(indieSpeed, slv2);
                                o4.tOption = si2.getValue(time, slv2);
                                pTsm.putCharacterStatValue(IndieSpeed, o4);
                                o5.rOption = BASIC_CHARGE_DRIVE_BUFF;
                                o5.nOption = si2.getValue(indieStance, slv2);
                                o5.tOption = si2.getValue(time, slv2);
                                pTsm.putCharacterStatValue(IndieStance, o5);
                                pTsm.sendSetStatPacket();
                            }
                        }
                    }
                    o1.rOption = BASIC_CHARGE_DRIVE_BUFF;
                    o1.nOption = si2.getValue(indieSpeed, slv2) * (hasChargeSpellAmp ? 2 : 1);
                    o1.tOption = si2.getValue(time, slv2);
                    tsm.putCharacterStatValue(IndieSpeed, o1);
                    o2.rOption = BASIC_CHARGE_DRIVE_BUFF;
                    o2.nOption = si2.getValue(indieStance, slv2) * (hasChargeSpellAmp ? 2 : 1);
                    o2.tOption = si2.getValue(time, slv2);
                    tsm.putCharacterStatValue(IndieStance, o2);
                }
                break;
            case SCARLET_CHARGE_DRIVE_ATOM:
                if (hasHitMobs) {
                    Skill skill2 = chr.getSkill(SCARLET_CHARGE_DRIVE_ATTACK_1);
                    SkillInfo si2 = SkillData.getSkillInfoById(SCARLET_CHARGE_DRIVE_BUFF);
                    int slv2 = (byte) skill2.getCurrentLevel();

                    var hasChargeSpellAmp = tsm.hasStat(ChargeSpellAmplifier);

                    if (hasChargeSpellAmp && chr.getParty() != null) {
                        SkillInfo csmSI = SkillData.getSkillInfoById(CHARGE_SPELL_AMPLIFIER);
                        Rect rect = csmSI.getFirstRect();
                        Party party = chr.getParty();
                        for (Char pChr : party.getPartyMembersInField(chr)) {
                            if (!pChr.equals(chr) && chr.getRectAround(rect).hasPositionInside(pChr.getPosition())) {
                                TemporaryStatManager pTsm = pChr.getTemporaryStatManager();
                                Option o4 = new Option();
                                Option o5 = new Option();
                                o4.rOption = SCARLET_CHARGE_DRIVE_BUFF;
                                o4.nOption = si2.getValue(indiePad, slv2);
                                o4.tOption = si2.getValue(time, slv2);
                                pTsm.putCharacterStatValue(IndiePAD, o4);
                                o5.rOption = SCARLET_CHARGE_DRIVE_BUFF;
                                o5.nOption = si2.getValue(indieCr, slv2);
                                o5.tOption = si2.getValue(time, slv2);
                                pTsm.putCharacterStatValue(IndieCr, o5);
                                pTsm.sendSetStatPacket();
                            }
                        }
                    }

                    o1.rOption = SCARLET_CHARGE_DRIVE_BUFF;
                    o1.nOption = si2.getValue(indiePad, slv2) * (hasChargeSpellAmp ? 2 : 1);
                    o1.tOption = si2.getValue(time, slv2);
                    tsm.putCharacterStatValue(IndiePAD, o1);
                    o2.rOption = SCARLET_CHARGE_DRIVE_BUFF;
                    o2.nOption = si2.getValue(indieCr, slv2) * (hasChargeSpellAmp ? 2 : 1);
                    o2.tOption = si2.getValue(time, slv2);
                    tsm.putCharacterStatValue(IndieCr, o2);
                }
                break;
            case GUST_CHARGE_DRIVE_ATOM:
                if (hasHitMobs) {
                    Skill skill2 = chr.getSkill(GUST_CHARGE_DRIVE_ATTACK);
                    SkillInfo si2 = SkillData.getSkillInfoById(GUST_CHARGE_DRIVE_BUFF);
                    int slv2 = (byte) skill2.getCurrentLevel();

                    var hasChargeSpellAmp = tsm.hasStat(ChargeSpellAmplifier);

                    if (hasChargeSpellAmp && chr.getParty() != null) {
                        SkillInfo csmSI = SkillData.getSkillInfoById(CHARGE_SPELL_AMPLIFIER);
                        Rect rect = csmSI.getFirstRect();
                        Party party = chr.getParty();
                        for (Char pChr : party.getPartyMembersInField(chr)) {
                            if (!pChr.equals(chr) && chr.getRectAround(rect).hasPositionInside(pChr.getPosition())) {
                                TemporaryStatManager pTsm = pChr.getTemporaryStatManager();
                                Option o4 = new Option();
                                Option o5 = new Option();
                                o4.rOption = GUST_CHARGE_DRIVE_BUFF;
                                o4.nOption = -1;
                                o4.tOption = si2.getValue(time, slv2);
                                pTsm.putCharacterStatValue(IndieBooster, o4);
                                o5.rOption = GUST_CHARGE_DRIVE_BUFF;
                                o5.nOption = si2.getValue(indieEvaR, slv2);
                                o5.tOption = si2.getValue(time, slv2);
                                pTsm.putCharacterStatValue(IndieEVAR, o5);
                                pTsm.sendSetStatPacket();
                            }
                        }
                    }

                    o1.rOption = GUST_CHARGE_DRIVE_BUFF;
                    o1.nOption = -1 * (hasChargeSpellAmp ? 2 : 1);
                    o1.tOption = si2.getValue(time, slv2);
                    tsm.putCharacterStatValue(IndieBooster, o1);
                    o2.rOption = GUST_CHARGE_DRIVE_BUFF;
                    o2.nOption = si2.getValue(indieEvaR, slv2) * (hasChargeSpellAmp ? 2 : 1);
                    o2.tOption = si2.getValue(time, slv2);
                    tsm.putCharacterStatValue(IndieEVAR, o2);
                }
                break;
            case ABYSSAL_CHARGE_DRIVE_ATOM:
                if (hasHitMobs) {
                    // Buff
                    Skill skill2 = chr.getSkill(ABYSSAL_CHARGE_DRIVE_ATTACK);
                    SkillInfo si2 = SkillData.getSkillInfoById(ABYSSAL_CHARGE_DRIVE_BUFF);
                    int slv2 = (byte) skill2.getCurrentLevel();

                    var hasChargeSpellAmp = tsm.hasStat(ChargeSpellAmplifier);

                    if (hasChargeSpellAmp && chr.getParty() != null) {
                        SkillInfo csmSI = SkillData.getSkillInfoById(CHARGE_SPELL_AMPLIFIER);
                        Rect rect = csmSI.getFirstRect();
                        Party party = chr.getParty();
                        for (Char pChr : party.getPartyMembersInField(chr)) {
                            if (!pChr.equals(chr) && chr.getRectAround(rect).hasPositionInside(pChr.getPosition())) {
                                TemporaryStatManager pTsm = pChr.getTemporaryStatManager();
                                Option o4 = new Option();
                                Option o5 = new Option();
                                Option o6 = new Option();
                                o4.rOption = ABYSSAL_CHARGE_DRIVE_BUFF;
                                o4.nOption = si2.getValue(indieDamR, slv2);
                                o4.tOption = si2.getValue(time, slv2);
                                pTsm.putCharacterStatValue(IndieDamR, o4);
                                o5.rOption = ABYSSAL_CHARGE_DRIVE_BUFF;
                                o5.nOption = si2.getValue(indieBDR, slv2);
                                o5.tOption = si2.getValue(time, slv2);
                                pTsm.putCharacterStatValue(IndieBDR, o5);
                                o6.rOption = ABYSSAL_CHARGE_DRIVE_BUFF;
                                o6.nOption = si2.getValue(indieIgnoreMobpdpR, slv2);
                                o6.tOption = si2.getValue(time, slv2);
                                pTsm.putCharacterStatValue(IndieIgnoreMobpdpR, o6);
                                pTsm.sendSetStatPacket();
                            }
                        }
                    }

                    o1.rOption = ABYSSAL_CHARGE_DRIVE_BUFF;
                    o1.nOption = si2.getValue(indieDamR, slv2) * (hasChargeSpellAmp ? 2 : 1);
                    o1.tOption = si2.getValue(time, slv2);
                    tsm.putCharacterStatValue(IndieDamR, o1);
                    o2.rOption = ABYSSAL_CHARGE_DRIVE_BUFF;
                    o2.nOption = si2.getValue(indieBDR, slv2) * (hasChargeSpellAmp ? 2 : 1);
                    o2.tOption = si2.getValue(time, slv2);
                    tsm.putCharacterStatValue(IndieBDR, o2);
                    o3.rOption = ABYSSAL_CHARGE_DRIVE_BUFF;
                    o3.nOption = si2.getValue(indieIgnoreMobpdpR, slv2) * (hasChargeSpellAmp ? 2 : 1);
                    o3.tOption = si2.getValue(time, slv2);
                    tsm.putCharacterStatValue(IndieIgnoreMobpdpR, o3);

                    // Tile
                    field = chr.getField();
                    Mob mob = Util.getRandomFromCollection(new ArrayList<Mob>() {{
                        attackInfo.mobAttackInfo.forEach(mai -> add((Mob) chr.getField().getLifeByObjectID(mai.mobId)));
                    }});
                    SkillInfo rca = SkillData.getSkillInfoById(ABYSSAL_CHARGE_DRIVE_TILE);
                    AffectedArea aa = AffectedArea.getAffectedArea(chr, attackInfo);
                    aa.setSkillID(ABYSSAL_CHARGE_DRIVE_TILE);
                    aa.setPosition(mob.getPosition());
                    Rect rect = aa.getPosition().getRectAround(rca.getFirstRect());
                    aa.setRect(rect);
                    field.spawnAffectedArea(aa);
                }
                break;

            case HEXA_BASIC_CAST:
                if (hasHitMobs) {
                    giveCastBuff(HEXA_BASIC_BUFF, HEXA_BASIC_CHARGE_DRIVE_ATTACK);
                }
                break;
            case HEXA_SCARLET_CAST:
                if (hasHitMobs) {
                    giveCastBuff(HEXA_SCARLET_BUFF, HEXA_SCARLET_CHARGE_DRIVE_ATTACK_1);
                }
                break;
            case HEXA_GUST_CAST:
            case HEXA_GUST_CAST_2:
                if (hasHitMobs) {
                    giveCastBuff(HEXA_GUST_BUFF, HEXA_GUST_CHARGE_DRIVE_ATTACK);
                }
                break;
            case HEXA_ABYSSAL_CAST:
                if (hasHitMobs) {
                    giveCastBuff(HEXA_ABYSSAL_BUFF, HEXA_ABYSSAL_CHARGE_DRIVE_ATTACK);
                    Mob tileMob = attackInfo.mobAttackInfo.stream()
                            .map(mai -> (Mob) chr.getField().getLifeByObjectID(mai.mobId))
                            .filter(Objects::nonNull).findAny().orElse(null);
                    SkillInfo tileSi = SkillData.getSkillInfoById(HEXA_ABYSSAL_TILE);
                    if (tileMob != null && tileSi != null && tileSi.getFirstRect() != null) {
                        AffectedArea tile = AffectedArea.getAffectedArea(chr, attackInfo);
                        tile.setSkillID(HEXA_ABYSSAL_TILE);
                        tile.setSlv((byte) chr.getSkillLevel(HEXA_ABYSSAL_CHARGE_DRIVE_ATTACK));
                        tile.setPosition(tileMob.getPosition());
                        tile.setRect(tile.getPosition().getRectAround(tileSi.getFirstRect()));
                        field.spawnAffectedArea(tile);
                    }
                }
                break;

            // Gain Energy Charge on Attack
            case BASIC_CHARGE_DRIVE_ATTACK:
            case HEXA_BASIC_CHARGE_DRIVE_ATTACK:
            case SCARLET_CHARGE_DRIVE_ATTACK_1:
            case HEXA_SCARLET_CHARGE_DRIVE_ATTACK_1:
            case SCARLET_CHARGE_DRIVE_ATTACK_2:
            case SCARLET_CHARGE_DRIVE_AIR:
            case SCARLET_CHARGE_DRIVE_ATTACK_COMBO_1:
            case SCARLET_CHARGE_DRIVE_ATTACK_COMBO_2:
            case GUST_CHARGE_DRIVE_ATTACK:
            case HEXA_GUST_CHARGE_DRIVE_ATTACK:
            case GUST_CHARGE_DRIVE_ATTACK_COMBO:
            case ABYSSAL_CHARGE_DRIVE_ATTACK:
            case HEXA_ABYSSAL_CHARGE_DRIVE_ATTACK:
                if (hasHitMobs) {
                    addSpellCharge(attackInfo.skillId);
                    if (tsm.hasStat(InfinitySpell)) {
                        for (int i = 0; i < 4; i++) {
                            addSpellCharge(BASIC_CHARGE_DRIVE_ATTACK);
                        }
                    }
                }
                break;
            case OMINOUS_NIGHTMARE:
            case VIVID_NIGHTMARE:
            case ENDLESS_NIGHTMARE:
            case HEXA_OMINOUS_NIGHTMARE:
                chr.addSkillCoolTime(OMINOUS_NIGHTMARE, 2000);
            case ENDLESS_DREAM:
            case HEXA_OMINOUS_DREAM:
                if (chr.hasSkill(NIGHTMARES_ESCAPE)) {
                    int vSkill = NIGHTMARES_ESCAPE;
                    if (tsm.hasStat(SpecterState)) {
                        vSkill = DREAMS_ESCAPE;
                    }
                    if (!chr.hasSkillOnCooldown(vSkill)) {
                        chr.write(UserLocal.userBonusAttackRequest(vSkill));
                    }
                }
                si = SkillData.getSkillInfoById(attackInfo.skillId);
                slv = attackInfo.slv;
                chr.heal(chr.getHPPerc(si.getValue(w, slv)));
                break;
            case VIVID_DREAM:
            case OMINOUS_DREAM:
            case GRIEVOUS_WOUND:
            case GRIEVOUS_WOUND_AIR:
            case GRIEVOUS_WOUND_COMBO:
            case INSATIABLE_HUNGER_FINISHER:
            case INSATIABLE_HUNGER_COMBO:
            case UNBRIDLED_CHAOS:
            case UNBRIDLED_CHAOS_COMBO:
                si = SkillData.getSkillInfoById(attackInfo.skillId);
                slv = attackInfo.slv;
                chr.heal(chr.getHPPerc(si.getValue(w, slv)));
                int cdSkillID = SkillConstants.getCorrectCooltimeSkillID(attackInfo.skillId);
                int lefSkillID = getLefSkillBySpecterSkill(cdSkillID);
                chr.addSkillCoolTime(cdSkillID, SkillData.getSkillInfoById(cdSkillID).getValue(cooltime, chr.getSkillLevel(lefSkillID)) * 1000);
                break;
            // HEXA Specter attacks: heal w% of max HP (cooldowns come from their own data)
            case HEXA_GRIEVOUS_WOUND: // HEXA Grievous Wound
            case HEXA_GRIEVOUS_WOUND_2:
            case HEXA_GRIEVOUS_WOUND_3:
            case HEXA_INSATIABLE_HUNGER: // HEXA Insatiable Hunger
            case HEXA_INSATIABLE_HUNGER_2:
            case HEXA_INSATIABLE_HUNGER_3:
            case HEXA_UNBRIDLED_CHAOS: // HEXA Unbridled Chaos
            case HEXA_UNBRIDLED_CHAOS_2: {
                SkillInfo hsi = SkillData.getSkillInfoById(attackInfo.skillId);
                if (hsi != null && hasHitMobs) {
                    chr.heal(chr.getHPPerc(hsi.getValue(w, Math.max(1, attackInfo.slv))));
                }
                break;
            }
            case NIGHTMARES_ESCAPE:
            case DREAMS_ESCAPE:
                si = SkillData.getSkillInfoById(NIGHTMARES_ESCAPE);
                slv = (byte) chr.getSkillLevel(NIGHTMARES_ESCAPE);
                chr.addSkillCoolTime(attackInfo.skillId, si.getValue(cooltime, slv) * 1000);
                break;
            case CREEPING_TERROR_HELD_DOWN:
                o1.nOption = 1;
                o1.rOption = attackInfo.skillId;
                o1.tOption = CREEPING_TERROR_HIT_INVINCIBLE_SEC;
                tsm.putCharacterStatValue(NotDamaged, o1);
                break;
            case CREEPING_TERROR:
                tsm.removeStatsBySkill(CREEPING_TERROR_HELD_DOWN);
                if (!tsm.hasStat(SpecterState)) {
                    changeSpecterState();
                }
                break;
            case ENDLESS_AGONY:
            case HEXA_ENDLESS_AGONY:
                if (!tsm.hasStat(SpecterState)) {
                    changeSpecterState();
                }
                break;
            case BLISSFUL_RESTRAINT_ATTACK:
            case HEXA_BLISSFUL_RESTRAINT_ATTACK:
                if (!tsm.hasStat(SpecterState)) {
                    changeSpecterState();
                }
                int tileId = attackInfo.skillId == HEXA_BLISSFUL_RESTRAINT_ATTACK ? HEXA_BLISSFUL_RESTRAINT_TILE : BLISSFUL_RESTRAINT_TILE;
                SkillInfo rca = SkillData.getSkillInfoById(tileId);
                if (rca == null || rca.getFirstRect() == null) {
                    rca = SkillData.getSkillInfoById(BLISSFUL_RESTRAINT_TILE);
                    tileId = BLISSFUL_RESTRAINT_TILE;
                }
                AffectedArea aa = AffectedArea.getAffectedArea(chr, attackInfo);
                aa.setSkillID(tileId);
                aa.setPosition(new Position(chr.getPosition().getX(), chr.getPosition().getY() - 300));
                Rect rect = chr.getPosition().getRectAround(rca.getFirstRect());
                aa.setRect(rect);
                field.spawnAffectedArea(aa);
                break;
            case BLISSFUL_RESTRAINT_TILE:
            case HEXA_BLISSFUL_RESTRAINT_TILE:
            case HEXA_BLISSFUL_RESTRAINT_TILE_ATTACK:
                o1.nOption = 1;
                o1.rOption = attackInfo.skillId;
                o1.tOption = BLISSFUL_RESTRAINT_FREEZE_SEC;

                DebuffUtil.applyDebuffOnMobs(chr, MobStat.Freeze, o1, attackInfo.getMobIds());
                break;
            case IMPENDING_DEATH_ATOM:
                spawnWreckage(attackInfo);
                break;
        }

        tsm.sendSetStatPacket();
        super.handleAttack(c, attackInfo);
    }

    @Override
    public BypassCooldownCheckType canBypassCooldownCheck(int skillId, AttackInfo attackInfo, SkillUseInfo sui, SkillUseSource source) {
        // Using attackInfo.skillId, as skillId is corrected through SkillConstants.getCorrectCooltimeSkill
        // And they need to be handled separately.
        switch (attackInfo.skillId) {
            case UNSTOPPABLE_IMPULSE:
            case UNSTOPPABLE_IMPULSE_COMBO:
            case TENACIOUS_INSTINCT:
            case TENACIOUS_INSTINCT_COMBO:
                return BypassCheck;
        }
        return super.canBypassCooldownCheck(skillId, attackInfo, sui, source);
    }

    private void incBattleFrenzy() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();

        var count = 1;
        if (tsm.hasStat(BattleFrenzy)) {
            count = tsm.getOption(BattleFrenzy).nOption;
            count++;
        }
        updateBattleFrenzy(count);
    }

    private void updateBattleFrenzy(int count) {
        if (!chr.hasSkill(BATTLE_FRENZY)) {
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o1 = new Option();

        SkillInfo si = SkillData.getSkillInfoById(BATTLE_FRENZY);
        var slv = chr.getSkillLevel(si.getSkillId());

        var max = si.getValue(x, slv);
        if (max <= 0) {
            return; // v271 Battle Frenzy is a flat passive (pdR), no stacks
        }

        o1.nOption = Math.min(count, max);
        o1.rOption = BATTLE_FRENZY;
        o1.tOption = si.getValue(time, slv);
        tsm.putCharacterStatValue(BattleFrenzy, o1, true);
    }

    private void addSpellCharge(int skillId) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o1 = new Option();
        if (!tsm.hasStat(SpecterState) && getCurrentChargeCount() < 5) {
            SpellChargeType spellChargeType = switch (skillId) {
                case HEXA_BASIC_CHARGE_DRIVE_ATTACK -> SpellChargeType.Basic;
                case HEXA_SCARLET_CHARGE_DRIVE_ATTACK_1 -> SpellChargeType.Scarlet;
                case HEXA_GUST_CHARGE_DRIVE_ATTACK -> SpellChargeType.Gust;
                case HEXA_ABYSSAL_CHARGE_DRIVE_ATTACK -> SpellChargeType.Abyssal;
                default -> SpellChargeType.getByVal(skillId / 10000);
            };
            if (spellChargeType == null) {
                return;
            }
            switch (spellChargeType) {
                case Basic:
                    o1.xOption = tsm.hasStat(BasicCast) ? tsm.getOption(BasicCast).xOption > 10 ? 10 : tsm.getOption(BasicCast).xOption + 2 : 2;
                    tsm.putCharacterStatValue(BasicCast, o1);
                    break;
                case Scarlet:
                    o1.xOption = 1;
                    tsm.putCharacterStatValue(ScarletCast, o1);
                    break;
                case Gust:
                    o1.xOption = 1;
                    tsm.putCharacterStatValue(GustCast, o1);
                    break;
                case Abyssal:
                    o1.xOption = 1;
                    tsm.putCharacterStatValue(AbyssalCast, o1);
                    break;
            }
        }
    }

    private static boolean isHexaCastOrTile(int skillId) {
        return switch (skillId) {
            case HEXA_BASIC_CAST, HEXA_SCARLET_CAST, HEXA_GUST_CAST, HEXA_GUST_CAST_2, HEXA_ABYSSAL_CAST,
                 HEXA_ABYSSAL_TILE, HEXA_BLISSFUL_RESTRAINT_TILE, HEXA_BLISSFUL_RESTRAINT_TILE_ATTACK,
                 HEXA_VENGEFUL_HATE -> true;
            default -> false;
        };
    }

    /**
     * A charge cast's buff: every indie stat of the buff skill, at the charge drive's level, doubled for the caster
     * under Charge Spell Amplifier, which also gives it to party members in range.
     */
    private void giveCastBuff(int buffId, int levelSkillId) {
        SkillInfo si = SkillData.getSkillInfoById(buffId);
        int slv = chr.getSkillLevel(levelSkillId);
        if (si == null || slv <= 0) {
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        boolean amp = tsm.hasStat(ChargeSpellAmplifier);
        int duration = si.getValue(time, slv);
        List<Char> others = new ArrayList<>();
        if (amp && chr.getParty() != null) {
            Rect rect = SkillData.getSkillInfoById(CHARGE_SPELL_AMPLIFIER).getFirstRect();
            for (Char pChr : chr.getParty().getPartyMembersInField(chr)) {
                if (!pChr.equals(chr) && rect != null && chr.getRectAround(rect).hasPositionInside(pChr.getPosition())) {
                    others.add(pChr);
                }
            }
        }
        for (var e : CAST_BUFF_STATS.entrySet()) {
            int value = si.getValue(e.getKey(), slv);
            if (value == 0) {
                continue;
            }
            tsm.putCharacterStatValue(e.getValue(), new Option(value * (amp ? 2 : 1), buffId, duration));
            for (Char pChr : others) {
                pChr.getTemporaryStatManager().putCharacterStatValue(e.getValue(), new Option(value, buffId, duration));
            }
        }
        for (Char pChr : others) {
            pChr.getTemporaryStatManager().sendSetStatPacket();
        }
    }

    private boolean impendingDeathOn() {
        return chr.getTemporaryStatManager().hasStat(ImpendingDeath) || chr.hasSkill(IMPENDING_DEATH);
    }

    private void spawnWreckage(AttackInfo attackInfo) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();

        if (!chr.hasSkill(VENGEFUL_HATE) || !tsm.hasStat(SpecterState) || !impendingDeathOn()) {
            return;
        }
        Field field = chr.getField();
        Skill skill = chr.getSkill(VENGEFUL_HATE);
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        int slv = skill.getCurrentLevel();
        // Unending Hatred: mark chance dummyStr (100%), max marks dummyStr2 (15); SkillInfo does not read dummyStr
        boolean hatred = tsm.hasStatBySkillId(UNENDING_HATRED);
        int markProp = hatred ? 100 : si.getValue(s, slv);
        int maxMarks = hatred ? 15 : si.getValue(z, slv);
        for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
            if (Util.succeedProp(markProp) && field.getWreckageByChrId(chr.getId()).size() < maxMarks) {
                Mob mob = (Mob) field.getLifeByObjectID(mai.mobId);
                if (mob == null) {
                    continue;
                }
                Wreckage wreckage = Wreckage.getWreckageBy(chr, skill.getSkillId(), mob.getPosition(), si.getValue(q, slv) * 1000, 0);
                field.spawnWreckage(chr, wreckage);
            }
        }
    }


    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillID, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        super.handleSkill(chr, tsm, skillID, slv, inPacket, skillUseInfo);
        SkillInfo si = null;
        if (chr.getSkill(skillID) != null) {
            si = SkillData.getSkillInfoById(skillID);
        }
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (skillID) {
            case CONTACT_CARAVAN:
                int randSkill = skillID + new Random().nextInt(4) + 1;
                si = SkillData.getSkillInfoById(randSkill);

                o1.nOption = si.getValue(indieDamR, slv);
                o1.rOption = randSkill;
                o1.tOption = si.getValue(time, slv);

                o2.nOption = si.getValue(indieExp, slv);
                o2.rOption = randSkill;
                o2.tOption = si.getValue(time, slv);

                if (o1.nOption > 0) {
                    tsm.putCharacterStatValue(IndieDamR, o1);
                }
                if (o2.nOption > 0) {
                    tsm.putCharacterStatValue(IndieEXP, o2);
                }
                if (randSkill == CONTACT_CARAVAN_FERRET) {
                    o3.nOption = si.getValue(indieCooltimeReduce, slv);
                    o3.rOption = randSkill;
                    o3.tOption = si.getValue(time, slv);
                    tsm.putCharacterStatValue(IndieCooltimeReduce, o3); // TODO  handle CooltimeReduce BaseStat
                }

                if (randSkill == CONTACT_CARAVAN_ZIPPY) {
                    int cdR = si.getValue(indieCooltimeReduce, slv);
                    for (var skillId : chr.getSkillCoolTimes().keySet()) {
                        if (!SkillConstants.isNoCooltimeResetSkill(skillId)) {
                            long remainingCooltime = chr.getRemainingCoolTime(skillId);
                            int cooltimeCut = (int) ((remainingCooltime * cdR) / 100D);
                            if (cooltimeCut > 0) {
                                chr.reduceSkillCoolTime(skillId, cooltimeCut);
                            }
                        }
                    }
                }

                chr.write(UserPacket.effect(Effect.skillUse(randSkill, chr.getLevel(), slv, 0)));
                chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.skillUse(randSkill, chr.getLevel(), slv, 0)));
                break;
            case FLOAT:
                si = SkillData.getSkillInfoById(skillID);
                slv = 1;
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                o1.setInMillis(true);
                tsm.putCharacterStatValue(IndieFloating, o1, true);
                o2.nOption = 1;
                o2.rOption = skillID;
                o2.tOption = si.getValue(time, slv);
                o2.setInMillis(true);
                tsm.putCharacterStatValue(NewFlying, o2, true);
                tsm.sendSetStatPacket();
                break;
            case SPELL_BULLETS:
                if (!chr.hasSkill(SPELL_BULLETS) || tsm.hasStat(SpecterState)) {
                    return;
                }
                createSpellBulletForceAtom();
                resetCharges();
                break;
            case VENGEFUL_HATE:
            case HEXA_VENGEFUL_HATE:
                List<Wreckage> wreckageList = chr.getField().getWreckageByChrId(chr.getId());
                createVengefulHateForceAtom(wreckageList);
                break;
            case MASTER_CORRUPTION:
                if (chr.hasSkill(MASTER_CORRUPTION) && tsm.hasStat(SpecterEnergy) && tsm.getOption(SpecterEnergy).xOption > 0) {
                    changeSpecterState();
                } else {
                    chr.chatMessage("Cannot enter Specter state because of Spectra Fatigue.");
                }
                break;
            case UNENDING_HATRED:
                // no named v271 stat: the icon rides on IndieBuffIcon
                tsm.putCharacterStatValue(IndieBuffIcon, new Option(1, skillID, si.getValue(time, slv)));
                break;
            case KNUCKLE_BOOSTER_ARK:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieBooster, o1);
                break;
            case IMPENDING_DEATH:
                if (tsm.hasStat(ImpendingDeath)) {
                    tsm.removeStatsBySkill(IMPENDING_DEATH);
                } else {
                    o1.nOption = 1;
                    o1.rOption = skillID;
                    o1.tOption = si.getValue(time, slv);
                    tsm.putCharacterStatValue(ImpendingDeath, o1);
                }
                break;
            case DIVINE_WRATH:
                o1.rOption = skillID;
                o1.nOption = si.getValue(indieDamR, slv);
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o1);
                break;
            case CHARGE_SPELL_AMPLIFIER:
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(ChargeSpellAmplifier, o1);
                break;
            case INFINITY_SPELL:
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(InfinitySpell, o1);
                break;
        }
        tsm.sendSetStatPacket();
    }

    @Override
    public void handleRemoveCTS(CharacterTemporaryStat cts, Option o) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (cts.equals(SpecterState)) {
            tsm.removeStatsBySkill(FLOAT);
        }
        
        super.handleRemoveCTS(cts, o);
    }

    @Override
    public void handleKeyDownSkill(Char chr, int skillID, InPacket inPacket) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option();
        Option o1 = new Option();
        switch (skillID) {
            case ABYSSAL_RECALL:
                if (!tsm.hasStat(SpecterState)) {
                    changeSpecterState();
                }
                o.nOption = 1;
                o.rOption = ABYSSAL_RECALL_2;
                o.tOption = ABYSSAL_RECALL_SEC;
                tsm.putCharacterStatValue(AbyssalRecall, o, true);
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = ABYSSAL_RECALL_INVINCIBLE_SEC;
                tsm.putCharacterStatValue(IndieNotDamaged, o1, true);
                tsm.sendSetStatPacket();
                break;
            case CREEPING_TERROR_HELD_DOWN:
                if (!tsm.hasStat(SpecterState)) {
                    changeSpecterState();
                }
                o.nOption = 1;
                // invincible while the key is held, up to time sec
                o.rOption = skillID;
                o.tOption = SkillData.getSkillInfoById(CREEPING_TERROR_HELD_DOWN).getValue(time, 1);
                tsm.putCharacterStatValue(IndieNotDamaged, o, true);
                tsm.sendSetStatPacket();
                break;
        }
        super.handleKeyDownSkill(chr, skillID, inPacket);
    }

    @Override
    public void handleCancelKeyDownSkill(Char chr, int skillID) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option();
        switch (skillID) {
            case ABYSSAL_RECALL:
                tsm.removeStatsBySkill(skillID);
                break;
            case CREEPING_TERROR_HELD_DOWN:
                tsm.removeStatsBySkill(skillID);
                break;
        }
        super.handleCancelKeyDownSkill(chr, skillID);
    }

    @Override
    public void handleSkillPrepareStart(Char chr, int skillId, SkillUseInfo sui) {
        switch (skillId) {
            case ENDLESSLY_STARVING_BEAST:
                chr.setSkillCooldown(skillId, sui.slv); // hardcode-ok
                return; // explicitly return as super.handleSkillPrepareStart for ENDLESSLY_STARVING_BEAST should NOT be called.
        }
        super.handleSkillPrepareStart(chr, skillId, sui);
    }

    private void createSpellBulletForceAtom() {
        if (!chr.hasSkill(SPELL_BULLETS)) {
            return;
        }
        Skill skill = chr.getSkill(SPELL_BULLETS);
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Field field = chr.getField();
        Rect rect = chr.getPosition().getRectAround(si.getFirstRect());
        if (!chr.isLeft()) {
            rect = rect.horizontalFlipAround(chr.getPosition().getX());
        }
        Random random = new Random();
        int i = new Random().nextBoolean() ? 900 : 700;
        ForceAtom abyssalFA = new ForceAtom(true, 0, chr.getId(), ForceAtomEnum.ABYSSAL_CHARGE,
                true, new ArrayList<>(), ABYSSAL_CHARGE_DRIVE_ATOM, new ArrayList<>(), new Rect(), 0, 0,
                new Position(), ABYSSAL_CHARGE_DRIVE_ATOM, new Position(), 0);
        ForceAtom gustFA = new ForceAtom(true, 0, chr.getId(), ForceAtomEnum.GUST_CHARGE,
                true, new ArrayList<>(), GUST_CHARGE_DRIVE_ATOM, new ArrayList<>(), new Rect(), 0, 0,
                new Position(), GUST_CHARGE_DRIVE_ATOM, new Position(), 0);
        ForceAtom scarletFA = new ForceAtom(true, 0, chr.getId(), ForceAtomEnum.SCARLET_CHARGE,
                true, new ArrayList<>(), SCARLET_CHARGE_DRIVE_ATOM, new ArrayList<>(), new Rect(), 0, 0,
                new Position(), SCARLET_CHARGE_DRIVE_ATOM, new Position(), 0);
        ForceAtom basicFA = new ForceAtom(true, 0, chr.getId(), ForceAtomEnum.BASIC_CHARGE,
                true, new ArrayList<>(), BASIC_CHARGE_DRIVE_ATOM, new ArrayList<>(), new Rect(), 0, 0,
                new Position(), BASIC_CHARGE_DRIVE_ATOM, new Position(), 0);
        for (CharacterTemporaryStat cast : spellCasts) {
            if (cast == null || !tsm.hasStat(cast)) {
                continue;
            }
            for (int j = 0; j < tsm.getOption(cast).xOption; j++) {
                int firstImpact = random.nextInt(15) + 35;
                int secondImpact = random.nextInt(2) + 5;
                int delay = random.nextInt(400) + 500;
                int angle = random.nextInt(20) + 50;

                Mob mob = Util.getRandomFromCollection(field.getMobsInRect(rect));
                ForceAtomInfo fai = new ForceAtomInfo(chr.getNewForceAtomKey(), i, firstImpact, secondImpact,
                        angle, delay, Util.getCurrentTime(), 0, 0,
                        new Position());

                switch (cast) {
                    case ScarletCast: // Scarlet Charge
                        scarletFA.getTargetIdList().add(mob != null ? mob.getObjectId() : 0);
                        scarletFA.getFaiList().add(fai);
                        break;
                    case GustCast: // Gust Charge
                        gustFA.getTargetIdList().add(mob != null ? mob.getObjectId() : 0);
                        gustFA.getFaiList().add(fai);
                        break;
                    case AbyssalCast: // Abyssal Charge
                        abyssalFA.getTargetIdList().add(mob != null ? mob.getObjectId() : 0);
                        abyssalFA.getFaiList().add(fai);
                        break;
                    default:    // Basic Charge
                        basicFA.getTargetIdList().add(mob != null ? mob.getObjectId() : 0);
                        basicFA.getFaiList().add(fai);
                        break;
                }
                i++;
            }
        }
        field.broadcastPacket(FieldPacket.createArkForceAtom(chr.getId(), SPELL_BULLETS, abyssalFA, gustFA, scarletFA, basicFA));
    }

    private void createImpendingDeathForceAtom() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!tsm.hasStatBySkillId(SPECTER_STATE) || !chr.hasSkill(IMPENDING_DEATH)) {
            return;
        }
        Field field = chr.getField();
        Skill skill = chr.getSkill(IMPENDING_DEATH);
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        int slv = skill.getCurrentLevel();

        Rect rect = chr.getPosition().getRectAround(SkillData.getSkillInfoById(IMPENDING_DEATH_ATOM).getFirstRect());
        if (!chr.isLeft()) {
            rect = rect.horizontalFlipAround(chr.getPosition().getX());
        }
        List<ForceAtomInfo> faiList = new ArrayList<>();
        List<Integer> targetIdList = new ArrayList<>();
        int firstImpact = new Random().nextInt(15) + 35;
        int secondImpact = new Random().nextInt(2) + 5;
        ForceAtomEnum fae = ForceAtomEnum.IMPENDING_DEATH;
        int bulletCount = si.getValue(z, slv) + (tsm.hasStat(InfinitySpell) ? 3 : 0);
        List<Mob> possibleTargets = field.getMobsInRect(rect).stream().filter(m ->!MobConstants.isFriendlyMob(m.getTemplateId())).collect(Collectors.toList());
        for (int i = 0; i < bulletCount; i++) {
            Mob mob = Util.getRandomFromCollection(possibleTargets);
            ForceAtomInfo forceAtomInfo = new ForceAtomInfo(1, fae.getInc(), firstImpact, secondImpact,
                    270, 100, Util.getCurrentTime(), 0, 0,
                    new Position());

            targetIdList.add(mob != null ? mob.getObjectId() : 0);
            faiList.add(forceAtomInfo);
        }
        chr.createForceAtom(new ForceAtom(false, 0, chr.getId(), fae,
                true, targetIdList, IMPENDING_DEATH_ATOM, faiList, new Rect(), 0, 300,
                new Position(), IMPENDING_DEATH_ATOM, new Position(), 0));
    }

    private void createVengefulHateForceAtom(List<Wreckage> wreckageList) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Field field = chr.getField();
        if (!chr.hasSkill(VENGEFUL_HATE) || !tsm.hasStat(SpecterState) || !impendingDeathOn()) {
            return;
        }
        for (Wreckage wreckage : wreckageList) {
            int firstImpact = new Random().nextInt(30) + 330;
            int secondImpact = new Random().nextInt(5) + 60;
            ForceAtomEnum fae = ForceAtomEnum.VENGEFUL_HATE;
            Mob mob = Util.getRandomFromCollection(field.getMobs());
            ForceAtomInfo forceAtomInfo = new ForceAtomInfo(1, fae.getInc(), firstImpact, secondImpact,
                    0, 500, Util.getCurrentTime(), 8, 0,
                    wreckage.getPosition());
            chr.createForceAtom(new ForceAtom(false, 0, chr.getId(), fae,
                    false, new ArrayList<>(), VENGEFUL_HATE, Collections.singletonList(forceAtomInfo), new Rect(), 0, 300,
                    chr.getPosition(), VENGEFUL_HATE, mob == null ? new Position() : mob.getPosition(), 0));
        }
        field.removeWreckage(chr, wreckageList);
    }

    @Override
    public void setCharCreationStats(Char chr) {
        super.setCharCreationStats(chr);
        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        cs.setPosMap(SANDSTORM_DESERT_FIELD);
        cs.setLevel(10);
        cs.setHp(442);
        cs.setMaxHp(442);
        cs.setMaxMp(320);
        cs.setMp(231);
        //chr.setJob(JobConstants.JobEnum.ARK_1.getJobId());
        chr.addSpToJobByCurrentJob(5);
        chr.getScriptManager().giveNewSecondary(INITIAL_PATH); // secondary
    }
}
