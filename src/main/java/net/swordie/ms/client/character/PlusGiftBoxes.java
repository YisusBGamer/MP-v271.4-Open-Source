package net.swordie.ms.client.character;

import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.ext.ServerHooks;
import net.swordie.ms.loaders.ItemData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Plus Gift Boxes (stages 1-8): each account gets each box once, when one of its characters reaches the box's
 * level, or on login for characters already past it. Given boxes are kept in coin_rewards (kind plus_gift_box). The
 * boxes open through resources/item-box-rewards.tsv.
 */
public final class PlusGiftBoxes {
    private static final int PLUS_GIFT_BOX_STAGE_1 = 2435814;
    private static final int PLUS_GIFT_BOX_STAGE_2 = 2435815;
    private static final int PLUS_GIFT_BOX_STAGE_3 = 2435816;
    private static final int PLUS_GIFT_BOX_STAGE_4 = 2435817;
    private static final int PLUS_GIFT_BOX_STAGE_5 = 2435818;
    private static final int PLUS_GIFT_BOX_STAGE_6 = 2435819;
    private static final int PLUS_GIFT_BOX_STAGE_7 = 2435820;
    private static final int PLUS_GIFT_BOX_STAGE_8 = 2435821;

    private static final Logger log = LogManager.getLogger();
    private static final String KIND = "plus_gift_box";

    /** {box, level} */
    private static final int[][] STAGES = {
            {PLUS_GIFT_BOX_STAGE_1, 30}, {PLUS_GIFT_BOX_STAGE_2, 50}, {PLUS_GIFT_BOX_STAGE_3, 60}, {PLUS_GIFT_BOX_STAGE_4, 80},
            {PLUS_GIFT_BOX_STAGE_5, 90}, {PLUS_GIFT_BOX_STAGE_6, 110}, {PLUS_GIFT_BOX_STAGE_7, 120}, {PLUS_GIFT_BOX_STAGE_8, 150},
    };

    /** Boxes already given, by account (filled as they are read or given). */
    private static final Map<Integer, Set<Integer>> given = new ConcurrentHashMap<>();

    private PlusGiftBoxes() {
    }

    /** @return the level a box needs, or 0 if it isn't a Plus Gift Box */
    public static int levelFor(int itemId) {
        for (int[] s : STAGES) {
            if (s[0] == itemId) {
                return s[1];
            }
        }
        return 0;
    }

    public static void onLogin(Char chr) {
        giveDue(chr);
    }

    public static void onLevelUp(Char chr) {
        giveDue(chr);
    }

    private static synchronized void giveDue(Char chr) {
        if (chr == null || chr.getAccount() == null || !ServerFeatures.on(ServerFeatures.Feature.PLUS_GIFT_BOXES)
                || (ServerHooks.isAutomated(chr) && !ServerHooks.isTestCharacter(chr))) {
            return;
        }
        int accId = chr.getAccount().getId();
        Set<Integer> done = given.computeIfAbsent(accId, k -> ConcurrentHashMap.newKeySet());
        for (int[] s : STAGES) {
            int box = s[0];
            if (chr.getLevel() < s[1] || done.contains(box)) {
                continue;
            }
            String key = String.valueOf(box);
            int stored = SwordieCoinRewards.stored(accId, KIND, key, SwordieCoinRewards.EVER);
            if (stored == Integer.MAX_VALUE) {
                return; // can't read the table: try again later
            }
            if (stored >= 0) {
                done.add(box);
                continue;
            }
            try {
                Item item = ItemData.getItemDeepCopy(box);
                if (item == null) {
                    continue;
                }
                if (!chr.canHold(item)) {
                    chr.chatMessage("A Plus Gift Box is waiting for you: make room in your Use tab and log in again"
                            + " or level up to get it.");
                    return;
                }
                if (!SwordieCoinRewards.store(accId, KIND, key, SwordieCoinRewards.EVER, 1)) {
                    return;
                }
                done.add(box);
                chr.addItemToInventory(item);
                chr.chatMessage("You received a Plus Gift Box <Stage " + (indexOf(box) + 1) + ">. Double-click it to"
                        + " open it.");
            } catch (Exception e) {
                log.error("Could not give Plus Gift Box " + box + " to " + chr.getName(), e);
            }
        }
    }

    private static int indexOf(int box) {
        for (int i = 0; i < STAGES.length; i++) {
            if (STAGES[i][0] == box) {
                return i;
            }
        }
        return -1;
    }
}
