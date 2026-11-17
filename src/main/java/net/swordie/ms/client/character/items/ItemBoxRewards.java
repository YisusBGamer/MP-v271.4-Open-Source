package net.swordie.ms.client.character.items;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.StringData;
import net.swordie.ms.util.FileTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Boxes, coupons and packages the server has no script for, whose contents are listed in their String.wz description
 * ("Double-click to receive #cRed Cube x12#", "select and obtain one of the following items: - A - B"). The table is
 * resources/item-box-rewards.tsv, made from the v265 data: item, mode (all = everything, choose = the player picks
 * one, gender = the (M)/(F) pairs, the character's one), rewards "item*count*days" (meso*n, mp*n for Maple Points).
 */
public class ItemBoxRewards {
    private static final Logger log = LogManager.getLogger();
    private static final String FILE = ServerConstants.RESOURCES_DIR + "/item-box-rewards.tsv";

    public record Reward(int itemId, int count, int days, String kind, double weight, int stars, String grade) {
        public String describe() {
            return switch (kind) {
                case "meso" -> String.format("%,d mesos", count);
                case "mp" -> String.format("%,d Maple Points", count);
                default -> String.format("#v%d# #t%d#%s%s", itemId, itemId, count > 1 ? " x" + count : "",
                        days > 0 ? " (" + days + " days)" : "");
            };
        }
    }

    public record Box(int itemId, String mode, List<Reward> rewards) {
        public boolean isChoice() {
            return "choose".equals(mode);
        }

        /** random = one reward by its weight (the odds the description gives, else equal) */
        public boolean isRandom() {
            return "random".equals(mode);
        }
    }

    private static volatile Map<Integer, Box> boxes;

    private static Map<Integer, Box> boxes() {
        if (boxes == null) {
            synchronized (ItemBoxRewards.class) {
                if (boxes == null) {
                    boxes = load();
                }
            }
        }
        return boxes;
    }

    private static Map<Integer, Box> load() {
        Map<Integer, Box> map = new HashMap<>();
        try {
            for (String line : Files.readAllLines(Path.of(FILE), StandardCharsets.UTF_8)) {
                if (line.isBlank() || line.startsWith("#")) {
                    continue;
                }
                String[] cols = line.split("\t");
                if (cols.length < 3) {
                    continue;
                }
                List<Reward> rewards = new ArrayList<>();
                for (String r : cols[2].split(";")) {
                    String[] p = r.split("\\*");
                    String kind = p[0].equals("meso") || p[0].equals("mp") ? p[0] : "item";
                    int id = kind.equals("item") ? Integer.parseInt(p[0]) : 0;
                    double weight = p.length > 3 ? Double.parseDouble(p[3]) : 1;
                    int stars = p.length > 4 ? Integer.parseInt(p[4]) : 0; // "15-Star Unique Dominator Pendant"
                    String grade = p.length > 5 ? p[5] : "";
                    rewards.add(new Reward(id, Integer.parseInt(p[1]), Integer.parseInt(p[2]), kind, weight, stars,
                            grade));
                }
                int id = Integer.parseInt(cols[0]);
                map.put(id, new Box(id, cols[1], rewards));
            }
        } catch (IOException | RuntimeException e) {
            log.error("Could not read " + FILE, e);
        }
        return map;
    }

    public static Box get(int itemId) {
        return boxes().get(itemId);
    }

    /** One reward of a random box, by weight */
    public static Reward pick(List<Reward> rewards) {
        double total = rewards.stream().mapToDouble(Reward::weight).sum();
        double roll = Math.random() * total;
        for (Reward r : rewards) {
            roll -= r.weight();
            if (roll < 0) {
                return r;
            }
        }
        return rewards.get(rewards.size() - 1);
    }

    /** The rewards a box gives this character: for gender boxes, the ones for their gender (or unisex). */
    public static List<Reward> rewardsFor(Char chr, Box box) {
        if (!"gender".equals(box.mode())) {
            return box.rewards();
        }
        int gender = chr.getAvatarData().getAvatarLook().getGender();
        List<Reward> list = new ArrayList<>();
        for (Reward r : box.rewards()) {
            int g = ItemConstants.isEquip(r.itemId()) ? (r.itemId() / 1000) % 10 : 2;
            if (g == 2 || g == gender) {
                list.add(r);
            }
        }
        return list;
    }

    private static List<Item> toItems(List<Reward> rewards) {
        List<Item> items = new ArrayList<>();
        for (Reward r : rewards) {
            if (!r.kind().equals("item")) {
                continue;
            }
            int copies = ItemConstants.isEquip(r.itemId()) ? Math.max(1, r.count()) : 1;
            for (int i = 0; i < copies; i++) {
                Item item = ItemData.getItemDeepCopy(r.itemId());
                if (item == null) {
                    return null;
                }
                if (!ItemConstants.isEquip(r.itemId())) {
                    item.setQuantity(Math.max(1, r.count()));
                }
                if (r.days() > 0) {
                    item.setDateExpire(FileTime.fromDate(LocalDateTime.now().plusDays(r.days())));
                }
                if (item instanceof Equip equip) {
                    if (ItemConstants.isOzRing(r.itemId()) || r.itemId() == 1113329) {
                        // the stars column is the ring's level
                        if (r.stars() > 0) {
                            equip.setLevel((short) r.stars());
                        }
                    } else {
                        SetBoxes.enhance(equip, r.stars(), r.grade());
                    }
                }
                items.add(item);
            }
        }
        return items;
    }

    /** For the selection script: opens the character's box of that id with the reward at that index of the list. */
    public static String openChoice(Char chr, int boxId, int index) {
        Box box = get(boxId);
        if (box == null) {
            return "This box can't be opened.";
        }
        List<Reward> rewards = rewardsFor(chr, box);
        if (index < 0 || index >= rewards.size()) {
            return "That isn't one of the choices.";
        }
        Item item = chr.getConsumeInventory().getItemByItemID(boxId);
        if (item == null) {
            item = chr.getCashInventory().getItemByItemID(boxId);
        }
        return open(chr, item, List.of(rewards.get(index)));
    }

    /**
     * Uses up the box and gives the rewards.
     *
     * @return null if done, else why not (nothing is used up then)
     */
    public static String open(Char chr, Item box, List<Reward> rewards) {
        if (box == null || box.getQuantity() <= 0 || rewards.isEmpty()) {
            return "This box can't be opened.";
        }
        List<Item> items = toItems(rewards);
        if (items == null) {
            log.warn("Box " + box.getItemId() + " names an item the server doesn't have.");
            return "This box can't be opened right now.";
        }
        long mesos = rewards.stream().filter(r -> r.kind().equals("meso")).mapToLong(Reward::count).sum();
        if (!chr.canAddMoney(mesos)) {
            return "You can't hold any more mesos.";
        }
        if (!chr.canHold(new ArrayList<>(items))) {
            return "Please make room in your inventory first.";
        }
        chr.consumeItem(box, 1);
        for (Item item : items) {
            chr.addItemToInventory(item);
        }
        if (mesos > 0) {
            chr.addMoney(mesos);
        }
        rewards.stream().filter(r -> r.kind().equals("mp")).forEach(r -> chr.addNx(r.count()));
        for (Reward r : rewards) {
            String what = r.kind().equals("item") ? StringData.getItemStringById(r.itemId()) : null;
            chr.chatMessage(what != null
                    ? String.format("You received %s%s.", what, r.count() > 1 ? " x" + r.count() : "")
                    : String.format("You received %s.", r.describe()));
        }
        return null;
    }
}
