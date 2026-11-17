package net.swordie.ms.connection.packet;

import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.client.LinkSkill;
import net.swordie.ms.client.alliance.AllianceResult;
import net.swordie.ms.client.blacklist.BlackListEntry;
import net.swordie.ms.client.blacklist.BlackListResult;
import net.swordie.ms.client.blacklist.BlackListTabType;
import net.swordie.ms.client.character.*;
import net.swordie.ms.client.character.commerce.vessel.Vessel;
import net.swordie.ms.client.character.commerce.voyage.VoyageInfoType;
import net.swordie.ms.client.character.emoticons.EmoticonType;
import net.swordie.ms.client.character.hyperstats.HyperStatsManager;
import net.swordie.ms.client.character.info.ZeroInfo;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.items.MemorialCubeInfo;
import net.swordie.ms.client.character.items.PowerCrystal;
import net.swordie.ms.client.character.modules.CustomTitleModule;
import net.swordie.ms.client.character.modules.LinkSkillsModule;
import net.swordie.ms.client.character.potential.CharacterPotential;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.TownPortal;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.character.skills.temp.V271LocalStats;
import net.swordie.ms.client.character.skills.vmatrix.MatrixRecord;
import net.swordie.ms.client.character.skills.vmatrix.MatrixSlot;
import net.swordie.ms.client.character.union.UnionBoard;
import net.swordie.ms.client.friend.result.FriendResult;
import net.swordie.ms.client.guild.Guild;
import net.swordie.ms.client.guild.bbs.GuildBBSPacket;
import net.swordie.ms.client.guild.result.GuildResult;
import net.swordie.ms.client.jobs.resistance.WildHunterInfo;
import net.swordie.ms.client.party.Party;
import net.swordie.ms.client.party.PartyMember;
import net.swordie.ms.client.party.PartyResult;
import net.swordie.ms.client.rankings.RankingResult;
import net.swordie.ms.client.soulcollection.SoulCollectionEntry;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.enums.*;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.RandomPortal;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.EventListData;
import net.swordie.ms.util.AntiMacro;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

import static net.swordie.ms.enums.InvType.EQUIPPED;

/**
 * Created on 12/22/2017.
 */
public class WvsContext {
    private static final int FLAME_CROWN = 1005458;

    private static final Logger log = LogManager.getRootLogger();

    public static OutPacket exclRequest() {
        return new OutPacket(OutHeader.EXCL_REQUEST);
    }

    public static OutPacket exclRequest2() {
        return new OutPacket(OutHeader.EXCL_REQUEST_2);
    }

    public static OutPacket setMaplePoint(int maplePoint) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_MAPLE_POINT);

        outPacket.encodeInt(maplePoint);

        return outPacket;
    }

    /**
     * Where a box was opened from. The reveal window keeps it, and its own Use 1 / Use All buttons send the next
     * request for that place (the field's cash item use, or the Cash Shop's request).
     */
    public enum GachaponWindowMode {
        FIELD(false, false), CASH_SHOP(true, false), CASH_INVENTORY(true, true);

        private final boolean inCashShop;
        private final boolean fromCashInventory;

        GachaponWindowMode(boolean inCashShop, boolean fromCashInventory) {
            this.inCashShop = inCashShop;
            this.fromCashInventory = fromCashInventory;
        }

        public void encode(OutPacket outPacket) {
            outPacket.encodeByte(inCashShop);
            outPacket.encodeByte(fromCashInventory);
        }
    }

    /**
     * What came out of boxes opened with Use 1 / Use All (Surprise Style Box and the like): the client plays the box's
     * UIGachapon window, lists the items and releases the exclusive request.
     */
    public static OutPacket cashGachaponResult(GachaponWindowMode mode, int boxItemId, List<Item> items) {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_GACHAPON_RESULT);
        mode.encode(outPacket);
        outPacket.encodeInt(boxItemId);
        outPacket.encodeInt(items.size());
        for (Item item : items) {
            outPacket.encode(item);
            outPacket.encodeInt(0);
        }
        return outPacket;
    }

    public static OutPacket statChanged(Map<Stat, Object> stats) {
        return statChanged(stats, 0);
    }

    public static OutPacket statChanged(Map<Stat, Object> stats, boolean exclRequest) {
        return statChanged(stats, exclRequest, (byte) -1, (byte) 0, (byte) 0, (byte) 0, false, 0, 0, (short) 0);
    }

    public static OutPacket statChanged(Map<Stat, Object> stats, int subJob) {
        return statChanged(stats, false, (byte) -1, (byte) 0, (byte) 0, (byte) 0, false, 0, 0, (short) subJob);
    }

    public static OutPacket statChanged(Map<Stat, Object> stats, boolean exclRequestSent, byte mixBaseHairColor,
                                        byte mixAddHairColor, byte mixHairBaseProb, byte charmOld, boolean updateRecovery,
                                        int hpRecovery, int mpRecovery, short subJob) {
        OutPacket outPacket = new OutPacket(OutHeader.STAT_CHANGED);

        outPacket.encodeByte(exclRequestSent);
        outPacket.encodeByte(false); // ?
        outPacket.encodeByte(1);
        // GW_CharacterStat::DecodeChangeStat
        int mask = 0;
        for (Stat stat : stats.keySet()) {
            mask |= stat.getVal();
        }
        outPacket.encodeLong(mask);
        Comparator statComper = Comparator.comparingLong(o -> ((Stat) o).getVal());
        TreeMap<Stat, Object> sortedStats = new TreeMap<>(statComper);
        sortedStats.putAll(stats);
        for (Map.Entry<Stat, Object> entry : sortedStats.entrySet()) {
            Stat stat = entry.getKey();
            Object value = entry.getValue();
            switch (stat) {
                case skin:
                    outPacket.encodeByte((Byte) value);
                    outPacket.encodeInt(0);
                    break;
                case face:
                case hair:
                case hp:
                case mhp:
                case mp:
                case mmp:
                case pop:
                case charismaEXP:
                case insightEXP:
                case willEXP:
                case craftEXP:
                case senseEXP:
                case charmEXP:
                case eventPoints:
                case level:
                    outPacket.encodeInt((Integer) value);
                    break;
                case str:
                case dex:
                case inte:
                case luk:
                case ap:
                case fatigue:
                    outPacket.encodeShort((Short) value);
                    break;
                case sp:
                    if (value instanceof ExtendSP) {
                        ((ExtendSP) value).encode(outPacket);
                    } else {
                        outPacket.encodeShort((Short) value);
                    }
                    break;
                case exp:
                    outPacket.encodeLong((Long) value);
                    break;
                case money:
                    break; // v271: after all the other stats (below)
                case dayLimit:
                    ((NonCombatStatDayLimit) value).encode(outPacket);
                    break;
                case characterCard:
                    break;
                case pvp2:
                    outPacket.encodeByte(2);
                    outPacket.encodeByte(2);
                    break;
                case job:
                    outPacket.encodeShort((Short) value);
                    outPacket.encodeShort(subJob);
                    break;
            }
        }

        // v271: the client reads money (bit 18) after the core stat decoder 0x140783230, in its wrapper 0x145a6ded0, so
        // it comes after every other selected stat (v265 read it in mask order)
        if (sortedStats.containsKey(Stat.money)) {
            outPacket.encodeLong((Long) sortedStats.get(Stat.money));
        }

        outPacket.encodeByte(charmOld > 0);
        if (charmOld > 0) {
            outPacket.encodeByte(charmOld);
        }

        outPacket.encodeByte(updateRecovery);
        if (updateRecovery) {
            outPacket.encodeInt(hpRecovery);
            outPacket.encodeInt(mpRecovery);
        }
        return outPacket;
    }

    public static OutPacket inventoryOperation(boolean exclRequest, boolean notRemoveAddInfo, InventoryOperation type, short oldPos, short newPos,
                                               int bagPos, Item item) {
        // ignoreExcl if a pet picks up an item
        List<ItemOperation> operations = new ArrayList<>();

        ItemOperation io = new ItemOperation();
        io.item = item;
        io.pos = oldPos;
        io.newPos = newPos;
        io.type = type;
        operations.add(io);

        return inventoryOperation(exclRequest, notRemoveAddInfo, operations);
    }

    public static OutPacket inventoryOperation(boolean exclResult, boolean notAddRemoveInfo, List<ItemOperation> itemOperations) {
        OutPacket outPacket = new OutPacket(OutHeader.INVENTORY_OPERATION);

        outPacket.encodeByte(exclResult);
        outPacket.encodeByte(0);
        outPacket.encodeInt(itemOperations.size());
        outPacket.encodeByte(notAddRemoveInfo);

        byte addMovementInfo = 0;
        for (ItemOperation itemOperation : itemOperations) {
            Item item = itemOperation.item;
            // logic like this in packets :(
            InvType invType = item.getInvType();
            if ((itemOperation.pos > 0 && itemOperation.newPos < 0 && invType == EQUIPPED) || (invType == EQUIPPED && itemOperation.pos < 0)) {
                invType = item.isCash() ? InvType.DEC : InvType.EQUIP;
            }
            boolean isEquippedItem = (invType == EQUIPPED ||
                    (invType.isEquipType() && (itemOperation.pos < 0 || itemOperation.newPos < 0)));

            outPacket.encodeByte(itemOperation.type.getVal());
            outPacket.encodeByte(invType.getVal());
            outPacket.encodeShort(itemOperation.pos);

            switch (itemOperation.type) {
                case Add:
                    outPacket.encode(item);
                    break;
                case UpdateQuantity:
                    outPacket.encodeInt(item.getQuantity()); // v271: 4 bytes
                    break;
                case Move:
                    outPacket.encodeShort(itemOperation.newPos);
                    if (isEquippedItem) {
                        addMovementInfo = 1;
                    }
                    break;
                case Remove:
                    if (isEquippedItem) {
                        addMovementInfo = 2;
                    }
                    break;
                case ItemExp:
                    outPacket.encodeLong(((Equip) item).getExp());
                    break;
                case Unk232:
                    outPacket.encode(item);
                    break;
                case UpdateBagPos:
                    outPacket.encodeInt(itemOperation.newPos);
                    break;
                case UpdateBagQuantity:
                    outPacket.encodeInt(item.getQuantity()); // v271: 4 bytes
                    break;
                case BagRemove:
                    break;
                case BagToBag:
                    outPacket.encodeShort(itemOperation.newPos);
                    break;
                case BagNewItem:
                    outPacket.encode(item);
                    break;
                case BagRemoveSlot:
                    break;
            }
        }
        if (addMovementInfo != 0) {
            outPacket.encodeByte(addMovementInfo);
        }

        return outPacket;
    }

    public static OutPacket updateEventNameTag(int[] tags) {
        OutPacket outPacket = new OutPacket(OutHeader.EVENT_NAME_TAG);

        for (int i = 0; i < 5; i++) {
            outPacket.encodeString("0000000000"); // owned tags of this colour, one digit each (none)
            if (i >= tags.length) {
                outPacket.encodeByte(-1);
            } else {
                outPacket.encodeByte(tags[i]);
            }
        }

        return outPacket;
    }

    public static OutPacket changeSkillRecordResult(Skill skill) {
        List<Skill> skills = new ArrayList<>();
        skills.add(skill);
        return changeSkillRecordResult(skills, true, false, false, false);
    }

    public static OutPacket changeSkillRecordResult(Collection<Skill> skills) {
        return changeSkillRecordResult(skills, true, false, false, false);
    }

    public static OutPacket changeSkillRecordResult(Collection<Skill> skills, boolean exclRequestSent, boolean showResult,
                                                    boolean removeLinkSkill, boolean sn) {
        OutPacket outPacket = new OutPacket(OutHeader.CHANGE_SKILL_RECORD_RESULT);

        outPacket.encodeByte(exclRequestSent);
        outPacket.encodeByte(showResult);
        // v265 has no remove-link-skill byte here (reference server: byte, byte, short count, ...)
        outPacket.encodeShort(skills.size());
        for (Skill skill : skills) {
            outPacket.encodeInt(skill.getSkillId());
            outPacket.encodeInt(skill.getCurrentLevel());
            outPacket.encodeInt(skill.getMasterLevel());
            outPacket.encodeFT(FileTime.MIN_TIME());
        }
        outPacket.encodeByte(sn);

        return outPacket;
    }

    public static OutPacket temporaryStatSet(TemporaryStatManager tsm) {
        OutPacket outPacket = new OutPacket(OutHeader.TEMPORARY_STAT_SET);

        boolean isTransform = tsm.hasNewStatOf(CharacterTemporaryStat.EtherealForm, CharacterTemporaryStat.MeltDown,
                CharacterTemporaryStat.IndieKeyDownTime, CharacterTemporaryStat.SwordOfLight,
                CharacterTemporaryStat.RideVehicle, CharacterTemporaryStat.SpreadThrow, CharacterTemporaryStat.LW_Restore,
                CharacterTemporaryStat.IceAura, CharacterTemporaryStat.Morph, CharacterTemporaryStat.FireBarrier,
                CharacterTemporaryStat.BlessEnsenble, CharacterTemporaryStat.LW_Nobility);
        // v271 layout (V271LocalStats); the moving byte follows the client's moving-stat mask. Encoding flushes new stats.
        var sent = V271LocalStats.encodeSet(outPacket, tsm, tsm.getNewStats());
        tsm.getNewStats().clear();
        boolean hasMovingAffectingStat = V271LocalStats.hasMoving(sent);

        outPacket.encodeShort(0); // delay
        outPacket.encodeByte(0);
        outPacket.encodeByte(false); // hide buff
        outPacket.encodeByte(isTransform);
        outPacket.encodeByte(true);
        outPacket.encodeByte(true);
        outPacket.encodeByte(hasMovingAffectingStat);
        if (hasMovingAffectingStat) {
            outPacket.encodeByte(0);
        }
        outPacket.encodeInt(0);
        outPacket.encodeByte(V271LocalStats.getSentCount(tsm));

        return outPacket;
    }

    public static OutPacket temporaryStatReset(TemporaryStatManager tsm, boolean demount) {
        OutPacket outPacket = new OutPacket(OutHeader.TEMPORARY_STAT_RESET);

        // v271: 4 bytes, mask, [Indie section], [moving], [pose], [mount groups' byte] (V271LocalStats)
        boolean moving = V271LocalStats.hasMoving(tsm.getRemovedStats().keySet());
        outPacket.encodeByte(true);
        outPacket.encodeByte(true);
        outPacket.encodeByte(V271LocalStats.getSentCount(tsm));
        outPacket.encodeByte(moving);
        V271LocalStats.encodeReset(outPacket, tsm, tsm.getRemovedStats());

        return outPacket;
    }

    public static OutPacket skillUseResult(boolean stillGoing, int skillId) {
        OutPacket outPacket = new OutPacket(OutHeader.SKILL_USE_RESULT);
        // 2221011 - Frozen Breath
        outPacket.encodeByte(stillGoing);
        outPacket.encodeInt(skillId);

        return outPacket;
    }

    public static OutPacket message(MessagePacket messagePacket) {
        var outPacket = new OutPacket(OutHeader.MESSAGE);

        messagePacket.encode(outPacket);

        return outPacket;
    }

    public static OutPacket flipTheCoinEnabled(byte enabled) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_FLIP_THE_COIN_ENABLED);

        outPacket.encodeByte(enabled);

        return outPacket;
    }

    public static OutPacket modComboResponse(int combo, boolean show) {
        OutPacket outPacket = new OutPacket(OutHeader.MOD_COMBO_RESPONSE);

        // v271 0x14546ad20 reads only the count (GMS: 4 bytes)
        outPacket.encodeInt(combo);

        return outPacket;
    }

    public static OutPacket wildHunterInfo(WildHunterInfo whi) {
        OutPacket outPacket = new OutPacket(OutHeader.WILD_HUNTER_INFO);

        whi.encode(outPacket);

        return outPacket;
    }

    public static OutPacket zeroInfo(ZeroInfo currentInfo, short mask) {
        OutPacket outPacket = new OutPacket(OutHeader.ZERO_INFO);

        currentInfo.encode(mask, outPacket);

        return outPacket;
    }

    public static OutPacket zeroInfo(ZeroInfo currentInfo) {
        OutPacket outPacket = new OutPacket(OutHeader.ZERO_INFO);

        currentInfo.encode(outPacket);

        return outPacket;
    }

    public static OutPacket zeroWp(int wpCoin) {
        OutPacket outPacket = new OutPacket(OutHeader.ZERO_WP);

        outPacket.encodeInt(wpCoin);

        return outPacket;
    }

    public static OutPacket gatherItemResult(byte type) {
        OutPacket outPacket = new OutPacket(OutHeader.GATHER_ITEM_RESULT);

        outPacket.encodeByte(0); // doesn't get used
        outPacket.encodeByte(type);

        return outPacket;
    }

    public static OutPacket sortItemResult(byte type) {
        OutPacket outPacket = new OutPacket(OutHeader.SORT_ITEM_RESULT);

        outPacket.encodeByte(0); // doesn't get used
        outPacket.encodeByte(type);

        return outPacket;
    }

    public static OutPacket clearAnnouncedQuest() {
        return new OutPacket(OutHeader.CLEAR_ANNOUNCED_QUEST);
    }

    public static OutPacket partyResult(PartyResult pri) {
        OutPacket outPacket = new OutPacket(OutHeader.PARTY_RESULT);

        outPacket.encode(pri);

        return outPacket;
    }

    public static OutPacket partyMemberCandidateResult(Set<Char> chars) {
        OutPacket outPacket = new OutPacket(OutHeader.PARTY_MEMBER_CANDIDATE_RESULT);

        outPacket.encodeByte(chars.size());
        for (Char chr : chars) {
            outPacket.encodeInt(chr.getId());
            outPacket.encodeString(chr.getName());
            outPacket.encodeShort(chr.getJob());
            outPacket.encodeShort(chr.getAvatarData().getCharacterStat().getSubJob());
            outPacket.encodeInt(chr.getLevel());
            outPacket.encodeLong(0); // v271: an 8-byte value per entry (GMS: small numbers, meaning unknown)
        }

        return outPacket;
    }

    public static OutPacket partyCandidateResult(Set<Party> parties) {
        OutPacket outPacket = new OutPacket(OutHeader.PARTY_CANDIDATE_RESULT);

        outPacket.encodeByte(parties.size());
        for (Party party : parties) {
            Char leader = party.getPartyLeader().getChr();

            outPacket.encodeInt(party.getId());
            outPacket.encodeString(leader.getName());
            outPacket.encodeInt(party.getAvgLevel());
            outPacket.encodeByte(party.getMembers().size());
            outPacket.encodeString(party.getName());
            outPacket.encodeLong(0); // v271: 8 bytes after the party name
            outPacket.encodeByte(party.getMembers().size());

            for (PartyMember pm : party.getMembers()) {
                outPacket.encodeInt(pm.getCharID());
                outPacket.encodeString(pm.getCharName());
                outPacket.encodeShort(pm.getJob());
                outPacket.encodeShort(pm.getSubSob());
                outPacket.encodeInt(pm.getLevel());
                outPacket.encodeByte(pm.equals(party.getPartyLeader()));
                outPacket.encodeByte(0); // new 200
                outPacket.encodeLong(0); // v271: 8 bytes per member, as in 213
            }
        }

        return outPacket;
    }

    public static OutPacket guildResult(GuildResult gri) {
        OutPacket outPacket = new OutPacket(OutHeader.GUILD_RESULT);

        gri.encode(outPacket);

        return outPacket;
    }

    public static final int GUILD_ATTEND_MAP_INFO_VERSION = 1;

    /**
     * The maps where the automatic guild check-in is refused: single maps, then [lo, hi] ranges, then the list's
     * version. Until the client has a list it shows "checking your current location" and never checks in.
     * Empty lists: every map allows it.
     */
    public static OutPacket guildAttendMapInfo() {
        OutPacket outPacket = new OutPacket(OutHeader.GUILD_ATTEND_MAP_INFO);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(GUILD_ATTEND_MAP_INFO_VERSION);
        return outPacket;
    }

    /**
     * The guild search window: the request's own header echoed back (type, mode, text, filters), then two lists.
     * The client shows the second; on type 4 (the window's recommendations) the first holds up to 5 featured guilds.
     */
    public static OutPacket guildSearchResult(byte[] requestHeader, Collection<Guild> featured, Collection<Guild> guilds) {
        OutPacket outPacket = new OutPacket(OutHeader.GUILD_SEARCH_RESULT);
        outPacket.encodeArr(requestHeader);
        for (Collection<Guild> list : List.of(featured, guilds)) {
            outPacket.encodeInt(list.size());
            for (Guild g : list) {
                outPacket.encodeInt(g.getId());
                outPacket.encodeByte(g.getLevel());
                outPacket.encodeString(g.getName());
                outPacket.encodeString(g.getGuildLeader() == null ? "" : g.getGuildLeader().getName());
                outPacket.encodeShort(g.getMembers().size());
                outPacket.encodeShort(g.getMembers().isEmpty() ? 0 : g.getAverageMemberLevel());
                outPacket.encodeByte(false);
                outPacket.encodeFT(FileTime.currentTime());
                outPacket.encodeByte(false);
                outPacket.encodeString(g.getNotice() == null ? "" : g.getNotice());
                outPacket.encodeInt(g.getMainActivity());
                outPacket.encodeInt(g.getActiveTimes());
                outPacket.encodeInt(g.getAgeGroup());
                outPacket.encodeByte(g.isAppliable());
            }
        }
        return outPacket;
    }


    public static OutPacket allianceResult(AllianceResult ar) {
        OutPacket outPacket = new OutPacket(OutHeader.ALLIANCE_RESULT);

        outPacket.encode(ar);

        return outPacket;
    }

    public static OutPacket guildBBSResult(GuildBBSPacket gbp) {
        OutPacket outPacket = new OutPacket(OutHeader.GUILD_BBS_RESULT);

        outPacket.encode(gbp);

        return outPacket;
    }

    public static OutPacket flameWizardFlameWalkEffect(Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.FLAME_WIZARD_FLAME_WALK_EFFECT);

        outPacket.encodeInt(chr.getId());

        return outPacket;
    }

    public static OutPacket flameWizardFlareBlink(Char chr, Position newPosition, boolean used) {
        OutPacket outPacket = new OutPacket(OutHeader.FLAME_WIZARD_FLARE_BLINK);

        outPacket.encodeInt(chr.getId()); //chr
        outPacket.encodeByte(used); //used

        if (used) {

            //Blink - Clear + Teleport
            chr.write(FieldPacket.teleport(newPosition, chr));

        } else {

            //Blink - Set Position
            outPacket.encodeByte(used);
            outPacket.encodeShort(1);
            outPacket.encodePosition(newPosition); //2x encode Short (x/y)
            outPacket.encodePosition(new Position()); //2x encode Short (x/y)
        }

        return outPacket;
    }

    public static OutPacket friendResult(FriendResult friendResult) {
        OutPacket outPacket = new OutPacket(OutHeader.FRIEND_RESULT);

        friendResult.encode(outPacket);

        return outPacket;
    }


    // removed v188?
//    public static OutPacket loadAccountIDOfCharacterFriendResult(Set<Friend> friends) {
//        OutPacket outPacket = new OutPacket(OutHeader.LOAD_ACCOUNT_ID_OF_CHARACTER_FRIEND_RESULT);
//
//        outPacket.encodeInt(friends.size());
//        for(Friend fr : friends) {
//            outPacket.encodeInt(fr.getFriendID());
//            outPacket.encodeInt(fr.getFriendAccountID());
//        }
//
//        return outPacket;
//    }

    public static OutPacket macroSysDataInit(List<Macro> macros) {
        OutPacket outPacket = new OutPacket(OutHeader.MACRO_SYS_DATA_INIT);

        outPacket.encodeByte(macros.size());
        for (Macro macro : macros.stream().sorted(Comparator.comparingInt(Macro::getMacroPos)).collect(Collectors.toList())) {
            macro.encode(outPacket);
        }
        return outPacket;
    }

    public static OutPacket monsterBookSetCard(int id) {
//        OutPacket outPacket = new OutPacket(OutHeader.MONSTER_LIFE_INVITE_ITEM_RESULT);
        OutPacket outPacket = new OutPacket(OutHeader.MONSTER_BOOK_SET_CARD);

        outPacket.encodeByte(id > 0); // false -> already added msg
        if (id > 0) {
            outPacket.encodeInt(id);
            outPacket.encodeInt(1); // card count, but we're just going to stuck with 1.
        }

        return outPacket;
    }

    /** The active Inner Ability preset (every line) */
    public static OutPacket characterPotentialSet(Char chr) {
        return characterPotentialSet(chr, chr.getPotentialMan().getActivePreset());
    }

    /** v265: byte exclRequest, byte preset (0-2, the list it replaces), short count, lines */
    public static OutPacket characterPotentialSet(Char chr, int preset) {
        OutPacket outPacket = new OutPacket(OutHeader.CHARACTER_POTENTIAL_SET);

        outPacket.encodeByte(true); // exclRequest
        outPacket.encodeByte(preset);
        encodePotentialList(outPacket, chr.getPotentialMan().getPotentials(preset));

        return outPacket;
    }

    /** Every preset's lines (the same block as the character data's) */
    public static OutPacket characterPotentialReset(Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.CHARACTER_POTENTIAL_RESET);

        encodePotentialPresets(outPacket, chr);

        return outPacket;
    }

    public static void encodePotentialPresets(OutPacket outPacket, Char chr) {
        var cpm = chr.getPotentialMan();
        for (int p = 0; p < net.swordie.ms.client.character.potential.CharacterPotentialMan.PRESETS; p++) {
            encodePotentialList(outPacket, cpm.getPotentials(p));
        }
    }

    private static void encodePotentialList(OutPacket outPacket, List<CharacterPotential> lines) {
        outPacket.encodeShort(lines.size());
        for (CharacterPotential cp : lines) {
            cp.encode(outPacket);
        }
    }

    public static OutPacket characterPotentialChangePreset(int preset) {
        OutPacket outPacket = new OutPacket(OutHeader.CHARACTER_POTENTIAL_CHANGE_PRESET);

        outPacket.encodeByte(true); // exclRequest
        outPacket.encodeByte(preset);

        return outPacket;
    }

    /**
     * The Circulator's before/after window: int count, {int skill, byte slv, byte key, byte grade}, int item,
     * FT, byte 1, byte preset, int. The player answers with USER_MIRACLE_CIRCULATOR_OPTION_REQUEST.
     */
    public static OutPacket miracleCirculatorResult(List<CharacterPotential> lines, int itemId, int preset) {
        OutPacket outPacket = new OutPacket(OutHeader.MIRACLE_CIRCULATOR_RESULT);

        outPacket.encodeInt(lines.size());
        for (CharacterPotential cp : lines) {
            outPacket.encodeInt(cp.getSkillID());
            outPacket.encodeByte(cp.getSlv());
            outPacket.encodeByte(cp.getKey());
            outPacket.encodeByte(cp.getGrade());
        }
        outPacket.encodeInt(itemId);
        outPacket.encodeFT(FileTime.currentTime());
        outPacket.encodeByte(1);
        outPacket.encodeByte(preset);
        outPacket.encodeInt(4);

        return outPacket;
    }

    /**
     * Black Rebirth Flame before/after window (type 1): byte 1, long SN, short pos, int flame, short tier,
     * byte n, n x long exGradeOption, then the new item. The player answers with
     * USER_MEMORIAL_EX_UPGRADE_ITEM_OPTION_REQUEST.
     */
    public static OutPacket memorialFlameResult(Equip equip, Equip result, int flameId, int tier) {
        return memorialFlameResult(equip, java.util.List.of(result), flameId, tier);
    }

    /** One result per reset; the window's pick is the result's index. */
    public static OutPacket memorialFlameResult(Equip equip, java.util.List<Equip> results, int flameId, int tier) {
        OutPacket outPacket = new OutPacket(OutHeader.BLACK_FLAME_OF_RESURRECTION_MODIFIED);

        outPacket.encodeInt(1);
        outPacket.encodeByte(1);
        outPacket.encodeLong(equip.getId());
        outPacket.encodeShort(equip.getBagIndexWithEquipped());
        outPacket.encodeInt(flameId);
        outPacket.encodeShort(tier);
        outPacket.encodeByte(results.size());
        for (Equip result : results) {
            outPacket.encodeLong(result.getExGradeOption());
        }
        outPacket.encodeInt(0); // window type 0 (client 0x141a58bd0): the flame window
        outPacket.encodeByte(results.size());
        for (Equip result : results) {
            result.encode(outPacket);
        }

        return outPacket;
    }

    /** The flame a bonus-stat reset for mesos rolls like (v271; 0 = no meso reset). */
    public static OutPacket mesoFlameItem(int flameItemId) {
        OutPacket outPacket = new OutPacket(OutHeader.MESO_FLAME_ITEM);

        outPacket.encodeInt(flameItemId);

        return outPacket;
    }

    /**
     * Type 0: a flame was used without a keep/replace choice. Ends the Enhance window's Bonus Stats step (client
     * 0x145b20e20 -> event 0x20 -> 0x1422194e0); the window waits for it after a flame from the window.
     */
    public static OutPacket flameApplied() {
        OutPacket outPacket = new OutPacket(OutHeader.BLACK_FLAME_OF_RESURRECTION_MODIFIED);

        outPacket.encodeInt(0);

        return outPacket;
    }

    /** Closes the Black Rebirth Flame window (type 2) */
    public static OutPacket memorialFlameClear() {
        OutPacket outPacket = new OutPacket(OutHeader.BLACK_FLAME_OF_RESURRECTION_MODIFIED);

        outPacket.encodeInt(2);

        return outPacket;
    }

    public static OutPacket characterHonorExp(int exp) {
        OutPacket outPacket = new OutPacket(OutHeader.CHARACTER_HONOR_EXP);

        outPacket.encodeInt(exp);

        return outPacket;
    }

    public static OutPacket cashPetPickUpOnOffResult(boolean changed, boolean on) {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_PET_PICK_UP_ON_OFF_RESULT);

        outPacket.encodeByte(on);
        outPacket.encodeByte(changed);

        return outPacket;
    }

    /** The answer to a title change: 1 when the title was taken off, 0 when one was put on */
    public static OutPacket nickSkillExpired(boolean removed) {
        OutPacket outPacket = new OutPacket(OutHeader.NICK_SKILL_EXPIRED);

        outPacket.encodeInt(removed ? 1 : 0);

        return outPacket;
    }

    public static OutPacket setSonOfLinkedSkillResult(LinkedSkillResultType lsrt, int sonID, String sonName,
                                                      int originalSkillID, String existingParentName) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_SON_OF_LINKED_SKILL_RESULT);

        outPacket.encodeInt(lsrt.getVal());
        outPacket.encodeInt(originalSkillID);
        switch (lsrt) {
            case SetSonOfLinkedSkillResult_Success:
                outPacket.encodeInt(sonID);
                outPacket.encodeString(sonName);
                break;
            case SetSonOfLinkedSkillResult_Fail_ParentAlreadyExist:
                outPacket.encodeString(existingParentName);
                outPacket.encodeString(sonName);
                break;
            case SetSonOfLinkedSkillResult_Fail_Unknown:
                break;
            case SetSonOfLinkedSkillResult_Fail_MaxCount:
                outPacket.encodeString(existingParentName);
                break;
            case SetSonOfLinkedSkillResult_Fail_DBRequestFail:
                break;
        }

        return outPacket;
    }

    public static OutPacket memorialCubeResult(Equip equip, MemorialCubeInfo mci) {
        OutPacket outPacket = new OutPacket(OutHeader.MEMORIAL_CUBE_RESULT);

        outPacket.encodeLong(equip.getSerialNumber());
        mci.encode(outPacket);
        outPacket.encodeInt(0); // v265

        return outPacket;
    }

    public static OutPacket rebirthFlameResult(int flameItemId, int equipPos, int b1) {
        OutPacket outPacket = new OutPacket(OutHeader.REBIRTH_FLAME_RESULT);

        outPacket.encodeInt(flameItemId);
        outPacket.encodeInt(equipPos);
        outPacket.encodeByte(b1);

        return outPacket;
    }

    public static OutPacket blackCubeResult(Equip equip, int cubeIndex, int remainingCount, MemorialCubeInfo mci) {
        return memorialCubeWindow(mci, 1);
    }

    public static OutPacket whiteCubeResult(Equip equip, int cubeIndex, MemorialCubeInfo mci) {
        return memorialCubeWindow(mci, 2);
    }

    /**
     * Before/after window (0x14552f2c0): memorial info (0x1406501e0), then int window type (1 potential,
     * 2 bonus potential) and that window's body (0x1416eea40): short, byte, 3 ints, byte n, n typed items (the
     * after state). The before state is the client's own copy of the equip. Answered with 382.
     * v271 (0x14062c930): the short is the BEFORE tier (1 rare .. 4 legendary). An AFTER card above it counts as a
     * rank-up (0x141a4e080), which disables Reset and warns when keeping BEFORE; 0 made every roll a rank-up.
     */
    public static OutPacket memorialCubeWindow(MemorialCubeInfo mci, int windowType) {
        OutPacket outPacket = new OutPacket(OutHeader.BLACK_CUBE_RESULT);

        mci.encode(outPacket);
        outPacket.encodeInt(windowType);
        Equip before = mci.getEquip();
        int grade = before == null ? 0 : windowType == 2 ? before.getBonusGrade() : before.getBaseGrade();
        outPacket.encodeShort(grade > 0 ? grade & 0xF : 0);
        outPacket.encodeByte(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeByte(mci.getResultCount());
        for (int i = 0; i < mci.getResultCount(); i++) {
            mci.getResult(i).encode(outPacket);
        }

        return outPacket;
    }

    /** Closes the before/after window and clears the client's memorial info (0x14552f3c0, no body). */
    public static OutPacket memorialCubeClear() {
        return new OutPacket(OutHeader.MEMORIAL_CUBE_MODIFIED);
    }

    public static OutPacket broadcastMsg(BroadcastMsg broadcastMsg) {
        OutPacket outPacket = new OutPacket(OutHeader.BROADCAST_MSG);

        broadcastMsg.encode(outPacket);

        return outPacket;
    }

    public static OutPacket setAvatarMegaphone(Char chr, int megaItemId, List<String> lineList, boolean whisperIcon) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_AVATAR_MEGAPHONE);

        outPacket.encodeInt(megaItemId); // Avatar Megaphone Item ID
        outPacket.encodeString(chr.getName());

        for (String line : lineList) {
            outPacket.encodeString(line);
        }
        chr.encodeChatInfo(outPacket, lineList.toString().replace("[", "").replace("]", "").replace(",", "\r\n"));

        outPacket.encodeInt(chr.getChannel() - 1);
        outPacket.encodeByte(whisperIcon);

        chr.getAvatarData().getAvatarLook().encode(outPacket); // encode AvatarLook
        outPacket.encodeByte(0);

        return outPacket;
    }

    public static OutPacket receiveHyperStatSkillResetResult(int charID, boolean exclRequest, boolean success) {
        OutPacket outPacket = new OutPacket(OutHeader.RECEIVE_HYPER_STAT_SKILL_RESET_RESULT);

        outPacket.encodeByte(exclRequest);
        outPacket.encodeInt(charID);
        outPacket.encodeByte(success);

        return outPacket;
    }

    public static OutPacket mapTransferResult(MapTransferType mapTransferType, byte itemType, List<Integer> hyperrockfields) {
        OutPacket outPacket = new OutPacket(OutHeader.MAP_TRANSFER_RESULT);

        outPacket.encodeByte(mapTransferType.getVal()); // Map Transfer Type
        outPacket.encodeByte(itemType); // Item Type (5 = Cash)
        if (mapTransferType == MapTransferType.DeleteListSend || mapTransferType == MapTransferType.RegisterListSend) {
            for (int fieldid : hyperrockfields) {
                outPacket.encodeInt(fieldid); // Target Field ID
            }
        }

        return outPacket;
    }

    public static OutPacket monsterCollectionResult(MonsterCollectionResultType mcrt, InvType invType, int fullSlots) {
        OutPacket outPacket = new OutPacket(OutHeader.MONSTER_COLLECTION_RESULT);

        outPacket.encodeInt(mcrt.ordinal());
        // v265: int n, n x (int inventory type, int slots), then for type 13 a list (none)
        outPacket.encodeInt(invType != null ? 1 : 0);
        if (invType != null) {
            outPacket.encodeInt(invType.getVal());
            outPacket.encodeInt(fullSlots);
        }
        if (mcrt.ordinal() == 13) {
            outPacket.encodeInt(0);
        }

        return outPacket;
    }

    public static OutPacket weatherEffectNotice(WeatherEffNoticeType type, String text, int duration) {
        return weatherEffectNotice(type.getVal(), text, duration);
    }

    /** type from data (boss pattern files) */
    public static OutPacket weatherEffectNotice(int type, String text, int duration) {
        OutPacket outPacket = new OutPacket(OutHeader.WEATHER_EFFECT_NOTICE);

        outPacket.encodeString(text); // Text
        outPacket.encodeInt(type); // Weather Notice Type
        outPacket.encodeInt(duration); // Duration in ms
        outPacket.encodeByte(1); // Forced Notice

        return outPacket;
    }

    public static OutPacket resultInstanceTable(String name, int type, int subType, boolean rightResult, int value) {
        OutPacket outPacket = new OutPacket(OutHeader.RESULT_INSTANCE_TABLE.getValue());

        outPacket.encodeString(name);
        outPacket.encodeInt(type); // nCount
        outPacket.encodeInt(subType);
        outPacket.encodeByte(rightResult);
        outPacket.encodeInt(value);

        return outPacket;
    }

    /**
     * Creates a packet to indicate the golden hammer is finished.
     *
     * @param returnResult See below
     * @param result       when returnResult is:
     *                     0 or 1:
     *                     Anything: Golden hammer refinement applied
     *                     2:
     *                     0: Increased available upgrade by 1
     *                     1: Refining using golden hammer failed
     *                     3:
     *                     1: Item is not upgradable
     *                     2: 2 upgrade increases have been used already
     *                     3: You can't vicious hammer non-horntail necklace
     * @param upgradesLeft amount of upgrades left. NOTE: ((v9 >> 8) & 0xFF) - v9 + 2) (where v9 = upgradesLeft)
     * @return the created packet
     */
    public static OutPacket goldHammerItemUpgradeResult(GoldHammerResult returnResult, int result, int upgradesLeft) {
        OutPacket outPacket = new OutPacket(OutHeader.GOLD_HAMMER_ITEM_UPGRADE_RESULT);

        outPacket.encodeByte(true);
        outPacket.encodeByte(returnResult.getVal());
        outPacket.encodeInt(result);
        if (returnResult == GoldHammerResult.Success) {
            outPacket.encodeInt(upgradesLeft);
        }

        return outPacket;
    }

    public static OutPacket returnToCharacterSelect() {
        return new OutPacket(OutHeader.RETURN_TO_CHARACTER_SELECT);
    }

    public static OutPacket returnToTitle() {
        return new OutPacket(OutHeader.RETURN_TO_TITLE);
    }

    /** Sets the client's clock, as SET_FIELD's first FILETIME does (same client setter). */
    public static OutPacket serverTime() {
        OutPacket outPacket = new OutPacket(OutHeader.GET_SERVER_TIME);
        outPacket.encodeFT(FileTime.currentTime());
        return outPacket;
    }

    public static OutPacket townPortal(TownPortal townPortal) {
        OutPacket outPacket = new OutPacket(OutHeader.TOWN_PORTAL); // As a response to Enter_TP_Request, creates the Door in the TownField

        outPacket.encodeInt(townPortal.getTownFieldId()); // townFieldId
        outPacket.encodeInt(townPortal.getFieldFieldId()); // field FieldId
        // the client reads the rest only when both field ids are real
        if (townPortal.getTownFieldId() != GameConstants.NO_MAP_ID && townPortal.getFieldFieldId() != GameConstants.NO_MAP_ID) {
            outPacket.encodeInt(townPortal.getSkillid()); // Skill Id
            // where the door stands in the field: the town twin sends the player back there
            Position fieldPos = townPortal.getFieldPosition();
            outPacket.encodePosition(fieldPos != null ? fieldPos : new Position());
        }

        return outPacket;
    }

    public static OutPacket givePopularityResult(PopularityResultType prType, Char targetChr, int newFame, boolean inc) {
        OutPacket outPacket = new OutPacket(OutHeader.GIVE_POPULARITY_RESULT);

        outPacket.encodeByte(prType.getVal());

        switch (prType) {
            case Success:
                outPacket.encodeString(targetChr.getName());
                outPacket.encodeByte(inc); // true = fame  |  false = defame
                outPacket.encodeInt(newFame);
                break;

            case InvalidCharacterId:
            case LevelLow:
            case AlreadyDoneToday:
            case AlreadyDoneTarget:
                break;

            case Notify:
                outPacket.encodeString(targetChr.getName());
                outPacket.encodeByte(inc); // true = fame  |  false = defame
                break;
        }

        return outPacket;
    }

    public static OutPacket requestEventList(int levelReq, boolean show, List<EventListData.EventListDataRecord> eventList) {
        OutPacket outPacket = new OutPacket(OutHeader.REQUEST_EVENT_LIST);

        // v271 CWvsContext::OnRequestEventList (0x1459ECA00): int defaultLevelLimit, byte show;
        // show: "main" string, byte info flag (+ int maxCount), int notify n x (byte, string), int n x event (0x1459ECA60)
        outPacket.encodeInt(0); // defaultLevelLimit
        outPacket.encodeByte(show);
        if (show) {
            outPacket.encodeString("Maple Events"); // "main"
            outPacket.encodeByte(0); // no "info" block, so maxCount is not read
            outPacket.encodeInt(0); // "notify" entries

            EventListData.encode(outPacket, eventList);
        }
        // always: int live event type + int n x LIVE_EVENT::Decode (0x140CE2F30)
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);

        return outPacket;
    }

    public static OutPacket eventListResult(List<EventListData.EventListDataRecord> eventList) {
        OutPacket outPacket = new OutPacket(OutHeader.EVENT_LIST);

        // v271 tabbed EVENT LIST (CUIGroupEventList): OnUnk378 (0x1459F0A00) -> sub_144D29200
        EventListData.encodeV271(outPacket, eventList);

        return outPacket;
    }

    public static OutPacket randomPortalNotice(RandomPortal randomPortal) {
        OutPacket outPacket = new OutPacket(OutHeader.RANDOM_PORTAL_NOTICE);

        outPacket.encodeByte(randomPortal.getAppearType().getVal());
        outPacket.encodeInt(randomPortal.getField().getId());

        return outPacket;
    }

    /** Type 0: the client closes both Surprise Mission windows. */
    public static OutPacket randomMissionClose() {
        OutPacket outPacket = new OutPacket(OutHeader.RANDOM_MISSION_RESULT);
        outPacket.encodeInt(0);
        return outPacket;
    }

    public static OutPacket randomMissionResult(RandomMissionType type, int arg1, int arg2) {
        OutPacket outPacket = new OutPacket(OutHeader.RANDOM_MISSION_RESULT);

        // v271 (0x145abad40) reads more only for types 1 (int) and 3 (the mission list); these result types end here
        outPacket.encodeInt(type.getVal());

        return outPacket;
    }

    public static OutPacket matrixUpdate(Char chr, boolean update, int type, int typeArg) {
        if (net.swordie.ms.ServerSettings.getBoolean("test.v271Matrix", true)) {
            return net.swordie.ms.client.character.skills.vmatrix.V271Matrix.update(chr); // v271 core map
        }
        OutPacket outPacket = new OutPacket(OutHeader.MATRIX_UPDATE);

        List<MatrixRecord> matrixRecords = chr.getSortedMatrixRecords();
        outPacket.encodeInt(matrixRecords.size());
        for (MatrixRecord mr : matrixRecords) {
            outPacket.encode(mr);
        }

        outPacket.encodeInt(GameConstants.MAX_NODE_SLOTS);
        for (int i = 0; i < GameConstants.MAX_NODE_SLOTS; i++) {
            MatrixRecord mr = chr.getMatrixRecordByPosition(i);
            MatrixSlot ms = chr.getMatrixSlotByPosition(i);
            outPacket.encodeInt(mr != null ? matrixRecords.indexOf(mr) : -1); // nodeID
            outPacket.encodeInt(i); // matrix Slot position
            outPacket.encodeInt(ms.getEnhancementLv()); // slot enhancement level
            outPacket.encodeByte(ms.isUnlockedByPurchase()); // bShow Unlocked Symbol
        }

        outPacket.encodeByte(update);
        if (update) {
            outPacket.encodeInt(type);
            if (type == MatrixUpdateRequest.Activate.getVal()) {
                outPacket.encodeInt(typeArg);
            }
        }

        return outPacket;
    }

    public static OutPacket nodestoneOpenResult(MatrixRecord mr) {
        OutPacket outPacket = new OutPacket(OutHeader.NODE_STONE_OPEN_RESULT);

        outPacket.encodeInt(mr.getIconID());
        outPacket.encodeInt(1); // ?
        outPacket.encodeInt(mr.getSkillID1());
        outPacket.encodeInt(mr.getSkillID2());
        outPacket.encodeInt(mr.getSkillID3());
        outPacket.encodeInt(0); // ?

        return outPacket;
    }

    public static OutPacket nodestoneBulkResult(java.util.List<MatrixRecord> records) {
        OutPacket outPacket = new OutPacket(OutHeader.NODESTONE_RESULT_BULK);

        outPacket.encodeInt(records.size()); // number shown in the result window (0 = none)
        outPacket.encodeInt(records.size());
        for (MatrixRecord mr : records) {
            outPacket.encodeInt(mr.getIconID());
            outPacket.encodeInt(mr.getSlv());
            outPacket.encodeInt(mr.getSkillID1());
            outPacket.encodeInt(mr.getSkillID2());
            outPacket.encodeInt(mr.getSkillID3());
        }

        return outPacket;
    }

    public static OutPacket nodeEnhanceResult(int pos, int expGained, int oldSlv, int newSlv) {
        OutPacket outPacket = new OutPacket(OutHeader.NODE_ENHANCE_RESULT);

        outPacket.encodeInt(pos);
        outPacket.encodeInt(expGained);
        outPacket.encodeInt(oldSlv);
        outPacket.encodeInt(newSlv);

        return outPacket;
    }

    public static OutPacket nodeDisassembleResult(int shardsGained) {
        OutPacket outPacket = new OutPacket(OutHeader.NODE_DISASSEMBLE_RESULT);

        outPacket.encodeInt(shardsGained);

        return outPacket;
    }

    public static OutPacket nodeCraftResult(MatrixRecord mr, int quantity) {
        OutPacket outPacket = new OutPacket(OutHeader.NODE_CRAFT_RESULT);

        outPacket.encodeInt(mr.getIconID());
        outPacket.encodeInt(1); // ?
        outPacket.encodeInt(mr.getSkillID1());
        outPacket.encodeInt(mr.getSkillID2());
        outPacket.encodeInt(mr.getSkillID3());
        outPacket.encodeInt(quantity);

        return outPacket;
    }

    public static OutPacket nodeOpenVmatrix(boolean success) { // PIN Correct?
        OutPacket outPacket = new OutPacket(OutHeader.NODE_OPEN_VMATRIX);

        outPacket.encodeInt(2);
        outPacket.encodeByte(success);

        return outPacket;
    }

    public static OutPacket antiMacroResult(final byte[] image,
                                            AntiMacro.AntiMacroResultType notificationType,
                                            AntiMacro.AntiMacroType antiMacroType,
                                            int subType) {
        OutPacket outPacket = new OutPacket(OutHeader.ANTI_MACRO_RESULT);

        var secureOutPacket = new OutPacket();
        secureOutPacket.encodeInt(notificationType.getVal());
        secureOutPacket.encodeInt(antiMacroType.getVal());
        secureOutPacket.encodeInt(subType); // nSubType
        secureOutPacket.encodeString(""); // targetChr
        outPacket.encodeEncryptedBuffer(secureOutPacket.getData());

        if (notificationType == AntiMacro.AntiMacroResultType.AntiMacroRes) {
            switch (antiMacroType.getVal()) {
                case 13:
                case 16:
                case 20:
                case 24:
                case 25:
                case 26:
                case 28:
                case 31:
                    // Violetta
                    outPacket.encodeInt(1);
                    outPacket.encodeInt(2);
                    outPacket.encodeInt(3);
                    outPacket.encodeInt(4);
                    break;
            }
            outPacket.encodeByte(true); // false = transparent no timer, true = solid yes timer
            outPacket.encodeInt(image.length);
            outPacket.encodeArr(image);
        } else {
            outPacket.encodeString("");
        }

        //Wack fix for LD crashing
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);

        return outPacket;
    }

    public static OutPacket setPassenserRequest(int requestorChrId) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_PASSENGER_REQUEST);

        outPacket.encodeInt(requestorChrId);

        return outPacket;
    }

    public static OutPacket platformarEnterResult(boolean wrap) {
        OutPacket outPacket = new OutPacket(OutHeader.PLATFORMAR_ENTER_RESULT);

        outPacket.encodeByte(wrap);

        return outPacket;
    }

    public static OutPacket platformarOxyzen(int oxyzen) {
        OutPacket outPacket = new OutPacket(OutHeader.PLATFORMAR_OXYZEN);

        outPacket.encodeInt(oxyzen); // casted to long in client side

        return outPacket;
    }

    public static OutPacket setMaplePoints(int maplePoints) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_MAPLE_POINT);

        outPacket.encodeByte(1); // v271: 1 = the balance the UI shows (0 sets a second, hidden one)
        outPacket.encodeInt(maplePoints);

        return outPacket;
    }

    public static OutPacket linkSkillResult(int skillID, LinkSkillResult lsr) {
        OutPacket outPacket = new OutPacket(OutHeader.LINK_SKILL_RESULT);

        // v265 reads one int message code (0x141e07690): 0 none, 1 error, 2 no more skills, 3 same skill,
        // 4 preset applied, 5 cannot change, 6 not right now, 7 not here
        int code = switch (lsr) {
            case Unlink_Success, Link_Success -> 0;
            case SkillLimitHit -> 2;
            case AlreadyTransferred -> 3;
            case UnableToActivate -> 6;
            case InvalidRequest -> 7;
            default -> 1;
        };
        outPacket.encodeInt(code);

        return outPacket;
    }

    public static OutPacket unlinkedSkillInfo(LinkSkill linkSkill) {
        return unlinkedSkillInfo(Collections.singleton(linkSkill));
    }

    public static OutPacket unlinkedSkillInfo(Set<LinkSkill> linkSkills) {
        OutPacket outPacket = new OutPacket(OutHeader.UNLINKED_SKILL_INFO);

        outPacket.encodeInt(linkSkills.size());
        for (LinkSkill ls : linkSkills) {
            outPacket.encodeInt(ls.getLinkSkillID());
            outPacket.encodeInt(ls.getOriginID());
        }

        return outPacket;
    }

    public static OutPacket linkedSkillInfo(Char chr, LinkSkill linkSkill) {
        // v265 link skills are presets: int type (0 set a preset, 1 apply one, 2 add, 3 remove), byte preset, ...
        return linkedSkillPreset(2, LinkSkillsModule.appliedPreset(chr),
                Collections.singletonList(linkSkill.getLinkSkillID()));
    }

    /** Own link changed (level up, job change): (char id, origin skill, level), and a link id to drop from presets. */
    public static OutPacket linkedSkillOwnInfo(int charId, int originSkillId, int level, int removeFromPresets) {
        OutPacket outPacket = new OutPacket(OutHeader.LINKED_SKILL_INFO);

        outPacket.encodeInt(4);
        outPacket.encodeInt(charId);
        outPacket.encodeInt(originSkillId);
        outPacket.encodeInt(level);
        outPacket.encodeInt(removeFromPresets);

        return outPacket;
    }

    public static OutPacket linkedSkillPreset(int type, int preset, List<Integer> skillIds) {
        OutPacket outPacket = new OutPacket(OutHeader.LINKED_SKILL_INFO);

        outPacket.encodeInt(type);
        outPacket.encodeByte(preset);
        if (type == 0) {
            outPacket.encodeInt(skillIds == null ? 0 : skillIds.size());
            if (skillIds != null) {
                for (int id : skillIds) {
                    outPacket.encodeInt(id);
                }
            }
        } else if (type == 2 || type == 3) {
            outPacket.encodeInt(skillIds == null || skillIds.isEmpty() ? 0 : skillIds.get(0));
        }

        return outPacket;
    }

    public static OutPacket towerChairSettingResult() {
        OutPacket outPacket = new OutPacket(OutHeader.TOWER_CHAIR_SETTING_RESULT);

        return outPacket;
    }

    public static OutPacket unionPresetInfoResult(int preset, boolean unlocked, UnionBoard ub) {
        OutPacket outPacket = new OutPacket(OutHeader.UNION_PRESET_INFO_RESULT);

        outPacket.encodeInt(preset);
        outPacket.encodeByte(unlocked);
        if (unlocked) {
            outPacket.encode(ub);
        }

        return outPacket;
    }

    /** Empty boss reward storage (inventory Boss Rewards panel): 5 item groups and the record list, all count 0. */
    public static OutPacket emptyBossRewardStorage() {
        OutPacket outPacket = new OutPacket(OutHeader.BOSS_REWARD_STORAGE);
        for (int i = 0; i < 5; i++) {
            outPacket.encodeInt(0);
        }
        outPacket.encodeInt(0);
        return outPacket;
    }

    public static OutPacket powerCrystalInfo(List<Item> powerCrystals) {
        OutPacket outPacket = new OutPacket(OutHeader.POWER_CRYSTAL_INFO);
        outPacket.encodeInt(powerCrystals.size());
        for(Item item : powerCrystals) {
            PowerCrystal powerCrystal = item.getPowerCrystalInfo();
            outPacket.encodeLong(item.getId());
            outPacket.encodeInt(powerCrystal.getBossId());
            outPacket.encodeInt(powerCrystal.getPlayerCount());
            outPacket.encodeLong(powerCrystal.getPrice());
            outPacket.encodeLong(0);
            outPacket.encodeFT(powerCrystal.getObtainedDate());
        }
        return outPacket;
    }

    public static OutPacket firstEnterReward(Set<FirstEnterReward> firstEnterRewards, FirstEnterRewardPacketType type, int receiveQuantity) {
        OutPacket outPacket = new OutPacket(OutHeader.FIRST_ENTER_REWARD);
        outPacket.encodeByte((byte)type.getVal());

        switch (type) {
            case Load_Items: // Item
                int size = firstEnterRewards.size();
                outPacket.encodeLong(0);
                outPacket.encodeInt(size);

                for (FirstEnterReward firstEnterReward : firstEnterRewards) {
                    int mask = 9; // hardcoded
                    outPacket.encodeInt((int)firstEnterReward.getId()); // nIdx
                    if ((mask & 1) != 0) {
                        outPacket.encodeFT(firstEnterReward.getExpireTime());
                        outPacket.encodeFT(firstEnterReward.getExpireTime()); // ftRewardExpireDate
                        outPacket.encodeFT(firstEnterReward.getExpireTime());
                        outPacket.encodeFT(firstEnterReward.getExpireTime());
                    }
                    if ((mask & 2) != 0) {
                        outPacket.encodeInt(2000000); // hardcode-ok
                        outPacket.encodeInt(2000000); // hardcode-ok
                        outPacket.encodeInt(2000000); // hardcode-ok
                        outPacket.encodeInt(2000000); // hardcode-ok
                        outPacket.encodeInt(2000000); // hardcode-ok
                        outPacket.encodeInt(2000000); // hardcode-ok
                        outPacket.encodeString("2000000"); // hardcode-ok
                        outPacket.encodeString("2000000"); // hardcode-ok
                        outPacket.encodeString("2000000"); // hardcode-ok
                    }
                    outPacket.encodeInt(firstEnterReward.getRewardType().getVal()); // nRewardType: 1 = Item, 2 = Item, 3 = Maple Points, 4 = Meso, 5 = EXP
                    int itemId = 0;
                    int quantity = 0;
                    if(firstEnterReward.getRewardType() == FirstEnterRewardType.CashItem || firstEnterReward.getRewardType() == FirstEnterRewardType.GameItem){
                        itemId = firstEnterReward.getItemId();
                        quantity = firstEnterReward.getQuantity();
                    }
                    outPacket.encodeInt(itemId); // nItemId
                    outPacket.encodeInt(quantity); // nQuantity
                    outPacket.encodeInt(12);
                    outPacket.encodeFT(firstEnterReward.getExpireTime());
                    outPacket.encodeInt(13);
                    int maplePoints = 0;
                    if(firstEnterReward.getRewardType() == FirstEnterRewardType.MaplePoints){
                        maplePoints = firstEnterReward.getQuantity();
                    }
                    outPacket.encodeInt(maplePoints); // nMaplePoints

                    long meso = 0L;
                    if(firstEnterReward.getRewardType() == FirstEnterRewardType.Meso){
                        meso = firstEnterReward.getQuantity();
                    }
                    outPacket.encodeLong(meso); // nMeso

                    int exp = 0;
                    if(firstEnterReward.getRewardType() == FirstEnterRewardType.Exp){
                        exp = firstEnterReward.getQuantity();
                    }
                    outPacket.encodeInt(exp); // nExp
                    outPacket.encodeInt(17);
                    outPacket.encodeInt(18);
                    outPacket.encodeString("2000000"); // hardcode-ok
                    outPacket.encodeString("2000000"); // hardcode-ok
                    outPacket.encodeString("2000000"); // hardcode-ok
                    if ((mask & 4) != 0) {
                        outPacket.encodeString("2000000"); // hardcode-ok
                    }
                    if ((mask & 8) != 0) {
                        outPacket.encodeString(firstEnterReward.getDescription());
                    }
                    boolean canClaim = true;
                    outPacket.encodeInt(canClaim ? 0 : 1);
                    outPacket.encodeInt(21);
                }
                break;
            case Nx_Claimed: // Maple Points
                outPacket.encodeInt(1); // nIdx
                outPacket.encodeInt(receiveQuantity); // nAmount
                outPacket.encodeInt(0);
                break;
            case Item_Claimed: // Game Item
                outPacket.encodeInt(1); // nIdx
                outPacket.encodeInt(ItemConstants.SWORD); // nItemId?
                outPacket.encodeInt(0);
            case Cash_Item_Claimed: // Cash Item
                outPacket.encodeInt(1); // nIdx
                outPacket.encodeInt(FLAME_CROWN); // nItemId?
                outPacket.encodeInt(0);
                break;
            case Meso_Claimed: // Mesos
                outPacket.encodeInt(1); // nIdx
                outPacket.encodeInt(receiveQuantity); // nAmount
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                break;
            case Exp_Claimed: // Exp
                outPacket.encodeInt(1); // nIdx
                outPacket.encodeInt(receiveQuantity); // nAmount
                outPacket.encodeInt(0);
                break;
            case Error: // Error
                outPacket.encodeByte(101); // 101~103, errorType
                break;
            case Error2: // Error 2
                outPacket.encodeByte(101); // 0/33/147, errorType
                break;
            case Meso_Error: // Meso Error
            case Exp_Error: // Exp Error
            case Time_Expired: // Error: Time has passed
                break;
        }

        return outPacket;
    }

    public static OutPacket emoticonResult(EmoticonType type, int emoticonId, short fromPos, short toPos, String shortcut) {
        OutPacket outPacket = new OutPacket(OutHeader.EMOTICON_RESULT);

        outPacket.encodeByte(type.getVal());
        switch (type) {
            case UnlockGroupChatSlot:
                outPacket.encodeShort(0); // nGroupChatIdx
                break;
            case MoveEmoticonGroup:
                outPacket.encodeShort(fromPos);
                outPacket.encodeShort(toPos);
                break;
            case Unk2:
                outPacket.encodeShort(0);
                break;
            case Unk3:
                outPacket.encodeShort(0);
                break;
            case Unk4:
                outPacket.encodeShort(0);
                break;
            case AddEmoticon:
                outPacket.encodeInt(emoticonId);
                outPacket.encodeShort(toPos);
                break;
            case RemoveEmoticon:
                outPacket.encodeInt(emoticonId);
                break;
            case MoveEmoticon:
                outPacket.encodeShort(fromPos);
                outPacket.encodeShort(toPos);
                break;
            case AddEmoticonShortcut:
                outPacket.encodeShort(toPos);
                outPacket.encodeInt(emoticonId);
                outPacket.encodeString(shortcut, 20); // fixed length
                outPacket.encodeByte(0);
                break;
            case MoveEmoticonShortcut:
                outPacket.encodeShort(fromPos);
                outPacket.encodeShort(toPos);
                break;
            case RemoveEmoticonShortcut:
                outPacket.encodeShort(fromPos);
                break;


        }

        return outPacket;
    }

    public static OutPacket updateDBChar(Char chr, DBChar mask) {
        OutPacket outPacket = new OutPacket(OutHeader.UPDATE_DBCHAR);

        var bool = true;
        outPacket.encodeByte(bool);
        if (bool) {
            CharEncoder.encode(chr, outPacket, mask);
        }

        return outPacket;
    }

    public static OutPacket aranDireWolfCurse(Map<Mob, Integer> mobs) {
        OutPacket outPacket = new OutPacket(OutHeader.ARAN_DIRE_WOLF_CURSE);

        outPacket.encodeInt(mobs.size());
        for (var entry : mobs.entrySet()) {
            var mob = entry.getKey();
            var direWolfCurseCount = entry.getValue();

            outPacket.encodeInt(mob.getObjectId());
            outPacket.encodeInt(direWolfCurseCount);
            outPacket.encodeInt(10000); // curse duration
        }

        return outPacket;
    }

    /** The uses a boss run gives each 6th job Ascent skill (its Skill.wz 6thCount); the client counts them down. */
    public static OutPacket sixthSkillCount(Map<Integer, Integer> counts) {
        OutPacket outPacket = new OutPacket(OutHeader.SIXTH_SKILL_COUNT);

        outPacket.encodeInt(counts.size());
        for (var entry : counts.entrySet()) {
            outPacket.encodeInt(entry.getKey());
            outPacket.encodeInt(entry.getValue());
        }

        return outPacket;
    }

    // uses left per skill, for skills limited to n uses per Adrenaline Rush / Adrenal Awakening
    public static OutPacket skillUseCount(boolean reset, Map<Integer, Integer> counts) {
        OutPacket outPacket = new OutPacket(OutHeader.SKILL_USE_COUNT);

        outPacket.encodeByte(reset);
        outPacket.encodeInt(counts.size());
        for (var entry : counts.entrySet()) {
            outPacket.encodeInt(entry.getKey());
            outPacket.encodeInt(entry.getValue());
        }

        return outPacket;
    }

    public static OutPacket updateVoyageInfo(VoyageInfoType type, Vessel vessel) {
        return updateVoyageInfo(type, vessel, false, 0);
    }

    public static OutPacket updateVoyageInfo(VoyageInfoType type, int routeId) {
        return updateVoyageInfo(type, null, false, routeId);
    }

    public static OutPacket updateVoyageInfo(VoyageInfoType type, Vessel vessel, boolean isInParty, int routeId) {
        OutPacket outPacket = new OutPacket(OutHeader.UPDATE_VOYAGE_INFO);

        outPacket.encodeByte(type.getVal());
        switch (type) {
            // More unknown/unhandled VoyageInfoTypes
            case UpdateInventoryInfo:
                outPacket.encodeByte(isInParty);
/*
                var encodeInventoryInfo = true;
                outPacket.encodeByte(encodeInventoryInfo);
                if (encodeInventoryInfo) {
                    var inventoryItems = commerce.getInventory().getItemIds();
                    outPacket.encodeInt(inventoryItems.size());
                    for (var item : inventoryItems) {
                        outPacket.encodeInt(item);
                        outPacket.encodeInt(7);  // maybe price or someshit
                        outPacket.encodeFT(FileTime.currentTime());
                    }
                }*/
                break;
            case UpdateVesselInfo:
            case UpdateVesselInfo_2:
                var encodeVesselInfo = true;
                outPacket.encodeByte(encodeVesselInfo);
                if (encodeVesselInfo) {
                    outPacket.encode(vessel);
                }
                break;
            case UnlockVoyageRoute:
                // the v265 handler on this opcode reads 10 more bytes when this byte is nonzero and
                // does not look like a voyage handler; 0 makes it stop here
                outPacket.encodeByte(0);
                outPacket.encodeByte(routeId);
                break;
        }

        return outPacket;
    }

    // Used to unlock Ayame skills
    public static OutPacket setRolePlayingCharacterInfo(RolePlayingType type, boolean transferField) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_ROLE_PLAYING_CHARACTER_INFO);

        outPacket.encodeInt(type.getVal());
        outPacket.encodeByte(transferField);

        return outPacket;
    }

    public static OutPacket blackList(BlackListTabType tab, BlackListResult result, BlackListEntry entry, List<BlackListEntry> entries) {
        OutPacket outPacket = new OutPacket(OutHeader.BLACK_LIST);

        outPacket.encodeByte(tab.ordinal()); // Individual = 0 | Guild = 1
        outPacket.encodeByte(result.getVal()); // BlackListResult Type

        switch (result) {
            case BlackListInsertRequestDone: // BlackListInsertRequestDone
            case BlackListDeleteRequestDone: // BlackListDeleteRequestDone
                outPacket.encodeString(entry.getTargetName()); // Target IGN
                if (tab.equals(BlackListTabType.Individual)) {
                    outPacket.encodeString(entry.getNickName()); // Target Nickname (?)
                    outPacket.encodeInt(entry.getChrId()); // Target Chr Id
                    outPacket.encodeInt(0); // ? Target Account Id (??)
                    outPacket.encodeString(""); // unknown
                } else if (tab.equals(BlackListTabType.Guild)) {
                    outPacket.encodeInt(entry.getGuildId()); // Guild Id
                }
                break;
            case EncodeBlackListView:
                if (tab.equals(BlackListTabType.Individual)) {
                    outPacket.encodeShort(entries.size());

                    for (var e : entries) {
                        outPacket.encodeString(e.getTargetName());
                        outPacket.encodeString(e.getNickName());
                        outPacket.encodeInt(e.getChrId());
                        outPacket.encodeInt(0); // ? Target Account Id (??)
                        outPacket.encodeString(""); // unknown
                    }
                } else if (tab.equals(BlackListTabType.Guild)) {
                    // TODO  Packet Structure
                }
                break;
            case FailedRequest: // Failed to make the request. Please try again later.
                break;
            case CharacterNotInList: // This character isn't on the list yet.
                break;
            case CannotAddAdminCharacter: // You cannot add the Administrator character as your friend.
                break;
            case IsAlreadyRegistered: // {0} already has their {1} registered.
                outPacket.encodeString(entry.getTargetName());
                outPacket.encodeString(entry.getNickName());
                break;
            case IsAlreadyBeingUsed: // {1} is already being used.
                outPacket.encodeString(entry.getTargetName());
                outPacket.encodeString(entry.getNickName());
                break;
            case IsInFriendList: // {0} is already on your friends list. Remove them and try again.
            case IsInFriendList2: // {0} is already on your friends list. Remove them and try again.
                outPacket.encodeString(entry.getTargetName()); // Target IGN
                outPacket.encodeString(entry.getNickName()); // Target Nickname (?)
                outPacket.encodeInt(entry.getChrId()); // Target Chr Id
                outPacket.encodeInt(0); // ? Target Account Id (??)
                break;
        }

        return outPacket;
    }

    public static OutPacket fieldSetVariable(String key, String value) {
        OutPacket outPacket = new OutPacket(OutHeader.FIELD_SET_VARIABLE);

        outPacket.encodeString(key);
        outPacket.encodeString(value);

        return outPacket;
    }

    public static OutPacket cashShopPreviewInfo(Map<Integer, Collection<Integer>> items) {
        OutPacket outPacket = new OutPacket(OutHeader.CASH_SHOP_PREVIEW_INFO);

        outPacket.encodeByte(0); // v265: first section (byte n, n x {1, [4, 1 n, ...]}), none
        // v271 (0x1459dd310): per box 4 item, 1, 2 n {4 male}, 2 n {4 female}; v265 had a leading 1 and (4 4) pairs
        outPacket.encodeByte(items.size());
        items.forEach((cashItem, itemList) -> {
            outPacket.encodeInt(cashItem);
            outPacket.encodeByte(0); // ?

            outPacket.encodeShort(itemList.size());
            for (var item : itemList) {
                outPacket.encodeInt(item);
            }
            outPacket.encodeShort(itemList.size());
            for (var item : itemList) {
                outPacket.encodeInt(item);
            }
        });

        // Same as ^, but duplicated?
        var size = 0;
        outPacket.encodeByte(size);
        for (int i = 0; i < size; i++) {
            outPacket.encodeByte(true); // bDecode
            outPacket.encodeInt(0);

            var size2 = 0;
            outPacket.encodeShort(size2);
            for (int k = 0; k < size2; k++) {
                outPacket.encodeByte(true);

                var size3 = 0;
                outPacket.encodeShort(size3);
                for (int j = 0; j < size3; j++) {
                    outPacket.encodeInt(0);
                    outPacket.encodeInt(0);
                }
            }
        }

        size = 0;
        outPacket.encodeInt(size);
        for (int i = 0; i < size; i++) {
            outPacket.encodeInt(0); // nItemId
            outPacket.encodeInt(0);
            outPacket.encodeLong(0);
            outPacket.encodeLong(0);

            var size2 = 0;
            outPacket.encodeInt(size2);
            for (int j = 0; j < size2; j++) {
                outPacket.encodeByte(0);
                outPacket.encodeInt(0);

                var size3 = 0;
                outPacket.encodeInt(size3);
                for (int k = 0; k < size3; k++) {
                    outPacket.encodeInt(0);
                }
            }
        }

        return outPacket;
    }

    public static OutPacket selectSoulCollectionResult(SoulCollectionEntry entry) {
        return selectSoulCollectionResult(entry, 0);
    }

    /**
     * v271 (GMS): 1, int n, n x (int soul, int flag), int m, m x int soul item registered.
     */
    public static OutPacket selectSoulCollectionResult(SoulCollectionEntry entry, int soulItemId) {
        OutPacket outPacket = new OutPacket(OutHeader.CHANGE_SOUL_COLLECTION_RESULT);

        outPacket.encodeByte(1);
        outPacket.encodeInt(1);
        outPacket.encode(entry);
        outPacket.encodeInt(soulItemId > 0 ? 1 : 0);
        if (soulItemId > 0) {
            outPacket.encodeInt(soulItemId);
        }

        return outPacket;
    }

    public static OutPacket towerResultUIOpen(int floor, int timeSeconds, int ozPoints, int exp, int itemId, int quantity) {
        OutPacket outPacket = new OutPacket(OutHeader.TOWER_RESULT_UI_OPEN);

        outPacket.encodeInt(floor);
        outPacket.encodeInt(timeSeconds);
        outPacket.encodeInt(ozPoints);
        outPacket.encodeInt(exp);
        outPacket.encodeInt(itemId);
        outPacket.encodeInt(quantity);

        return outPacket;
    }

    public static OutPacket topTowerRankResult(List<RankingResult> rankingResults) {
        OutPacket outPacket = new OutPacket(OutHeader.TOP_TOWER_RANK_RESULT);

        outPacket.encodeInt(rankingResults.size());
        for (var ranking : rankingResults) {
            outPacket.encodeInt(0); // chrId
            outPacket.encodeInt(0);
            outPacket.encodeString(ranking.getChrNames().get(0), 13);
            outPacket.encodeInt((int) ranking.getAmount());
            outPacket.encodeLong(ranking.getRankingTime());
            outPacket.encodeLong(0);
        }

        return outPacket;
    }

    public static OutPacket friendTowerRankResult(List<RankingResult> rankingResults) {
        OutPacket outPacket = new OutPacket(OutHeader.FRIEND_TOWER_RANK_RESULT);

        outPacket.encodeInt(rankingResults.size());
        for (var ranking : rankingResults) {
            outPacket.encodeInt(0); // chrId
            outPacket.encodeInt(0);
            outPacket.encodeString(ranking.getChrNames().get(0), 13);
            outPacket.encodeInt((int) ranking.getAmount());
            outPacket.encodeLong(ranking.getRankingTime());
            outPacket.encodeLong(0);
        }

        return outPacket;
    }

    public static OutPacket partyQuestRankingResult(List<RankingResult> rankingResults, boolean needsTime) {
        OutPacket outPacket = new OutPacket(OutHeader.PARTY_QUEST_RANKING_RESULT);

        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(needsTime ? 0 : -1); // type  0,1,2  0=time, 1=monsters, 2=items, -1=block, -2=brown crown, -3=silver crown, -4=gold crown

        outPacket.encodeInt(rankingResults.size());
        for (var rankingResult : rankingResults) {
            outPacket.encodeInt(rankingResult.getRankingTime()); // time
            outPacket.encodeInt(rankingResult.getChrNames().size());
            for (var chrName : rankingResult.getChrNames()) {
                outPacket.encodeString(chrName);
            }
        }

        return outPacket;
    }

    public static OutPacket deathPenalty(boolean firstSet, int totalTime, int remainTime, int expR, int dropR) {
        OutPacket outPacket = new OutPacket(OutHeader.DEATH_PENALTY);

        outPacket.encodeShort(firstSet ? 0 : 1); // 0 set, 1 update on map change
        outPacket.encodeInt(totalTime);
        outPacket.encodeInt(remainTime);
        outPacket.encodeInt(expR);
        outPacket.encodeInt(dropR);

        return outPacket;
    }

    /** Stat window action: type 2 opens it with the named tutorial bubble (GMS "apInstantTutorial"). */
    public static OutPacket statWindowGuide(int type, String name) {
        OutPacket outPacket = new OutPacket(OutHeader.STAT_WINDOW_GUIDE);

        outPacket.encodeInt(type);
        outPacket.encodeString(name);

        return outPacket;
    }

    /** Penalty removed (GMS sends 2, 1 when a Safety Charm clears it). */
    public static OutPacket deathPenaltyCleared() {
        OutPacket outPacket = new OutPacket(OutHeader.DEATH_PENALTY);

        outPacket.encodeShort(2);
        outPacket.encodeInt(1);

        return outPacket;
    }

    public static OutPacket hyperStatSkillUpdateResult(HyperStatsManager hsm, boolean encodeSkills) {
        OutPacket outPacket = new OutPacket(OutHeader.HYPER_STAT_SKILL_UPDATE_RESULT);

        outPacket.encodeByte(hsm.getCurrentPreset());
        outPacket.encodeByte(encodeSkills);

        if (encodeSkills) {
            hsm.encode(outPacket);
        }

        return outPacket;
    }

    public static OutPacket setUserCustomTitle(int lookTitle, int statTitle, String name, FileTime expire) {
        var outPacket = new OutPacket(OutHeader.SET_USER_CUSTOM_TITLE);

        outPacket.encodeInt(lookTitle);
        outPacket.encodeInt(statTitle);
        outPacket.encodeString(name);
        outPacket.encodeFT(expire);
        outPacket.encodeInt(0);

        return outPacket;
    }

    /**
     * Custom title refused: the current record, then 1 and a reason (1 can't be used, 2 frame only, 3 over 20
     * characters, 4 bad text, 5 not set up, 6 a source title, 7 expired).
     */
    public static OutPacket setUserCustomTitleFail(Char chr, int reason) {
        var outPacket = new OutPacket(OutHeader.SET_USER_CUSTOM_TITLE);

        CustomTitleModule.encodeCustomTitle(chr, outPacket);
        outPacket.encodeInt(1);
        outPacket.encodeInt(reason);
        if (reason == 6) {
            outPacket.encodeInt(0); // v271: the message formats this int in
        }

        return outPacket;
    }

    public static OutPacket updateUserCustomTitle(int lookTitle, int statTitle, String name, FileTime expire) {
        var outPacket = new OutPacket(OutHeader.UPDATE_USER_CUSTOM_TITLE);

        outPacket.encodeInt(lookTitle);
        outPacket.encodeInt(statTitle);
        outPacket.encodeString(name);
        outPacket.encodeFT(expire);

        return outPacket;
    }

    public static OutPacket rewardMobListResult(short groupCount, List<List<Integer>> groups) {
        var outPacket = new OutPacket(OutHeader.REWARD_MOB_LIST_RESULT);

        outPacket.encodeShort(groupCount);
        for (List<Integer> groupList : groups) {
            outPacket.encodeShort((short) groupList.size());
            groupList.forEach(val -> outPacket.encodeInt(val));
        }

        return outPacket;
    }
    // Contributed by Arixal (from GitLab).
    /** The server's answer to a step of a chained skill key: the client holds the next step until it comes. */
    public static OutPacket skillChainResult(int skillId, boolean ok) {
        var outPacket = new OutPacket(OutHeader.SKILL_CHAIN_RESULT);

        outPacket.encodeInt(0); // type: a step's result
        outPacket.encodeByte(ok);
        outPacket.encodeInt(skillId);

        return outPacket;
    }

    /** A skill's stack count (T STACK_SKILL_REQUEST_RESULT); -2 is the V-skill stack shown by StackOverTimeSkill. */
    public static OutPacket stackSkillRequestResult(int skillId, int stack) {
        var outPacket = new OutPacket(OutHeader.STACK_SKILL_REQUEST_RESULT);

        outPacket.encodeInt(skillId);
        outPacket.encodeByte(stack);
        outPacket.encodeInt(-1);

        return outPacket;
    }

    // EXTRA_SYSTEM_RESULT: int key (wire B3 24 7D 3F; the client drops the packet otherwise), short type, body
    private static final int EXTRA_SYSTEM_KEY = 0x3f7d24b3;

    private static int extraSystemCounter;

    /**
     * Type 48, a gauge slot (Mo Xuan's Tian/Di/Divine; other classes use it too): byte slot, int value, int duration
     * ms, int on, then 1 + short reply opcode + 4 bytes the client sends back on that opcode (In 1919) with an int.
     * GMS: value 110/111 or 210/211, 3000 ms, on 1 while it runs; a cleared slot has value 0, a tick, on 0.
     */
    public static OutPacket extraSystemGauge(int slot, int value, int duration, boolean on) {
        var outPacket = new OutPacket(OutHeader.EXTRA_SYSTEM_RESULT);

        outPacket.encodeInt(EXTRA_SYSTEM_KEY);
        outPacket.encodeShort(48);
        outPacket.encodeByte(slot);
        outPacket.encodeInt(value);
        outPacket.encodeInt(duration);
        outPacket.encodeInt(on ? 1 : 0);
        outPacket.encodeByte(1);
        outPacket.encodeShort(InHeader.EXTRA_SYSTEM_ACK.getValue());
        outPacket.encodeByte(slot);
        outPacket.encodeByte(0);
        outPacket.encodeByte(0);
        outPacket.encodeByte(++extraSystemCounter);

        return outPacket;
    }

    /** Mo Xuan's gauge in combat: the kind of power (slot) and its stage, as GMS (type 48, 3000 ms). */
    public static OutPacket moXuanGauge(int powerType, int godPower) {
        return extraSystemGauge(powerType, godPower, 3000, true);
    }

    /** Clears one of Mo Xuan's three gauge slots (type 0-2). */
    public static OutPacket moXuanGaugeClear(int type) {
        return extraSystemGauge(type, 0, (int) System.currentTimeMillis(), false);
    }

    /** Type 34 with 0: GMS sends it right before SET_FIELD. */
    public static OutPacket extraSystemInit() {
        var outPacket = new OutPacket(OutHeader.EXTRA_SYSTEM_RESULT);

        outPacket.encodeInt(EXTRA_SYSTEM_KEY);
        outPacket.encodeShort(34);
        outPacket.encodeInt(0);

        return outPacket;
    }

    /** SKILL_RESET_ITEM_RESULT (reader 0x1454a63e0: 1 success, 4 char, 1): "The SP Reset Scroll has reset your SP!" */
    public static OutPacket skillResetItemResult(int characterID) {
        OutPacket outPacket = new OutPacket(OutHeader.SKILL_RESET_ITEM_RESULT);
        outPacket.encodeByte(1);
        outPacket.encodeInt(characterID);
        outPacket.encodeByte(1);
        return outPacket;
    }

    public static OutPacket abilityResetItemResult(int characterID) {
        OutPacket outPacket = new OutPacket(OutHeader.ABILITY_RESET_ITEM_RESULT);
        outPacket.encodeByte(1);
        outPacket.encodeInt(characterID);
        outPacket.encodeByte(1);
        return outPacket;
    }

    public static OutPacket receiveToadsHammerRequestResult(int type, Item fromItem, Item toItem) {
        OutPacket outPacket = new OutPacket(OutHeader.RECEIVE_TOADS_HAMMER_REQUEST_RESULT);
        outPacket.encodeShort(type);
        if (type == 1) {
            fromItem.encode(outPacket);
            outPacket.encodeShort(toItem.getBagIndex());
        } else {
            toItem.encode(outPacket);
        }
        return outPacket;
    }
}
