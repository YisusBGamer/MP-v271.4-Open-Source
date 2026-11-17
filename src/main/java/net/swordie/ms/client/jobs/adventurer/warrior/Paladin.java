package net.swordie.ms.client.jobs.adventurer.warrior;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.info.HitInfo;
import net.swordie.ms.client.character.items.BodyPart;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.life.AffectedArea;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.client.character.skills.ExtraSkillInfo;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.SkillUseSource;
import net.swordie.ms.client.character.skills.atom.forceatom.ForceAtom;
import net.swordie.ms.client.character.skills.info.*;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.party.Party;
import net.swordie.ms.client.party.PartyMember;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.Effect;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.SkillChangeConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.enums.ForceAtomEnum;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.MobTemporaryStat;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

public class Paladin extends Warrior {
    public static final int WEAPON_BOOSTER_PAGE = 1201004;
    public static final int FINAL_ATTACK_PAGE = 1200002;
    public static final int HEXA_FINAL_ATTACK = 1240008;
    public static final int HEXA_BLAST = 1241000;
    public static final int HEXA_DIVINE_CHARGE = 1241003;
    public static final int FALLING_JUSTICE = 1241005;
    public static final int DIVINE_JUDGMENT = 1220022;
    public static final int DIVINE_JUDGMENT_ATTACK = 1221023;
    public static final int HEXA_DIVINE_JUDGMENT = 1240001;
    public static final int HEXA_DIVINE_JUDGMENT_ATTACK = 1241002;
    public static final int FALLING_JUSTICE_ATTACK = 1241006;
    public static final int SACRED_BASTION = 1241500;
    public static final int SACRED_BASTION_TILE = 1241501;
    public static final int SACRED_BASTION_EXTRA = 1241502;
    public static final int SACRED_BASTION_AFTER_ATTACK = 1241503;
    public static final int CLOSE_COMBAT = 1201013;
    public static final int VESSEL_OF_LIGHT = 1200014; // Light Charges from Holy Power skills
    public static final int DIVINE_SHIELD = 1210016;


    public static final int PARASHOCK_GUARD = 1210014; // v271 passive (v265 toggle 1211014)
    public static final int COMBAT_ORDERS = 1211011;
    public static final int HP_RECOVERY = 1211010;
    public static final int DIVINE_CHARGE = 1211018;
    public static final int NOBLE_DEMAND = 1211013;
    public static final int ACHILLES = 1210015;
    public static final int SHIELD_MASTERY = 1210001;


    public static final int ELEMENTAL_FORCE = 1221015; // Divine Blessing
    public static final int HEAVENS_HAMMER = 1221011;
    public static final int GREATER_VESSEL_OF_LIGHT = 1220010;
    public static final int DIVINE_MARK = 1221019;
    public static final int DIVINE_MARK_EXPLOSION = 1221020;
    public static final int DIVINE_MARK_CHARGE = 1221021; // Divine Charge once Divine Mark is learned
    public static final int MAPLE_WARRIOR_PALADIN = 1221000;
    public static final int BLAST = 1221009;
    public static final int GUARDIAN = 1221016;
    public static final int MAGIC_CRASH_PALLY = 1221014;
    public static final int HEROS_WILL_PALA = 1221012;
    public static final int SACROSANCTITY = 1221054; //Lv140
    public static final int SMITE_SHIELD = 1221052; //Lv160
    public static final int EPIC_ADVENTURE_PALA = 1221053; //Lv190


    // V skills
    public static final int HAMMERS_OF_THE_RIGHTEOUS = 400011052;
    public static final int GRAND_GUARDIAN = 400011072;
    public static final int HAMMERS_OF_THE_RIGHTEOUS_2 = 400011053;
    public static final int DIVINE_ECHO = 400011003;
    public static final int HEXA_DIVINE_ECHO = 500061000;
    public static final int DIVINE_ECHO_MIMIC = 400011021; // skillId given to the user that mimics
    public static final int MIGHTY_MJOLNIR = 400011131;
    public static final int MIGHTY_MJOLNIR_EXPLOSION = 400011132;

    private static final int GRAND_GUARDIAN_NO_KNOCKBACK = 100; // hardcode-ok
    private static final int GRAND_GUARDIAN_BIG_CROSS = 2; // hardcode-ok: client state value
    private static final int SHIELD_MASTERY_STUN_SEC = 3; // hardcode-ok: data time=1+u(x/4) is not in the tooltip

    private ScheduledFuture divineEchoTimer;

    public Paladin(Char chr) {
        super(chr);
    }


    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isPaladin(id);
    }

    private void doDivineEcho() {
        var tsm = chr.getTemporaryStatManager();
        if (!tsm.hasStat(DivineEcho)) {
            EventManager.stopTimer(divineEchoTimer);
            removeDivineEchoMimic(); // remove by searching from other chars in field
            return;
        }
        var party = chr.getParty();
        if (party == null) {
            return;
        }
        // if does not have echo partner
            // search
            // update tsm

        // if echo partner left rect
            // remove skills
            // update tsm
            // mark paladin as divine echo - free

        var mimic = getDivineEchoMimic();

        // Divine Echo does not have a mimic -> search for possible mimic
        if (mimic == null) {
            mimic = getRandomEligableDivineEchoMimic();

            if (mimic == null || mimic == chr) { // No Eligible mimic found.
                return;
            }
        }

        var mimicTsm = mimic.getTemporaryStatManager();
        var mimicPos = mimic.getPosition();
        var rect = chr.getRectAround(SkillData.getSkillInfoById(DIVINE_ECHO).getLastRect());

        // Mimic inside Rect -> give Divine Echo if doesn't have yet
        if (rect.hasPositionInside(mimicPos)) {
            // If selected mimic does not yet have Divine Echo update
            if (!mimicTsm.hasStatBySkillId(DIVINE_ECHO_MIMIC)) {
                var opt = new Option(mimic.getId(), DIVINE_ECHO_MIMIC, 0);
                opt.xOption = chr.getId();
                opt.chr = chr;
                mimicTsm.putCharacterStatValue(DivineEcho, opt);
                mimicTsm.sendSetStatPacket();

                var optPala = tsm.getOption(DivineEcho);
                optPala.nOption = mimic.getId();
                tsm.updateBuff(DivineEcho, optPala);
            }
        // Mimic outside Rect -> remove Divine Echo
        } else {
            mimicTsm.removeStatsBySkill(DIVINE_ECHO_MIMIC);
            var optPala = tsm.getOption(DivineEcho);
            optPala.nOption = chr.getId();
            tsm.updateBuff(DivineEcho, optPala);
        }
    }

    private Char getRandomEligableDivineEchoMimic() {
        if (chr.getParty() == null) {
            return chr;
        }

        var si = SkillData.getSkillInfoById(DIVINE_ECHO);
        Rect rect2 = chr.getRectAround(si.getLastRect());
        List<Char> chrList = chr.getParty().getPartyMembersInField(chr).stream()
                .filter(pChr -> rect2.hasPositionInside(pChr.getPosition())                     // Inside rect
                        && pChr != chr                                                          // not paladin self
                        && !JobConstants.isPaladin(pChr.getJob())                               // not a paladin class
                        && !pChr.getTemporaryStatManager().hasStatBySkillId(DIVINE_ECHO_MIMIC)) // is not currently affected by another Divine Echo
                .collect(Collectors.toList());
        if (chrList.size() <= 0) {
            return chr;
        }

        return Util.getRandomFromCollection(chrList);
    }

    private Char getDivineEchoMimic() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (tsm.hasStat(DivineEcho) && tsm.getOption(DivineEcho).nOption != chr.getId()) {
            return chr.getField().getCharByID(tsm.getOption(DivineEcho).nOption);
        }
        return null;
    }

    private void giveDivineEchoLinkedChrBuffs(Char mimic, TemporaryStatManager tsm, int skillId, int slv) {
        if (mimic == null || mimic == chr) {
            return;
        }
        var effect = Effect.skillUse(skillId, mimic.getLevel(), chr.getSkillLevel(skillId), 0);
        mimic.write(UserPacket.effect(effect));
        mimic.getField().broadcastPacket(UserRemote.effect(mimic.getId(), effect), mimic);
        handleSkill(mimic, tsm, skillId, slv, null, null);
    }

    public static void removeDivineEchoLinkedBuffs(Char linkedChr) {
        TemporaryStatManager linkedTSM = linkedChr.getTemporaryStatManager();
        Map<Integer, CharacterTemporaryStat> linkedSkills = new HashMap<Integer, CharacterTemporaryStat>() {{
            put(WEAPON_BOOSTER_PAGE, IndieBooster);
            put(ELEMENTAL_FORCE, IndiePMdR);
            put(SACROSANCTITY, NotDamaged);
        }};
        for (Map.Entry<Integer, CharacterTemporaryStat> entry : linkedSkills.entrySet()) {
            int skillId = entry.getKey();
            CharacterTemporaryStat cts = entry.getValue();
            if (linkedTSM.hasStatBySkillId(skillId) && linkedTSM.getOption(cts).chr != linkedChr) {
                linkedTSM.removeStatsBySkill(skillId);
            }
        }
    }

    public void handleKeyDownSkill(Char chr, int skillID, InPacket inPacket) {
        super.handleKeyDownSkill(chr, skillID, inPacket);

        TemporaryStatManager tsm = chr.getTemporaryStatManager();

        Skill skill = chr.getSkill(skillID);
        int skillId = 0;
        SkillInfo si = null;
        int slv = 0;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skill.getSkillId());
            slv = skill.getCurrentLevel();
            skillId = skill.getSkillId();
        }

        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (skillId) {
            case GRAND_GUARDIAN:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(GrandGuardian, o1);
                o2.nOption = -(si.getValue(x, slv));
                o2.rOption = skillId;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieHitDamageInclHPR, o2);
                o3.nOption = GRAND_GUARDIAN_NO_KNOCKBACK;
                o3.rOption = skillId;
                o3.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieNoKnockBack, o3);
                break;
        }
    }

    public void increaseGrandGuardianState() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (tsm.hasStat(GrandGuardian)) {
            Option o = new Option();
            o.nOption = GRAND_GUARDIAN_BIG_CROSS;
            o.rOption = GRAND_GUARDIAN;
            o.tOption = (int) tsm.getRemainingTime(GrandGuardian, GRAND_GUARDIAN);
            o.setInMillis(true);
            tsm.putCharacterStatValue(GrandGuardian, o, true);
            tsm.sendSetStatPacket();
        }
    }

    public void decreaseHPByGrandGuardian() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (tsm.hasStat(GrandGuardian)) {
            SkillInfo si = SkillData.getSkillInfoById(GRAND_GUARDIAN);
            int slv = chr.getSkillLevel(GRAND_GUARDIAN);
            int hpConsumed = (int) (((double) chr.getMaxHP() * 1.5) / 100F);    //TODO (int) ((double) (chr.getMaxHP() * si.getValue(t, slv)) / 100F);
            chr.heal(-hpConsumed);
        }
    }

    public void handleSkillRemove(Char chr, int skillID, Map<CharacterTemporaryStat, Option> removedOptions) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        switch (skillID) {

            case GRAND_GUARDIAN:
                tsm.removeStatsBySkill(skillID);
                break;
            case SACROSANCTITY:
                onSacrosanctityCancel();
                break;
        }
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

        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (attackInfo.skillId) {
            case CLOSE_COMBAT:
                o1.nOption = 1;
                o1.rOption = skill.getSkillId();
                o1.tOption = si.getValue(time, slv);
                if (Util.succeedProp(si.getValue(prop, slv))) {
                    for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        if (mob == null || mob.isBoss()) {
                            continue;
                        }
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        mts.addStatOptionsAndBroadcast(chr, MobStat.Stun, o1);
                    }
                }
                break;
            case DIVINE_CHARGE:
            case HEXA_DIVINE_CHARGE:
            case DIVINE_MARK_CHARGE:
                if (si == null) {
                    si = SkillData.getSkillInfoById(attackInfo.skillId);
                    slv = chr.getSkillLevel(DIVINE_CHARGE);
                }
                o1.nOption = 1;
                o1.rOption = attackInfo.skillId;
                o1.tOption = si.getValue(time, slv);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if (Util.succeedProp(si.getValue(prop, slv))) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        if (mob == null || mob.isBoss()) {
                            continue;
                        }
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        mts.addStatOptionsAndBroadcast(chr, MobStat.Seal, o1);
                    }
                }
                break;
            case SMITE_SHIELD:
                o1.nOption = 1;
                o1.rOption = skill.getSkillId();
                o1.tOption = si.getValue(time, slv);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    mts.addStatOptionsAndBroadcast(chr, MobStat.Smite, o1);
                }
                break;
        }
        if (hasHitMobs && attackInfo.bySummonedID == 0 && isHolyPowerSkill(attackInfo.skillId)) {
            giveChargeBuff();
        }
        if (hasHitMobs) {
            sacredBastionExtraAttack(attackInfo.skillId);
            fallingJustice(attackInfo);
            if (attackInfo.skillId == BLAST || attackInfo.skillId == HEXA_BLAST) {
                divineJudgment(attackInfo);
            }
        }
        super.handleAttack(c, attackInfo);
    }

    // Divine Judgment: each Blast hit adds a Divine Brand to the monster; x brands detonate (the client attacks)
    private final Map<Integer, long[]> divineBrands = new HashMap<>(); // mob oid -> {stack, expiry ms}

    private void divineJudgment(AttackInfo attackInfo) {
        if (!chr.hasSkill(DIVINE_JUDGMENT)) {
            return;
        }
        boolean hexa = chr.hasSkill(HEXA_DIVINE_JUDGMENT);
        int passiveId = hexa ? HEXA_DIVINE_JUDGMENT : DIVINE_JUDGMENT;
        int attackId = hexa ? HEXA_DIVINE_JUDGMENT_ATTACK : DIVINE_JUDGMENT_ATTACK;
        SkillInfo si = SkillData.getSkillInfoById(passiveId);
        if (si == null) {
            return;
        }
        int slv = Math.max(1, chr.getSkillLevel(passiveId));
        int maxStack = Math.max(1, si.getValue(x, slv));
        int durationMs = si.getValue(time, slv) * 1000;
        long now = Util.getCurrentTimeLong();
        divineBrands.entrySet().removeIf(e -> e.getValue()[1] <= now
                || !(chr.getField().getLifeByObjectID(e.getKey()) instanceof Mob m) || m.getHp() <= 0);
        List<Integer> explode = new ArrayList<>();
        for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
            Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
            if (mob == null || mob.getHp() <= 0) {
                continue;
            }
            long[] brand = divineBrands.computeIfAbsent(mai.mobId, k -> new long[]{0, now + durationMs});
            brand[0]++;
            if (brand[0] >= maxStack) {
                explode.add(mai.mobId);
                brand[0] = 0;
            }
        }
        Map<Integer, int[]> stacks = new LinkedHashMap<>();
        divineBrands.forEach((oid, b) -> stacks.put(oid, new int[]{(int) b[0], (int) Math.max(0, b[1] - now)}));
        chr.write(UserLocal.setMonsterDebuffMark(passiveId, attackId, maxStack, durationMs, stacks));
        if (!explode.isEmpty()) {
            chr.write(UserLocal.attackMonsterDebuffMark(attackId, maxStack, explode, attackInfo.skillId));
        }
    }

    // Falling Justice: a Paladin skill hit drops the hammer on the boss (or a monster hit), once per cooldown
    private void fallingJustice(AttackInfo attackInfo) {
        int root = attackInfo.skillId / 10000;
        if (!chr.hasSkill(FALLING_JUSTICE) || chr.hasSkillOnCooldown(FALLING_JUSTICE) || attackInfo.bySummonedID != 0
                || attackInfo.skillId == FALLING_JUSTICE_ATTACK
                || (root != 100 && root != 120 && root != 121 && root != 122 && root != 124)) {
            return;
        }
        Mob target = null;
        for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
            Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
            if (mob == null || mob.getHp() <= 0) {
                continue;
            }
            if (target == null || (mob.isBoss() && (!target.isBoss() || mob.getMaxHp() > target.getMaxHp()))) {
                target = mob;
            }
        }
        if (target == null) {
            return;
        }
        chr.write(UserLocal.userBonusAttackRequest(FALLING_JUSTICE, java.util.List.of(target.getObjectId())));
        chr.setSkillCooldown(FALLING_JUSTICE, chr.getSkillLevel(FALLING_JUSTICE)); // hardcode-ok
    }

    // Sacred Bastion: while its tile stands, attacks call the extra strike from the tile (every 0.5 s at most)
    private void sacredBastionExtraAttack(int skillId) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!tsm.hasStat(SacredBastion) || chr.hasSkillOnCooldown(SACRED_BASTION_EXTRA)
                || (skillId >= SACRED_BASTION && skillId <= SACRED_BASTION_AFTER_ATTACK)) {
            return;
        }
        AffectedArea tile = chr.getField().getAffectedAreas().stream()
                .filter(aa -> aa.getCharID() == chr.getId() && aa.getSkillID() == SACRED_BASTION_TILE)
                .findAny().orElse(null);
        if (tile == null) {
            return;
        }
        chr.write(FieldPacket.registerExtraSkill(SACRED_BASTION_EXTRA,
                new ExtraSkillInfo(SACRED_BASTION_EXTRA, tile.getPosition(), chr.isLeft(), 60, 1)));
        chr.addSkillCoolTime(SACRED_BASTION_EXTRA, 500);
    }

    private boolean isHolyPowerSkill(int skillId) {
        if (skillId == BLAST || skillId == HEXA_BLAST) {
            return true;
        }
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        return si != null && si.getElemAttr() != null && si.getElemAttr().contains("h");
    }

    // one Light Charge per Holy Power hit, Greater Vessel of Light overrides the per-charge values
    private void giveChargeBuff() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int skillId = chr.hasSkill(GREATER_VESSEL_OF_LIGHT) ? GREATER_VESSEL_OF_LIGHT : VESSEL_OF_LIGHT;
        int slv = Math.max(1, chr.getSkillLevel(skillId));
        SkillInfo chargeInfo = SkillData.getSkillInfoById(skillId);
        if (chargeInfo == null) {
            return;
        }
        int maxStack = chargeInfo.getValue(z, slv);
        int amount = 1;
        if (tsm.hasStat(ElementalCharge)) {
            amount = Math.min(maxStack, tsm.getOption(ElementalCharge).xOption + 1);
        }
        Option o = new Option();
        o.nOption = maxStack;
        o.rOption = VESSEL_OF_LIGHT;
        o.tOption = chargeInfo.getValue(time, slv) * (SkillChangeConstants.PRE_PORT_CUSTOMS ? SkillChangeConstants.ELEMENTAL_CHARGE_DURATION_MULTI : 1); // [Paladin] Custom Skill Change
        o.xOption = amount;
        o.yOption = amount * chargeInfo.getValue(y, slv); // attack power
        o.uOption = amount * chargeInfo.getValue(u, slv); // status resistance
        o.wOption = amount * chargeInfo.getValue(w, slv); // damage taken
        o.zOption = amount * chargeInfo.getValue(x, slv); // damage, server side only
        tsm.putCharacterStatValue(ElementalCharge, o, true);
        if (chr.hasSkill(HAMMERS_OF_THE_RIGHTEOUS)) {
            Option o1 = new Option();
            o1.nOption = amount;
            o1.rOption = HAMMERS_OF_THE_RIGHTEOUS;
            tsm.putCharacterStatValue(HammerOfTheRighteous, o1);
        }
        tsm.sendSetStatPacket();
    }

    private Skill getFinalAtkSkill() {
        Skill skill = null;
        if (chr.hasSkill(FINAL_ATTACK_PAGE)) {
            skill = chr.getSkill(FINAL_ATTACK_PAGE);
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
            int proc = si.getValue(prop, slv);

            if (Util.succeedProp(proc)) {
                return faSkill.getSkillId();
            }
        }
        return super.getFinalAttackSkill();
    }


    // Skill related methods -------------------------------------------------------------------------------------------

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        super.handleSkill(chr, tsm, skillId, slv, inPacket, skillUseInfo);
        Skill skill = chr.getSkill(skillId);
        SkillInfo si = null;
        var isDivineEchoMimicSkill = SkillConstants.isDivineEchoMimicSkills(skillId);
        if (skill != null || isDivineEchoMimicSkill) {
            si = SkillData.getSkillInfoById(skillId);
        }
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();

        if (tsm.hasStat(DivineEcho) && isDivineEchoMimicSkill) {
            var mimic = getDivineEchoMimic();
            if (mimic != null && mimic != chr) {
                giveDivineEchoLinkedChrBuffs(mimic, mimic.getTemporaryStatManager(), skillId, slv);
            }
        }

        switch (skillId) {
            case HP_RECOVERY:
                hpRecovery(chr);
                break;
            case SACRED_BASTION_TILE: {
                int bastionSlv = chr.getSkillLevel(SACRED_BASTION);
                SkillInfo tileSi = SkillData.getSkillInfoById(SACRED_BASTION_TILE);
                AffectedArea aa = AffectedArea.getPassiveAA(chr, SACRED_BASTION_TILE, bastionSlv);
                aa.setPosition(chr.getPosition());
                aa.setRect(aa.getRectAround(tileSi.getFirstRect()));
                aa.setDelay((short) 3);
                aa.setDuration(tileSi.getValue(time, bastionSlv) * 1000);
                chr.getField().spawnAffectedArea(aa);
                if (!tsm.hasStat(SacredBastion)) {
                    o1.nOption = 1;
                    o1.rOption = SACRED_BASTION;
                    o1.tOption = tileSi.getValue(time, bastionSlv);
                    tsm.putCharacterStatValue(SacredBastion, o1);
                    tsm.sendSetStatPacket();
                }
                break;
            }
            case NOBLE_DEMAND:
                Rect rect = chr.getPosition().getRectAround(si.getRects().get(0));
                if (!chr.isLeft()) {
                    rect = rect.horizontalFlipAround(chr.getPosition().getX());
                }
                o1.nOption = -si.getValue(w, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);

                o2.nOption = -si.getValue(z, slv);
                o2.rOption = skillId;
                o2.tOption = si.getValue(subTime, slv);
                int demandCount = 0;
                int maxDemand = Math.max(1, si.getValue(mobCount, slv));
                for (Life life : chr.getField().getLifesInRect(rect)) {
                    if (demandCount >= maxDemand) {
                        break;
                    }
                    if (life instanceof Mob && ((Mob) life).getHp() > 0) {
                        Mob mob = (Mob) life;
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        if (Util.succeedProp(si.getValue(prop, slv))) {
                            mts.addStatOptions(MobStat.PAD, o1);
                            mts.addStatOptions(MobStat.MAD, o1);
                            mts.addStatOptions(MobStat.PDR, o1);
                            mts.addStatOptions(MobStat.MDR, o1);
                            mts.addStatOptionsAndBroadcast(chr, MobStat.ACC, o2);
                            demandCount++;
                        }
                    }
                }
                break;
            case GUARDIAN:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(NotDamaged, o1);

                Party party = chr.getParty();
                if (party != null) {
                    Field field = chr.getField();
                    rect = chr.getPosition().getRectAround(si.getRects().get(0));
                    if (!chr.isLeft()) {
                        rect = rect.horizontalFlipAround(chr.getPosition().getX());
                    }
                    List<PartyMember> eligblePartyMemberList = field.getPartyMembersInRect(chr, rect).stream().
                            filter(pml -> pml.getChr().getId() != chr.getId() &&
                                    pml.getChr().getHP() <= 0).
                            collect(Collectors.toList());

                    if (eligblePartyMemberList.size() > 0) {
                        Char randomPartyChr = Util.getRandomFromCollection(eligblePartyMemberList).getChr();
                        TemporaryStatManager partyTSM = randomPartyChr.getTemporaryStatManager();
                        randomPartyChr.heal(randomPartyChr.getMaxHP(), false, true);
                        partyTSM.putCharacterStatValue(NotDamaged, o1);
                        partyTSM.sendSetStatPacket();
                        randomPartyChr.write(UserPacket.effect(Effect.skillAffected(skillId, (byte) 1, 0)));
                        randomPartyChr.getField().broadcastPacket(UserRemote.effect(randomPartyChr.getId(), Effect.skillAffected(skillId, (byte) 1, 0)));
                    }
                }

                break;
            case COMBAT_ORDERS:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(CombatOrders, o1);
                break;
            case PARASHOCK_GUARD:
                if (tsm.hasStat(ParashockGuard) && tsm.getOption(ParashockGuard).xOption == tsm.getChr().getId()) {
                    tsm.removeStatsBySkill(skillId);
                } else {
                    putParashockGuard(slv);
                }
                break;
            case ELEMENTAL_FORCE:
                o1.rOption = skillId;
                o1.nOption = si.getValue(indiePMdR, slv);
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePMdR, o1);
                break;
            case SACROSANCTITY:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieNotDamaged, o1, true);
                break;
            case DIVINE_ECHO:
            case HEXA_DIVINE_ECHO:
                if (!tsm.hasStat(DivineEcho)) {
                    var mimic = getRandomEligableDivineEchoMimic();
                    o1.nOption = mimic.getId();
                    o1.rOption = skillId;
                    o1.tOption = si.getValue(time, slv);
                    o1.xOption = chr.getId(); // from ChrId
                    tsm.putCharacterStatValue(DivineEcho, o1);
                    var interval = 500; // ms
                    var executes = o1.tOption / interval;
                    EventManager.stopTimer(divineEchoTimer);
                    divineEchoTimer = EventManager.addFixedRateEvent(this::doDivineEcho, 0, interval, executes);
                }
                break;
            case HAMMERS_OF_THE_RIGHTEOUS_2:
                si = SkillData.getSkillInfoById(HAMMERS_OF_THE_RIGHTEOUS);
                slv = chr.getSkillLevel(HAMMERS_OF_THE_RIGHTEOUS);
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(v, slv);
                tsm.putCharacterStatValue(BigHammerOfTheRighteous, o1);
                chr.addSkillCoolTime(HAMMERS_OF_THE_RIGHTEOUS, si.getValue(cooltime, slv) * 1000);
                break;
            case MIGHTY_MJOLNIR: // ForceAtom Use Skill
                int targetsListSize = inPacket.decodeByte();
                List<Integer> targetList = new ArrayList<>();
                for (int i = 0; i < targetsListSize; i++) {
                    targetList.add(inPacket.decodeInt());
                }
                if (targetList.size() > 0) {
                    createMightMjolnirForceAtom(targetList);
                    decreaseMightyMjolnir();
                }
                break;
        }
        tsm.sendSetStatPacket();
    }

    public void createMightMjolnirForceAtom(List<Integer> targetList) {
        if (!chr.hasSkill(MIGHTY_MJOLNIR) || targetList.size() <= 0) {
            return;
        }

        var fae = ForceAtomEnum.MIGHTY_MJOLNIR;
        Mob mob = (Mob) chr.getField().getLifeByObjectID(targetList.get(0));
        List<ForceAtomInfo> faiList = new ArrayList<>();
        for (int i = 0; i < targetList.size(); i++) {
            var fai = new ForceAtomInfo(chr.getNewForceAtomKey(), fae.getInc(), 45, 6,
                    0, 550, Util.getCurrentTime(), 0, 0,
                    new Position());
            faiList.add(fai);
        }
        chr.createForceAtom(
                new ForceAtom(false, chr.getId(), chr.getId(), fae,
                        true, targetList, MIGHTY_MJOLNIR, faiList, new Rect(), 0, 0,
                        mob.getPosition(), 0, mob.getPosition(), 0)
        );
    }

    public void decreaseMightyMjolnir() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!chr.hasSkill(MIGHTY_MJOLNIR) || !tsm.hasStat(StackOverTimeSkill)) {
            return;
        }

        var count = tsm.getOptByCTSAndSkill(StackOverTimeSkill, MIGHTY_MJOLNIR).nOption;
        if (count > 0) {
            count--;
        }
        updateMightyMjolnir(count);
    }

    public void increaseMightyMjolnir() {
        if (!chr.hasSkill(MIGHTY_MJOLNIR)) {
            return;
        }

        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int count = 1;
        if (tsm.hasStat(StackOverTimeSkill)) {
            count = tsm.getOption(StackOverTimeSkill).nOption;
            if (count < 2) {
                count++;
            }
        }
        updateMightyMjolnir(count);
    }

    public void updateMightyMjolnir(int count) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option();
        o.nOption = Math.min(Math.max(0, count), 2);
        o.rOption = MIGHTY_MJOLNIR;
        tsm.putCharacterStatValue(StackOverTimeSkill, o);
        tsm.sendSetStatPacket();
    }

    private void hpRecovery(Char pChr) {
        TemporaryStatManager tsm = pChr.getTemporaryStatManager();
        Option o = new Option();
        if (chr.hasSkill(HP_RECOVERY)) {
            Skill skill = chr.getSkill(HP_RECOVERY);
            int slv = skill.getCurrentLevel();
            SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
            int recovery = si.getValue(x, slv);
            int amount = 10;

            if (tsm.hasStat(Restoration)) {
                amount = tsm.getOption(Restoration).nOption;
                if (amount < 300) {
                    amount = amount + 10;
                }
            }

            o.nOption = amount;
            o.rOption = skill.getSkillId();
            o.tOption = si.getValue(time, slv);
            int heal = (recovery + 10) - amount > 10 ? (recovery + 10) - amount : 10;
            pChr.heal(pChr.getHPPerc(heal));
            tsm.putCharacterStatValue(Restoration, o);
            tsm.sendSetStatPacket();
        }
    }

    public int alterCooldownSkill(int skillId, int slv, AttackInfo attackInfo, SkillUseInfo sui, SkillUseSource source) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Skill skill = chr.getSkill(skillId);
        if (skill != null) {
            SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());

            switch (skillId) {
                case HAMMERS_OF_THE_RIGHTEOUS:
                    if (tsm.hasStat(HammerOfTheRighteous)) {
                        return 0;
                    }
            }
        }
        return super.alterCooldownSkill(skillId, slv, attackInfo, sui, source);
    }

    public void handleRemoveCTS(CharacterTemporaryStat cts, Option o) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (cts == DivineEcho) {
            var mimic = chr.getField().getCharByID(o.nOption);
            if (mimic != null && mimic.getId() != chr.getId()) {
                mimic.getTemporaryStatManager().removeStatsBySkill(DIVINE_ECHO_MIMIC);
            }
            EventManager.stopTimer(divineEchoTimer);
        }
        if (cts == BlessingArmor) {
            SkillInfo si = SkillData.getSkillInfoById(DIVINE_SHIELD);
            int slv = chr.getSkillLevel(DIVINE_SHIELD);
            chr.addSkillCoolTime(DIVINE_SHIELD, si.getValue(cooltime, slv) * 1000);

        } else if (cts == ElementalCharge) {
            Option o1 = new Option();
            o1.nOption = 0;
            o1.rOption = HAMMERS_OF_THE_RIGHTEOUS;
            tsm.putCharacterStatValue(HammerOfTheRighteous, o1);
            tsm.sendSetStatPacket();

            tsm.removeStat(HammerOfTheRighteous);
            tsm.removeStat(BigHammerOfTheRighteous);
        }
        super.handleRemoveCTS(cts, o);
    }

    @Override
    public void handleKeyDownSkillCost(int skillId) {
        super.handleKeyDownSkillCost(skillId);

        switch (skillId) {
            case GRAND_GUARDIAN:
                if (chr.hasSkill(GRAND_GUARDIAN)) {
                    decreaseHPByGrandGuardian();
                }
                break;
        }
    }

    // Hit related methods ---------------------------------------------------------------------------------------------

    @Override
    public void handleHit(Client c, InPacket inPacket, HitInfo hitInfo) {

        // Paladin - Divine Shield
        doDivineShield();

        // Paladin - Shield Mastery
        if (chr.hasSkill(SHIELD_MASTERY) && hitInfo.fixDamR == 0) { // If Wearing a Shield
            Equip equip = (Equip) chr.getEquippedItemByBodyPart(BodyPart.Shield);
            if (equip != null && ItemConstants.isShield(equip.getItemId()) && hitInfo.hpDamage == 0 && hitInfo.mpDamage == 0) {
                // Guarded
                int mobID = hitInfo.mobID;
                Mob mob = (Mob) chr.getField().getLifeByObjectID(mobID);
                if (mob != null) {
                    Skill skill = chr.getSkill(SHIELD_MASTERY);
                    int slv = skill.getCurrentLevel();
                    SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
                    int proc = si.getValue(subProp, slv);
                    Option o = new Option();
                    o.nOption = 1;
                    o.rOption = skill.getSkillId();
                    o.tOption = SHIELD_MASTERY_STUN_SEC;
                    if (Util.succeedProp(proc) && !mob.isBoss()) {
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        mts.addStatOptionsAndBroadcast(chr, MobStat.Stun, o);
                    }
                }
            }
        }

        super.handleHit(c, inPacket, hitInfo);
    }

    private void doDivineShield() {
        Skill skill = chr.getSkill(DIVINE_SHIELD);
        if (skill == null || chr.hasSkillOnCooldown(skill.getSkillId())) {
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();

        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        int slv = skill.getCurrentLevel();
        int shieldprop = si.getValue(prop, slv);

        if (tsm.hasStat(BlessingArmor)) {
            decrementDivineShieldCount(tsm);
        } else if (Util.succeedProp(shieldprop)) {
            applyDivineShield(tsm, si, slv);
        }
    }

    private void applyDivineShield(TemporaryStatManager tsm, SkillInfo si, int slv) {
        var tOpt = si.getValue(time, slv);
        var o1 = new Option(si.getValue(x, slv), si.getSkillId(), tOpt);
        o1.yOption = si.getValue(y, slv);
        tsm.putCharacterStatValue(BlessingArmor, o1, true);
        var o2 = new Option(si.getValue(epad, slv), si.getSkillId() + 1000, tOpt);
        tsm.putCharacterStatValue(BlessingArmorIncPAD, o2, true);
        tsm.sendSetStatPacket();
    }

    private void decrementDivineShieldCount(TemporaryStatManager tsm) {
        var oldOpt = tsm.getOption(BlessingArmor);
        if (oldOpt.nOption > 1) {
            oldOpt.nOption--;
            tsm.updateBuff(BlessingArmor, oldOpt);
            var o = tsm.getOption(BlessingArmorIncPAD);
            tsm.updateBuff(BlessingArmorIncPAD, o);
        } else {
            tsm.removeStat(BlessingArmor);
            tsm.removeStat(BlessingArmorIncPAD);
        }
    }

    @Override
    public Long getJobBaseStat(BaseStat baseStat) {

        var skill = chr.getSkill(SHIELD_MASTERY);
        if (skill != null) {
            var si = SkillData.getSkillInfoById(skill.getSkillId());
            var slv = skill.getCurrentLevel();

            var item = chr.getEquippedItemByBodyPart(BodyPart.Shield);

            if (item != null && (ItemConstants.isShield(item.getItemId()) || ItemConstants.isSecondary(item.getItemId()))) {
                var statMap = new HashMap<BaseStat, Long>();
                statMap.put(BaseStat.ter, (long) si.getValue(terR, slv));
                statMap.put(BaseStat.asr, (long) si.getValue(asrR, slv));
                statMap.put(BaseStat.pdd, (long) si.getValue(x, slv));
                statMap.put(BaseStat.pad, (long) si.getValue(y, slv));

                return statMap.getOrDefault(baseStat, 0L);
            }
        }

        return super.getJobBaseStat(baseStat);
    }

    private void removeDivineEchoMimic() {
        var field = chr.getField();
        for (var fChr : field.getChars()) {
            var tsm = fChr.getTemporaryStatManager();
            if (tsm != null && tsm.hasStatBySkillId(DIVINE_ECHO_MIMIC)) {
                var opt = tsm.getOptByCTSAndSkill(DivineEcho, DIVINE_ECHO_MIMIC);
                if (opt != null && opt.xOption == chr.getId()) {
                    tsm.removeStat(DivineEcho);
                }
            }
        }
    }

    @Override
    public void onLeaveParty() {
        var tsm = chr.getTemporaryStatManager();
        if (tsm.hasStatBySkillId(DIVINE_ECHO)) {
            var mimic = chr.getField().getCharByID(tsm.getOption(DivineEcho).nOption);
            if (mimic != null && mimic.getId() != chr.getId()) {
                mimic.getTemporaryStatManager().removeStatsBySkill(DIVINE_ECHO_MIMIC);
            }
            tsm.getOption(DivineEcho).nOption = chr.getId(); // mark divine echo as inactive
        }

        super.onLeaveParty();
    }

    // v271 Parashock Guard is passive: always on; Attack Power = dummyStr (2x), which SkillInfo does not read
    private void putParashockGuard(int slv) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        SkillInfo si = SkillData.getSkillInfoById(PARASHOCK_GUARD);
        if (si == null || slv <= 0) {
            return;
        }
        Option o1 = new Option(slv, PARASHOCK_GUARD, 0);
        o1.xOption = chr.getId();
        o1.bOption = 0; // owner
        tsm.putCharacterStatValue(ParashockGuard, o1);
        int pad = si.getValue(indiePad, slv) > 0 ? si.getValue(indiePad, slv) : 2 * slv;
        tsm.putCharacterStatValue(IndiePAD, new Option(pad, PARASHOCK_GUARD, 0));
        tsm.putCharacterStatValue(IndieMAD, new Option(pad, PARASHOCK_GUARD, 0));
        tsm.sendSetStatPacket();
    }

    @Override
    public void onWarp(Field oldField, Field newField) {
        if (newField != null && chr.hasSkill(PARASHOCK_GUARD)
                && chr.getTemporaryStatManager().getOptByCTSAndSkill(ParashockGuard, PARASHOCK_GUARD) == null) {
            putParashockGuard(chr.getSkillLevel(PARASHOCK_GUARD));
        }
        var tsm = chr.getTemporaryStatManager();
        if (tsm.hasStatBySkillId(DIVINE_ECHO)) {
            var mimic = oldField.getCharByID(tsm.getOption(DivineEcho).nOption);
            if (mimic != null && mimic.getId() != chr.getId()) {
                mimic.getTemporaryStatManager().removeStatsBySkill(DIVINE_ECHO_MIMIC);
            }
            tsm.getOption(DivineEcho).nOption = chr.getId(); // mark divine echo as inactive
        }

        super.onWarp(oldField, newField);
    }

    @Override
    public void stopTimers() {
        EventManager.stopTimer(divineEchoTimer);

        super.stopTimers();
    }

    public void onSacrosanctityCancel() {
        var tsm = chr.getTemporaryStatManager();
        var currentOpt = tsm.getOptByCTSAndSkill(IndieNotDamaged, SACROSANCTITY);
        var remainingTime = currentOpt.getRemainingTime();

        if (remainingTime > 0) {
            chr.reduceSkillCoolTime(SACROSANCTITY, (int) remainingTime);
        }
    }

}
