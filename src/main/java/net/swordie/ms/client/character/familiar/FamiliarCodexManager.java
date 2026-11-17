package net.swordie.ms.client.character.familiar;

import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.ItemOption;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.connection.packet.CFamiliar;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.enums.EnterType;
import net.swordie.ms.life.Familiar;
import net.swordie.ms.loaders.FamiliarData;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.containerclasses.familiarcodex.BadgeInfo;
import net.swordie.ms.world.field.Field;
import net.swordie.orm.dao.FamiliarDao;
import net.swordie.orm.dao.SworDaoFactory;

import java.util.*;

/**
 * Created on 25/04/2020.
 *
 * @author Asura
 */
public class FamiliarCodexManager {

    private static final FamiliarDao familiarDao = (FamiliarDao) SworDaoFactory.getByClass(Familiar.class);

    private static final int BADGE_REQ_SLOT1 = 1;
    private static final int BADGE_REQ_SLOT2 = 8;

    private int id;
    private Char chr;
    private int summonGauge = GameConstants.MAX_SUMMON_GAUGE; // 2000
    private int familiarSlots = GameConstants.STARTING_FAMILIAR_SLOTS; // 100
    private Set<Familiar> familiars;
    private int[] activeFamiliars = new int[GameConstants.ACTIVE_FAMILIARS_SLOTS];
    private int[] activeBadges = new int[] { // Default
            -1, -1, -1, -1,
              -1, -1, -1, -1,
    };
    private Set<Integer> collectedFamiliars;
    private boolean familiarsSummoned;
    private List<Familiar> currentActiveFamiliars;


    public FamiliarCodexManager() {
    }

    public FamiliarCodexManager(Char chr) {
        this.chr = chr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Char getChr() {
        return chr;
    }

    public void setChr(Char chr) {
        this.chr = chr;
    }

    public int[] getActiveFamiliars() {
        return activeFamiliars;
    }

    public void setActiveFamiliars(int[] activeFamiliars) {
        this.activeFamiliars = activeFamiliars;
    }

    public Set<Familiar> getFamiliars() {
        if (familiars == null) {
            familiars = familiarDao.byChar(getChr());
        }
        for (Familiar f : familiars) {
            f.setChr(getChr());
        }
        return familiars;
    }

    public void setFamiliars(Set<Familiar> familiars) {
        this.familiars = familiars;
    }

    public Familiar getFamiliarByID(int familiarID) {
        return getFamiliars().stream().filter(f -> f.getId() == familiarID).findAny().orElse(null);
    }

    public Familiar getFamiliarByTemplateId(int templateId) {
        return getFamiliars().stream().filter(f -> f.getFamiliarID() == templateId).findFirst().orElse(null);
    }

    public void addFamiliar(Familiar familiar) {
        getFamiliars().add(familiar);
        getCollectedFamiliars().add(familiar.getFamiliarID());
    }

    public void removeFamiliar(Familiar familiar) {
        if (familiar != null) {
            getFamiliars().remove(familiar);
            familiarDao.delete(familiar);
        }
    }

    public void removeFamiliars(List<Familiar> familiars) {
        chr.write(UserLocal.familiarRemoveResult(familiars));
        for (Familiar f : familiars) {
            removeFamiliar(f);
        }
    }

    public int getSummonGauge() {
        return summonGauge;
    }

    public void setSummonGauge(int summonGauge) {
        this.summonGauge = summonGauge;
    }

    public int[] getActiveBadges() {
        return activeBadges;
    }

    public void setActiveBadges(int[] activeBadges) {
        this.activeBadges = activeBadges;
    }

    public int getFamPoints() {
        QuestManager qm = chr.getQuestManager();
        Quest quest = qm.getOrCreateQuestById(QuestConstants.FAMILIAR_POINTS);

        return quest.getIntProperty("point");
    }

    public void setFamPoints(int famPoints) {
        QuestManager qm = chr.getQuestManager();
        Quest quest = qm.getOrCreateQuestById(QuestConstants.FAMILIAR_POINTS);

        quest.setProperty("point", famPoints);
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
    }

    public void addFamPoints(int famPoints) {
        setFamPoints(getFamPoints() + famPoints);
    }

    public void deductFamPoints(int famPoints) {
        addFamPoints(-famPoints);
    }

    public int getFamiliarSlots() {
        return familiarSlots;
    }

    public void setFamiliarSlots(int familiarSlots) {
        this.familiarSlots = Math.min(familiarSlots, GameConstants.MAX_FAMILIAR_SLOTS);
    }

    public void incFamiliarSlots(int inc) {
        setFamiliarSlots(getFamiliarSlots() + inc);
    }

    public boolean isFamiliarsSummoned() {
        return familiarsSummoned;
    }

    public void setFamiliarsSummoned(boolean familiarsSummoned) {
        this.familiarsSummoned = familiarsSummoned;
    }

    public List<Familiar> getActiveFamiliarsToSummon() {
        List<Familiar> fams = new ArrayList<>();
        for (int famId : getActiveFamiliars()) {
            if (famId == 0) {
                continue;
            }
            Familiar f = getFamiliarByID(famId);
            if (f != null) {
                f.setIdx(fams.size());
                fams.add(f);
            }
        }
        setCurrentActiveFamiliars(fams);
        return fams;
    }

    public void summonFamiliars() {
        summonFamiliars(EnterType.Animation);
    }

    public void summonFamiliars(EnterType enterType) {
        Field field = getChr().getField();
        List<Familiar> fams = getActiveFamiliarsToSummon();
        if (fams.size() > 0) {
            for (Familiar f : fams) {
                field.spawnFamiliar(f, null, enterType);
            }
            setFamiliarsSummoned(true);
            field.broadcastPacket(CFamiliar.familiarEnterField(fams, true, false));
            startGaugeTopUp();
        }
    }

    private transient java.util.concurrent.ScheduledFuture<?> gaugeTopUp;

    /**
     * The client drains the Summon Gauge while familiars are out and recalls them when it's empty, so the server keeps
     * it topped up every 30 seconds while they're summoned.
     */
    private synchronized void startGaugeTopUp() {
        if (gaugeTopUp != null && !gaugeTopUp.isDone()) {
            return;
        }
        gaugeTopUp = net.swordie.ms.handlers.executors.EventManager.addFixedRateEvent(() -> {
            try {
                if (!isFamiliarsSummoned() || chr == null || chr.getClient() == null || chr.getField() == null) {
                    stopGaugeTopUp();
                    return;
                }
                setSummonGauge(GameConstants.MAX_SUMMON_GAUGE);
                updateForClient(FamiliarCodexUpdateMask.SummonGauge.getVal());
            } catch (Exception e) {
                stopGaugeTopUp();
            }
        }, 30_000, 30_000);
    }

    private synchronized void stopGaugeTopUp() {
        if (gaugeTopUp != null) {
            gaugeTopUp.cancel(false);
            gaugeTopUp = null;
        }
    }

    public void banishFamiliars() {
        Field field = getChr().getField();
        List<Familiar> fams = new ArrayList<>();
        for (Familiar f : getActiveFamiliarsToSummon()) {
            field.removeFamiliar(f);
            fams.add(f);
        }
        setFamiliarsSummoned(false);
        setCurrentActiveFamiliars(null);
        stopGaugeTopUp();
        field.broadcastPacket(CFamiliar.familiarEnterField(fams, false, false));
    }

    public Set<Integer> getCollectedFamiliars() {
        if (collectedFamiliars == null) {
            collectedFamiliars = familiarDao.getCollectedFamiliars(chr);
        }

        return collectedFamiliars;
    }

    public void addCollectedFamiliar(int familiarId) {
        getCollectedFamiliars().add(familiarId);
    }

    public Map<Integer, FamiliarBadgeInfo> getBadgePageInfo() {
        Map<Integer, FamiliarBadgeInfo> badgeInfoMap = new HashMap<>();

        for (int familiarId : getCollectedFamiliars()) {
            var familiarInfo = FamiliarData.getFamiliarInfoById(familiarId);

            if (familiarInfo == null || familiarInfo.getBadgeSetIds().size() == 0) {
                continue;
            }

            for (int badgeId : familiarInfo.getBadgeSetIds()) {
                var badgeInfo = FamiliarData.getBadgeInfoById(badgeId);

                var set = badgeId / 4;
                if (!badgeInfoMap.containsKey(set)) {
                    badgeInfoMap.put(set, new FamiliarBadgeInfo());
                }

                var familiarBadgeInfo = badgeInfoMap.get(set);
                familiarBadgeInfo.addFamiliar(familiarId, badgeId, badgeInfo.getPositionOfFamiliar(familiarId));
            }
        }

        return badgeInfoMap;
    }

    public double getBaseStat(BaseStat baseStat) {
        double stat = 0;
        if (baseStat == null) {
            return stat;
        }

        // Familiar
        if (isFamiliarsSummoned()) {
            for (Familiar f : getActiveFamiliarsToSummon()) {
                stat += f.getBaseStat(baseStat);
            }
        }

        // Badge
        for (int badgeId : getActiveBadges()) {
            BadgeInfo bi = FamiliarData.getBadgeInfoById(badgeId);
            if (bi == null) {
                continue;
            }

            for (int option : bi.getOptions()) {
                ItemOption io = ItemData.getFamiliarOptionById(option);
                if (io != null) {
                    Map<BaseStat, Double> valMap = io.getStatValuesByLevel(1);
                    stat += valMap.getOrDefault(baseStat, 0D);
                }
            }
        }

        return stat;
    }

    /**
     * Registers a familiar card straight into the codex, as using it would.
     * @return whether it was registered (false: not a familiar card, or no room - the card stays an item)
     */
    public boolean registerCard(int itemId, int quantity) {
        if (!net.swordie.ms.constants.ItemConstants.isFamiliar(itemId)) {
            return false;
        }
        var info = net.swordie.ms.loaders.ItemData.getItemInfoByID(itemId);
        if (info == null) {
            return false;
        }
        int familiarId = info.getFamiliarId();
        if (net.swordie.ms.constants.ItemConstants.isFauxmiliarCard(itemId)) {
            familiarId = itemId == ItemConstants.SUSPICIOUS_FAUXMILIAR_FAMILIAR ? 9963000 : 9963001; // hardcode-ok
        }
        if (familiarId == 0) {
            return false;
        }
        int room = getFamiliarSlots() - getFamiliars().size();
        if (room < quantity) {
            return false;
        }
        List<Familiar> added = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            Familiar f = new Familiar(familiarId, "", chr, info.getGrade());
            chr.addFamiliar(f);
            added.add(f);
        }
        // GMS: the codex record (familiar count) comes before the new familiars
        updateForClient(FamiliarCodexUpdateMask.FamiliarCount.getVal());
        chr.write(UserLocal.familiarAddResult(added));
        chr.write(UserLocal.familiarBadgeAddResult(chr));
        return true;
    }

    /**
     * Extracts every unlocked familiar the codex already has a better copy of (by grade, then level, then attack) into
     * Familiar Points. Summoned and locked familiars are kept.
     * @return {familiars extracted, points gained}
     */
    public int[] extractDuplicates() {
        java.util.Map<Integer, Familiar> best = new java.util.HashMap<>();
        java.util.Comparator<Familiar> rank = java.util.Comparator.comparingInt(Familiar::getGrade)
                .thenComparingInt(Familiar::getLevel).thenComparingInt(f -> (int) f.getAttack());
        for (Familiar f : getFamiliars()) {
            best.merge(f.getFamiliarID(), f, (a, b) -> rank.compare(a, b) >= 0 ? a : b);
        }
        java.util.Set<Familiar> active = new java.util.HashSet<>(getActiveFamiliarsToSummon());
        List<Familiar> out = new ArrayList<>();
        int points = 0;
        for (Familiar f : getFamiliars()) {
            if (f != best.get(f.getFamiliarID()) && !f.isLocked() && !active.contains(f)) {
                out.add(f);
                points += f.getFamPointsValue();
            }
        }
        if (!out.isEmpty()) {
            removeFamiliars(out);
            addFamPoints(points);
        }
        return new int[]{out.size(), points};
    }

    public void initQuestsForSlots() {
        var qm = chr.getQuestManager();
        // all three active familiar slots are open from the start instead of via badges (BADGE_REQ_SLOT1/2)
        if (!qm.hasQuestInProgress(QuestConstants.FAMILIAR_SLOT1)) {
            qm.addQuestWithQr(chr, QuestConstants.FAMILIAR_SLOT1, "famimax=1");
        }
        if (!qm.hasQuestCompleted(QuestConstants.FAMILIAR_SLOT2)) {
            qm.completeQuest(QuestConstants.FAMILIAR_SLOT2);
        }
    }

    /**
     * GMS gives the (hidden, fixLevel 1) "Summon Familiars" Beginner skill with the first familiar. Without it the
     * client's summon button refuses and opens the skill window at that id, which then looks empty.
     */
    public void ensureSummonSkill() {
        if (getFamiliars().isEmpty() || chr.getSkillLevel(GameConstants.SUMMON_FAMILIARS_SKILL) > 0) {
            return;
        }
        chr.addSkill(GameConstants.SUMMON_FAMILIARS_SKILL, 1, 1);
    }

    public void updateForClient(int mask) {
        chr.write(UserLocal.codexUpdate(this, mask));
    }

    /** Every familiar on the badge's list is collected. */
    public boolean isBadgeComplete(int badgeId) {
        BadgeInfo bi = FamiliarData.getBadgeInfoById(badgeId);
        if (bi == null || bi.getFamList().isEmpty()) {
            return false;
        }
        return getCollectedFamiliars().containsAll(bi.getFamList());
    }

    /** Equippable badges only: unknown, incomplete or repeated ones become empty slots (-1). */
    public int[] filterBadges(int[] badges) {
        int[] res = new int[GameConstants.BADGE_SLOTS];
        Set<Integer> seen = new HashSet<>();
        for (int i = 0; i < res.length; i++) {
            int b = badges != null && i < badges.length ? badges[i] : -1;
            res[i] = b >= 0 && isBadgeComplete(b) && seen.add(b) ? b : -1;
        }
        return res;
    }

    /**
     * After the character data: the codex record (the character data's copy is zeros, so the client had no slots
     * or gauge), every preset, and potential lines rolled from the old table rerolled from the v265 one.
     */
    public void onLogin() {
        if (chr == null) {
            return;
        }
        for (Familiar f : getFamiliars()) {
            getCollectedFamiliars().add(f.getFamiliarID());
        }
        List<Familiar> rerolled = FamiliarPresets.migrateLines(chr, getFamiliars());
        if (!rerolled.isEmpty()) {
            chr.write(UserLocal.familiarAddResult(rerolled,
                    FamiliarModifiedMask.PotLines0.getVal() | FamiliarModifiedMask.PotLines1.getVal()));
        }
        // v271: CharacterData F85 already carries the record and every preset
        chr.write(UserLocal.codexUpdate(this, FamiliarCodexUpdateMask.All.getVal()));
    }

    public Familiar getFamiliarByIdx(byte idx) {
        return getCurrentActiveFamiliars().get(idx);
    }

    public List<Familiar> getCurrentActiveFamiliars() {
        return currentActiveFamiliars;
    }

    public void setCurrentActiveFamiliars(List<Familiar> currentActiveFamiliars) {
        this.currentActiveFamiliars = currentActiveFamiliars;
    }

    public int getActiveFamiliarPreset() {
        return chr == null ? 0 : FamiliarPresets.getFamiliarPreset(chr);
    }

    public int getActiveBadgePreset() {
        return chr == null ? 0 : FamiliarPresets.getBadgePreset(chr);
    }
}
