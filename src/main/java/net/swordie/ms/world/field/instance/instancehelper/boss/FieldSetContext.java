package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.connection.packet.field.*;
import net.swordie.ms.connection.packet.field.kaling.*;
import net.swordie.ms.world.field.fieldskill.KalingSpiritParade;
import net.swordie.ms.world.field.fieldskill.HundunDarkCloud;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.world.field.Field;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A boss fight's field-set contexts (Mob.wz BossPattern/BossFieldSetInfo.img, Context): named values for the whole
 * field and for each player, each with its bounds. The client's boss windows read them; changes are sent as a full
 * refresh (Out 971) at most once per {@link #flush} call.
 */
public class FieldSetContext {

    private final Map<String, int[]> fieldDefs = new LinkedHashMap<>();
    private final Map<String, int[]> userDefs = new LinkedHashMap<>();
    private final Map<Integer, Map<String, int[]>> users = new LinkedHashMap<>();
    private final Map<String, String> fieldAliases = new LinkedHashMap<>();
    private final Map<String, String> userAliases = new LinkedHashMap<>();
    private boolean dirty;

    /** A role name the client looks up instead of a fixed name (BossFieldSetInfo FuncType, e.g. DeathCountKey). */
    public synchronized FieldSetContext userAlias(String role, String key) {
        userAliases.put(role, key);
        return this;
    }

    public synchronized FieldSetContext fieldAlias(String role, String key) {
        fieldAliases.put(role, key);
        return this;
    }

    /** A field context with its default value and bounds (as in BossFieldSetInfo.img). */
    public synchronized FieldSetContext field(String key, int def, int min, int max) {
        fieldDefs.put(key, new int[]{min, max, def, def});
        dirty = true;
        return this;
    }

    /** A user context: every player gets it with the default value. */
    public synchronized FieldSetContext user(String key, int def, int min, int max) {
        userDefs.put(key, new int[]{min, max, def, def});
        for (Map<String, int[]> u : users.values()) {
            u.putIfAbsent(key, new int[]{min, max, def, def});
        }
        dirty = true;
        return this;
    }

    public synchronized int get(String key) {
        int[] v = fieldDefs.get(key);
        return v == null ? 0 : v[2];
    }

    /** Sets a field context (clamped); returns the new value. */
    public synchronized int set(String key, int value) {
        int[] v = fieldDefs.get(key);
        if (v == null) {
            return 0;
        }
        int n = Math.max(v[0], Math.min(v[1], value));
        if (n != v[2]) {
            v[2] = n;
            dirty = true;
        }
        return n;
    }

    public synchronized int add(String key, int delta) {
        return set(key, get(key) + delta);
    }

    private Map<String, int[]> userMap(int charId) {
        return users.computeIfAbsent(charId, id -> {
            Map<String, int[]> m = new LinkedHashMap<>();
            userDefs.forEach((k, d) -> m.put(k, d.clone()));
            dirty = true;
            return m;
        });
    }

    public synchronized int get(Char chr, String key) {
        int[] v = userMap(chr.getId()).get(key);
        return v == null ? 0 : v[2];
    }

    public synchronized int set(Char chr, String key, int value) {
        int[] v = userMap(chr.getId()).get(key);
        if (v == null) {
            return 0;
        }
        int n = Math.max(v[0], Math.min(v[1], value));
        if (n != v[2]) {
            v[2] = n;
            dirty = true;
        }
        return n;
    }

    public synchronized int add(Char chr, String key, int delta) {
        return set(chr, key, get(chr, key) + delta);
    }

    /** Makes sure a player has its user contexts (on entering the fight). */
    public synchronized void join(Char chr) {
        userMap(chr.getId());
    }

    /** Sends the store to everyone in the field if anything changed (or always when forced). */
    public void flush(Field field, boolean force) {
        if (field == null) {
            return;
        }
        var packet = packetIfDirty(force);
        if (packet != null) {
            field.broadcastPacket(packet);
        }
    }

    /** Sends the store to one player (after it entered a fight map). */
    public void send(Char chr) {
        net.swordie.ms.connection.OutPacket packet;
        net.swordie.ms.connection.OutPacket aliases = null;
        synchronized (this) {
            userMap(chr.getId());
            packet = FieldPacket.fieldSetContext(copy(fieldDefs), copyUsers());
            if (!fieldAliases.isEmpty() || !userAliases.isEmpty()) {
                aliases = FieldPacket.fieldSetContextAlias(new LinkedHashMap<>(fieldAliases),
                        new LinkedHashMap<>(userAliases));
            }
        }
        chr.write(packet);
        if (aliases != null) {
            chr.write(aliases); // the client keeps aliases only once the store exists
        }
    }

    private synchronized net.swordie.ms.connection.OutPacket packetIfDirty(boolean force) {
        if (!dirty && !force) {
            return null;
        }
        dirty = false;
        return FieldPacket.fieldSetContext(copy(fieldDefs), copyUsers());
    }

    private static Map<String, int[]> copy(Map<String, int[]> m) {
        Map<String, int[]> c = new LinkedHashMap<>();
        m.forEach((k, v) -> c.put(k, v.clone()));
        return c;
    }

    private Map<Integer, Map<String, int[]>> copyUsers() {
        Map<Integer, Map<String, int[]>> c = new LinkedHashMap<>();
        users.forEach((id, m) -> c.put(id, copy(m)));
        return c;
    }
}
