/*
    This file is part of Desu: MapleStory v62 Server Emulator
    Copyright (C) 2014  Zygon <watchmystarz@hotmail.com>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package net.swordie.ms.connection.netty;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.Packet;
import net.swordie.ms.ServerConstants;
import net.swordie.ms.connection.crypto.MapleCrypto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Abstraction for Netty channels that contains some attribute keys
 * for important resources used by the net.swordie.ms.client during encryption,
 * decryption, and general functions. <B>Note: Some methods cannot be
 * overridden by descendents due to the nature of the functionality they 
 * provide</B>
 * 
 * @author Zygon
 */
public class NettyClient {

    private static final Logger log = LogManager.getLogger();
    private static final int MAX_PACKET_SIZE = 16777215; // Same as netty
    private static final Set<String> loggedUnverifiedSenders = ConcurrentHashMap.newKeySet();

    /**
     * Attribute key for MapleCrypto related to this Client.
     */
    public static final AttributeKey<MapleCrypto> CRYPTO_KEY = AttributeKey.valueOf("A");
    /**
     * Attribute key for this NettyClient object.
     */
    public static final AttributeKey<NettyClient> CLIENT_KEY = AttributeKey.valueOf("C");
    
    /**
     * Send seed or IV for one of the cryptography stages.
     */
    private byte[] siv;
    /**
     * Receive seed or IV for one of the cryptography stages.
     */
    private byte[] riv;
    /**
     * Stored length used for packet decryption. This is used for
     * storing the packet length for the next packet that is readable.
     * Since TCP sessions ensure that all data arrives to the server in order,
     * we can decodeByte packet data in the correct order.
     */
    private int storedLength = -1;
    /**
     * Channel object associated with this specific net.swordie.ms.client. Used for all
     * I/O operations regarding a MapleStory game session.
     */
    protected final Channel ch;
    
    /**
     * Lock regarding the encoding of packets to be sent to remote 
     * sessions.
     */
    private final ReentrantLock lock;
    
    /**
     * InPacket object for this specific session since this can help
     * scaling compared to keeping OutPacket for each session.
     */
    private final InPacket r;
    
    /**
     * Empty constructor for child class implementation.
     */
    private NettyClient() {
        ch = null;
        lock = null;
        r = null;
    }
    
    /**
     * Construct a new NettyClient with the corresponding Channel that
     * will be used to write to as well as the send and recv seeds or IVs.
     * @param c the channel object associated with this net.swordie.ms.client session.
     * @param alpha the send seed or IV.
     * @param delta the recv seed or IV.
     */
    public NettyClient(Channel c, byte[] alpha, byte[] delta) {
        ch = c;
        siv = alpha;
        riv = delta;
        r = new InPacket();
        lock = new ReentrantLock(true); // note: lock is fair to ensure logical sequence is maintained server-side
    }
    
    /**
     * Gets the InPacket object associated with this NettyClient.
     * @return a packet reader.
     */
    public final InPacket getReader() {
        return r;
    }
    
    /**
     * Gets the stored length for the next packet to be read. Used as
     * a decoding state variable to determine when it is ok to proceed with
     * decoding a net.swordie.ms.connection.packet.
     * @return stored length for next net.swordie.ms.connection.packet.
     */
    public final int getStoredLength() {
        return storedLength;
    }
    
    /**
     * Sets the stored length for the next packet to be read.
     * @param val length of the next packet to be read.
     */
    public final void setStoredLength(int val) {
        storedLength = val;
    }
    
    /**
     * Gets the current send seed or IV.
     * @return send IV.
     */
    public final byte[] getSendIV() {
        return siv;
    }
    
    /**
     * Gets the current recv seed or IV.
     * @return recv IV.
     */
    public final byte[] getRecvIV() {
        return riv;
    }

    /**
     * Sets the send seed or IV for this session.
     * @param alpha the new send IV.
     */
    public final void setSendIV(byte[] alpha) {
        siv = alpha;
    }

    /**
     * Sets the recv seed or IV for this session.
     * @param delta  the new recv IV.
     */
    public final void setRecvIV(byte[] delta) {
        riv = delta;
    }
    
    /**
     * Writes a message to the channel. Gets encoded later in the
     * pipeline.
     * @param msg the message to be sent.
     */
    public void write(Packet msg) {
        if (isUnverifiedHeader(msg) || isHeldByFieldTest(msg)) {
            return;
        }
        msg = overridden(msg);
        if (msg.size() > MAX_PACKET_SIZE) {
            log.error(String.format("Packet is too big! ([%s] %d size = %d)",
                    msg instanceof InPacket ? "In" : "Out", msg.getHeader(), msg.size()));
        } else {
            ch.writeAndFlush(msg);
        }
    }

    /**
     * For testing a client against hand-made packet bytes: when debug\<opcode>.hex exists (hex text, without the
     * opcode), that opcode's packets are sent with its bytes instead. Read on every send.
     */
    private static Packet overridden(Packet msg) {
        if (msg instanceof InPacket) {
            return msg;
        }
        int op = msg.getHeader() & 0xFFFF;
        java.nio.file.Path file = java.nio.file.Path.of(ServerConstants.DIR, "debug", op + ".hex");
        if (!java.nio.file.Files.isRegularFile(file)) {
            return msg;
        }
        try {
            String hex = java.nio.file.Files.readString(file).replaceAll("[^0-9A-Fa-f]", "");
            byte[] data = new byte[hex.length() / 2];
            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
            }
            var out = new net.swordie.ms.connection.OutPacket(op);
            out.encodeArr(data);
            log.warn("Sent {} from {} ({} bytes) instead of the server's packet", op, file, data.length);
            return out;
        } catch (Exception e) {
            log.error("Could not use " + file, e);
            return msg;
        }
    }

    /** Out opcodes never sent to clients, for finding which packet a client chokes on (server.properties). */
    private static final Set<Integer> HELD_OPCODES = parseOpcodes(
            net.swordie.ms.ServerSettings.getString("debug.holdOpcodes", ""));

    private static Set<Integer> parseOpcodes(String list) {
        Set<Integer> ops = new java.util.HashSet<>();
        for (String s : list.split(",")) {
            if (!s.isBlank()) {
                ops.add(Integer.parseInt(s.trim()));
            }
        }
        if (!ops.isEmpty()) {
            log.warn("Holding back Out opcodes {} (debug.holdOpcodes)", ops);
        }
        return ops;
    }

    /**
     * Packets whose OutHeader has no known value for this client version are not sent: the old value could mean
     * something else to the client. Each sending method is logged once. Held opcodes are dropped silently.
     */
    private static boolean isUnverifiedHeader(Packet msg) {
        if (msg instanceof InPacket) {
            return false;
        }
        if (HELD_OPCODES.contains(msg.getHeader() & 0xFFFF)) {
            return true;
        }
        if ((msg.getHeader() & 0xFFFF) != (ServerConstants.UNVERIFIED_OP & 0xFFFF)) {
            return false;
        }
        String sender = StackWalker.getInstance().walk(frames -> frames
                .filter(f -> !f.getClassName().startsWith("net.swordie.ms.connection.netty"))
                .limit(3)
                .map(f -> f.getClassName().substring(f.getClassName().lastIndexOf('.') + 1) + "." + f.getMethodName())
                .collect(java.util.stream.Collectors.joining(" < ")));
        if (loggedUnverifiedSenders.add(sender)) {
            log.warn("Dropped a packet with an unverified opcode, sent from {}", sender);
        }
        return true;
    }

    /** How many TEMPORARY_STAT_SET/RESET packets per connection the field test logs (test.logSpamLimit). */
    private static final int BUFF_LOG_LIMIT = parseLimit(
            net.swordie.ms.ServerSettings.getString("test.logSpamLimit", "50"), 50);

    private static int parseLimit(String s, int def) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /** Suffixes of the test.fieldAllow* / test.fieldInAllow* lists; "Audit" holds numbers checked by the opcode audit. */
    public static final String[] GATE_SUFFIXES = {"", "Endgame", "Boss", "Social", "Cash", "Life", "Multi",
            "Familiar", "Enhance", "Loop", "Jobs", "Audit"};

    /** Out headers sent while the field test gate is on (test.fieldAllow*, minus test.fieldDeny). */
    public static final Set<String> FIELD_TEST_OUT_ALLOW = gateList("test.fieldAllow", "test.fieldDeny", "SET_FIELD");

    /**
     * Every list under the prefix, from the defaults and server.properties together, so an entry added to either file
     * counts. The deny list takes names back out.
     */
    private static Set<String> gateList(String prefix, String denyKey, String fallback) {
        Set<String> names = new java.util.HashSet<>();
        for (String suffix : GATE_SUFFIXES) {
            names.addAll(parseNames(net.swordie.ms.ServerSettings.getMergedList(prefix + suffix)));
        }
        if (names.isEmpty()) {
            names.add(fallback);
        }
        names.removeAll(parseNames(net.swordie.ms.ServerSettings.getMergedList(denyKey)));
        return names;
    }

    public static Set<String> parseNames(String list) {
        Set<String> names = new java.util.HashSet<>();
        for (String s : list.split(",")) {
            if (!s.isBlank()) {
                names.add(s.trim());
            }
        }
        return names;
    }

    /**
     * v271 field test: from MIGRATE_IN on, only the allowed Out packets reach the client; the in-game Out numbers are
     * still v265's. Each held header is logged once per connection, with the packet count at the end of the log line.
     */
    private volatile boolean fieldTestGate;
    private final java.util.Map<String, Integer> heldByFieldTest = new java.util.concurrent.ConcurrentHashMap<>();
    private final java.util.Map<String, Integer> sentByFieldTest = new java.util.concurrent.ConcurrentHashMap<>();
    private final java.util.Map<Integer, Integer> handledByFieldTest = new java.util.concurrent.ConcurrentHashMap<>();

    /** The first 96 bytes of a packet (after the opcode) as hex, for the field test log. */
    public static String shortHex(Packet msg) {
        byte[] data = msg.getData();
        int from = msg instanceof InPacket ? 0 : 2;
        StringBuilder sb = new StringBuilder();
        for (int i = from; i < data.length && i < from + 96; i++) {
            sb.append(String.format("%02X ", data[i]));
        }
        if (data.length > from + 96) {
            sb.append("...");
        }
        return sb.toString().trim();
    }

    /** Counts an In packet the field test handled; true for the first 5 of each opcode (logged with their bytes). */
    public boolean noteHandledByFieldTest(int op) {
        return handledByFieldTest.merge(op, 1, Integer::sum) <= 5;
    }

    /** Who this connection is in packet log lines: the character's name once in game, else "-". */
    public String logWho() {
        return "-";
    }

    public boolean isFieldTestGate() {
        return fieldTestGate;
    }

    public void setFieldTestGate(boolean fieldTestGate) {
        this.fieldTestGate = fieldTestGate;
    }

    private boolean isHeldByFieldTest(Packet msg) {
        if (!fieldTestGate || msg instanceof InPacket) {
            return false;
        }
        var header = net.swordie.ms.handlers.header.OutHeader.getOutHeaderByOp(msg.getHeader());
        String name = header == null ? String.valueOf(msg.getHeader() & 0xFFFF) : header.name();
        // the spam list silences the field test lines too, not just the [Out] debug ones
        boolean spam = header != null && net.swordie.ms.handlers.header.OutHeader.isSpamHeader(header);
        if (!FIELD_TEST_OUT_ALLOW.contains(name) && msg.getData().length > 2) {
            // "NAME:n" allows one sub-type (the byte after the opcode), e.g. MESSAGE:1
            String sub = name + ":" + msg.getData()[2];
            if (FIELD_TEST_OUT_ALLOW.contains(sub) || "MESSAGE".equals(name) || "EFFECT".equals(name)) {
                name = sub; // held sub-typed packets are logged per sub-type
            }
        }
        if (FIELD_TEST_OUT_ALLOW.contains(name)) {
            int sent = sentByFieldTest.merge(name, 1, Integer::sum);
            boolean buff = name.startsWith("TEMPORARY_STAT_");
            if (!spam && (sent <= (buff ? BUFF_LOG_LIMIT : 5) || "SET_FIELD".equals(name))) {
                log.info(String.format("[Test] %s field test: sent %s (%d, %d bytes, #%d) at %d | %s", logWho(), name,
                        msg.getHeader() & 0xFFFF, msg.size(), sent, System.currentTimeMillis() % 100000,
                        shortHex(msg)));
            }
            return false;
        }
        int n = heldByFieldTest.merge(name, 1, Integer::sum);
        if (n == 1 && !spam) {
            log.info(String.format("[Test] %s field test: held back %s (v265 number %d)", logWho(), name,
                    msg.getHeader() & 0xFFFF));
        }
        return true;
    }

    /** The headers held back so far on this connection, with counts. */
    public String fieldTestHeldSummary() {
        return heldByFieldTest.toString();
    }

    /** In headers handled while the field test gate is on (test.fieldInAllow*, minus test.fieldInDeny). */
    public static final Set<String> FIELD_TEST_IN_ALLOW = gateList("test.fieldInAllow", "test.fieldInDeny",
            "CLIENT_ERROR");

    private final java.util.Map<Integer, Integer> unhandledByFieldTest = new java.util.concurrent.ConcurrentHashMap<>();

    /** Counts an In packet the field test didn't handle; true the first time its opcode shows up. */
    public boolean noteUnhandledByFieldTest(int op) {
        return unhandledByFieldTest.merge(op, 1, Integer::sum) == 1;
    }

    /** The In opcodes not handled so far on this connection, with counts. */
    public String fieldTestUnhandledSummary() {
        return unhandledByFieldTest.toString();
    }

    public void write(List<Packet> messages) {
        for (var msg : messages) {
            if (isUnverifiedHeader(msg) || isHeldByFieldTest(msg)) {
                continue;
            }
            msg = overridden(msg);
            if (msg.size() > MAX_PACKET_SIZE) {
                log.error(String.format("Packet is too big! ([%s] %d size = %d)",
                        msg instanceof InPacket ? "In" : "Out", msg.getHeader(), msg.size()));
            } else {
                ch.write(msg);
            }
        }

        ch.flush();
    }
    
    /**
     * Closes this channel and session.
     */
    public void close() {
        ch.close();
    }
    
    /**
     * Gets the remote IP address for this session.
     * @return the remote IP address.
     */
    public String getIP() {
        return ch.remoteAddress().toString().split(":")[0].substring(1);
    }
    
    /**
     * Acquires the encoding state for this specific send IV. This is to
     * prevent multiple encoding states to be possible at the same time. If 
     * allowed, the send IV would mutate to an unusable IV and the session would
     * be dropped as a result.
     */
    public final void acquireEncoderState() {
        lock.lock();
    }
    
    /**
     * Releases the encoding state for this specific send IV.
     */
    public final void releaseEncodeState() {
        lock.unlock();
    }

    private static final int RECENT_FRAMES = 64;
    private final java.util.ArrayDeque<String> recentFrames = new java.util.ArrayDeque<>();

    /**
     * Remembers a sent frame (wire header + start of the plain data), so a client error report, which carries the
     * wire header of the packet the client choked on, can be matched to the server packet behind it.
     */
    public final void noteSentFrame(byte[] header, byte[] plain, boolean simpleCipher) {
        StringBuilder sb = new StringBuilder(simpleCipher ? "" : "aes ");
        for (int i = 0; i < Math.min(4, header.length); i++) {
            sb.append(String.format("%02X ", header[i]));
        }
        sb.append("| len ").append(plain.length).append(" |");
        for (int i = 0; i < Math.min(16, plain.length); i++) {
            sb.append(String.format(" %02X", plain[i]));
        }
        synchronized (recentFrames) {
            if (recentFrames.size() >= RECENT_FRAMES) {
                recentFrames.pollFirst();
            }
            recentFrames.addLast(sb.toString());
        }
    }

    public final java.util.List<String> getRecentFrames() {
        synchronized (recentFrames) {
            return new java.util.ArrayList<>(recentFrames);
        }
    }
}
