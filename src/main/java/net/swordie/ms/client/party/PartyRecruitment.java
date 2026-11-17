package net.swordie.ms.client.party;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.LfgModule;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.socialbot.SocialBotFilter;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * Party Finder (the boss party recruitment board of the Party window). A party leader posts an ad for a boss and
 * difficulty with a description and entry minimums; anyone in the world sees the open ads, applies to one, and the
 * leader accepts or refuses each applicant. Ads end when the leader ends them, the party fills, disbands or changes
 * leader. A bot may hold an ad without a party yet ({@link #postHosted}); its code answers the applicants.
 * <p>
 * Client requests (PARTY_REQUEST type byte): 0x10 make a party and post (S name, 1 appliable, 4 loot, post), 0x11 post,
 * 0x13 end, 0x14 change (post), 0x15 apply (4 party), 0x17 cancel the application (4 party), 0x18 answer (1: 5 accept /
 * 4 refuse, 4 applicant), 0x19 list (the window opening and its refresh button, at most once a second), 0x1a view one
 * ad (4 party, 4 boss, 4 difficulty). A post is 4 boss (Etc/BossParty.img key, -1: none), then 1 difficulty (the
 * entry's "difficulty" value), S description, 4 level, 4 Arcane Power, 4 Sacred Power, 4 Legion level, 8 combat power.
 * <p>
 * Replies (PARTY_RESULT type byte, then a 4-byte code): 72 own party's ad (0 begun + ad, 1 ended, 2 changed + ad +
 * 4 n x 4), 74/75/76 post/end/change failed (13: no permission, else the shared codes of 73), 77 apply (0 sent: S
 * leader, 4 party, board entry; 4 refused: S leader, 4 party; 8: 4 party gone; 9 full; 10 in a party; 14 can't apply;
 * 18 applicant list full), 78 application ended (4 6, 4, 4, 1 auto), 79 answer failed, 81 board (1 ad gone: 4 party,
 * 1 ended, S leader; 10 list: 4 n x (4 party, entry); 11 view: 4 party, 1 found, entry or a new list), 82 own party's
 * applicants (7 removed: 4 char; 8 added: member + 8 time). An ad is the post, 8 posted time and the leader's chat
 * sender info; a board entry is 1, the six members, 4 leader, 4 n x (member, 8 applied time), then the ad.
 */
public final class PartyRecruitment {

    private static final Logger log = LogManager.getLogger(PartyRecruitment.class);

    public static final int REQ_CREATE_AND_POST = 0x10, REQ_POST = 0x11, REQ_END = 0x13, REQ_CHANGE = 0x14,
            REQ_APPLY = 0x15, REQ_CANCEL = 0x17, REQ_ANSWER = 0x18, REQ_LIST = 0x19, REQ_VIEW = 0x1a;

    private static final int RES_OWN = 72, RES_POST_FAIL = 74, RES_END_FAIL = 75, RES_CHANGE_FAIL = 76,
            RES_APPLY = 77, RES_APPLICATION = 78, RES_ANSWER_FAIL = 79, RES_BOARD = 81, RES_APPLICANTS = 82;

    /** Etc/BossParty.img recruit/applicantMax */
    public static final int APPLICANT_MAX = 15;
    public static final int DESCRIPTION_MAX = 60;
    private static final int ANSWER_ACCEPT = 5;
    /** A hosted ad its bot no longer looks after ends by itself after this long. */
    private static final long HOSTED_MAX_MS = 40 * 60_000L;

    public static final String[] DIFFICULTIES = {"Easy", "Normal", "Hard", "Chaos", "Extreme"};

    /** What the leader asks for: boss and difficulty, a description and entry minimums. */
    public record Post(int boss, int difficulty, String description, int minLevel, int minArcane, int minSacred,
                       int minUnion, long minPower) {

        static Post decode(InPacket in) {
            int boss = in.decodeInt();
            if (boss < 0) {
                return new Post(-1, 0, "", 0, 0, 0, 0, 0);
            }
            int difficulty = in.decodeByte() & 0xff;
            String description = clean(in.decodeString());
            in.decodeByte(); // v271 (0x140e412c0 / 0x140e41360): new byte after the description
            return new Post(boss, difficulty, description, in.decodeInt(), in.decodeInt(), in.decodeInt(),
                    in.decodeInt(), in.decodeLong());
        }

        void encode(OutPacket out) {
            out.encodeInt(boss);
            if (boss < 0) {
                return;
            }
            out.encodeByte(difficulty);
            out.encodeString(description);
            out.encodeByte(0); // v271: new byte (client +0x10)
            out.encodeInt(minLevel);
            out.encodeInt(minArcane);
            out.encodeInt(minSacred);
            out.encodeInt(minUnion);
            out.encodeLong(minPower);
        }
    }

    private static final class Ad {
        final int id;
        final int worldId;
        /** The party posting (null: a hosted ad, by a bot with no party yet). */
        final Party party;
        final Supplier<Char> host;
        final int posterId;
        final long created = System.currentTimeMillis();
        /** applicant char id -> when they applied, in order */
        final Map<Integer, Long> applicants = java.util.Collections.synchronizedMap(new LinkedHashMap<>());
        volatile Post post;

        Ad(int id, int worldId, Party party, Supplier<Char> host, int posterId, Post post) {
            this.id = id;
            this.worldId = worldId;
            this.party = party;
            this.host = host;
            this.posterId = posterId;
            this.post = post;
        }
    }

    private static final Map<Integer, Ad> ADS = new ConcurrentHashMap<>();
    /** Ids of hosted ads, kept clear of party ids. */
    private static final AtomicInteger HOSTED_IDS = new AtomicInteger(1_900_000_000);

    private PartyRecruitment() {
    }

    public static boolean isRequest(int type) {
        return switch (type) {
            case REQ_CREATE_AND_POST, REQ_POST, REQ_END, REQ_CHANGE, REQ_APPLY, REQ_CANCEL, REQ_ANSWER, REQ_LIST,
                 REQ_VIEW -> true;
            default -> false;
        };
    }

    private static String clean(String s) {
        String c = s == null ? "" : s.replaceAll("[\\r\\n]", " ").trim();
        return c.length() > DESCRIPTION_MAX ? c.substring(0, DESCRIPTION_MAX) : c;
    }

    private static int worldId(Char chr) {
        World w = chr.getWorld();
        return w == null || w.getWorldId() == null ? -1 : w.getWorldId().getVal();
    }

    // ------------------------------------------------------------------------------------------------ requests

    public static synchronized void handle(Char chr, int type, InPacket in) {
        try {
            switch (type) {
                case REQ_CREATE_AND_POST -> createAndPost(chr, in);
                case REQ_POST -> post(chr, chr.getParty(), Post.decode(in));
                case REQ_END -> end(chr);
                case REQ_CHANGE -> change(chr, Post.decode(in));
                case REQ_APPLY -> apply(chr, in.decodeInt());
                case REQ_CANCEL -> cancel(chr, in.decodeInt());
                case REQ_ANSWER -> {
                    int answer = in.decodeByte();
                    answer(chr, in.decodeInt(), answer == ANSWER_ACCEPT);
                }
                case REQ_LIST -> chr.write(board(chr));
                case REQ_VIEW -> view(chr, in.decodeInt());
                default -> {
                }
            }
        } finally {
            chr.dispose();
        }
    }

    private static void createAndPost(Char chr, InPacket in) {
        String name = in.decodeString();
        boolean appliable = in.decodeByte() != 0;
        boolean masterLoot = in.decodeInt() != 0;
        Post post = Post.decode(in);
        if (chr.getParty() != null) {
            post(chr, chr.getParty(), post);
            return;
        }
        if (post.boss() < 0 || chr.getWorld() == null) {
            chr.write(fail(RES_POST_FAIL, 12));
            return;
        }
        Party party = Party.createNewParty(appliable, masterLoot, name, chr.getWorld());
        party.addPartyMember(chr);
        chr.write(WvsContext.partyResult(PartyResult.createNewParty(party)));
        post(chr, party, post);
    }

    private static void post(Char chr, Party party, Post post) {
        if (party == null || !party.hasCharAsLeader(chr)) {
            chr.write(fail(RES_POST_FAIL, 13));
            return;
        }
        if (post.boss() < 0 || party.isFull() || ADS.containsKey(party.getId())) {
            chr.write(fail(RES_POST_FAIL, 12));
            return;
        }
        Ad ad = new Ad(party.getId(), worldId(chr), party, null, chr.getId(), post);
        ADS.put(ad.id, ad);
        party.broadcast(own(0, ad));
        mirrorToLfg(chr, post);
        log.info(String.format("Party Finder: %s posted %s (%s)", chr.getName(), describe(post), post.description()));
    }

    private static void end(Char chr) {
        Party party = chr.getParty();
        Ad ad = party == null ? null : ADS.get(party.getId());
        if (ad == null || !party.hasCharAsLeader(chr)) {
            chr.write(fail(RES_END_FAIL, 12));
            return;
        }
        endAd(ad, true);
    }

    private static void change(Char chr, Post post) {
        Party party = chr.getParty();
        Ad ad = party == null ? null : ADS.get(party.getId());
        if (ad == null || !party.hasCharAsLeader(chr) || post.boss() < 0) {
            chr.write(fail(RES_CHANGE_FAIL, ad == null || party == null || !party.hasCharAsLeader(chr) ? 13 : 12));
            return;
        }
        ad.post = post;
        OutPacket out = result(RES_OWN, 2);
        encodeAd(out, ad);
        out.encodeInt(0);
        party.broadcast(out);
        mirrorToLfg(chr, post);
    }

    private static void apply(Char chr, int id) {
        Ad ad = ADS.get(id);
        if (ad != null && !valid(ad)) {
            ad = null;
        }
        if (ad == null || ad.worldId != worldId(chr)) {
            OutPacket out = result(RES_APPLY, 8);
            out.encodeInt(id);
            chr.write(out);
            return;
        }
        if (chr.getParty() != null) {
            chr.write(result(RES_APPLY, 10));
            return;
        }
        if (ad.posterId == chr.getId() || chr.getLevel() < ad.post.minLevel()
                || net.swordie.ms.ext.ServerHooks.isTestCharacter(chr)) {
            chr.write(result(RES_APPLY, 14));
            return;
        }
        if (!ad.applicants.containsKey(chr.getId()) && ad.applicants.size() >= APPLICANT_MAX) {
            chr.write(result(RES_APPLY, 18));
            return;
        }
        for (Ad other : ADS.values()) {
            if (other != ad && other.applicants.remove(chr.getId()) != null) {
                applicantGone(other, chr.getId());
            }
        }
        long now = System.currentTimeMillis();
        ad.applicants.put(chr.getId(), now);
        Char leader = leaderOf(ad);
        OutPacket out = result(RES_APPLY, 0);
        out.encodeString(leader == null ? "" : leader.getName());
        out.encodeInt(ad.id);
        encodeEntry(out, ad);
        chr.write(out);
        if (ad.party != null) {
            OutPacket add = result(RES_APPLICANTS, 8);
            ad.party.encodeMember(add, new PartyMember(chr));
            add.encodeFT(FileTime.fromEpochMillis(now));
            ad.party.broadcast(add);
        }
    }

    private static void cancel(Char chr, int id) {
        Ad ad = ADS.get(id);
        if (ad == null || ad.applicants.remove(chr.getId()) == null) {
            return;
        }
        chr.write(applicationEnded(id, chr.getId(), false));
        applicantGone(ad, chr.getId());
    }

    private static void answer(Char chr, int applicantId, boolean accept) {
        Party party = chr.getParty();
        Ad ad = party == null ? null : ADS.get(party.getId());
        if (ad == null || !party.hasCharAsLeader(chr)) {
            chr.write(fail(RES_ANSWER_FAIL, 13));
            return;
        }
        if (ad.applicants.remove(applicantId) == null) {
            chr.write(fail(RES_ANSWER_FAIL, 12));
            return;
        }
        applicantGone(ad, applicantId);
        Char applicant = chr.getWorld() == null ? null : chr.getWorld().getCharByID(applicantId);
        if (!accept) {
            if (applicant != null) {
                applicant.write(refused(chr.getName(), ad.id));
            }
            return;
        }
        if (applicant == null || applicant.getParty() != null) {
            chr.write(fail(RES_ANSWER_FAIL, 12));
            return;
        }
        if (party.isFull()) {
            chr.write(fail(RES_ANSWER_FAIL, 17));
            return;
        }
        join(party, applicant);
        if (party.isFull()) {
            endAd(ad, true);
        }
    }

    /** Into the party as an accepted applicant: everyone is told, the joiner gets the whole party. */
    private static void join(Party party, Char applicant) {
        party.addPartyMember(applicant);
        if (applicant.getParty() != party) {
            return;
        }
        for (PartyMember pm : party.getOnlineMembers()) {
            Char member = pm.getChr();
            if (member != null && member != applicant) {
                applicant.write(UserRemote.receiveHP(member));
            }
        }
        party.broadcast(UserRemote.receiveHP(applicant));
        applicant.write(WvsContext.partyResult(PartyResult.loadParty(party, applicant)));
    }

    private static void view(Char chr, int id) {
        Ad ad = ADS.get(id);
        if (ad != null && !valid(ad)) {
            ad = null;
        }
        OutPacket out = result(RES_BOARD, 11);
        out.encodeInt(id);
        out.encodeByte(ad != null);
        if (ad != null) {
            encodeEntry(out, ad);
        } else {
            encodeList(out, chr);
        }
        chr.write(out);
    }

    // ------------------------------------------------------------------------------------------------ lifecycle

    /** Whether the ad still stands; one whose party disbanded, filled or changed leader is ended here. */
    private static boolean valid(Ad ad) {
        if (ad.party == null) {
            if (System.currentTimeMillis() - ad.created > HOSTED_MAX_MS) {
                endAd(ad, false);
                return false;
            }
            return ad.host.get() != null;
        }
        if (ad.party.getWorld() == null) {
            endAd(ad, false);
            return false;
        }
        Char leader = leaderOf(ad);
        if (leader == null || ad.party.getPartyLeaderID() != ad.posterId || ad.party.isFull()) {
            endAd(ad, ad.party.getWorld() != null);
            return false;
        }
        return true;
    }

    /** Ends the ad: applicants are told and dropped, the party (if told) sees its ad end. */
    private static void endAd(Ad ad, boolean tellParty) {
        if (!ADS.remove(ad.id, ad)) {
            return;
        }
        Char leader = leaderOf(ad);
        World world = leader == null ? null : leader.getWorld();
        String name = leader == null ? "" : leader.getName();
        for (int applicantId : new ArrayList<>(ad.applicants.keySet())) {
            Char a = world == null ? null : world.getCharByID(applicantId);
            if (a != null) {
                OutPacket out = result(RES_BOARD, 1);
                out.encodeInt(ad.id);
                out.encodeByte(1);
                out.encodeString(name);
                a.write(out);
            }
        }
        ad.applicants.clear();
        if (ad.party != null && tellParty && ad.party.getWorld() != null) {
            ad.party.broadcast(result(RES_OWN, 1));
        }
        if (leader != null && ad.party != null && !SocialBotFilter.isBot(leader)) {
            LfgModule.remove(leader);
        }
    }

    /** The party disbands: its ad (if any) ends with it. */
    public static synchronized void onDisband(Party party) {
        Ad ad = ADS.get(party.getId());
        if (ad != null && ad.party == party) {
            endAd(ad, false);
        }
    }

    private static void applicantGone(Ad ad, int charId) {
        if (ad.party != null) {
            OutPacket out = result(RES_APPLICANTS, 7);
            out.encodeInt(charId);
            ad.party.broadcast(out);
        }
    }

    private static Char leaderOf(Ad ad) {
        if (ad.party == null) {
            return ad.host.get();
        }
        PartyMember pm = ad.party.getPartyMemberByID(ad.posterId);
        return pm == null ? null : pm.getChr();
    }

    /** A player's ad also goes up on the @lfg board, where bots that can fight the boss look for a party. */
    private static void mirrorToLfg(Char chr, Post post) {
        if (SocialBotFilter.isBot(chr)) {
            return;
        }
        String note = describe(post) + (post.minLevel() > 0 ? " " + post.minLevel() + "+" : "");
        LfgModule.post(chr, 0, note);
    }

    // ------------------------------------------------------------------------------------------------ hosted ads

    /**
     * Puts up an ad held by a bot with no party yet (the board shows it alone in its party). @return the ad id, 0 when
     * it can't post
     */
    public static synchronized int postHosted(Char poster, Supplier<Char> host, Post post) {
        if (poster == null || poster.getParty() != null || post.boss() < 0) {
            return 0;
        }
        int id = HOSTED_IDS.incrementAndGet();
        ADS.put(id, new Ad(id, worldId(poster), null, host, poster.getId(), post));
        log.info(String.format("Party Finder: %s posted %s (%s)", poster.getName(), describe(post), post.description()));
        return id;
    }

    public static boolean isOpen(int id) {
        return ADS.containsKey(id);
    }

    /** The hosted ad's applicants still online without a party, in the order they applied. */
    public static synchronized List<Char> applicants(int id) {
        List<Char> res = new ArrayList<>();
        Ad ad = ADS.get(id);
        Char host = ad == null ? null : ad.host == null ? null : ad.host.get();
        World world = host == null ? null : host.getWorld();
        if (world == null) {
            return res;
        }
        for (int charId : new ArrayList<>(ad.applicants.keySet())) {
            Char c = world.getCharByID(charId);
            if (c != null && c.getParty() == null) {
                res.add(c);
            }
        }
        return res;
    }

    /** Takes an applicant off the hosted ad: accepted (the bot forms the party) or refused (they're told). */
    public static synchronized boolean answerHosted(int id, Char applicant, boolean accept) {
        Ad ad = ADS.get(id);
        if (ad == null || ad.applicants.remove(applicant.getId()) == null) {
            return false;
        }
        if (!accept) {
            Char host = ad.host.get();
            applicant.write(refused(host == null ? "" : host.getName(), id));
        }
        return true;
    }

    /** Ends a hosted ad (party formed, or the bot gave up): those still applying are told it ended. */
    public static synchronized void endHosted(int id) {
        Ad ad = ADS.get(id);
        if (ad != null) {
            endAd(ad, false);
        }
    }

    /** Joins an accepted applicant to the bot's new party, as the leader's accept does. */
    public static void joinAccepted(Party party, Char applicant) {
        join(party, applicant);
    }

    // ------------------------------------------------------------------------------------------------ packets

    private static OutPacket result(int type, int code) {
        OutPacket out = new OutPacket(OutHeader.PARTY_RESULT);
        out.encodeByte(PartyResult.v271Type(type));
        out.encodeInt(code);
        return out;
    }

    private static OutPacket fail(int type, int code) {
        return result(type, code);
    }

    private static OutPacket refused(String leaderName, int id) {
        OutPacket out = result(RES_APPLY, 4);
        out.encodeString(leaderName);
        out.encodeInt(id);
        return out;
    }

    private static OutPacket applicationEnded(int id, int charId, boolean auto) {
        OutPacket out = result(RES_APPLICATION, 6);
        out.encodeInt(id);
        out.encodeInt(charId);
        out.encodeByte(auto);
        return out;
    }

    private static OutPacket own(int code, Ad ad) {
        OutPacket out = result(RES_OWN, code);
        if (code == 0) {
            encodeAd(out, ad);
        }
        return out;
    }

    private static OutPacket board(Char viewer) {
        OutPacket out = result(RES_BOARD, 10);
        encodeList(out, viewer);
        return out;
    }

    private static void encodeList(OutPacket out, Char viewer) {
        int world = worldId(viewer);
        List<Ad> open = new ArrayList<>();
        for (Ad ad : new ArrayList<>(ADS.values())) {
            if (ad.worldId == world && valid(ad)) {
                open.add(ad);
            }
        }
        open.sort((a, b) -> Long.compare(b.created, a.created));
        out.encodeInt(open.size());
        for (Ad ad : open) {
            out.encodeInt(ad.id);
            encodeEntry(out, ad);
        }
    }

    /** byte, six members, leader, applicants, the ad */
    private static void encodeEntry(OutPacket out, Ad ad) {
        out.encodeByte(0);
        Party party = ad.party;
        if (party == null) {
            party = new Party();
            Char host = ad.host.get();
            party.setId(ad.id);
            party.setWorld(host == null ? null : host.getWorld());
            party.setPartyLeaderID(ad.posterId);
            if (host != null) {
                party.getPartyMembers()[0] = new PartyMember(host);
            }
        }
        party.encodeMembers(out);
        out.encodeInt(ad.posterId);
        encodeApplicants(out, ad, party);
        encodeAd(out, ad);
    }

    private static void encodeApplicants(OutPacket out, Ad ad, Party party) {
        World world = party.getWorld();
        List<Char> chars = new ArrayList<>();
        List<Long> times = new ArrayList<>();
        if (ad != null && world != null) {
            List<Map.Entry<Integer, Long>> entries;
            synchronized (ad.applicants) {
                entries = new ArrayList<>(ad.applicants.entrySet());
            }
            for (Map.Entry<Integer, Long> e : entries) {
                Char c = world.getCharByID(e.getKey());
                if (c != null) {
                    chars.add(c);
                    times.add(e.getValue());
                }
            }
        }
        out.encodeInt(chars.size());
        for (int i = 0; i < chars.size(); i++) {
            party.encodeMember(out, new PartyMember(chars.get(i)));
            out.encodeFT(FileTime.fromEpochMillis(times.get(i)));
            out.encodeInt(0); // v271 applicant (0x140e42020): int, string after the time
            out.encodeString("");
        }
    }

    /** The post, when it went up and who posted it (as chat sender info). */
    private static void encodeAd(OutPacket out, Ad ad) {
        Post post = ad.post;
        post.encode(out);
        if (post.boss() < 0) {
            return;
        }
        out.encodeFT(FileTime.fromEpochMillis(ad.created));
        Char leader = leaderOf(ad);
        if (leader != null && leader.getAccount() != null) {
            leader.encodeChatInfo(out, post.description());
        } else {
            out.encodeString(leader == null ? "" : leader.getName());
            out.encodeString(post.description());
            out.encodeInt(0);
            out.encodeInt(ad.posterId);
            out.encodeByte(0);
            out.encodeInt(ad.posterId);
            out.encodeInt(0);
            out.encodeString("");
            out.encodeInt(0);
            out.encodeString("");
            out.encodeInt(0);
        }
    }

    /** Party data's applicant list (count, entries) for this party's ad. */
    public static void encodeApplicantsOf(OutPacket out, Party party) {
        Ad ad = ADS.get(party.getId());
        encodeApplicants(out, ad != null && ad.party == party ? ad : null, party);
    }

    /** Party data's ad: the party's own, else boss -1. */
    public static void encodeAdOf(OutPacket out, Party party) {
        Ad ad = ADS.get(party.getId());
        if (ad == null || ad.party != party) {
            out.encodeInt(-1);
            return;
        }
        encodeAd(out, ad);
    }

    // ------------------------------------------------------------------------------------------------ names

    /** Etc/BossParty.img boss key -> name */
    public static String bossName(int boss) {
        return switch (boss) {
            case 0 -> "Balrog";
            case 1 -> "Zakum";
            case 2 -> "Horntail";
            case 3 -> "Hilla";
            case 4 -> "Pierre";
            case 5 -> "Von Bon";
            case 6 -> "Crimson Queen";
            case 7 -> "Vellum";
            case 8 -> "Von Leon";
            case 9 -> "Arkarium";
            case 10 -> "Magnus";
            case 11 -> "Pink Bean";
            case 12 -> "Cygnus";
            case 13 -> "Lotus";
            case 15 -> "Damien";
            case 16 -> "Gollux";
            case 17 -> "Mori Ranmaru";
            case 18 -> "Princess No";
            case 19 -> "Lucid";
            case 21 -> "OMNI-CLN";
            case 22 -> "Papulatus";
            case 23 -> "Will";
            case 24 -> "Verus Hilla";
            case 25 -> "Black Mage";
            case 26 -> "Gloom";
            case 27 -> "Guard Captain Darknell";
            case 28 -> "Chosen Seren";
            case 29 -> "Guardian Angel Slime";
            case 30 -> "Kalos the Guardian";
            case 31 -> "Kaling";
            case 32 -> "Monster Park Extreme";
            case 33 -> "Limbo";
            case 34 -> "Baldrix";
            case 35 -> "First Adversary";
            case 36 -> "Kai";
            case 37 -> "Malefic Star";
            case 38 -> "Jupiter";
            case 107 -> "Akechi Mitsuhide";
            default -> "Boss " + boss;
        };
    }

    public static String describe(Post post) {
        String d = post.difficulty() >= 0 && post.difficulty() < DIFFICULTIES.length
                ? DIFFICULTIES[post.difficulty()] + " " : "";
        return d + bossName(post.boss());
    }
}
