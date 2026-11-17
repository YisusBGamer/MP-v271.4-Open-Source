package net.swordie.ms.client.guild.result;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.guild.Guild;
import net.swordie.ms.client.guild.GuildMember;
import net.swordie.ms.client.guild.GuildRequestor;
import net.swordie.ms.client.guild.GuildSkill;
import net.swordie.ms.connection.OutPacket;

import static net.swordie.ms.client.guild.result.GuildType.*;

/**
 * Created on 3/21/2018.
 */
public class GuildResult {

    private final GuildType type;
    private GuildSubType subType;
    private Guild guild;
    private GuildMember member;
    private GuildMember member2;
    private boolean online;
    private boolean showBox;
    private Char chr;
    private int intArg;
    private String stringArg;
    private GuildSkill skill;
    private boolean boolArg;
    private int deletedGradeindex;
    private String deletedGradename;
    private GuildRequestor requestor;
    private java.util.function.Consumer<OutPacket> body;

    private GuildResult(GuildType type) {
        this.type = type;
    }

    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(GuildType.v271(type.getVal()));
        if (body != null) {
            body.accept(outPacket);
            return;
        }
        switch (type) {
            case Res_LoadGuild_Done, Res_CreateNewGuild_Done, Res_JoinGuild_DoneSelf -> {
                // GP the skill reset costs (v271 dropped the Noblesse reset cost)
                outPacket.encodeInt(net.swordie.ms.handlers.social.module.GuildModule.SKILL_RESET_GP);
                outPacket.encodeInt(intArg); // the receiver; other characters ignore it
                outPacket.encodeInt(guild == null ? 0 : guild.getId());
                if (guild != null) {
                    var alliance = guild.getAlliance();
                    outPacket.encodeByte(alliance != null); // 1: the alliance (header + its guilds) instead
                    if (alliance != null) {
                        alliance.encodeV265(outPacket);
                    } else {
                        outPacket.encode(guild);
                    }
                }
            }
            case Res_FindGuild_Done -> {
                outPacket.encode(guild);
                for (int i = 0; i < 4; i++) {
                    outPacket.encodeInt(0);
                }
            }
            case Res_CheckGuildName_Available -> outPacket.encodeString(stringArg);
            case Res_JoinGuild_Done -> {
                outPacket.encodeInt(guild.getId());
                outPacket.encodeInt(member.getCharID());
                outPacket.encode(member);
            }
            case Res_JoinRequest_Done -> {
                outPacket.encodeInt(guild.getId());
                outPacket.encodeInt(requestor.getCharID());
                requestor.encode(outPacket);
            }
            case Res_JoinCancelRequest_Done -> {
                outPacket.encodeInt(guild.getId());
                outPacket.encodeInt(intArg);
            }
            case Res_WithdrawGuild_Done, Res_WithdrawGuild_Done2, Res_KickGuild_Done -> {
                outPacket.encodeInt(guild.getId());
                outPacket.encodeInt(intArg);
                outPacket.encodeString(stringArg);
            }
            case Res_RemoveGuild_Done -> outPacket.encodeInt(guild.getId());
            case Res_InviteGuild_BlockedUser, Res_InviteGuild_BlockedRequests, Res_InviteGuild_Rejected,
                    Res_InviteGuild_DoneUser -> outPacket.encodeString(stringArg);
            case Res_InviteGuild_Done -> {
                outPacket.encodeInt(guild.getId());
                outPacket.encodeString(guild.getName());
                outPacket.encodeInt(chr.getId());
                outPacket.encodeString(chr.getName());
                outPacket.encodeInt(chr.getLevel());
                outPacket.encodeInt(chr.getJob());
                outPacket.encodeInt(chr.getSubJob());
                outPacket.encodeInt(chr.getId()); // checked against the invitee's block list
            }
            case Res_IncMaxMemberNum_Done -> {
                outPacket.encodeInt(guild.getId());
                outPacket.encodeByte(guild.getMaxMembers());
            }
            case Res_ChangeLevelOrJob -> {
                outPacket.encodeInt(guild.getId());
                outPacket.encodeInt(member.getCharID());
                outPacket.encodeByte(boolArg); // 1: level, 0: job
                outPacket.encodeInt(boolArg ? member.getLevel() : member.getJob());
            }
            case Res_NotifyLoginOrLogout -> {
                outPacket.encodeInt(guild.getId());
                outPacket.encodeInt(member.getCharID());
                outPacket.encodeByte(online);
                if (!online) {
                    outPacket.encodeFT(member.getLastOnline());
                }
                outPacket.encodeByte(0); // 1: two ints follow
                outPacket.encodeByte(showBox);
            }
            case Res_SetGradeNameAndPermission_Done, Res_AddGrade_Done, Res_DeleteGrade_Done -> {
                outPacket.encodeInt(guild.getId());
                outPacket.encodeInt(member.getCharID());
                var permissions = guild.getGradePermissions();
                var gradeNames = guild.getGradeNames();
                for (int i = 0; i < Guild.GRADE_SIZE; i++) {
                    outPacket.encodeInt(i < permissions.size() ? permissions.get(i) : 0);
                    outPacket.encodeString(i < gradeNames.size() ? gradeNames.get(i) : "");
                }
                if (type == Res_DeleteGrade_Done) {
                    outPacket.encodeString(deletedGradename);
                }
            }
            case Res_SetMemberGrade_Done -> {
                outPacket.encodeInt(guild.getId());
                outPacket.encodeInt(member.getCharID());
                outPacket.encodeByte(member.getGrade());
            }
            case Res_SetMemberCommitment_Done -> {
                outPacket.encodeInt(guild.getId());
                outPacket.encodeInt(member.getCharID());
                outPacket.encodeInt(member.getCommitment());
                outPacket.encodeInt(member.getDayCommitment());
                outPacket.encodeFT(member.getCommitmentIncTime());
            }
            case Res_SetMark_Done -> {
                outPacket.encodeInt(guild.getId());
                outPacket.encodeInt(member.getCharID());
                outPacket.encodeByte(guild.getCustomMark() != null);
                guild.encodeMark(outPacket);
            }
            case Res_SetNotice_Done -> {
                outPacket.encodeInt(guild.getId());
                outPacket.encodeInt(member.getCharID());
                outPacket.encodeString(guild.getNotice());
            }
            case Res_SetAdmission_Done -> {
                outPacket.encodeInt(guild.getId());
                outPacket.encodeInt(member.getCharID());
                outPacket.encodeByte(guild.isAppliable());
                outPacket.encodeInt(guild.getActiveTimes());
                outPacket.encodeInt(guild.getMainActivity());
                outPacket.encodeInt(guild.getAgeGroup());
            }
            case Res_IncPoint_Done -> {
                outPacket.encodeInt(guild.getId());
                outPacket.encodeInt(guild.getPoints());
                outPacket.encodeInt(guild.getLevel());
                outPacket.encodeInt(guild.getGgp());
            }
            case Res_SetGGP_Done -> {
                outPacket.encodeInt(guild.getId());
                outPacket.encodeInt(guild.getGgp());
            }
            case Res_SetSkill_Done -> {
                outPacket.encodeInt(guild.getId());
                outPacket.encodeInt(skill.getSkillID());
                outPacket.encodeInt(intArg); // nBuyCharacterID
                outPacket.encode(skill);
            }
            case Res_SetSkill_ResetBattleSkill_Done -> {
                outPacket.encodeInt(guild.getId());
                outPacket.encodeInt(guild.getBattleSp());
            }
            case Res_ResetSkill_Done -> {
                // v271 118: the guild's whole skill list (4 n, n x (4 skill, skill))
                outPacket.encodeInt(guild.getId());
                outPacket.encodeInt(guild.getSkills().size());
                guild.getSkills().forEach((skillId, sk) -> {
                    outPacket.encodeInt(skillId);
                    outPacket.encode(sk);
                });
            }
            case Res_SetSkill_LevelSet_Unknown -> outPacket.encodeByte(false);
            // msg(Res_UseSkill_Err) has no reason: Failed_Unknown (0 makes the client read more)
            case Res_UseSkill_Err ->
                    outPacket.encodeInt((subType == null ? GuildSubType.Failed_Unknown : subType).getVal());
            case Res_ChangeMaster_Done -> {
                outPacket.encodeInt(guild.getId());
                outPacket.encodeInt(member.getCharID());
                outPacket.encodeInt(member2.getCharID());
                outPacket.encodeByte(boolArg);
                outPacket.encodeByte(false); // true: one more int
            }
            case Res_BattleSkillOpen -> outPacket.encodeInt(guild.getBattleSp());
            case Res_CheckIn_Failed, Res_ChangeMaster_Unknown -> outPacket.encodeInt(intArg);
            default -> {
                // plain message cases read nothing
            }
        }
    }

    /** A member's check-in day, for every open check-in window. */
    public static GuildResult setMemberCheckIn(Guild guild, GuildMember member) {
        return allianceEvent(Res_SetMemberCheckIn, out -> {
            out.encodeInt(guild.getId());
            out.encodeInt(member.getCharID());
            out.encodeInt(member.getCheckInDate());
        });
    }

    public static GuildResult checkInRewards() {
        return allianceEvent(Res_CheckInRewards, out -> {
            out.encodeInt(Guild.CHECK_IN_BONUS.length);
            for (int[] step : Guild.CHECK_IN_BONUS) {
                out.encodeInt(step[0]);
                out.encodeInt(step[1]);
            }
        });
    }

    /** Another guild's custom emblem (answer to GUILD_REQUEST 46). */
    public static GuildResult customMark(Guild guild) {
        return allianceEvent(Res_CustomMark, out -> {
            var mark = guild.getCustomMark();
            out.encodeInt(-1);
            out.encodeInt(guild.getId());
            out.encodeByte(guild.getCustomMarkType());
            out.encodeInt(mark == null ? 0 : mark.length);
            if (mark != null) {
                out.encodeArr(mark);
            }
            out.encodeInt(guild.getCustomMarkVersion());
        });
    }

    /** A v265 alliance event (Res_Alliance_*) with its body. */
    public static GuildResult allianceEvent(GuildType type, java.util.function.Consumer<OutPacket> body) {
        GuildResult gri = new GuildResult(type);
        gri.body = body;
        return gri;
    }

    /** The guild window of {@code chr} (the client drops it unless the char id is its own). */
    public static GuildResult loadGuild(Guild guild, Char chr) {
        return loadGuild(Res_LoadGuild_Done, guild, chr);
    }

    public static GuildResult loadGuild(GuildType type, Guild guild, Char chr) {
        GuildResult gri = new GuildResult(type);
        gri.guild = guild;
        gri.intArg = chr.getId();
        return gri;
    }

    public static GuildResult setGradeNameAndPermission(Guild guild, GuildMember member) {
        GuildResult gri = new GuildResult(Res_SetGradeNameAndPermission_Done);
        gri.guild = guild;
        gri.member = member;
        return gri;
    }

    public static GuildResult addGrade(Guild guild, GuildMember member) {
        GuildResult gri = new GuildResult(Res_AddGrade_Done);
        gri.guild = guild;
        gri.member = member;
        return gri;
    }

    public static GuildResult deleteGrade(Guild guild, GuildMember member, int deleteIdx, String deletedGradename) {
        GuildResult gri = new GuildResult(Res_DeleteGrade_Done);
        gri.guild = guild;
        gri.member = member;
        gri.deletedGradeindex = deleteIdx;
        gri.deletedGradename = deletedGradename;
        return gri;
    }

    public static GuildResult createNewGuild(Guild guild, Char chr) {
        return loadGuild(Res_CreateNewGuild_Done, guild, chr);
    }

    /** The name dialog's "create a guild named ...?" question. */
    public static GuildResult checkNameAvailable(String name) {
        GuildResult gri = new GuildResult(Res_CheckGuildName_Available);
        gri.stringArg = name;
        return gri;
    }

    public static GuildResult joinRequest(Guild guild, GuildRequestor requestor) {
        GuildResult gri = new GuildResult(Res_JoinRequest_Done);
        gri.guild = guild;
        gri.requestor = requestor;
        return gri;
    }

    public static GuildResult joinCancelRequest(Guild guild, int charId) {
        GuildResult gri = new GuildResult(Res_JoinCancelRequest_Done);
        gri.guild = guild;
        gri.intArg = charId;
        return gri;
    }

    public static GuildResult joinGuild(Guild guild, GuildMember member) {
        GuildResult gri = new GuildResult(Res_JoinGuild_Done);
        gri.guild = guild;
        gri.member = member;
        return gri;
    }

    public static GuildResult leaveGuild(Guild guild, int leaverID, String leaverName, boolean expelled) {
        GuildResult gri = new GuildResult(expelled ? Res_KickGuild_Done : Res_WithdrawGuild_Done);
        gri.guild = guild;
        gri.intArg = leaverID;
        gri.stringArg = leaverName;
        return gri;
    }

    public static GuildResult setMark(Guild guild, GuildMember member) {
        GuildResult gri = new GuildResult(Res_SetMark_Done);
        gri.guild = guild;
        gri.member = member;
        return gri;
    }

    public static GuildResult setMemberGrade(Guild guild, GuildMember member) {
        GuildResult gri = new GuildResult(Res_SetMemberGrade_Done);
        gri.guild = guild;
        gri.member = member;
        return gri;
    }

    /** One field per packet: the level ({@code level}) or the job. */
    public static GuildResult changeLevelOrJob(Guild guild, GuildMember member, boolean level) {
        GuildResult gri = new GuildResult(Res_ChangeLevelOrJob);
        gri.guild = guild;
        gri.member = member;
        gri.boolArg = level;
        return gri;
    }

    public static GuildResult notifyLoginOrLogout(Guild guild, GuildMember member, boolean online, boolean showBox) {
        GuildResult gri = new GuildResult(Res_NotifyLoginOrLogout);
        gri.guild = guild;
        gri.member = member;
        gri.online = online;
        gri.showBox = showBox;
        return gri;
    }

    public static GuildResult msg(GuildType type) {
        return msg(type, null);
    }

    public static GuildResult msg(GuildType type, String str) {
        var gri = new GuildResult(type);
        gri.stringArg = str;
        return gri;
    }

    public static GuildResult setMemberCommitment(Guild guild, GuildMember member) {
        GuildResult gri = new GuildResult(Res_SetMemberCommitment_Done);
        gri.guild = guild;
        gri.member = member;
        return gri;
    }

    public static GuildResult setGgp(Guild guild) {
        GuildResult gri = new GuildResult(Res_SetGGP_Done);
        gri.guild = guild;
        return gri;
    }

    public static GuildResult setPointAndLevel(Guild guild) {
        GuildResult gr = new GuildResult(Res_IncPoint_Done);
        gr.guild = guild;
        return gr;
    }

    public static GuildResult setSkill(Guild guild, GuildSkill skill, int buyCharID) {
        GuildResult gr = new GuildResult(Res_SetSkill_Done);
        gr.guild = guild;
        gr.skill = skill;
        gr.intArg = buyCharID;
        return gr;
    }

    public static GuildResult resetBattleSkillDone(Guild guild) {
        GuildResult gr = new GuildResult(GuildType.Res_SetSkill_ResetBattleSkill_Done);
        gr.guild = guild;
        return gr;
    }

    public static GuildResult resetSkillDone(Guild guild) {
        GuildResult gr = new GuildResult(Res_ResetSkill_Done);
        gr.guild = guild;
        return gr;
    }

    public static GuildResult battleSkillOpen(Guild guild) {
        GuildResult gr = new GuildResult(Res_BattleSkillOpen);
        gr.guild = guild;
        return gr;
    }

    public static GuildResult setRank(Guild guild) {
        GuildResult gr = new GuildResult(Res_Rank_Refresh);
        gr.guild = guild;
        return gr;
    }

    public static GuildResult incMaxMemberNum(Guild guild) {
        GuildResult gr = new GuildResult(Res_IncMaxMemberNum_Done);
        gr.guild = guild;
        return gr;
    }

    public static GuildResult removeComplete(Guild guild) {
        GuildResult gr = new GuildResult(Res_RemoveGuild_Done);
        gr.guild = guild;
        return gr;
    }

    public static GuildResult findGuild(Guild guild) {
        GuildResult gr = new GuildResult(Res_FindGuild_Done);
        gr.guild = guild;
        return gr;
    }

    public static GuildResult setNoticeDone(Guild guild, GuildMember member) {
        GuildResult gri = new GuildResult(Res_SetNotice_Done);
        gri.guild = guild;
        gri.member = member;
        return gri;
    }

    public static GuildResult setAdmission(Guild guild, GuildMember member) {
        GuildResult gri = new GuildResult(Res_SetAdmission_Done);
        gri.guild = guild;
        gri.member = member;
        return gri;
    }

    public static GuildResult changeMaster(Guild guild, GuildMember gm, GuildMember newGm, boolean changedAllianceMaster) {
        GuildResult gri = new GuildResult(Res_ChangeMaster_Done);
        gri.guild = guild;
        gri.member = gm;
        gri.member2 = newGm;
        gri.boolArg = changedAllianceMaster;
        return gri;
    }

    /** Res_ChangeMaster_Unknown with a reason: 0 unknown error, 4 already the master, 5 another change in
     * progress, 6 the master is logged in or has not been away long enough. */
    public static GuildResult changeMasterFailed(int reason) {
        GuildResult gri = new GuildResult(Res_ChangeMaster_Unknown);
        gri.intArg = reason;
        return gri;
    }

    /** Res_CheckIn_Failed with its reason (see GuildType). */
    public static GuildResult checkInFailed(int reason) {
        GuildResult gri = new GuildResult(Res_CheckIn_Failed);
        gri.intArg = reason;
        return gri;
    }

    public static GuildResult useSkillError(GuildSubType subError) {
        GuildResult gri = new GuildResult(Res_UseSkill_Err);
        gri.subType = subError;
        return gri;
    }

    public static GuildResult inviteGuildDone(Guild guild, Char inviter) {
        GuildResult gri = new GuildResult(Res_InviteGuild_Done);
        gri.guild = guild;
        gri.chr = inviter;
        return gri;
    }

    public static GuildResult inviteGuildDoneUser(String invitedName) {
        GuildResult gri = new GuildResult(Res_InviteGuild_DoneUser);
        gri.stringArg = invitedName;
        return gri;
    }

    public GuildType getType() {
        return type;
    }
}
