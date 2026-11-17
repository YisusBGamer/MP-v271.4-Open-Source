package net.swordie.ms.loaders;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.skills.SkillStat;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.constants.HexaConstants;
import net.swordie.ms.jwzlib.WzFile;
import net.swordie.ms.jwzlib.WzObject;
import net.swordie.ms.jwzlib.WzProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * HEXA Matrix data from Etc.wz/HexaCore.img: the skill cores (id prefix 1 skill, 2 mastery, 3 boost, 4 common) and the
 * skills each one levels, which cores each 4th job has, and the HEXA Stat nodes. Read on first use (it's small).
 */
public class HexaCoreData {

    private static final Logger log = LogManager.getLogger(HexaCoreData.class);

    public static final int SKILL_CORE = 1;
    public static final int MASTERY_CORE = 2;
    public static final int BOOST_CORE = 3;
    public static final int COMMON_CORE = 4;

    public static class SkillCore {
        public final int id;
        public String name = "";
        public int type;
        public int subType;
        public int maxLevel;
        public final List<Integer> connectSkills = new ArrayList<>();

        SkillCore(int id) {
            this.id = id;
        }

        /** @return 1 skill, 2 mastery, 3 boost, 4 common */
        public int getCategory() {
            return id / 10_000_000;
        }
    }

    public static class StatCore {
        public final int id;
        public String name = "";
        public int reqLevel;
        public int maxLevel;
        public int reqCore;

        StatCore(int id) {
            this.id = id;
        }
    }

    private static final Map<Integer, SkillCore> skillCores = new HashMap<>();
    // job -> category (1-4) -> core ids
    private static final Map<Integer, Map<Integer, List<Integer>>> jobCores = new HashMap<>();
    private static final Map<Integer, StatCore> statCores = new LinkedHashMap<>();
    // HEXA Stat values: stat type (0-6) -> value by level (0-10), for the main stat and for the additional ones
    private static final Map<Integer, int[]> mainStatValues = new HashMap<>();
    private static final Map<Integer, int[]> additionalStatValues = new HashMap<>();
    // boost core skill -> the 5th job skills it raises, and its damR_6th by level
    private static final Map<Integer, Set<Integer>> boostTargets = new HashMap<>();
    private static final Map<Integer, SkillInfo> boostValues = new HashMap<>();
    private static final String DEFAULT_BOOST_DAM_R = "10+x+5*log10(x)+5*log20(x)+10*log30(x)";
    private static int solErdaGaugeMax = 1000;
    private static int solErdaSlotMax = 20;
    private static final Map<Integer, Integer> energyItems = new HashMap<>(); // item -> Sol Erda energy
    private static boolean loaded;

    private static synchronized void ensureLoaded() {
        if (loaded) {
            return;
        }
        loaded = true;
        try {
            WzFile file = new WzFile(ServerConstants.WZ_DIR + "/Etc.wz");
            WzObject<?, ?> root = file.getChild("HexaCore.img");
            if (root == null) {
                log.warn("Etc.wz has no HexaCore.img: HEXA Matrix is off");
                return;
            }
            WzObject<?, ?> solErda = root.getChild("solErda");
            if (solErda != null) {
                solErdaGaugeMax = intOf(solErda.getChild("gaugeMax"), solErdaGaugeMax);
                solErdaSlotMax = intOf(solErda.getChild("slotMax"), solErdaSlotMax);
                // solErda/<n>: solErdaExp per item of id/*
                for (WzObject<?, ?> group : solErda) {
                    int exp = intOf(group.getChild("solErdaExp"), 0);
                    WzObject<?, ?> ids = group.getChild("id");
                    if (exp > 0 && ids != null) {
                        for (WzObject<?, ?> id : ids) {
                            energyItems.put(intOf(id, 0), exp);
                        }
                    }
                }
            }
            WzObject<?, ?> coreData = root.getChildByPath("hexaSkill/coreData");
            if (coreData != null) {
                for (WzObject<?, ?> node : coreData) {
                    SkillCore core = new SkillCore(Integer.parseInt(node.getName()));
                    core.name = stringOf(node.getChild("name"));
                    core.type = intOf(node.getChild("type"), 0);
                    core.subType = intOf(node.getChild("subType"), 0);
                    core.maxLevel = intOf(node.getChild("maxLevel"), 30);
                    WzObject<?, ?> connect = node.getChild("connectSkill");
                    if (connect != null) {
                        for (WzObject<?, ?> skill : connect) {
                            core.connectSkills.add(intOf(skill, 0));
                        }
                    }
                    skillCores.put(core.id, core);
                }
            }
            WzObject<?, ?> jobCoreNode = root.getChildByPath("hexaSkill/jobCore");
            if (jobCoreNode != null) {
                for (WzObject<?, ?> jobNode : jobCoreNode) {
                    Map<Integer, List<Integer>> byCategory = new HashMap<>();
                    addCores(byCategory, SKILL_CORE, jobNode.getChild("skill"));
                    addCores(byCategory, MASTERY_CORE, jobNode.getChild("mastery"));
                    addCores(byCategory, BOOST_CORE, jobNode.getChild("enforce"));
                    addCores(byCategory, COMMON_CORE, jobNode.getChild("common"));
                    jobCores.put(Integer.parseInt(jobNode.getName()), byCategory);
                }
            }
            WzObject<?, ?> statData = root.getChildByPath("hexaStat/coreData");
            if (statData != null) {
                for (WzObject<?, ?> node : statData) {
                    StatCore core = new StatCore(Integer.parseInt(node.getName()));
                    core.name = stringOf(node.getChild("name"));
                    core.reqLevel = intOf(node.getChild("reqLevel"), 260);
                    core.maxLevel = intOf(node.getChild("maxLevel"), 20);
                    core.reqCore = intOf(node.getChild("reqCore"), 0);
                    statCores.put(core.id, core);
                }
            }
            readStatValues(root.getChildByPath("hexaStat/stat/main/type"), mainStatValues);
            readStatValues(root.getChildByPath("hexaStat/stat/additional/type"), additionalStatValues);
            readBoostSkills();
            log.info(String.format("Loaded %d HEXA cores for %d jobs and %d HEXA Stat nodes", skillCores.size(),
                    jobCores.size(), statCores.size()));
        } catch (Exception e) {
            log.error("Could not read HexaCore.img", e);
        }
    }

    /** type/{stat type}/level/{level}/{a single value: cdPerM, bdRPerM, ignoreMobpdpRPerM, damRPerM, padX, ...} */
    private static void readStatValues(WzObject<?, ?> types, Map<Integer, int[]> into) {
        if (types == null) {
            return;
        }
        for (WzObject<?, ?> type : types) {
            WzObject<?, ?> levels = type.getChild("level");
            if (levels == null) {
                continue;
            }
            int[] values = new int[HexaConstants.STAT_MAX_LEVEL + 1];
            for (WzObject<?, ?> level : levels) {
                int lv = Integer.parseInt(level.getName());
                if (lv < 0 || lv >= values.length) {
                    continue;
                }
                for (WzObject<?, ?> value : level) {
                    values[lv] = intOf(value, 0);
                }
            }
            into.put(Integer.parseInt(type.getName()), values);
        }
    }

    /**
     * The boost cores' skills (5000040xx) are passives that raise 5th job skills by damR_6th %. That stat isn't in the
     * skill data the server reads, so it comes from Skill.wz here (the client applies it on its own).
     */
    private static void readBoostSkills() {
        Set<Integer> skillIds = new HashSet<>();
        for (SkillCore core : skillCores.values()) {
            if (core.getCategory() == BOOST_CORE) {
                skillIds.addAll(core.connectSkills);
            }
        }
        Map<Integer, WzObject<?, ?>> images = new HashMap<>();
        WzFile skillWz = null;
        try {
            skillWz = new WzFile(ServerConstants.WZ_DIR + "/Skill.wz");
        } catch (Exception e) {
            log.warn("Could not open Skill.wz for the HEXA boost skills; using the skill data's links", e);
        }
        for (int skillId : skillIds) {
            String formula = null;
            Set<Integer> targets = new HashSet<>();
            if (skillWz != null) {
                WzFile wz = skillWz;
                WzObject<?, ?> img = images.computeIfAbsent(skillId / 10000, k -> wz.getChild(k + ".img"));
                WzObject<?, ?> node = img == null ? null : img.getChildByPath("skill/" + skillId);
                if (node != null) {
                    formula = stringOf(node.getChildByPath("common/damR_6th"));
                    WzObject<?, ?> psd = node.getChild("psdSkill");
                    if (psd != null) {
                        for (WzObject<?, ?> target : psd) {
                            targets.add(Integer.parseInt(target.getName()));
                        }
                    }
                }
            }
            if (targets.isEmpty()) {
                SkillInfo si = SkillData.getSkillInfoById(skillId);
                if (si != null) {
                    targets.addAll(si.getPsdSkills());
                }
            }
            SkillInfo values = new SkillInfo();
            values.setSkillId(skillId);
            values.addSkillStatInfo(SkillStat.damR_5th, formula == null || formula.isEmpty() ? DEFAULT_BOOST_DAM_R
                    : formula);
            boostValues.put(skillId, values);
            boostTargets.put(skillId, targets);
        }
    }

    private static void addCores(Map<Integer, List<Integer>> byCategory, int category, WzObject<?, ?> node) {
        List<Integer> ids = new ArrayList<>();
        if (node != null) {
            for (WzObject<?, ?> core : node) {
                ids.add(intOf(core, 0));
            }
        }
        byCategory.put(category, ids);
    }

    private static int intOf(WzObject<?, ?> node, int def) {
        return node instanceof WzProperty<?> prop ? prop.getIntValue() : def;
    }

    private static String stringOf(WzObject<?, ?> node) {
        return node instanceof WzProperty<?> prop && prop.getValue() != null ? prop.getValue().toString() : "";
    }

    public static SkillCore getSkillCore(int coreId) {
        ensureLoaded();
        return skillCores.get(coreId);
    }

    public static StatCore getStatCore(int coreId) {
        ensureLoaded();
        return statCores.get(coreId);
    }

    public static Collection<StatCore> getStatCores() {
        ensureLoaded();
        return statCores.values();
    }

    /** @return the core ids of the job (its 4th job id), all categories */
    public static List<Integer> getJobCores(int job) {
        ensureLoaded();
        List<Integer> all = new ArrayList<>();
        Map<Integer, List<Integer>> byCategory = jobCores.get(job);
        if (byCategory != null) {
            byCategory.values().forEach(all::addAll);
        }
        return all;
    }

    public static boolean isJobCore(int job, int coreId) {
        ensureLoaded();
        Map<Integer, List<Integer>> byCategory = jobCores.get(job);
        return byCategory != null && byCategory.getOrDefault(coreId / 10_000_000, List.of()).contains(coreId);
    }

    public static boolean hasJobCores(int job) {
        ensureLoaded();
        return jobCores.containsKey(job);
    }

    /**
     * @param main whether it's a node's main stat (the additional ones give less)
     * @return the value of a HEXA Stat type at a level, in the data's units (PerM stats are 1/100 %), 0 if none
     */
    public static int getStatValue(int type, int level, boolean main) {
        ensureLoaded();
        int[] values = (main ? mainStatValues : additionalStatValues).get(type);
        return values == null || level <= 0 ? 0 : values[Math.min(level, values.length - 1)];
    }

    /** @return the 5th job skills a boost core skill raises */
    public static Set<Integer> getBoostTargets(int boostSkillId) {
        ensureLoaded();
        return boostTargets.getOrDefault(boostSkillId, Set.of());
    }

    /** @return the damage % a boost core skill adds to its skills at a level */
    public static double getBoostDamR(int boostSkillId, int level) {
        ensureLoaded();
        SkillInfo values = boostValues.get(boostSkillId);
        return values == null ? 0 : values.getValueD(SkillStat.damR_5th, level);
    }

    public static int getSolErdaGaugeMax() {
        ensureLoaded();
        return solErdaGaugeMax;
    }

    /** Sol Erda energy one item gives (Etc/HexaCore.img/solErda), 0 if none */
    public static int getEnergyItemValue(int itemId) {
        ensureLoaded();
        return energyItems.getOrDefault(itemId, 0);
    }

    public static int getSolErdaSlotMax() {
        ensureLoaded();
        return solErdaSlotMax;
    }
}
