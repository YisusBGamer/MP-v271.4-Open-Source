package net.swordie.ms.world.field.fieldskill;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.life.mob.skill.FieldSkillID;

/** Guardian Angel Slime: slime rain on the given points (field skill 100025). */
public class GuardianSlimeRain extends FieldSkill {

    // lt -40,-16 rb 36,1 and delay 1440 match FieldSkill.img/100025; the rest is what GMS sends
    private static final int[] AREA = {-40, -16, 36, 1, 1440, 126, 53, 1};
    private static final int[] TAIL = {2, 1};

    private final int magmaId;
    private final int[][] points;

    public GuardianSlimeRain(int slv, int magmaId, int[][] points) {
        super(FieldSkillID.GuardianSlimeRain, slv);
        this.magmaId = magmaId;
        this.points = points;
    }

    @Override
    public void encode(OutPacket outPacket) {
        for (int v : AREA) {
            outPacket.encodeInt(v);
        }
        outPacket.encodeInt(magmaId);
        for (int v : TAIL) {
            outPacket.encodeInt(v);
        }
        outPacket.encodeByte(0);
        outPacket.encodeInt(points.length);
        for (int[] p : points) {
            outPacket.encodeShort(p[0]);
            outPacket.encodeShort(p[1]);
        }
    }
}
