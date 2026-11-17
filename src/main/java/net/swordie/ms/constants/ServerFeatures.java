package net.swordie.ms.constants;

import net.swordie.ms.client.character.BurningModule;
import net.swordie.orm.connection.HikariCPDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Quality-of-life switches, saved in the server_features table. A setting missing from the table uses its default,
 * so the defaults below are the server's normal behaviour.
 * <p>
 * Game code reads the values in effect with {@link #on}, {@link #getInt} and {@link #choice}: they come from an
 * immutable snapshot, so they never touch the database or take a lock. {@link #reload()} reads the table again and
 * swaps the snapshot; settings marked "needs restart" keep the value they had when the server started.
 * <p>
 * Static initialization here stays trivial on purpose: nothing below can throw while the class loads.
 */
public final class ServerFeatures {
    private static final Logger log = LogManager.getLogger();

    public static final String TABLE = "server_features";
    public static final String LOG_TABLE = "server_feature_log";

    public enum Type {
        BOOL, INT, CHOICE,
        /** a time zone id (IANA, e.g. America/Los_Angeles); choices are suggestions, any valid zone is accepted */
        ZONE
    }

    /** The groups the admin page shows, in this order. */
    public enum Category {
        DROPS("Drops and rates"),
        POWER("Power items"),
        BOSS_REWARDS("Boss rewards"),
        LOOT("Loot and QoL"),
        CLASSES("Classes and skills"),
        QUESTS("Quests and story"),
        RESETS("Daily and weekly resets"),
        SHOPS("Shops"),
        SERVICES("Menu services"),
        COMMANDS("Player commands"),
        ACCOUNTS("Accounts and characters"),
        BOTS("Bots"),
        SERVER("Server"),
        ;

        private final String label;

        Category(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public enum Feature {
        NEW_PLAYER_MODE("new_player_mode", Category.ACCOUNTS, "New player mode",
                "How characters play their story and job advancements: each player picks Express or Classic, everyone plays Express, everyone plays Classic, or the game's own way without the server's automatic skips. Each player's own pick is kept and counts again when this is set back to letting players choose. While \"Job paths\" is off, new characters play Classic in the first two modes (Express isn't offered); characters that already chose keep their choice.",
                "choice", new String[]{"choice", "express", "classic", "default"},
                new String[]{"Express and Classic (each player chooses)", "Express always", "Classic always",
                        "Default (no automatic skips)"}, true),
        AUTO_LOOT("auto_loot", Category.LOOT, "Auto-loot (normal monsters)",
                "Items and mesos from normal monsters go straight into the inventories of the players who fought them, instead of dropping on the ground.",
                GameConstants.AUTO_LOOT, false),
        AUTO_LOOT_BOSS("auto_loot_boss", Category.LOOT, "Auto-loot (bosses)",
                "Boss loot is shared out straight into the inventories of the players who fought the boss; whatever doesn't fit stays on the ground.",
                GameConstants.AUTO_LOOT_BOSS_DROPS, false),
        SILENT_LOOT("silent_loot", Category.LOOT, "Silent auto-loot",
                "Auto-looted items skip the falling and flying animations, so busy maps lag less. Only matters while auto-loot is on.",
                GameConstants.SILENT_AUTO_LOOT, false),
        ITEM_VAC("item_vac", Category.LOOT, "Item vac",
                "Players holding the item vac item (4030003) pull up the drops on their map every few seconds while they hunt.",
                true, false),
        EQUIP_DROPS_FOR_KILLER_ONLY("equip_drops_for_killer_only", Category.LOOT, "Equips for your class only",
                "Monsters only drop equips that the killer or a party member on the map can use (class, weapon type and gender).",
                GameConstants.EQUIP_DROPS_FOR_KILLER_ONLY, false),
        NO_GENERIC_SCROLL_DROPS("no_generic_scroll_drops", Category.LOOT, "No common scroll drops",
                "Monsters don't drop the ordinary equipment scrolls (like \"Scroll for Earring for DEX\"); special scrolls still drop.",
                GameConstants.NO_GENERIC_SCROLL_DROPS, false),
        NO_RECIPE_DROPS("no_recipe_drops", Category.LOOT, "No recipe drops",
                "Monsters never drop crafting recipes or the boxes that give random ones.",
                GameConstants.NO_RECIPE_DROPS, false),
        CUBE_FLAME_DROPS("cube_flame_drops", Category.POWER, "Cubes and flames from monsters",
                "Monsters of level 30 and up can drop a cube (Mystical below 120, Hard to 199, Solid from 200) and from level 60 a Rebirth Flame (Powerful below 160, Eternal from 160), each 1 in 5,000 kills before the drop rate. Boss clears drop 1-2 cubes of the boss's tier 30% of the time, 2-3 at 50% on Chaos, Hard, Extreme and Hell, before the drop rate. Off: no such drops.",
                true, false),
        QUEST_DROPS_ONLY_WHILE_NEEDED("quest_drops_only_while_needed", Category.LOOT, "Quest items only while needed",
                "Quest items only drop for players who still need more of them for a quest they're doing.",
                GameConstants.QUEST_DROPS_ONLY_WHILE_NEEDED, false),
        BOSS_UI_IGNORE_QUESTS("boss_ui_ignore_quests", Category.QUESTS, "Boss window skips prerequisite quests",
                "Every teleport button in the boss window works without the boss's story quests (not in the Default new player mode). Players see a change after they log in again or change channel.",
                GameConstants.BOSS_UI_IGNORE_QUEST_REQUIREMENTS, false),
        BOSS_COOLDOWNS("boss_cooldowns", Category.RESETS, "Boss cooldowns",
                "Bosses can only be entered again after their usual waiting time. Off: any boss can be entered again right away. Counts from the next entry or clear.",
                GameConstants.BOSS_COOLDOWNS, false),
        FREE_ENHANCE("free_enhance", Category.POWER, "Free @enhance",
                "Scrolling, Star Force and adding potentials with @enhance cost nothing (no Spell Traces or mesos).",
                GameConstants.AUTO_ENHANCE_FREE, false),
        SCROLLS_ALWAYS_SUCCEED("scrolls_always_succeed", Category.POWER, "Scrolls always succeed",
                "Every scroll, Spell Trace, potential scroll and mastery book works, and cursed scrolls never destroy items. Off: each uses its own success chance, as on the official servers, and so do @enhance's scrolls (bots excepted).",
                GameConstants.SCROLLS_ALWAYS_SUCCEED, false),
        STAR_FORCE_ALWAYS_SUCCEEDS("star_force_always_succeeds", Category.POWER, "Star Force always succeeds",
                "Every Star Force attempt (and Equipment Enhancement Scroll) adds a star and never destroys the item. Off: the official success, drop and destroy chances, also for @enhance, which then makes one attempt per star it shows (bots excepted).",
                true, false),
        PRIME_LINE_CHANCE("prime_line_chance", Category.POWER, "Prime line chance (%)",
                "Chance for each potential line after the first to be of the item's own rank. 0 = each cube's official chance (Red Cube 10% for the 2nd line and 1% for the 3rd, Black Cube 20% and 5%).",
                0, 0, 100, false),
        ALWAYS_THREE_POTENTIAL_LINES("always_three_potential_lines", Category.POWER, "Always 3 potential lines",
                "New and rerolled potential always has 3 lines. Off: potential scrolls and dropped items get a 3rd line 25% of the time (Special Potential Scrolls always), and cubes keep the number of lines.",
                false, false),
        LUCK_BOOST("luck_boost", Category.POWER, "Luck boost (%)",
                "Multiplies the odds of every random power gain, in percent of the official odds: potential ranks on drops, cube and meso reset rank-ups, higher flame tiers, Star Force, scroll, Spell Trace, potential scroll and mastery book success, Inner Ability and familiar rank-ups. 200 = double the official odds (capped at 100%); 100 = official. Star Force's destroy share of a failure stays the same, so destroys get rarer.",
                200, 100, 1000, false),

        SWORDIE_COINS("swordie_coins", Category.SHOPS, "Swordie Coins and the coin shop",
                "Players earn Swordie Coins (first weekly clear of each boss, level 200/250/260/270, Maple Achievements, Daily Gift days 1 and 15, rebirths, Polo's and Fritto's bounty games) and spend them in the Swordie Coin Shop (@shop): cosmetics, bags and capped time-savers. Off: no coins are given and the shop is closed; coins players have are kept.",
                true, false),
        REMOTE_SHOPS("remote_shops", Category.SERVICES, "Shops in @menu",
                "The shops in @menu > Shops open from anywhere. Off: the shops that have an NPC in town (Regular Shop: Collector at the Free Market entrance; Android Shop: Lauren in Henesys; crafted goods: Gere in Ardentmill) are left out of the list; the Legion shop stays, as it has no town NPC.",
                true, false),
        REMOTE_SELL("remote_sell", Category.SERVICES, "Sell items from anywhere (@sell)",
                "Players can sell items from anywhere with @sell or @menu. Off: they sell at a shop, or to Miss Richfield at the Free Market entrance.",
                true, false),
        REMOTE_ARI("remote_ari", Category.SERVICES, "Ari from anywhere",
                "Ari's shop (NX chairs, damage skins, megaphones) opens from @menu. Off: players visit Ari at the Free Market entrance.",
                true, false),
        REMOTE_PETS("remote_pets", Category.SERVICES, "Pet package in @menu",
                "Players can buy the pet package (a pet with every pet skill) from @menu > Shops. Off: it isn't sold; pets that were already bought are kept.",
                true, false),
        REMOTE_STORAGE("remote_storage", Category.SERVICES, "Storage from anywhere (@storage)",
                "Players can open their storage from anywhere with @storage or @menu. Off: they use a storage keeper in town.",
                true, false),
        REMOTE_ENHANCE("remote_enhance", Category.SERVICES, "@enhance helper",
                "@enhance scrolls, Star Forces, adds potentials to and cubes worn gear in one menu. Off: players use scrolls, cubes and the Star Force window themselves; gear already enhanced keeps everything.",
                true, false),
        REMOTE_OPTIMIZE("remote_optimize", Category.SERVICES, "@optimize helper",
                "@optimize puts on the best gear and sets up potentials, flames, Hyper Stats and Boost Nodes for the player. Off: players do this themselves; nothing they have is changed.",
                true, false),
        REMOTE_VMATRIX("remote_vmatrix", Category.SERVICES, "V Matrix helper from anywhere (@vmatrix)",
                "Archelle's V Matrix helper (equip your best Boost Nodes, clear level 1 nodes) opens from anywhere. Off: players talk to Archelle in Nameless Town; the game's own V Matrix window still works.",
                true, false),
        REMOTE_REBIRTH("remote_rebirth", Category.SERVICES, "Rebirth from anywhere (@rebirth)",
                "Players can open the rebirth choices from anywhere with @rebirth or @menu. Off: they talk to Lawry in Reverse City (Subway Tunnel Refuge); rebirths already done are kept.",
                true, false),
        REMOTE_BOSS_ENTRY("remote_boss_entry", Category.SERVICES, "Boss entrances in @menu",
                "@menu > Bosses > Boss entrances teleports players to any boss's entrance (and opens Seren's and Princess Sakuno's entry) from anywhere. Off: players travel to the entrances themselves.",
                true, false),

        CMD_GO("cmd_go", Category.COMMANDS, "@go", "Players can type @go <town> to travel to a town.", true, false),
        CMD_JOB("cmd_job", Category.COMMANDS, "@job",
                "Players can type @job (choose or check their job path). The Job path entry in @menu follows the \"Job paths\" setting instead.",
                true, false),
        CMD_REBIRTH("cmd_rebirth", Category.COMMANDS, "@rebirth", "Players can type @rebirth to open the rebirth choices.", true, false),
        CMD_ENHANCE("cmd_enhance", Category.COMMANDS, "@enhance", "Players can type @enhance to open the enhancing helper.", true, false),
        CMD_SELL("cmd_sell", Category.COMMANDS, "@sell", "Players can type @sell to sell items.", true, false),
        CMD_SHOP("cmd_shop", Category.COMMANDS, "@shop", "Players can type @shop to open the shop list.", true, false),
        CMD_STORAGE("cmd_storage", Category.COMMANDS, "@storage", "Players can type @storage to open their storage.", true, false),
        CMD_OPTIMIZE("cmd_optimize", Category.COMMANDS, "@optimize", "Players can type @optimize to open the gear optimizer.", true, false),
        CMD_VMATRIX("cmd_vmatrix", Category.COMMANDS, "@vmatrix", "Players can type @vmatrix to open the V Matrix helper.", true, false),
        CMD_BURNING("cmd_burning", Category.COMMANDS, "@burning",
                "Players can type @burning to make a character a Burning character (@burning end ends it). Off: the command is closed; the Burning setting decides whether characters can burn at all.",
                true, false),
        CMD_GEAR("cmd_gear", Category.COMMANDS, "@gear and @upgrades",
                "Players can type @gear (empty slots and where to farm them) and @upgrades (equip better gear they carry).",
                true, false),
        CMD_STATS("cmd_stats", Category.COMMANDS, "@str, @dex, @int, @luk",
                "Players can type @str/@dex/@int/@luk to spend ability points or take them back out. The Ability points page in @menu stays.",
                true, false),
        CMD_JOINGUILD("cmd_joinguild", Category.COMMANDS, "@joinguild", "Players can type @joinguild <name> to join a guild without an invitation.", true, false),
        CMD_TRANSMOG("cmd_transmog", Category.COMMANDS, "@transmog", "Players can type @transmog to open transmog.", true, false),
        CMD_STORY("cmd_story", Category.COMMANDS, "@story",
                "Express players can type @story <region> to keep a region's story (Arcane River, Grandis) instead of having it skipped when they reach it, and @story to see where each region's story stands.",
                true, false),

        JOB_PATHS("job_paths", Category.CLASSES, "Job paths (@job)",
                "From level 10 a beginner can pick any class with @job and then advances automatically. Off: no new job paths (characters that already have one, reborn characters and bots keep theirs), and new characters play Classic (Express isn't offered, as it skips the tutorial that gives some classes their first job); characters that chose Express before keep it.",
                true, false),
        JOB_ADVANCEMENT_BY_QUEST_ONLY("job_advancement_by_quest_only", Category.CLASSES, "Job advancements by quest only",
                "Characters without a job path advance through their class's official job quests (5th job included). Off: classes that can advance on their own (Evan, Kain, Shade, ...) do it at the level up, and 5th job opens by itself at level 200.",
                GameConstants.JOB_ADVANCEMENT_BY_QUEST_ONLY, false),
        ARCANE_SKIP("arcane_skip", Category.QUESTS, "Arcane River story skip",
                "5th job characters can skip the Arcane River storylines (Chief Legato and Spiegelmann offer it, and talking to them any time). Off: no offers and no skipping; stories already skipped stay skipped.",
                true, false),
        ARCANE_WEEKLY_MINIGAMES("arcane_weekly_minigames", Category.RESETS, "Arcane River weekly minigames",
                "Erda Spectrum (Vanishing Journey), Hungry Muto (Chu Chu Island), Midnight Chaser (Lachelein), Spirit Savior (Arcana), Ranheim Defense (Morass) and Esfera Guardian (Esfera) are open at their NPCs, with their instant clears. Off: the NPCs say the minigame isn't open.",
                true, false),
        ARCANE_SKIP_SYMBOLS("arcane_skip_symbols", Category.QUESTS, "Arcane Symbols from the skip",
                "Skipping an Arcane River story also gives that area's Arcane Symbol. Off: the skip completes the quests only. Only matters while the skip is on.",
                true, false),
        SIXTH_JOB_QUESTLINE("sixth_job_questline", Category.QUESTS, "6th job questline",
                "At level 260, with the 5th job and [Convergence] True Form done, the 6th job is earned through the [6th Job] quests in the Erda Torrent (the Maple Administrator takes you there). Classic characters only; Express gets the 6th job at 260. Off: everyone gets it at level 260 after the 5th.",
                true, false),
        CUSTOM_EXP_CURVE("custom_exp_curve", Category.DROPS, "Gentler EXP curve after 200",
                "From level 200 each level takes only a little longer than the last, instead of the official jumps at 210, 220, ... 250. Takes effect at the next server start.",
                GameConstants.CUSTOM_EXP_CURVE, true),

        NEXT_HELPER("next_helper", Category.ACCOUNTS, "\"What should I do next?\" (@next)",
                "@next and the top of @menu suggest the character's next useful steps, each one click away.",
                true, false),
        SPROUT_BONUS("sprout_bonus", Category.DROPS, "Sprout party EXP bonus",
                "Party members hunting with a new account's character below level 100 get up to +30% EXP, and so does the new character.",
                true, false),
        RESTED_EXP("rested_exp", Category.DROPS, "Rested EXP",
                "Time spent logged out builds bonus EXP (doubled hunting EXP until it's used), up to one level's worth after a day away. Off: no rested EXP is built or used; what a character has saved stays for later.",
                true, false),
        ONBOARDING_TIPS("onboarding_tips", Category.ACCOUNTS, "Welcome messages and tips",
                "A welcome on a character's first login, a welcome box in its first town and a tip at a few levels. The new player guide in @menu stays either way.",
                GameConstants.ONBOARDING_TIPS, false),
        BURNING("burning", Category.ACCOUNTS, "Burning characters",
                "Hyper Burning MAX (each level up from 10 gives 4 more levels until 260) and Burning Beyond (+1 per level up from 260 to 270), up to " + BurningModule.MAX_BURNING_PER_ACCOUNT + " characters at once per account, for both play styles. A new character is asked once after its play style choice; the character select Burning button and @menu > My character offer it too, and a Burning character can end its Burning early. Off: no new Burning characters; existing ones keep burning.",
                true, false),
        EXPRESS_KIT("express_kit", Category.ACCOUNTS, "Express arrival kit",
                "A character that becomes Express gets, once, what the skipped story would have given it (resources/express-kit.tsv: items, quest completions, records) and a note listing what Express opens. Off: nothing is given.",
                true, false),
        PLUS_GIFT_BOXES("plus_gift_boxes", Category.ACCOUNTS, "Plus Gift Boxes",
                "Each account gets the eight Plus Gift Boxes once, the first time one of its characters reaches level 30, 50, 60, 80, 90, 110, 120 and 150 (characters already past a level get theirs on their next login). Contents in resources/item-box-rewards.tsv. Off: no new boxes are given; boxes already given still open.",
                true, false),
        MAX_SLOTS("max_slots", Category.ACCOUNTS, "Every slot open",
                "Accounts and characters have every slot the Cash Shop sells: " + net.swordie.ms.ServerConstants.MAX_CHARACTERS + " character slots, " + net.swordie.ms.client.character.items.Inventory.MAX_SLOTS + " slots in each inventory tab, " + GameConstants.MAX_TRUNK_SIZE + " storage slots, a full Beauty Album, " + GameConstants.MAX_FAMILIAR_SLOTS + " familiar slots and the second pendant slot. Older accounts and characters get them at their next login. Off: new accounts and characters start with the usual counts and expand them in the Cash Shop; slots already open stay open.",
                true, false),

        BOTS_JOIN_LFG("bots_join_lfg", Category.BOTS, "Bots join looking-for-group posts",
                "A bot of the right level may join a player's @lfg post that still has room after a couple of minutes (one bot per party, never the last free slot) for a party quest it can help with, a boss or training. It follows the leader, fights next to them, only picks up its own loot and leaves after the run. Off: no bot joins a post; bots already in a party leave after their run.",
                true, false),

        ERDA_REQUEST_DAILY("erda_request_daily", Category.RESETS, "Erda's Request every day",
                "Erda's Request (Lv. 260+ after the 6th job, 5,000 Sacred Power enemies) can be done by every character every day for 3 Sol Erda. Off: as on the official servers: once a week per login, shared by every world (Thursday reset), for 9 Sol Erda and 90 Sol Erda Fragments.",
                true, false),
        EPIC_DUNGEON_PER_CHARACTER("epic_dungeon_per_character", Category.RESETS, "Epic Dungeons for every character",
                "Every character can clear each Epic Dungeon (High Mountain, Angler Company) once a week. Off: as on the official servers: two Epic Dungeon clears a week per account, one dungeon per character (Thursday reset).",
                true, false),
        EPIC_DUNGEON_UNLIMITED("epic_dungeon_unlimited", Category.RESETS, "Unlimited Epic Dungeon runs",
                "Epic Dungeons (High Mountain, Angler Company) can be run again as soon as the last clear reward is claimed, with the full clear reward every time. Off: the weekly limits above apply.",
                true, false),
        MONSTER_PARK_UNLIMITED("monster_park_unlimited", Category.RESETS, "Unlimited Monster Park",
                "Monster Park can be cleared any number of times a day, with no entry tickets; the first clear of the day still gives the bonus coins. Off: as on the official servers: 7 clears a day per character, 14 per world, the first 2 free and the rest a ticket each.",
                true, false),
        MONSTER_PARK_EXTREME_UNLIMITED("monster_park_extreme_unlimited", Category.RESETS, "Unlimited Monster Park Extreme",
                "Monster Park Extreme can be cleared any number of times. Off: as on the official servers: one clear a week per character, two per world (Thursday reset).",
                true, false),
        ARCANE_WEEKLY_UNLIMITED("arcane_weekly_unlimited", Category.RESETS, "Unlimited Arcane River weekly content",
                "Arcane River weekly content (Spirit Savior, Midnight Chaser, ...) and its instant clears can be done any number of times. Off: as on the official servers: 3 clears a week per character (Thursday reset).",
                true, false),

        RESET_TIME_ZONE("reset_time_zone", Category.SERVER, "Reset time zone",
                "Daily, weekly and monthly resets (dailies, Daily Gift, boss clears, weekly quests, guild weeks, Monster Park, Arcane River, ...) happen at midnight in this time zone, and the game's clock shows this zone's time, so the day players see turns with the resets. Takes effect at the next server start. When it changes, no day is repeated: moving to a later midnight, the current day runs on until midnight in the new zone; moving to an earlier one, the next day starts at the restart.",
                Type.ZONE, net.swordie.ms.util.ResetClock.DEFAULT_ZONE_ID, 0, 0,
                new String[]{"America/Los_Angeles",
                        "America/Denver",
                        "America/Phoenix",
                        "America/Chicago",
                        "America/New_York",
                        "America/Anchorage",
                        "Pacific/Honolulu",
                        "America/Sao_Paulo",
                        "UTC",
                        "Europe/London",
                        "Europe/Berlin",
                        "Europe/Moscow",
                        "Asia/Singapore",
                        "Asia/Shanghai",
                        "Asia/Seoul",
                        "Asia/Tokyo",
                        "Australia/Sydney"},
                new String[]{"Pacific (Los Angeles)", "Mountain (Denver)", "Mountain, no daylight saving (Phoenix)",
                        "Central (Chicago)", "Eastern (New York)", "Alaska (Anchorage)", "Hawaii (Honolulu)",
                        "Brasilia (Sao Paulo)", "UTC (as GMS)", "UK (London)", "Central Europe (Berlin)",
                        "Moscow", "Singapore (as MapleSEA)", "China (Shanghai)", "Korea (Seoul, as KMS)",
                        "Japan (Tokyo)", "Australia East (Sydney)"}, true),

        // drops and rates
        MOB_SPAWN_RATE("mob_spawn_rate", Category.DROPS, "Monsters per map (%)",
                "How many monsters a map holds, in % of the official count. Takes effect at the next server start.",
                (int) Math.round(GameConstants.MOB_SPAWN_RATE_MULTIPLIER * 100), 25, 400, true),
        SPELL_TRACE_DROP_CHANCE("spell_trace_drop_chance", Category.DROPS, "Spell Trace drop chance (%)",
                "How often monsters drop Spell Traces, in % of the drop tables (most monsters are at 0.5% before the drop rate). The Spell Trace rate sets how many drop.",
                100, 0, 1000, false),
        CHARM_EXP_MULTIPLIER("charm_exp_multiplier", Category.DROPS, "Charm EXP from items (x)",
                "Charm EXP from equipping or using items and from titles is multiplied by this. 1 = official.",
                GameConstants.CHARM_EXP_MULTIPLIER, 1, 50, false),

        // power items
        SOL_ERDA_ENERGY_CHANCE("sol_erda_energy_chance", Category.POWER, "Faint Sol Erda Energy chance (per 10,000)",
                "Chance a Lv. 260+ monster in Grandis drops Faint Sol Erda Energy for a 6th job killer, out of 10,000, before the drop rate and the Sol Erda rate. 0 = none.",
                net.swordie.ms.client.character.hexa.HexaEffects.FAINT_ENERGY_DROP_CHANCE, 0, 10000, false),
        SOL_ERDA_FRAGMENT_CHANCE("sol_erda_fragment_chance", Category.POWER, "Sol Erda Fragment chance (per 10,000)",
                "Chance a Lv. 260+ monster in Grandis drops a Sol Erda Fragment for a 6th job killer, out of 10,000, before the drop rate and the Sol Erda rate. 0 = none.",
                net.swordie.ms.client.character.hexa.HexaEffects.FRAGMENT_DROP_CHANCE, 0, 10000, false),
        ARCANE_SYMBOL_DROPS("arcane_symbol_drops", Category.POWER, "Arcane Symbol drops (%)",
                "How often Arcane River monsters drop Arcane Symbols, in % of this server's tables (about 10% a kill before the drop rate). Official is about 3. 0 = none.",
                100, 0, 1000, false),
        SACRED_SYMBOL_DROPS("sacred_symbol_drops", Category.POWER, "Sacred Symbol drops (%)",
                "How often Grandis monsters drop Sacred Symbols, in % of this server's tables (about 10% a kill before the drop rate). Official is about 1. 0 = none.",
                100, 0, 1000, false),
        NODESTONE_DROPS("nodestone_drops", Category.POWER, "Nodestone drops (%)",
                "How often monsters drop Nodestones, in % of this server's tables (twice the official chance). 0 = none.",
                100, 0, 1000, false),
        MEDAL_OF_HONOR_CHANCE("medal_of_honor_chance", Category.POWER, "Medal of Honor chance (per 10,000)",
                "Chance a Lv. 30+ monster drops a Medal of Honor (Honor EXP for Inner Ability), out of 10,000, before the drop rate. Boss clears always drop a Boss Medal of Honor. 0 = none.",
                ItemConstants.MEDAL_OF_HONOR_DROP_CHANCE, 0, 10000, false),
        MOB_CUBE_FLAME_CHANCE("mob_cube_flame_chance", Category.POWER, "Cube and flame chance (per 10,000)",
                "Chance a monster drops a cube, and separately a flame, out of 10,000, before the drop rate. Only while \"Cubes and flames from monsters\" is on.",
                net.swordie.ms.life.drop.CubeDrops.MOB_CHANCE, 0, 10000, false),
        HEROIC_MESO_FLAME("heroic_meso_flame", Category.POWER, "Flame resets for mesos",
                "The flame window can reroll bonus stats for mesos, like on Heroic worlds. Off: flames only.",
                true, false),
        HEROIC_MESO_POTENTIAL_RESET("heroic_meso_potential_reset", Category.POWER, "Potential resets for mesos",
                "The potential window can reroll potentials for mesos, like on Heroic worlds. Off: cubes only.",
                true, false),
        EXTRACT_SPELL_TRACES("extract_spell_traces", Category.POWER, "Spell Traces from disassembling",
                "Disassembling equips gives Spell Traces on top of the Magic Powder.",
                true, false),

        // boss rewards
        BOSS_CUBE_CHANCE("boss_cube_chance", Category.BOSS_REWARDS, "Cubes from boss clears (%)",
                "Chance a Normal or Easy boss clear drops 1-2 cubes of the boss's tier, before the drop rate. Only while \"Cubes and flames from monsters\" is on.",
                net.swordie.ms.life.drop.CubeDrops.BOSS_CHANCE / 100, 0, 100, false),
        BOSS_CUBE_CHANCE_HARD("boss_cube_chance_hard", Category.BOSS_REWARDS, "Cubes from hard boss clears (%)",
                "Same for Chaos, Hard, Extreme and Hell clears, which drop 2-3.",
                net.swordie.ms.life.drop.CubeDrops.BOSS_HARD_CHANCE / 100, 0, 100, false),
        BOSS_SOUL_CHANCE("boss_soul_chance", Category.BOSS_REWARDS, "Souls from boss clears (%)",
                "Chance a boss clear drops one of that boss's souls. 0 = none.",
                SoulWeaponData.BOSS_SOUL_CHANCE / 100, 0, 100, false),
        FIELD_BOSS_SOUL_CHANCE("field_boss_soul_chance", Category.BOSS_REWARDS, "Souls from field bosses (%)",
                "Same for field bosses like Rex or Pianus. 0 = none.",
                SoulWeaponData.AREA_BOSS_SOUL_CHANCE / 100, 0, 100, false),
        MAGNIFICENT_SOUL_CHANCE("magnificent_soul_chance", Category.BOSS_REWARDS, "Magnificent souls (%)",
                "Share of dropped souls that are Magnificent.",
                SoulWeaponData.MAGNIFICENT_SOUL_PERCENT, 0, 100, false),
        SOUL_SHARD_DROPS("soul_shard_drops", Category.BOSS_REWARDS, "Soul Shard drops (%)",
                "How often bosses drop Soul Shards, in % of this server's tables (30% a clear). Official is about 3 (1% a clear).",
                100, 0, 1000, false),
        CRA_RUNES("cra_runes", Category.BOSS_REWARDS, "Chaos Root Abyss runes per clear",
                "How many of each Yggdrasil Rune the Chaos Root Abyss bosses drop. Official is 1.",
                4, 0, 100, false),
        PIECE_BOXES("piece_boxes", Category.BOSS_REWARDS, "Piece boxes per clear",
                "How many \"Piece of\" boxes the Chaos Root Abyss bosses and Cygnus drop. Official is 1.",
                5, 0, 100, false),
        PIECE_BOX_CHANCE("piece_box_chance", Category.BOSS_REWARDS, "Piece box chance (%)",
                "Chance those bosses drop their Piece boxes, before the drop rate. Official is 35.",
                100, 0, 100, false),
        GOLLUX_PENNIES("gollux_pennies", Category.BOSS_REWARDS, "Gollux Pennies (%)",
                "How many Gollux Pennies the Gollux fight drops, in % of this server's tables (3x official). Official is 33.",
                100, 0, 1000, false),
        GOLLUX_COINS("gollux_coins", Category.BOSS_REWARDS, "Gollux Coins (%)",
                "How many Gollux Coins Gollux drops, in % of this server's tables (5x official). Official is 20.",
                100, 0, 1000, false),
        BOSS_CORES_DROPLETS("boss_cores_droplets", Category.BOSS_REWARDS, "Lotus cores and Lucid droplets (%)",
                "How many Extraordinary Energy Cores (Lotus) and Butterfly Droplet Stones (Lucid) drop, in % of this server's tables (2x official). Official is 50.",
                100, 0, 1000, false),
        ETERNAL_BOX_DROPS("eternal_box_drops", Category.BOSS_REWARDS, "Eternal box drops (%)",
                "How often Eternal Armor Boxes drop, in % of this server's tables (10% on Chaos and Hard, 20% on Extreme). Official is 10.",
                100, 0, 1000, false),
        BOSS_EQUIP_BOOST("boss_equip_boost", Category.BOSS_REWARDS, "Better boss equip odds (%)",
                "Boss equips get this much more likely to drop, and rare ones (under 10% or 20% with the drop rate) go up to 15% or 25%. 0 = official odds.",
                GameConstants.BOSS_EQUIP_DROP_BONUS_PERCENT, 0, 300, false),
        CRYSTAL_TOP_PRICE("crystal_top_price", Category.BOSS_REWARDS, "Top boss crystal price (millions)",
                "0 = each crystal sells for its GMS price. Set a price in millions of mesos for the most valuable crystal and the others scale down from it (for high-cost servers).",
                0, 0, 99_999, false),
        BOSS_FIRST_CLEAR_ANNOUNCE("boss_first_clear_announce", Category.BOSS_REWARDS, "First clear announcements",
                "The whole world sees it when someone clears a boss and difficulty for the first time.",
                GameConstants.BOSS_FIRST_CLEAR_ANNOUNCE, false),

        // loot and QoL
        NO_MAXED_SYMBOL_DROPS("no_maxed_symbol_drops", Category.LOOT, "No drops of maxed symbols",
                "Monsters stop dropping an Arcane or Sacred Symbol for a player who already has it at max level.",
                GameConstants.NO_DROPS_FOR_MAXED_ARCANE_SYMBOLS, false),
        AUTO_MERGE_SYMBOLS("auto_merge_symbols", Category.LOOT, "Symbols merge by themselves",
                "A new Arcane or Sacred Symbol goes straight into the one you wear and levels it up as far as your mesos allow.",
                GameConstants.AUTO_MERGE_SYMBOLS, false),
        SUMMARIZE_EQUIP_LOOT("summarize_equip_loot", Category.LOOT, "One message for looted equips",
                "One line (\"3 Unique Equips, 5 Equips Obtained.\") instead of a message per equip picked up.",
                GameConstants.SUMMARIZE_EQUIP_LOOT, false),
        EXPIRY_WARNINGS("expiry_warnings", Category.LOOT, "Expiring item warnings",
                "Players are told when an item is about to expire.",
                GameConstants.EXPIRY_WARNINGS, false),

        // classes and skills
        RESET_COOLDOWNS_AFTER_BOSS("reset_cooldowns_after_boss", Category.CLASSES, "Cooldowns end after bosses",
                "Leaving a boss fight ends all skill cooldowns.",
                GameConstants.RESET_COOLDOWNS_AFTER_BOSS, false),
        JOB_PATH_REFUND_AP("job_path_refund_ap", Category.CLASSES, "AP back on the first @job",
                "A beginner's first @job choice puts STR/DEX/INT/LUK back to 4 and returns the rest as AP.",
                GameConstants.JOB_PATH_REFUND_ABILITY_POINTS, false),

        // quests and story
        QUEST_READY_NOTICE("quest_ready_notice", Category.QUESTS, "Quest ready notices",
                "Players are told when a quest is ready to turn in, and to whom.",
                GameConstants.QUEST_READY_NOTICE, false),

        // shops
        GOLDEN_LEAVES("golden_leaves", Category.SHOPS, "Golden Maple Leaves",
                "Mesos past the limit below turn into Golden Maple Leaves (1 per billion), spent or traded back with @leaves. Off: mesos stop at the game's cap.",
                true, false),
        GOLDEN_LEAF_KEEP("golden_leaf_keep", Category.SHOPS, "Mesos kept before Leaves (billions)",
                "Mesos up to this many billions stay mesos.",
                (int) (net.swordie.ms.client.character.GoldenLeafModule.KEEP_MESOS / 1_000_000_000L), 1, 99, false),
        CASH_SHOP_PREFER_PREPAID("cash_shop_prefer_prepaid", Category.SHOPS, "Cash Shop spends NX Prepaid first",
                "Cash Shop buys use NX Prepaid first, whichever NX type is picked.",
                GameConstants.CASH_SHOP_PREFER_PREPAID, false),

        // menu services
        ENHANCE_TRACE_MARKUP("enhance_trace_markup", Category.SERVICES, "@enhance Spell Trace markup (%)",
                "Extra Spell Traces @enhance charges over scrolling by hand.",
                GameConstants.AUTO_ENHANCE_TRACE_MARKUP_PERCENT, 0, 1000, false),
        ENHANCE_MESO_MARKUP("enhance_meso_markup", Category.SERVICES, "@enhance meso markup (%)",
                "Extra mesos @enhance charges over doing it by hand.",
                GameConstants.AUTO_ENHANCE_MESO_MARKUP_PERCENT, 0, 1000, false),
        OPTIMIZE_MESO_MARKUP("optimize_meso_markup", Category.SERVICES, "@optimize meso markup (%)",
                "Extra mesos @optimize charges for Star Force, flames and cubes over doing it by hand.",
                GameConstants.OPTIMIZE_MESO_MARKUP_PERCENT, 0, 1000, false),
        OPTIMIZE_FEE("optimize_fee", Category.SERVICES, "@optimize fee (% of mesos)",
                "What each @optimize run costs, in % of the mesos you carry.",
                GameConstants.OPTIMIZE_CONVENIENCE_FEE_PERCENT, 0, 100, false),
        TRANSMOG_FROM_STORAGE("transmog_from_storage", Category.SERVICES, "Transmog from storage",
                "@transmog can copy looks from items in storage too.",
                GameConstants.TRANSMOG_FROM_STORAGE, false),

        // accounts and characters
        REBIRTH_MAX("rebirth_max", Category.ACCOUNTS, "Rebirths per character",
                "How many times a character can be reborn. 0 = no limit.",
                GameConstants.REBIRTH_MAX_COUNT, 0, 1000, false),
        REBIRTH_BONUS_AP("rebirth_bonus_ap", Category.ACCOUNTS, "Bonus AP per rebirth",
                "Extra AP for each rebirth done, given at every rebirth (5 = 5, 10, 15, ...).",
                GameConstants.REBIRTH_BONUS_AP, 0, 100, false),
        REBIRTH_RESET_STATS("rebirth_reset_stats", Category.ACCOUNTS, "Rebirth resets stats",
                "A rebirth puts STR/DEX/INT/LUK, max HP/MP and AP back to a new character's. Off: stats are kept.",
                GameConstants.REBIRTH_RESET_STATS, false),
        REBIRTH_PASSIVES_STACK("rebirth_passives_stack", Category.ACCOUNTS, "Passives from earlier lives",
                "Passive skills of a reborn character's earlier classes keep counting. Takes effect at the next server start.",
                GameConstants.REBIRTH_PASSIVES_STACK, true),

        // server
        AUTOSAVE_MINUTES("autosave_minutes", Category.SERVER, "Autosave (minutes)",
                "How often everyone online is saved. 0 = only when the server stops. Takes effect at the next server start.",
                5, 0, 120, true),
        ;

        private final String key;
        private final Category category;
        private final String label;
        private final String description;
        private final Type type;
        /** Text form of the default: "1"/"0" for switches, a number, or one of {@link #choices}. */
        private final String defaultText;
        private final int min;
        private final int max;
        private final List<String> choices;
        /** Names the page shows for the choices (same order as {@link #choices}); empty: the choices themselves. */
        private final Map<String, String> choiceLabels;
        private final boolean restartRequired;

        /** An on/off switch. */
        Feature(String key, Category category, String label, String description, boolean defaultValue,
                boolean restartRequired) {
            this(key, category, label, description, Type.BOOL, defaultValue ? "1" : "0", 0, 1, null, restartRequired);
        }

        /** A whole number between min and max. */
        Feature(String key, Category category, String label, String description, int defaultValue, int min, int max,
                boolean restartRequired) {
            this(key, category, label, description, Type.INT, String.valueOf(defaultValue), min, max, null,
                    restartRequired);
        }

        /** One of a fixed list of values. */
        Feature(String key, Category category, String label, String description, String defaultValue,
                String[] choices, boolean restartRequired) {
            this(key, category, label, description, Type.CHOICE, defaultValue, 0, 0, choices, null, restartRequired);
        }

        /** One of a fixed list of values, each with the name the page shows for it (choiceLabels[i] for choices[i]). */
        Feature(String key, Category category, String label, String description, String defaultValue,
                String[] choices, String[] choiceLabels, boolean restartRequired) {
            this(key, category, label, description, Type.CHOICE, defaultValue, 0, 0, choices, choiceLabels,
                    restartRequired);
        }

        Feature(String key, Category category, String label, String description, Type type, String defaultText,
                int min, int max, String[] choices, boolean restartRequired) {
            this(key, category, label, description, type, defaultText, min, max, choices, null, restartRequired);
        }

        Feature(String key, Category category, String label, String description, Type type, String defaultText,
                int min, int max, String[] choices, String[] choiceLabels, boolean restartRequired) {
            this.key = key;
            this.category = category;
            this.label = label;
            this.description = description;
            this.type = type;
            this.defaultText = defaultText;
            this.min = min;
            this.max = max;
            this.choices = choices == null ? List.of() : List.of(choices);
            Map<String, String> labels = new LinkedHashMap<>();
            if (choices != null && choiceLabels != null) {
                for (int i = 0; i < Math.min(choices.length, choiceLabels.length); i++) {
                    labels.put(choices[i], choiceLabels[i]);
                }
            }
            this.choiceLabels = Collections.unmodifiableMap(labels);
            this.restartRequired = restartRequired;
        }

        public String getKey() {
            return key;
        }

        public Category getCategory() {
            return category;
        }

        public String getLabel() {
            return label;
        }

        public String getDescription() {
            return description;
        }

        public Type getType() {
            return type;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }

        public List<String> getChoices() {
            return choices;
        }

        /** @return choice -> the name the page shows for it (empty when the choices are shown as they are) */
        public Map<String, String> getChoiceLabels() {
            return choiceLabels;
        }

        public boolean isRestartRequired() {
            return restartRequired;
        }

        /**
         * @return the official servers' value in the same text form as the default, or null when there's no official
         * equivalent (our own features)
         */
        public String getVanillaText() {
            return switch (this) {
                case NEW_PLAYER_MODE -> "default";
                case AUTO_LOOT, AUTO_LOOT_BOSS, ITEM_VAC, EQUIP_DROPS_FOR_KILLER_ONLY, NO_GENERIC_SCROLL_DROPS,
                     NO_RECIPE_DROPS, BOSS_UI_IGNORE_QUESTS, SCROLLS_ALWAYS_SUCCEED, STAR_FORCE_ALWAYS_SUCCEEDS,
                     ARCANE_SKIP, CUSTOM_EXP_CURVE, ERDA_REQUEST_DAILY, EPIC_DUNGEON_PER_CHARACTER,
                     EPIC_DUNGEON_UNLIMITED, MONSTER_PARK_UNLIMITED, MONSTER_PARK_EXTREME_UNLIMITED,
                     ARCANE_WEEKLY_UNLIMITED, HEROIC_MESO_FLAME, HEROIC_MESO_POTENTIAL_RESET,
                     BOSS_FIRST_CLEAR_ANNOUNCE, NO_MAXED_SYMBOL_DROPS, AUTO_MERGE_SYMBOLS, SUMMARIZE_EQUIP_LOOT,
                     RESET_COOLDOWNS_AFTER_BOSS, QUEST_READY_NOTICE, GOLDEN_LEAVES, CASH_SHOP_PREFER_PREPAID,
                     ALWAYS_THREE_POTENTIAL_LINES -> "0";
                case QUEST_DROPS_ONLY_WHILE_NEEDED, BOSS_COOLDOWNS, JOB_ADVANCEMENT_BY_QUEST_ONLY, BURNING -> "1";
                case LUCK_BOOST, MOB_SPAWN_RATE -> "100";
                case CHARM_EXP_MULTIPLIER, CRA_RUNES, PIECE_BOXES, SACRED_SYMBOL_DROPS -> "1";
                case ARCANE_SYMBOL_DROPS, SOUL_SHARD_DROPS -> "3";
                case NODESTONE_DROPS, BOSS_CORES_DROPLETS -> "50";
                case PIECE_BOX_CHANCE -> "35";
                case GOLLUX_PENNIES -> "33";
                case GOLLUX_COINS -> "20";
                case ETERNAL_BOX_DROPS -> "10";
                case BOSS_EQUIP_BOOST, CRYSTAL_TOP_PRICE, PRIME_LINE_CHANCE -> "0";
                case RESET_TIME_ZONE -> "UTC";
                default -> null;
            };
        }

        /** @return the official servers' value as Boolean, Integer or String, or null (see {@link #getVanillaText}) */
        public Object getVanillaValue() {
            String t = getVanillaText();
            return t == null ? null : parse(t);
        }

        /** @return the default as Boolean, Integer or String (by type) */
        public Object getDefaultValue() {
            Object v = parse(defaultText);
            return v != null ? v : fallback();
        }

        /** Only reached when an entry's own default is invalid (reported by {@link #load()}). */
        private Object fallback() {
            return switch (type) {
                case BOOL -> Boolean.FALSE;
                case INT -> min;
                case CHOICE, ZONE -> choices.isEmpty() ? "" : choices.get(0);
            };
        }

        /**
         * @return the text read from the table (or sent by the page) as Boolean, Integer or String, or null when it
         * isn't valid for this setting
         */
        public Object parse(String text) {
            if (text == null) {
                return null;
            }
            String t = text.trim();
            return switch (type) {
                case BOOL -> parseBool(t);
                case INT -> parseInt(t);
                case CHOICE -> parseChoice(t);
                case ZONE -> parseZone(t);
            };
        }

        private static String parseZone(String t) {
            java.time.ZoneId z = net.swordie.ms.util.ResetClock.parseZone(t);
            return z == null ? null : z.getId();
        }

        private static Boolean parseBool(String t) {
            return switch (t.toLowerCase(Locale.ROOT)) {
                case "1", "true", "on", "yes" -> Boolean.TRUE;
                case "0", "false", "off", "no" -> Boolean.FALSE;
                default -> null;
            };
        }

        private Integer parseInt(String t) {
            try {
                int v = Integer.parseInt(t);
                return v < min || v > max ? null : v;
            } catch (NumberFormatException e) {
                return null;
            }
        }

        private String parseChoice(String t) {
            for (String c : choices) {
                if (c.equalsIgnoreCase(t)) {
                    return c;
                }
            }
            return null;
        }

        /** @return the text a value is saved as */
        public String format(Object value) {
            if (value instanceof Boolean b) {
                return b ? "1" : "0";
            }
            return String.valueOf(value);
        }

        public static Feature getByKey(String key) {
            for (Feature f : values()) {
                if (f.key.equalsIgnoreCase(key)) {
                    return f;
                }
            }
            return null;
        }
    }

    /**
     * Sets of values the admin page and the desktop app can fill in (they only fill the form; nothing is saved until
     * someone saves). Each preset gives every setting a value: the ones it names, the rest their default. Exported in
     * definitions.json ("presets") and in GET /api/admin/features.
     */
    public enum Preset {
        SWORDIE_PLUS("Swordie Plus", "Every setting at its default: how Swordie Plus normally plays."),
        GMS_LIKE("GMS-like", "As close to the official servers as the settings go: no auto-loot, item vac or loot "
                + "filters, real scroll and Star Force odds, boss cooldowns, official EXP table, job quests only, no "
                + "@job, no story or Arcane River skips, no services or helper commands from anywhere, no new player "
                + "helpers, every new character on the Classic path (full tutorial and job quests), official drop "
                + "chances and boss reward amounts where we know them. Burning stays on, as on the official servers."),
        CASUAL("Casual", "The conveniences without the automation: job paths, automatic job advancement, the "
                + "gentler EXP curve, scrolls and Star Force that never fail, no boss cooldowns, every command, "
                + "service and new player helper; but loot drops on the ground, no item vac or loot filters, the "
                + "boss window needs its quests, and @enhance costs as usual."),
        ;

        private final String name;
        private final String description;

        Preset(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        /** @return every setting's value in this preset (built on each call, so nothing runs while the class loads) */
        public Map<Feature, Object> getValues() {
            Map<Feature, Object> m = new EnumMap<>(Feature.class);
            for (Feature f : Feature.values()) {
                m.put(f, f.getDefaultValue());
            }
            switch (this) {
                case GMS_LIKE -> {
                    for (Feature f : Feature.values()) {
                        Object v = f.getVanillaValue();
                        if (v != null && f.getType() != Type.ZONE) {
                            m.put(f, v);
                        }
                    }
                    // every switch group off; boss cooldowns on (the official servers have them)
                    setAll(m, false, Feature.AUTO_LOOT, Feature.AUTO_LOOT_BOSS, Feature.SILENT_LOOT, Feature.ITEM_VAC,
                            Feature.BOSS_UI_IGNORE_QUESTS, Feature.FREE_ENHANCE, Feature.SCROLLS_ALWAYS_SUCCEED,
                            Feature.STAR_FORCE_ALWAYS_SUCCEEDS, Feature.JOB_PATHS, Feature.CUSTOM_EXP_CURVE);
                    setAll(m, false, LOOT_FILTERS);
                    setAll(m, false, NEW_PLAYER_HELPERS);
                    setAll(m, false, SERVICE_SWITCHES);
                    setCategory(m, false, Category.COMMANDS);
                    m.put(Feature.BOSS_COOLDOWNS, true);
                    m.put(Feature.JOB_ADVANCEMENT_BY_QUEST_ONLY, true); // automatic job advancement off
                    m.put(Feature.NEW_PLAYER_MODE, "classic"); // everyone plays the full story and job quests
                    setAll(m, false, Feature.EXPRESS_KIT, Feature.ARCANE_SKIP,
                            Feature.ARCANE_SKIP_SYMBOLS, Feature.ARCANE_WEEKLY_MINIGAMES);
                    setAll(m, false, Feature.ERDA_REQUEST_DAILY, Feature.EPIC_DUNGEON_PER_CHARACTER,
                            Feature.EPIC_DUNGEON_UNLIMITED, Feature.MONSTER_PARK_UNLIMITED,
                            Feature.MONSTER_PARK_EXTREME_UNLIMITED, Feature.ARCANE_WEEKLY_UNLIMITED);
                    m.put(Feature.BURNING, true); // the official servers have Hyper Burning
                    m.put(Feature.MAX_SLOTS, false); // slots come from the Cash Shop
                    m.put(Feature.LUCK_BOOST, 100); // official odds
                }
                case CASUAL -> {
                    setAll(m, false, Feature.AUTO_LOOT, Feature.AUTO_LOOT_BOSS, Feature.SILENT_LOOT, Feature.ITEM_VAC,
                            Feature.BOSS_UI_IGNORE_QUESTS, Feature.FREE_ENHANCE, Feature.BOSS_COOLDOWNS);
                    setAll(m, false, LOOT_FILTERS);
                    setAll(m, true, Feature.SCROLLS_ALWAYS_SUCCEED, Feature.STAR_FORCE_ALWAYS_SUCCEEDS,
                            Feature.JOB_PATHS, Feature.CUSTOM_EXP_CURVE);
                    setAll(m, true, NEW_PLAYER_HELPERS);
                    setAll(m, true, SERVICE_SWITCHES);
                    setCategory(m, true, Category.COMMANDS);
                    m.put(Feature.JOB_ADVANCEMENT_BY_QUEST_ONLY, false); // automatic job advancement on
                    m.put(Feature.NEW_PLAYER_MODE, "choice"); // Arcane River skip offers as today
                }
                default -> {
                }
            }
            return Collections.unmodifiableMap(m);
        }

        /** @return the preset as key -> the text a value is saved as ("1"/"0", a number or a choice) */
        public Map<String, String> getValueTexts() {
            Map<String, String> res = new LinkedHashMap<>();
            for (Map.Entry<Feature, Object> e : getValues().entrySet()) {
                if (e.getKey().getType() == Type.ZONE) {
                    continue; // a server's time zone isn't part of a play style
                }
                res.put(e.getKey().getKey(), e.getKey().format(e.getValue()));
            }
            return res;
        }

        private static final Feature[] LOOT_FILTERS = {Feature.EQUIP_DROPS_FOR_KILLER_ONLY,
                Feature.NO_GENERIC_SCROLL_DROPS, Feature.NO_RECIPE_DROPS, Feature.QUEST_DROPS_ONLY_WHILE_NEEDED};
        private static final Feature[] NEW_PLAYER_HELPERS = {Feature.NEXT_HELPER,
                Feature.SPROUT_BONUS, Feature.RESTED_EXP, Feature.ONBOARDING_TIPS};
        private static final Feature[] SERVICE_SWITCHES = {Feature.SWORDIE_COINS, Feature.REMOTE_SHOPS,
                Feature.REMOTE_SELL, Feature.REMOTE_ARI, Feature.REMOTE_PETS, Feature.REMOTE_STORAGE,
                Feature.REMOTE_ENHANCE, Feature.REMOTE_OPTIMIZE, Feature.REMOTE_VMATRIX, Feature.REMOTE_REBIRTH,
                Feature.REMOTE_BOSS_ENTRY};

        private static void setAll(Map<Feature, Object> m, boolean on, Feature... features) {
            for (Feature f : features) {
                m.put(f, on);
            }
        }

        private static void setCategory(Map<Feature, Object> m, boolean on, Category category) {
            for (Feature f : Feature.values()) {
                if (f.getCategory() == category && f.getType() == Type.BOOL) {
                    m.put(f, on);
                }
            }
        }
    }

    /** Called after a reload changed values in effect, with the settings that changed. */
    @FunctionalInterface
    public interface Listener {
        void changed(Set<Feature> changed);
    }

    /** Every default; building it can't throw (parse never does). */
    private static final Map<Feature, Object> DEFAULTS = defaults();

    /** server.properties key: a preset whose values replace the defaults for settings with no row in the table. */
    public static final String PRESET_PROPERTY = "features.preset";

    private static String presetKey(String name) {
        return name == null ? "" : name.replaceAll("[\\s_-]", "").toLowerCase(Locale.ROOT);
    }

    /** @return the preset named by {@link #PRESET_PROPERTY} ("gms_like", "GMS-like", "casual", ...), or null */
    public static Preset configuredPreset() {
        String name;
        try {
            name = net.swordie.ms.ServerSettings.getString(PRESET_PROPERTY, "");
        } catch (Exception e) {
            return null;
        }
        String n = presetKey(name);
        if (n.isEmpty()) {
            return null;
        }
        for (Preset p : Preset.values()) {
            if (presetKey(p.name()).equals(n) || presetKey(p.getName()).equals(n)) {
                return p;
            }
        }
        log.warn("[Features] Unknown " + PRESET_PROPERTY + " \"" + name + "\"; using the defaults.");
        return null;
    }

    /** @return the values settings without a saved row have: the configured preset's, else the defaults */
    private static Map<Feature, Object> base() {
        Preset p = configuredPreset();
        return p == null ? DEFAULTS : p.getValues();
    }
    /** The values in effect; null until the first load, which means {@link #DEFAULTS}. */
    private static volatile Map<Feature, Object> active;
    /** The values read when the server started; "needs restart" settings keep these. */
    private static volatile Map<Feature, Object> atBoot;
    private static final List<Listener> listeners = new CopyOnWriteArrayList<>();

    private ServerFeatures() {
    }

    // ------------------------------------------------------------------------------------------------- reading

    private static Map<Feature, Object> snapshot() {
        Map<Feature, Object> v = active;
        return v != null ? v : DEFAULTS;
    }

    private static Object value(Feature f) {
        Object v = snapshot().get(f);
        return v != null ? v : f.getDefaultValue();
    }

    /** @return whether an on/off setting is on now (false for a setting that isn't on/off) */
    public static boolean on(Feature f) {
        return Boolean.TRUE.equals(value(f));
    }

    /** @return the number in effect for a number setting (0 for a setting that isn't a number) */
    public static int getInt(Feature f) {
        return value(f) instanceof Integer i ? i : 0;
    }

    /** @return the choice in effect for a choice setting ("" for a setting that isn't a choice) */
    public static String choice(Feature f) {
        Object v = value(f);
        return f.getType() == Type.CHOICE && v instanceof String s ? s : "";
    }

    /** @return the value in effect as Boolean, Integer or String */
    public static Object get(Feature f) {
        return value(f);
    }

    public static Map<Feature, Object> defaults() {
        Map<Feature, Object> m = new EnumMap<>(Feature.class);
        for (Feature f : Feature.values()) {
            m.put(f, f.getDefaultValue());
        }
        return Collections.unmodifiableMap(m);
    }

    // ------------------------------------------------------------------------------------------------- loading

    /**
     * The outcome of a reload: ok is false when the table couldn't be read (the values in effect were kept).
     */
    public record ReloadResult(boolean ok, Set<Feature> changed) {
    }

    /**
     * Reads the settings once while the server starts (after the migrations ran). Never throws.
     *
     * @return whether the table could be read (if not, the defaults are in effect)
     */
    public static synchronized boolean load() {
        List<String> problems = new ArrayList<>();
        for (Feature f : Feature.values()) {
            if (f.parse(f.defaultText) == null) {
                problems.add(f.getKey() + " has an invalid default (" + f.defaultText + ")");
            }
        }
        Map<Feature, Object> read = readOrNull(problems);
        Map<Feature, Object> res = read != null ? read : base();
        Preset preset = configuredPreset();
        if (preset != null) {
            log.info("[Features] Preset " + preset.getName() + " (" + PRESET_PROPERTY + ") for the settings not saved "
                    + "in " + TABLE + ".");
        }
        atBoot = res;
        active = res;
        warn(problems, read == null);
        return read != null;
    }

    /**
     * Reads the table again and puts the new values in effect (settings that need a restart keep their start
     * values). Never throws: if the table can't be read the values in effect are kept and one warning is logged.
     *
     * @return whether the table was read, and the settings whose value in effect changed
     */
    public static ReloadResult reload() {
        Set<Feature> changed = EnumSet.noneOf(Feature.class);
        synchronized (ServerFeatures.class) {
            if (atBoot == null) {
                // not loaded at boot yet: this first read is the boot read, so there's nothing to compare against
                return new ReloadResult(load(), Collections.emptySet());
            }
            List<String> problems = new ArrayList<>();
            Map<Feature, Object> read = readOrNull(problems);
            if (read == null) {
                warn(problems, false);
                log.warn("[Features] Kept the settings in effect.");
                return new ReloadResult(false, Collections.emptySet());
            }
            Map<Feature, Object> boot = atBoot;
            Map<Feature, Object> old = snapshot();
            Map<Feature, Object> next = new EnumMap<>(Feature.class);
            for (Feature f : Feature.values()) {
                Object v = f.isRestartRequired() ? boot.get(f) : read.get(f);
                if (v == null) {
                    v = f.getDefaultValue();
                }
                next.put(f, v);
                if (!v.equals(old.get(f))) {
                    changed.add(f);
                }
            }
            active = Collections.unmodifiableMap(next);
            warn(problems, false);
        }
        Set<Feature> result = Collections.unmodifiableSet(changed);
        if (!changed.isEmpty()) {
            log.info("[Features] Reloaded, changed: " + changed);
            for (Listener l : listeners) {
                try {
                    l.changed(result);
                } catch (Exception e) {
                    log.warn("[Features] A listener failed: " + e);
                }
            }
        }
        return new ReloadResult(true, result);
    }

    /** @return every setting's saved value (defaults for missing or invalid rows), or null if the table can't be read */
    private static Map<Feature, Object> readOrNull(List<String> problems) {
        Map<String, String> raw;
        try (Connection con = HikariCPDataSource.getConnection()) {
            raw = readRaw(con);
            con.rollback();
        } catch (Exception e) {
            problems.add("could not read the " + TABLE + " table (" + e.getMessage() + ")");
            return null;
        }
        Map<Feature, Object> base = base();
        Map<Feature, Object> res = new EnumMap<>(Feature.class);
        for (Feature f : Feature.values()) {
            String text = raw.get(f.getKey());
            Object v = text == null ? null : f.parse(text);
            if (text != null && v == null) {
                problems.add(f.getKey() + " has an invalid value (" + text + ")");
            }
            Object b = base.get(f);
            res.put(f, v != null ? v : b != null ? b : f.getDefaultValue());
        }
        return Collections.unmodifiableMap(res);
    }

    private static void warn(List<String> problems, boolean usingDefaults) {
        if (!problems.isEmpty()) {
            log.warn("[Features] " + String.join("; ", problems)
                    + (usingDefaults ? ". Using the defaults." : ". Those use their defaults."));
        }
    }

    /**
     * @return every row of the table as key -> saved text, unknown keys included (lower-cased keys, in table order)
     */
    public static Map<String, String> readRaw(Connection con) throws SQLException {
        Map<String, String> res = new LinkedHashMap<>();
        try (PreparedStatement ps = con.prepareStatement("SELECT name, value FROM " + TABLE + " ORDER BY name");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString(1);
                if (name != null) {
                    res.put(name.toLowerCase(Locale.ROOT), rs.getString(2));
                }
            }
        }
        return res;
    }

    /** @return the value a setting had when the server started (its default before the first load) */
    public static Object getAtBoot(Feature f) {
        Map<Feature, Object> b = atBoot;
        Object v = b == null ? null : b.get(f);
        return v != null ? v : f.getDefaultValue();
    }

    // ----------------------------------------------------------------------------------------------- listeners

    /**
     * Runs after a reload changed values, on the thread that reloaded (the web API or AutoControl): keep it short and
     * hand anything heavy to the game's scheduler.
     */
    public static void addListener(Listener l) {
        if (l != null) {
            listeners.add(l);
        }
    }

    public static void removeListener(Listener l) {
        listeners.remove(l);
    }
}
