package net.swordie.ms.client.guild;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.guild.bbs.BBSRecord;
import net.swordie.ms.client.guild.result.GuildResult;
import net.swordie.ms.connection.Encodable;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.util.FileTime;

import javax.persistence.Transient;

public class GuildMember implements Encodable {
    private int id;

    private int charID;
    private int grade;
    private int allianceGrade;
    private int commitment;
    private int dayCommitment;
    private int igp;
    private FileTime commitmentIncTime;
    private FileTime lastOnline;
    private String name;
    private int job;
    private int level;
    private boolean online;
    private int weekCommitment;
    private int weekStart; // yyyymmdd of the week's Monday (reset day) that weekCommitment counts
    private int checkInDate; // yyyymmdd (reset day) of the last guild check-in
    private int missionWeek; // yyyymmdd of the week's Monday (reset day) that the mission fields count
    private int missionHunt, missionBoss, missionKills; // weekly mission points, and kills toward the next hunt point

    @Transient
    private Char chr;
    @Transient
    private boolean noticeUnread;
    private Guild guild;
    private BBSRecord editingRecord;

    public GuildMember(Guild guild) {
        this.guild = guild;
    }

    public GuildMember(Guild guild, Char chr, boolean online) {
        this(guild);
        this.chr = chr;
        updateInfoFromChar(chr);
        grade = 5;
        allianceGrade = 5;
        if (!online) {
            this.chr = null; // so they're not mistaken as online players
        }
    }

    public void updateInfoFromChar(Char chr) {
        setName(chr.getName());
        setCharID(chr.getId());
        setJob(chr.getJob());
        setLevel(chr.getLevel());
        setOnline(chr.isOnline());
        setLastOnline(FileTime.currentTime());
    }

    public Char getChr() {
        return chr;
    }

    public void setChr(Char chr) {
        this.chr = chr;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getAllianceGrade() {
        return allianceGrade;
    }

    public void setAllianceGrade(int allianceGrade) {
        this.allianceGrade = allianceGrade;
    }

    public int getCommitment() {
        return commitment;
    }

    public void setCommitment(int commitment) {
        this.commitment = commitment;
    }

    public int getDayCommitment() {
        return dayCommitment;
    }

    public void setDayCommitment(int dayCommitment) {
        this.dayCommitment = dayCommitment;
    }

    public int getIgp() {
        return igp;
    }

    public void setIgp(int igp) {
        this.igp = igp;
    }

    public FileTime getCommitmentIncTime() {
        return commitmentIncTime;
    }

    public void setCommitmentIncTime(FileTime commitmentIncTime) {
        this.commitmentIncTime = commitmentIncTime;
    }

    public void encode(OutPacket outPacket) {
        resetDayCommitmentIfNewDay();
        outPacket.encodeInt(getCharID());
        outPacket.encodeString(getName());
        outPacket.encodeInt(getJob());
        outPacket.encodeInt(getLevel());
        outPacket.encodeInt(getGrade());
        outPacket.encodeInt(getAllianceGrade());
        outPacket.encodeFT(FileTime.fromDate(FileTime.currentTime().toLocalDateTime().minusDays(6)));
        outPacket.encodeByte(isOnline());
        outPacket.encodeFT(getLastOnline());
        outPacket.encodeInt(getCommitment());
        outPacket.encodeInt(getDayCommitment());
        outPacket.encodeFT(getCommitmentIncTime());
        outPacket.encodeInt(getCheckInDate()); // the check-in window counts members whose date is today
        // a changed notice not read yet: the client clears it and sends GUILD_REQUEST 40 when the notice opens
        outPacket.encodeFT(isNoticeUnread() ? FileTime.currentTime() : FileTime.MIN_TIME());
    }

    public void addCommitment(int commitment) {
        resetDayCommitmentIfNewDay();
        setCommitment(getCommitment() + commitment);
        setDayCommitment(getDayCommitment() + commitment);
        addIgp((int) (commitment * GameConstants.IGP_PER_CONTRIBUTION));
        setCommitmentIncTime(FileTime.currentTime());
        addWeekCommitment(commitment);
        if (getChr() != null) {
            Guild g = getChr().getGuild();
            // Own message and row at once; the rest of the guild gets the row with the next batched update
            getChr().write(WvsContext.message(MessagePacket.incCommitmentMessage(commitment)));
            if (g != null) {
                getChr().write(WvsContext.guildResult(GuildResult.setMemberCommitment(g, this)));
                g.queueSync(this, false, false);
            }
        }

    }

    /** Today's contribution starts over each day. */
    public void resetDayCommitmentIfNewDay() {
        FileTime last = getCommitmentIncTime();
        if (getDayCommitment() != 0 && (last == null
                || !net.swordie.ms.util.ResetClock.isToday(last.toMillis()))) {
            setDayCommitment(0);
        }
    }

    /** Contribution this week (from Monday's daily reset on), for the guild ranking's weekly Honor EXP list. */
    public int getWeekCommitment() {
        return weekStart == GuildContents.currentWeekStart() ? weekCommitment : 0;
    }

    private void addWeekCommitment(int amount) {
        int week = GuildContents.currentWeekStart();
        if (weekStart != week) {
            weekStart = week;
            weekCommitment = 0;
        }
        weekCommitment = (int) Math.min(Integer.MAX_VALUE, (long) weekCommitment + amount);
    }

    public int getRawWeekCommitment() {
        return weekCommitment;
    }

    public void setRawWeekCommitment(int weekCommitment) {
        this.weekCommitment = weekCommitment;
    }

    public int getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(int weekStart) {
        this.weekStart = weekStart;
    }

    public int getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(int checkInDate) {
        this.checkInDate = checkInDate;
    }

    /** Rolls the weekly mission fields over to this week. */
    public void rollMissionWeek(int week) {
        if (missionWeek != week) {
            missionWeek = week;
            missionHunt = 0;
            missionBoss = 0;
            missionKills = 0;
        }
    }

    /** Weekly mission points this week (hunting + boss). */
    public int getMissionPoints() {
        return missionWeek == GuildContents.currentWeekStart() ? missionHunt + missionBoss : 0;
    }

    public int getMissionWeek() {
        return missionWeek;
    }

    public void setMissionWeek(int missionWeek) {
        this.missionWeek = missionWeek;
    }

    public int getMissionHunt() {
        return missionHunt;
    }

    public void setMissionHunt(int missionHunt) {
        this.missionHunt = missionHunt;
    }

    public int getMissionBoss() {
        return missionBoss;
    }

    public void setMissionBoss(int missionBoss) {
        this.missionBoss = missionBoss;
    }

    public int getMissionKills() {
        return missionKills;
    }

    public void setMissionKills(int missionKills) {
        this.missionKills = missionKills;
    }

    public boolean isNoticeUnread() {
        return noticeUnread;
    }

    public void setNoticeUnread(boolean noticeUnread) {
        this.noticeUnread = noticeUnread;
    }

    private void addIgp(int igp) {
        setIgp(getIgp() + igp);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GuildMember && getCharID() == ((GuildMember) obj).getCharID();
    }

    public int getCharID() {
        return charID;
    }

    public void setCharID(int charID) {
        this.charID = charID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getJob() {
        return job;
    }

    public void setJob(int job) {
        this.job = job;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
        setLastOnline(FileTime.currentTime());
    }

    public boolean hasPermission(GuildPermission permission) {
        var myPermissions = getGuild().getGradePermissions().get(getGrade() - 1); // grades are 1-5
        return (myPermissions & permission.getVal()) != 0;
    }

    public Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public FileTime getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(FileTime lastOnline) {
        this.lastOnline = lastOnline;
    }

    public void setEditingRecord(BBSRecord editingRecord) {
        this.editingRecord = editingRecord;
    }

    public BBSRecord getEditingRecord() {
        return editingRecord;
    }
}
