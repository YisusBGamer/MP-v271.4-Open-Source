package net.swordie.ms.world.gach;

import java.util.ArrayList;
import java.util.List;

public class GachaponInfo {
    private List<String> messages = new ArrayList<>();
    private List<GachItem> items = new ArrayList<>();

    public GachaponInfo(List<String> messages, List<GachItem> items) {
        this.messages = messages;
        this.items = items;
    }

    public List<GachItem> getRewards() {
        return items;
    }

    public List<Integer> getHotItems() {
        List<Integer> hotItems = new ArrayList<>();
        for (GachItem i : items)
            if (i.isHotTime()) hotItems.add(i.getItemID());
        return hotItems;
    }

    public List<String> getMessages() {
        return messages;
    }

    public static class GachItem {
        private final int itemID;
        private final short count;
        private final int weight;
        private final boolean hotTime;

        public GachItem(final int itemID, final short count, final int weight, final boolean hotTime) {
            this.itemID = itemID;
            this.count = (short) Math.max(1, count);
            this.weight = Math.max(1, weight);
            this.hotTime = hotTime;
        }

        public GachItem(final int itemID, final boolean hotTime) {
            this(itemID, (short) 1, 1, hotTime);
        }

        public GachItem(final int itemID) {
            this(itemID, false);
        }

        public int getItemID() {
            return itemID;
        }

        public short getCount() {
            return count;
        }

        public int getWeight() {
            return weight;
        }

        public boolean isHotTime() {
            return hotTime;
        }
    }
}
