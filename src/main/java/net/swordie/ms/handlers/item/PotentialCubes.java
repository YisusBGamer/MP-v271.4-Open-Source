package net.swordie.ms.handlers.item;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.ProgressionLog;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.items.ItemOption;
import net.swordie.ms.connection.packet.PotentialRpc;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.enums.InvType;
import net.swordie.ms.enums.ItemGrade;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cubes that roll lines their own way: Cube of Equality (every line at the item's rank), Unicube and Bonus Unicube
 * (one random line rerolled at its own rank) and Violet Cube (twice the item's lines rolled, the player keeps as many
 * as the item has).
 */
public final class PotentialCubes {
    private static final Logger log = LogManager.getLogger();

    public static final int EQUALITY_CUBE = 5062021;
    public static final int UNICUBE = 5062026;
    public static final int BONUS_UNICUBE = 5062523;
    public static final int BONUS_MYSTICAL_CUBE = 2730000;

    /** The cubes the odds page lists, in shop order. */
    private static final int[] ODDS_CUBES = {ItemConstants.GLOWING_CUBE, ItemConstants.BRIGHT_CUBE, EQUALITY_CUBE,
            ItemConstants.VIOLET_CUBE, UNICUBE, ItemConstants.CRAFTSMAN_CUBE, ItemConstants.MEISTER_CUBE,
            ItemConstants.OCCULT_CUBE, ItemConstants.BONUS_GLOWING_CUBE, ItemConstants.BONUS_BRIGHT_CUBE, BONUS_UNICUBE,
            BONUS_MYSTICAL_CUBE};

    // Enhance window calls (client 0x1433df8a0 picks them by the cube's family table)
    public static final int RPC_VIOLET_USE = 0x21A77572; // UserHexaCube::OnUse: B mask, [1: 2 slot] [2: 2 equip]
    public static final int RPC_UNICUBE_USE = 0x269C20DE; // UserUniCube::OnUse: B mask, [1: 4 slot] [2: 4 equip] [4: 4]
    public static final int RPC_BONUS_UNICUBE_USE = 0xE477FEDA; // UserAdditionalUniCube::OnUse, as the Unicube

    /** The cubes whose rank-up odds these use (GMS lists the same odds for them). */
    private static final int EQUALITY_RANK_UP_AS = ItemConstants.RED_CUBE;
    private static final int VIOLET_RANK_UP_AS = ItemConstants.BLACK_CUBE;

    private static final int RESULT_OK = 0;
    private static final int RESULT_FAILED = 1;

    /** A Violet Cube roll waiting for the player's pick: the item, its new rank and the lines to pick from. */
    private record VioletChoice(Equip equip, short hiddenGrade, int lines, List<Integer> options) {
    }

    private static final Map<Integer, VioletChoice> violetChoices = new ConcurrentHashMap<>();

    private PotentialCubes() {
    }

    /** The cube family uses ItemHandler.handlePotentialRpcRequest reads (Glowing, Bright, their bonus and Use-tab ones). */
    private static final Set<Integer> FAMILY_USES = Set.of(0xC96EBA56, 0xFBE5AFFE, 0xFDA934E3, 0xD1EE4340, 0xC4BADADE,
            0xC383BFA5, 0xC516F80F, 0x46E382FC);

    public static boolean isFamilyUse(int method) {
        return FAMILY_USES.contains(method);
    }

    public static boolean isOwnRpc(int method) {
        return method == RPC_VIOLET_USE || method == RPC_UNICUBE_USE || method == RPC_BONUS_UNICUBE_USE;
    }

    /** Whether a call that isn't a cube use can be the Violet Cube pick (a choice is waiting). */
    public static boolean hasVioletChoice(Char chr) {
        return violetChoices.containsKey(chr.getId());
    }

    // ------------------------------------------------------------------------------------------------ Equality

    /** A copy of the equip with every line at the item's own rank (the rank may go up first). */
    static Equip rollEquality(Equip equip) {
        Equip result = equip.deepCopy();
        short hidden = ItemGrade.getHiddenGradeByVal(equip.getBaseGrade()).getVal();
        if (hidden >= ItemGrade.HiddenRare.getVal() && hidden < ItemGrade.HiddenLegendary.getVal()
                && Util.succeedProp(ItemConstants.getTierUpChance(EQUALITY_RANK_UP_AS, hidden), 1_000_000)) {
            hidden++;
        }
        result.setHiddenOptionBase(hidden, ItemConstants.getCubeThirdLineChance());
        for (int i = 0; i < 3; i++) {
            if (result.getOptionBase(i) < 0) {
                // line 0 is always of the item's rank
                result.setOptionBase(i, Util.getRandomFromCollection(
                        ItemConstants.getWeightedOptionsByEquip(result, false, 0, EQUALITY_CUBE)));
            }
        }
        return result;
    }

    // ------------------------------------------------------------------------------------------------ calls

    /** An Enhance window cube call of this class: the bytes after the field mask, the call id is the last int. */
    public static void onRpc(Char chr, int method, int mask, byte[] b, int callId) {
        try {
            switch (method) {
                case RPC_UNICUBE_USE, RPC_BONUS_UNICUBE_USE -> useUnicube(chr, method == RPC_BONUS_UNICUBE_USE,
                        mask, b, callId);
                case RPC_VIOLET_USE -> useViolet(chr, mask, b, callId);
                default -> chr.write(PotentialRpc.uniCubeResult(callId, RESULT_FAILED));
            }
        } catch (RuntimeException e) {
            log.error(String.format("Cube call %08X failed for character %d", method, chr.getId()), e);
            chr.write(PotentialRpc.uniCubeResult(callId, RESULT_FAILED));
        }
        chr.dispose();
    }

    private static Equip equipAt(Char chr, int ePos) {
        Item item = chr.getInventoryByType(ePos < 0 ? InvType.EQUIPPED : InvType.EQUIP).getItemBySlot((short) ePos);
        return item instanceof Equip e ? e : null;
    }

    private static Item cubeAt(Char chr, int slot, int cubeId) {
        for (InvType inv : new InvType[]{InvType.CASH, InvType.CONSUME}) {
            Item item = chr.getInventoryByType(inv).getItemBySlot((short) slot);
            if (item != null && item.getItemId() == cubeId) {
                return item;
            }
        }
        return null;
    }

    /** The cube fee (same as the other cubes); false when the player can't pay it. */
    private static boolean payFee(Char chr, Equip equip) {
        long fee = ItemHandlerModule.cubeMesoFee(chr, equip);
        if (fee <= 0) {
            return true;
        }
        if (chr.getMoney() < fee) {
            chr.chatMessage(ChatType.SystemNotice, String.format("You need %,d mesos to use a cube on this item.", fee));
            return false;
        }
        chr.deductMoney(fee, ProgressionLog.Sink.CUBES);
        return true;
    }

    private static boolean cubeable(Equip equip, boolean bonus) {
        int grade = bonus ? equip.getBonusGrade() : equip.getBaseGrade();
        var info = equip.getInfo();
        return grade >= ItemGrade.Rare.getVal() && grade <= ItemGrade.Legendary.getVal()
                && (info == null || !info.isFixedPotential());
    }

    // ------------------------------------------------------------------------------------------------ Unicube

    private static void useUnicube(Char chr, boolean bonus, int mask, byte[] b, int callId) {
        int[] off = {0};
        int slot = (mask & 1) != 0 ? readInt(b, off) : -1;
        int ePos = (mask & 2) != 0 ? readInt(b, off) : 0;
        int cubeId = bonus ? BONUS_UNICUBE : UNICUBE;
        Item cube = cubeAt(chr, slot, cubeId);
        Equip equip = equipAt(chr, ePos);
        if (cube == null || equip == null || !cubeable(equip, bonus)) {
            chr.chatMessage(ChatType.SystemNotice, "This cube can't be used on that item.");
            chr.write(PotentialRpc.uniCubeResult(callId, RESULT_FAILED));
            return;
        }
        if (!payFee(chr, equip)) {
            chr.write(PotentialRpc.uniCubeResult(callId, RESULT_FAILED));
            return;
        }
        int line = rerollOneLine(equip, bonus);
        if (line < 0) {
            chr.write(PotentialRpc.uniCubeResult(callId, RESULT_FAILED));
            return;
        }
        chr.consumeItem(cube, 1);
        applyToChar(chr, equip);
        chr.write(UserLocal.cubeResultNotice(cubeId, equip.getItemId()));
        chr.chatMessage(ChatType.SystemNotice, String.format("Line %d of the %s was set anew.", line + 1,
                bonus ? "Bonus Potential" : "Potential"));
        chr.write(PotentialRpc.uniCubeResult(callId, RESULT_OK));
    }

    /** Rerolls one random line at the rank it has. @return the line, or -1 when the item has none */
    static int rerollOneLine(Equip equip, boolean bonus) {
        List<Integer> lines = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (equip.getOption(i, bonus) > 0) {
                lines.add(i);
            }
        }
        if (lines.isEmpty()) {
            return -1;
        }
        int line = Util.getRandomFromCollection(lines);
        ItemGrade tier = ItemGrade.getGradeByOption(equip.getOption(line, bonus));
        int option = weightedOption(equip, bonus, tier);
        if (option <= 0) {
            return -1;
        }
        equip.setOption(line, option, bonus);
        return line;
    }

    private static int weightedOption(Equip equip, boolean bonus, ItemGrade tier) {
        List<ItemOption> options = ItemConstants.getOptionsByEquip(equip, bonus, tier);
        int total = 0;
        for (ItemOption io : options) {
            total += Math.max(0, io.getWeight());
        }
        if (total <= 0) {
            return 0;
        }
        int roll = Util.getRandom(total - 1);
        for (ItemOption io : options) {
            roll -= Math.max(0, io.getWeight());
            if (roll < 0) {
                return io.getId();
            }
        }
        return 0;
    }

    // ------------------------------------------------------------------------------------------------ Violet

    private static void useViolet(Char chr, int mask, byte[] b, int callId) {
        int[] off = {0};
        int slot = (mask & 1) != 0 ? readShort(b, off) : -1;
        int ePos = (mask & 2) != 0 ? (short) readShort(b, off) : 0;
        Equip equip = equipAt(chr, ePos);
        VioletChoice waiting = violetChoices.get(chr.getId());
        if (waiting != null && waiting.equip() == equip) {
            // the last roll for this item wasn't picked yet: show it again instead of using another cube
            chr.write(PotentialRpc.violetCubeResult(callId, RESULT_OK, waiting.options()));
            return;
        }
        Item cube = cubeAt(chr, slot, ItemConstants.VIOLET_CUBE);
        if (cube == null || equip == null || !cubeable(equip, false)) {
            chr.chatMessage(ChatType.SystemNotice, "This cube can't be used on that item.");
            chr.write(PotentialRpc.violetCubeResult(callId, RESULT_FAILED, null));
            return;
        }
        if (!payFee(chr, equip)) {
            chr.write(PotentialRpc.violetCubeResult(callId, RESULT_FAILED, null));
            return;
        }
        VioletChoice choice = rollViolet(equip);
        violetChoices.put(chr.getId(), choice);
        chr.consumeItem(cube, 1);
        chr.write(UserLocal.cubeResultNotice(ItemConstants.VIOLET_CUBE, equip.getItemId()));
        chr.write(PotentialRpc.violetCubeResult(callId, RESULT_OK, choice.options()));
        log.info(String.format("Violet Cube: character %d rolled %s for item %d", chr.getId(), choice.options(),
                equip.getItemId()));
    }

    /** Twice the item's line count of lines at the (maybe raised) rank, each slot rolled like that line. */
    static VioletChoice rollViolet(Equip equip) {
        int lines = equip.getOptionBase(2) != 0 ? 3 : 2;
        short hidden = ItemGrade.getHiddenGradeByVal(equip.getBaseGrade()).getVal();
        if (hidden >= ItemGrade.HiddenRare.getVal() && hidden < ItemGrade.HiddenLegendary.getVal()
                && Util.succeedProp(ItemConstants.getTierUpChance(VIOLET_RANK_UP_AS, hidden), 1_000_000)) {
            hidden++;
        }
        Equip probe = equip.deepCopy();
        probe.setHiddenOptionBase(hidden, 0);
        ItemGrade grade = ItemGrade.getGradeByVal(probe.getBaseGrade());
        List<Integer> options = new ArrayList<>();
        for (int i = 0; i < lines * 2; i++) {
            ItemGrade tier = ItemConstants.getLineTier(i % lines, gradeOf(hidden), VIOLET_RANK_UP_AS, false);
            int option = weightedOption(probe, false, tier == null ? grade : tier);
            options.add(option);
        }
        return new VioletChoice(equip, hidden, lines, List.copyOf(options));
    }

    /** The shown rank of a hidden one (HiddenEpic -> Epic). */
    private static ItemGrade gradeOf(short hidden) {
        return ItemGrade.getGradeByVal(ItemGrade.Rare.getVal() + hidden - ItemGrade.HiddenRare.getVal());
    }

    /**
     * The player's Violet Cube pick, from a call that isn't one of the cube uses while a choice waits. Read as the
     * use reply's line list: B mask, [1: varint n, n x 4 picked index]. Every call is logged until the client's
     * select layout is confirmed.
     */
    public static void onVioletPick(Char chr, int method, int mask, byte[] b, int callId) {
        VioletChoice choice = violetChoices.get(chr.getId());
        log.info(String.format("Violet Cube pick call %08X from character %d (mask %d): %s", method, chr.getId(), mask,
                Util.readableByteArray(b)));
        if (choice == null) {
            chr.write(PotentialRpc.uniCubeResult(callId, RESULT_FAILED));
            chr.dispose();
            return;
        }
        List<Integer> picks = new ArrayList<>();
        if ((mask & 1) != 0) {
            int[] off = {0};
            int n = PotentialRpc.readVarint(b, off);
            for (int i = 0; i < n && off[0] + 4 <= b.length - 4; i++) {
                picks.add(readInt(b, off));
            }
        }
        String why = applyViolet(chr, choice, picks);
        if (why != null) {
            chr.chatMessage(ChatType.SystemNotice, why);
            chr.write(PotentialRpc.uniCubeResult(callId, RESULT_FAILED));
        } else {
            chr.write(PotentialRpc.uniCubeResult(callId, RESULT_OK));
        }
        chr.dispose();
    }

    /** Puts the picked lines on the item, in the order picked. @return why not, or null when done */
    static String applyViolet(Char chr, VioletChoice choice, List<Integer> picks) {
        Set<Integer> seen = new HashSet<>(picks);
        if (picks.size() != choice.lines() || seen.size() != picks.size()) {
            return "Pick " + choice.lines() + " different lines.";
        }
        for (int p : picks) {
            if (p < 0 || p >= choice.options().size()) {
                return "Pick " + choice.lines() + " different lines.";
            }
        }
        // the first line keeps the item's rank: a pick of that rank goes first
        List<Integer> order = new ArrayList<>(picks);
        ItemGrade rank = gradeOf(choice.hiddenGrade());
        int prime = -1;
        for (int i = 0; i < order.size() && prime < 0; i++) {
            if (ItemGrade.getGradeByOption(choice.options().get(order.get(i))) == rank) {
                prime = i;
            }
        }
        if (prime < 0) {
            return "Pick at least one line of the item's rank.";
        }
        order.add(0, order.remove(prime));
        Equip equip = choice.equip();
        boolean stillThere = chr.getEquippedInventory().getItems().contains(equip)
                || chr.getEquipInventory().getItems().contains(equip);
        violetChoices.remove(chr.getId(), choice);
        if (!stillThere) {
            return "The item is no longer in your inventory.";
        }
        for (int i = 0; i < 3; i++) {
            equip.setOptionBase(i, i < order.size() ? choice.options().get(order.get(i)) : 0);
        }
        applyToChar(chr, equip);
        return null;
    }

    // ------------------------------------------------------------------------------------------------ odds

    /** The odds page (@menu > Gear): each cube's rank-up odds and how often its 2nd and 3rd lines are of the rank. */
    public static String oddsText() {
        StringBuilder sb = new StringBuilder("#e#bCube odds#k#n (with this server's event rates)\r\n");
        for (int cubeId : ODDS_CUBES) {
            sb.append("\r\n#v").append(cubeId).append("# #b#t").append(cubeId).append("##k\r\n");
            if (cubeId == UNICUBE || cubeId == BONUS_UNICUBE) {
                sb.append("One random line is set anew at the rank it has. No rank up.\r\n");
                continue;
            }
            int asCube = cubeId == EQUALITY_CUBE ? EQUALITY_RANK_UP_AS : cubeId == ItemConstants.VIOLET_CUBE
                    ? VIOLET_RANK_UP_AS : cubeId;
            String[] steps = {"Rare > Epic", "Epic > Unique", "Unique > Legendary"};
            List<String> ranks = new ArrayList<>();
            for (int g = ItemGrade.HiddenRare.getVal(); g < ItemGrade.HiddenLegendary.getVal(); g++) {
                int chance = ItemConstants.getTierUpChance(asCube, g);
                if (chance > 0) {
                    ranks.add(steps[g - ItemGrade.HiddenRare.getVal()] + " " + percent(chance));
                }
            }
            sb.append("Rank up: ").append(ranks.isEmpty() ? "none" : String.join(", ", ranks)).append("\r\n");
            if (cubeId == EQUALITY_CUBE) {
                sb.append("Every line is of the item's rank.\r\n");
                continue;
            }
            boolean bonus = cubeId == ItemConstants.BONUS_GLOWING_CUBE || cubeId == ItemConstants.BONUS_BRIGHT_CUBE
                    || cubeId == BONUS_MYSTICAL_CUBE;
            int legendary = ItemGrade.Legendary.getVal();
            sb.append("Line of the item's rank (Legendary): 2nd ")
                    .append(percent(ItemConstants.getPrimeLineChance(asCube, bonus, 1, legendary)))
                    .append(", 3rd ").append(percent(ItemConstants.getPrimeLineChance(asCube, bonus, 2, legendary)))
                    .append("\r\n");
            if (cubeId == ItemConstants.VIOLET_CUBE) {
                sb.append("Shows twice the item's lines; you keep as many as it has.\r\n");
            }
        }
        return sb.toString();
    }

    /** A per-million chance as a percent. */
    private static String percent(int perMillion) {
        return String.format("%.2f%%", perMillion / 10_000.0);
    }

    // ------------------------------------------------------------------------------------------------ shared

    /** Shows the changed lines, on Zero's other weapon too. */
    private static void applyToChar(Char chr, Equip equip) {
        if (JobConstants.isZero(chr.getJob()) && ItemConstants.isLongOrBigSword(equip.getItemId())
                && chr.getEquippedInventory().getItems().contains(equip)) {
            int otherPos = Math.abs(equip.getBagIndex()) == 10 ? 11 : 10;
            if (chr.getEquippedInventory().getItemBySlot(otherPos) instanceof Equip other) {
                other.setOptions(new ArrayList<>(equip.getOptions()));
                other.updateToChar(chr);
            }
        }
        equip.updateToChar(chr);
        if (chr.getEquippedInventory().getItems().contains(equip)) {
            chr.getEquippedInventory().recalcBaseStats(chr);
            chr.initBaseStats();
        }
    }

    private static int readInt(byte[] b, int[] off) {
        if (off[0] + 4 > b.length) {
            return 0;
        }
        off[0] += 4;
        return PotentialRpc.readIntLE(b, off[0] - 4);
    }

    private static int readShort(byte[] b, int[] off) {
        if (off[0] + 2 > b.length) {
            return 0;
        }
        int o = off[0];
        off[0] += 2;
        return (b[o] & 0xFF) | (b[o + 1] & 0xFF) << 8;
    }
}
