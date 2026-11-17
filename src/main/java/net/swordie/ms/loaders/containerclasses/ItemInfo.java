package net.swordie.ms.loaders.containerclasses;

import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.enums.InvType;
import net.swordie.ms.enums.ScrollStat;
import net.swordie.ms.enums.SpecStat;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.util.container.Tuple;

import java.util.*;

/**
 * Created on 1/9/2018.
 */
public class ItemInfo {
    private int itemId;
    private InvType invType;
    private boolean cash;
    private int price;
    private int slotMax = 200;
    private boolean tradeBlock;
    private boolean notSale;
    private String path = "";
    private boolean noCursed;
    private Map<ScrollStat, Integer> scrollStats = new HashMap<>();
    private Map<SpecStat, Integer> specStats = new HashMap<>();
    private Map<BaseStat, Double> optionStats = new HashMap<>();
    private List<Tuple<Integer, Integer>> familiarCons = new ArrayList<>();
    private String nebString = "";
    private int bagType;
    private int charmEXP;
    private boolean quest;
    private int reqQuestOnProgress;
    private int senseEXP;
    private Set<Integer> questIDs = new HashSet<>();
    private int mobID;
    private int npcID;
    private int linkedID;
    private boolean monsterBook;
    private boolean notConsume;
    private String script = "";
    private int scriptNPC;
    private int life;
    private int masterLv;
    private int reqSkillLv;
    private Set<Integer> skills = new HashSet<>();
    private Set<Integer> reqs = new HashSet<>();
    private int moveTo;
    private Set<ItemRewardInfo> itemRewardInfos = new HashSet<>();
    private int skillId;
    private int grade;
    private int android;
    private boolean accountSharable;
    private int delta;
    private GroupChairInfo groupChairInfo;
    private CustomChairInfo customChairInfo;
    private int familiarId;

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setInvType(InvType invType) {
        this.invType = invType;
    }

    public InvType getInvType() {
        return invType;
    }

    public void setCash(boolean cash) {
        this.cash = cash;
    }

    public boolean isCash() {
        return cash;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setSlotMax(int slotMax) {
        this.slotMax = slotMax;
    }

    public int getSlotMax() {
        if (ItemConstants.isGolluxTeleportRock(itemId)) {
            return ItemConstants.GOLLUX_TELEPORT_ROCK_SLOT_MAX;
        }
        if (itemId == ItemConstants.SPELL_TRACE_ID && GameConstants.SPELL_TRACE_SLOT_MAX > 0) {
            return GameConstants.SPELL_TRACE_SLOT_MAX;
        }
        if (GameConstants.SLOT_MAX_MULTIPLIER > 1 && slotMax > 1 && invType != null && invType != InvType.EQUIP
                && invType != InvType.CASH && !ItemConstants.isRechargable(itemId)) {
            return (int) Math.min(Short.MAX_VALUE, (long) slotMax * GameConstants.SLOT_MAX_MULTIPLIER);
        }
        return slotMax;
    }

    public void setTradeBlock(boolean tradeBlock) {
        this.tradeBlock = tradeBlock;
    }

    public boolean isTradeBlock() {
        return tradeBlock;
    }

    public void setNotSale(boolean notSale) {
        this.notSale = notSale;
    }

    public boolean isNotSale() {
        return notSale;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setNoCursed(boolean noCursed) {
        this.noCursed = noCursed;
    }

    public boolean isNoCursed() {
        return noCursed;
    }

    public Map<ScrollStat, Integer> getScrollStats() {
        return scrollStats;
    }

    public void setScrollStats(Map<ScrollStat, Integer> scrollStats) {
        this.scrollStats = scrollStats;
    }

    public void putScrollStat(ScrollStat scrollStat, int val) {
        getScrollStats().put(scrollStat, val);
    }

    public Map<BaseStat, Double> getOptionStats() {
        return optionStats;
    }

    public void setOptionStats(Map<BaseStat, Double> optionStats) {
        this.optionStats = optionStats;
    }

    public void addOptionStat(BaseStat baseStat, double value) {
        getOptionStats().put(baseStat, value);
    }

    public String getNebString() {
        return nebString;
    }

    public void setNebString(String nebString) {
        this.nebString = nebString;
    }

    public void setBagType(int bagType) {
        this.bagType = bagType;
    }

    public int getBagType() {
        return bagType;
    }

    public void setCharmEXP(int charmEXP) {
        this.charmEXP = charmEXP;
    }

    public int getCharmEXP() {
        return charmEXP;
    }

    public void setQuest(boolean quest) {
        this.quest = quest;
    }

    public boolean isQuest() {
        return quest;
    }

    public void setReqQuestOnProgress(int reqQuestOnProgress) {
        this.reqQuestOnProgress = reqQuestOnProgress;
    }

    public int getReqQuestOnProgress() {
        return reqQuestOnProgress;
    }

    public void setSenseEXP(int senseEXP) {
        this.senseEXP = senseEXP;
    }

    public int getSenseEXP() {
        return senseEXP;
    }

    public void addQuest(int questID) {
        getQuestIDs().add(questID);
    }

    public Set<Integer> getQuestIDs() {
        return questIDs;
    }

    public void setMobID(int mobID) {
        this.mobID = mobID;
    }

    public int getMobID() {
        return mobID;
    }

    public void setNpcID(int npcID) {
        this.npcID = npcID;
    }

    public int getNpcID() {
        return npcID;
    }

    public void setLinkedID(int linkedID) {
        this.linkedID = linkedID;
    }

    public int getLinkedID() {
        return linkedID;
    }

    public void setMonsterBook(boolean monsterBook) {
        this.monsterBook = monsterBook;
    }

    public boolean isMonsterBook() {
        return monsterBook;
    }

    public void setNotConsume(boolean notConsume) {
        this.notConsume = notConsume;
    }

    public boolean isNotConsume() {
        return notConsume;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getScript() {
        return script;
    }

    public void putSpecStat(SpecStat ss, int i) {
        getSpecStats().put(ss, i);
    }

    public Map<SpecStat, Integer> getSpecStats() {
        return specStats;
    }

    public void setSpecStats(Map<SpecStat, Integer> specStats) {
        this.specStats = specStats;
    }

    public List<Tuple<Integer, Integer>> getFamiliarCons() {
        return familiarCons;
    }

    public void setFamiliarCons(List<Tuple<Integer, Integer>> familiarCons) {
        this.familiarCons = familiarCons;
    }

    public void addFamiliarCon(int sMap, int eMap) {
        Tuple<Integer, Integer> t = new Tuple<>(sMap, eMap);
        getFamiliarCons().add(t);
    }

    public void setScriptNPC(int scriptNPC) {
        this.scriptNPC = scriptNPC;
    }

    public int getScriptNPC() {
        return scriptNPC;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getLife() {
        return life;
    }

    public int getMasterLv() {
        return masterLv;
    }

    public void setMasterLv(int masterLv) {
        this.masterLv = masterLv;
    }

    public int getReqSkillLv() {
        return reqSkillLv;
    }

    public void setReqSkillLv(int reqSkillLv) {
        this.reqSkillLv = reqSkillLv;
    }

    public Set<Integer> getSkills() {
        return skills;
    }

    public void setSkills(Set<Integer> skills) {
        this.skills = skills;
    }

    public void addSkill(int skill) {skills.add(skill); }

    public int getMoveTo() {
        return moveTo;
    }

    public void setMoveTo(int moveTo) {
        this.moveTo = moveTo;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public void addItemReward(ItemRewardInfo iri) {
        getItemRewardInfos().add(iri);
    }

    public Set<ItemRewardInfo> getItemRewardInfos() {
        return itemRewardInfos;
    }

    /**
     * Rolls a reward of this box (the chances are weights).
     *
     * @return the reward entry: an item, or mesos when its item id is 0 (the amount is its count); null if none
     */
    public ItemRewardInfo getRandomRewardInfo() {
        List<ItemRewardInfo> iris = new ArrayList<>(getItemRewardInfos());
        iris.sort(Comparator.comparingDouble(ItemRewardInfo::getProb));
        Collections.reverse(iris);
        double total = iris.stream().mapToDouble(ItemRewardInfo::getProb).sum();
        if (total <= 0) {
            return null;
        }
        double rand = new Random().nextDouble() * total;
        for (ItemRewardInfo iri : iris) {
            if (rand < iri.getProb()) {
                return iri;
            }
            rand -= iri.getProb();
        }
        return null;
    }

    public Item getRandomReward() {
        List<ItemRewardInfo> iris = new ArrayList<>(getItemRewardInfos());
        iris.sort(Comparator.comparingDouble(ItemRewardInfo::getProb));
        Collections.reverse(iris);
        // the chances are weights: roll within their total (some boxes add up to more or less than 100)
        double total = iris.stream().mapToDouble(ItemRewardInfo::getProb).sum();
        if (total <= 0) {
            return null;
        }
        double rand = new Random().nextDouble() * total;
        for (ItemRewardInfo iri : iris) {
            if (rand < iri.getProb()) {
                Item item = ItemData.getItemDeepCopy(iri.getItemID());
                if (item == null) {
                    return null; // meso prize or an item missing from the data
                }
                item.setQuantity(Math.max(1, iri.getCount()));
                return item;
            }
            rand -= iri.getProb();
        }
        return null;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setAndroid(int android) {
        this.android = android;
    }

    public int getAndroid() {
        return android;
    }

    public boolean isAccountSharable() {
        return accountSharable;
    }

    public void setAccountSharable(boolean accountSharable) {
        this.accountSharable = accountSharable;
    }

    public int getDelta() {
        return delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public GroupChairInfo getGroupChairInfo() {
        return groupChairInfo;
    }

    public void setGroupChairInfo(GroupChairInfo groupChairInfo) {
        this.groupChairInfo = groupChairInfo;
    }

    public void addReq(int req) {
        getReqs().add(req);
    }

    public Set<Integer> getReqs() {
        return reqs;
    }

    public boolean canBeUsedOnItem(Item item) {
        return canBeUsedOnItem(item.getItemId());
    }

    public boolean canBeUsedOnItem(int itemId) {
        return getReqs().size() == 0 || getReqs().contains(itemId);
    }

    public void setFamiliarId(int familiarId) {
        this.familiarId = familiarId;
    }

    public int getFamiliarId() {
        return familiarId;
    }

    public CustomChairInfo getCustomChairInfo() {
        return customChairInfo;
    }

    public void setCustomChairInfo(CustomChairInfo customChairInfo) {
        this.customChairInfo = customChairInfo;
    }

    // Item use data (Item.wz): cosmetic coupons (spec/cosmetic: the hair or face), EXP items (spec/exp or expinc,
    // usable up to info/maxLevel) and the charm EXP of using a coupon (info/incCharmExp)
    private int cosmeticLook;
    private long useExp;
    private int useMaxLevel;
    private int useCharmExp;

    public int getCosmeticLook() {
        return cosmeticLook;
    }

    public void setCosmeticLook(int cosmeticLook) {
        this.cosmeticLook = cosmeticLook;
    }

    public long getUseExp() {
        return useExp;
    }

    public void setUseExp(long useExp) {
        this.useExp = useExp;
    }

    public int getUseMaxLevel() {
        return useMaxLevel;
    }

    public void setUseMaxLevel(int useMaxLevel) {
        this.useMaxLevel = useMaxLevel;
    }

    public int getUseCharmExp() {
        return useCharmExp;
    }

    public void setUseCharmExp(int useCharmExp) {
        this.useCharmExp = useCharmExp;
    }

    /** Item.wz info/only: a character can only have one (dat part version 5). */
    private boolean only;
    /** Item.wz info/expireOnLogout: it's gone when the character logs out (dat part version 5). */
    private boolean expireOnLogout;

    public boolean isOnly() {
        return only;
    }

    public void setOnly(boolean only) {
        this.only = only;
    }

    public boolean isExpireOnLogout() {
        return expireOnLogout;
    }

    public void setExpireOnLogout(boolean expireOnLogout) {
        this.expireOnLogout = expireOnLogout;
    }

    // Item.wz info of script items, used when the server has no script for them (dat part version 9)
    private int ridingSkillId;
    private int damageSkinId = -1;
    private int slotExpand;
    private int incHonorExp;

    public int getRidingSkillId() {
        return ridingSkillId;
    }

    public void setRidingSkillId(int ridingSkillId) {
        this.ridingSkillId = ridingSkillId;
    }

    /** info/damageSkinID, -1 if the item isn't a damage skin */
    public int getDamageSkinId() {
        return damageSkinId;
    }

    public void setDamageSkinId(int damageSkinId) {
        this.damageSkinId = damageSkinId;
    }

    /** info/slotExpand: the inventory tab an 8-slot coupon is for (1 equip ... 4 etc), 0 if none */
    public int getSlotExpand() {
        return slotExpand;
    }

    public void setSlotExpand(int slotExpand) {
        this.slotExpand = slotExpand;
    }

    // info/meso of a meso sack, info/maplepoint of a Maple Point coupon (dat part version 9)
    private int mesoBag;
    private int maplePoint;

    public int getMesoBag() {
        return mesoBag;
    }

    public void setMesoBag(int mesoBag) {
        this.mesoBag = mesoBag;
    }

    public int getMaplePoint() {
        return maplePoint;
    }

    public void setMaplePoint(int maplePoint) {
        this.maplePoint = maplePoint;
    }

    // EXP (521) / drop (536) rate coupons: info/rate, info/time ("MON:00-24", "HOL:..."), minLevel, maxLevel
    private int couponRate;
    private List<String> couponTimes = new ArrayList<>();
    private int couponMinLevel;
    private int couponMaxLevel;

    /** cash pet food (524): the pets it's for (spec/0, 1, ...); empty = every pet */
    private List<Integer> petFoodPets = new ArrayList<>();

    public List<Integer> getPetFoodPets() {
        return petFoodPets;
    }

    public void setPetFoodPets(List<Integer> petFoodPets) {
        this.petFoodPets = petFoodPets;
    }

    public int getCouponRate() {
        return couponRate;
    }

    public void setCouponRate(int couponRate) {
        this.couponRate = couponRate;
    }

    public List<String> getCouponTimes() {
        return couponTimes;
    }

    public void setCouponTimes(List<String> couponTimes) {
        this.couponTimes = couponTimes;
    }

    public int getCouponMinLevel() {
        return couponMinLevel;
    }

    public void setCouponMinLevel(int couponMinLevel) {
        this.couponMinLevel = couponMinLevel;
    }

    public int getCouponMaxLevel() {
        return couponMaxLevel;
    }

    public void setCouponMaxLevel(int couponMaxLevel) {
        this.couponMaxLevel = couponMaxLevel;
    }

    public int getIncHonorExp() {
        return incHonorExp;
    }

    public void setIncHonorExp(int incHonorExp) {
        this.incHonorExp = incHonorExp;
    }

    // info/addTime (seconds added: pet life, Potion Pot rust), maxDays, protectTime (Item Guard days),
    // stateChangeItem (the buff a map area item gives), limitMin (teleport cooldown in minutes)
    private int addTime;
    private int maxDays;
    private int protectTime;
    private int stateChangeItem;
    private int limitMin;

    public int getAddTime() {
        return addTime;
    }

    public void setAddTime(int addTime) {
        this.addTime = addTime;
    }

    public int getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(int maxDays) {
        this.maxDays = maxDays;
    }

    public int getProtectTime() {
        return protectTime;
    }

    public void setProtectTime(int protectTime) {
        this.protectTime = protectTime;
    }

    public int getStateChangeItem() {
        return stateChangeItem;
    }

    public void setStateChangeItem(int stateChangeItem) {
        this.stateChangeItem = stateChangeItem;
    }

    public int getLimitMin() {
        return limitMin;
    }

    public void setLimitMin(int limitMin) {
        this.limitMin = limitMin;
    }

    /** spec flags of what a potion cures; bit i is CURES[i] */
    public static final String[] CURES = {"poison", "seal", "darkness", "weakness", "curse"};
    private int cureFlags;

    public int getCureFlags() {
        return cureFlags;
    }

    public void setCureFlags(int cureFlags) {
        this.cureFlags = cureFlags;
    }

    // raise items (Etc 0422, info/uiData raise): info/exp is what one grade holds, or what an item adds to one;
    // info/consumeItem the items a raise item takes (dat part version 13)
    private int infoExp;
    private List<Integer> consumeItems = new ArrayList<>();

    public int getInfoExp() {
        return infoExp;
    }

    public void setInfoExp(int infoExp) {
        this.infoExp = infoExp;
    }

    public List<Integer> getConsumeItems() {
        return consumeItems;
    }

    public void setConsumeItems(List<Integer> consumeItems) {
        this.consumeItems = consumeItems;
    }
}
