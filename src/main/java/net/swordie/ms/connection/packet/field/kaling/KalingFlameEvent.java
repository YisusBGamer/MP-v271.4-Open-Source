package net.swordie.ms.connection.packet.field.kaling;

import net.swordie.ms.connection.OutPacket;

/**
 * Kaling's black flames and shock-zone rules.
 * <ul>
 *     <li>Flames (client 0x142499880): 24-int config (attack delay, trigger delay, enhanced attack delay, enhanced
 *     trigger delay, unused, trigger/hit/enhanced trigger/enhanced hit boxes as lt.x lt.y rb.x rb.y, refMobID, attack,
 *     enhanced attack), then {id, x, y, state, token} per flame.</li>
 *     <li>FlameStates (0x142499af0): {id, state}; 0 out, 3 lit, 0x11 lit enhanced.</li>
 *     <li>FlameAttack (0x142499930): id and the flame's new token.</li>
 *     <li>ZoneRules (0x14245fed0), 8 ints: ?, tick ms, ?, hits before the stun request, refMobID, attack, stun mob
 *     skill, its level.</li>
 * </ul>
 */
public class KalingFlameEvent {

    private final KalingFlameEventType type;
    private int[] values;
    private int[][] rows;
    private int id, token;

    private KalingFlameEvent(KalingFlameEventType type) {
        this.type = type;
    }

    public static KalingFlameEvent flames(int[] config, int[][] flames) {
        KalingFlameEvent e = new KalingFlameEvent(KalingFlameEventType.Flames);
        e.values = config;
        e.rows = flames;
        return e;
    }

    public static KalingFlameEvent states(int[][] states) {
        KalingFlameEvent e = new KalingFlameEvent(KalingFlameEventType.FlameStates);
        e.rows = states;
        return e;
    }

    public static KalingFlameEvent attack(int id, int token) {
        KalingFlameEvent e = new KalingFlameEvent(KalingFlameEventType.FlameAttack);
        e.id = id;
        e.token = token;
        return e;
    }

    public static KalingFlameEvent zoneRules(int[] rules) {
        KalingFlameEvent e = new KalingFlameEvent(KalingFlameEventType.ZoneRules);
        e.values = rules;
        return e;
    }

    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(type.getVal());
        switch (type) {
            case Flames:
                encodeInts(outPacket, values);
                outPacket.encodeInt(rows.length);
                for (int[] f : rows) {
                    encodeInts(outPacket, f);
                }
                break;
            case FlameStates:
                outPacket.encodeInt(rows.length);
                for (int[] s : rows) {
                    outPacket.encodeInt(s[0]);
                    outPacket.encodeInt(s[1]);
                }
                break;
            case FlameAttack:
                outPacket.encodeInt(id);
                outPacket.encodeInt(token);
                break;
            case ZoneRules:
                encodeInts(outPacket, values);
                break;
        }
    }

    static void encodeInts(OutPacket outPacket, int[] values) {
        for (int v : values) {
            outPacket.encodeInt(v);
        }
    }
}
