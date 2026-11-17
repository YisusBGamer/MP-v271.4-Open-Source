package net.swordie.ms.loaders;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.items.ItemOption;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.jwzlib.WzFile;
import net.swordie.ms.jwzlib.WzObject;
import net.swordie.ms.jwzlib.WzProperty;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Familiar potential lines as the v265 client reads them (0x1406c7770): the top level of Item/FamiliarOption.img,
 * "legacy" skipped, ids under 100000 (100000+ and 200000+ are Mystic Frontier dice lines). Id / 10000 is the rank
 * (1 Common .. 5 Legendary); the 0R9xxx lines are familiar buffs (familiarBuff item). Values are flat, stored as
 * level 1. Badge potentials still use the legacy table (ItemData).
 */
public class FamiliarOptionData {

    private static final Logger log = LogManager.getLogger(FamiliarOptionData.class);

    private static volatile Map<Integer, ItemOption> options;

    private static final Map<String, BaseStat> STATS = new LinkedHashMap<>();

    static {
        STATS.put("incSTR", BaseStat.str);
        STATS.put("incDEX", BaseStat.dex);
        STATS.put("incINT", BaseStat.inte);
        STATS.put("incLUK", BaseStat.luk);
        STATS.put("incMHP", BaseStat.mhp);
        STATS.put("incMMP", BaseStat.mmp);
        STATS.put("incPAD", BaseStat.pad);
        STATS.put("incMAD", BaseStat.mad);
        STATS.put("incPDD", BaseStat.pdd);
        STATS.put("incCr", BaseStat.cr);
        STATS.put("incCriticaldamage", BaseStat.crDmg);
        STATS.put("incSTRr", BaseStat.strR);
        STATS.put("incDEXr", BaseStat.dexR);
        STATS.put("incINTr", BaseStat.intR);
        STATS.put("incLUKr", BaseStat.lukR);
        STATS.put("incMHPr", BaseStat.mhpR);
        STATS.put("incMMPr", BaseStat.mmpR);
        STATS.put("incPADr", BaseStat.padR);
        STATS.put("incMADr", BaseStat.madR);
        STATS.put("incPDDr", BaseStat.pddR);
        STATS.put("ignoreTargetDEF", BaseStat.ied);
        STATS.put("RecoveryUP", BaseStat.recoveryUp);
        STATS.put("mpconReduce", BaseStat.mpconReduce);
        STATS.put("incMesoProp", BaseStat.mesoR);
        STATS.put("incRewardProp", BaseStat.dropR);
    }

    private static Map<Integer, ItemOption> get() {
        Map<Integer, ItemOption> res = options;
        if (res == null) {
            synchronized (FamiliarOptionData.class) {
                res = options;
                if (res == null) {
                    res = load();
                    options = res;
                }
            }
        }
        return res;
    }

    private static Map<Integer, ItemOption> load() {
        Map<Integer, ItemOption> res = new HashMap<>();
        try {
            WzFile file = new WzFile(ServerConstants.WZ_DIR + "/Item.wz");
            WzObject<?, ?> root = file.getChild("FamiliarOption.img");
            if (root == null) {
                log.warn("[Familiar] Item.wz/FamiliarOption.img not found");
                return res;
            }
            for (WzObject<?, ?> idNode : root) {
                if (!Util.isNumber(idNode.getName())) {
                    continue;
                }
                int id = Integer.parseInt(idNode.getName());
                if (id <= 0 || id >= 100000) {
                    continue;
                }
                ItemOption io = new ItemOption();
                io.setId(id);
                io.setWeight(1);
                WzObject<?, ?> info = idNode.getChild("info");
                if (info != null && info.getChild("string") != null) {
                    io.setString(String.valueOf(((WzProperty<?>) info.getChild("string")).getValue()));
                }
                WzObject<?, ?> level = idNode.getChild("level");
                if (level != null) {
                    for (WzObject<?, ?> v : level) {
                        if (!(v instanceof WzProperty) || v.getChild("0") != null) {
                            continue;
                        }
                        addValue(io, v.getName(), ((WzProperty<?>) v).getIntValue(), level.hasChild("boss"));
                    }
                }
                res.put(id, io);
            }
            file.clear();
        } catch (Exception e) {
            log.error("[Familiar] could not load the familiar potential lines", e);
        }
        log.info("[Familiar] loaded " + res.size() + " familiar potential lines");
        return res;
    }

    private static void addValue(ItemOption io, String key, int val, boolean boss) {
        BaseStat bs = STATS.get(key);
        if (bs != null) {
            io.addStatValue(1, bs, val);
            return;
        }
        switch (key) {
            case "incDAMr":
                io.addStatValue(1, boss ? BaseStat.bd : BaseStat.damR, val);
                break;
            case "familiarBuff":
                io.addMiscValue(1, ItemOption.ItemOptionType.familiarBuff, val);
                break;
            case "prop":
                io.addMiscValue(1, ItemOption.ItemOptionType.prop, val);
                break;
            case "time":
                io.addMiscValue(1, ItemOption.ItemOptionType.time, val);
                break;
            case "HP":
                io.addMiscValue(1, ItemOption.ItemOptionType.hpRecoveryOnHit, val);
                break;
            case "MP":
                io.addMiscValue(1, ItemOption.ItemOptionType.mpRecoveryOnHit, val);
                break;
            case "ignoreDAMr":
                io.addMiscValue(1, ItemOption.ItemOptionType.ignoreDam, val);
                break;
            default:
                break;
        }
    }

    public static ItemOption getOption(int id) {
        return get().get(id);
    }

    /** Lines of a familiar grade (0 Common .. 4 Legendary). */
    public static List<ItemOption> getByGrade(int grade) {
        int rank = grade + 1;
        List<ItemOption> res = new ArrayList<>();
        for (ItemOption io : get().values()) {
            if (io.getId() / 10000 == rank) {
                res.add(io);
            }
        }
        return res;
    }
}
