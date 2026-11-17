package net.swordie.ms.connection.api;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.netty.NettyClient;

import java.util.List;

/**
 * Frames: a big-endian int length, then that many bytes (short opcode + body, little-endian fields).
 *
 * @author Sjonnie
 * Created on 10/5/2018.
 */
public class ApiDecoder extends ByteToMessageDecoder {

    /** The launcher's requests fit in 256 bytes; anything far bigger is not a launcher. */
    public static final int MAX_FRAME = 1024;

    @Override
    protected void decode(ChannelHandlerContext chc, ByteBuf in, List<Object> out) throws Exception {
        NettyClient c = chc.channel().attr(NettyClient.CLIENT_KEY).get();
        if (c == null) {
            in.skipBytes(in.readableBytes());
            return;
        }
        while (true) {
            if (c.getStoredLength() == -1) {
                if (in.readableBytes() < 4) {
                    return;
                }
                int length = in.readInt();
                if (length < 2 || length > MAX_FRAME) {
                    in.skipBytes(in.readableBytes());
                    chc.close();
                    return;
                }
                c.setStoredLength(length);
            }
            if (in.readableBytes() < c.getStoredLength()) {
                return;
            }
            byte[] dec = new byte[c.getStoredLength()];
            in.readBytes(dec);
            c.setStoredLength(-1);
            out.add(new InPacket(dec));
        }
    }
}
