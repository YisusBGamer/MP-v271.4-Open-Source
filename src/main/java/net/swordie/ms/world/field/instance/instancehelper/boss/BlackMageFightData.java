package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.jwzlib.WzFile;
import net.swordie.ms.jwzlib.WzObject;
import net.swordie.ms.jwzlib.WzProperty;
import net.swordie.ms.loaders.DataTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * The Black Mage fight's schedule (resources/data/blackmage-fight.tsv, by mode and phase) and the crimson columns of
 * its phase 2 timer (Skill.wz FieldSkill.img 100017 BMTrigger2).
 */
public final class BlackMageFightData {

    private static final Logger log = LogManager.getLogger(BlackMageFightData.class);

    public enum Kind {holder, linked, ref, skill, attack, wave, timer, timerskill, walls, trigger, marks, notice, popup, clear}

    /** One row; times in ms from the phase's start. */
    public record Entry(Kind kind, int id, int level, int x, int y, int first, int period, int action, int actor,
                        int arg, int lead, int ms, int[] xs, int[] xs2, String text) {
    }

    /** A column of the crimson lightning: a segment with a width. */
    public record Column(int sx, int sy, int ex, int ey, int width) {
        boolean contains(int x, int y) {
            double dx = ex - sx, dy = ey - sy, len2 = dx * dx + dy * dy;
            double t = len2 == 0 ? 0 : Math.max(0, Math.min(1, ((x - sx) * dx + (y - sy) * dy) / len2));
            double px = sx + t * dx - x, py = sy + t * dy - y;
            return px * px + py * py <= (width / 2.0) * (width / 2.0);
        }
    }

    /** One BMTrigger2 set: picked by the boss's action, its columns strike delay ms after actionDelay. */
    public record Trigger(int index, int action, int actionDelay, int delay, int duration, List<Column> columns) {
        public boolean hits(int x, int y) {
            for (Column c : columns) {
                if (c.contains(x, y)) {
                    return true;
                }
            }
            return false;
        }
    }

    private static final Supplier<Map<String, Map<Integer, List<Entry>>>> FIGHT = DataTable.cached("blackmage-fight",
            BlackMageFightData::build);
    private static final Map<String, List<Trigger>> TRIGGERS = new ConcurrentHashMap<>();

    private BlackMageFightData() {
    }

    private static Map<String, Map<Integer, List<Entry>>> build(DataTable t) {
        Map<String, Map<Integer, List<Entry>>> res = new HashMap<>();
        for (DataTable.Row r : t.rows()) {
            try {
                Entry e = new Entry(Kind.valueOf(r.str("kind").trim()), r.i("id", 0), r.i("level", 0), r.i("x", 0),
                        r.i("y", 0), r.i("first", 0), r.i("period", 0), r.i("action", -1), r.i("actor", 0),
                        r.i("arg", 0), r.i("lead", 0), r.i("ms", 0), r.ints("xs"), r.ints("xs2"), r.str("text"));
                res.computeIfAbsent(r.str("mode").trim(), k -> new HashMap<>())
                        .computeIfAbsent(r.i("phase"), k -> new ArrayList<>()).add(e);
            } catch (RuntimeException ex) {
                log.error("blackmage-fight.tsv line " + r.line() + " skipped", ex);
            }
        }
        return res;
    }

    /** The rows of one mode's phase (1-4); empty when the table has none. */
    public static List<Entry> phase(String mode, int phase) {
        return FIGHT.get().getOrDefault(mode, Collections.emptyMap()).getOrDefault(phase, Collections.emptyList());
    }

    /** The first row of a kind in a phase, or null. */
    public static Entry first(String mode, int phase, Kind kind) {
        for (Entry e : phase(mode, phase)) {
            if (e.kind() == kind) {
                return e;
            }
        }
        return null;
    }

    /** BMTrigger2 of a field skill level; read once, empty when the data is missing. */
    public static List<Trigger> triggers(int skillId, int slv) {
        return TRIGGERS.computeIfAbsent(skillId + "/" + slv, k -> readTriggers(skillId, slv));
    }

    private static List<Trigger> readTriggers(int skillId, int slv) {
        List<Trigger> res = new ArrayList<>();
        try {
            WzObject<?, ?> img = new WzFile(ServerConstants.WZ_DIR + "/Skill.wz").getChild("FieldSkill.img");
            WzObject<?, ?> sets = img == null ? null : img.getChildByPath(skillId + "/level/" + slv + "/BMTrigger2");
            if (sets == null) {
                log.warn("FieldSkill.img " + skillId + " level " + slv + " has no BMTrigger2");
                return res;
            }
            for (WzObject<?, ?> set : sets) {
                WzObject<?, ?> p1 = (WzObject<?, ?>) set.getChild("phase1");
                List<Column> cols = new ArrayList<>();
                WzObject<?, ?> areas = p1 == null ? null : (WzObject<?, ?>) p1.getChild("area");
                if (areas != null) {
                    for (WzObject<?, ?> a : areas) {
                        Point s = vec(a.getChild("start")), e = vec(a.getChild("end"));
                        cols.add(new Column(s.x, s.y, e.x, e.y, num(a.getChild("width"))));
                    }
                }
                res.add(new Trigger(Integer.parseInt(set.getName()), num(set.getChild("action")),
                        num(set.getChild("actionDelay")), p1 == null ? 0 : num(p1.getChild("delay")),
                        p1 == null ? 0 : num(p1.getChild("duration")), cols));
            }
        } catch (Exception e) {
            log.error("Could not read FieldSkill.img " + skillId + " level " + slv + " BMTrigger2", e);
        }
        return res;
    }

    private static int num(Object node) {
        return node instanceof WzProperty<?> p ? p.getIntValue() : 0;
    }

    private static Point vec(Object node) {
        return node instanceof WzProperty<?> p && p.getValue() instanceof Point pt ? pt : new Point();
    }
}
