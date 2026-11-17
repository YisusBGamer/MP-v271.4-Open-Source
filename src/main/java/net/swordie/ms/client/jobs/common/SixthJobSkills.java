package net.swordie.ms.client.jobs.common;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.SkillStat;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.world.field.instance.Instance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 6th job Origin and Ascent skills (a 6th job book, ids x1500 and up). Their cast is a skill request that pays the
 * cooldown, and the hits that follow come in as attacks of the same id for the cast's updatableTime: those don't pay it
 * again (refusing them would drop the slashes and leave only the later hits).
 * <p>
 * Ascent skills (Skill.wz common/6thCount) also follow GMS in boss runs: the entry gives each that many uses with no
 * cooldown in between (Out SIXTH_SKILL_COUNT; the client counts them down), and the last use starts the Skill.wz
 * cooldown. Outside a boss run they only have that cooldown.
 */
public final class SixthJobSkills {

    private static final int SIXTH_JOB_BOOK_DIGIT = 4;
    private static final int FIRST_ORIGIN_INDEX = 1500;
    private static final int FIRST_NON_JOB_SKILL = 400_000_000;
    /** how long the hits of a cast may come when the skill gives no updatableTime / ndTime */
    private static final int DEFAULT_CAST_MS = 10_000;

    /** The uses left, for the boss run (instance) they were given in. */
    private static final class Run {
        final Instance instance;
        final Map<Integer, Integer> left;

        Run(Instance instance, Map<Integer, Integer> left) {
            this.instance = instance;
            this.left = left;
        }
    }

    // both maps are only touched while holding their own lock
    private static final Map<Char, Run> runs = Collections.synchronizedMap(new WeakHashMap<>());
    private static final Map<Char, Map<Integer, Long>> castUntil = Collections.synchronizedMap(new WeakHashMap<>());

    private SixthJobSkills() {
    }

    /** A 6th job book (job id ending in 4), Origin/Ascent range; not 5th job (400xxxxxx) or common (500xxxxxx). */
    public static boolean isOriginOrAscent(int skillId) {
        return (skillId / 10000) % 10 == SIXTH_JOB_BOOK_DIGIT && skillId % 10000 >= FIRST_ORIGIN_INDEX
                && skillId < FIRST_NON_JOB_SKILL;
    }

    /** A cast that went through: its hits may come for the skill's updatableTime (or ndTime). */
    public static void onCast(Char chr, int skillId, int slv) {
        if (!isOriginOrAscent(skillId)) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        int ms = 0;
        if (si != null && slv > 0) {
            ms = Math.max(si.getValue(SkillStat.updatableTime, slv), si.getValue(SkillStat.ndTime, slv));
        }
        long until = System.currentTimeMillis() + (ms > 0 ? ms : DEFAULT_CAST_MS);
        synchronized (castUntil) {
            castUntil.computeIfAbsent(chr, k -> new HashMap<>()).put(skillId, until);
        }
    }

    /** Whether an attack of the skill is a hit of a cast that already paid. */
    public static boolean isCastHit(Char chr, int skillId) {
        if (!isOriginOrAscent(skillId)) {
            return false;
        }
        synchronized (castUntil) {
            Map<Integer, Long> casts = castUntil.get(chr);
            Long until = casts == null ? null : casts.get(skillId);
            return until != null && System.currentTimeMillis() <= until;
        }
    }

    /** A boss entry: the Char's Ascent skills get their uses for this run (once per run). */
    public static void onBossEntry(Char chr) {
        Instance instance = chr == null ? null : chr.getInstance();
        if (instance == null) {
            return;
        }
        Map<Integer, Integer> counts = new HashMap<>();
        for (Skill skill : new ArrayList<>(chr.getSkills())) {
            int id = skill.getSkillId();
            if (!isOriginOrAscent(id)) {
                continue;
            }
            int count = SkillData.getSixthCount(id);
            if (count > 0 && chr.hasSkill(id)) {
                counts.put(id, count);
            }
        }
        if (counts.isEmpty()) {
            return;
        }
        synchronized (runs) {
            Run run = runs.get(chr);
            if (run != null && run.instance == instance) {
                return;
            }
            runs.put(chr, new Run(instance, new HashMap<>(counts)));
        }
        chr.write(WvsContext.sixthSkillCount(counts));
    }

    /**
     * A cast of the skill. In a boss run with uses left it takes one: true while uses stay after it (that cast has no
     * cooldown), false for the last one (its cooldown starts) and for any skill this doesn't count.
     */
    public static boolean useWithoutCooldown(Char chr, int skillId) {
        synchronized (runs) {
            Run run = runs.get(chr);
            if (run != null && run.instance != chr.getInstance()) {
                runs.remove(chr); // left that run
                return false;
            }
            Integer left = run == null ? null : run.left.get(skillId);
            if (left == null || left <= 0) {
                return false;
            }
            run.left.put(skillId, left - 1);
            return left - 1 > 0;
        }
    }
}
