package net.swordie.ms.life;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.drop.Drop;
import net.swordie.ms.loaders.ReactorData;
import net.swordie.ms.loaders.containerclasses.ReactorInfo;
import net.swordie.ms.scripts.ScriptType;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * The official "drop an item on it" reactors (Reactor.wz state events of type 100, e.g. Surl's water tank) in
 * normal maps. An item dropped in a reactor's area that its current state waits for is taken, the reactor moves on to
 * the event's state, and its action script runs (the script can check {@code reactor.getState()}). A type 101 event
 * takes it back to an earlier state when nothing more is dropped within the state's timeOut.
 * <p>
 * Maps of an instance (party quests, bosses) are left alone: their instance helpers handle their own drops.
 * Data: dat/reactorItemEvents.tsv, made from Reactor.wz by reactorevents.py.
 */
public final class ReactorItemEvents {

    private static final Logger log = LogManager.getLogger(ReactorItemEvents.class);
    private static final Path FILE = Paths.get(ServerConstants.DAT_DIR, "reactorItemEvents.tsv");

    /** One item event of a reactor state; timeoutMs/back: the state's timeout and where it goes then (-1 none). */
    record Ev(int state, int item, int count, int next, int ltX, int ltY, int rbX, int rbY, int timeoutMs, int back) {
    }

    /** Reactors another handler already takes items for (SeasonalEvents: the Haunted Mansion's Candle Chests). */
    private static final java.util.Set<Integer> HANDLED_ELSEWHERE = java.util.Set.of(2292008);

    private static volatile Map<Integer, List<Ev>> table;
    private static final Map<Reactor, ScheduledFuture<?>> timeouts = new ConcurrentHashMap<>();

    private ReactorItemEvents() {
    }

    private static Map<Integer, List<Ev>> table() {
        Map<Integer, List<Ev>> t = table;
        if (t != null) {
            return t;
        }
        synchronized (ReactorItemEvents.class) {
            if (table != null) {
                return table;
            }
            Map<Integer, List<Ev>> res = new HashMap<>();
            try {
                if (Files.exists(FILE)) {
                    for (String line : Files.readAllLines(FILE, StandardCharsets.UTF_8)) {
                        if (line.isBlank() || line.startsWith("#")) {
                            continue;
                        }
                        String[] f = line.split("\t");
                        if (f.length < 11) {
                            continue;
                        }
                        int[] v = new int[11];
                        for (int i = 0; i < 11; i++) {
                            v[i] = Integer.parseInt(f[i].trim());
                        }
                        res.computeIfAbsent(v[0], k -> new ArrayList<>())
                                .add(new Ev(v[1], v[2], v[3], v[4], v[5], v[6], v[7], v[8], v[9], v[10]));
                    }
                } else {
                    log.warn("[ReactorItemEvents] " + FILE + " is missing: no item-drop reactors");
                }
            } catch (Exception e) {
                log.error("[ReactorItemEvents] Couldn't read " + FILE, e);
                res = Collections.emptyMap();
            }
            table = res;
            return res;
        }
    }

    /**
     * Called for every item a player drops (Events#onItemDropped) in a map that isn't part of an instance.
     *
     * @return whether a reactor took the item
     */
    public static boolean onItemDropped(Char chr, Item item, int quantity, Position position, Drop drop) {
        Field field = chr == null ? null : chr.getField();
        if (field == null || field.getInstance() != null || item == null || drop == null || position == null) {
            return false;
        }
        Map<Integer, List<Ev>> t = table();
        if (t.isEmpty()) {
            return false;
        }
        for (Reactor r : new ArrayList<>(field.getReactors())) {
            List<Ev> evs = t.get(r.getTemplateId());
            if (evs == null || HANDLED_ELSEWHERE.contains(r.getTemplateId())) {
                continue;
            }
            for (Ev e : evs) {
                if (e.state() == r.getState() && e.item() == item.getItemId() && quantity >= Math.max(1, e.count())
                        && inArea(r, e, position)) {
                    // a moment later, once it has landed (as SeasonalEvents does), unless someone picked it up again
                    EventManager.addEvent(() -> {
                        try {
                            if (field.getLifeByObjectID(drop.getObjectId()) != drop || r.getState() != e.state()
                                    || r.getField() != field || !drop.tryClaim()) {
                                return;
                            }
                            take(chr, field, r, e, drop);
                        } catch (Exception ex) {
                            log.error("[ReactorItemEvents] item " + e.item() + " on reactor " + r.getTemplateId(), ex);
                        }
                    }, 1200);
                    return true;
                }
            }
        }
        return false;
    }

    /** Whether the drop landed in the event's area around the reactor (lt/rb relative to it). */
    private static boolean inArea(Reactor r, Ev e, Position p) {
        int x = r.getPosition().getX();
        int y = r.getPosition().getY();
        if (e.rbX() - e.ltX() < 10 || e.rbY() - e.ltY() < 10) {
            // a point rather than an area: anywhere close to the reactor
            return Math.abs(p.getX() - x) <= 90 && Math.abs(p.getY() - y) <= 120;
        }
        // the item lands on the ground below where it was dropped: some room up and down
        return p.getX() >= x + e.ltX() && p.getX() <= x + e.rbX()
                && p.getY() >= y + e.ltY() - 40 && p.getY() <= y + e.rbY() + 40;
    }

    private static synchronized void take(Char chr, Field field, Reactor r, Ev e, Drop drop) {
        field.removeDrop(drop.getObjectId(), 0, false, -1);
        byte next = (byte) e.next();
        r.setState(next);
        r.broadcastChangeState();
        ScheduledFuture<?> old = timeouts.remove(r);
        if (old != null) {
            old.cancel(false);
        }
        // the new state's timeout: back to an earlier state when nothing more comes
        for (Ev n : table().getOrDefault(r.getTemplateId(), List.of())) {
            if (n.state() == next && n.timeoutMs() > 0 && n.back() >= 0) {
                byte back = (byte) n.back();
                timeouts.put(r, EventManager.addEvent(() -> {
                    timeouts.remove(r);
                    if (r.getField() == field && r.getState() == next) {
                        r.setState(back);
                        r.broadcastChangeState();
                    }
                }, n.timeoutMs(), TimeUnit.MILLISECONDS));
                break;
            }
        }
        String action = action(r.getTemplateId());
        if (action != null) {
            chr.getScriptManager().startScript(r.getTemplateId(), r.getObjectId(), action, ScriptType.Reactor);
        }
    }

    /** The reactor's action script: its own, else that of the reactor it links to. */
    private static String action(int templateId) {
        ReactorInfo ri = ReactorData.getReactorInfoByID(templateId);
        String a = ri == null || ri.getAction() == null ? "" : ri.getAction();
        if (a.isBlank() && ri != null && ri.getLink() > 0) {
            ReactorInfo linked = ReactorData.getReactorInfoByID(ri.getLink());
            a = linked == null || linked.getAction() == null ? "" : linked.getAction();
        }
        return a.isBlank() ? null : a;
    }
}
