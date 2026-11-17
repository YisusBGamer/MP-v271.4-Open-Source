package net.swordie.ms.connection.packet;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.mob.skill.BurnedInfo;

/**
 * Created on 2/10/2018.
 */
public class BattleRecordMan {
    public static OutPacket serverOnCalcRequestResult(boolean on) {
        OutPacket outPacket = new OutPacket(OutHeader.SERVER_ON_CALC_REQUEST_RESULT);

        outPacket.encodeByte(true);

        return outPacket;
    }

    public static OutPacket dotDamageInfo(BurnedInfo burnedInfo, int count) {
        OutPacket outPacket = new OutPacket(OutHeader.DOT_DAMAGE_INFO.getValue());

        outPacket.encodeLong(burnedInfo.getDamage());
        outPacket.encodeInt(count);

        outPacket.encodeByte(burnedInfo.getDotTickDamR() > 0);
        if (burnedInfo.getDotTickDamR() > 0) {
            outPacket.encodeInt(burnedInfo.getDotTickDamR());
        }
        outPacket.encodeInt(burnedInfo.getSkillId());
        outPacket.encodeInt(0); // v265 reads one more int

        return outPacket;
    }

    public static OutPacket killDamageInfo(int skillId, long damage) {
        OutPacket outPacket = new OutPacket(OutHeader.KILL_DAMAGE_INFO.getValue());
        outPacket.encodeInt(skillId);
        outPacket.encodeLong(damage);
        outPacket.encodeInt(0);
        return outPacket;
    }

    public static OutPacket battleDamageInfo(int mobId, int chrId, int skillId, long damageBonus) {
        OutPacket outPacket = new OutPacket(OutHeader.BATTLE_DAMAGE_INFO.getValue());
        outPacket.encodeInt(mobId);
        outPacket.encodeInt(chrId);
        outPacket.encodeInt(skillId);
        outPacket.encodeLong(damageBonus);
        outPacket.encodeInt(0);
        return outPacket;
    }

    public static OutPacket enemiesDefeatedInc() {
        // v271 reads nothing after the header (0x141f0b160): Enemies Defeated +1 while recording.
        // Contributed by Dapsy (from the Discord).
        return new OutPacket(OutHeader.MOBS_DEFEATED_INC);
    }
}
