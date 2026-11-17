package net.swordie.ms.client.guild;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.constants.MobConstants;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.QuestData;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

/**
 * Guild weekly mission (contents page list 3). Members earn hunting points from monsters near their level and boss
 * points from boss clears, each capped per week; the guild's weekly total fills the contents page gauge against
 * GUILD_WEEK_GOAL. When a new week starts, the week before is settled: its total is kept as last week's result
 * and the highest reward step reached gives the guild GP. Weeks start Monday 00:00 UTC (GuildContents).
 */
public class GuildMission {

    // ---- values (adjustable; listed in plans/guild-audit-20261027.md) ----
    /** Monster kills (non-boss, within HUNT_LEVEL_RANGE of the character's level) per hunting point. */
    public static final int HUNT_KILLS_PER_POINT = 100;
    public static final int HUNT_LEVEL_RANGE = 20;
    /** A member's weekly caps: hunting points, boss points, and the two together (the contents page's own gauge). */
    public static final int HUNT_WEEK_CAP = 500;
    public static final int BOSS_WEEK_CAP = 500;
    public static final int MEMBER_WEEK_CAP = HUNT_WEEK_CAP + BOSS_WEEK_CAP;
    /** The guild's weekly goal: the contents page gauge maximum. */
    public static final int GUILD_WEEK_GOAL = 10_000;
    /** Boss points per boss clear: {boss level from, points}. */
    public static final int[][] BOSS_POINTS = {{0, 5}, {150, 10}, {200, 20}, {240, 30}, {260, 50}};
    /** Reward when a week is settled: {guild total reached, GP}; the highest step reached pays. */
    public static final int[][] GP_REWARDS = {{2_000, 2_000}, {5_000, 5_000}, {10_000, 10_000}};
    // ----

    /** The client reads huntPoint/bossPoint from this QuestEx while its "d" (yyyyMMddHHmm) is inside the week. */
    public static final int QUEST_EX = 100813;
    private static final DateTimeFormatter QUEST_DATE = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    public static int getBossPoints(int level) {
        int points = 0;
        for (int[] step : BOSS_POINTS) {
            if (level >= step[0]) {
                points = step[1];
            }
        }
        return points;
    }

    public static int getGpReward(int total) {
        int gp = 0;
        for (int[] step : GP_REWARDS) {
            if (total >= step[0]) {
                gp = step[1];
            }
        }
        return gp;
    }

    /** Guild total this week. */
    public static int getWeekPoints(Guild guild) {
        long sum = 0;
        for (GuildMember gm : guild.getMembers()) {
            sum += gm.getMissionPoints();
        }
        return (int) Math.min(Integer.MAX_VALUE, sum);
    }

    /**
     * Settles every finished week not settled yet: rewards each week's total and keeps the total of the week just
     * before this one as last week's result. Runs before any member's points roll over to a new week.
     */
    public static void settle(Guild guild) {
        int cur = GuildContents.currentWeekStart();
        int gp = 0;
        int lastTotal;
        int prevWeek = GuildContents.dateInt(GuildContents.weekStartDate().minusWeeks(1));
        synchronized (guild) {
            int checked = guild.getMissionCheckedWeek();
            if (checked == cur) {
                return;
            }
            Map<Integer, Integer> totals = new TreeMap<>();
            for (GuildMember gm : guild.getMembers()) {
                int week = gm.getMissionWeek();
                if (week > 0 && week >= checked && week < cur) {
                    totals.merge(week, gm.getMissionHunt() + gm.getMissionBoss(), Integer::sum);
                }
            }
            for (int total : totals.values()) {
                gp += getGpReward(total);
            }
            lastTotal = totals.getOrDefault(prevWeek, 0);
            guild.setMissionCheckedWeek(cur);
            guild.setMissionLastPoints(lastTotal);
        }
        if (gp > 0) {
            guild.addGgp(gp);
            guild.broadcast(UserLocal.chatMsg(ChatType.Notice2, String.format(
                    "[Guild] Weekly mission settled (last week: %,d points). The guild gets %,d GP.", lastTotal, gp)));
        }
    }

    /** Hunting and boss points for a kill the character took part in. */
    public static void onMobKill(Char chr, Mob mob) {
        Guild guild = chr.getGuild();
        if (guild == null) {
            return;
        }
        GuildMember gm = guild.getMemberByCharID(chr.getId());
        if (gm == null) {
            return;
        }
        if (mob.isBoss()) {
            if (MobConstants.isBossClearNxMob(mob.getTemplateId())) {
                addBossPoints(chr, guild, gm, getBossPoints(mob.getLevel()));
            }
        } else if (Math.abs(mob.getLevel() - chr.getLevel()) <= HUNT_LEVEL_RANGE) {
            addKill(chr, guild, gm);
        }
    }

    private static void addKill(Char chr, Guild guild, GuildMember gm) {
        settle(guild);
        int gained;
        boolean hitCap;
        synchronized (guild) {
            gm.rollMissionWeek(GuildContents.currentWeekStart());
            if (gm.getMissionHunt() >= HUNT_WEEK_CAP) {
                return;
            }
            gm.setMissionKills(gm.getMissionKills() + 1);
            if (gm.getMissionKills() < HUNT_KILLS_PER_POINT) {
                return;
            }
            gm.setMissionKills(0);
            gained = 1;
            gm.setMissionHunt(gm.getMissionHunt() + gained);
            hitCap = gm.getMissionHunt() >= HUNT_WEEK_CAP;
        }
        chr.write(GuildContents.missionPointsMessage(gained));
        if (hitCap) {
            chr.write(GuildContents.missionLimitMessage());
        }
        updateQuestEx(chr, gm);
    }

    private static void addBossPoints(Char chr, Guild guild, GuildMember gm, int points) {
        if (points <= 0) {
            return;
        }
        settle(guild);
        int gained;
        boolean hitCap;
        synchronized (guild) {
            gm.rollMissionWeek(GuildContents.currentWeekStart());
            gained = Math.min(points, BOSS_WEEK_CAP - gm.getMissionBoss());
            if (gained <= 0) {
                return;
            }
            gm.setMissionBoss(gm.getMissionBoss() + gained);
            hitCap = gm.getMissionBoss() >= BOSS_WEEK_CAP;
        }
        chr.write(GuildContents.bossPointsMessage(gained));
        if (hitCap) {
            chr.write(GuildContents.bossLimitMessage());
        }
        updateQuestEx(chr, gm);
    }

    /** The contents page's own hunting/boss numbers. */
    public static void updateQuestEx(Char chr, GuildMember gm) {
        QuestManager qm = chr.getQuestManager();
        if (qm == null) {
            return;
        }
        Quest quest = qm.getQuestById(QUEST_EX);
        if (quest == null) {
            quest = QuestData.createQuestFromId(QUEST_EX);
            qm.addQuest(quest);
        }
        quest.setProperty("d", net.swordie.ms.util.FileTime.clientNow().format(QUEST_DATE)); // the client's clock
        quest.setProperty("huntPoint", gm.getMissionHunt());
        quest.setProperty("bossPoint", gm.getMissionBoss());
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
    }
}
