package net.swordie.ms.world.field.instance.instancehelper.events;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.constants.ServerRates;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;
import net.swordie.ms.world.field.instance.instancehelper.pq.PartyQuestRecords;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Ursus the Mighty (Dimensional Mirror), after live servers (maplestorywiki.net/w/Ursus): level 100+, a party of 1-6,
 * 3 clears a day. The group shares 80 lives; when they run out the fight ends. Ursus drops no items: everyone gets
 * Mesos by their level and the group's rank (by the time taken, SSS to E), doubled during Golden Time (1-5 AM and
 * 6-10 PM in the server's reset zone), times the server's Meso rate.
 */
public class UrsusInstanceHelper extends InstanceHelper {

    public static final String RECORD_KEY = "ursus";
    public static final int MIN_LEVEL = 100;
    public static final int MAX_PARTY = 6;
    public static final int DAILY_ENTRIES = 3;
    public static final int LIVES = 80;
    public static final int TIME_LIMIT_SECONDS = 60 * 60;

    public static final int ENTRY_MAP = 970072200;
    public static final int BATTLE_MAP = 970072000;
    public static final int EXIT_MAP = 970072100;
    private static final int URSUS = 8881000;

    // rank by minutes taken: SSS, SS, S, A, B, C, D, E
    private static final String[] RANKS = {"SSS", "SS", "S", "A", "B", "C", "D", "E"};
    private static final int[] RANK_MINUTES = {5, 8, 12, 17, 23, 30, 40, Integer.MAX_VALUE};
    private static final double[] RANK_MULTI = {1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3};

    private long startTime;
    private int deaths;
    private boolean finished;

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        instance.setForcedReturn(EXIT_MAP);
        instance.setTimeout(TIME_LIMIT_SECONDS);
        startTime = System.currentTimeMillis();
        addTimer(EventManager.addEvent(() -> {
            Field field = getInstance().getField(BATTLE_MAP);
            if (field != null) {
                field.spawnMob(URSUS, 0, -2, false, -1);
                for (Char c : field.getChars()) {
                    c.chatMessage(ChatType.SystemNotice, "Ursus the Mighty has appeared! The group has " + LIVES
                            + " lives.");
                }
            }
        }, 3000));
    }

    public static String getEntryBlocker(Char chr) {
        var party = chr.getParty();
        if (party != null && party.getPartyLeaderID() != chr.getId()) {
            return "Please have your party leader talk to me.";
        }
        var members = party == null ? java.util.List.of(chr) : party.getPartyMembersInField(chr);
        if (members.size() > MAX_PARTY) {
            return "At most " + MAX_PARTY + " can fight Ursus together.";
        }
        for (Char c : members) {
            if (c.getLevel() < MIN_LEVEL) {
                return c.getName() + " must be at least level " + MIN_LEVEL + ".";
            }
            if (PartyQuestRecords.getTodayEntries(c, RECORD_KEY) >= DAILY_ENTRIES) {
                return c.getName() + " has already fought Ursus " + DAILY_ENTRIES + " times today.";
            }
        }
        return null;
    }

    public static void recordEntries(Char chr) {
        var party = chr.getParty();
        var members = party == null ? java.util.List.of(chr) : party.getPartyMembersInField(chr);
        for (Char c : members) {
            PartyQuestRecords.addEntry(c, RECORD_KEY);
        }
    }

    public static int getRemainingEntries(Char chr) {
        return Math.max(0, DAILY_ENTRIES - PartyQuestRecords.getTodayEntries(chr, RECORD_KEY));
    }

    /** @return whether it's Golden Time (2x Mesos): 1-5 AM or 6-10 PM server time (the reset zone, as the client's clock) */
    public static boolean isGoldenTime() {
        int h = net.swordie.ms.util.ResetClock.now().getHour();
        return (h >= 1 && h < 5) || (h >= 18 && h < 22);
    }

    /** The Mesos for defeating Ursus at a rank (0 = SSS ... 7 = E). */
    public static long getMesoReward(int level, int rank) {
        double base = 2_000_000 + Math.max(0, level - MIN_LEVEL) * 80_000L;
        double meso = base * RANK_MULTI[Math.max(0, Math.min(RANKS.length - 1, rank))]
                * (isGoldenTime() ? 2 : 1) * Math.max(1, ServerRates.getMesoRate());
        return (long) Math.min(Long.MAX_VALUE / 4, meso);
    }

    @Override
    public synchronized void onChrDeath(Char chr) {
        super.onChrDeath(chr);
        if (finished) {
            return;
        }
        deaths++;
        int left = Math.max(0, LIVES - deaths);
        getInstance().chatMessage("Lives left: " + left);
        if (left <= 0) {
            finished = true;
            getInstance().chatMessage("The group has run out of lives. The fight is over.");
            addTimer(EventManager.addEvent(() -> getInstance().clear(), 5000));
        }
    }

    /** Ursus clears on record 33562 (clear=N): the Ursus Defeater medal quests (33578, 33579) need it. */
    private static void addClearRecord(Char c) {
        var qm = c.getQuestManager();
        var quest = qm.getQuestById(CLEAR_RECORD);
        if (quest == null) {
            quest = net.swordie.ms.loaders.QuestData.createQuestFromId(CLEAR_RECORD);
            qm.addQuest(quest);
        }
        int clears = quest.hasProperty("clear") ? quest.getIntProperty("clear") : 0;
        quest.setProperty("clear", clears + 1);
        c.write(net.swordie.ms.connection.packet.WvsContext.message(
                net.swordie.ms.connection.packet.model.MessagePacket.questRecordExMessage(quest)));
    }

    private static final int CLEAR_RECORD = 33562;

    @Override
    public synchronized void onMobDeath(Field field, Mob mob) {
        super.onMobDeath(field, mob);
        if (mob.getTemplateId() != URSUS || finished) {
            return;
        }
        finished = true;
        long minutes = (System.currentTimeMillis() - startTime) / 60_000;
        int rank = 0;
        while (rank < RANK_MINUTES.length - 1 && minutes >= RANK_MINUTES[rank]) {
            rank++;
        }
        for (Char c : getInstance().getChars()) {
            long meso = getMesoReward(c.getLevel(), rank);
            if (c.canAddMoney(meso)) {
                c.addMoney(meso);
            }
            PartyQuestRecords.addClear(c, RECORD_KEY);
            addClearRecord(c);
            c.chatMessage(ChatType.SystemNotice, "Ursus is defeated! Rank " + RANKS[rank] + ": you got " + meso
                    + " Mesos" + (isGoldenTime() ? " (Golden Time x2)" : "") + ".");
        }
        addTimer(EventManager.addEvent(() -> getInstance().clear(), 10_000));
    }
}
