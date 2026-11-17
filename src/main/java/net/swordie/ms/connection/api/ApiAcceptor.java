package net.swordie.ms.connection.api;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static net.swordie.ms.connection.netty.NettyClient.CLIENT_KEY;

/**
 * The launcher's TCP API (see {@link ApiHandler}).
 *
 * @author Sjonnie
 * Created on 10/5/2018.
 */
public class ApiAcceptor implements Runnable {

    private static final Logger log = LogManager.getLogger();

    private static final Map<String, AtomicInteger> connectionsByAddress = new ConcurrentHashMap<>();

    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) {
                    String address = address(ch);
                    AtomicInteger open = connectionsByAddress.computeIfAbsent(address, k -> new AtomicInteger());
                    if (open.incrementAndGet() > Math.max(1, ServerConstants.CLIENT_API_MAX_PER_ADDRESS)) {
                        open.decrementAndGet();
                        ch.close();
                        return;
                    }
                    ch.closeFuture().addListener(f -> {
                        if (open.decrementAndGet() <= 0) {
                            connectionsByAddress.remove(address, open);
                        }
                    });

                    if (ServerConstants.CLIENT_API_IDLE_MINUTES > 0) {
                        ch.pipeline().addLast(new IdleStateHandler(ServerConstants.CLIENT_API_IDLE_MINUTES, 0, 0,
                                TimeUnit.MINUTES));
                    }
                    ch.pipeline().addLast(new ApiDecoder(), new ApiHandler(), new ApiEncoder());

                    Client c = new Client(ch, new byte[1], new byte[1]);

                    ch.attr(CLIENT_KEY).set(c);
                }
            });

            b.childOption(ChannelOption.TCP_NODELAY, true);
            b.childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(ServerConstants.CLIENT_API_PORT).sync();
            log.info(String.format("Launcher API listening on port %d", ServerConstants.CLIENT_API_PORT));

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error(String.format("Launcher API could not start on port %d: %s", ServerConstants.CLIENT_API_PORT,
                    e.getMessage()));
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    static String address(SocketChannel ch) {
        InetSocketAddress remote = ch.remoteAddress();
        return remote == null || remote.getAddress() == null ? "?" : remote.getAddress().getHostAddress();
    }
}
