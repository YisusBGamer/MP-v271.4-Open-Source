package net.swordie.ms.client.character.skills.shootobject;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.util.Position;

/**
 * Created on 4-4-2019.
 *
 * @author Asura
 */
public class ShootObject {
    private Char owner;
    private int skillId, slv, id;
    private short direction;
    private Position position;
    private int delay;
    private boolean flip;
    private boolean isCrystal, isUnk;
    private Position crystalPosition, unkPosition;
    private int unknownInt1, unknownInt2, unknownInt3, index;
    private Position position2;

    public ShootObject(Char owner, int skillId, int slv, int id, short direction, Position position, int delay, boolean flip, boolean isCrystal, Position crystalPosition) {
        this.owner = owner;
        this.skillId = skillId;
        this.slv = slv;
        this.id = id;
        this.direction = direction;
        this.position = position;
        this.delay = delay;
        this.flip = flip;
        this.isCrystal = isCrystal;
        this.crystalPosition = crystalPosition;
    }

    public ShootObject(Char owner, InPacket inPacket) {
        this.owner = owner;
        this.skillId = inPacket.decodeInt();
        this.slv = inPacket.decodeInt();
        this.id = inPacket.decodeInt();
        // v265 order (client writer and the reference server): int, short direction, start and end position,
        // delay, flip, int, crystal, arrow, int
        this.unknownInt1 = inPacket.decodeInt();
        this.direction = inPacket.decodeShort();
        this.position = inPacket.decodePosition();
        this.position2 = inPacket.decodePosition();
        this.delay = inPacket.decodeInt();
        this.flip = inPacket.decodeByte() != 0;
        this.unknownInt2 = inPacket.decodeInt();
        this.isCrystal = inPacket.decodeByte() != 0;
        if (isCrystal()) {
            this.crystalPosition = inPacket.decodePositionInt();
        }
        this.isUnk = inPacket.decodeByte() != 0;
        if (isUnk()) {
            this.unkPosition = inPacket.decodePositionInt();
        }
        this.unknownInt3 = inPacket.decodeInt();
        this.index = inPacket.decodeInt(); // v271: the object's index in the request
    }

    public int getUnknownInt1() {
        return unknownInt1;
    }

    public int getUnknownInt2() {
        return unknownInt2;
    }

    public int getUnknownInt3() {
        return unknownInt3;
    }

    public Position getPosition2() {
        return position2;
    }

    public void encodeShootObjectRemote(OutPacket outPacket) {
        outPacket.encodeInt(getSkillId());
        outPacket.encodeInt(getSlv());
        outPacket.encodeInt(getId());
        outPacket.encodeInt(unknownInt1);
        outPacket.encodeShort(getDirection());
        outPacket.encodePosition(getPosition()); // start
        outPacket.encodePosition(position2 != null ? position2 : getPosition()); // end
        outPacket.encodeInt(getDelay());
        outPacket.encodeByte(isFlip());
        outPacket.encodeInt(unknownInt2);
        outPacket.encodeByte(isCrystal());
        if (isCrystal()) {
            outPacket.encodePositionInt(getCrystalPosition());
        }
        outPacket.encodeByte(isUnk());
        if (isUnk()) {
            outPacket.encodePositionInt(getUnkPosition());
        }
        outPacket.encodeInt(unknownInt3);
        outPacket.encodeInt(index); // v271 (0x140f147c0) reads a second int here
    }

    public Char getOwner() {
        return owner;
    }

    public void setOwner(Char owner) {
        this.owner = owner;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getSlv() {
        return slv;
    }

    public void setSlv(int slv) {
        this.slv = slv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public short getDirection() {
        return direction;
    }

    public void setDirection(short direction) {
        this.direction = direction;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public boolean isFlip() {
        return flip;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }

    public boolean isCrystal() {
        return isCrystal;
    }

    public void setCrystal(boolean crystal) {
        isCrystal = crystal;
    }

    public Position getCrystalPosition() {
        return crystalPosition;
    }

    public void setCrystalPosition(Position crystalPosition) {
        this.crystalPosition = crystalPosition;
    }

    public boolean isUnk() {
        return isUnk;
    }

    public void setUnk(boolean unk) {
        isUnk = unk;
    }

    public Position getUnkPosition() {
        return unkPosition;
    }

    public void setUnkPosition(Position unkPosition) {
        this.unkPosition = unkPosition;
    }
}
