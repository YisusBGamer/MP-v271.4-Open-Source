package net.swordie.ms.connection.api;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import net.swordie.ms.client.Client;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.ApiRequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static net.swordie.ms.connection.netty.NettyClient.CLIENT_KEY;

/**
 * The launcher's requests. Each one gets exactly one answer; the work (password hashing, database) runs on a small
 * pool of its own, never on the network threads. Request contents are never logged (they carry passwords and tokens).
 *
 * @author Sjonnie
 * Created on 10/5/2018.
 */
public class ApiHandler extends SimpleChannelInboundHandler<InPacket> {

    private static final Logger log = LogManager.getLogger();

    private static final ThreadPoolExecutor API_EXECUTOR = new ThreadPoolExecutor(2, 4, 60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(64), new ThreadFactoryBuilder().setNameFormat("api-%d").setDaemon(true).build());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InPacket inPacket) {
        Client c = (Client) ctx.channel().attr(CLIENT_KEY).get();
        short op = inPacket.decodeShort();
        ApiInHeader opHeader = ApiInHeader.getByVal(op);
        if (c == null || opHeader == null) {
            log.warn(String.format("Unknown launcher API request %d from %s; closing", op,
                    c == null ? "?" : c.getIP()));
            ctx.close();
            return;
        }
        try {
            API_EXECUTOR.execute(() -> handleApiRequest(inPacket, c, opHeader));
        } catch (RejectedExecutionException e) {
            inPacket.release();
            ctx.close();
        }
    }

    private void handleApiRequest(InPacket inPacket, Client c, ApiInHeader opHeader) {
        try {
            switch (opHeader) {
                case REQUEST_TOKEN -> ApiRequestHandler.handleOldTokenRequest(c, inPacket);
                case REQUEST_TOKEN_X64 -> ApiRequestHandler.handleTokenRequest(c, inPacket);
                case CREATE_ACCOUNT_REQUEST -> ApiRequestHandler.handleCreateAccountRequest(c, inPacket);
                case REQUEST_ACCOUNT_DATA_X64 -> ApiRequestHandler.handleAccountDataRequest(c, inPacket);
                case LOGOUT_REQUEST -> ApiRequestHandler.handleLogoutRequest(c, inPacket);
                case REQUEST_WORLD_STATS -> ApiRequestHandler.handleWorldStatsRequest(c, inPacket);
                case REQUEST_ACCOUNT_UPDATE -> ApiRequestHandler.handleAccountUpdateRequest(c, inPacket);
            }
        } catch (ApiRequestHandler.BadRequestException e) {
            log.warn(String.format("Bad launcher API request (%s) from %s: %s; closing", opHeader, c.getIP(),
                    e.getMessage()));
            c.close();
        } catch (Exception e) {
            log.error(String.format("Launcher API request %s from %s failed", opHeader, c.getIP()), e);
            c.close();
        } finally {
            inPacket.release();
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            ctx.close();
            return;
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (!(cause instanceof IOException)) {
            log.warn("Launcher API connection error: " + cause);
        }
        ctx.close();
    }
}
