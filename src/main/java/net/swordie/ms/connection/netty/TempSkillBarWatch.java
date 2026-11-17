package net.swordie.ms.connection.netty;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.handlers.header.OutHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * While a client has a temporary skill bar (boss/cutscene skills, out 2317) open, logs each in-opcode it sends once
 * per 10 s, so the opcode a bar key press uses shows up in the console.
 */
public final class TempSkillBarWatch {
    private static final Logger log = LogManager.getRootLogger();
    private static final long REPEAT_MS = 10_000;
    private static final Map<NettyClient, Map<Short, Long>> active = Collections.synchronizedMap(new WeakHashMap<>());

    private TempSkillBarWatch() {
    }

    /** Called for every out packet; data starts with the opcode, then the bar's int set index (0 clears it). */
    static void onOut(NettyClient c, byte[] data) {
        if (c == null || data.length < 6 || OutHeader.UPDATE_TEMPORARY_SKILL_BAR.getValue() == ServerConstants.UNVERIFIED_OP) {
            return;
        }
        int op = (data[0] & 0xFF) | ((data[1] & 0xFF) << 8);
        if (op != (OutHeader.UPDATE_TEMPORARY_SKILL_BAR.getValue() & 0xFFFF)) {
            return;
        }
        int index = (data[2] & 0xFF) | ((data[3] & 0xFF) << 8) | ((data[4] & 0xFF) << 16) | ((data[5] & 0xFF) << 24);
        if (index != 0) {
            active.put(c, new ConcurrentHashMap<>());
        } else {
            active.remove(c);
        }
    }

    static void onIn(NettyClient c, short op, int length) {
        if (c == null) {
            return;
        }
        Map<Short, Long> seen = active.get(c);
        if (seen == null) {
            return;
        }
        long now = System.currentTimeMillis();
        Long last = seen.get(op);
        if (last != null && now - last < REPEAT_MS) {
            return;
        }
        seen.put(op, now);
        log.info(String.format("[TempSkillBar] in opcode %d (%s), %d bytes", op, InHeader.getInHeaderByOp(op), length));
    }
}
