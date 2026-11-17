package net.swordie.ms.connection.packet;

import net.swordie.ms.client.jobs.legend.Luminous;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.CharEncoder;
import net.swordie.ms.client.character.CharacterStat;
import net.swordie.ms.client.character.avatar.AvatarLook;
import net.swordie.ms.client.character.chair.PortableChair;
import net.swordie.ms.client.character.modules.CustomTitleModule;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.character.skills.temp.V271RemoteStats;
import net.swordie.ms.client.guild.Guild;
import net.swordie.ms.client.social.miniroom.gameroom.MiniGameRoom;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.enums.TSIndex;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.Familiar;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.skill.MobSkillStat;
import net.swordie.ms.life.pet.Pet;
import net.swordie.ms.loaders.containerclasses.MobSkillInfo;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Foothold;

import java.util.List;
import java.util.Map;

/**
 * Created on 3/18/2018.
 */
public class UserPool {
    private static final int DRESS_UP_SUIT = 1051291;
    private static final int NAME_TAG_SHOWN = 1;
    private static final int TITLE_MAP_KEY = 0;
    private static final String TITLE_MAP_VALUE = "0";

    public static OutPacket userEnterField(Char chr) {
        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        AvatarLook al = chr.getAvatarData().getAvatarLook();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();

        OutPacket outPacket = new OutPacket(OutHeader.USER_ENTER_FIELD);

        outPacket.encodeInt(chr.getUserId());
        outPacket.encodeInt(chr.getId()); // a 0 here makes the client read the char id once more
        outPacket.encodeByte(0);
        outPacket.encodeInt(chr.getGuildId());

        // CUser::Init
        outPacket.encodeInt(chr.getLevel());
        outPacket.encodeString(chr.getName());
        outPacket.encodeString(""); // parent name, deprecated
        if (chr.getGuild() != null) {
            chr.getGuild().encodeForRemote(outPacket);
        } else {
            Guild.defaultEncodeForRemote(outPacket);
        }
        Guild.encodeNameTag(outPacket);
        outPacket.encodeByte(NAME_TAG_SHOWN);
        outPacket.encodeByte(cs.getGender());
        outPacket.encodeInt(cs.getPop());
        outPacket.encodeInt(0); // nNameTagMark
        outPacket.encodeByte(0);
        outPacket.encodeInt(0);

        synchronized (tsm.currentStats) {
            V271RemoteStats.encode(outPacket, tsm, tsm.getCurrentStats());
        }

        outPacket.encodeShort(chr.getJob());
        outPacket.encodeShort(cs.getSubJob());
        outPacket.encodeInt(chr.getTotalChuc());
        outPacket.encodeInt(chr.getTotalAf());
        outPacket.encodeInt(chr.getTotalStat(BaseStat.authForce));

        if (JobConstants.isZero(chr.getJob())) {
            if (chr.isZeroBeta()) { // the shown look first
                chr.getAvatarData().getZeroAvatarLook().encode(outPacket);
                chr.getAvatarData().getAvatarLook().encode(outPacket);
            } else {
                chr.getAvatarData().getAvatarLook().encode(outPacket);
                chr.getAvatarData().getZeroAvatarLook().encode(outPacket);
            }
        } else {
            al.encode(outPacket);
        }

        outPacket.encodeInt(chr.getDriverID());
        outPacket.encodeInt(chr.getPassengerID()); // dwPassenserID
        outPacket.encodeByte(1);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0); // list of (int, int)
        outPacket.encodeInt(chr.getChocoCount());
        outPacket.encodeInt(chr.getActiveEffectItemID()); // nx effect
        outPacket.encodeInt(chr.getMonkeyEffectItemID());
        outPacket.encodeInt(0); // v271: new
        outPacket.encodeInt(0); // v271: new
        outPacket.encodeInt(chr.getNickItem()); // title
        int medalId = chr.getMedalItemId();
        outPacket.encodeInt(medalId);
        outPacket.encodeInt(0); // second title slot
        outPacket.encodeByte(false); // [str]
        outPacket.encodeByte(false); // [str]
        boolean titled = medalId != 0 || chr.getNickItem() != 0;
        outPacket.encodeInt(titled ? 1 : 0); // map of (int, str) (0x1456c7d70)
        if (titled) {
            outPacket.encodeInt(TITLE_MAP_KEY);
            outPacket.encodeString(TITLE_MAP_VALUE);
        }
        if (chr.getNickItem() == ItemConstants.CUSTOM_TITLE_ITEM_ID || chr.getNickItem() == 3700870) { // hardcode-ok
            // CUser::DecodeCustomTitle
            CustomTitleModule.encodeCustomTitle(chr, outPacket);
        }

        outPacket.encodeInt(CharEncoder.activeSkinId(chr.getActiveDamageSkin()));
        outPacket.encodeString(""); // damage skin text
        outPacket.encodeString(""); // premium damage skin text

        outPacket.encodeInt(al.getDemonWingID());
        outPacket.encodeInt(al.getKaiserWingID());
        outPacket.encodeInt(al.getKaiserTailID());
        outPacket.encodeByte(JobConstants.isHoYoung(chr.getJob()));
        outPacket.encodeByte(0);
        outPacket.encodeByte(0); // v271: new
        outPacket.encodeInt(chr.getCompletedSetItemID());
        outPacket.encodeShort(chr.getFieldSeatID());

        PortableChair chair = chr.getChair();
        outPacket.encodeInt(chair != null ? chair.getItemID() : 0);
        outPacket.encodeInt(0);

        Position pos = chr.getPosition();
        int fh = chr.getFoothold();
        Foothold ground = fh == 0 && chr.getMoveAction() == 0 ? groundBelow(chr) : null;
        if (ground != null) {
            // placed by a spawn or warp and not moved yet: a remote user with no foothold hangs in the air
            pos = new Position(pos.getX(), ground.getYFromX(pos.getX()));
            fh = ground.getId();
        }
        outPacket.encodePosition(pos);
        outPacket.encodeByte(chr.getMoveAction());
        outPacket.encodeShort(fh);
        outPacket.encodeByte(0);
        outPacket.encodeByte(0);
        outPacket.encodeByte(0);

        outPacket.encodeByte(chair != null);
        if (chair != null) {
            chair.encode(outPacket);
        }
        outPacket.encodeByte(0); // v271: count of per-slot item effect records (0x1452adbf0)

        for (Pet pet : chr.getPets()) {
            outPacket.encodeByte(1);
            outPacket.encodeInt(pet.getIdx());
            pet.encode(outPacket);
        }
        outPacket.encodeByte(0); // end of pets
        outPacket.encodeByte(0); // v271: no object (0x1417ae8d0)

        outPacket.encodeByte(false);

        List<Familiar> famList = chr.getFamiliarCodexManager().getActiveFamiliarsToSummon();
        int size = famList.size();
        if (!chr.getFamiliarCodexManager().isFamiliarsSummoned()) {
            size = 0;
        }
        outPacket.encodeByte(size);
        for (int i = 0; i < size; i++) {
            Familiar f = famList.get(i);
            outPacket.encodeByte(true); // show
            f.encodeForRemote(outPacket);
        }

        outPacket.encodeInt(chr.getTamingMobLevel());
        outPacket.encodeInt(chr.getTamingMobExp());
        outPacket.encodeInt(chr.getTamingMobFatigue());
        outPacket.encodeByte(false);

        var miniRoom = chr.getMiniRoom();
        if (!(miniRoom instanceof MiniGameRoom) || miniRoom.getRoomOwner() != chr) {
            outPacket.encodeByte(0);
        } else {
            ((MiniGameRoom) miniRoom).encodeBalloon(outPacket);
        }

        outPacket.encodeByte(chr.getADBoardRemoteMsg() != null);
        if (chr.getADBoardRemoteMsg() != null) {
            outPacket.encodeString(chr.getADBoardRemoteMsg());
        }
        outPacket.encodeByte(chr.isInCouple());
        if (chr.isInCouple()) {
            chr.getCouple().encodeForRemote(outPacket);
        }
        outPacket.encodeByte(chr.hasFriendshipItem());
        if (chr.hasFriendshipItem()) {
            chr.getFriendshipRingRecord().encode(outPacket);
        }
        outPacket.encodeByte(chr.isMarried());
        if (chr.isMarried()) {
            chr.getMarriageRecord().encodeForRemote(outPacket);
        }

        // CSecondAtom
        outPacket.encodeByte(true);
        outPacket.encodeInt(0); // size

        outPacket.encodeByte(0); // flags 0x8 / 0x10 / 0x20 add an int each

        outPacket.encodeInt(chr.getEvanDragonGlide());
        if (JobConstants.isKaiser(chr.getJob())) {
            outPacket.encodeInt(chr.getKaiserColourExtern());
            outPacket.encodeInt(chr.getKaiserColourInner());
            outPacket.encodeByte(chr.getKaiserColourPremium());
        }
        outPacket.encodeInt(chr.getMakingMeisterSkillEff());
        for (int i = 0; i < 5; i++) {
            outPacket.encodeByte(-1); // activeEventNameTag
        }
        outPacket.encodeInt(chr.getCustomizeEffect());
        if (chr.getCustomizeEffect() > 0) {
            outPacket.encodeString(chr.getCustomizeEffectMsg());
        }
        outPacket.encodeByte(chr.getSoulEffect());

        if (tsm.hasStat(CharacterTemporaryStat.RideVehicle)) {
            int vehicleID = tsm.getTSBByTSIndex(TSIndex.RideVehicle).getNOption();
            if (vehicleID == 1932249) { // is_mix_vehicle; hardcode-ok
                outPacket.encodeInt(0); // size
            }
        }
        outPacket.encodeInt(-1);
        outPacket.encodeByte(false); // flash fire
        outPacket.encodeByte(true);
        outPacket.encodeByte(false);

        outPacket.encodeInt(0); // CUser::DecodeTextEquipInfo
        chr.getFreezeHotEventInfo().encode(outPacket);
        outPacket.encodeInt(chr.getEventBestFriendAID()); // CUser::DecodeEventBestFriendInfo

        // v271: the psychic shield byte is gone; one int more after the dress-up clothes
        // Luminous's appearance switches (Eclipse, then Equilibrium), as SKILL_ON_OFF_EFFECT's on byte
        outPacket.encodeByte(isActionToggleOn(chr, Luminous.ECLIPSE));
        outPacket.encodeByte(isActionToggleOn(chr, Luminous.EQUILIBRIUM));
        outPacket.encodeInt(DRESS_UP_SUIT); // Angelic Buster dress-up clothes
        outPacket.encodeInt(0);
        outPacket.encodeByte(false); // event name tag: int, int

        // actor object stream (0x146674130): Lynn's controller or Kanna's actors, then the closing object id 0
        net.swordie.ms.client.jobs.jianghu.Lynn.encodeControllerFor(chr, outPacket);
        net.swordie.ms.client.jobs.sengoku.Kanna.encodeActorsFor(chr, outPacket);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0); // ImmovableObj id: 9 ints when set
        outPacket.encodeInt(0); // list of (int skill, byte)
        // v271: the int, int, str, int before this and the int after it are gone
        outPacket.encodeInt(0); // id: 3 ints, 2 shorts when set
        outPacket.encodeLong(0);
        outPacket.encodeInt(-1);

        return outPacket;
    }

    private static boolean isActionToggleOn(Char chr, int skillId) {
        var quest = chr.getQuestManager().getQuestById(net.swordie.ms.constants.QuestConstants.SKILL_COMMAND_LOCK_QUEST_ID_2);
        if (quest == null) {
            return false;
        }
        quest.convertQRValueToProperties();
        return quest.hasProperty(String.valueOf(skillId));
    }

    private static Foothold groundBelow(Char chr) {
        Field field = chr.getField();
        if (field == null || field.getInfo() == null || chr.getPosition() == null) {
            return null;
        }
        Foothold fh = field.findFootHoldBelow(chr.getPosition());
        return fh == null || fh.getX1() == fh.getX2() ? null : fh;
    }

    public static OutPacket userLeaveField(Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.USER_LEAVE_FIELD);

        outPacket.encodeInt(chr.getId());

        return outPacket;
    }

    public static OutPacket releasePsychicLock(Char chr, int id) {
        OutPacket outPacket = new OutPacket(OutHeader.RELEASE_PSYCHIC_LOCK);

        outPacket.encodeInt(chr.getId());
        outPacket.encodeInt(id);

        return outPacket;
    }

    public static OutPacket releasePsychicArea(Char chr, int localAreaKey) {
        OutPacket outPacket = new OutPacket(OutHeader.RELEASE_PSYCHIC_AREA);

        outPacket.encodeInt(chr.getId());
        outPacket.encodeInt(localAreaKey);
        outPacket.encodeInt(localAreaKey);

        return outPacket;
    }

    public static OutPacket releasePsychicLockMob(Char chr, List<Integer> ids) {
        OutPacket outPacket = new OutPacket(OutHeader.RELEASE_PSYCHIC_LOCK_MOB);

        outPacket.encodeInt(chr.getId());
        for (int i : ids) {
            outPacket.encodeByte(1);
            outPacket.encodeInt(i);
        }
        outPacket.encodeByte(0);

        return outPacket;
    }

    public static OutPacket recreatePathPsychicLock(Char chr, int skillID, int slv, int action, int actionSpeed, boolean left,
                                                    Map<Integer, Integer> skillIDMap, Map<Integer, Integer> lockMap) {
        OutPacket outPacket = new OutPacket(OutHeader.RECREATE_PATH_PSYCHIC_LOCK);

        outPacket.encodeInt(chr.getId());
        outPacket.encodeInt(skillID);
        outPacket.encodeShort(slv);
        outPacket.encodeInt(action);
        outPacket.encodeInt(actionSpeed);
        outPacket.encodeByte(left);

        outPacket.encodeInt(skillIDMap.size());
        for (Map.Entry<Integer, Integer> entry : skillIDMap.entrySet()) {
            outPacket.encodeInt(entry.getKey());
            outPacket.encodeInt(entry.getValue());
        }
        outPacket.encodeInt(lockMap.size());
        for (Map.Entry<Integer, Integer> entry : lockMap.entrySet()) {
            outPacket.encodeInt(entry.getKey());
        }

        return outPacket;
    }

    public static OutPacket teslaTriangle(int chrId, List<Summon> rockNshockList) {
        OutPacket outPacket = new OutPacket(OutHeader.TESLA_TRIANGLE);

        outPacket.encodeInt(chrId);
        for (Summon rockNshockSummon : rockNshockList) {
            outPacket.encodeInt(rockNshockSummon.getObjectId());
        }

        return outPacket;
    }

    public static OutPacket skillOnOffEffect(int chrId, boolean show) {
        OutPacket outPacket = new OutPacket(OutHeader.SKILL_ON_OFF_EFFECT);

        outPacket.encodeInt(chrId);
        outPacket.encodeByte(show);
        outPacket.encodeInt(0); // v265: skill id

        return outPacket;
    }

    /** The v265 layout (chr, byte, skill), as the client reads it: a skill's on/off effect, e.g. a switched card */
    public static OutPacket skillOnOffEffectOf(int chrId, int skillId) {
        return skillOnOffEffectOf(chrId, skillId, false);
    }

    /** on = the skill's right-click toggle state; the local client keeps it for skills in its toggle lists. */
    public static OutPacket skillOnOffEffectOf(int chrId, int skillId, boolean on) {
        OutPacket outPacket = new OutPacket(OutHeader.SKILL_ON_OFF_EFFECT);

        outPacket.encodeInt(chrId);
        outPacket.encodeByte(on);
        outPacket.encodeInt(skillId);

        return outPacket;
    }

    public static OutPacket skillOnOffEffect2(int chrId, int skillId, boolean show) {
        OutPacket outPacket = new OutPacket(OutHeader.SKILL_ON_OFF_EFFECT_2);

        outPacket.encodeInt(chrId);
        outPacket.encodeByte(show);
        outPacket.encodeInt(skillId);

        return outPacket;
    }

    public static OutPacket addMesoChairCount(int chrId, long meso) {
        OutPacket outPacket = new OutPacket(OutHeader.ADD_MESO_CHAIR_COUNT);

        outPacket.encodeInt(chrId);
        outPacket.encodeInt(0); // unk
        outPacket.encodeLong(meso);
        outPacket.encodeLong(meso);

        return outPacket;
    }

    public static OutPacket tossedByMobSkill(int chrId, Mob mob, MobSkillInfo msi) {
        OutPacket outPacket = new OutPacket(OutHeader.TOSSED_BY_MOB_SKILL);

        outPacket.encodeInt(chrId);
        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeInt(msi.getSkillId());
        outPacket.encodeInt(msi.getLevel());
        outPacket.encodeInt(msi.getSkillStatIntValue(MobSkillStat.x));
        outPacket.encodeInt(msi.getSkillStatIntValue(MobSkillStat.x));

        return outPacket;
    }

    public static OutPacket rwZeroBunkerMobBind(int chrId, boolean success, boolean isAlive, Mob mob) {
        OutPacket outPacket = new OutPacket(OutHeader.RW_ZERO_BUNKER_MOB_BIND);

        outPacket.encodeInt(chrId); // v265
        outPacket.encodeByte(success);
        outPacket.encodeByte(isAlive);
        outPacket.encodeInt(mob.getObjectId());
        outPacket.encodeInt(mob.getTemplateId());
        outPacket.encodePositionInt(mob.getPosition());

        return outPacket;
    }
}
