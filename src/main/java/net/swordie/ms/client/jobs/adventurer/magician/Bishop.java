package net.swordie.ms.client.jobs.adventurer.magician;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.debuff.DebuffUtil;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.common.DecentSkillHandler;
import net.swordie.ms.client.party.Party;
import net.swordie.ms.client.party.PartyMember;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.Effect;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.util.Util;
import net.swordie.ms.client.character.skills.TownPortal;
import net.swordie.ms.util.Position;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.client.character.skills.atom.secondatom.SecondAtom;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.MobPool;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.AssistType;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.enums.MoveAbility;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.life.AffectedArea;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Rect;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

public class Bishop extends Magician {
    public static final int TELEPORT_BOOST = 2311016; // on/off: longer teleport
    public static final int BLESS = 2301004;
    public static final int HEAL = 2301002;
    public static final int MP_EATER_BISHOP = 2300000;
    public static final int MAGIC_BOOSTER_BISH = 2301008;
    public static final int BLESSED_ENSEMBLE = 2300009;


    public static final int HOLY_SYMBOL = 2311003;
    public static final int DIVINE_PROTECTION = 2311012;
    public static final int HOLY_MAGIC_SHELL = 2311009;
    public static final int HOLY_MAGIC_SHELL_COOLTIME = 2310013;
    public static final int DISPEL = 2311001;
    public static final int SHINING_RAY = 2311004;
    public static final int TELEPORT_MASTERY_BISH = 2311007;
    public static final int HOLY_FOUNTAIN = 2311011;
    public static final int MYSTIC_DOOR = 2311002;


    public static final int HOLY_MAGIC_SHELL_PERSIST = 2320044;
    public static final int HOLY_MAGIC_SHELL_EXTRA_GUARD = 2320043;
    public static final int HOLY_SYMBOL_EXPERIENCE = 2320046;
    public static final int HOLY_SYMBOL_PREPARATION = 2320047;
    public static final int HOLY_SYMBOL_ITEM_DROP = 2320048;
    public static final int ADV_BLESSING_FEROCITY = 2320049;
    public static final int ADV_BLESSING_BOSS_RUSH = 2320050;
    public static final int ADV_BLESSING_EXTRA_POINT = 2320051;
    public static final int RESURRECTION = 2321006;
    public static final int ADV_BLESSING = 2321005;
    public static final int BAHAMUT = 2321003;
    public static final int INFINITY_BISH = 2320004; // v271 passive (v265 2321004)
    public static final int BLESSED_HARMONY = 2320013;
    public static final int MAPLE_WARRIOR_BISH = 2321000;
    public static final int GENESIS = 2321008;
    public static final int BIG_BANG = 2321001; // TODO  Stacking Debuff
    public static final int ARCANE_AIM_BISH = 2320011;
    public static final int ANGEL_RAY = 2321007;
    public static final int HEROS_WILL_BISH = 2321009;
    public static final int EPIC_ADVENTURE_BISH = 2321053;
    public static final int RIGHTEOUSLY_INDIGNANT = 2321054;
    public static final int HEAVENS_DOOR = 2321052;
    public static final int HEAVENS_DOOR_SKILL_USE = 2321055;


    // V skills
    public static final int BENEDICTION = 400021003;
    public static final int ANGEL_OF_BALANCE_AVENGE = 400021033;
    public static final int ANGEL_OF_BALANCE_BENEVOLENCE = 400021032;
    public static final int PEACEMAKER_TRAVEL = 400021070;
    public static final int PEACEMAKER_EXPLOSION = 400021077;
    public static final int DIVINE_PUNIHSMENT = 400021086;
    public static final int HEXA_ANGEL_RAY = 2341000; // HEXA Angel Ray
    public static final int ANGELIC_WRATH = 2301010;
    public static final int FOUNTAIN_OF_VENGEANCE = 2311014;
    public static final int TRIUMPH_FEATHER = 2311015;
    public static final int TRIUMPH_FEATHER_ATOM = 2311017;
    public static final int HOLY_WATER = 2321015;
    public static final int BLOOD_OF_THE_DIVINE = 2321016;
    public static final int HEXA_TRIUMPH_FEATHER = 2341004;
    public static final int HEXA_TRIUMPH_FEATHER_ATOM = 2341005;
    public static final int HEXA_FOUNTAIN_OF_VENGEANCE = 2341006;
    public static final int HEXA_ANGELIC_WRATH = 2341007;
    public static final int HEXA_ANGELIC_WRATH_RELIC = 2341008;
    public static final int HEXA_ANGEL_RAY_BONUS = 2341001; // HEXA Angel Ray, every 12th hit
    public static final int HEXA_BIG_BANG = 2341002; // HEXA Big Bang
    public static final int HEXA_BIG_BANG_HOLY_EXPLOSION = 2341003; // hidden, holds the cooltime
    public static final int HEXA_BAHAMUT = 2341013; // HEXA Bahamut
    public static final int HEXA_GENESIS = 2341009; // HEXA Genesis
    public static final int HEXA_GENESIS_FA = 2341010; // HEXA Genesis, final attack
    public static final int HEXA_HEAVENS_DOOR = 2341011;
    public static final int HEXA_BENEDICTION = 500061002;
    public static final int HOLY_ADVENT = 2341500;
    public static final int HOLY_ADVENT_M = 2341501;
    public static final int HOLY_ADVENT_L = 2341502;
    public static final int HOLY_ADVENT_R = 2341503;
    public static final int HOLY_ADVENT_AFTER = 2341504;




    public Bishop(Char chr) {
        super(chr);
    }


    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isBishop(id);
    }

    public void giveAngelOfBalanceSummonBuff() {
        if (chr.hasSkill(ANGEL_OF_BALANCE_BENEVOLENCE)) {
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            Option o = new Option();
            Skill skill = chr.getSkill(ANGEL_OF_BALANCE_BENEVOLENCE);
            SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
            int slv = skill.getCurrentLevel();
            chr.heal((si.getValue(y, slv) * chr.getMaxHP()) / 100); // heal 15% HP
            int buffnValue = si.getValue(w, slv) + (chr.getStat(Stat.inte) / si.getValue(attackCount, slv));
            o.rOption = ANGEL_OF_BALANCE_AVENGE;
            o.nOption = buffnValue > 100 ? 100 : buffnValue;
            o.tOption = si.getValue(subTime, slv);
            tsm.putCharacterStatValue(IndieDamR, o);
            tsm.sendSetStatPacket();
        }
    }

    private void changeBlessedCount() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option();
        if (getBlessedSkill() != null) {
            Skill skill = getBlessedSkill();
            SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
            int slv = skill.getCurrentLevel();

            int amount = tsm.getCurrentStats().size() + 1;
            if (amount > 1) {
                o.nOption = si.getValue(x, slv) * (amount > 6 ? 6 : amount);
                o.rOption = BLESSED_ENSEMBLE;
                tsm.putCharacterStatValue(BlessEnsenble, o);
                tsm.sendSetStatPacket();
            } else {
                tsm.removeStatsBySkill(BLESSED_ENSEMBLE);
            }
        }
    }

    private Skill getBlessedSkill() {
        Skill skill = null;
        if (chr.hasSkill(BLESSED_ENSEMBLE)) {
            skill = chr.getSkill(BLESSED_ENSEMBLE);
        }
        if (chr.hasSkill(BLESSED_HARMONY)) {
            skill = chr.getSkill(BLESSED_HARMONY);
        }
        return skill;
    }

    private int changeBishopHealingBuffs(int skillID) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Skill skill = chr.getSkill(skillID);
        int slv = skill.getCurrentLevel();
        SkillInfo si = SkillData.getSkillInfoById(skillID);
        int rate;
        int maxHP = chr.getMaxHP();
        int healrate = 0;
        switch (skillID) {
            case HOLY_MAGIC_SHELL:
                rate = si.getValue(z, slv);
                healrate = (int) (maxHP / ((double) 100 / rate));
                break;
            case ANGEL_RAY:
            case HEXA_ANGEL_RAY:
                rate = si.getValue(hp, slv);
                healrate = (int) (maxHP / ((double) 100 / rate));
                break;
        }
        if (tsm.hasStat(VengeanceOfAngel)) {
            SkillInfo hsi = SkillData.getSkillInfoById(RIGHTEOUSLY_INDIGNANT);
            healrate = (int) (healrate / ((double) 100 / (hsi.getValue(hp, 1))));
        }
        return healrate;
    }

    public void giveBenedictionBuff() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!chr.hasSkill(BENEDICTION) || !tsm.hasStat(Benediction) || !JobConstants.isBishop(chr.getJob())) {
            return;
        }
        Skill skill = chr.getSkill(chr.hasSkill(HEXA_BENEDICTION) ? HEXA_BENEDICTION : BENEDICTION);
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        int slv = skill.getCurrentLevel();
        Rect rect = chr.getRectAround(si.getFirstRect());

        int userINT = chr.getTotalBasicStats().getOrDefault(BaseStat.inte, 0);
        int bonusDamage = userINT / si.getValue(q2, slv);
        int bonusDamageCap = si.getValue(w, slv);
        int bonusRecovery = userINT / si.getValue(y, slv);
        int bonusRecoveryCap = si.getValue(z, slv);
        int bonusAttSpeed = userINT / si.getValue(u, slv);
        int bonusAttSpeedCap = 3;

        List<Char> chrList = new ArrayList<>();
        if (chr.getParty() == null) {
            chrList.add(chr);
        } else {
            chrList.addAll(chr.getParty().getPartyMembersInField(chr).stream().filter(c -> rect.hasPositionInside(c.getPosition())).collect(Collectors.toList()));
        }
        Option o1 = new Option();
        Option o2 = new Option();

        o1.rOption = skill.getSkillId();
        o1.nOption = si.getValue(q, slv) + (bonusDamage > bonusDamageCap ? bonusDamageCap : bonusDamage);
        o1.tOption = 3;

        o2.rOption = skill.getSkillId();
        o2.nOption = -(bonusAttSpeed > bonusAttSpeedCap ? bonusAttSpeedCap : bonusAttSpeed);
        o2.tOption = 3;

        for (Char pChr : chrList) {
            TemporaryStatManager pTSM = pChr.getTemporaryStatManager();

            pTSM.putCharacterStatValue(IndiePMdR, o1);
            pTSM.putCharacterStatValue(IndieBooster, o2);

            int HPRecovery = (int) (((si.getValue(x, slv) + (bonusRecovery > bonusRecoveryCap ? bonusRecoveryCap : bonusRecovery)) * pChr.getMaxHP()) / 100D);
            int MPRecovery = (int) (((si.getValue(x, slv) + (bonusRecovery > bonusRecoveryCap ? bonusRecoveryCap : bonusRecovery)) * pChr.getMaxMP()) / 100D);
            pChr.heal(HPRecovery);
            pChr.healMP(MPRecovery);
            pTSM.removeAllDebuffs();
            pTSM.sendSetStatPacket();
            if (pChr != chr) {
                pChr.write(UserPacket.effect(Effect.skillAffected(skill.getSkillId(), slv, 0)));
                pChr.getField().broadcastPacket(UserRemote.effect(pChr.getId(), Effect.skillAffected(skill.getSkillId(), slv, 0)));
            }
        }
    }


    // HEXA Angel Ray: each cast with a hit adds a stack; at 12 the next one also fires the bonus strike
    private void addSixthAngelsRay(AttackInfo attackInfo) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int value = tsm.hasStat(SixthAngelsRay) ? tsm.getOption(SixthAngelsRay).nOption + 1 : 1;
        if (value > 12) {
            List<Integer> mobs = new ArrayList<>();
            for (var mai : attackInfo.mobAttackInfo) {
                if (mai.mob != null && mai.mob.getHp() > 0) {
                    mobs.add(mai.mob.getObjectId());
                    chr.getField().broadcastPacket(MobPool.specialEffectBySkill(mai.mob, HEXA_ANGEL_RAY_BONUS, chr.getId(), (short) 0));
                }
            }
            chr.write(UserLocal.userBonusAttackRequest(HEXA_ANGEL_RAY_BONUS, mobs));
            value = 0;
        }
        Option o = new Option();
        o.nOption = value;
        o.rOption = HEXA_ANGEL_RAY;
        tsm.putCharacterStatValue(SixthAngelsRay, o);
        tsm.sendSetStatPacket();
    }

    // HEXA Heaven's Door is an attack: landing it gives the party one revive
    private void giveHeavensDoorRevive(int skillId, int slv) {
        Option o = new Option();
        o.nOption = 1;
        o.rOption = skillId;
        List<Char> chrs = new ArrayList<>();
        if (chr.getParty() == null) {
            chrs.add(chr);
        } else {
            chrs.addAll(chr.getParty().getPartyMembersInField(chr));
        }
        for (Char pChr : chrs) {
            TemporaryStatManager pTsm = pChr.getTemporaryStatManager();
            pTsm.putCharacterStatValue(ReviveOnce, o.deepCopy());
            pTsm.sendSetStatPacket();
            if (pChr != chr) {
                pChr.write(UserPacket.effect(Effect.skillAffected(skillId, slv, 0)));
                pChr.getField().broadcastPacket(UserRemote.effect(pChr.getId(), Effect.skillAffected(skillId, slv, 0)), pChr);
            }
        }
    }

    // Holy Advent: the follow-up hit brings the archangel and its two companions for the buff's duration
    private void spawnHolyAdvent() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int slv = chr.getSkillLevel(HOLY_ADVENT);
        SkillInfo si = SkillData.getSkillInfoById(HOLY_ADVENT);
        Option o = new Option();
        o.nOption = 1;
        o.rOption = HOLY_ADVENT;
        o.tOption = si.getValue(time, slv);
        tsm.putCharacterStatValue(HolyAdvent, o);
        tsm.sendSetStatPacket();
        Field field = chr.getField();
        int[] ids = {HOLY_ADVENT_M, HOLY_ADVENT_L, HOLY_ADVENT_R};
        int[] dx = {0, -400, 400};
        for (int i = 0; i < ids.length; i++) {
            Summon summon = Summon.getSummonByNoCTS(chr, ids[i], slv);
            summon.setFlyMob(true);
            summon.setMoveAbility(MoveAbility.Stop);
            summon.setAssistType(AssistType.Attack);
            summon.setPosition(chr.getPosition().deepCopy().add(new Position(dx[i], 0)));
            var fh = field.findFootHoldBelow(summon.getPosition());
            summon.setCurFoothold(fh != null ? (short) fh.getId() : 0);
            field.spawnSummonAndRemoveOld(summon);
        }
    }

    // Holy Water: every u Angel Ray hits make one (up to w)
    private int angelRayHits;

    private void addHolyWater(int hits) {
        SkillInfo si = SkillData.getSkillInfoById(HOLY_WATER);
        int slv = chr.getSkillLevel(HOLY_WATER);
        if (si == null || slv <= 0) {
            return;
        }
        angelRayHits += hits;
        int need = Math.max(1, si.getValue(u, slv));
        if (angelRayHits < need) {
            return;
        }
        angelRayHits %= need;
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int cur = tsm.hasStat(HolyWater) ? tsm.getOption(HolyWater).nOption : 0;
        int next = Math.min(Math.max(1, si.getValue(w, slv)), cur + 1);
        if (next != cur) {
            setHolyWater(next);
        }
    }

    private void setHolyWater(int count) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option(count, HOLY_WATER, 0);
        o.xOption = count;
        tsm.putCharacterStatValue(HolyWater, o);
        tsm.sendSetStatPacket();
    }

    // Triumph Feather: while it's on, a hit sends bulletCount feathers, every w seconds at most
    private void triumphFeathers(AttackInfo attackInfo) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int atomId = chr.hasSkill(HEXA_TRIUMPH_FEATHER) ? HEXA_TRIUMPH_FEATHER_ATOM : TRIUMPH_FEATHER_ATOM;
        int buffId = atomId == HEXA_TRIUMPH_FEATHER_ATOM ? HEXA_TRIUMPH_FEATHER : TRIUMPH_FEATHER;
        if (!tsm.hasStat(TriumphFeather) || chr.hasSkillOnCooldown(atomId)
                || attackInfo.skillId == TRIUMPH_FEATHER_ATOM || attackInfo.skillId == HEXA_TRIUMPH_FEATHER_ATOM) {
            return;
        }
        SkillInfo atomSi = SkillData.getSkillInfoById(atomId);
        SkillInfo buffSi = SkillData.getSkillInfoById(buffId);
        if (atomSi == null || buffSi == null || atomSi.getSecondAtomInfos().isEmpty()) {
            return;
        }
        int slv = Math.max(1, chr.getSkillLevel(buffId));
        List<Integer> targets = attackInfo.getMobIds();
        int count = Math.max(1, buffSi.getValue(bulletCount, slv));
        List<SecondAtom> atoms = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            var sai = atomSi.getSecondAtomInfos().get(Math.min(i, atomSi.getSecondAtomInfos().size() - 1));
            SecondAtom atom = SecondAtom.createFromInfo(chr, atomId, sai);
            atom.setKey(i);
            atom.setTargetId(targets.isEmpty() ? 0 : targets.get(i % targets.size()));
            atom.setPosition(chr.getPosition().deepCopy().add(new Position(Util.getRandom(-200, 200), -Util.getRandom(100, 200))));
            atoms.add(atom);
        }
        chr.spawnSecondAtoms(atoms);
        chr.addSkillCoolTime(atomId, Math.max(1, buffSi.getValue(w, slv)) * 1000);
    }

    private void spawnAngelicWrathRelic(AttackInfo attackInfo, int slv) {
        Mob target = null;
        for (var mai : attackInfo.mobAttackInfo) {
            if (mai.mob != null && mai.mob.getHp() > 0) {
                target = mai.mob;
                break;
            }
        }
        if (target == null) {
            return;
        }
        Field field = chr.getField();
        Summon relic = Summon.getSummonByNoCTS(chr, HEXA_ANGELIC_WRATH_RELIC, slv);
        relic.setMoveAbility(MoveAbility.Stop);
        relic.setAssistType(AssistType.Attack);
        relic.setPosition(target.getPosition().deepCopy());
        var fh = field.findFootHoldBelow(relic.getPosition());
        relic.setCurFoothold(fh != null ? (short) fh.getId() : 0);
        field.spawnSummonAndRemoveOld(relic);
    }

    @Override
    public int getFinalAttackSkill() {
        if (chr.hasSkill(HEXA_GENESIS)) {
            SkillInfo si = SkillData.getSkillInfoById(HEXA_GENESIS);
            if (Util.succeedProp(si.getValue(prop, chr.getSkillLevel(HEXA_GENESIS)))) {
                return HEXA_GENESIS_FA;
            }
        }
        return super.getFinalAttackSkill();
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
        Option o4 = new Option();
        switch (attackInfo.skillId) {
            // Big Bang has no defense debuff in the data any more (no x/w on 2321001; w on the HEXA one is damage)
            case BAHAMUT:
            case HEXA_BAHAMUT:
            case ANGEL_OF_BALANCE_AVENGE:
                if (attackInfo.skillId == ANGEL_OF_BALANCE_AVENGE) {
                    si = SkillData.getSkillInfoById(ANGEL_OF_BALANCE_BENEVOLENCE);
                    slv = (byte) chr.getSkillLevel(ANGEL_OF_BALANCE_BENEVOLENCE);
                }
                o1.nOption = attackInfo.skillId == BAHAMUT ? 25 : si.getValue(q2, slv);
                o1.rOption = attackInfo.skillId;
                o1.tOption = attackInfo.skillId == BAHAMUT ? si.getValue(subTime, slv) : si.getValue(q, slv) * 1000;
                o1.cOption = chr.getId();

                DebuffUtil.applyDebuffOnMobs(chr, MobStat.BahamutLightElemAddDam, o1, attackInfo.getMobIds());
                break;
            // Heal no longer marks mobs for party damage (no x/time in the data)
            case ANGEL_RAY:
            case HEXA_ANGEL_RAY:
                chr.heal(changeBishopHealingBuffs(ANGEL_RAY));
                if (attackInfo.skillId == HEXA_ANGEL_RAY && hasHitMobs) {
                    addSixthAngelsRay(attackInfo);
                }
                if (hasHitMobs) {
                    addHolyWater(attackInfo.mobAttackInfo.size());
                }
                break;
            case ANGELIC_WRATH:
            case HEXA_ANGELIC_WRATH:
            case HEXA_ANGELIC_WRATH_RELIC: {
                // Defense down (x is negative); the HEXA cast also leaves a relic that keeps applying it
                int ownId = attackInfo.skillId == ANGELIC_WRATH ? ANGELIC_WRATH : HEXA_ANGELIC_WRATH;
                SkillInfo awSi = SkillData.getSkillInfoById(attackInfo.skillId);
                int awSlv = Math.max(1, chr.getSkillLevel(ownId));
                if (awSi == null || !hasHitMobs) {
                    break;
                }
                o1.nOption = awSi.getValue(x, awSlv);
                o1.rOption = ownId;
                o1.tOption = attackInfo.skillId == HEXA_ANGELIC_WRATH_RELIC ? awSi.getValue(subTime, awSlv) : awSi.getValue(time, awSlv);
                DebuffUtil.applyDebuffOnMobs(chr, MobStat.PDR, o1, attackInfo.getMobIds());
                if (attackInfo.skillId == HEXA_ANGELIC_WRATH) {
                    spawnAngelicWrathRelic(attackInfo, awSlv);
                }
                break;
            }
            case HEXA_HEAVENS_DOOR:
                giveHeavensDoorRevive(skillID, slv);
                break;
            case HOLY_ADVENT_AFTER:
                if (!tsm.hasStat(HolyAdvent)) {
                    spawnHolyAdvent();
                }
                break;
            case SHINING_RAY:
            case TELEPORT_MASTERY_BISH:
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                DebuffUtil.applyDebuffOnMobs(chr, MobStat.Stun, o1, attackInfo.getMobIds(),
                        new net.swordie.ms.client.character.skills.debuff.DebuffInfo(true,
                                si.getValue(attackInfo.skillId == TELEPORT_MASTERY_BISH ? subProp : prop, slv)));
                break;
            case GENESIS:
            case HEXA_GENESIS:
                o1.nOption = 1;
                o1.rOption = chr.hasSkill(HEXA_BIG_BANG) ? HEXA_BIG_BANG : BIG_BANG;
                o1.tOption = si.getValue(cooltime, slv);
                tsm.putCharacterStatValue(KeyDownTimeIgnore, o1);
                tsm.sendSetStatPacket();
                break;
        }
        if (hasHitMobs) {
            triumphFeathers(attackInfo);
        }
        super.handleAttack(c, attackInfo);
    }

    public void givePeacemakerBuffs(int skillId, Rect rect, int contactCount) {
        if (!chr.hasSkill(PEACEMAKER_TRAVEL)) {
            return;
        }
        Field field = chr.getField();
        Skill skill = chr.getSkill(PEACEMAKER_TRAVEL);
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        List<Char> contactList = new ArrayList<>();
        int slv = skill.getCurrentLevel();
        int maxContactCount = si.getValue(w, slv);

        if (chr.getParty() != null) {
            contactList = field.getCharsInRect(rect).stream()
                    .filter(c -> c.getPartyID() == chr.getPartyID()
                            && c.getFieldID() == chr.getFieldID())
                    .collect(Collectors.toList());
        } else {
            contactList.add(chr);
        }
        switch (skillId) {
            case PEACEMAKER_TRAVEL:
                if (contactCount < maxContactCount) {
                    for (Char partyChr : contactList) {
                        partyChr.heal((int) ((double) partyChr.getMaxHP() * si.getValue(hp, slv) / 100D));
                        partyChr.write(UserPacket.effect(Effect.skillAffected(skillId, slv, 0)));
                        partyChr.getField().broadcastPacket(UserRemote.effect(partyChr.getId(), Effect.skillAffected(skillId, slv, 0)));
                    }
                }
                break;
            case PEACEMAKER_EXPLOSION:
                int remainingContactCount = maxContactCount - contactCount;
                for (Char partyChr : contactList) {
                    TemporaryStatManager partyTSM = partyChr.getTemporaryStatManager();
                    Option o = new Option();
                    o.rOption = skillId;
                    o.nOption = si.getValue(q2, slv) + (remainingContactCount * si.getValue(w2, slv));
                    o.tOption = si.getValue(time, slv);
                    partyTSM.putCharacterStatValue(IndieDamR, o);
                    partyTSM.sendSetStatPacket();
                }
                break;
        }
    }

    // Skill related methods -------------------------------------------------------------------------------------------

    // Holy Water: uses all held waters, one chalice each (positions from the client when it sends them)
    private void useHolyWater(SkillInfo si, int slv, InPacket inPacket) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int held = tsm.hasStat(HolyWater) ? tsm.getOption(HolyWater).nOption : 0;
        if (si == null || held <= 0) {
            return;
        }
        List<Position> spots = new ArrayList<>();
        if (inPacket != null && inPacket.getUnreadAmount() >= 2) {
            int n = inPacket.decodeShort();
            for (int i = 0; i < Math.min(n, held) && inPacket.getUnreadAmount() >= 8; i++) {
                spots.add(inPacket.decodePositionInt());
            }
        }
        int spacing = Math.max(50, si.getValue(range, slv) / Math.max(1, held));
        for (int i = spots.size(); i < held; i++) {
            spots.add(chr.getPosition().deepCopy().add(new Position((i - held / 2) * spacing, 0)));
        }
        int inte = chr.getStat(Stat.inte);
        int durationMs = (si.getValue(q, slv) + si.getValue(q2, slv) * (inte / Math.max(1, si.getValue(s2, slv)))) * 1000;
        for (Position pos : spots) {
            AffectedArea aa = AffectedArea.getPassiveAA(chr, HOLY_WATER, slv, pos);
            if (si.getFirstRect() != null) {
                aa.setRect(pos.getRectAround(si.getFirstRect()));
            }
            aa.setDelay((short) 4);
            aa.setDuration(durationMs);
            chr.getField().spawnAffectedArea(aa);
        }
        setHolyWater(0);
    }

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
        Option o5 = new Option();
        Option o6 = new Option();
        Option o7 = new Option();
        switch (skillId) {
            case TRIUMPH_FEATHER:
            case HEXA_TRIUMPH_FEATHER:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(TriumphFeather, o1);
                break;
            case FOUNTAIN_OF_VENGEANCE:
            case HEXA_FOUNTAIN_OF_VENGEANCE:
                summon = Summon.getSummonBy(chr, skillId, slv);
                summon.setMoveAbility(MoveAbility.Stop);
                summon.setAssistType(AssistType.Attack);
                field.spawnSummonAndRemoveOld(summon);
                break;
            case BLOOD_OF_THE_DIVINE:
                // toggle: final damage +u%, damage taken +v% for time
                if (tsm.hasStat(HolyBlood)) {
                    tsm.removeStatsBySkill(skillId);
                    break;
                }
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(HolyBlood, o1);
                o2.nOption = si.getValue(u, slv);
                o2.rOption = skillId;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePMdR, o2);
                o3.nOption = si.getValue(v, slv);
                o3.rOption = skillId;
                o3.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieHitDamage, o3);
                break;
            case HOLY_WATER:
                useHolyWater(si, slv, inPacket);
                break;
            case HOLY_FOUNTAIN:
                AffectedArea aa = AffectedArea.getPassiveAA(chr, skillId, slv);
                aa.setPosition(chr.getPosition());
                aa.setRect(aa.getPosition().getRectAround(si.getRects().get(0)));
                aa.setDelay((short) 4);
                chr.getField().spawnAffectedArea(aa);
                break;
            case DISPEL:
                tsm.removeAllDebuffs();
                break;
            case MYSTIC_DOOR:
                // the door in the field, and its twin at the town's "tp" portal
                Field townField = chr.getClient().getChannelInstance().getField(chr.getField().getInfo().getReturnMap());
                Portal townSpot = townField == null ? null : townField.getInfo().getPortalByName("tp");
                if (townSpot == null || si == null) {
                    org.apache.logging.log4j.LogManager.getLogger(Bishop.class).warn(String.format("Mystic Door of character %d in field %d has no town portal or skill info", chr.getId(), chr.getFieldID()));
                } else {
                    if (chr.getTownPortal() != null) {
                        chr.getTownPortal().despawnTownPortal();
                    }
                    new TownPortal(chr, new Position(townSpot.getX(), townSpot.getY()), new Position(chr.getPosition().getX(), chr.getPosition().getY()),
                            townField.getId(), chr.getFieldID(), skillId, si.getValue(time, slv)).spawnTownPortal();
                }
                chr.dispose();
                break;
            case DIVINE_PROTECTION:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                o1.bOption = 1;
                tsm.putCharacterStatValue(AntiMagicShell, o1);
                break;
            case HEAL:
                Rect rect = chr.getRectAround(si.getFirstRect());
                if (!chr.isLeft()) {
                    rect = rect.horizontalFlipAround(chr.getPosition().getX());
                }
                if (chr.getParty() == null) {
                    chr.heal(chr.getHPPerc(80), true, false, skillId);
                } else {
                    Rect finalRect = rect;
                    List<Char> pChrList = chr.getParty().getPartyMembersInField(chr).stream().filter(pc -> finalRect.hasPositionInside(pc.getPosition())).collect(Collectors.toList());
                    for (Char pChr : pChrList) {
                        pChr.heal(pChr.getHPPerc(100 / pChrList.size()));
                    }
                    chr.reduceSkillCoolTime(skillId, si.getValue(y, slv) * 1000);
                }
                break;
            case BLESS:
                tsm.removeStatsBySkill(ADV_BLESSING);
                o1.nOption = si.getValue(u, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(DEF, o1);
                o2.nOption = si.getValue(v, slv);
                o2.rOption = skillId;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(EVA, o2);
                tsm.putCharacterStatValue(ACC, o2);
                o3.nOption = si.getValue(x, slv);
                o3.rOption = skillId;
                o3.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(PAD, o3);
                tsm.putCharacterStatValue(MAD, o3);
                break;
            case ADV_BLESSING:

                if (tsm.hasStatBySkillId(DecentSkillHandler.DECENT_ADV_BLESSING_BASE) || tsm.hasStatBySkillId(DECENT_ADV_BLESSING_V)) {
                    tsm.removeStatsBySkill(DecentSkillHandler.DECENT_ADV_BLESSING_BASE);
                    tsm.removeStatsBySkill(DECENT_ADV_BLESSING_V);
                }

                tsm.removeStatsBySkill(BLESS);
                // GMS: AdvancedBless (n = attack / magic attack) and IndieMHP / IndieMMP only
                o3.nOption = si.getValue(x, slv) + (chr.hasSkill(ADV_BLESSING_FEROCITY) ? 20 : 0);
                o3.rOption = skillId;
                o3.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(AdvancedBless, o3);
                o4.nOption = si.getValue(indieMhp, slv) + chr.getSkillStatValue(indieMhp, ADV_BLESSING_EXTRA_POINT);
                o4.rOption = skillId;
                o4.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieMHP, o4);
                tsm.putCharacterStatValue(IndieMMP, o4);
                if (chr.hasSkill(ADV_BLESSING_BOSS_RUSH)) {
                    o5.nOption = chr.getSkillStatValue(bdR, ADV_BLESSING_BOSS_RUSH);
                    o5.rOption = skillId;
                    o5.tOption = si.getValue(time, slv);
                    tsm.putCharacterStatValue(IndieBDR, o5);
                }
                break;
            case HOLY_SYMBOL:
                o1.slv = slv;
                o1.nOption = si.getValue(x, slv) + chr.getSkillStatValue(y, HOLY_SYMBOL_EXPERIENCE);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                o1.sOption = chr.getSkillStatValue(v, HOLY_SYMBOL_ITEM_DROP); // DropR
                tsm.putCharacterStatValue(HolySymbol, o1);
                if (chr.hasSkill(HOLY_SYMBOL_PREPARATION)) {
                    o2.nOption = chr.getSkillStatValue(asrR, HOLY_SYMBOL_PREPARATION);
                    o2.rOption = skillId;
                    o2.tOption = si.getValue(time, slv);
                    tsm.putCharacterStatValue(IndieAsrR, o2);
                    tsm.putCharacterStatValue(IndieTerR, o2);
                }
                break;
            case HOLY_MAGIC_SHELL:
                if (!tsm.getChr().hasSkillOnCooldown(HOLY_MAGIC_SHELL_COOLTIME)) {
                    chr.heal(changeBishopHealingBuffs(HOLY_MAGIC_SHELL));
                    o1.nOption = 1;
                    o1.rOption = skillId;
                    o1.tOption = si.getValue(time, slv) + (chr.hasSkill(HOLY_MAGIC_SHELL_PERSIST) ? 5 : 0);
                    o1.xOption = si.getValue(x, slv) + (chr.hasSkill(HOLY_MAGIC_SHELL_EXTRA_GUARD) ? 2 : 0);
                    tsm.putCharacterStatValue(HolyMagicShell, o1);
                    tsm.getChr().addSkillCoolTime(HOLY_MAGIC_SHELL_COOLTIME, si.getValue(y, slv) * 1000);
                }
                break;
            case RESURRECTION:
                Party party = chr.getParty();
                if (party != null) {
                    field = chr.getField();
                    Rect rect2 = chr.getPosition().getRectAround(si.getFirstRect());
                    if (!chr.isLeft()) {
                        rect2 = rect2.horizontalFlipAround(chr.getPosition().getX());
                    }
                    List<PartyMember> eligblePartyMemberList = field.getPartyMembersInRect(chr, rect2).stream().
                            filter(pml -> pml.getChr().getId() != chr.getId() &&
                                    pml.getChr().getHP() <= 0).
                            collect(Collectors.toList());
                    for (PartyMember partyMember : eligblePartyMemberList) {
                        Char partyChr = partyMember.getChr();
                        partyChr.heal(partyChr.getMaxHP(), false, true);
                        partyChr.healMP(partyChr.getMaxMP());
                        partyChr.write(UserPacket.effect(Effect.skillAffected(skillId, (byte) 1, 0)));
                        partyChr.getField().broadcastPacket(UserRemote.effect(partyChr.getId(), Effect.skillAffected(skillId, (byte) 1, 0)));
                    }
                }
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(NotDamaged, o1);
                break;
            case HEAVENS_DOOR_SKILL_USE:
                party = chr.getParty();
                if (party != null) {
                    for (PartyMember partyMember : party.getOnlineMembers()) {
                        Char partyChr = partyMember.getChr();
                        TemporaryStatManager partyTSM = partyChr.getTemporaryStatManager();
                        if (partyChr.hasSkillOnCooldown(HEAVENS_DOOR_SKILL_USE)) {
                            continue;
                        }
                        o1.nOption = 1;
                        o1.rOption = HEAVENS_DOOR;
                        partyTSM.putCharacterStatValue(ReviveOnce, o1);
                        partyChr.addSkillCoolTime(HEAVENS_DOOR_SKILL_USE, chr.getSkillStatValue(time, HEAVENS_DOOR) * 1000);
                        partyTSM.sendSetStatPacket();
                        if (partyChr != chr) {
                            chr.getField().broadcastPacket(UserRemote.effect(partyChr.getId(), Effect.skillAffected(HEAVENS_DOOR, slv, 0)), partyChr);
                            partyChr.write(UserPacket.effect(Effect.skillAffected(HEAVENS_DOOR, slv, 0)));
                        }
                    }
                } else {
                    if (chr.hasSkillOnCooldown(HEAVENS_DOOR_SKILL_USE)) {
                        return;
                    }
                    o1.nOption = 1;
                    o1.rOption = HEAVENS_DOOR;
                    tsm.putCharacterStatValue(ReviveOnce, o1);
                    tsm.sendSetStatPacket();
                    chr.addSkillCoolTime(HEAVENS_DOOR_SKILL_USE, chr.getSkillStatValue(time, HEAVENS_DOOR) * 1000);
                }
                break;
            case RIGHTEOUSLY_INDIGNANT:
                o1.nOption = 1;
                o1.rOption = skillId;
                // stance swap only: its MATT/FD/IED/elemental stats are passive (psd) in the data
                tsm.putCharacterStatValue(VengeanceOfAngel, o1);
                break;
            case ANGEL_OF_BALANCE_BENEVOLENCE:
            case ANGEL_OF_BALANCE_AVENGE:
                summon = Summon.getSummonBy(chr, ANGEL_OF_BALANCE_BENEVOLENCE, slv);
                summon.setSkillID(skillId);
                summon.setFlyMob(true);
                summon.setMoveAbility(MoveAbility.Walk);
                summon.setAssistType(skillId == ANGEL_OF_BALANCE_AVENGE ? AssistType.Attack : AssistType.Heal);
                field.spawnSummonAndRemoveOld(summon);
                break;
            case BENEDICTION:
            case HEXA_BENEDICTION:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Benediction, o1);
                break;
        }
        changeBlessedCount();
        tsm.sendSetStatPacket();
    }

    public void increaseDivinePunishment() {
        if (!chr.hasSkill(DIVINE_PUNIHSMENT)) {
            return;
        }

        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int count = 1;
        if (tsm.hasStat(StackOverTimeSkill)) {
            count = tsm.getOption(StackOverTimeSkill).nOption;
            if (count < 8) {
                count++;
            }
        }

        updateDivinePunishment(count);
    }

    private void updateDivinePunishment(int count) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option(Math.min(Math.max(0, count), 8), DIVINE_PUNIHSMENT, 0);
        tsm.putCharacterStatValue(StackOverTimeSkill, o);
        tsm.sendSetStatPacket();
    }

    @Override
    public void handleKeyDownSkillCost(int skillId) {
        super.handleKeyDownSkillCost(skillId);

        switch (skillId) {
            case DIVINE_PUNIHSMENT:
                TemporaryStatManager tsm = chr.getTemporaryStatManager();
                Option o = tsm.getOptByCTSAndSkill(StackOverTimeSkill, DIVINE_PUNIHSMENT);
                if (o == null) {
                    return;
                }

                updateDivinePunishment(o.nOption - 1); // Cost of keydown
                break;
        }
    }

    @Override
    public void handleKeyDownSkill(Char chr, int skillID, InPacket inPacket) {
        switch (skillID) {
            case BIG_BANG:
            case HEXA_BIG_BANG:
                break;
            default:
                super.handleKeyDownSkill(chr, skillID, inPacket);
                break;
        }
    }

    @Override
    public void handleCancelKeyDownSkill(Char chr, int skillID) {
        //TemporaryStatManager tsm = chr.getTemporaryStatManager();
        //if (tsm.getOptByCTSAndSkill(KeyDownTimeIgnore, BIG_BANG) != null && skillID == BIG_BANG) {
        //
        //}
        switch (skillID) {
            case BIG_BANG:
            case HEXA_BIG_BANG:
            case DIVINE_PUNIHSMENT:
                break;
            default:
                super.handleCancelKeyDownSkill(chr, skillID);
                break;
        }
    }

    public void handleRemoveCTS(CharacterTemporaryStat cts, Option o) {
        changeBlessedCount();
        super.handleRemoveCTS(cts, o);
    }

    @Override
    public void handleSkillPrepareStop(Char chr, int skillId, SkillUseInfo sui) {
        if (skillId == BIG_BANG) {
            chr.getJobHandler().handleSkillRemove(chr, skillId, new HashMap<>());
            return;
        }
        if (skillId == DIVINE_PUNIHSMENT) {
            return;
        }
        super.handleSkillPrepareStop(chr, skillId, sui);
    }

    public static void reviveByHeavensDoor(Char chr) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        chr.heal(chr.getMaxHP(), false, true);
        tsm.removeStatsBySkill(HEAVENS_DOOR);
        tsm.sendResetStatPacket();
        chr.chatMessage("You have been revived by Heaven's Door.");
    }


    // Hit related methods ---------------------------------------------------------------------------------------------
}
