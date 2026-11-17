package net.swordie.ms.world.field.fieldskill;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.life.mob.skill.FieldSkillID;

/** Guardian Angel Slime: Magma Slimes thrown from the boss's hands (field skill 100023). */
public class GuardianSlimeMagmaThrow extends FieldSkill {

    // values GMS sends for this level
    private static final int[] HEADER = {1, 3000, 40, 240, 1440, 210};
    private static final int THROW_TIME = 3000;

    private final int magmaId;
    private final int[][] throwsXYV;

    /** @param throwsXYV x, y, vx, vy per throw */
    public GuardianSlimeMagmaThrow(int slv, int magmaId, int[][] throwsXYV) {
        super(FieldSkillID.GuardianSlimeMagmaThrow, slv);
        this.magmaId = magmaId;
        this.throwsXYV = throwsXYV;
    }

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(magmaId);
        for (int v : HEADER) {
            outPacket.encodeInt(v);
        }
        outPacket.encodeInt(throwsXYV.length);
        for (int[] t : throwsXYV) {
            outPacket.encodeShort(t[0]);
            outPacket.encodeShort(t[1]);
            outPacket.encodeShort(t[2]);
            outPacket.encodeShort(t[3]);
            outPacket.encodeShort(THROW_TIME);
        }
    }
}
