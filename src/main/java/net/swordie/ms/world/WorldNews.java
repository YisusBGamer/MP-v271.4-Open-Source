package net.swordie.ms.world;

import net.swordie.ms.Server;
import net.swordie.ms.client.character.BroadcastMsg;
import net.swordie.ms.connection.packet.WvsContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * Server-wide announcements, also kept as data for code that reacts to them (the Discord relay and extensions).
 * Each one is broadcast to its world as a plain-text "[Tag] ..." notice and kept here:
 * <ul>
 * <li>{@link #subscribe}: called for every new announcement on the publishing thread - keep it quick;</li>
 * <li>{@link #since}: announcements after a given id (only the last {@link #KEEP} are kept);</li>
 * <li>each one is logged as "[WorldNews] TYPE world=N ...";</li>
 * <li>{@link DiscordRelay} posts them to Discord when discord-webhook.txt is set.</li>
 * </ul>
 */
public final class WorldNews {

    private static final Logger log = LogManager.getLogger(WorldNews.class);

    /** What happened. New kinds are added at the end. */
    public enum Type {
        /** A character defeated a boss for the first time. data: boss, difficulty, display, mobId */
        BOSS_FIRST_CLEAR,
        /** A Looking for group post (LfgModule). data: category, note, level */
        LFG_POST,
        /** Hot Time started (HotTimeModule). data: coins, until */
        HOT_TIME,
        /** A character reached a milestone level for the first time (NewPlayerModule). data: level */
        LEVEL_MILESTONE,
        /** A seasonal event started (SeasonalEvents). data: event, name, until (MM-dd) */
        SEASONAL_EVENT
    }

    /**
     * One announcement.
     *
     * @param id        increases by one for each announcement (since the server started)
     * @param time      epoch millis
     * @param worldId   the world it was announced in
     * @param text      the exact text players saw
     * @param charIds   the characters it's about, in the same order as charNames
     * @param charNames their names
     * @param data      type-specific details (see {@link Type})
     */
    public record Announcement(long id, long time, int worldId, Type type, String text, List<Integer> charIds,
                               List<String> charNames, Map<String, String> data) {
    }

    public static final int KEEP = 100;

    private static final Deque<Announcement> recent = new ArrayDeque<>();
    private static final List<Consumer<Announcement>> listeners = new CopyOnWriteArrayList<>();
    private static final AtomicLong lastId = new AtomicLong();

    private WorldNews() {
    }

    public static void subscribe(Consumer<Announcement> listener) {
        listeners.add(listener);
    }

    public static void unsubscribe(Consumer<Announcement> listener) {
        listeners.remove(listener);
    }

    /** @return the kept announcements with an id above the given one, oldest first (0 = all kept) */
    public static synchronized List<Announcement> since(long id) {
        List<Announcement> res = new ArrayList<>();
        for (Announcement a : recent) {
            if (a.id() > id) {
                res.add(a);
            }
        }
        return res;
    }

    /** @return the id of the newest announcement (0 = none yet) */
    public static long lastId() {
        return lastId.get();
    }

    /** Broadcasts the text to the world and hands the announcement to everyone listening. */
    public static Announcement publish(int worldId, Type type, String text, List<Integer> charIds,
                                       List<String> charNames, Map<String, String> data) {
        if (net.swordie.ms.ext.ServerHooks.involvesTestCharacter(charIds)) {
            // test characters are never announced
            log.info(String.format("[WorldNews] not announced (test character) %s: %s", type, text));
            return new Announcement(0, System.currentTimeMillis(), worldId, type, text, List.copyOf(charIds),
                    List.copyOf(charNames), Map.copyOf(data));
        }
        Announcement a;
        synchronized (WorldNews.class) {
            a = new Announcement(lastId.incrementAndGet(), System.currentTimeMillis(), worldId, type, text,
                    List.copyOf(charIds), List.copyOf(charNames), Map.copyOf(data));
            recent.addLast(a);
            while (recent.size() > KEEP) {
                recent.removeFirst();
            }
        }
        World world = Server.getInstance().getWorldById(worldId);
        if (world != null) {
            world.broadcastPacket(WvsContext.broadcastMsg(BroadcastMsg.notice(text)));
        }
        log.info(String.format("[WorldNews] %s world=%d chars=%s data=%s: %s", type, worldId, a.charIds(), a.data(),
                text));
        for (Consumer<Announcement> l : listeners) {
            try {
                l.accept(a);
            } catch (Exception e) {
                log.error("A WorldNews listener failed", e);
            }
        }
        DiscordRelay.post(a); // no-op unless discord-webhook.txt is set
        return a;
    }
}
