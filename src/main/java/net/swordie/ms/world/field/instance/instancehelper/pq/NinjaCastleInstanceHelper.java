package net.swordie.ms.world.field.instance.instancehelper.pq;

import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.containerclasses.FieldInfo;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Portal;
import net.swordie.ms.world.field.instance.Instance;

import java.util.*;
import java.util.concurrent.ScheduledFuture;

/**
 * Ninja Castle inner halls (Zipangu), entered from Baetan in the Ninja Castle Corridor. Level 160+, alone or a party
 * of up to 6.
 * <ul>
 *     <li>Floor 1: five Long Corridors in a row. Each has four chamber doors on the floor (Time, Battle, Traps,
 *     Wisdom); clearing one chamber opens the corridor's upper door (gate0) to the next corridor. Hidden passages
 *     (h0N0/h0N1) link the floor and the upper ledges.</li>
 *     <li>Floor 2: Phantom's Rooms. A few rooms in a row, each with three doors: one leads on, the others hold an
 *     ambush.</li>
 *     <li>Boudoir 1: defeat the Kaede Toad guards to open the way to the Tower Room, where the False Daimyo turns
 *     into the Gigatoad when defeated (Mob.wz revive).</li>
 * </ul>
 */
public class NinjaCastleInstanceHelper extends PartyQuestInstanceHelper {

    public static final String RECORD_KEY = "ninja_castle";
    public static final int MIN_LEVEL = 160;
    public static final int MAX_PARTY = 6;
    public static final int DAILY_ENTRIES = 10;
    public static final int TIME_LIMIT_SECONDS = 40 * 60;

    public static final int CASTLE_CORRIDOR = 800040110;
    public static final int REAR_CORRIDOR = 800040420;
    public static final int[] CORRIDORS = {800040200, 800040210, 800040220, 800040230, 800040240};
    public static final int PHANTOM_FIRST = 800040300;
    public static final int PHANTOM_LAST = 800040311;
    public static final int MAZE_ROOMS = 4;
    public static final int BOUDOIR = 800040400;
    public static final int TOWER_ROOM = 800040410;
    public static final int BOUDOIR_B = 800040500; // second copy of the boss rooms, same rules
    public static final int TOWER_ROOM_B = 800040510;

    public static final int TIME = 1, BATTLE = 2, TRAPS = 3, WISDOM = 4;
    public static final int TIME_CHAMBER_SECONDS = 60;

    private static final int KAEDE_NINTO = 9400403;
    private static final int KACCHUU_MUSHA = 9400405;
    private static final int KAEDE_KUNOICHI = 9400406;
    private static final int KAEDE_TOAD = 9400407;
    private static final int FALSE_DAIMYO = 9400408;
    private static final int GIGATOAD = 9400409;
    // each corridor's own Map.wz monsters
    private static final int[][] CORRIDOR_MOBS = DataTable.read("ninja-castle-corridors").rows().stream()
            .map(r -> r.ints("mobs")).toArray(int[][]::new);

    private static final int CHAMBER_PERCENT = 6; // x5 corridors
    private static final int MAZE_PERCENT = 10;
    private static final int BOSS_PERCENT = 60;
    private static final int MAZE_STAGE = 6, GUARD_STAGE = 7, BOSS_STAGE = 8;

    private final Set<Integer> clearedChambers = new HashSet<>();
    private final Set<Integer> startedChambers = new HashSet<>();
    private final List<Integer> mazePath = new ArrayList<>();
    private final Map<Integer, Integer> mazeDoor = new HashMap<>();
    private final Map<Integer, Set<Integer>> triedDoors = new HashMap<>();
    private final Map<Integer, ScheduledFuture> timeWaves = new HashMap<>();
    private final Set<Integer> bossRoomsSpawned = new HashSet<>();
    private volatile boolean finished;

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        instance.setForcedReturn(REAR_CORRIDOR);
        instance.setTimeout(TIME_LIMIT_SECONDS);
        List<Integer> rooms = new ArrayList<>();
        for (int id = PHANTOM_FIRST; id <= PHANTOM_LAST; id++) {
            rooms.add(id);
        }
        Collections.shuffle(rooms);
        for (int i = 0; i < MAZE_ROOMS; i++) {
            mazePath.add(rooms.get(i));
            mazeDoor.put(rooms.get(i), Util.getRandom(2));
        }
    }

    @Override
    protected void startStage(int stageNumber) {
    }

    // ---------------------------------------------------------------------------------------------------------- entry

    public static String getEntryBlocker(Char leader) {
        var party = leader.getParty();
        if (party != null && party.getPartyLeaderID() != leader.getId()) {
            return "Only your party leader can lead the way in.";
        }
        List<Char> members = party == null ? List.of(leader) : party.getPartyMembersInField(leader);
        if (members.size() > MAX_PARTY) {
            return "At most " + MAX_PARTY + " can go in together.";
        }
        for (Char c : members) {
            if (c.getLevel() < MIN_LEVEL) {
                return c.getName() + " must be at least level " + MIN_LEVEL + ".";
            }
            if (PartyQuestRecords.getTodayEntries(c, RECORD_KEY) >= DAILY_ENTRIES) {
                return c.getName() + " has already gone in " + DAILY_ENTRIES + " times today.";
            }
        }
        return null;
    }

    public static void recordEntries(Char leader) {
        var party = leader.getParty();
        List<Char> members = party == null ? List.of(leader) : party.getPartyMembersInField(leader);
        for (Char c : members) {
            PartyQuestRecords.addEntry(c, RECORD_KEY);
        }
    }

    // ---------------------------------------------------------------------------------------------------------- maps

    private static int corridorIndex(int fid) {
        for (int i = 0; i < CORRIDORS.length; i++) {
            if (CORRIDORS[i] == fid) {
                return i;
            }
        }
        return -1;
    }

    /** @return the chamber kind (TIME..WISDOM) of a chamber map, or 0 */
    public static int chamberKind(int fid) {
        int kind = fid % 10;
        return kind >= TIME && kind <= WISDOM && corridorIndex(fid - kind) >= 0 ? kind : 0;
    }

    private static boolean isBoudoir(int fid) {
        return fid == BOUDOIR || fid == BOUDOIR_B;
    }

    private static boolean isTower(int fid) {
        return fid == TOWER_ROOM || fid == TOWER_ROOM_B;
    }

    private static int portalId(int fid, String name) {
        FieldInfo fi = net.swordie.ms.loaders.FieldData.getFieldInfoById(fid);
        Portal p = fi == null ? null : fi.getPortalByName(name);
        return p == null ? 0 : p.getId();
    }

    private boolean corridorOpen(int index) {
        return isStageCleared(index + 1);
    }

    @Override
    public boolean canSpawnMapMobs(Field field) {
        return corridorIndex(field.getId()) >= 0; // everything else is spawned here
    }

    @Override
    public boolean canRespawnMobs(Field field) {
        return corridorIndex(field.getId()) >= 0;
    }

    @Override
    public void onMobSpawn(Field field, Mob mob) {
        scaleMobToParty(mob);
        super.onMobSpawn(field, mob);
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        if (newField != null) {
            int fid = newField.getId();
            int kind = chamberKind(fid);
            if (kind == BATTLE) {
                startBattle(newField);
            } else if (kind == TIME) {
                startTime(newField);
            } else if (isBoudoir(fid)) {
                spawnGuards(newField);
            } else if (isTower(fid)) {
                spawnDaimyo(newField);
            } else if (corridorIndex(fid) >= 0 && corridorOpen(corridorIndex(fid))) {
                chr.chatMessage(ChatType.SystemNotice, "The upper door of this corridor is open.");
            }
        }
        super.onWarp(chr, oldField, newField);
    }

    private static void notice(Field field, String msg) {
        if (field != null) {
            for (Char c : field.getChars()) {
                c.chatMessage(ChatType.SystemNotice, msg);
            }
        }
    }

    private void spawnSome(Field field, int[] ids, int count, int minX, int maxX, int y) {
        for (int i = 0; i < count; i++) {
            field.spawnMob(ids[Util.getRandom(ids.length - 1)], Util.getRandom(minX, maxX), y, false, -1);
        }
    }

    private static int[] mobsFor(int chamberFid) {
        int idx = corridorIndex(chamberFid - chamberFid % 10);
        return CORRIDOR_MOBS[Math.max(0, idx)];
    }

    // ---------------------------------------------------------------------------------------------------------- floor 1

    /** ninjaF1_gate: gate0 leads on once a chamber is cleared, gate1-4 lead into the chambers. */
    public synchronized void corridorGate(Char chr, int gate) {
        int fid = chr.getField().getId();
        int idx = corridorIndex(fid);
        if (idx < 0) {
            return;
        }
        if (gate >= TIME && gate <= WISDOM) {
            int chamber = fid + gate;
            if (clearedChambers.contains(chamber)) {
                chr.chatMessage(ChatType.SystemNotice, "This chamber has already been cleared.");
                return;
            }
            chr.warp(chamber, portalId(chamber, "st00"));
            return;
        }
        if (!corridorOpen(idx)) {
            chr.chatMessage(ChatType.SystemNotice, "The door is sealed. Clear one of the four chambers below first.");
            return;
        }
        if (idx + 1 < CORRIDORS.length) {
            chr.warp(CORRIDORS[idx + 1], portalId(CORRIDORS[idx + 1], "st00"));
        } else {
            chr.warp(mazePath.get(0), portalId(mazePath.get(0), "st00"));
        }
    }

    /** Back from a chamber to its door in the corridor. */
    public void leaveChamber(Char chr) {
        int fid = chr.getField().getId();
        int kind = chamberKind(fid);
        if (kind == 0) {
            return;
        }
        int corridor = fid - kind;
        chr.warp(corridor, portalId(corridor, "gate" + kind));
    }

    public synchronized boolean isChamberCleared(Char chr) {
        return clearedChambers.contains(chr.getField().getId());
    }

    public synchronized void clearChamber(Field field) {
        int fid = field.getId();
        int kind = chamberKind(fid);
        if (kind == 0 || !clearedChambers.add(fid)) {
            return;
        }
        int idx = corridorIndex(fid - kind);
        if (clearStage(idx + 1, field, CHAMBER_PERCENT)) {
            notice(field, "Chamber cleared! The upper door of the corridor is open.");
        } else {
            notice(field, "Chamber cleared!");
        }
    }

    private synchronized void startBattle(Field field) {
        if (!startedChambers.add(field.getId())) {
            return;
        }
        spawnSome(field, mobsFor(field.getId()), 8, -400, 1200, 139);
        spawnSome(field, new int[]{KAEDE_NINTO}, 5, -400, 1200, 139);
        field.spawnMob(KACCHUU_MUSHA, 800, 139, false, -1);
        notice(field, "Chamber of Battle: defeat every soldier, including their Kacchuu Musha captain.");
    }

    private synchronized void startTime(Field field) {
        if (!startedChambers.add(field.getId())) {
            return;
        }
        int[] ids = mobsFor(field.getId());
        spawnSome(field, ids, 6, -150, 690, 139);
        notice(field, "Chamber of Time: hold out for " + TIME_CHAMBER_SECONDS + " seconds!");
        timeWaves.put(field.getId(), addTimer(EventManager.addFixedRateEvent(() -> {
            if (field.getMobs().size() < 8) {
                spawnSome(field, ids, 2, -150, 690, 139);
            }
        }, 10_000, 10_000)));
        addTimer(EventManager.addEvent(() -> notice(field, "30 seconds left!"), (TIME_CHAMBER_SECONDS - 30) * 1000L));
        addTimer(EventManager.addEvent(() -> endTime(field), TIME_CHAMBER_SECONDS * 1000L));
    }

    private synchronized void endTime(Field field) {
        ScheduledFuture waves = timeWaves.remove(field.getId());
        if (waves != null) {
            EventManager.stopTimer(waves);
        }
        for (Mob m : new ArrayList<>(field.getMobs())) {
            m.disappear();
        }
        if (field.getChars().isEmpty()) {
            startedChambers.remove(field.getId()); // nobody held out: it can be tried again
            return;
        }
        clearChamber(field);
    }

    /** ninjaF1_trap: the end of the trap course. */
    public void finishTraps(Char chr) {
        if (chamberKind(chr.getField().getId()) == TRAPS) {
            clearChamber(chr.getField());
        }
        leaveChamber(chr);
    }

    /** ninjaF1_smart: the riddle at the door was answered. */
    public void answerRiddle(Char chr, boolean correct) {
        Field field = chr.getField();
        if (chamberKind(field.getId()) != WISDOM) {
            return;
        }
        if (correct) {
            clearChamber(field);
        } else {
            spawnSome(field, mobsFor(field.getId()), 4, -150, 690, 139);
            notice(field, "Wrong! The guards rush in. Defeat them before you try again.");
        }
    }

    // ---------------------------------------------------------------------------------------------------------- floor 2

    /** ninjaF2_gate */
    public synchronized void phantomGate(Char chr, int door) {
        Field field = chr.getField();
        int fid = field.getId();
        int step = mazePath.indexOf(fid);
        if (step < 0) {
            chr.warp(mazePath.get(0), portalId(mazePath.get(0), "st00"));
            return;
        }
        if (!field.getMobs().isEmpty()) {
            chr.chatMessage(ChatType.SystemNotice, "The phantoms block the doors. Defeat them first.");
            return;
        }
        if (mazeDoor.get(fid) == door) {
            if (step + 1 < mazePath.size()) {
                int next = mazePath.get(step + 1);
                chr.warp(next, portalId(next, "st00"));
            } else {
                if (clearStage(MAZE_STAGE, field, MAZE_PERCENT)) {
                    notice(field, "You found the way through the Phantom's Rooms!");
                }
                chr.warp(BOUDOIR, portalId(BOUDOIR, "st00"));
            }
            return;
        }
        Set<Integer> tried = triedDoors.computeIfAbsent(fid, k -> new HashSet<>());
        if (!tried.add(door)) {
            chr.chatMessage(ChatType.SystemNotice, "That door leads nowhere.");
            return;
        }
        Portal p = field.getInfo().getPortalByName("gate" + door);
        int x = p == null ? 0 : p.getX();
        spawnSome(field, new int[]{KAEDE_NINTO, KAEDE_KUNOICHI}, 3, x - 120, x + 120, 140);
        notice(field, "A trap! Ninjas drop from the ceiling.");
    }

    public int getMazeStep(Char chr) {
        return mazePath.indexOf(chr.getField().getId()) + 1;
    }

    // ---------------------------------------------------------------------------------------------------------- boss

    private synchronized void spawnGuards(Field field) {
        if (!bossRoomsSpawned.add(field.getId())) {
            return;
        }
        spawnSome(field, new int[]{KAEDE_TOAD}, 8, -300, 1000, 142);
        notice(field, "Defeat the Daimyo's toad guards to open the way to the Tower Room.");
    }

    private synchronized void spawnDaimyo(Field field) {
        if (!bossRoomsSpawned.add(field.getId())) {
            return;
        }
        field.spawnMob(FALSE_DAIMYO, 500, 175, false, -1);
        notice(field, "The Daimyo... is not what he seems!");
    }

    /** ninjaBossEnter */
    public void enterTower(Char chr) {
        int fid = chr.getField().getId();
        if (!isBoudoir(fid)) {
            return;
        }
        if (!chr.getField().getMobs().isEmpty()) {
            chr.chatMessage(ChatType.SystemNotice, "The toad guards still block the door.");
            return;
        }
        int tower = fid == BOUDOIR ? TOWER_ROOM : TOWER_ROOM_B;
        chr.warp(tower, portalId(tower, "st00"));
    }

    @Override
    public synchronized void onMobDeath(Field field, Mob mob) {
        int fid = field.getId();
        int t = mob.getTemplateId();
        if (chamberKind(fid) == BATTLE && field.getMobs().isEmpty()) {
            clearChamber(field);
        } else if (isBoudoir(fid) && field.getMobs().isEmpty()) {
            if (clearStage(GUARD_STAGE, field, 0)) {
                notice(field, "The guards are down. The door to the Tower Room is open.");
            }
        } else if (isTower(fid) && t == GIGATOAD && !finished) {
            finished = true;
            clearStage(BOSS_STAGE, field, BOSS_PERCENT);
            for (Char c : getInstance().getChars()) {
                PartyQuestRecords.addClear(c, RECORD_KEY);
            }
            notice(field, "The Gigatoad is defeated! Pick up your loot: you'll be taken out in 60 seconds.");
            addTimer(EventManager.addEvent(() -> getInstance().clear(), 60_000));
        }
        super.onMobDeath(field, mob);
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    protected long getBaseCompletionExp() {
        return 12_000_000L;
    }

    @Override
    protected int getExpMinLevel() {
        return MIN_LEVEL;
    }

    @Override
    protected int getExpMaxLevel() {
        return 200;
    }

    @Override
    protected List<Integer> getPartyQuestItems() {
        return Collections.emptyList();
    }
}
