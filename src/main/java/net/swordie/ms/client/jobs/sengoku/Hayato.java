package net.swordie.ms.client.jobs.sengoku;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.CharacterStat;
import net.swordie.ms.client.character.info.HitInfo;
import net.swordie.ms.client.character.items.BodyPart;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.skills.BypassCooldownCheckType;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.SkillUseSource;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Hayato as rewritten in v266 (v271 data): MP instead of Sword Energy, Shinsoku and Battou skills, the Akatsuki
 * state that Battou skills start and extend, Noto, Reactive Focus and Deflection.
 * The Battou forms (41100009-41100012), Noto and Shin Quick Draw's stance are drawn by the client; the v271 client
 * has no named stat for them, so the server only keeps their timing for the effects it applies itself.
 */
public class Hayato extends Job {

    private static final Logger log = LogManager.getLogger();

    // Beginner
    public static final int NATURAL_TALENT = 40010000;
    public static final int MOONLIT_BLADE_LEARNINGS = 40010001; // link skill
    public static final int RETURN_OF_THE_FIVE_PLANETS = 40011227;

    // 1st
    public static final int MIST_SLASH = 41001000;
    public static final int GRAZING_CUT = 41001001;
    public static final int BATTOUJUTSU_DASH = 41001002;
    public static final int AORYU = 41000004;

    // 2nd
    public static final int AKATSUKI_SAMURAI = 41100005;
    public static final int BATTOU = 41100009;
    public static final int BATTOU_SHOGETSU = 41100010;
    public static final int BATTOU_BOOST = 41100011;
    public static final int BATTOU_SHOGETSU_BOOST = 41100012;
    public static final int CROSSING_DRAW = 41101002;
    public static final int CROSSING_DRAW_2 = 41101013;
    public static final int MUSHIN = 41101003;

    // 3rd
    public static final int WARRIORS_HEART = 41110008;
    public static final int REACTIVE_FOCUS = 41111001;
    public static final int REACTIVE_FOCUS_COUNTER = 41111010;
    public static final int DARK_MOON_CUT = 41111002;
    public static final int DARK_MOON_CUT_2 = 41111009;

    // 4th
    public static final int DEFLECTION = 41120012;
    public static final int NOTO = 41121001;
    public static final int AKATSUKI_WILL = 41121004;
    public static final int AKATSUKI_WARRIOR = 41121005;
    public static final int FULL_MOONS_RAGE = 41121006;
    public static final int FULL_MOONS_RAGE_2 = 41121007;
    public static final int SWEEPING_SWORD = 41111017;
    public static final int BATTOU_FULL_MOONS_RAGE = 41121015;
    public static final int REACTIVE_FOCUS_ENHANCE = 41120051;
    public static final int AFTERIMAGE_SLASH = 41121002;
    public static final int AFTERIMAGE_SLASH_RETURN = 41121003;

    // Hyper
    public static final int AKATSUKI_BLESSING = 41121053;
    public static final int BUSHIDO = 41121054;

    // HEXA / 6th
    public static final int HEXA_DARK_MOON_CUT = 41141006;
    public static final int HEXA_DARK_MOON_CUT_2 = 41141007;
    public static final int HEXA_FULL_MOONS_RAGE = 41141008;
    public static final int HEXA_FULL_MOONS_RAGE_2 = 41141009;
    public static final int SHIN_QUICK_DRAW = 41141500;
    public static final int HEXA_AFTERIMAGE_SLASH = 41141001;
    public static final int HEXA_AFTERIMAGE_SLASH_RETURN = 41141002;

    // V
    public static final int SHOGETSU_FORM = 400011026;
    public static final int CRASHING_TIDE = 400011029;
    public static final int LIGHT_CUTTER = 400011104;
    public static final int WAILING_HEAVENS = 400011138;
    public static final int WAILING_HEAVENS_2 = 400011159;

    // v265 names other files still use (same ids)
    public static final int AKATSUKI_HERO_HAYATO = AKATSUKI_WARRIOR;
    public static final int AKATSUKI_BLOSSOMS = AKATSUKI_WILL;
    public static final int BATTOUJUTSU_ZANKOU = SHOGETSU_FORM;
    public static final int IAIJUTSU_PHANTOM_BLADE = CRASHING_TIDE;
    public static final int BATTOUJUTSU_ULTIMATE_WILL = LIGHT_CUTTER;
    public static final int INSTANT_SLICE = WAILING_HEAVENS;
    // v265 ids gone from v271
    public static final int SHINSOKU = NOTO;
    public static final int BATTOUJUTSU_ADVANCE = 41001010;
    public static final int SUMMER_RAIN = 40011289;
    public static final int HITOKIRI_HUNDRED_STRIKE = 40011290;
    public static final int RISING_SLASH = 41101014;
    public static final int FALCON_DIVE = 41101015;
    public static final int FUU_SANRENZAN = 41111011;
    public static final int FUU_SANRENZAN_2 = 41111012;
    public static final int SUMMER_RAIN_2 = 41121014;
    public static final int HITOKIRI_STRIKE_ANGLE = 41121022;
    public static final int HEXA_BATTOU_DARK_MOON_CUT = 41141010;
    public static final int HEXA_BATTOU_DARK_MOON_CUT_2 = 41141011;
    public static final int HEXA_BATTOU_FULL_MOONS_RAGE = 41141012;
    public static final int HEXA_BATTOU_FULL_MOONS_RAGE_2 = 41141013;
    public static final int BATTOU_WAILING_HEAVENS = 400011160;
    public static final int BATTOU_WAILING_HEAVENS_2 = 400011161;
    public static final int HONNOU_JI_EASTERN_WALL_FIELD = 807100000;

    /** Akatsuki: seconds a Battou skill adds (common/incMoonBuffSecondsOnUse; not a loaded skill stat). */
    private static final Map<Integer, Integer> AKATSUKI_SECONDS = Map.ofEntries(
            Map.entry(CROSSING_DRAW, 5), Map.entry(CROSSING_DRAW_2, 5),
            Map.entry(DARK_MOON_CUT, 10), Map.entry(DARK_MOON_CUT_2, 10),
            Map.entry(FULL_MOONS_RAGE, 15), Map.entry(FULL_MOONS_RAGE_2, 15),
            Map.entry(HEXA_DARK_MOON_CUT, 10), Map.entry(HEXA_DARK_MOON_CUT_2, 10),
            Map.entry(HEXA_FULL_MOONS_RAGE, 15), Map.entry(HEXA_FULL_MOONS_RAGE_2, 15),
            Map.entry(WAILING_HEAVENS, 40), Map.entry(WAILING_HEAVENS_2, 40),
            Map.entry(RISING_SLASH, 5), Map.entry(FALCON_DIVE, 5), Map.entry(FUU_SANRENZAN, 10), Map.entry(FUU_SANRENZAN_2, 10),
            Map.entry(SUMMER_RAIN_2, 15), Map.entry(BATTOU_FULL_MOONS_RAGE, 15), Map.entry(BATTOU_WAILING_HEAVENS, 40), Map.entry(BATTOU_WAILING_HEAVENS_2, 40),
            Map.entry(HEXA_BATTOU_DARK_MOON_CUT, 10), Map.entry(HEXA_BATTOU_DARK_MOON_CUT_2, 10), Map.entry(HEXA_BATTOU_FULL_MOONS_RAGE, 15), Map.entry(HEXA_BATTOU_FULL_MOONS_RAGE_2, 15)
    );
    /**
     * Battou skills open with USER_SKILL_PREPARE_START. GMS answers with the MP cost, the cooldown (on the id below) and
     * a 2 s draw stance: IndieBuffIcon = the Battou form skill, IndieAntiMagicShell 1. The attack follows without
     * cost or cooldown check.
     */
    private static final Map<Integer, Integer> BATTOU_PREPARE = Map.of(
            CROSSING_DRAW, CROSSING_DRAW, DARK_MOON_CUT, DARK_MOON_CUT, FULL_MOONS_RAGE, FULL_MOONS_RAGE,
            HEXA_DARK_MOON_CUT, DARK_MOON_CUT, HEXA_FULL_MOONS_RAGE, FULL_MOONS_RAGE,
            WAILING_HEAVENS, WAILING_HEAVENS, WAILING_HEAVENS_2, WAILING_HEAVENS);
    private static final int BATTOU_STANCE_MS = 2000;
    private static final int WAILING_GUARD_MS = 3000;
    private static final long BATTOU_ATTACK_WINDOW_MS = 4000;
    private static final Set<Integer> AFTERIMAGE_FIRST = Set.of(AFTERIMAGE_SLASH, HEXA_AFTERIMAGE_SLASH);
    private static final Map<Integer, Integer> AFTERIMAGE_RETURN = Map.of(
            AFTERIMAGE_SLASH_RETURN, AFTERIMAGE_SLASH, HEXA_AFTERIMAGE_SLASH_RETURN, HEXA_AFTERIMAGE_SLASH);
    /** autoChargeStack skills (v271 data): id, max charges, ms per charge. Shown with IndieAutoChargeStack. */
    private static final int[][] CHARGE_SKILLS = {{GRAZING_CUT, 5, 1000}, {LIGHT_CUTTER, 3, 15000}};
    /** Reactive Focus: the Shuchu window (0.dummyStr4 sec). */
    private static final long SHUCHU_MS = 600;
    public static final int WHITE_TIGER_BLADE = 1352804;

    private static final int[] addedSkills = new int[]{
            NATURAL_TALENT,
            RETURN_OF_THE_FIVE_PLANETS,
    };

    private long notoUntil;
    private long shuchuUntil;
    private long nextDeflection;
    private int preparedBattou;
    private long preparedAt;
    private boolean preparedUsed;
    private final int[] charges = new int[CHARGE_SKILLS.length];
    private final long[] chargeAt = new long[CHARGE_SKILLS.length];
    private final boolean[] chargeKnown = new boolean[CHARGE_SKILLS.length];
    private ScheduledFuture<?> chargeTimer;
    private boolean chargeTickFailed;

    public Hayato(Char chr) {
        super(chr);
        if (isHandlerOfJob(chr.getJob())) {
            for (int id : addedSkills) {
                if (!chr.hasSkill(id)) {
                    Skill skill = SkillData.getSkillDeepCopyById(id);
                    if (skill != null) {
                        skill.setCurrentLevel(skill.getMasterLevel());
                        chr.addSkill(skill);
                    }
                }
            }
            if (!chr.getQuestManager().hasQuestCompleted(57143)) {
                chr.getQuestManager().completeQuest(57143, false); // required to job adv
            }
            chargeTimer = EventManager.addFixedRateEvent(this::tickCharges, 1000, 250, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isHayato(id);
    }

    public static boolean isBattouSkill(int skillId) {
        return AKATSUKI_SECONDS.containsKey(skillId);
    }

    /** Light Cutter has no summon in v271; kept for Summon.onRemoved. */
    public void gainSwordEnergyFromGodOfBladesSummon(int swordEnergy) {
    }


    // Attack related methods ------------------------------------------------------------------------------------------

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        boolean hasHitMobs = attackInfo.mobAttackInfo.size() > 0;
        int skillId = attackInfo.skillId;
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (isBattouSkill(skillId) && !(isPreparedBattouAttack(skillId) && preparedUsed)) {
            // once per draw: a draw sends one attack per slash
            if (System.currentTimeMillis() < notoUntil) {
                notoUntil = 0; // a Battou skill uses up Noto
                tsm.removeStatsBySkill(NOTO);
            }
            if (isPreparedBattouAttack(skillId)) {
                preparedUsed = true;
                if (preparedBattou != WAILING_HEAVENS && preparedBattou != WAILING_HEAVENS_2) {
                    tsm.removeStatsBySkill(preparedBattou); // GMS resets the draw stance after the slash
                }
            }
            tsm.sendResetStatPacket();
            extendAkatsuki(AKATSUKI_SECONDS.get(skillId));
        }
        if (skillId == GRAZING_CUT) {
            useCharge(0);
        }
        if (AFTERIMAGE_FIRST.contains(skillId) && tsm.getOptByCTSAndSkill(IndieCalcDamageStat, skillId) == null) {
            placeAfterimage(skillId, attackInfo.slv);
        } else if (AFTERIMAGE_RETURN.containsKey(skillId)) {
            tsm.removeStatsBySkill(AFTERIMAGE_RETURN.get(skillId));
            tsm.sendResetStatPacket();
        }
        if (hasHitMobs) {
            warriorsHeart();
        }
        super.handleAttack(c, attackInfo);
    }

    /** Akatsuki: Final Damage / Ignore Defense from Akatsuki Samurai, extended per Battou skill up to subTime sec. */
    private void extendAkatsuki(int seconds) {
        int slv = chr.getSkillLevel(AKATSUKI_SAMURAI);
        SkillInfo si = SkillData.getSkillInfoById(AKATSUKI_SAMURAI);
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (slv <= 0 || si == null || tsm.getOptByCTSAndSkill(IndiePMdR, SHOGETSU_FORM) != null) {
            return; // Shogetsu replaces Akatsuki while it lasts
        }
        int max = si.getValue(subTime, slv) * 1000;
        long left = tsm.getRemainingTime(IndiePMdR, AKATSUKI_SAMURAI);
        int duration = (int) Math.min(max > 0 ? max : 60000, left + seconds * 1000L);
        Option fd = new Option(si.getValue(indiePMdR, slv), AKATSUKI_SAMURAI, duration);
        fd.setInMillis(true);
        Option ied = new Option(si.getValue(indieIgnoreMobpdpR, slv), AKATSUKI_SAMURAI, duration);
        ied.setInMillis(true);
        tsm.putCharacterStatValue(IndiePMdR, fd, true);
        tsm.putCharacterStatValue(IndieIgnoreMobpdpR, ied, true);
        if (!isAkatsukiEffectLocked()) {
            Option loop = new Option(chr.getId(), AKATSUKI_SAMURAI, duration);
            loop.setInMillis(true);
            tsm.putCharacterStatValue(IndieLoopEffect, loop, true);
        }
        tsm.sendSetStatPacket();
    }

    /** Akatsuki Samurai's right-click (In 1151) locks its activation effect; kept in quest-ex 1544. */
    private boolean isAkatsukiEffectLocked() {
        var q = chr.getQuestManager() == null ? null
                : chr.getQuestManager().getQuestById(QuestConstants.SKILL_COMMAND_LOCK_QUEST_ID_2);
        if (q == null) {
            return false;
        }
        q.convertQRValueToProperties();
        return q.hasProperty(String.valueOf(AKATSUKI_SAMURAI));
    }

    /**
     * Afterimage Slash leaves its afterimage where the dash starts: GMS sets IndieCheckTimeByClient (IndieFloating) 1,
     * IndieLoopEffect and IndieCalcDamageStat = the position (x low short, y high short) for the skill's time. The
     * client allows the return slash while they last.
     */
    private void placeAfterimage(int skillId, int slv) {
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        int lv = slv > 0 ? slv : Math.max(1, chr.getSkillLevel(skillId));
        int dur = si == null ? 5 : Math.max(1, si.getValue(time, lv));
        Position p = chr.getPosition();
        int packed = (p.getX() & 0xFFFF) | (p.getY() << 16);
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        tsm.putCharacterStatValue(IndieFloating, new Option(1, skillId, dur), true);
        tsm.putCharacterStatValue(IndieLoopEffect, new Option(packed, skillId, dur), true);
        tsm.putCharacterStatValue(IndieCalcDamageStat, new Option(packed, skillId, dur), true);
        tsm.sendSetStatPacket();
    }

    private boolean isPreparedBattouAttack(int skillId) {
        return preparedBattou != 0 && isBattouSkill(skillId)
                && System.currentTimeMillis() - preparedAt <= BATTOU_ATTACK_WINDOW_MS;
    }

    @Override
    public void handleSkillPrepareStart(Char chr, int skillId, SkillUseInfo sui) {
        Integer cdSkill = BATTOU_PREPARE.get(skillId);
        if (cdSkill == null) {
            super.handleSkillPrepareStart(chr, skillId, sui);
            return;
        }
        int slv = sui.slv > 0 ? sui.slv : Math.max(chr.getSkillLevel(skillId), chr.getSkillLevel(cdSkill));
        if (slv <= 0 || chr.hasSkillOnCooldown(cdSkill)
                || !chr.applyMpCon(skillId, slv, new AttackInfo(), sui, SkillUseSource.SkillUseRequest)) {
            chr.dispose();
            return;
        }
        int cdLv = chr.getSkillLevel(cdSkill);
        chr.setSkillCooldown(cdSkill, cdLv > 0 ? cdLv : slv); // hardcode-ok
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (skillId == WAILING_HEAVENS || skillId == WAILING_HEAVENS_2) {
            Option guard = new Option(1, skillId, WAILING_GUARD_MS);
            guard.setInMillis(true);
            tsm.putCharacterStatValue(IndieNotDamaged, guard, true);
            tsm.sendSetStatPacket();
        }
        boolean shogetsu = tsm.getOptByCTSAndSkill(IndiePMdR, SHOGETSU_FORM) != null;
        boolean noto = System.currentTimeMillis() < notoUntil;
        int form = shogetsu ? (noto ? BATTOU_SHOGETSU_BOOST : BATTOU_SHOGETSU) : (noto ? BATTOU_BOOST : BATTOU);
        Option icon = new Option(form, skillId, BATTOU_STANCE_MS);
        icon.setInMillis(true);
        Option stance = new Option(1, skillId, BATTOU_STANCE_MS);
        stance.setInMillis(true);
        tsm.putCharacterStatValue(IndieBuffIcon, icon, true);
        tsm.putCharacterStatValue(IndieAntiMagicShell, stance, true);
        tsm.sendSetStatPacket();
        preparedBattou = skillId;
        preparedAt = System.currentTimeMillis();
        preparedUsed = false;
    }

    @Override
    public BypassCooldownCheckType canBypassCooldownCheck(int skillId, AttackInfo attackInfo, SkillUseInfo sui,
                                                          SkillUseSource source) {
        if (source.isAttackRequest() && isPreparedBattouAttack(skillId)) {
            return BypassCooldownCheckType.BypassCheckAndCooldown; // paid at the draw
        }
        return super.canBypassCooldownCheck(skillId, attackInfo, sui, source);
    }

    @Override
    public int alterMpCon(int mpCon, int skillId, int slv, AttackInfo attackInfo, SkillUseInfo sui,
                          SkillUseSource source) {
        if (source.isAttackRequest() && isPreparedBattouAttack(skillId)) {
            return 0;
        }
        return super.alterMpCon(mpCon, skillId, slv, attackInfo, sui, source);
    }

    // Charges ---------------------------------------------------------------------------------------------------------

    /** GMS starts the charges at 0 on login and adds one per interval up to the max (IndieAutoChargeStack, no time). */
    private synchronized void tickCharges() {
        try {
            if (!chr.isOnline() || chr.getField() == null || !isHandlerOfJob(chr.getJob())) {
                return;
            }
            long now = System.currentTimeMillis();
            boolean changed = false;
            for (int i = 0; i < CHARGE_SKILLS.length; i++) {
                if (chr.getSkillLevel(CHARGE_SKILLS[i][0]) <= 0) {
                    continue;
                }
                if (!chargeKnown[i]) {
                    chargeKnown[i] = true;
                    chargeAt[i] = now;
                    changed = true;
                } else if (charges[i] < CHARGE_SKILLS[i][1] && now - chargeAt[i] >= CHARGE_SKILLS[i][2]) {
                    charges[i]++;
                    chargeAt[i] = now;
                    changed = true;
                }
            }
            if (changed) {
                sendCharges();
            }
        } catch (Exception e) {
            // keep the timer alive; one line per character, the timer runs every second
            if (!chargeTickFailed) {
                chargeTickFailed = true;
                log.error("Hayato charge tick failed for character " + chr.getId(), e);
            }
        }
    }

    private synchronized void useCharge(int i) {
        if (!chargeKnown[i]) {
            return;
        }
        if (charges[i] >= CHARGE_SKILLS[i][1]) {
            chargeAt[i] = System.currentTimeMillis(); // refilling starts with the first use
        }
        if (charges[i] > 0) {
            charges[i]--;
        }
        sendCharges();
    }

    private void sendCharges() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        for (int i = 0; i < CHARGE_SKILLS.length; i++) {
            if (chargeKnown[i]) {
                Option o = new Option(charges[i], CHARGE_SKILLS[i][0], 0);
                o.setInMillis(true);
                tsm.putCharacterStatValue(IndieAutoChargeStack, o, true);
            }
        }
        tsm.sendSetStatPacket();
    }

    @Override
    public void stopTimers() {
        EventManager.stopTimer(chargeTimer);
        super.stopTimers();
    }

    /** Warrior's Heart: prop% to heal x% of Max HP (the data ties it to critical hits; landed hits stand in). */
    private void warriorsHeart() {
        int slv = chr.getSkillLevel(WARRIORS_HEART);
        SkillInfo si = SkillData.getSkillInfoById(WARRIORS_HEART);
        if (slv <= 0 || si == null || chr.getHP() >= chr.getMaxHP()) {
            return;
        }
        if (Util.succeedProp(si.getValue(prop, slv))) {
            chr.heal(chr.getHPPerc(si.getValue(x, slv)));
        }
    }


    // Skill related methods -------------------------------------------------------------------------------------------

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        super.handleSkill(chr, tsm, skillId, slv, inPacket, skillUseInfo);
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (si == null) {
            return;
        }
        switch (skillId) {
            case NOTO: {
                // GMS: IndieCheckTimeByClient (IndieFloating here) 1 for Noto's time
                int dur = si.getValue(time, slv);
                notoUntil = System.currentTimeMillis() + dur * 1000L;
                tsm.putCharacterStatValue(IndieFloating, new Option(1, NOTO, dur), true);
                tsm.sendSetStatPacket();
                break;
            }
            case LIGHT_CUTTER:
                useCharge(1);
                break;
            case REACTIVE_FOCUS:
                shuchuUntil = System.currentTimeMillis() + SHUCHU_MS;
                break;
            case SHOGETSU_FORM: {
                // replaces Akatsuki for its time and enters Noto
                tsm.removeStatsBySkill(AKATSUKI_SAMURAI);
                tsm.sendResetStatPacket();
                int dur = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePMdR, new Option(si.getValue(indiePMdR, slv), skillId, dur));
                tsm.putCharacterStatValue(IndieIgnoreMobpdpR, new Option(si.getValue(indieIgnoreMobpdpR, slv), skillId, dur));
                // GMS also: its loop effect, floating and no knock-back for the form's time
                tsm.putCharacterStatValue(IndieLoopEffect, new Option(chr.getId(), skillId, dur));
                tsm.putCharacterStatValue(IndieFloating, new Option(1, skillId, dur));
                tsm.putCharacterStatValue(IndieNoKnockBack, new Option(1, skillId, dur));
                tsm.sendSetStatPacket();
                notoUntil = System.currentTimeMillis() + 3000;
                break;
            }
            case BUSHIDO: {
                int dur = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePAD, new Option(si.getValue(indiePad, slv), skillId, dur));
                tsm.putCharacterStatValue(IndieAsrR, new Option(si.getValue(indieAsrR, slv), skillId, dur));
                tsm.putCharacterStatValue(IndieTerR, new Option(si.getValue(indieTerR, slv), skillId, dur));
                tsm.putCharacterStatValue(IndieCrDmg, new Option(si.getValue(indieCD, slv), skillId, dur));
                tsm.putCharacterStatValue(IndieBDR, new Option(si.getValue(indieBDR, slv), skillId, dur));
                tsm.putCharacterStatValue(IndieLoopEffect, new Option(chr.getId(), skillId, dur));
                tsm.sendSetStatPacket();
                break;
            }
            case SHIN_QUICK_DRAW:
                notoUntil = System.currentTimeMillis() + Math.max(3, si.getValue(time, slv)) * 1000L;
                break;
        }
    }


    // Hit related methods ---------------------------------------------------------------------------------------------

    @Override
    public void handleHit(Client c, InPacket inPacket, HitInfo hitInfo) {
        long now = System.currentTimeMillis();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (now < shuchuUntil && hitInfo.hpDamage > 0) {
            // Reactive Focus: hit while in Shuchu -> invincible for s sec (the counterattack is the client's)
            shuchuUntil = 0;
            int slv = chr.getSkillLevel(REACTIVE_FOCUS);
            SkillInfo si = SkillData.getSkillInfoById(REACTIVE_FOCUS);
            if (slv > 0 && si != null) {
                tsm.putCharacterStatValue(IndieNotDamaged, new Option(1, REACTIVE_FOCUS, Math.max(1, si.getValue(s, slv))), true);
                tsm.sendSetStatPacket();
            }
        }
        if (hitInfo.hpDamage > 0 && now >= nextDeflection) {
            int slv = chr.getSkillLevel(DEFLECTION);
            SkillInfo si = SkillData.getSkillInfoById(DEFLECTION);
            if (slv > 0 && si != null && Util.succeedProp(si.getValue(prop, slv))) {
                nextDeflection = now + si.getValue(cooltime, slv) * 1000L;
                tsm.putCharacterStatValue(IndieDamReduceR, new Option(si.getValue(x, slv), DEFLECTION, si.getValue(time, slv)));
                tsm.sendSetStatPacket();
            }
        }
        super.handleHit(c, inPacket, hitInfo);
    }

    @Override
    public void setCharCreationStats(Char chr) {
        super.setCharCreationStats(chr);
        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        cs.setPosMap(HONNOU_JI_EASTERN_WALL_FIELD);
        startInTownIfNoTutorial(chr, HONNOU_JI_EASTERN_WALL_FIELD, JobConstants.JobEnum.HAYATO_1.getJobId());
        Item item = ItemData.getItemDeepCopy(WHITE_TIGER_BLADE); // Secondary
        item.setBagIndex(BodyPart.Shield.getVal());
        chr.getEquippedInventory().addItem(item);
    }

    /** Momijigaoka, where Hayato and Kanna start when the data has no tutorial for them. */
    public static final int SENGOKU_TOWN = 807000000;

    /**
     * v271 replaced the v265 Hayato/Kanna tutorial maps (807100000 / 807100100) with new ones that have no scripts here:
     * without the old map, the character starts in town at level 10 with its 1st job (as classes without a tutorial).
     */
    public static void startInTownIfNoTutorial(Char chr, int tutorialMap, int firstJob) {
        if (net.swordie.ms.loaders.FieldData.getFieldInfoById(tutorialMap) != null) {
            return;
        }
        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        chr.setJob(firstJob);
        chr.addSpToJobByCurrentJob(5);
        cs.setLevel(10);
        cs.setAp(4 + cs.getLevel() * 5);
        cs.setHp(450);
        cs.setMaxHp(450);
        cs.setMp(300);
        cs.setMaxMp(300);
        cs.setPosMap(SENGOKU_TOWN);
    }

    @Override
    public void handleLevelUp() {
        super.handleLevelUp();
        // v271 Hayato has no advancement quests (the 685xx story is done at creation): the job comes with the level,
        // also with job_advancement_by_quest_only
        advanceByLevel(JobConstants.JobEnum.HAYATO_1.getJobId(), JobConstants.JobEnum.HAYATO_2.getJobId(),
                JobConstants.JobEnum.HAYATO_3.getJobId(), JobConstants.JobEnum.HAYATO_4.getJobId());
    }
}
