package net.swordie.ms.handlers.script;

import java.util.Map;

import java.util.ArrayList;
import java.util.List;
import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.ProgressionLog;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.util.Util;
import net.swordie.ms.constants.QuestCleanup;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.enums.MedalReissueResultType;
import net.swordie.ms.enums.QuestType;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.QuestData;
import net.swordie.ms.loaders.containerclasses.QuestInfo;
import net.swordie.ms.scripts.ScriptManagerImpl;
import net.swordie.ms.scripts.ScriptType;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QuestHandler {

    private static final Logger log = LogManager.getLogger();

    /** Quest.wz 7707: normalAutoStart + dayByDay at the Maple Administrator; the client asks to open it until it's done for the day. */
    private static final int MIDNIGHT_REST_QUEST = 7707;

    private static final boolean HIDDEN_MARKER_ACCEPT =
            net.swordie.ms.ServerSettings.getBoolean("test.acceptHiddenMarkers", true);

    private static void markDoneToday(Char chr, int questId) {
        QuestManager qm = chr.getQuestManager();
        Quest quest = qm.getQuestById(questId);
        if (quest == null || quest.getStatus() != net.swordie.ms.enums.QuestStatus.Completed) {
            qm.completeQuestSilently(questId);
            return;
        }
        var done = quest.getCompletedTime();
        if (done == null || !done.toClientDateTime().toLocalDate().equals(net.swordie.ms.util.FileTime.clientNow().toLocalDate())) {
            quest.setCompletedTime(net.swordie.ms.util.FileTime.currentTime());
            chr.write(net.swordie.ms.connection.packet.WvsContext.message(
                    net.swordie.ms.connection.packet.model.MessagePacket.questRecordMessage(quest)));
        }
    }


    /**
     * Runs a quest's start or end script (Quest.wz startscript / endscript, else &lt;id&gt;s / &lt;id&gt;e), as the client's
     * OpeningScript / CompleteScript requests do; the caller checks the quest can be started or completed.
     */
    public static void openQuestScript(Char chr, QuestInfo qi, boolean start, int npcTemplateID) {
        int questID = qi.getQuestID();
        String scriptName = start ? qi.getStartScript() : qi.getEndScript();
        if (scriptName == null || scriptName.isEmpty()) {
            scriptName = questID + (start ? ScriptManagerImpl.QUEST_START_SCRIPT_END_TAG
                    : ScriptManagerImpl.QUEST_COMPLETE_SCRIPT_END_TAG);
        }
        // Quest.wz script names don't always end in s/e/x, so tell the script what it's for
        chr.getScriptManager().startScript(questID, scriptName, ScriptType.Quest,
                Map.<String, Object>of("startQuest", start, "resignQuest", false, "questNpc", npcTemplateID));
    }

    @Handler(op = InHeader.USER_QUEST_REQUEST)
    public static void handleUserQuestRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        QuestManager qm = chr.getQuestManager();
        byte type = inPacket.decodeByte();
        int questID = 0;
        int npcTemplateID = 0;
        Position position = null;
        int selection = -1;
        List<Integer> lostItemIds = new ArrayList<>();
        int nextQuestID = 0;
        boolean startNavigation = false;
        QuestType qt = QuestType.getQTFromByte(type);
        boolean success = false;
        if (qt != null) {
            switch (qt) {
                case QuestReq_AcceptQuest: // Quest start
                case QuestReq_CompleteQuest: // Quest end
                case QuestReq_OpeningScript: // Scripted quest start
                case QuestReq_CompleteScript: // Scripted quest end
                    questID = inPacket.decodeInt();
                    npcTemplateID = inPacket.decodeInt();
                    if (inPacket.getUnreadAmount() > 4) {
                        position = inPacket.decodePosition();
                    }
                    if (qt == QuestType.QuestReq_CompleteQuest && inPacket.getUnreadAmount() >= 4) {
                        // index of the reward the player picked for quests with selectable rewards (prop -1)
                        selection = inPacket.decodeInt();
                    }
                    break;
                case QuestReq_ResignQuest: //Quest forfeit
                    questID = inPacket.decodeInt();
                    break;
                case QuestReq_LostItem: // the player lost an item the quest gave them and asks the NPC for it again
                    questID = inPacket.decodeInt();
                    // the rest of the request lists the lost item(s); read whatever ints follow
                    while (inPacket.getUnreadAmount() >= 4 && lostItemIds.size() < 32) {
                        lostItemIds.add(inPacket.decodeInt());
                    }
                    break;
                case QuestReq_LaterStep:
                case QuestReq_AfterActSuccess:
                    questID = inPacket.decodeInt();
                    break;
                default:
                    log.error(String.format("Unhandled quest request %s!", qt));
                    break;
            }
        }
        if (questID == 0 || qt == null) {
            chr.chatMessage(String.format("Could not find quest %d.", questID));
            return;
        }
        QuestInfo qi = QuestData.getQuestInfoById(questID);

        if (qi == null) {
            return;
        }
        if ((qt == QuestType.QuestReq_AcceptQuest || qt == QuestType.QuestReq_OpeningScript)
                && QuestCleanup.isRemoved(questID)) {
            // leftover marker / foreign-only quest that only clutters the quest list (see QuestCleanup)
            chr.dispose();
            return;
        }

        switch (qt) {
            case QuestReq_AcceptQuest:
                if (QuestConstants.IGNORED_ACCEPT_QUESTS.contains(questID)) {
                    // the reference server neither starts nor answers these, so the client keeps asking; with
                    // test.acceptHiddenMarkers a hidden auto-accept marker is started quietly (MESSAGE:1 only)
                    if (HIDDEN_MARKER_ACCEPT && qi.isAutoStart() && qi.isHiddenInQuestLog()
                            && qm.canStartQuestQuietly(questID)) {
                        qm.addQuest(QuestData.createQuestFromId(questID));
                    }
                    return;
                }
                if (!qm.canStartQuest(questID)) {
                    // only when the player is talking to the quest's NPC: the client also asks on its own, over and
                    // over, for quests it shows as available (no NPC then), and GMS refuses those silently
                    if (!qi.isAutoStart() && !qi.isNormalAutoStart() && npcTemplateID != 0) {
                        tellWhy(chr, qm.playerReasonCantStart(questID));
                    }
                } else {
                    qm.addQuest(QuestData.createQuestFromId(questID));
                    startNavigation = qi.isStartNavi();
                    success = true;
                    // story scenes that start with a quest that has no start script (scripts/quest/start_<id>.py)
                    net.swordie.ms.client.character.ClassQuestSetupModule.onQuestAccepted(chr, questID);
                }
                break;
            case QuestReq_CompleteQuest:
                if (qm.hasQuestInProgress(questID)) {
                    Quest quest = qm.getQuestById(questID);
                    if (quest.isComplete(chr) && qm.meetsCompleteRequirements(questID)) {
                        if (qm.completeQuest(questID, true, selection)) {
                            // Act.img nextQuest: the client continues with the next quest of the chain
                            nextQuestID = qi.getNextQuest();
                            success = true;
                        }
                    } else if (npcTemplateID != 0) {
                        tellWhy(chr, qm.playerReasonCantComplete(questID));
                    }
                }
                break;
            case QuestReq_OpeningScript:
                String scriptName = qi.getStartScript();
                if (QuestConstants.isSpamQuest(scriptName)) {
                    if (questID == MIDNIGHT_REST_QUEST) {
                        markDoneToday(chr, questID);
                    }
                    return;
                }
                if (!qm.canStartQuest(questID)) {
                    // the client asks this on its own (auto-start quests): no message, as in GMS
                    if (npcTemplateID != 0 && !qi.isAutoStart() && !qi.isNormalAutoStart()) {
                        playStopLines(chr, questID, 0, npcTemplateID);
                    }
                    return;
                }
                openQuestScript(chr, qi, true, npcTemplateID);
                break;
            case QuestReq_CompleteScript:
                if (!qm.hasQuestInProgress(questID) || !qm.getQuestById(questID).isComplete(chr)
                        || !qm.meetsCompleteRequirements(questID)) {
                    log.debug("Could not complete quest, as the prerequisites haven't been met.");
                    if (qm.hasQuestInProgress(questID) && npcTemplateID != 0
                            && !playStopLines(chr, questID, 1, npcTemplateID)) {
                        tellWhy(chr, qm.playerReasonCantComplete(questID));
                    }
                    return;
                }
                openQuestScript(chr, qi, false, npcTemplateID);
                break;
            case QuestReq_LaterStep:
                if (qi.getTransferFields() != null && qi.getTransferFields().size() > 0) {
                    int transferField = 0;
                    if (chr.hasQuestInProgress(qi.getQuestID()) && qi.getTransferFields().containsKey(0)) {
                        transferField = qi.getTransferFields().get(0);
                    } else if (chr.getQuestManager().hasQuestCompleted(qi.getQuestID()) && qi.getTransferFields().containsKey(1)) {
                        transferField = qi.getTransferFields().get(1);
                    }
                    if (transferField != 0 && transferField != chr.getFieldID()) {
                        Field field = chr.getOrCreateFieldByCurrentInstanceType(transferField);
                        chr.warp(field);
                    }
                }
                break;
            case QuestReq_ResignQuest:
                scriptName = qi.getResignationScript();
                if (!qm.hasQuestInProgress(questID)) {
                    // ~_~
                    return;
                }
                // Remove the quest first: the resign script runs asynchronously, and must not race with (or undo)
                // the removal. Only quests with a resignScript in Quest.wz have something to run.
                chr.getQuestManager().removeQuest(questID);
                if (scriptName != null && !scriptName.isEmpty()) {
                    chr.getScriptManager().startScript(questID, scriptName, ScriptType.Quest,
                            Map.<String, Object>of("startQuest", false, "resignQuest", true));
                }
                break;
            case QuestReq_LostItem:
                qm.restoreLostItems(questID, lostItemIds);
                break;
        }
        if (success) {
            chr.write(UserLocal.questResult(QuestType.QuestRes_Act_Success, questID, npcTemplateID, nextQuestID,
                    startNavigation));
        }
    }

    @Handler(op = InHeader.USER_MEDAL_REISSUE_REQUEST)
    public static void handleUserMedalReissueRequest(Char chr, InPacket inPacket) {
        int questId = inPacket.decodeInt();
        int medalItemId = inPacket.decodeInt();
        ScriptManagerImpl sm = chr.getScriptManager();
        long actualMesoCost;
        int count = 0;
        if (sm.getQRValue(QuestConstants.MEDAL_REISSUE_QUEST).contains("count=")) {
            String countString = sm.getQRValue(QuestConstants.MEDAL_REISSUE_QUEST).replace("count=", "");
            count = Util.isNumber(countString) ? Integer.parseInt(countString) : 0;
        } else {
            sm.createQuestWithQRValue(QuestConstants.MEDAL_REISSUE_QUEST, "");
        }
        switch (count) {
            case 0:
                actualMesoCost = 100;
                break;
            case 1:
                actualMesoCost = 1000;
                break;
            case 2:
                actualMesoCost = 10000;
                break;
            case 3:
                actualMesoCost = 100000;
                break;
            default:
                actualMesoCost = 1000000; // hardcode-ok
                break;
        }
        if (QuestData.getQuestInfoById(questId) == null) {
            chr.write(UserLocal.medalReissueResult(MedalReissueResultType.Unknown, medalItemId));

        } else if (!ItemConstants.isMedal(medalItemId) || QuestData.getQuestInfoById(questId).getMedalItemId() != medalItemId) {
            chr.getOffenseManager().addOffense(String.format("Character %d tried to reissue an item {%d} that isn't a medal or tried to reissue a medal from a quest {%d} that doesn't give the given medal", chr.getId(), medalItemId, questId));

        } else if (!sm.hasQuestCompleted(questId)) {
            chr.getOffenseManager().addOffense(String.format("Character %d tried to reissue a medal from a quest {%d} which they have not completed.", chr.getId(), questId));

        } else if (ItemData.getItemDeepCopy(medalItemId) == null || QuestData.getQuestInfoById(questId) == null) {
            chr.write(UserLocal.medalReissueResult(MedalReissueResultType.Unknown, medalItemId));

        } else if (chr.getMoney() < actualMesoCost) {
            chr.write(UserLocal.medalReissueResult(MedalReissueResultType.NoMoney, medalItemId));

        } else if (!chr.canHoldItem(medalItemId, 1)) {
            chr.write(UserLocal.medalReissueResult(MedalReissueResultType.NoSlot, medalItemId));

        } else if (chr.hasItem(medalItemId)) {
            chr.write(UserLocal.medalReissueResult(MedalReissueResultType.AlreadyHas, medalItemId));

        } else {
            count++;
            sm.setQRValue(QuestConstants.MEDAL_REISSUE_QUEST, "count=" + count);
            chr.deductMoney(actualMesoCost, ProgressionLog.Sink.FEES);
            chr.addItemToInventory(QuestData.getQuestInfoById(questId).getMedalItemId(), 1);
            chr.write(UserLocal.medalReissueResult(MedalReissueResultType.Success, medalItemId));
        }
        chr.dispose();
    }

    /**
     * Has the NPC say the quest's official stop line (Quest.wz/Say.img stop/[reason]) for a scripted quest it can't
     * start or hand in yet; the client shows these itself only for quests without a script.
     *
     * @return whether the quest has such a line (the dialogue was started)
     */
    private static boolean playStopLines(Char chr, int questID, int part, int npcTemplateID) {
        ScriptManagerImpl sm = chr.getScriptManager();
        String reason = chr.getQuestManager().stopReason(questID, part, npcTemplateID);
        if (sm.questStopLines(questID, part, npcTemplateID, reason).isEmpty()) {
            return false;
        }
        sm.startScript(questID, "questStop", ScriptType.Quest, Map.<String, Object>of("startQuest", part == 0,
                "resignQuest", false, "questNpc", npcTemplateID, "stopPart", part,
                "stopReason", reason == null ? "default" : reason));
        return true;
    }

    /** character id + reason -> when it was last said (each reason at most once a minute, even when they alternate). */
    private static final Map<String, Long> lastRefusalAt = new java.util.concurrent.ConcurrentHashMap<>();

    /**
     * Tells the player why a quest request was refused, when there's something to tell; the same reason again within
     * a minute (clicks repeated on the NPC, the client asking again) isn't repeated.
     */
    private static void tellWhy(Char chr, String why) {
        if (why == null) {
            return;
        }
        long now = System.currentTimeMillis();
        String key = chr.getId() + ":" + why;
        Long at = lastRefusalAt.get(key);
        if (at != null && now - at < 60_000) {
            return;
        }
        if (lastRefusalAt.size() > 10_000) {
            lastRefusalAt.clear();
        }
        lastRefusalAt.put(key, now);
        chr.chatMessage(why);
    }
}
