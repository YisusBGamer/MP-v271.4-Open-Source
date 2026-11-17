package net.swordie.ms.client.guild.result;

import java.util.Arrays;

/**
 * GUILD_RESULT types (int). Values with a layout note are read by the client's GUILD_RESULT handler as written in
 * GuildResult; the rest are plain message cases placed by the old type order. A type the client has no case for
 * shows its generic guild error, so unsure errors use Res_Failed.
 */
public enum GuildType {
    Res_InputGuildName(0), // opens the name dialog; the client answers with Req_CheckGuildName
    Res_LoadGuild_Done(1), // int, int, int charId, int guildId, [byte 0, guild]
    Res_FindGuild_Done(3), // guild, 4 ints
    Res_CheckGuildName_Available(4), // str name: the "create this guild?" yes/no
    Res_CheckGuildName_AlreadyUsed(5), // reopens the name dialog
    Res_Failed(6), // no client case: generic guild error
    Res_CreateNewGuild_Done(7), // as Res_LoadGuild_Done, plus the "created" message
    Res_JoinGuild_DoneSelf(13), // as Res_LoadGuild_Done, plus the "joined" message
    Res_JoinGuild_Done(14), // int guildId, int charId, member (others; the joiner gets Res_JoinGuild_DoneSelf)
    Res_JoinGuild_AlreadyJoined(15),
    Res_JoinGuild_AlreadyFull(17),
    Res_JoinGuild_LimitRequest(18),
    Res_JoinGuild_NotAcceptRequest(19),
    Res_JoinGuild_UnknownUser(20),
    Res_JoinGuild_Unknown(22),

    Res_JoinRequest_Done(24), // int guildId, int charId, requestor
    Res_JoinRequest_AlreadyFull(28),
    Res_JoinRequest_LimitTime(29),
    Res_JoinRequest_AlreadyMade(30),
    Res_JoinRequest_Unknown(31),
    Res_JoinCancelRequest_Done(32), // int guildId, int charId

    Res_WithdrawGuild_Done(33), // int guildId, int charId, str name
    Res_WithdrawGuild_Done2(34), // same layout
    Res_WithdrawGuild_NotJoined(35),
    Res_WithdrawGuild_Unknown(36),

    Res_KickGuild_Done(37), // int guildId, int charId, str name
    Res_KickGuild_NotJoined(38),
    Res_KickGuild_Unknown(39),

    Res_RemoveGuild_Done(40), // int guildId
    Res_RemoveGuild_Unknown(42),
    Res_RemoveRequestGuild_Done(43),

    Res_InviteGuild_BlockedUser(44), // str name
    Res_InviteGuild_BlockedRequests(45), // str name
    Res_InviteGuild_Rejected(46), // str name
    Res_InviteGuild_DoneUser(47), // str name
    Res_InviteGuild_Done(48), // the invitation: int guildId, str guild, int charId, str name, int level, job, subJob, int
    Res_InviteGuild_Unknown(49),
    Res_InviteGuild_NotExist(50),
    Res_InviteGuild_NoGuild(51),
    Res_InviteGuild_AlreadyInGuild(52),
    Res_InviteGuild_LimitMembers(53),
    Res_AdminCannotCreate(54),

    Res_IncMaxMemberNum_Done(56), // int guildId, byte max
    Res_IncMaxMemberNum_Unknown(57),

    Res_ChangeMemberName(58), // int guildId, int charId, str name
    Res_ChangeRequestUserName(59), // int guildId, int charId, str name
    Res_ChangeLevelOrJob(60), // int guildId, int charId, byte isLevel, int value
    Res_NotifyLoginOrLogout(61), // int guildId, int charId, byte online, [FT], byte 0, byte showBox
    Res_SetEveryoneMemberGrade_Done(63), // int guildId, byte grade, int count, ints

    Res_SetGradeNameAndPermission_Done(68), // int guildId, int charId, 10 x (int permission, str name)
    Res_SetGradeNameAndPermission_Unknown(69),
    Res_AddGrade_Done(70), // same layout
    Res_AddGrade_Unknown(71),
    Res_DeleteGrade_Done(72), // same layout, then str deleted name
    Res_DeleteGrade_Unknown(73),
    Res_SetMemberGrade_Done(74), // int guildId, int charId, byte grade
    Res_SetMemberGrade_Unknown(75),
    Res_SetMemberCommitment_Done(76), // int guildId, int charId, int, int, FT
    Res_SetMark_Done(78), // int guildId, int charId, mark, byte custom type, int len, bytes, int
    Res_SetMark_Unknown(79),
    Res_SetCustomMark_Unknown(87),
    Res_SetNotice_Done(88), // int guildId, int charId, str notice
    Res_SetNotice_Unkown(89),
    Res_SetAdmission_Done(90), // int guildId, int charId, byte, int, int, int
    Res_SetAdmission_Unknown(91),
    Res_Advertise_Unknown(93),
    Res_Advertise_NotEnoughGP(94),

    Res_IncPoint_Done(98), // int guildId, int, int, int
    Res_SetGGP_Done(100), // int guildId, int
    Res_ResetSkill_Done(105), // int guildId, int
    Res_SetSkill_Done(106), // int guildId, int skillId, int charId, skill
    Res_SetSkill_LevelSet_Unknown(107), // byte
    Res_SetSkill_ResetBattleSkill_Done(108), // int guildId, int
    Res_SetSkill_ResetBattleSkill_Unknown(6),
    Res_SetSkill_ResetBattleSkill_NotEnoughGP(111), // "Not enough GP." with the load's reset cost; no body
    Res_SetSkill_ResetBattleSkill_Unknown1(112), // "All of the Guild Skills have already been reset."
    Res_UseSkill_Success(115),
    Res_UseSkill_Err(116), // int reason (not 0)
    Res_ChangeMaster_Done(118), // int guildId, int charId, int newCharId, byte, byte 0
    Res_ChangeMaster_Unknown(133), // int reason (0: "could not be changed due to an unknown error"); 119 has no case
    Res_BlockedBehaviorCreate(121),
    Res_BlockedBehaviorJoin(122),
    Res_BattleSkillOpen(128), // int
    Res_SetMemberCheckIn(126), // int guildId, int charId, int date (yyyymmdd): the check-in window counts today's
    Res_CheckInRewards(129), // int n, n x (int members, int Honor EXP): the check-in window's reward steps
    Res_CheckIn_Failed(130), // int reason: 0/3/6 unknown, 1 already checked in, 2 not a member, 4 bonus Honor EXP, 5 incapacitated
    Res_CustomMark(136), // int world (-1: any), int guildId, byte type, int len + bytes, int version: emblem cache
    Res_Rank_Refresh(6), // no v265 case found
    // v265 alliance events (client 0x14551f110); bodies are written by GuildResult.allianceEvent
    Res_Alliance_Created(138), // alliance data
    Res_Alliance_Withdraw(139), // int alliance, int guild, byte kicked
    Res_Alliance_Joined(141), // int alliance, int guild, alliance data
    Res_Alliance_InviteBlocked(143), // str guild
    Res_Alliance_AlreadyInvited(144), // str guild
    Res_Alliance_InviteRejected(145), // str guild
    Res_Alliance_ChangeMaster(146), // int alliance, int old master, int new master
    Res_Alliance_SetGradeName(147), // int alliance, int, 5 x str
    Res_Alliance_SetGrade(148), // int guild, int char, int alliance, byte grade
    Res_Alliance_Capacity(150), // int alliance, int max guilds
    Res_Alliance_Destroy(151), // int alliance
    Res_Alliance_SetNotice(152), // int alliance, str
    Res_Alliance_Rename(168), // int alliance, str, str
    ;

    private final int val;

    GuildType(int val) {
        this.val = val;
    }

    /**
     * The v271 number of a v265 type (client 0x145b260b0, cases aligned by reads and StringPool ids with v265
     * 0x14551f110): 0-39 same, 40-48 +4, 49-54 by message, 56-63 +4, 64-104 +10, 106-109 +10/+11, 111-120 +12,
     * 121-164 +13, the rename block 168+ at 249+. v265 105 (skill reset) has no case: the reset sends v271 118
     * (the guild's skill list) instead.
     */
    public static int v271(int v) {
        if (v <= 39) return v;
        if (v <= 48) return v + 4;
        switch (v) {
            case 49, 53: return 53; // no v271 case: the client's generic error
            case 50: return 54;
            case 51: return 55;
            case 52: return 56;
            case 54: return 57;
            case 55: return 59;
            case 105: return 118;
            case 106: return 116;
            case 107: return 117;
            case 108: return 119;
            case 109: return 120;
            case 110: return 121;
        }
        if (v <= 63) return v + 4;
        if (v <= 104) return v + 10;
        if (v <= 120) return v + 12;
        if (v <= 167) return v + 13;
        return v + 81;
    }

    /** The v265 type of a v271 number the client sends back (In GUILD_RESULT). */
    public static int fromV271(int v271) {
        for (int v = 0; v < 200; v++) {
            if (v271(v) == v271 && v != 49) {
                return v;
            }
        }
        return -1;
    }

    public static GuildType getTypeByVal(int val) {
        return Arrays.stream(values()).filter(grt -> grt.getVal() == val).findAny().orElse(null);
    }

    public int getVal() {
        return val;
    }
}
