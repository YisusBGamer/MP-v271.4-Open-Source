package net.swordie.ms.client.character.commands;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.MenuModule;
import net.swordie.ms.client.character.NewPlayerModule;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.constants.ServerRates;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.enums.AccountType;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.handlers.social.module.GuildModule;
import net.swordie.ms.util.Rect;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static net.swordie.ms.enums.AccountType.Player;
import static net.swordie.ms.enums.ChatType.SpeakerChannel;

public class PlayerCommands {

    // Player commands: @menu holds almost everything (see MenuModule and scripts/npc/menu.py); @go, @job, @gear,
    // @upgrades, @enhance, @sell, @shop, @rebirth and @stuck are shortcuts for what players use most, and @help
    // lists them.
    // More shortcuts that skip the menu's navigation (getShortcutList; "@help shortcuts" explains them).
    // @mobinfo and @lifeinfo are for testers.

    /**
     * The commands listed outside the game. Kept to these on purpose: newer commands are only listed in-game
     * (@help).
     */
    private static final java.util.Set<String> LISTED_COMMANDS = java.util.Set.of(
            "@menu", "@go [town]", "@job", "@enhance", "@stuck", "@help");

    /**
     * The player commands listed outside the game: the ones in {@link #LISTED_COMMANDS}, worded as in the
     * in-game command list.
     */
    public static List<String[]> getCommandList() {
        // Every player command: the @help list, then the shortcuts
        List<String[]> res = new java.util.ArrayList<>(getHelpCommandList());
        res.addAll(getShortcutList());
        return res;
    }

    /**
     * All player commands as {command, what it does}, which @help prints in the chat.
     */
    public static List<String[]> getHelpCommandList() {
        return List.of(
                new String[]{"@menu", "Everything in one place (also in the Quick Move button)."},
                new String[]{"@go [town]", String.format("Travel to a town. @go fm: Free Market, @go agent: Agent HQ, @go home: nearest "
                        + "town. %d s cooldown.", GameConstants.TRAVEL_COMMAND_COOLDOWN_SECONDS)},
                new String[]{"@job", "Pick a job path. " + GameConstants.JOB_PATH_WARNING},
                new String[]{"@rebirth", "Open the rebirth choices (level " + GameConstants.REBIRTH_MIN_LEVEL + " and up)."},
                new String[]{"@str/@dex/@int/@luk <amount|all|-amount|-all>",
                        "Spend ability points on a base stat, or take them back out (stats stay at 4 or more)."},
                new String[]{"@joinguild <name>", "Join a guild by its exact name."},
                new String[]{"@gear", "Your empty gear slots and where to farm them."},
                new String[]{"@upgrades", "Equip better gear you're carrying, or unequip."},
                new String[]{"@enhance", "Scroll, Star Force, potentials and cubing for your worn gear."},
                new String[]{"@sell", "Sell items for mesos (buy them back at any shop)."},
                new String[]{"@leaves", "Golden Maple Leaf Shop: mesos over 80B become Leaves; spend them or "
                        + "exchange them back."},
                new String[]{"@shop", "Open the shops."},
                new String[]{"@stuck", "Unfreeze chat or skills, or move to Henesys."},
                new String[]{"@burning", "Make this character a Burning character: Hyper Burning MAX "
                        + "(5 levels for each level up to 260), then Burning Beyond to 270. @burning end ends it."},
                new String[]{"@story [arcane|grandis] [skip]", "Express: keep a region's story instead of skipping "
                        + "it on arrival, or see where each region's story stands."},
                new String[]{"@help", "Lists these commands."}
        );
    }

    /**
     * The shortcuts as {command, what it does}: each opens a menu page (or does the action) without navigating
     * the menu. Listed in-game (@help, @menu > Help > Commands) and outside the game.
     */
    /** The menu's sections, in its order: @help and the menu's command list group the shortcuts by these. */
    public static final List<String> SHORTCUT_GROUPS = List.of("Travel", "Character", "Gear", "Bosses", "Shops",
            "Social & rewards", "Info & help", "Storage & inventory");

    public static List<String[]> getShortcutList() {
        return List.of(
                new String[]{"@towns", "Every town by region", "Travel"},
                new String[]{"@hunt", "Hunting maps for your level", "Travel"},
                new String[]{"@bossenter", "Teleport to a boss's entrance", "Travel"},
                new String[]{"@char", "My character page", "Character"},
                new String[]{"@ap", "Ability points", "Character"},
                new String[]{"@stats", "Your stats", "Character"},
                new String[]{"@ia", "Inner Ability: reroll and lock lines", "Character"},
                new String[]{"@playstyle", "Classic or Express", "Character"},
                new String[]{"@style", "Change your hair, face, eyes and skin for free", "Character"},
                new String[]{"@transmog", "Transmog", "Character"},
                new String[]{"@vmatrix", "V Matrix: equip your best Boost Nodes, clear level 1 nodes", "Gear"},
                new String[]{"@optimize", "Gear, potentials, flames, cubes, Hyper Stats and Boost Nodes done for you", "Gear"},
                new String[]{"@jobgear", "The weapons and secondaries your class's job quests give", "Gear"},
                new String[]{"@bosses", "Bosses page", "Bosses"},
                new String[]{"@petshop", "Pets", "Shops"},
                new String[]{"@cubeshop", "Heroic Cube Shop: cubes for mesos", "Shops"},
                new String[]{"@coinshop", "Swordie Coin Shop", "Shops"},
                new String[]{"@ari", "Ari's NX shop", "Shops"},
                new String[]{"@mlg", "Maple Leaf Gold exchange", "Shops"},
                new String[]{"@lfg", "Looking for group", "Social & rewards"},
                new String[]{"@gifts", "Claim waiting gifts", "Social & rewards"},
                new String[]{"@checklist", "Onboarding checklist", "Social & rewards"},
                new String[]{"@rankings", "Class, boss and fame rankings", "Social & rewards"},
                new String[]{"@social", "Friends, guild and party", "Social & rewards"},
                new String[]{"@next", "What should I do next?", "Info & help"},
                new String[]{"@stronger", "How do I get stronger? (also @guide)", "Info & help"},
                new String[]{"@drops", "Drops in this map", "Info & help"},
                new String[]{"@whodrops [item]", "Which monsters drop an item", "Info & help"},
                new String[]{"@effects", "Other players' effects on/off", "Info & help"},
                new String[]{"@agent", "Talk to Agent E (the Secret Agent event); @go agent: the Agent HQ", "Info & help"},
                new String[]{"@settings", "Your settings", "Info & help"},
                new String[]{"@storage", "Open your storage", "Storage & inventory"},
                new String[]{"@fredrick", "Fredrick: your shop's mesos and items", "Storage & inventory"},
                new String[]{"@merge", "Merge partial stacks", "Storage & inventory"}
        );
    }

    /** What a player is told when a command is switched off (settings cmd_*, ServerFeatures). */
    public static final String OFF_TEXT = "That command is turned off on this server.";

    /**
     * @return the setting that switches a player command on and off (by any of its names, without the @), or null
     * for the commands that are always there: @menu, @help, @stuck and the shortcuts to menu pages
     */
    public static ServerFeatures.Feature getSetting(String name) {
        return switch (name == null ? "" : name.toLowerCase(Locale.ROOT)) {
            case "go" -> ServerFeatures.Feature.CMD_GO;
            case "job" -> ServerFeatures.Feature.CMD_JOB;
            case "rebirth" -> ServerFeatures.Feature.CMD_REBIRTH;
            case "enhance" -> ServerFeatures.Feature.CMD_ENHANCE;
            case "sell" -> ServerFeatures.Feature.CMD_SELL;
            case "shop", "shops" -> ServerFeatures.Feature.CMD_SHOP;
            case "storage" -> ServerFeatures.Feature.CMD_STORAGE;
            case "optimize", "optimise" -> ServerFeatures.Feature.CMD_OPTIMIZE;
            case "vmatrix" -> ServerFeatures.Feature.CMD_VMATRIX;
            case "burning" -> ServerFeatures.Feature.CMD_BURNING;
            case "story" -> ServerFeatures.Feature.CMD_STORY;
            case "gear", "gearguide", "upgrades", "upgrade" -> ServerFeatures.Feature.CMD_GEAR;
            case "str", "dex", "int", "luk" -> ServerFeatures.Feature.CMD_STATS;
            case "joinguild" -> ServerFeatures.Feature.CMD_JOINGUILD;
            case "transmog" -> ServerFeatures.Feature.CMD_TRANSMOG;
            case "next" -> ServerFeatures.Feature.NEXT_HELPER;
            default -> null;
        };
    }

    /**
     * @param command a command as the lists write it ("@go [town]", "@str/@dex/@int/@luk <amount>") or a bare name
     * @return whether it can be used now
     */
    public static boolean isOn(String command) {
        if (command == null) {
            return true;
        }
        String s = command.startsWith("@") ? command.substring(1) : command;
        int end = 0;
        while (end < s.length() && Character.isLetter(s.charAt(end))) {
            end++;
        }
        String name = s.substring(0, end).toLowerCase(Locale.ROOT);
        ServerFeatures.Feature setting = getSetting(name);
        if (setting != null && !ServerFeatures.on(setting)) {
            return false;
        }
        String service = getService(name);
        if (service != null && !MenuModule.isServiceOn(service)) {
            return false;
        }
        return !name.equals("cubeshop") || MenuModule.isHeroicCubeShopOn();
    }

    /** @return the @menu service a shortcut opens (its on/off switch), or null */
    private static String getService(String name) {
        return switch (name) {
            case "petshop", "pets" -> "pet";
            case "coinshop", "coins" -> "coins";
            case "ari", "nxshop" -> "ari";
            case "bossenter", "bossportal" -> "bossEntrances";
            default -> null;
        };
    }

    /** @return {@link #getHelpCommandList()} without the commands that are switched off */
    public static List<String[]> getEnabledHelpCommandList() {
        return getHelpCommandList().stream().filter(c -> isOn(c[0])).collect(Collectors.toList());
    }

    /** @return {@link #getShortcutList()} without the shortcuts that are switched off */
    public static List<String[]> getEnabledShortcutList() {
        return getShortcutList().stream().filter(c -> isOn(c[0])).collect(Collectors.toList());
    }

    /** @return true (after telling the player where to go instead) when a menu service is switched off */
    private static boolean serviceOff(Char chr, String feature) {
        if (MenuModule.isServiceOn(feature)) {
            return false;
        }
        chr.chatMessage(MenuModule.getServiceOffText(feature));
        return true;
    }

    private static boolean busy(Char chr) {
        if (chr.getMiniRoom() != null || chr.getShop() != null || chr.isInTrunk()) {
            chr.chatMessage("Close the trade, shop or storage first.");
            return true;
        }
        if (chr.isTalkingToNpc()) {
            chr.chatMessage("Finish your conversation first. (If a chat window is stuck, type @stuck.)");
            return true;
        }
        return false;
    }

    @Command(names = {"menu"}, requiredType = Player)
    public static class Menu extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.openMenu(chr);
        }
    }

    @Command(names = {"go"}, requiredType = Player)
    public static class Go extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.go(chr, args);
        }
    }

    @Command(names = {"job"}, requiredType = Player)
    public static class JobPath extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            // choose a job path (a menu opens); see JobPathModule
            if (!busy(chr)) {
                MenuModule.open(chr, "job");
            }
        }
    }

    @Command(names = {"rebirth"}, requiredType = Player)
    public static class Rebirth extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.open(chr, "rebirth");
        }
    }

    @Command(names = {"enhance"}, requiredType = Player)
    public static class AutoEnhance extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            // scrolls and Star Forces equipped items to the max, potentials, cubing, ... (autoEnhance.py)
            if (!busy(chr)) {
                MenuModule.open(chr, "enhance");
            }
        }
    }

    @Command(names = {"gear", "gearguide"}, requiredType = Player)
    public static class GearGuide extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            // empty equipment slots and where to farm them (gearGuide.py, GearGuideModule)
            if (!busy(chr)) {
                MenuModule.open(chr, "gearGuide");
            }
        }
    }

    @Command(names = {"upgrades", "upgrade"}, requiredType = Player)
    public static class Upgrades extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            // better equips in the Equip inventory, equipped on request; unequipping (upgrades.py, UpgradeFinder)
            if (!busy(chr)) {
                MenuModule.open(chr, "upgrades");
            }
        }
    }

    @Command(names = {"sell"}, requiredType = Player)
    public static class Sell extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            // selling only (mesoMarket.py, Miss Richfield); sold items go to the shops' buyback list
            if (!busy(chr)) {
                MenuModule.open(chr, "sell");
            }
        }
    }

    @Command(names = {"shop", "shops"}, requiredType = Player)
    public static class Shop extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            // the menu's Shops & trading page (menu.py shops_page)
            if (!busy(chr)) {
                MenuModule.open(chr, "shops");
            }
        }
    }

    @Command(names = {"joinguild"}, requiredType = Player)
    public static class JoinGuild extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            if (busy(chr)) {
                return;
            }
            if (args.length < 2) {
                chr.chatMessage("Use @joinguild <exact guild name>.");
                return;
            }
            if (chr.getGuild() != null) {
                chr.chatMessage("You are already in a guild.");
                return;
            }
            String guildName = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length)).trim();
            var guild = chr.getWorld().getGuildByName(guildName);
            if (guild == null) {
                chr.chatMessage("No guild named '" + guildName + "' was found.");
                return;
            }
            if (guild.isFull()) {
                chr.chatMessage("That guild is full.");
                return;
            }
            GuildModule.joinGuild(guild, chr);
            chr.chatMessage("You joined the guild " + guild.getName() + ".");
        }
    }

    @Command(names = {"stuck"}, requiredType = Player)
    public static class Stuck extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            // first clears a frozen NPC chat/shop, then asks whether to move to Henesys
            if (chr.getMiniRoom() != null || chr.isInTrunk()) {
                chr.chatMessage("Close the trade or storage first.");
                return;
            }
            MenuModule.clearStuckScripts(chr);
            MenuModule.open(chr, "stuck");
        }
    }

    /** Burning characters, see BurningModule. */
    @Command(names = {"burning"}, requiredType = Player)
    public static class Burning extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length >= 2 && (args[1].equalsIgnoreCase("end") || args[1].equalsIgnoreCase("stop"))) {
                String endBlocker = net.swordie.ms.client.character.BurningModule.getEndBlocker(chr);
                if (endBlocker != null) {
                    chr.chatMessage(endBlocker);
                } else if (args.length < 3 || !args[2].equalsIgnoreCase("yes")) {
                    chr.chatMessage("Ending Burning is for good: the extra levels stop, the levels gained stay. "
                            + "Type @burning end yes to end it.");
                } else {
                    String r = net.swordie.ms.client.character.BurningModule.endBurning(chr);
                    if (r != null) {
                        chr.chatMessage(r);
                    }
                }
                return;
            }
            String blocker = MenuModule.getBurningBlocker(chr);
            if (blocker != null) {
                chr.chatMessage(blocker);
                return;
            }
            if (args.length < 2 || !args[1].equalsIgnoreCase("yes")) {
                chr.chatMessage(MenuModule.getBurningRules().replace("#b", "").replace("#k", ""));
                chr.chatMessage("Type @burning yes to make " + chr.getName() + " a Burning character.");
                return;
            }
            chr.chatMessage(MenuModule.makeBurning(chr));
        }
    }

    /** Express story opt-out per region, see ContentAccess. */
    @Command(names = {"story"}, requiredType = Player)
    public static class Story extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            String arg = args.length < 2 ? "" : String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
            for (String line : net.swordie.ms.client.character.ContentAccess.storyCommand(chr, arg)) {
                chr.chatMessage(line);
            }
        }
    }

    @Command(names = {"help", "commands"}, requiredType = Player)
    public static class Help extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            if (args.length > 1 && args[1].toLowerCase().startsWith("short")) { // @help shortcuts
                for (String[] command : getEnabledShortcutList()) {
                    chr.chatMessage(command[0] + ": " + command[1]);
                }
                return;
            }
            for (String[] command : getEnabledHelpCommandList()) {
                if (!command[0].equals("@help")) {
                    chr.chatMessage(command[0] + ": " + command[1]);
                }
            }
            for (String group : SHORTCUT_GROUPS) {
                List<String> names = getEnabledShortcutList().stream().filter(c -> c.length > 2 && c[2].equals(group))
                        .map(c -> c[0].split(" ")[0]).collect(Collectors.toList());
                if (!names.isEmpty()) {
                    chr.chatMessage(group + ": " + String.join(", ", names));
                }
            }
            chr.chatMessage("Type @help shortcuts to see what each one does.");
        }
    }

    // ---------------------------------------------------------------------------------------------------------------
    // Shortcuts: straight to a menu page or action (MenuModule.openPage opens menu.py on that page)
    // ---------------------------------------------------------------------------------------------------------------

    @Command(names = {"next"}, requiredType = Player)
    public static class Next extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.openPage(chr, "next", "");
        }
    }

    @Command(names = {"checkin"}, requiredType = Player)
    public static class CheckIn extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            chr.chatMessage("The daily check-in has been removed.");
        }
    }

    @Command(names = {"checklist"}, requiredType = Player)
    public static class Checklist extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.openPage(chr, "checklist", "");
        }
    }

    @Command(names = {"lfg", "party"}, requiredType = Player)
    public static class Lfg extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.openPage(chr, "lfg", "");
        }
    }

    @Command(names = {"hunt", "train"}, requiredType = Player)
    public static class Hunt extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.openPage(chr, "hunt", "");
        }
    }

    @Command(names = {"bosses", "boss"}, requiredType = Player)
    public static class Bosses extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.openPage(chr, "bosses", "");
        }
    }

    @Command(names = {"stronger"}, requiredType = Player)
    public static class Stronger extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.open(chr, "guide");
        }
    }

    @Command(names = {"char", "character"}, requiredType = Player)
    public static class CharacterPage extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.openPage(chr, "char", "");
        }
    }

    @Command(names = {"ap"}, requiredType = Player)
    public static class AbilityPoints extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.openPage(chr, "ap", "");
        }
    }

    /**
     * @str/@dex/@int/@luk: a positive amount (or "all") assigns AP to the stat; a negative amount
     * (or "-all") takes it back out as AP. The stat never goes below 4 and unspent AP never past 32,767
     * (MenuModule.getReturnableAbility).
     */
    private static void assignAbility(Char chr, String[] args, int index, String command) {
        int available = MenuModule.getAddableAbility(chr, index);
        int returnable = MenuModule.getReturnableAbility(chr, index);
        if (args.length != 2) {
            chr.chatMessage(String.format("Use @%s <amount> or @%s all to add, @%s -<amount> or @%s -all to remove. "
                    + "You can add %,d or remove %,d here.", command, command, command, command, available, returnable));
            return;
        }

        int amount;
        if (args[1].equalsIgnoreCase("all")) {
            amount = available;
        } else if (args[1].equalsIgnoreCase("-all")) {
            amount = -returnable;
        } else {
            try {
                amount = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                chr.chatMessage("Enter a whole number (negative to remove), or use @" + command + " all / -all.");
                return;
            }
        }
        if (amount == 0) {
            chr.chatMessage(args[1].startsWith("-")
                    ? "That stat can't go lower (4 is the minimum), or your unspent AP is full."
                    : available == 0 ? "You have no AP available for that stat." : "Enter an amount other than 0.");
            return;
        }
        if (amount > available) {
            chr.chatMessage(String.format("You can assign at most %,d AP to that stat right now.", available));
            return;
        }
        if (-amount > returnable) {
            chr.chatMessage(String.format("You can remove at most %,d from that stat right now (it stays at 4 or more"
                    + "%s).", returnable, returnable < MenuModule.getAbilityAboveMinimum(chr, index)
                    ? ", and unspent AP can't go past 32,767" : ""));
            return;
        }
        chr.chatMessage(MenuModule.allocateAbility(chr, index, amount));
    }

    @Command(names = {"str"}, requiredType = Player)
    public static class Strength extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            assignAbility(chr, args, 0, "str");
        }
    }

    @Command(names = {"dex"}, requiredType = Player)
    public static class Dexterity extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            assignAbility(chr, args, 1, "dex");
        }
    }

    @Command(names = {"int"}, requiredType = Player)
    public static class Intelligence extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            assignAbility(chr, args, 2, "int");
        }
    }

    @Command(names = {"luk"}, requiredType = Player)
    public static class Luck extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            assignAbility(chr, args, 3, "luk");
        }
    }

    @Command(names = {"stats", "stat"}, requiredType = Player)
    public static class Stats extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.openPage(chr, "stats", "");
        }
    }

    @Command(names = {"rates", "rate", "exprate"}, requiredType = Player)
    public static class Rates extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            chr.chatMessage(String.format("Server rates: EXP x%d (x%d below level 10), Drop x%d, Meso x%d.",
                    ServerRates.getExpRate(), ServerRates.getLowLevelExpRate(), ServerRates.getDropRate(),
                    ServerRates.getMesoRate()));
            chr.chatMessage(String.format("Your bonuses on top: EXP +%d%%, Drop +%d%%, Meso +%d%%.",
                    chr.getTotalStat(BaseStat.expR) - 100, chr.getTotalStat(BaseStat.dropR) - 100,
                    chr.getTotalStat(BaseStat.mesoR) - 100));
        }
    }

    @Command(names = {"transmog"}, requiredType = Player)
    public static class Transmog extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.open(chr, "transmog");
        }
    }

    @Command(names = {"style", "hair", "face", "skin", "beauty", "salon"}, requiredType = Player)
    public static class Style extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.open(chr, "style");
        }
    }

    @Command(names = {"storage"}, requiredType = Player)
    public static class Storage extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            if (!MenuModule.isBusy(chr) && !serviceOff(chr, "storage")) {
                NewPlayerModule.markStep(chr, "storage");
                MenuModule.open(chr, "storage");
            }
        }
    }

    @Command(names = {"leaves", "leafshop"}, requiredType = Player)
    public static class Leaves extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            if (!busy(chr)) {
                MenuModule.open(chr, "leaves");
            }
        }
    }

    @Command(names = {"optimize", "optimise"}, requiredType = Player)
    public static class Optimize extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            if (!busy(chr) && !serviceOff(chr, "optimize")) {
                chr.getScriptManager().startScript(2007, "optimize", net.swordie.ms.scripts.ScriptType.Npc);
            }
        }
    }

    @Command(names = {"vmatrix"}, requiredType = Player)
    public static class VMatrix extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            if (!busy(chr) && !serviceOff(chr, "vmatrix")) {
                chr.getScriptManager().startScript(GameConstants.ARCHELLE_NPC, "VcoreUI", net.swordie.ms.scripts.ScriptType.Npc); // Archelle
            }
        }
    }

    @Command(names = {"agent"}, requiredType = Player)
    public static class Agent extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            if (!busy(chr)) {
                chr.getScriptManager().startScript(net.swordie.ms.world.SecretAgentEvent.AGENT_E, "A_office",
                        net.swordie.ms.scripts.ScriptType.Npc);
            }
        }
    }

    @Command(names = {"merge"}, requiredType = Player)
    public static class Merge extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            if (!busy(chr)) {
                chr.chatMessage(MenuModule.mergeStacks(chr));
            }
        }
    }

    @Command(names = {"gifts", "gift", "checkgifts"}, requiredType = Player)
    public static class Gifts extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.open(chr, "gifts");
        }
    }

    @Command(names = {"drops", "dropinfo"}, requiredType = Player)
    public static class Drops extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.openPage(chr, "drops", "");
        }
    }

    @Command(names = {"whodrops", "whodrop"}, requiredType = Player)
    public static class WhoDrops extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            String item = args.length > 1 ? String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length)) : "";
            MenuModule.openPage(chr, "whodrops", item);
        }
    }

    @Command(names = {"effects"}, requiredType = Player)
    public static class Effects extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            chr.chatMessage(MenuModule.toggleEffects(chr).replaceAll("#[a-z]", ""));
        }
    }

    @Command(names = {"mobinfo", "mi"}, requiredType = AccountType.Tester)
    public static class MobInfo extends PlayerCommand {

        public static void execute(Char chr, String[] args) {
            Rect rect = new Rect(
                    chr.getPosition().deepCopy().getX() - 200,
                    chr.getPosition().deepCopy().getY() - 200,
                    chr.getPosition().deepCopy().getX() + 200,
                    chr.getPosition().deepCopy().getY() + 200
            );
            Mob mob = chr.getField().getMobs().stream().filter(m -> rect.hasPositionInside(m.getPosition())).findFirst().orElse(null);
            if (mob != null) {
                Char controller = chr.getField().getLifeToControllers().get(mob);
                chr.chatMessage(SpeakerChannel, String.format("Object ID: %s | Template/Mob ID: %s | Level: %d | HP: %s/%s " +
                                "| MP: %s/%s | Left: %s | PDR: %s | MDR: %s " +
                                "| Controller: %s | Exp : %s | NX: %s",
                        NumberFormat.getNumberInstance(Locale.US).format(mob.getObjectId()),
                        NumberFormat.getNumberInstance(Locale.US).format(mob.getTemplateId()),
                        mob.getLevel(),
                        NumberFormat.getNumberInstance(Locale.US).format(mob.getHp()),
                        NumberFormat.getNumberInstance(Locale.US).format(mob.getMaxHp()),
                        NumberFormat.getNumberInstance(Locale.US).format(mob.getMp()),
                        NumberFormat.getNumberInstance(Locale.US).format(mob.getMaxMp()),
                        mob.isLeft(),
                        mob.getPdr(),
                        mob.getMdr(),
                        controller == null ? "null" : chr.getName(),
                        mob.getForcedMobStat().getExp(),
                        mob.getNxDropAmount()
                        )
                );
            } else {
                chr.chatMessage(SpeakerChannel, "Could not find mob.");
            }
        }
    }

    @Command(names = {"lifeinfo", "li"}, requiredType = AccountType.Tester)
    public static class LifeInfo extends PlayerCommand {

        public static void execute(Char chr, String[] args) {
            var ordereLifes = chr.getField().getLifes().values().stream()
                    .sorted(Comparator.comparingInt(Life::getObjectId))
                    .collect(Collectors.toList());
            for (var life : ordereLifes) {
                var str = String.format("%s", life);
                if (life instanceof Mob) {
                    var mob = (Mob) life;
                    str += String.format(", MaxHP: %d, HP: %d, ", mob.getMaxHp(), mob.getHp());
                }
                chr.chatMessage(str);
            }
        }

    }


    // Shortcuts for the other useful @menu pages: same checks as the menu entry

    private static void openService(Char chr, String service, String feature) {
        if (!busy(chr) && (service == null || !serviceOff(chr, service))) {
            MenuModule.open(chr, feature);
        }
    }

    @Command(names = {"petshop", "pets"}, requiredType = Player)
    public static class PetShop extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            openService(chr, "pet", "pet");
        }
    }

    @Command(names = {"cubeshop", "cubes"}, requiredType = Player)
    public static class CubeShop extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            openService(chr, null, "heroicCubes");
        }
    }

    @Command(names = {"coinshop", "coins"}, requiredType = Player)
    public static class CoinShop extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            openService(chr, "coins", "coins");
        }
    }

    @Command(names = {"ari", "nxshop"}, requiredType = Player)
    public static class Ari extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            openService(chr, "ari", "ari");
        }
    }

    @Command(names = {"fredrick"}, requiredType = Player)
    public static class Fredrick extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            openService(chr, null, "fredrick");
        }
    }

    @Command(names = {"settings"}, requiredType = Player)
    public static class Settings extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            openService(chr, null, "settings");
        }
    }

    @Command(names = {"playstyle"}, requiredType = Player)
    public static class PlayStyle extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            openService(chr, null, "playStyle");
        }
    }

    @Command(names = {"guide"}, requiredType = Player)
    public static class Guide extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.open(chr, "guide");
        }
    }

    @Command(names = {"mlg", "exchange"}, requiredType = Player)
    public static class MapleLeafGold extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.openPage(chr, "exchange", "");
        }
    }

    @Command(names = {"jobgear"}, requiredType = Player)
    public static class JobGear extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            if (!net.swordie.ms.client.character.JobAdvanceGearModule.hasGear(chr)) {
                chr.chatMessage("Your class has no job advancement gear to get back.");
                return;
            }
            MenuModule.openPage(chr, "jobgear", "");
        }
    }

    @Command(names = {"towns"}, requiredType = Player)
    public static class Towns extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.openPage(chr, "towns", "");
        }
    }

    @Command(names = {"bossenter", "bossportal"}, requiredType = Player)
    public static class BossEnter extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            if (!serviceOff(chr, "bossEntrances")) {
                MenuModule.openPage(chr, "bossentrance", "");
            }
        }
    }

    @Command(names = {"ia", "innerability"}, requiredType = Player)
    public static class InnerAbility extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.openPage(chr, "innerability", "");
        }
    }

    @Command(names = {"rankings", "rank", "ranks"}, requiredType = Player)
    public static class Rankings extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.openPage(chr, "rankings", "");
        }
    }

    @Command(names = {"social"}, requiredType = Player)
    public static class Social extends PlayerCommand {
        public static void execute(Char chr, String[] args) {
            MenuModule.openPage(chr, "social", "");
        }
    }
}
