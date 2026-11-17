package net.swordie.ms.connection.packet.field.kaling;

/** KALING_SEASON_PICK types (Kaling's Four Seasons hub, client 0x142a115a0). */
public enum SeasonPickType {
    Init(0),
    Member(1),
    Choice(3),
    Ready(4),
    Leave(5),
    ;

    private final int val;

    SeasonPickType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
