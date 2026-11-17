package net.swordie.ms.connection.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.swordie.ms.client.Client;
import net.swordie.ms.connection.crypto.MapleCrypto;
import net.swordie.ms.connection.packet.Login;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

import static net.swordie.ms.connection.netty.NettyClient.CLIENT_KEY;

public class ChannelAcceptor implements Runnable {

    public net.swordie.ms.world.Channel channel;
    private static final Logger log = LogManager.getRootLogger();

    @Override
    public void run() {
        // Taken from http://netty.io/wiki/user-guide-for-4.x.html

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) {

                    ch.pipeline().addLast(new PacketDecoder(), new ChannelHandler(true), new PacketEncoder());

                    byte[] siv = new byte[4];
                    byte[] riv = new byte[4];

                    Random random = new Random();
                    random.nextBytes(siv);
                    random.nextBytes(riv);

                    Client c = new Client(ch, siv, riv);
                    long helloDelay = net.swordie.ms.ServerSettings.getInt("test.channelHelloDelayMs", 0);
                    if (helloDelay <= 0) {
                        c.write(Login.sendConnect(riv, siv, true));
                    } else {
                        // the hello must go out unencrypted: written as raw bytes, past the packet encoder
                        net.swordie.ms.connection.OutPacket hello = Login.sendConnect(riv, siv, true);
                        byte[] helloBytes = hello.getData();
                        hello.release();
                        log.info(String.format("[Test] channel %s: connected at %d, hello in %d ms",
                                ch.remoteAddress(), System.currentTimeMillis() % 100000, helloDelay));
                        ch.eventLoop().schedule(() -> {
                            if (!ch.isActive()) {
                                log.info("[Test] channel connection closed before the hello");
                                return;
                            }
                            log.info(String.format("[Test] channel: hello sent at %d (%s)",
                                    System.currentTimeMillis() % 100000, net.swordie.ms.util.Util.readableByteArray(helloBytes)));
                            ch.writeAndFlush(io.netty.buffer.Unpooled.wrappedBuffer(helloBytes));
                        }, helloDelay, java.util.concurrent.TimeUnit.MILLISECONDS);
                    }

                    ch.attr(CLIENT_KEY).set(c);
                    ch.attr(Client.CRYPTO_KEY).set(new MapleCrypto());

                    // The client sends MIGRATE_IN from its hello handler, before it reads anything else, and its
                    // receive cipher changes around then (in v265 a late 4-byte SECURITY_PACKET_CODE crashed a login),
                    // so the early packets go in one flush with the file check list last.
                    // v271 test: the file check list's number is unknown (UNVERIFIED_OP, dropped), and
                    // test.channelEarlyDelayMs holds the early packets back so MIGRATE_IN shows up on its own first.
                    java.util.List<net.swordie.ms.connection.Packet> early = java.util.List.of(
                            Login.sendServerValues(),
                            Login.sendServerEnvironment(),
                            Login.sendWzCheckList(),
                            Login.sendSecurityCodePacket(),
                            Login.sendSecurityCodePacketAttach(),
                            Login.sendFileCheckList());
                    long earlyDelay = net.swordie.ms.ServerSettings.getInt("test.channelEarlyDelayMs", 0);
                    if (helloDelay > 0) {
                        earlyDelay = helloDelay + Math.max(earlyDelay, 0);
                    }
                    if (!net.swordie.ms.ServerSettings.getBoolean("test.channelEarlyPackets", true)) {
                        // v271 field test: the channel sends no early packets (the client has the login's), so
                        // nothing in AES crosses the client's switch to the in-game cipher after MIGRATE_IN
                        log.info(String.format("[Test] channel %s: no early packets (test.channelEarlyPackets=false)",
                                c.getIP()));
                    } else if (earlyDelay <= 0) {
                        c.write(early);
                    } else {
                        log.info(String.format("[Test] channel %s: hello sent at %d, early packets in %d ms",
                                c.getIP(), System.currentTimeMillis() % 100000, earlyDelay));
                        ch.eventLoop().schedule(() -> {
                            if (!ch.isActive()) {
                                log.info("[Test] channel connection closed before the early packets");
                                return;
                            }
                            log.info(String.format("[Test] channel %s: early packets sent at %d", c.getIP(),
                                    System.currentTimeMillis() % 100000));
                            c.write(early);
                        }, earlyDelay, java.util.concurrent.TimeUnit.MILLISECONDS);
                    }

                    // the first ping waits until the character is in, so no AES ping crosses the switch
                    c.startGamePing(10000);
                }
            });

            b.childOption(ChannelOption.TCP_NODELAY, true);
            b.childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections.
            String host = net.swordie.ms.ServerSettings.getString("test.bindAddress", "");
            ChannelFuture f = (host.isEmpty() ? b.bind(channel.getPort()) : b.bind(host, channel.getPort())).sync();
            log.info(String.format("Channel %s-%d listening on port %d%s", channel.getWorldId(), channel.getChannelId(),
                    channel.getPort(), host.isEmpty() ? "" : " (" + host + " only)"));
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("ChannelAcceptor.initChannel", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
