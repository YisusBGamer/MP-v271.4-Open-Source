package net.swordie.ms.world.field.fieldskill;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.life.mob.skill.FieldSkillID;

/**
 * Created on 02/08/2021.
 *
 * @author Asura
 */
public class GloomTerrorFlame extends FieldSkill {

    private final int mobId;

    /** mobId: the tentacle holder the flames are worked out from (GMS: int mob, int 0). */
    public GloomTerrorFlame(int slv, int mobId) {
        super(FieldSkillID.GloomTerrorFlame, slv);
        this.mobId = mobId;
    }

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(mobId);
        outPacket.encodeInt(0);
    }
}
