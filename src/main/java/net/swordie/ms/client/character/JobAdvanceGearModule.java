package net.swordie.ms.client.character;

import net.swordie.ms.client.character.items.EquipDropFilter;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.StringData;

import java.util.*;
import java.util.function.Supplier;

/**
 * The weapons and secondary weapons a class's job advancement quests hand out, for sale once per character in @shop.
 * A character that skips those quests (Express, @job) would otherwise never get them: the Demon Slayer's Force
 * Shields, the Kaiser's Nova Essences and the like. Each piece shows once the character reached the job tier of its
 * level (10: 1st job, 30: 2nd, 60: 3rd, 100: 4th) and costs about what Neville asks for a secondary of that level.
 */
public class JobAdvanceGearModule {

    /** Server-only record: the item ids already bought, comma separated. */
    public static final int BOUGHT_QR = 89999966;

    /** Class name (as {@link EquipDropFilter#getClassNames}) -> the pieces its job advancements give, lowest first. */
    private static final Supplier<Map<String, int[]>> GEAR = DataTable.cached("job-advance-gear", t -> {
        Map<String, List<Integer>> m = new LinkedHashMap<>();
        for (DataTable.Row r : t.rows()) {
            m.computeIfAbsent(r.str("class").trim(), k -> new ArrayList<>()).add(r.i("item"));
        }
        Map<String, int[]> res = new LinkedHashMap<>();
        m.forEach((cls, ids) -> res.put(cls, ids.stream().mapToInt(Integer::intValue).toArray()));
        return res;
    });

    private JobAdvanceGearModule() {
    }

    public static int getReqLevel(int itemId) {
        var info = ItemData.getEquipInfoById(itemId);
        return info == null ? 0 : info.getrLevel();
    }

    /** @return the job tier a piece of this required level belongs to */
    private static int getTier(int reqLevel) {
        return reqLevel < 30 ? 1 : reqLevel < 60 ? 2 : reqLevel < 100 ? 3 : 4;
    }

    /** @return the meso price: Neville's for a secondary of that level */
    public static long getPrice(int itemId) {
        int lv = getReqLevel(itemId);
        return lv < 30 ? 3_000 : lv < 60 ? 80_000 : lv < 100 ? 330_000 : 580_000;
    }

    /** @return all pieces of the character's class, in order (empty if its class has none) */
    public static List<Integer> getClassGear(Char chr) {
        List<Integer> res = new ArrayList<>();
        for (String cls : EquipDropFilter.getClassNames(chr)) {
            for (int id : GEAR.get().getOrDefault(cls, new int[0])) {
                if (!res.contains(id) && ItemData.getEquipInfoById(id) != null) {
                    res.add(id);
                }
            }
        }
        return res;
    }

    /** @return whether the character reached the job tier (and level) of the piece */
    public static boolean isUnlocked(Char chr, int itemId) {
        int lv = getReqLevel(itemId);
        return chr.getLevel() >= lv && JobConstants.getAdvancementTier(chr.getJob()) >= getTier(lv);
    }

    private static Set<Integer> getBought(Char chr) {
        Set<Integer> res = new HashSet<>();
        String rec = chr.getQuestManager().getHiddenRecord(BOUGHT_QR);
        if (rec != null) {
            for (String s : rec.split(",")) {
                try {
                    res.add(Integer.parseInt(s.trim()));
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return res;
    }

    public static boolean isBought(Char chr, int itemId) {
        return getBought(chr).contains(itemId);
    }

    /** @return whether the class has any pieces at all (the @shop entry shows only then) */
    public static boolean hasGear(Char chr) {
        return !getClassGear(chr).isEmpty();
    }

    /**
     * Sells one piece.
     *
     * @return what happened, for the player
     */
    public static String buy(Char chr, int itemId) {
        if (!getClassGear(chr).contains(itemId) || !EquipDropFilter.isUsableBy(itemId, chr)) {
            return "That isn't gear of your class.";
        }
        if (!isUnlocked(chr, itemId)) {
            return "You can get this once you reach the job advancement of level " + getReqLevel(itemId) + ".";
        }
        Set<Integer> bought = getBought(chr);
        if (bought.contains(itemId)) {
            return "You already got this one.";
        }
        long price = getPrice(itemId);
        if (chr.getMoney() < price) {
            return "You need " + String.format("%,d", price) + " mesos.";
        }
        if (!chr.canHold(itemId, 1)) {
            return "Make room in your Equip tab first.";
        }
        chr.deductMoney(price, ProgressionLog.Sink.SHOPS);
        chr.addItemToInventory(itemId, 1);
        bought.add(itemId);
        StringBuilder rec = new StringBuilder();
        for (int id : bought) {
            rec.append(rec.length() == 0 ? "" : ",").append(id);
        }
        chr.getQuestManager().setHiddenRecord(BOUGHT_QR, rec.toString());
        String name = StringData.getItemStringById(itemId);
        return "You got " + (name == null ? String.valueOf(itemId) : name) + ". It's in your Equip tab.";
    }
}
