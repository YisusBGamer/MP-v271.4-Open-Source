package net.swordie.ms.connection.packet.field;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;

/**
 * Packets of the Grandis boss fields (v265.3 client: Kaling OnPacket 0x14245eb00, Limbo 0x1422155f0, Baldrix
 * 0x1417a63d0).
 */
public class GrandisBossFieldPacket {

    /** Kaling: the party's Willpower (the "mental" gauge of Etc/BossKaring.img). */
    public static OutPacket kalingWillpower(int willpower) {
        OutPacket outPacket = new OutPacket(OutHeader.KALING_FIELD_RESULT);
        outPacket.encodeInt(0); // type 0: Willpower
        outPacket.encodeInt(willpower);
        return outPacket;
    }

    /** Kaling: the Spring, Summer and Fall gauges (perilsGauge UI, keys 0-2). */
    public static OutPacket kalingSeasons(int[] seasons, boolean[] flags) {
        OutPacket outPacket = new OutPacket(OutHeader.KALING_FIELD_RESULT);
        outPacket.encodeInt(1); // type 1: seasons
        for (int i = 0; i < 3; i++) {
            outPacket.encodeInt(seasons[i]);
            outPacket.encodeByte(flags[i]);
        }
        return outPacket;
    }

    /**
     * Kaling: Vengeful Spirits, field skill 100029 (FIELD_SKILL; object 0x142b86590, decode 0x142b865c0). Per type
     * 9 ints: index, lt.x, lt.y, rb.x, rb.y, delay, attackCount, refMobID, refMobAttack; per point 4 ints: x, y, delay,
     * type. The client skips every point while no monster of refMobID is in the map (0x14369a700), then hits as that
     * monster's attack. The first int's meaning is unknown (kept with each point; a serial is sent).
     *
     * @param types  {lt.x, lt.y, rb.x, rb.y, delay, attackCount, refMobID, refMobAttack} per type
     * @param points {x, y, delay, type} per point
     */
    public static OutPacket kalingSpiritParade(int slv, int serial, int startDelay, int[][] types, int[][] points) {
        OutPacket outPacket = new OutPacket(OutHeader.FIELD_SKILL);
        outPacket.encodeInt(100029);
        outPacket.encodeInt(slv);
        outPacket.encodeInt(serial);
        outPacket.encodeInt(startDelay);
        outPacket.encodeInt(types.length);
        for (int i = 0; i < types.length; i++) {
            outPacket.encodeInt(i);
            for (int v : types[i]) {
                outPacket.encodeInt(v);
            }
        }
        outPacket.encodeInt(points.length);
        for (int[] p : points) {
            for (int v : p) {
                outPacket.encodeInt(v);
            }
        }
        return outPacket;
    }

    /**
     * Kaling's black flames (KALING_FIELD_EVENT 0x3e9; client 0x14245ee20, config 0x140357990, flame 0x142499880).
     * Config, 24 ints: attack delay, trigger delay, enhanced attack delay, enhanced trigger delay, an unused int,
     * trigger box, hit box, enhanced trigger box, enhanced hit box (lt.x, lt.y, rb.x, rb.y each), refMobID, attack,
     * enhanced attack. A flame hits the local user with refMobID's attack (that monster must be in the map).
     *
     * @param flames {id, x, y, state, value} per flame; value is a token the client only stores (0x3eb replaces it) and
     *               sends back with its trigger request (KALING_FIELD_REQUEST 1)
     */
    public static OutPacket kalingFlames(int[] config, int[][] flames) {
        OutPacket outPacket = new OutPacket(OutHeader.KALING_FIELD_EVENT);
        outPacket.encodeInt(0x3e9);
        for (int v : config) {
            outPacket.encodeInt(v);
        }
        outPacket.encodeInt(flames.length);
        for (int[] f : flames) {
            for (int v : f) {
                outPacket.encodeInt(v);
            }
        }
        return outPacket;
    }

    /**
     * Black flame states (0x3ea; 0x142499af0). The client maps the state sent by the flame's current one: 0 puts it
     * out, 3 lights it (ready, triggers on a user in the trigger box), 0x11 lights it enhanced.
     *
     * @param states {id, state} per flame
     */
    public static OutPacket kalingFlameStates(int[][] states) {
        OutPacket outPacket = new OutPacket(OutHeader.KALING_FIELD_EVENT);
        outPacket.encodeInt(0x3ea);
        outPacket.encodeInt(states.length);
        for (int[] s : states) {
            outPacket.encodeInt(s[0]);
            outPacket.encodeInt(s[1]);
        }
        return outPacket;
    }

    /** A black flame attacks (0x3eb; 0x142499930): int id, int value (the flame's new token). */
    public static OutPacket kalingFlameAttack(int id, int value) {
        OutPacket outPacket = new OutPacket(OutHeader.KALING_FIELD_EVENT);
        outPacket.encodeInt(0x3eb);
        outPacket.encodeInt(id);
        outPacket.encodeInt(value);
        return outPacket;
    }

    /**
     * Shock-zone rules (0x3ec; tick 0x14245fed0), 8 ints: ?, tick ms, ?, hits before the stun request, refMobID,
     * attack, stun mob skill, its level. While the local user stands in a zone it takes refMobID's attack every tick;
     * after the set number of hits it sends KALING_FIELD_REQUEST 0x3ed.
     */
    public static OutPacket kalingZoneRules(int[] rules) {
        OutPacket outPacket = new OutPacket(OutHeader.KALING_FIELD_EVENT);
        outPacket.encodeInt(0x3ec);
        for (int v : rules) {
            outPacket.encodeInt(v);
        }
        return outPacket;
    }

    /** Kaling's shock zones appear (KALING_ELECTRIC_EVENT 9; 0x142463580): {id, type 0-2, x, y} per zone. */
    public static OutPacket kalingZonesAdd(int[][] zones) {
        OutPacket outPacket = new OutPacket(OutHeader.KALING_ELECTRIC_EVENT);
        outPacket.encodeInt(9);
        outPacket.encodeInt(zones.length);
        for (int[] z : zones) {
            for (int v : z) {
                outPacket.encodeInt(v);
            }
        }
        return outPacket;
    }

    /** Shock zones fade (KALING_ELECTRIC_EVENT 10; 0x1424637e0): the given ids, or all with null. */
    public static OutPacket kalingZonesEnd(int[] ids) {
        OutPacket outPacket = new OutPacket(OutHeader.KALING_ELECTRIC_EVENT);
        outPacket.encodeInt(10);
        outPacket.encodeByte(ids == null);
        if (ids != null) {
            outPacket.encodeInt(ids.length);
            for (int id : ids) {
                outPacket.encodeInt(id);
            }
        }
        return outPacket;
    }

    /**
     * Taowu's tesla coils (KALING_ELECTRIC_EVENT 0; 0x1424622b0 case 0). int Taowu's template id (the client finds him
     * by it), coils {x, y, int ?, byte active}, links {coil a, coil b, int ?} (drawn while both coils are active),
     * triangles {coil a, b, c, byte active} (the client checks Taowu against an active one and reports it in
     * KALING_ELECTRIC_REQUEST 4), an int passed to every mini coil's layer (meaning unknown), mini coils
     * {key, x, y, byte used}.
     */
    public static OutPacket kalingTeslaCoils(int taowuTemplateId, int[][] coils, int[][] links, int[][] triangles,
                                             int miniCoilParam, int[][] miniCoils) {
        OutPacket outPacket = new OutPacket(OutHeader.KALING_ELECTRIC_EVENT);
        outPacket.encodeInt(0);
        outPacket.encodeInt(taowuTemplateId);
        outPacket.encodeInt(coils.length);
        for (int[] c : coils) {
            outPacket.encodeInt(c[0]);
            outPacket.encodeInt(c[1]);
            outPacket.encodeInt(c[2]);
            outPacket.encodeByte(c[3] != 0);
        }
        outPacket.encodeInt(links.length);
        for (int[] l : links) {
            outPacket.encodeInt(l[0]);
            outPacket.encodeInt(l[1]);
            outPacket.encodeInt(l[2]);
        }
        outPacket.encodeInt(triangles.length);
        for (int[] t : triangles) {
            outPacket.encodeInt(t[0]);
            outPacket.encodeInt(t[1]);
            outPacket.encodeInt(t[2]);
            outPacket.encodeByte(t[3] != 0);
        }
        outPacket.encodeInt(miniCoilParam);
        outPacket.encodeInt(miniCoils.length);
        for (int[] m : miniCoils) {
            outPacket.encodeInt(m[0]);
            outPacket.encodeInt(m[1]);
            outPacket.encodeInt(m[2]);
            outPacket.encodeByte(m[3] != 0);
        }
        return outPacket;
    }

    /** Tesla coils switch on or off (KALING_ELECTRIC_EVENT 1): byte on, the coil indexes. */
    public static OutPacket kalingCoilStates(boolean on, int[] coils) {
        OutPacket outPacket = new OutPacket(OutHeader.KALING_ELECTRIC_EVENT);
        outPacket.encodeInt(1);
        outPacket.encodeByte(on);
        outPacket.encodeInt(coils.length);
        for (int c : coils) {
            outPacket.encodeInt(c);
        }
        return outPacket;
    }

    /** A coil triangle's current starts or stops (KALING_ELECTRIC_EVENT 3): int index, byte active. */
    public static OutPacket kalingCurrent(int triangle, boolean active) {
        OutPacket outPacket = new OutPacket(OutHeader.KALING_ELECTRIC_EVENT);
        outPacket.encodeInt(3);
        outPacket.encodeInt(triangle);
        outPacket.encodeByte(active);
        return outPacket;
    }

    /** Taowu stands in a current or leaves it (KALING_ELECTRIC_EVENT 4): his effect and the weakened icon. */
    public static OutPacket kalingTaowuInCurrent(boolean in) {
        OutPacket outPacket = new OutPacket(OutHeader.KALING_ELECTRIC_EVENT);
        outPacket.encodeInt(4);
        outPacket.encodeByte(in);
        return outPacket;
    }

    /** A mini coil is used (KALING_ELECTRIC_EVENT 5): int key, the shock zones it takes in. */
    public static OutPacket kalingMiniCoilUsed(int key, int[] zoneIds) {
        OutPacket outPacket = new OutPacket(OutHeader.KALING_ELECTRIC_EVENT);
        outPacket.encodeInt(5);
        outPacket.encodeInt(key);
        outPacket.encodeInt(zoneIds.length);
        for (int id : zoneIds) {
            outPacket.encodeInt(id);
        }
        return outPacket;
    }

    /** A mini coil can be used again (KALING_ELECTRIC_EVENT 6): int key. */
    public static OutPacket kalingMiniCoilReady(int key) {
        OutPacket outPacket = new OutPacket(OutHeader.KALING_ELECTRIC_EVENT);
        outPacket.encodeInt(6);
        outPacket.encodeInt(key);
        return outPacket;
    }

    /**
     * Taowu's aggro target (KALING_ELECTRIC_EVENT 7): int character id, queued by the client, which sends it back in
     * KALING_ELECTRIC_REQUEST 8 while Taowu targets someone else.
     */
    public static OutPacket kalingTaowuAggro(int charId) {
        OutPacket outPacket = new OutPacket(OutHeader.KALING_ELECTRIC_EVENT);
        outPacket.encodeInt(7);
        outPacket.encodeInt(charId);
        return outPacket;
    }

    /**
     * Hundun's Dark Cloud, field skill 100028 (FIELD_SKILL; class 0x142b84aa0, decode 0x142b85180): int key (sent back
     * in FIELD_SKILL_REQUEST), int the boss's object id, x, y, int ms the boss keeps heading for it. Box and art come
     * from Skill.wz FieldSkill.img/100028/level/slv.
     */
    public static OutPacket darkCloud(int slv, int key, int mobObjectId, int x, int y, int durationMs) {
        OutPacket outPacket = new OutPacket(OutHeader.FIELD_SKILL);
        outPacket.encodeInt(100028);
        outPacket.encodeInt(slv);
        outPacket.encodeInt(key);
        outPacket.encodeInt(mobObjectId);
        outPacket.encodeInt(x);
        outPacket.encodeInt(y);
        outPacket.encodeInt(durationMs);
        return outPacket;
    }

    /**
     * Limbo: one player's Erosion gauge (1000 = none taken, 0 = out) and the boss HP in permille. Phase: 0 for field
     * type 380, 1 for 381/382/384, 2 for 383 (0x14035bd20); the client ignores a packet for another phase. Type 0
     * also makes the gauge window.
     */
    public static OutPacket limboErosion(int phase, int left, int hpPermille) {
        OutPacket outPacket = new OutPacket(OutHeader.LIMBO_FIELD_RESULT);
        outPacket.encodeInt(0);
        outPacket.encodeInt(phase);
        outPacket.encodeInt(left);
        outPacket.encodeInt(hpPermille);
        return outPacket;
    }

    /** Limbo: an Erosion change with the hit effect (needs the window from {@link #limboErosion} first). */
    public static OutPacket limboErosionChange(int phase, int left, boolean effect) {
        OutPacket outPacket = new OutPacket(OutHeader.LIMBO_FIELD_RESULT);
        outPacket.encodeInt(1);
        outPacket.encodeInt(phase);
        outPacket.encodeInt(left);
        outPacket.encodeByte(effect);
        return outPacket;
    }

    /** Pattern-system boss fields (Baldrix): the 4-digit death count number; effect plays the change animation. */
    public static OutPacket patternDeathCount(int value, boolean effect) {
        OutPacket outPacket = new OutPacket(OutHeader.BOSS_PATTERN_FIELD_RESULT);
        outPacket.encodeInt(7);
        outPacket.encodeByte(effect);
        outPacket.encodeInt(value);
        return outPacket;
    }

    /**
     * The field-set context store (v271 Out 971): every field context and every player's user contexts with their
     * bounds. The client keeps it per field; the boss windows (Malefic Star's gauges, Kai's Chrono Gauge) read the
     * values from it. Sending it again replaces the values.
     *
     * @param field name -> {min, max, value}
     * @param users character id -> (name -> {min, max, value})
     */
    public static OutPacket fieldSetContext(java.util.Map<String, int[]> field,
                                            java.util.Map<Integer, java.util.Map<String, int[]>> users) {
        OutPacket outPacket = new OutPacket(OutHeader.FIELD_SET_CONTEXT_INIT);
        encodeContexts(outPacket, field);
        outPacket.encodeInt(0); // running changes
        outPacket.encodeInt(users.size());
        for (var e : users.entrySet()) {
            outPacket.encodeInt(e.getKey());
            encodeContexts(outPacket, e.getValue());
            outPacket.encodeInt(0);
        }
        return outPacket;
    }

    /**
     * Out 972: role -> context name aliases for the field and for users (e.g. DeathCountKey -> DeathCount, which the
     * boss windows' death counter reads), then two name sets (sent empty). Needs the store, so send it after 971.
     */
    public static OutPacket fieldSetContextAlias(java.util.Map<String, String> field,
                                                 java.util.Map<String, String> user) {
        OutPacket outPacket = new OutPacket(OutHeader.FIELD_SET_CONTEXT_ALIAS);
        for (var m : java.util.List.of(field, user)) {
            outPacket.encodeInt(m.size());
            m.forEach((k, v) -> {
                outPacket.encodeString(k);
                outPacket.encodeString(v);
            });
        }
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        return outPacket;
    }

    private static void encodeContexts(OutPacket outPacket, java.util.Map<String, int[]> contexts) {
        outPacket.encodeInt(contexts.size());
        for (var e : contexts.entrySet()) {
            int[] v = e.getValue();
            outPacket.encodeString(e.getKey());
            outPacket.encodeInt(0);
            outPacket.encodeInt(v[1]); // max
            outPacket.encodeInt(v[0]); // min
            outPacket.encodeInt(v[2]); // value
        }
    }
}
