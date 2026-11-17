package net.swordie.ms.handlers.user;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.enums.MaplePlannerRequest;
import net.swordie.ms.enums.MaplePlannerTab;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.handlers.script.QuestHandler;
import net.swordie.ms.loaders.EtcData;
import net.swordie.ms.loaders.QuestData;
import net.swordie.ms.loaders.containerclasses.QuestInfo;
import net.swordie.ms.loaders.containerclasses.SchedulerContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Maple Planner. Save keeps a tab's checked rows in its quest-ex record (MaplePlannerTab). Start, Complete and
 * Start All / Complete All work on the quest rows of Etc.wz MapleScheduler.img/contents: they run the row's quest the
 * way its NPC does (its start script, or the mission's end script once the mission is done), with the checks of a
 * quest request. Only quests listed in the planner can be started or completed this way.
 * <p>
 * Based on a fix contributed by Twoal (from the Discord).
 */
public class MaplePlannerHandler {

    private static final Logger log = LogManager.getLogger();

    private static final int INT_SIZE = 4;
    private static final int NO_NPC = 0;
    private static final char CHECKED = '1';
    private static final char UNCHECKED = '0';
    private static final String SCHEDULED_KEY = "scheduled";
    private static final String TYPE_ORDER_KEY = "typeOrder";
    private static final List<MaplePlannerTab> QUEST_TABS = List.of(MaplePlannerTab.Daily, MaplePlannerTab.Weekly);

    /** What a row's Start or Complete did. */
    private enum Outcome {
        NOTHING, DONE, DIALOG
    }

    @Handler(op = InHeader.MAPLE_PLANNER_MODIFIED)
    public static void handleMaplePlannerModified(Char chr, InPacket inPacket) {
        int requestVal = inPacket.decodeInt();
        MaplePlannerRequest request = MaplePlannerRequest.getByVal(requestVal);
        MaplePlannerTab tab = inPacket.getUnreadAmount() >= INT_SIZE
                ? MaplePlannerTab.getByVal(inPacket.decodeInt()) : null;
        if (request == null) {
            log.debug("Unknown Maple Planner request {} ({} bytes left)", requestVal, inPacket.getUnreadAmount());
        } else {
            switch (request) {
                case Start -> {
                    if (inPacket.getUnreadAmount() >= INT_SIZE) {
                        advance(chr, content(tab, inPacket.decodeInt()));
                    }
                }
                case Complete -> {
                    // the row and the quest it wants closed, or only the quest
                    int row = inPacket.getUnreadAmount() >= 2 * INT_SIZE ? inPacket.decodeInt() : -1;
                    int questId = inPacket.getUnreadAmount() >= INT_SIZE ? inPacket.decodeInt() : 0;
                    complete(chr, tab, row, questId);
                }
                case All -> advanceAll(chr);
                case Save -> save(chr, tab, inPacket);
            }
        }
        chr.write(UserLocal.maplePlannerUpdate(requestVal));
    }

    private static void save(Char chr, MaplePlannerTab tab, InPacket inPacket) {
        int count = inPacket.getUnreadAmount() >= INT_SIZE ? inPacket.decodeInt() : -1;
        if (tab == null || count < 0 || count > tab.getFlags() || inPacket.getUnreadAmount() < count * INT_SIZE) {
            return;
        }
        char[] flags = new char[tab.getFlags()];
        Arrays.fill(flags, UNCHECKED);
        for (int i = 0; i < count; i++) {
            int row = inPacket.decodeInt();
            if (row >= 0 && row < flags.length) {
                flags[row] = CHECKED;
            }
        }
        ClientRecordHandler.setQuestEx(chr, tab.getRecordId(),
                SCHEDULED_KEY + "=" + new String(flags) + ";" + TYPE_ORDER_KEY + "=" + tab.getTypeOrder());
    }

    /** A row's Start: completes the mission when it's done, starts the row's quest otherwise. */
    private static void advance(Char chr, SchedulerContent content) {
        if (content != null && completeReady(chr, content) == Outcome.NOTHING) {
            start(chr, content);
        }
    }

    /** A row's Complete, for the quest the client names when it's one of the row's quests. */
    private static void complete(Char chr, MaplePlannerTab tab, int row, int questId) {
        SchedulerContent content = row >= 0 ? content(tab, row) : null;
        if (content == null && questId > 0) {
            content = contentByQuest(tab, questId);
        }
        if (content == null) {
            return;
        }
        if (!content.quests().contains(questId) || finish(chr, questId) == Outcome.NOTHING) {
            completeReady(chr, content);
        }
    }

    /**
     * Start All (nothing scheduled taken yet) or Complete All over the scheduled daily and weekly rows. A quest that
     * runs its NPC's script opens a dialog, so one press handles the rows up to the first dialog.
     */
    private static void advanceAll(Char chr) {
        QuestManager qm = chr.getQuestManager();
        List<SchedulerContent> scheduled = scheduled(chr);
        boolean completing = scheduled.stream().flatMap(c -> c.quests().stream())
                .anyMatch(q -> qm.hasQuestInProgress(q) || qm.hasQuestCompleted(q));
        for (SchedulerContent content : scheduled) {
            if ((completing ? completeReady(chr, content) : start(chr, content)) == Outcome.DIALOG) {
                return;
            }
        }
    }

    /** Completes the first of the row's quests that is in progress and done. */
    private static Outcome completeReady(Char chr, SchedulerContent content) {
        for (int questId : content.quests()) {
            Outcome outcome = finish(chr, questId);
            if (outcome != Outcome.NOTHING) {
                return outcome;
            }
        }
        return Outcome.NOTHING;
    }

    private static Outcome finish(Char chr, int questId) {
        QuestManager qm = chr.getQuestManager();
        QuestInfo qi = QuestData.getQuestInfoById(questId);
        if (qi == null || !qm.isComplete(questId) || !qm.meetsCompleteRequirements(questId)) {
            return Outcome.NOTHING;
        }
        if (hasScript(qi.getEndScript())) {
            QuestHandler.openQuestScript(chr, qi, false, NO_NPC);
            return Outcome.DIALOG;
        }
        return qm.completeQuest(questId, true) ? Outcome.DONE : Outcome.NOTHING;
    }

    private static Outcome start(Char chr, SchedulerContent content) {
        QuestManager qm = chr.getQuestManager();
        QuestInfo qi = QuestData.getQuestInfoById(content.quest());
        if (qi == null || !qm.canStartQuest(content.quest())) {
            return Outcome.NOTHING;
        }
        if (hasScript(qi.getStartScript())) {
            // the script picks the mission (linkQuest) as the NPC does
            QuestHandler.openQuestScript(chr, qi, true, NO_NPC);
            return Outcome.DIALOG;
        }
        qm.addQuest(QuestData.createQuestFromId(content.quest()));
        return Outcome.DONE;
    }

    private static boolean hasScript(String script) {
        return script != null && !script.isEmpty();
    }

    /** The scheduled ("scheduled" flag set) quest rows of the daily and weekly tabs. */
    private static List<SchedulerContent> scheduled(Char chr) {
        List<SchedulerContent> res = new ArrayList<>();
        for (MaplePlannerTab tab : QUEST_TABS) {
            Quest record = chr.getQuestManager().getQuestById(tab.getRecordId());
            String flags = record == null ? null : record.getProperty(SCHEDULED_KEY);
            if (flags == null) {
                continue;
            }
            for (int row = 0; row < flags.length(); row++) {
                SchedulerContent content = flags.charAt(row) == CHECKED ? content(tab, row) : null;
                if (content != null) {
                    res.add(content);
                }
            }
        }
        return res;
    }

    /** @return the tab's quest row, or null for other rows (field contents can't be started from the planner) */
    private static SchedulerContent content(MaplePlannerTab tab, int row) {
        if (tab == null || tab.getSchedulerTab() == null) {
            return null;
        }
        SchedulerContent content = EtcData.getSchedulerContents(tab.getSchedulerTab()).get(row);
        return content != null && content.isQuest() ? content : null;
    }

    private static SchedulerContent contentByQuest(MaplePlannerTab tab, int questId) {
        if (tab == null || tab.getSchedulerTab() == null) {
            return null;
        }
        return EtcData.getSchedulerContents(tab.getSchedulerTab()).values().stream()
                .filter(c -> c.isQuest() && c.quests().contains(questId)).findAny().orElse(null);
    }
}
