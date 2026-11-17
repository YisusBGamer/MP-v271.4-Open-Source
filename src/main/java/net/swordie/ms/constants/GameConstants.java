package net.swordie.ms.constants;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.connection.packet.QuickMoveInfo;
import net.swordie.ms.enums.*;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.util.container.Triple;

import java.util.*;

/**
 * Created on 1/23/2018.
 */
public class GameConstants {
    public static final int ARCHELLE_NPC = 1540945;
    public static final int COLLECTOR_NPC = 9001212;

    // ---- Quality of life settings (set to false / 1 to get the original behaviour back) ----

    // Equips get their hidden potential and bonus potential revealed as soon as they're obtained (picked up, quest
    // rewards, crafting, shops, ...) or a potential scroll is used on them, so no Magnifying Glass is needed.

    /**
     * Every scroll succeeds: equip scrolls (normal, chaos, clean slate, innocence, white), spell traces, potential and
     * bonus potential scrolls, potential/bonus potential stamps, soul enchanters, golden hammers and mastery books.
     * Cursed scrolls never destroy items and no upgrade slots are lost. Only the default of the setting
     * scrolls_always_succeed (ServerFeatures); Star Force has its own (star_force_always_succeeds).
     */
    public static final boolean SCROLLS_ALWAYS_SUCCEED = true;

    /**
     * @return whether a scroll with the given success chance (in %) succeeds
     */
    public static boolean rollScrollSuccess(int chance) {
        return ServerFeatures.on(ServerFeatures.Feature.SCROLLS_ALWAYS_SUCCEED)
                || net.swordie.ms.util.Util.succeedProp(boosted(chance));
    }

    /** @return the luck_boost setting as a multiplier (2.0 = double the official odds, never below 1) */
    public static double luckBoost() {
        return Math.max(100, ServerFeatures.getInt(ServerFeatures.Feature.LUCK_BOOST)) / 100D;
    }

    /** @return a chance in % with luck_boost applied, capped at 100 */
    public static int boosted(int chance) {
        return boosted(chance, 100);
    }

    /** @return a chance out of outOf with luck_boost applied, capped at outOf */
    public static int boosted(int chance, int outOf) {
        if (chance <= 0) {
            return chance;
        }
        return (int) Math.min(outOf, Math.round(chance * luckBoost()));
    }

    /**
     * Odds of ordered outcomes (worst first) with luck_boost applied: every outcome better than the most common one
     * gets its odds multiplied (best first, capped by what is left), paid for by the worst outcomes. Same total.
     */
    public static int[] boostedOdds(int[] odds) {
        double boost = luckBoost();
        int[] res = odds.clone();
        if (boost <= 1 || odds.length < 2) {
            return res;
        }
        int mode = 0;
        for (int i = 1; i < odds.length; i++) {
            if (odds[i] > odds[mode]) {
                mode = i;
            }
        }
        int left = 0; // what the most common and worse outcomes can give up
        for (int i = 0; i <= mode; i++) {
            left += odds[i];
        }
        int extra = 0;
        for (int i = odds.length - 1; i > mode; i--) {
            int add = (int) Math.min(Math.round(odds[i] * (boost - 1)), Math.max(0, left));
            res[i] += add;
            left -= add;
            extra += add;
        }
        for (int i = 0; i <= mode && extra > 0; i++) { // paid by the worst outcomes, the most common last
            int take = Math.min(res[i], extra);
            res[i] -= take;
            extra -= take;
        }
        return res;
    }
    // Drops of a killed mob go straight into the inventories of the killer and their party members on the map (each
    // drop to someone allowed to pick it up), solo or in a party. Bosses: AUTO_LOOT_BOSS_DROPS.
    public static final boolean AUTO_LOOT = true;
    // Monsters only drop equips the killer (or a party member on the map) can use: right class, a weapon or secondary
    // weapon their skills work with, and the right gender. Level requirements are not checked.
    public static final boolean EQUIP_DROPS_FOR_KILLER_ONLY = true;
    // Instead of a pickup message per looted equip, one summary ("3 Unique Equips, 5 Equips Obtained.") once nothing
    // has been looted for EQUIP_LOOT_SUMMARY_DELAY_SECONDS.
    public static final boolean SUMMARIZE_EQUIP_LOOT = true;
    public static final int EQUIP_LOOT_SUMMARY_DELAY_SECONDS = 5;
    // Monsters don't drop the generic equipment scrolls ("Scroll for Earring for DEX 100%", "Dark Scroll for ...",
    // "Tablet for ..."); special scrolls (chaos, clean slate, potential, event scrolls, ...) still drop.
    public static final boolean NO_GENERIC_SCROLL_DROPS = true;
    /** Swordie Plus: monsters never drop crafting recipes (or the boxes that give random ones). */
    public static final boolean NO_RECIPE_DROPS = true;
    // @enhance (Scroll & Star Force) charges extra for the convenience, in %: Spell Traces +10%, mesos +100%
    public static final int AUTO_ENHANCE_TRACE_MARKUP_PERCENT = 10;
    public static final int AUTO_ENHANCE_MESO_MARKUP_PERCENT = 100;
    /** Bots keep the old @enhance meso markup (and pay no @optimize markup or fee). */
    public static final int AUTO_ENHANCE_BOT_MESO_MARKUP_PERCENT = 10;
    /** @optimize's meso markup on Star Force, flames and cube fees, in % (300 = 4x the by-hand price). */
    public static final int OPTIMIZE_MESO_MARKUP_PERCENT = 300;
    /** @optimize's convenience fee per run, in % of the mesos carried when the run starts. */
    public static final int OPTIMIZE_CONVENIENCE_FEE_PERCENT = 2;
    // Boss equip drop odds, per equip, counting the drop rate (the odds a player actually gets):
    // below BOSS_EQUIP_LOW_ODDS_BELOW % -> BOSS_EQUIP_LOW_ODDS %; below BOSS_EQUIP_MID_ODDS_BELOW % -> BOSS_EQUIP_MID_ODDS %;
    // higher odds get BOSS_EQUIP_DROP_BONUS_PERCENT % more (30 = 1.3x; never past 100%). 0 bonus = all off.
    public static final int BOSS_EQUIP_DROP_BONUS_PERCENT = 30;
    public static final double BOSS_EQUIP_LOW_ODDS_BELOW = 10;
    public static final double BOSS_EQUIP_LOW_ODDS = 15;
    public static final double BOSS_EQUIP_MID_ODDS_BELOW = 20;
    public static final double BOSS_EQUIP_MID_ODDS = 25;
    // The boss window's teleport works for every boss without its prerequisite quest line (the client is told those
    // quests are done; nothing is saved). Quests already in progress keep their real state.
    public static final boolean BOSS_UI_IGNORE_QUEST_REQUIREMENTS = true;
    // @enhance: true = scrolling and Star Forcing with the command is free; false = the usual Spell Traces and mesos
    public static final boolean AUTO_ENHANCE_FREE = false;
    // Boss loot goes straight into the inventories of the players who fought the boss (dealt out in turns among the
    // ones allowed to pick each drop up); what doesn't fit stays on the ground.
    public static final boolean AUTO_LOOT_BOSS_DROPS = true;
    // The first @job choice (as a beginner) puts STR/DEX/INT/LUK back to 4 and returns the rest as AP, so the points a
    // beginner put into STR automatically can go into the new class's stats.
    public static final boolean JOB_PATH_REFUND_ABILITY_POINTS = true;
    // @pet: price in mesos of a pet with every pet skill, of unlocking every skill on an owned pet, and of the pet
    // shop's items
    public static final long PET_PACKAGE_PRICE = 250_000_000L;
    public static final long PET_SET_PRICE = 500_000_000L; // pets of a 3-pet set (set skill)
    public static final long PET_WONDER_PRICE = 1_000_000_000L; // Wonder pets (stronger set skill)
    public static final long PET_LUNA_PRICE = 3_000_000_000L; // Luna pets (strongest set skill, wider pickup)
    public static final long PET_SKILL_UNLOCK_PRICE = 100_000_000L;
    public static final long PET_FOOD_PRICE = 10_000L;
    public static final long PET_SNACK_PRICE = 50_000L;
    public static final long ROCK_OF_EVOLUTION_PRICE = 5_000_000L;
    // Spell Traces stack up to this many per slot (the most an item stack can hold is 32,767; the game data's is 9,000)
    public static final int SPELL_TRACE_SLOT_MAX = Short.MAX_VALUE;
    // Charm EXP from items is multiplied by this (6 = +500%): equipping an item, using an item with charm EXP, and
    // getting a title (Set-up item) with charm EXP (once per title)
    public static final int CHARM_EXP_MULTIPLIER = 6;
    // Quest-only items only drop (are visible) for players who still need more of them for a quest in progress.
    public static final boolean QUEST_DROPS_ONLY_WHILE_NEEDED = true;
    // Warn players about items that expire before the next expiry check (see EXPIRY_TIME_CHECK).
    public static final boolean EXPIRY_WARNINGS = true;
    // Tell players when a quest's last mob/item/meso requirement is done and to whom to turn it in.
    public static final boolean QUEST_READY_NOTICE = true;
    // Developer messages ("Starting script ...", "[Info] Accepted quest ...", "(Field) Not coded ...") are never shown
    // to players unless this is true; Tester/GM accounts see them only while debug mode is on (!debug).
    public static final boolean SHOW_DEBUG_MESSAGES_TO_PLAYERS = false;
    // Multiplies the max stack size of Use/Set-up/Etc items. Keep at 1 unless the client's Item.wz slotMax values
    // are raised too: the client uses its own slotMax when moving/merging items.
    public static final int SLOT_MAX_MULTIPLIER = 1;
    // ---- EXP curve (MobExpConstants) ----
    // Below level 10 the "EXP rate (levels 1-9)" applies and from 10 the server's EXP rate (server_rates table).
    // From CUSTOM_EXP_FROM_LEVEL a gentler EXP-to-level curve is used (CUSTOM_EXP_TO_LEVEL[level - 200]): the time per
    // level grows slowly (~(level - 200)^1.6) instead of jumping at 200/210/220/.../250 like the official table, and is
    // never slower than the official table. The client's EXP bar uses the official table, so instead of lowering the
    // EXP needed, monster EXP is multiplied by officialExp / customExp: the bar fills at the curve's pace.
    // Passive skills of every class from a character's earlier lives keep counting after a rebirth (they stack), as
    // the client counts them.
    public static final boolean REBIRTH_PASSIVES_STACK = true;
    public static final boolean CUSTOM_EXP_CURVE = true;
    // each level of the curve from CUSTOM_EXP_FROM_LEVEL takes this share of its time (0.6 = 40% less)
    public static final double CUSTOM_EXP_TIME_FACTOR = 0.6;
    public static final int CUSTOM_EXP_FROM_LEVEL = 200;
    public static final long[] CUSTOM_EXP_TO_LEVEL = {
            2207026000L, 2272259000L, 2372701000L, 2464643000L, // 200-203
            2577243000L, 2953298000L, 4356279000L, 4879032000L, // 204-207
            5464516000L, 6120258000L, 6877837000L, 7582458000L, // 208-211
            8327855000L, 13392408000L, 14865573000L, 19325244000L, // 212-215
            21064516000L, 22543497000L, 23787729000L, 25074151000L, // 216-219
            29255425000L, 31821121000L, 34896810000L, 37360653000L, // 220-223
            39172286000L, 41937437000L, 45699645000L, 48679738000L, // 224-227
            51372624000L, 63600000000L, 66300450000L, 69055459000L, // 228-231
            71864317000L, 74726345000L, 77640890000L, 82564903000L, // 232-235
            88826779000L, 94649757000L, 98054594000L, 103154606000L, // 236-239
            108650319000L, 114342394000L, 120234610000L, 128067942000L, // 240-243
            132181344000L, 138707539000L, 145164519000L, 154671578000L, // 244-247
            159301683000L, 166708381000L, 174345809000L, 182224045000L, // 248-251
            190340820000L, 201170805000L, 214039881000L, 223206497000L, // 252-255
            238014937000L, 247875570000L, 258028687000L, 268448328000L, // 256-259
            279646781000L, 290667169000L, 297662790000L, 315807031000L, // 260-263
            362906757000L, 616046632000L, 636112501000L, 882771612000L, // 264-267
            929411589000L, 1068475346000L, 1130086713000L, 1178408198000L, // 268-271
            1258596672000L, 1284770983000L, 1373372898000L, 1410359903000L, // 272-275
            1438634103000L, 1467132409000L, 1495853651000L, 1524796681000L, // 276-279
            1553960373000L, 1583343620000L, 1612945331000L, 1642764439000L, // 280-283
            1672799892000L, 1703050655000L, 1733515711000L, 1764194061000L, // 284-287
            1795084721000L, 1826186721000L, 1857499110000L, 1889020948000L, // 288-291
            1920751312000L, 1952689292000L, 1984833992000L, 2017184531000L, // 292-295
            2049740038000L, 2082499656000L, 2115462542000L, 2148627864000L, // 296-299
    };
    // Cooldown of @go (the travel in @menu doesn't use it, like the old @qm).
    public static final int TRAVEL_COMMAND_COOLDOWN_SECONDS = 60;
    // Custom quest records (not in Quest.wz) used to remember things across logins.
    public static final int FREE_MARKET_RETURN_QR = 89999950; // map to return to when leaving the Free Market
    public static final int VISITED_TOWNS_QR = 89999951; // towns visited, for @go
    public static final int MENU_RECENT_QR = 89999948; // server-only record: the last places @menu took the character

    // ---- Rebirth (@rebirth / the NPC Lawry, see RebirthModule) ----
    public static final int REBIRTH_MIN_LEVEL = 255;
    public static final int REBIRTH_MAX_COUNT = 0; // 0 = unlimited
    public static final int REBIRTH_BONUS_AP = 5; // extra AP per rebirth done, given at each rebirth (5, 10, 15, ...)
    public static final boolean REBIRTH_RESET_STATS = false; // true: STR/DEX/INT/LUK, max HP/MP and AP go back to a new character's
    // gift box delivery on a character's first rebirth: level 1 equipment any class can wear
    public static final int[] FIRST_REBIRTH_GIFT = {
            1302067, // Maple 1500 Anniv. Flag (one-handed sword, all stats +1, attack 38)
            1042167, // Simple Warrior Top (unisex, no job requirement)
            1062115, // Simple Warrior Pants
            1072445, // New Member Shoes (speed +40, jump +30)
            1102585, // Maple Cape (Level 1)
    };
    public static final int REBIRTH_QR = 89999952; // server-only record: rebirth count and old job path
    public static final int JOB_PATH_MIN_LEVEL = 10; // @job: level from which a beginner can choose any job path
    public static final int JOB_PATH_QR = 89999953; // server-only record: the job path chosen with @job
    // Only characters that chose a path with @job advance automatically (JobPathModule), 5th job included. Everyone
    // else advances through their class's official job advancement quests, as on the official servers.
    public static final boolean JOB_ADVANCEMENT_BY_QUEST_ONLY = true;
    // Shown wherever @job is offered: choosing a path skips the class's official questline
    public static final String JOB_PATH_WARNING = "Choosing a job path with @job advances you automatically, so you "
            + "won't experience your class's job advancement questline and story as intended on the official servers.";
    public static final int CHARM_ITEMS_QR = 89999954; // server-only record: titles that already gave their charm EXP
    public static final int CHARM_ITEMS_QR_EXTRA_FIRST = 89999960; // ...continued in 89999960-89999979 when it gets long
    public static final int CHARM_ITEMS_QR_EXTRA_COUNT = 20;
    public static final int HIDDEN_RECORD_MAX_LENGTH = 250; // quest records are stored in a varchar(255)

    public static final int CHANNELS_PER_WORLD = 3;
    public static final int BUFFED_CHANNELS = CHANNELS_PER_WORLD;
    public static final int COMBO_ORB_EXP_RATE = 100; // Note: MOB_EXP_RATE does not affect combo orbs
    public static final boolean TRANSMOG_FROM_STORAGE = true; // @transmog can also copy looks from items in storage
    public static final boolean RESET_COOLDOWNS_AFTER_BOSS = true; // leaving a boss encounter ends all skill cooldowns
    // Boss cooldowns (BossCooldown) are off: any boss can be entered again right away, and nothing about them is
    // tracked or saved (no work or database writes on boss entry or clear). BossCooldown's hours only count when true.
    public static final boolean BOSS_COOLDOWNS = false;
    // "[First Clear] <name> defeated <boss> (<difficulty>) for the first time!" to the whole world (BossFirstClearModule)
    public static final boolean BOSS_FIRST_CLEAR_ANNOUNCE = true;
    public static final boolean NO_DROPS_FOR_MAXED_ARCANE_SYMBOLS = true; // a maxed Arcane Symbol stops dropping for that player
    // an Arcane or Sacred Symbol obtained from any source merges into the same equipped symbol, which then levels up as
    // far as growth and mesos allow; a copy of a max level equipped symbol turns to dust (SymbolAutoMergeModule)
    public static final boolean AUTO_MERGE_SYMBOLS = true;
    // Arcane River story skip (Spiegelmann, ArcaneRiverSkipModule): server-only record of the levels the skip prompt
    // was already offered at, and how long the prompt (and its protection) may stay open
    public static final int ARCANE_SKIP_PROMPT_QR = 89999959;
    public static final int ARCANE_SKIP_PROMPT_SECONDS = 60;
    // New-player help (OnboardingModule): a welcome on a character's first login, a welcome box on its first town visit
    // (characters up to ONBOARDING_WELCOME_BOX_MAX_LEVEL only) and one tip at a few level milestones. The new player
    // guide (@menu > Help) is always there.
    public static final boolean ONBOARDING_TIPS = true;
    public static final int ONBOARDING_QR = 89999980; // server-only record: which welcome messages/tips were shown
    public static final int ONBOARDING_WELCOME_BOX_MAX_LEVEL = 30;
    // Play style (PlayStyleModule): "classic" (tutorial, official job quests) or "express" (tutorial skipped, @job)
    public static final int PLAY_STYLE_QR = 89999981;
    public static final int FIRST_JOB_SP_QR = 89999990; // server-only record: the 1st job SP check of a job path character is done
    // the server's day and weekend (buffed channels, rested EXP)
    public static final boolean CASH_SHOP_PREFER_PREPAID = true; // NX purchases spend NX Prepaid first, whichever NX type is selected
    // the EXP, drop, meso and NX rates are in ServerRates (the server_rates table)
    public static final long MAX_MONEY = 99_999_999_999L;
    public static final short DAMAGE_SKIN_MAX_SIZE = 100;
    public static final int MAX_PET_AMOUNT = 3;
    public static final int MAX_HP_MP = 500000;
    public static final long DAMAGE_CAP = 150_000_000_000L;
    public static final int BEGINNER_SP_MAX_LV = 7;
    public static final int RESISTANCE_SP_MAX_LV = 10;
    public static final int QUICKSLOT_LENGTH = 32;
    public static final int MAX_TRUNK_SIZE = 128;
    public static final int DEFAULT_TRUNK_SIZE = 20; // a new account's storage with max_slots off
    public static final int MAX_CHAT_MESSAGE_LENGTH = 70;
    // the v265 client reads the rest of a remote attack / skill prepare while the action is below its action count
    // (over 3100, loaded at start; HEXA and newer class actions are past 2386): send it for any real action
    public static final short MAX_ATTACK_ACTION = Short.MAX_VALUE;
    public static final int FIELD_REMOVAL_TIME = 30;
    public static final boolean PIC_ON_V_MATRIX_OPEN = false; // if true,  PIC will be required when opening VMatrix UI
    public static final long EXPIRY_TIME_CHECK = 15; // minutes

    // User Death
    public static final int BUFF_FREEZER_TIMER = 30; // 30 seconds
    public static final int USER_REVIVE_TIMER = 5; // 5 seconds
    public static final int DEATH_PENALTY_MAX_DURATION = 10 * 60; // GMS v271: 585 s with a 2.5% reduction
    public static final int DEATH_PENALTY_DROP_DEBUFF = 80; // 80% less drop rate
    public static final int DEATH_PENALTY_EXP_DEBUFF = 80; // 80% less exp

    // Stats
    public static final int HP_PER_LEVEL = 50;
    public static final int MP_PER_LEVEL = 37;
    public static final double STR_HP_MULT = 1.5;
    public static final double STR_MP_MULT = 0.75;
    public static final double INT_HP_MULT = 0.75;
    public static final double INT_MP_MULT = 1.5;

    // Field
    public static final int NO_MAP_ID = 999999999;
    public static final int HYPER_ROCK_SLOTS = 13;
    public static final int VIDEO_FIELD = 931050990; // Used for Effects and/or Videos
    public static final int ARDENTMILL = 910001000;
    public static final int GUILD_BASE = 200000301;
    public static final int FOREST_OF_TENACITY = 993001000;
    public static final int DEFAULT_FIELD_MOB_CAPACITY = 25;
    /** Multiplies every map's Map.wz mobRate (how many monsters the map holds for its size). */
    public static final double MOB_SPAWN_RATE_MULTIPLIER = 1.75;
    /** The most monsters a map holds for one player; it grows up to twice that with more players (official: 40). */
    public static final int MAX_FIELD_MOB_CAPACITY = 60;
    /** With this many players in a map it holds its most monsters (twice its one-player amount, at most MAX_FIELD_MOB_CAPACITY_BUSY). */
    public static final int FIELD_MOB_CAPACITY_FULL_USERS = 5;
    /** The most monsters a map holds however many players are in it (fewer monsters = fewer effects on screen, less client lag). */
    public static final int MAX_FIELD_MOB_CAPACITY_BUSY = 90;
    /** Server-only record: 1 when the character hides other players' attacks and skill effects (@effects). */
    public static final int HIDE_OTHER_EFFECTS_QR = 89999955;
    /** Server-only record: the character's last measured boss fights for @bosses (see BossReadinessModule). */
    public static final int BOSS_DPS_QR = 89999956;
    public static final int PARTY_QUEST_QR = 89999958; // server-only record: party quest entries per day, clears, rewards
    public static final int BUDDY_CAPACITY_QR = 89999992; // server-only record: buddy list size, unset = BUDDY_CAPACITY_DEFAULT
    public static final int BUDDY_CAPACITY_DEFAULT = 20;
    public static final int BUDDY_CAPACITY_MAX = 200;
    public static final int BUDDY_CAPACITY_STEP = 5; // slots per expansion, as the client's dialog says
    public static final int BUDDY_CAPACITY_CHEAP_BELOW = 100; // below this size an expansion costs the cheap price
    public static final long BUDDY_CAPACITY_COST_CHEAP = 50_000;
    public static final long BUDDY_CAPACITY_COST = 1_000_000;
    /** Auto-looted drops go straight to the inventory without being shown on the ground (less client lag). */
    public static final boolean SILENT_AUTO_LOOT = true;
    public static final int BASE_MOB_RESPAWN_RATE = 5000; // In milliseconds
    public static final Rect MOB_CHECK_RECT = new Rect(-100, -100, 100, 100);
    public static final int MIN_LEVEL_FOR_RANDOM_FIELD_OCCURRENCES = 10;

    // Drop
    public static final int DROP_HEIGHT = 100;
    public static final int DROP_DIFF = 20;
    public static final int DROP_DIFF_FROM_FIELD_BORDER = 25;
    public static final int DROP_REMAIN_ON_GROUND_TIME = 120; // 2 minutes
    public static final int DROP_REMOVE_OWNERSHIP_TIME = 30; // 30 sec
    public static final int MIN_MONEY_MULT = 20;
    public static final int MAX_MONEY_MULT = 40;
    public static final int MAX_DROP_CHANCE = 10000;

    // Combo Kill
    public static final int COMBO_KILL_RESET_TIMER = 10000; // 7 sec
    public static final int COMBO_KILL_REWARD_BLUE = 50; // Combo kills
    public static final int COMBO_KILL_REWARD_PURPLE = 350; // Combo kills
    public static final int COMBO_KILL_REWARD_RED = 750; // Combo kills
    public static final int COMBO_KILL_REWARD_GOLD = 1000; // Combo kills

    // Multi Kill
    public static final float MULTI_KILL_BONUS_EXP_MULTIPLIER = 0.01f; // Multi Kill Bonus Exp given  =  mobEXP * (( multi Kill Amount - 2 ) * 5) * BONUS_EXP_FOR_MULTI_KILL

    // Aggressive Ranking
    public static final long AGGRESSIVE_RANKING_UPDATE_DELAY = 1000L; // 1000 ms

    // Inner Ability
    public static final int CHAR_POT_BASE_ID = 70000000;
    public static final int CHAR_POT_END_ID = 70000062;
    public static final int CHAR_POT_RESET_COST = 1;
    public static final int CHAR_POT_GRADE_LOCK_COST_EPIC = 1; // added cost to lock Epic Grade
    public static final int CHAR_POT_GRADE_LOCK_COST_UNIQUE = 1; // added cost to lock Unique Grade
    public static final int CHAR_POT_GRADE_LOCK_COST_LEGENDARY = 1; // added cost to lock Legendary Grade
    public static final int CHAR_POT_LOCK_1_COST = 1;
    public static final int CHAR_POT_LOCK_2_COST = 1;

    // Potential on dropped equips, out of 10,000 (GMS-like estimate): none, Rare, Epic, Unique, Legendary;
    // luck_boost raises the chance of a potential and of each rank above Rare
    public static final int[] DROP_POTENTIAL_ODDS = {7500, 2000, 400, 90, 10};

    /** @return the hidden grade a dropped equip's potential gets, or None */
    public static ItemGrade rollDropPotentialGrade() {
        int[] odds = boostedOdds(DROP_POTENTIAL_ODDS);
        int roll = java.util.concurrent.ThreadLocalRandom.current().nextInt(10000);
        ItemGrade[] grades = {ItemGrade.None, ItemGrade.HiddenRare, ItemGrade.HiddenEpic, ItemGrade.HiddenUnique,
                ItemGrade.HiddenLegendary};
        for (int i = 0; i < odds.length; i++) {
            if (roll < odds[i]) {
                return grades[i];
            }
            roll -= odds[i];
        }
        return ItemGrade.None;
    }

    // BagItem
    // bags per tab, as the client checks a bag index
    public static final int BAG_ITEM_MAX_CONSUME = 8;
    public static final int BAG_ITEM_MAX_INSTALL = 20;
    public static final int BAG_ITEM_MAX_ETC = 24;
    public static int getMaxItemPerBagByType(InvType type) {
        switch (type) {
            case CONSUME:
                return BAG_ITEM_MAX_CONSUME;
            case INSTALL:
                return BAG_ITEM_MAX_INSTALL;
            case ETC:
                return BAG_ITEM_MAX_ETC;
            default: return 0;
        }
    }

    // Random Portal
    public static final int RANDOM_PORTAL_NEXT_SPAWN_TIME_QUEST_ID = 89999997; // nextTime=X
    public static final int RANDOM_PORTAL_SPAWN_CHANCE = 25; // out of a 10,000 (0.25%)
    public static final int RANDOM_PORTAL_COOLTIME = 30 * 60 * 1000; // 30 minutes
    public static final int RANDOM_PORTAL_DURATION = 3 * 60 * 1000; // 3 minutes
    public static final long INFERNO_WOLF_HP = 60_000_000_000_000L; // 60 tril


    public static final int LIE_DETECTOR_CHANCE = 7000; // 1 out of N
    public static final int LIE_DETECTOR_MIN_LEVEL = 180;


    public static final long FIELD_UPDATE_FREQ = 500; // 2 Hz
    public static final long CHAR_UPDATE_FREQ = 250; // 4 Hz
    public static final int DAILY_RESET_FREQ = 24 * 60 * 60; // 1 day
    public static final int RANKING_REFRESH_FREQ = 1; // 1 hour

    // World Map Bookmark
    public static final int WORLD_MAP_BOOKMARK_LENGTH = 30;

    // AD Board
    public static final int MAX_AD_BOARD_MSG_LEN = 40;

    public static final int randomPortalInfernoWolfCoinFormula(long dmg) { // returns the amount of coins gained depending on dmg done
        if (dmg <= 0) {
            return 0;
        } else if (dmg <         400_000_000L) {
            return Util.getRandom(5, 8); // 5 ~ 7 coins
        } else if (dmg <      35_000_000_000L) {
            return Util.getRandom(7, 11); // 7 ~ 10 coins
        } else if (dmg <     500_000_000_000L) {
            return Util.getRandom(8, 12); // 8 ~ 11 coins
        } else if (dmg <   1_500_000_000_000L) {
            return Util.getRandom(11, 15); // 11 ~ 14 coins
        } else if (dmg <   5_000_000_000_000L) {
            return Util.getRandom(13, 17); // 13 ~ 16 coins
        } else if (dmg <   7_500_000_000_000L) {
            return Util.getRandom(19, 23); // 19 ~ 22 coins
        } else if (dmg <  10_700_000_000_000L) {
            return Util.getRandom(20, 26); // 20 ~ 25 coins
        } else {
            return Util.getRandom(23, 30); // 23 ~ 29 coins
        }
    }
    public static final int randomPortalFrittoCoinFormula(long stage) {
        if (stage <= 0) {
            return 0;
        } else if (stage < 2) {
            return Util.getRandom(3, 6); // 3 ~ 5 coins
        } else if (stage < 3) {
            return Util.getRandom(5, 8); // 5 ~ 8 coins
        } else if (stage < 5) {
            return Util.getRandom(8, 11); // 8 ~ 11 coins
        } else if (stage < 7) {
            return Util.getRandom(9, 14); // 9 ~ 13 coins
        } else if (stage < 9) {
            return Util.getRandom(10, 15); // 10 ~ 14 coins
        } else if (stage < 11) {
            return Util.getRandom(13, 20); // 13 ~ 19 coins
        } else if (stage < 13) {
            return Util.getRandom(14, 22); // 14 ~ 21 coins
        } else {
            return 0;
        }
    }


    // Rune
    public static final Rect RUNE_USE_RECT = new Rect(-120, -120, 120, 120); // Must be within this rect  to use a Rune
    public static final int RUNE_RESPAWN_TIME = 15; // minutes
    public static final int RUNE_COOLDOWN_TIME = 15; // minutes, as GMS
    public static final int RUNE_ANTIBOT_TIME = 15; // minutes
    public static final int RUNE_MIN_LEVEL_FOR_ANTIBOT = 150;
    public static final int RUNE_LEVEL_RANGE = 20;
    public static final int THUNDER_RUNE_ATTACK_DELAY = 4; // seconds
    public static final int DARKNESS_RUNE_NUMBER_OF_ELITE_MOBS_SPAWNED = 3; // number of elites spawned when activating Rune of Darkness
    public static final String RUNE_NOTICE_TEXT = "Free the rune and lift the Elite Boss curse! \r\n" +
            "Curse Stage %d: %d%% reduction of EXP, Drop and Meso Rate in effect.";
    public static final int RUNE_CURSE_INTERVAL = 5 * 60 * 1000; // 5 minutes
    // the official Elite Boss curse: EXP/drops x0.5, x0.35, x0.2 and x0 at curse stages 1-4 (5/10/15/20 minutes)
    public static final int[] RUNE_CURSE_RATES = {100, 50, 35, 20, 0};
    public static final int RUNE_SPAWN_CHANCE = 100; // out of 10000
    public static final int LEVEL_RANGE_TO_SPAWN_RUNE = 20;

    // BurningField
    // as official: stages 1-10 at +10% EXP each (+100% at most), a stage more for each tick no one is in the map, a
    // stage less for each tick someone is; hunting maps with monsters of level 100 and up
    public static final int BURNING_FIELD_MAX_LEVEL = 10; //Maximum Burning Field Level
    public static final int BURNING_FIELD_LEVEL_ON_START = BURNING_FIELD_MAX_LEVEL; // maps start fully burning
    public static final int BURNING_FIELD_TIMER = 15; // minutes: a stage less per tick while someone is in the map
    public static final int BURNING_FIELD_RISE_TICKS = 1; // empty ticks for a stage more (faster than the official hour)
    public static final int BURNING_FIELD_MIN_MOB_LEVEL = 100; //Minimum Mob Level for the Field to become a Burning Field
    public static final int BURNING_FIELD_BONUS_EXP_MULTIPLIER_PER_LEVEL = 10; // multiplied by the BurningField Level  =  Bonus Exp% given

    // Burning characters (Hyper Burning MAX 10-260 then Burning Beyond 260-270), see BurningModule
    public static final int BURNING_CHAR_MIN_LEVEL = 10;
    public static final int BURNING_CHAR_MAX_LEVEL = 270;

    // Exp Orb
    public static final int BLUE_EXP_ORB_ID = 2023484;
    public static final double BLUE_EXP_ORB_MULT = 2;
    public static final int PURPLE_EXP_ORB_ID = 2023494;
    public static final double PURPLE_EXP_ORB_MULT = 3.5;
    public static final int RED_EXP_ORB_ID = 2023495;
    public static final double RED_EXP_ORB_MULT = 5;
    public static final int GOLD_EXP_ORB_ID = 2023669;
    public static final double GOLD_EXP_ORB_MULT = 7;

    // Mob
    public static final int MOB_SKILL_CHANCE = 20;
    public static final int MOB_ATTACK_CHANCE = 40;
    public static final int NX_DROP_CHANCE = 50; // % of kills that give NX (boss clears always do)
    public static final int MOB_ATTACK_COOLDOWN_MIN = 3;
    public static final int MOB_ATTACK_COOLDOWN_MAX = 5;
    public static final int MOB_RESUMMON_COOLDOWN = 30; // 2 minutes
    public static final int STUN_COOLDOWN = 6; // 5 seconds   |  Cooldown on user getting stunned
    public static final int MOBS_NEAR_YOUR_LEVEL_RANGE = 20; // +- 20 based off chrLv

    // Elite mob
    public static final int ELITE_MOB_SKILL_COUNT = 2;
    public static final int ELITE_MOB_RESPAWN_TIME = 150; // seconds
    public static final int ELITE_MOB_SPAWN_CHANCE = 5; // out of a 1000
    public static final int ELITE_MOB_DARK_NOTIFICATION = 17;
    public static final int ELITE_BOSS_REQUIRED_KILLS = 20;
    public static final int ELITE_NOTICE_MS = 5000; // elite and Elite Champion map notices (GMS)
    public static final int ELITE_WOLF_NOTICE_MS = 3000;
    public static final Integer[] ELITE_BOSS_TEMPLATES = new Integer[]{8220022, 8220023, 8220024, 8220025, 8220026};
    public static final String ELITE_BOSS_BGM = "Bgm45/Anthem For Heroes";
    public static final long ELITE_BOSS_HP_RATE = 500; // multiplier for boss' hp compared to the mobs on the map
    public static final int ELITE_CHAMPION_CYCLIC_COUNT = 5; // every 5 elite mobs, it will spawn a random elite champion
    public static final int ELITE_CHAMPION_TIME_LIMIT = 150; // 150 seconds

    // Elite Champion
    public static final int ELITE_CHAMPION_ORB = 2023927;

    // FamiliarCodex
    public static final short FAMILIAR_ORB_VITALITY = 300;
    public static final int ACTIVE_FAMILIARS_SLOTS = 3;
    public static final int BADGE_SLOTS = 8; // amount of badge slots
    public static final int MAX_FAMILIAR_SLOTS = 2000; // amount of familiars
    public static final int STARTING_FAMILIAR_SLOTS = 100; // default amount of familiars
    public static final int MAX_SUMMON_GAUGE = 2000;
    public static final int SUMMON_FAMILIARS_SKILL = 80011951; // Beginner tab skill, sends FAMILIAR_SUMMON_REQUEST
    public static final int EXP_PER_KILL = 10;
    public static final int MAX_FAMILIAR_LEVEL_U_L = 7;
    public static final int MAX_FAMILIAR_LEVEL_C_R_E = 5;
    public static final Rect FAMILIAR_AFFECT_NEARBY_ALLIES_RECT = new Rect(-300, -300, 300, 300);
    // Familiar Red Card
    public static final int RANK_UP_CHANCE = 4; // Chance to rank your familiar up
    public static final int SECONDARY_LINE_PRIME_CHANCE = 50; // Chance for the second line of familiar potential to be prime when rolled.


    // Megaphone
    public static final int MEGAPHONE_COOLTIME = 2 * 60 * 1000; // 2 Minutes
    public static final int AVATAR_MEGAPHONE_COOLTIME = 2 * 60 * 1000; // 2 Minutes
    public static final int TRIPLE_MEGAPHONE_COOLTIME = 5 * 60 * 1000; // 5 Minutes


    // Party
    public static final int MAX_PARTY_MOB_LEVEL_DIFF = 5; // x levels lower than mob level
    public static final int MAX_PARTY_CHR_LEVEL_DIFF = 60; // x levels lower than chr level

    // Hyper Stats
    public static final long HYPER_STAT_RESET_COST = 10_000_000;
    public static final long HYPER_STAT_PRESET_SWITCH_COST = 2_000_000;
    public static final int HYPER_STATS_MAX_PRESETS = 3;

    // Cash Shop
    public static final int MAX_CS_ITEMS_PER_PAGE = 12;
    public static final int MAX_LOCKER_SIZE = 200;
    public static final int MAX_CASH_SHOP_BUY_QUANT = 100;

    // Surprise Missions
    public static final int SURPRISE_MISSION_MIN_LEVEL = 33;
    public static final int SURPRISE_MISSION_CLAIM_REWARD_TIME_LIMIT = 60; // seconds
    public static final int SURPRISE_MISSION_COOLTIME = 20 * 60 * 1000; // 20 minutes | Cooltime on mission
    public static final int SURPRISE_MISSION_CHANCE = 5; // chance of succeeding and gaining a mission
    public static final int SURPRISE_MISSION_TRY_FREQUENCY = 3; // 3 minutes | We try to give mission every 3 minutes

    // Field Ownership
    public static final long MAP_OWNERSHIP_LOSS_TIME = 10 * 60 * 1000; // 10 minutes

    // START OF Party Quests
    public static final long PARTY_QUEST_GLOBAL_EXP = 30000000; // The minimum amount of Exp given from a PQ.
    public static final int NODE_STONE_CRAFT_REQ = 35;
    public static final String DEFAULT_SHOP_SCRIPT = "unknown_shop";
    public static final double MAX_PET_DROP_PICKUP_DIST = 350;
    public static final long MAX_AUCTION_QUERY_RESULT_SIZE = 150;
    public static final byte[] SERVER_IP = new byte[]{54, 68, (byte) 160, 34};

    public static final long PARTY_QUEST_EXP_FORMULA(Char chr) {
        // Exp formula for giving Exp from Party Quests
        return PARTY_QUEST_GLOBAL_EXP * (1 + (chr.getParty().getPartyMembers().length * 100 / chr.getParty().getAvgLevel()));
    }

    // Dojo
    public static final int DOJO_DUMMY_DURATION = 10; // Dummy will stay alive for [] minutes, after which it will be removed.
    public static final int DOJO_SPAWM_BOSS_DELAY = 3; // Spawn delay, in seconds, per boss on the Dojo Floors

    // Monster Park
    public static final byte MAX_MONSTER_PARK_RUNS = 99; // Max Monster Park runs per character
    public static final int MONSTER_PARK_EXP_QUEST = 99999; // Quest where the Exp for MP runs gets stored.
    public static final int MONSTER_PARK_ENTRANCE_CHECK_QUEST = 99997; // Quest where the Number of MP runs are stored
    public static final int MONSTER_PARK_TIME = 10 * 60; // 10minutes
    public static final int MONSTER_PARK_DAILY_BONUS_QUEST = 99989; // date of the last first-clear-of-the-day bonus
    public static final int MONSTER_PARK_DAILY_BONUS_MIN = 25; // coins for the first Monster Park clear of the day
    public static final int MONSTER_PARK_DAILY_BONUS_MAX = 50;
    public static final String MONSTER_PARK_CLEAR_EFFECT = "monsterPark/clear"; // field effect when a stage is cleared

    // Lord Pirate Party Quest
    public static final int LORD_PIRATE_QUEST = 99998; // Quest where the NPC state is stored, to close/open portals

    // END OF Party Quests

    //Boss QR Values
    public static final int EASY_HORNTAIL_QUEST = 99996; // Quest where the Spawn state of horntail's heads is stored
    public static final int EASY_HILLA_QUEST = 99995; //Quest where the state of hilla portals is stored
    public static final int ARKARIUM_QUEST = 99994; //Quest wehre difficulty of arkarium is stored
    public static final int LOADED_DICE_SELECTION = 99990; // Quest where the User's Loaded Dice selection is stored

    // Custom Quest QR Values
    public static final int EVAN_INTRO = 99992; // Used for portal script "evanRoom1" so script doesn't loop
    public static final int EVAN_INTRO2 = 99993; // Used for portal script "DragonEggNotice" so script doesn't loop


    // Trading
    public static final int MAX_TRADE_ITEMS = 12; // v271 trade window: slots 0-11 per side (0x141935740)

    // Faming
    public static final int MIN_LEVEL_TO_FAME = 15;
    public static final int FAME_COOLDOWN = 24; // in hours

    // Guild
    public static final int SP_PER_GUILD_LEVEL = 2;
    public static final double CONITRUBITION_PER_EXP = 0.00002;
    public static final double GGP_PER_CONTRIBUTION = 0.3;
    public static final double IGP_PER_CONTRIBUTION = 0.7;
    public static final int GUILD_BBS_RECORDS_PER_PAGE = 6;
    public static final int GGP_FOR_SKILL_RESET = 5;
    public static final int MAX_GUILD_LV = 30;
    public static final int MAX_GUILD_MEMBERS = 200;

    // Monster Collection
    public static final int MOBS_PER_PAGE = 25;
    public static final int MOBS_PER_GROUP = 5;

    // V Matrix
    // v265 Etc/VMatrixOption.img/info: equipSlotMin 12 at level 200, equipSlotMax 30, extendLevel 5, extensionMax 5
    public static final int MAX_NODE_SLOTS = 30;
    public static final int MATRIX_BASE_SLOTS = 12;
    public static final int MATRIX_EXTEND_LEVEL = 5;
    public static final int MATRIX_MAX_PURCHASED_SLOTS = 5;
    /**
     * The level that opens each slot, as the client's matrix window (0x1412985a0; the count is 0x141298570:
     * 12 + (level - 200) / 10 + (level - 200) / 5): 12 at 200, then three per 10 levels (205, 210, 210, 215, 220,
     * 220, ...), all 30 at 260.
     */
    public static final int[] REQ_LV_BY_MATRIX_SLOT_POS = matrixSlotLevels();

    private static int[] matrixSlotLevels() {
        int[] lv = new int[MAX_NODE_SLOTS];
        for (int pos = 0; pos < MAX_NODE_SLOTS; pos++) {
            int k = pos + 1 - MATRIX_BASE_SLOTS;
            if (k <= 0) {
                lv[pos] = 200;
                continue;
            }
            int q = k / 3, rem = k % 3;
            lv[pos] = 200 + 2 * MATRIX_EXTEND_LEVEL * q + (rem >= 1 ? MATRIX_EXTEND_LEVEL : 0)
                    + (rem >= 2 ? MATRIX_EXTEND_LEVEL : 0);
        }
        return lv;
    }

    // Nodestone
    public static final int NODE_SELF_JOB_CHANCE = 75;
    public static final int NODE_ENFORCE_CHANCE = 60;
    public static final int NODE_SKILL_CHANCE = 30;
    public static final int SPECIAL_NODE_ACTIVATION_CD = 30; // 30 seconds

    public static long[] charExp = new long[300];
    public static int[] traitExp = new int[100];
    private static int[][] enchantSuccessRates = new int[25][2];
    private static int[][] enchantSuccessRatesSuperior = new int[15][2];
    private static int[] guildExp = new int[MAX_GUILD_LV];
    public static final int MAX_CHAR_LEVEL = charExp.length;

    // Auction
    public static final long AUCTION_LIST_TIME = 24; // hours
    public static final long AUCTION_DEPOSIT_AMOUNT = 2000;
    public static final long AUCTION_MIN_PRICE = 50;
    public static final long AUCTION_MAX_PRICE = 99_999_999_999L;
    public static final double AUCTION_TAX = 0.95; // 5%

    // Skills
    public static final int TIME_LEAP_QR_KEY = 99996; // Quest where personal Time Leap CDs get stored
    public static final int COOLTIME_THRESHOLD_MS = 10000; // When switch from reduceCooltime to reduceCooltimeR  is
    public static final int NO_REDUCE_COOLTIME_CD_MS = 5000; // if cd is below  this  cdReductions wont apply

    //Merchant
    public static final int MAX_MERCHANT_VISITORS = 6;
    public static final int MAX_MERCHANT_SLOTS = 16;

    // Starforce
    private static final int STARFORCE_LEVELS[][] = {
            {Integer.MAX_VALUE, -1}, // per equip
            {137, (ServerConstants.VERSION >= 197 ? 20 : 13)},
            {127, (ServerConstants.VERSION >= 197 ? 15 : 12)},
            {117, 10},
            {107, 8},
            {95, 5},
    };

    private static final int STARFORCE_LEVELS_SUPERIOR[][] = {
            {Integer.MAX_VALUE, 15},
            {137, 12},
            {127, 10},
            {117, 8},
            {107, 5},
            {95, 3},
    };

    // Harvesting
    private static Map<Integer, Map<Integer, Integer>> gatherDrops; // reactorId -> item -> drop chance (out of 100)

    public static final int HARVEST_HERB_START = 100000;
    public static final int HARVEST_HERB_END = 100013;
    public static final int HARVEST_VEIN_START = 200000;
    public static final int HARVEST_VEIN_END = 200013;

    public static final int MIN_LEVEL_FOR_HIGH_LEVEL_HARVESTING = 130;
    public static final int MAX_HARVESTABLE_REACTORS = 3;
    /**
     * Crafting (professions) is retired on this server: no herbs or veins appear on maps and the Ardentmill NPCs sell
     * the useful crafted goods instead (also in @menu > Shops). Set to false to bring harvesting back.
     */
    public static final boolean CRAFTING_RETIRED = true;
    public static final int HARVEST_TIMER = 3; // every 3 minutes
    public static final int MAX_TRAIT_EXP = 93_596;

    private static List<QuickMoveInfo> quickMoveInfos;

    public static final int EMOTICON_SLOTS = 28; // size of EMOTICONS
    public static final int EMOTICON_SHORTCUT_SLOTS = 10;
    public static final int[] EMOTICON_BASES = new int[] {
            1008, 1009, 1010, 1011, 1012
    };

    public static final int[] EMOTICONS = new int[] {
            10080001,
            10080002,
            10080003,
            10080004,
            10080005,
            10080006,
            10080007,
            10080008,
            10080009,
            10080010,

            10090001,
            10090002,
            10090003,
            10090004,

            10100001,
            10100002,
            10100003,
            10100004,
            10100005,
            10100006,

            10110001,
            10110002,
            10110003,
            10110004,

            10120001,
            10120002,
            10120003,
            10120004,
    };

    static {
        initCharExp();
        initTraitExp();
        initEnchantRates();
        initEnchantRatesSuperior();
        initQuickMove();
        initGuildExp();
        initGatherDrops();
    }

    /** The Quick Move button's "Menu" entry (the boat NPC's icon): opens @menu (see MigrationHandler). */
    public static final int MENU_QUICK_MOVE_NPC = 9072302;
    /** The Quick Move button's Dimensional Mirror entry; the Maple Guide opens it too (NpcHandler). */
    public static final int DIMENSIONAL_MIRROR_NPC = 9010022;

    /** Mr. Wang: a Portable Storage item with this NPC opens storage, not a shop */
    public static final int STORAGE_NPC = 1022005;

    private static void initQuickMove() {
        // GMS v265's list, in code order: the client gives each code its own icon and place, so leaving
        // codes out shows as gaps. The Maple Administrator opens @menu.
        String w = "#fcFFFFFFFF#", g = "#fcFFB7BFC5#";
        quickMoveInfos = new ArrayList<>();
        quickMoveInfos.add(QuickMoveInfo.gms(100, 9071003, 105, 0, "Monster Park",
                "Move to " + w + "Monster Park" + g, "Characters Lv. 105 or higher can visit Monster Park.",
                "quick_monsterPark", "mParkShuttle"));
        quickMoveInfos.add(QuickMoveInfo.gms(101, 9031019, 10, 0, "Ardentmill",
                "Move to the town of professions, " + w + "Ardentmill", "", "quick_MeisterVille", "meisterVilleNpc"));
        quickMoveInfos.add(QuickMoveInfo.gms(102, 9010111, 0, 0, "Legion Coin Shop",
                "Purchase various items from the " + w + "Legion Coin Shop" + g, "", "union_CoinShop", "NPC_unionShop"));
        quickMoveInfos.add(QuickMoveInfo.gms(103, 9001212, 0, 0, "Crystals & Elixir",
                "Sell " + w + "Intense Power Crystals" + g + " or purchase various " + w + "elixirs" + g, "",
                "quick_Collecter", "quick_Collecter"));
        quickMoveInfos.add(QuickMoveInfo.gms(104, 9030300, 0, 0, "Auction House",
                "Use the " + w + "Auction House" + g, "", "quick_auctionMove", "auction_Enter"));
        quickMoveInfos.add(QuickMoveInfo.gms(105, 9062008, 61, 0, "Meso Market",
                "Exchange " + w + "Maple Points" + g + " and " + w + "Mesos" + g, "", "mesoMarketMove", "mesoMarket"));
        quickMoveInfos.add(QuickMoveInfo.gms(106, DIMENSIONAL_MIRROR_NPC, 50, 0, "Dimensional Mirror",
                "Select and enter " + w + "Grand Athenaeum" + g + ", or " + w + "Party Quests" + g, "",
                "DivisionMirror", "unityPortal"));
        quickMoveInfos.add(QuickMoveInfo.gms(107, 3003146, 0, 34120, "Symbol Express Pass",
                "Use Spiegelmann's " + w + "Symbol Express Pass" + g,
                "Characters that completed #qn34120# can use the Symbol Express Pass", "quickpath_symbol",
                "npc_3003146"));
        quickMoveInfos.add(QuickMoveInfo.gms(109, 9010000, 10, 0, "Maple Administrator",
                "Talk with the " + w + "Maple Administrator" + g,
                "Characters Lv. 105 or higher can visit Monster Park.", "quick_adminNPC", "mapleAdmin"));
        quickMoveInfos.add(QuickMoveInfo.gms(110, 9070110, 10, 0, "Storage Room",
                "Use the " + w + "Storage Room" + g, "", "quick_trunk", "9070110"));
    }

    public static List<QuickMoveInfo> getQuickMoveInfos() {
        return quickMoveInfos;
    }

    // EXP to the next level, index = level: the v271 client's own table (so the bar and the server agree).
    // v271 lowered levels 210-259; the rest matches v265.
    // In a holder class: initCharExp runs from a static block above, before a plain field here would be set.
    private static final class V271Exp {
        static final long[] TABLE = {
            0L, 15L, 34L, 57L, 92L, 135L,
            372L, 560L, 840L, 1242L, 1242L, 1242L,
            1242L, 1242L, 1242L, 1490L, 1788L, 2145L,
            2574L, 3088L, 3705L, 4446L, 5335L, 6402L,
            7682L, 9218L, 11061L, 13273L, 15927L, 19112L,
            19112L, 19112L, 19112L, 19112L, 19112L, 22934L,
            27520L, 33024L, 39628L, 47553L, 51357L, 55465L,
            59902L, 64694L, 69869L, 75458L, 81494L, 88013L,
            95054L, 102658L, 110870L, 119739L, 129318L, 139663L,
            150836L, 162902L, 175934L, 190008L, 205208L, 221624L,
            221624L, 221624L, 221624L, 221624L, 221624L, 238245L,
            256113L, 275321L, 295970L, 318167L, 342029L, 367681L,
            395257L, 424901L, 456768L, 488741L, 522952L, 559558L,
            598727L, 640637L, 685481L, 733464L, 784806L, 839742L,
            898523L, 961419L, 1028718L, 1100728L, 1177778L, 1260222L,
            1342136L, 1429374L, 1522283L, 1621231L, 1726611L, 1838840L,
            1958364L, 2085657L, 2221224L, 2365603L, 2365603L, 2365603L,
            2365603L, 2365603L, 2365603L, 2519367L, 2683125L, 2857528L,
            3043267L, 3241079L, 3451749L, 3676112L, 3915059L, 4169537L,
            4440556L, 4729192L, 5036589L, 5363967L, 5712624L, 6083944L,
            6479400L, 6900561L, 7349097L, 7826788L, 8335529L, 8877338L,
            9454364L, 10068897L, 10723375L, 11420394L, 12162719L, 12953295L,
            13795259L, 14691950L, 15646926L, 16663976L, 17747134L, 18900697L,
            20129242L, 21437642L, 22777494L, 24201087L, 25713654L, 27320757L,
            29028304L, 30842573L, 32770233L, 34818372L, 36994520L, 39306677L,
            41763344L, 44373553L, 47146900L, 50093581L, 53224429L, 56550955L,
            60085389L, 63840725L, 67830770L, 72070193L, 76574580L, 81360491L,
            86445521L, 91848366L, 97588888L, 103688193L, 110168705L, 117054249L,
            124370139L, 132143272L, 138750435L, 145687956L, 152972353L, 160620970L,
            168652018L, 177084618L, 185938848L, 195235790L, 204997579L, 215247457L,
            226009829L, 237310320L, 249175836L, 261634627L, 274716358L, 288452175L,
            302874783L, 318018522L, 333919448L, 350615420L, 368146191L, 386553500L,
            405881175L, 426175233L, 447483994L, 469858193L, 493351102L, 518018657L,
            543919589L, 571115568L, 2207026470L, 2471869646L, 2768494003L, 3100713283L,
            3472798876L, 3889534741L, 4356278909L, 4879032378L, 5464516263L, 6120258214L,
            7344309856L, 8152183940L, 9048924173L, 10044305832L, 11149179473L, 13379015367L,
            14583126750L, 15895608157L, 17326212891L, 18885572051L, 22662686461L, 24249074513L,
            25946509728L, 27762765408L, 29706158986L, 35647390783L, 38142708137L, 40812697706L,
            43669586545L, 46726457603L, 56071749123L, 57753901596L, 59486518643L, 61271114202L,
            63109247628L, 75731097153L, 78003030067L, 80343120969L, 82753414598L, 85236017035L,
            102283220442L, 105351717055L, 108512268566L, 111767636622L, 115120665720L, 138144798864L,
            142289142829L, 146557817113L, 150954551626L, 155483188174L, 186579825808L, 192177220582L,
            197942537199L, 203880813314L, 209997237713L, 216297154844L, 222786069489L, 229469651573L,
            236353741120L, 243444353353L, 1731919984062L, 1749239183902L, 1766731575741L, 1784398891498L,
            1802242880412L, 2342915744535L, 2366344901980L, 2390008350999L, 2413908434508L, 2438047518853L,
            5412465491853L, 5466590146771L, 5521256048238L, 5576468608720L, 5632233294807L, 11377111255510L,
            12514822381061L, 13766304619167L, 15142935081083L, 16657228589191L, 33647601750165L, 37012361925181L,
            40713598117699L, 44784957929468L, 49263453722414L, 99512176519276L, 109463394171203L, 120409733588323L,
            132450706947155L, 145695777641870L, 294305470836577L, 323736017920234L, 356109619712257L, 391720581683482L,
            430892639851830L, 870403132500696L, 957443445750765L, 1053187790325841L, 1158506569358425L, 1737759854037637L,
        };
    }

    private static void initCharExp() {
        System.arraycopy(V271Exp.TABLE, 0, charExp, 0, charExp.length);
    }

    public static void main(String[] args) {
        initCharExp();
        System.out.println(charExp[250]); // hardcode-ok
        System.out.println(591608554168L); // hardcode-ok
    }

    private static void initTraitExp() {
        traitExp[0] = 20; traitExp[1] = 26; traitExp[2] = 34; traitExp[3] = 44; traitExp[4] = 57; traitExp[5] = 74; traitExp[6] = 96; traitExp[7] = 125; traitExp[8] = 163; traitExp[9] = 212;
        traitExp[10] = 233; traitExp[11] = 256; traitExp[12] = 282; traitExp[13] = 310; traitExp[14] = 341; traitExp[15] = 375; traitExp[16] = 413; traitExp[17] = 454; traitExp[18] = 499; traitExp[19] = 549;
        traitExp[20] = 565; traitExp[21] = 582; traitExp[22] = 599; traitExp[23] = 617; traitExp[24] = 636; traitExp[25] = 655; traitExp[26] = 675; traitExp[27] = 695; traitExp[28] = 716; traitExp[29] = 737;
        traitExp[30] = 748; traitExp[31] = 759; traitExp[32] = 770; traitExp[33] = 782; traitExp[34] = 794; traitExp[35] = 806; traitExp[36] = 818; traitExp[37] = 830; traitExp[38] = 842; traitExp[39] = 855;
        traitExp[40] = 868; traitExp[41] = 881; traitExp[42] = 894; traitExp[43] = 907; traitExp[44] = 921; traitExp[45] = 935; traitExp[46] = 949; traitExp[47] = 963; traitExp[48] = 977; traitExp[49] = 992;
        traitExp[50] = 1007; traitExp[51] = 1022; traitExp[52] = 1037; traitExp[53] = 1053; traitExp[54] = 1069; traitExp[55] = 1085; traitExp[56] = 1101; traitExp[57] = 1118; traitExp[58] = 1135; traitExp[59] = 1152;
        traitExp[60] = 1169; traitExp[61] = 1187; traitExp[62] = 1205; traitExp[63] = 1223; traitExp[64] = 1241; traitExp[65] = 1260; traitExp[66] = 1279; traitExp[67] = 1298; traitExp[68] = 1317; traitExp[69] = 1337;
        traitExp[70] = 1341; traitExp[71] = 1345; traitExp[72] = 1349; traitExp[73] = 1353; traitExp[74] = 1357; traitExp[75] = 1361; traitExp[76] = 1365; traitExp[77] = 1369; traitExp[78] = 1373; traitExp[79] = 1377;
        traitExp[80] = 1381; traitExp[81] = 1385; traitExp[82] = 1389; traitExp[83] = 1393; traitExp[84] = 1397; traitExp[85] = 1401; traitExp[86] = 1405; traitExp[87] = 1409; traitExp[88] = 1413; traitExp[89] = 1417;
        traitExp[90] = 1421; traitExp[91] = 1425; traitExp[92] = 1429; traitExp[93] = 1433; traitExp[94] = 1437; traitExp[95] = 1441; traitExp[96] = 1445; traitExp[97] = 1449; traitExp[98] = 1453; traitExp[99] = 1457;
    }

    public static int getTraitLevel(int currentExp) {
        var i = 0;
        while (currentExp > 0 && i < 100) {
            currentExp -= traitExp[i];
            i++;
        }
        return currentExp >= 0 ? i : i - 1; // currentExp > 0 = reached a level exactly or above max level
    }

    public static int getTraitHPMP(int level) {
        if(level <= 4) { return 0;}
        if(level <= 9) { return 100;}
        if(level <=14) { return 200;}
        if(level <= 19) { return 300;}
        if(level <= 24) { return 400;}
        if(level <= 29) { return 500;}
        if(level <= 34) { return 600;}
        if(level <= 39) { return 700;}
        if(level <= 44) { return 800;}
        if(level <= 49) { return 900;}
        if(level <= 54) { return 1000;}
        if(level <= 59) { return 1100;}
        if(level <= 65) { return 1200;}
        if(level <= 69) { return 1300;}
        if(level <= 74) { return 1400;}
        if(level <= 79) { return 1500;}
        if(level <= 84) { return 1600;}
        if(level <= 89) { return 1700;}
        if(level <= 94) { return 1800;}
        if(level <= 99) { return 1900;}
        return 2000;
   }

    public static int getMaxStars(Equip equip) {
        if (equip.getInfo() == null) {
            return 0;
        }
        int level = equip.getReqLevel() + equip.getiIncReq();
        var superior = equip.getInfo().isSuperiorEqp();

        var exceptionStars = ItemConstants.getItemStarLimit(equip.getItemId());
        if (exceptionStars != -1) {
            return exceptionStars;
        }
        
        if (superior) {
            if (level < 95) {
                return 3;
            } else if (level < 107) {
                return 5;
            } else if (level < 117) {
                return 8;
            } else if (level < 127) {
                return 10;
            } else if (level < 137) {
                return 12;
            } else {
                return 15;
            }
        } else {
            // GMS v265 (30-star system): 0-94 5, 95-107 8, 108-117 10, 118-127 15, 128-137 20, 138+ 30
            if (level < 95) {
                return 5;
            } else if (level < 108) {
                return 8;
            } else if (level < 118) {
                return 10;
            } else if (level < 128) {
                return 15;
            } else if (level < 138) {
                return 20;
            } else {
                return 30;
            }
        }
    }

    /**
     * Which normal-item Star Force table is used while star_force_always_succeeds is off. true: the GMS v265 table
     * (30-star system, MapleStory Wiki "Star Force Enhancement"; failures keep the star; the window's success
     * is x1.05, see {@link #rollStarForce}). false: the older 25-star-era GMS table (failures above 10 stars drop one,
     * except at 15 and 20).
     */
    public static final boolean STAR_FORCE_WIKI_2024_RATES = true;

    // success / destroy, out of 1000, per star (0 -> 1 first); the rest of each attempt is a failure. Methods, not
    // fields: the static block that loads them runs before fields declared further down are set
    private static int[][] starForceRates2024() {
        return new int[][]{
                {950, 0}, {900, 0}, {850, 0}, {850, 0}, {800, 0}, // 0 -> 5
                {750, 0}, {700, 0}, {650, 0}, {600, 0}, {550, 0}, // 5 -> 10
                {500, 0}, {450, 0}, {400, 0}, {350, 0}, {300, 0}, // 10 -> 15
                {300, 21}, {300, 21}, {150, 68}, {150, 68}, {150, 85}, // 15 -> 20
                {300, 105}, {150, 128}, {150, 170}, {100, 180}, {100, 180}, // 20 -> 25 (21 -> 22 destroy is 12.75%)
                {100, 180}, {70, 186}, {50, 190}, {30, 194}, {10, 198}, // 25 -> 30
        };
    }

    // GMS v265 (Etc/GL_StarforceRecovery.img): the star count a destroyed item's trace comes back with
    public static int getStarForceRecoveryStars(Equip equip, int starsWhenDestroyed) {
        if (equip.getInfo() != null && equip.getInfo().isSuperiorEqp()) {
            return 0;
        }
        if (starsWhenDestroyed >= 26) {
            return 20;
        } else if (starsWhenDestroyed >= 23) {
            return 19;
        } else if (starsWhenDestroyed >= 21) {
            return 17;
        } else if (starsWhenDestroyed == 20) {
            return 15;
        } else if (starsWhenDestroyed >= 15) {
            return 12;
        }
        return starsWhenDestroyed;
    }

    private static int[][] starForceRates25Star() {
        return new int[][]{
                {950, 0}, {900, 0}, {850, 0}, {850, 0}, {800, 0}, // 0 -> 5
                {750, 0}, {700, 0}, {650, 0}, {600, 0}, {550, 0}, // 5 -> 10
                {500, 0}, {450, 0}, {400, 6}, {350, 13}, {300, 14}, // 10 -> 15
                {300, 21}, {300, 21}, {300, 21}, {300, 28}, {300, 28}, // 15 -> 20
                {300, 70}, {300, 70}, {30, 194}, {20, 294}, {10, 396}, // 20 -> 25
        };
    }

    private static void initEnchantRates() {
        enchantSuccessRates = STAR_FORCE_WIKI_2024_RATES ? starForceRates2024() : starForceRates25Star();
    }

    private static void initEnchantRatesSuperior() {
        // superior (Tyrant) items: the wiki has no table for them, so these stay the GMS rates; a failure drops a star
        enchantSuccessRatesSuperior = new int[][]{
                {500, 0}, // 0 -> 1
                {500, 0},
                {450, 0},
                {400, 0},
                {400, 0},

                {400, 18}, // 5 -> 6
                {400, 30},
                {400, 42},
                {400, 60},
                {370, 95},

                {350, 130}, // 10 -> 11
                {350, 163},
                {30, 485},
                {20, 490},
                {10, 495},
        };
    }

    public static void initGuildExp() {
        guildExp[1] = 15000;
        for (int i = 2; i < guildExp.length; i++) {
            guildExp[i] = guildExp[i - 1] + 30000;
        }
    }

    private static void initGatherDrops() {
        gatherDrops = new HashMap<>(); // Map: reactorId -> item -> drop % (out of 100)

        // Base Herbs
        // Silver Herb
        var silverHerbDropMap = new HashMap<Integer, Integer>();
        silverHerbDropMap.put(4022000, 80);
        silverHerbDropMap.put(4022001, 80);
        silverHerbDropMap.put(4023000, 80);
        silverHerbDropMap.put(4023001, 80);
        gatherDrops.put(HARVEST_HERB_START, silverHerbDropMap);

        // Magenta Herb
        var magentaHerbDropMap = new HashMap<Integer, Integer>();
        magentaHerbDropMap.put(4022002, 80);
        magentaHerbDropMap.put(4022003, 80);
        magentaHerbDropMap.put(4023002, 80);
        magentaHerbDropMap.put(4023003, 80);
        gatherDrops.put(HARVEST_HERB_START + 1, magentaHerbDropMap);

        // Blue Herb
        var blueHerbDropMap = new HashMap<Integer, Integer>();
        blueHerbDropMap.put(4022004, 80);
        blueHerbDropMap.put(4022005, 80);
        blueHerbDropMap.put(4023004, 80);
        blueHerbDropMap.put(4023005, 80);
        gatherDrops.put(HARVEST_HERB_START + 2, blueHerbDropMap);

        // Brown Herb
        var brownHerbDropMap = new HashMap<Integer, Integer>();
        brownHerbDropMap.put(4022006, 80);
        brownHerbDropMap.put(4022007, 80);
        brownHerbDropMap.put(4023006, 80);
        brownHerbDropMap.put(4023007, 80);
        gatherDrops.put(HARVEST_HERB_START + 3, brownHerbDropMap);

        // Emerald Herb
        var emeraldHerbDropMap = new HashMap<Integer, Integer>();
        emeraldHerbDropMap.put(4022008, 80);
        emeraldHerbDropMap.put(4022009, 80);
        emeraldHerbDropMap.put(4022010, 80);
        emeraldHerbDropMap.put(4023008, 80);
        emeraldHerbDropMap.put(4023009, 80);
        emeraldHerbDropMap.put(4023010, 80);
        gatherDrops.put(HARVEST_HERB_START + 4, emeraldHerbDropMap);

        // Gold Herb
        var goldHerbDropMap = new HashMap<Integer, Integer>();
        goldHerbDropMap.put(4022011, 80);
        goldHerbDropMap.put(4022012, 80);
        goldHerbDropMap.put(4023011, 80);
        goldHerbDropMap.put(4023012, 80);
        gatherDrops.put(HARVEST_HERB_START + 5, goldHerbDropMap);

        // Aquamarine Herb
        var aquamarineHerbDropMap = new HashMap<Integer, Integer>();
        aquamarineHerbDropMap.put(4022013, 80);
        aquamarineHerbDropMap.put(4022014, 80);
        aquamarineHerbDropMap.put(4023013, 80);
        aquamarineHerbDropMap.put(4023014, 80);
        gatherDrops.put(HARVEST_HERB_START + 6, aquamarineHerbDropMap);

        // Red Herb
        var redHerbDropMap = new HashMap<Integer, Integer>();
        redHerbDropMap.put(4022015, 80);
        redHerbDropMap.put(4022016, 80);
        redHerbDropMap.put(4023015, 80);
        redHerbDropMap.put(4023016, 80);
        gatherDrops.put(HARVEST_HERB_START + 7, redHerbDropMap);

        // Black Herb
        var blackHerbDropMap = new HashMap<Integer, Integer>();
        blackHerbDropMap.put(4022017, 80);
        blackHerbDropMap.put(4022018, 80);
        blackHerbDropMap.put(4023017, 80);
        blackHerbDropMap.put(4023018, 80);
        gatherDrops.put(HARVEST_HERB_START + 8, blackHerbDropMap);

        // Purple Herb
        var purpleHerbDropMap = new HashMap<Integer, Integer>();
        purpleHerbDropMap.put(4022019, 80);
        purpleHerbDropMap.put(4022020, 80);
        purpleHerbDropMap.put(4022021, 80);
        purpleHerbDropMap.put(4023019, 80);
        purpleHerbDropMap.put(4023020, 80);
        purpleHerbDropMap.put(4023021, 80);
        gatherDrops.put(HARVEST_HERB_START + 9, purpleHerbDropMap);

        // Gold Flower, oils 6-8
        var goldFlowerDropMap = new HashMap<Integer, Integer>();
        goldFlowerDropMap.put(4022011, 80);
        goldFlowerDropMap.put(4022012, 80);
        goldFlowerDropMap.put(4022013, 80);
        goldFlowerDropMap.put(4022014, 80);
        goldFlowerDropMap.put(4022015, 60);
        goldFlowerDropMap.put(4022016, 50);
        goldFlowerDropMap.put(4023011, 80);
        goldFlowerDropMap.put(4023012, 80);
        goldFlowerDropMap.put(4023013, 80);
        goldFlowerDropMap.put(4023014, 80);
        goldFlowerDropMap.put(4023015, 60);
        goldFlowerDropMap.put(4023016, 50);
        goldFlowerDropMap.put(4021021, 10);

        goldFlowerDropMap.put(4007000, 30);
        goldFlowerDropMap.put(4007001, 30);
        goldFlowerDropMap.put(4007002, 30);
        goldFlowerDropMap.put(4007003, 30);
        goldFlowerDropMap.put(4007004, 30);
        goldFlowerDropMap.put(4007005, 30);
        goldFlowerDropMap.put(4007006, 30);
        goldFlowerDropMap.put(4007007, 30);
        goldFlowerDropMap.put(4021022, 50);
        goldFlowerDropMap.put(4023023, 50);
        goldFlowerDropMap.put(4023024, 10);
        goldFlowerDropMap.put(4022023, 50);
        goldFlowerDropMap.put(4022024, 10);

        goldFlowerDropMap.put(4020009, 20);
        goldFlowerDropMap.put(2048717, 5);
        goldFlowerDropMap.put(2048716, 15);

        gatherDrops.put(HARVEST_HERB_START + 11, goldFlowerDropMap);

        // Mysterious Herb, oils 7-10
        var mysteriousHerbDropMap = new HashMap<Integer, Integer>();
        mysteriousHerbDropMap.put(4022013, 40);
        mysteriousHerbDropMap.put(4022014, 50);
        mysteriousHerbDropMap.put(4022015, 80);
        mysteriousHerbDropMap.put(4022016, 80);
        mysteriousHerbDropMap.put(4022017, 90);
        mysteriousHerbDropMap.put(4022018, 90);
        mysteriousHerbDropMap.put(4022019, 90);
        mysteriousHerbDropMap.put(4022020, 90);
        mysteriousHerbDropMap.put(4023013, 40);
        mysteriousHerbDropMap.put(4023014, 50);
        mysteriousHerbDropMap.put(4023015, 80);
        mysteriousHerbDropMap.put(4023016, 80);
        mysteriousHerbDropMap.put(4023017, 90);
        mysteriousHerbDropMap.put(4023018, 90);
        mysteriousHerbDropMap.put(4023019, 90);
        mysteriousHerbDropMap.put(4023020, 90);

        mysteriousHerbDropMap.put(4007000, 30);
        mysteriousHerbDropMap.put(4007001, 30);
        mysteriousHerbDropMap.put(4007002, 30);
        mysteriousHerbDropMap.put(4007003, 30);
        mysteriousHerbDropMap.put(4007004, 30);
        mysteriousHerbDropMap.put(4007005, 30);
        mysteriousHerbDropMap.put(4007006, 30);
        mysteriousHerbDropMap.put(4007007, 30);
        mysteriousHerbDropMap.put(4022023, 50);
        mysteriousHerbDropMap.put(4021022, 50);
        mysteriousHerbDropMap.put(4021021, 10);
        mysteriousHerbDropMap.put(4023023, 50);
        mysteriousHerbDropMap.put(4023024, 10);

        mysteriousHerbDropMap.put(4020009, 20);
        mysteriousHerbDropMap.put(2048717, 5);
        mysteriousHerbDropMap.put(2048716, 15);
        gatherDrops.put(HARVEST_HERB_START + 12, mysteriousHerbDropMap);

        // Mysterious Legendary Herb, oils 8-10
        var mysteriousLegendaryHerbDropMap = new HashMap<Integer, Integer>();
        mysteriousLegendaryHerbDropMap.put(4022013, 40);
        mysteriousLegendaryHerbDropMap.put(4022014, 50);
        mysteriousLegendaryHerbDropMap.put(4022015, 80);
        mysteriousLegendaryHerbDropMap.put(4022016, 80);
        mysteriousLegendaryHerbDropMap.put(4022017, 95);
        mysteriousLegendaryHerbDropMap.put(4022018, 95);
        mysteriousLegendaryHerbDropMap.put(4022019, 95);
        mysteriousLegendaryHerbDropMap.put(4022020, 95);
        mysteriousLegendaryHerbDropMap.put(4022021, 50);

        mysteriousLegendaryHerbDropMap.put(4023013, 40);
        mysteriousLegendaryHerbDropMap.put(4023014, 50);
        mysteriousLegendaryHerbDropMap.put(4023015, 80);
        mysteriousLegendaryHerbDropMap.put(4023016, 80);
        mysteriousLegendaryHerbDropMap.put(4023017, 95);
        mysteriousLegendaryHerbDropMap.put(4023018, 95);
        mysteriousLegendaryHerbDropMap.put(4023019, 95);
        mysteriousLegendaryHerbDropMap.put(4023020, 95);
        mysteriousLegendaryHerbDropMap.put(4023021, 50);

        mysteriousLegendaryHerbDropMap.put(4021022, 50);
        mysteriousLegendaryHerbDropMap.put(4021021, 10);

        mysteriousLegendaryHerbDropMap.put(4007000, 30);
        mysteriousLegendaryHerbDropMap.put(4007001, 30);
        mysteriousLegendaryHerbDropMap.put(4007002, 30);
        mysteriousLegendaryHerbDropMap.put(4007003, 30);
        mysteriousLegendaryHerbDropMap.put(4007004, 30);
        mysteriousLegendaryHerbDropMap.put(4007005, 30);
        mysteriousLegendaryHerbDropMap.put(4007006, 30);
        mysteriousLegendaryHerbDropMap.put(4007007, 30);
        mysteriousLegendaryHerbDropMap.put(4022023, 50);
        mysteriousLegendaryHerbDropMap.put(4023023, 50);
        mysteriousLegendaryHerbDropMap.put(4023024, 10);

        mysteriousLegendaryHerbDropMap.put(4020009, 20);
        mysteriousLegendaryHerbDropMap.put(2048717, 5);
        mysteriousLegendaryHerbDropMap.put(2048716, 15);
        gatherDrops.put(HARVEST_HERB_START + 13, mysteriousLegendaryHerbDropMap);

        // Base Veins
        // Silver Vein
        var silverVeinDropMap = new HashMap<Integer, Integer>();
        silverVeinDropMap.put(4034815, 80);
        silverVeinDropMap.put(4010001, 80);
        gatherDrops.put(HARVEST_VEIN_START, silverVeinDropMap);

        // Magenta Vein
        var magentaVeinDropMap = new HashMap<Integer, Integer>();
        magentaVeinDropMap.put(4010005, 80);
        magentaVeinDropMap.put(4020001, 80);
        gatherDrops.put(HARVEST_VEIN_START + 1, magentaVeinDropMap);

        // Blue Vein
        var blueVeinDropMap = new HashMap<Integer, Integer>();
        blueVeinDropMap.put(4020005, 80);
        blueVeinDropMap.put(4010001, 80);
        gatherDrops.put(HARVEST_VEIN_START + 2, blueVeinDropMap);

        // Brown Vein
        var brownVeinDropMap = new HashMap<Integer, Integer>();
        brownVeinDropMap.put(4010000, 80);
        brownVeinDropMap.put(4010003, 80);
        gatherDrops.put(HARVEST_VEIN_START + 3, brownVeinDropMap);

        // Emerald Vein
        var emeraldVeinDropMap = new HashMap<Integer, Integer>();
        emeraldVeinDropMap.put(4020003, 80);
        emeraldVeinDropMap.put(4010002, 80);
        emeraldVeinDropMap.put(4004002, 80);
        gatherDrops.put(HARVEST_VEIN_START + 4, emeraldVeinDropMap);

        // Gold Vein
        var goldVeinDropMap = new HashMap<Integer, Integer>();
        goldVeinDropMap.put(4010006, 80);
        goldVeinDropMap.put(4020006, 80);
        gatherDrops.put(HARVEST_VEIN_START + 5, goldVeinDropMap);

        // Aquamarine Vein
        var aquamarineVeinDropMap = new HashMap<Integer, Integer>();
        aquamarineVeinDropMap.put(4020002, 80);
        aquamarineVeinDropMap.put(4020007, 80);
        gatherDrops.put(HARVEST_VEIN_START + 6, aquamarineVeinDropMap);

        // Red Vein
        var redVeinDropMap = new HashMap<Integer, Integer>();
        redVeinDropMap.put(4004000, 80);
        redVeinDropMap.put(4020000, 80);
        gatherDrops.put(HARVEST_VEIN_START + 7, redVeinDropMap);

        // Black Vein
        var blackVeinDropMap = new HashMap<Integer, Integer>();
        blackVeinDropMap.put(4020008, 80);
        blackVeinDropMap.put(4004004, 80);
        gatherDrops.put(HARVEST_VEIN_START + 8, blackVeinDropMap);

        // Purple Vein
        var purpleVeinDropMap = new HashMap<Integer, Integer>();
        purpleVeinDropMap.put(4004001, 80);
        purpleVeinDropMap.put(4004003, 80);
        purpleVeinDropMap.put(4010007, 80);
        gatherDrops.put(HARVEST_VEIN_START + 9, purpleVeinDropMap);

        // Heartstone, gems 6-8
        var heartstoneDropMap = new HashMap<Integer, Integer>();
        heartstoneDropMap.put(4011006, 80);
        heartstoneDropMap.put(4021006, 80);
        heartstoneDropMap.put(4021002, 80);
        heartstoneDropMap.put(4021007, 80);
        heartstoneDropMap.put(4005000, 60);
        heartstoneDropMap.put(4021000, 50);
        heartstoneDropMap.put(4021021, 10);

        heartstoneDropMap.put(4007000, 30);
        heartstoneDropMap.put(4007001, 30);
        heartstoneDropMap.put(4007002, 30);
        heartstoneDropMap.put(4007003, 30);
        heartstoneDropMap.put(4007004, 30);
        heartstoneDropMap.put(4007005, 30);
        heartstoneDropMap.put(4007006, 30);
        heartstoneDropMap.put(4007007, 30);
        heartstoneDropMap.put(4011010, 50);
        heartstoneDropMap.put(4021022, 50);
        heartstoneDropMap.put(4023023, 50);
        heartstoneDropMap.put(4023024, 10);

        heartstoneDropMap.put(4020009, 20);
        heartstoneDropMap.put(2048717, 5);
        heartstoneDropMap.put(2048716, 15);
        gatherDrops.put(HARVEST_VEIN_START + 11, heartstoneDropMap);

        // Mysterious Vein, oils 7-10
        var mysteriousVeinDropMap = new HashMap<Integer, Integer>();
        mysteriousVeinDropMap.put(4011006, 40);
        mysteriousVeinDropMap.put(4021006, 50);
        mysteriousVeinDropMap.put(4021002, 80);
        mysteriousVeinDropMap.put(4021007, 80);
        mysteriousVeinDropMap.put(4005000, 90);
        mysteriousVeinDropMap.put(4021000, 90);
        mysteriousVeinDropMap.put(4005004, 90);
        mysteriousVeinDropMap.put(4021008, 90);
        mysteriousVeinDropMap.put(4011010, 50);
        mysteriousVeinDropMap.put(4021022, 50);
        mysteriousVeinDropMap.put(4021021, 10);

        mysteriousVeinDropMap.put(4007000, 30);
        mysteriousVeinDropMap.put(4007001, 30);
        mysteriousVeinDropMap.put(4007002, 30);
        mysteriousVeinDropMap.put(4007003, 30);
        mysteriousVeinDropMap.put(4007004, 30);
        mysteriousVeinDropMap.put(4007005, 30);
        mysteriousVeinDropMap.put(4007006, 30);
        mysteriousVeinDropMap.put(4007007, 30);
        mysteriousVeinDropMap.put(4023023, 50);
        mysteriousVeinDropMap.put(4023024, 10);

        mysteriousVeinDropMap.put(4020009, 20);
        mysteriousVeinDropMap.put(2048717, 5);
        mysteriousVeinDropMap.put(2048716, 15);
        gatherDrops.put(HARVEST_VEIN_START + 12, mysteriousVeinDropMap);

        // Mysterious Legendary Vein, oils 8-10
        var mysteriousLegendaryVeinDropMap = new HashMap<Integer, Integer>();
        mysteriousLegendaryVeinDropMap.put(4011006, 40);
        mysteriousLegendaryVeinDropMap.put(4021006, 50);
        mysteriousLegendaryVeinDropMap.put(4021002, 80);
        mysteriousLegendaryVeinDropMap.put(4021007, 80);
        mysteriousLegendaryVeinDropMap.put(4005000, 95);
        mysteriousLegendaryVeinDropMap.put(4021000, 95);
        mysteriousLegendaryVeinDropMap.put(4005001, 95);
        mysteriousLegendaryVeinDropMap.put(4005003, 95);
        mysteriousLegendaryVeinDropMap.put(4011008, 50);
        mysteriousLegendaryVeinDropMap.put(4011010, 50);
        mysteriousLegendaryVeinDropMap.put(4021022, 50);
        mysteriousLegendaryVeinDropMap.put(4021021, 10);

        mysteriousLegendaryVeinDropMap.put(4007000, 30);
        mysteriousLegendaryVeinDropMap.put(4007001, 30);
        mysteriousLegendaryVeinDropMap.put(4007002, 30);
        mysteriousLegendaryVeinDropMap.put(4007003, 30);
        mysteriousLegendaryVeinDropMap.put(4007004, 30);
        mysteriousLegendaryVeinDropMap.put(4007005, 30);
        mysteriousLegendaryVeinDropMap.put(4007006, 30);
        mysteriousLegendaryVeinDropMap.put(4007007, 30);
        mysteriousLegendaryVeinDropMap.put(4023023, 50);
        mysteriousLegendaryVeinDropMap.put(4023024, 10);

        mysteriousLegendaryVeinDropMap.put(4020009, 20);
        mysteriousLegendaryVeinDropMap.put(2048717, 5);
        mysteriousLegendaryVeinDropMap.put(2048716, 15);
        gatherDrops.put(HARVEST_VEIN_START + 13, mysteriousLegendaryVeinDropMap);
    }

    public static long getEnchantmentMesoCost(Equip equip) {
        var reqLevel = equip.getReqLevel();
        var chuc = equip.getChuc();
        var superior = equip.getInfo().isSuperiorEqp();

        // GMS v265: 1000 + L^3 * (S+1) / 25 below 10 stars, then 1000 + L^3 * (S+1)^2.7 / divisor; nearest 100
        double cube = Math.pow(reqLevel, 3);
        double cost;
        if (superior) {
            cost = 1000 + Math.pow(reqLevel, 3.56);
        } else if (chuc < 10) {
            cost = 1000 + cube * (chuc + 1) / 25D;
        } else {
            cost = 1000 + cube * Math.pow(chuc + 1, 2.7) / starForceCostDivisor(chuc);
        }
        return Math.round(cost / 100D) * 100;
    }

    private static double starForceCostDivisor(int chuc) {
        switch (chuc) {
            case 10: return 400;
            case 11: return 220;
            case 12: return 150;
            case 13: return 110;
            case 14: return 75;
            case 17: return 150;
            case 18: return 70;
            case 19: return 45;
            case 21: return 125;
            default: return 200; // 15, 16, 20 and 22+
        }
    }

    public static long getEnchantmentNxCost(Equip equip, long mesoCost) {
        if (equip.getChuc() <= 15) {
            return 0;
        }
        return (long) (mesoCost * 0.012D);
    }

    public static int getVestigeRecoverCost(Equip equip) {
        var level = equip.getReqLevel();

        if (level < 100) {
            return 500000;
        } else if (level < 140) {
            return 1000000;
        } else if (level < 150) {
            return 2000000;
        } else if (level < 160) {
            return 4000000;
        }
        return 11000000;
    }

    /** @return the Star Force success chance (out of 1000) with the setting star_force_always_succeeds in effect */
    public static int getEnchantmentSuccessRate(Equip equip) {
        return getEnchantmentSuccessRate(equip,
                ServerFeatures.on(ServerFeatures.Feature.STAR_FORCE_ALWAYS_SUCCEEDS));
    }

    /** @param alwaysSucceeds every attempt succeeds (the setting star_force_always_succeeds, read once per attempt) */
    public static int getEnchantmentSuccessRate(Equip equip, boolean alwaysSucceeds) {
        if (alwaysSucceeds || equip.getDropStreak() >= 2) {
            return 1000;
        }
        int chuc = equip.getChuc();
        boolean superior = equip.getInfo().isSuperiorEqp();
        int[][] rates = superior ? enchantSuccessRatesSuperior : enchantSuccessRates;
        if (chuc < 0 || chuc >= rates.length) {
            return 0; // the superior table is shorter (15 stars)
        }
        return rates[chuc][0];
    }

    /** @return the Star Force destroy chance (out of 1000) with the setting star_force_always_succeeds in effect */
    public static int getEnchantmentDestroyRate(Equip equip) {
        return getEnchantmentDestroyRate(equip,
                ServerFeatures.on(ServerFeatures.Feature.STAR_FORCE_ALWAYS_SUCCEEDS));
    }

    public static int getEnchantmentDestroyRate(Equip equip, boolean alwaysSucceeds) {
        if (alwaysSucceeds || equip.getDropStreak() >= 2) {
            return 0;
        }
        int chuc = equip.getChuc();
        boolean superior = equip.getInfo().isSuperiorEqp();
        int[][] rates = superior ? enchantSuccessRatesSuperior : enchantSuccessRates;
        if (chuc < 0 || chuc >= rates.length) {
            return 0; // the superior table is shorter (15 stars)
        }
        return rates[chuc][1];
    }

    /**
     * The one place that decides whether a failed Star Force attempt takes a star off: superior items always (above 0
     * stars); normal items only with the 25-star-era table, above 10 stars except at 15 and 20.
     */
    public static boolean canStarForceDrop(Equip equip) {
        int chuc = equip.getChuc();
        if (equip.getInfo().isSuperiorEqp()) {
            return chuc > 0;
        }
        return !STAR_FORCE_WIKI_2024_RATES && chuc > 10 && chuc % 5 != 0;
    }

    /*
     * Star Force modes (the window's destroy-rate slider, sent as 0..3 = modes 1-4) for normal items at 15-21 stars.
     * Per star: base success (out of 1000) and the destroyed share of a failure (out of 1000) per mode, and the cost
     * multiplier per mode. The window shows success x1.05 (the former Star Catch bonus, now always on) and destroy as
     * share x (1 - shown success); mode 4 never destroys.
     */
    private static final int[][] SF_MODE_SUCCESS = {
            {300, 300, 300, 300}, {300, 300, 300, 300}, {150, 150, 150, 150}, // 15-17
            {150, 120, 100, 80}, {150, 120, 100, 80}, {300, 250, 200, 150}, {150, 120, 100, 80}, // 18-21
    };
    private static final int[][] SF_MODE_DESTROY_SHARE = {
            {30, 20, 10, 0}, {30, 20, 10, 0}, {80, 50, 20, 0},
            {80, 50, 20, 0}, {100, 70, 40, 0}, {150, 100, 50, 0}, {150, 100, 50, 0},
    };
    private static final double[][] SF_MODE_COST = {{1, 1.5, 2.5, 3}, {1, 2, 3.5, 6.5}};

    private static boolean hasStarForceModes(Equip equip) {
        int chuc = equip.getChuc();
        return chuc >= 15 && chuc <= 21 && !equip.getInfo().isSuperiorEqp();
    }

    /** The mode that applies: 0 outside 15-21 stars and for superior items; Safeguard at 15-17 means mode 1. */
    public static int starForceMode(Equip equip, int mode, boolean safeguard) {
        if (mode <= 0 || mode > 3 || !hasStarForceModes(equip) || (safeguard && equip.canSafeguardHyperUpgrade())) {
            return 0;
        }
        return mode;
    }

    /** Meso cost of one attempt in a mode, Safeguard (+200%) included. */
    public static long starForceCost(Equip equip, int mode, boolean safeguard) {
        long cost = getEnchantmentMesoCost(equip);
        mode = starForceMode(equip, mode, safeguard);
        if (mode > 0) {
            return Math.round(cost * SF_MODE_COST[equip.getChuc() <= 17 ? 0 : 1][mode]);
        }
        return safeguard && equip.canSafeguardHyperUpgrade() ? cost * 3 : cost;
    }

    /**
     * @return {success, destroyed share of a failure}, both out of 1000: what the window is sent and what a roll uses
     */
    public static int[] starForceChances(Equip equip, int mode, boolean safeguard, boolean alwaysSucceeds) {
        int success = getEnchantmentSuccessRate(equip, alwaysSucceeds);
        if (success >= 1000) {
            return new int[]{1000, 0};
        }
        boolean guarded = safeguard && equip.canSafeguardHyperUpgrade();
        if (hasStarForceModes(equip)) {
            mode = starForceMode(equip, mode, safeguard);
            int row = equip.getChuc() - 15;
            return new int[]{boosted(SF_MODE_SUCCESS[row][mode], 1000),
                    guarded ? 0 : SF_MODE_DESTROY_SHARE[row][mode]};
        }
        int destroy = guarded ? 0 : getEnchantmentDestroyRate(equip, alwaysSucceeds);
        // luck_boost raises the success only; the destroyed share of a failure is the official one
        return new int[]{boosted(success, 1000),
                destroy <= 0 ? 0 : (int) Math.round(destroy * 1000D / (1000 - success))};
    }

    /**
     * Rolls one attempt from {@link #starForceChances}. With windowBonus (Star Force window and @enhance) the success
     * is x1.05, capped at 100%: GMS v271 removed Star Catching and applies its bonus to every attempt, and the client
     * always shows that rate (0x1421f73e0: rate x 105 / 10, out of 10,000). Destroy = share x the rest.
     *
     * @return 1 success, 2 destroyed, 0 failed
     */
    public static int rollStarForce(int[] chances, boolean windowBonus) {
        long shown = Math.min(10000, chances[0] * (windowBonus ? 105L : 100L) / 10); // out of 10,000
        long destroy = (10000 - shown) * chances[1]; // out of 10,000,000
        long r = java.util.concurrent.ThreadLocalRandom.current().nextLong(10_000_000L);
        return r < shown * 1000 ? 1 : r < shown * 1000 + destroy ? 2 : 0;
    }

    // Superior items, per star 1-15. Bands: 0-77, 78-87, 88-97, 98-107, 108-117, 118-127, 128-137, 138-149, 150+
    private static final int[] SUPERIOR_BAND_MIN = {0, 78, 88, 98, 108, 118, 128, 138, 150};
    private static final int[][] SUPERIOR_STAT = { // stars 1-5
            {1, 2, 4, 0, 0}, {2, 3, 5, 0, 0}, {4, 5, 7, 10, 14}, {7, 8, 10, 13, 17}, {9, 10, 12, 15, 19},
            {12, 13, 15, 18, 22}, {14, 15, 17, 20, 24}, {17, 18, 20, 23, 27}, {19, 20, 22, 25, 29},
    };
    private static final int[][] SUPERIOR_ATT = { // stars 6-15
            {}, {}, {}, {}, {5, 6, 7}, {6, 7, 8, 9, 10}, {7, 8, 9, 10, 11, 13, 15},
            {8, 9, 10, 11, 12, 14, 16, 18, 20, 22}, {9, 10, 11, 12, 13, 15, 17, 19, 21, 23},
    };

    private static int superiorBand(int reqLevel) {
        int band = 0;
        while (band + 1 < SUPERIOR_BAND_MIN.length && reqLevel >= SUPERIOR_BAND_MIN[band + 1]) {
            band++;
        }
        return band;
    }

    public static int getStatForSuperiorEnhancement(int reqLevel, short chuc) {
        int[] row = SUPERIOR_STAT[superiorBand(reqLevel)];
        return chuc >= 0 && chuc < row.length ? row[chuc] : 0;
    }

    public static int getAttForSuperiorEnhancement(int reqLevel, short chuc) {
        int[] row = SUPERIOR_ATT[superiorBand(reqLevel)];
        int i = chuc - 5;
        return i >= 0 && i < row.length ? row[i] : 0;
    }

    // Star Force gains for stars 16+, by level band: 128-137, 138-149, 150-159, 160-199, 200-249, 250+
    private static final int[] SF_MAIN_STAT_16_22 = {7, 9, 11, 13, 15, 17};
    private static final int[][] SF_ARMOR_ATT = { // stars 16-30
            {7, 8, 9, 10, 11},
            {8, 9, 10, 11, 12, 13, 15, 17, 19, 21, 22, 23, 24, 25, 26},
            {9, 10, 11, 12, 13, 14, 16, 18, 20, 22, 23, 24, 25, 26, 27},
            {10, 11, 12, 13, 14, 15, 17, 19, 21, 23, 24, 25, 26, 27, 28},
            {12, 13, 14, 15, 16, 17, 19, 21, 23, 25, 26, 27, 28, 29, 30},
            {14, 15, 16, 17, 18, 19, 21, 23, 25, 27, 28, 29, 30, 31, 32},
    };
    // stars 16-25. 250+ and stars 26-30 have no published table: 250+ is the 200 row plus the armor gap (+2),
    // and 26-30 continue +1 per star from 25 like armor does.
    private static final int[][] SF_WEAPON_ATT = {
            {6, 7, 7, 8, 9},
            {7, 8, 8, 9, 10, 11, 12, 30, 31, 32},
            {8, 9, 9, 10, 11, 12, 13, 31, 32, 33},
            {9, 9, 10, 11, 12, 13, 14, 32, 33, 34},
            {13, 13, 14, 14, 15, 16, 17, 34, 35, 36},
            {15, 15, 16, 16, 17, 18, 19, 36, 37, 38},
    };

    private static int starForceBand(int reqLevel) {
        return reqLevel <= 137 ? 0 : reqLevel <= 149 ? 1 : reqLevel <= 159 ? 2 : reqLevel <= 199 ? 3 : reqLevel <= 249 ? 4 : 5;
    }

    private static int starForceAtt(int[][] table, int band, int star) {
        int[] row = table[band];
        int i = star - 16;
        if (i < 0) {
            return 0;
        }
        if (i < row.length) {
            return row[i];
        }
        // past the weapon table (26-30): last listed value, +1 per star
        return table == SF_WEAPON_ATT && row.length == 10 && star <= 30 ? row[row.length - 1] + (i - row.length + 1) : 0;
    }

    /** Stat gained going from {@code chuc} stars to {@code chuc + 1} (normal items). */
    public static int getEquipStatBoost(Equip equip, EnchantStat es, short chuc) {
        int star = chuc + 1;
        int itemId = equip.getItemId();
        boolean weapon = ItemConstants.isWeapon(itemId) && !ItemConstants.isSecondary(itemId);
        int band = starForceBand(equip.getReqLevel() + equip.getiIncReq());
        int stat = 0;
        if (es == EnchantStat.STR || es == EnchantStat.DEX || es == EnchantStat.INT || es == EnchantStat.LUK) {
            stat += star <= 5 ? 2 : star <= 15 ? 3 : star <= 22 ? SF_MAIN_STAT_16_22[band] : 0;
        }
        if (star <= 15 && (es == EnchantStat.MHP || (weapon && es == EnchantStat.MMP))) {
            boolean noHp = ItemConstants.isGlove(itemId) || ItemConstants.isShoe(itemId)
                    || ItemConstants.isFaceAccessory(itemId) || ItemConstants.isEyeAccessory(itemId);
            if (weapon || !noHp) {
                stat += weapon
                        ? (star <= 3 ? 5 : star <= 5 ? 10 : star <= 7 ? 15 : star <= 9 ? 20 : 25)
                        : (star <= 2 ? 5 : star <= 5 ? 10 : star <= 7 ? 15 : star <= 9 ? 20 : 25);
            }
        }
        if (es == EnchantStat.PAD || es == EnchantStat.MAD) {
            if (weapon) {
                int base = es == EnchantStat.PAD ? equip.getiPad() : equip.getiMad();
                if (base > 0) {
                    stat += star <= 15 ? base / 50 + 1 : starForceAtt(SF_WEAPON_ATT, band, star);
                }
            } else {
                stat += starForceAtt(SF_ARMOR_ATT, band, star);
                if (ItemConstants.isGlove(itemId) && (star == 5 || star == 7 || star == 9 || star == 11 || star >= 13) && star <= 15) {
                    stat += 1;
                }
            }
        }
        if (ItemConstants.isShoe(itemId) && (es == EnchantStat.SPEED || es == EnchantStat.JUMP) && star >= 3 && star <= 15) {
            stat += star <= 11 ? 1 : 2;
        }
        return stat;
    }

    public static int getEnchantmentValByChuc(Equip equip, EnchantStat es, short chuc, int curAmount) {
        var baseEquip = ItemData.getEquipInfoById(equip.getItemId());
        if (equip.isCash() || baseEquip == null || (baseEquip.getTuc() <= 0 && !ItemConstants.isTucIgnoreItem(equip.getItemId()))) {
            return 0;
        }
        if (es == EnchantStat.DEF) {
            return (int) (equip.getiPDD() * (ItemConstants.isOverall(equip.getItemId()) ? 0.10 : 0.05));
        }
        if (!equip.getInfo().isSuperiorEqp()) {
            return getEquipStatBoost(equip, es, chuc);
        } else {
            if (es == EnchantStat.STR || es == EnchantStat.DEX || es == EnchantStat.INT || es == EnchantStat.LUK) {
                return getStatForSuperiorEnhancement(equip.getReqLevel() + equip.getiIncReq(), chuc);
            }
            if (es == EnchantStat.PAD || es == EnchantStat.MAD) {
                return getAttForSuperiorEnhancement(equip.getReqLevel() + equip.getiIncReq(), chuc);
            }
        }
        return 0;
    }

    public static BaseStat getMainStatByJob(short job) {
        if (JobConstants.isJett(job) || JobConstants.isCorsair(job) || JobConstants.isWildHunter(job)
                || JobConstants.isMercedes(job) || JobConstants.isAngelicBuster(job) || JobConstants.isWindArcher(job)
                || JobConstants.isMechanic(job) || JobConstants.isAdventurerArcher(job) || JobConstants.isKain(job)
                || JobConstants.isMoXuan(job)) {
            return BaseStat.dex;

        } else if (JobConstants.isBeastTamer(job) || JobConstants.isBlazeWizard(job) || JobConstants.isBishop(job)
                || JobConstants.isEvan(job) || JobConstants.isIceLightning(job) || JobConstants.isFirePoison(job)
                || JobConstants.isAdventurerMage(job) || JobConstants.isKanna(job) || JobConstants.isKinesis(job)
                || JobConstants.isLuminous(job) || JobConstants.isIllium(job) || JobConstants.isBattleMage(job)
                || JobConstants.isLara(job) || JobConstants.isLynn(job) || JobConstants.isSiaAstelle(job)) {
            return BaseStat.inte;

        } else if (JobConstants.isAdventurerThief(job) || JobConstants.isNightLord(job) || JobConstants.isShadower(job)
                || JobConstants.isPhantom(job) || JobConstants.isNightWalker(job) || JobConstants.isDualBlade(job)
                || JobConstants.isCadena(job) || JobConstants.isHoYoung(job) || JobConstants.isKhali(job)) {
            return BaseStat.luk;

        } else if (JobConstants.isDemonAvenger(job)) {
            return BaseStat.mhp;
        } else if (JobConstants.isBeginnerJob(job) || JobConstants.isBuccaneer(job) || JobConstants.isAdventurerPirate(job)
                || JobConstants.isPinkBean(job) || JobConstants.isYeti(job) || JobConstants.isDawnWarrior(job)
                || JobConstants.isKaiser(job)
                || JobConstants.isZero(job) || JobConstants.isDemon(job) || JobConstants.isDemonSlayer(job)
                || JobConstants.isAran(job) || JobConstants.isCannonShooter(job) || JobConstants.isDarkKnight(job)
                || JobConstants.isHero(job) || JobConstants.isPaladin(job) || JobConstants.isBlaster(job)
                || JobConstants.isHayato(job) || JobConstants.isMihile(job) || JobConstants.isShade(job)
                || JobConstants.isThunderBreaker(job) || JobConstants.isAdventurerWarrior(job) || JobConstants.isArk(job)
                || JobConstants.isAdele(job) || JobConstants.isRen(job) || JobConstants.isErelLight(job)) {
            return BaseStat.str;

        } else
            return null;
    }

    public static ItemJob getItemJobByJob(int jobArg) {
        short job = (short) jobArg;
        if (JobConstants.isWarriorEquipJob(job)) {
            return ItemJob.WARRIOR;
        }
        if (JobConstants.isArcherEquipJob(job)) {
            return ItemJob.BOWMAN;
        }
        if (JobConstants.isMageEquipJob(job)) {
            return ItemJob.MAGICIAN;
        }
        if (JobConstants.isThiefEquipJob(job)) {
            return ItemJob.THIEF;
        }
        if (JobConstants.isPirateEquipJob(job)) {
            return ItemJob.PIRATE;
        } else {
            return ItemJob.BEGINNER;
        }
    }

    public static BaseStat getSecStatByMainStat(BaseStat mainStat) {
        if (mainStat == null) {
            return null;
        }
        switch (mainStat) {
            case str:
                return BaseStat.dex;
            case dex:
                return BaseStat.str;
            case inte:
                return BaseStat.luk;
            case luk:
                return BaseStat.dex;
        }
        return null;
    }

    public static double getExpOrbExpModifierById(int itemID) {
        switch (itemID) {
            case BLUE_EXP_ORB_ID:
                return BLUE_EXP_ORB_MULT * COMBO_ORB_EXP_RATE;
            case PURPLE_EXP_ORB_ID:
                return PURPLE_EXP_ORB_MULT * COMBO_ORB_EXP_RATE;
            case RED_EXP_ORB_ID:
                return RED_EXP_ORB_MULT * COMBO_ORB_EXP_RATE;
            case GOLD_EXP_ORB_ID:
                return GOLD_EXP_ORB_MULT * COMBO_ORB_EXP_RATE;

            case ELITE_CHAMPION_ORB:
                return 1;
        }
        return 0;
    }

    /**
     * Gets a list of possible elite stats by mob level.
     *
     * @param level the level of the mob
     * @return list of Triples, each triple indicating the level (left), extra hp rate (mid) and the extra exp/meso drop rate (right).
     */
    public static List<Triple<Integer, Double, Double>> getEliteInfoByMobLevel(int level) {
        List<Triple<Integer, Double, Double>> list = new ArrayList<>();
        if (level < 100) {
            list.add(new Triple<>(0, 21D, 10.5));
            list.add(new Triple<>(1, 29D, 14.5));
            list.add(new Triple<>(2, 38D, 19D));
        } else if (level < 200) {
            list.add(new Triple<>(0, 30D, 15D));
            list.add(new Triple<>(1, 45D, 22.5));
            list.add(new Triple<>(2, 60D, 30D));
        } else {
            // level >= 200
            list.add(new Triple<>(0, 35D, 17.5));
            list.add(new Triple<>(1, 52.5, 26.25));
            list.add(new Triple<>(2, 70D, 35D));
        }
        return list;
    }

    public static double getPartyExpRateByAttackersAndLeechers(int attackers, int leechers) {
        if (leechers == 1) {
            return 0; // Just 1 attacker
        }
        if (attackers >= 3) {
            switch (leechers) {
                case 6:
                    return 1.95;
                case 5:
                    return 1.5;
                case 4:
                    return 1.1;
                default:
                    return 0.75;
            }
        } else {
            switch (leechers) {
                case 6:
                    return 1.65 + attackers * 0.1;
                case 5:
                    return 1.2 + attackers * 0.1;
                case 4:
                    return 0.8 + attackers * 0.1;
                case 3:
                    return 0.4 + attackers * 0.1;
                default:
                    return 0.15 + attackers * 0.1;
            }
        }
    }

    public static long applyTax(long money) {
        // 5% global tax starting from v180ish
        return Math.round(money * 0.95);
    }

    public static int getExpRequiredForNextGuildLevel(int curLevel) {
        if (curLevel >= guildExp.length || curLevel < 1) {
            return Integer.MAX_VALUE;
        }
        return guildExp[curLevel];
    }

    // -1 if not in Maplerunner
    // 0 for lobby
    // N (1-50) for Maplerunner stages
    public static int getMaplerunnerField(int fieldId) {
        // Forest of Tenacity prefix
        if (fieldId / 1000 != 993001) {
            return -1;
        }
        return fieldId % 1000 / 10;
    }

    public static boolean isValidName(String name) {
        return name.length() >= 4 && name.length() <= 13 && Util.isDigitLetterString(name);
    }

    public static boolean isValidEmotion(int emotion) {
        return emotion >= 0 && emotion <= 10;
    }

    public static boolean isFreeMarketField(int id) {
        // room 1~22
        return id > 910000000 && id < 910001000;
    }

    public static int slotsFromLevel(short level) {
        return 4 + (level - 200) / 5;
    }

    public static int getNodeCreateShardCost(int iconID) {
        switch (iconID / 10000000) {
            case 1: // Skill
                return 140;
            case 2: // Enforcement
                return 70;
            case 3: // Special
                return 250;
            default:
                return 0;
        }
    }

    public static double getUnionMultiplier(int level) {
        double multiplier = 0.5; // default for 60~99
        if (level >= 100 && level <= 139) {
            multiplier = 0.4;
        } else if (level >= 140 && level <= 179) {
            multiplier = 0.7;
        } else if (level >= 180 && level <= 199) {
            multiplier = 0.8;
        } else if (level >= 200 && level <= 209) {
            multiplier = 1.0;
        } else if (level >= 210 && level <= 219) {
            multiplier = 1.1;
        } else if (level >= 220 && level <= 229) {
            multiplier = 1.15;
        } else if (level >= 230 && level <= 239) {
            multiplier = 1.2;
        } else if (level >= 240) {
            multiplier = 1.25;
        }
        return multiplier;
    }

    public static UnionChucMultiplier getUnionChucMultiplier(int chuc) {
        // values for chuc 0~59
        double firstMulti = 0.10;
        double secondMulti = 15.0;
        double thirdMulti = 750.0;
        double fourthMulti = 0.0;
        if (chuc >= 60 && chuc <= 119) {
            firstMulti = 0.11;
            secondMulti = 16.5;
            thirdMulti = 825.0;
            fourthMulti = 1250.0;
        } else if (chuc >= 120 && chuc <= 179) {
            firstMulti = 0.12;
            secondMulti = 18.0;
            thirdMulti = 900.0;
            fourthMulti = 2500.0;
        } else if (chuc >= 180 && chuc <= 229) {
            firstMulti = 0.13;
            secondMulti = 19.5;
            thirdMulti = 975.0;
            fourthMulti = 3750.0;
        } else if (chuc >= 230 && chuc <= 259) {
            firstMulti = 0.14;
            secondMulti = 21.0;
            thirdMulti = 1050.0;
            fourthMulti = 5000.0;
        } else if (chuc >= 260 && chuc <= 289) {
            firstMulti = 0.15;
            secondMulti = 22.5;
            thirdMulti = 1125.0;
            fourthMulti = 6250.0;
        } else if (chuc >= 290 && chuc <= 319) {
            firstMulti = 0.16;
            secondMulti = 24.0;
            thirdMulti = 1200.0;
            fourthMulti = 7500.0;
        } else if (chuc >= 320 && chuc <= 349) {
            firstMulti = 0.17;
            secondMulti = 25.5;
            thirdMulti = 1275.0;
            fourthMulti = 8750.0;
        } else if (chuc >= 350 && chuc <= 379) {
            firstMulti = 0.18;
            secondMulti = 27.0;
            thirdMulti = 1350.0;
            fourthMulti = 10000.0;
        } else if (chuc >= 380 && chuc <= 300) {
            firstMulti = 0.19;
            secondMulti = 28.5;
            thirdMulti = 1425.0;
            fourthMulti = 11250.0;
        } else if (chuc >= 400) {
            firstMulti = 0.2;
            secondMulti = 30.0;
            thirdMulti = 1500.0;
            fourthMulti = 12500.0;

        }
        return new UnionChucMultiplier(firstMulti, secondMulti, thirdMulti, fourthMulti);
    }

    public static long calcAuctionCost(long unitPrice, int quant) {
        long price = unitPrice + quant;
        return (long) (AUCTION_DEPOSIT_AMOUNT + price * 0.05);
    }

    public static int getStarForceMultiplier(int perc, int diff) {
        if (perc <= 9) {
            return -100;
        } else if (perc <= 29) {
            return -90;
        } else if (perc <= 49) {
            return -70;
        } else if (perc <= 69) {
            return -50;
        } else if (perc <= 99) {
            return -30;
        } else if (perc == 100) {
            return 0;
        }
        return Math.min(20, diff);
    }

    public static int getArcaneForceMultiplier(int perc) {
        if (perc <= 9) {
            return -90;
        } else if (perc <= 29) {
            return -70;
        } else if (perc <= 49) {
            return -40;
        } else if (perc <= 69) {
            return -30;
        } else if (perc <= 99) {
            return -20;
        } else if (perc <= 109) {
            return 0;
        } else if (perc <= 129) {
            return 10;
        } else if (perc <= 149) {
            return 30;
        }
        return 50;
    }

    public static int[] getHpMpPerLevel(short job) {
        var mainStat = GameConstants.getMainStatByJob(job);
        int hp = HP_PER_LEVEL;
        int mp = MP_PER_LEVEL;
        if (mainStat == BaseStat.str) {
            hp *= STR_HP_MULT;
            mp *= STR_MP_MULT;
        } else if (mainStat == BaseStat.inte) {
            hp *= INT_HP_MULT;
            mp *= INT_MP_MULT;
        }
        return new int[]{hp, mp};
    }

    public static Set<Integer> getRandomHarvestReactorsByLevel(int averageMobLevel, boolean herb) {
        var possibleReactors = new HashSet<Integer>();
        if (herb) {
            if (averageMobLevel >= MIN_LEVEL_FOR_HIGH_LEVEL_HARVESTING) {
                possibleReactors.add(100011);
                possibleReactors.add(100012);
                possibleReactors.add(100013);
            } else {
                possibleReactors.add(100000);
                possibleReactors.add(100001);
                possibleReactors.add(100002);
                possibleReactors.add(100003);
                possibleReactors.add(100004);
                possibleReactors.add(100005);
                possibleReactors.add(100006);
                possibleReactors.add(100007);
                possibleReactors.add(100008);
                possibleReactors.add(100009);
            }
        } else {
            if (averageMobLevel >= MIN_LEVEL_FOR_HIGH_LEVEL_HARVESTING) {
                possibleReactors.add(200011);
                possibleReactors.add(200012);
                possibleReactors.add(200013);
            } else {
                possibleReactors.add(200000);
                possibleReactors.add(200001);
                possibleReactors.add(200002);
                possibleReactors.add(200003);
                possibleReactors.add(200004);
                possibleReactors.add(200005);
                possibleReactors.add(200006);
                possibleReactors.add(200007);
                possibleReactors.add(200008);
                possibleReactors.add(200009);
            }
        }
        return possibleReactors;
    }

    public static int getRequiredMakingSkillForExtraction(int reqLevel) {
        if (reqLevel <= 50) {
            return 1;
        } else if (reqLevel <= 70) {
            return 2;
        } else if (reqLevel <= 90) {
            return 3;
        } else if (reqLevel <= 110) {
            return 4;
        } else if (reqLevel <= 130) {
            return 5;
        } else {
            return 6;
        }
    }

    public static int getExtractionReward(int reqLevel) {
        if (reqLevel <= 60) {
            return 4021013;
        } else if (reqLevel <= 90) {
            return 4021014;
        } else if (reqLevel <= 120) {
            return 4021015;
        } else {
            return 4021016;
        }
    }

    public static int getTraitExpByMakingSkillLevel(int level) {
        return 2 + level * 5;
    }

    public static int getNonCombatLevelByExp(short nonCombatExp) {
        var total = 0;
        var expReq = 20; // for level 0 -> 1
        for (int level = 0; level < 100; level++) {
            total += expReq;
            if (nonCombatExp < total) {
                return level;
            }
            expReq *= getNonCombatLevelMultiplier(level);
        }

        return 100; // max
    }

    private static double getNonCombatLevelMultiplier(int level) {
        if (level >= 0 && level < 10) {
            return 1.3;
        } else if (level < 20) {
            return 1.1;
        } else if (level < 30) {
            return 1.03;
        } else if (level < 70) {
            return 1.015;
        } else {
            return 1.003;
        }
    }

    /**
     * Chance (%) that resetting Inner Ability in the Ability window raises its rank, by the current rank.
     */
    public static int getCharPotRankUpChance(CharPotGrade current) {
        switch (current) {
            case Rare:
                return 20; // -> Epic
            case Epic:
                return 10; // -> Unique
            case Unique:
                return 5; // -> Legendary
            default:
                return 0; // Legendary is the highest
        }
    }

    public static int getCharPotResetCost(CharPotGrade value) {
        switch (value) {
            case Rare:
                return 100;
            case Epic:
                return 100;
            case Unique:
                return 100;
            case Legendary:
                return 100;
        }

        return 100;
    }

    public static int getCharPotLockCost(int size, CharPotGrade gradeVal) {
        if (size == 0 || gradeVal == CharPotGrade.Rare || gradeVal == CharPotGrade.Epic) {
            return 0;
        }

        if (gradeVal == CharPotGrade.Unique) {
            if (size == 1) {
                return 1500;
            }

            return 4000;
        } else {
            if (size == 1) {
                return 3000;
            }

            return 8000;
        }
    }

    public static class UnionChucMultiplier {
        public double firstMulti, secondMulti, thirdMulti, fourthMulti;

        public UnionChucMultiplier(double firstMulti, double secondMulti, double thirdMulti, double fourthMulti) {
            this.firstMulti = firstMulti;
            this.secondMulti = secondMulti;
            this.thirdMulti = thirdMulti;
            this.fourthMulti = fourthMulti;
        }
    }

    public static double getDamageBonusFromLevelDifference(int charLevel, int mobLevel) {
        double mult = 0;
        int diff = charLevel - mobLevel;
        if (diff >= 0) {
            diff = Math.min(diff, 5); // max 5 * 2% extra
            mult = 10 + (diff * 2);
        } else if (diff < 0 && diff >= -5) {
            // can do calc based on diff, but needs some rounding, so just to a switch
            switch (diff) {
                case -1:
                    mult = 1.08 * 0.98;
                    break;
                case -2:
                    mult = 1.06 * 0.95;
                    break;
                case -3:
                    mult = 1.04 * 0.93;
                    break;
                case -4:
                    mult = 1.02 * 0.9;
                    break;
                case -5:
                    mult = 1.0 * 0.88;
                    break;
            }
            mult -= 1;
            mult *= 100;
        } else {
            // diff < 5, max out at 40 diff (=100% damage reduction)
            diff = Math.max(-40, diff);
            mult = Math.round(-15 - (-2.5 * (diff + 6)));
        }
        return mult / 100;
    }

    public static Set<Integer> getGatherDrops(int reactorId) {
        if (!gatherDrops.containsKey(reactorId)) {
            return new HashSet<>();
        }
        var drops = new HashSet<Integer>();
        for (var entry : gatherDrops.get(reactorId).entrySet()) {
            var item = entry.getKey();
            var chance = entry.getValue();
            if (Util.succeedProp(chance)) {
                drops.add(item);
            }
        }
        return drops;
    }

    public static boolean isEmoticon(int id) {
        return (id >= 10080001 && id <= 10080010)
                || (id >= 10090001 && id <= 10090004)
                || (id >= 10100001 && id <= 10100006)
                || (id >= 10110001 && id <= 10110004)
                || (id >= 10120001 && id <= 10120004);
    }

    // content rates moved here from their helpers (this server's values)
    /** @enhance best mode: Spell Trace cost multiplier of the best scroll */
    public static final int AUTO_ENHANCE_BEST_SCROLL_COST_MULTIPLIER = 5;
    /** Nihal trade: Masarayu buys anything at this share of the tier price */
    public static final double NIHAL_MERCIFUL_BUY_RATE = 0.7;
    /** Nihal trade: Moon Bunny sells a few goods at this share of the tier price */
    public static final double NIHAL_DISCOUNT_SELL_RATE = 0.85;
    /** Christmas encyclopedia volume drop, out of 10,000 per monster near your level */
    public static final int CHRISTMAS_VOLUME_CHANCE = 400;
    /** Halloween: % of Elite Bosses that are pumpkins */
    public static final int HALLOWEEN_ELITE_PUMPKIN_CHANCE = 35;
    /** Halloween ticket piece drop, out of 10,000 per monster near your level */
    public static final int HALLOWEEN_TICKET_PIECE_CHANCE = 150;
    /** Elite Champion orbs: EXP as a multiple of the field's lowest monster EXP */
    public static final int ELITE_CHAMPION_ORB_EXP_MULTIPLIER = 15;
    /** Piggy Bar: EXP for a win, as a share of the level's EXP */
    public static final double PIGGY_BAR_WIN_EXP_RATE = 0.03;
    /** Piggy Bar: EXP for a loss, as a share of the level's EXP */
    public static final double PIGGY_BAR_LOSE_EXP_RATE = 0.01;
    /** Mirror Dungeon: clear EXP as a share of the level's EXP */
    public static final double MIRROR_DUNGEON_CLEAR_EXP_RATE = 0.05;
    /** Moon Bunny PQ: % chance a monster drops a Primrose Seed */
    public static final int MOON_BUNNY_PRIMROSE_SEED_CHANCE = 80;
    /** Lord Pirate PQ: % chance of a tier mark */
    public static final int LORD_PIRATE_TIER_MARK_CHANCE = 60;
}
