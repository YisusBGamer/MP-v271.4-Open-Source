package net.swordie.ms.connection.packet.field.kaling;

import net.swordie.ms.connection.OutPacket;

/** Kaling's gauges: the party's Willpower and the Spring, Summer and Fall gauges (perilsGauge keys 0-2). */
public class KalingResult {

    public static final int SEASONS = 3;

    private final KalingResultType type;
    private int willpower;
    private int[] seasons;
    private boolean[] flags;

    private KalingResult(KalingResultType type) {
        this.type = type;
    }

    public static KalingResult willpower(int willpower) {
        KalingResult r = new KalingResult(KalingResultType.Willpower);
        r.willpower = willpower;
        return r;
    }

    public static KalingResult seasons(int[] seasons, boolean[] flags) {
        KalingResult r = new KalingResult(KalingResultType.Seasons);
        r.seasons = seasons;
        r.flags = flags;
        return r;
    }

    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(type.getVal());
        switch (type) {
            case Willpower:
                outPacket.encodeInt(willpower);
                break;
            case Seasons:
                for (int i = 0; i < SEASONS; i++) {
                    outPacket.encodeInt(seasons[i]);
                    outPacket.encodeByte(flags[i]);
                }
                break;
        }
    }
}
