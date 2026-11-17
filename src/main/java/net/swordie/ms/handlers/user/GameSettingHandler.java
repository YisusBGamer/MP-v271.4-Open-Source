package net.swordie.ms.handlers.user;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.loaders.DataTable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Game settings window: the client sends changed options as key/value pairs and reads them back from quest-ex
 * records at login. Key table: resources/data/game-settings.tsv (the v271 client's key order).
 */
public class GameSettingHandler {

    private record Target(int questId, String key) {
    }

    private static final String GAME_TABLE = "game";
    private static final String QUICK_TABLE = "quick";

    // table ("game" for the Settings window, "quick" for the rest) -> key -> record and name
    private static final Supplier<Map<String, Map<Integer, Target>>> TABLES = DataTable.cached("game-settings", t -> {
        Map<String, Map<Integer, Target>> m = new HashMap<>();
        for (DataTable.Row r : t.rows()) {
            m.computeIfAbsent(r.str("table"), k -> new HashMap<>())
                    .put(r.i("key"), new Target(r.i("record"), r.str("name")));
        }
        return m;
    });

    @Handler(op = InHeader.USER_QUICK_SLOT_MODIFIED)
    public static void handleUserQuickSlotModified(Char chr, InPacket inPacket) {
        // int type, byte count, count x (int key, int value)
        int type = inPacket.decodeInt();
        if (inPacket.getUnreadAmount() < 1) {
            return; // type 3 comes alone
        }
        int count = inPacket.decodeByte() & 0xFF;
        Map<Integer, Target> table = TABLES.get().getOrDefault(type == 0 ? GAME_TABLE : QUICK_TABLE, Map.of());
        Map<Integer, Quest> changed = new HashMap<>();
        for (int i = 0; i < count && inPacket.getUnreadAmount() >= 8; i++) {
            int key = inPacket.decodeInt();
            int value = inPacket.decodeInt();
            Target target = table.get(key);
            if (target == null) {
                continue;
            }
            Quest q = changed.computeIfAbsent(target.questId(), id -> getRecord(chr, id));
            if (q != null) {
                q.setProperty(target.key(), value);
            }
        }
        for (Quest q : changed.values()) {
            if (q != null) {
                chr.write(WvsContext.message(MessagePacket.questRecordExMessage(q)));
            }
        }
    }

    private static Quest getRecord(Char chr, int questId) {
        var qm = chr.getQuestManager();
        boolean isNew = qm.getQuestById(questId) == null;
        Quest q = qm.getOrCreateQuestById(questId);
        if (q == null) {
            return null;
        }
        if (isNew) {
            if (questId == QuestConstants.OVERAL_SETTING) {
                q.setQrValue(QuestConstants.OVERAL_SETTING_DEFAULT);
            } else if (questId == QuestConstants.QUICK_SLOT_SETTING) {
                q.setQrValue(QuestConstants.QUICK_SLOT_SETTING_DEFAULT);
            }
        }
        // setProperty rebuilds the value from the property map, so load what's stored first
        q.convertQRValueToProperties();
        return q;
    }
}
