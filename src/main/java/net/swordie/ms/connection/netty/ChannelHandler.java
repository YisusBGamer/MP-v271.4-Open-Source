package net.swordie.ms.connection.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.swordie.ms.Server;
import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.Client;
import net.swordie.ms.client.User;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.util.ErrorLogger;
import net.swordie.ms.util.HandleTimeInfo;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.TransferInfo;
import net.swordie.orm.dao.SworDaoFactory;
import net.swordie.orm.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static net.swordie.ms.connection.netty.NettyClient.CLIENT_KEY;


/**
 * Created by Sjonnie on 2/28/2017.
 */
public class ChannelHandler extends SimpleChannelInboundHandler<InPacket> {
    private static final UserDao userDao = (UserDao) SworDaoFactory.getByClass(User.class);

    public static final Map<InHeader, HandleTimeInfo> timeInfo = new java.util.concurrent.ConcurrentHashMap<>(); // all event loops write it
    private static final Logger log = LogManager.getRootLogger();
    /** Handler failures already logged without details (header and cause), so each shows once. */
    private static final Set<String> loggedHandlerErrors = java.util.concurrent.ConcurrentHashMap.newKeySet();
    private static final int LOGGED_HANDLER_ERRORS_MAX = 1000;
    private static final Map<InHeader, Method> handlers = new java.util.concurrent.ConcurrentHashMap<>(); // re-registered by hot-load while packets are handled

    public static void initHandlers(boolean mayOverride) {
        long start = System.currentTimeMillis();
        int unverified = 0;
        String handlersDir = ServerConstants.HANDLERS_DIR;
        Set<File> files = new HashSet<>();
        Util.findAllFilesInDirectory(files, new File(handlersDir));
        for (File file : files) {
            try {
                // grab all files in the handlers dir, strip them to their package name, and remove .java extension
                String className = file.getPath()
                        .replaceAll("[\\\\|/]", ".")
                        .split("src\\.main\\.java\\.")[1]
                        .replaceAll("\\.java", "");
                Class clazz = Class.forName(className);
                for (Method method : clazz.getMethods()) {
                    Handler handler = method.getAnnotation(Handler.class);
                    if (handler != null) {
                        InHeader header = handler.op();
                        if (header != InHeader.NO) {
                            if (header.getValue() == ServerConstants.UNVERIFIED_OP) {
                                unverified++;
                            }
                            if (handlers.containsKey(header) && !mayOverride) {
                                throw new IllegalArgumentException(String.format("Multiple handlers found for header %s! " +
                                        "Had method %s, but also found %s.", header, handlers.get(header).getName(), method.getName()));
                            }
                            handlers.put(header, method);
                        }
                        InHeader[] headers = handler.ops();
                        for (InHeader h : headers) {
                            if (h.getValue() == ServerConstants.UNVERIFIED_OP) {
                                unverified++;
                            }
                            handlers.put(h, method);
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                log.error("Could not load a handler class", e);
            }
        }
        log.info("Initialized " + handlers.size() + " handlers in " + (System.currentTimeMillis() - start) + "ms.");
        if (unverified > 0) {
            log.info("{} of them are bound to UNVERIFIED_OP and can never fire until their v271 number is known " +
                    "(resources/v271-dead-handlers.tsv).", unverified);
        }
    }

    public ChannelHandler(boolean autoRelease) {
        super(autoRelease);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        NettyClient o = ctx.channel().attr(CLIENT_KEY).get();
//        if(!LoginAcceptor.channelPool.containsKey(o.getIP())) {
//            log.info("[Dropping currently unknown client]");
//            o.close();
//        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.debug("[ChannelHandler] | Channel inactive.");

        Client c = (Client) ctx.channel().attr(CLIENT_KEY).get();
        if (c != null && c.isFieldTestGate()) {
            log.info(String.format("[Test] %s field test: connection closed at %d. Out held back: %s. In not handled: %s",
                    c.logWho(), System.currentTimeMillis() % 100000, c.fieldTestHeldSummary(), c.fieldTestUnhandledSummary()));
        }
        if (c != null) {
            User user = c.getUser();
            // a Champion Raid hands the session back to the character that entered it
            Char chr = net.swordie.ms.client.character.union.ChampionRaid.onDisconnect(c.getChr());
            if (chr != null && !chr.isChangingChannel() && c == chr.getClient()) {
                // client is gone without getting a new connection, and we're not expecting it
                chr.logout();
            } else if (chr != null) {
                // changing channel, or client changed (AKA this function is called after handleMigrateIn)
                // Remove the character from the channel owned by the connection that just closed; if a new connection already
                // owns the character, chr.getClient() would remove it from the new channel instead and later saves would miss it.
                if (c.getChannelInstance() != null) {
                    c.getChannelInstance().removeChar(chr);
                }
                var worldId = chr.getWorld().getWorldId().getVal();
                var transfer = Server.getInstance().getTransferInfo(chr.getId(), worldId);
                if (transfer != null) {
                    transfer.setState(TransferInfo.State.Removed);
                }
                saveOnClose(chr.getUser(), chr);
            } else if (user != null && user.getCurrentChr() != null) {
                // login
                // chr == null, but user.getCurrentChr() == null: handleMigrateIn was too early, just save user
                var world = user.getCurrentWorld();
                if (world == null) {
                    world = user.getCurrentChr().getWorld();
                }
                if (world != null) {
                    var transfer = Server.getInstance().getTransferInfo(user.getCurrentChr().getId(), world.getWorldId().getVal());
                    if (transfer != null) {
                        transfer.setState(TransferInfo.State.Removed);
                    }
                }
                saveOnClose(user, null);
            } else if (user != null) {
                // from char select => alt+f4/crash
                // just save user, just in case (make users wait 2 min to ensure old client is gone)
                saveOnClose(user, null);
                Server.getInstance().removeUser(user);
            }
        }
        NettyClient o = ctx.channel().attr(CLIENT_KEY).get();
        if (o != null) {
            o.close();
        }
    }

    /** A failed save is logged and the connection is still cleaned up; the rows it missed stay unsaved. */
    private static void saveOnClose(User user, Char chr) {
        try {
            userDao.saveOrUpdate(user, chr);
        } catch (Exception e) {
            log.error("Save on disconnect failed for login " + (user == null ? 0 : user.getId())
                    + (chr == null ? "" : ", character " + chr.getId()) + ": " + e.getMessage());
        }
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, InPacket inPacket) {
        Client c = (Client) ctx.channel().attr(CLIENT_KEY).get();
        Char chr = c.getChr();
        short op = inPacket.decodeShort();
        if (op >= InHeader.BEGIN_USER.getValue()
                && !c.getEncryptedHeaderToNormalHeaders().isEmpty()) {
            op = c.getEncryptedHeaderToNormalHeaders().getOrDefault(op, (short) -1);
        }
        if (op == -1 || !Server.OPCODE_ENCRYPTION) {
//            op = inPacket.decodeShort();
        }
        TempSkillBarWatch.onIn(c, op, inPacket.getLength());
        SwitchCharWatch.onIn(c, op, inPacket.getLength());
        InHeader inHeader = InHeader.getInHeaderByOp(op);
        // the spam list silences the field test lines too, not just the [In]/[Out] debug ones
        boolean spam = InHeader.isSpamHeader(inHeader);
        if (c.isFieldTestGate() && (inHeader == null || !NettyClient.FIELD_TEST_IN_ALLOW.contains(inHeader.name()))) {
            // v271 field test: the in-game In numbers are still v265's, so nothing is handled; first one of each logged
            if (!spam && c.noteUnhandledByFieldTest(op & 0xFFFF)) {
                log.info(String.format("[Test] %s field test: In %d (v265 name %s), %d bytes at %d, not handled | %s",
                        c.logWho(), op & 0xFFFF, inHeader, inPacket.getUnreadAmount(), System.currentTimeMillis() % 100000,
                        inPacket));
            }
            net.swordie.ms.handlers.ClientRequestLock.release(c, op & 0xFFFF);
            return;
        }
        if (c.isFieldTestGate() && !spam && c.noteHandledByFieldTest(op & 0xFFFF)) {
            log.info(String.format("[Test] %s field test: In %d %s, %d bytes at %d, handled | %s", c.logWho(),
                    op & 0xFFFF, inHeader,
                    inPacket.getUnreadAmount(), System.currentTimeMillis() % 100000, inPacket));
        }
        if (inHeader == null) {
            handleUnknown(inPacket, op, chr);
            return;
        }
        if (!InHeader.isSpamHeader(InHeader.getInHeaderByOp(op))) {
            log.debug(String.format("[In]\t| %s, %d/0x%s, %s\t| %s", InHeader.getInHeaderByOp(op), op,
                    Integer.toHexString(op).toUpperCase(), c.logWho(), inPacket));
        }
        if (chr == null && op >= InHeader.BEGIN_USER.getValue()) {
            // leftover packets from ingame after logging out
            return;
        }

        Method method = handlers.get(inHeader);
        long start = System.currentTimeMillis();
        long perf = net.swordie.ms.util.PerfLog.start();
        try {
            if (method == null) {
                handleUnknown(inPacket, op, chr);
            } else {
                Class clazz = method.getParameterTypes()[0];
                try {
                    if (method.getParameterTypes().length == 3) {
                        method.invoke(this, chr, inPacket, inHeader);
                    } else if (clazz == Client.class) {
                        method.invoke(this, c, inPacket);
                    } else if (clazz == Char.class) {
                        method.invoke(this, chr, inPacket);
                    } else {
                        log.error("Unhandled first param type of handler {}, type = {}", method.getName(), clazz);
                    }
                    if (inPacket.getUnreadAmount() > 0) {
                        InLayoutLog.leftover(inHeader, op & 0xFFFF, inPacket);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    if (InLayoutLog.isPastEnd(e)) {
                        InLayoutLog.pastEnd(inHeader, op & 0xFFFF, inPacket);
                    }
                    boolean details = Server.DEBUG || inHeader.isAlwaysShowExceptions()
                            || (Server.SHOW_EXCEPTIONS && !inHeader.isIgnoreShowExceptions());
                    if (!details) {
                        logHandlerErrorOnce(inHeader, chr, e);
                    } else {
                        if (inHeader == InHeader.MIGRATE_IN) {
                            ErrorLogger.logAsClientError(e.getCause());
                        }

                        if (e instanceof InvocationTargetException && e.getCause() != null) {
                            log.error("ChannelHandler.channelRead0", e.getCause());
                        } else {
                            log.error("ChannelHandler.channelRead0", e);
                        }
                    }
                }
            }
        } finally {
            inPacket.release();
            net.swordie.ms.util.PerfLog.end("packet", inHeader.name(), perf);
            if (Server.DEBUG_PACKET_TIMES) {
                addTimeInfo(inHeader, start);
            }
        }
    }

    private void addTimeInfo(InHeader header, long start) {
        long timeTaken = System.currentTimeMillis() - start;
        var info = timeInfo.computeIfAbsent(header, HandleTimeInfo::new);
        info.addInfo(timeTaken);
    }

    /** A handler failure without the details flags: one line per header and cause, without the stack. */
    private static void logHandlerErrorOnce(InHeader inHeader, Char chr, Exception e) {
        Throwable cause = e instanceof InvocationTargetException && e.getCause() != null ? e.getCause() : e;
        String key = inHeader + " " + cause;
        if (loggedHandlerErrors.size() < LOGGED_HANDLER_ERRORS_MAX && loggedHandlerErrors.add(key)) {
            StackTraceElement[] at = cause.getStackTrace();
            log.error("Handler {} failed for character {}: {} at {}", inHeader, chr == null ? 0 : chr.getId(), cause,
                    at.length > 0 ? at[0] : "?");
        }
    }

    private void handleUnknown(InPacket inPacket, short opCode, Char chr) {
        InHeader header = InHeader.getInHeaderByOp(opCode);
        if (chr != null) {
            // a dropped request the client waits on would leave it locked
            net.swordie.ms.handlers.ClientRequestLock.release(chr.getClient(), opCode & 0xFFFF);
        }
        if (!InHeader.isSpamHeader(header)) {
            log.debug(String.format("Unhandled opcode %s/0x%s, packet %s", opCode, Integer.toHexString(opCode).toUpperCase(), inPacket));
            // keep samples of unnamed/unhandled packets to help identify them (logs/unknown_packets.log)
            UnknownPacketLog.record("in", opCode, header, inPacket, chr);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof IOException) {
            log.debug("Client forcibly closed the game.");
        } else {
            log.error("ChannelHandler.exceptionCaught", cause);
        }
    }
}
