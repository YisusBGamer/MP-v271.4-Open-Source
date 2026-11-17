package net.swordie.ms.client.character.keys;

import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.loaders.SkillData;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Which key entries the v271 client accepts in FUNC_KEY_MAPPED_MAN_INIT (reader 0x143811eb0).
 * A skill entry that fails the entry decoder (0x14057f1e0) makes the client throw away that whole preset, load its
 * default keys and send them back (FUNC_KEY_MAPPED_MODIFIED). Entries failing the later check (0x14057f8c0) are only
 * blanked. The Skill.wz/Etc.wz parts of the client's not-keyable test (0x141004d20) come from SkillInfo.keyFlags.
 */
public final class FuncKeyRules {

    private FuncKeyRules() {
    }

    public static final byte TYPE_NONE = 0;
    public static final byte TYPE_SKILL = 1;

    // SkillInfo.keyFlags
    /** processtype or additional_process has 90 (the client's skill entry +0x1c30 set). */
    public static final int KEY_PROCESS_90 = 1;
    /** Another skill's changeSkill/changeSkillJob entry with quickslot and no onCooltime (SkillInfo +0x1988 map). */
    public static final int KEY_QUICKSLOT_CHANGE = 2;
    /** baseSkill of an Etc.wz UIEvent.img liveEventSkill event (map 0x148ca88b0). */
    public static final int KEY_EVENT_BASE = 4;
    /** showSummonedBuffIcon: the summon gets a buff icon (IndieBuffIcon for the summon's time). Not a key rule. */
    public static final int SUMMON_BUFF_ICON = 8;

    /** Whether the client keeps this entry; false entries should be sent as empty. */
    public static boolean isAllowed(int type, int value) {
        return !wipesPreset(type, value) && !isBlanked(type, value);
    }

    /** The entry decoder refuses it: the client resets the whole preset to its defaults. */
    public static boolean wipesPreset(int type, int value) {
        if (type != TYPE_SKILL) {
            return false;
        }
        int digit = (value / 1000) % 10;
        if (!in(Rule.oldStyleKeyable, value) && (digit == 0 || digit == 9)) {
            // old-style numbers (passives, hidden skills) only as an addAttack trigger
            if (!in(Rule.addAttackTrigger, value) || in(Rule.triggerRefused, value)) {
                return true;
            }
        }
        return isNotKeyable(value) || in(Rule.wipesPreset, value);
    }

    /** The client blanks just this entry (0x14057f8c0). */
    public static boolean isBlanked(int type, int value) {
        switch (type) {
            case TYPE_NONE:
                return value != 0;
            case TYPE_SKILL:
                return in(Rule.blanked, value) || SkillData.getSkillInfoById(value) == null;
            case 4:
            case 5:
            case 6:
                return REMOVED_FUNCTIONS.contains(value);
            case 7:
                return in(Rule.blankedType7, value);
            default:
                return false;
        }
    }

    private static int skillJob(int skillId) {
        int job = skillId / 10000;
        if (job == 8000 || job == 8001) {
            job = skillId / 100;
        }
        return job;
    }

    // 0x141004d20
    static boolean isNotKeyable(int id) {
        int job = skillJob(id);
        if (job == 40000 || job == 50000) {
            if (id % 10000 >= 4000) {
                return true;
            }
        } else if (job > 50000 && job <= 50008) {
            return true;
        }
        if (in(Rule.notKeyable, id)) {
            return true;
        }
        if (in(Rule.keyable, id)) {
            return false;
        }
        if (id % 10000 == 1098 && isBeginnerJob(job)) {
            return true; // 0x1406adbf0 == 1098
        }
        if (id > 0 && job == 9100) {
            return !in(Rule.keyableGuild, id);
        }
        if (id > 0 && job == 9500) {
            return true;
        }
        if (in(Rule.notKeyableExtra, id)) {
            return true;
        }
        // Skill.wz/Etc.wz data
        if (HEXA_JOBS.contains(job) && id % 1000 < 500) {
            return true; // 0x140f39c90: HEXA mastery/enhancement nodes
        }
        SkillInfo si = SkillData.getSkillInfoById(id);
        if (si == null) {
            return false;
        }
        if ((si.getKeyFlags() & (KEY_PROCESS_90 | KEY_QUICKSLOT_CHANGE | KEY_EVENT_BASE)) != 0) {
            return true;
        }
        return id >= COMMON_V_SKILL_FIRST && id <= COMMON_V_SKILL_LAST && si.getvSkill() == 2; // 0x140f3d120
    }

    /** Which part of the not-keyable test refuses this skill (for checks), or null. */
    public static String notKeyableReason(int id) {
        if (!isNotKeyable(id)) {
            return null;
        }
        int job = skillJob(id);
        SkillInfo si = SkillData.getSkillInfoById(id);
        int flags = si == null ? 0 : si.getKeyFlags();
        if (in(Rule.notKeyable, id) || in(Rule.notKeyableExtra, id) || job == 9100 || job == 9500
                || job == 40000 || job == 50000 || (job > 50000 && job <= 50008)) {
            return "list";
        }
        if (id % 10000 == 1098 && isBeginnerJob(job)) {
            return "beginner1098";
        }
        if (HEXA_JOBS.contains(job) && id % 1000 < 500) {
            return "hexaJob";
        }
        if ((flags & KEY_PROCESS_90) != 0) {
            return "process90";
        }
        if ((flags & KEY_QUICKSLOT_CHANGE) != 0) {
            return "quickslotChange";
        }
        if ((flags & KEY_EVENT_BASE) != 0) {
            return "eventBase";
        }
        return "vSkill2";
    }

    // 0x1406ae2e0
    private static boolean isBeginnerJob(int job) {
        if (job % 1000 == 0 || (job >= 800000 && job < 800100) || (job >= 800100 && job < 800200) || job == 8001) {
            return true;
        }
        return BEGINNER_JOBS.contains(job);
    }

    private static final Set<Integer> BEGINNER_JOBS = Set.of(
            2001, 2002, 2003, 2004, 2005, 3001, 3002, 4001, 4002, 6001, 6002, 6003, 12005, 12006, 13001, 15001, 15002,
            16001, 16002, 17001, 18001
    );

    // 0x1489982d8 (built at 0x140010e50): 6th job (HEXA) jobs
    private static final Set<Integer> HEXA_JOBS = Set.of(
            114, 124, 134, 214, 224, 234, 314, 324, 334, 414, 424, 436, 514, 524, 534, 1114, 1214, 1314, 1414, 1514,
            2114, 2220, 2314, 2414, 2514, 2714, 3114, 3124, 3214, 3314, 3514, 3614, 3714, 5114, 6114, 6314, 6414, 6514,
            10114, 14214, 15114, 15214, 15414, 15514, 16214, 16414, 16114, 4114, 4214, 17514, 17214, 18214, 18114
    );

    /** the client's skill id lists (resources/data/func-key-rules.tsv rule column) */
    private enum Rule {
        notKeyable, keyable, notKeyableExtra, addAttackTrigger, triggerRefused, wipesPreset, oldStyleKeyable, blanked,
        blankedType7, keyableGuild
    }

    private static final Supplier<Map<Rule, Set<Integer>>> RULES = DataTable.cached("func-key-rules", t -> {
        Map<Rule, Set<Integer>> m = new EnumMap<>(Rule.class);
        for (DataTable.Row r : t.rows()) {
            m.computeIfAbsent(Rule.valueOf(r.str("rule").trim()), k -> new HashSet<>()).add(r.i("id"));
        }
        m.replaceAll((k, v) -> Set.copyOf(v));
        return m;
    });

    private static Set<Integer> ids(Rule rule) {
        return RULES.get().getOrDefault(rule, Set.of());
    }

    private static boolean in(Rule rule, int id) {
        return ids(rule).contains(id);
    }

    // 0x140f3d120: shared V skills
    private static final int COMMON_V_SKILL_FIRST = 80010000, COMMON_V_SKILL_LAST = 80019999;

    // window/action/macro-style function ids gone in v271 (0x14057f4b0)

    private static final Set<Integer> REMOVED_FUNCTIONS = Set.of(24, 28, 32, 33, 34, 36, 37, 38, 44, 200, 201);
}
