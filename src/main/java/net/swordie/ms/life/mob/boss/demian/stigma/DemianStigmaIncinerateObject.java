package net.swordie.ms.life.mob.boss.demian.stigma;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.field.DemianFieldPacket;
import net.swordie.ms.enums.EnterType;
import net.swordie.ms.life.Life;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.Field;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 18-8-2019.
 *
 * @author Asura
 */
public class DemianStigmaIncinerateObject extends Life {

    /** Hold time at the pillar; the client sends success by itself once it has passed. */
    public static final int CASTING_TIME = 2500;
    /** Slack for clock drift when checking a success. */
    private static final int CASTING_SLACK = 500;

    // char id -> server time the cast started
    private final Map<Integer, Long> casts = new ConcurrentHashMap<>();

    public DemianStigmaIncinerateObject(int templateId) {
        super(templateId);
    }

    public void startCast(Char chr) {
        casts.put(chr.getId(), System.currentTimeMillis());
    }

    /** Ends the cast; true if it started at this pillar and ran its full time. */
    public boolean endCast(Char chr) {
        Long start = casts.remove(chr.getId());
        return start != null && System.currentTimeMillis() - start >= CASTING_TIME - CASTING_SLACK;
    }

    @Override
    public void broadcastSpawnPacket(Char onlyChar, EnterType enterType) {
        Field field = getField();
        if (onlyChar == null || getPosition() == null) {
            // new spot per spawn; late joiners get the spot everyone else sees
            setPosition(new Position(new Random().nextInt(1400) + 100, 16));
        }
        if (onlyChar == null) {
            field.broadcastPacket(DemianFieldPacket.stigmaIncinerateObjectPacket(this, enterType.equals(EnterType.Animation)));
        } else {
            onlyChar.write(DemianFieldPacket.stigmaIncinerateObjectPacket(this, enterType.equals(EnterType.Animation)));
        }
    }

    @Override
    public void broadcastLeavePacket() {
        Field field = getField();
        field.broadcastPacket(DemianFieldPacket.stigmaIncinerateObjectPacket(null, true)); // null -> remove
    }
}
