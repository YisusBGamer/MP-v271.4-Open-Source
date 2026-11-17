package net.swordie.ms.client.character;

import net.swordie.ms.connection.db.Saveable;
import net.swordie.ms.enums.FirstEnterRewardType;
import net.swordie.ms.util.FileTime;

import java.util.concurrent.TimeUnit;


public class FirstEnterReward implements Saveable {

    private long id;
    private int accountId;
    private int charId;
    private FirstEnterRewardType rewardType;
    private int itemId;
    private int quantity;
    private String description;
    private FileTime expireTime;

    public FirstEnterReward() {}

    public FirstEnterReward(int charId, int itemId, int quantity, FirstEnterRewardType type, String description) {
        setCharId(charId);
        setItemId(itemId);
        setQuantity(quantity);
        setRewardType(type);
        setExpireTime(FileTime.fromEpochMillis(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(365)));
        setDescription(description);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public FirstEnterRewardType getRewardType() {
        return rewardType;
    }

    public void setRewardType(FirstEnterRewardType rewardType) {
        this.rewardType = rewardType;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FileTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(FileTime expireTime) {
        this.expireTime = expireTime;
    }

    public int getCharId() {
        return charId;
    }

    public void setCharId(int charId) {
        this.charId = charId;
    }

    /** @return whether a claimed item goes to the Cash Shop storage instead of the inventory (Surprise Style Box) */
    public static boolean claimsToLocker(int itemId) {
        return itemId == net.swordie.ms.constants.ItemConstants.SURPRISE_STYLE_BOX;
    }

    /**
     * @return whether a gift comes from a system that was removed (starter supplies, Hot Time, onboarding checklist,
     * burning level gifts, Swordie Coin overflow). Such gifts are never created, shown or claimed.
     */
    public static boolean isRemovedSource(String description) {
        if (description == null) {
            return false;
        }
        String d = description.trim();
        return d.equals("Starter supplies") || d.equals("Sprout medal") || d.equals("Hot Time")
                || d.equals("Onboarding checklist")
                || (d.startsWith("Level ") && d.endsWith(" supplies"))
                || d.startsWith("Hyper Burning MAX")
                || d.contains(" Swordie Coins (");
    }

    /** @return the gift's line in the gift box dialog */
    public String getDialogText() {
        if (getRewardType() == null) {
            return "#bA gift#k";
        }
        return switch (getRewardType()) {
            case GameItem, CashItem -> String.format("#i%d# #b#t%d##k x%d", getItemId(), getItemId(), getQuantity());
            case MaplePoints -> String.format("#b%,d Maple Points#k", getQuantity());
            case Meso -> String.format("#b%,d mesos#k", getQuantity());
            case Exp -> String.format("#b%,d EXP#k", getQuantity());
        };
    }

    /**
     * Claims a gift from the gift box dialog (the v265 client has no gift notifier window).
     * @return null when claimed, else what the player is told
     */
    public static String claim(Char chr, long id) {
        FirstEnterReward gift = null;
        for (FirstEnterReward r : chr.getFirstEnterRewards()) {
            if (r.getId() == id) {
                gift = r;
                break;
            }
        }
        if (gift == null) {
            return "That gift is no longer in your gift box.";
        }
        if (isRemovedSource(gift.getDescription()) || gift.getRewardType() == null) {
            chr.removeFirstEnterReward(gift);
            return "That gift is no longer available.";
        }
        if (gift.getExpireTime() != null && gift.getExpireTime().isExpired()) {
            chr.removeFirstEnterReward(gift);
            return "That gift has expired.";
        }
        switch (gift.getRewardType()) {
            case GameItem, CashItem -> {
                if (claimsToLocker(gift.getItemId())) {
                    claimToLocker(chr, gift.getItemId(), gift.getQuantity());
                } else if (chr.canHold(gift.getItemId(), gift.getQuantity())) {
                    chr.addItemToInventory(gift.getItemId(), gift.getQuantity());
                } else {
                    return "Make room in your inventory first.";
                }
            }
            case MaplePoints -> chr.addNx(gift.getQuantity());
            case Meso -> {
                if (!chr.canAddMoney(gift.getQuantity())) {
                    return "You can't hold that many mesos.";
                }
                chr.addMoney(gift.getQuantity());
            }
            case Exp -> chr.addExp(gift.getQuantity());
        }
        chr.removeFirstEnterReward(gift);
        return null;
    }

    /** Puts a claimed item into the account's Cash Shop storage. */
    public static void claimToLocker(Char chr, int itemId, int quantity) {
        chr.getAccount().getTrunk().addCashItem(
                net.swordie.ms.world.shop.cashshop.CashItemInfo.fromNewItem(chr, itemId, quantity));
        chr.chatMessage(net.swordie.ms.enums.ChatType.Notice2,
                "Your Surprise Style Box is in your Cash Shop storage. Open it there.");
    }
}
