package net.swordie.ms.world.field.instance.instancehelper.arcane;

import java.util.function.Supplier;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.connection.packet.field.MidnightChaserFieldPacket.MidnightChaserState;
import net.swordie.ms.connection.packet.field.MidnightChaserFieldPacket;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.Reactor;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.ReactorData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;

import java.util.*;

/**
 * Midnight Chaser (Lachelein, 921175000; GMS since the Savior update): hide-and-seek with Mr. Flopsy in 9 rooms.
 * Each room holds one piece of furniture (Etc.wz midnightChaser.img object 0-8, reactors 9300000-9300008, placed at
 * area/N/pos:object/K). The progress bar lists where Mr. Flopsy hides, step by step; the player clears the room's
 * monsters, then searches the furniture (harvest key). The right piece clears the room and the step; a wrong one
 * brings the room's monsters back. 9 finds within the time clear the run.
 * Client: MIDNIGHT_CHASER_OBJECTS 2523 (the steps' (room, furniture)), STATE 2524 (1 start, 2 clear, 3 fail),
 * STEP_RESULT 2525 (step, 0 fail / 1 success), ROOM 2522 (room cleared on the board).
 */
public class MidnightChaserInstanceHelper extends InstanceHelper {
    public static final int FIELD = 921175000;
    public static final int EXIT = 921171100;
    /** Etc.wz midnightChaser.img info/qr (clear, ctime) and MapleScheduler's weekly record (wCount, lastDate) */
    public static final int RECORD_QR = 34341;
    public static final int TIME_LIMIT_SECONDS = 600;
    public static final int ROOMS = 9;
    private static final int FURNITURE_REACTOR = 9300000;
    // the tables: resources/data/midnight-chaser-*.tsv (furniture, areas, mobs, objects; Etc.wz midnightChaser.img)
    private static final Supplier<String[]> FURNITURE = DataTable.cached("midnight-chaser-furniture", t ->
            t.rows().stream().sorted(java.util.Comparator.comparingInt(r -> r.i("furniture"))).map(r -> r.str("name"))
                    .toArray(String[]::new));
    /** room 1-9 (index room - 1): left, top, right, bottom */
    private static final Supplier<int[][]> AREAS = DataTable.cached("midnight-chaser-areas", t ->
            t.rows().stream().sorted(java.util.Comparator.comparingInt(r -> r.i("room")))
                    .map(r -> new int[]{r.i("left"), r.i("top"), r.i("right"), r.i("bottom")}).toArray(int[][]::new));
    /** room, monster, x, y */
    private static final Supplier<int[][]> MOBS = DataTable.cached("midnight-chaser-mobs", t ->
            t.rows().stream().map(r -> new int[]{r.i("room"), r.i("mob"), r.i("x"), r.i("y")}).toArray(int[][]::new));
    /** [room - 1][furniture] = {x, y} */
    private static final Supplier<int[][][]> OBJECTS = DataTable.cached("midnight-chaser-objects", t -> {
        int rooms = t.rows().stream().mapToInt(r -> r.i("room")).max().orElse(0);
        int kinds = t.rows().stream().mapToInt(r -> r.i("furniture")).max().orElse(-1) + 1;
        int[][][] o = new int[rooms][kinds][];
        for (DataTable.Row r : t.rows()) {
            o[r.i("room") - 1][r.i("furniture")] = new int[]{r.i("x"), r.i("y")};
        }
        return o;
    });

    private static String[] furniture() {
        return FURNITURE.get();
    }

    private static int[][] areas() {
        return AREAS.get();
    }

    private static int[][] mobs() {
        return MOBS.get();
    }

    private static int[][][] objects() {
        return OBJECTS.get();
    }

    private final int[] furnitureOfRoom = new int[ROOMS + 1];
    private final Map<Integer, Integer> reactorRooms = new HashMap<>();
    private final Map<Integer, Set<Integer>> roomMobs = new HashMap<>();
    private final boolean[] roomCleared = new boolean[ROOMS + 1];
    private final List<Integer> steps = new ArrayList<>();
    private int step;
    private boolean cleared;

    public static boolean isField(int fieldId) {
        return fieldId == FIELD;
    }

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        Field field = getField();
        List<Integer> furniture = new ArrayList<>();
        for (int k = 0; k < furniture().length; k++) {
            furniture.add(k);
        }
        Collections.shuffle(furniture);
        for (int room = 1; room <= ROOMS; room++) {
            int k = furniture.get(room - 1);
            furnitureOfRoom[room] = k;
            Reactor r = ReactorData.getReactorByID(FURNITURE_REACTOR + k);
            if (r != null) {
                int[] at = objects()[room - 1][k];
                r.setPosition(new Position(at[0], at[1]));
                field.spawnLife(r, null);
                reactorRooms.put(r.getObjectId(), room);
            }
            spawnRoom(field, room);
            steps.add(room);
        }
        Collections.shuffle(steps);
        for (Char chr : instance.getChars()) {
            chr.write(MidnightChaserFieldPacket.objects(steps, furnitureOfRoom));
            chr.write(MidnightChaserFieldPacket.state(MidnightChaserState.Start));
            chr.getScriptManager().progressMessageFont("Find Mr. Flopsy! He's hiding in the " + target() + ".");
        }
    }

    @Override
    protected void startStage(int stageNumber) {
        // one stage, set up in setup()
    }

    private String target() {
        return step < steps.size() ? furniture()[furnitureOfRoom[steps.get(step)]] : "";
    }

    private void spawnRoom(Field field, int room) {
        Set<Integer> alive = roomMobs.computeIfAbsent(room, r -> new HashSet<>());
        for (int[] m : mobs()) {
            if (m[0] == room) {
                Mob mob = field.spawnMob(m[1], m[2], m[3], false, 0);
                if (mob != null) {
                    alive.add(mob.getObjectId());
                }
            }
        }
    }

    @Override
    public synchronized void onMobDeath(Field field, Mob mob) {
        for (Set<Integer> alive : roomMobs.values()) {
            alive.remove(mob.getObjectId());
        }
    }

    /** @return the room (1-9) a position is in, or 0 */
    public static int roomAt(Position pos) {
        for (int i = 0; i < areas().length; i++) {
            int[] a = areas()[i];
            if (new Rect(a[0], a[1], a[2], a[3]).hasPositionInside(pos)) {
                return i + 1;
            }
        }
        return 0;
    }

    /** The harvest key on a piece of furniture */
    public synchronized void search(Char chr, Reactor reactor) {
        Integer room = reactorRooms.get(reactor.getObjectId());
        if (room == null || cleared) {
            return;
        }
        var sm = chr.getScriptManager();
        if (roomCleared[room]) {
            sm.progressMessageFont("You've already found Mr. Flopsy here.");
            return;
        }
        Set<Integer> alive = roomMobs.getOrDefault(room, Collections.emptySet());
        alive.removeIf(id -> chr.getField().getLifeByObjectID(id) == null);
        if (!alive.isEmpty()) {
            sm.progressMessageFont("Defeat all the monsters in this room before searching the " + furniture()[furnitureOfRoom[room]] + ".");
            return;
        }
        if (steps.get(step) == (int) room) {
            roomCleared[room] = true;
            chr.write(MidnightChaserFieldPacket.stepResult(step + 1, true));
            chr.write(MidnightChaserFieldPacket.room(room));
            step++;
            if (step >= steps.size()) {
                clear(chr);
            } else {
                sm.progressMessageFont("Found him! Now he's hiding in the " + target() + ".");
            }
        } else {
            chr.write(MidnightChaserFieldPacket.stepResult(step + 1, false));
            sm.progressMessageFont("Mr. Flopsy isn't in the " + furniture()[furnitureOfRoom[room]] + "... The monsters are back!");
            spawnRoom(chr.getField(), room);
        }
    }

    /** The search request with no reactor (the client's own request): the furniture of the room the player is in */
    public void searchHere(Char chr) {
        int room = roomAt(chr.getPosition());
        for (Map.Entry<Integer, Integer> e : reactorRooms.entrySet()) {
            if (e.getValue() == room && chr.getField().getLifeByObjectID(e.getKey()) instanceof Reactor r) {
                search(chr, r);
                return;
            }
        }
    }

    public int getStep() {
        return step;
    }

    /** @return the room Mr. Flopsy hides in now (1-9), or 0 when the run is done */
    public int getTargetRoom() {
        return step < steps.size() ? steps.get(step) : 0;
    }

    private void clear(Char chr) {
        cleared = true;
        var sm = chr.getScriptManager();
        chr.write(MidnightChaserFieldPacket.state(MidnightChaserState.Clear));
        sm.setQRValue(RECORD_QR, "clear", "1");
        sm.setQRValue(RECORD_QR, "ctime", String.valueOf(getInstance().getTotalInstanceDuration() / 1000));
        sm.setQRValue(RECORD_QR, "reward", "1");
        ArcaneWeekly.addClear(chr, RECORD_QR);
        sm.progressMessageFont("You found Mr. Flopsy 9 times! Talk to him outside for your reward.");
        addTimer(EventManager.addEvent(() -> {
            if (chr.getInstance() == getInstance()) {
                getInstance().removeChar(chr, EXIT, 0, true, false);
            }
        }, 4000));
    }
}
