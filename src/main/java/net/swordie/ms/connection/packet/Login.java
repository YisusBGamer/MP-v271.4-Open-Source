package net.swordie.ms.connection.packet;

import net.swordie.ms.Server;
import net.swordie.ms.ServerConstants;
import net.swordie.ms.ServerStatus;
import net.swordie.ms.client.Account;
import net.swordie.ms.client.QuickStart;
import net.swordie.ms.client.User;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.LoginType;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;
import net.swordie.ms.util.container.Tuple;
import net.swordie.ms.world.Channel;
import net.swordie.ms.world.World;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Created by Sjonnie on 2/28/2017.
 */
public class Login {

    private static final Comparator<Char> LOCATION_COMPARATOR = (Char c1, Char c2) -> {
        int res;
        int c1id = c1.getId();
        int c1loc = c1.getLocation();
        int c2id = c2.getId();
        int c2loc = c2.getLocation();
        if (c1loc == c2loc) {
            res = Integer.compare(c1id, c2id);
        } else {
            res = Integer.compare(c1loc, c2loc);
        }
        return res;
    };

    /**
     * The unencrypted hello, prefixed with its length. The login port repeats the version block and adds the
     * combined version (major * 100 + minor) the client checks before it logs in.
     */
    public static OutPacket sendConnect(byte[] siv, byte[] riv, boolean isChannel) {
        OutPacket body = new OutPacket();
        String minor = isChannel ? ServerConstants.MINOR_VERSION : ServerConstants.MINOR_VERSION + ":0";
        body.encodeShort(ServerConstants.VERSION);
        body.encodeString(minor);
        body.encodeArr(siv);
        body.encodeArr(riv);
        body.encodeByte(ServerConstants.LOCALE);
        if (isChannel) {
            // connection type: 4 makes the client run its protected game-server hook (connect and close)
            body.encodeByte(net.swordie.ms.ServerSettings.getInt("test.channelHelloType", 4));
        } else {
            body.encodeByte(0);
            body.encodeByte(1);
            body.encodeShort(ServerConstants.VERSION);
            body.encodeString(minor);
            body.encodeArr(siv);
            body.encodeArr(riv);
            body.encodeByte(ServerConstants.LOCALE);
            int fullVersion = ServerConstants.VERSION * 100 + Integer.parseInt(ServerConstants.MINOR_VERSION);
            body.encodeInt(fullVersion);
            body.encodeInt(fullVersion);
            body.encodeInt(0);
            body.encodeByte(1);
            body.encodeByte(0);
            body.encodeByte(5);
        }
        byte[] data = body.getData();
        OutPacket outPacket = new OutPacket();
        outPacket.encodeShort(data.length);
        outPacket.encodeArr(data);
        return outPacket;
    }

    public static OutPacket sendAliveReq() {
        return new OutPacket(OutHeader.ALIVE_REQ.getValue()); // GMS v271: empty, answered by an empty ALIVE_ACK
    }

    // Sent right after the hello, in this order (login port: all but FILE_CHECK_LIST; channel port: all but
    // SECURITY_PACKET).

    public static OutPacket sendSecurityPacket() {
        OutPacket outPacket = new OutPacket(OutHeader.SECURITY_PACKET);
        outPacket.encodeShort(0);
        outPacket.encodeShort(0);
        outPacket.encodeByte(0);
        return outPacket;
    }

    public static OutPacket sendServerValues() {
        return ServerValues.serverValues();
    }

    public static OutPacket sendServerEnvironment() {
        return ServerValues.serverEnvironment();
    }

    public static OutPacket sendWzCheckList() {
        return ClientFileLists.wzCheckList();
    }

    public static OutPacket sendFileCheckList() {
        return ClientFileLists.fileCheckList();
    }

    public static OutPacket sendSecurityCodePacket() {
        OutPacket outPacket = new OutPacket(OutHeader.SECURITY_PACKET_CODE);
        outPacket.encodeByte(1);
        outPacket.encodeByte(0);
        return outPacket;
    }

    public static OutPacket sendSecurityCodePacketAttach() {
        return new OutPacket(OutHeader.SECURITY_PACKET_CODE_ATTACH);
    }

    /**
     * Answer to SPECIAL_LOGIN_AUTH_REQUEST (CLogin::OnCheckPasswordResult).
     */
    public static OutPacket checkPasswordResult(LoginType result, User user) {
        OutPacket outPacket = new OutPacket(OutHeader.CHECK_PASSWORD_RESULT);

        outPacket.encodeByte(result.getValue());
        outPacket.encodeString("");
        outPacket.encodeByte(0);
        outPacket.encodeInt(0);
        if (result == LoginType.Success) {
            outPacket.encodeString(user.getName());
            outPacket.encodeLong(user.getId());
            outPacket.encodeInt(user.getId());
            outPacket.encodeByte(0);
            outPacket.encodeInt(user.getAccountType().getVal());
            outPacket.encodeInt(user.getAge());
            outPacket.encodeByte(!user.hasCensoredNxLoginID());
            // v271 reads exactly one string here
            outPacket.encodeString(user.hasCensoredNxLoginID()
                    ? user.getCensoredNxLoginID() : user.getName());
            outPacket.encodeInt(255);
            outPacket.encodeByte(2);
            outPacket.encodeByte(0); // chat block
            outPacket.encodeLong(0);
            outPacket.encodeLong(130908999600000000L);
            outPacket.encodeInt(user.getCharacterSlots());
            outPacket.encodeString("");
            outPacket.encodeInt(0); // v271: new int here; the v265 int after the grade byte is gone
            JobConstants.encode(outPacket);
            outPacket.encodeByte(user.getGradeCode());
            outPacket.encodeByte(1);
            outPacket.encodeFT(user.getCreationDate() == null ? FileTime.currentTime() : user.getCreationDate());
        }

        return outPacket;
    }

    /**
     * The last played character on the login screen; null = none.
     */
    public static OutPacket quickLogin(Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.QUICK_LOGIN);

        outPacket.encodeByte(chr != null);
        if (chr != null) {
            outPacket.encodeString(chr.getName());
            outPacket.encodeInt(chr.getLevel());
            outPacket.encodeShort(chr.getJob());
            outPacket.encodeLong(chr.getCombatPower());
            chr.getAvatarData().encode(outPacket);
        }

        return outPacket;
    }

    public static OutPacket sendLoginTime() {
        OutPacket outPacket = new OutPacket(OutHeader.USER_GET_TIME_RESULT);
        outPacket.encodeFT(FileTime.currentTime());
        return outPacket;
    }

    public static OutPacket sendJobValues() {
        OutPacket outPacket = new OutPacket(OutHeader.LOGIN_JOB_VALUES);
        JobConstants.encode(outPacket);
        return outPacket;
    }

    public static OutPacket setClientKey(long clientKey) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_CLIENT_KEY);
        outPacket.encodeLong(clientKey);
        return outPacket;
    }

    public static OutPacket setPhysicalWorldAttach(String local) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_PHYSICAL_WORLD_ATTACH);
        outPacket.encodeString(local);
        return outPacket;
    }

    public static OutPacket clientAlive() {
        return new OutPacket(OutHeader.CLIENT_ALIVE);
    }

    /**
     * Login screen background (Map.wz\Obj\login.img\WorldSelect tag).
     */
    public static OutPacket setLoginBackground(String tag) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_MAP_TAGGED_OBJECT_VISIBLE_LOGIN);
        outPacket.encodeString(tag);
        return outPacket;
    }

    public static OutPacket sendAuthServer(boolean useAuthServer) {
        OutPacket outPacket = new OutPacket(OutHeader.AUTH_SERVER.getValue());
        outPacket.encodeByte(useAuthServer);
        return outPacket;
    }

    /** Out 62 HOTFIX with size -1: no hotfix; the client deletes any Data.wz it has. */
    public static OutPacket sendHotfix(byte[] data) {
        OutPacket outPacket = new OutPacket(OutHeader.HOTFIX.getValue());
        outPacket.encodeByte(1);
        return outPacket;
    }

    /**
     * Out 62 HOTFIX packets for a Data.wz the client should write and apply. The client reads a packed size and the
     * file hash (first 4 bytes of its SHA-1, as In APPLY_HOTFIX sends it), then 0x10000 bytes per packet; packets
     * after the first carry only bytes. data == null: size 0, the client applies the Data.wz it already has.
     */
    public static List<OutPacket> hotfixPackets(byte[] data, int hash) {
        List<OutPacket> packets = new ArrayList<>();
        OutPacket first = new OutPacket(OutHeader.HOTFIX.getValue());
        if (data == null) {
            first.encodeArr(Util.toPackedInt(0));
            packets.add(first);
            return packets;
        }
        int chunk = 0x10000;
        first.encodeArr(Util.toPackedInt(data.length));
        first.encodeInt(hash);
        first.encodeArr(java.util.Arrays.copyOfRange(data, 0, Math.min(chunk, data.length)));
        packets.add(first);
        for (int off = chunk; off < data.length; off += chunk) {
            OutPacket next = new OutPacket(OutHeader.HOTFIX.getValue());
            next.encodeArr(java.util.Arrays.copyOfRange(data, off, Math.min(off + chunk, data.length)));
            packets.add(next);
        }
        return packets;
    }

    public static OutPacket sendWorldInformation(World world, int charCount, Set<Tuple<Position, String>> stringInfos) {
        // CLogin::OnWorldInformation
        OutPacket outPacket = new OutPacket(OutHeader.WORLD_INFORMATION);

        outPacket.encodeByte(world.getWorldId().getVal());
        // if ^ is >= 0, always true
        outPacket.encodeString(world.getName());
        outPacket.encodeInt(0);
        outPacket.encodeInt(world.isReboot() ? 1 : 0);
        outPacket.encodeByte(0); // bNormal (0 or >1 = recommended)
        outPacket.encodeByte(0);
        outPacket.encodeByte(world.getWorldState());
        outPacket.encodeByte(0); // v271: no optional 8-byte metadata block (nonzero: 8 bytes follow)
        outPacket.encodeByte(0); // enum for small emblem to the right (1~3)
        // the v265 client crashes on selecting a world that has event text: the buffed channel notice can't go here
        outPacket.encodeString("");
        outPacket.encodeInt(charCount);
        outPacket.encodeByte(world.isCharCreateBlock());
        outPacket.encodeInt(-1);
        outPacket.encodeByte(world.getChannels().size());
        for (Channel c : world.getChannels()) {
            outPacket.encodeString(c.getName());
            outPacket.encodeInt(c.getGaugePx());
            outPacket.encodeByte(c.getWorldId().getVal());
            outPacket.encodeByte(c.getChannelId());
            outPacket.encodeByte(c.isAdultChannel());
        }
        if (stringInfos == null) {
            outPacket.encodeShort(0);
        } else {
            outPacket.encodeShort(stringInfos.size());
            for (Tuple<Position, String> stringInfo : stringInfos) {
                outPacket.encodePosition(stringInfo.getLeft());
                outPacket.encodeString(stringInfo.getRight());
            }
        }
        outPacket.encodeInt(0); // some offset
        outPacket.encodeByte(1);
        outPacket.encodeInt(0);

        return outPacket;
    }

    /**
     * End of the world list. The trailing lists name the world to preselect and its last played character.
     */
    public static OutPacket sendWorldInformationEnd(int worldId, int lastCharId, List<World> worlds) {
        QuickStart.Entry last = lastCharId > 0 ? new QuickStart.Entry(worldId, 0, lastCharId) : QuickStart.Entry.NONE;
        return sendWorldInformationEnd(worldId, new QuickStart.Records(last, QuickStart.Entry.NONE), worlds);
    }

    public static OutPacket sendWorldInformationEnd(int worldId, QuickStart.Records qs, List<World> worlds) {
        OutPacket outPacket = new OutPacket(OutHeader.WORLD_INFORMATION);

        outPacket.encodeByte(-1);

        outPacket.encodeByte(0); // advertisements
        outPacket.encodeByte(0);
        outPacket.encodeByte(1);
        outPacket.encodeInt(-1);
        outPacket.encodeInt(-1);
        outPacket.encodeInt(-1);
        encodeQuickStart(outPacket, qs, worldId, 0, false);
        outPacket.encodeByte(1);
        outPacket.encodeInt(0);
        outPacket.encodeByte(0);
        encodeQuickStart(outPacket, qs, worldId, 0, true);

        // world -> list group (1 Normal = Interactive, 2 Reboot = Heroic, 4 Seasonal title). All worlds the server
        // has must appear here: the client renders this list, not the individual world packets above.
        int worldCount = worlds == null ? 0 : worlds.size();
        outPacket.encodeInt(worldCount);
        for (World world : worlds) {
            outPacket.encodeInt(world.getWorldId().getVal());
            outPacket.encodeInt(worldGroup(world));
        }

        outPacket.encodeInt(worldCount);
        for (World world : worlds) {
            outPacket.encodeInt(world.getWorldId().getVal());
        }

        outPacket.encodeInt(worldCount);
        for (World world : worlds) {
            outPacket.encodeInt(world.getWorldId().getVal());
            outPacket.encodeInt(worldGroup(world));
        }

        outPacket.encodeInt(1);
        outPacket.encodeInt(1);
        outPacket.encodeInt(1);

        return outPacket;
    }

    private static int worldGroup(World world) {
        return world.isReboot() ? 2 : 1;
    }

    /**
     * One Quick Start record: (world, channel index, character id) and, for the target record, the favourite flag.
     * The target is the favourite if there is one, else the last played character.
     */
    private static void encodeQuickStart(OutPacket outPacket, QuickStart.Records qs, int defWorld, int defChannel,
                                         boolean lastPlayed) {
        QuickStart.Entry e = qs == null ? QuickStart.Entry.NONE : lastPlayed ? qs.last() : qs.target();
        boolean set = e.isSet();
        outPacket.encodeInt(set ? e.world() : defWorld);
        outPacket.encodeInt(set ? e.channel() : defChannel);
        outPacket.encodeInt(set ? e.charId() : 0);
        if (!lastPlayed) {
            outPacket.encodeByte(qs != null && set && qs.favourite().isSet());
        }
    }

    public static OutPacket viewChannelResult(LoginType type, int worldId) {
        OutPacket outPacket = new OutPacket(OutHeader.SELECT_WORLD_BUTTON);

        outPacket.encodeByte(type.getValue());
        // the client needs this marker (U+2700 in UTF-8); an empty string crashes it on selecting a world
        outPacket.encodeShort(3);
        outPacket.encodeArr(new byte[]{(byte) 0xE2, (byte) 0x9C, (byte) 0x80});
        outPacket.encodeInt(worldId);
        outPacket.encodeInt(-1);

        return outPacket;
    }

    public static OutPacket sendAccountInfo(User user) {
        OutPacket outPacket = new OutPacket(OutHeader.ACCOUNT_INFO_RESULT);

        outPacket.encodeByte(0); // succeed
        outPacket.encodeString("");

        outPacket.encodeLong(user.getId()); // v271: new long first, as in the login result
        outPacket.encodeInt(user.getId());
        outPacket.encodeByte(user.getGradeCode());
        outPacket.encodeInt(user.getAccountType().getVal());

        outPacket.encodeInt(user.getVipGrade());
        outPacket.encodeByte(user.getPurchaseExp());
        outPacket.encodeString(user.getName());
        outPacket.encodeByte(user.getnBlockReason());
        outPacket.encodeByte(0); // ?
        outPacket.encodeLong(user.getChatUnblockDate());
        outPacket.encodeString(user.getCensoredNxLoginID());

        outPacket.encodeLong(0);
        outPacket.encodeInt(0);
        outPacket.encodeLong(0);
        outPacket.encodeString(""); //v25 = CInPacket::DecodeStr(iPacket_1, &nAge);
        outPacket.encodeInt(0); // new 228
        outPacket.encodeInt(0); // v271: read before the string (1 = a different panel mode)

        outPacket.encodeString("");

        JobConstants.encode(outPacket);

        outPacket.encodeByte(0); // bIsBeginningUser
        // v271: the int that followed is gone
        outPacket.encodeByte(0);
        outPacket.encodeByte(1);

        return outPacket;
    }

    public static OutPacket sendServerStatus(byte worldId) {
        OutPacket outPacket = new OutPacket(OutHeader.SERVER_STATUS.getValue());
        World world = Server.getInstance().getWorldById(worldId);
        if (world != null && !world.isFull()) {
            outPacket.encodeByte(world.getStatus().getValue());
        } else {
            outPacket.encodeByte(ServerStatus.BUSY.getValue());
        }
        outPacket.encodeByte(0); // ?

        return outPacket;
    }

    public static OutPacket selectWorldResult(User user, Account account, byte code, World world, int channelId,
                                              boolean burningEventBlock, QuickStart.Records quickStart) {
        OutPacket outPacket = new OutPacket(OutHeader.SELECT_WORLD_RESULT);

        int worldId = world.getWorldId().getVal();
        outPacket.encodeByte(code);
        outPacket.encodeString("");
        outPacket.encodeByte(0);
        // the client's clock until it enters the game; picks whether it offers Burning
        outPacket.encodeFT(net.swordie.ms.client.character.BurningModule.getLoginClock(account));
        outPacket.encodeInt(worldId);
        outPacket.encodeInt(channelId - 1);
        // GMS sends 2, 2, 2 here (meaning unknown; we sent 1s)
        outPacket.encodeInt(2);
        outPacket.encodeInt(2);
        outPacket.encodeInt(2);
        outPacket.encodeInt(account.getTrunk().getSlotCount());
        outPacket.encodeByte(burningEventBlock); // true blocks the Event/EventEx/EventHyper/EventHyper2 Burning offers
        outPacket.encodeByte(false);

        outPacket.encodeInt(0); // characters waiting for deletion: id, start time

        List<Char> chars = new ArrayList<>(account.getCharacters());
        chars.sort(LOCATION_COMPARATOR);
        outPacket.encodeInt(chars.size());
        for (Char chr : chars) {
            // order of chars
            outPacket.encodeInt(chr.getId());
        }

        outPacket.encodeByte(chars.size());
        for (Char chr : chars) {
            chr.getCombatPower(); // fills the entry's combat power
            chr.getAvatarData().encode(outPacket);
        }

        int slots = ServerConstants.MAX_CHARACTERS;
        outPacket.encodeByte(0); // bQuerySSNOnCreateNewCharacter
        outPacket.encodeInt(slots);
        outPacket.encodeInt(0); // buying char slots
        outPacket.encodeByte(1);
        outPacket.encodeByte(1);
        outPacket.encodeInt(slots);
        outPacket.encodeInt(0);
        outPacket.encodeInt(-1); // nEventNewCharJob
        outPacket.encodeByte(1);
        outPacket.encodeByte(0); // character removed from the deletion queue
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);

        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(5);
        outPacket.encodeByte(1);
        outPacket.encodeByte(0);
        outPacket.encodeInt(0); // v271 reads one int here (v265: this and an int 1)

        encodeQuickStart(outPacket, quickStart, worldId, channelId - 1, false);
        encodeQuickStart(outPacket, quickStart, worldId, channelId - 1, true);

        outPacket.encodeString(""); // account quest 607 value
        // character select theme; above 1 the client also needs it unlocked in the string above, else shows 0
        outPacket.encodeInt(QuickStart.loadTheme(user));
        outPacket.encodeByte(0); // v271
        outPacket.encodeString(""); // v271

        outPacket.encodeInt(0);

        return outPacket;
    }

    public static OutPacket checkDuplicatedIDResult(String name, byte code) {
        OutPacket outPacket = new OutPacket(OutHeader.CHECK_DUPLICATED_ID_RESULT);

        outPacket.encodeString(name);
        outPacket.encodeByte(code);

        return outPacket;
    }

    public static OutPacket createNewCharacterResult(LoginType type, Char chr, boolean returnToCharSelect) {
        OutPacket outPacket = new OutPacket(OutHeader.CREATE_NEW_CHARACTER_RESULT);

        outPacket.encodeByte(type.getValue());
        if (type == LoginType.Success) {
            outPacket.encodeInt(chr.getAccount().getWorldId());
            chr.getAvatarData().encode(outPacket);
            // 0: the client enters the game with the new character (SELECT_CHARACTER_AFTER_CREATE); else char select
            outPacket.encodeByte(returnToCharSelect);
            // v271 (0x1439ca2f0): one more int, kept with the new character's id; GMS sends 1
            outPacket.encodeInt(1);
        } else {
            outPacket.encodeByte(returnToCharSelect);
        }

        return outPacket;
    }

    public static OutPacket sendAuthResponse(int response) {
        OutPacket outPacket = new OutPacket(OutHeader.PRIVATE_SERVER_PACKET);

        outPacket.encodeInt(response);

        return outPacket;
    }

    public static OutPacket selectCharacterResult(LoginType type) {
        return selectCharacterResult(type, (byte) 0, 0, 0, null);
    }

    public static OutPacket selectCharacterResult(LoginType loginType, byte errorCode, int port, int characterId, World world) {
        OutPacket outPacket = new OutPacket(OutHeader.SELECT_CHARACTER_RESULT);

        outPacket.encodeByte(loginType.getValue());
        outPacket.encodeString("");
        outPacket.encodeByte(errorCode);

        if (loginType == LoginType.Success) {
            outPacket.encodeArr(GameConstants.SERVER_IP);
            outPacket.encodeShort(ClientSocket.clientPort(port));
            var rnd = java.util.concurrent.ThreadLocalRandom.current();
            outPacket.encodeInt(characterId);
            outPacket.encodeInt(1);
            outPacket.encodeInt(1);
            outPacket.encodeInt(1);
            outPacket.encodeInt(rnd.nextInt()); // GMS: a per-login value here, not the character id
            outPacket.encodeByte(0); // bAuthenCode
            outPacket.encodeInt(0); // ulArgument
            outPacket.encodeByte(0);
            outPacket.encodeByte(20);
            outPacket.encodeInt(1000);
            outPacket.encodeByte(0);
            outPacket.encodeByte(0);
            outPacket.encodeLong(0);
            // GMS: a key, the length (4) and 4 bytes the client XOR-decodes with the key (0x1439cb280)
            outPacket.encodeInt(rnd.nextInt());
            outPacket.encodeInt(4);
            outPacket.encodeInt(rnd.nextInt());
        }

        return outPacket;
    }

    public static OutPacket sendDeleteCharacterResult(int charId, LoginType loginType) {
        OutPacket outPacket = new OutPacket(OutHeader.DELETE_CHARACTER_RESULT);

        outPacket.encodeInt(charId);
        outPacket.encodeByte(loginType.getValue());

        return outPacket;
    }

    public static OutPacket sendAuthInfoRequest() {
        OutPacket outPacket = new OutPacket(OutHeader.AUTH_INFO_REQUEST);

        outPacket.encodeByte(0);
        outPacket.encodeByte(0);

        return outPacket;
    }

    public static OutPacket someChannelInfoResult() {
        OutPacket outPacket = new OutPacket(OutHeader.SOME_CHANNEL_REQUEST_RESULT);

        int size = 0;
        outPacket.encodeInt(size);
        for (int i = 0; i < size; i++) {
            outPacket.encodeInt(1);
            outPacket.encodeInt(21);
            outPacket.encodeInt(1);
            outPacket.encodeString("All");
        }

        return outPacket;
    }

    public static OutPacket setPhysicalWorldId(byte worldId) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_PHYSICAL_WORLD_ID);

        outPacket.encodeInt(worldId);
        outPacket.encodeInt(worldId);

        return outPacket;
    }
}
