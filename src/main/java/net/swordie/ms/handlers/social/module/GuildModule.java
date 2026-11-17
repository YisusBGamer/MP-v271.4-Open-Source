package net.swordie.ms.handlers.social.module;

import net.swordie.ms.client.jobs.common.GuildSkillHandler;
import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.ProgressionLog;
import net.swordie.ms.client.character.skills.SkillStat;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.guild.Guild;
import net.swordie.ms.client.guild.GuildMember;
import net.swordie.ms.client.guild.GuildPermission;
import net.swordie.ms.client.guild.GuildSkill;
import net.swordie.ms.client.guild.result.GuildResult;
import net.swordie.ms.client.guild.result.GuildType;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.world.World;
import net.swordie.ms.world.field.Field;
import net.swordie.orm.dao.CharDao;
import net.swordie.orm.dao.GuildDao;
import net.swordie.orm.dao.SworDaoFactory;

import net.swordie.ms.client.alliance.Alliance;
import net.swordie.ms.client.alliance.AllianceResult;
import net.swordie.ms.client.character.BroadcastMsg;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.enums.FieldOption;
import net.swordie.ms.loaders.ForbiddenWordsData;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.StringData;
import net.swordie.ms.scripts.ScriptType;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.Util;
import net.swordie.orm.dao.AllianceDao;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GuildModule {

    private static final GuildDao guildDao = (GuildDao) SworDaoFactory.getByClass(Guild.class);
    private static final CharDao charDao = (CharDao) SworDaoFactory.getByClass(Char.class);
    private static final AllianceDao allianceDao = (AllianceDao) SworDaoFactory.getByClass(Alliance.class);
    private static final int CUSTOM_MARK_DIMENSIONS = 17; // 17x17 pixels
    private static final int CUSTOMMARK_MAX_LENGTH = 20000;

    // ----------------------------------------------------------------------------------------------------------------
    public static final long CREATE_COST = 5_000_000L, ALLIANCE_COST = 5_000_000L;
    public static final int CHECK_IN_CONTRIBUTION = 30;
    /** Official: 20 members to start, +10 per expansion for 10,000 x (1 + expansions so far) GP, up to 200. */
    public static final int BASE_MEMBERS = 20, MEMBERS_PER_EXPANSION = 10, EXPANSION_GP = 10_000;
    public static final int ALLIANCE_MAX_GUILDS = 5;
    /**
     * GP for resetting the guild skills (the client's own default when the load sends 0). Noblesse levels follow the
     * guild level here (Guild#syncNoblesseSkills), so their reset stays off (0).
     */
    public static final int SKILL_RESET_GP = 50_000, NOBLESSE_RESET_GP = 0;
    /** Hidden per-character record: "ci" = last check-in day, "bp" = the week Banner of Plenty was handed out. */
    public static final int GUILD_QR = 89999991;

    // v271 Skill.wz 9100: 91000012 Scroll Enhancement Mastery, 91000014 Scroll Enhancement Craftsman, 91000027 Better
    // Together, 91000035 / 91000016 Special Power (Arcane Power arcX and Star Force starX, replacing Arcane Force and
    // Amidst the Stars). Item Salvation 91000013, Banner of Plenty 91000009/31/32, Amidst the Stars 91000038/41,
    // I Summon Thee 91001017 and Rise, Minions! 91001020 are gone from the data (their values read 0).
    public static final int MERCHANDISING = 91000008, BANNER_OF_PLENTY_1 = 91000009, ENHANCEMENT_MASTERY = 91000012,
            ITEM_SALVATION = GuildSkillHandler.MAYBE_ITS_STILL_OKAY, UPGRADE_SALVATION = GuildSkillHandler.UPGRADE_SALVATION, GUILD_EXPERTISE = GuildSkillHandler.GUILD_EXPERTISE,
            BANNER_OF_PLENTY_2 = GuildSkillHandler.BANNER_OF_PLENTY_II, BANNER_OF_PLENTY_3 = GuildSkillHandler.BANNER_OF_PLENTY_III, ARCANE_FORCE = GuildSkillHandler.ARCANE_FORCE,
            SPECIAL_POWER = ARCANE_FORCE, SPECIAL_POWER_2 = GuildSkillHandler.SPECIAL_POWER,
            AMIDST_THE_STARS = GuildSkillHandler.AMIDST_THE_STARS, AMIDST_THE_STARS_REBOOT = GuildSkillHandler.AMIDST_THE_STARS_2;
    public static final int ON_MY_WAY = 91001016, I_SUMMON_THEE = 91001017, RISE_MINIONS = 91001020;
    public static final int G_POTION = 2002037, BLESSING_OF_THE_GUILD = 2023300;

    /** Skill.wz 9100.img req/reqGuildLv: the guild level each skill needs (not in the server's skill data). */
    private static final Map<Integer, Integer> REQ_GUILD_LV = new HashMap<>();

    static {
        int[][] req = {{GuildSkillHandler.MERCHANDISING, 10}, {GuildSkillHandler.SMALL_CHANGE, 1}, {GuildSkillHandler.ENHANCEMENT_MASTERY, 15}, {GuildSkillHandler.UPGRADE_SALVATION, 25}, {GuildSkillHandler.FEARLESS, 20},
                {GuildSkillHandler.SPECIAL_POWER, 5}, {GuildSkillHandler.GUILD_EXPERTISE_VI, 25}, {SkillConstants.GUILD_SKILL_BASE_2, 5}, {GuildSkillHandler.UNITED_FRONT, 1}, {GuildSkillHandler.GUILD_EXPERTISE, 1}, {GuildSkillHandler.GUILD_ON_FIRE_I, 5},
                {GuildSkillHandler.GUILD_ON_FIRE_II, 10}, {GuildSkillHandler.GUILD_ON_FIRE_III, 15}, {GuildSkillHandler.TEAM_PLAYERS, 20}, {GuildSkillHandler.WELL_ROUNDED, 25}, {GuildSkillHandler.ARCANE_FORCE, 5}, {GuildSkillHandler.SHARENIAN_DEMON_MOUNT, 30},
                {GuildSkillHandler.GUILD_ON_FIRE_I_2, 5}, {GuildSkillHandler.GUILD_ON_FIRE_II_2, 15}, {SkillConstants.GUILD_SKILL_END, 15}};
        for (int[] r : req) {
            REQ_GUILD_LV.put(r[0], r[1]);
        }
    }

    public static void handleLoadGuild(Char chr, InPacket inPacket, Guild guild) {
        if (guild != null) {
            chr.write(WvsContext.guildResult(GuildResult.loadGuild(guild, chr)));
            chr.write(WvsContext.guildResult(GuildResult.checkInRewards()));
        }
    }

    /** Request type 44: another guild's window. */
    public static void handleShowGuild(InPacket inPacket, Char chr, World world) {
        var guild = world.getGuildByID(inPacket.decodeInt());
        if (guild == null) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_Advertise_Unknown)));
            return;
        }
        chr.write(WvsContext.guildResult(GuildResult.findGuild(guild)));
    }

    public static void handleFindGuildByCid(InPacket inPacket, Char chr, Field field, World world) {
        // only an invitation lets the character join
        acceptInviteOrShow(chr, inPacket.decodeInt());
    }

    public static void handleFindGuildByGid(InPacket inPacket, Char chr, World world) {
        acceptInviteOrShow(chr, inPacket.decodeInt());
    }

    /** Joins the guild if it invited chr (and has room), else shows the guild's info. */
    public static void acceptInviteOrShow(Char chr, int guildID) {
        var guild = chr.getWorld().getGuildByID(guildID);
        if (guild == null) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_Advertise_Unknown)));
            return;
        }
        if (chr.getGuild() == null && guild.hasPendingChar(chr)) {
            if (guild.isFull()) {
                guild.removePendingChar(chr);
                chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_JoinGuild_AlreadyFull)));
                return;
            }
            joinGuild(guild, chr);
        } else {
            chr.write(WvsContext.guildResult(GuildResult.findGuild(guild)));
        }
    }

    /**
     * Puts an online character in a guild (an accepted invitation or join request): the guild hears of it
     * (Guild#addMember), the character gets the guild window and its passive skills, and the map sees the tag.
     */
    public static void joinGuild(Guild guild, Char chr) {
        guild.addMember(chr);
        World world = chr.getWorld();
        if (world != null) {
            world.removeRequestor(chr);
        }
        // the join result carries the alliance (ALLIANCE_RESULT is another packet in this client)
        chr.write(WvsContext.guildResult(GuildResult.loadGuild(GuildType.Res_JoinGuild_DoneSelf, guild, chr)));
        for (GuildSkill gs : guild.getSkills().values()) {
            if (holdsStats(gs)) { // as the login adds them (Char#initBaseStats)
                chr.addToBaseStatCache(gs);
            }
        }
        Field field = chr.getField();
        if (field != null) {
            field.broadcastPacket(UserRemote.guildUpdated(chr));
        }
        guildDao.saveOrUpdate(guild);
    }

    /** An online character leaving their guild drops its passive skills' stats. */
    public static void dropGuildStats(Char chr, Guild guild) {
        if (chr == null || guild == null) {
            return;
        }
        for (GuildSkill gs : guild.getSkills().values()) {
            if (holdsStats(gs)) { // only what was added (active skills' stats were taken away too)
                chr.removeFromBaseStatCache(gs);
            }
        }
    }

    /**
     * Whether a guild skill's stats are in its members' base stats: passive skills with a level, the ones the
     * login adds (Char#initBaseStats) and Char#addToBaseStatCache keeps.
     */
    public static boolean holdsStats(GuildSkill gs) {
        return gs != null && gs.getLevel() > 0 && SkillConstants.isPassiveSkill_NoPsdSkillsCheck(gs.getSkillID())
                && SkillConstants.isPassiveSkill(gs.getSkillID());
    }

    /**
     * changes a guild skill's level and swaps the old level's stats for the new level's in the base stats of the
     * members online.
     */
    public static void changeSkillLevel(Guild guild, GuildSkill gs, int level) {
        List<Char> online = new ArrayList<>();
        for (GuildMember gm : guild.getOnlineMembers()) {
            if (gm.getChr() != null) {
                online.add(gm.getChr());
            }
        }
        if (holdsStats(gs)) {
            online.forEach(c -> c.removeFromBaseStatCache(gs));
        }
        gs.setLevel((short) level);
        if (holdsStats(gs)) {
            online.forEach(c -> c.addToBaseStatCache(gs));
        }
    }

    /** Official guild names: 2-12 letters and numbers. */
    public static boolean isValidGuildName(String name) {
        return name != null && name.length() >= 2 && name.length() <= 12 && Util.isDigitLetterString(name)
                && !ForbiddenWordsData.isForbiddenIGN(name);
    }

    /** Names waiting for the creator's yes on Res_CheckGuildName_Available, by char id. */
    private static final Map<Integer, String> pendingGuildNames = new java.util.concurrent.ConcurrentHashMap<>();

    /** Why chr can't create a guild named name (null: they can); also shows the reason. */
    private static String checkCreate(Char chr, String name) {
        if (chr.getGuild() != null) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_JoinGuild_AlreadyJoined)));
            return "already in a guild";
        }
        if (!isValidGuildName(name)) {
            chr.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage("A guild name is 2 to 12 letters and "
                    + "numbers.")));
            return "invalid name";
        }
        if (chr.getMoney() < CREATE_COST) {
            chr.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage("Creating a guild costs 5,000,000 mesos.")));
            return "not enough mesos";
        }
        return null;
    }

    /** Request type 1: the name typed in the dialog Res_InputGuildName opened. */
    public static void handleCheckGuildName(InPacket inPacket, Char chr) {
        String name = inPacket.decodeString();
        pendingGuildNames.remove(chr.getId());
        if (checkCreate(chr, name) != null) {
            return;
        }
        if (chr.getWorld().getGuildByName(name) != null) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_CheckGuildName_AlreadyUsed)));
            return;
        }
        pendingGuildNames.put(chr.getId(), name);
        chr.write(WvsContext.guildResult(GuildResult.checkNameAvailable(name)));
    }

    /** Request type 43: no to "create this guild?", so the name dialog again. */
    public static void handleInputGuildName(Char chr) {
        pendingGuildNames.remove(chr.getId());
        chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_InputGuildName)));
    }

    /** Request type 2: yes to "create this guild?". */
    public static void handleCreateGuildAgree(Char chr, Field field, World world) {
        String name = pendingGuildNames.remove(chr.getId());
        if (name == null || checkCreate(chr, name) != null) {
            return;
        }
        if (world.getGuildByName(name) != null) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_CheckGuildName_AlreadyUsed)));
            return;
        }
        createGuild(chr, name);
    }

    /** Creates the guild with chr as its master and charges the fee. */
    public static Guild createGuild(Char chr, String name) {
        World world = chr.getWorld();
        Field field = chr.getField();
        Guild guild = new Guild(true);
        guild.setLevel(1);
        guild.setName(name);
        guildDao.saveOrUpdate(guild);
        world.addGuild(guild);
        chr.setGuild(guild);
        guild = chr.getGuild(); // setGuild may change the instance
        guild.addMember(chr);
        world.removeRequestor(chr);
        guild.setWorldID(chr.getClient().getWorldId());
        world.addGuild(guild);
        guild.syncNoblesseSkills(false); // Lv. 1's Noblesse skills (granted with the first level)
        guildDao.saveOrUpdate(guild); // save now so the master is persisted
        chr.write(WvsContext.guildResult(GuildResult.createNewGuild(guild, chr)));
        if (field != null) {
            field.broadcastPacket(UserRemote.guildUpdated(chr));
        }
        chr.deductMoney(CREATE_COST, ProgressionLog.Sink.FEES);
        return guild;
    }

    public static void handleInviteGuild(InPacket inPacket, Char chr, Guild guild, GuildMember gm) {
        if (guild == null || gm == null || !gm.hasPermission(GuildPermission.Invite)) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_InviteGuild_Unknown)));
            return;
        }

        Char invited = chr.getWorld().getCharByName(inPacket.decodeString());
        if (invited == null) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_InviteGuild_NotExist)));
            return;
        }

        if (invited.hasChrBlacklisted(chr.getId())) {
            chr.chatMessage(String.format("%s is not accepting invitations.", invited.getName()));
            chr.dispose();
            return;
        }

        if (invited.getGuild() != null) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_InviteGuild_AlreadyInGuild, invited.getName())));
            return;
        }

        if (guild.isFull()) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_InviteGuild_LimitMembers)));
            return;
        }

        guild.addPendingChr(invited);

        invited.write(WvsContext.guildResult(GuildResult.inviteGuildDone(guild, chr)));
        chr.chatMessage("You have invited %s to the guild.", invited.getName());
    }

    public static void handleKickGuild(InPacket inPacket, Char chr, Guild guild, Field field, GuildMember gm) {
        if (guild == null || gm == null || !gm.hasPermission(GuildPermission.Kick)) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_KickGuild_Unknown)));
            return;
        }
        int expelledID = inPacket.decodeInt(); // the name only goes to the client's own yes/no
        var expelledGm = guild.getMemberByCharID(expelledID);

        if (expelledGm == null) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_KickGuild_NotJoined)));
            return;
        }
        if (expelledID == guild.getLeaderID() || expelledID == chr.getId() || expelledGm.getGrade() <= gm.getGrade()) {
            // the master, and members of the same or a higher rank, can't be kicked
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_KickGuild_Unknown)));
            return;
        }

        Char expelled = expelledGm.getChr();
        guild.broadcast(WvsContext.guildResult(GuildResult.leaveGuild(guild, expelledID, expelledGm.getName(), true)));
        if (expelled == null) {
            Char offline = charDao.getById(expelledID);
            guild.removeMember(expelledGm);
            if (offline != null) {
                offline.setGuild(null);
                charDao.saveOrUpdate(offline);
            }
            return; // a deleted character's entry is removed too
        }
        dropGuildStats(expelled, guild);
        guild.removeMember(expelledGm);
        Field theirField = expelled.getField(); // their map, not the kicker's
        if (theirField != null) {
            theirField.broadcastPacket(UserRemote.guildUpdated(expelled));
        }
    }

    public static void handleWithdrawGuild(Char chr, Guild guild, Field field) {
        if (guild == null) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_WithdrawGuild_Unknown)));
            return;
        }
        if (guild.isGuildMaster(chr)) {
            // the master can't simply leave
            if (guild.getMembers().size() > 1) {
                chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_WithdrawGuild_Unknown)));
                chr.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage("You're the guild master. Make another "
                        + "member the master first, or disband the guild.")));
                return;
            }
            guild.disband();
            return;
        }
        var name = chr.getName();
        guild.broadcast(WvsContext.guildResult(GuildResult.leaveGuild(guild, chr.getId(), name, false)));
        dropGuildStats(chr, guild);
        guild.removeMember(chr);
        chr.setGuild(null);
        field.broadcastPacket(UserRemote.guildUpdated(chr));
        if (guild.getMembers().size() == 0) {
            guild.disband();
        }
    }

    /** The master disbands the guild (Req_RemoveGuild). */
    public static void handleRemoveGuild(Char chr, Guild guild) {
        if (guild == null || !guild.isGuildMaster(chr)) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_RemoveGuild_Unknown)));
            return;
        }
        guild.disband(); // drops every online member's guild skill stats (Guild#disband)
    }

    /** Room for 10 more members for 10,000 x (1 + expansions so far) GP, up to 200 (official). */
    public static void handleIncMaxMemberNum(Char chr, Guild guild, GuildMember gm) {
        if (guild == null || gm == null || !guild.isGuildMaster(chr)) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_IncMaxMemberNum_Unknown)));
            return;
        }
        if (guild.getMaxMembers() >= GameConstants.MAX_GUILD_MEMBERS) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_IncMaxMemberNum_Unknown)));
            chr.chatMessage("Your guild already has room for the most members a guild can have.");
            return;
        }
        int cost = getExpansionCost(guild);
        if (guild.getGgp() < cost) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_IncMaxMemberNum_Unknown)));
            chr.chatMessage(String.format("Expanding the guild costs %,d GP (the guild has %,d).", cost, guild.getGgp()));
            return;
        }
        guild.addGgpNow(-cost);
        guild.setMaxMembers(Math.min(GameConstants.MAX_GUILD_MEMBERS, guild.getMaxMembers() + MEMBERS_PER_EXPANSION));
        guild.broadcast(WvsContext.guildResult(GuildResult.incMaxMemberNum(guild)));
        guild.broadcast(WvsContext.guildResult(GuildResult.setGgp(guild)));
        guildDao.saveOrUpdate(guild);
    }

    public static int getExpansionCost(Guild guild) {
        int expansions = Math.max(0, (guild.getMaxMembers() - BASE_MEMBERS) / MEMBERS_PER_EXPANSION);
        return EXPANSION_GP * (1 + expansions);
    }

    /** the daily guild check-in (Req_CheckIn): 30 Contribution once a day. */
    public static void handleCheckIn(Char chr, Guild guild, GuildMember gm) {
        if (guild == null || gm == null) {
            chr.write(WvsContext.guildResult(GuildResult.checkInFailed(2)));
            return;
        }
        if (chr.isDead()) {
            chr.write(WvsContext.guildResult(GuildResult.checkInFailed(5)));
            return;
        }
        Map<String, String> rec = record(chr);
        // once per reset day, as the client's check-in window counts it (GuildContents.checkInDay); the record keeps
        // the same day for the achievements
        String today = net.swordie.ms.util.ResetClock.today().toString();
        int day = net.swordie.ms.client.guild.GuildContents.checkInDayInt();
        if (gm.getCheckInDate() == 0 && today.equals(rec.get("ci"))) {
            gm.setCheckInDate(day); // checked in today before members kept the day
        }
        if (gm.getCheckInDate() == day) {
            chr.write(WvsContext.guildResult(GuildResult.checkInFailed(1))); // "already completed"
            chr.dispose();
            return;
        }
        rec.put("ci", today);
        saveRecord(chr, rec);
        guild.noteCheckIn(gm);
        guild.addCommitmentToChar(chr, CHECK_IN_CONTRIBUTION);
        // every open check-in window counts this member now
        guild.broadcast(WvsContext.guildResult(GuildResult.setMemberCheckIn(guild, gm)));
        int count = guild.getCheckInsToday();
        chr.chatMessage(ChatType.Notice2, String.format("[Guild] You checked in! %d member%s checked in today.",
                count, count == 1 ? " has" : "s have"));
        chr.dispose();
    }

    // ------------------------------------------------------------------------------- hidden record

    static Map<String, String> record(Char chr) {
        Map<String, String> m = new LinkedHashMap<>();
        String raw = chr.getQuestManager().getHiddenRecord(GUILD_QR);
        if (raw != null) {
            for (String part : raw.split(";")) {
                int eq = part.indexOf('=');
                if (eq > 0) {
                    m.put(part.substring(0, eq), part.substring(eq + 1));
                }
            }
        }
        return m;
    }

    static void saveRecord(Char chr, Map<String, String> m) {
        StringBuilder sb = new StringBuilder();
        m.forEach((k, v) -> sb.append(sb.length() == 0 ? "" : ";").append(k).append('=').append(v));
        chr.getQuestManager().setHiddenRecord(GUILD_QR, sb.toString());
    }

    public static void handleSetMemberGrade(InPacket inPacket, Char chr, Guild guild, GuildMember gm) {
        if (guild == null || gm == null || !gm.hasPermission(GuildPermission.SetMemberGrade)) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetMemberGrade_Unknown)));
            chr.write(WvsContext.exclRequest());
            return;
        }
        int id = inPacket.decodeInt();
        byte grade = inPacket.decodeByte();
        var setter = gm;
        gm = guild.getMemberByCharID(id);

        // only members of a lower rank, to a rank below the setter's own; never the master or to master
        if (grade <= Guild.MASTER || grade > guild.getGradeNames().size() || gm == null || id == guild.getLeaderID()
                || gm.getGrade() <= setter.getGrade() || grade <= setter.getGrade()) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetMemberGrade_Unknown)));
            chr.write(WvsContext.exclRequest());
            return;
        }

        gm.setGrade(grade);
        guild.broadcast(WvsContext.guildResult(GuildResult.setMemberGrade(guild, gm)));
    }

    /** Request type 17: byte grade, int count, int charIds; each member as by handleSetMemberGrade. */
    public static void handleSetEveryoneMemberGrade(InPacket inPacket, Char chr, Guild guild, GuildMember gm) {
        if (guild == null || gm == null || !gm.hasPermission(GuildPermission.SetMemberGrade)) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetMemberGrade_Unknown)));
            return;
        }
        byte grade = inPacket.decodeByte();
        int count = inPacket.decodeInt();
        if (grade <= Guild.MASTER || grade > guild.getGradeNames().size() || grade <= gm.getGrade() || count < 0
                || count > guild.getMembers().size()) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetMemberGrade_Unknown)));
            return;
        }
        for (int i = 0; i < count && inPacket.getUnreadAmount() >= 4; i++) {
            int id = inPacket.decodeInt();
            var member = guild.getMemberByCharID(id);
            if (member == null || id == guild.getLeaderID() || member.getGrade() <= gm.getGrade()) {
                continue;
            }
            member.setGrade(grade);
            guild.broadcast(WvsContext.guildResult(GuildResult.setMemberGrade(guild, member)));
        }
    }

    public static void handleAddGrade(InPacket inPacket, Char chr, Guild guild, GuildMember gm) {
        if (guild == null || gm == null || !guild.isGuildMaster(chr)) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_AddGrade_Unknown)));
            chr.write(WvsContext.exclRequest());
            return;
        }

        var index = inPacket.decodeByte() - 1;
        // a new rank goes after the others
        if (index < Guild.MIN_GRADE_SIZE || index >= Guild.GRADE_SIZE || index > guild.getGradeNames().size()) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_AddGrade_Unknown)));
            chr.write(WvsContext.exclRequest());
            return;
        }
        String name = inPacket.decodeString();
        int permission = inPacket.decodeInt();
        writeGrade(guild, index, name, permission);
        guild.broadcast(WvsContext.guildResult(GuildResult.addGrade(guild, gm)));
    }

    public static void handleDeleteGrade(InPacket inPacket, Char chr, Guild guild, GuildMember gm) {
        if (guild == null || gm == null || !guild.isGuildMaster(chr)) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_DeleteGrade_Unknown)));
            chr.write(WvsContext.exclRequest());
            return;
        }

        var index = inPacket.decodeByte() - 1;
        if (index >= guild.getGradePermissions().size() || index < Guild.MIN_GRADE_SIZE) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_DeleteGrade_Unknown)));
            chr.write(WvsContext.exclRequest());
            return;
        }

        if (guild.getMembers().stream().anyMatch(mem -> mem.getGrade() == index + 1)) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_DeleteGrade_Unknown)));
            chr.chatMessage("At least one of your guild members has that rank. Please promote them before deleting the rank.");
            chr.write(WvsContext.exclRequest());
            return;
        }

        var deletedName = guild.getGradeNames().get(index);

        guild.getGradePermissions().remove(index);
        guild.getGradeNames().remove(index);
        for (GuildMember member : guild.getMembers()) {
            if (member.getGrade() > index + 1) {
                member.setGrade(member.getGrade() - 1); // the ranks below move up one
            }
        }
        guild.broadcast(WvsContext.guildResult(GuildResult.deleteGrade(guild, gm, index, deletedName)));
    }

    public static void handleSetGradeNameAndPermissions(InPacket inPacket, Char chr, Guild guild, GuildMember gm) {
        if (guild == null || gm == null || !guild.isGuildMaster(chr)) {
            // only the master edits ranks
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetGradeNameAndPermission_Unknown)));
            return;
        }

        var index = inPacket.decodeByte() - 1;
        if (index < 0 || index >= Guild.GRADE_SIZE || index >= Math.max(guild.getGradeNames().size(),
                Guild.MIN_GRADE_SIZE)) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetGradeNameAndPermission_Unknown)));
            return;
        }
        String name = inPacket.decodeString();
        int sentPermission = inPacket.decodeInt();
        // Keep Guild Master permissions always the same
        Integer permission = index != 0 ? sentPermission : null;
        writeGrade(guild, index, name, permission);
        guild.broadcast(WvsContext.guildResult(GuildResult.setGradeNameAndPermission(guild, gm)));
        chr.write(WvsContext.exclRequest());
    }

    /**
     * changes one rank only, so unused slots aren't created and ranks still in use aren't deleted.
     */
    private static void writeGrade(Guild guild, int index, String name, Integer permission) {
        List<String> names = new ArrayList<>();
        List<Integer> perms = new ArrayList<>();
        for (int i = 0; i < guild.getGradeNames().size(); i++) {
            String n = guild.getGradeNames().get(i);
            names.add(n == null ? "" : n);
            perms.add(i < guild.getGradePermissions().size() ? guild.getGradePermissions().get(i) : 0);
        }
        while (names.size() <= index) {
            names.add("");
            perms.add(0);
        }
        names.set(index, name == null ? "" : name);
        if (permission != null) {
            perms.set(index, permission);
        }
        perms.set(0, -1); // the master can do everything
        int highestUsed = guild.getMembers().stream().mapToInt(GuildMember::getGrade).max().orElse(1);
        while (names.size() > Math.max(Guild.MIN_GRADE_SIZE, highestUsed) && names.get(names.size() - 1).isEmpty()) {
            names.remove(names.size() - 1);
            perms.remove(perms.size() - 1);
        }
        guild.setGradeNames(names.toArray(new String[0]));
        guild.setGradePermissions(perms.stream().mapToInt(Integer::intValue).toArray());
    }

    public static void handleSetMark(InPacket inPacket, Char chr, Guild guild, Field field, GuildMember gm) {
        if (guild == null || gm == null || !gm.hasPermission(GuildPermission.EditMark)) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetMark_Unknown)));
            return;
        }
        var isCustomMark = inPacket.decodeByte() != 0;

        if (!isCustomMark) {
            guild.setMarkBg(inPacket.decodeShort());
            guild.setMarkBgColor(inPacket.decodeByte());
            guild.setMark(inPacket.decodeShort());
            guild.setMarkColor(inPacket.decodeByte());
            guild.setCustomMark(null);
        } else {
            guild.setCustomMarkType(inPacket.decodeByte());
            var customLength = inPacket.decodeInt();

            if (customLength <= 0 || customLength > CUSTOMMARK_MAX_LENGTH) {
                chr.chatMessage("That emblem file is invalid.");
                chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetCustomMark_Unknown)));
                return;
            }
            var customMark = inPacket.decodeArr(customLength);
            if (checkCustomMark(customMark)) {
                guild.setCustomMark(customMark);
                guild.setMarkBg(0);
                guild.setMarkBgColor(0);
                guild.setMark(0);
                guild.setMarkColor(0);
            } else {
                chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetCustomMark_Unknown)));
                return;
            }
        }

        guild.broadcast(WvsContext.guildResult(GuildResult.setMark(guild, gm)));
        // Every online member's map sees the new emblem
        for (GuildMember member : guild.getOnlineMembers()) {
            Char c = member.getChr();
            if (c != null && c.getField() != null) {
                c.getField().broadcastPacket(UserRemote.guildNameChanged(c), c);
                c.getField().broadcastPacket(UserRemote.guildMarkChanged(c), c);
            }
        }
        guildDao.saveOrUpdate(guild);
    }

    public static void handleSkillLevelSetup(InPacket inPacket, Char chr, Guild guild, GuildMember gm) {
        if (guild == null || gm == null || !gm.hasPermission(GuildPermission.ManageSkills)) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetSkill_LevelSet_Unknown)));
            return;
        }
        int skillID = inPacket.decodeInt();
        boolean up = inPacket.decodeByte() != 0;
        if (up) {
            increaseGuildSkillLevel(chr, guild, skillID);
        } else {
            decrementGuildSkill(chr, guild, skillID);
        }
        chr.dispose();
    }

    public static void handleResetSkills(Char chr, Guild guild, GuildMember gm) {
        if (guild == null || gm == null || !gm.hasPermission(GuildPermission.ManageSkills)) {
            chr.chatMessage("You do not have the required permissions to do this.");
            chr.write(WvsContext.exclRequest());
            return;
        }

        if (guild.getSpentSp() <= 0) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetSkill_ResetBattleSkill_Unknown1)));
            chr.write(WvsContext.exclRequest());
            return;
        }
        if (guild.getGgp() < SKILL_RESET_GP) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetSkill_ResetBattleSkill_NotEnoughGP)));
            chr.write(WvsContext.exclRequest());
            return;
        }

        guild.addGgpNow(-SKILL_RESET_GP);

        for (var gs : guild.getSkills().values()) {
            if (SkillConstants.isGuildContentSkill(gs.getSkillID())) {
                changeSkillLevel(guild, gs, 0); // remove the stats while the level is still set
            }
        }

        guild.broadcast(WvsContext.guildResult(GuildResult.setGgp(guild)));
        guild.broadcast(WvsContext.guildResult(GuildResult.resetSkillDone(guild))); // the client clears its tree
        guildDao.saveOrUpdate(guild);
        chr.write(WvsContext.exclRequest());
    }

    public static void decrementGuildSkill(Char chr, Guild guild, int skillID) {
        GuildSkill gs = guild.getSkillById(skillID);
        if (gs == null || gs.getLevel() == 0) {
            chr.getOffenseManager().addOffense(String.format("Character %d tried to decrement a guild skill without that skill existing (id %d).",
                    chr.getId(), skillID));
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetSkill_LevelSet_Unknown)));
            return;
        }
        if (guild.getGgp() < GameConstants.GGP_FOR_SKILL_RESET) {
            chr.getOffenseManager().addOffense(String.format("Character %d tried to decrement a guild skill without having enough GGP (needed %d, has %d).",
                    chr.getId(), GameConstants.GGP_FOR_SKILL_RESET, guild.getGgp()));
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetSkill_ResetBattleSkill_NotEnoughGP)));
            return;
        }
        guild.addGgpNow(-GameConstants.GGP_FOR_SKILL_RESET);

        changeSkillLevel(guild, gs, gs.getLevel() - 1);

        guild.broadcast(WvsContext.guildResult(GuildResult.setSkill(guild, gs, chr.getId())));
        guild.broadcast(WvsContext.guildResult(GuildResult.setGgp(guild)));
    }

    public static void increaseGuildSkillLevel(Char chr, Guild guild, int skillID) {
        if (!SkillConstants.isGuildContentSkill(skillID) && !SkillConstants.isGuildNoblesseSkill(skillID)) {
            chr.getOffenseManager().addOffense(String.format("Character %d tried to add an invalid guild skill (%d)", chr.getId(), skillID));
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_UseSkill_Err)));
            return;
        }
        int spentSp = guild.getSpentSp();

        if (SkillConstants.isGuildContentSkill(skillID)) {
            if (spentSp >= guild.getSkillSp()) {
                chr.getOffenseManager().addOffense(String.format("Character %d tried to add a guild skill without enough sp (spent %d, level %d).",
                        chr.getId(), spentSp, guild.getLevel()));
                chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetSkill_LevelSet_Unknown)));
                return;
            }
        } else if (guild.getBattleSp() - guild.getSpentBattleSp() <= 0) { // Noblesse
            chr.getOffenseManager().addOffense(String.format("Character %d tried to add a guild battle skill without enough sp (spent %d).",
                    chr.getId(), guild.getSpentBattleSp()));
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetSkill_LevelSet_Unknown)));
            return;
        }

        SkillInfo si = SkillData.getSkillInfoById(skillID);
        if (si == null) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetSkill_LevelSet_Unknown)));
            return;
        }
        if (SkillConstants.isGuildContentSkill(skillID) && spentSp < si.getReqTierPoint()) {
            chr.getOffenseManager().addOffense(String.format("Character %d tried to add a guild skill without enough sp spent (spent %d, needed %d).",
                    chr.getId(), spentSp, si.getReqTierPoint()));
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetSkill_LevelSet_Unknown)));
            return;
        }
        int reqGuildLv = REQ_GUILD_LV.getOrDefault(skillID, 0);
        if (guild.getLevel() < reqGuildLv) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetSkill_LevelSet_Unknown)));
            chr.chatMessage(String.format("That skill needs a Lv. %d guild.", reqGuildLv));
            return;
        }

        for (Map.Entry<Integer, Integer> entry : si.getReqSkills().entrySet()) {
            int reqSkillID = entry.getKey();
            int reqSlv = entry.getValue();
            GuildSkill gs = guild.getSkillById(reqSkillID);
            if (gs == null || gs.getLevel() < reqSlv) {
                chr.getOffenseManager().addOffense(String.format("Character %d tried to add a guild skill without having the required "
                                + "skill first (req id %d, needed %d, has %d).",
                        chr.getId(), reqSkillID, reqSlv, gs == null ? 0 : gs.getLevel()));
                chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetSkill_LevelSet_Unknown)));
                return;
            }
        }

        GuildSkill skill = guild.getSkillById(skillID);
        if (skill != null && skill.getLevel() >= si.getMaxLevel()) {
            chr.chatMessage("That skill is already at its max level.");
            chr.dispose();
            return;
        }
        if (skill == null) {
            skill = new GuildSkill();
            skill.setBuyCharacterName(chr.getName());
            skill.setExtendCharacterName(chr.getName());
            skill.setSkillID(skillID);
            guild.addGuildSkill(skill);
        } else {
            // Remove base stats as the skill already existed
            for (GuildMember guildMember : guild.getOnlineMembers()) {
                if (guildMember.getChr() != null) {
                    guildMember.getChr().removeFromBaseStatCache(skill);
                }
            }
        }
        changeSkillLevel(guild, skill, skill.getLevel() + 1); // the old level's stats out, the new one's in
        guild.broadcast(WvsContext.guildResult(GuildResult.setSkill(guild, skill, chr.getId())));
        guildDao.saveOrUpdate(guild);
    }

    //TODO remove this function after guild skill bandaid fix is replaced
    public static void setGuildSkillLevel(Char chr, Guild guild, int skillID, int skillLevel) {
        SkillInfo si = SkillData.getSkillInfoById(skillID);
        //if chr is null, it comes from World.java, so we don't need to do offense manager stuff
        if (chr != null) {
            if (!SkillConstants.isGuildContentSkill(skillID) && !SkillConstants.isGuildNoblesseSkill(skillID)) {
                chr.getOffenseManager().addOffense(String.format("Character %d tried to add an invalid guild skill (%d)", chr.getId(), skillID));
                chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_UseSkill_Err)));
                return;
            }
            int spentSp = guild.getSpentSp();

            if (SkillConstants.isGuildContentSkill(skillID)) {
                if (spentSp >= guild.getSkillSp()) {
                    chr.getOffenseManager().addOffense(String.format("Character %d tried to add a guild skill without enough sp (spent %d, level %d).",
                            chr.getId(), spentSp, guild.getLevel()));
                    chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetSkill_LevelSet_Unknown)));
                    return;
                }
            }
            /*else if (guild.getBattleSp() - guild.getSpentBattleSp() <= 0) { // Noblesse
            chr.getOffenseManager().addOffense(String.format("Character %d tried to add a guild battle skill without enough sp (spent %d).",
                    chr.getId(), guild.getSpentBattleSp()));
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetSkill_LevelSet_Unknown)));
            return;
            }*/


            if (SkillConstants.isGuildContentSkill(skillID) && spentSp < si.getReqTierPoint()) {
                chr.getOffenseManager().addOffense(String.format("Character %d tried to add a guild skill without enough sp spent (spent %d, needed %d).",
                        chr.getId(), spentSp, si.getReqTierPoint()));
                chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetSkill_LevelSet_Unknown)));
                return;
            }

            for (Map.Entry<Integer, Integer> entry : si.getReqSkills().entrySet()) {
                int reqSkillID = entry.getKey();
                int reqSlv = entry.getValue();
                GuildSkill gs = guild.getSkillById(reqSkillID);
                if (gs == null || gs.getLevel() < reqSlv) {
                    chr.getOffenseManager().addOffense(String.format("Character %d tried to add a guild skill without having the required "
                                    + "skill first (req id %d, needed %d, has %d).",
                            chr.getId(), reqSkillID, reqSlv, gs == null ? 0 : gs.getLevel()));
                    chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetSkill_LevelSet_Unknown)));
                    return;
                }
            }
        }

        GuildSkill skill = guild.getSkillById(skillID);
        if (skill == null) {
            skill = new GuildSkill();
            if(chr != null) {
                skill.setBuyCharacterName(chr.getName());
                skill.setExtendCharacterName(chr.getName());
            }
            skill.setSkillID(skillID);
            guild.addGuildSkill(skill);
        }
        if(chr != null) {
            if (skill.getLevel() >= si.getMaxLevel()) {
                chr.chatMessage("That skill is already at its max level.");
                chr.dispose();
                return;
            }
        }

        changeSkillLevel(guild, skill, skillLevel); // the old level's stats out, the new one's in

        if(chr != null) {
            guild.broadcast(WvsContext.guildResult(GuildResult.setSkill(guild, skill, chr.getId())));
        }
    }

    public static void handleBattleSkillOpen(Char chr, Guild guild, GuildMember gm) {
        if (guild == null || gm == null || !gm.hasPermission(GuildPermission.UseSkills)) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_UseSkill_Err)));
            return;
        }
        chr.write(WvsContext.guildResult(GuildResult.battleSkillOpen(guild)));
    }

    public static void handleUseActiveSkill(Client c, InPacket inPacket, Char chr, Guild guild, GuildMember gm) {
        if (guild == null || gm == null || !gm.hasPermission(GuildPermission.UseSkills)) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_UseSkill_Err)));
            return;
        }
        var skillID = inPacket.decodeInt();
        if (inPacket.getUnreadAmount() >= 5) {
            inPacket.decodeByte();
        }

        if (isOnCooldown(chr, skillID)) {
            return;
        }

        GuildSkill gs = guild.getSkillById(skillID);
        if (gs == null || gs.getLevel() == 0) {
            chr.getOffenseManager().addOffense(String.format("Character %d tried to use a guild skill without having it (id %d).",
                    chr.getId(), skillID));
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetSkill_LevelSet_Unknown)));
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(skillID);
        if (si == null) {
            chr.dispose();
            return;
        }
        // On My Way, I Summon Thee and Rise, Minions!
        if (skillID == ON_MY_WAY || skillID == I_SUMMON_THEE) {
            int target = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : 0;
            if (target != 0 && guild.getMemberByCharID(target) != null && target != chr.getId()) {
                String error = useMoveSkill(chr, skillID, target);
                if (error != null) {
                    chr.chatMessage(error);
                    chr.dispose();
                }
            } else {
                // the guildmate wasn't in the request: pick one
                Map<String, Object> props = new HashMap<>();
                props.put("guildSkill", skillID);
                chr.getScriptManager().startScript(0, "guild_skill", ScriptType.Npc, props);
            }
            return;
        }
        if (skillID == RISE_MINIONS) {
            riseMinions(chr, guild, gs.getLevel());
            return;
        }
        chr.addSkillCoolTime(skillID, 1000 * si.getValue(SkillStat.cooltime, gs.getLevel()));

        var sui = new SkillUseInfo();
        sui.skillID = skillID;
        sui.slv = gs.getLevel();

        chr.getJobHandler().handleSkill(chr, chr.getTemporaryStatManager(), skillID, gs.getLevel(), inPacket, sui);
    }

    private static boolean isOnCooldown(Char chr, int skillID) {
        var skillCooldown = chr.getSkillCooltimeBySkillId(skillID);
        if (skillCooldown > System.currentTimeMillis() && !chr.hasSkillCDBypass()) {
            // rounded up, at least 1
            long minutes = Math.max(1, (skillCooldown - System.currentTimeMillis() + 59999) / 60000);
            chr.chatMessage(String.format("That skill is still on cooldown: %d minute%s remaining.", minutes,
                    minutes == 1 ? "" : "s"));
            chr.dispose();
            return true;
        }
        return false;
    }

    private static void startGuildSkillCooldown(Char chr, int skillID) {
        Guild guild = chr.getGuild();
        GuildSkill gs = guild == null ? null : guild.getSkillById(skillID);
        SkillInfo si = SkillData.getSkillInfoById(skillID);
        if (gs != null && si != null) {
            chr.addSkillCoolTime(skillID, 1000 * si.getValue(SkillStat.cooltime, gs.getLevel()));
        }
    }

    /** Fields a guild skill can move people out of or into: normal maps that allow teleporting. */
    private static boolean canMoveWithSkill(Field field) {
        if (field == null || !field.isChannelField()) {
            return false;
        }
        return !net.swordie.ms.constants.FieldConstants.teleportWouldBreak(null, field);
    }

    /** @return the guildmates in the same channel (not chr) that On My Way / I Summon Thee can be used on */
    public static List<Char> getMoveTargets(Char chr) {
        List<Char> res = new ArrayList<>();
        Guild guild = chr.getGuild();
        if (guild == null) {
            return res;
        }
        for (GuildMember gm : guild.getOnlineMembers()) {
            Char c = gm.getChr();
            if (c != null && c != chr && c.getChannel() == chr.getChannel() && c.getField() != null) {
                res.add(c);
            }
        }
        return res;
    }

    /**
     * On My Way (go to a guildmate) and I Summon Thee (ask a guildmate to come), same channel only (Skill.wz
     * 91001016 / 91001017). @return an error to show, or null when it went through
     */
    public static String useMoveSkill(Char chr, int skillID, int targetID) {
        Guild guild = chr.getGuild();
        GuildMember gm = guild == null ? null : guild.getMemberByCharID(chr.getId());
        GuildSkill gs = guild == null ? null : guild.getSkillById(skillID);
        if (gm == null || gs == null || gs.getLevel() <= 0 || !gm.hasPermission(GuildPermission.UseSkills)) {
            return "You can't use that guild skill.";
        }
        if (chr.getSkillCooltimeBySkillId(skillID) > System.currentTimeMillis() && !chr.hasSkillCDBypass()) {
            return "That skill is still on cooldown.";
        }
        GuildMember targetGm = guild.getMemberByCharID(targetID);
        Char target = targetGm == null ? null : targetGm.getChr();
        if (target == null || target == chr || target.getChannel() != chr.getChannel()) {
            return "That guildmate isn't online in this channel.";
        }
        if (chr.isDead() || target.isDead()) {
            return "That can't be done right now.";
        }
        if (skillID == ON_MY_WAY) {
            if (!canMoveWithSkill(chr.getField()) || !canMoveWithSkill(target.getField())) {
                return "You can't move to that guildmate from here, or to where they are.";
            }
            startGuildSkillCooldown(chr, skillID);
            moveTo(chr, target.getField(), target.getPosition());
            return null;
        }
        // I Summon Thee: they're asked first; the cooldown starts either way (official)
        if (!canMoveWithSkill(chr.getField()) || !canMoveWithSkill(target.getField())) {
            return "That guildmate can't be summoned from there, or to here.";
        }
        if (target.getScriptManager().isActive(ScriptType.Npc)) {
            return target.getName() + " is busy right now. Try again in a moment.";
        }
        startGuildSkillCooldown(chr, skillID);
        Map<String, Object> props = new HashMap<>();
        props.put("summonerId", chr.getId());
        props.put("summonerName", chr.getName());
        props.put("summonField", chr.getFieldID());
        target.getScriptManager().startScript(0, "guild_summon", ScriptType.Npc, props);
        chr.chatMessage(ChatType.Notice2, String.format("[Guild] You asked %s to come to you.", target.getName()));
        return null;
    }

    /** I Summon Thee, accepted: to the summoner, if they're still in that map. @return an error, or null */
    public static String acceptSummon(Char chr, int summonerId, int fieldId) {
        Guild guild = chr.getGuild();
        GuildMember gm = guild == null ? null : guild.getMemberByCharID(summonerId);
        Char summoner = gm == null ? null : gm.getChr();
        if (summoner == null || summoner.getChannel() != chr.getChannel() || summoner.getFieldID() != fieldId
                || !canMoveWithSkill(summoner.getField()) || !canMoveWithSkill(chr.getField()) || chr.isDead()) {
            return "The summon can't be answered any more.";
        }
        moveTo(chr, summoner.getField(), summoner.getPosition());
        return null;
    }

    private static void moveTo(Char chr, Field field, net.swordie.ms.util.Position pos) {
        if (chr.getField() != field) {
            chr.setInstance(null);
            chr.warp(field);
        }
        chr.write(FieldPacket.teleport(pos, chr));
    }

    /** Rise, Minions!: revives the party in this map, with full MP for guildmates (Skill.wz 91001020). */
    private static void riseMinions(Char chr, Guild guild, int slv) {
        List<Char> who = new ArrayList<>();
        who.add(chr);
        if (chr.getParty() != null) {
            who.addAll(chr.getParty().getPartyMembersInSameField(chr));
        }
        int revived = 0;
        for (Char c : who) {
            if (c.isDead()) {
                c.heal(c.getMaxHP(), true, true);
                revived++;
            }
            if (c.getGuild() == guild) {
                c.healMP(c.getMaxMP());
            }
        }
        startGuildSkillCooldown(chr, RISE_MINIONS);
        chr.chatMessage(ChatType.Notice2, revived > 0 ? String.format("[Guild] Rise, Minions! revived %d party "
                + "member%s.", revived, revived == 1 ? "" : "s") : "[Guild] Rise, Minions! No one needed reviving.");
        chr.dispose();
    }

    // ------------------------------------------------------------------------ passive guild skill effects

    /** @return the level of a guild skill the character's guild has (0 if none) */
    public static int skillLevel(Char chr, int skillId) {
        Guild guild = chr == null ? null : chr.getGuild();
        GuildSkill gs = guild == null ? null : guild.getSkillById(skillId);
        return gs == null ? 0 : Math.max(0, gs.getLevel());
    }

    /** @return a stat of a guild skill the character's guild has, at its level (0 if none) */
    public static int skillValue(Char chr, int skillId, SkillStat stat) {
        int lv = skillLevel(chr, skillId);
        if (lv <= 0) {
            return 0;
        }
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        return si == null ? 0 : si.getValue(stat, lv);
    }

    /**
     * Merchandising: shop prices -disCountR%, and mesos from selling +disCountR%, but not for items that sell back
     * for 70% or more of what the shop asks (Skill.wz 91000008). @return the mesos after the guild's bonus
     */
    public static long applyMerchandising(Char chr, long mesos, long shopUnitPrice, long resellUnitPrice,
                                          boolean buying) {
        int r = skillValue(chr, MERCHANDISING, SkillStat.disCountR);
        if (r <= 0 || mesos <= 0) {
            return mesos;
        }
        if (shopUnitPrice > 0 && resellUnitPrice * 10 >= shopUnitPrice * 7) {
            return mesos;
        }
        return buying ? mesos - mesos * r / 100 : mesos + mesos * r / 100;
    }

    /** What an item sells to a shop for, per piece (equips per item). */
    public static long getResellPrice(int itemId) {
        if (net.swordie.ms.constants.ItemConstants.isEquip(itemId)) {
            var equip = ItemData.getEquipDeepCopy(itemId, false);
            return equip == null || equip.getInfo() == null ? 0 : equip.getInfo().getPrice();
        }
        var info = ItemData.getItemInfoByID(itemId);
        return info == null ? 0 : info.getPrice();
    }

    /** Special Power: Star Force +starX (Skill.wz: 15 for 91000035, 25 for 91000016; starX isn't a loaded stat). */
    public static int getStarForceBonus(Char chr) {
        return skillLevel(chr, SPECIAL_POWER_2) > 0 ? 25 : skillLevel(chr, SPECIAL_POWER) > 0 ? 15 : 0;
    }

    /** Special Power: Arcane Power +arcX, once the character has Arcane Power of their own (A Greater Power). */
    public static int getArcaneForceBonus(Char chr, int ownArc) {
        return ownArc > 0 ? Math.max(skillValue(chr, SPECIAL_POWER, SkillStat.arcX),
                skillValue(chr, SPECIAL_POWER_2, SkillStat.arcX)) : 0;
    }

    /** Guild Expertise: +5% EXP from normal monsters Lv. 101-200. */
    public static int getExpertiseExpR(Char chr, int mobLevel, boolean boss) {
        return !boss && mobLevel >= 101 && mobLevel <= 200 ? 5 * skillLevel(chr, GUILD_EXPERTISE) : 0;
    }

    /**
     * Banner of Plenty I-III: each week, G Potions and Blessings of the Guild that last until that Sunday. Handed out
     * on login (the week is remembered, so once a week).
     */
    public static void giveWeeklyBanner(Char chr) {
        Guild guild = chr.getGuild();
        if (guild == null || skillLevel(chr, BANNER_OF_PLENTY_1) <= 0) {
            return;
        }
        LocalDate monday = net.swordie.ms.util.ResetClock.weekStart(DayOfWeek.MONDAY);
        Map<String, String> rec = record(chr);
        if (monday.toString().equals(rec.get("bp"))) {
            return;
        }
        int potions = 0, blessings = 0;
        for (int id : new int[]{BANNER_OF_PLENTY_1, BANNER_OF_PLENTY_2, BANNER_OF_PLENTY_3}) {
            potions += skillValue(chr, id, SkillStat.x);
            blessings += skillValue(chr, id, SkillStat.y);
        }
        if (potions <= 0 && blessings <= 0) {
            return; // not in the v271 data
        }
        if ((potions > 0 && !chr.canHold(G_POTION, potions))
                || (blessings > 0 && !chr.canHold(BLESSING_OF_THE_GUILD, blessings))) {
            chr.chatMessage(ChatType.Notice2, "[Guild] Banner of Plenty: make room in your Use inventory and log in "
                    + "again to get this week's guild potions.");
            return;
        }
        LocalDateTime sundayNight = monday.plusDays(6).atTime(23, 59, 59);
        giveUntil(chr, G_POTION, potions, sundayNight);
        giveUntil(chr, BLESSING_OF_THE_GUILD, blessings, sundayNight);
        rec.put("bp", monday.toString());
        saveRecord(chr, rec);
        chr.chatMessage(ChatType.Notice2, String.format("[Guild] Banner of Plenty: %d G Potions and %d Blessings of "
                + "the Guild for this week (until Sunday).", potions, blessings));
    }

    private static void giveUntil(Char chr, int itemId, int quantity, LocalDateTime until) {
        if (quantity <= 0) {
            return;
        }
        Item item = ItemData.getItemDeepCopy(itemId);
        if (item == null) {
            return;
        }
        item.setQuantity(quantity);
        item.setDateExpire(FileTime.fromDate(until));
        chr.addItemToInventory(item);
    }

    // -------------------------------------------------------------------------------- Lenario (alliances)

    /**
     * Creates an alliance (Lenario, the Guild Base): a party of 2-5 guild masters in the same map, none of them in an
     * alliance; the party leader pays 5,000,000 mesos and becomes the alliance master. @return an error, or null
     */
    public static String createAlliance(Char chr, String name) {
        var party = chr.getParty();
        Guild guild = chr.getGuild();
        if (guild == null || !guild.isGuildMaster(chr) || party == null || party.getPartyLeaderID() != chr.getId()) {
            return "Only a party leader who is a guild master can do that.";
        }
        List<Char> masters = new ArrayList<>();
        masters.add(chr);
        masters.addAll(party.getPartyMembersInSameField(chr));
        if (masters.size() < 2 || masters.size() > ALLIANCE_MAX_GUILDS
                || party.getOnlineMembers().size() != masters.size()) {
            return "Bring a party of 2 to 5 guild masters, all here in this map.";
        }
        List<Guild> guilds = new ArrayList<>();
        for (Char c : masters) {
            Guild g = c.getGuild();
            if (g == null || !g.isGuildMaster(c) || guilds.contains(g)) {
                return "Everyone in the party has to be the master of a different guild.";
            }
            if (g.getAlliance() != null) {
                return String.format("[%s] is already in an alliance.", g.getName());
            }
            guilds.add(g);
        }
        if (name == null || name.length() < 2 || name.length() > 12 || !Util.isDigitLetterString(name)
                || ForbiddenWordsData.isForbiddenIGN(name)) {
            return "An alliance name is 2 to 12 letters and numbers.";
        }
        World world = chr.getWorld();
        if (world == null || world.getAlliance(name) != null) {
            return "That name is already taken.";
        }
        if (chr.getMoney() < ALLIANCE_COST) {
            return "Creating an alliance costs 5,000,000 mesos.";
        }
        chr.deductMoney(ALLIANCE_COST, ProgressionLog.Sink.FEES);

        Alliance alliance = new Alliance(true);
        alliance.setName(name);
        alliance.setNotice("");
        alliance.setMaxMemberNum(ALLIANCE_MAX_GUILDS);
        alliance.setWorld(world);
        allianceDao.saveOrUpdate(alliance); // for its id
        for (Guild g : guilds) {
            g.setAlliance(alliance);
            alliance.getGuilds().add(g);
            for (GuildMember m : g.getMembers()) {
                m.setAllianceGrade(defaultAllianceGrade(g, m));
            }
        }
        guild.getGuildLeader().setAllianceGrade(1);
        allianceDao.saveOrUpdate(alliance);
        world.addAlliance(alliance);
        alliance.broadcast(net.swordie.ms.connection.packet.AlliancePacket.created(alliance));
        alliance.updateToMembers();
        return null;
    }

    /** Alliance ranks: 1 master, 2 the other guild masters, 3-5 everyone else (by their guild rank). */
    public static int defaultAllianceGrade(Guild guild, GuildMember member) {
        if (guild.getLeaderID() == member.getCharID()) {
            return 2;
        }
        return Math.min(5, Math.max(3, member.getGrade() + 1));
    }

    /**
     * Request type 49, the guild search window. Type 4 with an empty text is the window opening (recruiting guilds,
     * the 5 highest-level ones featured); otherwise the text is matched against guild names (type/mode 1), master
     * names (2) or both, exactly when the client asks for it. The header is echoed back as the client expects.
     */
    public static void handleSearch(InPacket inPacket, Char chr, World world) {
        var header = new net.swordie.ms.connection.OutPacket();
        byte type = inPacket.decodeByte();
        byte mode = inPacket.decodeByte();
        String query = inPacket.decodeString();
        byte exactByte = inPacket.decodeByte();
        header.encodeByte(type);
        header.encodeByte(mode);
        header.encodeString(query);
        header.encodeByte(exactByte);
        for (int i = 0; i < 2; i++) {
            if (i == 1) {
                header.encodeByte(inPacket.decodeByte());
            }
            boolean hasFilter = inPacket.decodeByte() != 0;
            header.encodeByte(hasFilter);
            if (hasFilter) {
                header.encodeArr(inPacket.decodeArr(19)); // byte, int, long, byte, byte, int
            }
        }
        boolean exact = exactByte != 0;
        String q = query == null ? "" : query.trim().toLowerCase();
        int by = mode != 0 ? mode : type; // 1 name, 2 master, else either
        Guild own = chr.getGuild();
        List<Guild> found = new ArrayList<>();
        for (Guild g : world.getGuilds().values()) {
            GuildMember leader = g.getGuildLeader();
            if (g == own || g.getMembers().isEmpty() || leader == null) {
                continue;
            }
            if (q.isEmpty()) {
                if (g.isAppliable()) {
                    found.add(g);
                }
                continue;
            }
            boolean name = by != 2 && matches(g.getName(), q, exact);
            boolean master = by != 1 && matches(leader.getName(), q, exact);
            if (name || master) {
                found.add(g);
            }
        }
        found.sort(java.util.Comparator.comparingInt(Guild::getLevel).reversed()
                .thenComparing(java.util.Comparator.comparingInt((Guild g) -> g.getMembers().size()).reversed()));
        if (found.size() > 100) {
            found = new ArrayList<>(found.subList(0, 100));
        }
        List<Guild> featured = new ArrayList<>();
        if (type == 4 && mode == 0 && q.isEmpty()) {
            featured.addAll(found.subList(0, Math.min(5, found.size())));
            found.removeAll(featured);
        }
        chr.write(WvsContext.guildSearchResult(header.getData(), featured, found));
    }

    private static boolean matches(String s, String lowerQuery, boolean exact) {
        if (s == null) {
            return false;
        }
        return exact ? s.equalsIgnoreCase(lowerQuery) : s.toLowerCase().contains(lowerQuery);
    }

    private static boolean checkCustomMark(byte[] customMark) {
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(customMark));
            BufferedImage img = ImageIO.read(inputStream);
            return img.getHeight() == CUSTOM_MARK_DIMENSIONS && img.getWidth() == CUSTOM_MARK_DIMENSIONS;
        } catch (Exception e) {
            return false;
        }
    }

    public static void handleSetNotice(InPacket inPacket, Char chr, Guild guild, GuildMember gm) {
        if (guild == null || gm == null || !gm.hasPermission(GuildPermission.EditGreeting)) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetNotice_Unkown)));
            return;
        }

        var notice = inPacket.decodeString();
        if (notice.length() > 150) {
            chr.boxMessage("That notice is too long.");
            chr.dispose();
            return;
        }

        guild.setNotice(notice);
        for (GuildMember member : guild.getMembers()) {
            member.setNoticeUnread(member != gm);
        }
        guild.broadcast(WvsContext.guildResult(GuildResult.setNoticeDone(guild, gm)));
    }

    public static void handleAdmission(InPacket inPacket, Char chr, Guild guild, GuildMember gm) {
        if (guild == null || gm == null || !gm.hasPermission(GuildPermission.ManageAdmission)) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetAdmission_Unknown)));
            return;
        }

        var appliable = inPacket.decodeByte() != 0;
        var activeTimes = inPacket.decodeInt();
        var mainActivity = inPacket.decodeInt();
        var ageGroup = inPacket.decodeInt();

        guild.setAppliable(appliable);
        guild.setActiveTimes(activeTimes);
        guild.setMainActivity(mainActivity);
        guild.setAgeGroup(ageGroup);

        guild.broadcast(WvsContext.guildResult(GuildResult.setAdmission(guild, gm)));
    }

    public static void handleChangeGuildMaster(Char chr, InPacket inPacket, Guild guild, GuildMember gm) {
        if (guild == null || gm == null || !guild.isGuildMaster(chr)) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_ChangeMaster_Unknown)));
            return;
        }
        var newGmId = inPacket.decodeInt();
        var newGm = guild.getMemberByCharID(newGmId);

        int job = newGm == null ? 0 : newGm.getJob();
        if (newGm == null || newGm == gm || newGmId == chr.getId()
                || job == 13000 || job == 13001 || job == 13100 || job == 13500) { // Pink Bean / Yeti can't lead
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_ChangeMaster_Unknown)));
            return;
        }

        transferMaster(guild, gm, newGm);
    }

    /** Makes newGm the master and the old master a junior master; in an alliance the new one takes over the
     * old one's alliance rank. */
    private static void transferMaster(Guild guild, GuildMember gm, GuildMember newGm) {
        guild.setLeader(newGm);
        newGm.setGrade(Guild.MASTER);
        gm.setGrade(Guild.JR_MASTER);

        // In an alliance, the new master takes the old one's alliance rank (master or junior master)
        boolean allianceMaster = false;
        Alliance alliance = guild.getAlliance();
        if (alliance != null) {
            allianceMaster = gm.getAllianceGrade() == 1;
            newGm.setAllianceGrade(Math.min(gm.getAllianceGrade(), 2));
            gm.setAllianceGrade(3);
        }

        guild.broadcast(WvsContext.guildResult(GuildResult.changeMaster(guild, gm, newGm, allianceMaster)));
        if (alliance != null) {
            alliance.updateToMembers();
        }
        guildDao.saveOrUpdate(guild);
    }

    /** The invitee declined (or their client turned the invitation down): the inviter hears why. */
    public static void handleInviteGuildRejected(Char chr, GuildType reason, InPacket inPacket) {
        var inviterName = inPacket.decodeString();
        var inviter = chr.getWorld().getCharByName(inviterName);

        if (inviter != null) {
            inviter.write(WvsContext.guildResult(GuildResult.msg(reason, chr.getName())));
            var guild = inviter.getGuild();
            if (guild != null) {
                guild.removePendingChar(chr);
            }
        }
        chr.chatMessage("You have the denied the guild invitation.");
    }

    public static void handleResetGuildBattleSkill(Char chr, Guild guild, GuildMember gm) {
        if (guild == null || gm == null || !gm.hasPermission(GuildPermission.ManageSkills)) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetSkill_ResetBattleSkill_Unknown)));
            return;
        }

        var reqGgpForReset = 4;
        if (guild.getGgp() < reqGgpForReset) {
            chr.write(WvsContext.guildResult(GuildResult.msg(GuildType.Res_SetSkill_ResetBattleSkill_NotEnoughGP)));
            return; // not enough GP
        }

        for (var skill : guild.getSkills().values()) {
            if (!SkillConstants.isGuildNoblesseSkill(skill.getSkillID())) {
                changeSkillLevel(guild, skill, 0); // with the online members' stats
            }
        }
        guild.addGgpNow(-reqGgpForReset);

        guild.broadcast(WvsContext.guildResult(GuildResult.resetBattleSkillDone(guild)));
        guild.updateToMembers();
        chr.dispose();
    }

    // ------------------------------------------------------------------------------- part 2 request types

    /** Admin path (!guildmaster): makes the member the guild's master, as a master's own hand-over would. */
    public static void adminSetMaster(Guild guild, GuildMember newGm) {
        GuildMember leader = guild.getMemberByCharID(guild.getLeaderID());
        if (leader == newGm) {
            return;
        }
        if (leader == null) {
            guild.setLeader(newGm);
            newGm.setGrade(Guild.MASTER);
            guild.broadcastLoad();
            guildDao.saveOrUpdate(guild);
            return;
        }
        transferMaster(guild, leader, newGm);
    }

    /** Request 21: a member takes the guild over from a master away for Guild.LEADER_CLAIM_DAYS. */
    public static void handleClaimMaster(Char chr, Guild guild, GuildMember gm) {
        if (guild == null || gm == null) {
            chr.write(WvsContext.guildResult(GuildResult.changeMasterFailed(0)));
            return;
        }
        GuildMember leader = guild.getMemberByCharID(guild.getLeaderID());
        if (leader == gm || guild.getLeaderID() == chr.getId()) {
            chr.write(WvsContext.guildResult(GuildResult.changeMasterFailed(4))); // "Already a guild leader."
            return;
        }
        if (leader != null && (leader.isOnline() || guild.getLeaderClaimTime().toMillis() > FileTime.currentTime().toMillis())) {
            chr.write(WvsContext.guildResult(GuildResult.changeMasterFailed(6))); // master logged in / not away long enough
            return;
        }
        int job = gm.getJob();
        if (job == 13000 || job == 13001 || job == 13100 || job == 13500) { // Pink Bean / Yeti can't lead
            chr.write(WvsContext.guildResult(GuildResult.changeMasterFailed(0)));
            return;
        }
        if (leader == null) {
            guild.setLeader(gm);
            gm.setGrade(Guild.MASTER);
            guild.broadcastLoad();
            guildDao.saveOrUpdate(guild);
            return;
        }
        transferMaster(guild, leader, gm);
    }

    private static final Map<Integer, Long> lastBoardAlarm = new java.util.concurrent.ConcurrentHashMap<>();

    /** Request 39: a notice was posted with "Alarm" ticked; the online members hear of it. */
    public static void handleBoardAlarm(Char chr, Guild guild, GuildMember gm) {
        if (guild == null || gm == null) {
            return;
        }
        long now = System.currentTimeMillis();
        Long last = lastBoardAlarm.put(chr.getId(), now);
        if (last != null && now - last < 60_000) {
            return;
        }
        String line = String.format("[Guild] %s posted a new notice on the guild board.", chr.getName());
        for (GuildMember member : guild.getOnlineMembers()) {
            Char other = member.getChr();
            if (other != null && other != chr) {
                other.chatMessage(ChatType.GroupGuild, line);
            }
        }
    }

    /** Request 46: the custom emblem of a guild one of the visible characters wears. */
    public static void handleCustomMarkRequest(Char chr, InPacket inPacket, World world) {
        int guildId = inPacket.decodeInt();
        inPacket.decodeInt();
        Guild guild = world == null ? null : world.getGuildByID(guildId);
        if (guild == null || guild.getCustomMarkVersion() == 0) {
            return;
        }
        chr.write(WvsContext.guildResult(GuildResult.customMark(guild)));
        Field field = chr.getField();
        if (field != null) {
            for (Char other : field.getChars()) {
                if (other != chr && other.getGuild() == guild) {
                    chr.write(UserRemote.guildMarkChanged(other));
                }
            }
        }
    }

    /** Request 58: the alliance master (or a guild master in it) moves a member's alliance rank one step. */
    public static void handleAllianceSetMemberGrade(Char chr, InPacket inPacket, Guild guild, GuildMember gm) {
        int charId = inPacket.decodeInt();
        boolean promote = inPacket.decodeByte() != 0;
        int guildId = inPacket.decodeInt();
        Alliance alliance = guild == null ? null : guild.getAlliance();
        if (alliance == null || gm == null || gm.getAllianceGrade() > 2) {
            return;
        }
        Guild targetGuild = alliance.getGuildByID(guildId);
        GuildMember target = targetGuild == null ? null : targetGuild.getMemberByCharID(charId);
        if (target == null || target == gm) {
            return;
        }
        int grade = target.getAllianceGrade();
        int newGrade = grade + (promote ? -1 : 1);
        // ranks 3-5 move between 2 and 5, and never to or above the setter's own rank
        if (grade < 3 || newGrade < 2 || newGrade > 5 || newGrade <= gm.getAllianceGrade()) {
            return;
        }
        target.setAllianceGrade(newGrade);
        alliance.broadcast(net.swordie.ms.connection.packet.AlliancePacket.setGrade(alliance, targetGuild, target));
        guildDao.saveOrUpdate(targetGuild);
    }

    /** Request 79: the member page's requests button; the window reads the requestors from the guild data. */
    public static void handleRequestorsWindow(Char chr, Guild guild, GuildMember gm) {
        if (guild != null && gm != null && (gm.getGrade() == Guild.MASTER || gm.hasPermission(GuildPermission.Accept))) {
            chr.write(WvsContext.guildResult(GuildResult.loadGuild(guild, chr)));
        }
    }
}
