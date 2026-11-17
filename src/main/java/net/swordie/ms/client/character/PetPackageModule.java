package net.swordie.ms.client.character;

import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.items.PetItem;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.enums.InvType;
import net.swordie.ms.life.pet.PetSkill;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.loaders.EtcData;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.StringData;

import java.text.NumberFormat;
import java.util.*;
import java.util.function.Supplier;

/**
 * @pet (scripts/npc/petPackage.py): a pet of the player's choice with every pet skill unlocked at once (item pickup,
 * expanded range, auto move, ignore items, expired pickup, auto HP/MP, auto buff, auto feed, fatten up, pet shop,
 * recall, auto speaking, auto cure). The same skills can also be unlocked
 * on a pet the player already has ({@link GameConstants#PET_SKILL_UNLOCK_PRICE}). Price of a pet:
 * {@link GameConstants#PET_PACKAGE_PRICE} mesos. Also sells pet food, snacks and The Rock of Evolution, and evolves
 * pets (Baby Dragon) like Mar the Fairy does.
 */
public class PetPackageModule {

    /** Pets shown before any search (pet-featured.tsv). */
    private static final Supplier<int[]> FEATURED = DataTable.cached("pet-featured",
            t -> t.rows().stream().mapToInt(r -> r.i("item")).toArray());
    /** the pet id block the shop's tier lists scan */
    private static final int PET_ID_FIRST = 5000000, PET_ID_LAST = 5009999;
    private static final int PET_FOOD = 2120000, DRY_TREAT = 2120008, ROCK_OF_EVOLUTION = 5380000;

    private static final int MAX_RESULTS = 40;
    private static final int MAX_TIER_RESULTS = 60;

    /**
     * Price brackets by what a pet adds: a plain pet gives nothing; pets of a 3-pet set give a set skill (+3 to +7
     * ATT/M.ATT); Wonder pets (wonderGrade 1) a stronger one (+5 to +9); Luna pets (wonderGrade 6) the strongest
     * (+8 to +18 and a wider pickup range).
     */
    public enum Tier {
        PLAIN("Pets", GameConstants.PET_PACKAGE_PRICE),
        SET("Set pets", GameConstants.PET_SET_PRICE),
        WONDER("Wonder pets", GameConstants.PET_WONDER_PRICE),
        LUNA("Luna pets", GameConstants.PET_LUNA_PRICE);

        private final String title;
        private final long price;

        Tier(String title, long price) {
            this.title = title;
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public long getPrice() {
            return price;
        }
    }

    public static Tier getTier(int petId) {
        var info = ItemData.getPetInfoByID(petId);
        if (info == null) {
            return Tier.PLAIN;
        }
        if (info.getWonderGrade() == 6) {
            return Tier.LUNA;
        }
        if (info.getWonderGrade() == 1) {
            return Tier.WONDER;
        }
        if (info.getSetItemID() > 0 && EtcData.getSetItemInfoById(info.getSetItemID()) != null) {
            return Tier.SET;
        }
        return Tier.PLAIN;
    }

    public static long getPrice(int petId) {
        return getTier(petId).getPrice();
    }

    public static Tier[] getTiers() {
        return Tier.values();
    }

    /**
     * @return pets of a tier, newest first (one of each name)
     */
    public static List<Integer> getByTier(String tierName) {
        return tierLists().getOrDefault(Tier.valueOf(tierName), List.of());
    }

    private static volatile Map<Tier, List<Integer>> tierLists;

    private static Map<Tier, List<Integer>> tierLists() {
        if (tierLists == null) {
            Map<Tier, List<Integer>> lists = new EnumMap<>(Tier.class);
            Map<Tier, Set<String>> names = new EnumMap<>(Tier.class);
            for (int id = PET_ID_LAST; id >= PET_ID_FIRST; id--) {
                if (!isBuyablePet(id)) {
                    continue;
                }
                Tier tier = getTier(id);
                List<Integer> list = lists.computeIfAbsent(tier, t -> new ArrayList<>());
                if (list.size() < MAX_TIER_RESULTS
                        && names.computeIfAbsent(tier, t -> new HashSet<>()).add(StringData.getItemStringById(id).trim().toLowerCase())) {
                    list.add(id);
                }
            }
            tierLists = lists;
        }
        return tierLists;
    }

    public static String format(long n) {
        return NumberFormat.getNumberInstance(Locale.US).format(n);
    }

    private static boolean isBuyablePet(int itemId) {
        return ItemConstants.isPet(itemId) && ItemData.getPetInfoByID(itemId) != null
                && StringData.getItemStringById(itemId) != null;
    }

    public static List<Integer> getFeatured() {
        List<Integer> res = new ArrayList<>();
        for (int id : FEATURED.get()) {
            if (isBuyablePet(id)) {
                res.add(id);
            }
        }
        return res;
    }

    /**
     * @return pets whose name contains the query, by name (one of each name)
     */
    public static List<Integer> search(String query) {
        List<Integer> res = new ArrayList<>();
        if (query == null || query.trim().length() < 2) {
            return res;
        }
        Set<String> names = new HashSet<>();
        StringData.getItemStringByName(query.trim(), false).entrySet().stream()
                .filter(e -> isBuyablePet(e.getKey()))
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> {
                    if (res.size() < MAX_RESULTS && names.add(e.getValue().trim().toLowerCase())) {
                        res.add(e.getKey());
                    }
                });
        return res;
    }

    private static int allSkills() {
        int flags = 0;
        for (PetSkill ps : PetSkill.values()) {
            flags |= ps.getVal();
        }
        return flags;
    }

    /**
     * @return the pets this character has (cash inventory) that don't have every skill yet
     */
    public static List<PetItem> getUpgradablePets(Char chr) {
        List<PetItem> res = new ArrayList<>();
        int all = allSkills();
        for (Item item : chr.getCashInventory().getItems()) {
            if (item instanceof PetItem pet && (pet.getPetSkill() & all) != all) {
                res.add(pet);
            }
        }
        res.sort(Comparator.comparingInt(Item::getBagIndex));
        return res;
    }

    public static long getUnlockPrice() {
        return GameConstants.PET_SKILL_UNLOCK_PRICE;
    }

    private static String pay(Char chr, long price) {
        if (chr.getMoney() < price) {
            return String.format("You need %s mesos for this.", format(price));
        }
        chr.deductMoney(price, ProgressionLog.Sink.FEES);
        return null;
    }

    // ------------------------------------------------------------------------------------------------- shop

    /** Pet shop items: item id, price in mesos. */
    public static final long[][] SHOP = {
            {PET_FOOD, GameConstants.PET_FOOD_PRICE},
            {DRY_TREAT, GameConstants.PET_SNACK_PRICE},
            {ROCK_OF_EVOLUTION, GameConstants.ROCK_OF_EVOLUTION_PRICE},
    };

    public static int getShopSize() {
        return SHOP.length;
    }

    public static int getShopItem(int i) {
        return (int) SHOP[i][0];
    }

    public static long getShopPrice(int i) {
        return SHOP[i][1];
    }

    /**
     * @return how many of shop item i the character can afford and hold (at most one full stack)
     */
    public static int getMaxBuyable(Char chr, int i) {
        int itemId = getShopItem(i);
        var info = ItemData.getItemInfoByID(itemId);
        int max = info == null ? 1 : Math.max(1, info.getSlotMax());
        long affordable = chr.getMoney() / getShopPrice(i);
        return (int) Math.max(0, Math.min(max, affordable));
    }

    public static synchronized String buyShopItem(Char chr, int i, int quantity) {
        if (i < 0 || i >= SHOP.length || quantity <= 0 || quantity > getMaxBuyable(chr, i)) {
            return "You can't buy that many.";
        }
        int itemId = getShopItem(i);
        if (!chr.canHold(itemId, quantity)) {
            return "Make room in your inventory first.";
        }
        String error = pay(chr, getShopPrice(i) * quantity);
        if (error != null) {
            return error;
        }
        Item item = ItemData.getItemDeepCopy(itemId);
        item.setQuantity(quantity);
        chr.addItemToInventory(item);
        return String.format("Here you go: #b%,d #t%d##k.", quantity, itemId);
    }

    // ------------------------------------------------------------------------------------------------- evolution

    /**
     * @return the character's pets (cash inventory) that can evolve with an item, e.g. Baby Dragons
     */
    public static List<PetItem> getEvolvablePets(Char chr) {
        List<PetItem> res = new ArrayList<>();
        for (Item item : chr.getCashInventory().getItems()) {
            if (item instanceof PetItem pet && pet.canEvolveWithItem()) {
                res.add(pet);
            }
        }
        res.sort(Comparator.comparingInt(Item::getBagIndex));
        return res;
    }

    /**
     * Evolves a pet (e.g. Baby Dragon -> Green/Red/Blue/Black Dragon) for its evolution item (The Rock of
     * Evolution) once it reaches the required pet level. It keeps its level, closeness, skills and name.
     */
    public static synchronized String evolve(Char chr, PetItem pet) {
        if (pet == null || chr.getCashInventory().getItemBySlot(pet.getBagIndex()) != pet || !pet.canEvolveWithItem()) {
            return "That pet can't evolve.";
        }
        if (pet.getActiveState() != 0) {
            return "Put your pet away first (unsummon it), then try again.";
        }
        if (pet.getLevel() < pet.getEvolutionLevel()) {
            return String.format("%s needs to be pet level %d to evolve (it is level %d).", pet.getName(),
                    pet.getEvolutionLevel(), pet.getLevel());
        }
        int reqItem = pet.getEvolutionItemId();
        if (reqItem != 0 && !chr.hasItemCount(reqItem, 1)) {
            return String.format("You need #b#t%d##k to evolve %s.", reqItem, pet.getName());
        }
        if (reqItem != 0) {
            chr.consumeItem(reqItem, 1);
        }
        int newId = pet.evolve();
        pet.updateToChar(chr);
        return String.format("Your pet evolved into #b#t%d##k!", newId);
    }

    public static synchronized String buy(Char chr, int petId) {
        if (!isBuyablePet(petId)) {
            return "That pet isn't available.";
        }
        if (chr.getCashInventory().getEmptySlots() < 1) {
            return "Make room for 1 item in your Cash inventory first.";
        }
        Item item = ItemData.getItemDeepCopy(petId);
        if (!(item instanceof PetItem pet)) {
            return "That pet isn't available.";
        }
        String error = pay(chr, getPrice(petId));
        if (error != null) {
            return error;
        }
        pet.hatchIfEgg();
        pet.setPetSkill(pet.getPetSkill() | allSkills());
        chr.addItemToInventory(pet);
        return String.format("Here is your #b#t%d##k, with every pet skill unlocked!", pet.getItemId());
    }

    public static synchronized String upgrade(Char chr, PetItem pet) {
        if (pet == null || chr.getCashInventory().getItemBySlot(pet.getBagIndex()) != pet) {
            return "Could not find that pet.";
        }
        String error = pay(chr, getUnlockPrice());
        if (error != null) {
            return error;
        }
        pet.setPetSkill(pet.getPetSkill() | allSkills());
        pet.updateToChar(chr);
        return String.format("#b%s#k now has every pet skill unlocked!", pet.getName());
    }
}
