package net.swordie.ms.client.guild;

import net.swordie.ms.Server;
import net.swordie.ms.client.alliance.Alliance;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.guild.bbs.BBSRecord;
import net.swordie.ms.client.guild.result.GuildResult;
import net.swordie.ms.client.guild.result.GuildType;
import net.swordie.ms.connection.Encodable;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.handlers.social.module.GuildModule;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.World;
import net.swordie.orm.dao.*;

import javax.persistence.Transient;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created on 3/18/2018.
 */
public class Guild implements Encodable {

    private static final int NAME_TAG_BG_DEFAULT = 0, NAME_TAG_BORDER_DEFAULT = 0, NAME_TAG_FONT_WHITE = 198;
    private static final GuildDao guildDao = (GuildDao) SworDaoFactory.getByClass(Guild.class);
    private static final GuildMemberDao guildMemberDao = (GuildMemberDao) SworDaoFactory.getByClass(GuildMember.class);
    private static final GuildRequestorDao guildRequestorDao = (GuildRequestorDao) SworDaoFactory.getByClass(GuildRequestor.class);
    private static final CharDao charDao = (CharDao) SworDaoFactory.getByClass(Char.class);
    private static final GuildSkillDao guildSkillDao = (GuildSkillDao) SworDaoFactory.getByClass(GuildSkill.class);
    private static final BBSRecordDao bbsRecordDao = (BBSRecordDao) SworDaoFactory.getByClass(BBSRecord.class);

    public static final int GRADE_SIZE = 10;
    public static final int MIN_GRADE_SIZE = 5;
    public static final int MASTER = 1, JR_MASTER = 2, VETERAN = 3, MEMBER = 4, NEW = 5;

    private int id;
    private String name;
    private int leaderID;
    private int worldID;

    private Set<GuildRequestor> requestors;

    private Set<GuildMember> members;

    private List<String> gradeNames;
    private List<Integer> gradePermissions;

    private int markBg;
    private int markBgColor;
    private int mark;
    private int markColor;
    private byte[] customMark;
    @Transient
    private int customMarkType = 1;
    private int maxMembers;
    private String notice;
    private int points;
    private int seasonPoints;
    private int allianceID;
    private int level;
    private int rank;
    private int ggp;
    private int missionCheckedWeek; // yyyymmdd of the week whose start last settled the weekly mission
    private int missionLastPoints; // the weekly mission total of the week before missionCheckedWeek
    // Start GUILDSETTING struct
    private boolean appliable;
    private int reqLevel;
    private int activeTimes;
    private int mainActivity;
    private int ageGroup;
    // End GUILDSETTING struct

    private Map<Integer, GuildSkill> skills;

    private Set<BBSRecord> bbsRecords;

    @Transient
    private Alliance alliance;
    private final Set<Integer> pendingChars = new HashSet<>();

    public Guild(boolean init) {
        if (init) {
            requestors = new HashSet<>();
            members = concurrentSet(null);
            skills = new HashMap<>();
            bbsRecords = new HashSet<>();
            gradeNames = new ArrayList<>();

            setGradeNames(new String[]{"Master", "Officer", "Veteran", "Member", "Initiate"});
            setGradePermissions(new int[]{-1, 0, 0, 0, 0});
            setAppliable(true);
            setMaxMembers(50);
            setLevel(1);
            setName("Default guild");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void encode(OutPacket outPacket) {
        // GUILDDATA: requestors come before members, and each entry carries its char id
        outPacket.encodeInt(getId());
        outPacket.encodeString(getName());
        for (int i = 0; i < GRADE_SIZE; i++) {
            if (i < getGradeNames().size()) {
                outPacket.encodeString(getGradeNames().get(i));
                outPacket.encodeInt(getGradePermissions().get(i));
            } else {
                outPacket.encodeString("");
                outPacket.encodeInt(0);
            }
        }
        outPacket.encodeShort(getRequestors().size());
        getRequestors().forEach(gr -> gr.encode(outPacket));
        outPacket.encodeShort(getMembers().size());
        getMembers().forEach(gm -> gm.encode(outPacket));

        outPacket.encodeInt(getMaxMembers());
        outPacket.encodeShort(getMarkBg());
        outPacket.encodeByte(getMarkBgColor());
        outPacket.encodeShort(getMark());
        outPacket.encodeByte(getMarkColor());
        outPacket.encodeString(getNotice());

        outPacket.encodeInt(getRank());
        outPacket.encodeInt(getSeasonPoints());
        outPacket.encodeByte(getLevel());
        outPacket.encodeInt(getGgp());
        outPacket.encodeInt(getPoints());
        outPacket.encodeInt(-1); // nCulvertRanking
        outPacket.encodeByte(isAppliable());
        outPacket.encodeFT(getLeaderClaimTime()); // members may take over the guild from then on (request 21)
        outPacket.encodeFT(FileTime.fromDate(FileTime.currentTime().toLocalDateTime().minusDays(1)));
        outPacket.encodeInt(getActiveTimes());
        outPacket.encodeInt(getMainActivity());
        outPacket.encodeInt(getAgeGroup());

        outPacket.encodeShort(getSkills().size());
        getSkills().forEach((skillId, skill) -> {
            outPacket.encodeInt(skillId);
            outPacket.encode(skill);
        });

        encodeCustomMark(outPacket);
        encodeNameTag(outPacket);
        outPacket.encodeByte(0); // 1: a serialized object follows
    }

    /** The emblem as GUILD_RESULT's mark change reads it: mark, then the custom mark. */
    public void encodeMark(OutPacket outPacket) {
        outPacket.encodeShort(getMarkBg());
        outPacket.encodeByte(getMarkBgColor());
        outPacket.encodeShort(getMark());
        outPacket.encodeByte(getMarkColor());
        encodeCustomMark(outPacket);
    }

    private void encodeCustomMark(OutPacket outPacket) {
        var customMark = getCustomMark();
        outPacket.encodeByte(customMark == null ? 0 : customMarkType);
        outPacket.encodeInt(customMark == null ? 0 : customMark.length);
        if (customMark != null) {
            outPacket.encodeArr(customMark);
        }
        outPacket.encodeInt(getCustomMarkVersion());
    }

    /** Days the master has to be away before a member can take the guild over (request 21). */
    public static final int LEADER_CLAIM_DAYS = 365;

    /** When the master's absence lets members claim the guild: away LEADER_CLAIM_DAYS since their last login. */
    public FileTime getLeaderClaimTime() {
        GuildMember leader = getMemberByCharID(getLeaderID());
        java.time.LocalDateTime from = leader == null || leader.isOnline() || leader.getLastOnline() == null
                ? java.time.LocalDateTime.now() : leader.getLastOnline().toLocalDateTime();
        return FileTime.fromDate(from.plusDays(LEADER_CLAIM_DAYS));
    }

    /** Keys the clients' custom emblem cache: 0 without one, else changes with the emblem. */
    public int getCustomMarkVersion() {
        var customMark = getCustomMark();
        return customMark == null || customMark.length == 0 ? 0 : java.util.Arrays.hashCode(customMark) | 1;
    }

    public int getCustomMarkType() {
        return customMarkType;
    }

    public void setCustomMarkType(int customMarkType) {
        this.customMarkType = customMarkType;
    }

    /** Each online member's own guild window (GUILD_RESULT's load is per character). */
    public void broadcastLoad() {
        getOnlineMembers().stream().filter(gm -> gm.getChr() != null)
                .forEach(gm -> gm.getChr().write(WvsContext.guildResult(GuildResult.loadGuild(this, gm.getChr()))));
    }

    public void encodeForRemote(OutPacket outPacket) {
        outPacket.encodeInt(getId());
        outPacket.encodeString(getName());
        outPacket.encodeShort(getMarkBg());
        outPacket.encodeByte(getMarkBgColor());
        outPacket.encodeShort(getMark());
        outPacket.encodeByte(getMarkColor());
        // custom emblem: its guild and version; on a cache miss the client asks with GUILD_REQUEST 46
        int version = getCustomMarkVersion();
        outPacket.encodeInt(version == 0 ? 0 : getId());
        outPacket.encodeInt(version);
    }

    public Set<GuildMember> getMembers() {
        if (members == null) {
            members = concurrentSet(guildMemberDao.byGuild(this));
        }
        return members;
    }

    /** Joins and leaves happen while other threads walk the members (rankings, contents page). */
    private static Set<GuildMember> concurrentSet(Collection<GuildMember> from) {
        Set<GuildMember> set = java.util.concurrent.ConcurrentHashMap.newKeySet();
        if (from != null) {
            for (GuildMember gm : from) {
                if (gm != null) {
                    set.add(gm);
                }
            }
        }
        return set;
    }

    public void addMember(GuildMember guildMember) {
        getPendingChars().remove(guildMember.getCharID());
        getMembers().add(guildMember);
        if (guildMember.getChr() != null && guildMember.getChr().getGuild() == null) {
            guildMember.getChr().setGuild(this);
        }
        if (getLeaderID() == 0) {
            // no one is guild leader
            setLeader(guildMember);
        } else {
            guildMember.setGrade(NEW);
            broadcast(WvsContext.guildResult(GuildResult.joinGuild(this, guildMember)));
        }
    }

    public void demote(GuildMember guildMember) {
        guildMember.setGrade(Math.min(guildMember.getGrade() + 1, getGradeNames().size()));
    }

    public void promote(GuildMember guildMember) {
        guildMember.setGrade(Math.max(guildMember.getGrade() - 1, 1));
    }

    public void addMember(Char chr, boolean online) {
        addMember(new GuildMember(this, chr, online));
    }

    public void addMember(Char chr) {
        addMember(chr, true);
    }

    public void removeMember(GuildMember guildMember) {
        if (guildMember.getChr() != null) {
            guildMember.getChr().setGuild(null);
        }
        getMembers().remove(guildMember);
        guildMemberDao.delete(guildMember);
    }

    public void removeMember(Char chr) {
        removeMember(getMemberByChar(chr));
    }

    public GuildMember getMemberByChar(Char chr) {
        return getMembers().stream()
                .filter(gm -> gm.getChr() != null && gm.getChr().equals(chr))
                .findAny()
                .orElse(null);
    }

    public int getLeaderID() {
        return leaderID;
    }

    public void setLeaderID(int leaderID) {
        this.leaderID = leaderID;
    }

    public void setLeader(GuildMember leader) {
        if (getLeaderID() != 0) {
            getMemberByCharID(getLeaderID()).setGrade(JR_MASTER);
        }
        this.leaderID = leader.getCharID();
        setLeaderID(leader.getCharID());
        leader.setGrade(MASTER);
    }

    public boolean isGuildMember(GuildMember gm) {
        return getLeaderID() == gm.getCharID();
    }

    public GuildMember getMemberByCharID(int id) {
        return getMembers().stream().filter(gm -> gm.getCharID() == id).findAny().orElse(null);
    }

    public int getMarkBg() {
        return markBg;
    }

    public void setMarkBg(int markBg) {
        this.markBg = markBg;
    }

    public int getMarkBgColor() {
        return markBgColor;
    }

    public void setMarkBgColor(int markBgColor) {
        this.markBgColor = markBgColor;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getMarkColor() {
        return markColor;
    }

    public void setMarkColor(int markColor) {
        this.markColor = markColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<GuildMember> getOnlineMembers() {
        return getMembers().stream().filter(GuildMember::isOnline).collect(Collectors.toList());
    }

    public void broadcast(OutPacket outPacket) {
        getOnlineMembers().stream().filter(gm -> gm.getChr() != null).forEach(gm -> gm.getChr().write(outPacket));
    }

    public void broadcast(OutPacket outPacket, Char exceptChr) {
        getOnlineMembers().stream().filter(gm -> gm.getChr() != null && !gm.getChr().equals(exceptChr))
                .forEach(gm -> gm.getChr().write(outPacket));
    }

    public void broadcastByChr(Char byChr, OutPacket outPacket, Char exceptChr) {
        getOnlineMembers().stream()
                .filter(gm ->
                        gm.getChr() != null && !gm.getChr().equals(exceptChr) && !gm.getChr().hasChrBlacklisted(byChr.getId()))
                .forEach(gm -> gm.getChr().write(outPacket));
    }

    public Set<GuildRequestor> getRequestors() {
        if (requestors == null) {
            requestors = guildRequestorDao.byGuild(this);
        }
        return requestors;
    }

    public void setRequestors(Set<GuildRequestor> requestors) {
        this.requestors = requestors;
    }

    public void addGuildRequestor(Char chr, String introduction) {
        GuildRequestor guildRequestor = new GuildRequestor(this, chr, introduction);
        getRequestors().add(guildRequestor);
    }

    public void removeGuildRequestor(GuildRequestor gr) {
        getRequestors().remove(gr);
        guildRequestorDao.delete(gr);
    }

    public List<String> getGradeNames() {
        return gradeNames;
    }

    public void setGradeNames(List<String> gradeNames) {
        this.gradeNames = gradeNames;
    }

    public void setGradeNames(String[] gradeNames) {
        if (getGradeNames() == null) {
            setGradeNames(new ArrayList<>());
        } else {
            getGradeNames().clear();
        }
        for (String gradeName : gradeNames) {
            getGradeNames().add(gradeName);
        }
    }

    public void setGradePermissions(int[] gradePermissions) {
        if (getGradePermissions() == null) {
            setGradePermissions(new ArrayList<>());
        } else {
            getGradePermissions().clear();
        }
        for (var gradePermission : gradePermissions) {
            getGradePermissions().add(gradePermission);
        }
    }

    public void setMembers(Set<GuildMember> members) {
        this.members = concurrentSet(members);
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public boolean hasMaxMembers() {
        return isFull(); // a guild over its limit counts as full
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getSeasonPoints() {
        return seasonPoints;
    }

    public void setSeasonPoints(int seasonPoints) {
        this.seasonPoints = seasonPoints;
    }

    public int getAllianceID() {
        return allianceID;
    }

    public void setAllianceID(int allianceID) {
        this.allianceID = allianceID;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getGgp() {
        return ggp;
    }

    public int getMissionCheckedWeek() {
        return missionCheckedWeek;
    }

    public void setMissionCheckedWeek(int missionCheckedWeek) {
        this.missionCheckedWeek = missionCheckedWeek;
    }

    public int getMissionLastPoints() {
        return missionLastPoints;
    }

    public void setMissionLastPoints(int missionLastPoints) {
        this.missionLastPoints = missionLastPoints;
    }

    public void setGgp(int ggp) {
        this.ggp = ggp;
    }

    public boolean isAppliable() {
        return appliable;
    }

    public void setAppliable(boolean setState) {
        this.appliable = setState;
    }

    public int getReqLevel() {
        return reqLevel;
    }

    public void setReqLevel(int reqLevel) {
        this.reqLevel = reqLevel;
    }

    public Map<Integer, GuildSkill> getSkills() {
        if (skills == null) {
            skills = guildSkillDao.byGuild(this);
        }
        return skills;
    }

    public void setSkills(Map<Integer, GuildSkill> skills) {
        this.skills = skills;
    }

    public void addGuildSkill(GuildSkill gs) {
        getSkills().put(gs.getSkillID(), gs);
    }

    public int getWorldID() {
        return worldID;
    }

    public void setWorldID(int worldID) {
        this.worldID = worldID;
    }

    /** CGuild::DecodeCustomNameTag: background, border and font colour of the guild name tag, then a byte. */
    public static void encodeNameTag(OutPacket outPacket) {
        outPacket.encodeInt(NAME_TAG_BG_DEFAULT);
        outPacket.encodeInt(NAME_TAG_BORDER_DEFAULT);
        outPacket.encodeInt(NAME_TAG_FONT_WHITE);
        outPacket.encodeByte(0);
    }

    public static void defaultEncodeForRemote(OutPacket outPacket) {
        outPacket.encodeInt(0);
        outPacket.encodeString("");
        outPacket.encodeShort(0);
        outPacket.encodeByte(0);
        outPacket.encodeShort(0);
        outPacket.encodeByte(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
    }

    /**
     * Adds a given amount of commitment to the char.
     *
     * @param chr        the char that the commitment should be given to
     * @param commitment the amount of commitment to add
     */
    public void addCommitmentToChar(Char chr, int commitment) {
        GuildMember gm = getMemberByCharID(chr.getId());
        if (gm != null) {
            gm.addCommitment(commitment);
            net.swordie.ms.client.character.achievement.MapleAchievements.onGuildContribution(chr, commitment);
            addGgp((int) (commitment * (GameConstants.GGP_PER_CONTRIBUTION)));
            addPoints(chr, commitment);
        }
    }

    private void addPoints(Char chr, int commitment) {
        addGuildPoints(commitment);
    }

    /**
     * Adds guild EXP ("points") and levels the guild up as far as it reaches.
     */
    public void addGuildPoints(int amount) {
        if (amount <= 0) {
            return;
        }
        // kills on any channel's thread add at once: the sum and level-ups under the guild lock, packets after
        List<Integer> newLevels = new ArrayList<>();
        synchronized (this) {
            setPoints((int) Math.min(Integer.MAX_VALUE, (long) getPoints() + amount));
            while (getLevel() < GameConstants.MAX_GUILD_LV
                    && getPoints() >= GameConstants.getExpRequiredForNextGuildLevel(getLevel())) {
                setLevel(getLevel() + 1);
                newLevels.add(getLevel());
            }
            if (!newLevels.isEmpty()) {
                syncPoints = false;
            }
        }
        for (int level : newLevels) {
            broadcast(UserLocal.chatMsg(ChatType.Notice2, String.format("The guild has leveled up! [%s] is now level %d.",
                    getName(), level)));
        }
        if (!newLevels.isEmpty()) {
            syncNoblesseSkills(true);
            broadcast(WvsContext.guildResult(GuildResult.setPointAndLevel(this)));
        } else {
            queueSync(null, false, true);
        }
    }

    public void setNoblesseSkillLevels(Char chr) {
        syncNoblesseSkills(chr != null);
    }

    /**
     * The four Noblesse (guild battle) skills follow the guild level: Boss Slayers and Undeterred 1 per level up to
     * 15, For the Guild! and Hard Hitter 1 per level above 15. Set directly, bypassing the skill-learning checks.
     */
    public void syncNoblesseSkills(boolean announce) {
        int lv = getLevel();
        int[] want = {Math.min(lv, 15), Math.min(lv, 15), Math.min(Math.max(lv - 15, 0), 15),
                Math.min(Math.max(lv - 15, 0), 15)};
        for (int i = 0; i < want.length; i++) {
            int id = SkillConstants.GUILD_NOBLESSE_BASE + i;
            GuildSkill gs = getSkillById(id);
            if (gs == null) {
                if (want[i] == 0) {
                    continue;
                }
                gs = new GuildSkill();
                gs.setSkillID(id);
                addGuildSkill(gs);
            }
            if (gs.getLevel() == want[i]) {
                continue;
            }
            // With the online members' stats
            net.swordie.ms.handlers.social.module.GuildModule.changeSkillLevel(this, gs, want[i]);
            if (announce) {
                broadcast(WvsContext.guildResult(GuildResult.setSkill(this, gs, 0)));
            }
        }
    }

    // ------------------------------------------------------------------------------- guild check-in

    /**
     * Records a member's check-in (on the client's day, see GuildContents.checkInDay). The day's first check-in
     * gives the guild its bonus EXP for the day before's check-ins (official: 15-29 = 50, 30-59 = 100,
     * 60-99 = 1,000, 100+ = 2,000).
     */
    public synchronized void noteCheckIn(GuildMember member) {
        int today = GuildContents.checkInDayInt();
        boolean firstToday = getMembers().stream().noneMatch(gm -> gm.getCheckInDate() == today);
        if (firstToday) {
            int yesterday = GuildContents.dateInt(GuildContents.checkInDay().minusDays(1));
            int count = (int) getMembers().stream().filter(gm -> gm.getCheckInDate() == yesterday).count();
            int bonus = getCheckInBonus(count);
            if (bonus > 0) {
                broadcast(UserLocal.chatMsg(ChatType.Notice2, String.format("[Guild] %d members checked in "
                        + "yesterday: the guild gets %,d guild EXP.", count, bonus)));
                addGuildPoints(bonus);
            }
        }
        member.setCheckInDate(today);
    }

    public static final int[][] CHECK_IN_BONUS = {{15, 50}, {30, 100}, {60, 1000}, {100, 2000}};

    public static int getCheckInBonus(int count) {
        int bonus = 0;
        for (int[] step : CHECK_IN_BONUS) {
            if (count >= step[0]) {
                bonus = step[1];
            }
        }
        return bonus;
    }

    public int getCheckInsToday() {
        int today = GuildContents.checkInDayInt();
        return (int) getMembers().stream().filter(gm -> gm.getCheckInDate() == today).count();
    }

    public void addGgp(int ggp) {
        addGgpNow(ggp);
        queueSync(null, true, false);
    }

    /** Adds (or with a negative amount spends) GP at once, without the batched update; the caller sends it. */
    public synchronized void addGgpNow(int ggp) {
        setGgp(getGgp() + ggp);
    }

    // ------------------------------------------------------------------------------- batched updates

    /**
     * Contribution, GP and guild EXP from play (kills, levels) reach the members together at most this often,
     * not once per gain; the packets carry the current totals, so the end values are the same.
     */
    private static final long SYNC_DELAY_MS = 10_000;
    @Transient
    private Map<Integer, GuildMember> syncMembers;
    @Transient
    private boolean syncGgp, syncPoints, syncQueued;

    /** Queues a member's contribution row and/or the guild's GP and EXP for the next batched update. */
    public void queueSync(GuildMember member, boolean ggp, boolean points) {
        synchronized (this) {
            if (member != null) {
                if (syncMembers == null) {
                    syncMembers = new LinkedHashMap<>();
                }
                syncMembers.put(member.getCharID(), member);
            }
            syncGgp |= ggp;
            syncPoints |= points;
            if (syncQueued) {
                return;
            }
            syncQueued = true;
        }
        net.swordie.ms.handlers.executors.EventManager.addEvent(this::flushSync, SYNC_DELAY_MS);
    }

    /** Sends whatever is queued now. */
    public void flushSync() {
        List<GuildMember> rows;
        boolean ggp, points;
        synchronized (this) {
            rows = syncMembers == null ? List.of() : new ArrayList<>(syncMembers.values());
            if (syncMembers != null) {
                syncMembers.clear();
            }
            ggp = syncGgp;
            points = syncPoints;
            syncGgp = syncPoints = syncQueued = false;
        }
        try {
            for (GuildMember gm : rows) {
                if (getMembers().contains(gm)) {
                    broadcast(WvsContext.guildResult(GuildResult.setMemberCommitment(this, gm)));
                }
            }
            if (ggp) {
                broadcast(WvsContext.guildResult(GuildResult.setGgp(this)));
            }
            if (points) {
                broadcast(WvsContext.guildResult(GuildResult.setPointAndLevel(this)));
            }
        } catch (RuntimeException e) {
            org.apache.logging.log4j.LogManager.getLogger(Guild.class).error("guild sync " + getId(), e);
        }
    }

    public Set<BBSRecord> getBbsRecords() {
        if (bbsRecords == null) {
            bbsRecords = bbsRecordDao.byGuild(this);
        }
        return bbsRecords;
    }

    public void setBbsRecords(Set<BBSRecord> bbsRecords) {
        this.bbsRecords = bbsRecords;
    }

    public void addBbsRecord(BBSRecord record) {
        getBbsRecords().add(record);
        record.setIdForBbs(getBbsRecords().size()); // whatevs
    }

    public BBSRecord getRecordByID(int id) {
       return getBbsRecords().stream().filter(r -> r.getIdForBbs() == id).findAny().orElse(null);
    }

    public void removeRecord(BBSRecord record) {
        getBbsRecords().remove(record);
        bbsRecordDao.delete(record);
        resetBbsIds();
    }

    public void resetBbsIds() {
        int i = 1;
        for (BBSRecord r : getBbsRecords().stream().sorted().collect(Collectors.toList())) {
            r.setIdForBbs(i++);
        }
    }

    public int getSpentSp() {
        return getSkills().values().stream()
                .filter(gs -> SkillConstants.isGuildContentSkill(gs.getSkillID()))
                .mapToInt(GuildSkill::getLevel).sum();
    }

    public int getSpentBattleSp() {
        int spentSp = 0;
        for (int i = SkillConstants.GUILD_NOBLESSE_BASE; i <= SkillConstants.GUILD_NOBLESSE_END; i++) {
            GuildSkill gs = getSkillById(i);
            spentSp += gs == null ? 0 : gs.getLevel();
        }
        return spentSp;
    }

    public int getBattleSp() {
        return getLevel() * 2;
    }

    public GuildSkill getSkillById(int skillId) {
        return getSkills().get(skillId);
    }

    public void setAlliance(Alliance alliance) {
        this.alliance = alliance;
        if (alliance == null) {
            setAllianceID(0);
        } else {
            setAllianceID(alliance.getId());
        }
    }

    public Alliance getAlliance() {
        return alliance;
    }

    public boolean isGuildMaster(Char chr) {
        return getLeaderID() == chr.getId();
    }

    public GuildMember getGuildLeader() {
        return getMemberByCharID(getLeaderID());
    }

    public int getAverageMemberLevel() {
        int size = getMembers().size();
        int averageLevel = 0;
        for (GuildMember gm : getMembers()) {
            averageLevel += gm.getLevel();
        }
        return averageLevel / size;
    }

    public void disband() {
        if (getAlliance() != null) {
            // The alliance gets a new master / is disbanded too (see GuildHandler)
            net.swordie.ms.handlers.social.GuildHandler.leaveAlliance(this, false);
        }
        var removed = WvsContext.guildResult(GuildResult.removeComplete(this));
        for (GuildMember gm : new ArrayList<>(getMembers())) {
            Char chr = gm.getChr();
            if (chr != null) {
                net.swordie.ms.handlers.social.module.GuildModule.dropGuildStats(chr, this); // also the master leaving last
                chr.setGuild(null);
                chr.write(removed); // every member's guild window, not only the master's
                if (chr.getField() != null) {
                    chr.getField().broadcastPacket(UserRemote.guildUpdated(chr));
                }
            } else {
                chr = charDao.getById(gm.getCharID());
                if (chr != null) { // deleted characters are skipped
                    chr.setGuild(null);
                    charDao.saveOrUpdate(chr);
                }
            }
        }
        World world = Server.getInstance().getWorldById(getWorldID());
        if (world != null) {
            world.removeGuild(this); // also deletes the members, requests, skills and the board's posts
        }
        getMembers().clear();
    }

    public boolean canAcceptMember(Char chr) {
        GuildMember gm = getMemberByChar(chr);
        return gm != null && gm.getGrade() <= JR_MASTER;
    }

    public GuildRequestor getRequestorById(int charId) {
        return Util.findWithPred(getRequestors(), gr -> gr.getCharID() == charId);
    }

    public boolean isFull() {
        return getMembers().size() >= getMaxMembers();
    }

    public void updateToMembers() {
        broadcastLoad();
    }

    public List<Integer> getGradePermissions() {
        return gradePermissions;
    }

    public void setGradePermissions(List<Integer> gradePermissions) {
        this.gradePermissions = gradePermissions;
    }

    public void setCustomMark(byte[] customMark) {
        this.customMark = customMark;
    }

    public byte[] getCustomMark() {
        return customMark;
    }

    public void setActiveTimes(int activeTimes) {
        this.activeTimes = activeTimes;
    }

    public int getActiveTimes() {
        return activeTimes;
    }

    public void setMainActivity(int mainActivity) {
        this.mainActivity = mainActivity;
    }

    public int getMainActivity() {
        return mainActivity;
    }

    public void setAgeGroup(int ageGroup) {
        this.ageGroup = ageGroup;
    }

    public int getAgeGroup() {
        return ageGroup;
    }

    public void addPendingChr(Char invited) {
        getPendingChars().add(invited.getId());
    }

    public boolean hasPendingChar(Char chr) {
        return getPendingChars().contains(chr.getId());
    }

    public void removePendingChar(Char chr) {
        getPendingChars().remove(chr.getId());
    }

    public Set<Integer> getPendingChars() {
        return pendingChars;
    }

    public int getSkillSp() {
        return getLevel() * 2;
    }
}
