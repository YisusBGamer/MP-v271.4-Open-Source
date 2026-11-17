/*
    This file is part of Desu: MapleStory v62 Server Emulator
    Copyright (C) 2014  Zygon <watchmystarz@hotmail.com>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package net.swordie.ms.connection.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.swordie.ms.Server;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.Packet;
import net.swordie.ms.connection.crypto.MapleCrypto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Implementation of a Netty decoder pattern so that decryption of MapleStory
 * packets is possible. Follows steps using the special MapleAES as well as
 * ShandaCrypto (which became non-used after v149.2 in GMS).
 *
 * @author Zygon
 */
public class PacketDecoder extends ByteToMessageDecoder {
    private static final Logger log = LogManager.getRootLogger();

    @Override
    protected void decode(ChannelHandlerContext chc, ByteBuf in, List<Object> out) {
        NettyClient c = chc.channel().attr(NettyClient.CLIENT_KEY).get();
        MapleCrypto mCr = chc.channel().attr(NettyClient.CRYPTO_KEY).get();
        if (c != null) {
            byte[] iv = c.getRecvIV();
            if (c.getStoredLength() == -1) {
                if (in.readableBytes() < 4) {
                    return;
                }
                in.markReaderIndex();
                int h = in.readInt();
                if (!MapleCrypto.checkPacket(h, iv)) {
                    if (Server.DEBUG) {
                        log.warn(String.format("[PacketDecoder] | Incorrect packet seq! Dropping client %s.", c.getIP()));
                    }
                    c.close();
                    return;
                }
                int len = MapleCrypto.getLength(h);
                if (len >= Packet.MAX_SHORT_PACKET_SIZE) {
                    // extended header: LE int length xor the first header word, bit 31 is a flag
                    if (in.readableBytes() < 4) {
                        in.resetReaderIndex();
                        return;
                    }
                    int rawSeq = ((h >>> 24) & 0xFF) | (((h >>> 16) & 0xFF) << 8);
                    len = (in.readIntLE() ^ rawSeq) & 0x7FFFFFFF;
                    if (len > 0x100000) {
                        log.warn(String.format("[PacketDecoder] | Oversized packet (%d) from %s, dropping.", len, c.getIP()));
                        c.close();
                        return;
                    }
                }
                c.setStoredLength(len);
            }
            if (in.readableBytes() >= c.getStoredLength()) {
                byte[] dec = new byte[c.getStoredLength()];
                in.readBytes(dec);
                c.setStoredLength(-1);

                // v271 sends some long packets with a 1452-byte first block; keep what a re-read needs
                boolean keep = dec.length > InPacket.SHORT_FIRST_BLOCK && dec.length < Packet.MAX_SHORT_PACKET_SIZE;
                byte[] cipher = keep ? dec.clone() : null;
                byte[] cipherIv = keep ? iv.clone() : null;
                dec = mCr.crypt(dec, iv);
                c.setRecvIV(MapleCrypto.getNewIv(iv));

                InPacket inPacket = new InPacket(dec);
                if (keep) {
                    inPacket.keepCipher(cipher, cipherIv, mCr);
                }
                out.add(inPacket);
            }
        }
    }
}
