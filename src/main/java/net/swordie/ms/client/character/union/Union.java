package net.swordie.ms.client.character.union;

import net.swordie.ms.client.Account;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.loaders.SkillData;
import net.swordie.orm.dao.SworDaoFactory;
import net.swordie.orm.dao.UnionBoardDao;

import java.util.*;
import java.util.stream.Collectors;

public class Union {

    private static final UnionBoardDao unionBoardDao = (UnionBoardDao) SworDaoFactory.getByClass(UnionBoard.class);
    // Reverse compare
    private static final Comparator<Char> chrHighestLevelComparator = (chr1, chr2) -> Short.compare(chr2.getLevel(), chr1.getLevel());

    public static final int MAX_PRESETS = 5;
    public static final int MAX_STATS = 8; // changeable
    public static final int MAX_CHARS = 42; // characters counted towards the Legion level
    public static final int DEFAULT_PRESETS = 2;
    public static final int DEFAULT_RANK = 101;
    public static final int MAX_GRID_SLOTS = 440;
    public static final int GRID_WIDTH = 22;
    public static final int GRID_STAT_BASE_SKILLID = 71004000;

    private Account account;

    private List<UnionBoard> unionBoards;

    private long id;
    private int unionCoin;
    private int unionRank;
    private int presets;
    private int activePreset;
    private Map<BaseStat, Integer> gridBaseStats = new HashMap<>(); // are multiplied by R vars
    private Map<BaseStat, Integer> memberBaseStats = new HashMap<>(); // are NOT multiplied by R vars

    public Union() {
    }

    public Union(int presets, int unionRank) {
        this.presets = presets;
        this.unionRank = unionRank;
        unionBoards = new ArrayList<>();
        for (int i = 0; i < presets; i++) {
            addPreset();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void addPreset() {
        if (getPresets() < MAX_PRESETS) {
            setPresets(getPresets() + 1);
            var ub = new UnionBoard(true);
            getUnionBoards().add(ub);
        }
    }

    public UnionBoard getBoardByPreset(int preset) {
        if (hasPresetUnlocked(preset)) {
            return getUnionBoards().get(preset);
        }
        return null;
    }

    public List<UnionBoard> getUnionBoards() {
        if (unionBoards == null) {
            unionBoards = unionBoardDao.byUnion(this);
        }
        return unionBoards;
    }

    public void setUnionBoards(List<UnionBoard> unionBoards) {
        this.unionBoards = unionBoards;
    }

    public int getUnionCoin() {
        return unionCoin;
    }

    public void setUnionCoin(int unionCoin) {
        this.unionCoin = unionCoin;

        //Always visually update when we set coin, this makes it also happen upon login
        if (account == null) { return; }
        Char chr = account.getCurrentChr();
        if (chr != null) {
            Quest quest = chr.getQuestManager().getOrCreateQuestById(QuestConstants.UNION_COIN);
            quest.setProperty("point", unionCoin);
            chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
            chr.write(WvsContext.message(MessagePacket.questRecordMessage(quest)));
        }
    }

    public void addUnionCoin(int amount) {
        if((long)getUnionCoin() + (long) amount > Integer.MAX_VALUE) {
            setUnionCoin(Integer.MAX_VALUE);
            return;
        }

        setUnionCoin(getUnionCoin() + amount);
    }

    public int getUnionRank() {
        return unionRank;
    }

    public void setUnionRank(int unionRank) {
        this.unionRank = unionRank;
    }

    public int getActivePreset() {
        return activePreset;
    }

    public void setActivePreset(int activePreset) {
        this.activePreset = activePreset;
    }

    public int getPresets() {
        return presets;
    }

    public void setPresets(int presets) {
        this.presets = presets;
    }

    public boolean hasPresetUnlocked(int preset) {
        return preset >= 0 && preset < getPresets() && preset < getUnionBoards().size();
    }

    private int getUnionRankIndex() {
        int high = getUnionRank() / 100;
        int low = getUnionRank() % 100;
        return (low - 1) + (high - 1) * 5;
    }

    private boolean isMaxUnionRank() {
        return getUnionRank() == 505; // Supreme V
    }

    private static int clampRankIndex(int idx) {
        return Math.max(0, Math.min(idx, UnionMember.ranks.length - 1));
    }

    public String getUnionRankName() {
        return UnionMember.ranks[clampRankIndex(getUnionRankIndex())];
    }

    public String getUnionNextRankName() {
        return UnionMember.ranks[clampRankIndex(isMaxUnionRank() ? getUnionRankIndex() : getUnionRankIndex() + 1)];
    }

    public boolean isMaxRank() {
        return isMaxUnionRank();
    }

    /**
     * @return the Legion level needed to rank up from the current rank (0 at the highest rank)
     */
    public int getRankUpLevelReq() {
        int idx = getUnionRankIndex();
        return isMaxUnionRank() || idx < 0 || idx >= UnionMember.reqLev.length ? 0 : UnionMember.reqLev[idx];
    }

    /**
     * @return the Legion Coins needed to rank up from the current rank (0 at the highest rank)
     */
    public int getRankUpCoinReq() {
        int idx = getUnionRankIndex();
        return isMaxUnionRank() || idx < 0 || idx >= UnionMember.reqCoin.length ? 0 : UnionMember.reqCoin[idx];
    }

    public long getUnionPower() {
        return getUnionPower(null);
    }

    public long getUnionPower(Char chr) { // call with null to get full union power
        UnionBoard activeBoard = getBoardByPreset(getActivePreset());
        return activeBoard == null ? 0 : activeBoard.calculateTotalUnionPower(chr);
    }

    /**
     * The Legion's characters, as the Legion window lists them: the ones placed on the active board first, then the
     * other eligible characters, highest level first. Like on live servers every eligible character is listed (only
     * the 42 highest level ones count towards the Legion level) and only one Zero (the highest level one) is.
     */
    public List<Char> getEligibleUnionChars() {
        var currentActiveUnionChars = new HashSet<Integer>();
        var active = getActiveUnionChars(getActivePreset());
        if (active != null) {
            for (UnionMember um : active) {
                currentActiveUnionChars.add(um.getCharId());
            }
        }
        var sorted = account.getCharacters().stream()
                .filter(this::isEligibleUnionChar)
                .sorted(chrHighestLevelComparator)
                .collect(Collectors.toList());
        var counted = new ArrayList<Char>();
        boolean hasZero = false;
        for (Char chr : sorted) {
            if (JobConstants.isZero(chr.getJob())) {
                if (hasZero) {
                    continue;
                }
                hasZero = true;
            }
            counted.add(chr);
        }
        // chars that are already placed first (the window expects them first), then the rest
        var eligibleChars = new ArrayList<Char>();
        for (Char chr : counted) {
            if (currentActiveUnionChars.contains(chr.getId())) {
                eligibleChars.add(chr);
            }
        }
        for (Char chr : counted) {
            if (!currentActiveUnionChars.contains(chr.getId())) {
                eligibleChars.add(chr);
            }
        }
        return eligibleChars;
    }

    /**
     * @return the Legion level (Cumulative Level): the levels of the {@link #MAX_CHARS} highest level characters added up
     */
    public int getUnionLevel() {
        int total = 0;
        var byLevel = new ArrayList<>(getEligibleUnionChars());
        byLevel.sort(chrHighestLevelComparator);
        for (Char chr : byLevel.subList(0, Math.min(MAX_CHARS, byLevel.size()))) {
            total += chr.getLevel();
        }
        return total;
    }

    /**
     * Lv. 60+ (Zero: Lv. 130+) characters that have made their 2nd job advancement, like on live servers.
     */
    public boolean isEligibleUnionChar(Char chr) {
        return (JobConstants.isZero(chr.getJob()) ? chr.getLevel() >= 130 : chr.getLevel() >= 60) // Level Requirement
                && JobConstants.getJobLevel(chr.getJob()) >= 2 // 2nd Job Advancement Requirement
                && !JobConstants.isBeginnerJob(chr.getJob());
    }

    /**
     * @return how many characters the current Legion rank can place on the board (Etc.wz mapleUnion/unionRank)
     */
    public int getAttackerCount() {
        int idx = getUnionRankIndex();
        if (idx < 0) {
            return UnionMember.attackerCount[0];
        }
        return UnionMember.attackerCount[Math.min(idx, UnionMember.attackerCount.length - 1)];
    }

    public Set<UnionMember> getActiveUnionChars(int preset) {
        // Get eligible characters that have a grid position of >= 0.
        if (hasPresetUnlocked(preset)) {
            return getBoardByPreset(preset).getActiveMembers();
        }
        return null;
    }

    public void setCharPosForPreset(int preset, Char chr, int rotation, int grid) {
        UnionBoard ub = getBoardByPreset(preset);
        if (ub != null) {
            ub.setCharGridPos(chr, rotation, grid, getAttackerCount());
        }
    }

    /**
     * @return whether the given character is placed on the active board
     */
    public boolean isActiveMember(int charId) {
        var active = getActiveUnionChars(getActivePreset());
        return active != null && active.stream().anyMatch(um -> um.getCharId() == charId);
    }

    public void updateUnionBonuses(Char chr) {
        UnionBoard ub = getBoardByPreset(chr.getActiveUnionPreset());

        // Remove Union Bonuses
        removeUnionBonuses();
        if (ub == null) {
            return;
        }

        // the board's members are loaded from the database, so the character being played is a copy there; use the
        // live one so its level (block size, member rank) is current
        for (UnionBoard board : getUnionBoards()) {
            for (UnionMember um : board.getActiveMembers()) {
                if (um.getCharId() == chr.getId()) {
                    um.setChr(chr);
                }
            }
        }

        // Get Union Member Bonuses
        Set<Skill> unionMemberSkills = ub.getAllMemberBonusSkills();

        // Get Union Grid Bonuses
        List<Skill> unionGridSkills = ub.getAllGridBonusSkills(getUnionRank());


        // Apply Union Member BaseStats
        for (Skill skill : unionMemberSkills) {
            getBaseStatsBySkill(chr, skill).forEach(this::putMemberBaseStat);
        }

        // Apply Union Grid BaseStats
        for (Skill skill : unionGridSkills) {
            getBaseStatsBySkill(chr, skill).forEach(this::putGridBaseStat);
        }
    }

    private void removeUnionBonuses() {
        getMemberBaseStats().clear();
        getGridBaseStats().clear();
    }

    private Map<BaseStat, Integer> getBaseStatsBySkill(Char chr, Skill skill) {
        if (skill != null && skill.getCurrentLevel() > 0) {
            SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
            // Legion skills (7100xxxx: member bonuses and grid stats) are always passive, whether or not the WZ marks
            // them so
            boolean legionSkill = skill.getSkillId() / 10000 == 7100;
            if (si != null && (legionSkill || SkillConstants.isPassiveSkill(skill.getSkillId()))) {
                int slv = skill.getCurrentLevel();
                if (si.getMaxLevel() > 0) {
                    slv = Math.min(slv, si.getMaxLevel());
                }
                return si.getBaseStatValues(chr, slv);
            }
        }
        return new HashMap<>();
    }

    public static int getGridStatBySynergyType(SynergyGridType synergyGridType) {
        return GRID_STAT_BASE_SKILLID + synergyGridType.getVal();
    }

    public Map<BaseStat, Integer> getGridBaseStats() {
        return gridBaseStats;
    }

    public void putGridBaseStat(BaseStat bs, Integer val) {
        if (getGridBaseStats().containsKey(bs)) {
            getGridBaseStats().put(bs, getGridBaseStat(bs) + val);
        } else {
            getGridBaseStats().put(bs, val);
        }
    }

    public int getGridBaseStat(BaseStat bs) {
        return getGridBaseStats().getOrDefault(bs, 0);
    }


    public Map<BaseStat, Integer> getMemberBaseStats() {
        return memberBaseStats;
    }

    public void putMemberBaseStat(BaseStat bs, Integer val) {
        if (getMemberBaseStats().containsKey(bs)) {
            getMemberBaseStats().put(bs, getMemberBaseStat(bs) + val);
        } else {
            getMemberBaseStats().put(bs, val);
        }
    }

    public int getMemberBaseStat(BaseStat bs) {
        return getMemberBaseStats().getOrDefault(bs, 0);
    }
}
