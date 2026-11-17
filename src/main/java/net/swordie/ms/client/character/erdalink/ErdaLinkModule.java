package net.swordie.ms.client.character.erdalink;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.hexa.HexaModule;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.vmatrix.BoostNodeModule;
import net.swordie.ms.client.character.skills.vmatrix.MatrixRecord;
import net.swordie.ms.client.character.skills.vmatrix.MatrixSlot;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.ErdaLinkPacket;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.loaders.ErdaLinkData;
import net.swordie.ms.loaders.ErdaLinkData.Cost;
import net.swordie.ms.loaders.ErdaLinkData.Stone;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Util;
import net.swordie.orm.dao.ErdaLinkDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Erda Link: Sia Astelle's 6th job board. Stones open along the board's paths from the Origin stone; skill, origin
 * and ultimate stones level the skills they name (up to 30), the Boost and Rush stones are passives, SHINE stones are
 * HEXA-Stat-like passives whose enhancement can fail or drop a level. Costs are Sol Erda (the HEXA Matrix's, quest
 * record 1489), Sol Erda Fragments and, for SHINE, mesos (ErdaLinkSolerda.img).
 * <p>
 * Stones are kept per character whatever the job; only the current job's board counts.
 */
public final class ErdaLinkModule {

    private static final Logger log = LogManager.getLogger(ErdaLinkModule.class);

    public static final ErdaLinkDao DAO = new ErdaLinkDao();

    // failure codes, shown by the client as "Failed to ... the stone. Code: %d" (numbered like its own checks)
    private static final int ERR_REQUIREMENTS = 1;
    private static final int ERR_COST = 3;
    private static final int ERR_PREV = 4;
    private static final int ERR_MAX_LEVEL = 6;
    private static final int ERR_INVALID = 9;

    private static boolean loggedUnknownField;

    private ErdaLinkModule() {
    }

    // ------------------------------------------------------------------------------------------------- state

    /** Whether the character's 6th job runs through Erda Link (its job has a board and it has the 6th job) */
    public static boolean hasErdaLink(Char chr) {
        return usesErdaLink(chr.getJob()) && HexaModule.hasSixthJob(chr);
    }

    /** Whether a job's 6th job is Erda Link rather than the HEXA Matrix */
    public static boolean usesErdaLink(int job) {
        return net.swordie.ms.constants.JobConstants.isSiaAstelle(job) && ErdaLinkData.hasTree(job);
    }

    private static ErdaLinkStone find(Char chr, int stoneId) {
        for (ErdaLinkStone s : chr.getErdaLinkStones()) {
            if (s.getStoneId() == stoneId) {
                return s;
            }
        }
        return null;
    }

    public static int getLevel(Char chr, int stoneId) {
        ErdaLinkStone s = find(chr, stoneId);
        return s == null ? 0 : s.getLevel();
    }

    /** @return the current board's active stones and their levels */
    public static Map<Stone, Integer> getActiveStones(Char chr) {
        Map<Stone, Integer> res = new LinkedHashMap<>();
        int job = chr.getJob();
        for (ErdaLinkStone s : chr.getErdaLinkStones()) {
            if (s.getLevel() > 0 && ErdaLinkData.isOnTree(job, s.getStoneId())) {
                res.put(ErdaLinkData.getStone(job, s.getStoneId()), s.getLevel());
            }
        }
        return res;
    }

    private static void setLevel(Char chr, Stone stone, int level) {
        ErdaLinkStone s = find(chr, stone.id);
        if (s == null) {
            s = new ErdaLinkStone(stone.id, level);
            chr.getErdaLinkStones().add(s);
        } else {
            s.setLevel(level);
        }
        ErdaLinkEffects.invalidate(chr);
        if (!stone.connectSkills.isEmpty()) {
            applySkills(chr, stone.connectSkills);
        }
    }

    /**
     * Gives the named skills the summed level of every active stone on the board that names them (the two half
     * stones of a skill boost make one skill), with the summed max level as master level.
     */
    private static void applySkills(Char chr, Collection<Integer> skillIds) {
        int job = chr.getJob();
        List<Skill> skills = new ArrayList<>();
        for (int skillId : new LinkedHashSet<>(skillIds)) {
            int level = 0;
            int max = 0;
            for (Stone stone : ErdaLinkData.getTreeStones(job)) {
                if (stone.connectSkills.contains(skillId) && !stone.locked) {
                    level += getLevel(chr, stone.id);
                    max += stone.maxLevel;
                }
            }
            Skill skill = SkillData.getSkillDeepCopyById(skillId);
            if (skill == null) {
                log.warn("Erda Link names skill " + skillId + ", which the data doesn't have");
                continue;
            }
            max = Math.max(1, max);
            skill.setCurrentLevel(Math.min(level, max));
            skill.setMasterLevel(max);
            skill.setMaxLevel(max);
            skills.add(skill);
        }
        if (!skills.isEmpty()) {
            chr.addSkills(skills);
        }
    }

    /** Re-gives every board skill its stones' level (after a job change back to Sia, or to repair) */
    public static void reapplySkills(Char chr) {
        Set<Integer> ids = new LinkedHashSet<>();
        for (Stone stone : ErdaLinkData.getTreeStones(chr.getJob())) {
            if (!stone.locked && getLevel(chr, stone.id) > 0) {
                ids.addAll(stone.connectSkills);
            }
        }
        if (!ids.isEmpty()) {
            applySkills(chr, ids);
        }
    }

    // ------------------------------------------------------------------------------------------------- unlock

    /**
     * The 6th job for an Erda Link class: its Origin stone, free, like the HEXA Matrix's Origin node. Called from
     * HexaModule.unlockSixthJob once the 6th job quest is done.
     */
    public static void unlock(Char chr, boolean first) {
        if (!hasErdaLink(chr)) {
            return;
        }
        boolean changed = false;
        for (Stone stone : ErdaLinkData.getTreeStones(chr.getJob())) {
            if (stone.category == ErdaLinkData.ORIGIN && !stone.locked && getLevel(chr, stone.id) == 0) {
                setLevel(chr, stone, 1);
                changed = true;
            }
        }
        if (changed) {
            chr.write(ErdaLinkPacket.info(chr));
            log.info("Erda Link Origin stone for " + chr.getName() + " (job " + chr.getJob() + ")");
        } else {
            reapplySkills(chr);
        }
        if (first) {
            chr.chatMessage(ChatType.SystemNotice, "You've reached the 6th Job! Open Erda Link to use Sol Erda and "
                    + "Sol Erda Fragments on your stones.");
        }
    }

    // ------------------------------------------------------------------------------------------------- costs

    private static long countItem(Char chr, int itemId) {
        return chr.getInventories().stream().flatMap(inv -> inv.getItems().stream())
                .filter(i -> i.getItemId() == itemId).mapToLong(Item::getQuantity).sum();
    }

    public static long getFragments(Char chr) {
        long total = 0;
        for (int id : ErdaLinkData.getFragmentItems()) {
            total += countItem(chr, id);
        }
        return total;
    }

    private static void takeFragments(Char chr, int amount) {
        int need = amount;
        for (int id : ErdaLinkData.getFragmentItems()) {
            int take = (int) Math.min(countItem(chr, id), need);
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
    private static boolean pay(Char chr, int solErda, int fragments, long meso) {
        int haveErda = HexaModule.getSolErda(chr);
        long haveFragments = getFragments(chr);
        if (solErda > haveErda || fragments > haveFragments || meso > chr.getMoney()) {
            chr.chatMessage(ChatType.SystemNotice, String.format("Not enough Sol Erda (%d/%d), Sol Erda Fragments "
                    + "(%d/%d) or mesos (%,d/%,d).", haveErda, solErda, haveFragments, fragments, chr.getMoney(), meso));
            return false;
        }
        takeFragments(chr, fragments);
        if (solErda > 0) {
            HexaModule.addSolErda(chr, -solErda);
        }
        if (meso > 0) {
            chr.deductMoney(meso);
        }
        return true;
    }

    // ------------------------------------------------------------------------------------------------- rules

    /** A node is open if its "and" nodes are all active and one of its "or" nodes is; a node with neither hangs off
     * the center (the Origin stone) */
    private static boolean prevActive(Char chr, Stone stone) {
        Map<Integer, Integer> tree = ErdaLinkData.getTree(chr.getJob());
        if (stone.prevAnd.length == 0 && stone.prevOr.length == 0) {
            return stone.category == ErdaLinkData.ORIGIN || hasCategory(chr, ErdaLinkData.ORIGIN, 1);
        }
        for (int idx : stone.prevAnd) {
            Integer id = tree.get(idx);
            if (id == null || getLevel(chr, id) <= 0) {
                return false;
            }
        }
        if (stone.prevOr.length == 0) {
            return true;
        }
        for (int idx : stone.prevOr) {
            Integer id = tree.get(idx);
            if (id != null && getLevel(chr, id) > 0) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasCategory(Char chr, int category, int count) {
        int n = 0;
        for (Stone stone : getActiveStones(chr).keySet()) {
            if (stone.category == category && ++n >= count) {
                return true;
            }
        }
        return count <= 0;
    }

    /**
     * The level a requirement skill counts as: its own level, or for a 5th job boost skill (one that raises other
     * skills, like Ray Boost) the highest active V Matrix boost node over its skills, since boost nodes are kept as
     * levels on the skills they raise here.
     */
    private static int getReqSkillLevel(Char chr, int skillId) {
        int level = chr.getSkillLevel(skillId);
        if (level > 0) {
            return level;
        }
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (si == null || si.getPsdSkills().isEmpty()) {
            return 0;
        }
        Set<Integer> targets = new HashSet<>(si.getPsdSkills());
        for (MatrixRecord mr : chr.getMatrixRecords()) {
            if (!mr.isActive() || !BoostNodeModule.isBoostNode(mr)) {
                continue;
            }
            for (int s : mr.getSkills()) {
                if (targets.contains(s)) {
                    MatrixSlot ms = chr.getMatrixSlotByPosition(mr.getPosition());
                    level = Math.max(level, mr.getSlv() + (ms == null ? 0 : ms.getEnhancementLv()));
                    break;
                }
            }
        }
        return level;
    }

    private static boolean meetsConditions(Char chr, Stone stone) {
        for (Map.Entry<Integer, Integer> req : stone.reqSkills.entrySet()) {
            if (getReqSkillLevel(chr, req.getKey()) < req.getValue()) {
                return false;
            }
        }
        if (stone.condLevel > 0 && chr.getLevel() < stone.condLevel) {
            return false;
        }
        if (stone.condStoneCategory >= 0 && !hasCategory(chr, stone.condStoneCategory, stone.condStoneCount)) {
            return false;
        }
        Map<Stone, Integer> active = getActiveStones(chr);
        if (stone.condStoneLevelCategory >= 0 && active.entrySet().stream().noneMatch(e ->
                e.getKey().category == stone.condStoneLevelCategory && e.getValue() >= stone.condStoneLevel)) {
            return false;
        }
        if (stone.condEndStones > 0
                && active.keySet().stream().filter(s -> s.rushEnd).count() < stone.condEndStones) {
            return false;
        }
        if (stone.condAll) {
            for (Stone other : ErdaLinkData.getTreeStones(chr.getJob())) {
                if (other != stone && !other.locked && other.category != ErdaLinkData.SHINE
                        && !active.containsKey(other)) {
                    return false;
                }
            }
        }
        return true;
    }

    // ------------------------------------------------------------------------------------------------- requests

    /** ERDA_LINK_REQUEST: type (0 activate, 1 enhance), int, stone, category, then the type's own fields */
    public static void handleRequest(Char chr, InPacket inPacket) {
        int type = inPacket.decodeInt();
        int field = inPacket.decodeInt();
        int stoneId = inPacket.decodeInt();
        int category = inPacket.decodeInt();
        if (field != 0 && !loggedUnknownField) {
            loggedUnknownField = true;
            log.info("Erda Link request " + type + " for stone " + stoneId + " sent " + field + " in its second int");
        }
        Stone stone = ErdaLinkData.getStone(chr.getJob(), stoneId);
        boolean valid = hasErdaLink(chr) && stone != null && stone.category == category
                && ErdaLinkData.isOnTree(chr.getJob(), stoneId);
        if (type == ErdaLinkPacket.ACTIVATE) {
            if ((category == ErdaLinkData.BOOST || category == ErdaLinkData.ULTIMATE)
                    && inPacket.getUnreadAmount() >= 16) {
                for (int i = 0; i < 4; i++) {
                    inPacket.decodeInt(); // the light module's rect on the board (client checks the stone is in it)
                }
            }
            if (!valid) {
                chr.write(ErdaLinkPacket.activateResult(ERR_INVALID, stoneId, category));
            } else {
                activate(chr, stone);
            }
        } else if (type == ErdaLinkPacket.ENHANCE) {
            int from = inPacket.decodeInt();
            int to = inPacket.decodeInt();
            if (!valid) {
                chr.write(ErdaLinkPacket.enhanceResult(ERR_INVALID, stoneId, category, from, from));
            } else {
                enhance(chr, stone, from, to);
            }
        }
        chr.dispose();
    }

    private static void activate(Char chr, Stone stone) {
        int code = ErdaLinkPacket.OK;
        if (stone.locked || getLevel(chr, stone.id) > 0) {
            code = ERR_INVALID;
        } else if (!prevActive(chr, stone)) {
            code = ERR_PREV;
        } else if (!meetsConditions(chr, stone)) {
            code = ERR_REQUIREMENTS;
        } else {
            Cost cost = ErdaLinkData.getActivationCost(stone);
            if (cost == null || !pay(chr, cost.solErda, cost.fragments, cost.meso)) {
                code = ERR_COST;
            }
        }
        if (code == ErdaLinkPacket.OK) {
            setLevel(chr, stone, 1);
            chr.write(ErdaLinkPacket.info(chr));
        }
        chr.write(ErdaLinkPacket.activateResult(code, stone.id, stone.category));
    }

    private static void enhance(Char chr, Stone stone, int from, int to) {
        int level = getLevel(chr, stone.id);
        boolean shine = stone.category == ErdaLinkData.SHINE;
        if (level <= 0 || from != level || to <= level) {
            chr.write(ErdaLinkPacket.enhanceResult(ERR_INVALID, stone.id, stone.category, level, level));
            return;
        }
        if (to > stone.maxLevel) {
            chr.write(ErdaLinkPacket.enhanceResult(ERR_MAX_LEVEL, stone.id, stone.category, level, level));
            return;
        }
        if (shine) {
            to = level + 1; // one try at a time
        }
        int solErda = 0;
        int fragments = 0;
        long meso = 0;
        for (int i = level; i < to; i++) {
            Cost cost = ErdaLinkData.getEnforcementCost(stone, i);
            if (cost == null) {
                chr.write(ErdaLinkPacket.enhanceResult(ERR_MAX_LEVEL, stone.id, stone.category, level, level));
                return;
            }
            solErda += cost.solErda;
            fragments += cost.fragments;
            meso += cost.meso;
        }
        if (!pay(chr, solErda, fragments, meso)) {
            chr.write(ErdaLinkPacket.enhanceResult(ERR_COST, stone.id, stone.category, level, level));
            return;
        }
        int code = ErdaLinkPacket.OK;
        int after = to;
        if (shine) {
            double[] prob = level < stone.enforceProb.length ? stone.enforceProb[level] : new double[]{100, 0, 0};
            double roll = Util.getRandom(0, 10000) / 100D;
            if (roll < prob[0]) {
                after = level + 1;
            } else if (roll < prob[0] + prob[1]) {
                code = ErdaLinkPacket.SHINE_FAIL;
                after = level;
            } else {
                code = ErdaLinkPacket.SHINE_DOWNGRADE;
                after = Math.max(1, level - 1);
            }
        }
        if (after != level) {
            setLevel(chr, stone, after);
            chr.write(ErdaLinkPacket.info(chr));
        }
        chr.write(ErdaLinkPacket.enhanceResult(code, stone.id, stone.category, level, after));
    }

    // ------------------------------------------------------------------------------------------------- encoding

    /**
     * The client's Erda Link state (decoder 0x1406525d0, also the CharacterData section after the HEXA ones):
     * int count, (int stone, int ?, int level)..., byte, int count, int...
     * The byte is set once a boost or ultimate stone (the ones placed under the light module) is active; the int
     * set is left empty (the server can clear it with result type 2, its meaning isn't known).
     * Reads in 0x1406525d0: Decode4 n; n x (Decode4 key, 0x1406523f0: Decode4, Decode4); Decode1; Decode4 m;
     * m x Decode4. So 9 + 12 n bytes with an empty set.
     */
    public static void encode(Char chr, OutPacket outPacket) {
        Map<Stone, Integer> active = Map.of();
        try {
            if (chr.getId() != 0 && usesErdaLink(chr.getJob())) {
                active = getActiveStones(chr);
            }
        } catch (Exception e) {
            log.error("Could not read the Erda Link stones of " + chr.getName() + "; sending none", e);
            active = Map.of();
        }
        if (active.isEmpty()) {
            // every other character (and Sia without stones): the same 9 zero bytes as before Erda Link
            outPacket.encodeInt(0);
            outPacket.encodeByte(0);
            outPacket.encodeInt(0);
            return;
        }
        outPacket.encodeInt(active.size());
        boolean lit = false;
        for (Map.Entry<Stone, Integer> e : active.entrySet()) {
            outPacket.encodeInt(e.getKey().id);
            // the data key the stone window looks the enforcement data up with; 0 leaves the Enhance Stone panel disabled
            outPacket.encodeInt(e.getKey().id);
            outPacket.encodeInt(e.getValue());
            lit |= e.getKey().category == ErdaLinkData.BOOST || e.getKey().category == ErdaLinkData.ULTIMATE;
        }
        outPacket.encodeByte(lit);
        outPacket.encodeInt(0);
    }
}
