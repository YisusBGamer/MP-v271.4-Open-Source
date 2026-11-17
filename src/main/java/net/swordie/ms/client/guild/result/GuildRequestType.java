package net.swordie.ms.client.guild.result;

import java.util.Arrays;

/**
 * GUILD_REQUEST types (int), from the client's senders. Layouts are what the client writes after the type.
 */
public enum GuildRequestType {
    CheckGuildName(1), // str name (after Res_InputGuildName)
    CreateGuildAgree(2), // yes on Res_CheckGuildName_Available
    JoinRequest(3), // int guildId, str introduction, int, int, byte, byte
    JoinCancelRequest(4), // int guildId
    AcceptRequestors(6), // byte count, int charIds
    RejectRequestors(7), // byte count, int charIds
    Withdraw(8), // int charId, str name
    Kick(9), // int charId (after a yes/no)
    Remove(10), // int charId, int (master only)
    SetMemberGrade(12), // int charId, byte grade
    SetGradeNameAndPermission(14), // byte grade, str name, int permission, int
    AddGrade(15), // byte grade, str name, int permission
    DeleteGrade(16), // byte grade
    SetEveryoneMemberGrade(17), // byte grade, int count, int charIds
    SetMark(18), // byte 0, short bg, byte bgColor, short mark, byte markColor | byte 1, byte type, int len, bytes
    SetNotice(19), // str
    SetAdmission(20), // byte, int, int, int
    ClaimMaster(21), // no body: take over from a master away too long (yes/no once the guild data's claim time passed)
    ResetSkills(31), // no body; costs the GP sent in the guild load (0x14551bc10)
    ResetNoblesseSkills(32), // no body; the client only sends it when the load's Noblesse cost is >= 1 (0x14551bf10)
    ChangeMaster(33), // int new master's charId ("Would you like to assign %s", 0x142b02bb0 -> 0x142e7a220)
    JoinGuild(34), // int guildId (accepting an invitation; also sent from guild lists)
    LoadGuild(35),
    Invite(36), // str name
    CheckIn(37),
    SkillLevelSetUp(41), // int skillId, byte up
    InputGuildName(43), // no on Res_CheckGuildName_Available: ask for another name
    ShowGuild(44), // int guildId (answered with Res_FindGuild_Done)
    UseActiveSkill(45), // int skillId, byte, int target
    BoardAlarm(39), // no body: after a guild board post with "Alarm" ticked (0x142af6e50)
    NoticeRead(40), // no body: the notice opened while the member's unread-notice time was set (0x14539dee0)
    CustomMarkRequest(46), // int guildId, int: a user's custom emblem is not in the client's cache (0x14539e740)
    Help(48), // no body: the guild window's Help button, 1 s throttle (0x14539e7e0)
    Search(49), // byte type, byte mode, str, byte exact, byte has + filter(1 4 8 1 1 4), byte, byte has + filter
    AllianceWithdraw(51), // no body (alliance window "leave", 0x142abe670)
    AllianceInviteGuild(52), // int guildId (0x142aff770)
    AllianceAcceptInvite(53), // int allianceId, int own guildId (alarm 0x1b accept, 0x142e4a9f0)
    AllianceKickGuild(55), // int guildId, int allianceId (0x142b02bb0, "Do you wish to remove [%s] Guild")
    AllianceChangeMaster(56), // int guildId, int charId (0x142b02bb0, "Would you like to assign %s")
    AllianceSetGradeName(57), // 5 x str (0x142b1c710)
    AllianceSetMemberGrade(58), // int charId, byte (1 promote, 0 demote), int guildId (0x142e7a2b0 -> 0x142f39250)
    RequestorsWindow(79), // no body: member page "NewComer" button, the requests window for admins (0x142ac1260)
    Rename(94), // str current name, str new name (rename window, 0x144381750)
    RenameClose(96), // no body (rename window closed, 0x144381a90)
    ;

    private final int val;

    GuildRequestType(int val) {
        this.val = val;
    }

    /**
     * v265 type of a v271 GUILD_REQUEST type (In 502; v271 senders paired with the v265 ones by address order and
     * write sequence): 1-31 same, v265 32 (Noblesse reset) gone, 33-37 -1 (v271 36 = check-in, no body: the client's
     * automatic daily check-in 0x1459a6100), board alarm and 40-46 -1, 48-58 -2, 79 same, 93-96 +5. v271 76
     * (0x1418d8e70, a timer / web-reply sender, not the check-in) is not answered.
     */
    public static int fromV271(int t) {
        if (t <= 31) return t;
        if (t >= 32 && t <= 36) return t + 1;
        if (t >= 38 && t <= 45) return t + 1;
        if (t >= 46 && t <= 56) return t + 2;
        if (t == 79) return t;
        if (t >= 98 && t <= 101) return t - 5;
        return -1;
    }

    public static GuildRequestType getByVal(int val) {
        return Arrays.stream(values()).filter(t -> t.val == val).findAny().orElse(null);
    }

    public int getVal() {
        return val;
    }
}
