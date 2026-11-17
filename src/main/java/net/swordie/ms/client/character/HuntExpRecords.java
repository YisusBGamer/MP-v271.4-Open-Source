package net.swordie.ms.client.character;

import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.loaders.DataTable;

import java.util.List;
import java.util.function.Supplier;

/**
 * Quests that fill a record with hunting EXP (resources/data/hunt-exp-records.tsv), like the Black Wings energy
 * cartridges: while the quest is in progress, each kill's EXP is added to the record, up to its target.
 */
public final class HuntExpRecords {

    private record Row(int quest, int record, long target) {
    }

    private static final Supplier<List<Row>> ROWS = DataTable.cached("hunt-exp-records", t -> t.rows().stream()
            .map(r -> new Row(r.i("quest"), r.i("record"), r.l("target"))).toList());

    private HuntExpRecords() {
    }

    /** Called with the EXP a kill gave (Mob#distributeExp). */
    public static void onHuntExp(Char chr, long exp) {
        QuestManager qm = chr.getQuestManager();
        if (exp <= 0 || qm == null) {
            return;
        }
        for (Row row : ROWS.get()) {
            if (!qm.hasQuestInProgress(row.quest())) {
                continue;
            }
            synchronized (qm) {
                Quest record = qm.getOrCreateQuestById(row.record());
                if (record == null) {
                    continue;
                }
                String value = record.getQRValue() == null ? "" : record.getQRValue().trim();
                long now = value.matches("[0-9]{1,18}") ? Long.parseLong(value) : 0;
                if (now >= row.target()) {
                    continue;
                }
                record.setQrValue(String.valueOf(Math.min(row.target(), now + exp)));
                qm.sendRecord(record);
            }
        }
    }
}
