package net.swordie.ms.client.character;

import net.swordie.ms.ext.ServerHooks;
import net.swordie.ms.loaders.QuestData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Unlocks the Cernium story and its Sacred Symbol.
 * <ul>
 *     <li>At level 260 the Cernium story ([Cernium (Before)] 39801-39818) starts automatically once its Quest.wz
 *     prerequisites are done ([Convergence] True Form, the end of the Black Mage story). Characters that skip the
 *     stories (Express, bots) get it at 260 without them.</li>
 *     <li>Finishing Cernium Unified (39818) gives Sacred Symbol: Cernium once (retried at login if the Equip tab was
 *     full). The Flame Priest's Cernium Research dailies level it up.</li>
 * </ul>
 */
public final class CerniumStoryModule {

    private static final Logger log = LogManager.getLogger(CerniumStoryModule.class);

    public static final int LEVEL = 260;
    public static final int FIRST_QUEST = 39801; // [Cernium (Before)] A Traveler from Beyond the Sea
    public static final int LAST_QUEST = 39818; // [Cernium (Before)] Cernium Unified
    public static final int SACRED_SYMBOL_CERNIUM = 1713000;
    /** Server-only record: "1" once the story's Sacred Symbol was given. */
    public static final int SYMBOL_GIVEN_QR = 89999940;

    private CerniumStoryModule() {
    }

    /** Called at login and level up (LevelUnlockModule.check). */
    public static void check(Char chr) {
        var qm = chr.getQuestManager();
        boolean skipsStory = ServerHooks.isAutomated(chr) || ContentAccess.unlocksAll(chr);
        if (chr.getLevel() >= LEVEL && !qm.hasQuestCompleted(FIRST_QUEST) && !qm.hasQuestInProgress(FIRST_QUEST)
                && (skipsStory || qm.canStartQuestQuietly(FIRST_QUEST))) {
            var quest = QuestData.createQuestFromId(FIRST_QUEST);
            if (quest != null) {
                qm.addQuest(quest);
                chr.chatMessage("Neinheart: a traveler from beyond the sea needs you. Edea waits in Cernium "
                        + "(@go Cernium): the Cernium story is in your quest list.");
            }
        }
        if (qm.hasQuestCompleted(LAST_QUEST)) {
            giveSymbol(chr);
        }
    }

    /** Gives Sacred Symbol: Cernium once, when the Cernium story is done. @return whether it was given now */
    public static boolean giveSymbol(Char chr) {
        var qm = chr.getQuestManager();
        if (!qm.hasQuestCompleted(LAST_QUEST) || "1".equals(qm.getHiddenRecord(SYMBOL_GIVEN_QR))) {
            return false;
        }
        if (!chr.canHold(SACRED_SYMBOL_CERNIUM, 1)) {
            chr.chatMessage("Make room in your Equip inventory for your Sacred Symbol: Cernium (it comes at your next "
                    + "login or level up).");
            return false;
        }
        try {
            chr.addItemToInventory(SACRED_SYMBOL_CERNIUM, 1);
            qm.setHiddenRecord(SYMBOL_GIVEN_QR, "1");
            chr.chatMessage("You received Sacred Symbol: Cernium. Equip it, then see the Flame Priest in Cernium "
                    + "for Cernium Research to level it up.");
            return true;
        } catch (Exception e) {
            log.error("Could not give the Sacred Symbol: Cernium to " + chr.getName(), e);
            return false;
        }
    }
}
