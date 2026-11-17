package net.swordie.ms.world;

import java.util.function.Supplier;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.Server;
import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.life.drop.Drop;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.npc.Npc;
import net.swordie.ms.loaders.NpcData;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Foothold;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Event NPC placements, monster and item hooks shared by the event content. The seasonal calendar is gone (no
 * seasonal event is ever on), so the Halloween, Christmas and holiday content stays dormant; placements without an
 * event (the Secret Agent HQ, the Afterlands, story NPCs) are always there.
 */
public final class SeasonalEvents {
    private static final int CANDLE_CHEST_KEY = 4009379;

    private static final Logger log = LogManager.getLogger(SeasonalEvents.class);
    public static final String HALLOWEEN = "halloween";
    public static final String CHRISTMAS = "christmas";

    /** One event's yearly window. */
    public record Season(String id, String name, MonthDay start, MonthDay end, MonthDay holiday, boolean enabled) {
        boolean contains(MonthDay d) {
            if (!start.isAfter(end)) {
                return !d.isBefore(start) && !d.isAfter(end);
            }
            return !d.isBefore(start) || !d.isAfter(end); // wraps around the new year
        }
    }

    /** An NPC that stands somewhere while its event is on (event null: always). */
    public record Placement(String event, int fieldId, int npcId, int x, int y, boolean flip) {
    }

    /** resources/data/seasonal-placements.tsv: event (empty: all year), map, npc, x, y, flip */
    private static final Supplier<List<Placement>> PLACEMENTS = DataTable.cached("seasonal-placements", t ->
            t.rows().stream().map(r -> new Placement(r.has("event") ? r.str("event").trim() : null, r.i("map"),
                    r.i("npc"), r.i("x"), r.i("y"), r.b("flip"))).toList());

    /** NPCs that stand somewhere while their event is on */
    public static List<Placement> placements() {
        return PLACEMENTS.get();
    }

    /** The NPCs this class spawned: field -> placement -> the NPC's object id. */
    private static final Map<Field, Map<Placement, Integer>> spawned = Collections.synchronizedMap(new WeakHashMap<>());

    private SeasonalEvents() {
    }

    public static Season get(String id) {
        return null;
    }

    /** @return whether the event is on (only year-round content, event null) */
    public static boolean isActive(String id) {
        return id == null;
    }

    public static Set<String> activeIds() {
        return Set.of();
    }

    public static String windowText(String id) {
        return "";
    }

    public static boolean overridesQuestDates(int questId) {
        return false;
    }

    /** Every minute (Server): timed items that turn into another item. */
    public static void tick() {
        try {
            ExpiryReplacements.sweepOnline();
        } catch (Exception e) {
            log.error("SeasonalEvents: timed item replacements", e);
        }
    }

    // ----------------------------------------------------------------------------------------------- event NPCs

    /** Channel#createAndReturnNewField: puts the event NPCs of a map that was just loaded in place. */
    public static void onFieldCreated(Field field) {
        try {
            syncField(field);
        } catch (Exception e) {
            log.error("SeasonalEvents: NPCs of map " + field.getId(), e);
        }
    }

    private static void syncField(Field field) {
        for (Placement p : placements()) {
            if (p.fieldId() != field.getId()) {
                continue;
            }
            Map<Placement, Integer> mine = spawned.computeIfAbsent(field, f -> new ConcurrentHashMap<>());
            Integer objId = mine.get(p);
            boolean there = objId != null && field.getLifeByObjectID(objId) instanceof Npc;
            boolean wanted = isActive(p.event());
            if (wanted && !there) {
                Npc npc = spawnNpc(field, p);
                if (npc != null) {
                    mine.put(p, npc.getObjectId());
                }
            } else if (!wanted && there) {
                field.removeLife(objId, false);
                mine.remove(p);
            }
        }
    }

    private static Npc spawnNpc(Field field, Placement p) {
        if (p.event() == null && field.getLifeByTemplateId(p.npcId()) instanceof Npc
                && p.fieldId() != SecretAgentEvent.HQ) {
            return null; // already in the map data
        }
        Npc npc = NpcData.getNpcDeepCopyById(p.npcId());
        if (npc == null) {
            log.warn("SeasonalEvents: NPC " + p.npcId() + " doesn't exist");
            return null;
        }
        npc.setPosition(new Position(p.x(), p.y()));
        npc.setCy(p.y());
        npc.setRx0(p.x() + 50);
        npc.setRx1(p.x() - 50);
        npc.setFlip(p.flip());
        Foothold fh = field.findFootHoldBelow(new Position(p.x(), p.y() - 2));
        if (fh != null) {
            npc.setFh(fh.getId());
        }
        npc.setNotRespawnable(true);
        field.spawnLife(npc, null);
        return npc;
    }

    // -------------------------------------------------------------------------------------------- monster hooks

    /** Mob death (next to SwordieCoinRewards#onMobKilled). */
    public static void onMobKilled(Mob mob) {
        if (mob == null || mob.getField() == null) {
            return;
        }
        try {
            if (isActive(HALLOWEEN)) {
                HalloweenEvent.onMobKilled(mob);
            }
            if (isActive(CHRISTMAS)) {
                ChristmasEvent.onMobKilled(mob);
            }
            HolidayEvents.onMobKilled(mob);
            AfterlandsEvent.onMobKilled(mob);
            SecretAgentEvent.onMobKilled(mob);
        } catch (Exception e) {
            log.error("SeasonalEvents: kill of " + mob.getTemplateId(), e);
        }
    }

    /** EliteFieldModule#spawnEliteBoss: at Halloween, an Elite Pumpkin Zombie sometimes comes instead. */
    public static int pickEliteBoss(int template) {
        if (isActive(HALLOWEEN) && net.swordie.ms.util.Util.succeedProp(net.swordie.ms.constants.GameConstants.HALLOWEEN_ELITE_PUMPKIN_CHANCE)) {
            return HalloweenEvent.ELITE_PUMPKIN_ZOMBIE;
        }
        return template;
    }

    /** The HP an elite boss is based on (the Elite Pumpkin Zombie's own is tiny). */
    public static long eliteBossBaseHp(int template, long hp) {
        return template == HalloweenEvent.ELITE_PUMPKIN_ZOMBIE ? HalloweenEvent.ELITE_PUMPKIN_BASE_HP : hp;
    }

    // ----------------------------------------------------------------------------------------- items on reactors

    /**
     * Reactors triggered by an item dropped in front of them (Reactor.wz event type 100): reactor id -> {item id, left,
     * top, right, bottom} (the reactor's box, relative to it).
     */
    private static final Map<Integer, int[]> ITEM_REACTORS = Map.of(
            2292008, new int[]{CANDLE_CHEST_KEY, -43, -57, 43, 11} // Haunted Mansion: Locked Candle Chest <- Candle Chest Key; hardcode-ok
    );

    /** Events#onItemDropped: an item dropped on a reactor that wants it is used up and runs the reactor's script. */
    public static void onItemDropped(Char chr, Item item, Position pos, Drop drop) {
        if (chr == null || item == null || pos == null || drop == null || chr.getField() == null) {
            return;
        }
        Field field = chr.getField();
        for (var reactor : field.getReactors()) {
            int[] want = ITEM_REACTORS.get(reactor.getTemplateId());
            if (want == null || want[0] != item.getItemId()) {
                continue;
            }
            Position rp = reactor.getPosition();
            int dx = pos.getX() - rp.getX();
            int dy = pos.getY() - rp.getY();
            // a little slack: the drop lands on the floor below the player
            if (dx < want[1] - 30 || dx > want[3] + 30 || dy < want[2] - 60 || dy > want[4] + 60) {
                continue;
            }
            int objId = reactor.getObjectId();
            int templateId = reactor.getTemplateId();
            net.swordie.ms.handlers.executors.EventManager.addEvent(() -> {
                try {
                    if (field.getLifeByObjectID(drop.getObjectId()) != drop || !drop.tryClaim()) {
                        return; // somebody picked it up again
                    }
                    field.removeDrop(drop.getObjectId(), 0, false, -1);
                    String action = net.swordie.ms.loaders.ReactorData.getReactorInfoByID(templateId).getAction();
                    chr.getScriptManager().startScript(templateId, objId, action,
                            net.swordie.ms.scripts.ScriptType.Reactor);
                } catch (Exception e) {
                    log.error("SeasonalEvents: item on reactor " + templateId, e);
                }
            }, 1200);
            return;
        }
    }

    // ------------------------------------------------------------------------------------------------- helpers

    /** Opens an NPC's chat a moment later (a quest script's chat has to close first). */
    public static void openNpcLater(Char chr, int npcId) {
        net.swordie.ms.handlers.executors.EventManager.addEvent(() -> {
            try {
                if (chr.isOnline() && !chr.isTalkingToNpc()) {
                    chr.setTalkingToNpc(true);
                    chr.getScriptManager().openNpc(npcId);
                }
            } catch (Exception e) {
                chr.chatMessage(ChatType.Notice2, "That didn't open. Please talk to the NPC again.");
            }
        }, 200);
    }

    /** @return the EXP a quest gives: the given percent of what the character's level needs */
    public static long expPercent(Char chr, double percent) {
        int lv = Math.max(1, Math.min(chr.getLevel(), net.swordie.ms.constants.GameConstants.MAX_CHAR_LEVEL - 1));
        return Math.max(1, (long) (net.swordie.ms.constants.GameConstants.charExp[lv] * percent / 100D));
    }

    /** A monster "drop" that goes straight into the inventory, with the usual pick-up message. */
    public static boolean grantFind(Char chr, int itemId, int count) {
        if (!chr.canHold(itemId, count)) {
            return false;
        }
        chr.addItemToInventory(itemId, count);
        Item shown = net.swordie.ms.loaders.ItemData.getItemDeepCopy(itemId);
        if (shown != null) {
            chr.write(net.swordie.ms.connection.packet.WvsContext.message(
                    net.swordie.ms.connection.packet.model.MessagePacket.dropPickupMessage(shown, (short) count)));
        }
        return true;
    }

    /** Gives an item (equips one by one, with their stats). Check canHold first. */
    public static void giveItem(Char chr, int itemId, int count) {
        if (net.swordie.ms.constants.ItemConstants.isEquip(itemId)) {
            for (int i = 0; i < count; i++) {
                chr.addItemToInventory(net.swordie.ms.loaders.ItemData.getItemDeepCopy(itemId));
            }
        } else {
            chr.addItemToInventory(itemId, count);
        }
    }

    /** Swordie Coins to the Etc inventory, or the gift box when it's full. Nothing while coins are off. */
    public static void giveCoins(Char chr, int amount, String reason) {
        if (!net.swordie.ms.client.character.SwordieCoinRewards.COINS_ON) {
            return;
        }
        int coin = net.swordie.ms.constants.ItemConstants.SWORDIE_COIN;
        if (chr.canHold(coin, amount)) {
            chr.addItemToInventory(coin, amount);
            chr.chatMessage(String.format("You received %d Swordie Coins (%s).", amount, reason));
        } else {
            chr.addFirstEnterReward(new net.swordie.ms.client.character.FirstEnterReward(chr.getId(), coin, amount,
                    net.swordie.ms.enums.FirstEnterRewardType.GameItem,
                    String.format("%d Swordie Coins (%s).", amount, reason)));
            chr.checkFirstEnterReward();
            chr.chatMessage(String.format("Your Etc inventory is full, so your %d Swordie Coins (%s) were sent to "
                    + "your gift box.", amount, reason));
        }
    }
}
