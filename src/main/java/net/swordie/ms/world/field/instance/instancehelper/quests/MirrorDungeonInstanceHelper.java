package net.swordie.ms.world.field.instance.instancehelper.quests;

import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.jwzlib.WzFile;
import net.swordie.ms.jwzlib.WzObject;
import net.swordie.ms.jwzlib.WzProperty;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.MobGen;
import net.swordie.ms.world.field.Portal;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Mirror World dungeons (Zero, Etc.wz/mirrorDungeonMap.img): a run is one solo instance through the dungeon's rooms.
 * Room monsters spawn once; when the last one falls the room is cleared (Out 1269, the client's "go" arrow) and its
 * nextDungeon portal opens. Clearing a boss room ends the run: the clear window (Out 1271) with the time, the best
 * time, EXP and WP, and the dungeon's record (mirrorDungeonEnter recordID: count, min, sec) is updated.
 * EXP (a share of the level's EXP, cut above the level cap) and WP are this server's numbers.
 */
public class MirrorDungeonInstanceHelper extends InstanceHelper {

    private static final Logger log = LogManager.getLogger(MirrorDungeonInstanceHelper.class);

    private static final int OVER_LEVEL_EXP_DIVISOR = 10;
    private static final int LEVEL_CAP_MARGIN = 5;
    private static final int CLEAR_WP = 100;

    private record Room(String dungeon, int index, boolean boss) {
    }

    private static final Map<Integer, Room> ROOMS = new HashMap<>();
    private static final Map<String, Integer> RECORD_IDS = new HashMap<>();
    private static final Map<String, Integer> MAX_LEVELS = new HashMap<>();
    private static volatile boolean loaded;

    private final Set<Integer> clearedFields = new HashSet<>();
    private String dungeon;
    private long startTime;
    private boolean finished;

    public static boolean isRoom(int fieldId) {
        ensureLoaded();
        return ROOMS.containsKey(fieldId);
    }

    private static synchronized void ensureLoaded() {
        if (loaded) {
            return;
        }
        loaded = true;
        try {
            WzFile etc = new WzFile(ServerConstants.WZ_DIR + "/Etc.wz");
            WzObject<?, ?> map = etc.getChild("mirrorDungeonMap.img");
            if (map == null) {
                log.warn("Etc.wz has no mirrorDungeonMap.img: Mirror World dungeon rooms are plain maps");
                return;
            }
            for (WzObject<?, ?> d : map) {
                for (WzObject<?, ?> room : d) {
                    int field = intOf(room.getChild("map"), 0);
                    if (field > 0) {
                        ROOMS.put(field, new Room(d.getName(), Integer.parseInt(room.getName()),
                                intOf(room.getChild("boss"), 0) != 0));
                    }
                }
            }
            WzObject<?, ?> enter = etc.getChild("mirrorDungeonEnter.img");
            if (enter != null) {
                for (WzObject<?, ?> town : enter) {
                    for (WzObject<?, ?> d : town) {
                        int record = intOf(d.getChild("recordID"), 0);
                        if (record > 0) {
                            RECORD_IDS.put(d.getName(), record);
                        }
                    }
                }
            }
            WzObject<?, ?> names = new WzFile(ServerConstants.WZ_DIR + "/String.wz").getChild("mirrorDungeon.img");
            if (names != null) {
                for (WzObject<?, ?> d : names) {
                    String level = stringOf(d.getChild("level")); // "100 - 103"
                    String[] parts = level.split("-");
                    if (parts.length == 2) {
                        try {
                            MAX_LEVELS.put(d.getName(), Integer.parseInt(parts[1].trim()));
                        } catch (NumberFormatException ignored) {
                        }
                    }
                }
            }
            log.info(String.format("Loaded %d Mirror World dungeon rooms", ROOMS.size()));
        } catch (Exception e) {
            log.error("Could not read the Mirror World dungeon data", e);
        }
    }

    private static int intOf(WzObject<?, ?> node, int def) {
        if (node instanceof WzProperty<?> prop) {
            try {
                return prop.getIntValue();
            } catch (RuntimeException e) {
                return def;
            }
        }
        return def;
    }

    private static String stringOf(WzObject<?, ?> node) {
        return node instanceof WzProperty<?> prop && prop.getValue() != null ? prop.getValue().toString() : "";
    }

    @Override
    protected void startStagePreDelay() {
        // no stages: rooms are cleared by their monsters
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        if (newField == null || newField.getInstance() != getInstance()) {
            return;
        }
        Room room = ROOMS.get(newField.getId());
        if (room == null) {
            return;
        }
        synchronized (this) {
            if (dungeon == null) {
                dungeon = room.dungeon();
                startTime = Util.getCurrentTimeLong();
            }
            if (clearedFields.contains(newField.getId())) {
                chr.chatMessage(ChatType.Mob, "This room is already clear.");
            }
        }
    }

    @Override
    public boolean canRespawnMobs(Field field) {
        return !ROOMS.containsKey(field.getId());
    }

    @Override
    public boolean canUsePortal(Char chr, Field field, Portal portal) {
        String script = portal.getScript();
        if (script == null || !script.startsWith("nextDungeon") || !ROOMS.containsKey(field.getId())) {
            return true;
        }
        synchronized (this) {
            return clearedFields.contains(field.getId());
        }
    }

    @Override
    public void onMobDeath(Field field, Mob mob) {
        Room room = ROOMS.get(field.getId());
        if (room == null || !field.getMobs().isEmpty()) {
            return;
        }
        for (MobGen mg : field.getMobGens()) {
            if (!mg.hasSpawned()) {
                return; // more of the room's monsters still to come
            }
        }
        synchronized (this) {
            if (finished || !clearedFields.add(field.getId())) {
                return;
            }
        }
        field.broadcastPacket(UserLocal.mirrorDungeonUnitCleared(false, false, List.of(room.index())));
        if (room.boss()) {
            for (Char chr : new ArrayList<>(field.getChars())) {
                finish(chr, room);
            }
        } else {
            for (Char chr : field.getChars()) {
                chr.chatMessage(ChatType.Mob, "The room is clear. The portal ahead is open.");
            }
        }
    }

    private void finish(Char chr, Room room) {
        synchronized (this) {
            if (finished) {
                return;
            }
            finished = true;
        }
        int seconds = (int) Math.max(1, (Util.getCurrentTimeLong() - startTime) / 1000);
        String key = dungeon != null ? dungeon : room.dungeon();
        var sm = chr.getScriptManager();
        int best = seconds;
        Integer recordId = RECORD_IDS.get(key);
        if (recordId != null) {
            int count = parse(sm.getQRValue(recordId, "count"));
            int oldMin = parse(sm.getQRValue(recordId, "min"));
            int oldSec = parse(sm.getQRValue(recordId, "sec"));
            int oldBest = oldMin * 60 + oldSec;
            if (oldBest > 0 && oldBest < best) {
                best = oldBest;
            }
            sm.setQRValue(recordId, "count", String.valueOf(count + 1));
            sm.setQRValue(recordId, "min", String.valueOf(best / 60));
            sm.setQRValue(recordId, "sec", String.valueOf(best % 60));
        }
        int levelCap = MAX_LEVELS.getOrDefault(key, (int) chr.getLevel()) + LEVEL_CAP_MARGIN;
        int level = Math.min(chr.getLevel(), GameConstants.charExp.length - 1);
        long exp = (long) (GameConstants.charExp[level] * GameConstants.MIRROR_DUNGEON_CLEAR_EXP_RATE);
        if (chr.getLevel() > levelCap) {
            exp /= OVER_LEVEL_EXP_DIVISOR;
        }
        int wp = JobConstants.isZero(chr.getJob()) ? CLEAR_WP : 0;
        chr.write(UserLocal.mirrorDungeonRecord(seconds, best, exp, wp, levelCap));
        if (exp > 0) {
            chr.addExpNoMsg(exp);
        }
        if (wp > 0) {
            chr.addWpCoin(wp);
        }
        chr.chatMessage(ChatType.Mob, "Dungeon clear! Use a portal to return to town.");
    }

    private static int parse(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
