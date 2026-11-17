package net.swordie.ms.client.character.quest;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.enums.QuestStatus;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.life.npc.Npc;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.loaders.QuestData;
import net.swordie.ms.loaders.containerclasses.QuestInfo;
import net.swordie.ms.world.field.Field;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Shows hidden story NPCs when the player's quests need them.
 * <p>
 * Many quest NPCs are hidden in Npc.wz (info/hide) and only appear when a quest record key says so: e.g. Rasol in
 * Subway Tunnel 4 (3004609) needs key "09" = "h1" on record 37600 (conditionN in Npc.wz). On live servers the quest
 * scripts set those keys. When a player enters a map, or starts or finishes a quest, every such NPC in the map that
 * ends one of the player's quests in progress, or starts a quest the player can take now, gets its "show" key set.
 * A key is only changed while the NPC is hidden (unset or a hiding value), so a pose or state a script chose for a
 * visible NPC is kept. NPCs that appear by quest state are handled by the client itself.
 * <p>
 * Records that are real quests (e.g. Purrfect Rangers, whose NPCs key on their own quest) are left out: creating
 * them would start the quest.
 * <p>
 * The table (resources/data/quest-npc-visibility.tsv) was generated from Npc.wz and Quest.wz (v232, then v265:
 * start quests redone without the retired ones, and the hidden NPCs of the v265 questlines added): Arcane River,
 * Cernium, Hotel Arcus, Odium, Partem, Sellas, Elodin, Mushroom Shrine, Korean Folk Town, Detective Rave, Purrfect
 * Rangers, ...
 */
public class QuestNpcVisibility {

    /** a record key that shows the NPC: show = the value that shows it, hide = the values that hide it (a,b) */
    private record KeyShow(int record, String key, String show, String hide) {
    }

    /** the whole value of a record that isn't a quest (Mushroom Shrine, Korean Folk Town, Afterlands, ...) */
    private record ValueShow(int record, String show, String hide) {
    }

    /**
     * Hidden quest NPC template id -> the quests (not auto-start) it hands out, its key or value record, and its own
     * quest whose record key "hide" = "off" shows it (Purrfect Rangers).
     */
    private record Tables(Map<Integer, int[]> startQuests, Map<Integer, KeyShow> keyShow,
                          Map<Integer, ValueShow> valueShow, Map<Integer, Integer> ownQuestShow) {
    }

    /** resources/data/quest-npc-visibility.tsv: kind (start/key/value/own), npc, record, key, show, hide, quests */
    private static final Supplier<Tables> TABLES = DataTable.cached("quest-npc-visibility", t -> {
        Map<Integer, int[]> start = new HashMap<>();
        Map<Integer, KeyShow> key = new HashMap<>();
        Map<Integer, ValueShow> value = new HashMap<>();
        Map<Integer, Integer> own = new HashMap<>();
        for (DataTable.Row r : t.rows()) {
            int npc = r.i("npc");
            switch (r.str("kind").trim()) {
                case "start" -> start.put(npc, r.ints("quests"));
                case "key" -> key.put(npc, new KeyShow(r.i("record"), r.str("key"), r.str("show"), r.str("hide")));
                case "value" -> value.put(npc, new ValueShow(r.i("record"), r.str("show"), r.str("hide")));
                case "own" -> own.put(npc, r.i("quests"));
                default -> throw new IllegalArgumentException("quest-npc-visibility.tsv line " + r.line()
                        + ": unknown kind " + r.str("kind"));
            }
        }
        return new Tables(Map.copyOf(start), Map.copyOf(key), Map.copyOf(value), Map.copyOf(own));
    });

    /**
     * Shows the hidden NPCs in the player's map that their quests need right now.
     */
    public static void refresh(Char chr) {
        Field field = chr == null ? null : chr.getField();
        if (field == null) {
            return;
        }
        QuestManager qm = chr.getQuestManager();
        if (qm == null) {
            return;
        }
        Tables tables = TABLES.get();
        Set<Integer> endNpcs = null;
        for (Npc npc : field.getNpcs()) {
            int templateId = npc.getTemplateId();
            KeyShow flag = tables.keyShow().get(templateId);
            ValueShow valueFlag = tables.valueShow().get(templateId);
            Integer ownQuest = tables.ownQuestShow().get(templateId);
            if (flag == null && valueFlag == null && ownQuest == null) {
                continue;
            }
            if (endNpcs == null) {
                endNpcs = new HashSet<>();
                for (Quest quest : qm.getQuestsInProgress()) {
                    QuestInfo qi = QuestData.getQuestInfoById(quest.getQRKey());
                    if (qi != null && qi.getEndNpc() != 0) {
                        endNpcs.add(qi.getEndNpc());
                    }
                }
            }
            if (!endNpcs.contains(templateId) && !canStartAQuest(qm, templateId)) {
                continue;
            }
            if (flag != null) {
                setShowFlag(chr, qm, flag.record(), flag.key(), flag.show(), flag.hide());
            }
            if (valueFlag != null) {
                setShowValue(chr, qm, valueFlag.record(), valueFlag.show(), valueFlag.hide());
            }
            if (ownQuest != null) {
                showWithOwnQuest(chr, qm, ownQuest);
            }
        }
    }

    /**
     * Sets the quest record key that makes the client show a hidden NPC (what the live scripts do), unless the NPC
     * is already shown in some state.
     */
    private static void setShowFlag(Char chr, QuestManager qm, int recordId, String key, String value,
                                    String hideValues) {
        Quest record = qm.getQuestById(recordId);
        String current = record == null ? null : record.getProperty(key);
        if (current != null && !current.isEmpty() && !("," + hideValues + ",").contains("," + current + ",")) {
            return; // already visible (this or another visible state)
        }
        if (record == null) {
            record = QuestData.createQuestFromId(recordId);
            qm.addCustomQuest(record);
        }
        record.setProperty(key, value);
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(record)));
    }

    /**
     * Sets the whole value of a (non-quest) record that makes the client show a hidden NPC, unless it's already shown.
     */
    private static void setShowValue(Char chr, QuestManager qm, int recordId, String value, String hideValues) {
        Quest record = qm.getQuestById(recordId);
        String current = record == null ? null : record.getCustomQrValue();
        if (current != null && !current.isEmpty() && !("," + hideValues + ",").contains("," + current + ",")) {
            return;
        }
        if (record == null) {
            record = QuestData.createQuestFromId(recordId);
            qm.addCustomQuest(record);
        }
        record.setQrValue(value);
        chr.write(WvsContext.message(MessagePacket.questRecordMessage(record)));
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(record)));
    }

    /**
     * Purrfect Rangers NPCs show when their own quest's record has hide=off. Never starts the quest: in progress, the
     * key is saved on the quest; otherwise it's only sent to the client.
     */
    private static void showWithOwnQuest(Char chr, QuestManager qm, int questId) {
        Quest quest = qm.getQuestById(questId);
        if (quest != null && quest.getStatus() == QuestStatus.Started) {
            if (!"off".equals(quest.getProperty("hide"))) {
                quest.setProperty("hide", "off");
                chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
            }
        } else if (quest == null) {
            Quest view = new Quest();
            view.setQRKey(questId);
            view.setQrValue("hide=off");
            chr.write(WvsContext.message(MessagePacket.questRecordExMessage(view)));
        }
    }

    private static boolean canStartAQuest(QuestManager qm, int npcTemplateId) {
        int[] quests = TABLES.get().startQuests().get(npcTemplateId);
        if (quests == null) {
            return false;
        }
        for (int questId : quests) {
            if (!qm.hasQuestInProgress(questId) && !qm.hasQuestCompleted(questId) && qm.canStartQuestQuietly(questId)) {
                return true;
            }
        }
        return false;
    }
}
