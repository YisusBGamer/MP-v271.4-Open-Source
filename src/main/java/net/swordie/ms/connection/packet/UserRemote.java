package net.swordie.ms.connection.packet;

import net.swordie.ms.client.jobs.adventurer.Yeti;
import net.swordie.ms.client.jobs.adventurer.Kinesis;
import net.swordie.ms.client.jobs.flora.Khali;
import net.swordie.ms.client.jobs.legend.Aran;
import net.swordie.ms.client.jobs.sengoku.Hayato;
import net.swordie.ms.client.jobs.shine.ErelLight;
import net.swordie.ms.client.jobs.resistance.Blaster;
import net.swordie.ms.client.jobs.legend.Phantom;
import net.swordie.ms.client.jobs.legend.Mercedes;
import net.swordie.ms.client.jobs.flora.Ark;
import net.swordie.ms.client.jobs.flora.Adele;
import net.swordie.ms.client.jobs.cygnus.NightWalker;
import net.swordie.ms.client.jobs.adventurer.warrior.Hero;
import net.swordie.ms.client.jobs.adventurer.pirate.Buccaneer;
import net.swordie.ms.client.jobs.adventurer.archer.Marksman;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.avatar.AvatarLook;
import net.swordie.ms.client.character.chair.PortableChair;
import net.swordie.ms.client.character.info.HitInfo;
import net.swordie.ms.client.character.skills.KeyDownSkillInfo;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.MobAttackInfo;
import net.swordie.ms.client.character.skills.shootobject.ShootObject;
import net.swordie.ms.client.character.skills.shootobject.ShootObjectSkillInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.character.skills.temp.V271RemoteStats;
import net.swordie.ms.client.guild.Guild;
import net.swordie.ms.client.jobs.Zero;
import net.swordie.ms.client.jobs.adventurer.BeastTamer;
import net.swordie.ms.client.jobs.adventurer.pirate.Cannoneer;
import net.swordie.ms.client.jobs.adventurer.thief.NightLord;
import net.swordie.ms.client.jobs.adventurer.warrior.Paladin;
import net.swordie.ms.client.jobs.anima.HoYoung;
import net.swordie.ms.client.jobs.cygnus.DawnWarrior;
import net.swordie.ms.client.jobs.cygnus.Mihile;
import net.swordie.ms.client.jobs.cygnus.ThunderBreaker;
import net.swordie.ms.client.jobs.nova.Cadena;
import net.swordie.ms.client.jobs.nova.Kain;
import net.swordie.ms.client.jobs.resistance.BattleMage;
import net.swordie.ms.client.jobs.sengoku.Kanna;
import net.swordie.ms.client.character.modules.CustomTitleModule;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.AvatarModifiedMask;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.movement.MovementInfo;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;

import java.util.List;


/**
 * Created on 2/3/2018.
 */
public class UserRemote {
    public static OutPacket setActiveNickItem(Char chr, String msg) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_SET_ACTIVE_NICK_ITEM);

        outPacket.encodeInt(chr.getId());
        outPacket.encodeInt(chr.getNickItem());
        outPacket.encodeByte(msg != null);
        if (msg != null) {
            outPacket.encodeString(msg);
        }
        if (chr.getNickItem() == ItemConstants.CUSTOM_TITLE_ITEM_ID || chr.getNickItem() == 3700870) { // hardcode-ok
            CustomTitleModule.encodeCustomTitle(chr, outPacket); // v265
        }

        return outPacket;
    }

    public static OutPacket move(Char chr, MovementInfo movementInfo) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_MOVE);

        outPacket.encodeInt(chr.getId());
        movementInfo.encodeWithoutKeyPads(outPacket); // GMS sends the path without the key pad part

        return outPacket;
    }

    public static OutPacket emotion(int id, int emotion, int duration, boolean byItemOption) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_EMOTION);

        outPacket.encodeInt(id);
        outPacket.encodeInt(emotion);
        outPacket.encodeInt(duration);
        outPacket.encodeByte(byItemOption);

        return outPacket;
    }

    public static OutPacket attack(Char chr, AttackInfo ai) {
        OutHeader attackType = ai.attackHeader;
        int skillID = ai.skillId;
        OutPacket outPacket = new OutPacket(attackType);
        outPacket.encodeInt(chr.getId());

        outPacket.encodeByte(ai.fieldKey);
        outPacket.encodeByte(ai.mobCount << 4 | ai.hits);
        outPacket.encodeInt(chr.getLevel());
        outPacket.encodeInt(ai.slv);

        if (ai.slv > 0) {
            outPacket.encodeInt(skillID);
        }

        if (SkillConstants.isZeroSkill(skillID)) {
            outPacket.encodeByte(ai.zeroTag);
            if (ai.zeroTag != 0) {
                outPacket.encodePosition(chr.getPosition());
            }
        }

        Boolean shootPassive = RemoteAttackLayout.hasShootPassive(skillID);
        if (shootPassive == null) {
            shootPassive = SkillConstants.getAdvancedCountHyperSkill(skillID) != 0
                    || SkillConstants.getAdvancedAttackCountHyperSkill(skillID) != 0;
        }
        if (attackType == OutHeader.REMOTE_SHOOT_ATTACK && shootPassive) {
            outPacket.encodeInt(ai.passiveSLV);
            if (ai.passiveSLV > 0) {
                outPacket.encodeInt(ai.passiveSkillID);
            }
        }

        if (skillID == SkillConstants.SHIKIGAMI_HAUNTING_3RD_HIT) { // Shikigami haunting (3rd hit)
            int val = 0;
            outPacket.encodeInt(val);
            if (val != 0) {
                outPacket.encodeInt(0);
            }
        }

        outPacket.encodeByte(ai.someMask);
        outPacket.encodeByte(ai.buckShot);
        outPacket.encodeInt(ai.option3);
        outPacket.encodeInt(ai.bySummonedID);
        outPacket.encodeInt(RemoteAttackLayout.repeatIndex(chr.getId(), skillID));
        outPacket.encodeByte(0);

        if ((ai.buckShot & 2) != 0) {
            Option o = chr.getTemporaryStatManager().getOption(CharacterTemporaryStat.BuckShot);
            if (o != null) {
                outPacket.encodeInt(o.rOption);
                outPacket.encodeInt(o.nOption);
            } else {
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
            }
        }

        if ((ai.buckShot & 8) != 0) {
            outPacket.encodeByte(ai.psdTargetPlus);
        }

        byte left = (byte) (ai.left ? 1 : 0);
        outPacket.encodeShort((left << 15) | ai.attackAction);
        if (ai.attackAction < GameConstants.MAX_ATTACK_ACTION) {
            outPacket.encodeByte(ai.dragon ? ai.dragonAttackActionType : ai.attackActionType);
            outPacket.encodeShort(ai.x);
            outPacket.encodeShort(ai.y);
            outPacket.encodeByte(ai.dragonAttackStart); //
            outPacket.encodeByte(ai.dragonAttackProgess); //
            outPacket.encodeByte(ai.attackSpeed);

            outPacket.encodeByte(chr.getTotalStat(BaseStat.mastery));
            outPacket.encodeInt(ai.bulletID);
            for (MobAttackInfo mai : ai.mobAttackInfo) {
                outPacket.encodeInt(mai.mobId);
                if (mai.mobId > 0) {
                    outPacket.encodeByte(mai.hitAction); //
                    outPacket.encodeByte(mai.left);
                    outPacket.encodeByte(mai.idk3);
                    outPacket.encodeShort(mai.idk6); //
                    outPacket.encodeInt(0); // new 188
                    outPacket.encodeInt(0); // new 200
                    // v271 (0x145699030): each damage is 8 bytes with the critical flag in the top bit; Soul Shear
                    // (80001835 only) has its own count; no psychic-lock int after the damage
                    if (skillID == SkillConstants.SOUL_SHEAR) {
                        outPacket.encodeByte(mai.damages.length);
                    }
                    for (int i = 0; i < mai.damages.length; i++) {
                        long dmg = mai.damages[i] & Long.MAX_VALUE;
                        outPacket.encodeLong(mai.crits[i] ? dmg | Long.MIN_VALUE : dmg);
                    }
                    if (skillID == Blaster.ROCKET_RUSH) { // rocket rush
                        outPacket.encodeByte(mai.isResWarriorLiftPress); // boolean
                    }
                    if (skillID == HoYoung.BOTTLE_SUCTION_1) {
                        outPacket.encodeInt(mai.templateID); // mobId
                    }
                }
            }

            Integer extra = RemoteAttackLayout.extraBytes(skillID);
            if (skillID == ErelLight.PATH_OF_FIONN && ai.clientTail != null && ai.clientTail[0] == 1) {
                // flag + position, which the request ends with
                outPacket.encodeArr(ai.clientTail);
            } else if (extra != null) {
                outPacket.encodeArr(new byte[extra]);
            } else {
                encodeSkillExtras(outPacket, chr, ai, skillID);
            }
        }

        // GMS (melee, shoot and magic): one byte and an int after the hit list; the int is 1
        outPacket.encodeByte(0);
        outPacket.encodeInt(1);

        return outPacket;
    }

    private static void encodeSkillExtras(OutPacket outPacket, Char chr, AttackInfo ai, int skillID) {
        if (SkillConstants.isKeyDownAttackForRemote(skillID)) {
            outPacket.encodeInt(ai.keyDown);
        } else if (SkillConstants.isSuperNovaSkill(skillID) || SkillConstants.isScreenCenterAttackSkill(skillID)
                || SkillConstants.isRandomAttackSkill(skillID) || SkillConstants.isWingedJavelinOrAbyssalCast(skillID)
                || skillID == Zero.SHADOW_STRIKE || skillID == Zero.AIR_RIOT_2 || skillID == NightWalker.SHADOW_SPEAR_AA_LARGE || skillID == Marksman.SPLIT_SHOT_FINAL_ATTACK
                || skillID == Phantom.ACE_IN_THE_HOLE_FINISHER || skillID == Marksman.PIERCING_ARROW_2 || skillID == Kinesis.ULTIMATE_PSYCHIC_SHOCKWAVE || skillID == Job.OTHERWORLDY_VOID || skillID == Job.TRANSCENDENCE_BLESSING_3) {
            outPacket.encodePositionInt(ai.ptAttackRefPoint);
        }

        if (skillID == SkillConstants.FRUITFUL_BOUNTY) {
            outPacket.encodePositionInt(chr.getPosition());
        }

        else if (skillID == Paladin.MIGHTY_MJOLNIR_EXPLOSION || skillID == Zero.EGO_WEAPON_ALPHA) { // Ego weapon
            outPacket.encodePositionInt(ai.chrPos);
        }

        else if (skillID == Kinesis.LAW_OF_GRAVITY_2 || skillID == Kinesis.LAW_OF_GRAVITY_3) { // Law of gravity
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
        }

        else if (skillID == Kain.THANATOS_DESCENT_2) { // Kain Future Skill
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
        }

        else if (skillID == Cannoneer.POOLMAKER_ATTACK) {
            outPacket.encodeByte(ai.poolmakerEnabled); // bEnable
        }

        else if (skillID == NightLord.THROW_BLASTING_ATTACK
                || skillID == NightLord.THROW_BLASTING_CD
                || skillID == Ark.ENDLESSLY_STARVING_BEAST // Endlessly Starving Beast
                || skillID == Cadena.MUSCLE_MEMORY_FINALE
                || skillID == HoYoung.THREE_PATHS_APPARITION_ATTACK_1
                || skillID == HoYoung.THREE_PATHS_APPARITION_ATTACK_2
                || skillID == HoYoung.THREE_PATHS_APPARITION_ATTACK_3
                || skillID == Hero.SWORD_ILLUSION_2 // Sword Illusion
                || skillID == Hero.SWORD_ILLUSION_3 // Sword Illusion
                || skillID == Ark.BLISSFUL_RESTRAINT
                || skillID == Kain.INCARNATION_2) {
            outPacket.encodePositionInt(ai.sui.position);
        }

        else if (skillID == ThunderBreaker.LIGHTNING_SPEAR_MULTISTRIKE_THUNDERBOLT_1 || skillID == ThunderBreaker.LIGHTNING_SPEAR_MULTISTRIKE_THUNDERBOLT_2) {
            // Surely Position
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
        }

        // 400021107 no encode

        else if (skillID == Kain.SHAFT_BREAK_EXPLOSION || skillID == 63111101 || skillID == 63111102) { // Kain Future Skills; hardcode-ok
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
        }

        else if (SkillConstants.isSentientArrowOrTornadoFlight(skillID)) {
            outPacket.encodePosition(ai.ptAttackRefPoint);
        }

        else if (skillID == Mihile.RADIANT_CROSS) {
            outPacket.encodeByte(ai.showFixedDamage);
        }

        if (skillID == BeastTamer.FORMATION_ATTACK) { // formation attack
            outPacket.encodeInt(0); // does a << 16
        }

        if (skillID == Kanna.SOUL_BOMB) { // Soul Bomb
            outPacket.encodeShort(0);
            byte size = 0;
            outPacket.encodeByte(size);
            for (int i = 0; i < size; i++) {
                outPacket.encodePosition(new Position());
            }
        }

        // Teleport Skill
        if (skillID == Aran.FINISHER_HUNTER_PREY_2 || skillID == Blaster.HYPER_MAGNUM_PUNCH || SkillConstants.isShadowAssault(skillID)
                || skillID == DawnWarrior.EQUINOX_SLASH || skillID == Buccaneer.CORKSCREW_BLOW) {
            outPacket.encodeByte(ai.teleportLeft); // new 188
            outPacket.encodePositionInt(ai.teleportPt);
        }

        // Teleport Skill
        if (skillID == BattleMage.ABYSSAL_LIGHTNING_PORTAL_ATTACK) { // Abyssal Lightning
            outPacket.encodePositionInt(ai.sui.position == null ? new Position(ai.forcedX, ai.forcedY) : ai.sui.position);
            outPacket.encodePositionInt(ai.sui.endingPosition);
        }

        boolean isSomePfSkill = SkillConstants.isSomePathfinderSkill(skillID);
        if (SkillConstants.isSomeAA(skillID) || isSomePfSkill || SkillConstants.isShootObj(skillID)) {
            outPacket.encodePosition(ai.pos3);
            if (isSomePfSkill) {
                outPacket.encodeInt(ai.shootObjId);
                outPacket.encodeByte(ai.idk5);
            }
        }

        if (SkillConstants.isSomeShootObjectSkillsForRemote(skillID)) {
            outPacket.encodeInt(ai.shootObjId);
            outPacket.encodeByte(ai.idk5);
        }

        // Angled Rush Skills
        if (skillID == Ark.UNSTOPPABLE_IMPULSE
                || skillID == Ark.TENACIOUS_INSTINCT
                || skillID == Buccaneer.SERPENT_VORTEX
                || skillID == Hayato.HITOKIRI_STRIKE_ANGLE
                || skillID == Adele.RESONANCE
                || skillID == Adele.RESONANCE_COMBO
                || skillID == Yeti.HOMEMADE_JETPACK || skillID == Yeti.HOMEMADE_SUPER_JETPACK || skillID == Yeti.HOMEMADE_JETPACK_2 // sub
        ) {
            boolean bool = true;
            outPacket.encodeByte(bool);
            if (bool) {
                outPacket.encodeInt(ai.sui.rushX);
                outPacket.encodeInt(ai.sui.rushY);
                outPacket.encodeInt(0);
            }
        }

        if (skillID == Mercedes.ROLLING_MOONSAULT || skillID == SkillConstants.BOMB_PUNCH) {
            // Rolling Moonsault / Bomb Punch
            outPacket.encodeByte(ai.addAttackProcRemote);
        }
    }

    public static OutPacket avatarModified(Char chr, byte mask, byte carryItemEffect) {
        boolean isBeta = chr.isZeroBeta();
        AvatarLook al = chr.getAvatarData().getAvatarLook(isBeta);
        AvatarLook zeroAL = chr.getAvatarData().getAvatarLook(!isBeta);

        TemporaryStatManager tsm = chr.getTemporaryStatManager();

        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_AVATAR_MODIFIED);

        outPacket.encodeInt(chr.getId());
        outPacket.encodeByte(mask);
        if ((mask & AvatarModifiedMask.AvatarLook.getVal()) != 0) {
            al.encode(outPacket);
            outPacket.encodeString(""); // v265
        }
        if ((mask & AvatarModifiedMask.SubAvatarLook.getVal()) != 0) {
            zeroAL.encode(outPacket); // subAvatarLook
        }
        if ((mask & AvatarModifiedMask.Speed.getVal()) != 0) {
            outPacket.encodeByte(tsm.getOption(CharacterTemporaryStat.Speed).nOption);
        }
        if ((mask & AvatarModifiedMask.CarryItemEffect.getVal()) != 0) {
            outPacket.encodeByte(carryItemEffect);
        }
        boolean hasCouple = chr.getCouple() != null;
        outPacket.encodeByte(hasCouple);
        if (hasCouple) {
            chr.getCouple().encodeForRemote(outPacket);
        }
        boolean hasFriendShip = chr.getFriendshipRingRecord() != null;
        outPacket.encodeByte(hasFriendShip);
        if (hasFriendShip) {
            chr.getFriendshipRingRecord().encode(outPacket);
        }
        boolean hasWedding = chr.getMarriageRecord() != null;
        outPacket.encodeByte(hasWedding);
        if (hasWedding) {
            chr.getMarriageRecord().encodeForRemote(outPacket);
        }
        outPacket.encodeInt(chr.getCompletedSetItemID());
        outPacket.encodeInt(chr.getTotalChuc());
        outPacket.encodeInt(chr.getTotalAf());
        outPacket.encodeInt(chr.getTotalStat(BaseStat.authForce));
        outPacket.encodeByte(0); // v271 (0x1456a6040): count of per-slot item effect records (0x1452adbf0)

        return outPacket;
    }

    public static OutPacket throwGrenade(int charID, int grenadeID, Position pos, int keyDown, int skillID, int bySummonedID,
                                         int slv, boolean left, int attackSpeed) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_THROW_GRENADE);

        outPacket.encodeInt(charID);

        outPacket.encodeInt(grenadeID);
        outPacket.encodePositionInt(pos);
        outPacket.encodeInt(keyDown);
        outPacket.encodeInt(skillID);
        outPacket.encodeInt(bySummonedID);
        outPacket.encodeInt(slv);
        outPacket.encodeByte(left);
        outPacket.encodeInt(attackSpeed);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        // v265: two more ints in a sub-record, unknown
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);

        return outPacket;
    }

    public static OutPacket destroyGrenade(int charID, int grenadeID, int skillID) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_DESTROY_GRENADE);

        outPacket.encodeInt(charID);
        outPacket.encodeInt(grenadeID);
        outPacket.encodeInt(skillID);

        return outPacket;
    }

    public static OutPacket receiveHP(Char chr) {
        return receiveHP(chr.getId(), chr.getHP(), chr.getTotalStat(BaseStat.mhp));
    }

    public static OutPacket receiveHP(int charID, int curHP, int maxHP) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_RECEIVE_HP);

        outPacket.encodeInt(charID);
        outPacket.encodeInt(curHP);
        outPacket.encodeInt(maxHP);

        return outPacket;
    }

    public static OutPacket hit(Char chr, HitInfo hitInfo) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_HIT);

        outPacket.encodeInt(chr.getId());

        // a miss flag, then one more byte only when it hit; a miss ends the packet (v271 0x1456a4b80)
        boolean miss = hitInfo.hpDamage == 0;
        outPacket.encodeByte(hitInfo.type);
        outPacket.encodeInt(hitInfo.hpDamage);
        outPacket.encodeByte(hitInfo.isCrit);
        outPacket.encodeByte(miss);
        if (miss) {
            return outPacket;
        }
        outPacket.encodeByte(0);
        if (hitInfo.type == -8) {
            outPacket.encodeInt(hitInfo.blockSkillId);
            outPacket.encodeInt(0);
            outPacket.encodeInt(hitInfo.otherUserID);
            outPacket.encodeByte(0);
        } else if (hitInfo.type == -12) {
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeByte(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0); // v271 (0x1456a4b80): one int more
        } else if (hitInfo.type >= -1) {
            outPacket.encodeInt(hitInfo.templateID);
            outPacket.encodeByte(hitInfo.action);
            outPacket.encodeInt(hitInfo.mobID);

            outPacket.encodeInt(0);
            outPacket.encodeInt(hitInfo.reflectDamage);
            outPacket.encodeByte(0);
            if (hitInfo.reflectDamage > 0) {
                outPacket.encodeByte(hitInfo.isGuard);
                outPacket.encodeInt(hitInfo.mobID);
                outPacket.encodeByte(hitInfo.hitAction);
                outPacket.encodePosition(chr.getPosition());
            }
            outPacket.encodeByte(hitInfo.specialEffectSkill);
            if (hitInfo.specialEffectSkill != 0) { // v271 reads the int for any non-zero byte
                outPacket.encodeInt(hitInfo.stanceSkillID);
            }
        }
        outPacket.encodeInt(hitInfo.hpDamage);
        if (hitInfo.hpDamage == -1) {
            outPacket.encodeInt(hitInfo.userSkillID);
        }

        return outPacket;
    }

    public static OutPacket effect(int id, Effect effect) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_EFFECT);

        outPacket.encodeInt(id);
        effect.encode(outPacket);
        if (effect.hasTrailingInt()) {
            outPacket.encodeInt(-1);
        }

        return outPacket;
    }

    public static OutPacket showUpgradeTombEffect(Char chr, int itemId, Position pos) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_SHOW_UPGRADE_TOMB_EFFECT);

        outPacket.encodeInt(chr.getId());
        outPacket.encodeInt(itemId);
        outPacket.encodePositionInt(pos);

        return outPacket;
    }

    public static OutPacket setDefaultWingItem(Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_SET_DEFAULT_WING_ITEM);

        outPacket.encodeInt(chr.getId());
        outPacket.encodeInt(chr.getAvatarData().getCharacterStat().getWingItem());

        return outPacket;
    }

    public static OutPacket setTemporaryStat(Char chr, short delay) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_SET_TEMPORARY_STAT);

        outPacket.encodeInt(chr.getId());

        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        synchronized (tsm.newStats) {
            V271RemoteStats.encode(outPacket, tsm, tsm.getNewStats());
        }

        outPacket.encodeShort(delay);
        outPacket.encodeByte(tsm.hasStat(CharacterTemporaryStat.Larkness)
                || tsm.hasStat(CharacterTemporaryStat.MeltDown)
                || tsm.hasStat(CharacterTemporaryStat.BigHammerOfTheRighteous)
                || tsm.hasStat(CharacterTemporaryStat.IceAura));

        return outPacket;
    }

    public static OutPacket resetTemporaryStat(Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_RESET_TEMPORARY_STAT);

        TemporaryStatManager tsm = chr.getTemporaryStatManager();

        outPacket.encodeInt(chr.getId());
        // v271 (0x1456a8290): the mask; the client reads more only for stats V271RemoteStats never sends
        V271RemoteStats.encodeReset(outPacket, tsm);

        return outPacket;
    }

    public static OutPacket remoteSetActivePortableChair(int chrId, PortableChair chair) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_SET_ACTIVE_PORTABLE_CHAIR);

        outPacket.encodeInt(chrId);

        // v265: item id, time offset (the client adds it to the current time), has-data byte
        if (chair == null) {
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeByte(false);
        } else {
            outPacket.encodeInt(chair.getItemID());
            outPacket.encodeInt(0);
            outPacket.encodeByte(true);
            chair.encode(outPacket);
        }

        return outPacket;
    }

    public static OutPacket skillPrepare(Char chr, KeyDownSkillInfo si) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_SKILL_PREPARE);

        outPacket.encodeInt(chr.getId());

        outPacket.encodeInt(si.getSkillId());
        outPacket.encodeByte(si.getSlv());

        byte left = (byte) (si.isLeft() ? 1 : 0);
        outPacket.encodeShort((left << 15) | si.getAttackAction());

        if (si.getAttackAction() < GameConstants.MAX_ATTACK_ACTION) {
            outPacket.encodeByte(si.getAttackSpeed());
            if (SkillConstants.isSentientArrowOrTornadoFlight(si.getSkillId())) {
                outPacket.encodePosition(si.getPosition());
            }
        }

        return outPacket;
    }

    public static OutPacket skillCancel(int chrId, int skillId) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_SKILL_CANCEL);

        outPacket.encodeInt(chrId);
        outPacket.encodeInt(skillId);
        outPacket.encodeInt(skillId); // v265

        return outPacket;
    }

    public static OutPacket shootObject(ShootObjectSkillInfo shootObjectSkillInfo) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_SHOOT_OBJECT);

        outPacket.encodeInt(shootObjectSkillInfo.getChr().getId());
        outPacket.encodeInt(shootObjectSkillInfo.getSkillId());
        outPacket.encodeInt(shootObjectSkillInfo.getSlv());
        outPacket.encodeInt(shootObjectSkillInfo.getAction());
        outPacket.encodeInt(shootObjectSkillInfo.getActionSpeed());
        outPacket.encodeByte(shootObjectSkillInfo.getUnknownByte());
        outPacket.encodeInt(shootObjectSkillInfo.getProjectileItemId());

        outPacket.encodeByte(shootObjectSkillInfo.isEncodeExtra());
        if (shootObjectSkillInfo.isEncodeExtra()) {
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
        }
        int skillId = shootObjectSkillInfo.getSkillId();
        if (skillId == Khali.HEX_CHAKRAM_SPLIT || skillId == Khali.HEXA_HEX_CHAKRAM_SPLIT) { // Khali
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeByte(0);
        }

        outPacket.encodeInt(shootObjectSkillInfo.getShootObjects().size());
        for (ShootObject shootObject : shootObjectSkillInfo.getShootObjects()) {
            shootObject.encodeShootObjectRemote(outPacket);
        }

        return outPacket;
    }

    public static OutPacket guildMarkChanged(Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_GUILD_MARK_CHANGED);

        outPacket.encodeInt(chr.getId());

        // v265: int world of the emblem cache entry (-1: any), mark, int custom-mark version; if not 0: int guild,
        // byte type, int length + bytes (the client caches it by guild and checks the version on user enter)
        outPacket.encodeInt(-1);
        encodeRemoteMark(outPacket, chr.getGuild());

        return outPacket;
    }

    /**
     * v271 REMOTE_GUILD_UPDATE (0x1456a6b10): world of the emblem cache entry (-1: any), guild id, name, the mark as
     * in REMOTE_GUILD_MARK_CHANGED, then the guild-data ints of 0x14086c200 (4 4 4 1). Join / leave / kick in one packet,
     * and the only remote packet that also sets the user's guild id.
     */
    public static OutPacket guildUpdated(Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_GUILD_UPDATE);

        outPacket.encodeInt(chr.getId());
        Guild guild = chr.getGuild();
        outPacket.encodeInt(-1);
        outPacket.encodeInt(guild == null ? 0 : guild.getId());
        outPacket.encodeString(guild == null ? "" : guild.getName());
        encodeRemoteMark(outPacket, guild);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeByte(0);

        return outPacket;
    }

    private static void encodeRemoteMark(OutPacket outPacket, Guild guild) {
        if (guild == null) {
            outPacket.encodeShort(0);
            outPacket.encodeByte(0);
            outPacket.encodeShort(0);
            outPacket.encodeByte(0);
            outPacket.encodeInt(0);
        } else {
            outPacket.encodeShort(guild.getMarkBg());
            outPacket.encodeByte(guild.getMarkBgColor());
            outPacket.encodeShort(guild.getMark());
            outPacket.encodeByte(guild.getMarkColor());
            int version = guild.getCustomMarkVersion();
            outPacket.encodeInt(version);
            if (version != 0) {
                var customMark = guild.getCustomMark();
                outPacket.encodeInt(guild.getId());
                outPacket.encodeByte(guild.getCustomMarkType());
                outPacket.encodeInt(customMark.length);
                outPacket.encodeArr(customMark);
            }
        }
    }

    public static OutPacket guildNameChanged(Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_GUILD_NAME_CHANGED);

        outPacket.encodeInt(chr.getId());

        Guild guild = chr.getGuild();
        if (guild == null) {
            outPacket.encodeString("");
        } else {
            outPacket.encodeString(guild.getName());
        }

        return outPacket;
    }

    public static OutPacket kinesisPsychicEnergyShieldEffect(Char chr, boolean active) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_KINESIS_PSYCHIC_ENERGY_SHIELD_EFFECT);

        outPacket.encodeInt(chr.getId());
        outPacket.encodeByte(active);

        return outPacket;
    }

    public static OutPacket laserInfoForRemote(Char chr, int direction, int slv, boolean flag) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_LASER_INFO_FOR_REMOTE);

        outPacket.encodeInt(chr.getId());
        outPacket.encodeInt(direction);
        outPacket.encodeInt(slv);
        outPacket.encodeByte(flag); // relayed from the caster's request

        return outPacket;
    }

    public static OutPacket keyDownAreaMovingPath(Char chr, List<Byte> movePaths) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_KEY_DOWN_AREA_MOVE_PATH);

        outPacket.encodeInt(chr.getId());
        outPacket.encodeByte(movePaths.size());
        for (Byte path : movePaths) {
            outPacket.encodeByte(path);
        }

        return outPacket;
    }

    public static OutPacket dragonAction(Char chr, int action, int skillID, int slv) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_DRAGON_ACTION);

        outPacket.encodeInt(chr.getId());

        outPacket.encodeInt(action);
        outPacket.encodeInt(skillID);
        outPacket.encodeInt(slv);

        return outPacket;
    }

    public static OutPacket dragonBreathEarthEffect(Char chr, int skillId, int slv, Rect rect) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_DRAGON_BREATH_EARTH_EFFECT);

        outPacket.encodeInt(chr.getId());

        outPacket.encodeInt(skillId);
        outPacket.encodeInt(slv);
        outPacket.encodeRectInt(rect);

        return outPacket;
    }

    public static OutPacket zeroTag(Char chr, boolean isBeta) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_ZERO_TAG);
        AvatarLook al = chr.getAvatarData().getAvatarLook(isBeta);

        outPacket.encodeInt(chr.getId());

        al.encode(outPacket);

        return outPacket;
    }

    public static OutPacket zeroLastAssistState(Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_ZERO_LAST_ASSIST_STATE);

        outPacket.encodeInt(chr.getId());

        return outPacket;
    }

    public static OutPacket releaseRWGrab(Char chr, int skillId, int mobObjId) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_RELEASE_RW_GRAB);

        outPacket.encodeInt(chr.getId());

        outPacket.encodeInt(skillId);
        outPacket.encodeInt(mobObjId);

        return outPacket;
    }

    /** Shows another player's Stigma cast: type 3 starts it, 4 ends it; mode 1 = hand to targetId, 2 = pillar. */
    public static OutPacket stigmaDeliveryResponse(Char chr, int type, int targetId, int mode) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_STIGMA_DELIVERY_RESPONSE);

        outPacket.encodeInt(chr.getId());

        outPacket.encodeInt(type);
        outPacket.encodeInt(targetId);
        outPacket.encodeInt(mode);

        return outPacket;
    }

    public static OutPacket rwMultiChargeCancelRequest(Char chr, List<Integer> chargeSkills) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_RW_MULTI_CHARGE_CANCEL_REQUEST);

        outPacket.encodeInt(chr.getId());

        outPacket.encodeByte(chargeSkills.size());
        chargeSkills.forEach(outPacket::encodeInt);

        return outPacket;
    }

    public static OutPacket kaiserColorOrMorphChange(Char chr, int extern, int inner, boolean premium) {
        OutPacket outPacket = new OutPacket(OutHeader.REMOTE_KAISER_COLOR_OR_MORPH_CHANGE);

        outPacket.encodeInt(chr.getId());

        outPacket.encodeInt(extern);
        outPacket.encodeInt(inner);
        outPacket.encodeByte(premium);

        return outPacket;
    }
}
