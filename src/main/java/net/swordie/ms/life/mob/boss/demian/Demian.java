package net.swordie.ms.life.mob.boss.demian;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.boss.demian.stigma.DemianStigma;
import net.swordie.ms.life.mob.boss.demian.stigma.DemianStigmaIncinerateObject;
import net.swordie.ms.world.field.Field;

import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created on 17-8-2019.
 *
 * @author Asura
 */
public class Demian {

    /** The Incineration pillar comes back every 20 seconds while a Damien fights in this field, then stops. */
    public static ScheduledFuture stigmaIncinerateObjectTimer(Field field) {
        ScheduledFuture[] self = new ScheduledFuture[1];
        self[0] = EventManager.addFixedRateEvent(() -> {
                    if (!hasFightingDamien(field)) {
                        if (self[0] != null) {
                            self[0].cancel(false);
                        }
                        return;
                    }
                    field.spawnLifeForTime(
                            new DemianStigmaIncinerateObject(-1),
                            BossConstants.DEMIAN_STIGMA_INCINERATE_OBJECT_DURATION_TIME);
                },
                5000,
                BossConstants.DEMIAN_STIGMA_INCINERATE_OBJECT_RESPAWN_TIME,
                TimeUnit.MILLISECONDS
        );
        return self[0];
    }

    public static boolean isFightingDamien(int templateId) {
        return templateId == BossConstants.DEMIAN_NORMAL_TEMPLATE_ID_1 || templateId == BossConstants.DEMIAN_NORMAL_TEMPLATE_ID_2
                || templateId == BossConstants.DEMIAN_HARD_TEMPLATE_ID_1 || templateId == BossConstants.DEMIAN_HARD_TEMPLATE_ID_2;
    }

    /** @return true while one of the four fighting Damiens (not the fallen one or the Shadow Zone) is alive here */
    public static boolean hasFightingDamien(Field field) {
        if (field == null) {
            return false;
        }
        for (Mob mob : new ArrayList<>(field.getMobs())) {
            if (isFightingDamien(mob.getTemplateId()) && !mob.isDead() && mob.getHp() > 0) {
                return true;
            }
        }
        return false;
    }

    public static ScheduledFuture increaseStigmaPassiveTimer(Char chr) {
        return EventManager.addFixedRateEvent(() -> increaseStigmaPassive(chr), 0, BossConstants.DEMIAN_PASSIVE_STIGMA_TIME, TimeUnit.MILLISECONDS);
    }

    public static void increaseStigmaPassive(Char chr) {
        DemianStigma.incStigma(chr);
        //chr.write(DemianFieldPacket.userDeadReviveTimer(BossConstants.DEMIAN_PASSIVE_STIGMA_TIME));
    }
}
