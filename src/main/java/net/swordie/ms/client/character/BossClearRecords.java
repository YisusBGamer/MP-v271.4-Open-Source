package net.swordie.ms.client.character;

import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.loaders.EtcData;
import net.swordie.ms.util.ResetClock;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Supplier;

/**
 * Boss clear check marks.
 * <p>
 * The boss window ("Move to Boss", BossParty.img) looks each difficulty's recordInfo/clear mobs up in the client's
 * mobId -> record id table (UserLocal.bossClearRecordTable, sent on entering the game) and paints the check when that
 * record holds "count=N;mobid=..;mobDead=1;lasttime=yyyy/MM/dd HH:mm:ss" inside the difficulty's daily or weekly
 * window. The Contents Guide's boss tab reads "mobId=1" from the ContentsGuide.img/boss qrexId record.
 * <p>
 * The client only reads records it got with the character data (quest ex and world share), so the boss records go out
 * at login even while empty.
 * <p>
 * Based on a fix contributed by Twoal (from the Discord), who found the record table packet the check marks need.
 */
public final class BossClearRecords {

    private static final DateTimeFormatter LAST_TIME = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static final String COUNT_KEY = "count";
    private static final String GUIDE_CLEARED = "1";
    /** Ids for clear mobs the client's table doesn't list; they only have to be unique (the client matches by mob). */
    private static final int OWN_RECORD_BASE = 90_000_000;

    /** resources/data/boss-clear-records.tsv: the client's record id per clear mob. */
    private static final Supplier<Map<Integer, Integer>> KNOWN_IDS = DataTable.cached("boss-clear-records", t -> {
        Map<Integer, Integer> m = new HashMap<>();
        for (DataTable.Row r : t.rows()) {
            m.put(r.i("mob"), r.i("record"));
        }
        return Map.copyOf(m);
    });

    private BossClearRecords() {
    }

    /** @return mobId -> record id for every boss window clear mob, in mob order */
    public static Map<Integer, Integer> recordIds() {
        Map<Integer, Integer> ids = new TreeMap<>();
        for (int mobId : BossUiAccess.getClearMobs()) {
            ids.put(mobId, recordId(mobId));
        }
        return ids;
    }

    private static int recordId(int mobId) {
        return KNOWN_IDS.get().getOrDefault(mobId, OWN_RECORD_BASE + mobId);
    }

    /** @return the boss window records this character has no record of yet (sent empty with the character data) */
    public static List<Integer> getMissingRecords(Char chr) {
        QuestManager qm = chr.getQuestManager();
        List<Integer> missing = new ArrayList<>();
        for (int recordId : new LinkedHashSet<>(recordIds().values())) {
            if (qm.getQuestById(recordId) == null) {
                missing.add(recordId);
            }
        }
        return missing;
    }

    /** @return the boss window and Contents Guide records with a clear, for the character data's world-share list */
    public static List<Quest> getWorldShareRecords(Char chr) {
        QuestManager qm = chr.getQuestManager();
        List<Quest> records = new ArrayList<>();
        if (qm == null) {
            return records;
        }
        Set<Integer> ids = new LinkedHashSet<>(recordIds().values());
        ids.addAll(EtcData.getContentsGuideBossRecords().values());
        for (int id : ids) {
            Quest quest = qm.getQuestById(id);
            if (quest != null && !quest.getQRValue().isEmpty()) {
                records.add(quest);
            }
        }
        return records;
    }

    /** Writes the boss window and Contents Guide clears of this mob for everyone the clear counts for. */
    public static void onMobKilled(Mob mob) {
        int mobId = mob.getTemplateId();
        Integer recordId = BossUiAccess.getClearMobs().contains(mobId) ? recordId(mobId) : null;
        Integer guideId = EtcData.getContentsGuideBossRecords().get(mobId);
        if (recordId == null && guideId == null) {
            return;
        }
        for (Char chr : SwordieCoinRewards.creditedInField(mob)) {
            QuestManager qm = chr.getQuestManager();
            if (recordId != null) {
                Quest quest = qm.getOrCreateQuestById(recordId);
                int count = quest.getQRValue().isEmpty() ? 0 : quest.getIntProperty(COUNT_KEY);
                quest.setQrValue(COUNT_KEY + "=" + (count + 1) + ";mobid=" + mobId + ";mobDead=1;lasttime="
                        + ResetClock.now().format(LAST_TIME));
                send(chr, quest);
            }
            if (guideId != null) {
                Quest quest = qm.getOrCreateQuestById(guideId);
                quest.convertQRValueToProperties();
                quest.setProperty(String.valueOf(mobId), GUIDE_CLEARED);
                send(chr, quest);
            }
        }
    }

    private static void send(Char chr, Quest quest) {
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
        chr.write(WvsContext.message(MessagePacket.worldShareRecordMessage(quest)));
    }
}
