package net.swordie.ms.client.character.items;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.JobPathModule;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.enums.ItemGrade;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.StringData;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Weapon boxes named by a set rather than an item (resources/item-set-boxes.tsv, from the String.wz descriptions):
 * job = the set's weapon for the opener's class ("17-Star Unique AbsoLab weapon, tailored for the current class"),
 * random = any weapon of the set, choose = the player picks one (only the ones their class can use, if any).
 * Part emblem = the class's level 100 emblem (Unique Emblem Box); part pieces = one item from each group of ids in
 * the set column (groups split by ';', ids by ','), the first the class can use, all given together (armor sets). Stars and potential are put on the item when the
 * description names them.
 */
public class SetBoxes {
    private static final Logger log = LogManager.getLogger();
    private static final String FILE = ServerConstants.RESOURCES_DIR + "/item-set-boxes.tsv";

    public record SetBox(int boxId, String mode, String set, String part, int stars, String grade, int days) {
        public boolean isChoice() {
            return "choose".equals(mode);
        }
    }

    private static volatile Map<Integer, SetBox> boxes;

    private static Map<Integer, SetBox> boxes() {
        if (boxes == null) {
            synchronized (SetBoxes.class) {
                if (boxes == null) {
                    Map<Integer, SetBox> map = new HashMap<>();
                    try {
                        for (String line : Files.readAllLines(Path.of(FILE), StandardCharsets.UTF_8)) {
                            if (line.isBlank() || line.startsWith("#")) {
                                continue;
                            }
                            String[] c = line.split("\t", -1);
                            int id = Integer.parseInt(c[0]);
                            map.put(id, new SetBox(id, c[1], c[2], c[3], Integer.parseInt(c[4]), c[5],
                                    Integer.parseInt(c[6])));
                        }
                    } catch (Exception e) {
                        log.error("Could not read " + FILE, e);
                    }
                    boxes = map;
                }
            }
        }
        return boxes;
    }

    public static SetBox get(int boxId) {
        return boxes().get(boxId);
    }

    /** The set's items of that part (weapon / secondary), lowest id first */
    public static List<Integer> items(SetBox box) {
        List<Integer> out = new ArrayList<>();
        for (var e : StringData.getItemStrings().entrySet()) {
            int id = e.getKey();
            String name = e.getValue();
            if (!ItemConstants.isEquip(id) || name == null
                    || !(name.startsWith(box.set() + " ") || name.equals(box.set()))) {
                continue;
            }
            int pre = id / 10000;
            boolean weapon = ItemConstants.isWeapon(id);
            boolean secondary = pre == 109 || ItemConstants.isSecondary(id);
            if (("weapon".equals(box.part()) && weapon) || ("secondary".equals(box.part()) && secondary)) {
                if (ItemData.getEquipInfoById(id) != null) {
                    out.add(id);
                }
            }
        }
        Collections.sort(out);
        return out;
    }

    /** The items a player sees or gets: job = the class's one, choose = the usable ones (all if none), random = all */
    public static List<Integer> candidates(Char chr, SetBox box) {
        if ("emblem".equals(box.part())) {
            int emblem = classEmblem(chr);
            return emblem == 0 ? List.of() : List.of(emblem);
        }
        if ("pieces".equals(box.part())) {
            List<Integer> out = new ArrayList<>();
            for (String group : box.set().split(";")) {
                for (String id : group.split(",")) {
                    int itemId = Integer.parseInt(id.trim());
                    if (!ItemConstants.isEquip(itemId)
                            || (ItemData.getEquipInfoById(itemId) != null && EquipDropFilter.isUsableBy(itemId, chr))) {
                        out.add(itemId);
                        break;
                    }
                }
            }
            return out;
        }
        List<Integer> all = items(box);
        if ("random".equals(box.mode())) {
            return all;
        }
        List<Integer> usable = all.stream().filter(id -> EquipDropFilter.isUsableBy(id, chr)).toList();
        if ("job".equals(box.mode())) {
            if (usable.isEmpty()) {
                return usable;
            }
            var current = chr.getWeaponType();
            var same = usable.stream().filter(id -> ItemConstants.getWeaponType(id) == current).toList();
            // the highest required level first (a set's low-level look-alikes, e.g. Frozen Tuna), then the lowest id
            return List.of((same.isEmpty() ? usable : same).stream()
                    .max(Comparator.comparingInt((Integer id) -> ItemData.getEquipInfoById(id).getrLevel())
                            .thenComparing(Comparator.<Integer>reverseOrder())).orElseThrow());
        }
        return usable.isEmpty() ? all : usable;
    }

    /**
     * The class's Gold (level 100) emblem: the one whose reqSpecJob is the job group, else the group's named one
     * (Demon, Resistance, Cygnus, Kinesis, Agent for Cadena, Astro for Mo Xuan), else the Gold Maple Leaf Emblem.
     */
    public static int classEmblem(Char chr) {
        List<Integer> path = JobPathModule.getActivePath(chr);
        int job = path.isEmpty() ? chr.getJob() : path.get(path.size() - 1);
        int group = job / 100;
        Map<String, Integer> byName = new HashMap<>();
        int spec = 0;
        for (var e : StringData.getItemStrings().entrySet()) {
            int id = e.getKey();
            if (!ItemConstants.isEmblem(id) || e.getValue() == null) {
                continue;
            }
            var info = ItemData.getEquipInfoById(id);
            if (info == null || info.getrLevel() != 100) {
                continue;
            }
            byName.merge(e.getValue(), id, Math::min);
            if (info.getReqSpecJob() > 0 && info.getReqSpecJob() == group && (spec == 0 || id < spec)) {
                spec = id;
            }
        }
        if (spec > 0) {
            return spec;
        }
        String name;
        if (group == 31) {
            name = "Gold Demon Emblem";
        } else if (job / 1000 == 3) {
            name = "Gold Resistance Emblem";
        } else if (job / 1000 == 1 || group == 51) {
            name = "Gold Cygnus Emblem";
        } else if (group == 142) {
            name = "Gold Kinesis Emblem";
        } else if (group == 64) {
            name = "Gold Agent Emblem";
        } else if (group == 175) {
            name = "Gold Astro Emblem";
        } else {
            name = "Gold Maple Leaf Emblem";
        }
        Integer id = byName.get(name);
        return id != null ? id : byName.getOrDefault("Gold Maple Leaf Emblem", 0);
    }

    private static Equip make(SetBox box, int itemId) {
        Equip equip = ItemData.getEquipDeepCopy(itemId, false);
        if (equip == null) {
            return null;
        }
        enhance(equip, box.stars(), box.grade());
        if (box.days() > 0) {
            equip.setDateExpire(FileTime.fromDate(LocalDateTime.now().plusDays(box.days())));
        }
        return equip;
    }

    /** Stars and a potential grade ("Rare" ... "Legendary"), as a box description promises them */
    public static void enhance(Equip equip, int stars, String gradeName) {
        if (stars > 0) {
            equip.setChuc((short) stars);
        }
        int grade = switch (gradeName == null ? "" : gradeName) {
            case "Rare" -> ItemGrade.HiddenRare.getVal();
            case "Epic" -> ItemGrade.HiddenEpic.getVal();
            case "Unique" -> ItemGrade.HiddenUnique.getVal();
            case "Legendary" -> ItemGrade.HiddenLegendary.getVal();
            default -> 0;
        };
        if (grade > 0 && ItemConstants.canEquipHavePotential(equip)) {
            equip.setHiddenOptionBase((short) grade, 100);
            equip.releaseOptions(false);
        }
    }

    /**
     * Opens the box: job/random pick the weapon themselves, choose takes the player's pick (an id from candidates).
     *
     * @return null if done, else why not
     */
    public static String open(Char chr, Item box, SetBox setBox, int chosenId) {
        if (box == null || box.getQuantity() <= 0) {
            return "This box can't be opened.";
        }
        List<Integer> cands = candidates(chr, setBox);
        if (cands.isEmpty()) {
            return "Your class can't use anything from this box.";
        }
        if ("pieces".equals(setBox.part())) {
            return openPieces(chr, box, setBox, cands);
        }
        int itemId = setBox.isChoice() ? chosenId
                : "random".equals(setBox.mode()) ? Util.getRandomFromCollection(cands) : cands.get(0);
        if (!cands.contains(itemId)) {
            return "That isn't one of the choices.";
        }
        Equip equip = make(setBox, itemId);
        if (equip == null) {
            return "This box can't be opened right now.";
        }
        if (!chr.canHold(equip.deepCopy())) {
            return "Please make room in your Equip inventory first.";
        }
        chr.consumeItem(box, 1);
        chr.addItemToInventory(equip);
        String name = StringData.getItemStringById(itemId);
        chr.chatMessage(String.format("You received %s.", name != null ? name : String.valueOf(itemId)));
        return null;
    }

    private static String openPieces(Char chr, Item box, SetBox setBox, List<Integer> ids) {
        List<Equip> equips = new ArrayList<>();
        List<Item> others = new ArrayList<>();
        for (int id : ids) {
            if (!ItemConstants.isEquip(id)) {
                Item other = ItemData.getItemDeepCopy(id);
                if (other == null || !chr.canHold(other.deepCopy())) {
                    return "Please make room in your inventory first.";
                }
                others.add(other);
                continue;
            }
            Equip e = make(setBox, id);
            if (e == null) {
                return "This box can't be opened right now.";
            }
            equips.add(e);
        }
        if (chr.getEquipInventory().getEmptySlots() < equips.size()) {
            return String.format("Please make %d slots of room in your Equip inventory first.", equips.size());
        }
        chr.consumeItem(box, 1);
        List<Item> all = new ArrayList<>(equips);
        all.addAll(others);
        for (Item e : all) {
            chr.addItemToInventory(e);
            String name = StringData.getItemStringById(e.getItemId());
            chr.chatMessage(String.format("You received %s.", name != null ? name : String.valueOf(e.getItemId())));
        }
        return null;
    }

    /** For the selection script */
    public static String openChoice(Char chr, int boxId, int itemId) {
        SetBox sb = get(boxId);
        Item item = chr.getConsumeInventory().getItemByItemID(boxId);
        if (item == null) {
            item = chr.getCashInventory().getItemByItemID(boxId);
        }
        return sb == null ? "This box can't be opened." : open(chr, item, sb, itemId);
    }
}
