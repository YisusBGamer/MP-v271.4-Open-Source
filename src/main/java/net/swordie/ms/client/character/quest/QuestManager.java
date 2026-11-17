package net.swordie.ms.client.character.quest;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.quest.progress.QuestProgressItemRequirement;
import net.swordie.ms.client.character.quest.progress.QuestProgressMobRequirement;
import net.swordie.ms.client.character.quest.progress.QuestProgressRequirement;
import net.swordie.ms.client.character.quest.requirement.*;
import net.swordie.ms.client.character.quest.reward.QuestBuffItemReward;
import net.swordie.ms.client.character.quest.reward.QuestItemReward;
import net.swordie.ms.client.character.quest.reward.QuestMoneyReward;
import net.swordie.ms.client.character.quest.reward.QuestReward;
import net.swordie.ms.client.surprisemission.SurpriseMissionModule;
import net.swordie.ms.connection.packet.Effect;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.enums.InvType;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.loaders.StringData;
import net.swordie.ms.enums.MobSetType;
import net.swordie.ms.enums.QuestStatus;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.QuestData;
import net.swordie.ms.loaders.containerclasses.QuestInfo;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.orm.dao.QuestDao;
import net.swordie.orm.dao.SworDaoFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

import static net.swordie.ms.enums.ChatType.Mob;
import static net.swordie.ms.enums.QuestStatus.*;

/**
 * Created on 12/20/2017.
 */
public class QuestManager {

    private static final Logger log = LogManager.getLogger();
    /** server-only record ids (quest ids Quest.wz does not use) */
    public static final int HIDDEN_RECORD_FIRST = 89999900, HIDDEN_RECORD_LAST = 89999999;
    private static final QuestDao questDao = (QuestDao) SworDaoFactory.getByClass(Quest.class);

    private long id;

    private Map<Integer, Quest> quests;

    @Transient
    private Char chr;

    // Bulk completions (story skips) aren't sent one by one: each MESSAGE quest record makes the client show a
    // "Quest complete!" notice. They reach the client in the CharacterData of the next field entry instead.
    @Transient
    private final Set<Integer> untold = java.util.concurrent.ConcurrentHashMap.newKeySet();
    @Transient
    private volatile int quietDepth;
    @Transient
    private volatile boolean heldForField;

    public QuestManager() {

    }

    public QuestManager(Char chr) {
        quests = new java.util.concurrent.ConcurrentHashMap<>();
        this.chr = chr;
    }

    /**
     * @return the quest records sent to the client at login (not the server-only records, see isHiddenRecordId)
     */
    public Collection<Quest> getEx() {
        return getQuests().values().stream()
                .filter(quest -> !isHiddenRecordId(quest.getQRKey()))
                .collect(Collectors.toList());
    }

    public Set<Quest> getCompletedQuests() {
        return getQuests().values().stream()
                .filter(quest -> quest.getStatus() == Completed)
                .collect(Collectors.toSet());
    }

    public Set<Quest> getQuestsInProgress() {
        return getQuests().values().stream().
                filter(quest -> quest.getStatus() == Started && !isHiddenRecordId(quest.getQRKey()))
                .collect(Collectors.toSet());
    }

    /**
     * @return whether the id is one of the server-only records (setHiddenRecord): they're stored as quests, but are
     * never sent to the client or treated as quests in progress
     */
    public static boolean isHiddenRecordId(int questID) {
        return questID >= HIDDEN_RECORD_FIRST && questID <= HIDDEN_RECORD_LAST;
    }

    public int getSize() {
        return quests.size();
    }

    public Map<Integer, Quest> getQuests() {
        if (quests == null) {
            // scripts, bots and the client thread read and change a character's quests at the same time
            quests = new java.util.concurrent.ConcurrentHashMap<>(questDao.byQuestManager(this));
        }
        return quests;
    }

    public boolean hasQuestInProgress(int questID) {
        Quest quest = getQuestById(questID);
        return quest != null && quest.getStatus() == Started;
    }

    /**
     * Checks if a quest has been completed, i.e. the status is COMPLETE.
     *
     * @param questID the quest's id to check
     * @return quest completeness
     */
    public boolean hasQuestCompleted(int questID) {
        Quest quest = getQuestById(questID);
        return quest != null && quest.getStatus() == Completed;
    }

    /**
     * Checks if a quest is complete. This means that a quest's status is STARTED, and that all the requirements to
     * complete it have been met.
     *
     * @param questID the quest's id to check
     * @return completeness
     */
    public boolean isComplete(int questID) {
        Quest quest = getQuestById(questID);
        return hasQuestInProgress(questID) && quest.isComplete(chr);
    }

    public void addQuest(Quest quest) {
        addQuest(quest, true);
    }

    public void addCustomQuest(Quest quest) {
        addQuest(quest, false);
    }

    /**
     * Adds a new {@link Quest} to this QuestManager's quests. If it already exists, doesn't do anything.
     *
     * @param quest            The Quest to add.
     * @param addRewardsFromWz Whether or not to addRewards from the WzFiles
     */
    private void addQuest(Quest quest, boolean addRewardsFromWz) {
        //If quest is repeatable, first remove the previous (completed) record from the quest list
        Quest existing = getQuestById(quest.getQRKey());
        if (existing != null) {
            QuestInfo existingInfo = QuestData.getQuestInfoById(quest.getQRKey());
            boolean repeatable = QuestConstants.REPEATABLE_QUESTS.contains(quest.getQRKey())
                    || (existing.getStatus() == Completed && existingInfo != null && canRepeat(existingInfo, existing));
            if ((repeatable || existing.getStatus() == NotStarted) && existing != quest) {
                getQuests().remove(quest.getQRKey());
                questDao.delete(existing);
            }
        }
        if (getQuestById(quest.getQRKey()) == null) {
            getQuests().put(quest.getQRKey(), quest);

            if (quest.getProperties() == null || quest.getProperties().size() == 0) {
                sendRecord(quest);
            } else {
                chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
            }

            if (quest.getStatus() == QuestStatus.Completed) {
                chr.debugMessage("[Info] Completed quest " + quest.getQRKey());
            } else {
                chr.debugMessage("[Info] Accepted quest " + quest.getQRKey());
                if (addRewardsFromWz) {
                    QuestInfo qi = QuestData.getQuestInfoById(quest.getQRKey());
                    if (qi != null) {
                        giveRewards(qi, 0, -1);
                    }
                    QuestNpcVisibility.refresh(chr); // the NPC that ends it may be hidden in this map
                }
            }
        }
    }

    /**
     * Gives the rewards (Quest.wz/Act.img) of a quest for the given state.
     *
     * @param qi        the quest's info
     * @param status    0 for the rewards given on start, 1 for the rewards given on completion
     * @param selection index of the selectable item the player picked, or -1
     */
    private void giveRewards(QuestInfo qi, int status, int selection) {
        List<QuestItemReward> items = resolveItemRewards(qi, status, selection);
        for (QuestReward qr : qi.getQuestRewards()) {
            if (qr instanceof QuestItemReward) {
                continue;
            }
            boolean rewardOnStart = qr.getStatus() == 0;
            if (rewardOnStart == (status == 0)) {
                qr.giveReward(chr);
            }
        }
        for (QuestItemReward qir : items) {
            qir.giveReward(chr);
        }
    }

    /**
     * Determines which item rewards a quest gives for the given state, following the Act.img rules:
     * items with prop 0 are always given, one item out of those with prop &gt; 0 is picked at random (weighted by
     * prop), and out of those with prop -1 the item the player selected is given. Items not meant for the Char's
     * job or gender are skipped.
     *
     * @param qi        the quest's info
     * @param status    0 for start rewards, 1 for completion rewards
     * @param selection index of the selected item among the selectable items, or -1
     * @return the item rewards that should be given
     */
    public List<QuestItemReward> resolveItemRewards(QuestInfo qi, int status, int selection) {
        List<QuestItemReward> fixed = new ArrayList<>();
        List<QuestItemReward> random = new ArrayList<>();
        List<QuestItemReward> selectable = new ArrayList<>();
        for (QuestReward qr : qi.getQuestRewards()) {
            if (!(qr instanceof QuestItemReward)) {
                continue;
            }
            QuestItemReward qir = (QuestItemReward) qr;
            boolean rewardOnStart = qir.getStatus() == 0;
            if (rewardOnStart != (status == 0) || !qir.isEligible(chr)) {
                continue;
            }
            if (qir.isRandom()) {
                random.add(qir);
            } else if (qir.isSelectable()) {
                selectable.add(qir);
            } else {
                fixed.add(qir);
            }
        }
        List<QuestItemReward> result = new ArrayList<>(fixed);
        if (!random.isEmpty()) {
            int total = random.stream().mapToInt(QuestItemReward::getProp).sum();
            int roll = Util.getRandom(total - 1);
            for (QuestItemReward qir : random) {
                roll -= qir.getProp();
                if (roll < 0) {
                    result.add(qir);
                    break;
                }
            }
        }
        if (!selectable.isEmpty()) {
            if (selection >= 0 && selection < selectable.size()) {
                result.add(selectable.get(selection));
            } else {
                log.debug(String.format("No valid reward selection (%d) for quest %d, giving the first option.",
                        selection, qi.getQuestID()));
                result.add(selectable.get(0));
            }
        }
        return result;
    }

    /**
     * Checks whether the Char has room for the given item rewards (rewards that take items away are ignored).
     */
    /**
     * Explains which inventory tabs need more free slots for the given item rewards, e.g.
     * "Your inventory is full: you need 2 free Equip slot(s) and 1 free Use slot(s) to complete this quest."
     */
    public String describeSpaceNeeded(List<QuestItemReward> rewards, String action) {
        Map<InvType, Integer> needed = new EnumMap<>(InvType.class);
        for (QuestItemReward qir : rewards) {
            if (qir.getQuantity() < 0) {
                continue;
            }
            var item = ItemData.getItemDeepCopy(qir.getId());
            if (item == null) {
                continue;
            }
            InvType type = item.getInvType();
            int quantity = Math.max(1, qir.getQuantity());
            int slots = 1;
            if (type.isStackable(item.getItemId())) {
                var ii = ItemData.getItemInfoByID(item.getItemId());
                int slotMax = ii == null || ii.getSlotMax() <= 0 ? 1 : ii.getSlotMax();
                var existing = chr.getInventoryByType(type).getItemByItemIDAndStackable(item.getItemId());
                int room = existing == null ? 0 : Math.max(0, slotMax - existing.getQuantity());
                slots = quantity <= room ? 0 : (int) Math.ceil((quantity - room) / (double) slotMax);
            }
            needed.merge(type, slots, Integer::sum);
        }
        List<String> parts = new ArrayList<>();
        for (Map.Entry<InvType, Integer> e : needed.entrySet()) {
            int free = chr.getInventoryByType(e.getKey()).getEmptySlots();
            if (e.getValue() > free) {
                parts.add(String.format("%d free %s slot(s) (you have %d)", e.getValue(), tabName(e.getKey()), free));
            }
        }
        if (parts.isEmpty()) {
            return "Your inventory is full. Please make more space before trying to " + action + ".";
        }
        return "Your inventory is full: you need " + String.join(" and ", parts) + " to " + action + ".";
    }

    private static String tabName(InvType type) {
        switch (type) {
            case CONSUME:
                return "Use";
            case INSTALL:
                return "Set-up";
            default:
                return type.toString();
        }
    }

    /**
     * @return the quests in progress that need the given item and aren't ready to be turned in yet (empty when the
     * quest ready notice is off)
     */
    public List<Quest> getQuestsNotReadyNeedingItem(int itemID) {
        if (!net.swordie.ms.constants.ServerFeatures.on(net.swordie.ms.constants.ServerFeatures.Feature.QUEST_READY_NOTICE) || getQuests() == null) {
            return Collections.emptyList();
        }
        List<Quest> res = new ArrayList<>();
        for (Quest q : getQuestsInProgress()) {
            if (q.getItemReqs().stream().anyMatch(r -> r.getItemID() == itemID) && !q.isComplete(chr)) {
                res.add(q);
            }
        }
        return res;
    }

    /**
     * Tells the player about the given quests that have just become ready to be turned in.
     */
    public void notifyQuestsReady(Collection<Quest> quests) {
        for (Quest q : quests) {
            notifyIfReady(q);
        }
    }

    /**
     * QoL: tells the player that a quest in progress can be turned in, and to whom.
     */
    public void notifyIfReady(Quest q) {
        if (q == null || !hasQuestInProgress(q.getQRKey()) || !q.isComplete(chr)
                || !meetsCompleteRequirements(q.getQRKey())) {
            return;
        }
        net.swordie.ms.client.character.ClassQuestSetupModule.onQuestReady(chr, q.getQRKey());
        if (!net.swordie.ms.constants.ServerFeatures.on(net.swordie.ms.constants.ServerFeatures.Feature.QUEST_READY_NOTICE)) {
            return;
        }
        QuestInfo qi = QuestData.getQuestInfoById(q.getQRKey());
        String name = StringData.getQuestStringById(q.getQRKey());
        int npc = qi == null ? 0 : (qi.getEndNpc() != 0 ? qi.getEndNpc() : qi.getStartNpc());
        String npcName = npc == 0 ? null : StringData.getNpcStringById(npc);
        chr.chatMessage(ChatType.Notice2, String.format("[Quest] %s is ready to be completed%s.",
                name == null ? "Quest " + q.getQRKey() : name,
                npcName == null ? "" : " - talk to " + npcName));
    }

    private boolean canHoldItemRewards(List<QuestItemReward> rewards) {
        var items = new ArrayList<Item>();
        for (QuestItemReward qir : rewards) {
            if (qir.getQuantity() >= 0) {
                var item = ItemData.getItemDeepCopy(qir.getId());
                if (item == null) {
                    continue;
                }
                item.setQuantity(Math.max(1, qir.getQuantity()));
                items.add(item);
            }
        }
        return items.isEmpty() || chr.canHold(items);
    }

    /**
     * Checks whether a completed quest may be started again: the interval (minutes) has passed, or the daily/weekly
     * reset happened since the quest was completed.
     *
     * @param qi    the quest's info
     * @param quest the completed quest record
     * @return whether or not the quest may be done again
     */
    public boolean canRepeat(QuestInfo qi, Quest quest) {
        if (qi == null || !qi.isRepeatable()) {
            return false;
        }
        FileTime completedTime = quest == null ? null : quest.getCompletedTime();
        if (completedTime == null) {
            return true;
        }
        long completedMillis = completedTime.toMillis();
        long nowMillis = System.currentTimeMillis();
        if (qi.getRepeatInterval() >= 0 && nowMillis - completedMillis >= qi.getRepeatInterval() * 60_000L) {
            return true;
        }
        java.time.LocalDate today = net.swordie.ms.util.ResetClock.today();
        java.time.LocalDate completedDay = net.swordie.ms.util.ResetClock.dayOf(completedMillis);
        if (qi.isDayByDay() && today.isAfter(completedDay)) {
            return true;
        }
        int alarmReset = dailyAlarmReset(qi);
        if (alarmReset == DAILY_ALARM_DAILY) {
            return today.isAfter(completedDay);
        }
        if (qi.isWeeklyRepeat() || alarmReset == DAILY_ALARM_WEEKLY) {
            java.time.LocalDate weekStart = net.swordie.ms.util.ResetClock.weekStart(
                    qi.getQuestID() == ERDA_REQUEST_WEEKLY_QUEST
                            ? net.swordie.ms.client.character.AccountWeekly.ERDA_REQUEST_RESET_DAY
                            : QuestConstants.QUEST_WEEKLY_RESET_DAY);
            return completedDay.isBefore(weekStart);
        }
        return false;
    }

    private static final int DAILY_ALARM_NONE = 0;
    private static final int DAILY_ALARM_DAILY = 1;
    private static final int DAILY_ALARM_WEEKLY = 2;

    /** [Weekly Quest] Erda's Request: daily while setting erda_request_daily is on */
    public static final int ERDA_REQUEST_WEEKLY_QUEST = 504608;
    /** QuestInfo dailyAlarm quests (no other repeat data) that reset each day. */
    private static final Set<Integer> DAILY_ALARM_DAILY_QUESTS = new HashSet<>();
    /** QuestInfo dailyAlarm quests named "[Weekly Quest] ..." that reset each week. */
    private static final Set<Integer> DAILY_ALARM_WEEKLY_QUESTS = new HashSet<>();
    /**
     * dailyAlarm quests that never reset here: one-time unlocks other quests need completed, and the region dailies,
     * which DailiesManager resets.
     */
    private static final Set<Integer> DAILY_ALARM_NEVER_RESET = Set.of(
            34128, // Temple of Time Research Expedition
            34377, // Gray Mask's Request
            34772, // Ollie's Message
            35551, // Tenebris Research Group
            39013, // The Mad Chef
            39034, // The Tree Spirits' Request
            QuestConstants.VANISHING_JOURNEY_DAILY_QUEST,
            QuestConstants.CHU_CHU_DAILY_QUEST,
            QuestConstants.LACHELEIN_DAILY_QUEST,
            QuestConstants.ARCANA_DAILY_QUEST,
            QuestConstants.MORASS_DAILY_QUEST,
            QuestConstants.ESFERA_DAILY_QUEST,
            QuestConstants.CERNIUM_DAILY_QUEST,
            QuestConstants.HOTEL_ARCUS_DAILY_QUEST
    );

    static {
        addRange(DAILY_ALARM_DAILY_QUESTS, 39922, 39922); // Nerota's Proposal
        addRange(DAILY_ALARM_DAILY_QUESTS, 64274, 64274); // MONAD mission board
        addRange(DAILY_ALARM_DAILY_QUESTS, 64878, 64889); // Phantom Forest Wanted / Elite Hunt
        addRange(DAILY_ALARM_DAILY_QUESTS, 64891, 64894);
        addRange(DAILY_ALARM_DAILY_QUESTS, 64898, 64898); // Afinas
        addRange(DAILY_ALARM_DAILY_QUESTS, 65306, 65307); // Showa Town
        addRange(DAILY_ALARM_DAILY_QUESTS, 65310, 65315); // Punish Yakuza
        addRange(DAILY_ALARM_DAILY_QUESTS, 35550, 35550); // Tenebris Research
        addRange(DAILY_ALARM_DAILY_QUESTS, 35560, 35594); // Tenebris Research missions
        addRange(DAILY_ALARM_DAILY_QUESTS, 66882, 66882); // Erda's Request

        addRange(DAILY_ALARM_WEEKLY_QUESTS, 15418, 15418); // World Tree
        addRange(DAILY_ALARM_WEEKLY_QUESTS, 15708, 15708); // Dark World Tree
        addRange(DAILY_ALARM_WEEKLY_QUESTS, 32429, 32456); // Kritias
        addRange(DAILY_ALARM_WEEKLY_QUESTS, 32458, 32479);
        addRange(DAILY_ALARM_WEEKLY_QUESTS, 34151, 34151); // Diligent Research Reward
        addRange(DAILY_ALARM_WEEKLY_QUESTS, 39002, 39012); // World Tree
        addRange(DAILY_ALARM_WEEKLY_QUESTS, 39101, 39108); // Haven
        addRange(DAILY_ALARM_WEEKLY_QUESTS, 39111, 39119); // Scrapyard
        addRange(DAILY_ALARM_WEEKLY_QUESTS, 39121, 39127); // Skyline
        addRange(DAILY_ALARM_WEEKLY_QUESTS, 39131, 39136); // Black Heaven Deck
        addRange(DAILY_ALARM_WEEKLY_QUESTS, 39141, 39155); // Black Heaven Inside
        addRange(DAILY_ALARM_WEEKLY_QUESTS, 39160, 39160); // Request Reward
        addRange(DAILY_ALARM_WEEKLY_QUESTS, 39165, 39165); // Haven Weekly Mission
        addRange(DAILY_ALARM_WEEKLY_QUESTS, 503281, 503281); // Legion Weekly Dragon Extermination
        addRange(DAILY_ALARM_DAILY_QUESTS, 504608, 504608); // Erda's Request, 5,000 kills (daily here, weekly in GMS)
    }

    private static void addRange(Set<Integer> set, int from, int to) {
        for (int id = from; id <= to; id++) {
            set.add(id);
        }
    }

    /**
     * Quests whose only repeat data is QuestInfo dailyAlarm (no interval, dayByDay or weeklyRepeat in Check.img) reset
     * only when listed above.
     *
     * @return DAILY_ALARM_DAILY, DAILY_ALARM_WEEKLY or DAILY_ALARM_NONE
     */
    private static int dailyAlarmReset(QuestInfo qi) {
        if (!qi.isDailyAlarm() || qi.isDayByDay() || qi.isWeeklyRepeat() || qi.getRepeatInterval() >= 0
                || DAILY_ALARM_NEVER_RESET.contains(qi.getQuestID())) {
            return DAILY_ALARM_NONE;
        }
        if (qi.getQuestID() == ERDA_REQUEST_WEEKLY_QUEST
                && !net.swordie.ms.constants.ServerFeatures.on(net.swordie.ms.constants.ServerFeatures.Feature.ERDA_REQUEST_DAILY)) {
            return DAILY_ALARM_WEEKLY; // official: weekly, the reward once per account (AccountWeekly)
        }
        if (DAILY_ALARM_DAILY_QUESTS.contains(qi.getQuestID())) {
            return DAILY_ALARM_DAILY;
        }
        return DAILY_ALARM_WEEKLY_QUESTS.contains(qi.getQuestID()) ? DAILY_ALARM_WEEKLY : DAILY_ALARM_NONE;
    }

    /**
     * Requirements that were added to match the official server. They are only enforced for quest requests coming
     * from the client (see {@link QuestConstants#STRICT_QUEST_REQUIREMENTS}), not for quests started by scripts.
     */
    private static boolean isStrictOnlyRequirement(QuestStartRequirement qsr) {
        return qsr instanceof QuestStartCompletionRequirement
                || qsr instanceof QuestStartJobRequirement
                || qsr instanceof QuestRecordRequirement
                || qsr instanceof QuestSkillRequirement
                || qsr instanceof QuestEquipRequirement
                || qsr instanceof QuestWorldRequirement
                || qsr instanceof QuestTimeRequirement;
    }

    /**
     * Checks a set of Check.img requirements. Other-quest requirements are AND'ed, or OR'ed when QuestOrOption is set.
     *
     * @return the first requirement that isn't met, or null if all are met
     */
    private QuestStartRequirement findUnmetRequirement(Set<QuestStartRequirement> reqs, boolean questOrOption,
                                                       boolean strict) {
        return findUnmetRequirement(reqs, questOrOption, false, strict);
    }

    /**
     * @param itemOrOption Check.img ItemOrOption: having any one of the listed items (with the needed count) is enough
     */
    private QuestStartRequirement findUnmetRequirement(Set<QuestStartRequirement> reqs, boolean questOrOption,
                                                       boolean itemOrOption, boolean strict) {
        List<QuestStartRequirement> questReqs = new ArrayList<>();
        List<QuestStartRequirement> itemReqs = new ArrayList<>();
        for (QuestStartRequirement qsr : reqs) {
            if (!strict && isStrictOnlyRequirement(qsr)) {
                continue;
            }
            if (qsr instanceof QuestStartCompletionRequirement) {
                questReqs.add(qsr);
            } else if (itemOrOption && qsr instanceof QuestStartItemRequirement qsir && qsir.getQuantity() > 0) {
                itemReqs.add(qsr);
            } else if (!qsr.hasRequirements(chr)) {
                return qsr;
            }
        }
        if (!itemReqs.isEmpty() && itemReqs.stream().noneMatch(q -> q.hasRequirements(chr))) {
            return itemReqs.get(0);
        }
        if (!questReqs.isEmpty()) {
            if (questOrOption) {
                if (questReqs.stream().noneMatch(q -> q.hasRequirements(chr))) {
                    return questReqs.get(0);
                }
            } else {
                for (QuestStartRequirement q : questReqs) {
                    if (!q.hasRequirements(chr)) {
                        return q;
                    }
                }
            }
        }
        return null;
    }

    private static String describeRequirement(QuestStartRequirement qsr) {
        if (qsr instanceof QuestStartCompletionRequirement) {
            var q = (QuestStartCompletionRequirement) qsr;
            String state = q.getQuestStatus() == 2 ? "completed" : q.getQuestStatus() == 1 ? "in progress" : "not started";
            return String.format("quest %d must be %s", q.getQuestID(), state);
        } else if (qsr instanceof QuestRecordRequirement) {
            return String.format("quest record of quest %d", ((QuestRecordRequirement) qsr).getQuestID());
        } else if (qsr instanceof QuestStartJobRequirement) {
            return "job";
        } else if (qsr instanceof QuestSkillRequirement) {
            return String.format("skill %d", ((QuestSkillRequirement) qsr).getSkillID());
        }
        return qsr.getClass().getSimpleName();
    }

    /**
     * What a player is told when a quest won't start from the quest window or an NPC: the first requirement the
     * character misses, in words. Null when nothing is missing (or nothing a player could act on).
     */
    public String playerReasonCantStart(int questID) {
        QuestInfo qi = QuestData.getQuestInfoById(questID);
        if (qi == null) {
            return null;
        }
        Quest quest = getQuestById(questID);
        if (quest != null && quest.getStatus() == Started) {
            return "You're already on this quest.";
        }
        if (quest != null && quest.getStatus() == Completed && !canRepeat(qi, quest)
                && !QuestConstants.REPEATABLE_QUESTS.contains(questID)) {
            return "You've already completed this quest.";
        }
        if (qi.isUnreachable()) {
            return "This quest is no longer available.";
        }
        if (qi.isRetired()) {
            return "This quest is no longer available: a newer questline replaces it.";
        }
        QuestStartRequirement unmet = findUnmetRequirement(qi.getQuestStartRequirements(), qi.isStartQuestOrOption(),
                qi.isStartItemOrOption(), QuestConstants.STRICT_QUEST_REQUIREMENTS);
        return unmet == null ? null : playerReason(unmet, "start");
    }

    /** As {@link #playerReasonCantStart(int)}, for turning a quest in. */
    public String playerReasonCantComplete(int questID) {
        QuestInfo qi = QuestData.getQuestInfoById(questID);
        Quest quest = getQuestById(questID);
        if (qi == null || quest == null) {
            return null;
        }
        if (!quest.isComplete(chr)) {
            return "This quest isn't finished yet. Check what's left in your Quest window.";
        }
        QuestStartRequirement unmet = findUnmetRequirement(qi.getQuestCompleteRequirements(),
                qi.isCompleteQuestOrOption(), QuestConstants.STRICT_QUEST_REQUIREMENTS);
        return unmet == null ? null : playerReason(unmet, "complete");
    }

    /**
     * The Quest.wz/Say.img stop branch for why a quest can't be started (part 0) or handed in (part 1) at an NPC:
     * "npc" (not this quest's NPC), "mob", "item", "quest", "info" (a quest record) or "default". Null when nothing
     * is missing.
     */
    public String stopReason(int questID, int part, int npcTemplateID) {
        QuestInfo qi = QuestData.getQuestInfoById(questID);
        if (qi == null) {
            return null;
        }
        int questNpc = part == 0 ? qi.getStartNpc() : qi.getEndNpc();
        if (npcTemplateID != 0 && questNpc != 0 && npcTemplateID != questNpc) {
            return "npc";
        }
        if (part == 1) {
            Quest quest = getQuestById(questID);
            if (quest == null || quest.getStatus() != Started) {
                return null;
            }
            if (!quest.isComplete(chr)) {
                for (QuestProgressRequirement pr : quest.getProgressRequirements()) {
                    if (!pr.isComplete(chr)) {
                        return pr instanceof QuestProgressMobRequirement ? "mob"
                                : pr instanceof QuestProgressItemRequirement ? "item" : "default";
                    }
                }
                return "default";
            }
        }
        QuestStartRequirement unmet = part == 0
                ? findUnmetRequirement(qi.getQuestStartRequirements(), qi.isStartQuestOrOption(),
                qi.isStartItemOrOption(), QuestConstants.STRICT_QUEST_REQUIREMENTS)
                : findUnmetRequirement(qi.getQuestCompleteRequirements(), qi.isCompleteQuestOrOption(),
                QuestConstants.STRICT_QUEST_REQUIREMENTS);
        if (unmet == null) {
            return null;
        }
        if (unmet instanceof QuestStartCompletionRequirement) {
            return "quest";
        }
        if (unmet instanceof QuestRecordRequirement) {
            return "info";
        }
        if (unmet instanceof QuestStartItemRequirement || unmet instanceof QuestEquipRequirement) {
            return "item";
        }
        return "default";
    }

    private String playerReason(QuestStartRequirement r, String what) {
        if (r instanceof QuestStartMinStatRequirement ms && ms.getStat() == Stat.level) {
            return String.format("You need to be level %d to %s this quest.", ms.getReqAmount(), what);
        }
        if (r instanceof QuestStartMaxLevelRequirement ml) {
            return String.format("This quest is only for characters up to level %d.", ml.getLevel());
        }
        if (r instanceof QuestStartCompletionRequirement c) {
            String name = StringData.getQuestStringById(c.getQuestID());
            name = name == null || name.isBlank() ? "quest " + c.getQuestID() : "'" + name + "'";
            return switch (c.getQuestStatus()) {
                case 2 -> String.format("Complete %s first.", name);
                case 1 -> String.format("Start %s first.", name);
                default -> String.format("This quest can't be taken once %s has been started.", name);
            };
        }
        if (r instanceof QuestStartJobRequirement) {
            return "Your job can't take this quest.";
        }
        if (r instanceof QuestStartItemRequirement it) {
            String name = StringData.getItemStringById(it.getId());
            return String.format("You need %d %s to %s this quest.", Math.max(1, it.getQuantity()),
                    name == null ? "item " + it.getId() : name, what);
        }
        if (r instanceof QuestEquipRequirement) {
            return String.format("You need to be wearing the item this quest asks for to %s it.", what);
        }
        if (r instanceof QuestRecordRequirement) {
            return String.format("You haven't done what this quest asks yet, so you can't %s it.", what);
        }
        return null;
    }

    /**
     * Returns whether or not a {@link Char} can start a given quest, checking all Quest.wz requirements when
     * {@link QuestConstants#STRICT_QUEST_REQUIREMENTS} is enabled. Used for quest requests from the client.
     *
     * @param questID The Quest's ID to check.
     * @return Whether or not the Char can start the quest.
     */
    public boolean canStartQuest(int questID) {
        return canStartQuest(questID, QuestConstants.STRICT_QUEST_REQUIREMENTS);
    }

    /**
     * Returns whether or not a quest can be started from a script. Uses the lenient checks, so scripts that start
     * quests as part of their flow keep working.
     *
     * @param questID The Quest's ID to check.
     * @return Whether or not the Char can start the quest.
     */
    public boolean canStartQuestFromScript(int questID) {
        return canStartQuest(questID, false);
    }

    /**
     * Returns whether or not a {@link Char} can start a given quest.
     *
     * @param questID The Quest's ID to check.
     * @param strict  whether or not to check all requirements of the official server
     * @return Whether or not the Char can start the quest.
     */
    /**
     * Like {@link #canStartQuest(int)}, but without telling the player anything (no "make room" messages) and without
     * checking inventory space: for checks the player didn't ask for, e.g. whether a hidden quest NPC is needed.
     */
    public boolean canStartQuestQuietly(int questID) {
        QuestInfo qi = QuestData.getQuestInfoById(questID);
        if (qi == null) {
            return false;
        }
        Quest quest = getQuestById(questID);
        if (quest != null && quest.getStatus() == Started) {
            return false;
        }
        if (quest != null && quest.getStatus() == Completed && !canRepeat(qi, quest)
                && !QuestConstants.REPEATABLE_QUESTS.contains(questID)) {
            return false;
        }
        if (qi.isExpiredEvent() || qi.isRetired()) {
            return false;
        }
        return findUnmetRequirement(qi.getQuestStartRequirements(), qi.isStartQuestOrOption(),
                qi.isStartItemOrOption(), QuestConstants.STRICT_QUEST_REQUIREMENTS) == null;
    }

    /**
     * Why {@link #canStartQuestQuietly(int)} says no, in words (for reports), or null when the
     * quest can be started.
     */
    public String whyCantStartQuietly(int questID) {
        QuestInfo qi = QuestData.getQuestInfoById(questID);
        if (qi == null) {
            return "the server doesn't have this quest";
        }
        Quest quest = getQuestById(questID);
        if (quest != null && quest.getStatus() == Started) {
            return "already in progress";
        }
        if (quest != null && quest.getStatus() == Completed && !canRepeat(qi, quest)
                && !QuestConstants.REPEATABLE_QUESTS.contains(questID)) {
            return "already completed";
        }
        if (qi.isExpiredEvent()) {
            return "an event that has ended";
        }
        if (qi.isUnreachable()) {
            return "retired (its NPC, map or monster is gone)";
        }
        if (qi.isRetired()) {
            return "retired (a newer questline replaces it)";
        }
        QuestStartRequirement unmet = findUnmetRequirement(qi.getQuestStartRequirements(), qi.isStartQuestOrOption(),
                qi.isStartItemOrOption(), QuestConstants.STRICT_QUEST_REQUIREMENTS);
        return unmet == null ? null : describeRequirement(unmet);
    }

    public boolean canStartQuest(int questID, boolean strict) {
        QuestInfo qi = QuestData.getQuestInfoById(questID);
        if (qi == null) {
            return true;
        }
        if (qi.isRetired()) {
            log.debug(String.format("Char %d tried to start retired quest %d.", chr.getId(), questID));
            return false;
        }
        if (strict) {
            Quest quest = getQuestById(questID);
            if (quest != null && quest.getStatus() == Started) {
                return false;
            }
            if (quest != null && quest.getStatus() == Completed && !canRepeat(qi, quest)
                    && !QuestConstants.REPEATABLE_QUESTS.contains(questID)) {
                log.debug(String.format("Char %d tried to start quest %d again, but it is not repeatable (yet).",
                        chr.getId(), questID));
                return false;
            }
            if (qi.isExpiredEvent()) {
                // an event quest whose end date has passed (scripts can still start it, e.g. a custom event)
                chr.chatMessage("This event has ended.");
                return false;
            }
        }

        QuestStartRequirement unmet = findUnmetRequirement(qi.getQuestStartRequirements(), qi.isStartQuestOrOption(),
                qi.isStartItemOrOption(), strict);
        if (unmet != null) {
            log.debug(String.format("Char %d does not meet the requirement (%s) to start quest %d.",
                    chr.getId(), describeRequirement(unmet), questID));
            return false;
        }
        if (!hasMesoForCosts(qi, 0)) {
            chr.chatMessage(String.format("You need %,d mesos to accept this quest.", -mesoChange(qi, 0)));
            return false;
        }

        List<QuestItemReward> unfit = findStartRewardsThatDontFit(qi);
        if (unfit != null) {
            chr.chatMessage(describeSpaceNeeded(unfit, "accept this quest"));
            return false;
        }
        return true;
    }

    /**
     * The random start reward (prop &gt; 0) is only rolled when the quest is accepted, so there must be room for it
     * whichever item is rolled.
     *
     * @return the start item rewards (with a random item) that don't fit in the Char's inventory, or null if all fit
     */
    private List<QuestItemReward> findStartRewardsThatDontFit(QuestInfo qi) {
        List<QuestItemReward> base = new ArrayList<>();
        for (QuestItemReward qir : resolveItemRewards(qi, 0, -1)) {
            if (!qir.isRandom()) {
                base.add(qir);
            }
        }
        List<QuestItemReward> random = new ArrayList<>();
        for (QuestReward qr : qi.getQuestRewards()) {
            if (qr instanceof QuestItemReward qir && qir.isRandom() && qir.getStatus() == 0 && qir.isEligible(chr)) {
                random.add(qir);
            }
        }
        if (random.isEmpty()) {
            return canHoldItemRewards(base) ? null : base;
        }
        for (QuestItemReward qir : random) {
            List<QuestItemReward> outcome = new ArrayList<>(base);
            outcome.add(qir);
            if (!canHoldItemRewards(outcome)) {
                return outcome;
            }
        }
        return null;
    }

    /**
     * Checks the completion requirements of Quest.wz/Check.img/[id]/1 that aren't progress requirements (quest
     * records, other quests' states, equipped items, level, ...).
     *
     * @param questID the quest's id
     * @return whether or not all of them are met
     */
    public boolean meetsCompleteRequirements(int questID) {
        QuestInfo qi = QuestData.getQuestInfoById(questID);
        if (qi == null) {
            return true;
        }
        QuestStartRequirement unmet = findUnmetRequirement(qi.getQuestCompleteRequirements(),
                qi.isCompleteQuestOrOption(), QuestConstants.STRICT_QUEST_REQUIREMENTS);
        if (unmet != null) {
            log.debug(String.format("Char %d does not meet the requirement (%s) to complete quest %d.",
                    chr.getId(), describeRequirement(unmet), questID));
            return false;
        }
        return true;
    }

    public Char getChr() {
        return chr;
    }

    /**
     * @return the total meso change of a quest's Act.img money for the given state (negative: the quest costs meso)
     */
    private static long mesoChange(QuestInfo qi, int status) {
        long total = 0;
        for (QuestReward qr : qi.getQuestRewards()) {
            if (qr instanceof QuestMoneyReward qmr && (qmr.getStatus() == 0) == (status == 0)) {
                total += qmr.getMoney();
            }
        }
        return total;
    }

    /**
     * @return whether the Char has the meso the quest takes for the given state (if it takes any)
     */
    private boolean hasMesoForCosts(QuestInfo qi, int status) {
        long change = mesoChange(qi, status);
        return change >= 0 || chr.getMoney() + change >= 0;
    }

    /**
     * Completes a quest. Assumes the check for in-progressness has already been done, so this method can be used
     * to complete quests that the Char does not actually have.
     *
     * @param questId The quest ID to finish.
     */
    public boolean completeQuest(int questId) {
        return completeQuest(questId, true);
    }

    /**
     * Completes a quest. Assumes the check for in-progressness has already been done, so this method can be used
     * to complete quests that the Char does not actually have.
     *
     * @param questId     The quest ID to finish.
     * @param giveRewards whether or not to reward the quest's rewards
     */
    public boolean completeQuest(int questId, boolean giveRewards) {
        return completeQuest(questId, giveRewards, -1);
    }

    /**
     * Completes a quest. Assumes the check for in-progressness has already been done, so this method can be used
     * to complete quests that the Char does not actually have.
     *
     * @param questId     The quest ID to finish.
     * @param giveRewards whether or not to reward the quest's rewards
     * @param selection   index of the selectable reward item the player chose, or -1
     */
    public boolean completeQuest(int questId, boolean giveRewards, int selection) {
        QuestInfo questInfo = QuestData.getQuestInfoById(questId);
        if (questInfo == null) {
            return false;
        }
        Quest existing = getQuestById(questId);
        if (existing != null && existing.getStatus() == QuestStatus.Completed) {
            // already done (repeatable quests are started again first): completing it again, e.g. from a script that
            // runs every time a map is entered, must not give the rewards again
            giveRewards = false;
        }

        if (giveRewards) {
            if (!hasMesoForCosts(questInfo, 1)) {
                // the meso the quest takes (Act.img negative money): addMoney would just skip it
                chr.chatMessage(String.format("You need %,d mesos to complete this quest.", -mesoChange(questInfo, 1)));
                return false;
            }
            List<QuestItemReward> itemRewards = resolveItemRewards(questInfo, 1, selection);
            if (!canHoldItemRewards(itemRewards)) {
                chr.chatMessage(describeSpaceNeeded(itemRewards, "complete this quest"));
                return false;
            }
            // every item the quest takes must be there before anything is given or taken (no partial hand-in)
            for (QuestItemReward qir : itemRewards) {
                if (qir.getQuantity() < 0 && !chr.hasItemCount(qir.getId(), -qir.getQuantity())) {
                    chr.chatMessage("You don't have the items needed to complete this quest.");
                    return false;
                }
            }

            for (QuestReward qr : questInfo.getQuestRewards()) {
                if (!(qr instanceof QuestItemReward) && qr.getStatus() != 0) {
                    qr.giveReward(chr);
                }
            }
            for (QuestItemReward qir : itemRewards) {
                qir.giveReward(chr);
            }
        }

        Quest quest = getQuestById(questId);
        if (quest == null) {
            quest = QuestData.createQuestFromId(questId);
            if (quest == null) {
                return false;
            }
            addQuest(quest);
        }

        quest.setStatus(QuestStatus.Completed);
        quest.setCompletedTime(FileTime.currentTime());
        chr.debugMessage("[Info] Completed quest " + quest.getQRKey());
        if (chr.getField() != null && !isQuiet()) {
            chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.questCompleteEffect()));
            chr.write(UserPacket.effect(Effect.questCompleteEffect()));
        }
        sendRecord(quest);
        QuestNpcVisibility.refresh(chr); // the NPC that starts the next quest may be hidden in this map

        if (QuestConstants.isSurpriseMissionQuest(questId)) {
            SurpriseMissionModule.completeSurpriseMission(chr);
        }

        if (questInfo.getTransferFields() != null) {
            if (questInfo.getTransferFields().size() > 0) {
                // transferField 0 means no move
                if (questInfo.getTransferFields().containsKey(1) && questInfo.getTransferFields().get(1) > 0) {
                    Field field = chr.getOrCreateFieldByCurrentInstanceType(questInfo.getTransferFields().get(1));
                    if (field != null) {
                        chr.warp(field);
                    }
                }
            }
        }

        checkAndAddCompletedAccountQuest(questId);
        // official job advancements that happen when a quest is handed in to an NPC (no script runs then)
        net.swordie.ms.client.character.ClassQuestSetupModule.onQuestCompleted(chr, questId);
        net.swordie.ms.client.character.achievement.MapleAchievements.onQuestState(chr, questId, 2);
        if (giveRewards) {
            net.swordie.ms.world.HolidayEvents.onQuestCompleted(chr, questId); // holiday event rewards
            net.swordie.ms.client.character.hexa.HexaEffects.onQuestCompleted(chr, questId); // Sol Erda energy
        }
        return true;
    }

    /**
     * Marks a quest completed without its rewards, effects, map transfer or NPC refresh (for skipping whole
     * questlines: the caller refreshes once at the end). Does nothing if the quest is already completed.
     *
     * @return whether the quest was completed now
     */
    public boolean completeQuestSilently(int questId) {
        Quest quest = getQuestById(questId);
        if (quest != null && quest.getStatus() == QuestStatus.Completed) {
            return false;
        }
        if (quest == null) {
            quest = QuestData.createQuestFromId(questId);
            if (quest == null) {
                return false;
            }
            addQuest(quest);
        }
        quest.setStatus(QuestStatus.Completed);
        quest.setCompletedTime(FileTime.currentTime());
        sendRecord(quest);
        checkAndAddCompletedAccountQuest(questId);
        return true;
    }

    /** Sends the quest record, or keeps it for the next CharacterData while completions are quiet. */
    public void sendRecord(Quest quest) {
        if (isQuiet()) {
            untold.add(quest.getQRKey());
        } else {
            chr.write(WvsContext.message(MessagePacket.questRecordMessage(quest)));
        }
    }

    /** @return whether quest records are being kept back (see {@link #beginQuiet()}) */
    public boolean isQuiet() {
        return quietDepth > 0 || heldForField;
    }

    /** Starts a bulk of completions whose records are kept back; pair with {@link #endQuiet()}. */
    public synchronized void beginQuiet() {
        quietDepth++;
    }

    public synchronized void endQuiet() {
        if (quietDepth > 0) {
            quietDepth--;
        }
    }

    /** Keeps records back until the next field change (a tutorial skip that warps afterwards). */
    public void holdForNextField() {
        heldForField = true;
    }

    public boolean isHeldForField() {
        return heldForField;
    }

    public void releaseHold() {
        heldForField = false;
    }

    /** @return how many quest records the client hasn't been sent */
    public int getUntoldCount() {
        return untold.size();
    }

    /**
     * Called by a field change before its SET_FIELD. Ends a hold.
     *
     * @return whether this SET_FIELD has to carry CharacterData for the records kept back
     */
    public boolean onFieldChange(boolean toInstance) {
        heldForField = false;
        if (untold.isEmpty() || quietDepth > 0) {
            return false;
        }
        if (toInstance) {
            sendUntold(); // no CharacterData into instances: one by one after all
            return false;
        }
        return true;
    }

    /** CharacterData went out: it has every record. */
    public void onCharacterDataSent() {
        untold.clear();
    }

    /** Sends the records kept back one by one (each completed one shows a notice). */
    public void sendUntold() {
        for (Integer id : new ArrayList<>(untold)) {
            untold.remove(id);
            Quest q = getQuestById(id);
            if (q != null) {
                chr.write(WvsContext.message(MessagePacket.questRecordMessage(q)));
            }
        }
    }

    public void checkAndAddCompletedAccountQuest(int questId) {
        var account = chr.getAccount();
        if (QuestConstants.ACCOUNT_WIDE_QUESTS.contains(questId)) {
            account.addCompletedAccountQuest(questId);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setChr(Char chr) {
        this.chr = chr;
    }

    /**
     * Removes a given quest from this QuestManager, and notifies the client of this change. Does nothing if the Char
     * does not currently have the quest.
     *
     * @param questID the id of the quest that should be removed
     */
    public void removeQuest(int questID) {
        Quest q = getQuestById(questID);
        if (q != null) {
            boolean wasInProgress = q.getStatus() == Started;
            removeQuest(q);
            QuestInfo qi = QuestData.getQuestInfoById(questID);
            if (wasInProgress && qi != null) {
                // items given on start that are flagged with resignRemove are taken back on forfeit
                for (QuestReward qr : qi.getQuestRewards()) {
                    if (qr instanceof QuestItemReward) {
                        QuestItemReward qir = (QuestItemReward) qr;
                        if (qir.getStatus() == 0 && qir.isResignRemove() && chr.hasItem(qir.getId())) {
                            chr.consumeItem(qir.getId(), Math.max(1, qir.getQuantity()));
                        }
                    }
                }
            }

            if (QuestConstants.isSurpriseMissionQuest(questID)) {
                SurpriseMissionModule.removeSurpriseMission(chr);
            }

            if (QuestConstants.M0_QR_QUESTS.contains(questID)) {
                List<QuestProgressMobRequirement> requirements = new ArrayList<>(q.getMobReqs());
                requirements.sort(Comparator.comparingInt(QuestProgressMobRequirement::getOrder));
                for (QuestProgressMobRequirement qpmr : requirements) {
                    qpmr.setCurrentCount(0);
                }
                chr.write(WvsContext.message(MessagePacket.questRecordExMessage(q)));
            }
        }
    }

    /**
     * Gives back items a quest handed out on start (Act.img/[id]/0/item) that the Char lost, as asked through the
     * "lost" dialogue of Quest.wz/Say.img. Only items that are also needed to complete the quest
     * (Check.img/[id]/1/item) are given back, and only when the Char has none of them left, so this can't be used to
     * farm items.
     *
     * @param questID      the quest
     * @param requestedIds the item ids the client listed (may be empty or contain other values)
     * @return the number of items given back
     */
    public int restoreLostItems(int questID, Collection<Integer> requestedIds) {
        if (!hasQuestInProgress(questID)) {
            return 0;
        }
        QuestInfo qi = QuestData.getQuestInfoById(questID);
        if (qi == null) {
            return 0;
        }
        Set<Integer> needed = new HashSet<>();
        for (QuestProgressRequirement qpr : qi.getQuestProgressRequirements()) {
            if (qpr instanceof QuestProgressItemRequirement) {
                needed.add(((QuestProgressItemRequirement) qpr).getItemID());
            }
        }
        boolean filter = requestedIds != null && requestedIds.stream().anyMatch(needed::contains);
        int restored = 0;
        for (QuestReward qr : qi.getQuestRewards()) {
            if (!(qr instanceof QuestItemReward)) {
                continue;
            }
            QuestItemReward qir = (QuestItemReward) qr;
            int itemID = qir.getId();
            if (qir.getStatus() != 0 || qir.getQuantity() <= 0 || !needed.contains(itemID)
                    || (filter && !requestedIds.contains(itemID)) || chr.hasItem(itemID)) {
                continue;
            }
            if (!chr.canHold(itemID, qir.getQuantity())) {
                chr.chatMessage(describeSpaceNeeded(List.of(qir), "get the item back"));
                continue;
            }
            Item item = ItemData.getItemDeepCopy(itemID);
            if (item == null) {
                continue;
            }
            item.setQuantity(qir.getQuantity());
            chr.addItemToInventory(item);
            restored++;
        }
        return restored;
    }

    public void removeQuest(Quest q) {
        q.setStatus(NotStarted);
        getQuests().remove(q.getQRKey());
        chr.write(WvsContext.message(MessagePacket.questRecordMessage(q)));
        questDao.delete(q);
    }

    /**
     * Adds a quest to this QuestManager with a given id. If there is no quest with that id, does nothing.
     *
     * @param id the quest's id to add
     */
    public void addQuest(int id) {
        Quest q = QuestData.createQuestFromId(id);
        if (q != null) {
            addQuest(q);
        }
    }

    public Quest getQuestById(int questId) {
        return getQuests().get(questId);
    }

    /**
     * Stores a value in a server-only quest record (a quest id that doesn't exist in Quest.wz), without sending
     * anything to the client. Saved with the character like any other quest record.
     */
    public void setHiddenRecord(int questID, String value) {
        Quest q = getQuestById(questID);
        if (q == null) {
            q = QuestData.createQuestFromId(questID);
            getQuests().put(questID, q);
        }
        q.setQrValue(value);
    }

    /**
     * @return the value of a server-only quest record, or null if it was never set
     */
    public String getHiddenRecord(int questID) {
        Quest q = getQuestById(questID);
        return q == null ? null : q.getQRValue();
    }

    public Quest getOrCreateQuestById(int questId) {
        Quest quest = getQuestById(questId);
        if (quest == null) {
            addQuest(questId);
        }
        quest = getQuestById(questId);
        return quest;
    }

    public void initUnion() {
        var unionQuest = QuestData.createQuestFromId(QuestConstants.UNION_QUEST);
        var unionRankQuest = QuestData.createQuestFromId(QuestConstants.UNION_RANK);
        var unionPresetQuest = QuestData.createQuestFromId(QuestConstants.UNION_PRESET);
        var currentDateStr = net.swordie.ms.util.ResetClock.todayYYMMDD();
        // key=value pairs are separated by ';' (they were run together, so the client couldn't read the daily
        // Legion quest record)
        unionQuest.setQrValue("q0=1;q1=0;pq=0;q2=0;q1Date=" + currentDateStr + ";pqDate=" +
                currentDateStr + ";q2Date=" + currentDateStr);
        int rank = chr.getUnion().getUnionRank();
        if (rank == 0) {
            rank = 101;
        }
        unionRankQuest.setProperty("rank", rank);
        unionPresetQuest.setProperty("presetNo", chr.getActiveUnionPreset());
        addQuest(unionQuest);
        addQuest(unionRankQuest);
        addQuest(unionPresetQuest);
        completeQuest(QuestConstants.UNION_START_QUEST);
        getChr().getUnionRaid().initialize();
    }

    public void addQuestWithQr(Char chr, int questId, String qrValue) {
        Quest q = getQuestById(questId);
        if (q == null) {
            q = QuestData.createQuestFromId(questId);
            addQuest(q);
        }
        q.setQrValue(qrValue);
        chr.write(WvsContext.message(MessagePacket.questRecordMessage(q)));
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(q)));
    }

    public List<Quest> getQuestsWithMobSetProgressRequirement() {
        return getQuestsInProgress().stream().filter(q -> Arrays.stream(MobSetType.values()).anyMatch(v -> q.hasMobReq(v.getVal()))).collect(Collectors.toList());
    }
}
