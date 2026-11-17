package net.swordie.ms.client.character.hexa;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.erdalink.ErdaLinkModule;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.HexaPacket;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.HexaConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.enums.QuestStatus;
import net.swordie.ms.loaders.HexaCoreData;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.Util;
import net.swordie.orm.dao.HexaDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * HEXA Matrix (6th job): Sol Erda (quest record 1489: 0 = Sol Erda, 0exp = energy towards the next one), Sol Erda
 * Fragments (items), the job's skill/mastery/boost/common nodes and the HEXA Stat nodes.
 * <p>
 * The cores are kept per character (hexa_skills, hexa_stats) whatever the job; only the current job's cores are sent
 * and can be enhanced, so a reborn character that picks the same class again finds its cores as it left them.
 * Mechanics ported from the reference tree's UserHandler.handleHexaMatrixOperationRequest.
 */
public final class HexaModule {

    private static final Logger log = LogManager.getLogger(HexaModule.class);

    public static final HexaDao DAO = new HexaDao();

    // HEXA_MATRIX_OPERATION types
    private static final int ACTIVATE_NODE = 0;
    private static final int ENHANCE_NODE = 1;
    private static final int ACTIVATE_STAT = 2;
    private static final int ENHANCE_STAT = 3;
    private static final int RESET_STAT = 6;
    private static final int CHANGE_STAT = 7;

    private HexaModule() {
    }

    // ------------------------------------------------------------------------------------------------- 6th job

    public static boolean hasSixthJob(Char chr) {
        return chr.getQuestManager() != null && chr.getQuestManager().hasQuestCompleted(HexaConstants.SIXTH_JOB_QUEST);
    }

    /**
     * The 6th job at level 260 after the 5th, with the job's Origin skill node active (as the advancement gives it).
     * There is no scripted 6th job questline here, so this is the only way in. A reborn character that comes back
     * to 260 as another class gets that class's Origin node the same way.
     */
    public static void unlockSixthJob(Char chr) {
        var qm = chr.getQuestManager();
        int job = chr.getJob();
        if (chr.getLevel() < HexaConstants.SIXTH_JOB_LEVEL || !qm.hasQuestCompleted(QuestConstants.FIFTH_JOB_QUEST)
                || !HexaCoreData.hasJobCores(job)) {
            return;
        }
        boolean first = !hasSixthJob(chr);
        if (first) {
            if (!qm.completeQuest(HexaConstants.SIXTH_JOB_QUEST, false)) {
                // 1488 has no quest data in the v265 files: mark it completed directly
                Quest q = qm.getQuestById(HexaConstants.SIXTH_JOB_QUEST);
                if (q == null) {
                    q = new Quest(HexaConstants.SIXTH_JOB_QUEST, QuestStatus.Completed);
                    qm.addQuest(q);
                }
                q.setStatus(QuestStatus.Completed);
                q.setCompletedTime(FileTime.currentTime());
                chr.write(WvsContext.message(MessagePacket.questRecordMessage(q)));
            }
            getSolErdaQuest(chr);
        }
        if (ErdaLinkModule.usesErdaLink(job)) {
            // Sia Astelle's 6th job is Erda Link, not the HEXA Matrix
            ErdaLinkModule.unlock(chr, first);
            return;
        }
        if (chr.getHexaSkills().stream().anyMatch(hs -> HexaCoreData.isJobCore(job, hs.getCoreId()))) {
            if (first) {
                chr.write(HexaPacket.skillUpdate(chr));
            }
            return;
        }
        int origin = HexaCoreData.getJobCores(job).stream()
                .filter(id -> id / 10_000_000 == HexaCoreData.SKILL_CORE).findFirst().orElse(0);
        if (origin != 0) {
            setNodeLevel(chr, origin, 1);
        }
        chr.write(HexaPacket.skillUpdate(chr));
        if (first) {
            chr.chatMessage(ChatType.SystemNotice, "You've reached the 6th Job! Open the HEXA Matrix to use Sol Erda "
                    + "and Sol Erda Fragments on your 6th job skills and HEXA Stats.");
        }
        log.info("6th job Origin node for " + chr.getName() + " (job " + job + ")");
    }

    public static void onLogin(Char chr) {
        chr.write(HexaPacket.matrixInformation());
    }

    // ------------------------------------------------------------------------------------------------- Sol Erda

    private static Quest getSolErdaQuest(Char chr) {
        Quest q = chr.getQuestManager().getQuestById(HexaConstants.SOL_ERDA_QUEST);
        if (q == null || !q.hasProperty("0")) {
            chr.getQuestManager().addQuestWithQr(chr, HexaConstants.SOL_ERDA_QUEST, "0=0;0exp=0");
            q = chr.getQuestManager().getQuestById(HexaConstants.SOL_ERDA_QUEST);
        }
        return q;
    }

    public static int getSolErda(Char chr) {
        return getSolErdaQuest(chr).getIntProperty("0");
    }

    public static void addSolErda(Char chr, int amount) {
        Quest q = getSolErdaQuest(chr);
        int max = HexaCoreData.getSolErdaSlotMax();
        q.setProperty("0", Math.max(0, Math.min(max, q.getIntProperty("0") + amount)));
        sendRecord(chr, q);
    }

    /** Sol Erda energy: every gauge-full (1000) becomes a Sol Erda, up to the 20 the client can hold */
    public static void addSolErdaEnergy(Char chr, int energy) {
        if (energy <= 0) {
            return;
        }
        Quest q = getSolErdaQuest(chr);
        int gauge = HexaCoreData.getSolErdaGaugeMax();
        int max = HexaCoreData.getSolErdaSlotMax();
        long total = (long) q.getIntProperty("0exp") + energy;
        int gained = (int) (total / gauge);
        int solErda = q.getIntProperty("0");
        if (solErda + gained >= max) {
            gained = Math.max(0, max - solErda);
            total = solErda + gained >= max ? 0 : total % gauge;
        } else {
            total %= gauge;
        }
        q.setProperty("0", solErda + gained);
        q.setProperty("0exp", (int) total);
        sendRecord(chr, q);
        chr.chatMessage(ChatType.SystemNotice, gained > 0
                ? String.format("You gained %d Sol Erda energy and %d Sol Erda.", energy, gained)
                : String.format("You gained %d Sol Erda energy.", energy));
    }

    private static void sendRecord(Char chr, Quest q) {
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(q)));
    }

    /** Picking up or using a Sol Erda energy item; @return whether it was one */
    public static boolean useEnergyItem(Char chr, int itemId, int quantity) {
        int energy = HexaConstants.getSolErdaEnergy(itemId);
        if (energy <= 0) {
            return false;
        }
        addSolErdaEnergy(chr, energy * Math.max(1, quantity));
        return true;
    }

    /**
     * The client's "how many?" box for Sol Erda energy items (In ITEM_USE_COUNT_REQUEST): uses up to count of them,
     * but no more than it takes to fill Sol Erda, so none are wasted. Contributed by Dapsy (from the Discord).
     */
    public static void useEnergyItems(Char chr, int itemId, int count) {
        int energy = HexaConstants.getSolErdaEnergy(itemId);
        if (energy <= 0) {
            log.warn(chr.getName() + " used " + count + " of item " + itemId + " (not Sol Erda energy): ignored");
            return;
        }
        int owned = chr.getInventories().stream().flatMap(inv -> inv.getItems().stream())
                .filter(i -> i.getItemId() == itemId).mapToInt(Item::getQuantity).sum();
        Quest q = getSolErdaQuest(chr);
        int uses = energyItemsToUse(q.getIntProperty("0"), q.getIntProperty("0exp"),
                HexaCoreData.getSolErdaSlotMax(), HexaCoreData.getSolErdaGaugeMax(), energy, Math.min(count, owned));
        if (uses <= 0) {
            chr.chatMessage(ChatType.SystemNotice, owned <= 0 ? "You don't have that item."
                    : "Your Sol Erda is full. Spend some in the HEXA Matrix first.");
            return;
        }
        chr.consumeItem(itemId, uses);
        addSolErdaEnergy(chr, energy * uses);
    }

    /**
     * @return how many energy items to use: the ones asked for, but only as many as it takes to fill Sol Erda
     * (max Sol Erda, each worth gauge energy), never fewer than 0
     */
    static int energyItemsToUse(int solErda, int gaugeExp, int max, int gauge, int energy, int asked) {
        if (asked <= 0 || energy <= 0 || solErda >= max) {
            return 0;
        }
        long missing = (long) (max - solErda) * gauge - gaugeExp;
        long needed = (missing + energy - 1) / energy;
        return (int) Math.max(0, Math.min(asked, needed));
    }

    /**
     * !maxskills: every HEXA skill node of the character's job at its max level (unlocking the 6th job first if the
     * character qualifies). @return the nodes set, or -1 if the character can't have the 6th job.
     * Contributed by Dapsy (from the Discord).
     */
    public static int maxAllNodes(Char chr) {
        int job = chr.getJob();
        if (ErdaLinkModule.usesErdaLink(job) || !HexaCoreData.hasJobCores(job)) {
            return -1;
        }
        if (!hasSixthJob(chr)) {
            unlockSixthJob(chr);
            if (!hasSixthJob(chr)) {
                return -1;
            }
        }
        int set = 0;
        for (int coreId : HexaCoreData.getJobCores(job)) {
            HexaCoreData.SkillCore core = HexaCoreData.getSkillCore(coreId);
            if (core != null && getNodeLevel(chr, coreId) < core.maxLevel) {
                setNodeLevel(chr, coreId, core.maxLevel);
                set++;
            }
        }
        chr.write(HexaPacket.skillUpdate(chr));
        return set;
    }

    public static long getFragments(Char chr) {
        long total = 0;
        for (int id : HexaConstants.SOL_ERDA_FRAGMENTS) {
            total += chr.getInventories().stream().flatMap(inv -> inv.getItems().stream())
                    .filter(i -> i.getItemId() == id).mapToLong(Item::getQuantity).sum();
        }
        return total;
    }

    private static void takeFragments(Char chr, int amount) {
        int need = amount;
        for (int id : HexaConstants.SOL_ERDA_FRAGMENTS) {
            int have = chr.getInventories().stream().flatMap(inv -> inv.getItems().stream())
                    .filter(i -> i.getItemId() == id).mapToInt(Item::getQuantity).sum();
            int take = Math.min(have, need);
            if (take > 0) {
                chr.consumeItem(id, take);
                need -= take;
            }
            if (need <= 0) {
                return;
            }
        }
    }

    /** @return whether the cost could be paid (it is paid if so) */
    private static boolean pay(Char chr, int solErda, int fragments) {
        int haveErda = getSolErda(chr);
        long haveFragments = getFragments(chr);
        if (solErda > haveErda || fragments > haveFragments) {
            chr.chatMessage(ChatType.SystemNotice, String.format("Not enough Sol Erda (%d/%d) or Sol Erda Fragments "
                    + "(%d/%d).", haveErda, solErda, haveFragments, fragments));
            return false;
        }
        takeFragments(chr, fragments);
        addSolErda(chr, -solErda);
        return true;
    }

    // ------------------------------------------------------------------------------------------------- nodes

    private static HexaSkill getSkill(Char chr, int coreId) {
        return chr.getHexaSkills().stream().filter(hs -> hs.getCoreId() == coreId).findFirst().orElse(null);
    }

    /**
     * The level a cast of a HEXA-boosted skill (5000610xx) the character has no record of goes at: the boost nodes
     * level 5000040xx passives, and the client casts the boosted id once one is learned. The level it sent, at most
     * the skill's max level, while a boost node is learned; 0 otherwise.
     */
    public static int boostedCastLevel(Char chr, int skillId, int sentLevel) {
        if (chr == null || sentLevel <= 0) {
            return 0;
        }
        if (!net.swordie.ms.constants.SkillConstants.isHexaBoostSkill(skillId)) {
            return masteryCastLevel(chr, skillId, sentLevel);
        }
        boolean boosted = new ArrayList<>(chr.getHexaSkills()).stream().anyMatch(hs -> {
            HexaCoreData.SkillCore core = HexaCoreData.getSkillCore(hs.getCoreId());
            return hs.getLevel() > 0 && core != null && core.getCategory() == HexaCoreData.BOOST_CORE;
        });
        var si = boosted ? SkillData.getSkillInfoById(skillId) : null;
        return si == null ? 0 : Math.min(sentLevel, si.getMaxLevel());
    }

    /** A learned mastery node's skill (HEXA Dominion) cast without a skill record: the level the node holds, at most the one sent. */
    private static int masteryCastLevel(Char chr, int skillId, int sentLevel) {
        return new ArrayList<>(chr.getHexaSkills()).stream().filter(hs -> hs.getLevel() > 0).mapToInt(hs -> {
            HexaCoreData.SkillCore core = HexaCoreData.getSkillCore(hs.getCoreId());
            boolean owns = core != null && core.getCategory() == HexaCoreData.MASTERY_CORE
                    && core.connectSkills.contains(skillId);
            return owns ? Math.min(sentLevel, Math.min(core.maxLevel, hs.getLevel())) : 0;
        }).max().orElse(0);
    }

    public static int getNodeLevel(Char chr, int coreId) {
        HexaSkill hs = getSkill(chr, coreId);
        return hs == null ? 0 : hs.getLevel();
    }

    /** Sets a node's level and gives its skills that level */
    public static void setNodeLevel(Char chr, int coreId, int level) {
        HexaCoreData.SkillCore core = HexaCoreData.getSkillCore(coreId);
        if (core == null) {
            return;
        }
        HexaSkill hs = getSkill(chr, coreId);
        if (hs == null) {
            hs = new HexaSkill(coreId, level);
            chr.getHexaSkills().add(hs);
        } else {
            hs.setLevel(level);
        }
        applyNodeSkills(chr, core, level);
    }

    private static void applyNodeSkills(Char chr, HexaCoreData.SkillCore core, int level) {
        List<Skill> skills = new ArrayList<>();
        for (int skillId : core.connectSkills) {
            Skill skill = SkillData.getSkillDeepCopyById(skillId);
            if (skill == null) {
                log.warn("HEXA core " + core.id + " names skill " + skillId + ", which the data doesn't have");
                continue;
            }
            int lv = Math.min(core.maxLevel, level);
            skill.setCurrentLevel(lv);
            skill.setMasterLevel(core.maxLevel);
            skill.setMaxLevel(core.maxLevel);
            skills.add(skill);
        }
        if (!skills.isEmpty()) {
            chr.addSkills(skills);
        }
    }

    private static HexaStat getStat(Char chr, int coreId) {
        return chr.getHexaStats().stream().filter(hs -> hs.getCoreId() == coreId).findFirst().orElse(null);
    }

    // ------------------------------------------------------------------------------------------------- requests

    public static void handleOperation(Char chr, InPacket inPacket) {
        int type = inPacket.decodeInt();
        if (!hasSixthJob(chr)) {
            chr.dispose();
            return;
        }
        switch (type) {
            case ACTIVATE_NODE -> activateNode(chr, inPacket.decodeInt());
            case ENHANCE_NODE -> {
                int coreId = inPacket.decodeInt();
                int fromLevel = inPacket.decodeInt();
                int toLevel = inPacket.decodeInt();
                inPacket.decodeInt(); // the Sol Erda the client worked out
                inPacket.decodeInt(); // the fragments the client worked out
                enhanceNode(chr, coreId, fromLevel, toLevel);
            }
            case ACTIVATE_STAT -> {
                int coreId = inPacket.decodeInt();
                int[] types = {inPacket.decodeInt(), inPacket.decodeInt(), inPacket.decodeInt()};
                activateStat(chr, coreId, types);
            }
            case ENHANCE_STAT -> enhanceStat(chr, inPacket.decodeInt());
            case RESET_STAT -> resetStat(chr, inPacket.decodeInt());
            case CHANGE_STAT -> {
                long meso = inPacket.decodeLong();
                int count = inPacket.decodeInt();
                List<int[]> changes = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    changes.add(new int[]{inPacket.decodeInt(), inPacket.decodeInt(), inPacket.decodeInt(),
                            inPacket.decodeInt()});
                }
                changeStats(chr, meso, changes);
            }
            default -> {
                // 4, 5 (stat presets), 8 (second password): nothing kept server side
                chr.write(HexaPacket.message(type, 0, 0, 0));
            }
        }
        chr.dispose();
    }

    private static void activateNode(Char chr, int coreId) {
        HexaCoreData.SkillCore core = HexaCoreData.getSkillCore(coreId);
        if (core == null || !HexaCoreData.isJobCore(chr.getJob(), coreId) || getNodeLevel(chr, coreId) > 0
                || ErdaLinkModule.usesErdaLink(chr.getJob())) {
            chr.write(HexaPacket.activationResult(false));
            return;
        }
        int nodeType = core.getCategory() - 1;
        if (!pay(chr, HexaConstants.getActivateSolErda(nodeType, core.subType),
                HexaConstants.getActivateFragments(nodeType, core.subType))) {
            chr.write(HexaPacket.activationResult(false));
            return;
        }
        setNodeLevel(chr, coreId, 1);
        chr.write(HexaPacket.skillUpdate(chr));
        chr.write(HexaPacket.message(ACTIVATE_NODE, 0, coreId, 0));
        chr.write(HexaPacket.activationResult(true));
    }

    private static void enhanceNode(Char chr, int coreId, int fromLevel, int toLevel) {
        HexaCoreData.SkillCore core = HexaCoreData.getSkillCore(coreId);
        int level = getNodeLevel(chr, coreId);
        if (core == null || level == 0 || level != fromLevel || toLevel <= level || toLevel > core.maxLevel
                || !HexaCoreData.isJobCore(chr.getJob(), coreId) || ErdaLinkModule.usesErdaLink(chr.getJob())) {
            return;
        }
        int nodeType = core.getCategory() - 1;
        int solErda = 0;
        int fragments = 0;
        for (int i = level; i < toLevel; i++) {
            solErda += HexaConstants.getUpgradeSolErda(nodeType, core.subType, i);
            fragments += HexaConstants.getUpgradeFragments(nodeType, core.subType, i);
        }
        if (!pay(chr, solErda, fragments)) {
            return;
        }
        setNodeLevel(chr, coreId, toLevel);
        chr.write(HexaPacket.skillUpdate(chr));
        chr.write(HexaPacket.message(ENHANCE_NODE, 0, coreId, toLevel));
    }

    private static void activateStat(Char chr, int coreId, int[] types) {
        HexaCoreData.StatCore core = HexaCoreData.getStatCore(coreId);
        int[] unlock = HexaConstants.getStatCoreUnlock(coreId);
        boolean valid = core != null && unlock != null && getStat(chr, coreId) == null && chr.getLevel() >= core.reqLevel
                && (core.reqCore == 0 || getStat(chr, core.reqCore) != null)
                && types[0] != types[1] && types[0] != types[2] && types[1] != types[2];
        for (int t : types) {
            valid &= t >= 0 && t < HexaConstants.STAT_TYPES;
        }
        if (!valid || !pay(chr, unlock[1], unlock[2])) {
            chr.write(HexaPacket.activationResult(false));
            return;
        }
        HexaStat stat = new HexaStat(coreId);
        for (int i = 0; i < 3; i++) {
            stat.setType(i, types[i]);
            stat.setLevel(i, i == 0 ? 1 : 0);
        }
        chr.getHexaStats().add(stat);
        if (!chr.hasSkill(HexaConstants.HEXA_STAT_SKILL)) {
            chr.addSkill(HexaConstants.HEXA_STAT_SKILL, 1, 1);
        }
        chr.write(HexaPacket.statUpdate(chr));
        chr.write(HexaPacket.message(ACTIVATE_STAT, 0, coreId, 1));
        chr.write(HexaPacket.activationResult(true));
    }

    private static void enhanceStat(Char chr, int coreId) {
        HexaCoreData.StatCore core = HexaCoreData.getStatCore(coreId);
        HexaStat stat = getStat(chr, coreId);
        if (core == null || stat == null || stat.getTotalLevel() >= core.maxLevel) {
            return;
        }
        int mainLevel = stat.getLevel(0);
        int idx = Math.min(mainLevel, HexaConstants.STAT_UPGRADE_FRAGMENTS.length - 1);
        if (!pay(chr, HexaConstants.STAT_UPGRADE_SOL_ERDA[idx], HexaConstants.STAT_UPGRADE_FRAGMENTS[idx])) {
            return;
        }
        // the main stat gets the level with its level's chance, otherwise one of the additional stats
        int target;
        double weight = mainLevel < HexaConstants.STAT_MAIN_WEIGHT.length ? HexaConstants.STAT_MAIN_WEIGHT[mainLevel] : 0;
        if (mainLevel < HexaConstants.STAT_MAX_LEVEL && Util.succeedProp((int) Math.round(weight * 100))) {
            target = 0;
        } else {
            List<Integer> options = new ArrayList<>();
            for (int i = 1; i < 3; i++) {
                if (stat.getLevel(i) < HexaConstants.STAT_MAX_LEVEL) {
                    options.add(i);
                }
            }
            target = options.isEmpty() ? 0 : options.get(Util.getRandom(options.size() - 1));
        }
        stat.setLevel(target, Math.min(HexaConstants.STAT_MAX_LEVEL, stat.getLevel(target) + 1));
        chr.write(HexaPacket.statUpdate(chr));
        chr.write(HexaPacket.message(ENHANCE_STAT, 0, coreId, stat.getLevel(target)));
    }

    private static void resetStat(Char chr, int coreId) {
        HexaStat stat = getStat(chr, coreId);
        if (stat == null || chr.getMoney() < HexaConstants.STAT_RESET_MESO) {
            return;
        }
        chr.deductMoney(HexaConstants.STAT_RESET_MESO);
        for (int i = 0; i < 3; i++) {
            stat.setLevel(i, 0);
        }
        chr.write(HexaPacket.statUpdate(chr));
        chr.write(HexaPacket.message(RESET_STAT, 0, 0, 0));
    }

    /** {core, main type, additional type, additional type} per node */
    private static void changeStats(Char chr, long meso, List<int[]> changes) {
        if (meso < 0 || chr.getMoney() < meso) {
            return;
        }
        boolean changed = false;
        for (int[] change : changes) {
            HexaStat stat = getStat(chr, change[0]);
            if (stat == null) {
                continue;
            }
            for (int i = 0; i < 3; i++) {
                int t = change[i + 1];
                if (t >= 0 && t < HexaConstants.STAT_TYPES) {
                    stat.setType(i, t);
                    changed = true;
                }
            }
        }
        if (changed) {
            chr.deductMoney(meso);
        }
        chr.write(HexaPacket.statUpdate(chr));
        chr.write(HexaPacket.message(CHANGE_STAT, 0, 0, 0));
    }

    /** Sol Erda Conversion: type 0 asks for the list, type 1 converts (item, Sol Erda to use) */
    public static void handleErdaConversion(Char chr, InPacket inPacket) {
        int type = inPacket.decodeInt();
        if (type == 0) {
            chr.write(HexaPacket.erdaConversion(0, 0, 0, 0));
        } else if (type == 1) {
            int itemId = inPacket.decodeInt();
            int solErda = inPacket.decodeInt();
            int perErda = 0;
            for (int[] conversion : HexaConstants.ERDA_CONVERSIONS) {
                if (conversion[0] == itemId) {
                    perErda = conversion[1];
                }
            }
            int quantity = perErda * solErda;
            if (perErda > 0 && solErda > 0 && solErda <= getSolErda(chr) && chr.canHold(itemId, quantity)) {
                addSolErda(chr, -solErda);
                chr.addItemToInventory(itemId, quantity);
                chr.write(HexaPacket.erdaConversion(1, solErda, itemId, quantity));
            }
        }
        chr.dispose();
    }

    // ------------------------------------------------------------------------------------------------- encoding

    /** HEXA nodes of the current job: count, (core, level, enabled, disabled)..., byte, int */
    public static void encodeSkills(Char chr, OutPacket outPacket) {
        List<HexaSkill> skills = new ArrayList<>();
        if (chr.getId() != 0) {
            for (HexaSkill hs : chr.getHexaSkills()) {
                if (HexaCoreData.isJobCore(chr.getJob(), hs.getCoreId())) {
                    skills.add(hs);
                }
            }
        }
        outPacket.encodeInt(skills.size());
        for (HexaSkill hs : skills) {
            hs.encode(outPacket);
        }
        outPacket.encodeByte(0);
        outPacket.encodeInt(0);
    }

    /** HEXA Stat nodes: the active set and a saved (empty) set per node, then the node list */
    public static void encodeStats(Char chr, OutPacket outPacket) {
        List<HexaStat> stats = chr.getId() != 0 ? chr.getHexaStats() : List.of();
        outPacket.encodeInt(stats.size());
        for (HexaStat stat : stats) {
            outPacket.encodeInt(stat.getCoreId());
            outPacket.encodeInt(stat.getCoreId());
            outPacket.encodeInt(0);
            outPacket.encodeInt(1); // in use
            for (int i = 0; i < 3; i++) {
                outPacket.encodeInt(stat.getType(i));
                outPacket.encodeInt(stat.getLevel(i));
            }
            outPacket.encodeInt(stat.getCoreId());
            outPacket.encodeInt(1);
            outPacket.encodeInt(0);
            for (int i = 0; i < 3; i++) {
                outPacket.encodeInt(-1);
                outPacket.encodeInt(0);
            }
        }
        outPacket.encodeInt(stats.size());
        for (HexaStat stat : stats) {
            outPacket.encodeInt(stat.getCoreId());
            outPacket.encodeInt(stat.getCoreId());
            outPacket.encodeInt(0);
        }
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
    }
}
