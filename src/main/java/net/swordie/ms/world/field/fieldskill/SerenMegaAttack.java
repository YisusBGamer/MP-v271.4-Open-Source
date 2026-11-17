package net.swordie.ms.world.field.fieldskill;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.life.mob.skill.FieldSkillID;

/**
 * Chosen Seren's Ray of Light (field skill 100024, v271 decoder 0x14314b6e0): a row of rays, one of them the safe
 * one. If a ray touches anyone, every client plays the white-out and takes refMob's attack.
 */
public class SerenMegaAttack extends FieldSkill {

    private final int rays;
    private final int safeRay;
    private final int delay;
    private final int whiteOut;
    private final int refMob;
    private final int[] attackMobs;

    public SerenMegaAttack(int slv, int rays, int safeRay, int delay, int whiteOut, int refMob, int[] attackMobs) {
        super(FieldSkillID.SerenMegaAttack, slv);
        this.rays = rays;
        this.safeRay = safeRay;
        this.delay = delay;
        this.whiteOut = whiteOut;
        this.refMob = refMob;
        this.attackMobs = attackMobs;
    }

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(rays);
        outPacket.encodeInt(safeRay);
        outPacket.encodeInt(delay);
        outPacket.encodeInt(whiteOut);
        outPacket.encodeInt(0);
        outPacket.encodeInt(refMob);
        outPacket.encodeInt(0); // refMobAttack
        outPacket.encodeInt(1); // attackMobAction
        outPacket.encodeInt(attackMobs.length);
        for (int mob : attackMobs) {
            outPacket.encodeInt(mob);
        }
    }
}
