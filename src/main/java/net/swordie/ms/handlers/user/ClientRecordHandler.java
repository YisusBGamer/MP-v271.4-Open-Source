package net.swordie.ms.handlers.user;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.loaders.QuestData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Client requests GMS answers with a quest-ex record (MESSAGE type 13), as GMS v271.
 */
public class ClientRecordHandler {

    private static final DateTimeFormatter NOTICE_TIME = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /** Sets a whole quest-ex record and sends it, as GMS does. */
    public static void setQuestEx(Char chr, int questId, String value) {
        var qm = chr.getQuestManager();
        Quest quest = qm.getQuestById(questId);
        if (quest == null) {
            quest = QuestData.createQuestFromId(questId);
            qm.addQuest(quest);
        }
        quest.setQrValue(value);
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
    }

    // The client sends it when the quest it was told about in quest-ex 1099 ("qid=") is the one now shown;
    // GMS clears the record.
    @Handler(op = InHeader.QUEST_ALERT_SEEN)
    public static void handleQuestAlertSeen(Char chr, InPacket inPacket) {
        setQuestEx(chr, 1099, "");
    }

    @Handler(op = InHeader.QUEST_EX_7966_CLEAR)
    public static void handleQuestEx7966Clear(Char chr, InPacket inPacket) {
        setQuestEx(chr, 7966, "");
    }

    // Sent when the notice window closes; GMS stores the time in quest-ex 100603.
    @Handler(op = InHeader.NOTICE_SEEN)
    public static void handleNoticeSeen(Char chr, InPacket inPacket) {
        setQuestEx(chr, 100603, "noticeTime=" + LocalDateTime.now().format(NOTICE_TIME));
    }

    // GMS: 1 day 5.2m, 7 days 25m, 31 days 100m mesos
    private static final int[][] HYPER_ROCK_OPTIONS = {{1440, 5_200_000}, {10080, 25_000_000}, {44640, 100_000_000}};
    private static final DateTimeFormatter HYPER_ROCK_TIME = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    @Handler(op = InHeader.HYPER_ROCK_PRICE_REQUEST)
    public static void handleHyperRockPriceRequest(Char chr, InPacket inPacket) {
        chr.write(UserLocal.hyperRockPriceList(HYPER_ROCK_OPTIONS));
    }

    // GMS answers quest-ex 1953 "can=<now>;time=<new end>", the mesos, then the result
    @Handler(op = InHeader.HYPER_ROCK_BUY_REQUEST)
    public static void handleHyperRockBuyRequest(Char chr, InPacket inPacket) {
        int idx = inPacket.decodeInt();
        if (idx < 0 || idx >= HYPER_ROCK_OPTIONS.length || chr.getMoney() < HYPER_ROCK_OPTIONS[idx][1]) {
            chr.write(UserLocal.hyperRockBuyResult(false, 0));
            return;
        }
        int minutes = HYPER_ROCK_OPTIONS[idx][0];
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = now;
        Quest q = chr.getQuestManager().getQuestById(1953);
        if (q != null) {
            String t = q.getProperty("time");
            if (t != null && !t.isEmpty()) {
                try {
                    LocalDateTime cur = LocalDateTime.parse(t, HYPER_ROCK_TIME);
                    if (cur.isAfter(now)) {
                        end = cur;
                    }
                } catch (Exception ignored) {
                }
            }
        }
        end = end.plusMinutes(minutes);
        setQuestEx(chr, 1953, "can=" + now.format(HYPER_ROCK_TIME) + ";time=" + end.format(HYPER_ROCK_TIME));
        chr.deductMoney(HYPER_ROCK_OPTIONS[idx][1]);
        chr.write(UserLocal.hyperRockBuyResult(true, minutes));
    }

    @Handler(op = InHeader.SCENE_SKIP_REQUEST)
    public static void handleSceneSkip(Char chr, InPacket inPacket) {
        inPacket.decodeString();
        chr.getScriptManager().skipScene();
    }

    // GMS answers with the quest-ex record set to "RunAct=1" (tutorial and quest scene triggers)
    @Handler(op = InHeader.QUEST_EX_RUN_ACT)
    public static void handleQuestExRunAct(Char chr, InPacket inPacket) {
        int questId = inPacket.decodeInt();
        if (questId > 0) {
            setQuestEx(chr, questId, "RunAct=1");
            chr.write(net.swordie.ms.connection.packet.UserLocal.questExRunActResult(questId));
        }
    }

    // GMS: the boss-selection window remembers the highlighted row in quest-ex 68317
    @Handler(op = InHeader.CHANNEL_ENTER_FLAG_ACK)
    public static void handleChannelEnterFlagAck(Char chr, InPacket inPacket) {
    }

    // character info window background: GMS stores the id as quest 7295's record
    @Handler(op = InHeader.CHARACTER_INFO_BACKGROUND_SET)
    public static void handleCharacterInfoBackgroundSet(Char chr, InPacket inPacket) {
        int background = inPacket.decodeInt();
        if (background < 0) {
            return;
        }
        var qm = chr.getQuestManager();
        Quest quest = qm.getQuestById(7295);
        if (quest == null) {
            quest = QuestData.createQuestFromId(7295);
            qm.addQuest(quest);
        }
        quest.setStatus(net.swordie.ms.enums.QuestStatus.Started);
        quest.setQrValue(String.valueOf(background));
        chr.write(WvsContext.message(MessagePacket.questRecordMessage(quest)));
    }

    // the client's list of menu entries already opened this period; GMS answers only when the record changes
    @Handler(op = InHeader.OPENED_RECORD_UPDATE)
    public static void handleOpenedRecordUpdate(Char chr, InPacket inPacket) {
        if (inPacket.decodeInt() != 1) {
            return;
        }
        String resetTime = inPacket.decodeString();
        String opened = inPacket.decodeString();
        String value = "resetTime=" + resetTime + ("-1".equals(opened) ? "" : ";opened=" + opened);
        Quest quest = chr.getQuestManager().getQuestById(102810);
        if (quest == null || !value.equals(quest.getQRValue())) {
            setQuestEx(chr, 102810, value);
        }
    }

    @Handler(op = InHeader.BOSS_UI_CURSOR_MOVE)
    public static void handleBossUiCursorMove(Char chr, InPacket inPacket) {
        inPacket.decodeInt(); // boss UI npc
        int row = inPacket.decodeInt();
        setQuestEx(chr, 68317, "cursor=" + row);
    }

    // Screen Crack timers run server-side (MobSkillModule), so the client's timer-up notices need no answer
    // the client reports a Zero stat on field entry and when it changes; GMS doesn't answer
    @Handler(op = InHeader.ZERO_STAT_REPORT)
    public static void handleZeroStatReport(Char chr, InPacket inPacket) {
    }

    @Handler(op = InHeader.ARKARIUM_SCREEN_CRACK_ACK)
    public static void handleArkariumScreenCrackAck(Char chr, InPacket inPacket) {
    }
}
