package net.swordie.ms.client.character.skills.vmatrix;

import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.enums.InvType;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.loaders.VCoreData;
import net.swordie.ms.loaders.containerclasses.VCoreInfo;
import net.swordie.ms.util.Util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created on 06/01/2022.
 *
 * @author Asura
 */
public class NodestoneModule {
    public static final int AMAZING_NODESTONE = 2436037;
    
    // items of the client's "Use Nodestones" group 0 (0x1405a84b0)
    private static final Set<Integer> BULK_GROUP_NORMAL = Set.of(2435719, 2435902, 2436078, 2436324, 2439869, 2631128);

    /**
     * The "Use Nodestones" window: uses up to count Nodestones of the group (time-limited or untradable ones
     * first, then from the front of the Use tab) and sends the list of nodes made.
     */
    public static void openBulk(Char chr, int group, int count) {
        if (group != 0 || count <= 0) {
            chr.dispose();
            return;
        }
        if (!chr.getQuestManager().hasQuestCompleted(QuestConstants.FIFTH_JOB_QUEST)) {
            chr.chatMessage("You need to be 5th job to open this item.");
            chr.dispose();
            return;
        }
        int free = MatrixRecord.MAX_RECORDS - chr.getMatrixRecords().size();
        if (free <= 0) {
            chr.chatMessage("You currently have a lot of nodes. Please disassemble before trying to get new nodes.");
            chr.dispose();
            return;
        }
        List<Item> stones = chr.getInventoryByType(InvType.CONSUME).getItems().stream()
                .filter(i -> BULK_GROUP_NORMAL.contains(i.getItemId()) && i.getQuantity() > 0)
                .sorted(Comparator.comparing((Item i) -> !(i.hasExpireDate() || !i.isTradable()))
                        .thenComparingInt(Item::getBagIndex))
                .collect(Collectors.toList());
        int left = Math.min(count, free);
        List<MatrixRecord> made = new ArrayList<>();
        for (Item stone : stones) {
            if (left <= 0) {
                break;
            }
            int itemId = stone.getItemId();
            int use = Math.min(left, stone.getQuantity());
            int used = 0;
            for (int n = 0; n < use; n++) {
                MatrixRecord mr = rollNode(chr, itemId);
                if (mr == null) {
                    break;
                }
                made.add(mr);
                used++;
            }
            if (used > 0) {
                chr.consumeItem(stone, used);
            }
            left -= used;
            if (used < use) {
                break;
            }
        }
        if (made.size() < count && made.size() == free) {
            chr.chatMessage("Your V Matrix is full. Please disassemble nodes before using more Nodestones.");
        }
        chr.write(WvsContext.matrixUpdate(chr, false, 0, 0));
        chr.write(WvsContext.nodestoneBulkResult(made));
        chr.dispose();
    }

    /** Experience Nodestones: each gives a Concentrated Experience node (VCore 40000000, enhance fodder only) */
    public static final Set<Integer> EXPERIENCE_NODESTONES = Set.of(2439279, 2630402, 2631527, 2632290);
    public static final int EXPERIENCE_NODE = 40000000;

    /** Mirror World Nodestone: the True Arachnid Reflection node (VCore 10000024), once you have the Esfera symbol */
    public static final int MIRROR_WORLD_NODESTONE = 2438411;
    private static final int ESFERA_SYMBOL = 1712006;

    public static boolean isFixedNodestone(int itemId) {
        return EXPERIENCE_NODESTONES.contains(itemId) || itemId == MIRROR_WORLD_NODESTONE;
    }

    private static boolean hasItem(Char chr, int itemId) {
        return chr.getEquippedInventory().getItems().stream().anyMatch(i -> i.getItemId() == itemId)
                || chr.getEquipInventory().getItems().stream().anyMatch(i -> i.getItemId() == itemId);
    }

    /** @return null if used, else why not */
    public static String openExperience(Char chr, Item stone) {
        if (net.swordie.ms.ServerSettings.getBoolean("test.v271Matrix", true) && V271Matrix.isGemstone(stone.getItemId())) {
            V271Matrix.useGemstonesFromItem(chr, stone.getItemId(), 1); // v271: points (and the core it opens)
            return null;
        }
        if (!chr.getQuestManager().hasQuestCompleted(QuestConstants.FIFTH_JOB_QUEST)) {
            return "You need to be 5th job to open this item.";
        }
        if (chr.getMatrixRecords().size() >= MatrixRecord.MAX_RECORDS) {
            return "You currently have a lot of nodes. Please disassemble before trying to get new nodes.";
        }
        boolean mirror = stone.getItemId() == MIRROR_WORLD_NODESTONE;
        if (mirror && !hasItem(chr, ESFERA_SYMBOL)) {
            return "You can use this once you have the Arcane Symbol: Esfera.";
        }
        chr.consumeItem(stone, 1);
        MatrixRecord mr = new MatrixRecord(chr);
        if (mirror) {
            mr.setIconID(10000024); // hardcode-ok
            mr.setMaxLevel(50);
            mr.setSkillID1(Job.TRUE_ARACHNID_REFLECTION_SKILL_USE);
        } else {
            mr.setIconID(EXPERIENCE_NODE);
            mr.setMaxLevel(1);
        }
        mr.setSlv(1);
        chr.addMatrixRecord(mr);
        chr.write(WvsContext.nodestoneOpenResult(mr));
        chr.write(WvsContext.matrixUpdate(chr, false, 0, 0));
        return null;
    }

    public static void openNodestone(Char chr, int itemId, boolean writeNodestoneOpenResultPacket) {
        MatrixRecord mr = rollNode(chr, itemId);
        if (mr != null && (writeNodestoneOpenResultPacket || itemId == AMAZING_NODESTONE)) {
            chr.write(WvsContext.nodestoneOpenResult(mr));
        }
        chr.write(WvsContext.matrixUpdate(chr, false, 0, 0));
    }

    /** Makes one node from the Nodestone and adds it to the V Matrix; null if no node fits. */
    private static MatrixRecord rollNode(Char chr, int itemId) {

        if (itemId == AMAZING_NODESTONE) { //Amazing Nodestone, only gives you Rope Lift
            MatrixRecord mr = new MatrixRecord(chr);
            mr.setIconID(10000000); // hardcode-ok
            mr.setMaxLevel(25);
            mr.setSlv(1);
            mr.setSkillID1(Job.ROPE_LIFT);
            chr.addMatrixRecord(mr);
            return mr;

        } else {
            List<VCoreInfo> infos;
            if (Util.succeedProp(GameConstants.NODE_SELF_JOB_CHANCE)) {
                infos = new ArrayList<>(VCoreData.getPossibilitiesByJob(chr.getJob()));
            } else {
                List<Integer> jobs = new ArrayList<>(VCoreData.getJobSkills().keySet());
                jobs.remove((Integer) (int) chr.getJob()); // xd
                int job = Util.getRandomFromCollection(jobs);
                infos = new ArrayList<>(VCoreData.getPossibilitiesByJob(job));
            }

            int rng = Util.getRandom(100);
            if (rng < GameConstants.NODE_ENFORCE_CHANCE) {
                infos = infos.stream().filter(VCoreInfo::isEnforce).collect(Collectors.toList());
            } else if (rng - GameConstants.NODE_ENFORCE_CHANCE < GameConstants.NODE_SKILL_CHANCE) {
                infos = infos.stream().filter(VCoreInfo::isSkill).collect(Collectors.toList());
            } else {
                infos = infos.stream().filter(VCoreInfo::isSpecial).collect(Collectors.toList());
            }
            if (infos.isEmpty()) {
                // a job with no cores of that kind (jobs missing from VCoreData's class groups): own boost nodes
                infos = VCoreData.getPossibilitiesByJob(chr.getJob()).stream().filter(VCoreInfo::isEnforce)
                        .collect(Collectors.toList());
            }
            if (infos.isEmpty()) {
                return null;
            }

            MatrixRecord mr = new MatrixRecord(chr);
            for (int i = 0; i < 3; i++) {
                VCoreInfo vci = Util.getRandomFromCollection(infos);
                if (vci == null) {
                    break;
                }
                infos.remove(Util.findWithPred(infos, v -> v.getIconID() == vci.getIconID()));
                switch (i) {
                    case 0:
                        mr.setIconID(vci.getIconID());
                        mr.setMaxLevel(vci.getMaxLevel());
                        mr.setSkillID1(vci.getSkillID());
                        mr.setSlv(1);
                        if (vci.isSoloNode()) {
                            // stop if the info is a solo node (i.e, 5th job skill/5th job special node)
                            i = 3;
                        } else {
                            // if it's not a solo node, reduce the possibilities to the other non-solo nodes
                            infos = infos.stream().filter(v -> !v.isSoloNode()).collect(Collectors.toList());
                        }
                        break;

                    case 1:
                        mr.setSkillID2(vci.getSkillID());
                        break;

                    case 2:
                        mr.setSkillID3(vci.getSkillID());
                        break;
                }
            }

            chr.addMatrixRecord(mr);
            return mr;
        }
    }
}
