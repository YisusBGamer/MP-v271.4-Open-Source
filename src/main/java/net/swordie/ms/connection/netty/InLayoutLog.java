package net.swordie.ms.connection.netty;

import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.header.InHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Client packets whose handler read past the end or left bytes unread: a layout that differs from the v271 client's.
 * logs/in-layout.tsv: time, kind (past-end / leftover), opcode, name, packet length, unread bytes, packet hex. At most
 * {@link #MAX_PER_KEY} lines per opcode, kind and length per server run.
 */
public class InLayoutLog {

    private static final Logger log = LogManager.getLogger(InLayoutLog.class);
    private static final String FILE = "logs/in-layout.tsv";
    private static final int MAX_PER_KEY = 3;
    private static final Map<String, AtomicInteger> seen = new ConcurrentHashMap<>();

    public static void leftover(InHeader header, int op, InPacket packet) {
        record("leftover", header, op, packet, packet.getUnreadAmount());
    }

    public static void pastEnd(InHeader header, int op, InPacket packet) {
        record("past-end", header, op, packet, -1);
    }

    private static void record(String kind, InHeader header, int op, InPacket packet, int unread) {
        // getLength() is the unread part: the whole packet is the data array
        int len = packet.getData() == null ? 0 : packet.getData().length;
        String key = kind + op + "/" + len;
        if (seen.computeIfAbsent(key, k -> new AtomicInteger()).incrementAndGet() > MAX_PER_KEY) {
            return;
        }
        String line = String.format("%s\t%s\t%d\t%s\t%d\t%d\t%s", LocalDateTime.now(), kind, op, header, len, unread,
                hex(packet.getData(), 160));
        synchronized (InLayoutLog.class) {
            File file = new File(FILE);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
                pw.println(line);
            } catch (IOException e) {
                log.debug("Could not write to " + FILE, e);
            }
        }
    }

    private static String hex(byte[] b, int max) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; b != null && i < b.length && i < max; i++) {
            sb.append(String.format("%02X ", b[i]));
        }
        return sb.toString().trim();
    }

    /** True for the exceptions a read past the end of a packet throws. */
    public static boolean isPastEnd(Throwable t) {
        for (int i = 0; t != null && i < 5; i++, t = t.getCause()) {
            if (t instanceof IndexOutOfBoundsException) {
                return true;
            }
        }
        return false;
    }
}
