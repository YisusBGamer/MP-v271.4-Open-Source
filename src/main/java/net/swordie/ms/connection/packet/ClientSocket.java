package net.swordie.ms.connection.packet;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.handlers.header.OutHeader;

/**
 * Created on 1/10/2018.
 */
public class ClientSocket {

    /**
     * The channel port as the client should dial it. Test server: test.clientPortShift (e.g. 10000) is taken off, so
     * the client dials the Nexon-range port (8585+) and the v271 test hook moves it back up to ours (18585+), the same
     * way as the login port.
     */
    public static int clientPort(int port) {
        int shift = net.swordie.ms.ServerSettings.getInt("test.clientPortShift", 0);
        int dialled = port - shift;
        return shift > 0 && dialled >= 8484 && dialled <= 8989 ? dialled : port;
    }

    public static OutPacket migrateCommand(boolean succeed, short port) {
        OutPacket outPacket = new OutPacket(OutHeader.MIGRATE_COMMAND);

        outPacket.encodeByte(succeed); // will disconnect if false
        if (succeed) {
            byte[] server = GameConstants.SERVER_IP;
            outPacket.encodeArr(server);
            outPacket.encodeShort(clientPort(port));
            outPacket.encodeInt(0); // ?
        }

        return outPacket;
    }

    public static OutPacket opcodeEncryption(int blockSize, byte[] buf) {
        OutPacket outPacket = new OutPacket(OutHeader.OPCODE_ENCRYPTION);

        outPacket.encodeInt(blockSize);
        outPacket.encodeInt(buf.length);
        outPacket.encodeArr(buf);

        return outPacket;
    }
}
