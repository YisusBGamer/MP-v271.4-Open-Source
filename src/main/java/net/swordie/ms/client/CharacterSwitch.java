package net.swordie.ms.client;

import net.swordie.ms.Server;
import net.swordie.ms.ServerSettings;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.netty.SwitchCharWatch;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.handlers.header.OutHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Switch Characters. The v265 client sends USER_SWITCH_CHARACTER_REQUEST (int target id, str ".", byte).
 * <p>
 * Nexon's cookie path (USER_SWITCH_CHARACTER_RESULT 0 -> REQUEST_RELOGIN_COOKIE -> ISSUE_RELOGIN_COOKIE) does not
 * work on this client: after the cookie it stays in the field and never goes back to the title, and a new login
 * never carries the cookie (CHECK_LOGIN_AUTH_INFO's string is empty, SPECIAL_LOGIN_AUTH_REQUEST only has the launch
 * token). So, as the reference server: remember the target for this login and send RETURN_TO_TITLE. The client
 * closes, reconnects and logs in again with its launch token by itself; that login (same login id, same address,
 * within a minute, once) is answered with the world and character results, so it goes straight into the target.
 */
public final class CharacterSwitch {
    private static final Logger log = LogManager.getRootLogger();

    /** false: everyone but switch.testUsers gets the title screen and picks the character by hand. */
    public static final boolean ENABLED = true;

    /** Login names that get the full flow while ENABLED is false (server.properties switch.testUsers). */
    private static final Set<String> TEST_USERS = parseNames(ServerSettings.getString("switch.testUsers", ""));

    private static final long TTL_MS = 60_000;
    private static final byte RESULT_REFUSED = 2;

    public record Ticket(int targetId, int worldId, int channelId, String ip, byte[] machineId, long expires) {
        boolean isExpired(long now) {
            return now > expires;
        }
    }

    /** By login (user) id. */
    private static final Map<Integer, Ticket> tickets = new ConcurrentHashMap<>();

    private CharacterSwitch() {
    }

    private static Set<String> parseNames(String list) {
        Set<String> names = new java.util.HashSet<>();
        for (String s : list.split(",")) {
            if (!s.isBlank()) {
                names.add(s.trim().toLowerCase(Locale.ROOT));
            }
        }
        return names;
    }

    private static boolean enabledFor(Client c) {
        if (ENABLED) {
            return true;
        }
        User user = c.getUser();
        return user != null && user.getName() != null && TEST_USERS.contains(user.getName().toLowerCase(Locale.ROOT));
    }

    public static void handleRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        if (chr == null) {
            return;
        }
        if (!enabledFor(c)) {
            c.write(WvsContext.returnToTitle());
            return;
        }
        try {
            int targetId = inPacket.decodeInt();
            String refusal = refusal(chr, targetId);
            if (refusal != null) {
                chr.chatMessage(refusal);
                c.write(refused());
                return;
            }
            User user = c.getUser();
            byte[] machineId = c.getMachineID();
            if (machineId == null) {
                // the move into the game checks the machine id against the login's; without one, pick by hand
                log.info(String.format("[Switch] %s: no machine id on this connection, title screen only", c.getIP()));
            } else {
                long now = System.currentTimeMillis();
                purgeExpired(now);
                tickets.put(user.getId(), new Ticket(targetId, chr.getAccount().getWorldId(), c.getChannel(),
                        c.getIP(), machineId.clone(), now + TTL_MS));
                log.info(String.format("[Switch] %s: login id %d, character %d -> %d, back to the title", c.getIP(),
                        user.getId(), chr.getId(), targetId));
            }
            c.write(WvsContext.returnToTitle());
            SwitchCharWatch.onTitleSent(c);
        } catch (Exception e) {
            log.error("[Switch] request failed, back to the title screen", e);
            c.write(WvsContext.returnToTitle());
        }
    }

    private static String refusal(Char chr, int targetId) {
        Account account = chr.getAccount();
        if (targetId == chr.getId()) {
            return "You are already playing that character.";
        }
        if (account == null || !account.hasCharacter(targetId)) {
            return "That character is not on this account in this world.";
        }
        if (chr.isInCashShop() || chr.isInAuctionHouse()) {
            return "You cannot switch characters here.";
        }
        if (Server.getInstance().isInShutdown()) {
            return "The server is restarting.";
        }
        return null;
    }

    /**
     * The ticket of a login that just authenticated with its launch token, if it was issued to this login id from
     * this address less than a minute ago. Used up when returned; another address leaves it to expire.
     */
    public static Ticket takeTicket(int userId, String ip) {
        long now = System.currentTimeMillis();
        purgeExpired(now);
        Ticket ticket = tickets.get(userId);
        if (ticket == null || ticket.isExpired(now) || !ticket.ip().equals(ip)) {
            return null;
        }
        return tickets.remove(userId, ticket) ? ticket : null;
    }

    private static void purgeExpired(long now) {
        tickets.values().removeIf(t -> t.isExpired(now));
    }

    /** USER_SWITCH_CHARACTER_RESULT 2: "You cannot do that at the moment." */
    private static OutPacket refused() {
        OutPacket outPacket = new OutPacket(OutHeader.USER_SWITCH_CHARACTER_RESULT);
        outPacket.encodeByte(RESULT_REFUSED);
        return outPacket;
    }
}
