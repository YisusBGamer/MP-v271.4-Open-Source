package net.swordie.ms.connection.packet;


import net.swordie.ms.client.jobs.adventurer.Kinesis;
import net.swordie.ms.client.jobs.adventurer.PinkBean;
import net.swordie.ms.client.jobs.adventurer.pirate.Jett;
import net.swordie.ms.client.jobs.cygnus.ThunderBreaker;
import net.swordie.ms.client.jobs.jianghu.MoXuan;
import net.swordie.ms.client.jobs.flora.Ark;
import net.swordie.ms.client.jobs.adventurer.warrior.Hero;
import net.swordie.ms.client.jobs.adventurer.pirate.Corsair;
import net.swordie.ms.client.jobs.adventurer.archer.Marksman;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.client.jobs.adventurer.archer.BowMaster;
import net.swordie.ms.client.jobs.adventurer.archer.Pathfinder;
import net.swordie.ms.client.jobs.adventurer.thief.BladeMaster;
import net.swordie.ms.client.jobs.adventurer.thief.Shadower;
import net.swordie.ms.client.jobs.adventurer.warrior.DarkKnight;
import net.swordie.ms.client.jobs.anima.HoYoung;
import net.swordie.ms.client.jobs.anima.Lara;
import net.swordie.ms.client.jobs.cygnus.NightWalker;
import net.swordie.ms.client.jobs.flora.Illium;
import net.swordie.ms.client.jobs.legend.Evan;
import net.swordie.ms.client.jobs.legend.Luminous;
import net.swordie.ms.client.jobs.legend.Phantom;
import net.swordie.ms.client.jobs.legend.Shade;
import net.swordie.ms.client.jobs.nova.AngelicBuster;
import net.swordie.ms.client.jobs.nova.Cadena;
import net.swordie.ms.client.jobs.nova.Kain;
import net.swordie.ms.client.jobs.nova.Kaiser;
import net.swordie.ms.client.jobs.resistance.BattleMage;
import net.swordie.ms.client.jobs.resistance.WildHunter;
import net.swordie.ms.client.jobs.resistance.demon.DemonSlayer;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.TextEffectType;
import net.swordie.ms.enums.UserEffectType;
import net.swordie.ms.life.mob.skill.MobSkillID;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.container.Tuple;
import net.swordie.ms.world.field.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.swordie.ms.enums.UserEffectType.*;

public class Effect {

    private UserEffectType userEffectType;
    private String string;
    private List<Tuple<Integer, Integer>> list = new ArrayList<>();
    private List<int[]> speedMirages = new ArrayList<>(); // x, y, left (0/1), angle
    private SkillUseInfo skillUseInfo;

    private int arg1;
    private int arg2;
    private int arg3;
    private int arg4;
    private int arg5;
    private int arg6;
    private int arg7;
    private int arg8;
    private int arg9;
    private int arg10;
    private int arg11;
    private int arg12;

    public static int x = 0;

    /**
     * The v271 client's number for a server (v265) effect type, or -1 when it has no known one (client 0x145085370
     * against v265 0x144ac6d00, matched by each case's reads): 0-26 unchanged, v265 27 and 29 gone, 28 is 27,
     * 30-67 are two lower, v271 66 is new, 68-99 are one lower; 100 and up are not matched.
     */
    /** Whether the client reads the int after this effect (GMS sends none for EffectUOL and PickUpItem). */
    public boolean hasTrailingInt() {
        var t = getUserEffectType();
        return t != UserEffectType.EffectUOL && t != UserEffectType.PickUpItem;
    }

    public static int v271Type(UserEffectType type) {
        int v = type.getVal();
        if (v <= 26) {
            return v;
        }
        if (v == 28) {
            return 27;
        }
        if (v >= 30 && v <= 67) {
            return v - 2;
        }
        if (v >= 68 && v <= 99) {
            return v - 1;
        }
        return -1;
    }

    public void encode(OutPacket outPacket) { // v204.1
        int type271 = v271Type(getUserEffectType());
        outPacket.encodeByte(type271 < 0 ? 0xFF : type271); // 0xFF: no client case, nothing is read

        int skillID = getArg1();
        switch (getUserEffectType()) {
            case LevelUp:
                // Empty
                break;
            case SkillUse:
            case SkillUseBySummoned:
                encodeSkillUse(outPacket); // too long to put here
                break;
            case SkillAffected:
                outPacket.encodeInt(getArg1()); // skill id
                outPacket.encodeByte(getArg2()); // slv
                if (skillID == DemonSlayer.RAVEN_STORM || skillID == Shade.DEATH_MARK) {
                    outPacket.encodeInt(getArg3()); // nDelta
                }
                break;
            case SkillAffected_Ex:
                outPacket.encodeInt(getArg1()); // skill id
                outPacket.encodeByte(getArg2()); // slv
                outPacket.encodeInt(getArg3()); // positionX ?
                outPacket.encodeInt(getArg4()); // positionY ?
                break;
            case SkillAffected_Select:
                outPacket.encodeInt(getArg1());
                outPacket.encodeInt(getArg2());
                outPacket.encodeInt(getArg3());
                outPacket.encodeByte(getArg4());
                outPacket.encodeByte(getArg5());
                break;
            case SkillSpecialAffected:
                // v271 (type 7): two ints; GMS puts the character's level in the second (local and remote)
                outPacket.encodeInt(getArg1()); // skill id
                outPacket.encodeInt(getArg3() > 0 ? getArg3() : getArg2());
                break;
            case Quest:
                outPacket.encodeByte(getList().size());
                for (Tuple<Integer, Integer> item : getList()) {
                    outPacket.encodeInt(item.getLeft()); // Item ID
                    outPacket.encodeInt(item.getRight()); // Quantity
                    outPacket.encodeByte(false); // bInBag
                }
                if (getList().isEmpty()) {
                    // v271 reads the text and its int only for an empty item list (GMS: none after the items)
                    outPacket.encodeString("");
                    outPacket.encodeInt(0);
                }
                break;
            case Pet:
                outPacket.encodeByte(getArg1());
                outPacket.encodeInt(getArg2());
                break;
            case SkillSpecial:
                outPacket.encodeInt(skillID); // skill id
                if (SkillConstants.isExplosionSkill(skillID)) {
                    outPacket.encodeInt(getArg2());
                    outPacket.encodeInt(getArg3());
                    outPacket.encodeInt(getArg4());
                } else if (skillID == BattleMage.DARK_SHOCK) {
                    outPacket.encodeInt(getArg2()); // slv
                    outPacket.encodeInt(getArg3()); // show effect
                    outPacket.encodePositionInt(new Position(getArg4(), getArg5())); // Origin Position -  arg4 = x, arg5 = y
                    outPacket.encodePositionInt(new Position(getArg6(), getArg7())); // Destination Position -  arg6 = x, arg7 = y
                } else if (skillID == SkillConstants.M_FORCE_GO || skillID == SkillConstants.STARLIGHT_EXPLOSION || skillID == SkillConstants.PETITE_AVATAR || skillID == SkillConstants.NATURES_ROAR
                        || skillID == SkillConstants.REMEMBERING_THE_JOURNEY || skillID == SkillConstants.PIXEL_WARRIORS || skillID == SkillConstants.MONSTER_BUSTERS || skillID == SkillConstants.SUMMON_SILVERBRISTLE
                        || skillID == SkillConstants.EXPLODING_ROBOT_KABOOM_BOMBER || skillID == SkillConstants.NEROTA_RELIC_HUNT || skillID == SkillConstants.GLORY_GUARD_MEDAL) {
                    // M-Force Go!, Starlight Explosion, Petite Avatar, Remembering the Journey
                    outPacket.encodeInt(getArg2());
                    outPacket.encodeInt(getArg3());
                    outPacket.encodeInt(getArg4());
                } else if (skillID == SkillConstants.PAPYRUS_OF_LUCK) {
                    outPacket.encodeInt(getArg2());
                    outPacket.encodeInt(getArg3());
                    if (getArg2() == 2) {
                        outPacket.encodeInt(getArg4());
                        outPacket.encodeByte(getArg5());
                    } else {
                        outPacket.encodeInt(getArg4());
                        outPacket.encodeInt(getArg5());
                    }
                } else if (skillID == SkillConstants.RIFTBRINGER) {
                    outPacket.encodeLong(getArg2());
                } else if (skillID == BattleMage.ABYSSAL_LIGHTNING_PORTAL_ATTACK) {
                    outPacket.encodePositionInt(new Position(getArg4(), getArg5())); // Origin Position -  arg4 = x, arg5 = y
                    outPacket.encodePositionInt(new Position(getArg6(), getArg7())); // Destination Position -  arg6 = x, arg7 = y
                } else if (skillID == BowMaster.SILHOUETTE_MIRAGE) {
                    outPacket.encodeInt(getArg2()); // clone  0 is a clone | 1 is the other
                    outPacket.encodePositionInt(new Position(getArg3(), getArg4()));
                } else if (skillID == HoYoung.THREE_PATHS_APPARITION_PROC) {
                    outPacket.encodeInt(getArg2()); // skillID
                    // 164111003
                    // 164121003
                }
                break;
            case Resist:
                // Empty
                break;
            case ProtectOnDieItemUse:
                outPacket.encodeByte(getArg1());
                outPacket.encodeByte(getArg2());
                outPacket.encodeByte(getArg3());
                outPacket.encodeInt(getArg4());     // ItemID
                break;
            case JobChanged:
                // v265: short job, short sub job (kept on the remote user, as in USER_ENTER_FIELD)
                outPacket.encodeShort(getArg1());
                outPacket.encodeShort(getArg2());
                break;
            case PlayPortalSE:
            case QuestComplete:
                // Empty
                break;
            case IncDecHPEffect:
                outPacket.encodeByte(getArg1());    // amount being healed     0 = Miss
                break;
            case BuffItemEffect:
                outPacket.encodeInt(getArg1());     // ItemID
                break;
            case SquibEffect:
                outPacket.encodeString(getString());
                break;
            case MonsterBookCardGet:
                // Empty
                break;
            case LotteryUse:
                outPacket.encodeInt(getArg1());
                outPacket.encodeByte(getArg2());    // success
                if (getArg2() > 0) {
                    outPacket.encodeString(getString());
                }
                break;
            case ItemLevelUp:
                // Empty
                break;
            case ItemMaker:
                outPacket.encodeInt(getArg1());     // success or failure      0 = Success,  1 = Failure
                break;
            case FieldMesoItemConsumed:
                outPacket.encodeInt(getArg1());     // Meso Gained
                break;
            case ExpItemConsumed:
                // Empty
                break;
            case FieldExpItemConsumed:
                outPacket.encodeLong(getArg1());     // Exp Gained (v271: 8 bytes)
                break;
            case ReservedEffect:
                outPacket.encodeByte(getArg1());    // bScreenCoord
                outPacket.encodeInt(getArg2());     // nRX
                outPacket.encodeInt(getArg3());     // nRY
                outPacket.encodeString(getString());// sEffect
                break;

                // 27

            case UpgradeTombItemUse:
                outPacket.encodeByte(getArg1());
                break;
            case BattlefieldItemUse:
                outPacket.encodeLong(getArg1());
                break;
            case unk30:
                outPacket.encodeString(getString());
                break;
            case AvatarOriented:
                outPacket.encodeString(getString());
                break;
            case AvatarOrientedRepeat:
                outPacket.encodeByte(getArg1());
                if (getArg1() > 0) {
                    outPacket.encodeString(getString());
                    outPacket.encodeInt(getArg2());
                    outPacket.encodeInt(getArg3());
                }
                break;
            case AvatarOrientedMultipleRepeat:
                outPacket.encodeString(getString());
                outPacket.encodeInt(getArg1());
                outPacket.encodeInt(getArg2());
                break;
            case IncubatorUse:
                outPacket.encodeInt(getArg1());
                outPacket.encodeString(getString());
                break;
            case PlaySoundWithMuteBGM:
                outPacket.encodeString(getString());
                break;
            case PlayExclSoundWithDownBGM:
                outPacket.encodeString(getString());
                outPacket.encodeInt(getArg1());
                break;
            case SoulStoneUse:
                // Empty
                break;
            case IncDecHPEffect_EX: // v271 type 36 (0x145085370 -> 0x1450990bb): 4 amount, 1 flag, 4 local HP value (negated; 0 = none)
                outPacket.encodeInt(getArg1()); // hp amount
                outPacket.encodeByte(getArg2()); // unk
                outPacket.encodeInt(getArg3());
                break;
            case IncDecHPRegenEffect:
                outPacket.encodeInt(getArg1()); // hp amount
                break;
            case EffectUOL:
                outPacket.encodeString(getString());
                outPacket.encodeByte(getArg1());
                outPacket.encodeInt(getArg2());
                outPacket.encodeInt(getArg3());
                outPacket.encodeByte(getArg5());
                if (getArg3() == 2) { // item sound
                    outPacket.encodeInt(getArg4()); // nItemID
                }
                break;
            case PvPRage:
                outPacket.encodeInt(getArg1());
                break;
            case PvPChampion:
                outPacket.encodeInt(getArg1());
                break;
            case PvPGradeUp:
            case PvPRevive:
            case PvPJobEffect:
                // Empty
                break;
            case FadeInOut:
                outPacket.encodeInt(getArg1());// tFadeIn
                outPacket.encodeInt(getArg2());// tDelay
                outPacket.encodeInt(getArg3());// tFadeOut
                outPacket.encodeByte(getArg4()); // nAlpha
                break;
            case MobSkillHit:
                outPacket.encodeInt(getArg1()); // mob skill id
                outPacket.encodeInt(getArg2()); // slv
                break;
            case unk48:
                outPacket.encodeInt(getArg1()); // field skill id (GMS: the Black Mage's 100017 hit)
                outPacket.encodeInt(getArg2()); // its level
                break;
            case BlindEffect:
                outPacket.encodeByte(getArg1()); //  set / remove
                break;
            case BossShieldCount:
                outPacket.encodeInt(getArg1());
                outPacket.encodeInt(getArg1());
                break;
            case ResetOnStateForOnOffSkill:
                // Empty
                break;
            case JewelCraft:
                outPacket.encodeByte(getArg1()); // Result  0,2 = Success,     5 = Unk error,      Other = Fail
                if (getArg1() < 5) {
                    outPacket.encodeInt(getArg2()); // Item ID
                }
                break;
            case ConsumeEffect:
                outPacket.encodeInt(getArg1()); // Item ID
                break;
            case PetBuff:
                // Empty
                break;
            case LotteryUIResult:
                outPacket.encodeByte(0);
                outPacket.encodeByte(1);
                outPacket.encodeByte(0);
                outPacket.encodeInt(getArg3()); // Item ID ?
                outPacket.encodeInt(getArg4()); // quantity
                outPacket.encodeByte(0);
                break;
            case LeftMonsterNumber:
                outPacket.encodeInt(getArg1()); // Number on Arrow
                break;
            case ReservedEffectRepeat:
                outPacket.encodeString(getString());    // effect
                outPacket.encodeByte(getArg2() != 0);        // bDuration
                if (getArg2() != 0) {
                    outPacket.encodeByte(getArg1());        // bSomething2
                    if (getArg1() != 0) {
                        outPacket.encodeByte(getArg3());    // bShow?
                        outPacket.encodeInt(getArg4());     // nX
                        outPacket.encodeInt(getArg5());     // nY
                    }
                } else {
                    outPacket.encodeInt(getArg2());    // nDuration (seconds)
                    outPacket.encodeInt(getArg4());     // nX
                    outPacket.encodeInt(getArg5());     // nY
                }
                break;
            case RobbinsBomb:
                outPacket.encodeByte(getArg1()); // Reset/Delete
                outPacket.encodeInt(getArg2()); // BombCount
                outPacket.encodeByte(getArg3()); // number (unknown)
                break;
            case SkillMode:
                outPacket.encodeInt(getArg1()); // Skill ID
                outPacket.encodeInt(getArg2()); // Rotate (?)
                outPacket.encodeInt(getArg3()); // Skip Frame (?)
                break;
            case ActQuestComplete:
                outPacket.encodeInt(getArg1());
                break;
            case Point:
                outPacket.encodeInt(getArg1()); // PosX ?
                outPacket.encodeInt(getArg2()); // PosY ?
                break;
            case SpeechBalloon:
                outPacket.encodeByte(arg1);  // normal
                outPacket.encodeInt(arg2); // idx
                outPacket.encodeInt(arg3); // linkType
                outPacket.encodeString(string); // speech
                outPacket.encodeInt(arg4); // time
                outPacket.encodeInt(arg5); // align
                outPacket.encodeInt(arg6); // x
                outPacket.encodeInt(arg7); // y
                outPacket.encodeInt(arg8); // z
                outPacket.encodeInt(arg9); // lineSpace
                outPacket.encodeInt(arg10); // npcTemplateId
                outPacket.encodeInt(arg11); // ?
                outPacket.encodeInt(arg12); // ?
                break;
            case TextEffect:
                outPacket.encodeString(getString());
                outPacket.encodeInt(getArg1()); // letter delay
                outPacket.encodeInt(getArg2()); // box duration
                outPacket.encodeInt(getArg3()); // Positioning on Client  ( 4 = middle )

                outPacket.encodeInt(getArg4()); // xPos
                outPacket.encodeInt(getArg5()); // yPos

                outPacket.encodeInt(getArg6()); // Align
                outPacket.encodeInt(getArg7()); // Line space
                outPacket.encodeInt(getArg8()); // Enter type (0 = fade in)
                outPacket.encodeInt(getArg9()); // Leave type?
                outPacket.encodeInt(getArg10()); // Type
                outPacket.encodeString("");
                // v271 still reads this int; without it the packet trailer is read as the int and the byte, and the
                // byte left set keeps a bobbing arrow on screen until the next field
                outPacket.encodeInt(0);
                outPacket.encodeByte(false);
                break;
            case SpeedMirageEff:
                outPacket.encodeInt(getArg1()); // skill id
                outPacket.encodeInt(getArg2()); // char id
                outPacket.encodeInt(speedMirages.size());
                for (int i = 0; i < speedMirages.size(); i++) {
                    int[] m = speedMirages.get(i);
                    outPacket.encodeInt(m[0]);
                    outPacket.encodeInt(m[1]);
                    outPacket.encodeByte(m[2]);
                    outPacket.encodeInt(i);
                    outPacket.encodeInt(m[3]);
                }
                break;
            case SkillPreLoopEnd:
                outPacket.encodeInt(getArg1()); // skill id
                outPacket.encodeInt(getArg2()); // time in ms
                if (getArg1() == NightWalker.DARKNESS_ASCENDING) {
                    outPacket.encodeInt(getArg3());
                }
                break;
            case Aiming:
                outPacket.encodeInt(getArg1());
                outPacket.encodeInt(getArg2());
                outPacket.encodeInt(getArg3());
                outPacket.encodeInt(getArg4());
                outPacket.encodeInt(getArg5());
                outPacket.encodeInt(getArg6());
                break;
            case Unk70:
                outPacket.encodeInt(getArg1());
                outPacket.encodeShort(getArg2()); // position ?
                outPacket.encodeShort(getArg3());
                outPacket.encodeShort(getArg4()); // position2 ?
                outPacket.encodeShort(getArg5());
                break;
            case PickUpItem:
                outPacket.encodeInt(getArg1());
                break;
            case BattlePvP_IncDecHp:
                outPacket.encodeInt(getArg1());
                outPacket.encodeString(getString());
                break;
            case CatchEffect:
                // Empty
                break;
            case FailCatchEffect:
                // Empty
                break;
            case BiteAttack_ReceiveSuccess:
                outPacket.encodeInt(getArg1());
                outPacket.encodeInt(getArg2());
                break;
            case BiteAttack_ReceiveFail:
                outPacket.encodeByte(getArg1()); // success/fail
                if (getArg1() > 0) {
                    outPacket.encodeInt(getArg2());
                }
                break;
            case IncDecHPEffect_Delayed:
                outPacket.encodeShort(getArg1());
                outPacket.encodeInt(getArg2()); // HP healed ?
                outPacket.encodeByte(getArg3());
                outPacket.encodeByte(getArg4());
                outPacket.encodeByte(getArg5());
                break;
            case MobSkillSpecial:
                // Papulatus:
                //              bSuccess = true; nMobSkillID = 241; nSLV = 4

                // Will:
                //              bSuccess = true; nMobSkillID = 242; nSLV = 4 || 5
                //              bSuccess = false; nMobSkillID = 242; nSLV = 15

                // JinHilla:
                //              bSuccess = true; nMobSkillID = 201; nSLV = 256
                outPacket.encodeByte(getArg1()); // nSuccess
                outPacket.encodeInt(getArg2()); // nMobSkillID
                outPacket.encodeInt(getArg3()); // nSLV
                if (getArg1() == 3 && getArg2() == 276 && getArg3() == 2) {
                    outPacket.encodeByte(getArg4());
                }
                break;
            case BlackMageEffect:
                outPacket.encodeInt(getArg1()); // 100017 for Black Mage lasers effect
                outPacket.encodeInt(getArg2()); // 1  unk
                if (getArg1() == 100017) {
                    outPacket.encodeByte(getArg3()); // 1  unk
                    outPacket.encodeByte(getArg4()); // 1  unk
                }
                break;
            case ResistAbnormalStatus:
                outPacket.encodeInt(getArg1());
                break;
            case Unk82:
                outPacket.encodeString(getString());
                outPacket.encodeInt(getArg1());
                outPacket.encodeInt(getArg2());
                outPacket.encodeInt(getArg3());
                outPacket.encodeByte(getArg4());
                outPacket.encodeInt(getArg5());
                outPacket.encodeInt(getArg6());
                break;
            case RedChat:
                outPacket.encodeString(getString()); // msg
                break;
            case StarVortex:
                outPacket.encodeInt(getArg1()); // SkillID - Scroll: Star Vortex
                outPacket.encodeInt(getArg2()); // slv
                outPacket.encodeInt(getArg3()); // nPosX
                outPacket.encodeInt(getArg4()); // nPosY
                outPacket.encodeInt(getArg5()); // loop size
                break;
            case Unk87:
            case WaterSmashResult:
                outPacket.encodeInt(getArg1());
                outPacket.encodeInt(getArg2());
                break;
            case UpgradePotionMsg:
                // Empty
                break;
            case Unk105:
                outPacket.encodeInt(getArg1());
                break;
            case ElunaJewel:
                outPacket.encodeByte(getArg1()); // type,
                switch (getArg1()) {
                    case 0:
                    case 2:
                    case 3:
                        outPacket.encodeInt(getArg2());
                        break;
                    case 1:
                    case 4:
                        outPacket.encodeInt(getArg2());
                        break;
                    case 5:
                        break;
                }
                break;
            case SomeUpgradeEffectOnUser:
                // Empty
                break;
            case MoXuanEff:
                outPacket.encodeInt(getArg1()); // skill id
                switch (getArg1()) {
                    case MoXuan.HEIR_OF_THE_DIVINE -> { // Heir of the Divine
                        outPacket.encodeByte(getArg2());
                        outPacket.encodeByte(getArg3());
                        outPacket.encodeByte(getArg4());
                    }
                    case MoXuan.SECRET_ART_QI_SHIELD -> outPacket.encodeInt(getArg2()); // Qi Shield
                    default -> {
                    }
                }
                break;
        }
    }

    private void encodeSkillUse(OutPacket outPacket) {
        int skillID = getArg1();
        if (getUserEffectType() == SkillUseBySummoned) {
            outPacket.encodeInt(getArg4()); // Summon ID
        }
        outPacket.encodeInt(skillID); // Skill id
        outPacket.encodeInt(getArg2()); // chr Level
        outPacket.encodeInt(getArg3()); // slv
        if (skillID == Evan.DRAGON_FURY) { // Dragon Fury
            outPacket.encodeByte(getArg5()); // bCreate
        } else if (skillID == DarkKnight.FINAL_PACT_INFO) {
            outPacket.encodeByte(getArg5()); // bLoadReincarnationEffect
        } else if (skillID == BladeMaster.CHAINS_OF_HELL) {
            outPacket.encodeByte(getArg5()); // bLeft
            outPacket.encodeInt(getArg6()); // dwMobID
        } else if (skillID == Marksman.HOOKSHOT_MM || skillID == BowMaster.HOOKSHOT_BM || skillID == Hero.COMBO_FURY_DOWN) { // Hooks (Warrior combo fury/archer skills)
            outPacket.encodeByte(getArg5()); // bLeft
            outPacket.encodeInt(getArg6()); // dwMobID
            outPacket.encodeInt(getArg7()); // nMobPosX
            outPacket.encodeInt(getArg8()); // nMobPosY
        } else if (skillID == Cadena.CHAIN_ARTS_PURSUIT_HORIZONTAL_START || skillID == Cadena.CHAIN_ARTS_PURSUIT_UP_START
                || skillID == Cadena.CHAIN_ARTS_PURSUIT_DOWN_START) {
            outPacket.encodeByte(getArg5());
        } else if (skillID == Cadena.CHAIN_ARTS_PURSUIT_DOWN || skillID == Cadena.CHAIN_ARTS_PURSUIT_UP ||
                skillID == Cadena.CHAIN_ARTS_PURSUIT_HORIZONTAL) {
            outPacket.encodeByte(getArg5()); // isLeft
            outPacket.encodeInt(getArg7()); // dwMobID
            outPacket.encodeInt(getArg8()); // Chain End PosX
            outPacket.encodeInt(getArg9()); // Chain End PosY
        } else if (skillID == Cadena.CHAIN_ARTS_PURSUIT_PULL) {
            outPacket.encodeByte(getArg5()); // isLeft
            outPacket.encodeInt(getArg8()); // Chain End PosX
            outPacket.encodeInt(getArg9()); // Chain End PosY
            outPacket.encodeInt(getArg6()); // Origin Skill
        } else if (skillID == WildHunter.CALL_OF_THE_HUNTER) {
            outPacket.encodeByte(getArg5()); // bLeft
            outPacket.encodeShort(getArg6()); // nPosX
            outPacket.encodeShort(getArg7()); // nPosY
        } else if (skillID == WildHunter.CAPTURE) {
            outPacket.encodeByte(getArg5()); // nType: 0 = Success, 1 = mob hp too high, 2 = mob cannot be captured
        } else if (skillID == Kaiser.VERTICAL_GRAPPLE || skillID == AngelicBuster.GRAPPLING_HEART || skillID == Job.ROPE_LIFT) {
            // vertial grapples
            outPacket.encodeInt(getArg5()); // nStartPosY
            outPacket.encodeInt(getArg6()); // ptRopeConnectDest.x
            outPacket.encodeInt(getArg7()); // ptRopeConnectDest.y
        } else if (skillID == Luminous.FLASH_BLINK || skillID == ThunderBreaker.FLASH || skillID == Shade.FOX_TROT
                || skillID == Shadower.INTO_DARKNESS || skillID == Jett.VORTEX_JUMP || skillID == Shadower.TRICKBLADE_FINISHER
                || skillID == Illium.CRYSTALLINE_WINGS) { // Flash, Vortex Jump
            outPacket.encodeInt(getArg5()); // ptBlinkLightOrigin.x
            outPacket.encodeInt(getArg6()); // ptBlinkLightOrigin.y
            outPacket.encodeInt(getArg7()); // ptBlinkLightDest.x
            outPacket.encodeInt(getArg8()); // ptBlinkLightDest.y
        } else if (SkillConstants.isSuperNovaSkill(skillID)) {
            outPacket.encodeInt(getArg5()); // ptStartX
            outPacket.encodeInt(getArg6()); // ptStartY
        } else if (SkillConstants.isSomeBlasterSkill(skillID)) {
            outPacket.encodeInt(getArg5());
        } else if (skillID == NightWalker.SHADOW_SPEAR_AA_LARGE) {
            outPacket.encodeInt(getArg5());
            outPacket.encodeInt(getArg6());
        } else if (skillID == Phantom.LUCK_OF_THE_DRAW) {
            outPacket.encodeInt(getArg5());
        } else if (SkillConstants.isLuckOfTheDrawSkill(skillID)) {
            outPacket.encodeInt(getArg5());
        } else if (skillID == Cadena.CHAIN_ARTS_VOID_STRIKE_ATTACK) {
            outPacket.encodePositionInt(skillUseInfo.startingPosition);
            outPacket.encodePositionInt(skillUseInfo.endingPosition);
            outPacket.encodePositionInt(skillUseInfo.position);
            outPacket.encodeInt(skillUseInfo.unk);
            outPacket.encodeInt(skillUseInfo.angle);
        } else if (skillID == SkillConstants.MOONLIGHT_SPATIAL_TRANSFER || skillID == SkillConstants.MOONLIGHT_HP_RECOVERY || skillID == SkillConstants.MOONLIGHT_WEB_SWEEP || skillID == SkillConstants.MOONLIGHT_INVINCIBILITY) {
            // Moonlight skills
            outPacket.encodeInt(getArg5());
        } else if (skillID == Pathfinder.CARDINAL_TORRENT || skillID == Pathfinder.CARDINAL_TORRENT_ADVANCED) {
            outPacket.encodeInt(getArg5());
        } else if (skillID == HoYoung.BOTTLE_SUCTION_1) {
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
        } else if (skillID == Kinesis.PSYCHIC_TORNADO_4 || skillID == Kinesis.PSYCHIC_TORNADO_5 || skillID == Kinesis.PSYCHIC_TORNADO_6 || skillID == Pathfinder.RELIC_UNBOUND_BURST_2) { // Psychic Tornado + Relic Unbound
            outPacket.encodeByte(skillUseInfo.isLeft);
            outPacket.encodePositionInt(skillUseInfo.endingPosition);
        } else if (skillID == PinkBean.PINK_BEANS_DIGNITY_MINI_BEAN_ATTACK) { // Pink Bean's Dignity: Mini Pink Bean Attack
            outPacket.encodeByte(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
        } else if (skillID == Ark.ENDLESSLY_STARVING_BEAST) { // Endlessly Starving Beast
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
        } else if (skillID == Corsair.DEATH_TRIGGER || skillID == Corsair.DEATH_TRIGGER_2) { // Death Trigger
            // sub
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
        } else if (skillID == Kain.GRAPPLING_WIRE) {
            outPacket.encodeByte(skillUseInfo.isLeft);
            outPacket.encodePositionInt(skillUseInfo.position);
            outPacket.encodeInt(skillUseInfo.angle);
        } else if (SkillConstants.isKainShadowStep(skillID)) {
            outPacket.encodeByte(skillUseInfo.isLeft);
            outPacket.encodePositionInt(skillUseInfo.position);
        } else if (skillID == 80001132) { // doesn't exist :thonk:; hardcode-ok
            outPacket.encodeByte(getArg5());
        } else if (SkillConstants.isUnregisteredSkill(skillID)) {
            outPacket.encodeByte(getArg5()); // bLeft
        } else if (skillID == Lara.ABSORPTION_SUNLIT_GRAIN_ACTIVE_2) {
            outPacket.encodeByte(getArg5());
            outPacket.encodePositionInt(new Position(getArg6(), getArg7()));
        }
    }

    public static Effect lotteryUse(int lotteryItem) { // the box / lottery item that has opened
        Effect effect = new Effect(LotteryUse);

        effect.setArg1(lotteryItem);
        effect.setArg2(0);

        return effect;
    }

    public static Effect lotteryUIResult(int itemId, int quantity) {
        Effect effect = new Effect(LotteryUIResult);

        effect.setArg3(itemId);
        effect.setArg4(quantity);

        return effect;
    }

    public static Effect changeHPEffect(int hpDiff) {
        Effect effect = new Effect(IncDecHPEffect_EX);

        effect.setArg1(hpDiff);

        return effect;
    }


    public static Effect createFieldTextEffect(String msg, int letterDelay, int showTime, int clientPosition,
                                               Position boxPos, int align, int lineSpace, TextEffectType type,
                                               int enterType, int leaveType) {
        Effect effect = new Effect(TextEffect);

        effect.setString(msg);
        effect.setArg1(letterDelay);
        effect.setArg2(showTime);
        effect.setArg3(clientPosition);
        effect.setArg4(boxPos.getX());
        effect.setArg5(boxPos.getY());
        effect.setArg6(align);
        effect.setArg7(lineSpace);
        effect.setArg8(type.getVal());
        effect.setArg9(enterType);
        effect.setArg10(leaveType);

        return effect;
    }

    /** Speed Mirage afterimages: each {x, y, left (0/1), angle}. */
    public static Effect speedMirage(int skillId, int charId, List<int[]> afterimages) {
        Effect effect = new Effect(SpeedMirageEff);
        effect.setArg1(skillId);
        effect.setArg2(charId);
        effect.speedMirages = afterimages;
        return effect;
    }

    public static Effect skillPreLoopEnd(int skillId, int timeInMS) {
        Effect effect = new Effect(SkillPreLoopEnd);

        effect.setArg1(skillId);
        effect.setArg2(timeInMS);

        return effect;
    }

    public static Effect createABRechargeEffect() { // Angelic Buster's Recharge userEffect
        Effect effect = new Effect(ResetOnStateForOnOffSkill);

        return effect;
    }

    public static Effect fieldItemConsumed(int expGained) { // [exp]EXP+  effect from looting ExpOrbs
        Effect effect = new Effect(FieldExpItemConsumed);

        effect.setArg1(expGained);

        return effect;
    }

    public static Effect skillModeEffect(int skillID, int mode, int modeStatus) { // Unknown Effect
        Effect effect = new Effect(SkillMode);

        effect.setArg1(skillID);
        effect.setArg2(mode); //rotate
        effect.setArg3(modeStatus); //skip frame

        return effect;
    }

    public static Effect robbinsBombEffect(boolean reset, int bombCount, byte number) { //Displays bomb with [bombCount] on the Bomb, above user,   Unsure what '[int] number' does
        Effect effect = new Effect(RobbinsBomb);

        effect.setArg1(reset ? 1 : 0);  // if true, resets/delets bombs
        effect.setArg2(bombCount);      // Number displayed on the Bomb
        effect.setArg3(number);         // unknown

        return effect;
    }

    public static Effect lefMonsterNumberEffect(int count) { //Displays arrow pointing towards the user, with [count] on the arrow,  Maximum count is 5
        Effect effect = new Effect(LeftMonsterNumber);

        effect.setArg1(count); // number/counter on the arrow

        return effect;
    }

    public static Effect petBuffEffect() { //Displays the PetBuff Effect onto the user
        Effect effect = new Effect(PetBuff);

        return effect;
    }

    public static Effect jewelCraftResultEffect(byte result, int itemID) { // result = Displays Success/Fail effect above player head with smile/frown respectively, itemID will show itemName in chat
        Effect effect = new Effect(JewelCraft);

        effect.setArg1(result); // 0, 2  = Success/Smile,       5 = Request denied due to unk error,        Everything else = Fail/Frown
        effect.setArg2(itemID);

        return effect;
    }

    /*
        public static Effect azwanSpearEffect(byte colour) { // 0 = red,    1 = orange
            Effect effect = new Effect();
            effect.setUserEffectType(AswanSiegeAttack);

            effect.setArg1(colour);

            return effect;
        }
    */
    public static Effect blindEffect(boolean active) {  // gives User the Blind Status Effect
        Effect effect = new Effect(BlindEffect);

        effect.setArg1(active ? 1 : 0);

        return effect;
    }

    public static Effect soulStoneUseEffect() { // gives the SoulStone Used Chat
        Effect effect = new Effect(SoulStoneUse);

        return effect;
    }

    public static Effect expItemConsumedEffect() { // gives exp item consumption effect
        Effect effect = new Effect(ExpItemConsumed);

        return effect;
    }

    public static Effect itemMakerEffect(boolean success) { // displays itemMaker result
        Effect effect = new Effect(ItemMaker);

        effect.setArg1(success ? 0 : 1); // 0 = Success,    1 = Failure

        return effect;
    }

    public static Effect itemLevelUpEffect() { //displays the Equipment Level Up effect
        Effect effect = new Effect(ItemLevelUp);

        return effect;
    }

    public static Effect questCompleteEffect() { //displays the Quest Complete effect
        Effect effect = new Effect(QuestComplete);

        return effect;
    }

    public static Effect incDecHPEffect(byte amount) { //displays the HP healing  effect
        Effect effect = new Effect(IncDecHPEffect);

        effect.setArg1(amount); // amount shown being healed,  0 = miss

        return effect;
    }

    public static Effect changeJobEffect(net.swordie.ms.client.character.Char chr) { //displays the JobAdvancement Effect
        Effect effect = new Effect(JobChanged);
        effect.setArg1(chr.getJob());
        effect.setArg2(chr.getAvatarData().getCharacterStat().getSubJob());

        return effect;
    }

    public static Effect buffItemEffect() { //displays the Buff Item Effect
        Effect effect = new Effect(BuffItemEffect);

        return effect;
    }

    public static Effect resistDamageEffect() { //displays the "Resist" Hit
        Effect effect = new Effect(Resist);

        return effect;
    }

    public static Effect levelUpEffect() { //displays the Level Up  Effect
        Effect effect = new Effect(LevelUp);

        return effect;
    }

    public static void showFameGradeUp(Char chr) {
        Field field = chr.getField();
        chr.write(UserPacket.effect(Effect.avatarOriented("Effect/BasicEff.img/FameGradeUp/front")));
        chr.write(UserPacket.effect(Effect.avatarOriented("Effect/BasicEff.img/FameGradeUp/back")));
        field.broadcastPacket(UserRemote.effect(chr.getId(), Effect.avatarOriented("Effect/BasicEff.img/FameGradeUp/front")));
        field.broadcastPacket(UserRemote.effect(chr.getId(), Effect.avatarOriented("Effect/BasicEff.img/FameGradeUp/back")));
    }

    public static Effect skillUse(int skillID, int chrLv, int slv, int bySummonedID) {
        Effect effect = new Effect(bySummonedID == 0 ? SkillUse : SkillUseBySummoned);

        effect.setArg4(bySummonedID);
        effect.setArg1(skillID);
        effect.setArg2(chrLv);
        effect.setArg3(slv);

        return effect;
    }

    public static Effect skillAffected(int skillID, int slv, int hpGain) {
        Effect effect = new Effect(SkillAffected);

        effect.setArg1(skillID);
        effect.setArg2(slv);
        effect.setArg3(hpGain);

        return effect;
    }

    public static Effect skillAffectedSelect(int skillID, int slv, int select, boolean special) {
        return skillAffectedSelect(skillID, slv, select, -1, special);
    }

    public static Effect skillAffectedSelect(int skillID, int slv, int select, int rootSelect, boolean special) {
        Effect effect = new Effect(SkillAffected_Select);

        effect.setArg1(select);
        effect.setArg2(rootSelect);
        effect.setArg3(skillID);
        effect.setArg4(slv);
        effect.setArg5(special ? 1 : 0);

        return effect;
    }

    public static Effect skillSpecial(int skillID) {
        Effect effect = new Effect(SkillSpecial);

        effect.setArg1(skillID);

        return effect;
    }

    /** Mo Xuan: Heir of the Divine (skill, a, b, c bytes), Qi Shield (skill, int), The Holy Mountain (skill). */
    public static Effect moXuanEffect(int skillID, int a, int b, int c) {
        Effect effect = new Effect(MoXuanEff);

        effect.setArg1(skillID);
        effect.setArg2(a);
        effect.setArg3(b);
        effect.setArg4(c);

        return effect;
    }

    public static Effect skillSpecialAffected(int skillID, int slv) {
        Effect effect = new Effect(SkillSpecialAffected);

        effect.setArg1(skillID);
        effect.setArg2(slv);

        return effect;
    }

    /** GMS form: the second int is the character's level. */
    public static Effect skillSpecialAffected(int skillID, int slv, int chrLevel) {
        Effect effect = skillSpecialAffected(skillID, slv);
        effect.setArg3(chrLevel);
        return effect;
    }

    public static Effect gainQuestItem(int itemID, int quantity) {
        Effect effect = new Effect(Quest);

        effect.setList(Collections.singletonList(new Tuple<>(itemID, quantity)));

        return effect;
    }

    public static Effect gainQuestItem(List<Tuple<Integer, Integer>> items) {
        Effect effect = new Effect(Quest);

        effect.setList(items);

        return effect;
    }

    public static Effect showChainArtPursuitEffect(int skillId, int chrLv, int slv, boolean isLeft, Position chainPos) {
        Effect effect = new Effect(SkillUse);

        effect.setArg1(skillId);
        effect.setArg2(chrLv);
        effect.setArg3(slv);
        effect.setArg5(isLeft ? 1 : 0);
        effect.setArg8(chainPos.getX());
        effect.setArg9(chainPos.getY());

        return effect;
    }

    public static Effect showChainArtPursuitPullEffect(int skillId, int chrLv, int slv, boolean isLeft, Position chainPos, int originSkill) {
        Effect effect = new Effect(SkillUse);

        effect.setArg1(skillId);
        effect.setArg2(chrLv);
        effect.setArg3(slv);
        effect.setArg5(isLeft ? 1 : 0);
        effect.setArg6(originSkill);
        effect.setArg8(chainPos.getX());
        effect.setArg9(chainPos.getY());

        return effect;
    }

    public static Effect showDragonFuryEffect(int skillID, int slv, int bySummonedID, boolean show) {
        Effect effect = new Effect(bySummonedID == 0 ? SkillUse : SkillUseBySummoned);

        effect.setArg4(bySummonedID);
        effect.setArg1(skillID);
        effect.setArg2(slv);
        effect.setArg3(slv);
        effect.setArg5(show ? 1 : 0);

        return effect;
    }

    public static Effect showFinalPactEffect(int skillID, int slv, int bySummonedID, boolean show) {
        Effect effect = new Effect(bySummonedID == 0 ? SkillUse : SkillUseBySummoned);

        effect.setArg4(bySummonedID);
        effect.setArg1(skillID);
        effect.setArg2(slv);
        effect.setArg3(slv);
        effect.setArg5(show ? 1 : 0);

        return effect;
    }

    public static Effect showChainsOfHellEffect(int skillID, int slv, int bySummonedID, boolean left, int mobId) {
        Effect effect = new Effect(bySummonedID == 0 ? SkillUse : SkillUseBySummoned);

        effect.setArg4(bySummonedID);
        effect.setArg1(skillID);
        effect.setArg2(slv);
        effect.setArg3(slv);
        effect.setArg5(left ? 1 : 0);
        effect.setArg6(mobId);

        return effect;
    }

    public static Effect showHookEffect(int skillID, int chrLv, int slv, int bySummonedID, boolean left, int mobId, int mobPosX, int mobPosY) {
        Effect effect = new Effect(bySummonedID == 0 ? SkillUse : SkillUseBySummoned);

        effect.setArg4(bySummonedID);
        effect.setArg1(skillID);
        effect.setArg2(chrLv);
        effect.setArg3(slv);
        effect.setArg5(left ? 1 : 0);
        effect.setArg6(mobId);
        effect.setArg7(mobPosX);
        effect.setArg8(mobPosY);

        return effect;
    }

    public static Effect showCallOfTheHunterEffect(int skillID, int slv, int bySummonedID, boolean left, int mobPosX, int mobPosY) {
        Effect effect = new Effect(bySummonedID == 0 ? SkillUse : SkillUseBySummoned);

        effect.setArg4(bySummonedID);
        effect.setArg1(skillID);
        effect.setArg2(slv);
        effect.setArg3(slv);
        effect.setArg5(left ? 1 : 0);
        effect.setArg6(mobPosX);
        effect.setArg7(mobPosY);

        return effect;
    }

    public static Effect showCaptureEffect(int skillID, int slv, int bySummonedID, int type) {
        Effect effect = new Effect(bySummonedID == 0 ? SkillUse : SkillUseBySummoned);

        effect.setArg4(bySummonedID);
        effect.setArg1(skillID);
        effect.setArg2(slv);
        effect.setArg3(slv);
        effect.setArg5(type); // Type: 0 = Success,  1 = mob hp too high,  2 = mob cannot be captured

        return effect;
    }

    public static Effect showVerticalGrappleEffect(int skillID, int chrLv, int slv, int bySummonedID, int startPosY,
                                                   int ropeConnectDestX, int ropeConnectDestY) {
        Effect effect = new Effect(bySummonedID == 0 ? SkillUse : SkillUseBySummoned);

        effect.setArg4(bySummonedID);
        effect.setArg1(skillID);
        effect.setArg2(chrLv);
        effect.setArg3(slv);
        effect.setArg5(startPosY);
        effect.setArg6(ropeConnectDestX);
        effect.setArg7(ropeConnectDestY);

        return effect;
    }

    public static Effect showFlashBlinkEffect(int skillID, int slv, int bySummonedID, int blinkOriginX,
                                              int blinkOriginY, int blinkDestX, int blinkDestY) {
        Effect effect = new Effect(bySummonedID == 0 ? SkillUse : SkillUseBySummoned);

        effect.setArg4(bySummonedID);
        effect.setArg1(skillID);
        effect.setArg2(slv);
        effect.setArg3(slv);
        effect.setArg5(blinkOriginX);
        effect.setArg6(blinkOriginY);
        effect.setArg7(blinkDestX);
        effect.setArg8(blinkDestY);

        return effect;
    }

    public static Effect showSuperNovaEffect(int skillID, int slv, int bySummonedID, int x, int y) {
        Effect effect = new Effect(bySummonedID == 0 ? SkillUse : SkillUseBySummoned);

        effect.setArg4(bySummonedID);
        effect.setArg1(skillID);
        effect.setArg2(slv);
        effect.setArg3(slv);
        effect.setArg5(x);
        effect.setArg6(y);

        return effect;
    }

    public static Effect showUnregisteredSkill(int skillID, int slv, int bySummonedID, boolean left) {
        Effect effect = new Effect(bySummonedID == 0 ? SkillUse : SkillUseBySummoned);

        effect.setArg4(bySummonedID);
        effect.setArg1(skillID);
        effect.setArg2(slv);
        effect.setArg3(slv);
        effect.setArg5(left ? 1 : 0);

        return effect;
    }

    public static Effect showDarkShockSkill(int skillId, int slv, Position origin, Position dest) {
        Effect effect = new Effect(SkillSpecial);

        effect.setArg1(skillId);
        effect.setArg2(slv);
        effect.setArg3(0); // show effect
        effect.setArg4(origin.getX());
        effect.setArg5(origin.getY());
        effect.setArg6(dest.getX());
        effect.setArg7(dest.getY());

        return effect;
    }

    public static Effect showAbyssalLightningSkill(int skillId, Position origin, Position dest) {
        Effect effect = new Effect(SkillSpecial);

        effect.setArg1(skillId);
        effect.setArg4(origin.getX());
        effect.setArg5(origin.getY());
        effect.setArg6(dest.getX());
        effect.setArg7(dest.getY());

        return effect;
    }

    public static Effect showSillhouteMirageAttack(int skillId, int sillhoute, Position pos) {
        Effect effect = new Effect(SkillSpecial);

        effect.setArg1(skillId);
        effect.setArg2(sillhoute);
        effect.setArg3(pos.getX());
        effect.setArg4(pos.getY());

        return effect;
    }

    public static Effect effectFromWZ(String effectPath, boolean flip, int duration, int type, int itemID) {
        Effect effect = new Effect(EffectUOL);

        effect.setString(effectPath);
        effect.setArg1(flip ? 1 : 0);
        effect.setArg2(duration);
        effect.setArg3(type);
        if (type == 2) {
            effect.setArg4(itemID);
        }

        return effect;
    }

    public static Effect fadeInOut(int fadeIn, int delay, int fadeOut, int alpha) {
        Effect effect = new Effect(FadeInOut);

        effect.setArg1(fadeIn);
        effect.setArg2(delay);
        effect.setArg3(fadeOut);
        effect.setArg4(alpha);
        return effect;
    }

    public static Effect avatarOriented(String effectPath) {
        Effect effect = new Effect(AvatarOriented);

        effect.setString(effectPath);

        return effect;
    }

    public static Effect avatarOrientedRepeat(String effectPath, int durationMS, int showType) {
        Effect effect = new Effect(AvatarOrientedRepeat);

        effect.setString(effectPath);
        effect.setArg1(1); // bShow
        effect.setArg2(durationMS); // duration MS
        effect.setArg3(showType);

        return effect;
    }

    public static Effect reservedEffect(String effectPath) {
        Effect effect = new Effect(ReservedEffect);

        effect.setArg1(0);// bShow
        effect.setArg2(0);
        effect.setArg3(0);
        effect.setString(effectPath);

        return effect;
    }

    public static Effect reservedEffect(boolean screenCoord, int x, int y, String effectName) {
        Effect effect = new Effect(ReservedEffect);

        effect.arg1 = screenCoord ? 1 : 0;
        effect.arg2 = x;
        effect.arg3 = y;
        effect.string = effectName;

        return effect;
    }

    public static Effect reservedEffectRepeat(String effectPath, boolean start) {
        Effect effect = new Effect(ReservedEffectRepeat);

        effect.setString(effectPath);
        effect.setArg1(start ? 1 : 0);
        return effect;
    }

    public static Effect reservedEffectRepeat(String effectName, boolean idk, boolean show, int x, int y, int duration) {
        Effect effect = new Effect(ReservedEffectRepeat);

        effect.string = effectName;
        effect.arg1 = idk ? 1 : 0;
        effect.arg2 = duration;
        effect.arg3 = show ? 1 : 0;
        effect.arg4 = x;
        effect.arg5 = y;

        return effect;
    }

    public static Effect playExclSoundWithDownBGM(String soundPath, int volume) {
        Effect effect = new Effect(PlayExclSoundWithDownBGM);

        effect.setString(soundPath);
        effect.setArg1(volume);

        return effect;
    }
/*
*
                boolean normal = inPacket.decodeByte() != 0;
                int idx = inPacket.decodeInt();
                int linkType = inPacket.decodeInt();
                String speech = inPacket.decodeString();
                int time = inPacket.decodeInt();
                int align = inPacket.decodeInt();
                int x = inPacket.decodeInt();
                int y = inPacket.decodeInt();
                int z = inPacket.decodeInt();
                int lineSpace = inPacket.decodeInt();
                int npcTemplateId = inPacket.decodeInt();*/

    public static Effect speechBalloon(boolean normal, int idx, int linkType, String speech, int time, int align, int x,
                                       int y, int z, int lineSpace, int npcTemplateId, int idk) {
        Effect effect = new Effect(SpeechBalloon);

        effect.arg1 = normal ? 1 : 0;
        effect.arg2 = idx;
        effect.arg3 = linkType;
        effect.string = speech;
        effect.arg4 = time;
        effect.arg5 = align;
        effect.arg6 = x;
        effect.arg7 = y;
        effect.arg8 = z;
        effect.arg9 = lineSpace;
        effect.arg10 = npcTemplateId;
        effect.arg11 = idk;

        return effect;
    }

    /** A field skill's hit on this character (GMS: the Black Mage's crimson columns, 100017). */
    public static Effect fieldSkillHit(int skillID, int slv) {
        Effect effect = new Effect(unk48);

        effect.setArg1(skillID);
        effect.setArg2(slv);

        return effect;
    }

    /** The Black Mage's field skill effect on this character: for 100017 the column set and its stage. */
    public static Effect blackMageEffect(int skillID, int slv, int set, int stage) {
        Effect effect = new Effect(BlackMageEffect);

        effect.setArg1(skillID);
        effect.setArg2(slv);
        effect.setArg3(set);
        effect.setArg4(stage);

        return effect;
    }

    public static Effect mobSkillHit(int skillID, int slv) {
        Effect effect = new Effect(MobSkillHit);

        effect.setArg1(skillID);
        effect.setArg2(slv);

        return effect;
    }

    public static Effect starVortex(int skillID, int slv, Position originPos, int size) {
        Effect effect = new Effect(StarVortex);

        effect.setArg1(skillID); // skillid
        effect.setArg2(slv); // slv
        effect.setArg3(originPos.getX()); // origin pos
        effect.setArg4(originPos.getY()); // origin pos
        effect.setArg5(size); // amount spawned

        return effect;
    }

    public static Effect mobSkillSpecial(boolean success, MobSkillID mobSkillID, int slv) {
        Effect effect = new Effect(MobSkillSpecial);

        effect.setArg1(success ? 1 : 0);
        effect.setArg2(mobSkillID.getVal());
        effect.setArg3(slv);

        return effect;
    }

    public Effect(UserEffectType userEffectType) {
        this.userEffectType = userEffectType;
    }

    public Effect() {
    }

    public void setUserEffectType(UserEffectType userEffectType) {
        this.userEffectType = userEffectType;
    }

    public UserEffectType getUserEffectType() {
        return userEffectType;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setList(List<Tuple<Integer, Integer>> list) {
        this.list = list;
    }

    public List<Tuple<Integer, Integer>> getList() {
        return list;
    }

    public SkillUseInfo getSkillUseInfo() {
        return skillUseInfo;
    }

    public void setSkillUseInfo(SkillUseInfo skillUseInfo) {
        this.skillUseInfo = skillUseInfo;
    }

    public int getArg1() {
        return arg1;
    }

    public void setArg1(int arg1) {
        this.arg1 = arg1;
    }

    public int getArg2() {
        return arg2;
    }

    public void setArg2(int arg2) {
        this.arg2 = arg2;
    }

    public int getArg3() {
        return arg3;
    }

    public void setArg3(int arg3) {
        this.arg3 = arg3;
    }

    public int getArg4() {
        return arg4;
    }

    public void setArg4(int arg4) {
        this.arg4 = arg4;
    }

    public int getArg5() {
        return arg5;
    }

    public void setArg5(int arg5) {
        this.arg5 = arg5;
    }

    public int getArg6() {
        return arg6;
    }

    public void setArg6(int arg6) {
        this.arg6 = arg6;
    }

    public int getArg7() {
        return arg7;
    }

    public void setArg7(int arg7) {
        this.arg7 = arg7;
    }

    public int getArg8() {
        return arg8;
    }

    public void setArg8(int arg8) {
        this.arg8 = arg8;
    }

    public int getArg9() {
        return arg9;
    }

    public void setArg9(int arg9) {
        this.arg9 = arg9;
    }

    public int getArg10() {
        return arg10;
    }

    public void setArg10(int arg10) {
        this.arg10 = arg10;
    }

    public int getArg11() {
        return arg11;
    }

    public void setArg11(int arg11) {
        this.arg11 = arg11;
    }
}
