package net.swordie.ms.world.field.instance.instancehelper.arcane;

import net.swordie.ms.connection.packet.field.ErdaSpectrumFieldPacket.ErdaSpectrumState;
import net.swordie.ms.connection.packet.field.ErdaSpectrumFieldPacket;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Erda Spectrum (Vanishing Journey, Nina 3003145), as GMS runs it:
 * <ul>
 * <li>Land of Oblivion (450001400, 10 min): every Erda monster defeated charges the Erda Collector by 1. The harvest
 * key next to it (In 1638) turns it on for 1 second per charge. Hitting it brings out Erda Clusters of its colour
 * (blue, red, purple in turn); pushed into the field of their colour (In 1640) they score 1, purple 2. 10 points end
 * the phase.</li>
 * <li>Land of Tranquility (450001500, 150 s): two Arma Jr. walk out of the caves toward the centre. Arma's Followers
 * charge the Erda Condenser; its laser (In 1639, held) pushes an Arma Jr. back into its cave. 5 pushes clear the run;
 * an Arma Jr. reaching the centre ends it.</li>
 * </ul>
 * The reward waits at Nina in 450001550 (record 34170 clear=1). Field UI: ERDA_SPECTRUM_STATE (2 running,
 * 3 collector on / done, 4 phase end) and ERDA_SPECTRUM_VALUES (time ms [, goal]).
 */
public class ErdaSpectrumInstanceHelper extends InstanceHelper {
    public static final int FIELD = 450001400;
    public static final int FIELD_2 = 450001500;
    public static final int EXIT = 450001550;
    public static final int WEEKLY_QR = 34170;
    public static final int PHASE_1_SECONDS = 600;
    public static final int PHASE_2_SECONDS = 150;
    public static final int GOAL_POINTS = 10;
    public static final int GOAL_PUSHES = 5;

    private static final int COLLECTOR = 8641018;
    private static final int RED_CLUSTER = 8641019;
    private static final int BLUE_CLUSTER = 8641020;
    private static final int PURPLE_CLUSTER = 8641021;
    private static final int[] CLUSTER_CYCLE = {BLUE_CLUSTER, RED_CLUSTER, PURPLE_CLUSTER};
    private static final int FOLLOWER = 8641029;
    private static final int ARMA_JR = 8641030;
    private static final int COLLECTOR_X = 505, GROUND_1 = 47;
    private static final int CENTER_X = -88, GROUND_2 = 177;
    private static final int[] CAVES_X = {-760, 580};
    private static final int CENTER_REACH = 60;
    private static final int CLUSTER_EVERY_MS = 1200;
    private static final int COLOUR_EVERY_MS = 3000;
    /** charged laser time that sends an Arma Jr. back into its cave */
    private static final int PUSH_MS = 10_000;
    private static final int HAPPY_ERDA_MOB = 8641022;
    private static final int JOYFUL_ERDA_MOB = 8641025;

    private int phase = 1;
    private int charge;
    private int points;
    private Mob collector;
    private long collectorEnd;
    private long lastCluster;
    private int pushes;
    private boolean done;
    /** Arma Jr. object id -> cave index */
    private final Map<Integer, Integer> armas = new HashMap<>();
    /** Arma Jr. object id -> laser time on it so far */
    private final Map<Integer, Long> laserTime = new HashMap<>();
    /** Arma Jr. object id -> when the current laser hold started (0 = not held) */
    private final Map<Integer, Long> laserSince = new HashMap<>();

    public static boolean isField(int fieldId) {
        return fieldId == FIELD;
    }

    @Override
    public boolean usesField(int fieldId) {
        return fieldId == FIELD || fieldId == FIELD_2;
    }

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        addTimer(EventManager.addFixedRateEvent(this::tick, 500, 500));
        addTimer(EventManager.addEvent(() -> {
            for (Char chr : instance.getChars()) {
                sendState(chr, ErdaSpectrumState.Running);
                sendValues(chr, PHASE_1_SECONDS * 1000, GOAL_POINTS);
            }
        }, 1500));
    }

    @Override
    protected void startStage(int stageNumber) {
        // the phases are driven by the requests and tick()
    }

    private Char player() {
        var chars = getInstance().getChars();
        return chars.isEmpty() ? null : chars.get(0);
    }

    // ------------------------------------------------------------------------------------------------- packets

    private static void sendState(Char chr, ErdaSpectrumState state) {
        chr.write(ErdaSpectrumFieldPacket.state(state));
    }

    private static void sendValues(Char chr, int timeMs, int goal) {
        chr.write(ErdaSpectrumFieldPacket.values(timeMs, goal));
    }

    private void tell(String msg) {
        for (Char chr : getInstance().getChars()) {
            chr.getScriptManager().progressMessageFont(3, 20, 0, 0, msg);
        }
    }

    private static boolean alive(Field field, Mob mob) {
        return mob != null && field.getLifeByObjectID(mob.getObjectId()) != null;
    }

    // ------------------------------------------------------------------------------------------------- phase 1

    @Override
    public void onMobDeath(Field field, Mob mob) {
        int t = mob.getTemplateId();
        if (done) {
            return;
        }
        if (phase == 1 && t >= HAPPY_ERDA_MOB && t <= JOYFUL_ERDA_MOB) {
            charge++;
        } else if (phase == 2 && t == FOLLOWER) {
            charge++;
        }
    }

    /** In 1638: the harvest key next to the Erda Collector */
    public synchronized void activateCollector(Char chr) {
        Field field = chr.getField();
        if (done || phase != 1 || field == null || field.getId() != FIELD) {
            return;
        }
        if (alive(field, collector) || charge <= 0) {
            if (charge <= 0) {
                chr.chatMessage("The Erda Collector has no Erda. Defeat the Erda around it first.");
            }
            return;
        }
        collectorEnd = System.currentTimeMillis() + charge * 1000L;
        charge = 0;
        collector = field.spawnMob(COLLECTOR, COLLECTOR_X, GROUND_1, false, 0);
        for (Char c : getInstance().getChars()) {
            sendState(c, ErdaSpectrumState.Cleared);
        }
        tell("The Erda Collector has been activated. Attack the collector to extract Erda of the collector's color.");
    }

    @Override
    public void onMobDamaged(Mob mob, Char dealer, long damage) {
        if (done || phase != 1 || collector == null || mob.getObjectId() != collector.getObjectId()) {
            return;
        }
        long now = System.currentTimeMillis();
        synchronized (this) {
            if (now - lastCluster < CLUSTER_EVERY_MS) {
                return;
            }
            lastCluster = now;
        }
        long colour = (now / COLOUR_EVERY_MS) % CLUSTER_CYCLE.length;
        int x = COLLECTOR_X + (Util.getRandom(1) == 0 ? -1 : 1) * (60 + Util.getRandom(120));
        mob.getField().spawnMob(CLUSTER_CYCLE[(int) colour], x, GROUND_1, false, 0);
    }

    /** In 1640: a cluster went into a field; ok = 1 when it was the field of its colour */
    public synchronized void clusterDelivered(Char chr, int ok, int objectId) {
        Field field = chr.getField();
        if (done || phase != 1 || field == null) {
            return;
        }
        Life life = field.getLifeByObjectID(objectId);
        if (!(life instanceof Mob cluster)) {
            return; // already counted
        }
        int t = cluster.getTemplateId();
        if (t != RED_CLUSTER && t != BLUE_CLUSTER && t != PURPLE_CLUSTER) {
            return;
        }
        field.removeLife(objectId, true);
        if (ok != 1) {
            return;
        }
        points += t == PURPLE_CLUSTER ? 2 : 1;
        chr.getScriptManager().progressMessageFont("Erda gathered: " + Math.min(points, GOAL_POINTS) + " / " + GOAL_POINTS);
        if (points >= GOAL_POINTS) {
            endPhase1(field);
        }
    }

    private void endPhase1(Field field) {
        phase = 2;
        charge = 0;
        if (alive(field, collector)) {
            field.removeLife(collector.getObjectId(), true);
        }
        collector = null;
        for (Char c : getInstance().getChars()) {
            sendState(c, ErdaSpectrumState.PhaseEnd);
        }
        tell("The Erda must have attracted them. There was a quick ripple of red light, but then nothing...");
        addTimer(EventManager.addEvent(this::startPhase2, 3000));
    }

    // ------------------------------------------------------------------------------------------------- phase 2

    private void startPhase2() {
        if (done) {
            return;
        }
        for (Char chr : new java.util.ArrayList<>(getInstance().getChars())) {
            chr.warp(FIELD_2);
        }
        Field field = getInstance().getField(FIELD_2);
        if (field == null) {
            return;
        }
        for (int cave = 0; cave < CAVES_X.length; cave++) {
            spawnArma(field, cave);
        }
        getInstance().setTimeout(PHASE_2_SECONDS);
        for (Char chr : getInstance().getChars()) {
            sendValues(chr, PHASE_2_SECONDS * 1000, -1);
            sendState(chr, ErdaSpectrumState.Running);
            chr.getScriptManager().progressMessageFont("Defeat Arma's Followers to charge the Erda Condenser, then hold "
                    + "its laser on an Arma Jr. to push it back into its cave. (0 / " + GOAL_PUSHES + ")");
        }
    }

    private synchronized void spawnArma(Field field, int cave) {
        Mob arma = field.spawnMob(ARMA_JR, CAVES_X[cave], GROUND_2, false, 0);
        if (arma != null) {
            armas.put(arma.getObjectId(), cave);
        }
    }

    /** In 1639: the condenser's laser on (1) or off (0) an Arma Jr. */
    public synchronized void laser(Char chr, boolean on, int armaObjectId) {
        Field field = chr.getField();
        if (done || phase != 2 || field == null || !armas.containsKey(armaObjectId)) {
            return;
        }
        long now = System.currentTimeMillis();
        long since = laserSince.getOrDefault(armaObjectId, 0L);
        if (on) {
            if (since == 0) {
                laserSince.put(armaObjectId, now);
            }
            return;
        }
        if (since != 0) {
            addLaser(field, armaObjectId, now - since);
            laserSince.put(armaObjectId, 0L);
        }
    }

    /** Charged laser time on an Arma Jr.; each second of it uses one condenser charge */
    private void addLaser(Field field, int armaObjectId, long ms) {
        long usable = Math.min(ms, charge * 1000L);
        charge -= (int) Math.ceil(usable / 1000D);
        if (usable <= 0) {
            return;
        }
        long total = laserTime.getOrDefault(armaObjectId, 0L) + usable;
        if (total < PUSH_MS) {
            laserTime.put(armaObjectId, total);
            return;
        }
        laserTime.remove(armaObjectId);
        laserSince.remove(armaObjectId);
        int cave = armas.remove(armaObjectId);
        field.removeLife(armaObjectId, true);
        pushes++;
        tell("Arma Jr. is trying to hide behind the cave.");
        if (pushes >= GOAL_PUSHES) {
            clear(field);
            return;
        }
        Char chr = player();
        if (chr != null) {
            chr.getScriptManager().progressMessageFont("Arma Jr. pushed back: " + pushes + " / " + GOAL_PUSHES);
        }
        addTimer(EventManager.addEvent(() -> {
            if (!done) {
                spawnArma(field, cave);
            }
        }, 3000));
    }

    private synchronized void tick() {
        if (done) {
            return;
        }
        long now = System.currentTimeMillis();
        if (phase == 1) {
            Field field = getInstance().getField(FIELD);
            if (field != null && collector != null && now >= collectorEnd) {
                if (alive(field, collector)) {
                    field.removeLife(collector.getObjectId(), true);
                }
                collector = null;
                for (Char c : getInstance().getChars()) {
                    sendState(c, ErdaSpectrumState.Running);
                }
                tell("The Erda Collector has run out of Erda. Defeat more Erda to charge it again.");
            }
            return;
        }
        Field field = getInstance().getField(FIELD_2);
        if (field == null) {
            return;
        }
        // a held laser counts as it goes
        for (var e : new java.util.ArrayList<>(laserSince.entrySet())) {
            if (e.getValue() != 0 && now - e.getValue() >= 1000) {
                laserSince.put(e.getKey(), now);
                addLaser(field, e.getKey(), now - e.getValue());
                if (done) {
                    return;
                }
            }
        }
        for (int id : new java.util.ArrayList<>(armas.keySet())) {
            Life life = field.getLifeByObjectID(id);
            if (life != null && life.getPosition() != null
                    && Math.abs(life.getPosition().getX() - CENTER_X) <= CENTER_REACH) {
                fail(field);
                return;
            }
        }
    }

    private void fail(Field field) {
        done = true;
        tell("Arma Jr. reached the Erda Condenser... The investigation failed.");
        warpOut(4000);
    }

    private void clear(Field field) {
        done = true;
        for (int id : new java.util.ArrayList<>(armas.keySet())) {
            field.removeLife(id, true);
        }
        armas.clear();
        for (Char chr : getInstance().getChars()) {
            sendState(chr, ErdaSpectrumState.Cleared);
            var sm = chr.getScriptManager();
            sm.setQRValue(WEEKLY_QR, "clear", "1");
            sm.showEffect("Map/Effect.img/killing/clear");
        }
        tell("You protected the Erda! Talk to Nina at the base for your reward.");
        warpOut(4000);
    }

    private void warpOut(int delayMs) {
        addTimer(EventManager.addEvent(() -> {
            for (Char chr : new java.util.ArrayList<>(getInstance().getChars())) {
                getInstance().removeChar(chr, EXIT, 0, true, false);
            }
        }, delayMs));
    }
}
