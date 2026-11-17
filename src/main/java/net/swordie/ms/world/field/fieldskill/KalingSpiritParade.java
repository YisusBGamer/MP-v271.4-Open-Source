package net.swordie.ms.world.field.fieldskill;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.life.mob.skill.FieldSkillID;

/**
 * Kaling's Vengeful Spirits (field skill 100029, client 0x142b865c0). Points are skipped while no monster of the
 * type's refMobID is in the map, then hit as that monster's attack. The serial is kept with each point.
 */
public class KalingSpiritParade extends FieldSkill {

    private final int serial;
    private final int startDelay;
    private final int[][] types;
    private final int[][] points;

    /**
     * @param types  {lt.x, lt.y, rb.x, rb.y, delay, attackCount, refMobID, refMobAttack} per type
     * @param points {x, y, delay, type} per point
     */
    public KalingSpiritParade(int slv, int serial, int startDelay, int[][] types, int[][] points) {
        super(FieldSkillID.KalingSpiritParade, slv);
        this.serial = serial;
        this.startDelay = startDelay;
        this.types = types;
        this.points = points;
    }

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(serial);
        outPacket.encodeInt(startDelay);
        outPacket.encodeInt(types.length);
        for (int i = 0; i < types.length; i++) {
            outPacket.encodeInt(i);
            for (int v : types[i]) {
                outPacket.encodeInt(v);
            }
        }
        outPacket.encodeInt(points.length);
        for (int[] p : points) {
            for (int v : p) {
                outPacket.encodeInt(v);
            }
        }
    }
}
