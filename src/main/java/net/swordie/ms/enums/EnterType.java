package net.swordie.ms.enums;

/**
 * Created on 1/6/2018.
 */
public enum EnterType {
    NoAnimation(0),
    Animation(1),
    ChangeField(2); // back in after the owner's map change

    private byte val;

    EnterType(int val) {
        this.val = (byte) val;
    }

    public byte getVal() {
        return val;
    }
}
