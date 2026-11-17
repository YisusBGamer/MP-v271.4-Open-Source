package net.swordie.ms.client.character.quest.reward;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.loaders.DatSerializable;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.util.FileTime;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Created on 3/2/2018.
 * <p>
 * Item reward from Quest.wz/Act.img/[id]/[0|1]/item/[n].
 * <ul>
 *     <li><code>prop</code>: 0 = always given, &gt; 0 = weighted random pick (only one of these is given),
 *     -1 = the player selects one of these in the reward window</li>
 *     <li><code>gender</code>: 0 = male, 1 = female, 2/absent = both</li>
 *     <li><code>job</code> / <code>jobEx</code>: job category bit masks the reward is limited to</li>
 *     <li><code>period</code>: expiration in minutes, <code>dateExpire</code>: fixed expiration (yyyyMMddHH)</li>
 *     <li><code>resignRemove</code>: removed again when the quest is forfeited</li>
 * </ul>
 */
public class QuestItemReward implements QuestReward{

    public static final int GENDER_BOTH = 2;

    // Bits of the "job" mask whose meaning is known (verified against the weapon rewards in Quest.wz)
    private static final int JOB_BEGINNER = 0x1;
    private static final int JOB_WARRIOR = 0x2;
    private static final int JOB_MAGICIAN = 0x4;
    private static final int JOB_BOWMAN = 0x8;
    private static final int JOB_THIEF = 0x10;
    private static final int JOB_PIRATE = 0x20;
    private static final int JOB_NOBLESSE = 0x400;
    private static final int JOB_DAWN_WARRIOR = 0x800;
    private static final int JOB_BLAZE_WIZARD = 0x1000;
    private static final int JOB_WIND_ARCHER = 0x2000;
    private static final int JOB_NIGHT_WALKER = 0x4000;
    private static final int JOB_THUNDER_BREAKER = 0x8000;
    private static final int JOB_ARAN = 0x200000;
    private static final int JOB_EVAN = 0x400000;
    private static final int JOB_MERCEDES = 0x800000;
    private static final int JOB_PHANTOM = 0x1000000;
    private static final int KNOWN_JOB_BITS = JOB_BEGINNER | JOB_WARRIOR | JOB_MAGICIAN | JOB_BOWMAN | JOB_THIEF
            | JOB_PIRATE | JOB_NOBLESSE | JOB_DAWN_WARRIOR | JOB_BLAZE_WIZARD | JOB_WIND_ARCHER | JOB_NIGHT_WALKER
            | JOB_THUNDER_BREAKER | 0x20000 | 0x100000 | JOB_ARAN | JOB_EVAN | JOB_MERCEDES | JOB_PHANTOM;

    private int id;
    private short quantity;
    private String potentialGrade;
    private int status;
    private int prop;
    private int gender = GENDER_BOTH;
    private int job;
    private int jobEx;
    private int period;
    private String dateExpire = "";
    private boolean resignRemove;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public short getQuantity() {
        return quantity;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    @Override
    public void giveReward(Char chr) {
        Item item = ItemData.getItemDeepCopy(getId());
        if (item == null) {
            return;
        }
        if (getQuantity() < 0) {
            chr.consumeItem(item.getItemId(), -getQuantity());
        } else {
            if (!ItemConstants.isEquip(getId())) {
                item.setQuantity(Math.max(1, getQuantity()));
            }
            FileTime expire = getExpireTime();
            if (expire != null) {
                item.setDateExpire(expire);
            }
            chr.addItemToInventory(item);
        }
    }

    /**
     * @return the expiration time of the item this reward gives, or null if it doesn't expire.
     */
    public FileTime getExpireTime() {
        if (getPeriod() > 0) {
            return FileTime.fromDate(LocalDateTime.now().plusMinutes(getPeriod()));
        }
        if (getDateExpire() != null && getDateExpire().length() == 10) {
            try {
                LocalDateTime date = LocalDateTime.parse(getDateExpire() + "00",
                        DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
                if (date.isBefore(LocalDateTime.now())) {
                    return null; // an old event's fixed date (event quests that run again): no expiry
                }
                return FileTime.fromDate(date);
            } catch (DateTimeParseException ignored) {
            }
        }
        return null;
    }

    /**
     * Whether or not this reward applies to the given Char, based on its gender and job restrictions.
     */
    public boolean isEligible(Char chr) {
        int gender = getGender();
        if ((gender == 0 || gender == 1) && chr.getAvatarData() != null && chr.getAvatarData().getAvatarLook() != null
                && chr.getAvatarData().getAvatarLook().getGender() != gender) {
            return false;
        }
        return isJobEligible(chr.getJob(), getJob(), getJobEx());
    }

    /**
     * Checks the "job" bit mask of an Act.img item. Bits for job branches whose bit is known are checked strictly.
     * For branches whose bit isn't known (newer jobs), the item is only given if the mask contains bits outside the
     * known set (or a jobEx mask), so that items clearly meant for a single known branch aren't handed out to them.
     */
    public static boolean isJobEligible(short charJob, int jobMask, int jobExMask) {
        if (jobMask == 0 && jobExMask == 0) {
            return true;
        }
        int bit = getJobBit(charJob);
        if (bit != 0) {
            return (jobMask & bit) != 0;
        }
        return (jobMask & ~KNOWN_JOB_BITS) != 0 || jobExMask != 0;
    }

    private static int getJobBit(short job) {
        if (job == 0) {
            return JOB_BEGINNER;
        }
        if (job == 1000) {
            return JOB_NOBLESSE;
        }
        int branch = job / 100;
        switch (branch) {
            case 1: return JOB_WARRIOR;
            case 2: return JOB_MAGICIAN;
            case 3: return JOB_BOWMAN; // includes Pathfinder (301, 330-332)
            case 4: return JOB_THIEF; // includes Dual Blade (430-434)
            case 5: return JOB_PIRATE; // includes Cannoneer (501, 530-532) and Jett (508, 570-572)
            case 11: return JOB_DAWN_WARRIOR;
            case 12: return JOB_BLAZE_WIZARD;
            case 13: return JOB_WIND_ARCHER;
            case 14: return JOB_NIGHT_WALKER;
            case 15: return JOB_THUNDER_BREAKER;
            case 21: return JOB_ARAN;
            case 22: return JOB_EVAN;
            case 23: return JOB_MERCEDES;
            case 24: return JOB_PHANTOM;
            default: return 0;
        }
    }

    public void setPotentialGrade(String potentialGrade) {
        this.potentialGrade = potentialGrade;
    }

    public String getPotentialGrade() {
        return potentialGrade == null ? "" : potentialGrade;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int getStatus() {
        return status;
    }

    public void setProp(int prop) {
        this.prop = prop;
    }

    public int getProp() {
        return prop;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getGender() {
        return gender;
    }

    public int getJob() {
        return job;
    }

    public void setJob(int job) {
        this.job = job;
    }

    public int getJobEx() {
        return jobEx;
    }

    public void setJobEx(int jobEx) {
        this.jobEx = jobEx;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getDateExpire() {
        return dateExpire == null ? "" : dateExpire;
    }

    public void setDateExpire(String dateExpire) {
        this.dateExpire = dateExpire;
    }

    public boolean isResignRemove() {
        return resignRemove;
    }

    public void setResignRemove(boolean resignRemove) {
        this.resignRemove = resignRemove;
    }

    /**
     * @return true if this item is one of the rewards the player chooses from.
     */
    public boolean isSelectable() {
        return getProp() == -1;
    }

    /**
     * @return true if this item takes part in a weighted random draw.
     */
    public boolean isRandom() {
        return getProp() > 0;
    }

    @Override
    public void write(RandomAccessFile dos) throws IOException {
        dos.writeInt(getId());
        dos.writeShort(getQuantity());
        dos.writeUTF(getPotentialGrade());
        dos.writeInt(getStatus());
        dos.writeInt(getProp());
        dos.writeInt(getGender());
        dos.writeInt(getJob());
        dos.writeInt(getJobEx());
        dos.writeInt(getPeriod());
        dos.writeUTF(getDateExpire());
        dos.writeBoolean(isResignRemove());
    }

    @Override
    public DatSerializable load(RandomAccessFile dis) throws IOException {
        QuestItemReward qir = new QuestItemReward();
        qir.setId(dis.readInt());
        qir.setQuantity(dis.readShort());
        qir.setPotentialGrade(dis.readUTF());
        qir.setStatus(dis.readInt());
        qir.setProp(dis.readInt());
        qir.setGender(dis.readInt());
        qir.setJob(dis.readInt());
        qir.setJobEx(dis.readInt());
        qir.setPeriod(dis.readInt());
        qir.setDateExpire(dis.readUTF());
        qir.setResignRemove(dis.readBoolean());
        return qir;
    }
}
