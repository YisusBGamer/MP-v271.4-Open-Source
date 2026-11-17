package net.swordie.ms.connection.netty;

import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.handlers.header.InHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * After a Switch Characters title return for a test account: logs every in packet from that address for 15 s (opcode
 * and length only), on the old and the new connection.
 */
public final class SwitchCharWatch {
    private static final Logger log = LogManager.getRootLogger();
    private static final long WATCH_MS = 15_000;

    private static final class Watch {
        final NettyClient old;
        final long sentAt;
        volatile boolean reconnected;

        Watch(NettyClient old, long sentAt) {
            this.old = old;
            this.sentAt = sentAt;
        }
    }

    /** By IP. */
    private static final Map<String, Watch> watches = new ConcurrentHashMap<>();

    private SwitchCharWatch() {
    }

    public static void onTitleSent(NettyClient c) {
        String ip = c.getIP();
        Watch watch = new Watch(c, System.currentTimeMillis());
        watches.put(ip, watch);
        log.info(String.format("[Switch] %s: RETURN_TO_TITLE sent; watching in packets for 15 s", ip));
        EventManager.addEvent(() -> {
            if (watches.remove(ip, watch)) {
                log.info(String.format("[Switch] %s: watch over, reconnected %s", ip, watch.reconnected));
            }
        }, WATCH_MS);
    }

    static void onIn(NettyClient c, short op, int length) {
        if (watches.isEmpty() || c == null) {
            return;
        }
        Watch watch = watches.get(c.getIP());
        if (watch == null) {
            return;
        }
        long since = System.currentTimeMillis() - watch.sentAt;
        if (since > WATCH_MS) {
            return;
        }
        boolean same = c == watch.old;
        if (!same) {
            watch.reconnected = true;
        }
        log.info(String.format("[Switch] +%d ms in %d (%s), %d bytes, %s connection", since, op,
                InHeader.getInHeaderByOp(op), length, same ? "old" : "new"));
    }
}
