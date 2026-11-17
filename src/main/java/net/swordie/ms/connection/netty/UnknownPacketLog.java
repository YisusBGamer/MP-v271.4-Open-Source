package net.swordie.ms.connection.netty;

import net.swordie.ms.client.character.Char;
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
 * Collects samples of packets the server can't name or doesn't handle, to help work out what the unknown opcodes
 * (the UNK... entries of InHeader/OutHeader) are. Each sample records the opcode, the raw packet and where the
 * player was, so a packet can be matched to what the player was doing at that moment.
 * Samples go to logs/unknown_packets.log, at most {@link #MAX_SAMPLES_PER_OPCODE} per opcode per server run.
 */
public class UnknownPacketLog {

    private static final Logger log = LogManager.getLogger(UnknownPacketLog.class);
    private static final String FILE = "logs/unknown_packets.log";
    private static final int MAX_SAMPLES_PER_OPCODE = 20;
    private static final Map<Integer, AtomicInteger> samples = new ConcurrentHashMap<>();

    public static void record(String direction, int opcode, Object name, Object packet, Char chr) {
        int count = samples.computeIfAbsent(opcode, k -> new AtomicInteger()).incrementAndGet();
        if (count > MAX_SAMPLES_PER_OPCODE) {
            return;
        }
        String where = chr == null ? "not in game"
                : String.format("char %s, field %d, job %d, level %d", chr.getName(), chr.getFieldID(), chr.getJob(),
                chr.getLevel());
        String line = String.format("%s\t%s\t%d/0x%s\t%s\t%s\t%s", LocalDateTime.now(), direction, opcode,
                Integer.toHexString(opcode).toUpperCase(), name == null ? "unnamed" : name, where, packet);
        synchronized (UnknownPacketLog.class) {
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
}
