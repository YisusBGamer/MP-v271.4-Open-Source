package net.swordie.ms.connection.packet;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.MobAttackInfo;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.Familiar;
import net.swordie.ms.life.movement.MovementInfo;

import java.util.List;

/**
 * @author Sjonnie
 * Created on 6/9/2018.
 */
public class CFamiliar {

    public static OutPacket familiarEnterField(List<Familiar> familiars, boolean summon, boolean noAnimation) {
        OutPacket outPacket = new OutPacket(OutHeader.FAMILIAR_ENTER_FIELD);
        if (familiars.size() > 0) {
            outPacket.encodeInt(familiars.get(0).getChr().getId());
        } else {
            outPacket.encodeInt(0);
        }
        outPacket.encodeByte(1); // has familiar data
        outPacket.encodeByte(summon); // 1 = summon, 0 = remove
        outPacket.encodeByte(noAnimation); // passed to each entry's decoder

        outPacket.encodeByte(familiars.size());
        for (Familiar familiar : familiars) {
            outPacket.encodeByte(familiar.getIdx() + 1);
            outPacket.encodeInt(familiar.getFamiliarID());
            outPacket.encodeInt((int) familiar.getId());
            outPacket.encodeString(familiar.getName());
            outPacket.encodePosition(familiar.getChr().getPosition());
            outPacket.encodeByte(familiar.getMoveAction());
            outPacket.encodeShort(familiar.getFh());
        }

        return outPacket;
    }

    public static OutPacket familiarMove(int charID, int idx, MovementInfo movementInfo) {
        OutPacket outPacket = new OutPacket(OutHeader.FAMILIAR_MOVE);

        outPacket.encodeInt(charID);
        outPacket.encodeByte(idx);
        movementInfo.encodeWithoutKeyPads(outPacket); // the reader passes 0 for the key pad part (v271 0x143446c00)

        return outPacket;
    }

    // 1078 (0x142e59a90) after the pool prefix: 1 facing, 4 mob object id, 4, 4 delay, 8 damage
    public static OutPacket familiarAttack(AttackInfo attackInfo) {
        OutPacket outPacket = new OutPacket(OutHeader.FAMILIAR_INFO);

        Familiar f = attackInfo.familiar;
        MobAttackInfo mai = attackInfo.mobAttackInfo.isEmpty() ? null : attackInfo.mobAttackInfo.get(0);

        outPacket.encodeInt(f.getChr().getId());
        outPacket.encodeByte(f.getIdx());
        outPacket.encodeByte(attackInfo.idk);
        outPacket.encodeInt(mai == null ? 0 : mai.mobId);
        outPacket.encodeInt(mai == null ? 0 : mai.templateID);
        outPacket.encodeInt(mai == null ? 0 : mai.idkInt);
        outPacket.encodeLong(mai == null || mai.damages == null || mai.damages.length == 0 ? 0 : mai.damages[0]);

        return outPacket;
    }

    public OutPacket familiarRename(Char chr, int idx, String newName) {
        OutPacket outPacket = new OutPacket(OutHeader.FAMILIAR_RENAME);

        outPacket.encodeInt(chr.getId());
        outPacket.encodeByte(idx);
        outPacket.encodeString(newName);

        return outPacket;
    }
}
