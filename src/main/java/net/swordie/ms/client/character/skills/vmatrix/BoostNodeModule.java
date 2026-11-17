package net.swordie.ms.client.character.skills.vmatrix;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.SkillStat;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.loaders.SkillData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Picks the Boost Nodes worth wearing: the ones that raise the skills a character hits hardest with. Each node is
 * scored by the attack worth (damage x hits x targets) of the learned skills its three boosts raise; the best ones
 * are worn, one per main skill, with every skill on at most two worn nodes (the usual "trio" setup).
 */
public final class BoostNodeModule {

    private BoostNodeModule() {
    }

    public static boolean isBoostNode(MatrixRecord mr) {
        return mr.getIconID() >= 20000000 && mr.getIconID() < 30000000; // hardcode-ok
    }

    /** How much a skill hits for: damage times hits times targets, 0 for skills that don't attack. */
    public static long attackWorth(SkillInfo si) {
        if (si == null) {
            return 0;
        }
        int lv = Math.max(1, si.getMaxLevel());
        int dmg = si.getValue(SkillStat.damage, lv);
        if (dmg <= 0 || (si.isPsd() && !SkillConstants.isKeyDownSkill(si.getSkillId()))) {
            return 0;
        }
        int hits = si.getValue(SkillStat.attackCount, lv);
        if (hits <= 0) {
            hits = si.getValue(SkillStat.bulletCount, lv);
        }
        hits = Math.min(15, Math.max(1, hits));
        return (long) dmg * hits * Math.max(1, si.getValue(SkillStat.mobCount, lv));
    }

    /** What a node is worth to this character: the attack worth of the learned skills its boosts raise. */
    public static long score(Char chr, MatrixRecord mr) {
        long score = 0;
        for (int boost : mr.getSkills()) {
            SkillInfo bsi = boost == 0 ? null : SkillData.getSkillInfoById(boost);
            if (bsi == null) {
                continue;
            }
            for (int base : bsi.getPsdSkills()) {
                if (chr.getSkillLevel(base) > 0) {
                    score += attackWorth(SkillData.getSkillInfoById(base));
                }
            }
        }
        return score;
    }

    /**
     * The best Boost Nodes to wear, at most {@code max}: highest score first (then level), one node per main skill,
     * every skill on at most two of them, and none that raise nothing the character uses.
     */
    public static List<MatrixRecord> choose(Char chr, Collection<MatrixRecord> nodes, int max) {
        List<MatrixRecord> res = new ArrayList<>();
        if (max <= 0) {
            return res;
        }
        Map<MatrixRecord, Long> scores = new HashMap<>();
        for (MatrixRecord mr : nodes) {
            if (isBoostNode(mr)) {
                scores.put(mr, score(chr, mr));
            }
        }
        List<MatrixRecord> sorted = new ArrayList<>(scores.keySet());
        sorted.removeIf(mr -> scores.get(mr) <= 0);
        sorted.sort(Comparator.comparingLong((MatrixRecord mr) -> -scores.get(mr))
                .thenComparingInt(mr -> -mr.getSlv()));
        Set<Integer> icons = new HashSet<>();
        Map<Integer, Integer> uses = new HashMap<>();
        for (MatrixRecord mr : sorted) {
            if (res.size() >= max || icons.contains(mr.getIconID())) {
                continue;
            }
            boolean fits = true;
            for (int id : mr.getSkills()) {
                if (id != 0 && uses.getOrDefault(id, 0) >= 2) {
                    fits = false;
                    break;
                }
            }
            if (!fits) {
                continue;
            }
            for (int id : mr.getSkills()) {
                if (id != 0) {
                    uses.merge(id, 1, Integer::sum);
                }
            }
            icons.add(mr.getIconID());
            res.add(mr);
        }
        return res;
    }

    /**
     * Wears the character's best Boost Nodes in every slot its other nodes don't use. Skill and special nodes stay
     * where they are, worn or not.
     *
     * @return a message for the player
     */
    public static String equipBest(Char chr) {
        int slots = chr.getMaxMatrixSlots();
        if (slots <= 0) {
            return "You don't have any node slots yet. They open with the 5th job.";
        }
        List<MatrixRecord> all = new ArrayList<>(chr.getMatrixRecords());
        long otherWorn = all.stream().filter(mr -> mr.isActive() && !isBoostNode(mr)).count();
        int free = (int) Math.max(0, slots - otherWorn);
        List<MatrixRecord> best = choose(chr, all, free);
        // worn nodes stay in the slots the picks don't fill (a node that scores nothing still beats an empty slot)
        List<MatrixRecord> keep = new ArrayList<>(best);
        for (MatrixRecord mr : all) {
            if (keep.size() >= free) {
                break;
            }
            if (mr.isActive() && isBoostNode(mr) && !keep.contains(mr)) {
                keep.add(mr);
            }
        }
        int removed = 0, added = 0;
        for (MatrixRecord mr : all) {
            if (mr.isActive() && isBoostNode(mr) && !keep.contains(mr)) {
                mr.deactivate(chr, -1);
                removed++;
            }
        }
        for (MatrixRecord mr : best) {
            if (!mr.isActive()) {
                int pos = chr.getFirstOpenMatrixSlot();
                if (pos < 0) {
                    break;
                }
                mr.activate(chr, pos);
                added++;
            }
        }
        chr.write(WvsContext.matrixUpdate(chr, false, 0, 0));
        if (best.isEmpty()) {
            return "None of your Boost Nodes raise a skill you attack with.";
        }
        return String.format("You now wear your %d best Boost Node(s) (%d put on, %d taken off). Skill and special "
                + "nodes were left as they were.", best.size(), added, removed);
    }
}
