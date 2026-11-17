package net.swordie.ms.client.character;

import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.DataTable;

import java.util.List;
import java.util.function.Supplier;

/**
 * Story quests that ask for a boss defeat through a quest record instead of a kill count
 * (resources/data/story-boss-records.tsv): the boss's death writes the record for everyone it credits who has the
 * quest in progress, so the quest can be handed in.
 */
public final class StoryBossRecords {

    private record Row(int mob, int quest, int record, String key, String value) {
    }

    private static final Supplier<List<Row>> ROWS = DataTable.cached("story-boss-records", t -> t.rows().stream()
            .map(r -> new Row(r.i("mob"), r.i("quest"), r.i("record"), r.str("key").trim(), r.str("value").trim()))
            .toList());

    private StoryBossRecords() {
    }

    public static void onMobKilled(Mob mob) {
        int mobId = mob.getTemplateId();
        for (Row row : ROWS.get()) {
            if (row.mob() != mobId) {
                continue;
            }
            for (Char chr : SwordieCoinRewards.creditedInField(mob)) {
                QuestManager qm = chr.getQuestManager();
                if (qm == null || !qm.hasQuestInProgress(row.quest())) {
                    continue;
                }
                Quest record = qm.getOrCreateQuestById(row.record());
                if (record == null) {
                    continue;
                }
                record.convertQRValueToProperties();
                record.setProperty(row.key(), row.value());
                chr.write(WvsContext.message(MessagePacket.questRecordExMessage(record)));
            }
        }
    }
}
