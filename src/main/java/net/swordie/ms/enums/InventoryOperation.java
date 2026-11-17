package net.swordie.ms.enums;

/**
 * Created on 2/28/2018.
 */
public enum InventoryOperation {
    Add(0),
    UpdateQuantity(1),
    Move(2),
    Remove(3),
    ItemExp(4),
    Unk232(5), // lock: an item follows
    PresetChange(6),
    UpdateBagPos(7),
    UpdateBagQuantity(8),
    BagRemove(9),
    BagToBag(10),
    BagNewItem(11),
    BagRemoveSlot(12),
    ;

    private byte val;

    InventoryOperation(int val) {
        this.val = (byte) val;
    }

    public byte getVal() {
        return val;
    }
}
