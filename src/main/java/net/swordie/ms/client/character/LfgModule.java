package net.swordie.ms.client.character;

import net.swordie.ms.Server;
import net.swordie.ms.client.party.Party;
import net.swordie.ms.client.party.PartyMember;
import net.swordie.ms.client.party.PartyResult;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * "Looking for group" board (@menu > Looking for group): a party leader (or a player without a party, who gets
 * one) posts what the group is for; anyone in the same world sees the list and joins with one click (the post is the
 * leader's invitation). Posts are kept in memory: they end after {@link #POST_MINUTES} minutes, when the leader logs
 * out, leaves the party, or the party is full.
 */
public final class LfgModule {

    private static final Logger log = LogManager.getLogger(LfgModule.class);

    public static final String[] CATEGORIES = {"Boss", "Party Quest", "Training", "Questing", "Other"};
    public static final int POST_MINUTES = 30;
    /** One post (and one world announcement) per character per this many minutes. */
    public static final int POST_COOLDOWN_MINUTES = 3;
    public static final int NOTE_MAX = 40;

    private static final class Post {
        final int charId;
        final int worldId;
        final String category;
        final String note;
        final long created;

        Post(int charId, int worldId, String category, String note) {
            this.charId = charId;
            this.worldId = worldId;
            this.category = category;
            this.note = note;
            this.created = System.currentTimeMillis();
        }
    }

    private static final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private static final Map<Integer, Long> lastPost = new ConcurrentHashMap<>();

    private LfgModule() {
    }

    private static int worldId(Char chr) {
        return chr.getClient() == null || chr.getClient().getWorld() == null ? -1
                : chr.getClient().getWorld().getWorldId().getVal();
    }

    private static Char findOnline(int worldId, int charId) {
        World world = Server.getInstance().getWorldById(worldId);
        return world == null ? null : world.getCharByID(charId);
    }

    /** Drops posts that ended. @return the posts still open in the character's world, newest first */
    private static List<Post> openPosts(Char chr) {
        long now = System.currentTimeMillis();
        int world = worldId(chr);
        List<Post> open = new ArrayList<>();
        for (Post p : new ArrayList<>(posts.values())) {
            Char leader = findOnline(p.worldId, p.charId);
            Party party = leader == null ? null : leader.getParty();
            if (leader == null || now - p.created > POST_MINUTES * 60_000L || party == null
                    || party.getPartyLeaderID() != leader.getId() || party.isFull()) {
                posts.remove(p.charId);
                continue;
            }
            if (p.worldId == world) {
                open.add(p);
            }
        }
        open.sort((a, b) -> Long.compare(b.created, a.created));
        return open;
    }

    public static int countOpen(Char chr) {
        int n = 0;
        for (Post p : openPosts(chr)) {
            if (p.charId != chr.getId()) {
                n++;
            }
        }
        return n;
    }

    /** @return the post ids (leader character ids) shown in {@link #getBoardText}, in the same order */
    public static List<Integer> getBoardIds(Char chr) {
        List<Integer> ids = new ArrayList<>();
        for (Post p : openPosts(chr)) {
            ids.add(p.charId);
        }
        return ids;
    }

    /** What a viewer of the board sees of one post (for the bots, which read the board as a player would). */
    public record PostView(int leaderId, String category, String note, long created) {
    }

    /** @return the open posts in the character's world, newest first (the character's own left out) */
    public static List<PostView> getOpenPosts(Char chr) {
        List<PostView> res = new ArrayList<>();
        for (Post p : openPosts(chr)) {
            if (p.charId != chr.getId()) {
                res.add(new PostView(p.charId, p.category, p.note, p.created));
            }
        }
        return res;
    }

    /** @return whether the leader's post is still on the board */
    public static boolean isOpen(Char viewer, int leaderId) {
        return openPosts(viewer).stream().anyMatch(p -> p.charId == leaderId);
    }

    /** @return one line describing a post, for the board */
    public static String describe(Char viewer, int leaderId) {
        Post p = posts.get(leaderId);
        if (p == null) {
            return "";
        }
        Char leader = findOnline(p.worldId, p.charId);
        if (leader == null || leader.getParty() == null) {
            return "";
        }
        Party party = leader.getParty();
        int members = party.getMembers().size();
        long minutes = (System.currentTimeMillis() - p.created) / 60_000; // only shown once a post gets old
        return String.format("#b[%s]#k %s#d%s Lv. %d, %d/6%s#k%s", p.category, p.note.isEmpty() ? "" : p.note + "  ",
                leader.getName(), leader.getLevel(), members, minutes >= 10 ? ", " + minutes + "m ago" : "",
                NewPlayerModule.sproutTag(leader));
    }

    /** @return whether this character has an open post */
    public static boolean hasPost(Char chr) {
        Post p = posts.get(chr.getId());
        return p != null && openPosts(chr).stream().anyMatch(o -> o.charId == chr.getId());
    }

    /** Posts (or replaces) the character's group. @return a message for the player */
    public static synchronized String post(Char chr, int category, String note) {
        if (net.swordie.ms.ext.ServerHooks.isTestCharacter(chr)) {
            return "Not posted: test characters stay off the board.";
        }
        if (category < 0 || category >= CATEGORIES.length) {
            return "Pick what the group is for.";
        }
        Party party = chr.getParty();
        if (party != null && party.getPartyLeaderID() != chr.getId()) {
            return "Only the party leader can post.";
        }
        if (party != null && party.isFull()) {
            return "Your party is already full.";
        }
        Long last = lastPost.get(chr.getId());
        long now = System.currentTimeMillis();
        if (last != null && now - last < POST_COOLDOWN_MINUTES * 60_000L && !posts.containsKey(chr.getId())) {
            return "You just posted. Try again in a few minutes.";
        }
        String clean = note == null ? "" : note.replaceAll("[#\\r\\n\\\\]", "").trim();
        if (clean.length() > NOTE_MAX) {
            clean = clean.substring(0, NOTE_MAX);
        }
        if (party == null) {
            party = Party.createNewParty(false, false, chr.getName() + "'s party", chr.getClient().getWorld());
            party.addPartyMember(chr);
            party.broadcast(WvsContext.partyResult(PartyResult.createNewParty(party)));
        }
        boolean announce = last == null || now - last >= POST_COOLDOWN_MINUTES * 60_000L;
        posts.put(chr.getId(), new Post(chr.getId(), worldId(chr), CATEGORIES[category], clean));
        lastPost.put(chr.getId(), now);
        if (announce && chr.getClient().getWorld() != null) {
            net.swordie.ms.world.WorldNews.publish(worldId(chr), net.swordie.ms.world.WorldNews.Type.LFG_POST,
                    String.format("[LFG] %s (Lv. %d): %s%s - type @lfg to join", chr.getName(), chr.getLevel(),
                            CATEGORIES[category], clean.isEmpty() ? "" : " - " + clean),
                    List.of(chr.getId()), List.of(chr.getName()), Map.of("category", CATEGORIES[category],
                            "note", clean, "level", String.valueOf(chr.getLevel())));
        }
        log.info(String.format("LFG: %s posted %s \"%s\"", chr.getName(), CATEGORIES[category], clean));
        return "Posted for " + POST_MINUTES + " minutes (or until your party is full).";
    }

    /** Takes the character's post off the board. */
    public static String remove(Char chr) {
        return posts.remove(chr.getId()) != null ? "Post removed." : "You have no post.";
    }

    /** Joins the posted group of the given leader. @return a message for the player */
    public static synchronized String join(Char chr, int leaderId) {
        if (net.swordie.ms.ext.ServerHooks.isTestCharacter(chr)) {
            return "Not joined: test characters never join players.";
        }
        Post p = posts.get(leaderId);
        if (p == null || p.worldId != worldId(chr)) {
            return "That group isn't on the board any more.";
        }
        if (leaderId == chr.getId()) {
            return "That's your own group.";
        }
        if (chr.getParty() != null) {
            return "Leave your current party first.";
        }
        Char leader = findOnline(p.worldId, leaderId);
        Party party = leader == null ? null : leader.getParty();
        if (party == null || party.getPartyLeaderID() != leaderId) {
            posts.remove(leaderId);
            return "That group isn't on the board any more.";
        }
        if (party.isFull()) {
            posts.remove(leaderId);
            return "That group is already full.";
        }
        party.addPartyMember(chr);
        if (chr.getParty() != party) {
            return "Could not join that group.";
        }
        for (PartyMember pm : party.getOnlineMembers()) {
            Char member = pm.getChr();
            if (member != null && member != chr) {
                chr.write(UserRemote.receiveHP(member));
            }
        }
        party.broadcast(UserRemote.receiveHP(chr));
        leader.chatMessage(String.format("[LFG] %s (Lv. %d) joined your party.",
                chr.getName(), chr.getLevel()));
        if (party.isFull()) {
            posts.remove(leaderId);
        }
        return "Joined " + leader.getName() + "'s party. They're in #b#m" + leader.getFieldID() + "##k.";
    }
}
