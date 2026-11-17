package net.swordie.ms.client.character.items;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.jwzlib.WzFile;
import net.swordie.ms.jwzlib.WzObject;
import net.swordie.ms.jwzlib.WzProperty;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.StringData;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Wisp's Wondrous Wonderberry and the Luna Crystals. The pets come from Item.wz/Pet info/wonderGrade: 1 and 2 are
 * the Wonderberry pets (Wonder Black / Royal Wonder), 6 the moonlight (Luna) pets the crystals make.
 */
public class WonderPets {
    private static final Logger log = LogManager.getLogger();
    public static final int WONDERBERRY = 5068300, WONDERBERRY_2 = 5068301;
    public static final int LUNA_CRYSTAL = 5069100, SPECIAL_LUNA_CRYSTAL = 5069101;
    public static final int LUNA_GRADE = 6;

    private static volatile Map<Integer, Integer> grades;

    private static Map<Integer, Integer> grades() {
        if (grades == null) {
            synchronized (WonderPets.class) {
                if (grades == null) {
                    Map<Integer, Integer> map = new HashMap<>();
                    try {
                        WzObject<?, ?> petDir = new WzFile(ServerConstants.WZ_DIR + "/Item.wz").getChild("Pet");
                        for (WzObject<?, ?> img : petDir) {
                            String name = img.getName().replace(".img", "");
                            if (!Util.isNumber(name)) {
                                continue;
                            }
                            WzObject<?, ?> info = img.getChild("info");
                            WzObject<?, ?> grade = info == null ? null : info.getChild("wonderGrade");
                            if (grade instanceof WzProperty<?> p && p.getIntValue() > 0) {
                                map.put(Integer.parseInt(name), p.getIntValue());
                            }
                        }
                    } catch (RuntimeException e) {
                        log.error("Could not read the Wonder pet grades from Item.wz/Pet", e);
                    }
                    grades = map;
                }
            }
        }
        return grades;
    }

    public static int gradeOf(int petId) {
        return grades().getOrDefault(petId, 0);
    }

    private static List<Integer> ofGrades(int... wanted) {
        List<Integer> list = new ArrayList<>();
        for (var e : grades().entrySet()) {
            for (int g : wanted) {
                if (e.getValue() == g && ItemData.getPetInfoByID(e.getKey()) != null) {
                    list.add(e.getKey());
                }
            }
        }
        Collections.sort(list);
        return list;
    }

    private static String give(Char chr, int petId, Item used, Item... alsoUsed) {
        Item pet = ItemData.getItemDeepCopy(petId);
        if (pet == null) {
            return "That pet can't be made right now.";
        }
        if (!chr.canHold(pet.deepCopy())) {
            return "Please make room in your Cash inventory first.";
        }
        for (Item i : alsoUsed) {
            chr.consumeItemFull(i);
        }
        chr.consumeItem(used, 1);
        chr.addItemToInventory(pet);
        String name = StringData.getItemStringById(petId);
        chr.chatMessage(String.format("You received the pet %s.", name != null ? name : String.valueOf(petId)));
        return null;
    }

    /** Wonderberry: a random Wonderberry pet. @return null if done, else why not */
    public static String openWonderberry(Char chr, Item berry) {
        List<Integer> pool = ofGrades(1, 2);
        if (pool.isEmpty()) {
            return "The Wonderberry has nothing to give right now.";
        }
        return give(chr, Util.getRandomFromCollection(pool), berry);
    }

    /**
     * Luna Crystal: two Wonderberry pets (base, material) become one moonlight pet. The Special Luna Crystal also takes
     * a moonlight pet as the base.
     */
    public static String useLunaCrystal(Char chr, Item crystal, long baseSn, long materialSn) {
        Item base = chr.getCashInventory().getItemBySN(baseSn);
        Item material = chr.getCashInventory().getItemBySN(materialSn);
        if (!(base instanceof PetItem) || !(material instanceof PetItem) || base == material) {
            return "Choose two different pets.";
        }
        if (((PetItem) base).getActiveState() != 0 || ((PetItem) material).getActiveState() != 0) {
            return "Put your pets away before using the Luna Crystal.";
        }
        int baseGrade = gradeOf(base.getItemId()), materialGrade = gradeOf(material.getItemId());
        boolean special = crystal.getItemId() == SPECIAL_LUNA_CRYSTAL;
        boolean baseOk = baseGrade == 1 || baseGrade == 2 || special && baseGrade == LUNA_GRADE;
        if (!baseOk || materialGrade != 1 && materialGrade != 2) {
            return "The Luna Crystal only works on pets born from Wisp's Wondrous Wonderberry.";
        }
        List<Integer> pool = ofGrades(LUNA_GRADE);
        if (pool.isEmpty()) {
            return "The Luna Crystal has nothing to give right now.";
        }
        return give(chr, Util.getRandomFromCollection(pool), crystal, base, material);
    }
}
