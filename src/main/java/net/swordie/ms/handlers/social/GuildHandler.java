package net.swordie.ms.handlers.social;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.alliance.Alliance;
import net.swordie.ms.client.alliance.AllianceResult;
import net.swordie.ms.client.character.BroadcastMsg;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.guild.Guild;
import net.swordie.ms.client.guild.GuildMember;
import net.swordie.ms.client.guild.GuildPermission;
import net.swordie.ms.client.guild.GuildRequestor;
import net.swordie.ms.client.guild.bbs.BBSRecord;
import net.swordie.ms.client.guild.bbs.BBSReply;
import net.swordie.ms.client.guild.bbs.GuildBBSPacket;
import net.swordie.ms.client.guild.bbs.GuildBBSType;
import net.swordie.ms.client.guild.result.GuildRequestType;
import net.swordie.ms.client.guild.result.GuildResult;
import net.swordie.ms.client.guild.result.GuildType;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.enums.AllianceType;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.handlers.social.module.GuildModule;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.World;
import net.swordie.ms.world.field.Field;
import net.swordie.orm.dao.CharDao;
import net.swordie.orm.dao.SworDaoFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GuildHandler {

    private static final Logger log = LogManager.getLogger();

    private static final CharDao charDao = (CharDao) SworDaoFactory.getByClass(Char.class);
    private static final int MAX_ANNOUNCEMENT_LENGTH = 2000;
    private static final int MAX_POST_LENGTH = 2000; // v271: title and body in one string
    private static final int MAX_REPLY_LENGTH = 300;

    /** The client's check-in map list is missing or older than the server's: 4 the client's version. */
    @Handler(op = InHeader.GUILD_ATTEND_MAP_INFO_REQUEST)
    public static void handleGuildAttendMapInfoRequest(Client c, InPacket inPacket) {
        c.write(WvsContext.guildAttendMapInfo());
    }

    @Handler(op = InHeader.GUILD_REQUEST)
    public static void handleGuildRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        int v271Type = inPacket.decodeInt();
        int type = GuildRequestType.fromV271(v271Type);
        GuildRequestType grt = GuildRequestType.getByVal(type);
        if (grt == null) {
            if (v271Type != 76) {
                log.warn(String.format("Unhandled guild request v271 %d: %s", v271Type, inPacket));
            }
            return;
        }
        Guild guild = chr.getGuild();
        Field field = chr.getField();
        World world = chr.getWorld();

        GuildMember gm = null;
        if (guild != null) {
            gm = guild.getMemberByCharID(chr.getId());
        }
        switch (grt) {
            case LoadGuild -> GuildModule.handleLoadGuild(chr, inPacket, guild);
            case ShowGuild -> GuildModule.handleShowGuild(inPacket, chr, world);
            case CheckGuildName -> GuildModule.handleCheckGuildName(inPacket, chr);
            case CreateGuildAgree -> GuildModule.handleCreateGuildAgree(chr, field, world);
            case InputGuildName -> GuildModule.handleInputGuildName(chr);
            case Invite -> GuildModule.handleInviteGuild(inPacket, chr, guild, gm);
            case JoinGuild -> GuildModule.acceptInviteOrShow(chr, inPacket.decodeInt());
            case JoinRequest -> handleGuildJoinRequest(chr, inPacket);
            case JoinCancelRequest -> handleGuildJoinCancelRequest(chr, inPacket);
            case AcceptRequestors -> handleGuildJoinAccept(chr, inPacket);
            case RejectRequestors -> handleGuildJoinReject(chr, inPacket);
            case Withdraw -> GuildModule.handleWithdrawGuild(chr, guild, field);
            case Kick -> GuildModule.handleKickGuild(inPacket, chr, guild, field, gm);
            case AllianceWithdraw -> {
                if (guild != null && guild.getAlliance() != null && chr.isGuildMaster()) {
                    leaveAlliance(guild, false);
                }
            }
            case AllianceInviteGuild -> inviteGuildToAlliance(chr, guild, gm, inPacket.decodeInt());
            case AllianceAcceptInvite -> {
                int allianceId = inPacket.decodeInt();
                inPacket.decodeInt(); // own guild id
                Alliance joining = world == null ? null : world.getAlliance(allianceId);
                Alliance pending = getPendingAllianceInvite(chr);
                if (joining == null || joining != pending || !acceptAllianceInvite(chr)) {
                    chr.chatMessage("That alliance invitation is no longer valid.");
                }
            }
            case AllianceKickGuild -> {
                int kickedGuildId = inPacket.decodeInt();
                inPacket.decodeInt(); // alliance id
                Alliance alliance = guild == null ? null : guild.getAlliance();
                if (alliance != null && gm != null && gm.getAllianceGrade() == 1) {
                    Guild kicked = alliance.getGuildByID(kickedGuildId);
                    if (kicked != null && kicked != guild) {
                        leaveAlliance(kicked, true);
                    }
                }
            }
            case AllianceChangeMaster -> {
                int targetGuildId = inPacket.decodeInt();
                int targetCharId = inPacket.decodeInt();
                Alliance alliance = guild == null ? null : guild.getAlliance();
                if (alliance == null || gm == null || gm.getAllianceGrade() != 1) {
                    return;
                }
                Guild target = alliance.getGuildByID(targetGuildId);
                GuildMember newMaster = target == null ? null : target.getMemberByCharID(targetCharId);
                if (target == null || target == guild || newMaster == null || target.getLeaderID() != targetCharId) {
                    chr.chatMessage("The new alliance master has to be the master of another guild in the alliance.");
                    return;
                }
                gm.setAllianceGrade(2);
                newMaster.setAllianceGrade(1);
                alliance.broadcast(net.swordie.ms.connection.packet.AlliancePacket.changeMaster(alliance, gm, newMaster));
                alliance.updateToMembers();
            }
            case AllianceSetGradeName -> {
                Alliance alliance = guild == null ? null : guild.getAlliance();
                List<String> names = new java.util.ArrayList<>(alliance == null ? List.of() : alliance.getGradeNames());
                for (int i = 0; i < 5; i++) {
                    String gradeName = inPacket.decodeString();
                    if (i < names.size() && gradeName.length() >= 2 && gradeName.length() <= 12) {
                        names.set(i, gradeName);
                    }
                }
                if (alliance != null && gm != null && gm.getAllianceGrade() == 1) {
                    alliance.setGradeNames(names);
                    alliance.broadcast(net.swordie.ms.connection.packet.AlliancePacket.setGradeNames(alliance));
                }
            }
            case Remove -> GuildModule.handleRemoveGuild(chr, guild);
            case SetMark -> GuildModule.handleSetMark(inPacket, chr, guild, field, gm);
            case SetGradeNameAndPermission -> GuildModule.handleSetGradeNameAndPermissions(inPacket, chr, guild, gm);
            case AddGrade -> GuildModule.handleAddGrade(inPacket, chr, guild, gm);
            case DeleteGrade -> GuildModule.handleDeleteGrade(inPacket, chr, guild, gm);
            case SetMemberGrade -> GuildModule.handleSetMemberGrade(inPacket, chr, guild, gm);
            case SetEveryoneMemberGrade -> GuildModule.handleSetEveryoneMemberGrade(inPacket, chr, guild, gm);
            case SetNotice -> GuildModule.handleSetNotice(inPacket, chr, guild, gm);
            case SetAdmission -> GuildModule.handleAdmission(inPacket, chr, guild, gm);
            case SkillLevelSetUp -> GuildModule.handleSkillLevelSetup(inPacket, chr, guild, gm);
            case ResetSkills -> GuildModule.handleResetSkills(chr, guild, gm);
            case ResetNoblesseSkills -> {
                // Noblesse levels follow the guild level here, so there is nothing to reset
                chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetSkill_ResetBattleSkill_Unknown1)));
                chr.write(WvsContext.exclRequest());
            }
            case ChangeMaster -> GuildModule.handleChangeGuildMaster(chr, inPacket, guild, gm);
            case Search -> GuildModule.handleSearch(inPacket, chr, world);
            case UseActiveSkill -> GuildModule.handleUseActiveSkill(c, inPacket, chr, guild, gm);
            case CheckIn -> GuildModule.handleCheckIn(chr, guild, gm);
            case ClaimMaster -> GuildModule.handleClaimMaster(chr, guild, gm);
            case BoardAlarm -> GuildModule.handleBoardAlarm(chr, guild, gm);
            case NoticeRead -> {
                if (gm != null) {
                    gm.setNoticeUnread(false);
                }
            }
            case CustomMarkRequest -> GuildModule.handleCustomMarkRequest(chr, inPacket, world);
            case AllianceSetMemberGrade -> GuildModule.handleAllianceSetMemberGrade(chr, inPacket, guild, gm);
            case RequestorsWindow -> GuildModule.handleRequestorsWindow(chr, guild, gm);
            case Help, RenameClose -> {
                // nothing to answer: help is client-side, and the rename window only closes
            }
            case Rename -> chr.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage(
                    "Guild names cannot be changed here.")));
        }
    }

    /** GUILD_CONTENT_RANK_REQUEST: byte 1 the ranking popup (all lists), 5 the contents page (own guild only). */
    @Handler(op = InHeader.GUILD_CONTENT_RANK_REQUEST)
    public static void handleGuildContentRankRequest(Char chr, InPacket inPacket) {
        byte type = inPacket.decodeByte();
        World world = chr.getWorld();
        if (world == null) {
            return;
        }
        if (type == 1 || type == 5) {
            chr.write(net.swordie.ms.client.guild.GuildContents.contentsTable()); // gauge maximums first
        }
        switch (type) {
            case 1 -> chr.write(net.swordie.ms.client.guild.GuildContents.fullRanking(world, chr.getGuild()));
            case 5 -> chr.write(net.swordie.ms.client.guild.GuildContents.ownRanking(world, chr.getGuild()));
            default -> log.warn("Unknown guild content rank request " + type);
        }
    }

    /** Request type 3: int guildId, str introduction, then two ints and two bytes (recruiting filters). */
    public static void handleGuildJoinRequest(Char chr, InPacket inPacket) {
        if (chr.getGuild() != null) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_JoinGuild_AlreadyJoined)));
            return;
        }
        int guildId = inPacket.decodeInt();
        var introduction = inPacket.decodeString();
        Guild guild = chr.getWorld().getGuildByID(guildId);
        if (guild == null) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_JoinGuild_Unknown)));
            return;
        }
        if (!guild.isAppliable() || guild.isFull()) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_JoinGuild_AlreadyFull)));
            return;
        }
        if (guild.getRequestorById(chr.getId()) != null) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_JoinRequest_AlreadyMade)));
            return;
        }
        long open = chr.getWorld().getGuilds().values().stream()
                .filter(g -> g.getRequestorById(chr.getId()) != null).count();
        if (open >= 5) { // "You can only apply to join up to 5 guilds."
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_JoinRequest_AlreadyFull)));
            return;
        }

        guild.addGuildRequestor(chr, introduction);
        var requestor = guild.getRequestorById(chr.getId());
        if (requestor != null) {
            guild.broadcast(WvsContext.guildResult(GuildResult.joinRequest(guild, requestor)));
        }
        chr.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage("You have successfully requested to join %s.".formatted(guild.getName()))));
    }

    /** Request type 4: int guildId. */
    public static void handleGuildJoinCancelRequest(Char chr, InPacket inPacket) {
        Guild guild = chr.getWorld().getGuildByID(inPacket.decodeInt());
        var requestor = guild == null ? null : guild.getRequestorById(chr.getId());
        if (requestor == null) {
            return;
        }
        guild.removeGuildRequestor(requestor);
        var cancelled = WvsContext.guildResult(GuildResult.joinCancelRequest(guild, chr.getId()));
        guild.broadcast(cancelled);
        chr.write(cancelled);
    }

    /** GUILD_RESULT (in): the invitee's client answering an invitation (int type, str inviter, str own name). */
    @Handler(op = InHeader.GUILD_RESULT)
    public static void handleGuildResult(Char chr, InPacket inPacket) {
        var typeVal = GuildType.fromV271(inPacket.decodeInt());
        var type = GuildType.getTypeByVal(typeVal);
        if (type == null) {
            log.warn("Unknown guild result type " + typeVal);
            return;
        }

        switch (type) {
            case Res_InviteGuild_BlockedUser, Res_InviteGuild_BlockedRequests, Res_InviteGuild_Rejected ->
                    GuildModule.handleInviteGuildRejected(chr, type, inPacket);
            default -> {
                log.error("Unhandled GuildType " + type);
                if (chr.getUser() != null && !chr.getUser().isPlayer()) {
                    chr.chatMessage("Unhandled guild result: " + type); // staff only
                }
            }
        }
    }

    /** Request type 6: byte count, int charIds. */
    public static void handleGuildJoinAccept(Char chr, InPacket inPacket) {
        Guild guild = chr.getGuild();
        World world = chr.getWorld();
        GuildMember accepter = guild == null ? null : guild.getMemberByCharID(chr.getId());
        // the rank's "accept" permission decides, not only master/junior master
        if (guild == null || accepter == null || (!accepter.hasPermission(GuildPermission.Accept)
                && accepter.getGrade() != Guild.MASTER)) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_JoinGuild_UnknownUser)));
            return;
        }
        byte size = inPacket.decodeByte();
        Set<Char> onlineChars = new HashSet<>();
        Set<Char> offlineChars = new HashSet<>();
        for (int i = 0; i < size; i++) {
            int charId = inPacket.decodeInt();
            GuildRequestor gr = guild.getRequestorById(charId);
            if (gr == null) {
                chr.chatMessage("That character no longer requests to join your guild.");
                continue;
            }
            Char toJoin = world.getCharByID(charId);
            if (toJoin != null) {
                if (toJoin.getGuild() != null) {
                    chr.chatMessage("%s is already in a guild.", gr.getName());
                } else {
                    onlineChars.add(toJoin);
                }
            } else {
                toJoin = charDao.getById(charId);
                if (toJoin != null) {
                    if (toJoin.getGuild() != null) {
                        chr.chatMessage("%s is already in a guild.", gr.getName());
                    } else {
                        offlineChars.add(toJoin);
                    }
                } else {
                    chr.chatMessage("Character %s could not be found, their character is probably gone.", gr.getName());
                }
            }
        }
        for (Char c : onlineChars) {
            if (guild.isFull()) {
                chr.chatMessage("%s was not added, as your guild is full.", c.getName());
                continue;
            }
            GuildModule.joinGuild(guild, c);
            chr.write(WvsContext.guildResult(GuildResult.loadGuild(guild, chr)));
        }
        for (Char c : offlineChars) {
            if (guild.isFull()) {
                chr.chatMessage("%s was not added, as your guild is full.", c.getName());
                continue;
            }
            guild.addMember(c, false);
            c.setGuild(guild);
            charDao.saveOrUpdate(c);
            world.removeRequestor(c);
        }
        if (!offlineChars.isEmpty()) {
            chr.write(WvsContext.guildResult(GuildResult.loadGuild(guild, chr)));
        }
    }

    /** Request type 7: byte count, int charIds (the client drops them from its list at once). */
    public static void handleGuildJoinReject(Char chr, InPacket inPacket) {
        var guild = chr.getGuild();
        if (guild == null) {
            return;
        }

        var gm = guild.getMemberByCharID(chr.getId());
        if (gm == null || !gm.hasPermission(GuildPermission.Accept)) {
            chr.chatMessage("You don't have permission to do this.");
            return;
        }

        var size = inPacket.decodeByte();
        for (int i = 0; i < size; i++) {
            var otherCharId = inPacket.decodeInt();
            var other = chr.getWorld().getCharByID(otherCharId);
            var otherGm = guild.getRequestorById(otherCharId);

            if (otherGm == null) {
                chr.chatMessage("Could not find that requestor.");
                continue;
            }

            guild.removeGuildRequestor(otherGm);
            if (other != null) {
                other.chatMessage("[%s] has rejected your guild join request.", guild.getName());
            }
        }
        guild.broadcastLoad();
    }

    @Handler(op = InHeader.ALLIANCE_REQUEST)
    public static void handleAllianceRequest(Char chr, InPacket inPacket) {
        byte type = inPacket.decodeByte();
        AllianceType at = AllianceType.getByVal(type);
        if (at == null) {
            log.error(String.format("Unknown alliance request %d", type));
            return;
        }
        Guild guild = chr.getGuild();
        Alliance alliance = guild == null ? null : guild.getAlliance();
        Char other;
        Guild otherGuild;
        World world = chr.getClient().getWorld();
        GuildMember member = guild == null ? null : chr.getGuild().getMemberByCharID(chr.getId());
        GuildMember otherMember;
        if (member == null) {
            return;
        }
        if (at == AllianceType.Req_Load) {
            // every member gets the alliance window, not only guild masters
            if (alliance != null) {
                chr.write(WvsContext.allianceResult(AllianceResult.loadDone(alliance)));
                chr.write(WvsContext.allianceResult(AllianceResult.loadGuildDone(alliance)));
            }
            return;
        }
        if (!chr.isGuildMaster()) {
            return;
        }
        // actions only the alliance master may take
        boolean allianceMaster = alliance != null && member.getAllianceGrade() == 1;
        switch (at) {
            case Req_Withdraw:
                if (alliance == null) {
                    chr.chatMessage("You are not in an alliance.");
                    return;
                }
                leaveAlliance(guild, false);
                break;
            case Req_Invite:
                String guildName = inPacket.decodeString();
                if (!allianceMaster) {
                    chr.write(WvsContext.allianceResult(AllianceResult.msg(AllianceType.Res_Invite_Failed)));
                    return;
                }
                if (alliance.getGuilds().size() >= getAllianceCapacity(alliance)) {
                    chr.chatMessage("Your alliance has no room for another guild.");
                    return;
                }
                otherGuild = world.getGuildByName(guildName);
                if (otherGuild != null && otherGuild != guild) {
                    other = world.getCharByID(otherGuild.getLeaderID());
                    if (other != null && other.getGuild() != null) {
                        if (other.getGuild().getAlliance() == null) {
                            other.write(WvsContext.allianceResult(AllianceResult.inviteGuild(alliance, member)));
                            alliance.addPendingGuild(other.getGuild());
                            chr.chatMessage("You have invited the guild [%s] to your alliance.", guildName);
                        } else {
                            chr.write(WvsContext.allianceResult(AllianceResult.msg(AllianceType.Res_InviteGuild_AlreadyInvited)));
                        }
                    } else {
                        chr.write(WvsContext.allianceResult(AllianceResult.msg(AllianceType.Res_Invite_Failed)));
                    }
                } else {
                    chr.write(WvsContext.allianceResult(AllianceResult.msg(AllianceType.Res_Invite_Failed)));
                }
                break;
            case Req_Join:
                int allianceId = inPacket.decodeInt();
                Alliance joining = world.getAlliance(allianceId);
                if (alliance != null || joining == null || joining.getGuilds().size() >= getAllianceCapacity(joining)
                        || !joining.hasPendingGuildInvite(guild.getId())) {
                    chr.write(WvsContext.allianceResult(AllianceResult.msg(AllianceType.Res_Invite_Failed)));
                    return;
                }
                for (GuildMember m : guild.getMembers()) {
                    m.setAllianceGrade(GuildModule.defaultAllianceGrade(guild, m));
                }
                joining.addGuild(guild);
                break;
            case Req_ChangeMaster:
                int newMasterId = inPacket.decodeInt();
                if (!allianceMaster) {
                    return;
                }
                other = world.getCharByID(newMasterId);
                otherGuild = other == null ? null : other.getGuild();
                if (otherGuild == null || otherGuild == guild || alliance.getGuildByID(otherGuild.getId()) == null
                        || !otherGuild.isGuildMaster(other)) {
                    chr.chatMessage("The new alliance master has to be an online master of another guild in the alliance.");
                    return;
                }
                otherMember = otherGuild.getMemberByCharID(other.getId());
                member.setAllianceGrade(2);
                otherMember.setAllianceGrade(1);
                alliance.broadcast(net.swordie.ms.connection.packet.AlliancePacket.changeMaster(alliance, member, otherMember));
                alliance.updateToMembers();
                break;
            case Req_Kick:
                int otherID = inPacket.decodeInt();
                int kickedGuildID = inPacket.decodeInt();
                if (!allianceMaster) {
                    return;
                }
                otherGuild = alliance.getGuildByID(kickedGuildID);
                if (otherGuild == null && alliance.getGuildByID(otherID) != null) {
                    otherGuild = alliance.getGuildByID(otherID); // the ids may come the other way around
                }
                if (otherGuild != null && otherGuild != guild) {
                    leaveAlliance(otherGuild, true);
                }
                break;
            case Req_SetGradeName:
                List<String> names = new java.util.ArrayList<>(alliance == null ? List.of() : alliance.getGradeNames());
                for (int i = 0; i < 5; i++) {
                    String gradeName = inPacket.decodeString();
                    if (i < names.size() && gradeName.length() >= 4 && gradeName.length() <= 12
                            && Util.isDigitLetterString(gradeName)) {
                        names.set(i, gradeName);
                    }
                }
                if (!allianceMaster) {
                    return;
                }
                alliance.setGradeNames(names);
                alliance.broadcast(net.swordie.ms.connection.packet.AlliancePacket.setGradeNames(alliance));
                break;
            case Req_ChangeGrade: {
                // the alliance master moves a member up or down a rank (3-5; 1 and 2 go with being a master)
                int charId = inPacket.decodeInt();
                boolean up = inPacket.getUnreadAmount() > 0 && inPacket.decodeByte() != 0;
                if (!allianceMaster) {
                    return;
                }
                GuildMember target = null;
                for (Guild g : alliance.getGuilds()) {
                    if (g.getMemberByCharID(charId) != null) {
                        target = g.getMemberByCharID(charId);
                        if (g.getLeaderID() == charId) {
                            target = null; // guild masters keep their rank
                        }
                        break;
                    }
                }
                if (target == null) {
                    chr.write(WvsContext.allianceResult(AllianceResult.msg(AllianceType.Res_ChangeGrade_Fail)));
                    return;
                }
                target.setAllianceGrade(Math.max(3, Math.min(5, target.getAllianceGrade() + (up ? -1 : 1))));
                alliance.broadcast(WvsContext.allianceResult(AllianceResult.changeGrade(target)));
                break;
            }
            case Req_SetNotice: {
                String notice = inPacket.decodeString();
                if (!allianceMaster || notice.length() > 100) {
                    return;
                }
                alliance.setNotice(notice);
                alliance.broadcast(net.swordie.ms.connection.packet.AlliancePacket.setNotice(alliance));
                break;
            }
            case Req_Destroy:
                if (!allianceMaster) {
                    return;
                }
                destroyAlliance(alliance);
                break;
            default:
                log.error(String.format("Unhandled alliance request type %s", at));
        }
    }

    /** The most guilds an alliance can hold (its saved capacity, 5 when it has none). */
    public static int getAllianceCapacity(Alliance alliance) {
        int max = alliance.getMaxMemberNum();
        return max <= 0 ? GuildModule.ALLIANCE_MAX_GUILDS : max;
    }

    /**
     * A guild leaves (or is kicked from) its alliance. If it led the alliance, another guild's master takes over; an
     * alliance left with one guild is disbanded.
     */
    /** The alliance that has invited {@code chr}'s guild (chr must be its master, guild not in one), or null. */
    public static Alliance getPendingAllianceInvite(Char chr) {
        Guild guild = chr.getGuild();
        if (guild == null || guild.getAlliance() != null || !chr.isGuildMaster() || chr.getWorld() == null) {
            return null;
        }
        for (Alliance a : chr.getWorld().getAlliances().values()) {
            if (a.hasPendingGuildInvite(guild.getId()) && a.getGuilds().size() < getAllianceCapacity(a)) {
                return a;
            }
        }
        return null;
    }

    /** Joins the alliance that invited chr's guild (Lenario). @return false if there is no valid invite */
    public static boolean acceptAllianceInvite(Char chr) {
        Alliance joining = getPendingAllianceInvite(chr);
        if (joining == null) {
            return false;
        }
        Guild guild = chr.getGuild();
        for (GuildMember m : guild.getMembers()) {
            m.setAllianceGrade(GuildModule.defaultAllianceGrade(guild, m));
        }
        joining.addGuild(guild);
        return true;
    }

    /** v265 alliance window invite (GUILD_REQUEST 52, a guild id): the other guild's master accepts at Lenario. */
    private static void inviteGuildToAlliance(Char chr, Guild guild, GuildMember gm, int guildId) {
        Alliance alliance = guild == null ? null : guild.getAlliance();
        if (alliance == null || gm == null || gm.getAllianceGrade() != 1) {
            chr.chatMessage("Only the alliance master can invite guilds.");
            return;
        }
        if (alliance.getGuilds().size() >= getAllianceCapacity(alliance)) {
            chr.chatMessage("Your alliance has no room for another guild.");
            return;
        }
        Guild other = chr.getWorld().getGuildByID(guildId);
        if (other == null || other == guild) {
            chr.chatMessage("That guild can't be invited.");
            return;
        }
        if (other.getAlliance() != null || alliance.hasPendingGuildInvite(other.getId())) {
            chr.write(net.swordie.ms.connection.packet.AlliancePacket.inviteMessage(net.swordie.ms.client.guild.result.GuildType.Res_Alliance_AlreadyInvited,
                    other.getName()));
            return;
        }
        Char master = chr.getWorld().getCharByID(other.getLeaderID());
        if (master == null) {
            chr.chatMessage("That guild's master is not online.");
            return;
        }
        alliance.addPendingGuild(other);
        master.write(net.swordie.ms.connection.packet.AlliancePacket.invite(alliance, chr.getName()));
        master.chatMessage(String.format("The alliance [%s] has invited your guild (accept from your notifications"
                + " or at Lenario).", alliance.getName()));
        chr.chatMessage(String.format("You have invited the guild [%s] to your alliance.", other.getName()));
    }

    public static void leaveAlliance(Guild guild, boolean kicked) {
        Alliance alliance = guild.getAlliance();
        if (alliance == null) {
            return;
        }
        GuildMember leader = guild.getGuildLeader();
        if (alliance.getGuilds().size() <= 2) {
            destroyAlliance(alliance);
            return;
        }
        if (leader != null && leader.getAllianceGrade() == 1) {
            Guild next = null;
            for (Guild g : alliance.getGuilds()) {
                if (g != guild && g.getGuildLeader() != null) {
                    next = g;
                    break;
                }
            }
            if (next != null) {
                next.getGuildLeader().setAllianceGrade(1);
                alliance.broadcast(net.swordie.ms.connection.packet.AlliancePacket.changeMaster(alliance, leader, next.getGuildLeader()));
            }
        }
        alliance.broadcast(net.swordie.ms.connection.packet.AlliancePacket.withdraw(alliance, guild, kicked));
        if (!kicked) {
            alliance.broadcast(UserLocal.chatMsg(ChatType.SystemNotice, String.format("[%s] has left the alliance.",
                    guild.getName())));
        }
        alliance.removeGuild(guild);
        for (GuildMember m : guild.getMembers()) {
            m.setAllianceGrade(5);
        }
        guild.updateToMembers();
        alliance.updateToMembers();
    }

    /** Disbands an alliance: every guild leaves it, and it's deleted. */
    public static void destroyAlliance(Alliance alliance) {
        alliance.broadcast(net.swordie.ms.connection.packet.AlliancePacket.destroy(alliance)); // "The alliance has disbanded."
        for (Guild g : new java.util.ArrayList<>(alliance.getGuilds())) {
            alliance.removeGuild(g);
            for (GuildMember m : g.getMembers()) {
                m.setAllianceGrade(5);
            }
            g.updateToMembers();
        }
        World world = alliance.getWorld();
        if (world == null) {
            for (World w : net.swordie.ms.Server.getInstance().getWorlds()) {
                if (w.getAlliances().containsKey(alliance.getId())) {
                    world = w;
                }
            }
        }
        if (world != null) {
            world.removeAlliance(alliance);
        }
    }

    /**
     * GUILD_BBS (v271 1234), the board window's senders 0x1418ccf00..0x1418ce1e0 (type: body): 11 open (none), 0 list
     * `4 page + 1, 4 category, 1, 4`, 1 write `1 0, 4 category, S, 1 poll [S 4 1 1 B8 4 n {S}]` or edit
     * `1 1, 4 post, 4 category, S`, 2 delete `4`, 4 open post `4 post, 1, 4 reply page, 1`, 5 reply `4 post, S, 4`,
     * 6 delete reply `4 post, 4 reply, 4`, 8 like `4`, 15 pin `4 post, 1`, 13 categories, 17 / 19 (not handled).
     * The text is "title" + line break + "body".
     */
    @Handler(op = InHeader.GUILD_BBS)
    public static void handleGuildBBS(Char chr, InPacket inPacket) {
        Guild guild = chr.getGuild();
        if (guild == null) {
            return;
        }

        var gm = guild.getMemberByCharID(chr.getId());
        if (gm == null) {
            return;
        }

        byte val = inPacket.decodeByte();
        GuildBBSType type = GuildBBSType.getByValue(val);
        if (type == null) {
            log.debug("Guild board request type {} not handled ({} bytes)", val, inPacket.getUnreadAmount());
            return;
        }
        switch (type) {
            case Req_LoadCategories -> chr.write(WvsContext.guildBBSResult(GuildBBSPacket.categories()));
            case Req_LoadPages -> handleLoadPages(chr, inPacket, guild, gm);
            case Req_CreateRecord -> handleCreateRecord(chr, inPacket, guild, gm);
            case Req_DeleteRecord -> handleDeleteRecord(chr, inPacket, guild, gm);
            case Req_LoadRecord -> handleLoadRecord(chr, inPacket, guild, gm);
            case Req_CreateReply -> handleCreateReply(chr, inPacket, guild);
            case Req_DeleteReply -> handleDeleteReply(chr, inPacket, guild, gm);
            case Req_Upvote -> handleUpvote(chr, inPacket, guild);
            case Req_Pin -> chr.write(WvsContext.guildBBSResult(
                    GuildBBSPacket.pinRefused(guild.getRecordByID(inPacket.decodeInt()))));
            default -> log.debug("Guild board request {} not handled ({} bytes)", type, inPacket.getUnreadAmount());
        }
    }

    private static void handleUpvote(Char chr, InPacket inPacket, Guild guild) {
        var recordId = inPacket.decodeInt();
        var record = guild.getRecordByID(recordId);

        if (record != null) {
            var chrId = chr.getId();
            if (record.getUpvotedIds().contains(chrId)) {
                record.getUpvotedIds().remove(chrId);
            } else {
                record.addUpvoted(chr);
            }
            chr.write(WvsContext.guildBBSResult(GuildBBSPacket.upvotes(record)));
        }
    }

    public static void handleDeleteReply(Char chr, InPacket inPacket, Guild guild, GuildMember gm) {
        var id = inPacket.decodeInt();
        int replyId = inPacket.decodeInt();
        var record = guild.getRecordByID(id);
        if (record != null && (record.isCreatedBy(chr) || gm.hasPermission(GuildPermission.ManageBBS))) {
            BBSReply reply = record.getReplyById(replyId);
            if (reply == null) {
                return;
            }
            record.removeReply(reply);
            chr.write(WvsContext.guildBBSResult(GuildBBSPacket.loadRecord(record)));
        }
    }

    public static void handleCreateReply(Char chr, InPacket inPacket, Guild guild) {
        var id = inPacket.decodeInt();
        var msg = inPacket.decodeString();
        var record = guild.getRecordByID(id);
        if (record != null && msg != null && !msg.isBlank() && msg.length() <= MAX_REPLY_LENGTH) {
            BBSReply reply = new BBSReply(chr.getId(), FileTime.currentTime(), msg);
            record.addReply(reply);
            chr.write(WvsContext.guildBBSResult(GuildBBSPacket.loadRecord(record))); // shows the new reply
        }
    }

    public static void handleLoadRecord(Char chr, InPacket inPacket, Guild guild, GuildMember gm) {
        var id = inPacket.decodeInt();
        var record = guild.getRecordByID(id);

        gm.setEditingRecord(record);
        if (record != null) {
            chr.write(WvsContext.guildBBSResult(GuildBBSPacket.loadRecord(record)));
        } else {
            chr.boxMessage("Could not find that post.");
        }
    }

    public static void handleDeleteRecord(Char chr, InPacket inPacket, Guild guild, GuildMember gm) {
        int id = inPacket.decodeInt();
        var record = guild.getRecordByID(id);
        if (record != null && (chr.getId() == record.getCreatorID() || gm.hasPermission(GuildPermission.ManageBBS))) {
            guild.removeRecord(record);
        } else {
            chr.boxMessage("Could not find that post.");
        }
    }

    public static void handleCreateRecord(Char chr, InPacket inPacket, Guild guild, GuildMember gm) {
        boolean edit = inPacket.decodeByte() != 0;
        BBSRecord editingRecord = null;
        if (edit) {
            editingRecord = guild.getRecordByID(inPacket.decodeInt());
            if (editingRecord == null) {
                chr.boxMessage("Could not find that post.");
                return;
            }
        }
        boolean isAnnouncement = inPacket.decodeInt() == GuildBBSPacket.CATEGORY_NOTICE;
        String msg = inPacket.decodeString(); // a poll may follow (not kept)

        if (editingRecord != null && editingRecord.getCreatorID() != chr.getId()
                && !gm.hasPermission(GuildPermission.ManageBBS)) {
            return; // only the author or a BBS manager may edit an existing post
        }
        var maxLen = isAnnouncement ? MAX_ANNOUNCEMENT_LENGTH : MAX_POST_LENGTH;
        if (msg == null || msg.isBlank() || (isAnnouncement && !gm.hasPermission(GuildPermission.ManageBBS))
                || msg.length() > maxLen) {
            return;
        }

        if (editingRecord != null) {
            editingRecord.setMsg(msg);
            editingRecord.setIsAnnouncement(isAnnouncement);
        } else {
            guild.addBbsRecord(new BBSRecord(chr.getId(), msg, isAnnouncement));
        }
    }

    /** Every record of the category: the client sorts them and keeps the requested page itself. */
    public static void handleLoadPages(Char chr, InPacket inPacket, Guild guild, GuildMember gm) {
        inPacket.decodeInt(); // page + 1
        int category = inPacket.decodeInt();

        gm.setEditingRecord(null);

        Predicate<BBSRecord> predicate = switch (category) {
            case GuildBBSPacket.CATEGORY_NOTICE -> BBSRecord::isAnnouncement;
            case GuildBBSPacket.CATEGORY_POST -> r -> !r.isAnnouncement();
            default -> r -> true;
        };
        List<BBSRecord> records = guild.getBbsRecords()
                .stream()
                .filter(predicate)
                .sorted(Comparator.comparingInt(BBSRecord::getIdForBbs))
                .collect(Collectors.toList());
        chr.write(WvsContext.guildBBSResult(GuildBBSPacket.loadPages(records.size(), records, 0)));
    }
}
