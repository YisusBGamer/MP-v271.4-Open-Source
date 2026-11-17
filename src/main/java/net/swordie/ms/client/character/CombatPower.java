package net.swordie.ms.client.character;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.items.BodyPart;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.jwzlib.WzFile;
import net.swordie.ms.jwzlib.WzObject;
import net.swordie.ms.jwzlib.WzProperty;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.containerclasses.EquipItemInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Combat Power as the v265 client works it out for the stat window ("stat Detail attack power", 0x1411b21e0):
 * ATT x stat x (1 + damage + boss damage) x final damage x (1.35 + crit damage) x job value x weapon type value.
 * ATT has the weapon's base attack swapped for a standard one by weapon level (Etc/StatCalibration.img/weapon) so
 * weapon classes compare evenly; the job and weapon type values come from the same file.
 */
public class CombatPower {
    private static final Logger log = LogManager.getLogger(CombatPower.class);

    private static final long REFRESH_MS = 15_000;

    private record Calibration(String expr, double min, double max, Set<Integer> jobs, Set<Integer> weaponTypes) {
    }

    private static boolean loaded;
    private static final TreeMap<Integer, Integer> levelPad = new TreeMap<>(); // weapon req level / 10 -> base ATT
    private static final Map<Integer, Integer> itemPad = new HashMap<>(); // weapon id -> base ATT (named sets, overrides)
    private static final List<Calibration> jobCalibrations = new ArrayList<>();
    private static final List<Calibration> weaponCalibrations = new ArrayList<>();

    /**
     * The character's Combat Power, worked out again at most every few seconds while it's online.
     */
    public static long get(Char chr) {
        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        long now = System.currentTimeMillis();
        boolean online = chr.getClient() != null;
        if (cs.getCombatPowerAt() > 0 && (!online || now - cs.getCombatPowerAt() < REFRESH_MS)) {
            return cs.getCombatPower();
        }
        long cp = 0;
        try {
            if (!online && chr.getBaseStats().isEmpty()) {
                chr.initBaseStats(); // passives and gear of a character that isn't logged in
            }
            cp = compute(chr);
        } catch (Exception e) {
            log.debug("Could not work out the Combat Power of " + chr.getName(), e);
        }
        cs.setCombatPower(cp, now);
        return cp;
    }

    public static long compute(Char chr) {
        ensureLoaded();
        Item weapon = chr.getEquippedItemByBodyPart(BodyPart.Weapon);
        if (weapon == null) {
            return 0;
        }
        int job = chr.getJob();
        boolean magic = isMagicJob(job);
        BaseStat att = magic ? BaseStat.mad : BaseStat.pad;
        BaseStat attR = magic ? BaseStat.madR : BaseStat.padR;
        double attack = chr.getTotalStat(att) + weaponVal(weapon, magic) * (1 + chr.getTotalStat(attR) / 100D);
        attack = Math.floor(attack);

        double cp = attack * statValue(chr)
                * (1 + (chr.getTotalStat(BaseStat.damR) + chr.getTotalStat(BaseStat.bd)) / 100D)
                * (1 + chr.getTotalStat(BaseStat.fd) / 100D)
                * (1.35 + chr.getTotalStat(BaseStat.crDmg) / 100D);
        cp *= calibrate(jobCalibrations, job, -1, cp);
        cp *= calibrate(weaponCalibrations, job, (weapon.getItemId() / 10000) % 100, cp);
        return Math.max(0, (long) cp);
    }

    /** The stat part: (4 main + secondary) / 100, with the client's own rules for Demon Avenger, Xenon and some thieves. */
    private static double statValue(Char chr) {
        int job = chr.getJob();
        int str = chr.getTotalStat(BaseStat.str);
        int dex = chr.getTotalStat(BaseStat.dex);
        int luk = chr.getTotalStat(BaseStat.luk);
        if (job == 3101 || (job >= 3120 && job <= 3122)) {
            int pureHp = chr.getAvatarData().getCharacterStat().getMaxHp();
            double hp = (chr.getMaxHP() - pureHp) * 0.8 + pureHp;
            return (hp / 3.5 + str) * 0.01;
        }
        if ((job >= 3600 && job < 3700) || job == 3002) {
            return (str + dex + luk) * 4 * 0.875 * 0.01;
        }
        if ((job >= 430 && job < 440) || (job >= 420 && job <= 422) || (job >= 6400 && job < 6500) || job == 6002) {
            return (4 * luk + str + dex) * 0.01;
        }
        BaseStat main = JobConstants.getMainStatByJob(job);
        if (main == null || main == BaseStat.mhp) {
            main = BaseStat.str;
        }
        BaseStat sub;
        if (JobConstants.isMageEquipJob(job)) {
            sub = BaseStat.luk;
        } else if (main == BaseStat.str || main == BaseStat.luk) {
            sub = BaseStat.dex;
        } else {
            sub = BaseStat.str;
        }
        return (4 * chr.getTotalStat(main) + chr.getTotalStat(sub)) * 0.01;
    }

    /** The client's magic-attack jobs for Combat Power. */
    private static boolean isMagicJob(int job) {
        int branch = job / 100;
        if (branch == 36 || branch == 37) {
            return false;
        }
        return branch == 27 || branch == 140 || branch == 142 || job % 1000 / 100 == 2;
    }

    /** Standard base ATT for the weapon minus its own base ATT (scrolls, flames and stars stay). */
    private static int weaponVal(Item weapon, boolean magic) {
        EquipItemInfo info = ItemData.getEquipInfoById(weapon.getItemId());
        if (info == null || !(weapon instanceof Equip)) {
            return 0;
        }
        int own = magic ? info.getiMad() : info.getiPad();
        Integer std = itemPad.get(weapon.getItemId());
        if (std == null) {
            if (levelPad.isEmpty()) {
                return 0;
            }
            int lv = Math.min(info.getrLevel() / 10, levelPad.lastKey());
            std = levelPad.getOrDefault(lv, 0);
        }
        return std - own;
    }

    private static double calibrate(List<Calibration> list, int job, int weaponType, double x) {
        for (Calibration c : list) {
            if (!c.jobs().contains(job)) {
                continue;
            }
            if (weaponType >= 0 && !c.weaponTypes().contains(weaponType)) {
                continue;
            }
            double v = eval(c.expr(), x);
            return Math.min(c.max(), Math.max(c.min(), v));
        }
        return 1;
    }

    // "a*log(x/b)+c" (log base 10, a leading m is a minus) or a plain number
    private static final Pattern LOG_EXPR = Pattern.compile("(m?[\\d.]+)\\*log\\(x/([\\d.]+)\\)([+m-][\\d.]+)?");

    private static double eval(String expr, double x) {
        String e = expr.replace(" ", "");
        try {
            Matcher m = LOG_EXPR.matcher(e);
            if (m.matches()) {
                double a = num(m.group(1));
                double b = num(m.group(2));
                double c = m.group(3) == null ? 0 : num(m.group(3).replace("+", ""));
                return x <= 0 ? c : a * Math.log10(x / b) + c;
            }
            return num(e);
        } catch (Exception ex) {
            return 1;
        }
    }

    private static double num(String s) {
        return s.startsWith("m") || s.startsWith("-") ? -Double.parseDouble(s.substring(1)) : Double.parseDouble(s);
    }

    private static synchronized void ensureLoaded() {
        if (loaded) {
            return;
        }
        loaded = true;
        try {
            WzObject<?, ?> root = new WzFile(ServerConstants.WZ_DIR + "/Etc.wz").getChild("StatCalibration.img");
            if (root == null) {
                log.warn("Etc.wz has no StatCalibration.img: Combat Power uses the weapons' own ATT");
                return;
            }
            WzObject<?, ?> levels = root.getChildByPath("weapon/level");
            if (levels != null) {
                for (WzObject<?, ?> lv : levels) {
                    levelPad.put(Integer.parseInt(lv.getName()), intOf(lv.getChild("Pad")));
                }
            }
            WzObject<?, ?> special = root.getChildByPath("weapon/special");
            if (special != null) {
                for (WzObject<?, ?> set : special) {
                    int pad = intOf(set.getChild("Pad"));
                    WzObject<?, ?> items = set.getChild("itemList");
                    if (items != null) {
                        for (WzObject<?, ?> it : items) {
                            itemPad.put(intOf(it), pad);
                        }
                    }
                }
                for (WzObject<?, ?> set : special) {
                    if (set.getName().chars().allMatch(Character::isDigit)) {
                        itemPad.put(Integer.parseInt(set.getName()), intOf(set.getChild("Pad")));
                    }
                }
            }
            WzObject<?, ?> calibration = root.getChild("calibration");
            if (calibration != null) {
                for (WzObject<?, ?> c : calibration) {
                    jobCalibrations.add(calibrationOf(c, ints(c.getChild("jobList")), Set.of()));
                }
            }
            WzObject<?, ?> weaponType = root.getChild("weaponType");
            if (weaponType != null) {
                for (WzObject<?, ?> group : weaponType) {
                    Set<Integer> jobs = ints(group.getChild("jobList"));
                    WzObject<?, ?> types = group.getChild("weaponType");
                    if (types != null) {
                        for (WzObject<?, ?> t : types) {
                            weaponCalibrations.add(calibrationOf(t, jobs, ints(t.getChild("weaponList"))));
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Could not read StatCalibration.img", e);
        }
    }

    private static Calibration calibrationOf(WzObject<?, ?> node, Set<Integer> jobs, Set<Integer> weaponTypes) {
        String expr = node.getChild("calibration") instanceof WzProperty<?> p && p.getValue() != null
                ? p.getValue().toString() : "1";
        return new Calibration(expr, doubleOf(node.getChild("calibration_min"), 0),
                doubleOf(node.getChild("calibration_max"), Double.MAX_VALUE), jobs, weaponTypes);
    }

    private static Set<Integer> ints(WzObject<?, ?> list) {
        Set<Integer> res = new HashSet<>();
        if (list != null) {
            for (WzObject<?, ?> v : list) {
                res.add(intOf(v));
            }
        }
        return res;
    }

    private static int intOf(Object node) {
        return node instanceof WzProperty<?> p ? p.getIntValue() : 0;
    }

    private static double doubleOf(Object node, double def) {
        if (node instanceof WzProperty<?> p && p.getValue() != null) {
            Object v = p.getValue();
            return v instanceof Number n ? n.doubleValue() : Double.parseDouble(v.toString().trim());
        }
        return def;
    }
}
