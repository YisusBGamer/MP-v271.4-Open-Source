package net.swordie.ms.life.drop;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.packet.DropPool;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.enums.DropLeaveType;
import net.swordie.ms.enums.DropMotionType;
import net.swordie.ms.enums.DropType;
import net.swordie.ms.enums.EnterType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.Life;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.containerclasses.ItemInfo;
import net.swordie.ms.util.FileTime;

import java.util.Collections;
import java.util.concurrent.ScheduledFuture;

/**
 * Created on 2/21/2018.
 */
public class Drop extends Life {

    private Item item;
    private int money;
    private DropType dropType;
    private int ownerID;
    private boolean explosiveDrop;
    private boolean canBePickedUpByPet;
    private FileTime expireTime;
    private long mobExp;
    private DropMotionType dropMotionType;
    private boolean byPickPocket = false;
    private byte moneyType; // coin look: 1 Pick Pocket, 4 Blood Money
    private ScheduledFuture ownerResetTimer;
    private boolean notSoulOrb = true;
    private boolean instanced;
    private FileTime removeTime;
    private Char dropper;
    private boolean blockFromPickup;
    private int minLevelToPickup;
    /** Not shown to the clients yet: an auto-looted drop that goes straight to an inventory (see Field#showDrop). */
    private boolean silent;

    public Drop(int templateId) {
        super(templateId);
        dropMotionType = DropMotionType.Normal;
        canBePickedUpByPet = true;
    }

    public Drop(int templateId, Item item, boolean instanced) {
        this(templateId, item);
        this.instanced = true;
    }

    public DropType getDropType() {
        return dropType;
    }

    public void setDropType(DropType dropType) {
        this.dropType = dropType;
    }

    public Drop(int templateId, Item item) {
        super(templateId);
        this.item = item;
        dropType = DropType.Item;
        dropMotionType = DropMotionType.Normal;
        expireTime = FileTime.fromType(FileTime.Type.ZERO_TIME);
    }

    public Drop(int templateId, int money) {
        super(templateId);
        this.money = money;
        dropType = DropType.Mesos;
        dropMotionType = DropMotionType.Normal;
        expireTime = FileTime.fromType(FileTime.Type.ZERO_TIME);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
        setDropType(DropType.Item);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
        setDropType(DropType.Mesos);
    }

    public boolean isMoney() {
        return getDropType() == DropType.Mesos && isNotSoulOrb();
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public boolean isExplosiveDrop() {
        return explosiveDrop;
    }

    public void setExplosiveDrop(boolean explosiveDrop) {
        this.explosiveDrop = explosiveDrop;
    }

    public FileTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(FileTime expireTime) {
        this.expireTime = expireTime;
    }

    public byte getItemGrade() {
        byte res = 0;
        if(getItem() != null && getItem() instanceof Equip) {
            res = (byte) ((Equip) getItem()).getGrade();
        }
        return res;
    }

    @Override
    public void broadcastSpawnPacket(Char onlyChar, EnterType enterType) {
        if (isSilent()) {
            return;
        }
        boolean canSpawn = canSpawn(onlyChar);
        if (canSpawn) {
            if (getItem() != null && net.swordie.ms.constants.ItemConstants.isCollisionLootItem(getItem().getItemId())) {
                // orbs are collected by touching them, which the client only does for drops sent as orbs
                if (isInstanced() && getOwnerID() != onlyChar.getId()) {
                    return;
                }
                onlyChar.write(DropPool.dropEnterFieldCollisionPickUp(this, getPosition(), 0));
                return;
            }
            onlyChar.write(DropPool.dropEnterField(this, getPosition(), getOwnerID(), canBePickedUpBy(onlyChar)));
        }
    }

    public boolean canSpawn(Char chr) {
        ItemInfo ii = null;
        if (item != null) {
            ii = ItemData.getItemInfoByID(item.getItemId());
        }
        return (isMoney()
                    || (item != null && ItemConstants.isEquip(item.getItemId()))
                    || (ii != null && chr != null
                        && chr.wantsQuestDrop(item.getItemId(), ii.isQuest() ? ii.getQuestIDs() : Collections.emptySet()))
                ) && !chr.isInIgnoredItemList(this);
    }

    public void setMobExp(long mobExp) {
        this.mobExp = mobExp;
    }

    public long getMobExp() {
        return mobExp;
    }

    public boolean canBePickedUpBy(Char chr) {
        int owner = getOwnerID();
        if (item != null && net.swordie.ms.constants.ItemConstants.isIntensePowerCrystal(item.getItemId()) && owner != 0) {
            return owner == chr.getId(); // each fighter's crystal is their own
        }
        return owner == chr.getId() ||
                (chr.getParty() != null && chr.getParty().hasPartyMember(owner))
                || owner == 0;
    }

    // set while a pickup of this drop is being handled, so two pickups (e.g. auto-loot on a scheduler thread and a
    // pickup request) can never both hand out the same drop
    private final java.util.concurrent.atomic.AtomicBoolean claimed = new java.util.concurrent.atomic.AtomicBoolean();

    /**
     * @return true if the caller may pick up this drop (nobody else is doing so); call {@link #releaseClaim()} if the
     * pickup fails
     */
    public boolean tryClaim() {
        return claimed.compareAndSet(false, true);
    }

    public void releaseClaim() {
        claimed.set(false);
    }

    public boolean canBePickedUpByPet() {
        return canBePickedUpByPet;
    }

    public void setCanBePickedUpByPet(boolean canBePickedUpByPet) {
        this.canBePickedUpByPet = canBePickedUpByPet;
    }

    public boolean isSilent() {
        return silent;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    @Override
    public void broadcastLeavePacket() {
        if (!isSilent()) { // the clients never saw a silent drop
            getField().broadcastPacket(DropPool.dropLeaveField(DropLeaveType.Fade, 0, getObjectId(), (short) 0, 0, 0));
        }
        cancelOwnerResetTimer();
    }

    public DropMotionType getDropMotionType() {
        return dropMotionType;
    }

    public void setDropMotionType(DropMotionType dropMotionType) {
        this.dropMotionType = dropMotionType;
    }

    public byte getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(byte moneyType) {
        this.moneyType = moneyType;
    }

    public boolean isByPickPocket() {
        return byPickPocket;
    }

    public void setByPickPocket(boolean byPickPocket) {
        this.byPickPocket = byPickPocket;
    }

    public ScheduledFuture getOwnerResetTimer() {
        return ownerResetTimer;
    }

    public void cancelOwnerResetTimer() {
        EventManager.stopTimer(ownerResetTimer);
    }

    public boolean isNotSoulOrb() {
        return notSoulOrb;
    }

    public void setNotSoulOrb(boolean notSoulOrb) {
        this.notSoulOrb = notSoulOrb;
    }

    public boolean isInstanced() {
        return instanced;
    }

    public void setInstanced(boolean instanced) {
        this.instanced = instanced;
    }

    public void setRemoveTime(FileTime removeTime) {
        this.removeTime = removeTime;
    }

    public FileTime getRemoveTime() {
        return removeTime;
    }

    public Char getDropper() {
        return dropper;
    }

    public void setDropper(Char dropper) {
        this.dropper = dropper;
    }

    public void setBlockFromPickup(boolean blockFromPickup) {
        this.blockFromPickup = blockFromPickup;
    }

    public boolean blockFromPickup() {
        return blockFromPickup;
    }

    public int getMinLevelToPickup() {
        return minLevelToPickup;
    }

    public void setMinLevelToPickup(int minLevelToPickup) {
        this.minLevelToPickup = minLevelToPickup;
    }
}
