package net.swordie.ms.handlers.social;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.User;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.friend.Friend;
import net.swordie.ms.client.friend.FriendFlag;
import net.swordie.ms.client.friend.FriendType;
import net.swordie.ms.client.friend.result.FriendResult;
import net.swordie.ms.connection.ClientText;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.world.World;
import net.swordie.orm.dao.CharDao;
import net.swordie.orm.dao.SworDaoFactory;
import net.swordie.orm.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * FRIEND_REQUEST, v271: account friends only, every id is an account id. Character friends from older saves are
 * still listed (by their account id) and can be accepted, removed and edited the same way.
 */
public class FriendHandler {

    private static final UserDao userDao = (UserDao) SworDaoFactory.getByClass(User.class);
    private static final CharDao charDao = (CharDao) SworDaoFactory.getByClass(Char.class);

    private static final Logger log = LogManager.getLogger();

    /** Group names and the "accept requests" switch per account; not saved. */
    private static final Map<Integer, Map<Integer, String>> groupNames = new ConcurrentHashMap<>();
    private static final Map<Integer, Boolean> notAccepting = new ConcurrentHashMap<>();

    @Handler(op = InHeader.LOAD_ACCOUNT_ID_OF_CHARACTER_FRIEND_REQUEST)
    public static void handleLoadAccountIDOfCharacterFriendRequest(Client c, InPacket inPacket) {
    }

    /** The list the client shows: one entry per account (the account friend first). */
    public static Collection<Friend> v271List(Char chr) {
        Map<Integer, Friend> byAcc = new LinkedHashMap<>();
        for (Friend f : chr.getUser().getFriends()) {
            byAcc.putIfAbsent(f.getFriendAccountID(), f);
        }
        for (Friend f : chr.getFriends()) {
            byAcc.putIfAbsent(f.getFriendAccountID(), f);
        }
        return byAcc.values();
    }

    private static Friend find(Char chr, int accId) {
        Friend f = chr.getUser().getFriendByUserID(accId);
        if (f == null) {
            f = chr.getFriends().stream().filter(x -> x.getFriendAccountID() == accId).findAny().orElse(null);
        }
        return f;
    }

    private static void remove(Char chr, Friend f) {
        if (chr.getUser().getFriends().contains(f)) {
            chr.getUser().removeFriend(f);
        } else {
            chr.removeFriend(f);
        }
    }

    /** The other side of a friendship: online user or loaded from the DB. */
    private static User userOf(World world, int accId) {
        User u = world.getUserById(accId);
        return u != null ? u : userDao.getById(accId);
    }

    private static Char onlineChr(User u) {
        Char c = u == null ? null : u.getCurrentChr();
        return c != null && c.getClient() != null ? c : null;
    }

    private static void save(User... users) {
        for (User u : users) {
            if (u != null) {
                userDao.saveOrUpdateFriends(u);
            }
        }
    }

    private static void write(Char chr, FriendResult fr) {
        chr.write(WvsContext.friendResult(fr));
    }

    private static void reload(Char chr) {
        write(chr, FriendResult.loadFriends(v271List(chr)));
    }

    @Handler(op = InHeader.FRIEND_REQUEST)
    public static void handleFriendRequest(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        int type = inPacket.decodeByte();
        World world = c.getWorld();
        User me = chr.getUser();
        switch (type) {
            case 1 -> setFriend(chr, world, inPacket);
            case 2 -> accept(chr, world, inPacket.decodeInt());
            case 3 -> {
                int accId = inPacket.decodeInt();
                delete(chr, world, accId, false);
            }
            case 4 -> {
                int accId = inPacket.decodeInt();
                delete(chr, world, accId, true);
            }
            case 5 -> {
                List<Friend> requests = new ArrayList<>();
                for (Friend f : v271List(chr)) {
                    if (f.getV271Flag() == Friend.V271_REQUEST) {
                        requests.add(f);
                    }
                }
                if (requests.isEmpty()) {
                    write(chr, FriendResult.message(FriendType.FriendRes_RejectAll_NoRequest));
                    return;
                }
                for (Friend f : requests) {
                    unlink(chr, world, f, true, false);
                }
                write(chr, FriendResult.rejectAllDone(v271List(chr)));
            }
            case 6 -> {
                int a = inPacket.decodeInt();
                int b = inPacket.decodeInt();
                String nick = inPacket.decodeString();
                String memo = inPacket.decodeString();
                Friend f = find(chr, a);
                if (f == null) {
                    f = find(chr, b);
                }
                if (f == null || nick.length() > 12 || memo.length() > 200 || !ClientText.isPrintable(memo)) {
                    write(chr, FriendResult.message(FriendType.FriendRes_SetFriend_Unknown));
                    return;
                }
                f.setNickname(nick);
                f.setMemo(memo);
                write(chr, FriendResult.updateFriend(f));
                save(me);
            }
            case 7, 12 -> {
                Map<Integer, String> groups = groupNames.computeIfAbsent(me.getId(), k -> new TreeMap<>());
                int n = inPacket.decodeInt();
                for (int i = 0; i < n && i < 64; i++) {
                    int key = type == 7 ? inPacket.decodeInt() : inPacket.decodeByte();
                    String name = inPacket.decodeString();
                    if (name.length() <= 16) {
                        groups.put(key, name);
                    }
                }
                if (type == 7) {
                    // 12 comes from the client's own list refresh: answering it would start the refresh again
                    write(chr, FriendResult.groupNames(groups));
                }
            }
            case 8 -> {
                boolean allow = inPacket.decodeByte() != 0;
                notAccepting.put(me.getId(), !allow);
                write(chr, FriendResult.allowRequests(allow));
            }
            case 9 -> bestFriendRequest(chr, inPacket.decodeInt());
            case 10, 11 -> bestFriendAnswer(chr, world, inPacket.decodeInt(), type == 10);
            default -> log.info(String.format("[Friend] v271 request %d not handled: %s", type, inPacket));
        }
    }

    /** Best-friend invites waiting for an answer: invitee char id -> requester char id. */
    private static final Map<Integer, Integer> bestFriendInvites = new ConcurrentHashMap<>();

    /** The client sends it only for a friend in the same map. */
    private static void bestFriendRequest(Char chr, int targetId) {
        Char target = chr.getField() == null ? null : chr.getField().getCharByID(targetId);
        Friend f = target == null ? null : find(chr, target.getUserId());
        if (target == null || target == chr || f == null || f.getV271Flag() != Friend.V271_FRIEND) {
            chr.chatMessage("Your friend has to be in this map.");
            return;
        }
        if (bestFriendInvites.putIfAbsent(target.getId(), chr.getId()) != null) {
            write(chr, FriendResult.message(FriendType.FriendRes_AlreadyMadeRequest));
            return;
        }
        write(target, FriendResult.bestFriendInvite(chr.getId(), chr.getName()));
        chr.chatMessage(String.format("You asked %s to be your best friend.", target.getName()));
    }

    private static void bestFriendAnswer(Char chr, World world, int requesterId, boolean accept) {
        if (!bestFriendInvites.remove(chr.getId(), requesterId)) {
            return;
        }
        Char requester = world == null ? null : world.getCharByID(requesterId);
        if (requester == null) {
            return;
        }
        if (accept) {
            requester.chatMessage(String.format("%s is now your best friend.", chr.getName()));
            chr.chatMessage(String.format("%s is now your best friend.", requester.getName()));
        } else {
            write(requester, FriendResult.message(FriendType.FriendRes_EventBestFriend_Refused, chr.getName()));
        }
    }

    private static void setFriend(Char chr, World world, InPacket inPacket) {
        String name = inPacket.decodeString();
        String group = inPacket.decodeString();
        String memo = inPacket.decodeString();
        String nick = inPacket.decodeString();
        if (memo.length() > 200 || !ClientText.isPrintable(memo) || nick.length() > 12) {
            return;
        }
        Char other = world.getCharByName(name);
        if (other == null) {
            other = charDao.getByNameAndWorld(name, chr.getClient().getWorldId());
        }
        if (other == null) {
            write(chr, FriendResult.message(FriendType.FriendRes_SetFriend_Unknown, "That character is not registered."));
            return;
        }
        if (other.getUserId() == chr.getUserId()) {
            write(chr, FriendResult.message(FriendType.FriendRes_SetFriend_CantSelf));
            return;
        }
        Friend existing = find(chr, other.getUserId());
        if (existing != null) {
            switch (existing.getV271Flag()) {
                case Friend.V271_REQUEST -> accept(chr, world, other.getUserId());
                case Friend.V271_WAITING -> write(chr, FriendResult.message(FriendType.FriendRes_SetFriend_AlreadyRequested));
                default -> write(chr, FriendResult.message(FriendType.FriendRes_SetFriend_AlreadySet));
            }
            return;
        }
        if (v271List(chr).size() >= chr.getBuddyCapacity()) {
            write(chr, FriendResult.message(FriendType.FriendRes_SetFriend_FullMe));
            return;
        }
        User otherUser = userOf(world, other.getUserId());
        if (otherUser == null) {
            write(chr, FriendResult.message(FriendType.FriendRes_SetFriend_Unknown, "That character is not registered."));
            return;
        }
        if (Boolean.TRUE.equals(notAccepting.get(otherUser.getId()))) {
            write(chr, FriendResult.message(FriendType.FriendRes_NotAccepting, other.getName()));
            return;
        }
        if (other.hasChrBlacklisted(chr.getId())) {
            write(chr, FriendResult.message(FriendType.FriendRes_SetFriend_Unknown, "That character is not accepting requests."));
            return;
        }
        Friend mine = new Friend();
        mine.setFriendID(other.getId());
        mine.setFriendAccountID(other.getUserId());
        mine.setName(other.getName());
        mine.setNickname(nick.isEmpty() ? other.getName() : nick);
        mine.setGroup(group);
        mine.setMemo(memo);
        mine.setFlag(FriendFlag.Waiting);
        chr.getUser().addFriend(mine);

        Friend theirs = otherUser.getFriendByUserID(chr.getUserId());
        if (theirs == null) {
            theirs = new Friend();
            theirs.setFriendID(chr.getId());
            theirs.setFriendAccountID(chr.getUserId());
            theirs.setName(chr.getName());
            theirs.setNickname(chr.getName());
            theirs.setGroup("");
            theirs.setMemo("");
            theirs.setFlag(FriendFlag.AccountFriendRequest);
            otherUser.addFriend(theirs);
        }
        theirs.setChr(chr);
        Char otherOnline = onlineChr(otherUser);
        mine.setChr(otherOnline);
        write(chr, FriendResult.message(FriendType.FriendRes_SetFriend_Done, other.getName()));
        reload(chr);
        if (otherOnline != null) {
            write(otherOnline, FriendResult.friendInvite(theirs, chr.getLevel(), chr.getJob(), chr.getSubJob()));
        }
        save(chr.getUser(), otherUser);
    }

    private static void accept(Char chr, World world, int accId) {
        Friend f = find(chr, accId);
        if (f == null || f.getV271Flag() != Friend.V271_REQUEST) {
            write(chr, FriendResult.message(FriendType.FriendRes_AcceptFriend_Unknown));
            return;
        }
        User otherUser = userOf(world, accId);
        Char otherOnline = onlineChr(otherUser);
        f.setFlag(f.isAccount() ? FriendFlag.AccountFriendOnline : FriendFlag.FriendOnline);
        f.setChr(otherOnline);
        Friend theirs = otherUser == null ? null : otherUser.getFriendByUserID(chr.getUserId());
        if (theirs != null) {
            theirs.setFlag(FriendFlag.AccountFriendOnline);
            theirs.setChr(chr);
        }
        write(chr, FriendResult.updateFriend(f));
        if (otherOnline != null && theirs != null) {
            write(otherOnline, FriendResult.updateFriend(theirs));
            otherOnline.chatMessage(String.format("%s has accepted your friend request.", chr.getName()));
        }
        save(chr.getUser(), otherUser);
    }

    private static void delete(Char chr, World world, int accId, boolean refuse) {
        Friend f = find(chr, accId);
        if (f == null) {
            write(chr, FriendResult.message(FriendType.FriendRes_SetFriend_Unknown));
            return;
        }
        unlink(chr, world, f, refuse, true);
    }

    /** Removes the entry on both sides; the other side is told when online. */
    private static void unlink(Char chr, World world, Friend f, boolean refuse, boolean tellMe) {
        int accId = f.getFriendAccountID();
        remove(chr, f);
        if (tellMe) {
            write(chr, FriendResult.deleteFriend(f));
        }
        User otherUser = userOf(world, accId);
        Char otherOnline = onlineChr(otherUser);
        Friend theirs = otherUser == null ? null : otherUser.getFriendByUserID(chr.getUserId());
        if (theirs != null) {
            otherUser.removeFriend(theirs);
        } else if (otherUser != null) {
            Char otherChr = otherOnline != null ? otherOnline : charDao.getById(f.getFriendID());
            Friend charEntry = otherChr == null ? null : otherChr.getFriendByCharID(chr.getId());
            if (charEntry != null) {
                otherChr.removeFriend(charEntry);
                theirs = charEntry;
                if (otherOnline == null) {
                    charDao.saveOrUpdate(otherChr);
                }
            }
        }
        if (otherOnline != null && theirs != null) {
            write(otherOnline, FriendResult.deleteFriend(theirs));
            if (refuse) {
                write(otherOnline, FriendResult.message(FriendType.FriendRes_Refused, chr.getName()));
            }
        }
        save(chr.getUser(), otherUser);
        if (!chr.getFriends().isEmpty()) {
            charDao.saveOrUpdate(chr);
        }
    }

    /**
     * Test helper (v271, no bots on the test server): makes name's account a friend of chr's account (both sides),
     * or with request=true leaves a request from name in chr's list.
     */
    public static String testLink(Char chr, String name, boolean request) {
        World world = chr.getClient().getWorld();
        Char other = world.getCharByName(name);
        if (other == null) {
            other = charDao.getByNameAndWorld(name, chr.getClient().getWorldId());
        }
        if (other == null || other.getUserId() == chr.getUserId()) {
            return "No character named " + name + " on another account.";
        }
        User otherUser = userOf(world, other.getUserId());
        Friend mine = find(chr, other.getUserId());
        if (mine == null) {
            mine = new Friend();
            mine.setFriendID(other.getId());
            mine.setFriendAccountID(other.getUserId());
            mine.setName(other.getName());
            mine.setNickname(other.getName());
            mine.setGroup("");
            mine.setMemo("");
            chr.getUser().addFriend(mine);
        }
        mine.setFlag(request ? FriendFlag.AccountFriendRequest : FriendFlag.AccountFriendOffline);
        mine.setChr(onlineChr(otherUser));
        if (otherUser != null) {
            Friend theirs = otherUser.getFriendByUserID(chr.getUserId());
            if (theirs == null) {
                theirs = new Friend();
                theirs.setFriendID(chr.getId());
                theirs.setFriendAccountID(chr.getUserId());
                theirs.setName(chr.getName());
                theirs.setNickname(chr.getName());
                theirs.setGroup("");
                theirs.setMemo("");
                otherUser.addFriend(theirs);
            }
            theirs.setFlag(request ? FriendFlag.Waiting : FriendFlag.AccountFriendOffline);
        }
        save(chr.getUser(), otherUser);
        if (request) {
            write(chr, FriendResult.friendInvite(mine, other.getLevel(), other.getJob(), other.getSubJob()));
        } else {
            reload(chr);
        }
        return (request ? "Request from " : "Friend: ") + other.getName() + " (account " + other.getUserId() + ").";
    }

    /** Test helper: shows a friend on a channel (0-based; -1 offline) without that character logging in. */
    public static String testStatus(Char chr, String name, int channel) {
        Friend f = v271List(chr).stream().filter(x -> x.getName() != null && x.getName().equalsIgnoreCase(name))
                .findAny().orElse(null);
        if (f == null) {
            return name + " is not in your friend list.";
        }
        f.setShownChannel(channel);
        write(chr, FriendResult.updateFriend(f));
        return f.getName() + " shown on channel index " + channel + ".";
    }
}
