package net.swordie.ms.life.movement;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.life.Life;

/**
 * @author Sjonnie
 * Created on 11/9/2018.
 */
public class MovementNew1 extends MovementBase {
    public MovementNew1(InPacket inPacket, byte command) {
        super();
        this.command = command;

        this.idk = inPacket.decodeInt();

        moveAction = inPacket.decodeByte();
        elapse = inPacket.decodeShort();
        forcedStop = inPacket.decodeByte();
        tailByte = inPacket.decodeByte(); // v271: a fifth tail byte (client 0x140dce130, element +0x74)
    }

    @Override
    public void encode(OutPacket outPacket) {

        outPacket.encodeByte(getCommand());
        outPacket.encodeInt(getIdk());

        outPacket.encodeByte(getMoveAction());
        outPacket.encodeShort(getElapse());
        outPacket.encodeByte(getForcedStop());
        outPacket.encodeByte(tailByte);
    }

    @Override
    public void applyTo(Char chr) {
        chr.setPosition(getPosition());
        chr.setMoveAction(getMoveAction());
    }

    @Override
    public void applyTo(Life life) {
        life.setPosition(getPosition());
        life.setvPosition(getVPosition());
        life.setMoveAction(getMoveAction());
    }
}
