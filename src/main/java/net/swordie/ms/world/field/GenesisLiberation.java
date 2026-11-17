package net.swordie.ms.world.field;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.loaders.QuestData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * Genesis Weapon liberation: the "Traces of ..." quests (35658-35665) ask for a boss to be
 * defeated "according to the mission conditions", which the client tracks in the record of
 * [Genesis Weapon] Trailing the Traces of the Black Mage (35650) as <code>clear=N</code>, which this
 * class sets. The Destiny Weapon's Battle quests work the same way on their own records (35858 for the first
 * stage, 35878 for the second), with the boss list in resources/data/weapon-liberation-bosses.tsv.
 * <p>
 * When one of those bosses dies, every character in the map with the matching quest in progress gets the record set
 * (and, for the quests that ask for it, the Black Mage Remnant the boss's afterimage would have dropped).
 * The mission conditions themselves (no deaths, time limits, ...) aren't tracked. Each quest also asks for Traces of
 * Darkness (<code>point</code> on the same record, 500 or 1000); the record gets at least that many with the clear, since
 * nothing here hands Traces out per boss.
 */
public final class GenesisLiberation {

    private static final Logger log = LogManager.getLogger();

    public static final int RECORD = 35650;
    private static final String KEY = "clear";
    private static final String POINTS = "point";
    private static final int REMNANT_NONE = 0;

    /**
     * weapon-liberation-bosses.tsv: {quest, clear value, remnant item (0 = none), points the quest asks for, the
     * record holding clear/point, boss templates that count...}
     */
    private static final Supplier<int[][]> BOSSES = DataTable.cached("weapon-liberation-bosses", t -> t.rows().stream()
            .map(r -> IntStream.concat(IntStream.of(r.i("quest"), r.i("clear"), r.i("remnant", REMNANT_NONE),
                    r.i("points"), r.i("record")), IntStream.of(r.ints("mobs"))).toArray())
            .toArray(int[][]::new));
    private static final int FIRST_MOB = 5;

    private GenesisLiberation() {
    }

    public static void onMobDeath(Field field, Mob mob) {
        if (field == null || mob == null) {
            return;
        }
        int templateId = mob.getTemplateId();
        for (int[] trace : BOSSES.get()) {
            for (int i = FIRST_MOB; i < trace.length; i++) {
                if (trace[i] == templateId) {
                    for (Char chr : field.getCharsReadOnly()) {
                        try {
                            credit(chr, trace);
                        } catch (Exception e) {
                            log.error("Genesis/Destiny weapon credit failed for " + chr.getName() + " (mob "
                                    + templateId + ")", e);
                        }
                    }
                }
            }
        }
    }

    private static void credit(Char chr, int[] trace) {
        int recordId = trace[4];
        QuestManager qm = chr.getQuestManager();
        if (!qm.hasQuestInProgress(trace[0])) {
            return;
        }
        Quest record = qm.getQuestById(recordId);
        if (record == null) {
            record = QuestData.createQuestFromId(recordId);
            qm.addQuest(record);
        }
        String value = String.valueOf(trace[1]);
        boolean changed = false;
        if (!value.equals(record.getProperty(KEY))) {
            record.setProperty(KEY, value);
            changed = true;
        }
        if (record.getIntProperty(POINTS) < trace[3]) {
            record.setProperty(POINTS, trace[3]);
            changed = true;
        }
        if (changed) {
            chr.write(WvsContext.message(MessagePacket.questRecordExMessage(record)));
        }
        if (trace[2] != REMNANT_NONE && !chr.hasItem(trace[2])) {
            if (chr.canHold(trace[2], 1)) {
                chr.addItemToInventory(trace[2], 1);
            } else {
                chr.chatMessage("Make room in your Etc inventory: the Black Mage Remnant couldn't be given.");
                return;
            }
        }
        chr.chatMessage(recordId == RECORD ? "[Genesis Weapon] Trace of Darkness obtained. Report to Grendel the Really Old."
                : "[Destiny Weapon] Battle won. Report back to continue the Destiny Weapon quest.");
    }
}
