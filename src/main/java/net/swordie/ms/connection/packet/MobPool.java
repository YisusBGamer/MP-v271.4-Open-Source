package net.swordie.ms.connection.packet;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.AppearingFoothold;
import net.swordie.ms.life.DeathType;
import net.swordie.ms.life.mob.*;
import net.swordie.ms.life.mob.skill.*;
import net.swordie.ms.life.movement.MovementInfo;
import net.swordie.ms.loaders.containerclasses.MobSkillInfo;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.world.field.Field;

import java.util.*;

/**
 * Created on 2/28/2018.
 */
public class MobPool {
    /**
     * v232's MOB_STAT_SET has an int between the delay and nCalcDamageStatIndex (the official packet in
     * MobStat#showMobStats: ... 3C 00 | 30 75 | 00 00 00 00 | 01). Without it the client read past the packet's end
     * and drew broken debuff icons over monsters. The v265 reference sends no such int, so it starts off here; it can
     * be switched on in-game with !mobstatint for comparison.
     */
    public static volatile boolean statSetExtraInt = false;

    public static OutPacket enterField(Mob mob, boolean hasBeenInit) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_ENTER_FIELD);

        outPacket.encodeByte(false); // bSealedInsteadDead
        outPacket.encodeString(""); // v271: split on '_' and kept by the client when non-empty
        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeByte(mob.getCalcDamageIndex());
        outPacket.encodeInt(mob.getTemplateId());
        ForcedMobStat fms = mob.getForcedMobStat();
        outPacket.encodeByte(fms != null);
        if (fms != null) {
            fms.encode(outPacket);
        }
        encodeTemporaryStatV271(mob, outPacket);
        if (!hasBeenInit) {
            mob.encodeInit(outPacket);
        }
        return outPacket;
    }

    /** The mob plays action (30 + it); unforced, the client skips it while the mob is busy. */
    public static OutPacket forcedAction(int objectId, int action, boolean force) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_FORCED_ACTION);
        outPacket.encodeInt(objectId);
        outPacket.encodeInt(action);
        outPacket.encodeByte(force);
        return outPacket;
    }

    /** The mob uses its attack (Mob.wz info/attack index), even one only the server starts (onlyFsm). */
    public static OutPacket forceAttack(int objectId, int attackIdx) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_FORCE_ATTACK);
        outPacket.encodeInt(objectId);
        outPacket.encodeInt(1);
        outPacket.encodeInt(attackIdx);
        outPacket.encodeInt(0);
        return outPacket;
    }

    public static OutPacket nextTargetFromSvr(int mobId, int charId) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_NEXT_TARGET_FROM_SVR);
        outPacket.encodeInt(mobId);
        outPacket.encodeInt(charId);
        return outPacket;
    }

    public static OutPacket lastHitInfo(int mobId, int charId, int ms) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_LAST_HIT_INFO);
        outPacket.encodeInt(mobId);
        outPacket.encodeInt(charId);
        outPacket.encodeInt(ms);
        return outPacket;
    }

    public static OutPacket changeController(Mob mob, boolean hasBeenInit, boolean isController) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_CHANGE_CONTROLLER);

        outPacket.encodeByte(isController);
        outPacket.encodeInt(mob.getObjectId());

        if (isController) {
            outPacket.encodeByte(mob.getCalcDamageIndex());

            outPacket.encodeInt(mob.getTemplateId());
            ForcedMobStat fms = mob.getForcedMobStat();
            outPacket.encodeByte(fms != null);
            if (fms != null) {
                fms.encode(outPacket);
            }
            encodeTemporaryStatV271(mob, outPacket);
            mob.encodeInit(outPacket);
        }

        return outPacket;
    }

    /** Off (test.mobEnterStats=false): an empty mask as before, the stats then only reach the client by MOB_STAT_SET. */
    public static volatile boolean enterStats = net.swordie.ms.ServerSettings.getBoolean("test.mobEnterStats", true);

    /**
     * The mob's active stats through v271's MobStat reader (client 0x140d5f7e0, the same one MOB_STAT_SET uses, called
     * from 0x143ba68e0 on enter / controller change).
     */
    private static void encodeTemporaryStatV271(Mob mob, OutPacket outPacket) {
        if (enterStats && mob.getTemporaryStat() != null) {
            mob.getTemporaryStat().encodeV271(outPacket, true);
            return;
        }
        for (int i = 0; i < MobStat.LENGTH; i++) {
            outPacket.encodeInt(0);
        }
    }

    public static OutPacket leaveField(int id, DeathType deadType) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_LEAVE_FIELD);

        outPacket.encodeInt(id);

        outPacket.encodeByte(deadType.getVal());
        outPacket.encodeByte(0); // new 228

        // v271: three ints for types 0, 1 and 5-11 (v265 two)
        int type = deadType.getVal();
        if (type == 0 || type == 1 || (type >= 5 && type <= 11)) {
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
        }
        if (type == 12) {
            outPacket.encodeInt(0);
        }
        if (type == 9) {
            outPacket.encodeInt(0);
        }

        return outPacket;
    }

    public static OutPacket damaged(int mobID, int templateId, long damage, byte type, long hp, long maxHp) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_DAMAGED);

        outPacket.encodeInt(mobID);
        outPacket.encodeByte(type);
        outPacket.encodeLong(damage);
        if (templateId / 10000 == 250 || templateId / 10000 == 251) {
            outPacket.encodeLong(hp);
            outPacket.encodeLong(maxHp);
        }

        return outPacket;
    }

    /** Points over a monster shot in FPS mode (Eagle Hunt), negative ones in another color. */
    public static OutPacket deadFpsMode(int objectId, int point) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_DEAD_FPS_MODE);

        outPacket.encodeInt(objectId);
        outPacket.encodeInt(point);

        return outPacket;
    }

    public static OutPacket hpIndicator(int objectId, byte percDamage) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_HP_INDICATOR);

        outPacket.encodeInt(objectId);
        outPacket.encodeInt(percDamage);
        outPacket.encodeByte(false); // v271: a new byte before this one
        outPacket.encodeByte(false);

        return outPacket;
    }

    public static OutPacket ctrlAck(Mob mob, boolean nextAttackPossible, short mobCtrlSN, int skillID, short slv, int forcedAttack) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_CTRL_ACK);

        outPacket.encodeInt(mob.getObjectId());

        outPacket.encodeShort(mobCtrlSN);
        outPacket.encodeByte(nextAttackPossible);
        outPacket.encodeInt((int) mob.getMp());
        outPacket.encodeInt(skillID);
        outPacket.encodeShort(slv);
        outPacket.encodeInt(forcedAttack);
        outPacket.encodeInt(0);
        outPacket.encodeByte(0);

        return outPacket;
    }

    public static OutPacket statSet(Mob mob, short delay) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_STAT_SET);

        MobTemporaryStat mts = mob.getTemporaryStat();
        boolean hasMovementStat = mts.hasNewMovementAffectingStat();
        outPacket.encodeInt(mob.getObjectId());

        mts.encodeV271(outPacket);
        outPacket.encodeShort(delay);
        if (statSetExtraInt) {
            outPacket.encodeInt(0);
        }
        outPacket.encodeByte(mob.getCalcDamageIndex()); // nCalcDamageStatIndex
        if (hasMovementStat) {
            outPacket.encodeByte(1);
        }

        return outPacket;
    }

    public static OutPacket statReset(Mob mob, byte byteCalcDamageStatIndex, boolean sn) {
        return statReset(mob, byteCalcDamageStatIndex, sn, null);
    }

    public synchronized static OutPacket statReset(Mob mob, byte calcDamageStatIndex, boolean sn, Set<BurnedInfo> biList) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_STAT_RESET);
        MobTemporaryStat resetStats = mob.getTemporaryStat();
        int[] mask = resetStats.getRemovedMaskV271();
        outPacket.encodeInt(mob.getObjectId());
        for (int i = 0; i < MobStat.LENGTH; i++) {
            outPacket.encodeInt(mask[i]);
        }
        if (resetStats.hasRemovedMobStat(MobStat.BurnedInfo)) {
            if (biList == null) {
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
            } else {
                outPacket.encodeInt(0);
                outPacket.encodeInt(biList.size());
                for (BurnedInfo bi : biList) {
                    outPacket.encodeLong(0);
                    outPacket.encodeInt(bi.getCharacterId());
                    outPacket.encodeInt(bi.getSkillId());
                }
            }
        }
        if (resetStats.hasRemovedMobStat(MobStat.NewBurnedInfo)) {
            outPacket.encodeInt(0); // the client reads the NewBurnedInfo removal list here (0x140d622b0)
        }
        outPacket.encodeByte(calcDamageStatIndex);
        resetStats.encodeIndieTempStat(outPacket);
        if (resetStats.hasRemovedMovementAffectingStat()) {
            outPacket.encodeByte(sn);
        }
        resetStats.getRemovedStatVals().clear();
        return outPacket;
    }

    public static OutPacket specialEffectBySkill(Mob mob, int skillID, int charId, short delay) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_SPECIAL_EFFECT_BY_SKILL);

        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeInt(skillID);
        outPacket.encodeInt(charId);
        outPacket.encodeShort(delay);

        return outPacket;
    }

    public static OutPacket specialSelectedEffectBySkill(Mob mob, int skillID, int charId) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_SPECIAL_SELECTED_EFFECT_BY_SKILL);

        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeInt(skillID);
        outPacket.encodeInt(charId);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);

        return outPacket;
    }

    public static OutPacket statusEffectBySkill(Mob mob, int skillID) {
        return statusEffectBySkill(mob, skillID, 0);
    }

    public static OutPacket statusEffectBySkill(Mob mob, int skillID, int chrId) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_STATUS_EFFECT_BY_SKILL);

        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeInt(skillID);
        outPacket.encodeInt(chrId); // v265: the client looks up this user

        return outPacket;
    }

    public static OutPacket effectBySkill(Mob mob, int skillID) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_EFFECT_BY_SKILL);

        outPacket.encodeInt(1); // loop size
        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeInt(skillID);

        return outPacket;
    }

    public static OutPacket affected(Mob mob, int skillID, int slv, boolean userSkill, short delay) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_AFFECTED);

        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeInt(skillID);
        outPacket.encodeShort(delay);
        outPacket.encodeByte(userSkill);
        outPacket.encodeInt(slv);

        return outPacket;
    }

    public static OutPacket freezeEffect(Mob mob) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_FREEZE_EFFECT);

        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeInt(0);

        return outPacket;
    }

    /**
     * The mob's bind list (who incapacitated it, with what, and for how much longer), sent after every stat set.
     */
    public static OutPacket bindRegister(Mob mob, List<MobTemporaryStat.BindInfo> binds) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_BIND_REGISTER);

        long now = System.currentTimeMillis();
        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeByte(false);
        outPacket.encodeInt(binds.size());
        for (MobTemporaryStat.BindInfo bi : binds) {
            outPacket.encodeInt(bi.charId());
            outPacket.encodeInt(bi.value());
            outPacket.encodeInt(bi.skillId());
            outPacket.encodeInt(bi.unk());
            outPacket.encodeInt((int) Math.max(0, bi.endTime() - now));
        }

        return outPacket;
    }

    public static OutPacket move(Mob mob, MobSkillAttackInfo msai, MovementInfo movementInfo) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_MOVE);

        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeByte(msai.actionAndDirMask);
        outPacket.encodeByte(msai.action);

        // decode8
        outPacket.encodeShort(msai.skillID);
        outPacket.encodeShort(msai.slv);
        outPacket.encodeInt(msai.option);

        outPacket.encodeByte(msai.multiTargetForBalls.size());
        for (Position pos : msai.multiTargetForBalls) {
            outPacket.encodePosition(pos);
        }

        outPacket.encodeByte(msai.randTimeForAreaAttacks.size());
        for (short s : msai.randTimeForAreaAttacks) {
            outPacket.encodeShort(s);
        }

        outPacket.encodeInt(msai.idk2);
        if (msai.idk2 != 0) {
            for (var idk : msai.ints2) {
                outPacket.encodeInt(idk); // x11
            }
        }

        outPacket.encodeInt(0);
        // v271 CMob::OnMove (0x143bb2740) decodes the path with the key pad flag 0 (0x143cde610): no key pad bytes
        movementInfo.encodeWithoutKeyPads(outPacket);
        outPacket.encodeByte(msai.teleportDone);

        return outPacket;
    }

    /** Mob skill LtRbDamage (224): mob object id, skill id, level (v265 reference layout) */
    public static OutPacket ltrbDamageSkill(Mob mob, int skillID, int slv) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_LTRB_DAMAGE_SKILL);
        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeInt(skillID);
        outPacket.encodeInt(slv);
        return outPacket;
    }

    public static OutPacket createBounceAttackSkill(Mob mob, MobSkillInfo msi, boolean afterConvexSkill, List<AppearingFoothold> afhs) {
        // 0s are the ones where the wz property to take is unknown
        OutPacket outPacket = new OutPacket(OutHeader.MOB_BOUNCE_ATTACK_SKILL);
        Field field = mob.getField();

        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeInt(msi.getSkillId()); // skillID
        outPacket.encodeInt(msi.getLevel());
        outPacket.encodeByte(afterConvexSkill);
        if (afterConvexSkill) { // save for when an afterConvexSkill is found
            outPacket.encodeInt(afhs.size()); // nCount
            outPacket.encodeByte(true); // bDelayedSkill
            outPacket.encodeInt(msi.getSkillStatIntValue(MobSkillStat.y)); // nCreateY
            outPacket.encodeInt(msi.getSkillStatIntValue(MobSkillStat.x)); // nDensity
            outPacket.encodeInt(msi.getSkillStatIntValue(MobSkillStat.z)); // nFriction
            outPacket.encodeInt(msi.getSkillStatIntValue(MobSkillStat.w)); // nRestitution
            outPacket.encodeInt(msi.getSkillStatIntValue(MobSkillStat.time) * 1000); // tDestroyDelay
            for (AppearingFoothold afh : afhs) {
                outPacket.encodeInt(afh.getObjectId());
            }

        } else {
            outPacket.encodeInt(msi.getSkillStatIntValue(MobSkillStat.x));
            outPacket.encodeInt(msi.getSkillStatIntValue(MobSkillStat.y));
            int count = msi.getSkillStatIntValue(MobSkillStat.count);
            outPacket.encodeInt(count);
            for (int i = 0; i < count; i++) {
                outPacket.encodeInt(field.getNewObjectID()); // Bouncing Object Obj Id
                outPacket.encodePositionInt(msi.getLt());
            }
            outPacket.encodeInt(msi.getSkillStatIntValue(MobSkillStat.z)); // nFriction
            outPacket.encodeInt(msi.getSkillStatIntValue(MobSkillStat.w)); // nRestitution
            outPacket.encodeInt(msi.getSkillStatIntValue(MobSkillStat.time) * 1000); // tDestroyDelay
            outPacket.encodeInt(msi.getSkillStatIntValue(MobSkillStat.delay)); // tStartDelay
            outPacket.encodeInt(0); // unk

            outPacket.encodeByte(msi.getSkillStatIntValue(MobSkillStat.noGravity)); // bNoGravity
            boolean notDestroyByCollide = msi.getSkillStatIntValue(MobSkillStat.notDestroyByCollide) != 0;
            outPacket.encodeByte(notDestroyByCollide); // bNotDestroyByCollide

            if ((msi.getSkillId() != MobSkillID.BounceAttack.getVal() && msi.getSkillId() != MobSkillID.MassivePurpleArea.getVal()) || (msi.getLevel() == 3 || msi.getLevel() == 4 || msi.getLevel() == 21)) {
                outPacket.encodePositionInt(msi.getRb2());
            }
            if (notDestroyByCollide) {
                outPacket.encodePositionInt(msi.getLt2());
                outPacket.encodePositionInt(msi.getRb2());
            }
        }

        return outPacket;
    }

    /** A body part (its name in the mob's data) shows the next stage of its damage (Zakum's body and arms). */
    public static OutPacket partHit(Mob mob, String part) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_PART_HIT);
        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeString(part);
        return outPacket;
    }

    /** Plays a mob skill's action (the WZ skill entry's action, minus 1) and, with withHit, its hit. */
    public static OutPacket skillAction(Mob mob, int actionMinusOne, boolean withHit, boolean flag) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_SKILL_ACTION);
        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeInt(actionMinusOne);
        outPacket.encodeByte(withHit);
        outPacket.encodeByte(flag);
        return outPacket;
    }

    public static OutPacket teleportRequest(Mob mob, int skillAfter, Position toPos) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_TELEPORT_REQUEST);

        outPacket.encodeInt(mob.getObjectId());

        outPacket.encodeByte(skillAfter == 0);
        if (skillAfter != 0) {
            outPacket.encodeInt(skillAfter);
            switch (skillAfter) {
                case 3:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 14:
                case 15:
                case 16:
                case 100:
                    outPacket.encodePositionInt(toPos); // possible position?
                    break;
                case 4:
                    outPacket.encodeInt(toPos.getX());
                    break;
            }
        }

        return outPacket;
    }

    // GMS numbers every delayed attack a Damien makes (1, 2, 3, ... over the fight); the client keys them by it
    private static final java.util.Map<Mob, java.util.concurrent.atomic.AtomicInteger> DEMIAN_ATTACK_IDS =
            java.util.Collections.synchronizedMap(new java.util.WeakHashMap<>());

    private static int nextDemianAttackId(Mob mob) {
        return DEMIAN_ATTACK_IDS.computeIfAbsent(mob, m -> new java.util.concurrent.atomic.AtomicInteger())
                .incrementAndGet();
    }

    public static OutPacket demianDelayedAttackCreate(Mob mob, int skillID, int slv, MobSkillInfo msi) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_DEMIAN_DELAYED_ATTACK_CREATE);

        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeInt(skillID);
        outPacket.encodeInt(slv);
        switch (slv) {
            case 42:
                outPacket.encodeByte(mob.isLeft()); // isLeft
                outPacket.encodeInt(1); // GMS 1
                int loopSize = msi.getSkillStatIntValue(MobSkillStat.x2);
                outPacket.encodeInt(loopSize); // loopSize

                int totaldistance = msi.getSkillStatIntValue(MobSkillStat.y);
                int d = totaldistance / loopSize;
                int originX = mob.getX() + (mob.isLeft() ? totaldistance : -totaldistance);
                Random r = new Random();
                for (int i = 0; i < loopSize; i++) {
                    outPacket.encodeInt(nextDemianAttackId(mob)); // the ball's id
                    outPacket.encodeInt(originX + ((mob.isLeft() ? -d : d) * i)); // xPos
                    outPacket.encodeInt(mob.getY() - msi.getSkillStatIntValue(MobSkillStat.y2)); // yPos
                    outPacket.encodeInt(r.nextInt(50) + 65); // angle
                }
                break;
            case 45:
            case 46:
                outPacket.encodeByte(mob.isLeft());
                outPacket.encodeInt(1); // GMS 1
                outPacket.encodeInt(nextDemianAttackId(mob)); // the attack's id
                outPacket.encodePositionInt(mob.getPosition());
                break;
            case 47:
            case 61:
                outPacket.encodeByte(mob.isLeft());
                outPacket.encodeInt(0); // unk
                outPacket.encodeInt(0); // unk
                outPacket.encodeInt(0); // unk
                outPacket.encodeInt(0); // unk
                break;
        }

        return outPacket;
    }

    public static OutPacket nextAttack(int mobId, int forcedAttackIdx) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_NEXT_ATTACK);

        outPacket.encodeInt(mobId);
        outPacket.encodeInt(forcedAttackIdx);

        return outPacket;
    }

    public static OutPacket setAfterAttack(int mobID, short afterAttack, int serverAction, int attackCount, boolean left) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_SET_AFTER_ATTACK);

        outPacket.encodeInt(mobID);
        outPacket.encodeShort(afterAttack);
        outPacket.encodeInt(attackCount);
        outPacket.encodeInt(serverAction);
        outPacket.encodeByte(left);

        return outPacket;
    }

    public static OutPacket setSkillDelay(int mobID, int skillAfter, int skillID, int slv, int sequenceDelay, List<Rect> rects) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_SKILL_DELAY);

        outPacket.encodeInt(mobID);
        outPacket.encodeInt(skillAfter); // delay in MS
        outPacket.encodeInt(skillID);
        outPacket.encodeInt(slv);
        outPacket.encodeInt(sequenceDelay);

        outPacket.encodeInt(0);
        outPacket.encodeInt(rects.size());
        rects.forEach(r -> r.encode(outPacket));

        return outPacket;
    }

    public static OutPacket escortFullPath(Mob mob, int oldAttr, boolean stopEscort) {
        OutPacket outPacket = new OutPacket(OutHeader.ESCORT_FULL_PATH);

        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeInt(mob.getEscortDest().size());
        outPacket.encodeShort(mob.getPosition().getX());
        outPacket.encodeShort(oldAttr);
        outPacket.encodeInt(mob.getPosition().getY());
        for (EscortDest escortDest : mob.getEscortDest()) {
            outPacket.encodeShort(escortDest.getDestPos().getX());
            outPacket.encodeShort(escortDest.getAttr());
            outPacket.encodeInt(escortDest.getDestPos().getY());
            outPacket.encodeInt(escortDest.getMass());
            if (escortDest.getMass() == 2) {
                outPacket.encodeInt(escortDest.getStopDuration());
            }
        }
        outPacket.encodeInt(mob.getCurrentDestIndex());
        int stopDuration = mob.getEscortStopDuration();
        outPacket.encodeByte(stopDuration > 0);
        if (stopDuration > 0) {
            outPacket.encodeInt(stopDuration);
        }
        outPacket.encodeByte(stopEscort);
        return outPacket;
    }

    public static OutPacket mobForcedSkillAction(Mob mob, MobSkill mobSkill) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_FORCED_SKILL_ACTION);

        outPacket.encodeInt(mob.getObjectId());

        outPacket.encodeInt(mobSkill.getSkillIdx());
        outPacket.encodeByte(false);

        return outPacket;
    }

    public static OutPacket mobAttackBlock(Mob mob, Collection<Integer> attackIds) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_ATTACK_BLOCK);

        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeInt(attackIds.size());
        for (int attackId : attackIds) {
            outPacket.encodeInt(attackId);
        }

        return outPacket;
    }

    public static OutPacket mobAttackPriority(Mob mob, Map<Integer, Collection<Integer>> priorities) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_ATTACK_PRIORITY);

        outPacket.encodeInt(mob.getObjectId());

        outPacket.encodeInt(priorities.size());
        for (var entry : priorities.entrySet()) {
            outPacket.encodeInt(entry.getKey()); // nPriority
            outPacket.encodeInt(entry.getValue().size());
            for (var attackIdx : entry.getValue()) {
                outPacket.encodeInt(attackIdx);
            }
        }

        return outPacket;
    }

    public static OutPacket mobAttackTimeInfo(Mob mob) {
        return mobAttackTimeInfo(mob, Collections.emptyList());
    }

    /**
     * Each entry is {time, attack index, count}; GMS sends {10, 8, -1} at Zakum's last phase.
     */
    public static OutPacket mobAttackTimeInfo(Mob mob, List<int[]> entries) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_ATTACK_TIME_INFO);

        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeInt(entries.size());
        for (int[] e : entries) {
            outPacket.encodeInt(e[0]);
            outPacket.encodeInt(e[1]);
            outPacket.encodeInt(e[2]);
        }

        return outPacket;
    }

    /**
     * Plays the action of one of the mob's attacks. GMS sends it right after each MOB_SKILL_DELAY (Zakum's arms:
     * index 0 for 176 lv 25, 1 for lv 26).
     */
    public static OutPacket mobAttackAction(int mobId, int attackIdx, boolean flag) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_ATTACK_ACTION);

        outPacket.encodeInt(mobId);
        outPacket.encodeInt(attackIdx);
        outPacket.encodeByte(flag);

        return outPacket;
    }

    public static OutPacket burnedInfoRemove(int mobId, List<Char> chrList) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_BURNED_INFO_REMOVE);

        outPacket.encodeInt(mobId);
        outPacket.encodeInt(chrList.size());
        for (Char chr : chrList) {
            outPacket.encodeInt(chr.getId());
        }

        return outPacket;
    }

    public static OutPacket damagedBySkill(int mobId, long damage, int chrId, int skillId, int slv, int attackCount) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_DAMAGED_BY_SKILL);

        outPacket.encodeInt(mobId);

        outPacket.encodeByte(0); // unk
        outPacket.encodeLong(damage);
        outPacket.encodeInt(chrId);
        outPacket.encodeInt(skillId);
        outPacket.encodeInt(slv); // unk
        outPacket.encodeInt(Math.max(1, attackCount)); // the client divides by it
        outPacket.encodeInt(1); // unk
        outPacket.encodeInt(0); // new 218

        int size = 0;
        outPacket.encodeInt(size); // unk loopSize
        for (int i = 0; i < size; i++) {
            outPacket.encodeLong(0);
        }

        return outPacket;
    }

    public static OutPacket forceChase(int mobId, boolean chase) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_FORCE_CHASE);

        outPacket.encodeInt(mobId);
        outPacket.encodeByte(chase);

        return outPacket;
    }

    public static OutPacket timeResist(int mobId, Option o) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_TIME_RESIST);

        outPacket.encodeInt(mobId);
        outPacket.encodeInt(5);
        outPacket.encodeInt(o.rOption);
        outPacket.encodeShort(112);
        outPacket.encodeInt(o.chr != null ? o.chr.getId() : 0);
        outPacket.encodeByte(true);

        return outPacket;
    }

    public static OutPacket airHit(int mobId) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_AIR_HIT);

        outPacket.encodeInt(mobId);
        outPacket.encodeInt(0);

        return outPacket;
    }

    public static OutPacket changePhase(int mobId, int phase) {
        OutPacket outPacket = new OutPacket(OutHeader.CHANGE_PHASE);

        outPacket.encodeInt(mobId);
        outPacket.encodeInt(phase);
        outPacket.encodeByte(false); // something with smart mob notice info if phase == 4444 (hero's gauntlet active? 910029100 ~ 910029120)

        return outPacket;
    }

    public static OutPacket hitBlendEffect(Mob mob, String spineEffectName) {
        OutPacket outPacket = new OutPacket(OutHeader.HIT_BLEND_EFFECT);

        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeString(spineEffectName);

        return outPacket;
    }

    public static OutPacket castingBarSkill(int objectId, MobCastingBarSkill mcbs) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_CASTING_BAR_SKILL);

        outPacket.encodeInt(objectId);
        mcbs.encode(outPacket);

        return outPacket;
    }


    /** Von Bon's time zone ran out (no body but the mob) */
    public static OutPacket breakDownTimeZoneTimeOut(int mobObjectId) {
        OutPacket outPacket = new OutPacket(OutHeader.MOB_BREAK_DOWN_TIME_ZONE_TIME_OUT);
        outPacket.encodeInt(mobObjectId);
        return outPacket;
    }
}
