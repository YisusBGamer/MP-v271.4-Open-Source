package net.swordie.ms.life.mob.boss.papulatus;

import net.swordie.ms.connection.Encodable;
import net.swordie.ms.connection.OutPacket;

/** One laser: index, on, then two doubles (start angle and turn, as GMS sends them). */
public class PapulatusLaserInfo implements Encodable {

    private final int idx;
    private final boolean enabled;
    private final double angle;
    private final double turn;

    public PapulatusLaserInfo(int idx, boolean enabled, double angle, double turn) {
        this.idx = idx;
        this.enabled = enabled;
        this.angle = angle;
        this.turn = turn;
    }

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(idx);
        outPacket.encodeByte(enabled);
        outPacket.encodeLong(Double.doubleToLongBits(angle));
        outPacket.encodeLong(Double.doubleToLongBits(turn));
    }
}
