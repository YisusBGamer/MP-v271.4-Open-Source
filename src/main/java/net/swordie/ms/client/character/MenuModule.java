package net.swordie.ms.client.character;

import net.swordie.ms.ServerSettings;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.trunk.TrunkDlg;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.constants.ServerRates;
import net.swordie.ms.enums.AccountType;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.enums.FieldOption;
import net.swordie.ms.enums.InvType;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.drop.DropInfo;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.DropData;
import net.swordie.ms.loaders.MobData;
import net.swordie.ms.loaders.StringData;
import net.swordie.ms.scripts.ScriptManagerImpl;
import net.swordie.ms.scripts.ScriptType;
import net.swordie.ms.util.FileTime;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @menu (scripts/npc/menu.py): one menu for everything this server adds - travel, shops, the character and gear
 * helpers, info and utilities. It replaced most of the separate player commands (@qm, @storage, ...); @gear, @upgrades,
 * @enhance and @sell open their helpers directly too, and @shop opens the Shops & trading page.
 * <p>
 * The helpers that are their own NPC scripts (@enhance, the gear guide, ...) can't start while the menu's NPC chat is
 * open, so the menu calls {@link #openAfterMenu(Char, String)} and closes: the feature opens a moment later. The small
 * helpers (stats, storage, drops, quests, ...) are here and answer inside the menu.
 * <p>
 * Also holds what the remaining commands share: the travel rules (@go and the menu's Travel) and @stuck's clean up.
 */
public final class MenuModule {
    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger();

    /** Who speaks in the menu (Maple Administrator), and the menu's script. */
    public static final int SPEAKER = 9010000;
    public static final String SCRIPT = "menu";
    /** The Free Market entrance. */
    public static final int FREE_MARKET = 910000000;
    /** NPCs that front the scripts the menu opens */
    private static final int EDEA = 3004412, PRINCESS_SAKUNO = 9130021, MISS_RICHFIELD = 9062008, CLOY = 1012005;
    private static final int LAWRY = 3004604, JUDGE_CASSANDRA = 9010024, FREDRICK = 9030000, ARI = 9100019;
    private static final int ARCHELLE = 1540945, SCROOGE = 9030100;
    /** Morass: Path to the Coral Forest, where "Unexpected Enemy (1)" starts */
    private static final int PATH_TO_CORAL_FOREST = 450006000;
    /** How long after the menu closes a feature opens (the menu's NPC chat must be gone first). */
    private static final long OPEN_DELAY_MS = 150;
    /** The lowest a stat can be lowered to (the same minimum a new character has). */
    public static final int MIN_ABILITY_STAT = 4;
    private static final int MAX_ABILITY_STAT = Short.MAX_VALUE;

    private static final Map<Integer, Long> lastTravel = new ConcurrentHashMap<>();

    private MenuModule() {
    }

    // ---------------------------------------------------------------------------------------------------------------
    // Opening the menu and its features
    // ---------------------------------------------------------------------------------------------------------------

    /** @return why the character can't open a menu/NPC chat now, or null if it can */
    private static String busyReason(Char chr) {
        if (chr == null || !chr.isOnline() || chr.getField() == null) {
            return "";
        }
        if (chr.getMiniRoom() != null || chr.getShop() != null || chr.isInTrunk()) {
            return "Close the trade, shop or storage first.";
        }
        if (chr.isTalkingToNpc() || chr.getScriptManager().isActive(ScriptType.Npc)) {
            return "Finish your conversation first. (If a chat window is stuck, type @stuck.)";
        }
        return null;
    }

    /** @return true (after telling the player why) when a menu or NPC chat can't open right now */
    public static boolean isBusy(Char chr) {
        String busy = busyReason(chr);
        if (busy != null && !busy.isEmpty()) {
            chr.chatMessage(busy);
        }
        return busy != null;
    }

    /**
     * Opens the menu straight on one of its pages (the shortcut commands: "next", "hunt", "bosses", ...; see menu.py),
     * with an optional argument (@whodrops' item).
     */
    public static void openPage(Char chr, String page, String arg) {
        if (!isBusy(chr)) {
            startMenu(chr, page, arg);
        }
    }

    /** Opens the menu (@menu, and the "Menu" entry of the Quick Move button). */
    public static void openMenu(Char chr) {
        open(chr, "menu");
    }

    /**
     * The menu calls this and then closes itself (sm.dispose()); the feature opens a moment later.
     */
    public static void openAfterMenu(Char chr, String feature) {
        EventManager.addEvent(() -> {
            try {
                open(chr, feature);
            } catch (Exception e) {
                log.error("Menu feature " + feature + " failed to open for character " + chr.getId(), e);
                chr.chatMessage("That didn't open. Please try again.");
            }
        }, OPEN_DELAY_MS);
    }

    /**
     * Opens one of the menu's features (each its own NPC script, or a window).
     */
    public static void open(Char chr, String feature) {
        String busy = busyReason(chr);
        if (busy != null) {
            if (!busy.isEmpty()) {
                chr.chatMessage(busy);
            }
            return;
        }
        if (!isServiceOn(feature)) {
            chr.chatMessage(getServiceOffText(feature));
            return;
        }
        ScriptManagerImpl sm = chr.getScriptManager();
        switch (feature) {
            case "menu" -> startMenu(chr, "main");
            case "shops" -> startMenu(chr, "shops"); // @shop
            case "serenEntry" -> sm.startScript(EDEA, "seren_enter", ScriptType.Npc);
            case "akechiEntry" -> sm.startScript(PRINCESS_SAKUNO, "akechi_enter", ScriptType.Npc);
            case "enhance" -> sm.startScript(MISS_RICHFIELD, "autoEnhance", ScriptType.Npc);
            case "gearGuide" -> sm.startScript(SPEAKER, "gearGuide", ScriptType.Npc);
            case "guide" -> sm.startScript(SPEAKER, "guide", ScriptType.Npc);
            case "bosses" -> sm.startScript(SPEAKER, "bossReadiness", ScriptType.Npc);
            case "upgrades" -> sm.startScript(SPEAKER, "upgrades", ScriptType.Npc);
            case "transmog" -> sm.startScript(SPEAKER, "transmog", ScriptType.Npc);
            case "style" -> sm.startScript(BeautyModule.SPEAKER, BeautyModule.SCRIPT, ScriptType.Npc);
            case "pet" -> sm.startScript(CLOY, "petPackage", ScriptType.Npc);
            case "sell" -> sm.startScript(MISS_RICHFIELD, "mesoMarket", ScriptType.Npc);
            case "stuck" -> sm.startScript(SPEAKER, "stuck", ScriptType.Npc);
            case "rebirth" -> sm.startScript(LAWRY, "3004604", ScriptType.Npc); // hardcode-ok
            case "settings" -> sm.startScript(JUDGE_CASSANDRA, "9010024", ScriptType.Npc); // hardcode-ok
            case "playStyle" -> sm.startScript(PlayStyleModule.SPEAKER, PlayStyleModule.SCRIPT, ScriptType.Npc);
            case "fredrick" -> sm.startScript(FREDRICK, "9030000", ScriptType.Npc); // hardcode-ok
            case "ari" -> sm.startScript(ARI, "npc_9100019", ScriptType.Npc);
            case "heroicCubes" -> {
                if (isHeroicCubeShopOn()) {
                    sm.startScript(HEROIC_CUBE_SHOP, "heroicCubeShop", ScriptType.Npc);
                } else {
                    chr.chatMessage("The Heroic Cube Shop is closed on this server.");
                }
            }
            case "coins" -> sm.startScript(SwordieCoinShop.SPEAKER, "swordieCoinShop", ScriptType.Npc);
            case "leaves" -> sm.startScript(SPEAKER, "leafShop", ScriptType.Npc);
            case "optimize" -> sm.startScript(2007, "optimize", ScriptType.Npc);
            case "vmatrix" -> sm.startScript(ARCHELLE, "VcoreUI", ScriptType.Npc);
            case "job" -> {
                String msg = JobPathModule.handleCommand(chr);
                if (msg != null) {
                    chr.chatMessage(msg);
                } else {
                    sm.startScript(SPEAKER, "jobPath", ScriptType.Npc);
                }
            }
            case "storage" -> openStorage(chr);
            case "gifts" -> {
                Set<FirstEnterReward> rewards = chr.getFirstEnterRewards();
                if (rewards.isEmpty()) {
                    chr.chatMessage("You have no gifts waiting.");
                } else {
                    sm.startScript(SPEAKER, "giftBox", ScriptType.Npc);
                }
            }
            default -> chr.chatMessage("Unknown menu option.");
        }
    }

    /**
     * @return the setting that lets players use a menu feature from anywhere (an NPC's service in town, or a helper
     * with no NPC), or null for a feature that is always there. "shopList" (the shops with an NPC in town) and
     * "bossEntrances" are only shown or hidden by menu.py.
     */
    public static ServerFeatures.Feature getServiceSetting(String feature) {
        if (feature == null) {
            return null;
        }
        return switch (feature) {
            case "shopList" -> ServerFeatures.Feature.REMOTE_SHOPS;
            case "sell" -> ServerFeatures.Feature.REMOTE_SELL;
            case "ari" -> ServerFeatures.Feature.REMOTE_ARI;
            case "coins" -> ServerFeatures.Feature.SWORDIE_COINS;
            case "pet" -> ServerFeatures.Feature.REMOTE_PETS;
            case "storage" -> ServerFeatures.Feature.REMOTE_STORAGE;
            case "enhance" -> ServerFeatures.Feature.REMOTE_ENHANCE;
            case "optimize" -> ServerFeatures.Feature.REMOTE_OPTIMIZE;
            case "vmatrix" -> ServerFeatures.Feature.REMOTE_VMATRIX;
            case "rebirth" -> ServerFeatures.Feature.REMOTE_REBIRTH;
            case "bossEntrances", "serenEntry", "akechiEntry" -> ServerFeatures.Feature.REMOTE_BOSS_ENTRY;
            default -> null;
        };
    }

    /** Heroic Cube Shop (Npc.wz scriptNpcShop, not placed on a map): cubes for mesos, opened from @menu > Shops. */
    public static final int HEROIC_CUBE_SHOP = 9090200;

    /** A Heroic-world convenience on this Interactive server (setting shop.heroicCubes, default on). */
    public static boolean isHeroicCubeShopOn() {
        return ServerSettings.getBoolean("shop.heroicCubes", true);
    }

    /** @return whether a menu feature can be used now (menu.py hides the ones that can't) */
    public static boolean isServiceOn(String feature) {
        ServerFeatures.Feature setting = getServiceSetting(feature);
        return setting == null || ServerFeatures.on(setting);
    }

    /** @return what a player is told when a feature is switched off: where to go instead */
    public static String getServiceOffText(String feature) {
        return switch (feature == null ? "" : feature) {
            case "sell" -> "Selling from anywhere is turned off on this server. Sell at any shop, or to Miss "
                    + "Richfield at the Free Market entrance.";
            case "ari" -> "Ari's shop is at the Free Market entrance.";
            case "coins" -> "The Swordie Coin Shop is closed right now. Your coins are kept.";
            case "pet" -> "The pet package is turned off on this server. Cloy in Henesys Park has pets.";
            case "storage" -> "Storage from anywhere is turned off on this server. Use a storage keeper in town.";
            case "enhance" -> "@enhance is turned off on this server. Use scrolls, cubes and the Star Force window.";
            case "optimize" -> "@optimize is turned off on this server.";
            case "vmatrix" -> "Talk to Archelle in Nameless Town for the V Matrix helper. (Your own V Matrix window "
                    + "still works.)";
            case "rebirth" -> "Talk to Lawry in Reverse City (Subway Tunnel Refuge) to be reborn.";
            case "serenEntry", "akechiEntry", "bossEntrances" -> "Boss teleports from the menu are turned off on "
                    + "this server. Travel to the boss's entrance yourself.";
            default -> "That is turned off on this server.";
        };
    }

    /** Starts the menu on a page ("main", "travel" or "shops"); bindings stay between scripts, so it's always set. */
    private static void startMenu(Char chr, String page) {
        startMenu(chr, page, "");
    }

    private static void startMenu(Char chr, String page, String arg) {
        Map<String, Object> bindings = new HashMap<>();
        bindings.put("startPage", page);
        bindings.put("startArg", arg == null ? "" : arg);
        chr.getScriptManager().startScript(SPEAKER, 0, SCRIPT, ScriptType.Npc, bindings);
    }

    /** Opens the account storage where the character stands (@menu storage, inventory Storage Room button). */
    public static void openStorage(Char chr) {
        if (chr.getInstance() != null || chr.getHP() <= 0) {
            chr.chatMessage("You can't open your storage here.");
            return;
        }
        chr.setInTrunk(true); // storage requests are only accepted while the storage is open
        chr.write(FieldPacket.trunkDlg(TrunkDlg.open(SCROOGE, chr.getAccount().getTrunk())));
    }

    /**
     * @stuck's first step: stops NPC/portal/quest/item/reactor scripts and closes a shop that's stuck open, so the
     * character can use skills and talk to NPCs again.
     */
    public static void clearStuckScripts(Char chr) {
        ScriptManagerImpl smi = chr.getScriptManager();
        chr.setTalkingToNpc(false);
        if (chr.isSkipPromptProtected()) {
            ArcaneRiverSkipModule.endPrompt(chr); // the Arcane River skip prompt's protection ends with its chat
        }
        smi.stop(ScriptType.Portal);
        smi.stop(ScriptType.Npc);
        smi.stop(ScriptType.Reactor);
        smi.stop(ScriptType.Quest);
        smi.stop(ScriptType.Item);
        chr.setShop(null);
        chr.dispose();
    }

    // ---------------------------------------------------------------------------------------------------------------
    // Travel (the menu's Travel and @go)
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * @param fromMenu true when asked from inside the menu (its own NPC chat doesn't count as busy)
     * @return why the character can't travel now, or null if it can: no instances/events, no maps that block
     * teleport items, not dead, not in a trade/shop/storage, and a short cooldown
     */
    public static String travelBlockReason(Char chr, boolean fromMenu) {
        var field = chr.getField();
        if (field == null) {
            return "You cannot travel right now.";
        }
        if (chr.getInstance() != null) {
            return "You cannot travel from inside a boss, party quest or event. Leave it first.";
        }
        if (chr.getHP() <= 0) {
            return "You cannot travel while dead.";
        }
        if (chr.getMiniRoom() != null || chr.getShop() != null || chr.isInTrunk()) {
            return "Close your trade, shop or storage window first.";
        }
        if (!fromMenu && chr.isTalkingToNpc()) {
            return "Finish your conversation first.";
        }
        if (net.swordie.ms.constants.FieldConstants.teleportWouldBreak(chr, field)) {
            return "You cannot teleport out of this map.";
        }
        // the menu's own travel has no cooldown (Shawn: on purpose); only @go and its commands wait
        if (fromMenu) {
            return null;
        }
        // no travel cooldown for admin accounts (Game Master and Admin)
        if (chr.getUser() != null && chr.getUser().getAccountType() != null
                && chr.getUser().getAccountType().ordinal() >= net.swordie.ms.enums.AccountType.GameMaster.ordinal()) {
            return null;
        }
        long wait = GameConstants.TRAVEL_COMMAND_COOLDOWN_SECONDS * 1000L
                - (System.currentTimeMillis() - lastTravel.getOrDefault(chr.getId(), 0L));
        if (wait > 0) {
            return String.format("You can travel again in %d second(s).", (wait + 999) / 1000);
        }
        return null;
    }

    private static final int RECENT_DESTINATIONS = 4;

    /** Remembers a place the menu took the character to (newest first, a few of them), for Travel's "Recent". */
    public static void rememberDestination(Char chr, int mapId) {
        List<Integer> recent = getRecentDestinations(chr);
        recent.remove(Integer.valueOf(mapId));
        recent.add(0, mapId);
        while (recent.size() > RECENT_DESTINATIONS) {
            recent.remove(recent.size() - 1);
        }
        chr.getQuestManager().setHiddenRecord(GameConstants.MENU_RECENT_QR, recent.stream().map(String::valueOf)
                .collect(java.util.stream.Collectors.joining(",")));
    }

    /** The places the menu took the character to lately, newest first. */
    public static List<Integer> getRecentDestinations(Char chr) {
        List<Integer> res = new ArrayList<>();
        String s = chr.getQuestManager().getHiddenRecord(GameConstants.MENU_RECENT_QR);
        if (s != null) {
            for (String part : s.split(",")) {
                if (part.matches("\\d+")) {
                    res.add(Integer.parseInt(part));
                }
            }
        }
        return res;
    }

    /** Starts the travel cooldown (called right before a menu/@go warp). */
    public static void markTravel(Char chr) {
        lastTravel.put(chr.getId(), System.currentTimeMillis());
    }

    /** @return the nearest town of the character's map, or 0 if there's none to go to */
    public static int getNearestTown(Char chr) {
        int target = chr.getField().getInfo().getReturnMap();
        return target <= 0 || target == GameConstants.NO_MAP_ID || target == chr.getFieldID() ? 0 : target;
    }

    /**
     * How many other players are in a map on the character's channel (for the hunting list). Only maps that are
     * loaded count (an empty map isn't loaded); hidden GMs don't count.
     */
    public static int countPlayersInMap(Char chr, int mapId) {
        try {
            var channel = chr.getClient() == null ? null : chr.getClient().getChannelInstance();
            var field = channel == null ? null : channel.getFields().get(mapId);
            if (field == null) {
                return 0;
            }
            int n = 0;
            for (Char other : field.getCharsReadOnly()) {
                if (other != chr && !other.isHidden()) {
                    n++;
                }
            }
            return n;
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean isInFreeMarket(Char chr) {
        return chr.getFieldID() == FREE_MARKET || GameConstants.isFreeMarketField(chr.getFieldID());
    }

    /** Warps to the Free Market; its exit brings the character back here. */
    public static void goToFreeMarket(Char chr) {
        chr.getScriptManager().setReturnField(chr.getFieldID());
        markTravel(chr);
        chr.warp(FREE_MARKET);
    }

    private static String mapName(int fieldID) {
        String name = StringData.getMapStringById(fieldID);
        return name == null ? String.valueOf(fieldID) : name;
    }

    /** @return a map's own name, without its street ("Victoria Road : Henesys" -> "Henesys") */
    private static String townName(int fieldID) {
        String name = mapName(fieldID);
        int i = name.lastIndexOf(" : ");
        return i >= 0 ? name.substring(i + 3).trim() : name.trim();
    }

    /**
     * How well a town matches what was typed for @go: its map id (10), its name exactly (6), the name with the
     * street (6), the start of its name (4), the start of a word in it (3), anywhere in its name or street (2);
     * 0 = no match.
     */
    private static int townMatchScore(int id, String query) {
        if (String.valueOf(id).equals(query)) {
            return 10;
        }
        String name = townName(id).toLowerCase();
        String full = mapName(id).toLowerCase();
        if (name.equals(query) || full.equals(query)) {
            return 6;
        }
        if (name.startsWith(query)) {
            return 4;
        }
        String area = QuickMoveModule.getAreaName(id); // Arcane River towns by their area's name
        if (area != null) {
            area = area.toLowerCase();
            if (area.equals(query)) {
                return 6;
            }
            if (area.startsWith(query) || area.contains(" " + query)) {
                return 4;
            }
        }
        if (name.contains(" " + query)) {
            return 3;
        }
        return full.contains(query) ? 2 : 0;
    }

    /**
     * @go [place]: without a place, opens the menu's Travel page. With one: any town (the menu's town list plus towns
     * this character has visited, by part of its name or its map id), "fm"/"market" for the Free Market, or
     * "town"/"home" for the nearest town.
     */
    public static void go(Char chr, String[] args) {
        if (args.length < 2) {
            String busy = busyReason(chr);
            if (busy != null) {
                if (!busy.isEmpty()) {
                    chr.chatMessage(busy);
                }
                return;
            }
            startMenu(chr, "travel");
            return;
        }
        String query = String.join(" ", Arrays.copyOfRange(args, 1, args.length)).toLowerCase().trim();
        if (query.equals("fm") || query.equals("market") || query.equals("free market")
                || query.equals("freemarket")) {
            if (isInFreeMarket(chr)) {
                chr.chatMessage("You are already in the Free Market.");
                return;
            }
            String why = travelBlockReason(chr, false);
            if (why != null) {
                chr.chatMessage(why);
                return;
            }
            goToFreeMarket(chr);
            return;
        }
        if (query.equals("agent") || query.equals("agents") || query.equals("agent hq") || query.equals("hq")) {
            if (chr.getFieldID() == net.swordie.ms.world.SecretAgentEvent.HQ) {
                chr.chatMessage("You are already in the Agent HQ.");
                return;
            }
            String why = travelBlockReason(chr, false);
            if (why != null) {
                chr.chatMessage(why);
                return;
            }
            chr.getScriptManager().setReturnField(chr.getFieldID());
            markTravel(chr);
            chr.warp(net.swordie.ms.world.SecretAgentEvent.HQ);
            return;
        }
        if (query.equals("town") || query.equals("home")) {
            String why = travelBlockReason(chr, false);
            if (why != null) {
                chr.chatMessage(why);
                return;
            }
            int town = getNearestTown(chr);
            if (town == 0) {
                chr.chatMessage("There is no town to return to from here.");
                return;
            }
            markTravel(chr);
            chr.warp(town);
            return;
        }
        Set<Integer> towns = new LinkedHashSet<>(QuickMoveModule.getAllTowns());
        String visitedStr = chr.getQuestManager().getHiddenRecord(GameConstants.VISITED_TOWNS_QR);
        if (visitedStr != null) {
            for (String s : visitedStr.split(",")) {
                if (s.matches("\\d+")) {
                    towns.add(Integer.parseInt(s));
                }
            }
        }
        // map names are "Street : Name" and several towns share a name or part of one ("Henesys", "Henesys
        // Market", ...): the best match wins (exact name, then the start of the name, then anywhere in it), towns of
        // the menu's list before other visited towns; only a real tie asks which one.
        List<Integer> mainTowns = QuickMoveModule.getAllTowns();
        Integer target = null;
        int bestScore = 0;
        List<Integer> best = new ArrayList<>();
        for (int id : towns) {
            int score = townMatchScore(id, query);
            if (score == 0) {
                continue;
            }
            if (mainTowns.contains(id)) {
                score += 1; // the menu's towns before other visited maps
            }
            if (score > bestScore) {
                bestScore = score;
                best.clear();
            }
            if (score == bestScore && !best.contains(id)) {
                best.add(id);
            }
        }
        if (best.size() == 1) {
            target = best.get(0);
        } else if (best.size() > 1) {
            // the same name more than once (e.g. a town and its event copy): the first in the menu's list
            for (int id : best) {
                if (mainTowns.contains(id) && bestScore >= 7) {
                    target = id;
                    break;
                }
            }
            if (target == null) {
                chr.chatMessage("Which one? " + best.stream().limit(8)
                        .map(id -> townName(id) + " (@go " + id + ")").collect(Collectors.joining(", ")));
                return;
            }
        }
        if (target == null) {
            chr.chatMessage("There is no town called \"%s\". Type @go to pick from the list.", query);
            return;
        }
        if (target == chr.getFieldID()) {
            chr.chatMessage("You are already there.");
            return;
        }
        String why = travelBlockReason(chr, false);
        if (why != null) {
            chr.chatMessage(why);
            return;
        }
        markTravel(chr);
        chr.warp(target);
    }

    // ---------------------------------------------------------------------------------------------------------------
    // My character
    // ---------------------------------------------------------------------------------------------------------------

    /** @return why the character can't become a Burning character, or null if it can. */
    public static String getBurningBlocker(Char chr) {
        return BurningModule.getBlocker(chr, BurningModule.TYPE_AUTO);
    }

    /** What Burning does, for the question before it's set. */
    public static String getBurningRules() {
        return BurningModule.getRules();
    }

    /** Makes the character a Burning character, unless {@link #getBurningBlocker(Char)} says no. */
    public static String makeBurning(Char chr) {
        String blocker = BurningModule.startBurning(chr, BurningModule.TYPE_AUTO);
        if (blocker != null) {
            return blocker;
        }
        return String.format("%s is now a Burning character. %s",
                chr.getName(), BurningModule.describe(chr.getAvatarData().getCharacterStat()));
    }

    /** The stats summary (was @check). */
    public static String getStatsText(Char chr) {
        Map<BaseStat, Integer> tbs = chr.getTotalBasicStats();
        return String.format("#e#bYour stats#k#n\r\n\r\n"
                        + "STR #b%,d#k (%d%%)   DEX #b%,d#k (%d%%)\r\nINT #b%,d#k (%d%%)   LUK #b%,d#k (%d%%)\r\n"
                        + "Attack #b%,d#k (%d%%)   Magic Attack #b%,d#k (%d%%)\r\n"
                        + "Max HP #b%,d#k (%d%%)   Max MP #b%,d#k (%d%%)\r\n"
                        + "Attack speed #b%d#k\r\n"
                        + "EXP #b+%d%%#k   Mesos #b+%d%%#k   Drop #b+%d%%#k\r\n\r\n"
                        + "#dServer time %s   Ping %d ms#k",
                tbs.getOrDefault(BaseStat.str, 0), tbs.getOrDefault(BaseStat.strR, 0),
                tbs.getOrDefault(BaseStat.dex, 0), tbs.getOrDefault(BaseStat.dexR, 0),
                tbs.getOrDefault(BaseStat.inte, 0), tbs.getOrDefault(BaseStat.intR, 0),
                tbs.getOrDefault(BaseStat.luk, 0), tbs.getOrDefault(BaseStat.lukR, 0),
                tbs.getOrDefault(BaseStat.pad, 0), tbs.getOrDefault(BaseStat.padR, 0),
                tbs.getOrDefault(BaseStat.mad, 0), tbs.getOrDefault(BaseStat.madR, 0),
                tbs.getOrDefault(BaseStat.mhp, 0), tbs.getOrDefault(BaseStat.mhpR, 0),
                tbs.getOrDefault(BaseStat.mmp, 0), tbs.getOrDefault(BaseStat.mmpR, 0),
                tbs.getOrDefault(BaseStat.booster, 0),
                tbs.getOrDefault(BaseStat.expR, 0), tbs.getOrDefault(BaseStat.mesoR, 0),
                tbs.getOrDefault(BaseStat.dropR, 0),
                FileTime.currentTime().toLocalDateTime().withNano(0).toString().replace('T', ' '),
                chr.getClient() == null ? 0 : chr.getClient().getPing());
    }

    private static Stat abilityStat(int index) {
        return switch (index) {
            case 0 -> Stat.str;
            case 1 -> Stat.dex;
            case 2 -> Stat.inte;
            default -> Stat.luk;
        };
    }

    public static String getAbilityName(int index) {
        return switch (index) {
            case 0 -> "STR";
            case 1 -> "DEX";
            case 2 -> "INT";
            default -> "LUK";
        };
    }

    public static int getAbility(Char chr, int index) {
        return chr.getStat(abilityStat(index));
    }

    /** @return how many points can come back out of a stat (above {@link #MIN_ABILITY_STAT}) */
    public static int getReturnableAbility(Char chr, int index) {
        // and no more than unspent AP can hold (the client reads AP as a short: past 32,767 it turns negative)
        int room = Math.max(0, MAX_AP - chr.getStat(Stat.ap));
        return Math.max(0, Math.min(getAbility(chr, index) - MIN_ABILITY_STAT, room));
    }

    /** How far the stat is above the minimum of 4 (what could come out with no AP limit). */
    public static int getAbilityAboveMinimum(Char chr, int index) {
        return Math.max(0, getAbility(chr, index) - MIN_ABILITY_STAT);
    }

    /** The most unspent AP a character can hold (the client reads it as a short). */
    private static final int MAX_AP = Short.MAX_VALUE;

    /** @return how many ability points can go into a stat */
    public static int getAddableAbility(Char chr, int index) {
        return Math.max(0, Math.min(chr.getStat(Stat.ap), MAX_ABILITY_STAT - getAbility(chr, index)));
    }

    /**
     * Puts ability points (AP) into STR/DEX/INT/LUK (index 0-3), or with a negative amount takes points out of it
     * and gives them back as AP. A stat never goes below {@link #MIN_ABILITY_STAT}.
     *
     * @return what happened, for the player
     */
    public static String allocateAbility(Char chr, int index, int amount) {
        Stat stat = abilityStat(index);
        String name = getAbilityName(index);
        int cur = chr.getStat(stat);
        int ap = chr.getStat(Stat.ap);
        int change = amount > 0 ? Math.min(amount, getAddableAbility(chr, index))
                : -Math.min(-amount, getReturnableAbility(chr, index));
        if (change == 0) {
            return amount > 0 ? "No ability points could be added to " + name + "."
                    : name + " is already at the minimum of " + MIN_ABILITY_STAT + ".";
        }
        chr.setStat(stat, cur + change);
        chr.setStat(Stat.ap, ap - change);
        Map<Stat, Object> stats = new HashMap<>();
        stats.put(stat, (short) chr.getStat(stat));
        stats.put(Stat.ap, (short) chr.getStat(Stat.ap));
        chr.write(WvsContext.statChanged(stats));
        chr.initBaseStats();
        if (change > 0) {
            return String.format("%s +%d (now %d). AP left: %d.", name, change, chr.getStat(stat), chr.getStat(Stat.ap));
        }
        return String.format("%s -%d (now %d). AP: %d.", name, -change, chr.getStat(stat), chr.getStat(Stat.ap));
    }

    /** Hides or shows other players' attacks and skill effects for this character (less lag). */
    public static String toggleEffects(Char chr) {
        boolean hide = !chr.isHidingOtherEffects();
        chr.setHidingOtherEffects(hide);
        return hide ? "Other players' effects: #bhidden#k (less lag)." : "Other players' effects: #bshown#k.";
    }

    // ---------------------------------------------------------------------------------------------------------------
    // Bosses, info and utilities
    // ---------------------------------------------------------------------------------------------------------------

    /** The players in the character's map (was @players). */
    public static String getPlayersInMapText(Char chr) {
        List<String> names = new ArrayList<>();
        for (Char other : chr.getField().getChars()) {
            if (other != chr && other.getUser() != null && other.getUser().getAccountType() == AccountType.Player) {
                names.add(other.getName() + NewPlayerModule.sproutTag(other));
            }
        }
        return names.isEmpty() ? "No other players here."
                : "#e#bPlayers here#k#n (" + names.size() + ")\r\n\r\n" + String.join(", ", names);
    }

    /** Merges partial stacks in the Use, Set-up and Etc inventories (was @merge). */
    public static String mergeStacks(Char chr) {
        int merged = 0;
        for (InvType type : new InvType[]{InvType.CONSUME, InvType.INSTALL, InvType.ETC}) {
            merged += chr.mergeStacks(type);
        }
        chr.dispose();
        return merged == 0 ? "Nothing to merge." : "Merged " + merged + " stack(s).";
    }

    /**
     * Fixes Arcane River quests that are known to get stuck, and the Zero weapon window.
     *
     * @return what was fixed; "WARP" + text when the fix needs the character moved to Morass (the menu warps)
     */
    public static String fixQuests(Char chr) {
        var qm = chr.getQuestManager();
        List<String> fixed = new ArrayList<>();
        if (JobConstants.isZero(chr.getJob()) && !PlayStyleModule.isClassic(chr)) { // Classic Zeros open it in the story
            if (!qm.hasQuestCompleted(QuestConstants.LAPIS_LAZULI)) {
                qm.completeQuest(QuestConstants.LAPIS_LAZULI, false);
                fixed.add("Zero's weapon window is unlocked (reopen the Equipment window).");
            }
            if (!qm.hasQuestCompleted(QuestConstants.ZERO_LAST_QUEST)) {
                qm.completeQuest(QuestConstants.ZERO_LAST_QUEST, false);
            }
        }
        if (chr.getLevel() >= 200) {
            if (chr.hasQuestInProgress(34307)) {
                qm.completeQuest(34307);
                fixed.add("Finding the Awakened Ones (1) is complete.");
            } else if (qm.hasQuestCompleted(34330) && !qm.hasQuestCompleted(34331)) {
                qm.completeQuest(34331);
                fixed.add("Decisive Battle is complete. The Lucid NPC should show up now.");
            } else if (qm.hasQuestCompleted(34249) && !qm.hasQuestInProgress(34250)
                    && !qm.hasQuestCompleted(34250)) {
                return "WARP";
            }
        }
        return fixed.isEmpty() ? "No stuck quests found." : String.join("\r\n", fixed);
    }

    /** The map Morass's "Unexpected Enemy (1)" starts in, for {@link #fixQuests(Char)}'s "WARP". */
    public static int getFixQuestWarpMap() {
        return PATH_TO_CORAL_FOREST;
    }

    private static double dropChance(Char chr, int chance) {
        double rate = chr.getTotalStat(BaseStat.dropR) * (double) ServerRates.getDropRate();
        return Math.min(100D, chance * rate / 100D / GameConstants.MAX_DROP_CHANCE * 100D);
    }

    private static String itemName(int itemID) {
        String name = StringData.getItemStringById(itemID);
        return name == null ? String.valueOf(itemID) : name;
    }

    private static String mobName(int mobID) {
        String name = StringData.getMobStringById(mobID);
        return name == null ? String.valueOf(mobID) : name;
    }

    /** What the monsters in this map drop, with the chances for this character (was @dropinfo). */
    public static String getMapDropsText(Char chr) {
        Map<Integer, Mob> mobs = new LinkedHashMap<>();
        for (Mob mob : chr.getField().getMobs()) {
            mobs.putIfAbsent(mob.getTemplateId(), mob);
        }
        if (mobs.isEmpty()) {
            return "No monsters here.";
        }
        StringBuilder sb = new StringBuilder("#e#bDrops in this map#k#n\r\n\r\n");
        for (Mob mob : mobs.values()) {
            String drops = mob.getDrops().stream()
                    .filter(d -> !d.isMoney() && d.getItemID() != 0)
                    .sorted(Comparator.comparingInt(DropInfo::getChance).reversed())
                    .limit(10)
                    .map(d -> String.format("%s (%.2f%%)", itemName(d.getItemID()), dropChance(chr, d.getChance())))
                    .collect(Collectors.joining(", "));
            sb.append(String.format("#b%s#k: %s\r\n\r\n", mobName(mob.getTemplateId()),
                    drops.isEmpty() ? "no item drops" : drops));
        }
        sb.append("#dIncludes your drop rate.#k");
        return sb.toString();
    }

    /** Which monsters drop an item, by name or id (was @whodrops). */
    public static String getWhoDropsText(Char chr, String query) {
        query = query == null ? "" : query.trim();
        if (query.isEmpty()) {
            return "Enter an item name.";
        }
        List<Integer> itemIDs = new ArrayList<>();
        if (query.matches("\\d+")) {
            itemIDs.add(Integer.parseInt(query));
        } else {
            var exact = StringData.getItemStringByName(query, true);
            var found = exact.isEmpty() ? StringData.getItemStringByName(query, false) : exact;
            itemIDs.addAll(found.keySet());
        }
        if (itemIDs.isEmpty()) {
            return String.format("No item called \"%s\".", query);
        }
        if (itemIDs.size() > 3) {
            return String.format("%d items match \"%s\". Be more specific, e.g. %s", itemIDs.size(), query,
                    itemIDs.stream().limit(5).map(MenuModule::itemName).collect(Collectors.joining(", ")));
        }
        StringBuilder sb = new StringBuilder();
        for (int itemID : itemIDs) {
            String drops = DropData.getDropInfoByItemId(itemID).stream()
                    .filter(d -> d.getMobId() > 0)
                    .sorted(Comparator.comparingInt(DropInfo::getChance).reversed())
                    .limit(10)
                    .map(d -> String.format("%s (%.2f%%)", mobName(d.getMobId()), dropChance(chr, d.getChance())))
                    .collect(Collectors.joining(", "));
            sb.append(String.format("#b%s#k: %s\r\n\r\n", itemName(itemID),
                    drops.isEmpty() ? "not dropped by monsters" : drops));
        }
        return sb.toString().trim();
    }
}
