package net.swordie.ms.client.character.quest;

import java.util.function.Supplier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.world.field.Field;

/** Visit and attack observations for the four Star Force introductions in v232 Quest.wz. */
public final class StarForceQuestModule {
    private StarForceQuestModule() { }

    private static final Supplier<Map<Integer, Integer>> FIELD_QUEST = DataTable.cached("star-force-intro-fields", t -> {
        Map<Integer, Integer> m = new HashMap<>();
        t.rows().forEach(r -> Arrays.stream(r.ints("fields")).forEach(f -> m.put(f, r.i("quest"))));
        return m;
    });

    private static int questForField(int fieldId) {
        return FIELD_QUEST.get().getOrDefault(fieldId, 0);
    }

    public static void onEnter(Char chr, Field field) {
        if (chr == null || field == null) {
            return;
        }
        int questId = questForField(field.getId());
        if (questId == 0 || !chr.getQuestManager().hasQuestInProgress(questId)) {
            return;
        }
        Quest quest = chr.getQuestManager().getQuestById(questId);
        if (!"1".equals(quest.getQRValue())) {
            quest.setQrValue("1");
            chr.write(WvsContext.message(MessagePacket.questRecordMessage(quest)));
        }
    }

    /** Called only for a validated attack on a living monster, not merely entering its map. */
    public static void onAttack(Char chr) {
        int questId = questForField(chr.getFieldID());
        if (questId == 0 || !chr.getQuestManager().hasQuestInProgress(questId)) {
            return;
        }
        onEnter(chr, chr.getField()); // also handles accepting the quest while already in the field
        setAttackFlag(chr, chr.getQuestManager().getQuestById(questId));
        setAttackFlag(chr, chr.getQuestManager().getOrCreateQuestById(25973));
        // "Star Power" (25972) offers itself on the first fight under Star Force and only explains it; these quests'
        // turn-in needs it done, and nothing else completes it
        if (!chr.getQuestManager().hasQuestCompleted(25972)) {
            chr.getScriptManager().completeQuestNoRewards(25972);
        }
    }

    private static void setAttackFlag(Char chr, Quest quest) {
        if (!"1".equals(quest.getPropertyEx("1"))) {
            // The visit uses the WHOLE normal QR. Keep its independent attack flag in QREx.
            quest.setPropertyEx("1", "1");
            chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
        }
    }
}
