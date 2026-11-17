package net.swordie.ms.connection.packet;

import net.swordie.ms.client.character.avatar.AvatarLook;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.MobAttackInfo;
import net.swordie.ms.client.character.skills.shootobject.ShootObject;
import net.swordie.ms.client.character.skills.shootobject.ShootObjectSkillInfo;
import net.swordie.ms.client.jobs.cygnus.BlazeWizard;
import net.swordie.ms.client.jobs.flora.Illium;
import net.swordie.ms.client.jobs.resistance.Mechanic;
import net.swordie.ms.client.jobs.sengoku.Kanna;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.EnterType;
import net.swordie.ms.enums.LeaveType;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.movement.MovementInfo;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.util.Position;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created on 5/21/2018.
 */
public class Summoned {

    /** How the client reads SUMMONED_CREATED for a skill; resources/data/summoned-skills.tsv lists the skills. */
    private enum Rule {
        /** the two clone delay ints */
        userClone,
        /** drawn from the template id */
        templateDrawn,
        /** tail: the owner's bat effect handles and how long they last */
        tailEffects,
        /** tail: one int */
        tailInt,
        /** tail: the summon term */
        tailTerm,
        /** tail: an int and a byte */
        tailIntByte,
        /** tail: one byte */
        tailByte
    }

    private static final Supplier<Map<Rule, Set<Integer>>> SKILLS = DataTable.cached("summoned-skills", t -> {
        Map<Rule, Set<Integer>> m = new EnumMap<>(Rule.class);
        for (DataTable.Row r : t.rows()) {
            m.computeIfAbsent(Rule.valueOf(r.str("rule").trim()), k -> new HashSet<>()).add(r.i("skill"));
        }
        return m;
    });
    private static final List<Rule> TAILS = List.of(Rule.tailEffects, Rule.tailInt, Rule.tailTerm, Rule.tailIntByte,
            Rule.tailByte);

    /** The last SUMMONED_ATTACK byte is set for this skill. */
    private static final int CRYSTALLINE_ESSENCE = 400021151;

    private static boolean has(Rule rule, int skillId) {
        return SKILLS.get().getOrDefault(rule, Set.of()).contains(skillId);
    }

    /** the skill's tail rule, or null */
    private static Rule tailOf(int skillId) {
        for (Rule r : TAILS) {
            if (has(r, skillId)) {
                return r;
            }
        }
        return null;
    }

    /**
     * A flying summon plays "fly"; v271 data dropped it for some (Phoenix, Freezer: "move" only) and the client
     * terminates (INVALID_GAME_DATA) when neither the action nor its fallback loads. Walk those, as Bahamut does.
     */
    private static net.swordie.ms.enums.MoveAbility clientMoveAbility(Summon summon) {
        net.swordie.ms.enums.MoveAbility ma = summon.getMoveAbility();
        if (ma != net.swordie.ms.enums.MoveAbility.Fly && ma != net.swordie.ms.enums.MoveAbility.FlyRandom) {
            return ma;
        }
        int dataId = has(Rule.templateDrawn, summon.getSkillID()) && summon.getTemplateId() > 0
                ? summon.getTemplateId() : summon.getSkillID();
        return net.swordie.ms.loaders.SkillData.summonLacksAction(dataId, "fly")
                ? net.swordie.ms.enums.MoveAbility.Walk : ma;
    }

    public static OutPacket summonedAssistAttackRequest(Summon summon, int summonSkillIdentifier) {
        OutPacket outpacket = new OutPacket(OutHeader.SUMMONED_ASSIST_ATTACK_REQUEST);

        outpacket.encodeInt(summon.getChr().getId());
        outpacket.encodeInt(summon.getObjectId());

        outpacket.encodeInt(summonSkillIdentifier); // seems to be used to identify which skill to attack with.

        return outpacket;
    }

    public static OutPacket assistSpecialAttackRequest(Summon summon, int summonSkillIdentifier) {
        // something with 400051028~400051032 (Suborbital Strike)
        OutPacket outPacket = new OutPacket(OutHeader.SUMMONED_ASSIST_SPECIAL_ATTACK_REQUEST);

        outPacket.encodeInt(summon.getChr().getId());
        outPacket.encodeInt(summon.getObjectId());

        outPacket.encodeInt(summonSkillIdentifier);
        // v271 (client 0x144201980) reads only the skill id

        return outPacket;
    }

    public static OutPacket summonedSummonAttackActive(Summon summon) {
        OutPacket outPacket = new OutPacket(OutHeader.SUMMONED_SUMMON_ATTACK_ACTIVE);

        outPacket.encodeInt(summon.getChr().getId());
        outPacket.encodeInt(summon.getObjectId());

        outPacket.encodeInt(0);

        return outPacket;
    }

    public static OutPacket summonedActionChange(Summon summon, byte action) {
        OutPacket outPacket = new OutPacket(OutHeader.SUMMONED_ACTION_CHANGE);

        outPacket.encodeInt(summon.getChr().getId());
        outPacket.encodeInt(summon.getObjectId());

        outPacket.encodeByte(action);

        return outPacket;
    }

    public static OutPacket summonedSkill(Summon summon, byte summonSkillType, int summonSkillID, ShootObjectSkillInfo shootObjectSkillInfo) {
        OutPacket outPacket = new OutPacket(OutHeader.SUMMONED_SKILL);

        outPacket.encodeInt(summon.getChr().getId());
        outPacket.encodeInt(summon.getObjectId());

        outPacket.encodeByte(summonSkillType); // action
        outPacket.encodeInt(summonSkillID);

        if (summon.getSkillID() == BlazeWizard.SALAMANDER_MISCHIEF) {
            outPacket.encodeInt(summon.isHide() ? 1 : 0); // hide = 1  |  unhide = 0
        }

        if (SkillConstants.isShootObjectSummon(summon.getSkillID())) {
            outPacket.encodeInt(shootObjectSkillInfo.getSkillId());
            outPacket.encodeInt(shootObjectSkillInfo.getSlv());
            outPacket.encodeInt(shootObjectSkillInfo.getAction());
            outPacket.encodeInt(shootObjectSkillInfo.getActionSpeed());
            outPacket.encodeInt(shootObjectSkillInfo.getProjectileItemId());
            outPacket.encodePosition(shootObjectSkillInfo.getPosition());

            outPacket.encodeInt(shootObjectSkillInfo.extraEncodeInt1);
            outPacket.encodeInt(shootObjectSkillInfo.extraEncodeInt2);
            outPacket.encodeInt(shootObjectSkillInfo.extraEncodeInt3);
            outPacket.encodeInt(shootObjectSkillInfo.extraEncodeInt4);
            outPacket.encodeInt(shootObjectSkillInfo.extraEncodeInt5);
            outPacket.encodeInt(shootObjectSkillInfo.extraEncodeInt6);
            outPacket.encodeByte(shootObjectSkillInfo.extraEncodeByte);
            outPacket.encodeInt(shootObjectSkillInfo.extraEncodeInt7);
            outPacket.encodeByte(shootObjectSkillInfo.extraEncodeByte2);
            outPacket.encodeLong(shootObjectSkillInfo.extraEncodeLong1);
            outPacket.encodeInt(shootObjectSkillInfo.extraEncodeInt8);
            outPacket.encodeInt(shootObjectSkillInfo.extraEncodeInt9);
            outPacket.encodeByte(0); // v271 (0x140f150d0) ends the record with a byte

            outPacket.encodeInt(shootObjectSkillInfo.getShootObjects().size());
            for (ShootObject shootObject : shootObjectSkillInfo.getShootObjects()) {
                shootObject.encodeShootObjectRemote(outPacket);
            }
        }

        return outPacket;
    }

    public static OutPacket summonBeholderRevengeAttack(Summon summon, int mob) {
        OutPacket outPacket = new OutPacket(OutHeader.SUMMONED_BEHOLDER_REVENGE_ATTACK);

        outPacket.encodeInt(summon.getChr().getId());//char ID
        outPacket.encodeInt(summon.getObjectId());//summon

        outPacket.encodeInt(1); // count (client 0x144201800: 4 n {4 mob})
        outPacket.encodeInt(mob);

        return outPacket;
    }

    public static OutPacket summonedCreated(Summon summon, EnterType enterType) {
        int skillId = summon.getSkillID();
        OutPacket outPacket = new OutPacket(OutHeader.SUMMONED_CREATED);

        outPacket.encodeInt(summon.getChr().getId());
        outPacket.encodeInt(summon.getObjectId());

        outPacket.encodeInt(summon.getSkillID());
        outPacket.encodeInt(summon.getCharLevel());
        outPacket.encodeInt(summon.getSlv());

        // CSummoned::Init
        outPacket.encodePosition(summon.getPosition());
        outPacket.encodeByte(summon.getMoveAction());
        outPacket.encodeShort(summon.getCurFoothold());
        outPacket.encodeByte(clientMoveAbility(summon).getVal());
        outPacket.encodeByte(summon.getAssistType().getVal());
        outPacket.encodeByte(enterType.getVal());
        outPacket.encodeInt(summon.getMobTemplateId()); // Cinder Maelstrom
        outPacket.encodeByte(summon.isFlyMob());
        outPacket.encodeByte(summon.isBeforeFirstAttack());
        outPacket.encodeInt(summon.getTemplateId());
        outPacket.encodeInt(summon.getBulletID());
        AvatarLook al = summon.getAvatarLook();
        outPacket.encodeByte(al != null);
        if(al != null) {
            al.encode(outPacket);
        }
        if (skillId == Mechanic.ROCK_N_SHOCK) { // Rock 'n Shock (aka Tesla Coil)
            outPacket.encodeByte(summon.getTeslaCoilState());
            for(Position pos : summon.getTeslaCoilPositions()) {
                outPacket.encodePosition(pos);
            }
        }
        if (has(Rule.userClone, skillId)) {
            outPacket.encodeInt(summon.getActionDelay()); // in ms
            outPacket.encodeInt(summon.getMovementDelay()); // unsure what this exactly is
        }
        // v271 Init (client 0x14413ac20): the Kishin positions, the Suborbital Strike byte and the byte before the
        // term are no longer read here
        outPacket.encodeInt(summon.getSummonTerm());
        outPacket.encodeByte(summon.isAttackActive());
        outPacket.encodeInt(summon.isFlip() ? 0 : 1); // isLeft
        outPacket.encodeInt(0);
        outPacket.encodeInt(summon.getMaxWalkDistance());
        // v271: the jaguar state/count bytes are not read here any more
        boolean isStateUsing = SkillConstants.isStateUsingSummon(summon.getSkillID());
        outPacket.encodeByte(isStateUsing);
        if (isStateUsing) {
            outPacket.encodeInt(summon.getCount());
            outPacket.encodeInt(summon.getState());
        }
        outPacket.encodeInt(summon.getLinkedSummonSkillIds().size());
        for (int linkedSummonSkillId : summon.getLinkedSummonSkillIds()) {
            outPacket.encodeInt(linkedSummonSkillId);
        }
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(-1);
        outPacket.encodeByte(0);
        outPacket.encodeByte(0);
        outPacket.encodeInt(0);

        // skill-specific tail
        Rule tail = tailOf(skillId);
        if (tail != null) {
            switch (tail) {
                case tailEffects -> {
                    outPacket.encodeInt(summon.getClientEffectIds()[0]);
                    outPacket.encodeInt(summon.getClientEffectIds()[1]);
                    outPacket.encodeInt(summon.getSummonTerm());
                }
                case tailInt -> outPacket.encodeInt(0);
                case tailTerm -> outPacket.encodeInt(summon.getSummonTerm());
                case tailIntByte -> {
                    outPacket.encodeInt(0);
                    outPacket.encodeByte(0);
                }
                case tailByte -> outPacket.encodeByte(0);
                default -> {
                }
            }
        }

        return outPacket;
    }

    public static OutPacket summonedRemoved(Summon summon, LeaveType leaveType) {
        OutPacket outPacket = new OutPacket(OutHeader.SUMMONED_REMOVED);

        outPacket.encodeInt(summon.getChr().getId());
        outPacket.encodeInt(summon.getObjectId());

        outPacket.encodeByte(leaveType.getVal());

        return outPacket;
    }

    public static OutPacket summonedAttack(int charID, AttackInfo ai, boolean counter) {
        OutPacket outPacket = new OutPacket(OutHeader.SUMMONED_ATTACK);

        outPacket.encodeInt(charID);
        outPacket.encodeInt(ai.summon.getObjectId());

        outPacket.encodeInt(ai.summon.getCharLevel());
        byte left = (byte) (ai.left ? 1 : 0);
        outPacket.encodeByte((left << 7) | ai.attackActionType);
        byte attackCount = (byte) (ai.mobAttackInfo.size() > 0 ? ai.mobAttackInfo.get(0).damages.length : 0);
        outPacket.encodeByte((ai.mobCount << 4) | (attackCount & 0xF));
        for (MobAttackInfo mai : ai.mobAttackInfo) {
            outPacket.encodeInt(mai.mobId);
            outPacket.encodeByte(mai.byteIdk1);
            for (long dmg : mai.damages) {
                outPacket.encodeLong(dmg);
            }
        }
        outPacket.encodeByte(counter); // bCounterAttack
        outPacket.encodeByte(ai.attackActionType == 0);
        outPacket.encodeShort(ai.summon.getX());
        outPacket.encodeShort(ai.summon.getY());
        outPacket.encodeInt(ai.skillId == ai.summon.getSkillID() ? (ai.summonSpecialSkillId == 0 ? ai.skillId : ai.summonSpecialSkillId): ai.skillId); // Attack Skill ID  TODO
        outPacket.encodeByte(false);
        outPacket.encodePosition(new Position());
        outPacket.encodeByte(ai.skillId == CRYSTALLINE_ESSENCE);

        return outPacket;
    }

    public static OutPacket summonedMove(int charID, int summonID, MovementInfo movementInfo) {
        OutPacket outPacket = new OutPacket(OutHeader.SUMMONED_MOVE);

        outPacket.encodeInt(charID);
        outPacket.encodeInt(summonID);

        movementInfo.encodeWithoutKeyPads(outPacket); // GMS sends the path without the key pad part

        return outPacket;
    }

    public static OutPacket summonedUpdateHPTag(Summon summon) {
        OutPacket outPacket = new OutPacket(OutHeader.SUMMONED_UPDATE_HP_TAG);

        outPacket.encodeInt(summon.getChr().getId());
        outPacket.encodeInt(summon.getObjectId());

        outPacket.encodeInt(summon.getHp());

        return outPacket;
    }

    public static OutPacket summonedDoAttackRequest(Summon summon, int skillId) {
        // Used with Pathfinder RelicUnbound  Burst and Torrent variant
        OutPacket outPacket = new OutPacket(OutHeader.SUMMONED_DO_ATTACK_REQUEST);

        outPacket.encodeInt(summon.getChr().getId());
        outPacket.encodeInt(summon.getObjectId());

        outPacket.encodeInt(skillId);
        outPacket.encodeByte(0);

        return outPacket;
    }

    public static OutPacket stateChanged(Summon summon, int type, Map<Integer, Boolean> crystalSkillModeMap) {
        OutPacket outPacket = new OutPacket(OutHeader.SUMMONED_STATE_CHANGED);

        outPacket.encodeInt(summon.getChr().getId());
        outPacket.encodeInt(summon.getObjectId());

        outPacket.encodeInt(type);
        switch (type) {
            case 1: // Rift of Damnation

                break;

            case 2: // Illium
                outPacket.encodeInt(crystalSkillModeMap.size()); // loop size
                for (Map.Entry<Integer, Boolean> entry : crystalSkillModeMap.entrySet()) {
                    outPacket.encodeInt(entry.getKey());            // Crystal Skill Idx
                    outPacket.encodeInt(entry.getValue() ? 1 : 0);  // boolean  Can use
                }
                break;

            // maybe more cases
        }



        return outPacket;
    }

    public static OutPacket summonedSetNewPosition(Summon summon, Position position) {
        OutPacket outPacket = new OutPacket(OutHeader.SUMMONED_SET_NEW_POSITION);

        outPacket.encodeInt(summon.getChr().getId());
        outPacket.encodeInt(summon.getObjectId());

        outPacket.encodePositionInt(position);

        return outPacket;
    }

    public static OutPacket repositionSummon(Summon summon, int skillId, Position newPosition) {
        OutPacket outPacket = new OutPacket(OutHeader.SUMMONED_REPOSITION_SUMMON);

        outPacket.encodeInt(summon.getChr().getId());
        outPacket.encodeInt(summon.getObjectId());

        outPacket.encodeInt(skillId);
        outPacket.encodeInt(summon.getSlv());
        outPacket.encodePositionInt(newPosition);

        return outPacket;
    }

    public static OutPacket effect(Summon summon, int effectType, int skillID) {
        // something with 400021054 (Spirit's Domain)
        // also used by Hayato's 3rd V skill summon
        OutPacket outPacket = new OutPacket(OutHeader.SUMMONED_EFFECT);

        outPacket.encodeInt(summon.getChr().getId());
        outPacket.encodeInt(summon.getObjectId());

        outPacket.encodeInt(effectType); // seems to show different summon effects
        switch (effectType) {
            case 0:
            case 1:
            case 2:
                // no decodes in IDA
                break;
            case 3:
                outPacket.encodeInt(0); // unk
                break;
            case 4:
                outPacket.encodeInt(skillID); // unk
                break;
        }

        return outPacket;
    }

    public static OutPacket summonUseSpecifiedSkill(Summon summon, int skillId) {
        OutPacket outPacket = new OutPacket(OutHeader.SUMMONED_SUMMON_USE_SPECIFIED_SKILL);

        outPacket.encodeInt(summon.getChr().getId());
        outPacket.encodeInt(summon.getObjectId());

        outPacket.encodeInt(skillId);

        return outPacket;
    }

    public static OutPacket summonUpgradeStage(Summon summon, int type, int skillID, int cooltime) {
        OutPacket outPacket = new OutPacket(OutHeader.SUMMONED_UPGRADE_STAGE);

        outPacket.encodeInt(summon.getChr().getId());
        outPacket.encodeInt(summon.getObjectId());

        outPacket.encodeInt(type); // still unsure about the exact structure.
        switch (type) {
            case 0:
                outPacket.encodeInt(0); // unk
                outPacket.encodeInt(0); // unk
                break;
            case 1:
                // no decodes
                break;
            case 2:
                outPacket.encodeInt(summon.getCount()); // crystal Count
                outPacket.encodeInt(summon.getState()); // crystal State
                break;
            case 3:
                // no decodes
                break;
            case 4:
                outPacket.encodeInt(0); // unk
                outPacket.encodeInt(0); // unk
                break;
            case 5: // Set Cooltime for Summon Skill
                outPacket.encodeInt(skillID);
                outPacket.encodeInt(cooltime);
                break;
        }

        return outPacket;
    }

    public static OutPacket gravityCrushDamage(Summon summon) {
        OutPacket outPacket = new OutPacket(OutHeader.SUMMONED_GRAVITY_CRUSH_DAMAGE);

        outPacket.encodeInt(summon.getChr().getId());

        outPacket.encodeInt(1); // Unknown
        outPacket.encodeInt(summon.getObjectId());
        outPacket.encodeLong(summon.getGravityCrushDmg());

        return outPacket;
    }

    public static OutPacket gravityCrushMaxDamage(Summon summon) {
        OutPacket outPacket = new OutPacket(OutHeader.SUMMONED_GRAVITY_CRUSH_MAX_DAMAGE);

        outPacket.encodeInt(summon.getChr().getId());

        outPacket.encodeInt(summon.getObjectId());
        outPacket.encodeLong(summon.getGravityCrushMaxDmg());

        return outPacket;
    }

    public static OutPacket broadcastSpiritDomainState(int broadcastToChr, int ownerChr, Summon domainSummon) {
        OutPacket outPacket = new OutPacket(OutHeader.SUMMONED_SPIRIT_DOMAIN_STATE);

        outPacket.encodeInt(broadcastToChr);
        outPacket.encodeInt(domainSummon.getObjectId());

        outPacket.encodeInt(ownerChr);
        outPacket.encodeInt(domainSummon.getSkillID());
        outPacket.encodeInt(domainSummon.getState());

        return outPacket;
    }
}
