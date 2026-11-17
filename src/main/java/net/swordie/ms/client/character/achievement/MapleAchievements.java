package net.swordie.ms.client.character.achievement;

import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.jwzlib.WzFile;
import net.swordie.ms.jwzlib.WzObject;
import net.swordie.ms.jwzlib.WzProperty;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.ext.ServerHooks;
import net.swordie.ms.util.FileTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Maple Achievements (Etc.wz/Achievement/AchievementData), kept per account.
 * <p>
 * Client side (v265): requests come on ACHIEVEMENT_REQUEST 962 (int type: 0 + int view a character on the map,
 * 1 rank, 2 + int claim the reward, 3 + int select an insignia, 4 and 5 when the window opens, 6 load (window open,
 * once a day), 7 the Update button). Records go out on MESSAGE type 44
 * (CWvsContext::OnMessage_AchievementInit 0x1454eff20: int n, n x (int achievement, byte mission or -1,
 * byte status, FT time, int 42, S "key=value")); a completion pops up with type 45 (int n, n ints); type 46 the
 * insignias (int n, n x (int id, int id, byte state 1 owned / 2 shown, FT obtained)); type 47 two ints (unread,
 * rank: 1-100 with Platinum or better is Master, below 1 no rank). ACHIEVEMENT_RESULT 231: 6 = global completion
 * counts, 7 = ends the Update button's wait. The character data block (CharEncoder) carries the same records,
 * insignias and rank, so the window is filled at login and in another character's info.
 * <p>
 * The client sums the score and works out the grade itself (AchievementInfo Grade); the server keeps the insignias
 * (rows with achievement id 0, mission = insignia id), unlocks them by grade, and gives each one's crest skill plus
 * Maple Crest 80002323, which the client turns into the shown insignia's crest.
 * <p>
 * Missions the server tracks: field_enter (plain field ids), quest_state_change, mob_kill (mob ids, no party or
 * skill conditions), user_lvup (level range, job codes), achievement_state_change, and the "Open Maple
 * Achievements" script mission, and the kinds with a hook below (Star Force, crafting, lottery, Spell Trace, shop
 * and hunting mesos, Inner Ability, Surprise Missions...). Missions with other conditions (burning phase, field
 * sets, party, elite...) or kinds without a hook aren't tracked, so their achievements stay open.
 */
public class MapleAchievements {

    private static final Logger log = LogManager.getLogger();

    public static final int REQ_VIEW_OTHER = 0;
    public static final int REQ_RANK = 1;
    public static final int REQ_CLAIM = 2;
    public static final int REQ_INSIGNIA = 3;
    public static final int REQ_OPEN = 4;
    public static final int REQ_OPEN_RANK = 5;
    public static final int REQ_LOAD = 6;
    public static final int REQ_UPDATE = 7;

    private static final byte WHOLE = -1;
    private static final byte CLAIMED = -2;
    private static final byte IN_PROGRESS = 1;
    private static final byte DONE = 2;
    private static final int MSG_INIT = 45; // v271 OnMessage 0x145a5bc60: 45-48 (v265 44-47; v271 44 is another type)
    private static final int MSG_COMPLETE = 46;
    private static final int MSG_INSIGNIA = 47;
    private static final int MSG_RANK = 48;
    private static final int RES_COMPLETIONS = 6;
    private static final int RES_UPDATED = 7;
    private static final int OPEN_ACHIEVEMENTS = 1;

    /** insignia rows: achievement id 0, mission = insignia id; status 1 owned, 2 shown */
    private static final int INSIGNIA_ROW = 0;
    private static final byte OWNED = 1;
    private static final byte SHOWN = 2;
    public static final int MAPLE_CREST = 80002323;
    private static final long RANK_REFRESH_MS = 10 * 60 * 1000L;

    /** Etc AchievementInfo Grade (bronze .. diamond in order; grade index = position + 1, master after them). */
    private static final List<String> gradeNames = new ArrayList<>(List.of("bronze", "silver", "gold", "platinum", "diamond"));
    private static final List<Integer> gradeMins = new ArrayList<>(List.of(10, 5000, 20000, 30000, 36000));
    private static int masterRankMax = 100;
    private static String masterNeeds = "platinum";
    /** Etc AchievementInfo Insignia: id -> {grade index, crest skill} */
    private static final Map<Integer, int[]> insignias = new TreeMap<>(Map.of(1, new int[]{1, 80002324},
            2, new int[]{2, SkillConstants.SILVER_CREST}, 3, new int[]{3, SkillConstants.GOLD_CREST}, 4, new int[]{4, SkillConstants.PLATINUM_CREST}, 5, new int[]{5, SkillConstants.DIAMOND_CREST},
            6, new int[]{6, SkillConstants.MASTER_CREST}));

    private static volatile Map<Integer, Integer> rankByAccount = Map.of();
    private static volatile Map<Integer, Integer> completions = Map.of();
    private static volatile long ranksAt;
    private static final Map<net.swordie.ms.client.Client, Boolean> crestChecked =
            Collections.synchronizedMap(new WeakHashMap<>());

    static final class Mission {
        int idx;
        String type;
        long target = 1;
        boolean tracked = true;
        Set<Integer> ids = new HashSet<>(); // fields, quests, mobs or achievements
        int min = Integer.MIN_VALUE, max = Integer.MAX_VALUE; // quest state / level / achievement state
        Set<Integer> jobs = new HashSet<>();
        boolean levelValue; // value = highest level reached (character level, profession level)
        boolean consecutiveDays; // score type consecutive_date_counting: days in a row, a missed day starts over
        String result = ""; // starforce_enchant: success / fail / destroy (comma list); trait name; "success"
        List<String> exKeys = new ArrayList<>(); // quest_qrex_change: quest-ex key = value, all of them
        List<String> exValues = new ArrayList<>();
        String exCompareKey;
        List<int[]> boxRanges = new ArrayList<>(); // lottery_result_item: the boxes (min..max item ids)
        int exCompareValue;
        boolean sum; // score type "sum": the hook's amount (mesos, Spell Traces, minutes...) adds up
        Map<Integer, Integer> skillMin = new HashMap<>(); // ability_change: ability skill -> lowest level
        String flag = ""; // spell_trace_enchant: "innocent" / "white"
        int hitMin = Integer.MIN_VALUE; // user_hit: lowest share of max HP taken in one hit (percent)
        int hpMin = Integer.MIN_VALUE, hpMax = Integer.MAX_VALUE; // user_hit: HP left after the hit
    }

    static final class Info {
        int id;
        int score;
        List<Mission> missions = new ArrayList<>();
        List<Integer> rewards = new ArrayList<>();
    }

    static final class Rec {
        byte status;
        long value;
        long time;
    }

    private static final Map<Integer, Info> infos = new HashMap<>();
    private static final Map<Integer, List<Mission[]>> byField = new HashMap<>();
    private static final Map<Integer, List<Mission[]>> byQuest = new HashMap<>();
    private static final Map<Integer, List<Mission[]>> byMob = new HashMap<>();
    private static final Map<Integer, List<Mission[]>> byAchievement = new HashMap<>();
    private static final Map<Integer, List<Mission[]>> byPickup = new HashMap<>();
    private static final Map<Integer, List<Mission[]>> byProfession = new HashMap<>();
    private static final List<Mission[]> runeMissions = new ArrayList<>();
    private static final List<Mission[]> starForceMissions = new ArrayList<>();
    private static final List<Mission[]> traitMissions = new ArrayList<>();
    private static final Map<Integer, List<Mission[]>> byRecipe = new HashMap<>();
    private static final Map<Integer, List<Mission[]>> byGatherSkill = new HashMap<>();
    private static final List<Mission[]> fameMissions = new ArrayList<>();
    private static final List<Mission[]> comboMissions = new ArrayList<>();
    private static final Map<Integer, List<Mission[]>> byLotteryResult = new HashMap<>();
    private static final List<Mission[]> dailyGiftMissions = new ArrayList<>();
    private static final List<Mission[]> questExMissions = new ArrayList<>();
    private static final List<Mission[]> equipMissions = new ArrayList<>();
    private static final List<Mission[]> collectionMissions = new ArrayList<>();
    private static final List<Mission[]> levelMissions = new ArrayList<>();
    private static final List<Mission[]> surpriseMissions = new ArrayList<>();
    private static final List<Mission[]> mesoPickupMissions = new ArrayList<>();
    private static final List<Mission[]> shopMissions = new ArrayList<>();
    private static final List<Mission[]> spellTraceMissions = new ArrayList<>();
    private static final List<Mission[]> unionPowerMissions = new ArrayList<>();
    private static final List<Mission[]> explorationMissions = new ArrayList<>();
    private static final List<Mission[]> guildContributionMissions = new ArrayList<>();
    private static final List<Mission[]> guildAttendMissions = new ArrayList<>();
    private static final List<Mission[]> popularityMissions = new ArrayList<>();
    private static final List<Mission[]> abilityMissions = new ArrayList<>();
    private static final Map<Integer, List<Mission[]>> byComboOrb = new HashMap<>();
    private static final List<Mission[]> multiKillMissions = new ArrayList<>();
    private static final List<Mission[]> extractionMissions = new ArrayList<>();
    private static final List<Mission[]> traitExpMissions = new ArrayList<>();
    private static final List<Mission[]> fusionMissions = new ArrayList<>();
    private static final List<Mission[]> hitMissions = new ArrayList<>();
    /** server-only record: trait EXP gained today, "day=yyyyMMdd;charisma=..;insight=..;..." (reset day, ResetClock) */
    public static final int TRAIT_TODAY_QR = 89999982;
    private static final Map<Integer, List<Mission[]>> byItemUse = new HashMap<>();
    private static final Map<Mission, Integer> owner = new IdentityHashMap<>();
    private static volatile boolean loaded;

    /** account id -> key (achievement * 1000 + mission & 0xff) -> record */
    private static final Map<Integer, Map<Integer, Rec>> accounts = new ConcurrentHashMap<>();

    // ------------------------------------------------------------------------------------------------------- data

    private static synchronized void ensureLoaded() {
        if (loaded) {
            return;
        }
        loaded = true;
        try {
            WzFile etc = new WzFile(ServerConstants.WZ_DIR + "/Etc.wz");
            loadInfo(etc);
            WzObject<?, ?> root = etc.getChildByPath("Achievement/AchievementData");
            if (root == null) {
                log.warn("Etc.wz has no Achievement/AchievementData: Maple Achievements are off");
                return;
            }
            int tracked = 0;
            for (WzObject<?, ?> node : root) {
                Info info = parse(node);
                if (info == null) {
                    continue;
                }
                infos.put(info.id, info);
                for (Mission m : info.missions) {
                    owner.put(m, info.id);
                    if (!m.tracked) {
                        continue;
                    }
                    tracked++;
                    Mission[] ref = {m};
                    switch (m.type) {
                        case "field_enter" -> m.ids.forEach(id -> byField.computeIfAbsent(id, k -> new ArrayList<>()).add(ref));
                        case "quest_state_change" -> m.ids.forEach(id -> byQuest.computeIfAbsent(id, k -> new ArrayList<>()).add(ref));
                        case "mob_kill" -> m.ids.forEach(id -> byMob.computeIfAbsent(id, k -> new ArrayList<>()).add(ref));
                        case "achievement_state_change" -> m.ids.forEach(id -> byAchievement.computeIfAbsent(id, k -> new ArrayList<>()).add(ref));
                        case "user_lvup" -> levelMissions.add(ref);
                        case "pickup_mob_reward_item" -> m.ids.forEach(id -> byPickup.computeIfAbsent(id, k -> new ArrayList<>()).add(ref));
                        case "makingskill_lvup" -> m.ids.forEach(id -> byProfession.computeIfAbsent(id, k -> new ArrayList<>()).add(ref));
                        case "rune_stone_use_result_success" -> runeMissions.add(ref);
                        case "starforce_enchant" -> starForceMissions.add(ref);
                        case "nc_stat_level_up" -> traitMissions.add(ref);
                        case "makingskill_making" -> m.ids.forEach(id -> byRecipe.computeIfAbsent(id, k -> new ArrayList<>()).add(ref));
                        case "makingskill_gather" -> m.ids.forEach(id -> byGatherSkill.computeIfAbsent(id, k -> new ArrayList<>()).add(ref));
                        case "popularity_give" -> fameMissions.add(ref);
                        case "combokill_increse" -> comboMissions.add(ref);
                        case "lottery_result_item" -> m.ids.forEach(id -> byLotteryResult.computeIfAbsent(id, k -> new ArrayList<>()).add(ref));
                        case "dailygift_get_reward" -> dailyGiftMissions.add(ref);
                        case "quest_qrex_change" -> questExMissions.add(ref);
                        case "item_equip" -> equipMissions.add(ref);
                        case "monstercollection_mob_collect" -> collectionMissions.add(ref);
                        case "suddenmission_complete" -> surpriseMissions.add(ref);
                        case "pickup_mob_reward_meso" -> mesoPickupMissions.add(ref);
                        case "npcshop_buy" -> shopMissions.add(ref);
                        case "spell_trace_enchant" -> spellTraceMissions.add(ref);
                        case "union_attacker_power_change" -> unionPowerMissions.add(ref);
                        case "monstercollection_adv_complete" -> explorationMissions.add(ref);
                        case "guild_commitment_inc" -> guildContributionMissions.add(ref);
                        case "guild_attend_check" -> guildAttendMissions.add(ref);
                        case "popularity_change" -> popularityMissions.add(ref);
                        case "ability_change" -> abilityMissions.add(ref);
                        case "combokill_get_marble" -> m.ids.forEach(id -> byComboOrb.computeIfAbsent(id, k -> new ArrayList<>()).add(ref));
                        case "multikill" -> multiKillMissions.add(ref);
                        case "makingskill_decomposition" -> extractionMissions.add(ref);
                        case "nc_stat_exp_up" -> traitExpMissions.add(ref);
                        case "makingskill_synthesize" -> fusionMissions.add(ref);
                        case "user_hit" -> hitMissions.add(ref);
                        case "item_use" -> m.ids.forEach(id -> byItemUse.computeIfAbsent(id, k -> new ArrayList<>()).add(ref));
                        default -> {
                        }
                    }
                }
            }
            log.info(String.format("Loaded %d Maple Achievements (%d tracked missions)", infos.size(), tracked));
        } catch (Exception e) {
            log.error("Could not read the Maple Achievements", e);
        }
    }

    /** AchievementInfo Grade and Insignia (the defaults above are the v265 values). */
    private static void loadInfo(WzFile etc) {
        try {
            WzObject<?, ?> grade = etc.getChildByPath("Achievement/AchievementInfo.img/Grade");
            if (grade != null) {
                List<Object[]> rows = new ArrayList<>();
                for (WzObject<?, ?> g : grade) {
                    if (g.getChild("scoreMin") != null) {
                        rows.add(new Object[]{g.getName(), intOf(g.getChild("scoreMin"), 0)});
                    } else if (g.getChild("rankMax") != null) {
                        masterRankMax = intOf(g.getChild("rankMax"), masterRankMax);
                        String needs = stringOf(g.getChild("grade"));
                        masterNeeds = needs.isEmpty() ? masterNeeds : needs;
                    }
                }
                if (!rows.isEmpty()) {
                    rows.sort(Comparator.comparingInt(r -> (Integer) r[1]));
                    gradeNames.clear();
                    gradeMins.clear();
                    for (Object[] r : rows) {
                        gradeNames.add((String) r[0]);
                        gradeMins.add((Integer) r[1]);
                    }
                }
            }
            WzObject<?, ?> ins = etc.getChildByPath("Achievement/AchievementInfo.img/Insignia");
            if (ins != null) {
                Map<Integer, int[]> read = new TreeMap<>();
                for (WzObject<?, ?> i : ins) {
                    int g = gradeIndex(stringOf(i.getChild("grade")));
                    int skill = intOf(i.getChild("skill"), 0);
                    if (g > 0 && skill > 0) {
                        read.put(Integer.parseInt(i.getName()), new int[]{g, skill});
                    }
                }
                if (!read.isEmpty()) {
                    insignias.clear();
                    insignias.putAll(read);
                }
            }
        } catch (Exception e) {
            log.warn("Could not read AchievementInfo grades/insignias, using the built-in ones", e);
        }
    }

    /** 1 bronze .. 5 diamond, 6 master; 0 none. */
    private static int gradeIndex(String name) {
        if ("master".equalsIgnoreCase(name)) {
            return gradeNames.size() + 1;
        }
        for (int i = 0; i < gradeNames.size(); i++) {
            if (gradeNames.get(i).equalsIgnoreCase(name)) {
                return i + 1;
            }
        }
        return 0;
    }

    private static Info parse(WzObject<?, ?> node) {
        WzObject<?, ?> infoNode = node.getChild("info");
        if (infoNode == null || infoNode.getChild("block") != null || infoNode.getChild("period") != null) {
            return null; // hidden, or a time-limited event
        }
        Info info = new Info();
        info.id = Integer.parseInt(node.getName().replace(".img", ""));
        info.score = intOf(infoNode.getChild("score"), 0);
        WzObject<?, ?> missions = node.getChild("mission");
        if (missions == null) {
            return null;
        }
        for (WzObject<?, ?> mNode : missions) {
            Mission m = new Mission();
            m.idx = Integer.parseInt(mNode.getName());
            WzObject<?, ?> sub = mNode.getChild("subMission");
            WzObject<?, ?> first = null;
            if (sub != null) {
                for (WzObject<?, ?> s : sub) {
                    first = s;
                    break;
                }
            }
            if (first == null) {
                m.type = "";
                m.tracked = false;
            } else {
                parseMission(m, first);
            }
            info.missions.add(m);
        }
        WzObject<?, ?> reward = node.getChild("reward");
        if (reward != null) {
            for (WzObject<?, ?> r : reward) {
                int id = intOf(r.getChild("id"), 0);
                if (id > 0) {
                    info.rewards.add(id);
                }
            }
        }
        info.missions.sort(Comparator.comparingInt(a -> a.idx));
        return info;
    }

    private static void parseMission(Mission m, WzObject<?, ?> sub) {
        m.type = sub.getName();
        WzObject<?, ?> score = sub.getChild("score");
        if (score != null) {
            m.target = Math.max(1, longOf(score.getChild("targetScore"), 1));
            String type = stringOf(score.getChild("type"));
            String option = stringOf(score.getChild("option"));
            String setValue = stringOf(score.getChild("setValue"));
            boolean groupOk = false;
            if ("sum".equals(type) && SUM_VALUES.contains(m.type + "/" + setValue)) {
                m.sum = true;
                groupOk = true;
            } else if ("set_if_higher".equals(type) && "popularity".equals(setValue) && "popularity_change".equals(m.type)) {
                m.levelValue = true; // highest fame
            } else if ("set".equals(type) && "union_attacker_power_total".equals(setValue)
                    && "union_attacker_power_change".equals(m.type)) {
                m.levelValue = true; // Legion raid power
            } else if ("set_if_higher".equals(type) && "character_level".equals(setValue) && "user_lvup".equals(m.type)) {
                m.levelValue = true; // "Reach Lv. N": the value is the highest level reached
            } else if ("set".equals(type) && "skill_level".equals(setValue) && "makingskill_lvup".equals(m.type)) {
                m.levelValue = true; // profession level: the value is the level reached
            } else if ("item_equip".equals(m.type) && "item_id".equals(setValue)
                    && "item_equipped".equals(stringOf(score.getChild("setGroup")))) {
                m.levelValue = true; // how many of the listed items are worn (kept at its highest)
            } else if ("set".equals(type) && "combokill_count".equals(setValue) && "combokill_increse".equals(m.type)) {
                m.levelValue = true; // highest combo
            } else if ("set".equals(type) && "nc_stat_exp_today".equals(setValue) && "nc_stat_exp_up".equals(m.type)) {
                m.levelValue = true; // trait EXP gained today on one character
            } else if ("set".equals(type) && "nc_stat_level".equals(setValue) && "nc_stat_level_up".equals(m.type)) {
                m.levelValue = true; // trait level
            } else if ("set_if_higher".equals(type) && "monstercollection_mob_count".equals(setValue)
                    && "monstercollection_mob_collect".equals(m.type)) {
                m.levelValue = true; // monsters registered in the Monster Collection
            } else if ("consecutive_date_counting".equals(type) && "guild_attend_check".equals(m.type)) {
                m.consecutiveDays = true; // guild check-in days in a row
            } else if (!type.isEmpty() && !"counting".equals(type)) {
                m.tracked = false; // other set / set_if_higher kinds (groups of jobs and the like)
            }
            if ((!option.isEmpty() && !"none".equals(option)) || (score.getChild("setGroup") != null && !m.levelValue && !groupOk)) {
                m.tracked = false;
            }
        }
        WzObject<?, ?> check = sub.getChild("checkValue");
        Set<String> allowed = switch (m.type) {
            case "field_enter" -> Set.of("field");
            case "quest_state_change" -> Set.of("quest_change_info");
            case "mob_kill" -> Set.of("mob");
            case "user_lvup" -> Set.of("character");
            case "achievement_state_change" -> Set.of("achievement_change_info");
            case "script" -> Set.of();
            case "pickup_mob_reward_item" -> Set.of("item");
            case "rune_stone_use_result_success" -> Set.of("rune_stone_id");
            case "starforce_enchant" -> Set.of("starforce_enchant");
            case "makingskill_lvup" -> Set.of("skill");
            case "nc_stat_level_up", "nc_stat_exp_up" -> Set.of("nc_stat_type");
            case "makingskill_synthesize" -> Set.of("makingskill_result");
            case "user_hit" -> Set.of("hit_damage_percent", "character");
            case "makingskill_making", "makingskill_gather" -> Set.of("skill_id", "makingskill_result");
            case "popularity_give" -> Set.of();
            case "combokill_increse" -> Set.of("combokill_count");
            case "lottery_result_item" -> Set.of("lottery_item_id", "lottery_result_item_id", "lottery_result_item_level");
            case "dailygift_get_reward" -> Set.of();
            case "quest_qrex_change" -> Set.of("quest_change_info");
            case "item_equip" -> Set.of("item_equipped");
            case "monstercollection_mob_collect" -> Set.of();
            case "suddenmission_complete" -> Set.of("quest_id", "suddenmission_clear_time");
            case "pickup_mob_reward_meso" -> Set.of("meso");
            case "spell_trace_enchant" -> Set.of("enchant_result", "spell_trace_enchant");
            case "ability_change" -> Set.of("ability");
            case "combokill_get_marble" -> Set.of("item");
            case "item_use" -> Set.of("item", "inc_hp");
            case "multikill" -> Set.of("multikill_count");
            case "npcshop_buy", "union_attacker_power_change", "monstercollection_adv_complete", "guild_commitment_inc",
                    "popularity_change", "makingskill_decomposition", "guild_attend_check" -> Set.of();
            default -> null;
        };
        if (allowed == null) {
            m.tracked = false;
            return;
        }
        if (check == null) {
            return;
        }
        for (WzObject<?, ?> c : check) {
            if (!allowed.contains(c.getName())) {
                m.tracked = false;
                return;
            }
            switch (c.getName()) {
                case "field" -> {
                    if (c.getChild("condition") != null || c.getChild("field_burning_phase") != null) {
                        m.tracked = false;
                        return;
                    }
                    addIds(m.ids, c, "field_id");
                }
                case "quest_change_info" -> {
                    WzObject<?, ?> q = c.getChild("quest_id");
                    if (q instanceof WzProperty<?>) {
                        m.ids.add(intOf(q, 0));
                    } else if (q != null) {
                        if (q.getChild("condition") != null) {
                            m.tracked = false; // "and"/"or" lists of quests
                            return;
                        }
                        WzObject<?, ?> values = q.getChild("values");
                        if (values != null) {
                            for (WzObject<?, ?> v : values) {
                                m.ids.add(intOf(v, 0));
                            }
                        }
                    }
                    WzObject<?, ?> qex = c.getChild("quest_ex");
                    if (qex != null) {
                        if (qex.getChild("key") != null) {
                            m.exKeys.add(stringOf(qex.getChild("key")));
                            m.exValues.add(stringOf(qex.getChild("value")));
                        } else if (qex.getChild("values") != null) {
                            if (qex.getChild("condition") != null && !"and".equals(stringOf(qex.getChild("condition")))) {
                                m.tracked = false;
                                return;
                            }
                            WzObject<?, ?> vals = qex.getChild("values");
                            for (WzObject<?, ?> v : vals) {
                                m.exKeys.add(stringOf(v.getChild("key")));
                                m.exValues.add(stringOf(v.getChild("value")));
                            }
                        }
                    }
                    WzObject<?, ?> cmp = c.getChild("quest_ex_compare");
                    if (cmp != null) {
                        if (!"greater_than_or_equal_to".equals(stringOf(cmp.getChild("compare")))) {
                            m.tracked = false;
                            return;
                        }
                        m.exCompareKey = stringOf(cmp.getChild("key"));
                        m.exCompareValue = intOf(cmp.getChild("value"), 0);
                    }
                    WzObject<?, ?> st = c.getChild("quest_state");
                    if (st != null) {
                        m.min = intOf(st.getChild("min"), 0);
                        m.max = intOf(st.getChild("max"), 2);
                    }
                }
                case "mob" -> {
                    for (WzObject<?, ?> part : c) {
                        if (!"mob_id".equals(part.getName())) {
                            m.tracked = false; // elite / level conditions
                            return;
                        }
                    }
                    WzObject<?, ?> mid = c.getChild("mob_id");
                    if (mid instanceof WzProperty<?>) {
                        m.ids.add(intOf(mid, 0));
                    } else if (mid != null) {
                        if (mid.getChild("condition") != null && !"or".equals(stringOf(mid.getChild("condition")))) {
                            m.tracked = false;
                            return;
                        }
                        WzObject<?, ?> values = mid.getChild("values");
                        if (values != null) {
                            for (WzObject<?, ?> v : values) {
                                m.ids.add(intOf(v, 0));
                            }
                        }
                    }
                }
                case "character" -> {
                    for (WzObject<?, ?> part : c) {
                        switch (part.getName()) {
                            case "character_level" -> {
                                m.min = intOf(part.getChild("min"), 0);
                                m.max = intOf(part.getChild("max"), Integer.MAX_VALUE);
                            }
                            case "character_jobcode" -> m.jobs.add(intOf(part, 0));
                            case "character_base_hp" -> {
                                m.hpMin = intOf(part.getChild("min"), Integer.MIN_VALUE);
                                m.hpMax = intOf(part.getChild("max"), Integer.MAX_VALUE);
                            }
                            case "values" -> {
                                for (WzObject<?, ?> v : part) {
                                    WzObject<?, ?> jc = v.getChild("character_jobcode");
                                    if (jc != null) {
                                        m.jobs.add(intOf(jc, 0));
                                    }
                                }
                            }
                            case "condition" -> {
                                if (!"or".equals(stringOf(part))) {
                                    m.tracked = false;
                                    return;
                                }
                            }
                            default -> {
                                m.tracked = false;
                                return;
                            }
                        }
                    }
                }
                case "item" -> {
                    if (c.getChild("item_potential") != null) {
                        m.tracked = false;
                        return;
                    }
                    addIds(m.ids, c, "item_id");
                }
                case "nc_stat_type" -> m.result = stringOf(c);
                case "hit_damage_percent" -> m.hitMin = intOf(c.getChild("min"), 0);
                case "suddenmission_clear_time" -> {
                    m.min = intOf(c.getChild("min"), 0);
                    m.max = intOf(c.getChild("max"), Integer.MAX_VALUE);
                }
                case "lottery_item_id" -> {
                    WzObject<?, ?> vals = c.getChild("values");
                    if (vals != null) {
                        for (WzObject<?, ?> v : vals) {
                            m.boxRanges.add(new int[]{intOf(v.getChild("min"), 0), intOf(v.getChild("max"), 0)});
                        }
                    }
                }
                case "lottery_result_item_id" -> m.ids.add(intOf(c, 0));
                case "lottery_result_item_level" -> {
                }
                case "combokill_count" -> {
                    m.min = intOf(c.getChild("min"), 0);
                    m.max = intOf(c.getChild("max"), Integer.MAX_VALUE);
                }
                case "skill_id" -> {
                    WzObject<?, ?> vals = c.getChild("values");
                    if (c instanceof WzProperty<?>) {
                        m.ids.add(intOf(c, 0));
                    } else if (vals != null) {
                        for (WzObject<?, ?> v : vals) {
                            m.ids.add(intOf(v, 0));
                        }
                    }
                }
                case "makingskill_result" -> m.result = intOf(c.getChild("is_success"), 1) == 1 ? "success" : "";
                case "item_equipped" -> addIds(m.ids, c, "item_id");
                case "rune_stone_id" -> {
                    WzObject<?, ?> values = c.getChild("values");
                    if (values != null) {
                        for (WzObject<?, ?> v : values) {
                            m.ids.add(intOf(v, 0));
                        }
                    }
                }
                case "starforce_enchant" -> {
                    if (c.getChild("star_catch_result") != null) {
                        m.tracked = false;
                        return;
                    }
                    WzObject<?, ?> res = c.getChild("starforce_enchant_result");
                    if (res instanceof WzProperty<?>) {
                        m.result = stringOf(res);
                    } else if (res != null && res.getChild("values") != null) {
                        WzObject<?, ?> vals = res.getChild("values");
                        for (WzObject<?, ?> v : vals) {
                            m.result = m.result.isEmpty() ? stringOf(v) : m.result + "," + stringOf(v);
                        }
                    }
                    WzObject<?, ?> stars = c.getChild("starforce_stars");
                    if (stars != null) {
                        m.min = intOf(stars.getChild("min"), 0);
                        m.max = intOf(stars.getChild("max"), Integer.MAX_VALUE);
                    }
                }
                case "skill" -> {
                    WzObject<?, ?> values = c.getChild("values");
                    if (values == null) {
                        m.tracked = false;
                        return;
                    }
                    for (WzObject<?, ?> v : values) {
                        if (v.getChild("skill_level") != null) {
                            m.tracked = false;
                            return;
                        }
                        m.ids.add(intOf(v.getChild("skill_id"), 0));
                    }
                }
                case "achievement_change_info" -> {
                    m.ids.add(intOf(c.getChild("achievement_id"), 0));
                    m.min = intOf(c.getChild("achievement_state"), DONE);
                }
                case "quest_id" -> {
                    if (c instanceof WzProperty<?>) {
                        m.ids.add(intOf(c, 0));
                    } else {
                        WzObject<?, ?> qvals = c.getChild("values");
                        if (qvals != null) {
                            for (WzObject<?, ?> v : qvals) {
                                m.ids.add(intOf(v, 0));
                            }
                        }
                    }
                }
                case "meso", "multikill_count", "inc_hp" -> {
                    m.min = intOf(c.getChild("min"), Integer.MIN_VALUE);
                    m.max = intOf(c.getChild("max"), Integer.MAX_VALUE);
                }
                case "enchant_result" -> {
                    if (intOf(c.getChild("is_success"), 0) == 1) {
                        m.result = "success";
                    }
                    WzObject<?, ?> prob = c.getChild("enchant_success_prob");
                    if (prob != null) {
                        m.min = intOf(prob.getChild("min"), 0);
                        m.max = intOf(prob.getChild("max"), 100);
                    }
                }
                case "spell_trace_enchant" -> {
                    if (intOf(c.getChild("is_innocent_scroll"), 0) == 1) {
                        m.flag = "innocent";
                    } else if (intOf(c.getChild("is_white_scroll"), 0) == 1) {
                        m.flag = "white";
                    }
                }
                case "ability" -> {
                    WzObject<?, ?> grade = c.getChild("ability_grade");
                    if (grade != null) {
                        m.min = intOf(grade.getChild("min"), 0);
                        m.max = intOf(grade.getChild("max"), 3);
                    }
                    WzObject<?, ?> skills = c.getChild("ability_skill");
                    WzObject<?, ?> vals = skills == null ? null : skills.getChild("values");
                    if (vals != null) {
                        for (WzObject<?, ?> v : vals) {
                            WzObject<?, ?> lv = v.getChild("skill_level");
                            m.skillMin.put(intOf(v.getChild("skill_id"), 0), lv == null ? 1 : intOf(lv.getChild("min"), 1));
                        }
                    }
                }
                default -> {
                }
            }
        }
        if (("field_enter".equals(m.type) || "quest_state_change".equals(m.type) || "mob_kill".equals(m.type)
                || "pickup_mob_reward_item".equals(m.type) || "makingskill_lvup".equals(m.type)
                || "makingskill_making".equals(m.type) || "makingskill_gather".equals(m.type)
                || "quest_qrex_change".equals(m.type) || "item_equip".equals(m.type)
                || "combokill_get_marble".equals(m.type) || "item_use".equals(m.type)) && m.ids.isEmpty()) {
            m.tracked = false;
        }
    }

    /** "sum" scores the hooks feed (kind/setValue). */
    private static final Set<String> SUM_VALUES = Set.of("pickup_mob_reward_meso/meso", "npcshop_buy/meso",
            "spell_trace_enchant/spell_trace_enchant_cost", "ability_change/honor_exp_dec",
            "monstercollection_adv_complete/monstercollection_adv_totaltime",
            "guild_commitment_inc/guild_commitment_inc_point");

    private static long longOf(WzObject<?, ?> node, long def) {
        try {
            if (!(node instanceof WzProperty<?> prop) || prop.getValue() == null) {
                return def;
            }
            return prop.getValue() instanceof Number n ? n.longValue() : Long.parseLong(prop.getValue().toString().trim());
        } catch (Exception e) {
            return def;
        }
    }

    private static void addIds(Set<Integer> ids, WzObject<?, ?> node, String key) {
        WzObject<?, ?> one = node.getChild(key);
        if (one instanceof WzProperty<?>) {
            ids.add(intOf(one, 0));
        }
        WzObject<?, ?> values = node.getChild("values");
        if (values != null) {
            for (WzObject<?, ?> v : values) {
                WzObject<?, ?> id = v.getChild(key);
                if (id != null) {
                    ids.add(intOf(id, 0));
                }
            }
        }
    }

    private static int intOf(WzObject<?, ?> node, int def) {
        try {
            return node instanceof WzProperty<?> prop ? prop.getIntValue() : def;
        } catch (Exception e) {
            return def;
        }
    }

    private static String stringOf(WzObject<?, ?> node) {
        return node instanceof WzProperty<?> prop && prop.getValue() != null ? prop.getValue().toString() : "";
    }

    // ------------------------------------------------------------------------------------------------------ state

    private static int key(int id, int mission) {
        return id * 1000 + (mission & 0xff);
    }

    private static boolean skip(Char chr) {
        return chr == null || chr.getAccount() == null
                || (ServerHooks.isAutomated(chr) && !ServerHooks.isTestCharacter(chr));
    }

    private static Map<Integer, Rec> state(Char chr) {
        int accId = chr.getAccount().getId();
        return accounts.computeIfAbsent(accId, id -> {
            Map<Integer, Rec> map = new ConcurrentHashMap<>();
            for (Map<String, Object> row : DatabaseManager.queryRows("SELECT infoid AS i, missionid AS m, status AS s, "
                    + "value AS v, unlocktime AS t FROM achievements WHERE accid = ?", id)) {
                Rec r = new Rec();
                r.status = ((Number) row.get("s")).byteValue();
                r.value = ((Number) row.get("v")).longValue();
                r.time = ((Number) row.get("t")).longValue();
                map.put(key(((Number) row.get("i")).intValue(), ((Number) row.get("m")).intValue()), r);
            }
            return map;
        });
    }

    private static void save(Char chr, int id, int mission, Rec r) {
        DatabaseManager.executeQuery("INSERT INTO achievements (accid, infoid, missionid, status, value, unlocktime) "
                        + "VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE status = VALUES(status), value = VALUES(value), "
                        + "unlocktime = VALUES(unlocktime)",
                chr.getAccount().getId(), id, mission, (int) r.status, r.value, r.time);
    }

    public static boolean isComplete(Char chr, int id) {
        Rec r = state(chr).get(key(id, WHOLE));
        return r != null && r.status == DONE;
    }

    // ---------------------------------------------------------------------------------------------------- packets

    private static void encodeRecord(OutPacket out, int id, int mission, Rec r, String msg) {
        out.encodeInt(id);
        out.encodeByte(mission);
        out.encodeByte(r.status);
        out.encodeFT(r.time > 0 ? FileTime.fromLong(r.time) : FileTime.currentTime());
        out.encodeInt(42);
        out.encodeString(msg);
    }

    private static String msgOf(int id, int mission, Rec r) {
        if (mission == WHOLE) {
            return "Completed!";
        }
        Info info = infos.get(id);
        String type = "";
        if (info != null) {
            for (Mission m : info.missions) {
                if (m.idx == mission) {
                    type = m.type;
                }
            }
        }
        return type + "=" + r.value;
    }

    private static void sendRecords(Char chr, List<Integer> keys) {
        Map<Integer, Rec> st = state(chr);
        List<Integer> list = new ArrayList<>();
        for (int k : keys) {
            if (st.containsKey(k) && (byte) (k % 1000) != CLAIMED && k / 1000 != INSIGNIA_ROW) {
                list.add(k);
            }
        }
        if (list.isEmpty()) {
            return;
        }
        OutPacket out = new OutPacket(OutHeader.MESSAGE);
        out.encodeByte(MSG_INIT);
        out.encodeInt(list.size());
        for (int k : list) {
            int id = k / 1000;
            int mission = (byte) (k % 1000);
            Rec r = st.get(k);
            encodeRecord(out, id, mission, r, msgOf(id, mission, r));
        }
        chr.write(out);
    }

    private static void sendCompleted(Char chr, int id) {
        OutPacket out = new OutPacket(OutHeader.MESSAGE);
        out.encodeByte(MSG_COMPLETE);
        out.encodeInt(1);
        out.encodeInt(id);
        chr.write(out);
    }

    // ----------------------------------------------------------------------------------------------------- engine

    /** Adds progress to a mission; completes it (and its achievement when every mission is done). */
    private static void progress(Char chr, Mission m, long amount, List<Integer> changed) {
        Integer id = owner.get(m);
        if (id == null || isComplete(chr, id)) {
            return;
        }
        Map<Integer, Rec> st = state(chr);
        int k = key(id, m.idx);
        Rec r = st.get(k);
        if (r != null && r.status == DONE) {
            return;
        }
        if (r == null) {
            r = new Rec();
            r.status = IN_PROGRESS;
            st.put(k, r);
        }
        long before = r.value;
        r.value = m.levelValue ? Math.max(r.value, Math.min(m.target, amount)) : Math.min(m.target, r.value + amount);
        if (r.value == before && before > 0) {
            return;
        }
        r.time = FileTime.currentTime().toLong();
        boolean done = r.value >= m.target;
        if (done) {
            r.status = DONE;
        }
        // big kill counts are saved in steps (and always on completion)
        long step = Math.max(1, m.target / (m.sum ? 100 : 20));
        if (done || before / step != r.value / step || before == 0) {
            save(chr, id, m.idx, r);
        }
        changed.add(k);
        if (done) {
            checkWhole(chr, id, changed);
        }
    }

    private static void checkWhole(Char chr, int id, List<Integer> changed) {
        Info info = infos.get(id);
        Map<Integer, Rec> st = state(chr);
        for (Mission m : info.missions) {
            Rec r = st.get(key(id, m.idx));
            if (r == null || r.status != DONE) {
                return;
            }
        }
        Rec whole = new Rec();
        whole.status = DONE;
        whole.time = FileTime.currentTime().toLong();
        st.put(key(id, WHOLE), whole);
        save(chr, id, WHOLE, whole);
        changed.add(key(id, WHOLE));
        sendCompleted(chr, id);
        net.swordie.ms.client.character.SwordieCoinRewards.onAchievement(chr, id, info.score);
        if (info.score > 0) {
            syncInsignias(chr);
        }
        // achievements that need this one
        for (Mission[] ref : byAchievement.getOrDefault(id, List.of())) {
            progress(chr, ref[0], 1, changed);
        }
    }

    private static void run(Char chr, List<Mission[]> missions, long amount) {
        if (missions == null || missions.isEmpty()) {
            return;
        }
        List<Integer> changed = new ArrayList<>();
        for (Mission[] ref : missions) {
            progress(chr, ref[0], amount, changed);
        }
        sendRecords(chr, changed);
    }

    // ------------------------------------------------------------------------------------------------------ hooks

    public static void onFieldEnter(Char chr, int fieldId) {
        if (skip(chr)) {
            return;
        }
        ensureLoaded();
        run(chr, byField.get(fieldId), 1);
        var client = chr.getClient();
        if (client != null && crestChecked.put(client, Boolean.TRUE) == null) {
            // insignias are per account: this character gets the crest skills of ones unlocked on another
            syncInsignias(chr);
        }
    }

    public static void onQuestState(Char chr, int questId, int state) {
        if (skip(chr)) {
            return;
        }
        ensureLoaded();
        List<Mission[]> list = byQuest.get(questId);
        if (list == null) {
            return;
        }
        List<Mission[]> match = new ArrayList<>();
        for (Mission[] ref : list) {
            if (state >= ref[0].min && state <= ref[0].max) {
                match.add(ref);
            }
        }
        run(chr, match, 1);
    }

    public static void onMobKill(Char chr, Mob mob) {
        if (skip(chr) || mob == null) {
            return;
        }
        ensureLoaded();
        run(chr, byMob.get(mob.getTemplateId()), 1);
    }

    public static void onLevelUp(Char chr, int startLevel, int level) {
        if (skip(chr)) {
            return;
        }
        ensureLoaded();
        List<Mission[]> match = new ArrayList<>();
        List<Integer> changed = new ArrayList<>();
        for (Mission[] ref : levelMissions) {
            Mission m = ref[0];
            if (!m.jobs.isEmpty() && !m.jobs.contains((int) chr.getJob())) {
                continue;
            }
            if (m.levelValue) {
                progress(chr, m, level, changed);
                continue;
            }
            for (int lv = startLevel + 1; lv <= level; lv++) {
                if (lv >= m.min && lv <= m.max) {
                    match.add(ref);
                    break;
                }
            }
        }
        for (Mission[] ref : match) {
            progress(chr, ref[0], 1, changed);
        }
        sendRecords(chr, changed);
    }

    /** A monster's drop picked up (pickup_mob_reward_item). */
    public static void onPickupMobDrop(Char chr, int itemId) {
        if (skip(chr)) {
            return;
        }
        ensureLoaded();
        run(chr, byPickup.get(itemId), 1);
    }

    /** A rune used successfully (rune_stone_use_result_success); an empty id list means any rune. */
    public static void onRuneUse(Char chr, int runeType) {
        if (skip(chr)) {
            return;
        }
        ensureLoaded();
        List<Mission[]> match = new ArrayList<>();
        for (Mission[] ref : runeMissions) {
            if (ref[0].ids.isEmpty() || ref[0].ids.contains(runeType)) {
                match.add(ref);
            }
        }
        run(chr, match, 1);
    }

    /** A Star Force attempt: result "success" / "fail" / "destroy", stars after it. */
    public static void onStarForce(Char chr, String result, int stars) {
        if (skip(chr)) {
            return;
        }
        ensureLoaded();
        List<Mission[]> match = new ArrayList<>();
        for (Mission[] ref : starForceMissions) {
            Mission m = ref[0];
            boolean resOk = m.result.isEmpty() || Arrays.asList(m.result.split(",")).contains(result);
            if (resOk && stars >= m.min && stars <= m.max) {
                match.add(ref);
            }
        }
        run(chr, match, 1);
    }

    /** A recipe crafted (makingskill_making). */
    public static void onCraft(Char chr, int recipeId, boolean success) {
        if (skip(chr)) {
            return;
        }
        ensureLoaded();
        List<Mission[]> match = new ArrayList<>();
        for (Mission[] ref : byRecipe.getOrDefault(recipeId, List.of())) {
            if (success || ref[0].result.isEmpty()) {
                match.add(ref);
            }
        }
        run(chr, match, 1);
    }

    /** An herb or vein gathered (makingskill_gather; Herbalism 92000000, Mining 92010000). */
    public static void onGather(Char chr, int makingSkillId) {
        if (skip(chr)) {
            return;
        }
        ensureLoaded();
        run(chr, byGatherSkill.get(makingSkillId), 1);
    }

    /** The combo counter went up (combokill_increse): counting ones count when the combo hits their range's start. */
    public static void onCombo(Char chr, int combo) {
        if (skip(chr) || comboMissions.isEmpty() && loaded) {
            return;
        }
        ensureLoaded();
        List<Integer> changed = new ArrayList<>();
        for (Mission[] ref : comboMissions) {
            Mission m = ref[0];
            if (m.levelValue) {
                if (combo >= m.target && combo % 100 == 0) {
                    progress(chr, m, combo, changed);
                }
            } else if (combo == Math.max(1, m.min)) {
                progress(chr, m, 1, changed);
            }
        }
        sendRecords(chr, changed);
    }

    /** A lottery box opened (lottery_result_item: e.g. the Tower of Oz ring boxes giving a ring). */
    public static void onLottery(Char chr, int boxId, int resultItemId) {
        if (skip(chr)) {
            return;
        }
        ensureLoaded();
        List<Mission[]> match = new ArrayList<>();
        for (Mission[] ref : byLotteryResult.getOrDefault(resultItemId, List.of())) {
            boolean box = ref[0].boxRanges.isEmpty();
            for (int[] r : ref[0].boxRanges) {
                box |= boxId >= r[0] && boxId <= r[1];
            }
            if (box) {
                match.add(ref);
            }
        }
        run(chr, match, 1);
    }

    /** The Daily Gift claimed (dailygift_get_reward, the counting ones; the month ones aren't tracked). */
    public static void onDailyGift(Char chr) {
        if (skip(chr)) {
            return;
        }
        ensureLoaded();
        run(chr, dailyGiftMissions, 1);
    }

    /** Fame given to someone (popularity_give). */
    public static void onFameGiven(Char chr) {
        if (skip(chr)) {
            return;
        }
        ensureLoaded();
        run(chr, fameMissions, 1);
    }

    /** A profession (Herbalism, Mining, Smithing, ...) reached a level (makingskill_lvup). */
    public static void onProfessionLevel(Char chr, int skillId, int level) {
        if (skip(chr)) {
            return;
        }
        ensureLoaded();
        run(chr, byProfession.get(skillId), level);
    }

    /** Runs the missions that pass: sum missions add the amount, counting ones count 1. */
    private static void runSum(Char chr, List<Mission[]> missions, long amount, java.util.function.Predicate<Mission> ok) {
        if (missions == null || missions.isEmpty()) {
            return;
        }
        List<Integer> changed = new ArrayList<>();
        for (Mission[] ref : missions) {
            if (ok == null || ok.test(ref[0])) {
                progress(chr, ref[0], ref[0].sum ? amount : 1, changed);
            }
        }
        sendRecords(chr, changed);
    }

    private static boolean inRange(Mission m, long v) {
        return v >= m.min && v <= m.max;
    }

    private static boolean ready(Char chr) {
        if (skip(chr)) {
            return false;
        }
        ensureLoaded();
        return true;
    }

    /** A Surprise Mission completed (suddenmission_complete; no quest list = any). */
    public static void onSurpriseMission(Char chr, int questId, long seconds) {
        if (ready(chr)) {
            runSum(chr, surpriseMissions, 1, m -> (m.ids.isEmpty() || m.ids.contains(questId))
                    && (m.min == Integer.MIN_VALUE || inRange(m, seconds)));
        }
    }

    /** Mesos picked up from a monster (pickup_mob_reward_meso). */
    public static void onMobMesos(Char chr, long mesos) {
        if (mesos > 0 && ready(chr)) {
            runSum(chr, mesoPickupMissions, mesos, m -> m.sum || inRange(m, mesos));
        }
    }

    /** Mesos spent in an NPC shop (npcshop_buy). */
    public static void onShopBuy(Char chr, long mesos) {
        if (mesos > 0 && ready(chr)) {
            runSum(chr, shopMissions, mesos, null);
        }
    }

    /** A Spell Trace scroll used (spell_trace_enchant): cost, result, the scroll's chance, innocent / clean slate. */
    public static void onSpellTrace(Char chr, int cost, boolean success, int chance, String flag) {
        if (ready(chr)) {
            runSum(chr, spellTraceMissions, cost, m -> m.sum || ((m.result.isEmpty() || success)
                    && inRange(m, chance) && (m.flag.isEmpty() || m.flag.equals(flag))));
        }
    }

    /** A Monster Collection exploration's reward taken (monstercollection_adv_complete: minutes explored). */
    public static void onExplorationDone(Char chr, int minutes) {
        if (minutes > 0 && ready(chr)) {
            runSum(chr, explorationMissions, minutes, null);
        }
    }

    /** Guild contribution gained (guild_commitment_inc). */
    public static void onGuildContribution(Char chr, int points) {
        if (points > 0 && ready(chr)) {
            runSum(chr, guildContributionMissions, points, null);
            onGuildCheckIn(chr);
        }
    }

    /**
     * Guild check-in days in a row (guild_attend_check). The check-in stamps today's date in the guild record
     * (ci=yyyy-mm-dd) and then grants its contribution, so a contribution on a day with that stamp counts the day
     * once per account; the mission's time holds the last day counted.
     */
    private static void onGuildCheckIn(Char chr) {
        if (guildAttendMissions.isEmpty()) {
            return;
        }
        String raw = chr.getQuestManager().getHiddenRecord(net.swordie.ms.handlers.social.module.GuildModule.GUILD_QR);
        java.time.LocalDate today = net.swordie.ms.util.ResetClock.today();
        if (raw == null || !Arrays.asList(raw.split(";")).contains("ci=" + today)) {
            return;
        }
        Map<Integer, Rec> st = state(chr);
        List<Integer> changed = new ArrayList<>();
        for (Mission[] ref : guildAttendMissions) {
            Mission m = ref[0];
            Integer id = owner.get(m);
            if (!m.consecutiveDays || id == null || isComplete(chr, id)) {
                continue;
            }
            int k = key(id, m.idx);
            Rec r = st.get(k);
            if (r != null && r.status == DONE) {
                continue;
            }
            java.time.LocalDate last = r == null || r.time <= 0 ? null
                    : net.swordie.ms.util.ResetClock.dayOf(r.time);
            if (today.equals(last)) {
                continue;
            }
            if (r == null) {
                r = new Rec();
                r.status = IN_PROGRESS;
                st.put(k, r);
            }
            r.value = Math.min(m.target, last != null && last.plusDays(1).equals(today) ? r.value + 1 : 1);
            r.time = System.currentTimeMillis();
            if (r.value >= m.target) {
                r.status = DONE;
            }
            save(chr, id, m.idx, r);
            changed.add(k);
            if (r.status == DONE) {
                checkWhole(chr, id, changed);
            }
        }
        sendRecords(chr, changed);
    }

    /** Fame changed (popularity_change: the highest fame reached). */
    public static void onFameChanged(Char chr, int fame) {
        if (fame > 0 && ready(chr)) {
            run(chr, popularityMissions, fame);
        }
    }

    /**
     * An Inner Ability reset (ability_change): Honor EXP spent, the preset's grade after it (0 rare - 3 legendary)
     * and its lines (skill id -> level).
     */
    public static void onAbilityReset(Char chr, int honorCost, int grade, Map<Integer, Integer> lines) {
        if (ready(chr)) {
            runSum(chr, abilityMissions, honorCost, m -> {
                if (m.sum) {
                    return honorCost > 0;
                }
                if (!m.skillMin.isEmpty()) {
                    return m.skillMin.entrySet().stream().anyMatch(e -> lines.getOrDefault(e.getKey(), 0) >= e.getValue());
                }
                return inRange(m, grade);
            });
        }
    }

    /** A combo kill orb picked up (combokill_get_marble). */
    public static void onComboOrb(Char chr, int itemId) {
        if (ready(chr)) {
            run(chr, byComboOrb.get(itemId), 1);
        }
    }

    /** A multi kill (multikill: mobs killed at once). */
    public static void onMultiKill(Char chr, int mobs) {
        if (ready(chr)) {
            runSum(chr, multiKillMissions, 1, m -> inRange(m, mobs));
        }
    }

    /** Trait EXP gained (nc_stat_exp_up): the day's total on this character for the trait counts. */
    public static void onTraitExp(Char chr, net.swordie.ms.enums.Stat trait, int amount) {
        if (amount <= 0 || !ready(chr)) {
            return;
        }
        String name = switch (trait) {
            case charismaEXP -> "charisma";
            case insightEXP -> "insight";
            case willEXP -> "will";
            case craftEXP -> "craft";
            case senseEXP -> "sense";
            case charmEXP -> "charm";
            default -> null;
        };
        if (name == null) {
            return;
        }
        int total = addTraitToday(chr, name, amount);
        List<Integer> changed = new ArrayList<>();
        for (Mission[] ref : traitExpMissions) {
            if (name.equals(ref[0].result)) {
                progress(chr, ref[0], total, changed);
            }
        }
        sendRecords(chr, changed);
    }

    /** Today's trait EXP per trait name from the record; empty once the day changed. */
    private static Map<String, Integer> traitToday(Char chr) {
        Map<String, Integer> out = new LinkedHashMap<>();
        String raw = chr.getQuestManager().getHiddenRecord(TRAIT_TODAY_QR);
        String today = net.swordie.ms.util.ResetClock.today("yyyyMMdd");
        if (raw == null || !raw.contains("day=" + today)) {
            return out;
        }
        for (String part : raw.split(";")) {
            int eq = part.indexOf('=');
            if (eq > 0 && !part.startsWith("day=")) {
                try {
                    out.put(part.substring(0, eq), Integer.parseInt(part.substring(eq + 1)));
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return out;
    }

    /** Adds trait EXP to today's record and returns the day's total for that trait. */
    private static synchronized int addTraitToday(Char chr, String name, int amount) {
        Map<String, Integer> totals = traitToday(chr);
        int total = totals.merge(name, amount, Integer::sum);
        StringBuilder sb = new StringBuilder("day=")
                .append(net.swordie.ms.util.ResetClock.today("yyyyMMdd"));
        totals.forEach((k, v) -> sb.append(';').append(k).append('=').append(v));
        chr.getQuestManager().setHiddenRecord(TRAIT_TODAY_QR, sb.toString());
        return total;
    }

    /** A Fusion Anvil look applied (makingskill_synthesize). */
    public static void onFusion(Char chr) {
        if (ready(chr)) {
            run(chr, fusionMissions, 1);
        }
    }

    /** A hit taken (user_hit): the share of max HP it took and the HP left. */
    public static void onUserHit(Char chr, int hpBefore, int hpAfter, int maxHp) {
        if (hpAfter >= hpBefore || maxHp <= 0 || !ready(chr)) {
            return;
        }
        int percent = (int) ((hpBefore - hpAfter) * 100L / maxHp);
        runSum(chr, hitMissions, 1, m -> percent >= m.hitMin && hpAfter >= m.hpMin && hpAfter <= m.hpMax);
    }

    /** Progress value of one mission for the account (0 if none yet); for the probe. */
    public static long missionValue(Char chr, int achievementId, int mission) {
        ensureLoaded();
        Rec r = state(chr).get(key(achievementId, mission));
        return r == null ? 0 : r.value;
    }

    /** An equip extracted (makingskill_decomposition). */
    public static void onExtract(Char chr) {
        if (ready(chr)) {
            run(chr, extractionMissions, 1);
        }
    }

    /** A consumable used (item_use), with the HP it recovered. */
    public static void onItemUse(Char chr, int itemId, long incHp) {
        if (ready(chr)) {
            runSum(chr, byItemUse.get(itemId), 1, m -> inRange(m, incHp));
        }
    }

    /**
     * The window's Update button and the first load: state that can be read now (completed quests, the current
     * level) counts, so progress made before this system (or on another of the account's characters) shows.
     */
    private static List<Integer> refresh(Char chr) {
        List<Integer> changed = new ArrayList<>();
        var qm = chr.getQuestManager();
        for (Map.Entry<Integer, List<Mission[]>> e : byQuest.entrySet()) {
            if (qm.hasQuestCompleted(e.getKey())) {
                for (Mission[] ref : e.getValue()) {
                    if (2 >= ref[0].min && 2 <= ref[0].max) {
                        progress(chr, ref[0], 1, changed);
                    }
                }
            }
        }
        int lv = chr.getLevel();
        for (Mission[] ref : levelMissions) {
            Mission m = ref[0];
            if (!m.jobs.isEmpty() && !m.jobs.contains((int) chr.getJob())) {
                continue;
            }
            if (m.levelValue) {
                progress(chr, m, lv, changed);
            } else if (lv >= m.min && m.min == m.max) {
                progress(chr, m, 1, changed);
            }
        }
        if (chr.getField() != null) {
            for (Mission[] ref : byField.getOrDefault(chr.getField().getId(), List.of())) {
                progress(chr, ref[0], 1, changed);
            }
        }
        for (Map.Entry<Integer, List<Mission[]>> e : byProfession.entrySet()) {
            int plv = chr.getMakingSkillLevel(e.getKey());
            if (plv > 0) {
                for (Mission[] ref : e.getValue()) {
                    progress(chr, ref[0], plv, changed);
                }
            }
        }
        var qmx = chr.getQuestManager();
        for (Mission[] ref : questExMissions) {
            Mission m = ref[0];
            for (int qid : m.ids) {
                var q = qmx.getQuestById(qid);
                if (q == null) {
                    continue;
                }
                boolean ok = true;
                for (int i = 0; i < m.exKeys.size() && ok; i++) {
                    ok = m.exValues.get(i).equals(q.getProperty(m.exKeys.get(i)));
                }
                if (ok && m.exCompareKey != null) {
                    try {
                        ok = Long.parseLong(q.getProperty(m.exCompareKey)) >= m.exCompareValue;
                    } catch (Exception e) {
                        ok = false;
                    }
                }
                if (ok && (!m.exKeys.isEmpty() || m.exCompareKey != null)) {
                    progress(chr, m, 1, changed);
                }
            }
        }
        if (!equipMissions.isEmpty()) {
            Set<Integer> worn = new HashSet<>();
            for (var item : chr.getEquippedInventory().getItems()) {
                worn.add(item.getItemId());
            }
            for (Mission[] ref : equipMissions) {
                int n = 0;
                for (int id : ref[0].ids) {
                    if (worn.contains(id)) {
                        n++;
                    }
                }
                if (n > 0) {
                    progress(chr, ref[0], n, changed);
                }
            }
        }
        for (Mission[] ref : traitMissions) {
            Stat trait = switch (ref[0].result) {
                case "charisma" -> Stat.charismaEXP;
                case "insight" -> Stat.insightEXP;
                case "will" -> Stat.willEXP;
                case "craft" -> Stat.craftEXP;
                case "sense" -> Stat.senseEXP;
                case "charm" -> Stat.charmEXP;
                default -> null;
            };
            if (trait != null) {
                int tlv = GameConstants.getTraitLevel(chr.getStat(trait));
                if (tlv > 0) {
                    progress(chr, ref[0], tlv, changed);
                }
            }
        }
        if (chr.getAccount().getMonsterCollection() != null && !collectionMissions.isEmpty()) {
            int mobs = chr.getAccount().getMonsterCollection().getMobs().size();
            for (Mission[] ref : collectionMissions) {
                if (mobs > 0) {
                    progress(chr, ref[0], mobs, changed);
                }
            }
        }
        int fame = chr.getAvatarData().getCharacterStat().getPop();
        if (fame > 0) {
            for (Mission[] ref : popularityMissions) {
                progress(chr, ref[0], fame, changed);
            }
        }
        var union = chr.getAccount().getUnion();
        long power = union == null ? 0 : union.getUnionPower();
        if (power > 0) {
            for (Mission[] ref : unionPowerMissions) {
                progress(chr, ref[0], power, changed);
            }
        }
        Info open = infos.get(OPEN_ACHIEVEMENTS);
        if (open != null) {
            for (Mission m : open.missions) {
                if ("script".equals(m.type)) {
                    progress(chr, m, m.target, changed);
                }
            }
        }
        return changed;
    }

    /** ACHIEVEMENT_REQUEST 962. */
    public static void handleRequest(Char chr, int type, net.swordie.ms.connection.InPacket in) {
        if (skip(chr)) {
            chr.dispose();
            return;
        }
        ensureLoaded();
        switch (type) {
            case REQ_LOAD, REQ_UPDATE -> {
                refresh(chr);
                sendRecords(chr, new ArrayList<>(state(chr).keySet()));
                syncInsignias(chr);
                sendInsignias(chr, null);
                sendRank(chr);
                if (type == REQ_LOAD) {
                    sendCompletions(chr);
                } else {
                    OutPacket out = new OutPacket(OutHeader.ACHIEVEMENT_RESULT);
                    out.encodeInt(RES_UPDATED);
                    out.encodeInt(0);
                    chr.write(out);
                }
            }
            case REQ_OPEN -> sendRecords(chr, refresh(chr));
            case REQ_OPEN_RANK, REQ_RANK -> {
                syncInsignias(chr);
                sendInsignias(chr, null);
                sendRank(chr);
            }
            case REQ_CLAIM -> {
                int id = in.getUnreadAmount() >= 4 ? in.decodeInt() : 0;
                claim(chr, id);
            }
            case REQ_INSIGNIA -> {
                int id = in.getUnreadAmount() >= 4 ? in.decodeInt() : 0;
                selectInsignia(chr, id);
            }
            case REQ_VIEW_OTHER -> {
                int charId = in.getUnreadAmount() >= 4 ? in.decodeInt() : 0;
                Char other = chr.getField() == null ? null : chr.getField().getCharByID(charId);
                if (other != null) {
                    // the info window: its character data carries that account's records, insignias and rank
                    chr.write(net.swordie.ms.connection.packet.field.FieldPacket.characterInfo(other));
                }
            }
            default -> {
            }
        }
        chr.dispose();
    }

    // ------------------------------------------------------------------------------------ score, grade, insignias

    /** Score of the account's completed achievements (the client adds up the same). */
    public static int score(Char chr) {
        ensureLoaded();
        int total = 0;
        for (Map.Entry<Integer, Rec> e : state(chr).entrySet()) {
            int k = e.getKey();
            int id = k / 1000;
            if (id != INSIGNIA_ROW && (byte) (k % 1000) == WHOLE && e.getValue().status == DONE) {
                Info info = infos.get(id);
                total += info == null ? 0 : info.score;
            }
        }
        return total;
    }

    /** 1 bronze .. 5 diamond by score; master (6) for a rank inside the master range with the needed grade. */
    private static int grade(Char chr) {
        int score = score(chr);
        int g = 0;
        for (int i = 0; i < gradeMins.size(); i++) {
            if (score >= gradeMins.get(i)) {
                g = i + 1;
            }
        }
        int needs = gradeIndex(masterNeeds);
        if (needs > 0 && g >= needs) {
            int rank = rank(chr);
            if (rank >= 1 && rank <= masterRankMax) {
                g = gradeNames.size() + 1;
            }
        }
        return g;
    }

    /** The account's place by score among all accounts with a score; -1 none. Refreshed every few minutes. */
    public static int rank(Char chr) {
        refreshRanks();
        return rankByAccount.getOrDefault(chr.getAccount().getId(), -1);
    }

    private static void refreshRanks() {
        long now = System.currentTimeMillis();
        if (now - ranksAt < RANK_REFRESH_MS) {
            return;
        }
        synchronized (MapleAchievements.class) {
            if (now - ranksAt < RANK_REFRESH_MS) {
                return;
            }
            ranksAt = now;
            Map<Integer, Integer> scores = new HashMap<>();
            Map<Integer, Integer> counts = new HashMap<>();
            try {
                for (Map<String, Object> row : DatabaseManager.queryRows("SELECT accid AS a, infoid AS i FROM achievements "
                        + "WHERE missionid = -1 AND status = 2 AND infoid > 0")) {
                    int acc = ((Number) row.get("a")).intValue();
                    int id = ((Number) row.get("i")).intValue();
                    Info info = infos.get(id);
                    if (info != null) {
                        scores.merge(acc, info.score, Integer::sum);
                        counts.merge(id, 1, Integer::sum);
                    }
                }
            } catch (Exception e) {
                log.warn("Could not read the achievement ranking", e);
                return;
            }
            List<Map.Entry<Integer, Integer>> order = new ArrayList<>(scores.entrySet());
            order.removeIf(e -> e.getValue() <= 0);
            order.sort((a, b) -> b.getValue().equals(a.getValue()) ? Integer.compare(a.getKey(), b.getKey())
                    : Integer.compare(b.getValue(), a.getValue()));
            Map<Integer, Integer> ranks = new HashMap<>();
            for (int i = 0; i < order.size(); i++) {
                ranks.put(order.get(i).getKey(), i + 1);
            }
            rankByAccount = ranks;
            completions = counts;
        }
    }

    /**
     * Unlocks the insignias the account's grade reached (one is shown if none is), tells the client, and gives this
     * character Maple Crest and the crest skills of the owned insignias.
     */
    private static void syncInsignias(Char chr) {
        if (skip(chr)) {
            return;
        }
        Map<Integer, Rec> st = state(chr);
        int grade = grade(chr);
        List<Integer> changed = new ArrayList<>();
        synchronized (st) {
            boolean shown = false;
            int best = 0;
            for (Map.Entry<Integer, int[]> e : insignias.entrySet()) {
                Rec r = st.get(key(INSIGNIA_ROW, e.getKey()));
                if (r == null && grade >= e.getValue()[0]) {
                    r = new Rec();
                    r.status = OWNED;
                    r.time = FileTime.currentTime().toLong();
                    st.put(key(INSIGNIA_ROW, e.getKey()), r);
                    save(chr, INSIGNIA_ROW, e.getKey(), r);
                    changed.add(e.getKey());
                }
                if (r != null) {
                    shown |= r.status == SHOWN;
                    best = e.getKey();
                }
            }
            if (!shown && best > 0) {
                Rec r = st.get(key(INSIGNIA_ROW, best));
                r.status = SHOWN;
                save(chr, INSIGNIA_ROW, best, r);
                if (!changed.contains(best)) {
                    changed.add(best);
                }
            }
        }
        if (!changed.isEmpty()) {
            sendInsignias(chr, changed);
        }
        giveCrestSkills(chr);
    }

    private static void giveCrestSkills(Char chr) {
        if (chr.getClient() == null) {
            return;
        }
        Map<Integer, Rec> st = state(chr);
        boolean any = false;
        for (Map.Entry<Integer, int[]> e : insignias.entrySet()) {
            if (st.containsKey(key(INSIGNIA_ROW, e.getKey()))) {
                any = true;
                if (!chr.hasSkill(e.getValue()[1])) {
                    chr.addSkill(e.getValue()[1], 1, 1);
                }
            }
        }
        if (any && !chr.hasSkill(MAPLE_CREST)) {
            chr.addSkill(MAPLE_CREST, 1, 1);
        }
    }

    /** Request 3: show another owned insignia (the client asks only for an owned, not shown one). */
    private static void selectInsignia(Char chr, int id) {
        Map<Integer, Rec> st = state(chr);
        List<Integer> changed = new ArrayList<>();
        synchronized (st) {
            Rec pick = insignias.containsKey(id) ? st.get(key(INSIGNIA_ROW, id)) : null;
            if (pick == null || pick.status == SHOWN) {
                return;
            }
            for (int other : insignias.keySet()) {
                Rec r = st.get(key(INSIGNIA_ROW, other));
                if (r != null && r.status == SHOWN) {
                    r.status = OWNED;
                    save(chr, INSIGNIA_ROW, other, r);
                    changed.add(other);
                }
            }
            pick.status = SHOWN;
            save(chr, INSIGNIA_ROW, id, pick);
            changed.add(id);
        }
        sendInsignias(chr, changed);
    }

    /** The owned insignias (null = all of them), as (id, state, FT obtained). */
    private static List<Object[]> insigniaList(Char chr, Collection<Integer> ids) {
        Map<Integer, Rec> st = state(chr);
        List<Object[]> out = new ArrayList<>();
        for (int id : insignias.keySet()) {
            Rec r = st.get(key(INSIGNIA_ROW, id));
            if (r != null && (ids == null || ids.contains(id))) {
                out.add(new Object[]{id, r.status, r.time});
            }
        }
        return out;
    }

    private static void encodeInsignia(OutPacket out, Object[] ins) {
        out.encodeInt((Integer) ins[0]);
        out.encodeByte((Byte) ins[1]);
        long time = (Long) ins[2];
        out.encodeFT(time > 0 ? FileTime.fromLong(time) : FileTime.currentTime());
    }

    private static void sendInsignias(Char chr, Collection<Integer> ids) {
        List<Object[]> list = insigniaList(chr, ids);
        if (list.isEmpty()) {
            return;
        }
        OutPacket out = new OutPacket(OutHeader.MESSAGE);
        out.encodeByte(MSG_INSIGNIA);
        out.encodeInt(list.size());
        for (Object[] ins : list) {
            out.encodeInt((Integer) ins[0]);
            encodeInsignia(out, ins);
        }
        chr.write(out);
    }

    private static void sendRank(Char chr) {
        OutPacket out = new OutPacket(OutHeader.MESSAGE);
        out.encodeByte(MSG_RANK);
        out.encodeInt(0); // not read by the v265 client
        out.encodeInt(rank(chr));
        chr.write(out);
    }

    /** ACHIEVEMENT_RESULT 6: how many accounts completed each achievement (the window's global completion rate). */
    private static void sendCompletions(Char chr) {
        refreshRanks();
        Map<Integer, Integer> counts = completions;
        OutPacket out = new OutPacket(OutHeader.ACHIEVEMENT_RESULT);
        out.encodeInt(RES_COMPLETIONS);
        int day;
        try {
            day = Integer.parseInt(net.swordie.ms.util.ResetClock.today("yyyyMMdd"));
        } catch (NumberFormatException e) {
            day = 0;
        }
        out.encodeInt(day);
        out.encodeInt(counts.size());
        counts.forEach((id, n) -> {
            out.encodeInt(id);
            out.encodeInt(n);
        });
        chr.write(out);
    }

    /**
     * The achievement block of the character data (login, another character's info): account, character, the
     * MESSAGE 47 pair, the records, the insignias (no leading id here).
     */
    public static void encodeCharData(Char chr, OutPacket out) {
        int accId = chr.getAccount() == null ? 0 : chr.getAccount().getId();
        List<Integer> keys = new ArrayList<>();
        Map<Integer, Rec> st = Map.of();
        List<Object[]> ins = List.of();
        int rank = -1;
        if (!skip(chr)) {
            try {
                ensureLoaded();
                st = state(chr);
                for (int k : st.keySet()) {
                    if ((byte) (k % 1000) != CLAIMED && k / 1000 != INSIGNIA_ROW) {
                        keys.add(k);
                    }
                }
                ins = insigniaList(chr, null);
                rank = rank(chr);
            } catch (Exception e) {
                log.warn("Maple Achievements: character data block for " + chr.getId() + " left empty", e);
                keys.clear();
                ins = List.of();
                rank = -1;
            }
        }
        out.encodeInt(accId);
        out.encodeInt(chr.getId());
        out.encodeInt(0);
        out.encodeInt(rank);
        out.encodeInt(keys.size());
        for (int k : keys) {
            int id = k / 1000;
            int mission = (byte) (k % 1000);
            Rec r = st.get(k);
            encodeRecord(out, id, mission, r, msgOf(id, mission, r));
        }
        out.encodeInt(ins.size());
        for (Object[] i : ins) {
            encodeInsignia(out, i);
        }
    }

    private static void claim(Char chr, int id) {
        Info info = infos.get(id);
        Map<Integer, Rec> st = state(chr);
        if (info == null || info.rewards.isEmpty() || !isComplete(chr, id) || st.containsKey(key(id, CLAIMED))) {
            return;
        }
        for (int item : info.rewards) {
            if (!chr.canHold(item, 1)) {
                chr.chatMessage("Please make room in your inventory first.");
                return;
            }
        }
        Rec r = new Rec();
        r.status = DONE;
        r.time = FileTime.currentTime().toLong();
        st.put(key(id, CLAIMED), r);
        save(chr, id, CLAIMED, r);
        for (int item : info.rewards) {
            chr.getScriptManager().giveItem(item, 1);
        }
    }
}
