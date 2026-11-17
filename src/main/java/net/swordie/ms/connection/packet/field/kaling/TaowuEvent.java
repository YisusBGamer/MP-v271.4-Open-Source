package net.swordie.ms.connection.packet.field.kaling;

import net.swordie.ms.connection.OutPacket;

/**
 * Taowu's map: tesla coils, currents, mini coils and shock zones.
 * <ul>
 *     <li>TeslaCoils: Taowu's template id, coils {x, y, ?, active}, links {a, b, ?}, triangles {a, b, c, active},
 *     an int passed to every mini coil's layer, mini coils {key, x, y, used}.</li>
 *     <li>Attack: byte normal, int attack 0-3 (or the special attack value), [byte facing when normal]; the client
 *     answers with KALING_ELECTRIC_REQUEST 2.</li>
 *     <li>ZonesAdd (0x142463580): {id, type 0-2, x, y}; ZonesEnd (0x1424637e0): the ids, or all.</li>
 *     <li>ZoneAck: accepts the zones a client reported under the key; Timer: client timer ms; TimerResult: the
 *     answer to request 12.</li>
 * </ul>
 */
public class TaowuEvent {

    private final TaowuEventType type;
    private int arg;
    private boolean flag, facing;
    private int[] ids;
    private int[][] rows, links, triangles, miniCoils;
    private int miniCoilParam;

    private TaowuEvent(TaowuEventType type) {
        this.type = type;
    }

    private static TaowuEvent of(TaowuEventType type, int arg) {
        TaowuEvent e = new TaowuEvent(type);
        e.arg = arg;
        return e;
    }

    public static TaowuEvent teslaCoils(int taowuTemplateId, int[][] coils, int[][] links, int[][] triangles,
                                        int miniCoilParam, int[][] miniCoils) {
        TaowuEvent e = of(TaowuEventType.TeslaCoils, taowuTemplateId);
        e.rows = coils;
        e.links = links;
        e.triangles = triangles;
        e.miniCoilParam = miniCoilParam;
        e.miniCoils = miniCoils;
        return e;
    }

    public static TaowuEvent coilStates(boolean on, int[] coils) {
        TaowuEvent e = new TaowuEvent(TaowuEventType.CoilStates);
        e.flag = on;
        e.ids = coils;
        return e;
    }

    public static TaowuEvent attack(boolean normal, int value, boolean facing) {
        TaowuEvent e = of(TaowuEventType.Attack, value);
        e.flag = normal;
        e.facing = facing;
        return e;
    }

    public static TaowuEvent current(int triangle, boolean active) {
        TaowuEvent e = of(TaowuEventType.Current, triangle);
        e.flag = active;
        return e;
    }

    public static TaowuEvent inCurrent(boolean in) {
        TaowuEvent e = new TaowuEvent(TaowuEventType.InCurrent);
        e.flag = in;
        return e;
    }

    public static TaowuEvent miniCoilUsed(int key, int[] zoneIds) {
        TaowuEvent e = of(TaowuEventType.MiniCoilUsed, key);
        e.ids = zoneIds;
        return e;
    }

    public static TaowuEvent miniCoilReady(int key) {
        return of(TaowuEventType.MiniCoilReady, key);
    }

    public static TaowuEvent zoneAck(int key) {
        return of(TaowuEventType.ZoneAck, key);
    }

    public static TaowuEvent zonesAdd(int[][] zones) {
        TaowuEvent e = new TaowuEvent(TaowuEventType.ZonesAdd);
        e.rows = zones;
        return e;
    }

    /** @param ids the zones that fade, or null for all */
    public static TaowuEvent zonesEnd(int[] ids) {
        TaowuEvent e = new TaowuEvent(TaowuEventType.ZonesEnd);
        e.ids = ids;
        return e;
    }

    public static TaowuEvent timer(int ms) {
        return of(TaowuEventType.Timer, ms);
    }

    public static TaowuEvent timerResult(boolean value) {
        TaowuEvent e = new TaowuEvent(TaowuEventType.TimerResult);
        e.flag = value;
        return e;
    }

    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(type.getVal());
        switch (type) {
            case TeslaCoils:
                outPacket.encodeInt(arg);
                encodeRows(outPacket, rows, true);
                encodeRows(outPacket, links, false);
                encodeRows(outPacket, triangles, true);
                outPacket.encodeInt(miniCoilParam);
                encodeRows(outPacket, miniCoils, true);
                break;
            case CoilStates:
                outPacket.encodeByte(flag);
                encodeIds(outPacket, ids);
                break;
            case Attack:
                outPacket.encodeByte(flag);
                outPacket.encodeInt(arg);
                if (flag) {
                    outPacket.encodeByte(facing);
                }
                break;
            case Current:
                outPacket.encodeInt(arg);
                outPacket.encodeByte(flag);
                break;
            case InCurrent:
            case TimerResult:
                outPacket.encodeByte(flag);
                break;
            case MiniCoilUsed:
                outPacket.encodeInt(arg);
                encodeIds(outPacket, ids);
                break;
            case MiniCoilReady:
            case ZoneAck:
            case Timer:
                outPacket.encodeInt(arg);
                break;
            case ZonesAdd:
                outPacket.encodeInt(rows.length);
                for (int[] z : rows) {
                    KalingFlameEvent.encodeInts(outPacket, z);
                }
                break;
            case ZonesEnd:
                outPacket.encodeByte(ids == null);
                if (ids != null) {
                    encodeIds(outPacket, ids);
                }
                break;
        }
    }

    /** Rows of three ints, plus the fourth value as a byte flag when flagged. */
    private static void encodeRows(OutPacket outPacket, int[][] rows, boolean flagged) {
        outPacket.encodeInt(rows.length);
        for (int[] r : rows) {
            outPacket.encodeInt(r[0]);
            outPacket.encodeInt(r[1]);
            outPacket.encodeInt(r[2]);
            if (flagged) {
                outPacket.encodeByte(r[3] != 0);
            }
        }
    }

    private static void encodeIds(OutPacket outPacket, int[] ids) {
        outPacket.encodeInt(ids.length);
        for (int id : ids) {
            outPacket.encodeInt(id);
        }
    }
}
