package net.swordie.ms.life.movement;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.life.Life;

/**
 * Created on 1/2/2018.
 */
public class MovementAction extends MovementBase {
    private final short[] params = new short[7];

    public MovementAction(InPacket inPacket, byte command) {
        super();
        this.command = command;
        if (command == 80 || command == 82) { // v271 (v265 81/83): six shorts and a short-sized block, no tail
            for (int i = 0; i < params.length; i++) {
                params[i] = inPacket.decodeShort();
            }
            return;
        }
        if (command == 92) { // v271 92 (v265 94): an int before the tail
            idk = inPacket.decodeInt();
        }
        moveAction = inPacket.decodeByte();
        elapse = inPacket.decodeShort();
        forcedStop = inPacket.decodeByte();
        tailByte = inPacket.decodeByte(); // v271: a fifth tail byte (client 0x140dce130, element +0x74)
    }

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeByte(getCommand());
        if (getCommand() == 80 || getCommand() == 82) {
            for (short p : params) {
                outPacket.encodeShort(p);
            }
            return;
        }
        if (getCommand() == 92) {
            outPacket.encodeInt(getIdk());
        }
        outPacket.encodeByte(getMoveAction());
        outPacket.encodeShort(getElapse());
        outPacket.encodeByte(getForcedStop());
        outPacket.encodeByte(tailByte);
    }

    @Override
    public void applyTo(Char chr) {
        chr.setMoveAction(moveAction);
    }

    @Override
    public void applyTo(Life life) {
        life.setMoveAction(moveAction);
    }


}
