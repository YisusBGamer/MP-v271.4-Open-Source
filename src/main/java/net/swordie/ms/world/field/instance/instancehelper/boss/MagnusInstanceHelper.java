package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.enums.DebuffObjectType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.DebuffObject;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 06/08/2021.
 *
 * @author Asura
 */
public class MagnusInstanceHelper extends InstanceHelper {


    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        if(getField().getId() == BossConstants.HARD_MAGNUS_MAPID){
            addTimer(EventManager.addFixedRateEvent(this::hardMagPoisonEmitterTick, 10000, 10000));
        }
        if(getField().getId() == BossConstants.NORMAL_MAGNUS_MAPID){
            addTimer(EventManager.addFixedRateEvent(this::normalMagPoisonEmitterTick, 10000, 15000));
        }
        // outside the aura: 10% max HP every 2 s in every mode
        addTimer(EventManager.addFixedRateEvent(this::outOfZoneDMG, 10000, 2000));
    }

    @Override
    public void onMobDeath(Field field, Mob mob) {
        super.onMobDeath(field, mob);
        if(mob.getTemplateId() == BossConstants.EASY_MAGNUS_TEMPLATE || mob.getTemplateId() == BossConstants.NORMAL_MAGNUS_TEMPLATE || mob.getTemplateId() == BossConstants.HARD_MAGNUS_TEMPLATE) {
            // GMS at the kill: phase 6666, zone 0 and this notice
            mob.changePhase(6666);
            // GMS: Magnus's Soul (the next id) stays where he fell
            field.spawnMobRevivedFrom(mob.getTemplateId() + 1, mob, 1);
            field.broadcastPacket(FieldPacket.smartMobNotice(0, mob.getTemplateId(), 5, 6666,
                    "Beating Magnus has made you immune to Guwaru's power."));
            stopTimers();
        }
    }

    @Override
    public void onMobDamaged(Char attacker, Mob mob, long damage, long oldHp, long newHp) {
        switch (mob.getTemplateId()) { // Hard magnus
            // Normal magnus
            case BossConstants.HARD_MAGNUS_TEMPLATE, BossConstants.NORMAL_MAGNUS_TEMPLATE, BossConstants.EASY_MAGNUS_TEMPLATE -> {
                var zoneId = 0;
                double perc = mob.getHp() / (double) mob.getMaxHp();
                // Easy has no 25% stage
                if (perc <= 0.25 && mob.getTemplateId() != BossConstants.EASY_MAGNUS_TEMPLATE) {
                    zoneId = 4;
                } else if (perc <= 0.5) {
                    zoneId = 3;
                } else if (perc <= 0.75) {
                    zoneId = 2;
                } else {
                    zoneId = 1;
                }

                if (mob.getCurZoneDataType() != zoneId) {
                    mob.changeZoneId(zoneId);
                    mob.changePhase(zoneId);
                    if (zoneId > 1) {
                        getField().broadcastPacket(FieldPacket.smartMobNotice(0, mob.getTemplateId(), 5, zoneId,
                                "Magnus can no longer suppress Guwaru. Guwaru's power grows..."));
                    }
                }
            }
        }
        super.onMobDamaged(attacker, mob, damage, oldHp, newHp);
    }

    private void hardMagPoisonEmitterTick(){
        List<DebuffObject> debuffObjects = new ArrayList<>();
        if (Util.getRandom(3) == 0) {
            debuffObjects.add(new DebuffObject(true, 1, DebuffObjectType.SleepGas, "sleepGas1", "sleepGas"));
        }
        if (Util.getRandom(3) == 0) {
            debuffObjects.add(new DebuffObject(true, 2, DebuffObjectType.SleepGas, "sllepGas2", "sleepGas"));
        }
        if (Util.getRandom(3) == 0) {
            debuffObjects.add(new DebuffObject(true, 3, DebuffObjectType.SleepGas, "sllepGas3", "sleepGas"));
        }
        if (Util.getRandom(3) == 0) {
            debuffObjects.add(new DebuffObject(true, 4, DebuffObjectType.SleepGas, "sleepGas4", "sleepGas"));
        }
        if (Util.getRandom(3) == 0) {
            debuffObjects.add(new DebuffObject(true, 5, DebuffObjectType.SleepGas, "sleepGas5", "sleepGas"));
        }
        getField().broadcastPacket(FieldPacket.enableDebuffObj(debuffObjects));
    }

    private void normalMagPoisonEmitterTick(){
        List<DebuffObject> debuffObjects = new ArrayList<>();
        if (Util.getRandom(3) == 0) {
            debuffObjects.add(new DebuffObject(true, 1, DebuffObjectType.SleepGas, "sleepGas10", "sleepGas"));
        }
        if (Util.getRandom(3) == 0) {
            debuffObjects.add(new DebuffObject(true, 2, DebuffObjectType.SleepGas, "sllepGas20", "sleepGas"));
        }
        if (Util.getRandom(3) == 0) {
            debuffObjects.add(new DebuffObject(true, 3, DebuffObjectType.SleepGas, "sllepGas30", "sleepGas"));
        }
        if (Util.getRandom(3) == 0) {
            debuffObjects.add(new DebuffObject(true, 4, DebuffObjectType.SleepGas, "sleepGas40", "sleepGas"));
        }
        if (Util.getRandom(3) == 0) {
            debuffObjects.add(new DebuffObject(true, 5, DebuffObjectType.SleepGas, "sleepGas50", "sleepGas"));
        }
        getField().broadcastPacket(FieldPacket.enableDebuffObj(debuffObjects));
    }

    private void outOfZoneDMG(){
        Mob mob = getMagnus();
        if(mob != null){
           Rect zone = mob.getMobInfo().getMobZones().get(mob.getCurZoneDataType());
           if (zone == null) {
               return; // no zone for this phase
           }
           Rect mobRect =  mob.getRectAround(zone);
           for(Char chr : getField().getChars()){
               if(!chr.isDead()){
                   if(!mobRect.hasPositionInside(chr.getPosition())){
                       chr.damage(chr.getMaxHP() / 10);
                   }
               }
           }
        }
    }

    private Mob getMagnus() {
        return getField().getMobs().stream().filter(mob -> mob.getTemplateId() == BossConstants.EASY_MAGNUS_TEMPLATE ||
                mob.getTemplateId() == BossConstants.NORMAL_MAGNUS_TEMPLATE || mob.getTemplateId() == BossConstants.HARD_MAGNUS_TEMPLATE).findFirst().orElse(null);
    }
}
