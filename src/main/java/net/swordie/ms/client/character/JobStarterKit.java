package net.swordie.ms.client.character;

import net.swordie.ms.client.character.items.EquipDropFilter;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.JobConstants.JobEnum;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.StringData;
import net.swordie.ms.loaders.containerclasses.ItemInfo;

import java.util.*;
import java.util.function.Supplier;

/**
 * The starter kit a character gets when it first chooses its job path with @job (see {@link JobPathModule}): a
 * level 10 weapon of its class plus the secondary weapon (and ammunition) the class needs from its 1st job.
 * <p>
 * Classes whose secondary weapons only start at the 2nd job (level 30) in the game data, like the Explorer medallions
 * and books, get the weapon only. Dual Blades get their first Katara (level 20) right away, since they need it at
 * their level 20 advancement.
 */
public class JobStarterKit {

    private record Kits(Map<Integer, int[]> kits, Map<Integer, Integer> secondaryAt30) {
    }

    /**
     * resources/data/job-starter-kits.tsv: final job of a path -> item ids of its kit, and its secondary weapon when
     * that one needs level 30 (given by {@link #giveMissingSecondary}). HashMaps, so iteration order stays the same.
     */
    private static final Supplier<Kits> KITS = DataTable.cached("job-starter-kits", t -> {
        Map<Integer, int[]> kits = new HashMap<>();
        Map<Integer, Integer> secondary = new HashMap<>();
        for (DataTable.Row r : t.rows()) {
            kits.put(r.i("job"), r.ints("items"));
            if (r.has("secondaryAt30")) {
                secondary.put(r.i("job"), r.i("secondaryAt30"));
            }
        }
        return new Kits(Collections.unmodifiableMap(kits), Collections.unmodifiableMap(secondary));
    });

    private static Map<Integer, int[]> kits() {
        return KITS.get().kits();
    }

    private static Map<Integer, Integer> secondaryAt30() {
        return KITS.get().secondaryAt30();
    }

    /** 1st jobs that start their own path inside a branch (the branch's other classes share x00). */
    private static final Map<Integer, Integer> FIRST_JOB_PATHS = Map.of(JobEnum.PATHFINDER_1.getJobId(), JobEnum.PATHFINDER_4.getJobId(), JobEnum.PIRATE_CANNONEER.getJobId(), JobEnum.CANNON_MASTER.getJobId(), JobEnum.JETT_1.getJobId(), JobEnum.JETT_4.getJobId(), JobEnum.DEMON_AVENGER_1.getJobId(), JobEnum.DEMON_AVENGER_4.getJobId());

    /**
     * The last job of the path a job belongs to, whose kit it uses: a 1st-3rd job (!testclass 3310) needs the same
     * weapon. A shared 1st job (100, 3100) gets its branch's first path. Jobs with no kit come back unchanged.
     */
    public static int kitJob(int job) {
        if (kits().containsKey(job)) {
            return job;
        }
        Integer first = FIRST_JOB_PATHS.get(job);
        if (first != null) {
            return first;
        }
        int path = job % 100 / 10;
        int best = -1;
        for (int k : kits().keySet()) {
            if (k / 100 != job / 100 || k < job || k % 100 / 10 != (path == 0 ? 1 : path)) {
                continue;
            }
            if (best == -1 || k < best) {
                best = k;
            }
        }
        return best == -1 ? job : best;
    }

    /** @return the level-30 secondary of the path the given job belongs to, or 0 */
    public static int getSecondaryAt30(int job) {
        return secondaryAt30().getOrDefault(kitJob(job), 0);
    }

    /** Whether the item is a weapon or secondary out of some class's kit (test characters collect them). */
    public static boolean isKitEquip(int itemId) {
        if (!ItemConstants.isEquip(itemId)) {
            return false;
        }
        if (secondaryAt30().containsValue(itemId)) {
            return true;
        }
        for (int[] ids : kits().values()) {
            for (int id : ids) {
                if (id == itemId) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * @return the items (with their quantities) of the kit of the path the given job belongs to; empty if it has
     * none. Items missing from the game data are left out.
     */
    public static List<Item> getKit(int job) {
        List<Item> res = new ArrayList<>();
        for (int itemId : kits().getOrDefault(kitJob(job), new int[0])) {
            Item item = ItemData.getItemDeepCopy(itemId);
            if (item == null) {
                continue;
            }
            if (ItemConstants.isEquip(itemId)) {
                item.setQuantity(1);
            } else {
                ItemInfo info = ItemData.getItemInfoByID(itemId);
                item.setQuantity(info != null && info.getSlotMax() > 0 ? info.getSlotMax() : 1000);
            }
            res.add(item);
        }
        return res;
    }

    /**
     * The class's own secondary weapon (prefix 135: Magic Arrows, Carte Magique, Light Orb, ...) from its kit, for a
     * character without one. The kit only comes with @job, so characters created normally never received it.
     *
     * @return the secondary's item id to give, or 0 (none needed, one worn or carried, or no single kit fits the job)
     */
    public static int getMissingSecondary(Char chr) {
        int job = chr.getJob();
        if (chr.getLevel() < 10 || job % 1000 == 0 || job / 100 == 0) {
            return 0; // beginners
        }
        int found = 0;
        Map<Integer, int[]> candidates = new HashMap<>(kits());
        if (chr.getLevel() >= 30) {
            secondaryAt30().forEach((key, id) -> candidates.put(-key, new int[]{id}));
        }
        for (var e : candidates.entrySet()) {
            int key = Math.abs(e.getKey());
            if (key / 100 != job / 100 || job < 1000 && key / 10 != job / 10) {
                continue; // another family (Explorers share theirs: Jett 57x isn't a Buccaneer 51x)
            }
            for (int id : e.getValue()) {
                if (id / 10000 == 135) {
                    if (found != 0 && found != id) {
                        return 0; // two classes of this family need different ones: not sure which
                    }
                    found = id;
                }
            }
        }
        if (found == 0) {
            return 0;
        }
        int kind = found / 100; // 13520xx Magic Arrows, 13521xx Carte Magique, ...
        // 13528xx: Kodachis, Leaves, Fists, Brace Bands, Compasses; 13522xx: Explorer medallions to charms;
        // 13529xx: Wrist Bands to Wild Beaks
        boolean shared = kind == 13528 || kind == 13522 || kind == 13529;
        for (var inv : List.of(chr.getEquippedInventory(), chr.getEquipInventory())) {
            for (Item item : inv.getItems()) {
                int id = item.getItemId();
                if (id / 100 == kind && (!shared || EquipDropFilter.isUsableBy(id, chr))) {
                    return 0;
                }
            }
        }
        return found;
    }

    /** Gives the class's secondary weapon if it has none (login and level up). */
    public static void giveMissingSecondary(Char chr) {
        int id = getMissingSecondary(chr);
        if (id == 0 || !chr.canHold(id, 1)) {
            return;
        }
        chr.addItemToInventory(id, 1);
        chr.chatMessage("Your class's skills need a secondary weapon: you received " + StringData.getItemStringById(id)
                + ". Equip it from your Equip tab.");
    }

    /**
     * @return whether the Char has room for the kit of the path that ends with the given job
     */
    public static boolean canHold(Char chr, int finalJob) {
        List<Item> kit = getKit(chr, finalJob);
        return kit.isEmpty() || chr.canHold(kit); // canHold consumes the list, so it gets a fresh one
    }

    /**
     * @return the kit without one-of-a-kind items the Char already has (kept from before a rebirth)
     */
    private static List<Item> getKit(Char chr, int finalJob) {
        List<Item> kit = getKit(finalJob);
        kit.removeIf(item -> chr.isOneOfAKindBlocked(item.getItemId(), item.getQuantity()));
        return kit;
    }

    /**
     * @return how many free Equip and Use slots the kit needs, for a message
     */
    public static String getSpaceNeeded(Char chr, int finalJob) {
        int equips = 0;
        int others = 0;
        for (Item item : getKit(chr, finalJob)) {
            if (ItemConstants.isEquip(item.getItemId())) {
                equips++;
            } else {
                others++;
            }
        }
        return others == 0 ? String.format("%d free Equip slot(s)", equips)
                : String.format("%d free Equip slot(s) and %d free Use slot(s)", equips, others);
    }

    /**
     * Gives the kit of the path that ends with the given job (check {@link #canHold(Char, int)} first).
     *
     * @return the names of the items given (empty if the path has no kit)
     */
    public static List<String> give(Char chr, int finalJob) {
        List<String> names = new ArrayList<>();
        for (Item item : getKit(chr, finalJob)) {
            chr.addItemToInventory(item.getItemId(), item.getQuantity());
            String name = StringData.getItemStringById(item.getItemId());
            name = name == null ? String.valueOf(item.getItemId()) : name;
            names.add(item.getQuantity() > 1 ? name + " x" + item.getQuantity() : name);
        }
        return names;
    }
}
