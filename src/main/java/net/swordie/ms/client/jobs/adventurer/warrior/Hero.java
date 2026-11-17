package net.swordie.ms.client.jobs.adventurer.warrior;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.info.HitInfo;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.SkillUseSource;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.MobAttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.*;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.enums.MoveAbility;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.MobTemporaryStat;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.enums.AssistType;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

public class Hero extends Warrior {
    public static final int RAGE = 1101006;
    public static final int WEAPON_BOOSTER_FIGHTER = 1101004;
    public static final int COMBO_ATTACK = 1100013; // v271 passive (v265 cast 1101013)
    public static final int FINAL_ATTACK_FIGHTER = 1100002;
    public static final int COMBO_FURY = 1101012;
    public static final int COMBO_FURY_DOWN = 1100012;


    public static final int COMBO_SYNERGY = 1110013;
    public static final int SELF_RECOVERY = 1110000;
    public static final int PANIC = 1111003; // Scarring Sword in v265: a buff, its hits scar enemies
    public static final int FLASH_BLADE = 1101014;
    public static final int BEAM_BLADE = 1111016;
    public static final int HEXA_BEAM_BLADE = 1141003;
    public static final int RENDING_EDGE = 1140005;
    // Rending Edge counts these (Skill.wz 1140005 skillList; skillList2 skills count half)
    private static final int VALHALLA_STANCE = 100; // hardcode-ok: full knockback resistance
    public static final int RUSH = 1111012;
    public static final int RAGING_BLOW_2 = 1120017;
    public static final int RAGING_BLOW = 1121008;
    public static final int HEXA_RAGING_BLOW = 1141000;
    public static final int HEXA_RAGING_BLOW_2 = 1141001;
    public static final int FINAL_BLADE = 1141004;
    public static final int SPIRIT_CALIBUR = 1141500;
    public static final int SPIRIT_CALIBUR_2 = 1141501;
    public static final int LEAP_ATTACK = 1001010;
    public static final int UPWARD_CHARGE = 1001011;
    public static final int HEXA_RISING_RAGE = 1141002;
    private static final java.util.Set<Integer> RENDING_EDGE_SKILLS = java.util.Set.of(1001005, 1111010, 1101011,
            BEAM_BLADE, RAGING_BLOW, Hero.PUNCTURE, RAGING_BLOW_2, Hero.WORLDREAVER, Hero.SWORD_ILLUSION_2, Hero.SWORD_ILLUSION_3, HEXA_RAGING_BLOW, HEXA_RAGING_BLOW_2, HEXA_RISING_RAGE, HEXA_BEAM_BLADE,
            FINAL_BLADE, Hero.HEXA_PUNCTURE, UPWARD_CHARGE, LEAP_ATTACK, FLASH_BLADE, RUSH);
    private static final java.util.Set<Integer> RENDING_EDGE_HALF_SKILLS = java.util.Set.of(1121055, 400011074,
            Hero.COMBO_INSTINCT_SLASH_DOWN, Hero.COMBO_INSTINCT_SLASH_STRAIGHT, Hero.HEXA_CRY_VALHALLA_SPIRIT, SPIRIT_CALIBUR, SPIRIT_CALIBUR_2);
    private int rendingEdgeHits; // in halves


    public static final int ADVANCED_FINAL_ATTACK = 1120013;
    public static final int ADVANCED_FINAL_ATTACK_OPPORTUNITY = 1120048;
    public static final int HEXA_FINAL_ATTACK = 1140009;
    public static final int PUNCTURE = 1121015;
    public static final int MAGIC_CRASH_HERO = 1121016;
    public static final int HEROS_WILL_HERO = 1121011;
    public static final int ADVANCED_COMBO = 1120003;
    public static final int ENRAGE = 1120010; // passive
    public static final int ADVANCED_COMBO_REINFORCE = 1120043;
    public static final int ADVANCED_COMBO_OPPORTUNITY = 1120044;
    public static final int MAPLE_WARRIOR_HERO = 1121000;
    public static final int CRY_VALHALLA = 1121054; //Lv150
    public static final int EPIC_ADVENTURE_HERO = 1121053; //Lv200


    // V skills
    public static final int BURNING_SOUL_BLADE = 400011001;
    public static final int BURNING_SOUL_BLADE_STATIONARY = 400011002;
    public static final int WORLDREAVER = 400011027;
    private static final int WORLDREAVER_CAST_MS = 2000;
    public static final int COMBO_INSTINCT = 400011073;
    public static final int COMBO_INSTINCT_SLASH_STRAIGHT = 400011076;
    public static final int COMBO_INSTINCT_SLASH_DOWN = 400011075;
    public static final int COMBO_INSTINCT_SLASH_UP = 400011074;
    public static final int SWORD_ILLUSION = 400011124;
    public static final int SWORD_ILLUSION_2 = 400011125;
    public static final int SWORD_ILLUSION_3 = 400011126;
    public static final int HEXA_PUNCTURE = 1141008; // HEXA Puncture
    public static final int HEXA_CRY_VALHALLA = 1141006; // HEXA Cry Valhalla
    public static final int HEXA_CRY_VALHALLA_SPIRIT = 1141007; // HEXA Cry Valhalla
    public static final int CRY_VALHALLA_SPIRIT = 1121055;


    private static final List<Integer> comboInstinctAttack = Arrays.asList(
            COMBO_INSTINCT_SLASH_DOWN,
            COMBO_INSTINCT_SLASH_UP,
            COMBO_INSTINCT_SLASH_STRAIGHT
    );
    private long soulBladeTime;
    private int cryValhallaCount;
    private ScheduledFuture selfRecoveryTimer;
    private ScheduledFuture flashBladeTimer;


    public Hero(Char chr) {
        super(chr);
        if (isHandlerOfJob(chr.getJob())) {
            startSelfRecovery();
            startFlashBladeCharge();
        }
    }

    // Flash Blade: one charge every w2 seconds, up to w
    private void startFlashBladeCharge() {
        EventManager.stopTimer(flashBladeTimer);
        SkillInfo si = SkillData.getSkillInfoById(FLASH_BLADE);
        if (si == null || !chr.hasSkill(FLASH_BLADE)) {
            return;
        }
        int slv = chr.getSkillLevel(FLASH_BLADE);
        int interval = Math.max(1, si.getValue(w2, slv));
        flashBladeTimer = EventManager.addFixedRateEvent(() -> {
            if (chr.getField() != null && chr.hasSkill(FLASH_BLADE)) {
                changeStack(FlashSlashStack, FLASH_BLADE, 1, Math.max(1, si.getValue(w, chr.getSkillLevel(FLASH_BLADE))));
            }
        }, 1, interval, TimeUnit.SECONDS);
    }

    private int getStack(net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat cts) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        return tsm.hasStat(cts) ? tsm.getOption(cts).nOption : 0;
    }

    private void changeStack(net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat cts, int skillId,
                             int delta, int max) {
        int cur = getStack(cts);
        int next = Math.max(0, Math.min(max, cur + delta));
        if (next == cur && chr.getTemporaryStatManager().hasStat(cts)) {
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        tsm.putCharacterStatValue(cts, new Option(next, skillId, 0));
        tsm.sendSetStatPacket();
    }

    // HEXA Beam Blade: the client asks for a charge every v2 seconds (up to 2)
    public void increaseBeamBlade() {
        if (!chr.hasSkill(HEXA_BEAM_BLADE)) {
            return;
        }
        changeStack(HeroAuraBladeStack, HEXA_BEAM_BLADE, 1, 2);
        chr.write(WvsContext.stackSkillRequestResult(HEXA_BEAM_BLADE, getStack(HeroAuraBladeStack)));
    }


    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isHero(id);
    }


    // v271 Combo Attack is passive: the combo counter is always there once learned
    @Override
    public void onWarp(Field oldField, Field newField) {
        if (newField != null) {
            giveComboCounter();
        }
        super.onWarp(oldField, newField);
    }

    // the boss revive happens in place and clears the buffs, so the counter has to come back here
    @Override
    public void handleRevive() {
        giveComboCounter();
        super.handleRevive();
    }

    private void giveComboCounter() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (chr.hasSkill(COMBO_ATTACK) && !tsm.hasStat(ComboCounter)) {
            Option o = new Option(1, COMBO_ATTACK, 0);
            o.xOption = 1;
            tsm.putCharacterStatValue(ComboCounter, o);
            tsm.sendSetStatPacket();
        }
    }

    private void addCombo() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o1 = new Option();
        if (tsm.hasStat(ComboCounter) && chr.hasSkill(COMBO_ATTACK)) {
            Skill skill = chr.getSkill(COMBO_ATTACK);
            int slv = skill.getCurrentLevel();
            int count = getComboCount();
            if (count < getMaxCombo()) {
                count++;
                if (chr.hasSkill(ADVANCED_COMBO) && !tsm.hasStatBySkillId(COMBO_INSTINCT)) {
                    SkillInfo acsi = SkillData.getSkillInfoById(ADVANCED_COMBO);
                    int acslv = chr.getSkillLevel(ADVANCED_COMBO);
                    var extraComboChance = acsi.getValue(prop, acslv) + chr.getSkillStatValue(prop, ADVANCED_COMBO_OPPORTUNITY);
                    if (slv > 0 && Util.succeedProp(extraComboChance)) {
                        count++;
                    }
                }
            }
            if (count > getMaxCombo()) {
                count = getMaxCombo();
            }

            var lock = false;
            int questId = QuestConstants.SKILL_COMMAND_LOCK_QUEST_ID_2; // questId 1544
            Quest quest = chr.getQuestManager().getQuestById(questId);
            if (quest != null) {
                lock = quest.getQRValue().equalsIgnoreCase("");
            }

            o1.nOption = count;
            o1.rOption = skill.getSkillId();
            o1.xOption = lock ? 0 : 1;
            tsm.putCharacterStatValue(ComboCounter, o1);
            tsm.sendSetStatPacket();
        }
    }

    private void fillCombo() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!tsm.hasStat(ComboCounter)) {
            return;
        }
        Option o = tsm.getOption(ComboCounter).deepCopy();
        o.nOption = getMaxCombo();
        tsm.putCharacterStatValue(ComboCounter, o);
        tsm.sendSetStatPacket();
    }

    private void removeCombo(int count) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!tsm.hasStat(ComboCounter)) {
            return;
        }
        Option o = new Option();
        int currentCount = getComboCount();
        int newCount = currentCount - count;

        var lock = false;
        int questId = QuestConstants.SKILL_COMMAND_LOCK_QUEST_ID_2; // questId 1544
        Quest quest = chr.getQuestManager().getQuestById(questId);
        if (quest != null) {
            lock = quest.getQRValue().equalsIgnoreCase("");
        }

        o.nOption = Math.max(newCount, 0);
        o.rOption = COMBO_ATTACK;
        o.xOption = lock ? 0 : 1;
        tsm.putCharacterStatValue(ComboCounter, o);
        tsm.sendSetStatPacket();
    }

    // Cry Valhalla: while it lasts, attacks call warrior spirits (w per call, u2 calls per cast)
    private void spawnCryValhallas() {
        int skillId = chr.hasSkill(HEXA_CRY_VALHALLA) ? HEXA_CRY_VALHALLA : CRY_VALHALLA;
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!chr.hasSkill(skillId) || !tsm.hasStatBySkillId(skillId)) {
            return;
        }
        int slv = chr.getSkillLevel(skillId);
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        int summonId = skillId == HEXA_CRY_VALHALLA ? HEXA_CRY_VALHALLA_SPIRIT : CRY_VALHALLA_SPIRIT;
        SkillInfo summonSi = SkillData.getSkillInfoById(summonId);
        if (summonSi == null || cryValhallaCount >= si.getValue(u2, slv)) {
            return;
        }
        for (int i = 0; i < summonSi.getValue(w, slv); i++) {
            Summon summon = Summon.getSummonByNoCTS(chr, summonId, slv);
            summon.setSummonTerm(summonSi.getValue(time, slv) / 1000);
            summon.setMoveAbility(MoveAbility.Stop);
            summon.setAssistType(AssistType.Attack);
            chr.getField().spawnSummon(summon);
        }
        cryValhallaCount++;
    }

    private int getComboProp() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Skill skill = null;
        if (chr.hasSkill(COMBO_SYNERGY)) {
            skill = chr.getSkill(COMBO_SYNERGY);
        } else if (chr.hasSkill(COMBO_ATTACK)) {
            skill = chr.getSkill(COMBO_ATTACK);
        }
        if (skill == null) {
            return 0;
        }
        int proc = SkillData.getSkillInfoById(skill.getSkillId()).getValue(prop, skill.getCurrentLevel());
        if (tsm.hasStatBySkillId(COMBO_INSTINCT)) {
            proc -= 50;
        }
        return proc < 0 ? 0 : proc;
    }

    private int getComboCount() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (tsm.hasStat(ComboCounter)) {
            return tsm.getOption(ComboCounter).nOption;
        }
        return -1;
    }

    private int getMaxCombo() {
        int num = 0;
        if (chr.hasSkill(COMBO_ATTACK)) {
            num = 6;
        }
        if (chr.hasSkill(ADVANCED_COMBO)) {
            num = 11;
        }
        return num;
    }

    public Skill getComboAttackSkill() {
        Skill skill = null;
        if (chr.hasSkill(ADVANCED_COMBO)) {
            skill = chr.getSkill(ADVANCED_COMBO);
        } else if (chr.hasSkill(COMBO_SYNERGY)) {
            skill = chr.getSkill(COMBO_SYNERGY);
        } else if (chr.hasSkill(COMBO_ATTACK)) {
            skill = chr.getSkill(COMBO_ATTACK);
        }

        return skill;
    }


    // Attack related methods ------------------------------------------------------------------------------------------

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        Char chr = c.getChr();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
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

        if (hasHitMobs) {
            //Combo
            if (tsm.hasStat(ComboCounter) && !isComboIgnoreSkill(attackInfo.skillId)) {
                int comboProp = getComboProp();
                if (Util.succeedProp(comboProp)) {
                    addCombo();
                }
            }
        }
        // Instinctual Combo
        // Raging Blow, its Enrage form and the HEXA versions (a 6th job Hero only casts 1141000/1141001)
        if (tsm.hasStatBySkillId(COMBO_INSTINCT) && (attackInfo.skillId == RAGING_BLOW || attackInfo.skillId == RAGING_BLOW_2
                || attackInfo.skillId == HEXA_RAGING_BLOW || attackInfo.skillId == HEXA_RAGING_BLOW_2)) {
            for (int comboInstinctSkillId : comboInstinctAttack) {
                chr.write(UserLocal.userBonusAttackRequest(comboInstinctSkillId));
            }
        }

        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();
        switch (attackInfo.skillId) {
            case COMBO_FURY:
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    if (Util.succeedProp(si.getValue(prop, slv))) {
                        addCombo();
                        if (!mob.isBoss()) {
                            mts.addStatOptionsAndBroadcast(chr, MobStat.Stun, o1);
                        }
                    }
                }
                break;
            case COMBO_FURY_DOWN:
                if (hasHitMobs) {
                    int mobId = attackInfo.mobAttackInfo.get(0).mobId;
                    Life mob = chr.getField().getLifeByObjectID(mobId);
                    if (mob instanceof Mob) {
                        chr.write(UserPacket.effect(Effect.showHookEffect(attackInfo.skillId, chr.getLevel(), 1, 0,
                                attackInfo.left, mobId, mob.getX(), mob.getY())));
                        chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.showHookEffect(attackInfo.skillId, chr.getLevel(), 1,
                                0, attackInfo.left, mobId, mob.getX(), mob.getY())));
                    }
                }
                break;
            case FLASH_BLADE:
                changeStack(FlashSlashStack, FLASH_BLADE, -1, 2);
                break;
            case HEXA_BEAM_BLADE:
                if (getStack(HeroAuraBladeStack) > 0) {
                    changeStack(HeroAuraBladeStack, HEXA_BEAM_BLADE, -1, 2);
                    chr.write(WvsContext.stackSkillRequestResult(HEXA_BEAM_BLADE, getStack(HeroAuraBladeStack)));
                }
                // Final Blade can follow within s seconds
                if (si != null && !tsm.hasStat(HeroAuraBladeAdv)) {
                    tsm.putCharacterStatValue(HeroAuraBladeAdv, new Option(1, skillID, Math.max(1, si.getValue(s, slv))));
                    tsm.sendSetStatPacket();
                }
                break;
            case PUNCTURE:
            case HEXA_PUNCTURE:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                removeCombo(si.getValue(y, slv));
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();

                    mts.addStatOptionsAndBroadcast(chr, MobStat.HitCriDamR, o1);
                    if (Util.succeedProp(si.getValue(prop, slv))) {
                        mts.createAndAddBurnedInfo(chr, skill);
                    }
                }
                break;
            case WORLDREAVER:
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if (mai.mob != null && mai.mobDies) {
                        chr.getField().broadcastPacket(MobPool.specialSelectedEffectBySkill(mai.mob, skillID, chr.getId()));
                    }
                }
                // v271 Worldreaver has no buff and no time value (a time of 0 never ran out): it is only
                // "invulnerable while casting", so the invincibility lasts about as long as the slash
                o2.nOption = 1;
                o2.rOption = WORLDREAVER;
                o2.tOption = WORLDREAVER_CAST_MS;
                o2.setInMillis(true);
                tsm.putCharacterStatValue(IndieNotDamaged, o2, true);
                tsm.sendSetStatPacket();
                break;
        }
        if (hasHitMobs && attackInfo.skillId != CRY_VALHALLA_SPIRIT && attackInfo.skillId != HEXA_CRY_VALHALLA_SPIRIT) {
            spawnCryValhallas();
        }
        if (hasHitMobs && tsm.hasStatBySkillId(PANIC)) {
            scarEnemies(attackInfo);
        }
        if (hasHitMobs) {
            rendingEdge(attackInfo);
        }
        super.handleAttack(c, attackInfo);
    }

    @Override
    public int alterMpCon(int mpCon, int skillId, int slv, AttackInfo attackInfo, SkillUseInfo sui, SkillUseSource source) {
        // Hero - Sword Illusion
        // Only apply MP Consumption on the first attack of SWORD_ILLUSION_2. Bypass MP Con for every other attack of Sword Illusion
        if (attackInfo.skillId == Hero.SWORD_ILLUSION_2 && attackInfo.sui.multiAttackInfoSequenceCounter != 1) {
            return 0;
        }
        if (attackInfo.skillId == HEXA_RISING_RAGE && attackInfo.sui.multiAttackInfoSequenceCounter > 1) {
            return 0;
        }

        return super.alterMpCon(mpCon, skillId, slv, attackInfo, sui, source);
    }

    // Rending Edge: every v counted hits leave a sword scar on the boss (or monster) with the most HP that was hit;
    // up to y scars (z during Combo Instinct)
    private void rendingEdge(AttackInfo attackInfo) {
        int skillId = attackInfo.skillId;
        boolean full = RENDING_EDGE_SKILLS.contains(skillId);
        if (!chr.hasSkill(RENDING_EDGE) || attackInfo.bySummonedID != 0 || (!full && !RENDING_EDGE_HALF_SKILLS.contains(skillId))) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(RENDING_EDGE);
        int slv = chr.getSkillLevel(RENDING_EDGE);
        if (si == null || si.getFirstRect() == null) {
            return;
        }
        rendingEdgeHits += full ? 2 : 1;
        int need = Math.max(1, si.getValue(v, slv)) * 2;
        if (rendingEdgeHits < need) {
            return;
        }
        rendingEdgeHits -= need;
        Field field = chr.getField();
        int max = chr.getTemporaryStatManager().hasStatBySkillId(COMBO_INSTINCT) ? si.getValue(z, slv) : si.getValue(y, slv);
        long scars = field.getAffectedAreas().stream()
                .filter(aa -> aa.getSkillID() == RENDING_EDGE && aa.getCharID() == chr.getId()).count();
        if (scars >= Math.max(1, max)) {
            return;
        }
        Mob target = null;
        for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
            Mob mob = (Mob) field.getLifeByObjectID(mai.mobId);
            if (mob == null || mob.getHp() <= 0) {
                continue;
            }
            if (target == null || (mob.isBoss() && !target.isBoss())
                    || (mob.isBoss() == target.isBoss() && mob.getMaxHp() > target.getMaxHp())) {
                target = mob;
            }
        }
        if (target == null) {
            return;
        }
        var pos = target.getPosition().deepCopy();
        net.swordie.ms.life.AffectedArea scar = net.swordie.ms.life.AffectedArea.getPassiveAA(chr, RENDING_EDGE, slv, pos);
        scar.setRect(pos.getRectAround(si.getFirstRect()));
        scar.setDuration(Math.max(1000, si.getValue(time, slv)));
        field.spawnAffectedArea(scar);
    }

    // Scarring Sword: prop% per enemy hit to lower its attack by w% and accuracy by x% for v seconds
    private void scarEnemies(AttackInfo attackInfo) {
        SkillInfo si = SkillData.getSkillInfoById(PANIC);
        int slv = chr.getSkillLevel(PANIC);
        if (si == null || slv <= 0) {
            return;
        }
        int dur = si.getValue(v, slv);
        for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
            Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
            if (mob == null || !Util.succeedProp(si.getValue(prop, slv))) {
                continue;
            }
            MobTemporaryStat mts = mob.getTemporaryStat();
            mts.addStatOptionsAndBroadcast(chr, MobStat.PAD, new Option(-si.getValue(w, slv), PANIC, dur));
            mts.addStatOptionsAndBroadcast(chr, MobStat.ACC, new Option(-si.getValue(x, slv), PANIC, dur));
        }
    }

    private Skill getFinalAtkSkill() {
        Skill skill = null;
        if (chr.hasSkill(FINAL_ATTACK_FIGHTER)) {
            skill = chr.getSkill(FINAL_ATTACK_FIGHTER);

        }
        if (chr.hasSkill(ADVANCED_FINAL_ATTACK)) {
            skill = chr.getSkill(ADVANCED_FINAL_ATTACK); // Hero Adv FA
        }
        if (chr.hasSkill(HEXA_FINAL_ATTACK)) {
            skill = chr.getSkill(HEXA_FINAL_ATTACK);
        }

        return skill;
    }

    @Override
    public int getFinalAttackSkill() {
        Skill faSkill = getFinalAtkSkill();
        if (faSkill != null) {
            SkillInfo si = SkillData.getSkillInfoById(faSkill.getSkillId());
            int slv = faSkill.getCurrentLevel();
            int proc = si.getValue(prop, slv) + chr.getSkillStatValue(prop, ADVANCED_FINAL_ATTACK_OPPORTUNITY);

            if (Util.succeedProp(proc)) {
                return faSkill.getSkillId();
            }
        }
        return super.getFinalAttackSkill();
    }


    private boolean isComboIgnoreSkill(int skillID) {
        return skillID == PANIC ||
                skillID == COMBO_FURY ||
                skillID == COMBO_FURY_DOWN ||
                skillID == PUNCTURE ||
                skillID == COMBO_INSTINCT_SLASH_DOWN ||
                skillID == COMBO_INSTINCT_SLASH_UP ||
                skillID == COMBO_INSTINCT_SLASH_STRAIGHT;
    }


    // Skill related methods -------------------------------------------------------------------------------------------

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        super.handleSkill(chr, tsm, skillId, slv, inPacket, skillUseInfo);
        Skill skill = chr.getSkill(skillId);
        SkillInfo si = null;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skillId);
        }
        Summon summon;
        Field field = chr.getField();
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();
        switch (skillId) {
            case RAGE:
                o1.rOption = skillId;
                o1.nOption = si.getValue(indiePad, slv);
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePAD, o1);
                o2.nOption = si.getValue(y, slv);
                o2.rOption = skillId;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePowerGuard, o2);
                break;
            case COMBO_ATTACK:
                var lock = false;
                int questId = QuestConstants.SKILL_COMMAND_LOCK_QUEST_ID_2; // questId 1544
                Quest quest = chr.getQuestManager().getQuestById(questId);
                if (quest != null) {
                    lock = quest.getQRValue().equalsIgnoreCase("");
                }
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.xOption = lock ? 0 : 1;
                tsm.putCharacterStatValue(ComboCounter, o1);
                break;
            case CRY_VALHALLA:
            case HEXA_CRY_VALHALLA:
                cryValhallaCount = 0;
                o1.rOption = skillId;
                o1.nOption = si.getValue(indieCr, slv);
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieCr, o1);
                o2.nOption = si.getValue(x, slv);
                o2.rOption = skillId;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(AsrR, o2);
                tsm.putCharacterStatValue(TerR, o2);
                o3.nOption = VALHALLA_STANCE;
                o3.rOption = skillId;
                o3.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Stance, o3);
                o4.rOption = skillId;
                o4.nOption = si.getValue(indiePad, slv);
                o4.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePAD, o4);
                fillCombo();
                break;
            case PANIC:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(CrusaderPanic, o1);
                break;
            case BURNING_SOUL_BLADE_STATIONARY: // down + skill key, or the key again with Action Customization on
            case BURNING_SOUL_BLADE: {
                int lv = chr.getSkillLevel(BURNING_SOUL_BLADE) > 0 ? chr.getSkillLevel(BURNING_SOUL_BLADE) : slv;
                boolean stationaryOut = field.getSummons().stream()
                        .anyMatch(s -> s.getSkillID() == BURNING_SOUL_BLADE_STATIONARY && s.getChr() == chr);
                boolean mobileOut = field.getSummons().stream()
                        .anyMatch(s -> s.getSkillID() == BURNING_SOUL_BLADE && s.getChr() == chr);
                if (stationaryOut) {
                    // the key again while it's fixed: back to the mobile blade for the time left
                    field.removeSummon(BURNING_SOUL_BLADE_STATIONARY, chr.getId());
                    summon = Summon.getSummonByNoCTS(chr, BURNING_SOUL_BLADE, lv);
                    summon.setMoveAbility(MoveAbility.Walk);
                    summon.setSummonTerm(Math.max(1, (int) ((soulBladeTime - Util.getCurrentTimeLong()) / 1000)));
                    field.spawnSummonAndRemoveOld(summon);
                } else if (mobileOut) {
                    field.removeSummon(BURNING_SOUL_BLADE, chr.getId());
                    summon = Summon.getSummonByNoCTS(chr, BURNING_SOUL_BLADE_STATIONARY, lv);
                    summon.setMoveAbility(MoveAbility.Stop);
                    summon.setSummonTerm(Math.max(1, (int) ((soulBladeTime - Util.getCurrentTimeLong()) / 1000)));
                    field.spawnSummonAndRemoveOld(summon);
                } else if (skillId == BURNING_SOUL_BLADE_STATIONARY && chr.hasSkillOnCooldown(BURNING_SOUL_BLADE)) {
                    chr.dispose(); // its cooldown is kept on the main skill
                } else {
                    // fresh cast: the fixed blade has its own (longer) time in the v271 data
                    int id = skillId == BURNING_SOUL_BLADE_STATIONARY ? BURNING_SOUL_BLADE_STATIONARY : BURNING_SOUL_BLADE;
                    SkillInfo bsi = SkillData.getSkillInfoById(id);
                    soulBladeTime = Util.getCurrentTimeLong() + 1000L * (bsi == null ? 0 : bsi.getValue(time, lv));
                    summon = Summon.getSummonBy(chr, id, lv);
                    summon.setMoveAbility(id == BURNING_SOUL_BLADE ? MoveAbility.Walk : MoveAbility.Stop);
                    field.spawnSummonAndRemoveOld(summon);
                    if (id == BURNING_SOUL_BLADE) {
                        fillCombo();
                    }
                    chr.setSkillCooldown(BURNING_SOUL_BLADE, lv); // hardcode-ok
                }
                break;
            }
            case COMBO_INSTINCT:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(ComboInstinct, o1);
                break;
        }
        tsm.sendSetStatPacket();
    }

    @Override
    public int alterCooldownSkill(int skillId, int slv, AttackInfo attackInfo, SkillUseInfo sui, SkillUseSource source) {
        switch (skillId) {
            case BURNING_SOUL_BLADE:
                var si = SkillData.getSkillInfoById(skillId);
                return si.getValue(x, slv) * 1000;
        }
        return super.alterCooldownSkill(skillId, slv, attackInfo, sui, source);
    }

    private void selfRecovery() {
        if (chr != null && chr.getField() != null && chr.hasSkill(SELF_RECOVERY)) {
            Skill skill = chr.getSkill(SELF_RECOVERY);
            SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
            int slv = skill.getCurrentLevel();
            // v265: x% of Max HP and y% of Max MP every u sec
            int healHP = si.getValue(hp, slv) + (int) ((long) chr.getMaxHP() * si.getValue(x, slv) / 100);
            int healMP = si.getValue(mp, slv) + (int) ((long) chr.getMaxMP() * si.getValue(y, slv) / 100);
            chr.heal(healHP, true, false);
            chr.healMP(healMP);
        } else if (selfRecoveryTimer != null && !selfRecoveryTimer.isDone()) {
            selfRecoveryTimer.cancel(true);
        }
    }


    // Hit related methods ---------------------------------------------------------------------------------------------

    @Override
    public void handleHit(Client c, InPacket inPacket, HitInfo hitInfo) {
        //Hero - Combo Synergy
        comboSynergy();

        super.handleHit(c, inPacket, hitInfo);
    }

    public void comboSynergy() {
        if (chr.hasSkill(COMBO_SYNERGY)) {
            Skill skill = chr.getSkill(COMBO_SYNERGY);
            SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
            int slv = skill.getCurrentLevel();
            int comboprop = si.getValue(subProp, slv);
            if (Util.succeedProp(comboprop)) {
                addCombo();
            }
        }
    }

    private void startSelfRecovery() {
        if (!chr.hasSkill(SELF_RECOVERY)) {
            return;
        }
        int interval = Math.max(1, chr.getSkillStatValue(u, SELF_RECOVERY));
        EventManager.stopTimer(selfRecoveryTimer);
        if (interval > 0) {
            selfRecoveryTimer = EventManager.addFixedRateEvent(this::selfRecovery, interval, interval, TimeUnit.SECONDS);
        }
    }

    @Override
    public void updateTimerSkill() {
        EventManager.stopTimer(selfRecoveryTimer);
        startSelfRecovery();
        startFlashBladeCharge();
        super.updateTimerSkill();
    }

    @Override
    public void stopTimers() {
        EventManager.stopTimer(selfRecoveryTimer);
        EventManager.stopTimer(flashBladeTimer);
        super.stopTimers();
    }
}
