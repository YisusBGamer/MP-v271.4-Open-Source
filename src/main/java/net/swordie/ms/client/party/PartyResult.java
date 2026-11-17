package net.swordie.ms.client.party;

import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.Encodable;
import net.swordie.ms.connection.OutPacket;

/**
 * Created on 3/19/2018.
 */
public class PartyResult implements Encodable {

    private PartyType type;
    private Party party;
    private PartyMember member;
    private Char chr;
    private int arg1;
    private boolean bool, bool2;
    private String str;

    public PartyResult(PartyType type) {
        this.type = type;
    }

    /**
     * v265 result type -> v271 (0x142dc6fd0 cases aligned by reads and string ids): 0-8 equal, then +1 (load 29 -> 30,
     * create 30 -> 31), +2 from 33 (withdraw 35, join 37, full 40), +3 from 43 (invite result 47, boss 61, member
     * update 69, settings 72, Party Finder 75-85).
     */
    public static int v271Type(int t) {
        if (t <= 8) {
            return t;
        }
        if (t <= 31) {
            return t + 1;
        }
        if (t <= 42) {
            return t + 2;
        }
        return t + 3;
    }

    @Override
    public void encode(OutPacket outPacket) {
        // v265 layouts and type values from the reference server (29 load, 30 create)
        switch (type) {
            case PartyRes_UserMigration:
            case PartyRes_UpdateShutdownStatus:
                if (party != null) {
                    outPacket.encodeByte(v271Type(PartyType.PartyRes_LoadParty_Done.getVal()));
                    outPacket.encodeByte(true);
                    party.encode(outPacket, chr);
                    return;
                }
                // no party at hand: a member data update (channel)
                outPacket.encodeByte(v271Type(PartyType.PartyRes_UpdateDataMember.getVal()));
                outPacket.encodeByte(1);
                outPacket.encodeInt(chr.getId());
                outPacket.encodeInt(3);
                outPacket.encodeInt(chr.isOnline() && chr.getClient() != null ? chr.getClient().getChannel() - 1 : -2);
                return;
            case PartyRes_ChangeLevelOrJob:
                outPacket.encodeByte(v271Type(PartyType.PartyRes_UpdateDataMember.getVal()));
                outPacket.encodeByte(1);
                outPacket.encodeInt(chr.getId());
                outPacket.encodeInt(2); // level
                outPacket.encodeInt(chr.getLevel());
                return;
            default:
                break;
        }
        outPacket.encodeByte(v271Type(type.getVal()));
        switch (type) {
            case PartyReq_InviteIntrusion:
                outPacket.encodeInt(party.getId());
                outPacket.encodeInt(party.getPartyLeaderID());
                outPacket.encodeByte(0); // v271: new byte before the name (types 4/5)
                outPacket.encodeString(chr.getName());
                outPacket.encodeInt(chr.getLevel());
                outPacket.encodeInt(chr.getJob());
                outPacket.encodeInt(chr.getAvatarData().getCharacterStat().getSubJob());
                break;
            case PartyReq_ApplyParty:
                outPacket.encodeInt(party.getId());
                outPacket.encodeInt(party.getPartyLeaderID());
                outPacket.encodeString(member.getCharName());
                outPacket.encodeInt(member.getLevel());
                outPacket.encodeInt(member.getJob());
                outPacket.encodeInt(member.getSubSob());
                break;
            case PartyRes_LoadParty_Done:
                outPacket.encodeByte(party != null);
                if (party != null) {
                    party.encode(outPacket, chr);
                } else {
                    outPacket.encodeByte(0);
                }
                break;
            case PartyRes_CreateNewParty_Done:
                // v271 (0x140e41ea0): id, the v265 byte as 4 bytes, a 20-byte town portal (v265 16), leader,
                // name block, ad, leader id, new string
                outPacket.encodeInt(party.getId());
                outPacket.encodeInt(0);
                outPacket.encodeInt(GameConstants.NO_MAP_ID);
                outPacket.encodeInt(GameConstants.NO_MAP_ID);
                outPacket.encodeInt(0);
                outPacket.encodeInt(-1);
                outPacket.encodeInt(-1);
                party.encodeMember(outPacket, party.getPartyLeader());
                party.encodeName(outPacket);
                outPacket.encodeByte(party.isAppliable());
                outPacket.encodeByte(0); // v271 name block byte
                outPacket.encodeInt(1); // loot permission
                outPacket.encodeInt(-1);
                outPacket.encodeInt(party.getPartyLeaderID());
                outPacket.encodeString("");
                break;
            case PartyRes_WithdrawParty_Done:
                outPacket.encodeInt(member == null ? 0 : member.getCharID());
                outPacket.encodeByte(bool); // bPartyExists
                if (bool) {
                    outPacket.encodeByte(bool2); // bExpelled
                    outPacket.encodeString(member.getCharName());
                    party.encode(outPacket, chr);
                }
                break;
            case PartyRes_JoinParty_Done:
                outPacket.encodeString(str); // sJoinerName
                outPacket.encodeByte(0);
                outPacket.encodeInt(0);
                party.encode(outPacket, chr);
                break;
            case PartyRes_ChangePartyBoss_Done:
                outPacket.encodeInt(member.getCharID());
                outPacket.encodeByte(arg1); // nReason
                break;
            case PartyRes_PartySettingDone:
                party.encodeSettings(outPacket);
                break;
            case PartyRes_InviteResult:
                outPacket.encodeInt(arg1);
                outPacket.encodeString(str);
                break;
        }
    }

    /** The client's invite notices by reason: 0 sent, 1 blocking, 2 busy, 3 already invited, 4 denied,
     * 7 not found, 8 no party, 9 party full, 10 already in a party */
    public static PartyResult inviteResult(int reason, String name) {
        PartyResult pr = new PartyResult(PartyType.PartyRes_InviteResult);
        pr.arg1 = reason;
        pr.str = name;
        return pr;
    }

    public static final int INVITE_NOT_FOUND = 7;

    public static PartyResult createNewParty(Party party) {
        PartyResult pr = new PartyResult(PartyType.PartyRes_CreateNewParty_Done);
        pr.party = party;
        return pr;
    }

    public static PartyResult loadParty(Party party, Char receiver) {
        PartyResult pr = new PartyResult(PartyType.PartyRes_LoadParty_Done);
        pr.party = party;
        pr.chr = receiver;
        return pr;
    }

    public static PartyResult inviteIntrusion(Party party, Char chr) {
        PartyResult pr = new PartyResult(PartyType.PartyReq_InviteIntrusion);
        pr.party = party;
        pr.chr = chr;
        return pr;
    }

    public static PartyResult applyParty(Party party, PartyMember member) {
        PartyResult pr = new PartyResult(PartyType.PartyReq_ApplyParty);
        pr.party = party;
        pr.member = member;
        return pr;
    }

    public static PartyResult changePartyBoss(Party party, int reason) {
        PartyResult pr = new PartyResult(PartyType.PartyRes_ChangePartyBoss_Done);
        pr.party = party;
        pr.member = party.getPartyLeader();
        pr.arg1 = reason;
        return pr;
    }

    public static PartyResult withdrawParty(Party party, PartyMember kickedMember, boolean partyStillExists, boolean expelled, Char receiver) {
        PartyResult pr = new PartyResult(PartyType.PartyRes_WithdrawParty_Done);
        pr.party = party;
        pr.member = kickedMember;
        pr.bool = partyStillExists;
        pr.bool2 = expelled;
        pr.chr = receiver;
        return pr;
    }

    public static PartyResult joinParty(Party party, String joinerName) {
        PartyResult pr = new PartyResult(PartyType.PartyRes_JoinParty_Done);
        pr.party = party;
        pr.str = joinerName;
        return pr;
    }

    public static PartyResult updateShutdownStatus(Char chr) {
        PartyResult pr = new PartyResult(PartyType.PartyRes_UpdateShutdownStatus);
        pr.chr = chr;
        return pr;
    }

    public static PartyResult changeLevelOrJob(Char chr) {
        PartyResult pr = new PartyResult(PartyType.PartyRes_ChangeLevelOrJob);
        pr.chr = chr;
        return pr;
    }

    public static PartyResult msg(PartyType type) {
        return new PartyResult(type);
    }

    public static PartyResult userMigration(Party party, Char receiver) {
        PartyResult pr = new PartyResult(PartyType.PartyRes_UserMigration);
        pr.party = party;
        pr.chr = receiver;
        return pr;
    }

    public static PartyResult settingDone(Party party) {
        PartyResult pr = new PartyResult(PartyType.PartyRes_PartySettingDone);
        pr.party = party;
        return pr;
    }
}
