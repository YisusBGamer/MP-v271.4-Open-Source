package net.swordie.ms.client.character;

import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.client.character.avatar.AvatarLook;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.EquipAttribute;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.util.FileTime;

/**
 * Created by Sjonnie on 2/18/2017.
 */
public class CharacterStat {

    private int id;
    private int characterId;
    private int characterIdForLog;
    private int worldIdForLog;

    private String name;
    private int gender;
    private int skin;
    private int face;
    private int hair;
    private int mixBaseHairColor;
    private int mixAddHairColor;
    private int mixHairBaseProb;
    private int level, prevLevel; // prev used to keep track of a change after login
    private int job;
    private int str;
    private int dex;
    private int inte;
    private int luk;
    private int hp;
    private int maxHp;
    private int mp;
    private int maxMp;
    private int ap;
    private int sp;
    private long exp, prevExp; // prev used to keep track of a change after login
    private int pop, prevPop; // fame  |  prev used to keep track of a change after login
    private long money;
    private int wp;
    private ExtendSP extendSP;
    private long posMap;
    private int portal;
    private int subJob;
    private int defFaceAcc;
    private int fatigue;
    private int lastFatigueUpdateTime;
    private int charismaExp; // ambition
    private int insightExp; // insight
    private int willExp; // willpower
    private int craftExp; // dilligence
    private int senseExp; // empathy
    private int charmExp; // charm
    private NonCombatStatDayLimit nonCombatStatDayLimit;
    private int pvpExp;
    private int pvpGrade;
    private int pvpPoint;
    private int pvpModeLevel;
    private int pvpModeType;
    private int eventPoint;
    private int albaActivityID;
    private FileTime albaStartTime;
    private int albaDuration;
    private int albaSpecialReward;
    private int burningType; // BurningModule.TYPE_*
    private int burningStartLevel;
    private boolean burningEnded; // a rebirth ended it
    private long combatPower; // not saved: worked out by CombatPower
    private long combatPowerAt;
    private FileTime lastLogout;
    private int gachExp;
    private int honorExp;
    private FileTime nextAvailableFameTime;
    private FileTime lastLevelObtainedTime = FileTime.fromType(FileTime.Type.MAX_TIME);

    // Transient
    private int wingItem;

    public CharacterStat(boolean init) {
        if (init) {
            extendSP = new ExtendSP(7);
            nonCombatStatDayLimit = new NonCombatStatDayLimit((short) 0, (short) 0, (byte) 0,(short) 0,(short) 0,(short) 0,(short) 0, FileTime.fromType(FileTime.Type.ZERO_TIME));
            albaStartTime = FileTime.fromType(FileTime.Type.PLAIN_ZERO);
            lastLogout = FileTime.fromType(FileTime.Type.PLAIN_ZERO);
            nextAvailableFameTime = FileTime.fromType(FileTime.Type.MAX_TIME);
        }
    }

    public CharacterStat(String name, int job) {
        this(true);
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public short getAp() {
        return (short) ap;
    }

    public short getDex() {
        return (short) dex;
    }

    public int getHp() {
        return hp;
    }

    public short getInt() {
        return (short) inte;
    }

    public short getJob() {
        return (short) job;
    }

    public short getLevel() {
        return (short) level;
    }

    public int getPrevLevel() {
        return prevLevel;
    }

    public int getCharismaExp() {
        return charismaExp;
    }

    public short getLuk() {
        return (short) luk;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public int getMp() {
        return mp;
    }

    public short getPop() { //Fame
        return (short) pop;
    }

    public int getPrevPop() {
        return prevPop;
    }

    public short getSp() {
        return (short) sp;
    }

    public short getStr() {
        return (short) str;
    }

    public short getWp() {
        return (short) wp;
    }

    public long getExp() {
        return exp;
    }

    public long getPrevExp() {
        return prevExp;
    }

    public long getMoney() {
        return money;
    }

    public ExtendSP getExtendSP() {
        return extendSP;
    }

    public int getCharacterId() {
        return characterId;
    }

    public int getCharacterIdForLog() {
        return characterId;
    }

    public int getFace() {
        return face;
    }

    public int getGender() {
        return gender;
    }

    public int getHair() {
        return hair;
    }

    public int getMixAddHairColor() {
        return mixAddHairColor;
    }

    public int getMixBaseHairColor() {
        return mixBaseHairColor;
    }

    public int getMixHairBaseProb() {
        return mixHairBaseProb;
    }

    public int getSkin() {
        return skin;
    }

    public int getWorldIdForLog() {
        return worldIdForLog;
    }

    public int getCharmExp() {
        return charmExp;
    }

    public int getCraftExp() {
        return craftExp;
    }

    public int getAlbaActivityID() {
        return albaActivityID;
    }

    public int getEventPoint() {
        return eventPoint;
    }

    public int getPortal() {
        return portal;
    }

    public int getAlbaDuration() {
        return albaDuration;
    }

    public int getInsightExp() {
        return insightExp;
    }

    public int getAlbaSpecialReward() {
        return albaSpecialReward;
    }

    public int getPvpExp() {
        return pvpExp;
    }

    public int getPvpGrade() {
        return pvpGrade;
    }

    public int getPvpModeLevel() {
        return pvpModeLevel;
    }

    public int getPvpModeType() {
        return pvpModeType;
    }

    public int getPvpPoint() {
        return pvpPoint;
    }

    public int getSenseExp() {
        return senseExp;
    }

    public int getWillExp() {
        return willExp;
    }

    public long getPosMap() {
        return posMap == 0 ? FieldConstants.NEGLECTED_ROCKY_MOUNTAIN_FIELD : posMap;
    }

    public NonCombatStatDayLimit getNonCombatStatDayLimit() {
        return nonCombatStatDayLimit;
    }

    public FileTime getAlbaStartTime() {
        return albaStartTime;
    }

    public int getDefFaceAcc() {
        return defFaceAcc;
    }

    public int getFatigue() {
        return fatigue;
    }

    public int getLastFatigueUpdateTime() {
        return lastFatigueUpdateTime;
    }

    public int getSubJob() {
        return subJob;
    }

    public void encode(OutPacket outPacket) {
        encode(outPacket, null);
    }

    /** look: the matching AvatarLook, so a mixed hair/eye colour is sent as the client's blended id */
    public void encode(OutPacket outPacket, AvatarLook look) {
        outPacket.encodeInt(getCharacterId());
        outPacket.encodeInt(getCharacterIdForLog());
        outPacket.encodeInt(getWorldIdForLog());
        outPacket.encodeString(getName(), 13);

        outPacket.encodeByte(getGender());
        outPacket.encodeByte(getSkin());
        outPacket.encodeInt(0);
        outPacket.encodeInt(look == null ? getFace() : look.encodeFace(getFace()));
        outPacket.encodeInt(look == null ? getHair() : look.encodeHair(getHair())); // a blend is part of the id

        outPacket.encodeInt(getLevel());
        outPacket.encodeShort(getJob());
        outPacket.encodeShort(getStr());
        outPacket.encodeShort(getDex());
        outPacket.encodeShort(getInt());
        outPacket.encodeShort(getLuk());
        outPacket.encodeInt(getHp());
        outPacket.encodeInt(getMaxHp());
        outPacket.encodeInt(getMp());
        outPacket.encodeInt(getMaxMp());
        outPacket.encodeShort(getAp());

        if (JobConstants.isExtendSpJob(getJob())) {
            getExtendSP().encode(outPacket);
        } else {
            outPacket.encodeShort(getSp());
        }

        outPacket.encodeLong(getExp());
        outPacket.encodeInt(getPop());
        outPacket.encodeInt(getWp()); // Waru
        outPacket.encodeInt(getGachExp());
        outPacket.encodeInt((int) getPosMap());
        outPacket.encodeByte(getPortal());
        outPacket.encodeInt(0); // TODO figure out
        outPacket.encodeShort(getSubJob());

        // v271: Beast Tamer no longer has this face accessory int (client 0x1407cf030)
        if (JobConstants.isDemon(getJob()) || JobConstants.isXenon(getJob())
                || JobConstants.isArk(getJob()) || JobConstants.isHoYoung(getJob())) {
            outPacket.encodeInt(getDefFaceAcc());
        }

        outPacket.encodeByte(0);
        outPacket.encodeFT(FileTime.MIN_TIME());

        // fatigue is gone in v265
        outPacket.encodeInt(getCharismaExp());
        outPacket.encodeInt(getInsightExp());
        outPacket.encodeInt(getWillExp());
        outPacket.encodeInt(getCraftExp());
        outPacket.encodeInt(getSenseExp());
        outPacket.encodeInt(getCharmExp());
        getNonCombatStatDayLimit().encode(outPacket);

        outPacket.encodeInt(getPvpExp());
        outPacket.encodeByte(getPvpGrade());
        outPacket.encodeInt(getPvpPoint());
        // skill-level gate: 0 caps skills at 2nd job, 1 at 3rd, 6 at beginner; any other value lifts it
        // (v271 skill window/skill use read it: client 0x141078950 via 0x140f97620)
        outPacket.encodeByte(2);

        outPacket.encodeByte(getPvpModeType());
        outPacket.encodeInt(getEventPoint());

        outPacket.encodeInt(0);
        outPacket.encodeInt(0);

        encodeStatExtra(outPacket);

        outPacket.encodeInt(0);
    }

    /**
     * The burning block and what follows it (client DecodeCharacterStatExtra, also read by CHARACTER_BURNING 447).
     * Burning: start, end, min level, max level, extra levels per level up, burning type (5 Hyper Burning MAX, 10
     * Burning Beyond).
     */
    public void encodeStatExtra(OutPacket outPacket) {
        int[] b = net.swordie.ms.client.character.BurningModule.getDisplay(this);
        if (b != null) {
            outPacket.encodeLong(BURNING_START_FT);
            outPacket.encodeLong(BURNING_END_FT);
            outPacket.encodeInt(b[0]);
            outPacket.encodeInt(b[1]);
            outPacket.encodeInt(b[2]);
            outPacket.encodeByte(b[3]);
        } else {
            outPacket.encodeLong(0);
            outPacket.encodeLong(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeByte(0);
        }
        outPacket.encodeInt(0);
        outPacket.encodeString("");
        outPacket.encodeInt(0);
        outPacket.encodeString("");
        outPacket.encodeString("");
        outPacket.encodeString("");
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
    }

    /**
     * The block between CharacterStat and AvatarLook in a character list entry. v271 (client 0x1407d0770): one int
     * less after the long, three strings more at the end.
     */
    public void encodeAvatarExtra(OutPacket outPacket) {
        outPacket.encodeInt(0); // star force
        outPacket.encodeInt(0); // arcane force
        outPacket.encodeLong(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeByte(0);
        outPacket.encodeByte(0);
        outPacket.encodeString("");
        outPacket.encodeByte(0);
        outPacket.encodeString("");
        outPacket.encodeLong(getCombatPower());
        outPacket.encodeString("");
        outPacket.encodeString("");
        outPacket.encodeString("");
    }

    // raw FILETIMEs (100 ns since 1601): 2020-01-01 00:00 UTC and the client's usual "never" (2079)
    private static final long BURNING_START_FT = 132223104000000000L;
    private static final long BURNING_END_FT = 0x0217E646BB058000L;

    public FileTime getLastLogout() {
        return lastLogout;
    }

    public void setLastLogout(FileTime lastLogout) {
        this.lastLogout = lastLogout;
    }

    public long getCombatPower() {
        return combatPower;
    }

    public long getCombatPowerAt() {
        return combatPowerAt;
    }

    public void setCombatPower(long combatPower, long at) {
        this.combatPower = combatPower;
        this.combatPowerAt = at;
    }

    /** @return whether this character burns at its current level (see BurningModule) */
    public boolean isBurning() {
        return net.swordie.ms.client.character.BurningModule.isBurningNow(this);
    }

    public int getBurningType() {
        return burningType;
    }

    public void setBurningType(int burningType) {
        this.burningType = burningType;
    }

    public int getBurningStartLevel() {
        return burningStartLevel;
    }

    public void setBurningStartLevel(int burningStartLevel) {
        this.burningStartLevel = burningStartLevel;
    }

    public boolean isBurningEnded() {
        return burningEnded;
    }

    public void setBurningEnded(boolean burningEnded) {
        this.burningEnded = burningEnded;
    }

    public void setJob(int job) {
        this.job = job;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGachExp() {
        return gachExp;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public void setCharacterIdForLog(int characterIdForLog) {
        this.characterIdForLog = characterIdForLog;
    }

    public void setWorldIdForLog(int worldIdForLog) {
        this.worldIdForLog = worldIdForLog;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setSkin(int skin) {
        this.skin = skin;
    }

    public void setFace(int face) {
        this.face = face;
    }

    public void setHair(int hair) {
        this.hair = hair;
    }

    public void setMixAddHairColor(int mixAddHairColor) {
        this.mixAddHairColor = mixAddHairColor;
    }

    public void setMixHairBaseProb(int mixHairBaseProb) {
        this.mixHairBaseProb = mixHairBaseProb;
    }

    public void setMixBaseHairColor(int mixBaseHairColor) {
        this.mixBaseHairColor = mixBaseHairColor;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public void setPrevLevel(int prevLevel) {
        this.prevLevel = prevLevel;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public void setDex(int dex) {
        this.dex = dex;
    }

    public void setInt(int inte) {
        this.inte = inte;
    }

    public void setLuk(int luk) {
        this.luk = luk;
    }

    public void setHp(int hp) {
        this.hp = Math.min(hp, GameConstants.MAX_HP_MP);
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = Math.max(0, Math.min(maxHp, GameConstants.MAX_HP_MP));
    }

    public void setMp(int mp) {
        this.mp = Math.max(0, Math.min(mp, GameConstants.MAX_HP_MP));
    }

    public void setMaxMp(int maxMp) {
        this.maxMp = Math.min(maxMp, GameConstants.MAX_HP_MP);
    }

    public void setAp(int ap) {
        this.ap = ap;
    }

    public void setSp(int sp) {
        this.sp = sp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public void setPrevExp(long prevExp) {
        this.prevExp = prevExp;
    }

    public void setPop(int pop) {
        this.pop = pop;
    }

    public void setPrevPop(int prevPop) {
        this.prevPop = prevPop;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public void setWp(int wp) {
        this.wp = wp;
    }

    public void setPosMap(long posMap) {
        this.posMap = posMap;
    }

    public void setPortal(int portal) {
        this.portal = portal;
    }

    public void setSubJob(int subJob) {
        this.subJob = subJob;
    }

    public void setDefFaceAcc(int defFaceAcc) {
        this.defFaceAcc = defFaceAcc;
    }

    public void setFatigue(int fatigue) {
        this.fatigue = fatigue;
    }

    public void setLastFatigueUpdateTime(int lastFatigueUpdateTime) {
        this.lastFatigueUpdateTime = lastFatigueUpdateTime;
    }

    public void setCharismaExp(int charismaExp) {
        this.charismaExp = charismaExp;
    }

    public void setInsightExp(int insightExp) {
        this.insightExp = insightExp;
    }

    public void setWillExp(int willExp) {
        this.willExp = willExp;
    }

    public void setCraftExp(int craftExp) {
        this.craftExp = craftExp;
    }

    public void setSenseExp(int senseExp) {
        this.senseExp = senseExp;
    }

    public void setCharmExp(int charmExp) {
        this.charmExp = charmExp;
    }

    public void setPvpExp(int pvpExp) {
        this.pvpExp = pvpExp;
    }

    public void setPvpGrade(int pvpGrade) {
        this.pvpGrade = pvpGrade;
    }

    public void setPvpPoint(int pvpPoint) {
        this.pvpPoint = pvpPoint;
    }

    public void setPvpModeLevel(int pvpModeLevel) {
        this.pvpModeLevel = pvpModeLevel;
    }

    public void setPvpModeType(int pvpModeType) {
        this.pvpModeType = pvpModeType;
    }

    public void setEventPoint(int eventPoint) {
        this.eventPoint = eventPoint;
    }

    public void setAlbaActivityID(int albaActivityID) {
        this.albaActivityID = albaActivityID;
    }

    public void setAlbaDuration(int albaDuration) {
        this.albaDuration = albaDuration;
    }

    public void setAlbaSpecialReward(int albaSpecialReward) {
        this.albaSpecialReward = albaSpecialReward;
    }

    public void setGachExp(int gachExp) {
        this.gachExp = gachExp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setExtendSP(ExtendSP extendSP) {
        this.extendSP = extendSP;
    }

    public void setNonCombatStatDayLimit(NonCombatStatDayLimit nonCombatStatDayLimit) {
        this.nonCombatStatDayLimit = nonCombatStatDayLimit;
    }

    public void setAlbaStartTime(FileTime albaStartTime) {
        this.albaStartTime = albaStartTime;
    }

    public int getHonorExp() {
        return honorExp;
    }

    public void setHonorExp(int honorExp) {
        this.honorExp = honorExp;
    }

    public void setWingItem(int wingItem) {
        this.wingItem = wingItem;
    }

    public int getWingItem() {
        return wingItem;
    }

    public FileTime getNextAvailableFameTime() {
        return nextAvailableFameTime;
    }

    public void setNextAvailableFameTime(FileTime nextAvailableFameTime) {
        this.nextAvailableFameTime = nextAvailableFameTime;
    }

    public FileTime getLastLevelObtainedTime() {
        return lastLevelObtainedTime;
    }

    public void setLastLevelObtainedTime(FileTime lastLevelObtainedTime) {
        this.lastLevelObtainedTime = lastLevelObtainedTime;
    }

    public void addSp(int sp) {
        setSp(getSp() + sp);
    }

    public int getExtraScrollChance(Equip equip) {
        int extra = 0;
        if (equip.hasAttribute(EquipAttribute.LuckyDay)) {
            extra += 10;
        }
        var dilligenceExp = getNonCombatStatDayLimit().getCraft();
        var level = GameConstants.getNonCombatLevelByExp(dilligenceExp);
        return extra + level / 10; // just ignore the .5% chance
    }
}

