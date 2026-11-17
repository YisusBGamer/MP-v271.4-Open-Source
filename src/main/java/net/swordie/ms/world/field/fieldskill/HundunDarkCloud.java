package net.swordie.ms.world.field.fieldskill;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.life.mob.skill.FieldSkillID;

/**
 * Hundun's Dark Cloud (field skill 100028, client 0x142b85180): the key comes back in FIELD_SKILL_REQUEST; the boss
 * heads for (x, y) for the duration. Box and art come from FieldSkill.img/100028/level/slv.
 */
public class HundunDarkCloud extends FieldSkill {

    private final int key;
    private final int mobObjectId;
    private final int x, y;
    private final int durationMs;

    public HundunDarkCloud(int slv, int key, int mobObjectId, int x, int y, int durationMs) {
        super(FieldSkillID.HundunDarkCloud, slv);
        this.key = key;
        this.mobObjectId = mobObjectId;
        this.x = x;
        this.y = y;
        this.durationMs = durationMs;
    }

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(key);
        outPacket.encodeInt(mobObjectId);
        outPacket.encodeInt(x);
        outPacket.encodeInt(y);
        outPacket.encodeInt(durationMs);
    }
}
