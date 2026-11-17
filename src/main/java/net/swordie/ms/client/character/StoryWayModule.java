package net.swordie.ms.client.character;

import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.loaders.DataTable;

import java.util.List;
import java.util.function.Supplier;

/**
 * Ways into story maps no other map leads to (resources/data/story-ways.tsv): while one of the listed quests can be
 * started or is in progress, "What should I do next?" offers to go to its map ({@link NewPlayerModule#getNextSteps}).
 */
public final class StoryWayModule {

    private record Way(int quest, int startMap, int doingMap, String text) {
    }

    private static final Supplier<List<Way>> WAYS = DataTable.cached("story-ways", t -> t.rows().stream()
            .map(r -> new Way(r.i("quest"), r.i("start"), r.i("doing"), r.str("text").trim())).toList());

    private StoryWayModule() {
    }

    /** @return {text, "hunt:&lt;map&gt;"} for the first open story way the character isn't at, or null */
    public static String[] getNextStep(Char chr) {
        QuestManager qm = chr.getQuestManager();
        if (qm == null) {
            return null;
        }
        for (Way w : WAYS.get()) {
            if (qm.hasQuestCompleted(w.quest())) {
                continue;
            }
            int map = qm.hasQuestInProgress(w.quest()) ? w.doingMap()
                    : w.startMap() > 0 && qm.canStartQuestQuietly(w.quest()) ? w.startMap() : 0;
            if (map > 0) {
                return map == chr.getFieldID() ? null : new String[]{"Story: #b" + w.text() + "#k", "hunt:" + map};
            }
        }
        return null;
    }
}
