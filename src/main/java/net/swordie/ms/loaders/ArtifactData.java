package net.swordie.ms.loaders;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.jwzlib.WzFile;
import net.swordie.ms.jwzlib.WzObject;
import net.swordie.ms.jwzlib.WzProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Union (Legion) Artifact data from Etc.wz/Artifact.img: costs, the crystals' required artifact levels, the slot
 * stages' AP costs, the stat skills, the common missions, the boss hunt missions (huntMission: a boss's clear
 * monster and its rank's reward, maxCount a week) and the first-clear special missions (specialMission of type
 * qrexIntValue on the boss clear record 39392). Read on first use.
 */
public class ArtifactData {

    private static final Logger log = LogManager.getLogger(ArtifactData.class);

    /** Per artifact level (index = level - 1): {max points held, EXP to the next level, artifact AP} */
    public static final int[][] LEVELS = {
            {10100, 2500, 1}, {10200, 2550, 2}, {10300, 2600, 3}, {10400, 2650, 4}, {10500, 2700, 6},
            {10600, 2750, 7}, {10700, 2800, 8}, {10800, 2850, 9}, {10900, 2900, 10}, {11000, 2950, 12},
            {11100, 3000, 13}, {11200, 3050, 14}, {11300, 3100, 15}, {11400, 3150, 16}, {11500, 3200, 18},
            {11600, 3250, 19}, {11700, 3300, 20}, {11800, 3350, 21}, {11900, 3400, 22}, {12000, 3450, 24},
            {12100, 3500, 25}, {12200, 3550, 26}, {12300, 3600, 27}, {12400, 3700, 28}, {12500, 3800, 30},
            {12600, 3900, 31}, {12700, 4000, 32}, {12800, 4500, 33}, {12900, 5000, 34}, {13000, 5500, 36},
            {13200, 6000, 37}, {13400, 6500, 38}, {13600, 7000, 39}, {13800, 7500, 40}, {14000, 8000, 42},
            {14200, 8500, 43}, {14400, 9000, 44}, {14600, 9500, 45}, {14800, 10000, 46}, {15000, 12000, 48},
            {15200, 14000, 49}, {15400, 16000, 50}, {15600, 18000, 51}, {15800, 20000, 52}, {16000, 22000, 54},
            {16200, 24000, 55}, {16400, 26000, 56}, {16600, 28000, 57}, {16800, 30000, 58}, {17000, 50000, 60},
            {17300, 55000, 61}, {17600, 60000, 62}, {17900, 65000, 63}, {18200, 70000, 64}, {18500, 100000, 66},
            {18800, 110000, 67}, {19100, 120000, 68}, {19400, 130000, 69}, {19700, 200000, 70}, {20000, 0, 72},
    };

    public static class Mission {
        public String name = "";
        public String type = "";
        public int point;
        public int exp;
        public int value;
    }

    private static int extendPeriodHours = 720;
    private static int extendPoint = 1000;
    private static int changePoint = 500;
    private static int resetPoint = 500;
    private static int maxSlotLevel = 5;
    private static final List<Integer> crystalReqLevels = new ArrayList<>();
    private static final Map<Integer, Integer> slotEnforcePoints = new HashMap<>(); // slot stage -> AP
    private static final List<Integer> statSkills = new ArrayList<>();
    private static final List<Mission> commonMissions = new ArrayList<>();
    /** huntMission: clear monster -> {mission index, artifact EXP, artifact points} (by its rank's reward) */
    private static final Map<Integer, int[]> huntMissions = new HashMap<>();
    private static int huntMaxCount = 3;
    /** specialMission on the boss clear record: clear monster -> {mission index, artifact EXP, artifact points} */
    private static final Map<Integer, int[]> firstClearMissions = new HashMap<>();
    public static final int BOSS_CLEAR_QUEST = 39392;
    private static boolean loaded;

    private static synchronized void ensureLoaded() {
        if (loaded) {
            return;
        }
        loaded = true;
        try {
            WzObject<?, ?> root = new WzFile(ServerConstants.WZ_DIR + "/Etc.wz").getChild("Artifact.img");
            if (root == null) {
                log.warn("Etc.wz has no Artifact.img: Union Artifacts are off");
                return;
            }
            WzObject<?, ?> info = root.getChildByPath("artifact/info");
            if (info != null) {
                extendPeriodHours = intOf(info.getChild("extendPeriod"), extendPeriodHours);
                extendPoint = intOf(info.getChild("extendPoint"), extendPoint);
                changePoint = intOf(info.getChild("changePoint"), changePoint);
                resetPoint = intOf(info.getChild("resetPoint"), resetPoint);
                maxSlotLevel = intOf(info.getChild("maxSlotLevel"), maxSlotLevel);
            }
            WzObject<?, ?> artifacts = root.getChildByPath("artifact/artifacts");
            if (artifacts != null) {
                for (int i = 0; artifacts.getChild(String.valueOf(i)) != null; i++) {
                    crystalReqLevels.add(intOf(artifacts.getChild(String.valueOf(i)).getChild("reqLv"), 0));
                }
            }
            WzObject<?, ?> slot = root.getChildByPath("artifact/slot");
            if (slot != null) {
                for (WzObject<?, ?> stage : slot) {
                    slotEnforcePoints.put(Integer.parseInt(stage.getName()), intOf(stage.getChild("enforceSlotPoint"), 0));
                }
            }
            WzObject<?, ?> stat = root.getChildByPath("artifact/stat");
            if (stat != null) {
                for (int i = 0; stat.getChild(String.valueOf(i)) != null; i++) {
                    statSkills.add(intOf(stat.getChild(String.valueOf(i)).getChild("skillCode"), 0));
                }
            }
            WzObject<?, ?> common = root.getChild("commonMission");
            if (common != null) {
                for (int i = 0; common.getChild(String.valueOf(i)) != null; i++) {
                    WzObject<?, ?> node = common.getChild(String.valueOf(i));
                    Mission m = new Mission();
                    m.name = stringOf(node.getChild("name"));
                    m.type = stringOf(node.getChild("type"));
                    m.point = intOf(node.getChild("artifactPoint"), 0);
                    m.exp = intOf(node.getChild("artifactExp"), 0);
                    m.value = intOf(node.getChildByPath("check/value"), 0);
                    commonMissions.add(m);
                }
            }
            WzObject<?, ?> hunt = root.getChild("huntMission");
            if (hunt != null) {
                huntMaxCount = intOf(hunt.getChild("maxCount"), huntMaxCount);
                WzObject<?, ?> rewards = hunt.getChild("rankReward");
                WzObject<?, ?> list = hunt.getChild("mission");
                if (list != null) {
                    for (WzObject<?, ?> node : list) {
                        int mob = intOf(node.getChild("mobID"), 0);
                        String rank = String.valueOf(intOf(node.getChild("rank"), 0));
                        WzObject<?, ?> reward = rewards == null ? null : rewards.getChild(rank);
                        if (mob > 0 && reward != null) {
                            huntMissions.put(mob, new int[]{Integer.parseInt(node.getName()),
                                    intOf(reward.getChild("artifactExp"), 0), intOf(reward.getChild("artifactPoint"), 0)});
                        }
                    }
                }
            }
            WzObject<?, ?> special = root.getChild("specialMission");
            if (special != null) {
                for (WzObject<?, ?> node : special) {
                    if (!"qrexIntValue".equals(stringOf(node.getChild("type")))
                            || intOf(node.getChildByPath("check/quest"), 0) != BOSS_CLEAR_QUEST) {
                        continue;
                    }
                    String key = stringOf(node.getChildByPath("check/key"));
                    if (key.matches("[0-9]+") && node.getName().matches("[0-9]+")) {
                        firstClearMissions.put(Integer.parseInt(key), new int[]{Integer.parseInt(node.getName()),
                                intOf(node.getChild("artifactExp"), 0), intOf(node.getChild("artifactPoint"), 0)});
                    }
                }
            }
            log.info(String.format("Loaded %d artifact crystals, %d stats, %d common, %d boss hunt and %d first-clear "
                            + "missions", crystalReqLevels.size(), statSkills.size(), commonMissions.size(),
                    huntMissions.size(), firstClearMissions.size()));
        } catch (Exception e) {
            log.error("Could not read Artifact.img", e);
        }
    }

    private static int intOf(WzObject<?, ?> node, int def) {
        return node instanceof WzProperty<?> prop ? prop.getIntValue() : def;
    }

    private static String stringOf(WzObject<?, ?> node) {
        return node instanceof WzProperty<?> prop && prop.getValue() != null ? prop.getValue().toString() : "";
    }

    public static int getExtendPeriodHours() {
        ensureLoaded();
        return extendPeriodHours;
    }

    public static int getExtendPoint() {
        ensureLoaded();
        return extendPoint;
    }

    public static int getChangePoint() {
        ensureLoaded();
        return changePoint;
    }

    public static int getResetPoint() {
        ensureLoaded();
        return resetPoint;
    }

    public static int getMaxSlotLevel() {
        ensureLoaded();
        return maxSlotLevel;
    }

    /** @return the artifact level each crystal needs, by crystal index */
    public static List<Integer> getCrystalReqLevels() {
        ensureLoaded();
        return crystalReqLevels;
    }

    /** @return the AP it takes to bring a crystal to the given level (2-5) */
    public static int getEnforcePoint(int level) {
        ensureLoaded();
        return slotEnforcePoints.getOrDefault(level - 1, 0);
    }

    public static List<Integer> getStatSkills() {
        ensureLoaded();
        return statSkills;
    }

    /** @return {mission index, artifact EXP, artifact points} of the boss hunt mission for this clear monster, or null */
    public static int[] getHuntMission(int mobId) {
        ensureLoaded();
        return huntMissions.get(mobId);
    }

    public static int getHuntMaxCount() {
        ensureLoaded();
        return huntMaxCount;
    }

    /** @return {mission index, artifact EXP, artifact points} of the first-clear mission for this monster, or null */
    public static int[] getFirstClearMission(int mobId) {
        ensureLoaded();
        return firstClearMissions.get(mobId);
    }

    public static List<Mission> getCommonMissions() {
        ensureLoaded();
        return commonMissions;
    }
}
