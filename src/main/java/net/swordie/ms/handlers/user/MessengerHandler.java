package net.swordie.ms.handlers.user;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.MessengerPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Maple Messenger (484). Client requests: 0 enter (1, 4 room id; 0 = new room), 2 leave, 3 invite (S name),
 * 5 decline (S inviter, S own name, 1), 6 chat (S, S, 4), 7 (S, S). Rooms live in memory; a member that went
 * offline is dropped the next time the room is used.
 */
public class MessengerHandler {

    private static final int REQ_ENTER = 0;
    private static final int REQ_LEAVE = 2;
    private static final int REQ_INVITE = 3;
    private static final int REQ_DECLINE = 5;
    private static final int REQ_CHAT = 6;
    private static final int REQ_CHAT_2 = 7;

    private static final int SLOTS = 6;
    private static final int MAX_TEXT = 200;

    private static final AtomicInteger nextId = new AtomicInteger(1);
    private static final Map<Integer, Room> rooms = new ConcurrentHashMap<>();
    private static final Map<Integer, Integer> roomOfChar = new ConcurrentHashMap<>();

    private static class Room {
        final int id;
        final Char[] members = new Char[SLOTS];

        Room(int id) {
            this.id = id;
        }

        synchronized void prune() {
            for (int i = 0; i < SLOTS; i++) {
                Char m = members[i];
                if (m != null && (!m.isOnline() || m.getClient() == null
                        || !Integer.valueOf(id).equals(roomOfChar.get(m.getId())))) {
                    members[i] = null;
                    broadcast(MessengerPacket.leave(i), null);
                }
            }
        }

        synchronized int slotOf(Char chr) {
            for (int i = 0; i < SLOTS; i++) {
                if (members[i] != null && members[i].getId() == chr.getId()) {
                    return i;
                }
            }
            return -1;
        }

        synchronized int freeSlot() {
            for (int i = 0; i < SLOTS; i++) {
                if (members[i] == null) {
                    return i;
                }
            }
            return -1;
        }

        synchronized boolean isEmpty() {
            for (Char m : members) {
                if (m != null) {
                    return false;
                }
            }
            return true;
        }

        synchronized void broadcast(OutPacket packet, Char except) {
            for (Char m : members) {
                if (m != null && (except == null || m.getId() != except.getId())) {
                    m.write(packet);
                }
            }
        }
    }

    @Handler(op = InHeader.MESSENGER)
    public static void handleMessenger(Char chr, InPacket inPacket) {
        if (inPacket.getUnreadAmount() < 1) {
            return;
        }
        int type = inPacket.decodeByte();
        switch (type) {
            case REQ_ENTER -> {
                inPacket.decodeByte();
                enter(chr, inPacket.decodeInt());
            }
            case REQ_LEAVE -> leave(chr);
            case REQ_INVITE -> invite(chr, inPacket.decodeString());
            case REQ_DECLINE -> {
                Char inviter = chr.getWorld().getCharByName(inPacket.decodeString());
                if (inviter != null) {
                    inviter.write(MessengerPacket.declined(chr.getName(), false));
                }
            }
            case REQ_CHAT, REQ_CHAT_2 -> {
                String name = inPacket.decodeString();
                String text = inPacket.decodeString();
                int value = type == REQ_CHAT && inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : 0;
                Room room = roomOf(chr);
                if (room != null && text.length() <= MAX_TEXT) {
                    room.broadcast(MessengerPacket.chat(type, name, text, value, chr), chr);
                }
            }
            default -> {
            }
        }
    }

    private static Room roomOf(Char chr) {
        Integer id = roomOfChar.get(chr.getId());
        Room room = id == null ? null : rooms.get(id);
        if (room != null) {
            room.prune();
            if (room.slotOf(chr) < 0) {
                roomOfChar.remove(chr.getId());
                return null;
            }
        }
        return room;
    }

    private static void enter(Char chr, int roomId) {
        leave(chr);
        Room room;
        if (roomId == 0) {
            room = new Room(nextId.getAndIncrement());
            rooms.put(room.id, room);
        } else {
            room = rooms.get(roomId);
            if (room != null) {
                room.prune();
            }
        }
        int slot = room == null ? -1 : room.freeSlot();
        if (slot < 0) {
            chr.write(MessengerPacket.selfEnter(-1));
            return;
        }
        synchronized (room) {
            room.members[slot] = chr;
        }
        roomOfChar.put(chr.getId(), room.id);
        chr.write(MessengerPacket.selfEnter(slot));
        for (int i = 0; i < SLOTS; i++) {
            Char m = room.members[i];
            if (m != null && i != slot) {
                chr.write(MessengerPacket.enter(i, m));
            }
        }
        room.broadcast(MessengerPacket.enter(slot, chr), chr);
    }

    private static void leave(Char chr) {
        Integer id = roomOfChar.remove(chr.getId());
        Room room = id == null ? null : rooms.get(id);
        if (room == null) {
            return;
        }
        int slot = room.slotOf(chr);
        if (slot >= 0) {
            synchronized (room) {
                room.members[slot] = null;
            }
            room.broadcast(MessengerPacket.leave(slot), chr);
        }
        if (room.isEmpty()) {
            rooms.remove(room.id);
        }
    }

    private static void invite(Char chr, String name) {
        Room room = roomOf(chr);
        Char target = chr.getWorld().getCharByName(name);
        if (room == null || target == null || !target.isOnline() || target.getId() == chr.getId()) {
            chr.write(MessengerPacket.inviteResult(name, false));
            return;
        }
        if (room.freeSlot() < 0 || roomOfChar.containsKey(target.getId())) {
            chr.write(MessengerPacket.declined(target.getName(), true));
            return;
        }
        target.write(MessengerPacket.invite(chr, room.id));
        chr.write(MessengerPacket.inviteResult(target.getName(), true));
    }
}
