package net.swordie.ms.client.character;

import net.swordie.ms.client.character.avatar.AvatarLook;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.handlers.header.OutHeader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Salon mannequins: saved hair (type 0), face (1) and skin (2) looks per character.
 * <ul>
 *     <li>CharacterData: three containers (v271 reader 0x140781980, writer 0x1407818E0): byte, byte, then
 *     48 x (byte has, [byte flag, int style, int extra]) for every type. The second byte is the slot limit
 *     (0x140781BF0 rejects slot &gt;= it); the first is the slot count the window shows (0x142E902A0 clamps it to
 *     42..48 for hair/face and to 12 for skin, which is why 42/42 and 12/12 are what a fresh account gets).</li>
 *     <li>USER_MANNEQUIN 833 (0x142E93BD0): int tick, byte type, byte action, byte slot; 2 = save the current look
 *     (byte v, byte f(v): 2 for a Zero/AB second look), 3 = delete (the slot's record), 4 = swap the slot with the
 *     current look (byte v, byte f(v)).</li>
 *     <li>MANNEQUIN_RESULT 425 (0x145AF6520): byte type, byte, int code; 1/2 quiet, -6 full, -7 nothing saved, -8 the
 *     same look, -9 other gender, -10 can't be applied, -11 already saved, -100 can't do that now.</li>
 * </ul>
 * The list in the window comes from CharacterData, so a change is followed by MANNEQUIN_UPDATE 426 mode 0, which
 * replaces that type's container and refreshes the open window.
 */
public class MannequinModule {

    public static final int TYPES = 3;
    /** Records per container: the v271 reader/writer always read and write 48 (v265 had 42). */
    public static final int SLOTS = 48;

    private static final int RES_OK = 1;
    private static final int RES_APPLIED = 2;
    private static final int RES_FULL = -6;
    private static final int RES_NONE = -7;
    private static final int RES_SAME = -8;
    private static final int RES_ALREADY = -11;
    private static final int RES_CANT = -100;

    private static final class Look {
        int flag;
        int style;
        int extra;
    }

    /** Slots a fresh account has: 42 hair/face, 12 skin (the window's own clamp in 0x142E902A0). */
    public static int openSlots(int type) {
        return type == 2 ? 12 : 42;
    }

    /** char id -> type * SLOTS + slot -> look */
    private static final Map<Integer, Map<Integer, Look>> chars = new ConcurrentHashMap<>();

    private static Map<Integer, Look> looks(Char chr) {
        return chars.computeIfAbsent(chr.getId(), id -> {
            Map<Integer, Look> map = new ConcurrentHashMap<>();
            for (Map<String, Object> row : DatabaseManager.queryRows("SELECT type AS t, slot AS s, flag AS f, "
                    + "styleid AS i, extra AS e FROM char_mannequins WHERE charid = ?", id)) {
                Look l = new Look();
                l.flag = ((Number) row.get("f")).intValue();
                l.style = ((Number) row.get("i")).intValue();
                l.extra = ((Number) row.get("e")).intValue();
                map.put(((Number) row.get("t")).intValue() * SLOTS + ((Number) row.get("s")).intValue(), l);
            }
            return map;
        });
    }

    /** One CharacterData container. */
    public static void encode(Char chr, OutPacket out, int type) {
        Map<Integer, Look> map = chr == null || chr.getId() == 0 ? Map.of() : looks(chr);
        int open = openSlots(type);
        out.encodeByte(open); // shown slots
        out.encodeByte(open); // last selectable slot
        for (int s = 0; s < SLOTS; s++) {
            Look l = map.get(type * SLOTS + s);
            out.encodeByte(l != null);
            if (l != null) {
                out.encodeByte(l.flag);
                out.encodeInt(l.style);
                out.encodeInt(l.extra);
            }
        }
    }

    /**
     * F88, read by 0x1407A6FF0 before the containers (client selector 0x1489C0890, flag 88): the slot count
     * the Beauty Room pages through (ceil(count / 12) pages, 0x144462A00) and the saved-look list
     * (n x [4 index, 4 code, 1 mixBaseHair, 1 mixAddHair, 1 mixHairBaseProb, 4 skinHSV], 0x1407A6710).
     * The Deco window only builds its tab and slot elements while all three counts are non-zero (0x14445EBF0);
     * otherwise the first refresh dereferences the missing elements (0x144460ED0+0x35) and the client dies.
     */
    public static void encodeSalonData(OutPacket out, int type) {
        out.encodeByte(openSlots(type));
        out.encodeByte(0); // saved looks; the records are only listed from the F88 updates (s2c 738)
    }

    private static int current(Char chr, int type) {
        AvatarLook al = chr.getAvatarData().getAvatarLook();
        return switch (type) {
            case 0 -> al.getHair();
            case 1 -> al.getFace();
            default -> al.getSkin();
        };
    }

    private static void save(Char chr, int type, int slot, Look l) {
        if (l == null) {
            DatabaseManager.executeQuery("DELETE FROM char_mannequins WHERE charid = ? AND type = ? AND slot = ?",
                    chr.getId(), type, slot);
        } else {
            DatabaseManager.executeQuery("INSERT INTO char_mannequins (charid, type, slot, flag, styleid, extra) VALUES "
                            + "(?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE flag = VALUES(flag), styleid = VALUES(styleid), "
                            + "extra = VALUES(extra)", chr.getId(), type, slot, l.flag, l.style, l.extra);
        }
    }

    /**
     * MANNEQUIN_UPDATE 426 mode 0 (0x145AF6590): replaces the type's whole container and refreshes the open
     * Beauty Room in place. The window reads its list from CharacterData, which is only resent on a field change.
     */
    private static void update(Char chr, int type, int action) {
        OutPacket out = new OutPacket(OutHeader.MANNEQUIN_UPDATE);
        out.encodeByte(type);
        out.encodeByte(action);
        out.encodeByte(0);
        encode(chr, out, type);
        chr.write(out);
    }

    private static OutPacket result(int type, int action, int code) {
        OutPacket out = new OutPacket(OutHeader.MANNEQUIN_RESULT);
        out.encodeByte(type);
        out.encodeByte(action);
        out.encodeInt(code);
        return out;
    }

    /** USER_MANNEQUIN 833 */
    public static void handle(Char chr, InPacket in) {
        in.decodeInt(); // tick
        int type = in.decodeByte();
        int action = in.decodeByte();
        int slot = in.decodeByte();
        if (type < 0 || type >= TYPES || slot < 0 || slot >= openSlots(type) || chr.isZeroBeta()) {
            chr.write(result(type, action, RES_CANT));
            chr.dispose();
            return;
        }
        Map<Integer, Look> map = looks(chr);
        int key = type * SLOTS + slot;
        int code;
        switch (action) {
            case 2 -> { // save the current look
                int v = in.getUnreadAmount() > 0 ? in.decodeByte() : 0;
                int cur = current(chr, type);
                boolean dup = map.entrySet().stream().anyMatch(e -> e.getKey() / SLOTS == type && e.getValue().style == cur);
                if (dup) {
                    code = RES_ALREADY;
                } else if (map.containsKey(key)) {
                    code = RES_FULL;
                } else {
                    Look l = new Look();
                    l.flag = v;
                    l.style = cur;
                    map.put(key, l);
                    save(chr, type, slot, l);
                    code = RES_OK;
                }
            }
            case 3 -> { // delete
                if (map.remove(key) == null) {
                    code = RES_NONE;
                } else {
                    save(chr, type, slot, null);
                    code = RES_OK;
                }
            }
            case 4 -> { // swap with the current look
                Look l = map.get(key);
                int cur = current(chr, type);
                if (l == null) {
                    code = RES_NONE;
                } else if (l.style == cur) {
                    code = RES_SAME;
                } else {
                    int saved = l.style;
                    l.style = cur;
                    save(chr, type, slot, l);
                    chr.getScriptManager().changeCharacterLook(saved);
                    code = RES_APPLIED;
                }
            }
            default -> code = RES_CANT;
        }
        chr.write(result(type, action, code));
        if (code == RES_OK || code == RES_APPLIED) {
            update(chr, type, action);
        }
        chr.dispose();
    }
}
