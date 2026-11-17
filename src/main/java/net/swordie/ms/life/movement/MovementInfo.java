package net.swordie.ms.life.movement;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.Encodable;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.life.Life;
import net.swordie.ms.util.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sjonnie
 * Created on 8/16/2018.
 */
public class MovementInfo implements Encodable {
    private static final Logger log = LogManager.getLogger();

    private int encodedGatherDuration;
    private Position oldPos;
    private Position oldVPos;
    private List<Movement> movements = new ArrayList<>();
    private byte keyPadState;
    private int unkInt;
    private byte[] keyPads = new byte[0];

    public MovementInfo(Position oldPos, Position oldVPos) {
        this.oldPos = oldPos;
        this.oldVPos = oldVPos;
    }

    public MovementInfo(InPacket inPacket) {
        decode(inPacket);
    }

    public void applyTo(Char chr) {
        for (Movement m : getMovements()) {
            m.applyTo(chr);
        }
    }

    public void applyTo(Life life) {
        for (Movement m : getMovements()) {
            m.applyTo(life);
        }
    }

    public void decode(InPacket inPacket) {
        encodedGatherDuration = inPacket.decodeInt();
        unkInt = inPacket.decodeInt();
        oldPos = inPacket.decodePosition();
        oldVPos = inPacket.decodePosition();
        movements = parseMovement(inPacket);
        keyPadState = inPacket.decodeByte();
        keyPads = new byte[(keyPadState + 1) / 2];
        for (int i = 0; i < keyPads.length; i++) {
            keyPads[i] = inPacket.decodeByte();
        }
    }

    @Override
    public void encode(OutPacket outPacket) {
        encode(outPacket, false);
    }

    public void encode(OutPacket outPacket, boolean passive) {
        encodeWithoutKeyPads(outPacket);
        // v265 sends the key pad part with every path
        outPacket.encodeByte(keyPadState);
        outPacket.encodeArr(keyPads.length == (keyPadState + 1) / 2 ? keyPads : new byte[(keyPadState + 1) / 2]);
    }

    /** The path alone: MOB_MOVE's reader (CMob::OnMove) skips the key pad count and nibbles. */
    public void encodeWithoutKeyPads(OutPacket outPacket) {
        outPacket.encodeInt(encodedGatherDuration);
        outPacket.encodeInt(unkInt);
        outPacket.encodePosition(oldPos);
        outPacket.encodePosition(oldVPos);
        outPacket.encodeShort(movements.size());
        for (Movement m : movements) {
            m.encode(outPacket);
        }
    }

    /**
     * The v271 element grammar, from the client's path encoder 0x140dce130 (v265 0x140bf8ac0). The type numbers moved
     * (some types were removed and added), and every element with the common tail (move action, elapse, forced stop)
     * now has a fifth tail byte (read by the classes). Same field shapes per class as v265.
     */
    private static List<Movement> parseMovement(InPacket inPacket) {
        List<Movement> res = new ArrayList<>();
        short size = inPacket.decodeShort();
        for (int i = 0; i < size; i++) {
            byte type = inPacket.decodeByte();
            switch (type & 0xFF) {
                // x, y, vx, vy, fh, [foot for 15/17/108], x offset, y offset, short, tail (108: an int first)
                case 0: case 8: case 15: case 17: case 19:
                case 72: case 73: case 74: case 75: case 76: case 77:
                case 93: case 107: case 108:
                    res.add(new MovementNormal(inPacket, type));
                    break;
                // vx, vy, [foot for 21/22], [x/y offset for 63], tail
                case 1: case 2: case 18: case 21: case 22: case 24:
                case 63: case 65: case 66: case 67: case 68: case 69: case 70: case 98:
                    res.add(new MovementJump(inPacket, type));
                    break;
                // x, y, fh, int, tail
                case 3: case 4: case 5: case 6: case 7: case 9: case 10: case 11: case 13: case 26: case 27:
                case 55: case 56: case 57: case 83: case 84: case 85: case 88: case 110:
                    res.add(new MovementTeleport(inPacket, type));
                    break;
                case 12: // one byte, no tail
                    res.add(new MovementStatChange(inPacket, type));
                    break;
                case 14: case 16: // vx, vy, foot, tail
                case 28: // x, y, fh, tail (same shape)
                    res.add(new MovementStartFallDown(inPacket, type));
                    break;
                // x, y, vx, vy, tail
                case 23: case 29: case 101: case 102:
                    res.add(new MovementFlyingBlock(inPacket, type));
                    break;
                // int, tail
                case 31: case 32: case 44:
                    res.add(new MovementNew1(inPacket, type));
                    break;
                case 52: // x, y, vx, vy, short, tail
                    res.add(new MovementOffsetX(inPacket, type));
                    break;
                case 59: case 71: // x, y, vx, vy, fh, tail
                    res.add(new MovementAngle(inPacket, type));
                    break;
                default:
                    // the tail only (92: an int first; 80/82: their own block, no tail); the encoder's default case
                    res.add(new MovementAction(inPacket, type));
                    break;
            }
        }
        return res;
    }

    public byte getKeyPadState() {
        return keyPadState;
    }

    public List<Movement> getMovements() {
        return movements;
    }
}
