package net.swordie.ms.handlers;

import net.swordie.ms.Server;
import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.Account;
import net.swordie.ms.client.CharacterSwitch;
import net.swordie.ms.client.Client;
import net.swordie.ms.client.QuickStart;
import net.swordie.ms.client.User;
import net.swordie.ms.client.character.BroadcastMsg;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.CharacterStat;
import net.swordie.ms.client.character.avatar.AvatarLook;
import net.swordie.ms.client.character.items.BodyPart;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.keys.FuncKeyMap;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.jobs.JobManager;
import net.swordie.ms.client.jobs.adventurer.BeastTamer;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.Login;
import net.swordie.ms.connection.packet.MapLoadable;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.CashItemType;
import net.swordie.ms.enums.CharNameResult;
import net.swordie.ms.enums.LoginType;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.loaders.ForbiddenWordsData;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.util.ErrorLogger;
import net.swordie.ms.util.UserUtil;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.Channel;
import net.swordie.ms.world.TransferInfo;
import net.swordie.ms.world.World;
import net.swordie.ms.world.field.MapTaggedObject;
import net.swordie.orm.dao.AccountDao;
import net.swordie.orm.dao.CharDao;
import net.swordie.orm.dao.SworDaoFactory;
import net.swordie.orm.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static net.swordie.ms.enums.InvType.EQUIPPED;

/**
 * Created on 4/28/2017.
 */
public class LoginHandler {
    private static final int RORO_THE_FAMILIAR_MANAGER = 1172000;

    private static final Logger log = LogManager.getRootLogger();

    private static final UserDao userDao = (UserDao) SworDaoFactory.getByClass(User.class);
    private static final AccountDao accountDao = (AccountDao) SworDaoFactory.getByClass(Account.class);
    private static final CharDao charDao = (CharDao) SworDaoFactory.getByClass(Char.class);

    private static final Lock userLoggedInLock = new ReentrantLock();

    @Handler(op = InHeader.PERMISSION_REQUEST)
    public static void handlePermissionRequest(Client c, InPacket inPacket) {
        // v265: locale, then the major version as a short (the v232 int + xxxyy short layout is gone)
        var locale = inPacket.decodeByte();
        var version = inPacket.decodeShort();

        if (locale != ServerConstants.LOCALE || version != ServerConstants.VERSION) {
            log.info(String.format("Client %s has an incorrect version (locale %d, version %d, rest %s).",
                    c.getIP(), locale, version, inPacket));
            c.close();
        }
    }

    @Handler(op = InHeader.APPLY_HOTFIX)
    public static void handleApplyHotfix(Client c, InPacket inPacket) {
        // the client's Data.wz hash (first 4 bytes of its SHA-1, LE), 0 when it has none
        int clientHash = inPacket.decodeInt();
        String path = net.swordie.ms.ServerSettings.getString("test.hotfixFile", "").trim();
        byte[] data = path.isEmpty() ? null : readHotfix(path);
        if (data == null || data.length == 0) {
            // size -1: no hotfix (the client waits for this before the login window and deletes any Data.wz)
            c.write(Login.sendHotfix(new byte[0]));
            return;
        }
        int hash = hotfixHash(data);
        boolean upToDate = hash == clientHash && clientHash != 0;
        log.info(String.format("[Hotfix] %s has %08x, file %08x (%d bytes): %s", c.getIP(), clientHash, hash,
                data.length, upToDate ? "up to date" : "sending"));
        for (OutPacket p : Login.hotfixPackets(upToDate ? null : data, hash)) {
            c.write(p);
        }
    }

    private static byte[] readHotfix(String path) {
        File f = new File(path);
        if (!f.isFile()) {
            log.warn("[Hotfix] test.hotfixFile not found: " + path);
            return null;
        }
        try {
            byte[] data = Files.readAllBytes(f.toPath());
            // a Data.wz is a WZ property image ("Property" at offset 0); anything else would crash the client
            if (data.length < 12 || (data[0] & 0xFF) != 0x73) {
                log.warn("[Hotfix] " + path + " is not a WZ image, not sent");
                return null;
            }
            return data;
        } catch (IOException e) {
            log.error("[Hotfix] can't read " + path, e);
            return null;
        }
    }

    private static int hotfixHash(byte[] data) {
        try {
            byte[] d = java.security.MessageDigest.getInstance("SHA-1").digest(data);
            return (d[0] & 0xFF) | (d[1] & 0xFF) << 8 | (d[2] & 0xFF) << 16 | (d[3] & 0xFF) << 24;
        } catch (java.security.NoSuchAlgorithmException e) {
            return 0;
        }
    }

    @Handler(ops = {InHeader.WORLD_INFO_REQUEST, InHeader.CHECK_LOGIN_AUTH_INFO})
    public static void handleWorldListRequest(Client c, InPacket inPacket) {
        if (inPacket.getUnreadAmount() > 0) {
            log.debug(String.format("[Login] %s world list request (%d bytes) | %s", c.getIP(),
                    inPacket.getUnreadAmount(), inPacket));
        }
        if (c.getUser() != null) {
            // v265: the user comes from SPECIAL_LOGIN_AUTH_REQUEST and stays for the whole login session
            c.setAccount(null);
            sendWorldList(c);
            // v271 Switch Characters: the reconnect logs in at 218 and then asks for the world list here
            enterSwitchTarget(c, c.getUser());
            return;
        }
        // Map.wz\Obj\login.img\WorldSelect
        // Maybe grab this from wz
        String[] bgs = new String[]{
/*                "15st",
                "16thNewtro",
                "2019haste",
                "2019holloween",
                "2019rollingHouse",
                "2019tactical",
                "6000Event",
                "Adventure",
                "adventurePathfinder",
                "Bard",
                "Beautyroid3",
                "borderless",
                "crown",
                "default",
                "doNothing",
                "GGGShow",
                "GL_Dokkaebi",
                "glory",
                "haste",
                "isekai",
                "NatureTale",
                "PL_Beautyroid",
                "PL_Beautyroid2",
                "signboard",
                "WorldUnite",
                "zero",*/

                // "rise",
                "adele"
        };
        // login background tag (126), as the reference server; not the in-map tagged object packet (123)
        c.write(Login.setLoginBackground(Util.getRandomFromCollection(bgs)));
        World first = null;
        for (World world : Server.getInstance().getWorlds()) {
            if (first == null) {
                first = world;
            }
            c.write(Login.sendWorldInformation(world, 0, null));
        }
        // v271: the Quick Login panel's Log In goes to this list's Quick Start target (world 0 when there is none),
        // so before login it names the test login's Quick Login character
        QuickLoginPick pick = testQuickLoginPick();
        List<World> worlds = Server.getInstance().getWorlds();
        if (pick != null) {
            c.write(Login.sendWorldInformationEnd(pick.entry().world(),
                    new QuickStart.Records(pick.entry(), QuickStart.Entry.NONE), worlds));
        } else {
            c.write(Login.sendWorldInformationEnd(first == null ? 0 : first.getWorldId().getVal(), 0, worlds));
        }
    }

    @Handler(op = InHeader.WVS_CRASH_CALLBACK)
    public static void handleWvsCrashCallback(Client c, InPacket inPacket) {
        if (c.getChr() != null) {
            c.getChr().setChangingChannel(false);
        }
    }

    @Handler(op = InHeader.SERVER_STATUS_REQUEST)
    public static void handleServerStatusRequest(Client c, InPacket inPacket) {
    }

    @Handler(op = InHeader.WORLD_STATUS_REQUEST)
    public static void handleWorldStatusRequest(Client c, InPacket inPacket) {
        // the v265 client's only sender of 183 is the Burning dialog (1 4 4 1 4)
        if (inPacket.getUnreadAmount() >= 13) {
            net.swordie.ms.client.character.BurningModule.handleLoginRequest(c, inPacket);
            return;
        }
        byte worldId = inPacket.decodeByte();
        c.write(Login.sendServerStatus(worldId));
    }

    @Handler(op = InHeader.SOME_CHANNEL_INFO_REQUEST)
    public static void handleUnk219_181(Client c, InPacket inPacket) {
        c.write(Login.someChannelInfoResult());
    }

    /**
     * v265 login: the hook hands the client the number after "WebStart" and the client sends it here. It is looked up
     * like the v232 string token (a launch token made of digits).
     */
    @Handler(op = InHeader.SPECIAL_LOGIN_AUTH_REQUEST)
    public static void handleSpecialLoginAuthRequest(Client c, InPacket inPacket) {
        inPacket.decodeByte();
        int token = inPacket.decodeInt();
        User user = loginWithToken(c, token, true);
        if (user == null) {
            return;
        }
        c.write(Login.checkPasswordResult(LoginType.Success, user));
        sendWorldList(c);
        // v271: QUICK_LOGIN is answered before login (QUICK_LOGIN_REQUEST); sent again here it would rebuild the
        // title's login panel
        enterSwitchTarget(c, user);
    }

    /**
     * Logs the connection in with a launch / login token. Refusals are answered with CHECK_PASSWORD_RESULT when
     * {@code answer} is set (the token login), else only with a pop-up (the v271 title, which is not waiting for it).
     *
     * @return the user, or null when refused
     */
    private static User loginWithToken(Client c, int token, boolean answer) {
        String tokenStr = Integer.toUnsignedString(token);
        // too many wrong tokens from one address: refused for a while, so tokens can't be guessed
        String guessKey = net.swordie.ms.util.LoginAttemptLimiter.key(c.getIP(), "#token");
        long blocked = net.swordie.ms.util.LoginAttemptLimiter.minutesBlocked(guessKey);
        if (blocked > 0) {
            log.info(String.format("[Login] %s sent a login token while blocked for wrong tokens (%d min left)",
                    c.getIP(), blocked));
            refuse(c, LoginType.TempBlocked, answer);
            return null;
        }
        int userId = Server.getInstance().getUserIdFromAuthToken(tokenStr, true, c.getIP());
        log.info(String.format("[Login] %s sent login token %s...: %s", c.getIP(),
                tokenStr.substring(0, Math.min(3, tokenStr.length())), userId > 0 ? "login id " + userId
                        : userId == Server.GAME_TOKEN_WRONG_ADDRESS ? "REFUSED, first used from another address"
                        : "no match"));
        if (userId == Server.GAME_TOKEN_WRONG_ADDRESS) {
            userId = 0;
        }
        if (userId > 0) {
            net.swordie.ms.util.LoginAttemptLimiter.recordSuccess(guessKey);
        } else {
            net.swordie.ms.util.LoginAttemptLimiter.recordFailure(guessKey);
        }

        if (Server.getInstance().isInShutdown()) {
            c.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage(
                    String.format("Server is restarting, so you cannot enter. (%d minutes remaining).",
                            Server.getInstance().getMinutesRemaining()))));
            refuse(c, LoginType.TempBlocked, answer);
            return null;
        }
        if (userId == 0) {
            // a token this server run never issued (most often: the server restarted after Play was pressed); the
            // client shows UnauthorizedUser as Nexon's "authenticate your account" message, so explain instead
            c.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage(
                    "This login has expired (the server may have restarted). Press Play in the launcher again.")));
            refuse(c, LoginType.TempBlocked, answer);
            return null;
        }
        User user = userDao.getById(userId);
        if (user == null) {
            refuse(c, LoginType.UnauthorizedUser, answer);
            return null;
        }

        try {
            userLoggedInLock.lock();
            if (Server.getInstance().isUserLoggedIn(user)) {
                var loggedInUser = Server.getInstance().getUserById(userId);
                if (loggedInUser == null) {
                    loggedInUser = Server.getInstance().getUserFromTransfer(userId);
                }
                if (loggedInUser != null && loggedInUser.isClientDisconnected()) {
                    loggedInUser.dropClient();
                    user.unstuck(false);
                    Server.getInstance().removeUser(loggedInUser);
                    c.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage("You were logged in, logging you out.")));
                    refuse(c, LoginType.TempBlocked, answer);
                } else {
                    if (!answer) {
                        c.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage("This login is already connected.")));
                    }
                    refuse(c, LoginType.AlreadyConnected, answer);
                }
                return null;
            } else if (user.getBanExpireDate() != null && !user.getBanExpireDate().isExpired()) {
                String banMsg = String.format("You have been banned. \r\nReason: %s \r\nExpire date: %s",
                        user.getBanReason(), user.getBanExpireDate().toLocalDateTime());
                c.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage(banMsg)));
                refuse(c, LoginType.TempBlocked, answer);
                return null;
            }
            net.swordie.ms.client.character.MaxSlotsModule.onUserLogin(user);
            c.setUser(user);
            user.setClient(c);
            Server.getInstance().addUser(user);
            user.setLastAliveAck(System.currentTimeMillis());
        } finally {
            userLoggedInLock.unlock();
        }
        // the v265 client has no second password step before CHAR_SELECT; the token is the login
        c.setAuthorized(true);
        return user;
    }

    private static void refuse(Client c, LoginType type, boolean answer) {
        if (answer) {
            c.write(Login.checkPasswordResult(type, null));
        }
    }

    /**
     * Back from Switch Characters: the client went to the title and logged in again with its launch token. With a
     * ticket for this login id and address, the world and character steps are answered at once (as the reference
     * server does), so the client moves into the target character.
     */
    private static void enterSwitchTarget(Client c, User user) {
        var ticket = CharacterSwitch.takeTicket(user.getId(), c.getIP());
        if (ticket == null) {
            return;
        }
        World world = Server.getInstance().getWorldById(ticket.worldId());
        Channel channel = world == null ? null : world.getChannelById(ticket.channelId());
        Account acc = user.getAccountByWorldId(ticket.worldId());
        if (channel == null || acc == null || !acc.hasCharacter(ticket.targetId())
                || Server.getInstance().isInTransfer(user) || user.isInGame()) {
            log.info(String.format("[Switch] %s: login id %d can't go into character %d now, world select instead",
                    c.getIP(), user.getId(), ticket.targetId()));
            return;
        }
        int channelId = ticket.channelId();
        int targetId = ticket.targetId();
        bindWorld(c, user, world, channelId);
        // MIGRATE_IN compares this with the game connection's machine id; the ticket has the old one's
        c.setMachineID(ticket.machineId());
        sendWorldSetup(c, user, world);
        // The world select result is the client's only setter of its current world, channel and channel list, which
        // the in-game Channel Change window draws from (without it: world 0, no channels). It also builds the
        // character select screen, which shows briefly before the result is read.
        c.write(Login.selectWorldResult(user, c.getAccount(), (byte) 0, world, channelId, false,
                QuickStart.load(user)));
        loginCharacterAndTransfer(c, targetId, user, world, channel);
        // a result the client ignored leaves this connection open: list and result again
        EventManager.addEvent(() -> {
            var transfer = Server.getInstance().getTransferInfo(targetId, world.getWorldId().getVal());
            if (transfer == null || transfer.getClient() != c
                    || transfer.getState().getState() != TransferInfo.State.Initial) {
                return;
            }
            log.info(String.format("[Switch] %s: login id %d still on world select, character list sent",
                    c.getIP(), user.getId()));
            c.write(Login.selectWorldResult(user, c.getAccount(), (byte) 0, world, channelId, false,
                    QuickStart.load(user)));
            c.write(Login.selectCharacterResult(LoginType.Success, (byte) 0, channel.getPort(), targetId, world));
        }, SWITCH_LIST_FALLBACK_MS);
        log.info(String.format("[Switch] %s: login id %d straight into character %d (channel %d)", c.getIP(),
                user.getId(), targetId, channelId));
    }

    /** Switch Characters: how long the relogin may stay on world select before it gets the character list. */
    private static final long SWITCH_LIST_FALLBACK_MS = 3000;

    @Handler(op = InHeader.SELECT_WORLD)
    public static void handleSelectWorld(Client c, InPacket inPacket) {
        String raw = inPacket.toString();
        inPacket.decodeByte();
        int worldId = inPacket.decodeByte();
        int channelId = inPacket.decodeByte() + 1;
        // v271 (test 13 log): local ip (4), cpu name, os name, int ram, then 32 bytes not read here. The v265 login
        // type, auth string and machine id are gone; the login's machine id comes from CHECK_SPW_REQUEST instead.
        String cpu = "";
        String os = "";
        if (inPacket.getUnreadAmount() >= 8) {
            inPacket.decodeInt(); // local ip
            cpu = inPacket.decodeString();
            os = inPacket.decodeString();
        }
        log.debug(String.format("[In]\t| SELECT_WORLD world %d channel %d, cpu \"%s\", os \"%s\"\t| %s",
                worldId, channelId, cpu, os, raw));

        User user = c.getUser();
        World world = Server.getInstance().getWorldById(worldId);
        if (user != null && (world == null || world.getChannelById(channelId) == null)) {
            // a misread request falls back to the Quick Login target the client was given
            QuickLoginPick pick = quickLoginPick(user);
            if (pick != null) {
                log.info(String.format("[Login] %s SELECT_WORLD world %d channel %d not found, using Quick Login "
                        + "target world %d channel %d", c.getIP(), worldId, channelId, pick.entry().world(),
                        pick.entry().channel() + 1));
                worldId = pick.entry().world();
                channelId = pick.entry().channel() + 1;
                world = Server.getInstance().getWorldById(worldId);
            }
        }
        if (user == null || world == null || world.getChannelById(channelId) == null) {
            c.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage("Login not found. Please restart the game from the launcher.")));
            c.write(Login.selectCharacterResult(LoginType.UnauthorizedUser));
            return;
        }
        enterWorld(c, user, world, channelId);
    }

    /** SELECT_WORLD's answer: the account for this world and the character list. */
    private static void enterWorld(Client c, User user, World world, int channelId) {
        bindWorld(c, user, world, channelId);
        sendWorldSetup(c, user, world);
        c.write(Login.selectWorldResult(user, c.getAccount(), (byte) 0, world, channelId, false, QuickStart.load(user)));
    }

    /** The login session's account for this world, world and channel. */
    private static void bindWorld(Client c, User user, World world, int channelId) {
        byte worldId = (byte) world.getWorldId().getVal();
        try {
            userLoggedInLock.lock();
            Account acc = user.getAccountByWorldId(worldId);
            if (acc == null) {
                acc = new Account(user, worldId);
                user.addAccount(acc);
                accountDao.saveOrUpdate(user, acc, null); // assign id
            }
            c.setAccount(acc);
            user.setCurrentAcc(acc);
            user.setClient(c);
            acc.setUser(user);
            if (Server.getInstance().getUserById(user.getId()) == null) {
                Server.getInstance().addUser(user);
            }
        } finally {
            userLoggedInLock.unlock();
        }

        c.setWorldId(worldId);
        c.setChannel(channelId);
    }

    /** What the client gets before the character list. */
    private static void sendWorldSetup(Client c, User user, World world) {
        c.write(Login.sendAccountInfo(user));
        c.write(Login.setClientKey(199));
        c.write(Login.setPhysicalWorldId((byte) world.getWorldId().getVal()));
        c.write(Login.setPhysicalWorldAttach("GMS"));
        c.write(Login.sendJobValues());
        c.write(Login.clientAlive());
    }

    private static void sendWorldList(Client c) {
        c.write(Login.setLoginBackground("adele"));
        int charCount = c.getAccount() != null ? c.getAccount().getCharacters().size() : 0;
        World first = null;
        for (World world : Server.getInstance().getWorlds()) {
            if (first == null) {
                first = world;
            }
            c.write(Login.sendWorldInformation(world, charCount, null));
        }
        c.write(Login.sendWorldInformationEnd(first == null ? 0 : first.getWorldId().getVal(),
                quickStartRecords(c.getUser()), Server.getInstance().getWorlds()));
    }

    /**
     * The Quick Start records for the world list end. v271: the title's Log In goes to the target record's world and
     * channel, so without a saved target it names the Quick Login character (QUICK_LOGIN_REQUEST's pick); a login
     * without characters gets none (the first world).
     */
    private static QuickStart.Records quickStartRecords(User user) {
        QuickStart.Records saved = QuickStart.load(user);
        if (user == null || QuickStart.findChar(user, saved.target()) != null) {
            return saved;
        }
        QuickLoginPick pick = quickLoginPick(user);
        return pick == null ? saved : new QuickStart.Records(pick.entry(), QuickStart.Entry.NONE);
    }

    @Handler(op = InHeader.USER_GET_TIME_REQUEST)
    public static void handleUserGetTimeRequest(Client c, InPacket inPacket) {
        c.write(Login.sendLoginTime());
    }

    @Handler(ops = {InHeader.CLIENT_LOADING_TIME_LOG, InHeader.PONG, InHeader.EXCEPTION_LOG_2,
            InHeader.WORLD_LEAP_REQUEST, InHeader.LOGIN_PANEL_NOTICE, InHeader.SOCKET_NOTICE_151,
            InHeader.UPDATE_CHARACTER_CARD_REQUEST, InHeader.CLIENT_LOCALE_REPORT})
    public static void handleIgnoredLoginReport(Client c, InPacket inPacket) {
    }

    @Handler(op = InHeader.CREATE_NEW_CHARACTER_CHECKER)
    public static void handleCreateNewCharacterChecker(Client c, InPacket inPacket) {
        OutPacket outPacket = new OutPacket(OutHeader.CREATE_NEW_CHARACTER_CHECKER_RESULT);
        outPacket.encodeByte(0);
        JobConstants.encode(outPacket);
        c.write(outPacket);
    }

    @Handler(op = InHeader.CHECK_DUPLICATE_ID)
    public static void handleCheckDuplicatedID(Client c, InPacket inPacket) {
        String name = inPacket.decodeString();
        CharNameResult code;
        if (!GameConstants.isValidName(name)) {
            code = CharNameResult.Unavailable_Invalid;
        } else {
            code = charDao.getByNameAndWorld(name, c.getWorldId()) == null ? CharNameResult.Available : CharNameResult.Unavailable_InUse;
        }
        c.write(Login.checkDuplicatedIDResult(name, code.getVal()));
    }

    @Handler(op = InHeader.CREATE_NEW_CHARACTER)
    public static void handleCreateNewCharacter(Client c, InPacket inPacket) {
        Account acc = c.getAccount();
        if (acc == null || c.getUser() == null) {
            return;
        }
        // v271: no plain-code sender in either client (built in protected code); layout read as v265, bytes logged
        log.info(String.format("[Login] %s CREATE_NEW_CHARACTER (%d bytes) | %s", c.getIP(),
                inPacket.getUnreadAmount(), inPacket));
        String name = inPacket.decodeString();
        int keySettingType = inPacket.decodeInt();
        int eventNewCharSaleJob = inPacket.decodeInt();
        int curSelectedRace = inPacket.decodeInt();

        CharNameResult code = null;
        var loginJob = JobConstants.LoginJob.getLoginJobById(curSelectedRace);
        if (loginJob == null || loginJob.getFlag() == JobConstants.LoginJob.JobFlag.DISABLED.getFlag()) {
            log.info(String.format("[Login] %s create refused: race %d is not available", c.getIP(), curSelectedRace));
            c.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage("This job is currently disabled.")));
            c.write(Login.checkDuplicatedIDResult(name, CharNameResult.Unavailable_Invalid.getVal()));
            return;
        }

        JobConstants.JobEnum job = loginJob.getBeginJob();

        short curSelectedSubJob = inPacket.decodeShort();
        byte gender = inPacket.decodeByte();
        byte skin = inPacket.decodeByte();

        int itemLength = inPacket.decodeByte() & 0xFF;
        if (itemLength < 2 || inPacket.getUnreadAmount() < itemLength * 4) {
            log.info(String.format("[Login] %s create refused: %d items, %d bytes left", c.getIP(), itemLength,
                    inPacket.getUnreadAmount()));
            c.write(Login.checkDuplicatedIDResult(name, CharNameResult.Unavailable_Invalid.getVal()));
            return;
        }
        int[] items = new int[itemLength]; //face, hair, markings, skin, overall, top, bottom, cape, boots, weapon
        for (int i = 0; i < itemLength; i++) {
            items[i] = inPacket.decodeInt();
        }
        readCreateTail(c, inPacket);
        int face = items[0];
        int hair = items[1];
        if (ItemConstants.isHair(face)) {
            // some creation screens send hair first
            face = items[1];
            hair = items[0];
        }
        if (!ItemData.isStartingItems(items) || skin > ItemConstants.MAX_SKIN || skin < 0
                || !ItemConstants.isFace(face) || !ItemConstants.isHair(hair)) {
            c.getUser().getOffenseManager().addOffense("Tried to add items unavailable on char creation.");
            code = CharNameResult.Unavailable_CashItem;
        }

        if (!GameConstants.isValidName(name) || ForbiddenWordsData.isForbiddenIGN(name)) {
            code = CharNameResult.Unavailable_Invalid;
        } else if (charDao.getByNameAndWorld(name, c.getWorldId()) != null) {
            code = CharNameResult.Unavailable_InUse;
        } else if (acc.getCharacters().size() >= ServerConstants.MAX_CHARACTERS) {
            code = CharNameResult.Unavailable_Invalid;
            c.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage("You are already at the maximum amount of characters on this world.")));
        }

        if (code != null) {
            c.write(Login.checkDuplicatedIDResult(name, code.getVal()));
            return;
        }

        int jobId = job.getJobId();

        Char chr = new Char(acc, name, keySettingType, eventNewCharSaleJob, jobId,
                curSelectedSubJob, gender, skin, face, hair, items);
        chr.setUserId(acc.getUser().getId());

        List<FuncKeyMap> fkms = new ArrayList<>();

        int keymaps = JobConstants.isBeastTamer(jobId) ? BeastTamer.KEYMAPS : 1;

        for (int i = 0; i < keymaps; i++) {
            fkms.add(FuncKeyMap.getDefaultMapping(keySettingType));
        }

        chr.setFuncKeyMap(fkms);

        // default quick slot keys
        chr.setQuickslotKeys(Arrays.asList(42, 82, 71, 73, 29, 83, 79, 81, 2, 3, 4, 5, 16, 17, 18, 19, 6, 7, 8, 9, 20,
                30, 31, 32, 10, 11, 33, 34, 37, 38, 49, 50));
        charDao.saveOrUpdate(acc, chr);
        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        if (curSelectedRace == JobConstants.LoginJob.DUAL_BLADE.getJobType()) {
            cs.setSubJob(1);
        }
        cs.setCharacterId(chr.getId());
        cs.setCharacterIdForLog(chr.getId());
        cs.setWorldIdForLog(acc.getWorldId());

        for (int i : chr.getAvatarData().getAvatarLook().getHairEquips()) {
            Equip equip = ItemData.getEquipDeepCopy(i, false);
            if (equip != null && equip.getItemId() >= 1000000) { // hardcode-ok
                equip.setBagIndex(ItemConstants.getBodyPartFromItem(
                        equip.getItemId(), chr.getAvatarData().getAvatarLook().getGender()));
                chr.addItemToInventory(EQUIPPED, equip, true);
            }
        }
        var jobHandler = JobManager.getJobById(jobId, chr);
        if (jobHandler instanceof net.swordie.ms.client.jobs.flora.Adele adele) {
            adele.setTutorialCreationStats(chr); // the v265 tutorial (bots are made at Lv. 10 in town)
        } else if (jobHandler instanceof net.swordie.ms.client.jobs.nova.Kain kain) {
            kain.setTutorialCreationStats(chr);
        } else {
            jobHandler.setCharCreationStats(chr);
        }

        Equip codex = ItemData.getEquipDeepCopy(RORO_THE_FAMILIAR_MANAGER, false);
        codex.setInvType(EQUIPPED);
        codex.setBagIndex(BodyPart.MonsterBook.getVal());
        chr.addItemToInventory(EQUIPPED, codex, true);

        if (curSelectedRace == 15) { // Zero hack for adding 2nd weapon (removing it in hairequips for zero look)
            Equip equip = ItemData.getEquipDeepCopy(ItemConstants.MASSIVE_SHADOW, false);
            equip.setBagIndex(ItemConstants.getBodyPartFromItem(
                    equip.getItemId(), chr.getAvatarData().getAvatarLook().getGender()));
            chr.addItemToInventory(EQUIPPED, equip, true);
        }

        chr.setLocation(ServerConstants.MAX_CHARACTERS); // so new characters are appended to the end

        acc.addCharacter(chr);
        acc.applyCompletedAccountQuests(chr);
        net.swordie.ms.client.character.AutoAbilityModule.setDefaultOn(chr);

        charDao.saveOrUpdate(chr);

        c.setAuthorized(true); // character is immediately allowed to login

        // false: the client asks the Burning question on the creation screen, then enters the game (in-op 184).
        // true goes back to character select, where that dialog never closes.
        c.write(Login.createNewCharacterResult(LoginType.Success, chr, false));
        log.info(String.format("[Login] %s created %s (id %d, race %d, job %d, map %d) for login id %d", c.getIP(),
                chr.getName(), chr.getId(), curSelectedRace, jobId, chr.getFieldID(), acc.getUser().getId()));

    }

    /**
     * v271 tail of CREATE_NEW_CHARACTER after the items (Test 36 log, 346 bytes): 7 strings of 32 hex digits, an int
     * length with that many opaque bytes (36), then 2 more 32-hex strings. Client machine fingerprints from the
     * protected sender: read and dropped (nothing in them about the character, burning or play style).
     */
    private static void readCreateTail(Client c, InPacket inPacket) {
        int left = inPacket.getUnreadAmount();
        if (left == 0) {
            return;
        }
        try {
            int strings = 0;
            for (int i = 0; i < 7 && inPacket.getUnreadAmount() >= 2; i++, strings++) {
                inPacket.decodeString();
            }
            int blob = inPacket.getUnreadAmount() >= 4 ? inPacket.decodeInt() : -1;
            if (blob >= 0 && blob <= inPacket.getUnreadAmount()) {
                inPacket.decodeArr(blob);
            }
            for (int i = 0; i < 2 && inPacket.getUnreadAmount() >= 2; i++, strings++) {
                inPacket.decodeString();
            }
            if (strings != 9 || inPacket.getUnreadAmount() != 0) {
                log.info(String.format("[Login] %s create: tail of %d bytes not in the known layout (%d strings, "
                        + "blob %d, %d left)", c.getIP(), left, strings, blob, inPacket.getUnreadAmount()));
            }
        } catch (Exception e) {
            log.info(String.format("[Login] %s create: tail of %d bytes not read (%s)", c.getIP(), left, e));
        }
    }

    @Handler(op = InHeader.DELETE_CHARACTER)
    public static void handleDeleteCharacter(Client c, InPacket inPacket) {
        Account acc = c.getAccount();
        if (acc != null) {
            String pic = inPacket.decodeString();
            int charId = inPacket.decodeInt();

            Char chr = acc.getCharById(charId);
            if (chr != null) {
                // a guild master can't be deleted (official); anyone else leaves their guild's member list
                var world = c.getWorld();
                var guild = world == null || chr.getGuildId() == 0 ? null : world.getGuildByID(chr.getGuildId());
                if (guild != null && guild.getLeaderID() == chr.getId() && guild.getMembers().size() > 1) {
                    c.write(Login.sendDeleteCharacterResult(charId, LoginType.DeleteCharacterFailedOnGuildMaster));
                    return;
                }
                if (guild != null) {
                    var gm = guild.getMemberByCharID(chr.getId());
                    if (guild.getMembers().size() <= 1) {
                        guild.disband();
                    } else if (gm != null) {
                        guild.broadcast(net.swordie.ms.connection.packet.WvsContext.guildResult(
                                net.swordie.ms.client.guild.result.GuildResult.leaveGuild(guild, chr.getId(),
                                        chr.getName(), false)));
                        guild.removeMember(gm);
                    }
                    chr.setGuild(null);
                }
                acc.removeChar(chr);
                c.write(Login.sendDeleteCharacterResult(charId, LoginType.Success));
            } else {
                c.write(Login.sendDeleteCharacterResult(charId, LoginType.UnauthorizedUser));
            }
        }
    }

    @Handler(op = InHeader.CLIENT_ERROR)
    public static void handleClientError(Client c, InPacket inPacket) {
        if (c.getChr() != null) {
            c.getChr().setChangingChannel(false);
        }
        c.close();
        if (inPacket.getData().length < 8) {
            log.warn(String.format("Error: %s", inPacket));
            return;
        }
        short type = inPacket.decodeShort();
        String type_str = "Unknown?!";
        if (type == 0x01) {
            type_str = "SendBackupPacket";
        } else if (type == 0x02) {
            type_str = "Crash Report";
        } else if (type == 0x03) {
            type_str = "Exception";
        }
        int errortype = inPacket.decodeInt();
        short data_length = inPacket.decodeShort();

        int idk = inPacket.decodeInt();

        short op = inPacket.decodeShort();

        OutHeader opcode = OutHeader.getOutHeaderByOp(op);
        var chrName = c.getChr() == null ? "-" : c.getChr().getName();
        ErrorLogger.logAsClientError(String.format("[%s | Error %s] (%s / %d) Data (%s): %s", chrName, errortype, opcode, op, data_length, inPacket));
        // the int before the opcode is the wire header of the packet the client choked on
        String wire = String.format("%02X %02X %02X %02X ", idk & 0xFF, (idk >>> 8) & 0xFF, (idk >>> 16) & 0xFF,
                (idk >>> 24) & 0xFF);
        var frames = c.getRecentFrames();
        StringBuilder sent = new StringBuilder("[" + chrName + "] last " + frames.size() + " frames sent (header | plain):");
        for (String f : frames) {
            sent.append("\n  ").append(f.contains(wire) ? ">> " : "   ").append(f);
        }
        ErrorLogger.logAsClientError(sent.toString());
        if (opcode == OutHeader.TEMPORARY_STAT_SET || opcode == OutHeader.REMOTE_SET_TEMPORARY_STAT) {
            if (opcode == OutHeader.TEMPORARY_STAT_SET) {
                inPacket.decodeInt();
                inPacket.decodeInt();
            } else {
                // remote
                inPacket.decodeInt();
            }
            for (int i = 0; i < CharacterTemporaryStat.length; i++) {
                int mask = inPacket.decodeInt();
                for (CharacterTemporaryStat cts : CharacterTemporaryStat.values()) {
                    if (cts.getPos() == i && (cts.getVal() & mask) != 0) {
                        ErrorLogger.logAsClientError(String.format("[Error %s] Contained stat %s", errortype, cts.toString()));
                    }
                }
            }
        } else if (opcode == OutHeader.USER_ENTER_FIELD) {
            inPacket.decodeLong();

            inPacket.decodeInt();
            inPacket.decodeInt();

            inPacket.decodeInt();
            inPacket.decodeString();
            inPacket.decodeString();

            inPacket.decodeInt();
            inPacket.decodeString();
            inPacket.decodeShort();
            inPacket.decodeByte();
            inPacket.decodeShort();
            inPacket.decodeByte();
            inPacket.decodeInt();
            inPacket.decodeInt();

            inPacket.decodeByte();

            inPacket.decodeInt();
            inPacket.decodeInt();
            inPacket.decodeInt();
            inPacket.decodeByte();
            inPacket.decodeInt();

            for (int i = 0; i < CharacterTemporaryStat.length; i++) {
                int mask = inPacket.decodeInt();
                for (CharacterTemporaryStat cts : CharacterTemporaryStat.values()) {
                    if (cts.getPos() == i && (cts.getVal() & mask) != 0) {
                        ErrorLogger.logAsClientError(String.format("[Error] Contained stat %s", cts.toString()));
                    }
                }
            }

            inPacket.decodeArr(28);
            var job = inPacket.decodeShort(); // job
            inPacket.decodeShort(); // subJob
            inPacket.decodeInt(); // Chuc
            inPacket.decodeInt(); // Af
            if (JobConstants.isZero(job)) {
                new AvatarLook(true).decode(inPacket);
                new AvatarLook(true).decode(inPacket);
            } else {
                new AvatarLook(true).decode(inPacket);
            }
            inPacket.decodeInt(); // driver
            inPacket.decodeInt(); // passenger
            inPacket.decodeInt(); // unk
            inPacket.decodeInt(); // unk

            var loop = inPacket.decodeInt();
            for (int i = 0; i < loop; i++) {
                inPacket.decodeInt();
                inPacket.decodeInt();
            }

            inPacket.decodeInt(); // ChocoCount
            inPacket.decodeInt(); // ActiveEffectItemID
            inPacket.decodeInt(); // MonkeyEffectItemID
            var activeNickItemID = inPacket.decodeInt(); // ActiveNickItemID

            var bool = inPacket.decodeByte(); // new 188
            if (bool != 0) {
                inPacket.decodeString();
            }

            if (activeNickItemID == ItemConstants.CUSTOM_TITLE_ITEM_ID) {
                // CUser::DecodeCustomTitle
                inPacket.decodeInt();
                inPacket.decodeInt();
                inPacket.decodeString();
                inPacket.decodeLong();
            }

            inPacket.decodeInt(); // Damage Skin
            inPacket.decodeInt(); // Prem Damage Skin
            inPacket.decodeInt(); // nUnitDamageSkin?
            inPacket.decodeString(); // dmgSkin Name
            inPacket.decodeString(); // premDmgSkin Name

            inPacket.decodeInt(); // something, dunno
            inPacket.decodeInt(); // DemonWingID
            inPacket.decodeInt(); // KaiserWingID
            inPacket.decodeInt(); // KaiserTailID
            inPacket.decodeByte(); // new 209
            inPacket.decodeInt(); // CompletedSetItemID
            inPacket.decodeShort(); // FieldSeatID

            var chairId = inPacket.decodeInt();
            ErrorLogger.logAsClientError(String.format("[Error] User sat on chair: %d", chairId));
        } else if (opcode == OutHeader.CASH_SHOP_CASH_ITEM_RESULT) {
            byte cashType = inPacket.decodeByte();
            CashItemType cit = CashItemType.getResultTypeByVal(cashType);
            ErrorLogger.logAsClientError(String.format("[Error %s] CashItemType %s", errortype, cit == null ? "Unknown" : cit.toString()));
        } else if (opcode == OutHeader.REMOTE_SET_ACTIVE_PORTABLE_CHAIR) {
            var chrId = inPacket.decodeInt();
            var chairId = inPacket.decodeInt();
            ErrorLogger.logAsClientError(String.format("[Error] User sat on chair: %d", chairId));
        } else if (opcode == OutHeader.REMOTE_MELEE_ATTACK) {
            inPacket.decodeInt();
            inPacket.decodeByte();
            inPacket.decodeByte();
            inPacket.decodeInt();
            inPacket.decodeInt();

            var skillId = inPacket.decodeInt(); // normally only if slv > 0, but usually there's a skill that e38s
            log.error("SkillId: " + skillId);

        }
    }

    @Handler(op = InHeader.PRIVATE_SERVER_PACKET)
    public static void handlePrivateServerPacket(Client c, InPacket inPacket) {
        if (inPacket.getUnreadAmount() == 4) { // hack to ignore another non-game op that throws you a bunch of random bytes
            c.write(Login.sendAuthResponse((OutHeader.PRIVATE_SERVER_PACKET.getValue()) ^ inPacket.decodeInt()));
        }
    }

    @Handler(op = InHeader.CHAR_SELECT_NO_PIC)
    public static void handleCharSelectNoPic(Client c, InPacket inPacket) {
        var user = c.getUser();
        // Contributed by Arixal (from GitLab).
        if (user == null || c.getAccount() == null) {
            return; // not logged in yet, or no world chosen since the world list cleared the account
        }
        if (!Util.isNullOrEmpty(c.getUser().getPic())) {
            user.getOffenseManager().addOffense("Tried logging in without pic while having a pic");
            return;
        }

        int characterId = inPacket.decodeInt();
        String mac = inPacket.decodeString();
        String somethingElse = inPacket.decodeString();

        String pic = inPacket.decodeString();
        if (!user.hasCharWithId(characterId)) {
            user.getOffenseManager().addOffense("Tried logging on invalid char id " + characterId);
            c.write(Login.selectCharacterResult(LoginType.UnauthorizedUser));
            return;
        }
        if (!UserUtil.isValidPic(pic)) {
            c.write(Login.selectCharacterResult(LoginType.IncorrectPassword));
            return;
        }
        user.setPicAndHash(pic);
        userDao.saveProperties(user);

        if (user.getBanExpireDate() != null && !user.getBanExpireDate().isExpired())
        {
            log.error(String.format("[CharId: %d] Logged in while being banned.", characterId));
            c.write(WvsContext.returnToTitle());
            c.close();
            return;
        }

        byte worldId = c.getWorldId();
        int channelId = c.getChannel();

        if (c.getAccount().hasCharacter(characterId)) {
            Channel channel = Server.getInstance().getWorldById(worldId).getChannelById(channelId);
            Server.getInstance().removeCharsByUser(user);
            c.getUser().setCurrentChr(c.getAccount().getCharById(characterId));
            var world = Server.getInstance().getWorldById(worldId);
            world.getChannelById(channelId).addClientInTransfer(null, channel, characterId, c);
            c.write(Login.selectCharacterResult(LoginType.Success, (byte) 0, channel.getPort(), characterId, world));
        } else {
            c.write(Login.selectCharacterResult(LoginType.UnauthorizedUser));
            user.getOffenseManager().addOffense("Tried logging on invalid char id " + characterId + " on world " + worldId);
        }
    }

    @Handler(ops = {InHeader.CHAR_SELECT, InHeader.SELECT_CHARACTER})
    public static void handleCharSelect(Client c, InPacket inPacket) {
        int characterId = inPacket.decodeInt();
        // CHAR_SELECT adds the name; SELECT_CHARACTER is the id alone
        byte worldId = c.getWorldId();
        int channelId = c.getChannel();

        var user = c.getUser();
        // Contributed by Arixal (from GitLab).
        if (user == null || c.getAccount() == null) {
            return; // not logged in yet, or no world chosen since the world list cleared the account
        }

        var world = Server.getInstance().getWorldById(worldId);
        var channel = world.getChannelById(channelId);

        if (user.getBanExpireDate() != null && !user.getBanExpireDate().isExpired())
        {
            log.error("[CharId: {}] Logged in while being banned.", characterId);
            c.write(WvsContext.returnToTitle());
            c.close();
            return;
        }

        if (c.isAuthorized() && c.getAccount().hasCharacter(characterId) && !Server.getInstance().isInTransfer(user) && !user.isInGame()) {
            loginCharacterAndTransfer(c, characterId, user, world, channel);
        }
        // if anything is wrong, the 2nd pwd authorizer should return an error
    }

    private static void loginCharacterAndTransfer(Client c, int characterId, User user, World world, Channel channel) {
        Server.getInstance().removeCharsByUser(user);
        c.getUser().setCurrentChr(c.getAccount().getCharById(characterId));
        c.getUser().setCurrentWorld(world);
        channel.addClientInTransfer(null, channel, characterId, c);
        c.write(Login.selectCharacterResult(LoginType.Success, (byte) 0, channel.getPort(), characterId, world));
        QuickStart.saveLast(user, new QuickStart.Entry(world.getWorldId().getVal(), channel.getChannelId() - 1,
                characterId));
    }

    @Handler(ops = {InHeader.SELECT_CHARACTER_AFTER_CREATE})
    public static void handleSelectCharacterAfterCreate(Client c, InPacket inPacket) {
        int charId = inPacket.decodeInt();
        var online = inPacket.decodeByte() != 0;
        var hardwareId = inPacket.decodeString();
        var hardwareId2 = inPacket.decodeString();
        // v271 (sender in 0x1439ca2f0): the same mac / hwid strings as CHECK_SPW_REQUEST, which this path skips;
        // MIGRATE_IN is checked against the hwid's machine id
        byte[] machineIdPrefix = machineIdPrefixOf(hardwareId2);
        if (machineIdPrefix == null) {
            machineIdPrefix = machineIdPrefixOf(hardwareId);
        }
        if (machineIdPrefix != null) {
            c.setMachineID(machineIdPrefix);
        }
        log.info(String.format("[Login] %s enters new character %d right after creating it (machine id %s)",
                c.getIP(), charId, machineIdPrefix == null ? "not found in \"" + hardwareId + "\" / \""
                        + hardwareId2 + "\"" : "set"));

        byte worldId = c.getWorldId();
        int channelId = c.getChannel();
        var world = Server.getInstance().getWorldById(worldId);
        var channel = world == null ? null : world.getChannelById(channelId);

        var user = c.getUser();
        if (user == null || channel == null || c.getAccount() == null) {
            return;
        }

        if (user.getBanExpireDate() != null && !user.getBanExpireDate().isExpired())
        {
            log.error("[CharId: {}] Logged in while being banned.", charId);
            c.write(WvsContext.returnToTitle());
            c.close();
            return;
        }

        // a repeated packet must not add a second transfer
        if (c.isAuthorized() && c.getAccount().hasCharacter(charId) && !Server.getInstance().isInTransfer(user)
                && !user.isInGame()) {
            loginCharacterAndTransfer(c, charId, user, world, channel);
        }
    }

    /**
     * The machine id's first 10 bytes from the login's hwid string ("F057A69A6E64_02D3CEC8": 6 + 4 bytes in hex, the
     * same bytes MIGRATE_IN starts its 16-byte machine id with). Null when the string has another shape.
     */
    static byte[] machineIdPrefixOf(String hwid) {
        if (hwid == null || !hwid.matches("[0-9A-Fa-f]{12}_[0-9A-Fa-f]{8}")) {
            return null;
        }
        String hex = hwid.replace("_", "");
        byte[] out = new byte[hex.length() / 2];
        for (int i = 0; i < out.length; i++) {
            out[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
        }
        return out;
    }

    @Handler(op = InHeader.CHECK_SPW_REQUEST)
    public static boolean handleCheckSpwRequest(Client c, InPacket inPacket) {
        boolean success = false;
        User user = c.getUser();
        // Contributed by Arixal (from GitLab).
        if (user == null) {
            return false; // not logged in yet
        }
        Account account = c.getAccount();
        int picEncodingType = inPacket.decodeInt();
        /* hardcoded 0, could change the encoding of pic in further patches . In 202.2 I think it did some weird things
        per encoding type + length. Got reverted in 202.3 though.
           */
        String pic = inPacket.decodeString();
        int charId = inPacket.decodeInt();
        boolean invisible = inPacket.decodeByte() != 0;
        String mac = inPacket.decodeString();
        String hwid = inPacket.decodeString();
        byte[] machineIdPrefix = machineIdPrefixOf(hwid);
        if (machineIdPrefix != null) {
            c.setMachineID(machineIdPrefix);
        }
//        int userId = inPacket.decodeInt();
        // after this: 2 strings indicating pc info. Not interested in that rn
        if (!Util.isStringBCrypt(user.getPic())) {
            user.setPicAndHash(user.getPic());
        }

        success = true;
        c.setAuthorized(success);
        return success;
    }

    @Handler(op = InHeader.CHANGE_SPW_REQUEST)
    public static void handleChangeSpwRequest(Client c, InPacket inPacket) {
        User user = c.getUser();
        // Contributed by Arixal (from GitLab).
        if (user == null) {
            return; // not logged in yet
        }
        String currentPic = inPacket.decodeString();
        String newPic = inPacket.decodeString();
        if (BCrypt.checkpw(currentPic, user.getPic())) {
            user.setPicAndHash(newPic);
            userDao.saveProperties(user);
        } else {
            c.write(Login.selectCharacterResult(LoginType.IncorrectPassword));
        }
    }

    @Handler(op = InHeader.AUTH_FAILURE)
    public static void handleAuthFailure(Client c, InPacket inPacket) {
        byte step = inPacket.decodeByte();
        int errorCode = inPacket.decodeInt();
        log.error(String.format("Auth failure! Login step %d, errorCode %d.", step, errorCode));
    }

    @Handler(op = InHeader.EXCEPTION_LOG)
    public static void handleExceptionLog(Client c, InPacket inPacket) {
        // v265: an int, then the text as raw bytes up to the end of the packet
        var lenOrSomething = inPacket.decodeInt();
        String str = new String(inPacket.decodeArr(inPacket.getUnreadAmount()), StandardCharsets.US_ASCII);
        if (!str.contains("]")) {
            var toLog = String.format("[%d] Exception log (ignore first 6 bytes of data): %s", lenOrSomething, str);
            log.error(toLog);
            ErrorLogger.logAsClientError(toLog);
            return;
        }
        String packet = str.split("[]]")[1].substring(12); // skip everything up until the opcode
        byte[] fullPacketArr = Util.getByteArrayByString(packet);
        short op = (short) ((fullPacketArr[0] & 0xFF) + ((fullPacketArr[1] & 0xFF) << 8));
        byte[] packetData = new byte[fullPacketArr.length - 2];
        System.arraycopy(fullPacketArr, 2, packetData, 0, packetData.length);
        OutHeader header = OutHeader.getOutHeaderByOp(op);
        ErrorLogger.logAsClientError(String.format("[%d] Exception log: [%s], %d/0x%X\t| %s", lenOrSomething, header, op, op, Util.readableByteArray(packetData)));
        if (header == OutHeader.TEMPORARY_STAT_SET || header == OutHeader.REMOTE_SET_TEMPORARY_STAT
                || header == OutHeader.USER_ENTER_FIELD) {
            inPacket = new InPacket(packetData);
            if (header == OutHeader.REMOTE_SET_TEMPORARY_STAT) {
                inPacket.decodeInt(); // chr id
            } else if (header == OutHeader.TEMPORARY_STAT_SET) {
                inPacket.decodeInt();
                inPacket.decodeInt();
            }
            else { // USER_ENTER_FIELD
                inPacket.decodeInt();
                inPacket.decodeInt();
                inPacket.decodeString();
                inPacket.decodeString(); // parent name, deprecated
                // guild
                inPacket.decodeString();
                inPacket.decodeShort();
                inPacket.decodeByte();
                inPacket.decodeShort();
                inPacket.decodeByte();
                // end guild
                inPacket.decodeByte();
                inPacket.decodeInt();
                inPacket.decodeInt();
                inPacket.decodeInt();
                inPacket.decodeInt();
                inPacket.decodeByte();
            }
            for (int i = 0; i < CharacterTemporaryStat.length; i++) {
                int mask = inPacket.decodeInt();
                for (CharacterTemporaryStat cts : CharacterTemporaryStat.values()) {
                    if (cts.getPos() == i && (cts.getVal() & mask) != 0) {
                        log.error("Contained stat {}", cts.toString());
                    }
                }
            }
        }
    }

    /**
     * v271 sender 0x1439bc3c0: byte, int world, byte Quick Login (the panel's Log In), byte, byte. Answered with
     * SELECT_WORLD_BUTTON; on success the client opens the world's channels (Quick Login goes on to SELECT_WORLD with
     * the world and channel of its Quick Start target).
     */
    @Handler(op = InHeader.VIEW_CHANNEL_REQUEST)
    public static void handleViewChannelRequest(Client c, InPacket inPacket) {
        byte idk = inPacket.decodeByte();
        int worldId = inPacket.decodeInt();
        boolean quickLogin = inPacket.getUnreadAmount() > 0 && inPacket.decodeByte() != 0;
        World world = Server.getInstance().getWorldById(worldId);
        if (quickLogin) {
            log.info(String.format("[Login] %s Quick Login: Log In for world %d", c.getIP(), worldId));
            if (world == null || !bindQuickLoginUser(c)) {
                c.write(Login.viewChannelResult(LoginType.Unknown, worldId));
                return;
            }
        }
        if (world != null) {
            c.write(Login.viewChannelResult(LoginType.Success, worldId));
        } else {
            c.write(Login.viewChannelResult(LoginType.Unknown, worldId));
        }

    }

    @Handler(op = InHeader.CHAR_POSITION_CHANGE)
    public static void handleCharPositionChange(Client c, InPacket inPacket) {
        User user = c.getUser();
        Account acc = c.getAccount();
        if (acc == null) {
            return;
        }
        inPacket.decodeInt(); // user id
        int size = inPacket.decodeInt();
        for (int i = 0; i < size; i++) {
            int charId = inPacket.decodeInt();
            Char chr = acc.getCharById(charId);
            if (chr == null) {
                user.getOffenseManager().addOffense("Tried to change order of a Char that is not linked to their account.");
            } else {
                chr.setLocation(i);
            }
        }

        accountDao.updateLocation(acc);
    }

    @Handler(ops = {InHeader.ALIVE_ACK, InHeader.PING_CHECK_ACK})
    public static void handleAliveAck(Client c, InPacket inPacket) {
        if (c.isWaitingForAliveAck()) {
            c.setPing((System.currentTimeMillis() - c.getLastPingTime()) / 2);
        }
        if (c.getUser() != null) {
            c.getUser().setLastAliveAck(System.currentTimeMillis());
        }
        c.resetUnackedPings();
    }

    /**
     * The client clears its world list and asks for it again (at the start and on "back" from character select).
     * 29 bytes: the Quick Start target (world, channel index, character id, favourite flag; -1, -1, 0, 0 when there
     * is none), the last played character (world, channel index, character id), then the character select theme
     * (-1 if unchanged). The theme window's Apply only changes the client's copy; this is where it reaches the server.
     */
    @Handler(op = InHeader.WORLD_LIST_REQUEST)
    public static void handleWorldListRefresh(Client c, InPacket inPacket) {
        QuickStart.Entry target = QuickStart.Entry.NONE;
        boolean favourite = false;
        int theme = -1;
        if (inPacket.getUnreadAmount() >= 29) {
            target = new QuickStart.Entry(inPacket.decodeInt(), inPacket.decodeInt(), inPacket.decodeInt());
            favourite = inPacket.decodeByte() != 0;
            inPacket.decodeInt();
            inPacket.decodeInt();
            inPacket.decodeInt();
            theme = inPacket.decodeInt();
        }
        var user = c.getUser();
        if (user == null) {
            handleWorldListRequest(c, inPacket);
            return;
        }
        // the favourite is set or cleared on the character select screen and only reaches the server here
        if (target.world() >= 0 && QuickStart.findChar(user, target) != null) {
            QuickStart.saveFavourite(user, favourite ? target : null);
        }
        if (theme >= 0 && theme < 100000) {
            QuickStart.saveTheme(user, theme);
            log.info(String.format("[Login] %s login id %d chose character select theme %d", c.getIP(),
                    user.getId(), theme));
        }
        // still logged in: the next SELECT_WORLD adds the user again
        if (!Server.getInstance().isInTransfer(user)) {
            Server.getInstance().removeUser(user);
        }
        c.setAccount(null);
        sendWorldList(c);
        // v271: no QUICK_LOGIN here; the client asks for it once on the title (QUICK_LOGIN_REQUEST)
    }

    /**
     * v271: sent once when the login screen opens, before the world info request. The screen shows "Loading Quick
     * Login character" until QUICK_LOGIN arrives; the panel's Log In button only shows with a character.
     * plus.dll appends the launcher's WebStart token (4 bytes; the client itself sends this packet empty): that logs
     * the connection in, and the panel shows that login's character. Without a token only the test login
     * (test.launchUser) is shown.
     */
    @Handler(op = InHeader.QUICK_LOGIN_REQUEST)
    public static void handleQuickLoginRequest(Client c, InPacket inPacket) {
        User user = c.getUser();
        if (user == null && inPacket.getUnreadAmount() >= 4) {
            user = loginWithToken(c, inPacket.decodeInt(), false);
            if (user != null) {
                log.info(String.format("[Login] %s logged in at the title with the launch token: %s (login id %d)",
                        c.getIP(), user.getName(), user.getId()));
            }
        }
        QuickLoginPick pick = user != null ? quickLoginPick(user) : testQuickLoginPick();
        Char chr = pick == null ? null : pick.chr();
        net.swordie.ms.connection.OutPacket reply;
        try {
            reply = Login.quickLogin(chr);
        } catch (Exception e) {
            log.error("[Login] Quick Login entry for " + chr.getName(), e);
            chr = null;
            reply = Login.quickLogin(null);
        }
        log.info(String.format("[Login] %s Quick Login: %s", c.getIP(),
                chr == null ? "no character" : chr.getName() + " (id " + chr.getId() + ")"));
        c.write(reply);
    }

    /** A login, its Quick Login character and that character's (world, channel index, id). */
    private record QuickLoginPick(User user, Char chr, QuickStart.Entry entry) {}

    /** {@link #quickLoginPick(User)} of test.launchUser; null when it is not set. */
    private static QuickLoginPick testQuickLoginPick() {
        String name = net.swordie.ms.ServerSettings.getString("test.launchUser", "");
        if (name.isEmpty()) {
            return null;
        }
        try {
            User user = userDao.getByName(name);
            return user == null ? null : quickLoginPick(user);
        } catch (Exception e) {
            log.error("[Login] Quick Login character for " + name, e);
            return null;
        }
    }

    /**
     * Quick Start target (favourite, else last played), else the login's highest level character, on a world and
     * channel this server has; null when the login has no character.
     */
    private static QuickLoginPick quickLoginPick(User user) {
        try {
            QuickStart.Entry entry = QuickStart.load(user).target();
            Char chr = QuickStart.findChar(user, entry);
            if (chr == null) {
                for (Account acc : user.getAccounts()) {
                    if (Server.getInstance().getWorldById(acc.getWorldId()) == null) {
                        continue;
                    }
                    for (Char ch : acc.getCharacters()) {
                        if (chr == null || ch.getLevel() > chr.getLevel()) {
                            chr = ch;
                            entry = new QuickStart.Entry(acc.getWorldId(), 0, ch.getId());
                        }
                    }
                }
            }
            if (chr == null) {
                return null;
            }
            // the client goes to this world and channel index; keep it on a channel this server has
            World world = Server.getInstance().getWorldById(entry.world());
            if (world == null) {
                return null;
            }
            if (world.getChannelById(entry.channel() + 1) == null) {
                entry = new QuickStart.Entry(entry.world(), 0, entry.charId());
            }
            return new QuickLoginPick(user, chr, entry);
        } catch (Exception e) {
            log.error("[Login] Quick Login character for login id " + user.getId(), e);
            return null;
        }
    }

    /**
     * v271: the Quick Login panel's Log In goes straight to VIEW_CHANNEL_REQUEST without a login token (the client's
     * token sender is only reached from protected code that this path never runs). With test.quickLoginBind on, a
     * loopback connection is logged in as test.launchUser here; the token login stays the only other way in.
     */
    private static boolean bindQuickLoginUser(Client c) {
        if (c.getUser() != null) {
            return true;
        }
        if (!net.swordie.ms.ServerSettings.getBoolean("test.quickLoginBind", false)) {
            log.info(String.format("[Login] %s Quick Login without a login token; test.quickLoginBind is off",
                    c.getIP()));
            c.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage(
                    "Not logged in. Start the game with Play in the Swordie Plus launcher.")));
            return false;
        }
        String ip = c.getIP();
        if (ip == null || !(ip.startsWith("127.") || ip.equals("0:0:0:0:0:0:0:1"))) {
            log.info(String.format("[Login] %s Quick Login refused: not a loopback connection", ip));
            return false;
        }
        QuickLoginPick pick = testQuickLoginPick();
        if (pick == null) {
            log.info(String.format("[Login] %s Quick Login refused: test.launchUser has no character", ip));
            return false;
        }
        User user = pick.user();
        try {
            userLoggedInLock.lock();
            if (Server.getInstance().isUserLoggedIn(user)) {
                c.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage("This login is already connected.")));
                log.info(String.format("[Login] %s Quick Login refused: login id %d already connected", ip,
                        user.getId()));
                return false;
            }
            if (user.getBanExpireDate() != null && !user.getBanExpireDate().isExpired()) {
                c.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage("This login is banned.")));
                return false;
            }
            net.swordie.ms.client.character.MaxSlotsModule.onUserLogin(user);
            c.setUser(user);
            user.setClient(c);
            Server.getInstance().addUser(user);
            user.setLastAliveAck(System.currentTimeMillis());
        } finally {
            userLoggedInLock.unlock();
        }
        c.setAuthorized(true);
        log.info(String.format("[Login] %s Quick Login: logged in as %s (login id %d), test.quickLoginBind", ip,
                user.getName(), user.getId()));
        return true;
    }

    /** v271: empty notices that the client dropped its Quick Login state (after an error popup or a world change). */
    @Handler(ops = {InHeader.QUICK_LOGIN_CANCEL, InHeader.QUICK_LOGIN_WORLD_CHANGE})
    public static void handleQuickLoginCancel(Client c, InPacket inPacket) {
        log.info(String.format("[Login] %s Quick Login state dropped by the client", c.getIP()));
    }
}
