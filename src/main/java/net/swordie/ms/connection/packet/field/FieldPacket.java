package net.swordie.ms.connection.packet.field;

import net.swordie.ms.client.jobs.adventurer.magician.Bishop;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.CharacterStat;
import net.swordie.ms.client.character.MarriageRecord;
import net.swordie.ms.client.character.chair.PortableChair;
import net.swordie.ms.client.character.info.FarmUserInfo;
import net.swordie.ms.client.character.items.*;
import net.swordie.ms.client.character.modules.ChatModule;
import net.swordie.ms.client.character.runestones.RuneStone;
import net.swordie.ms.client.character.runestones.RuneStoneAckType;
import net.swordie.ms.client.character.runestones.RuneStoneArrows;
import net.swordie.ms.client.character.skills.ExtraSkillInfo;
import net.swordie.ms.client.character.skills.PsychicArea;
import net.swordie.ms.client.character.skills.atom.forceatom.ForceAtom;
import net.swordie.ms.client.character.skills.atom.forceatom.GuideForceAtomInfo;
import net.swordie.ms.client.character.skills.info.ForceAtomInfo;
import net.swordie.ms.client.jobs.adventurer.magician.FirePoison;
import net.swordie.ms.client.jobs.adventurer.warrior.Paladin;
import net.swordie.ms.client.jobs.anima.Lara;
import net.swordie.ms.client.jobs.legend.Phantom;
import net.swordie.ms.client.jobs.nova.Kaiser;
import net.swordie.ms.client.jobs.legend.Shade;
import net.swordie.ms.client.jobs.resistance.BattleMage;
import net.swordie.ms.client.trunk.TrunkDlg;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.QuickMoveInfo;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.*;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.handlers.user.MakingSkillItem;
import net.swordie.ms.life.AffectedArea;
import net.swordie.ms.life.AppearingFoothold;
import net.swordie.ms.life.Wreckage;
import net.swordie.ms.life.pet.Pet;
import net.swordie.ms.loaders.containerclasses.MakingSkillRecipe;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;
import net.swordie.ms.util.container.Triple;
import net.swordie.ms.world.field.ClockPacket;
import net.swordie.ms.world.field.DebuffObject;
import net.swordie.ms.world.field.Portal;
import net.swordie.ms.world.field.fieldeffect.FieldEffect;
import net.swordie.ms.world.field.fieldskill.FieldSkill;
import net.swordie.ms.world.field.obstacleatom.ObstacleAtomInfo;
import net.swordie.ms.world.field.obstacleatom.ObstacleInRowInfo;
import net.swordie.ms.world.field.obstacleatom.ObstacleRadianInfo;

import java.util.*;
import java.util.stream.Collectors;

import static net.swordie.ms.client.jobs.adventurer.magician.IceLightning.BOLT_BARRAGE_TILE;

public class FieldPacket {

    public static OutPacket quickslotInit(List<Integer> keys) {
        OutPacket outPacket = new OutPacket(OutHeader.QUICKSLOT_INIT);

        boolean encode = keys != null && keys.size() > 0;
        outPacket.encodeByte(encode);
        if (encode) {
            for (int i = 0; i < GameConstants.QUICKSLOT_LENGTH; i++) {
                outPacket.encodeInt(i < keys.size() ? keys.get(i) : 0);
            }
        }

        return outPacket;
    }

    public static OutPacket affectedAreaCreated(AffectedArea aa) {
        OutPacket outPacket = new OutPacket(OutHeader.AFFECTED_AREA_CREATED);

        outPacket.encodeInt(aa.getObjectId());
        outPacket.encodeByte(aa.getAaType()); //bMobOwner
        if (aa.getAaType() == 1) {
            outPacket.encodeInt(aa.getMobOwnerOID()); // Mob AA
        } else {
            outPacket.encodeInt(aa.getCharID()); // chr AA
        }
        outPacket.encodeInt(aa.getSkillID());
        outPacket.encodeShort(aa.getSlv());
        outPacket.encodeShort(aa.getDelay());
        aa.getRect().encode(outPacket);
        if (SkillConstants.isWindSwing(aa.getSkillID())) {
            aa.getRect2().encode(outPacket);
        }
        outPacket.encodeInt(aa.getElemAttr());
        // v271: the int that followed here is gone (client 0x141662db0)
        outPacket.encodePosition(aa.getPosition()); // idk, it suddenly offset 50 units
        outPacket.encodeInt(aa.getForce());
        outPacket.encodeInt(aa.getOption());
        outPacket.encodeByte(aa.getIdk2());
        // v265 (89 bytes for a plain area): 3 ints, then the level (mob areas only)
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(aa.getAaType() == 1 ? aa.getMobLv() : 0);
        if (SkillConstants.isFlipAffectAreaSkill(aa.getSkillID())) {
            outPacket.encodeByte(aa.isFlip());
        }
        outPacket.encodeInt(aa.getSkillID() == BOLT_BARRAGE_TILE ? 0 : aa.getDuration()); // duration
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeByte(0); // has hit a mob
        outPacket.encodeByte(1);

        boolean bool = false;
        outPacket.encodeByte(bool);
        if (bool) {
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            int size = 0;
            outPacket.encodeInt(size);
            for (int i = 0; i < size; i++) {
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeString("");
            }
        }
        outPacket.encodeInt(0); // special areas
        if (aa.getSkillID() == SkillConstants.VESTIGE_OF_DIVINITY || aa.getSkillID() == Bishop.HOLY_WATER) { // Vestige of Divinity, Holy Water
            outPacket.encodeInt(0);
        }
        return outPacket;
    }

    public static OutPacket affectedAreaRemoved(AffectedArea aa, boolean mistEruption) {
        OutPacket outPacket = new OutPacket(OutHeader.AFFECTED_AREA_REMOVED);

        outPacket.encodeInt(aa.getObjectId());
        if (aa.getSkillID() == FirePoison.POISON_MIST) {
            // v265 (reference): linked skill, int 0
            outPacket.encodeInt(mistEruption ? FirePoison.POISON_MIST : 0);
            outPacket.encodeInt(0);
        } else {
            outPacket.encodeInt(1); // GMS sends 1
        }

        return outPacket;
    }

    public static OutPacket installedAreaFire(AffectedArea aa, List<AffectedArea> toFire) {
        OutPacket outPacket = new OutPacket(OutHeader.INSTALLED_AREA_FIRE);

        outPacket.encodeInt(aa.getSkillID());
        outPacket.encodeInt(aa.getSlv());
        outPacket.encodeInt(toFire.size());
        for (AffectedArea area : toFire) {
            outPacket.encodeInt(area.getObjectId());
        }

        return outPacket;
    }

    public static OutPacket curNodeEventEnd(boolean enable) {
        OutPacket outPacket = new OutPacket(OutHeader.CUR_NODE_EVENT_END);

        outPacket.encodeByte(enable);

        return outPacket;
    }

    public static OutPacket createForceAtom(ForceAtom fa) {
        OutPacket outPacket = new OutPacket(OutHeader.CREATE_FORCE_ATOM);

        int forceAtomType = fa.getForceAtomEnum().getForceAtomType();
        outPacket.encodeByte(fa.isByMob());
        if (fa.isByMob()) {
            outPacket.encodeInt(fa.getUserOwner());
        }
        outPacket.encodeInt(fa.getCharId());
        outPacket.encodeInt(forceAtomType);

        if (forceAtomType != 36 && forceAtomType != 37 && forceAtomType != 89 && forceAtomType != 91) {

            // Non Target Atoms
            if (forceAtomType == 0 || forceAtomType == 9 || forceAtomType == 14 || forceAtomType == 29 || forceAtomType == 35 || forceAtomType == 42) {
                if (forceAtomType == 42 || forceAtomType == 29) {
                    outPacket.encodeInt(fa.getSkillId());

                    if (fa.getSkillId() == BattleMage.GRIM_HARVEST) {
                        outPacket.encodeInt(2000); // duration gain
                    }
                }

            // Target Atoms
            } else {
                outPacket.encodeByte(fa.isToMob());
                switch (forceAtomType) {
                    case 2:
                    case 3:
                    case 7:
                    case 11:
                    case 12:
                    case 13:
                    case 17:
                    case 19:
                    case 20:
                    case 23:
                    case 24:
                    case 25:
                    case 27:
                    case 28:
                    case 30:
                    case 32:
                    case 34:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 47:
                    case 48:
                    case 49:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                    case 58:
                    case 60:
                    case 64:
                    case 65:
                    // v271 (client 0x1434ac3a0): 66 reads a single target (default below); 115 and 116 read the list
                    case 67:
                    case 68:
                    case 72:
                    case 73:
                    case 75:
                    case 76:
                    case 77:
                    case 84:
                    case 86:
                    case 87:
                    case 90:
                    case 92:
                    case 93:
                    case 95:
                    case 97:
                    case 101:
                    case 110:
                    case 112:
                    case 114:
                    case 115:
                    case 116:
                        outPacket.encodeInt(fa.getTargetIdList().size());
                        for (int i : fa.getTargetIdList()) {
                            outPacket.encodeInt(i);
                            if (forceAtomType == 114) {
                                outPacket.encodeRectInt(fa.getRect());
                            }
                        }
                        break;

                    case 62:
                        outPacket.encodeInt(fa.getTargetIdList().size());
                        for (int i : fa.getTargetIdList()) {
                            outPacket.encodeInt(i);
                        }
                        break;

                    default:
                        outPacket.encodeInt(fa.getTargetIdList().get(0));
                        break;
                }
                outPacket.encodeInt(fa.getSkillId());
            }

        } else {
            outPacket.encodeInt(fa.getSkillId());
        }

        for (ForceAtomInfo fai : fa.getFaiList()) {
            outPacket.encodeByte(1);
            fai.encode(outPacket);
        }
        outPacket.encodeByte(0);
        switch (forceAtomType) {
            case 11:
                outPacket.encodeRectInt(fa.getRect());
                outPacket.encodeInt(fa.getBulletId());
                // goto LABEL_107
                break;
            case 9:
                outPacket.encodeRectInt(fa.getRect());
                // goto LABEL_107
                break;
            case 15:
                outPacket.encodeRectInt(fa.getRect());
                outPacket.encodeByte(0); // Unknown Boolean
                outPacket.encodeInt(0); // v265: summon object id (0 = none)
                break;
            case 29:
                outPacket.encodeRectInt(fa.getRect());
                outPacket.encodePositionInt(fa.getForcedTargetPosition());
                // goto LABEL_107
                break;
            case 4:
            case 16:
            case 20:
            case 26:
            case 30:
            case 33:
            case 61:
            case 64:
            case 67:
                outPacket.encodePositionInt(fa.getPosition());
                // goto LABEL_107
                break;
            // v265 reads a position for these too (client 0x142f516f0)
            case 2:
            case 3:
            case 25:
            case 69:
            case 74:
            case 76:
            case 85:
            case 93:
            case 94:
            case 95:
            case 97:
            case 101:
            case 110:
            case 111:
            case 112:
            case 113:
                outPacket.encodePositionInt(fa.getForcedTargetPosition() != null
                        ? fa.getForcedTargetPosition() : fa.getPosition());
                break;
            case 77:
            case 17:
                outPacket.encodeInt(fa.getArriveDir());
                outPacket.encodeInt(fa.getArriveRange());
                break;
            case 18:
                outPacket.encodePositionInt(fa.getForcedTargetPosition());
            case 27:
                outPacket.encodeRectInt(fa.getRect());
                outPacket.encodeInt(0);
                break;
            case 28:
            case 34:
                outPacket.encodeRectInt(fa.getRect());
                outPacket.encodeInt(fa.getTime());
                break;
            case 57:
            case 58:
            case 86:
            case 87:
                outPacket.encodeRectInt(fa.getRect());
                outPacket.encodeInt(2);
                outPacket.encodePositionInt(fa.getPosition());
                break;
            case 35: // Removed?
                outPacket.encodePositionInt(fa.getForcedTargetPosition());
                break;
            case 36:
            case 39:
            case 89:
            case 90:
                outPacket.encodeInt(fa.getArriveDir()); // Enemies hit
                outPacket.encodeInt(fa.getArriveRange()); // Distance travelled
                outPacket.encodeInt(0);
                outPacket.encodeRectInt(fa.getRect());
                if (forceAtomType == 36 || forceAtomType == 89) {
                    outPacket.encodeRectInt(fa.getRect2());
                    outPacket.encodeInt(0);
                }
                break;
            case 37:
            case 91:
                outPacket.encodeInt(0);
                outPacket.encodeRectInt(fa.getRect());
                outPacket.encodePositionInt(fa.getForcedTargetPosition());
                break;
            case 42:
                outPacket.encodeRectInt(fa.getRect());
                break;
            case 49:
                outPacket.encodeInt(fa.getBulletId());
                outPacket.encodeInt(fa.getUserOwner()); // Summon Obj Id
                outPacket.encodeRectInt(fa.getRect());
                break;
            case 7:
                outPacket.encodeRectInt(fa.getRect());
                break;
            case 50:
                outPacket.encodePositionInt(fa.getForcedTargetPosition());
                outPacket.encodeInt(0); // v265
                break;
        }

        var skillId = fa.getSkillId();

        if (skillId == Phantom.ACE_IN_THE_HOLE_ATOM) {
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
        }

        else if (fa.getSkillId() == Shade.FOX_SPIRITS_ATOM || fa.getSkillId() == Shade.FOX_SPIRITS_ATOM_2) {
            outPacket.encodeInt(0); // idk
        }

        else if (skillId == Kaiser.BLADEFALL_ATTACK_FF) {
            outPacket.encodeInt(0); // v265 reads one int for this skill
        }

        else if (fa.getSkillId() == Paladin.MIGHTY_MJOLNIR) {
            outPacket.encodeInt(0);
            outPacket.encodeByte(0);
        }


        return outPacket;
    }

    public static OutPacket createArkForceAtom(int chrId, int skillId, ForceAtom abyssalFA, ForceAtom gustFA, ForceAtom scarletFA, ForceAtom basicFA) {
        OutPacket outPacket = new OutPacket(OutHeader.CREATE_ARK_FORCE_ATOM);

        outPacket.encodeInt(chrId);
        outPacket.encodeInt(skillId);

        outPacket.encodeInt(4); // size of the different force atoms Ark can get.

        // Abyssal Force Atom
        outPacket.encodeInt(abyssalFA.getForceAtomEnum().getForceAtomType());
        outPacket.encodeInt(abyssalFA.getSkillId());
        outPacket.encodeInt(abyssalFA.getTargetIdList().size());
        for (int i = 0; i < abyssalFA.getTargetIdList().size(); i++) {
            outPacket.encodeInt(abyssalFA.getTargetIdList().get(i));
            outPacket.encodeByte(abyssalFA.getFaiList().size());
            //for (ForceAtomInfo fai : gustFA.getFaiList()) {
            abyssalFA.getFaiList().get(i).encode(outPacket);
            outPacket.encodeByte(0); // unk
            //}
        }

        // Gust Force Atom
        outPacket.encodeInt(gustFA.getForceAtomEnum().getForceAtomType());
        outPacket.encodeInt(gustFA.getSkillId());
        outPacket.encodeInt(gustFA.getTargetIdList().size());
        for (int i = 0; i < gustFA.getTargetIdList().size(); i++) {
            outPacket.encodeInt(gustFA.getTargetIdList().get(i));
            outPacket.encodeByte(gustFA.getFaiList().size());
            //for (ForceAtomInfo fai : gustFA.getFaiList()) {
            gustFA.getFaiList().get(i).encode(outPacket);
            outPacket.encodeByte(0); // unk
            //}
        }

        // Scarlet Force Atom
        outPacket.encodeInt(scarletFA.getForceAtomEnum().getForceAtomType());
        outPacket.encodeInt(scarletFA.getSkillId());
        outPacket.encodeInt(scarletFA.getTargetIdList().size());
        for (int i = 0; i < scarletFA.getTargetIdList().size(); i++) {
            outPacket.encodeInt(scarletFA.getTargetIdList().get(i));
            outPacket.encodeByte(scarletFA.getFaiList().size());
            //for (ForceAtomInfo fai : gustFA.getFaiList()) {
            scarletFA.getFaiList().get(i).encode(outPacket);
            outPacket.encodeByte(0); // unk
            //}
        }

        // Basic Force Atom
        outPacket.encodeInt(basicFA.getForceAtomEnum().getForceAtomType());
        outPacket.encodeInt(basicFA.getSkillId());
        outPacket.encodeInt(basicFA.getTargetIdList().size());
        for (int i = 0; i < basicFA.getTargetIdList().size(); i++) {
            outPacket.encodeInt(basicFA.getTargetIdList().get(i));
            outPacket.encodeByte(basicFA.getFaiList().size());
            //for (ForceAtomInfo fai : gustFA.getFaiList()) {
            basicFA.getFaiList().get(i).encode(outPacket);
            outPacket.encodeByte(0); // unk
            //}
        }

        return outPacket;
    }

    public static OutPacket guideForceAtom(int chrId, int faKey, int mobId) {
        OutPacket outPacket = new OutPacket(OutHeader.GUIDE_FORCE_ATOM);

        outPacket.encodeInt(faKey);
        outPacket.encodeInt(chrId);
        outPacket.encodeInt(1);
        outPacket.encodeInt(1);
        outPacket.encodeInt(1);
        outPacket.encodeInt(mobId);

        return outPacket;
    }

    public static OutPacket guideForceAtom(GuideForceAtomInfo gfai) {
        OutPacket outPacket = new OutPacket(OutHeader.GUIDE_FORCE_ATOM);

        outPacket.encodeInt(gfai.faKey);
        outPacket.encodeInt(gfai.chrId);
        outPacket.encodeInt(gfai.type);
        outPacket.encodeInt(gfai.interactionObjId);
        switch (gfai.type) {
            case 1:
                outPacket.encodeRectShort(gfai.rect);
                break;
            case 2:
                outPacket.encodeInt(gfai.count);
                outPacket.encodeByte(gfai.flip);
                outPacket.encodeRectShort(gfai.rect);
                break;
            case 3:
                outPacket.encodeInt(gfai.path1);
                outPacket.encodeInt(gfai.path2);
                break;
            default:
                outPacket.encodeInt(gfai.count);
                outPacket.encodeInt(gfai.mobId);
                break;
        }

        return outPacket;
    }

    public static OutPacket removeForceAtom(int type, int chrId, int faKey) {
        return removeForceAtom(type, chrId, Collections.singletonList(faKey));
    }

    public static OutPacket removeForceAtom(int type, int chrId, List<Integer> faKeys) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOVE_FORCE_ATOM);

        outPacket.encodeInt(type);
        switch (type) {
            case 1: // Remove by faKey & ChrId
            case 2: // Remove by faKey & ChrId
                if (faKeys.size() > 0) {
                    outPacket.encodeInt(faKeys.get(0));
                    outPacket.encodeInt(chrId);
                } else {
                    outPacket.encodeInt(0);
                    outPacket.encodeInt(0);
                }
                break;
            case 3:
                outPacket.encodeInt(faKeys.size());
                for (var faKey : faKeys) {
                    outPacket.encodeInt(faKey);
                }
                outPacket.encodeInt(chrId);
                break;
        }

        return outPacket;
    }

    public static OutPacket finalAttackRequest(Char chr, int skillID, int finalSkillID, List<Integer> mobsHit) {
        OutPacket outPacket = new OutPacket(OutHeader.FINAL_ATTACK_REQUEST);

        WeaponType wt = ItemConstants.getWeaponType(chr.getEquippedItemByBodyPart(BodyPart.Weapon).getItemId());

        outPacket.encodeInt(Util.getCurrentTime());
        outPacket.encodeInt(1); // success
        outPacket.encodeInt(skillID);
        outPacket.encodeInt(finalSkillID);
        outPacket.encodeInt(wt == null ? 0 : wt.getVal());

        outPacket.encodeInt(mobsHit.size()); // Mob Loop
        for (var mob : mobsHit) {
            outPacket.encodeInt(mob);
        }

        return outPacket;
    }

    public static OutPacket setAmmo(int ammo) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_AMMO);

        outPacket.encodeInt(ammo);

        return outPacket;
    }

    public static OutPacket createPsychicArea(int charID, PsychicArea pa) {
        OutPacket outPacket = new OutPacket(OutHeader.CREATE_PSYCHIC_AREA);

        outPacket.encodeInt(charID);

        outPacket.encodeByte(pa.success);
        outPacket.encodeInt(pa.action);
        outPacket.encodeInt(pa.actionSpeed);
        outPacket.encodeInt(pa.psychicAreaKey);
        outPacket.encodeInt(pa.skillID);
        outPacket.encodeShort(pa.slv);
        outPacket.encodeInt(pa.localPsychicAreaKey);
        outPacket.encodeInt(pa.duration);
        outPacket.encodeByte(pa.isLeft);
        outPacket.encodeShort(pa.skeletonFilePathIdx);
        outPacket.encodeShort(pa.skeletonAniIdx);
        outPacket.encodeShort(pa.skeletonLoop);
        outPacket.encodePositionInt(pa.start);

        return outPacket;
    }

    /** The info window for another character, in the v265 layout (the reference server's CHARACTER_INFO_REMOTE) */
    public static OutPacket characterInfo(Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.CHARACTER_INFO);

        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        var guild = chr.getGuild();
        var alliance = guild == null ? null : guild.getAlliance();
        var union = chr.getUnion();
        var unionBoard = union == null ? null : union.getBoardByPreset(union.getActivePreset());

        // v271 client 0x145b151b0
        outPacket.encodeInt(0); // non-zero: the window shows an error instead
        outPacket.encodeInt(chr.getId());
        net.swordie.ms.client.character.CharEncoder.encodeV271Minimal(chr, outPacket);
        outPacket.encodeInt(0); // 0x140793990: keyed records
        outPacket.encodeByte(0); // second keyed list follows when set
        outPacket.encodeInt(0); // 0x14054a300: three counted lists
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);

        // 0x1410d6f30
        outPacket.encodeByte(cs.getGender());
        outPacket.encodeShort(cs.getLevel());
        outPacket.encodeInt(chr.getJob());
        outPacket.encodeInt(cs.getStr());
        outPacket.encodeInt(cs.getDex());
        outPacket.encodeInt(cs.getInt());
        outPacket.encodeInt(cs.getLuk());
        outPacket.encodeInt(cs.getPop());
        outPacket.encodeInt(chr.getMaxHP());
        outPacket.encodeInt(chr.getMaxMP());
        outPacket.encodeInt(chr.getMaxHP());
        for (int i = 0; i < 8; i++) {
            outPacket.encodeInt(0);
        }
        outPacket.encodeLong(Double.doubleToLongBits(1.0));
        outPacket.encodeLong(Double.doubleToLongBits(1.0));
        outPacket.encodeInt(0);

        outPacket.encodeInt(0); // forced stat mask (0x1410cdf20)
        var tsm = chr.getTemporaryStatManager();
        synchronized (tsm.currentStats) {
            net.swordie.ms.client.character.skills.temp.V271LocalStats.encodeSet(outPacket, tsm, tsm.getCurrentStats());
        }

        outPacket.encodeByte(0); // 0: pets follow
        for (Pet pet : chr.getPets()) {
            PetItem pi = pet.getItem();
            outPacket.encodeByte(1);
            outPacket.encodeInt(pet.getIdx());
            outPacket.encodeInt(pi.getBagIndex());
            pi.encode(outPacket);
        }
        outPacket.encodeByte(0);

        outPacket.encodeArr(new byte[23]);
        for (int i = 0; i < 15; i++) {
            outPacket.encodeInt(0);
        }
        for (int i = 0; i < 40; i++) {
            outPacket.encodeByte(-1);
        }

        outPacket.encodeInt(0); // familiars
        outPacket.encodeInt(0);
        outPacket.encodeString(alliance != null ? alliance.getName() : "");
        outPacket.encodeString(guild != null ? guild.getName() : "");
        outPacket.encodeInt(0); // Mu Lung floor
        outPacket.encodeString("");
        outPacket.encodeInt(unionBoard != null ? unionBoard.getUnionPower() : 0);
        outPacket.encodeLong(0);
        outPacket.encodeByte(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeByte(0);
        outPacket.encodeByte(0);
        outPacket.encodeByte(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeByte(0);
        outPacket.encodeLong(chr.getCombatPower());
        outPacket.encodeInt(0);

        outPacket.encodeInt(chr.getNickItem());
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeByte(0);

        List<net.swordie.ms.client.character.skills.Skill> guildSkills = new ArrayList<>();
        List<net.swordie.ms.client.character.skills.Skill> unionSkills = new ArrayList<>();
        for (var skill : chr.getSkills()) {
            int id = skill.getSkillId();
            if (id >= SkillConstants.GUILD_SKILL_BASE && id <= 91001050) { // hardcode-ok
                guildSkills.add(skill);
            } else if (id >= 71000000 && id <= 71010000) { // hardcode-ok
                unionSkills.add(skill);
            }
        }
        outPacket.encodeInt(guildSkills.size());
        for (var skill : guildSkills) {
            outPacket.encodeInt(skill.getSkillId());
            outPacket.encodeInt(skill.getCurrentLevel());
        }
        outPacket.encodeInt(unionSkills.size());
        for (var skill : unionSkills) {
            outPacket.encodeInt(skill.getSkillId());
            outPacket.encodeInt(skill.getCurrentLevel());
        }

        outPacket.encodeInt(5);
        for (int i = 0; i < 5; i++) {
            outPacket.encodeInt(-1);
        }
        outPacket.encodeByte(true);
        outPacket.encodeByte(0);
        outPacket.encodeByte(0);
        outPacket.encodeByte(0);
        outPacket.encodeByte(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeByte(0);
        outPacket.encodeString("");
        outPacket.encodeInt(0);
        outPacket.encodeByte(0);
        outPacket.encodeByte(0); // GMS sends one byte more than the client reads

        return outPacket;
    }

    /** Arkarium's Screen Crack: type 1 = warning timer, type 2 = crack timer with the warning text. */
    public static OutPacket arkariumScreenCrack(int type, int ms) {
        OutPacket outPacket = new OutPacket(OutHeader.ARKARIUM_SCREEN_CRACK);

        outPacket.encodeInt(type);
        outPacket.encodeInt(ms);

        return outPacket;
    }

    public static OutPacket showItemUpgradeEffect(int charID, boolean success, boolean enchantDlg, int uItemID, int eItemID, boolean boom) {
        OutPacket outPacket = new OutPacket(OutHeader.SHOW_ITEM_UPGRADE_EFFECT);

        outPacket.encodeInt(charID);

        // v265 reads byte success, int, int (the ints are read and dropped)
        outPacket.encodeByte(success && !boom);
        outPacket.encodeInt(uItemID);
        outPacket.encodeInt(eItemID);

        return outPacket;
    }

    public static OutPacket showItemReleaseEffect(int charID, short pos, boolean bonus) {
        OutPacket outPacket = new OutPacket(OutHeader.SHOW_ITEM_RELEASE_EFFECT);

        outPacket.encodeInt(charID);

        outPacket.encodeShort(pos);
        outPacket.encodeByte(bonus);

        return outPacket;
    }

    public static OutPacket hyperUpgradeDisplay(Equip equip, boolean downgradeable, long meso, long beforeMvp,
                                                long beforePc, int[] chances, boolean chanceTime,
                                                boolean extraCount, int mode) {
        OutPacket outPacket = new OutPacket(OutHeader.EQUIPMENT_ENCHANT);

        // v271 layout (client 0x1407f1ff0)
        outPacket.encodeByte(EquipmentEnchantType.HyperUpgradeDisplay.getVal());
        // the window shows "before" (MVP, else PC, else plain before-discount) struck out next to the cost to pay
        outPacket.encodeLong(meso); // cost to pay
        outPacket.encodeLong(meso); // before discounts
        outPacket.encodeLong(beforeMvp); // before the MVP discount (0 = none)
        outPacket.encodeLong(beforePc); // before the PC room discount (0 = none)
        outPacket.encodeByte(beforeMvp != 0); // bMvp
        outPacket.encodeByte(beforePc != 0 && beforeMvp == 0); // pc room
        outPacket.encodeByte(0); // v271
        // the window shows success x1.05 (out of 10000) and destroy as share x (10000 - that) (client 0x1421f73e0)
        outPacket.encodeInt(chances[0]); // success, out of 1000
        outPacket.encodeInt(chances[1]); // destroyed share of a failure, out of 1000
        outPacket.encodeInt(downgradeable ? 1 : 0);
        outPacket.encodeInt(0); // boosted success, shown over the above when non-zero
        outPacket.encodeInt(0); // boosted destroy share
        outPacket.encodeByte(chanceTime);
        outPacket.encodeInt(equip.getChuc() + 1); // star count after a success
        outPacket.encodeInt(mode); // the client asks again while this differs from its mode
        TreeMap<EnchantStat, Integer> vals = equip.getHyperUpgradeStats();
        int mask = 0;
        for (EnchantStat es : vals.keySet()) {
            mask |= es.getVal();
        }
        outPacket.encodeByte(0); // v271 stat block flag (0x1407f12e0)
        outPacket.encodeInt(mask);
        vals.forEach((es, val) -> outPacket.encodeInt(val));
        if (extraCount) {
            outPacket.encodeInt(0); // read only when the request's last byte was set
        }

        return outPacket;
    }

    public static OutPacket miniGameDisplay(EquipmentEnchantType eeType) {
        OutPacket outPacket = new OutPacket(OutHeader.EQUIPMENT_ENCHANT);

        outPacket.encodeByte(eeType.getVal());
        outPacket.encodeByte(0);
        outPacket.encodeInt(2000); // TODO nSeed

        return outPacket;
    }

    public static OutPacket showUpgradeResult(Equip oldEquip, Equip equip, boolean succeed, boolean boom, boolean canDegrade) {
        OutPacket outPacket = new OutPacket(OutHeader.EQUIPMENT_ENCHANT);

        // v265 (client 0x141d8c2b0): no item copies, the window reads the updated equip from the inventory
        outPacket.encodeByte(EquipmentEnchantType.ShowHyperUpgradeResult.getVal());
        outPacket.encodeInt(boom ? 2 : succeed ? 1 : canDegrade ? 0 : 3);
        outPacket.encodeByte(0);
        outPacket.encodeInt(0); // item id shown in auto mode

        return outPacket;
    }

    public static OutPacket showUnknownEnchantFailResult(byte msg) {
        OutPacket outPacket = new OutPacket(OutHeader.EQUIPMENT_ENCHANT);

        outPacket.encodeByte(EquipmentEnchantType.ShowUnknownFailResult.getVal()); // no payload

        return outPacket;
    }

    public static OutPacket scrollUpgradeDisplay(boolean feverTime, List<ScrollUpgradeInfo> infos) {
        OutPacket outPacket = new OutPacket(OutHeader.EQUIPMENT_ENCHANT);

        outPacket.encodeByte(EquipmentEnchantType.ScrollUpgradeDisplay.getVal());
        outPacket.encodeByte(feverTime);

        outPacket.encodeByte(infos.size());
        for (int i = 0; i < infos.size(); i++) {
            infos.get(i).encode(outPacket, i);
        }

        return outPacket;
    }

    public static OutPacket showScrollUpgradeResult(boolean feverAfter, int result, String desc, Equip prevEquip,
                                                    Equip newEquip) {
        OutPacket outPacket = new OutPacket(OutHeader.EQUIPMENT_ENCHANT);

        outPacket.encodeByte(EquipmentEnchantType.ShowScrollUpgradeResult.getVal());

        outPacket.encodeByte(feverAfter);
        outPacket.encodeInt(result);
        outPacket.encodeString(desc);
        outPacket.encode(prevEquip);
        outPacket.encode(newEquip);

        return outPacket;
    }

    /**
     * Scroll tab result for an inventory scroll used from the window (client 0x1421fa950 case 101). The client ignores
     * it unless the window's selected scroll sits at scrollPos. Result: 0 fail, 1 success, 2 destroyed.
     */
    public static OutPacket showScrollUseResult(int scrollPos, int scrollId, int result, boolean prot) {
        OutPacket outPacket = new OutPacket(OutHeader.EQUIPMENT_ENCHANT);

        outPacket.encodeByte(EquipmentEnchantType.ShowScrollUseResult.getVal());
        outPacket.encodeInt(scrollPos);
        outPacket.encodeInt(scrollId);
        outPacket.encodeByte(0); // no stat block (0x1407f12e0: byte flag, int mask, an int per mask bit)
        outPacket.encodeInt(0);
        outPacket.encodeByte(result);
        outPacket.encodeByte(0); // non-zero keeps the window waiting
        outPacket.encodeByte(prot); // "protected" instead of "fail"

        return outPacket;
    }

    /**
     * Trace Restoration done (client tabs 7 / 10, 0x142202a40 / 0x1422085d0): plays the effect, then selects the item
     * at the position (negative = equipped).
     */
    public static OutPacket showTraceRestorationResult(int pos) {
        OutPacket outPacket = new OutPacket(OutHeader.EQUIPMENT_ENCHANT);

        outPacket.encodeByte(EquipmentEnchantType.ShowTraceRestorationResult.getVal());
        outPacket.encodeInt(pos);

        return outPacket;
    }

    // v232 layouts, unused: v265 removed the red/bonus cube result packets (RED/BONUS stay unbound). 981 is the
    // Chaos Ring self-enchant window (SELF_ENCHANT_RESULT).
    public static OutPacket redCubeResult(Char chr, boolean upgrade, int cubeID, int ePos, Equip equip) {
        OutPacket outPacket = new OutPacket(OutHeader.RED_CUBE_RESULT);

        outPacket.encodeInt(chr.getId());
        outPacket.encodeByte(upgrade);
        outPacket.encodeInt(cubeID);
        outPacket.encodeInt(ePos);
        outPacket.encodeInt(chr.getCashInventory().getQuantity(cubeID) - 1); //Do -1 because we consume cube after this UI is shown.
        equip.encode(outPacket);

        return outPacket;
    }

    public static OutPacket bonusCubeResult(Char chr, boolean upgrade, int cubeID, int ePos, Equip equip) {
        OutPacket outPacket = new OutPacket(OutHeader.BONUS_CUBE_RESULT);

        outPacket.encodeInt(chr.getId());
        outPacket.encodeByte(upgrade);
        outPacket.encodeInt(cubeID);
        outPacket.encodeInt(ePos);
        outPacket.encodeInt(chr.getCashInventory().getQuantity(cubeID) - 1); //Do -1 because we consume cube after this UI is shown.
        equip.encode(outPacket);

        return outPacket;
    }

    public static OutPacket sitResult(int chrId, int fieldSeatId) {
        OutPacket outPacket = new OutPacket(OutHeader.SIT_RESULT);

        outPacket.encodeInt(chrId);
        outPacket.encodeByte(!(fieldSeatId == -1));
        if (!(fieldSeatId == -1)) {
            outPacket.encodeShort(fieldSeatId);
        }

        return outPacket;
    }


    public static OutPacket questClear(int qrKey) {
        OutPacket outPacket = new OutPacket(OutHeader.QUEST_CLEAR);

        outPacket.encodeInt(qrKey);

        return outPacket;
    }

    public static OutPacket setQuestTime(List<Triple<Integer, FileTime, FileTime>> questTimes) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_QUEST_TIME);

        outPacket.encodeByte(questTimes.size());
        for (Triple<Integer, FileTime, FileTime> times : questTimes) {
            outPacket.encodeInt(times.getLeft());
            outPacket.encodeFT(times.getMiddle());
            outPacket.encodeFT(times.getRight());
        }

        return outPacket;
    }

    public static OutPacket addWreckage(Wreckage wreckage, int wreckageCount) {
        OutPacket outPacket = new OutPacket(OutHeader.ADD_WRECKAGE);

        outPacket.encodeInt(wreckage.getOwnerId());  // owner Id
        outPacket.encodePositionInt(wreckage.getPosition());
        outPacket.encodeInt(wreckage.getDuration());  // duration
        outPacket.encodeInt(0); // v265: added to the duration, delay?
        outPacket.encodeInt(wreckage.getObjectId());  //evanWreckage.nIDx
        outPacket.encodeInt(wreckage.getSkillId());  //nSkillID
        outPacket.encodeInt(wreckage.getType());  //nType

        outPacket.encodeInt(wreckageCount);  //Number on Skill Icon, # of Wreckages on map

        return outPacket;
    }

    public static OutPacket delWreckage(Char chr, List<Wreckage> wreckageList) {
        OutPacket outPacket = new OutPacket(OutHeader.DEL_WRECKAGE);

        outPacket.encodeInt(chr.getId()); // Owner Id
        outPacket.encodeInt(wreckageList.size()); //Count
        outPacket.encodeByte(true); //Unk Boolean
        outPacket.encodeByte(true); //Unk Boolean
        for (Wreckage wreckage : wreckageList) {
            outPacket.encodeInt(wreckage.getObjectId()); // Wreckage Id
        }

        return outPacket;
    }

    public static OutPacket whisper(WhisperType type, Char chr, String msg, boolean foundDest, Item item, String itemName) {
        OutPacket outPacket = new OutPacket(OutHeader.WHISPER);

        outPacket.encodeByte(type.getVal());
        switch (type) {
            case Find:
            case Find_2:
                outPacket.encodeString("");
                outPacket.encodeByte(false);
                outPacket.encodeInt(0);
                break;

            case SaveName:
            case SaveName_2:
                outPacket.encodeByte(0); // v265: flag byte before the name
                outPacket.encodeString(msg);
                outPacket.encodeByte(foundDest);
                break;

            case In:
                outPacket.encodeInt(0); // v265
                outPacket.encodeString(chr.getName());
                outPacket.encodeInt(chr.getId());
                outPacket.encodeByte(chr.getChannel() - 1); // channel
                outPacket.encodeByte(chr.getUser().getAccountType().isGmOrAdmin() && item == null); // gm
                outPacket.encodeInt(chr.getAccount().getWorldId()); // v265
                outPacket.encodeString(msg);

                chr.encodeChatInfo(outPacket, msg);

                ChatModule.encodeLinkedMessage(outPacket, item, ChatModule.LinkedMessageType.Item);
                outPacket.encodeByte(0); // v265
                break;

            case Unk19:
                outPacket.encodeString(chr.getName());
                outPacket.encodeInt(chr.getId());
                outPacket.encodeByte(chr.getChannel() - 1); // channel
                outPacket.encodeString(msg);

                chr.encodeChatInfo(outPacket, msg);

                ChatModule.encodeLinkedMessage(outPacket, item, ChatModule.LinkedMessageType.Item);
                break;

            case Unk34:
                outPacket.encodeString("");
                outPacket.encodeByte(false);
                break;

            case Unk146:
                outPacket.encodeString("");
                outPacket.encodeByte(false);
                outPacket.encodeString("");
                break;
        }

        return outPacket;
    }

    public static OutPacket teleport(Position pos, Char chr) {
        return teleport(pos, chr, null);
    }

    public static OutPacket teleport(Position position, Char chr, Portal portal) {
        OutPacket outPacket = new OutPacket(OutHeader.TELEPORT);
        if (portal == null) {
            // v265: excl 1, calling type 6, char id, position
            outPacket.encodeByte(true);
            outPacket.encodeByte(6);
            outPacket.encodeInt(chr.getId());
            outPacket.encodePosition(position);
            // v271: byte (set: hold the position while the user is busy) and an int passed to the move
            outPacket.encodeByte(0);
            outPacket.encodeInt(0);
        } else {
            outPacket.encodeByte(false); // exclRequest
            outPacket.encodeByte(0); // portal form: 4 portal, 1 (same in v271)
            outPacket.encodeInt(portal.getId());
            outPacket.encodeByte(false);
        }

        return outPacket;
    }

    public static OutPacket fieldEffect(FieldEffect fieldEffect) {
        OutPacket outPacket = new OutPacket(OutHeader.FIELD_EFFECT);

        fieldEffect.encode(outPacket);

        return outPacket;
    }

    public static OutPacket removeBlowWeather() {
        return blowWeather(0, null);
    }

    public static OutPacket blowWeather(int itemID, String message) {
        return blowWeather(itemID, message, 5);
    }

    public static OutPacket blowWeather(int itemID, String message, int durationSec) {
        OutPacket outPacket = new OutPacket(OutHeader.BLOW_WEATHER);

        outPacket.encodeInt(itemID);
        if (itemID > 0) {
            outPacket.encodeString(message);
            outPacket.encodeInt(durationSec);

            var encodePackedAL = false;
            outPacket.encodeByte(encodePackedAL);
            if (encodePackedAL) {
                // encode packed avatar look (480 bytes)
            }
        }
        outPacket.encodeByte(0); // v271: GMS sets it for some items; 1 drops the message in stage types 4/5
        return outPacket;
    }

    public static OutPacket trunkDlg(TrunkDlg trunkDlg) {
        OutPacket outPacket = new OutPacket(OutHeader.TRUNK_DLG);

        trunkDlg.encode(outPacket);

        return outPacket;
    }

    public static final int TRANSFER_IGNORED_CHANNEL = 1;
    public static final int TRANSFER_IGNORED_CASH_SHOP = 2;

    /** Clears the client's pending channel / cash shop move and shows its notice (TRANSFER_IGNORED_*). */
    public static OutPacket transferChannelReqIgnored(int reason) {
        OutPacket outPacket = new OutPacket(OutHeader.TRANSFER_CHANNEL_REQ_IGNORED);

        outPacket.encodeByte(reason);

        return outPacket;
    }

    public static OutPacket openUIWithOption(int uiID, int option, int[] extraOptions) {
        OutPacket outPacket = new OutPacket(OutHeader.OPEN_UI_WITH_OPTION);

        outPacket.encodeInt(uiID);
        outPacket.encodeInt(option);
        outPacket.encodeInt(extraOptions.length);
        for (int extra : extraOptions) {
            outPacket.encodeInt(extra);
        }

        return outPacket;
    }

    public static OutPacket openUI(UIType uiType) {
        return openUI(uiType.getVal());
    }

    public static OutPacket openUI(int uiID) {
        OutPacket outpacket = new OutPacket(OutHeader.OPEN_UI);

        outpacket.encodeInt(uiID);

        return outpacket;
    }

    public static OutPacket closeUI(UIType uiType) {
        return closeUI(uiType.getVal());
    }

    public static OutPacket closeUI(int uiID) {
        OutPacket outpacket = new OutPacket(OutHeader.CLOSE_UI);
        outpacket.encodeInt(uiID);
        return outpacket;
    }

    public static OutPacket socketCreateResult(boolean success) {
        OutPacket outPacket = new OutPacket(OutHeader.SOCKET_CREATE_RESULT);

        outPacket.encodeByte(success ? 2 : 3);

        return outPacket;
    }

    public static OutPacket changeMobZone(int mobID, int zoneId) {
        OutPacket outPacket = new OutPacket(OutHeader.CHANGE_MOB_ZONE);

        outPacket.encodeInt(mobID);
        outPacket.encodeInt(zoneId);

        return outPacket;
    }

    public static OutPacket createObstacle(ObstacleAtomCreateType oact, ObstacleInRowInfo oiri, ObstacleRadianInfo ori,
                                           Set<ObstacleAtomInfo> atomInfos) {
        OutPacket outPacket = new OutPacket(OutHeader.CREATE_OBSTACLE);

        outPacket.encodeInt(0); // ? gets used in 1 function, which forwards it to another, which does nothing with it
        outPacket.encodeInt(atomInfos.size());
        outPacket.encodeByte(oact.getVal());
        if (oact == ObstacleAtomCreateType.IN_ROW) {
            oiri.encode(outPacket);
        } else if (oact == ObstacleAtomCreateType.RADIAL) {
            ori.encode(outPacket);
        }
        for (ObstacleAtomInfo atomInfo : atomInfos) {
            outPacket.encodeByte(true); // false -> no encode
            atomInfo.encode(outPacket);
            if (oact == ObstacleAtomCreateType.DIAGONAL) {
                atomInfo.getObstacleDiagonalInfo().encode(outPacket);
            }
        }

        return outPacket;
    }

    public static OutPacket enableDebuffObj(List<DebuffObject> debuffObjects){
        OutPacket outPacket = new OutPacket(OutHeader.DEBUFF_OBJ_ON);
        for(DebuffObject debuffObject : debuffObjects) {
            outPacket.encodeByte(debuffObject.isEnabled());
            outPacket.encodeInt(debuffObject.getId());//id, gets returned in the return packet
            outPacket.encodeInt(debuffObject.getDebuffObjectType().getVal()); //Type
            outPacket.encodeString(debuffObject.getName());
            outPacket.encodeString(debuffObject.getEffectPath()); //Effect.wz/DebuffObjEff/ string
            outPacket.encodeByte(0);//?
        }
        outPacket.encodeByte(0);//?
        return outPacket;
    }
    
    public static OutPacket setChaseEffect(Char chr, int mobObjectID, boolean enabled){
        OutPacket outPacket = new OutPacket(OutHeader.CHASE_EFFECT_SET);
        outPacket.encodeInt(chr.getId());
        outPacket.encodeShort(enabled == true ? 1 : 0);
        outPacket.encodeInt(mobObjectID);
        return outPacket;
    }

    public static OutPacket openDimensionalMirror() {
        OutPacket outPacket = new OutPacket(OutHeader.DIMENSIONAL_MIRROR);
        // v265 entry (client 0x1411e7320): name, description, int level, int card slot (0-2; any other slot is not
        // drawn), int required quest, int id, int n, n reward items. Level and quest lock the card. The window draws
        // at most 5 reward icons per card. Slot 0 is the locked placeholder card.
        var slots = DimensionalMirrorType.SLOTS;
        outPacket.encodeInt(slots.length);
        for (int slot = 0; slot < slots.length; slot++) {
            DimensionalMirrorType dmt = slots[slot];
            boolean open = dmt != null && dmt.isAvailable();
            outPacket.encodeString(dmt != null ? dmt.getName() : "");
            outPacket.encodeString(open ? dmt.getDesc() : "");
            outPacket.encodeInt(open ? dmt.getReqLv() : 999);
            outPacket.encodeInt(slot);
            outPacket.encodeInt(open ? dmt.getQuestID() : 0);
            outPacket.encodeInt(open ? dmt.getID() : 0);
            var items = open ? dmt.getItems().subList(0, Math.min(5, dmt.getItems().size())) : java.util.List.<Integer>of();
            outPacket.encodeInt(items.size());
            for (int item : items) {
                outPacket.encodeInt(item);
            }
        }
        return outPacket;
    }

    public static OutPacket runeStoneAppear(RuneStone runeStone) { //Spawn in RuneStone
        OutPacket outPacket = new OutPacket(OutHeader.RUNE_STONE_APPEAR);

        outPacket.encodeInt(0); // object id ??
        outPacket.encodeInt(runeStone.getEventType().ordinal());
        outPacket.encodeInt(2); // new 202, != 2 = crash
        outPacket.encodeInt(runeStone.getRuneType().getVal()); // Rune Type

        outPacket.encodePositionInt(runeStone.getPosition()); // Position
        outPacket.encodeByte(runeStone.isFlip()); // flip

        return outPacket;
    }

    public static OutPacket completeRune(Char chr) { //RuneStone Disappears
        OutPacket outPacket = new OutPacket(OutHeader.COMPLETE_RUNE);

        outPacket.encodeInt(0);
        outPacket.encodeInt(chr.getId());

        return outPacket;
    }

    public static OutPacket runeStoneUseAck(int mapId, RuneStoneAckType type) {
        return runeStoneUseAck(mapId, type, 0, null, 0);
    }

    public static OutPacket runeStoneUseAck(int mapId, RuneStoneAckType type, int runestoneId, int[] arrows, int remainingTime) {
        OutPacket outPacket = new OutPacket(OutHeader.RUNE_STONE_USE_ACK);

        outPacket.encodeInt(type.getVal());
        switch (type) {
            case CantUseAnotherRuneYet:
            case CantUseAnotherRuneYet2:
                outPacket.encodeInt(remainingTime); // RemainingTime MS
                break;
        }

        outPacket.encodeLong(0);

        return outPacket;
    }

    /** Type 9: starts the arrow mini-game. The client judges the arrows and answers with RUNE_STONE_END_NOTICE. */
    public static OutPacket runeStoneShowArrows(RuneStoneArrows arrows) {
        OutPacket outPacket = new OutPacket(OutHeader.RUNE_STONE_USE_ACK);

        outPacket.encodeInt(RuneStoneAckType.SecureShowArrows.getVal());
        outPacket.encodeByte(0);
        outPacket.encodeByte(1); // GMS sends 1; with 0 the client takes another mini-game path
        outPacket.encodeByte(0);
        outPacket.encodeArr(arrows.getBlock()); // key, length, encrypted body

        return outPacket;
    }

    public static OutPacket runeStoneDisappear(int charID) { //RuneStone is Used
        OutPacket outPacket = new OutPacket(OutHeader.RUNE_STONE_DISAPPEAR);

        outPacket.encodeInt(0); // Has to be 0
        outPacket.encodeInt(charID);
        outPacket.encodeInt(0); // new 188
        outPacket.encodeByte(false); // new 188
        outPacket.encodeByte(1); // new

        return outPacket;
    }

    public static OutPacket runeActSuccess(RuneType runeType, int time) {
        OutPacket outPacket = new OutPacket(OutHeader.RUNE_ACT_SUCCESS);

        outPacket.encodeInt(runeType.getVal());
        outPacket.encodeInt(time);

        return outPacket;
    }

    public static OutPacket runeStoneSkillAck(RuneType runeType) {
        OutPacket outPacket = new OutPacket(OutHeader.RUNE_STONE_SKILL_ACK);

        outPacket.encodeInt(runeType.getVal());

        return outPacket;
    }

    public static OutPacket runeStoneClearAndAllRegister() {
        OutPacket outPacket = new OutPacket(OutHeader.RUNE_STONE_CLEAR_AND_ALL_REGISTER);
        int count = 0;
        outPacket.encodeInt(count); // count
        outPacket.encodeInt(1); // new
        for (int i = 0; i < count; i++) {
            outPacket.encodeInt(0); // not sure, but whatever
        }

        return outPacket;
    }

    /**
     * Creates a Clock on a Field.
     *
     * @param clockPacket the clock to display
     * @return packet for the client
     */
    public static OutPacket clock(ClockPacket clockPacket) {
        OutPacket outPacket = new OutPacket(OutHeader.CLOCK);

        clockPacket.encode(outPacket);

        return outPacket;
    }

    /**
     * Creates a packet for changing the elite state of a field.
     *
     * @param eliteState             The new elite state
     * @param notShowPopup           whether or not the popup should show up (warning message for boss spawn, countdown for bonus)
     * @param bgm                    The new bgm if the state is ELITE_BOSS
     * @param propSpecialEliteEffect special elite effect
     * @param backUOL                back uol
     * @return packet for the client
     */
    public static OutPacket eliteState(EliteState eliteState, boolean notShowPopup, String bgm, String propSpecialEliteEffect,
                                       String backUOL, boolean notHappyTime) {

        OutPacket outPacket = new OutPacket(OutHeader.ELITE_STATE);

        outPacket.encodeInt(eliteState.getVal()); // elite state
        outPacket.encodeInt(notShowPopup ? 1 : 0); // ?
        // v265 (0x142ec30f0 area): state 2 reads three strings; any other state reads byte has, then four strings
        if (eliteState == EliteState.EliteBoss) {
            outPacket.encodeString(bgm);
            outPacket.encodeString("");
            outPacket.encodeString("");
        } else if (eliteState == EliteState.BonusStage || eliteState == EliteState.BonusStage2) {
            outPacket.encodeByte(1);
            outPacket.encodeString(propSpecialEliteEffect == null ? "" : propSpecialEliteEffect);
            outPacket.encodeString(backUOL == null ? "" : backUOL);
            outPacket.encodeString("");
            outPacket.encodeString("");
        } else if (eliteState == EliteState.EliteBoss2 || eliteState == EliteState.Unk) {
            outPacket.encodeByte(1);
            outPacket.encodeString(bgm == null ? "" : bgm);
            outPacket.encodeString("");
            outPacket.encodeString("");
            outPacket.encodeString("");
        } else {
            outPacket.encodeByte(0);
        }

        return outPacket;
    }

    public static OutPacket setQuickMoveInfo(boolean enable, List<QuickMoveInfo> quickMoveInfos) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_QUICK_MOVE_INFO);

        outPacket.encodeShort(enable ? 1 : 0);
        outPacket.encodeByte(quickMoveInfos.size());
        quickMoveInfos.forEach(qmi -> qmi.encode(outPacket));

        return outPacket;
    }

    public static OutPacket userOpenQuickMove(boolean open, List<QuickMoveInfo> quickMoveInfos) {
        OutPacket outPacket = new OutPacket(OutHeader.USER_OPEN_QUICK_MOVE);

        // byte open, then the SET_QUICK_MOVE_INFO body (GMS: 00, or 01 00 00 + count + entries)
        outPacket.encodeByte(open);
        if (open) {
            outPacket.encodeShort(0);
            outPacket.encodeByte(quickMoveInfos.size());
            quickMoveInfos.forEach(qmi -> qmi.encode(outPacket));
        }

        return outPacket;
    }

    public static OutPacket groupMessage(GroupMessageType gmt, Char from, String msg) {

        OutPacket outPacket = new OutPacket(OutHeader.GROUP_MESSAGE.getValue());

        outPacket.encodeByte(gmt.ordinal());

        outPacket.encodeInt(from.getUserId());
        outPacket.encodeInt(from.getId());
        outPacket.encodeString(from.getName());
        outPacket.encodeString(msg);
        outPacket.encodeInt(-1); // v265: int before the sender info (-1 = none)
        from.encodeChatInfo(outPacket, msg);
        ChatModule.encodeLinkedMessage(outPacket, null, ChatModule.LinkedMessageType.Nothing);
        outPacket.encodeByte(0); // int list count

        return outPacket;
    }

    public static OutPacket itemLinkedGroupMessage(GroupMessageType gmt, Char from, String msg, Item item, String itemName) {
        OutPacket outPacket = new OutPacket(OutHeader.GROUP_MESSAGE.getValue());

        outPacket.encodeByte(gmt.ordinal());

        outPacket.encodeInt(from.getUserId());
        outPacket.encodeInt(from.getId());
        outPacket.encodeString(from.getName());
        outPacket.encodeString(msg);
        outPacket.encodeInt(-1);
        from.encodeChatInfo(outPacket, msg);
        ChatModule.encodeLinkedMessage(outPacket, item, ChatModule.LinkedMessageType.Item);
        outPacket.encodeByte(0); // int list count

        return outPacket;
    }

    public static OutPacket createMirrorImage(Position position, int alpha, int red, int green, int blue, boolean left) {
        OutPacket outPacket = new OutPacket(OutHeader.CREATE_MIRROR_IMAGE);

        outPacket.encodePositionInt(position);
        outPacket.encodeInt(alpha); // nAlpha   out of 1,000 (?)
        outPacket.encodeInt(red); // R  out of 100,000 (?)

        outPacket.encodeInt(green); // G  out of 100,000 (?)
        outPacket.encodeInt(blue); // B  out of 100,000 (?)
        outPacket.encodeInt(left ? 1 : 0); // bLeft

        return outPacket;
    }

    public static OutPacket setOneTimeAction(int charID, int action, int duration) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_ONE_TIME_ACTION);

        outPacket.encodeInt(charID);
        outPacket.encodeInt(action);
        outPacket.encodeInt(duration);

        return outPacket;
    }

    public static OutPacket makingSkillResult(int charID,
                                              int recipeCode,
                                              MakingSkillType result,
                                              MakingSkillType subResult,
                                              List<MakingSkillItem> makingSkillItems,
                                              MakingSkillRecipe.TargetElem target,
                                              int incSkillProficiency
    ) {
        OutPacket outPacket = new OutPacket(OutHeader.MAKING_SKILL_RESULT);

        outPacket.encodeInt(charID);

        outPacket.encodeInt(result.getVal());

        if (result == MakingSkillType.Extraction) {
            outPacket.encodeInt(recipeCode);
            outPacket.encodeInt(0);
            outPacket.encodeByte(subResult.isSuccess());
            // the count is read either way; the four ints per item only on success
            outPacket.encodeInt(subResult.isSuccess() ? makingSkillItems.size() : 0);
            if (subResult.isSuccess()) {
                for (var msi : makingSkillItems) {
                    outPacket.encodeInt(msi.getItemId());
                    outPacket.encodeInt(msi.getIdk1());
                    outPacket.encodeInt(msi.getIdk2());
                    outPacket.encodeInt(msi.getIdk3());
                    outPacket.encodeInt(msi.getItemPos());
                }
            }
            outPacket.encodeInt(incSkillProficiency);
        } else {
            // success
            outPacket.encodeInt(recipeCode);
            outPacket.encodeInt(subResult.getVal()); // ?

            outPacket.encodeByte(subResult.isSuccess());
            if (subResult.isSuccess()) {
                outPacket.encodeInt(target.getItemID());
                outPacket.encodeInt(target.getCount());
            }
            outPacket.encodeInt(incSkillProficiency);
        }

        return outPacket;
    }

    public static OutPacket gatherResult(Char chr, boolean succeed) {
        OutPacket outPacket = new OutPacket(OutHeader.GATHER_RESULT);

        outPacket.encodeInt(chr.getId());
        outPacket.encodeByte(succeed);

        return outPacket;
    }

    public static OutPacket registerExtraSkill(Char chr, int mainSkillId, List<Integer> extraSkillIds) {
        return registerExtraSkill(chr.getPosition(), mainSkillId, extraSkillIds, chr.isLeft());
    }

    public static OutPacket registerExtraSkill(Position position, int mainSkillId, List<Integer> extraSkillIds, boolean isLeft) {
        List<ExtraSkillInfo> extraSkillInfoList = extraSkillIds.stream().map(i -> new ExtraSkillInfo(i, position, isLeft, 0, 1)).collect(Collectors.toList());
        return registerExtraSkill(mainSkillId, extraSkillInfoList);
    }

    public static OutPacket registerExtraSkill(int mainSkillId, ExtraSkillInfo extraSkillInfo) {
        return registerExtraSkill(mainSkillId, Collections.singletonList(extraSkillInfo));
    }

    public static OutPacket registerExtraSkill(int mainSkillId, List<ExtraSkillInfo> extraSkillInfoList) {
        OutPacket outPacket = new OutPacket(OutHeader.REGISTER_EXTRA_SKILL);

        var left = -1;
        var right = 1;

        if (mainSkillId == Phantom.PHANTOMS_MARK || mainSkillId == Lara.LANDS_CONNECTION) {
            left = 1;
            right = 0;
        }

        outPacket.encodeInt(mainSkillId);
        outPacket.encodeShort(extraSkillInfoList.size());
        for (var extraSkill : extraSkillInfoList) {
            outPacket.encodeInt(extraSkill.extraSkillId);
            outPacket.encodeInt(extraSkill.extraSkillId);
            outPacket.encodePositionInt(extraSkill.position);
            outPacket.encodeShort(extraSkill.left ? left : right);
            outPacket.encodeInt(extraSkill.delay); // ms
            outPacket.encodeInt(extraSkill.unk);
            outPacket.encodeInt(-1); // v265
            outPacket.encodeInt(0); // mob object ids
            outPacket.encodeInt(0);
            outPacket.encodeInt(0); // v265: target object id
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeByte(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
        }

        return outPacket;
    }

    public static OutPacket footholdAppear(AppearingFoothold afh) {
        return footholdAppear(afh.getFootholdName(), !afh.isHide(), afh.getPosition());
    }

    public static OutPacket footholdAppear(String footholdName, boolean show, Position position) {
        OutPacket outPacket = new OutPacket(OutHeader.FOOT_HOLD_APPEAR);

        int loopSize = 1;
        outPacket.encodeInt(loopSize);
        for (int i = 0; i < loopSize; i++) {
            outPacket.encodeString(footholdName);
            outPacket.encodeByte(0);
            outPacket.encodeInt(show ? 1 : 0);
            outPacket.encodePositionInt(position);
        }

        return outPacket;
    }

    public static OutPacket playSound(String dir) {
        OutPacket outPacket = new OutPacket(OutHeader.PLAY_SOUND);

        outPacket.encodeString(dir);

        return outPacket;
    }

    public static OutPacket updateTemporarySkillBar(int skillID) {
        return skillID == 0 ? setTemporarySkillSet(0) : setTemporarySkillSet(13, skillID);
    }

    /**
     * Temporary skill bar (v271 CTemporarySkillMan 0x144218950, v265 0x143bf7010): int set index, 0 clears the bar.
     * Otherwise int, byte, int, int, int n, n skill records (v271 0x1411db340), then int, string, int.
     */
    public static OutPacket setTemporarySkillSet(int index, int... skillIDs) {
        return setTemporarySkillSet(index, 0, false, skillIDs);
    }

    /**
     * cooldown: the record's sixth int (GMS: 10000 Bifrost, 6000 Enduring Will, 30000 in sets 50/114/115);
     * keyFlag: the byte after the ints (GMS sets it in sets 114/115).
     */
    public static OutPacket setTemporarySkillSet(int index, int cooldown, boolean keyFlag, int... skillIDs) {
        OutPacket outPacket = new OutPacket(OutHeader.UPDATE_TEMPORARY_SKILL_BAR);

        outPacket.encodeInt(index);
        if (index != 0) {
            outPacket.encodeInt(index);
            outPacket.encodeByte(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(skillIDs.length);
            for (int skillID : skillIDs) {
                outPacket.encodeByte(0); // type
                outPacket.encodeInt(skillID);
                outPacket.encodeByte(1); // slv
                outPacket.encodeInt(0); // continuous time
                outPacket.encodeInt(0); // continuation skill
                outPacket.encodeInt(0); // link skill
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(cooldown);
                outPacket.encodeByte(keyFlag); // process key at disable
                outPacket.encodeByte(0); // 0 in every GMS record
                outPacket.encodeByte(0); // v271
                outPacket.encodeByte(0); // v271
                // 0x1412133b0: int, int type, int, int, int n (+ n keyed entries)
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeByte(0); // v271
            }
            outPacket.encodeInt(0); // v271: int, string, int after the records
            outPacket.encodeString("");
            outPacket.encodeInt(0);
        }

        return outPacket;
    }

    /**
     * Boss notice line. GMS: type 0 = notice, 1 = "considers X the most threatening enemy" (category 3, value 0);
     * category 5 = phase change (value = new phase, 6666 at the kill); categories 1 and 2 carry small values (1-5).
     */
    public static OutPacket smartMobNotice(int type, int mobTemplateId, int category, int value, String msg) {
        OutPacket outPacket = new OutPacket(OutHeader.SMART_MOB_NOTICE);

        outPacket.encodeInt(type);
        outPacket.encodeInt(mobTemplateId);
        outPacket.encodeInt(category);
        outPacket.encodeInt(value);
        outPacket.encodeString(msg);

        return outPacket;
    }

    public static OutPacket adminResult(boolean hide) { // There are ton more different types. However, most we won't use
        OutPacket outPacket = new OutPacket(OutHeader.ADMIN_RESULT);

        outPacket.encodeInt(25); // nType  (25 is for Hide)

        // if (nType == 25) {
        outPacket.encodeByte(hide); // hide
        outPacket.encodeByte(false); // unk
        // }

        return outPacket;
    }

    public static OutPacket groupChairSitResult(PortableChair chair, int changeForChrId, boolean enter, boolean show) {
        return groupChairSitResult(chair, changeForChrId, enter, false, show);
    }

    public static OutPacket groupChairSitResult(PortableChair chair, int changeForChrId, boolean enter, boolean otherEnter, boolean show) {
        OutPacket outPacket = new OutPacket(OutHeader.GROUP_CHAIR_SIT_RESULT);

        chair.encodeGroupChair(outPacket, changeForChrId, enter, otherEnter, show);

        return outPacket;
    }

    public static OutPacket groupChairInvitationResult(GroupChairInvitationResultType type) {
        return groupChairInvitationResult(type, false);
    }

    /** sitDown: the far-away text for sitting down (465) instead of accepting an invitation */
    public static OutPacket groupChairInvitationResult(GroupChairInvitationResultType type, boolean sitDown) {
        OutPacket outPacket = new OutPacket(OutHeader.GROUP_CHAIR_INVITATION_RESULT);

        outPacket.encodeInt(type.getVal());
        outPacket.encodeByte(sitDown);

        return outPacket;
    }

    /*

    CWKPQ - Makes the pillars move:

    menhir0 , 1
    menhir1 , 1
    menhir2 , 1
    menhir3 , 1
    ...

     */
    public static OutPacket setObjectState(String objName, int state) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_OBJECT_STATE);

        outPacket.encodeString(objName);
        outPacket.encodeInt(state);

        return outPacket;
    }

    public static OutPacket fieldSkill(FieldSkill fieldSkill) {
        OutPacket outPacket = new OutPacket(OutHeader.FIELD_SKILL);

        outPacket.encodeInt(fieldSkill.getSkillID().getId());
        outPacket.encodeInt(fieldSkill.getSlv());

        fieldSkill.encode(outPacket);

        return outPacket;
    }

    public static OutPacket momentAreaOffAll(boolean encodeAreaControl, List<String> areaControl) {
        OutPacket outPacket = new OutPacket(OutHeader.MOMENT_AREA_OFF_ALL);

        var list = new ArrayList<String>();
        var encodeUnk = false;
        outPacket.encodeByte(encodeUnk);
        if (encodeUnk) {
            outPacket.encodeInt(list.size());
            list.forEach(outPacket::encodeString);
        }

        encodeUnk = false;
        outPacket.encodeByte(encodeUnk);
        if (encodeUnk) {
            outPacket.encodeInt(list.size());
            list.forEach(outPacket::encodeString);
        }

        outPacket.encodeByte(encodeAreaControl);
        if (encodeAreaControl) {
            outPacket.encodeInt(areaControl.size());
            areaControl.forEach(outPacket::encodeString);
        }

        return outPacket;
    }

    // Boss intro effects as GMS v271 sends them (FIELD_EFFECT types the v265 enum doesn't have). The fixed blocks are
    // as GMS sends them (Seren, Will, Kaling, First Adversary, Limbo intros); only the names change.
    private static final byte[] SPINE_INTRO_MID = {1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static final byte[] SPINE_INTRO_TAIL = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, (byte) 0xff, 0, 0, 0};
    private static final byte[] BOSS_ENTER_ANI_TAIL = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, (byte) 0xff, 0, 0, 0};
    /** where GMS puts Limbo's entrance skeleton; Lotus's sits at 0, 0 */
    private static final int LIMBO_SKELETON_X = -2, LIMBO_SKELETON_Y = -273;
    private static final int BOSS_ENTER_SKELETON_ALPHA = 255;
    private static final int FIELD_EFFECT_OVERLAY = 88;

    /** FIELD_EFFECT 84: a spine animation over the screen, removed later by OffSpineScreen with the same key. */
    public static OutPacket spineIntro(String key, String path, String animation) {
        OutPacket outPacket = new OutPacket(OutHeader.FIELD_EFFECT);
        outPacket.encodeByte(84);
        outPacket.encodeString(key);
        outPacket.encodeString(path);
        outPacket.encodeArr(SPINE_INTRO_MID);
        outPacket.encodeString(animation);
        outPacket.encodeArr(SPINE_INTRO_TAIL);
        return outPacket;
    }

    /** FIELD_EFFECT 47: a boss's entrance animation (Mob/BossPattern/BossEnterAni.img/...; First Adversary). */
    public static OutPacket bossEnterAnimation(String path) {
        OutPacket outPacket = new OutPacket(OutHeader.FIELD_EFFECT);
        outPacket.encodeByte(47);
        outPacket.encodeShort(0);
        outPacket.encodeString(path);
        outPacket.encodeInt(9001);
        outPacket.encodeArr(BOSS_ENTER_ANI_TAIL);
        return outPacket;
    }

    /** FIELD_EFFECT 44 at Limbo's offset. */
    public static OutPacket bossEnterSkeleton(String path, String animation) {
        return bossEnterSkeleton(path, animation, LIMBO_SKELETON_X, LIMBO_SKELETON_Y);
    }

    /** FIELD_EFFECT 44: a boss's entrance skeleton animation (Mob/BossPattern/BossEnterAni.img/...skel). */
    public static OutPacket bossEnterSkeleton(String path, String animation, int x, int y) {
        OutPacket outPacket = new OutPacket(OutHeader.FIELD_EFFECT);
        outPacket.encodeByte(44);
        outPacket.encodeShort(0);
        outPacket.encodeString(path);
        outPacket.encodeInt(9001);
        outPacket.encodeByte(1);
        outPacket.encodeString(animation);
        outPacket.encodeByte(0);
        outPacket.encodeInt(x);
        outPacket.encodeInt(y);
        outPacket.encodeInt(BOSS_ENTER_SKELETON_ALPHA);
        outPacket.encodeString("");
        return outPacket;
    }

    /** FIELD_EFFECT 88: the client's built-in screen overlay on or off (GMS: on before Lotus phase 3's transition). */
    public static OutPacket screenOverlay(boolean on) {
        OutPacket outPacket = new OutPacket(OutHeader.FIELD_EFFECT);
        outPacket.encodeByte(FIELD_EFFECT_OVERLAY);
        outPacket.encodeInt(on ? 1 : 0);
        return outPacket;
    }

    /**
     * FIELD_SET_CONTEXT_INIT: every field context and every player's user contexts with their bounds. The boss windows
     * (Malefic Star's gauges, Kai's Chrono Gauge) read their values from it; sending it again replaces them.
     *
     * @param field name -> {min, max, value[, default]}
     * @param users character id -> (name -> {min, max, value[, default]})
     */
    public static OutPacket fieldSetContext(Map<String, int[]> field, Map<Integer, Map<String, int[]>> users) {
        OutPacket outPacket = new OutPacket(OutHeader.FIELD_SET_CONTEXT_INIT);
        encodeContexts(outPacket, field);
        outPacket.encodeInt(0); // running changes
        outPacket.encodeInt(users.size());
        for (var e : users.entrySet()) {
            outPacket.encodeInt(e.getKey());
            encodeContexts(outPacket, e.getValue());
            outPacket.encodeInt(0);
        }
        return outPacket;
    }

    /**
     * FIELD_SET_CONTEXT_ALIAS: role -> context name for the field and for users (e.g. DeathCountKey -> DeathCount),
     * then two name sets (empty). Send it after {@link #fieldSetContext}.
     */
    public static OutPacket fieldSetContextAlias(Map<String, String> field, Map<String, String> user) {
        OutPacket outPacket = new OutPacket(OutHeader.FIELD_SET_CONTEXT_ALIAS);
        for (var m : List.of(field, user)) {
            outPacket.encodeInt(m.size());
            m.forEach((k, v) -> {
                outPacket.encodeString(k);
                outPacket.encodeString(v);
            });
        }
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        return outPacket;
    }

    private static void encodeContexts(OutPacket outPacket, Map<String, int[]> contexts) {
        outPacket.encodeInt(contexts.size());
        for (var e : contexts.entrySet()) {
            int[] v = e.getValue();
            outPacket.encodeString(e.getKey());
            outPacket.encodeInt(v.length > 3 ? v[3] : 0); // default (Gauge 500, DeathCount 1000, Element -1)
            outPacket.encodeInt(v[1]); // max
            outPacket.encodeInt(v[0]); // min
            outPacket.encodeInt(v[2]); // value
        }
    }

    /** FOOT_HOLD_OFF: turns the given footholds on or off. */
    public static OutPacket footholdsOnOff(int[] ids, boolean on) {
        OutPacket outPacket = new OutPacket(OutHeader.FOOT_HOLD_OFF);
        outPacket.encodeInt(ids.length);
        for (int id : ids) {
            outPacket.encodeInt(id);
            outPacket.encodeByte(on);
        }
        return outPacket;
    }

    /** FOOT_HOLD_OFF: the given footholds on, except the ones in off. */
    public static OutPacket footholdsOnExcept(int[] ids, int[] off) {
        OutPacket outPacket = new OutPacket(OutHeader.FOOT_HOLD_OFF);
        outPacket.encodeInt(ids.length);
        for (int id : ids) {
            outPacket.encodeInt(id);
            boolean on = true;
            for (int o : off) {
                if (o == id) {
                    on = false;
                    break;
                }
            }
            outPacket.encodeByte(on);
        }
        return outPacket;
    }

    /** NPC_LOCAL_NPC_REPEAT_EFFECT with one effect path. */
    public static OutPacket localRepeatEffect(String path, int value) {
        OutPacket outPacket = new OutPacket(OutHeader.NPC_LOCAL_NPC_REPEAT_EFFECT);
        outPacket.encodeByte(1);
        outPacket.encodeString(path);
        outPacket.encodeInt(value);
        outPacket.encodeByte(0);
        return outPacket;
    }


    /** the client's field value (key=value, e.g. a mini-game's score) */
    public static OutPacket fieldValue(String key, String value) {
        OutPacket out = new OutPacket(OutHeader.FIELD_VALUE);
        out.encodeString(key);
        out.encodeString(value);
        return out;
    }

    /** the client's session value (key=value) */
    public static OutPacket sessionValue(String key, String value) {
        OutPacket out = new OutPacket(OutHeader.SESSION_VALUE);
        out.encodeString(key);
        out.encodeString(value);
        return out;
    }
}
