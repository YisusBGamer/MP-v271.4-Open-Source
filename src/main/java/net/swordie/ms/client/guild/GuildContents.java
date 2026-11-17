package net.swordie.ms.client.guild;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.world.World;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Guild ranking (GUILD_CONTENT_RANK_RESULT 352, asked for with GUILD_CONTENT_RANK_REQUEST 493).
 * <p>
 * The client keeps four lists: 0 Flag Race, 1 weekly Honor EXP, 2 Culvert, 3 weekly mission. Each list entry is
 * int guildId, str name, int score + FILETIME (only counted when inside the week sent in the header), int rank,
 * byte details (int n, n x (int charId, int score, FILETIME)). The guild window shows the list 1/0/2 ranks, the
 * ranking popup lists the entries with a score, and the contents page reads the own guild's entry and its member
 * details. Weekly Honor EXP is the members' contribution this week, the weekly mission list the members' mission
 * points (GuildMission); Flag Race and Culvert stay empty. OUT 569 carries the contents page's gauge maximums.
 */
public class GuildContents {

    public static final int FLAG_RACE = 0, WEEKLY_HONOR = 1, CULVERT = 2, WEEKLY_MISSION = 3, LISTS = 4;
    private static final int FULL = 1, OWN_GUILD = 5;
    private static final int BOSS_POINTS = 11, MISSION_POINTS = 12, BOSS_LIMIT = 13, MISSION_LIMIT = 14;

    /** yyyymmdd as the client's date compare uses it. */
    public static int dateInt(LocalDate date) {
        return (date.getYear() * 100 + date.getMonthValue()) * 100 + date.getDayOfMonth();
    }

    /** The reset day (ResetClock: midnight in the reset time zone, which the client's clock also runs in). */
    public static LocalDate today() {
        return net.swordie.ms.util.ResetClock.today();
    }

    public static int todayInt() {
        return dateInt(today());
    }

    /**
     * The check-in day: the reset day, which the client's clock (FileTime.clientZone) also turns with; the check-in
     * window compares members' check-in days with it.
     */
    public static LocalDate checkInDay() {
        return today();
    }

    public static int checkInDayInt() {
        return dateInt(checkInDay());
    }

    /** Guild weeks run from Monday's daily reset. */
    public static LocalDate weekStartDate() {
        return today().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    public static int currentWeekStart() {
        return dateInt(weekStartDate());
    }

    public static int getWeeklyHonor(Guild guild) {
        long sum = 0;
        for (GuildMember gm : guild.getMembers()) {
            sum += gm.getWeekCommitment();
        }
        return (int) Math.min(Integer.MAX_VALUE, sum);
    }

    private record Entry(Guild guild, int score, int rank) {
    }

    private static int guildScore(int list, Guild g) {
        return switch (list) {
            case WEEKLY_HONOR -> getWeeklyHonor(g);
            case WEEKLY_MISSION -> GuildMission.getWeekPoints(g);
            default -> 0;
        };
    }

    private static int memberScore(int list, GuildMember gm) {
        return switch (list) {
            case WEEKLY_HONOR -> gm.getWeekCommitment();
            case WEEKLY_MISSION -> gm.getMissionPoints();
            default -> 0;
        };
    }

    private static List<Entry> ranking(int list, java.util.Collection<Guild> all) {
        List<Entry> res = new ArrayList<>();
        if (list != WEEKLY_HONOR && list != WEEKLY_MISSION) {
            return res;
        }
        List<Guild> guilds = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();
        for (Guild g : new ArrayList<>(all)) {
            if (g.getMembers().isEmpty()) {
                continue;
            }
            int score = guildScore(list, g);
            if (score > 0) {
                guilds.add(g);
                scores.add(score);
            }
        }
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < guilds.size(); i++) {
            order.add(i);
        }
        order.sort(Comparator.comparingInt((Integer i) -> -scores.get(i)).thenComparingInt(i -> guilds.get(i).getId()));
        int rank = 0;
        for (int i : order) {
            res.add(new Entry(guilds.get(i), scores.get(i), ++rank));
        }
        return res;
    }

    private static void encodeWeek(OutPacket out) {
        LocalDate start = weekStartDate();
        out.encodeFT(FileTime.fromEpochMillis(net.swordie.ms.util.ResetClock.startOfDay(start)));
        out.encodeFT(FileTime.fromEpochMillis(net.swordie.ms.util.ResetClock.startOfDay(start.plusWeeks(1))));
    }

    private static void encodeEntry(OutPacket out, int list, Entry e, boolean details) {
        FileTime now = FileTime.currentTime();
        out.encodeInt(e.guild().getId());
        out.encodeString(e.guild().getName());
        out.encodeInt(e.score());
        out.encodeFT(now);
        out.encodeInt(e.rank());
        out.encodeByte(details);
        if (details) {
            List<GuildMember> members = new ArrayList<>();
            for (GuildMember gm : e.guild().getMembers()) {
                if (memberScore(list, gm) > 0) {
                    members.add(gm);
                }
            }
            out.encodeInt(members.size());
            for (GuildMember gm : members) {
                out.encodeInt(gm.getCharID());
                out.encodeInt(memberScore(list, gm));
                out.encodeFT(now);
            }
        }
    }

    /**
     * Last week's results (the contents page's "last week" line). The first int is the Noblesse SP total the guild
     * skill window counts; 0, as Noblesse levels follow the guild level (the client zeroes it without a block too).
     */
    private static void encodeMissionBlock(OutPacket out, Guild own) {
        out.encodeByte(own != null);
        if (own == null) {
            return;
        }
        out.encodeInt(0); // Noblesse SP
        out.encodeInt(1);
        out.encodeByte(WEEKLY_MISSION);
        out.encodeInt(own.getMissionLastPoints());
        out.encodeInt(0); // rank
        out.encodeByte(100); // top %
        out.encodeByte(0); // bonus SP
        out.encodeByte(0); // SP
        out.encodeInt(0); // member details
    }

    /** The whole ranking (type 1), with member details for the asking character's guild. */
    public static OutPacket fullRanking(World world, Guild own) {
        return fullRanking(world.getGuilds().values(), own);
    }

    public static OutPacket fullRanking(java.util.Collection<Guild> all, Guild own) {
        if (own != null) {
            GuildMission.settle(own);
        }
        OutPacket out = new OutPacket(OutHeader.GUILD_CONTENT_RANK_RESULT);
        out.encodeByte(FULL);
        encodeWeek(out);
        for (int list = 0; list < LISTS; list++) {
            List<Entry> entries = ranking(list, all);
            out.encodeInt(entries.size());
            for (Entry e : entries) {
                encodeEntry(out, list, e, own != null && e.guild().getId() == own.getId());
            }
        }
        encodeMissionBlock(out, own);
        return out;
    }

    /** Only the asking character's guild in each list (type 5, the contents page). */
    public static OutPacket ownRanking(World world, Guild own) {
        return ownRanking(world.getGuilds().values(), own);
    }

    public static OutPacket ownRanking(java.util.Collection<Guild> all, Guild own) {
        if (own != null) {
            GuildMission.settle(own);
        }
        OutPacket out = new OutPacket(OutHeader.GUILD_CONTENT_RANK_RESULT);
        out.encodeByte(OWN_GUILD);
        encodeWeek(out);
        out.encodeInt(own == null ? 0 : own.getId());
        for (int list = 0; list < LISTS; list++) {
            Entry mine = null;
            List<Entry> entries = own == null ? List.of() : ranking(list, all);
            for (Entry e : entries) {
                if (e.guild().getId() == own.getId()) {
                    mine = e;
                }
            }
            out.encodeByte(mine != null);
            if (mine != null) {
                encodeEntry(out, list, mine, true);
                out.encodeInt(mine.rank() * 100 / entries.size()); // top x%
            }
        }
        encodeMissionBlock(out, own);
        return out;
    }

    /**
     * OUT 582 (v265 569), the contents page's table: int per-member weekly cap, int n, n x (int list, int m, m x (FILETIME
     * from, int a, a x (int rank, int SP), int b, b x (int top%, int SP), int c, c x (int score, int SP), int
     * gauge max)), then 30 ints (use unknown, 0). Only the weekly mission row: its gauge max, no SP steps.
     */
    public static OutPacket contentsTable() {
        OutPacket out = new OutPacket(OutHeader.GUILD_CONTENTS_TABLE);
        out.encodeInt(GuildMission.MEMBER_WEEK_CAP);
        out.encodeInt(1);
        out.encodeInt(WEEKLY_MISSION);
        out.encodeInt(1);
        // valid from long ago: the client picks the first row starting before the week's end
        out.encodeFT(FileTime.fromEpochMillis(LocalDate.of(2020, 1, 6).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()));
        out.encodeInt(0);
        out.encodeInt(0);
        out.encodeInt(0);
        out.encodeInt(GuildMission.GUILD_WEEK_GOAL);
        for (int i = 0; i < 30; i++) {
            out.encodeInt(0);
        }
        // v271 (0x14086c550): two 8-byte values and five ints after the 30 (stored globally, meaning unknown)
        out.encodeLong(0);
        out.encodeLong(0);
        for (int i = 0; i < 5; i++) {
            out.encodeInt(0);
        }
        return out;
    }

    private static OutPacket message(int type, int amount, boolean withAmount) {
        OutPacket out = new OutPacket(OutHeader.GUILD_CONTENT_RANK_RESULT);
        out.encodeByte(type);
        if (withAmount) {
            out.encodeInt(amount);
        }
        return out;
    }

    /** "You received Boss Points (+%d)" */
    public static OutPacket bossPointsMessage(int amount) {
        return message(BOSS_POINTS, amount, true);
    }

    /** "You received Weekly Mission Points (+%d)" */
    public static OutPacket missionPointsMessage(int amount) {
        return message(MISSION_POINTS, amount, true);
    }

    public static OutPacket bossLimitMessage() {
        return message(BOSS_LIMIT, 0, false);
    }

    public static OutPacket missionLimitMessage() {
        return message(MISSION_LIMIT, 0, false);
    }
}
