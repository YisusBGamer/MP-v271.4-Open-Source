package net.swordie.ms.enums;

import net.swordie.ms.util.Util;

/**
 * @author Sjonnie
 * Created on 10/2/2018.
 */
public enum MatrixUpdateRequest {
    Activate(0),
    Deactivate(1),
    Swap(2),

    // v265 numbers, from the client's senders: 7 = a list of nodes, 9 = craft (base core), 11 = slot enhancement
    // (seen in game), 13 = no body (reset), 15/16 = the node context menu (lock/unlock)
    Enhance(4),
    Disassemble(5),
    DisassembleGroup(7),

    CraftNode(9),

    EnhanceSlot(11),
    ExpandSlot(12),
    EnhanceReset(13),

    LockRecord(15),
    UnlockRecord(16),
    ;

    private int val;

    MatrixUpdateRequest(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public static MatrixUpdateRequest getByVal(int val) {
        return Util.findWithPred(values(), mur -> mur.getVal() == val);
    }
}
