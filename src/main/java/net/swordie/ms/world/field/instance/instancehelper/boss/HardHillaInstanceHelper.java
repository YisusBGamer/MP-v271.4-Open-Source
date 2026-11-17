package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.connection.packet.field.HillaFieldPacket;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.skill.MobSkillID;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

public class HardHillaInstanceHelper extends InstanceHelper {
    private boolean isVampActive = false;
    private ScheduledFuture vampCheckTimer;
    private ScheduledFuture vampEndTimer;
    private static final int VAMP_DURATION = 25000; //ms,can be found in skill info
    private static final int VAMP_TICK_TIME = 1000; //ms
    // 251 is left out: it is the Gray Maiden summon at 50% (summonOnce in MobSkill 200)
    private List<Integer> possibleSpawnSlvs = Arrays.asList(new Integer[]{249, 252, 253, 256});
    private static final int AZWAN_GHOST = 8870107;
    private static final int GRAY_MAIDEN_SUMMON_LV = 251;
    private static final int HILLAS_TOWER_FIELD = 262031300;

    public void activateVamp() {
        Mob hilla = getHardHilla();

        if (hilla == null) return;

        hilla.addBlockedSkill(MobSkillID.PMCounter, 12);

        isVampActive = true;

        if (vampCheckTimer != null) {
            EventManager.stopTimer(vampCheckTimer);
        }
        vampCheckTimer = addTimer(EventManager.addFixedRateEvent(() -> onVampTick(), VAMP_TICK_TIME, VAMP_TICK_TIME));

        if (vampEndTimer != null) {
            EventManager.stopTimer(vampEndTimer);
        }
        vampEndTimer = addTimer(EventManager.addEvent(() -> onVampFinished(), VAMP_DURATION));

        getInstance().getField(HILLAS_TOWER_FIELD).broadcastPacket(HillaFieldPacket.vampStart(hilla.getObjectId()));


        //Use a random summon skill
        hilla.setForcedSkill(MobSkillID.Summon.getVal(), Util.getRandomFromCollection(possibleSpawnSlvs));
    }

    private void onVampTick() {
        Mob hilla = getHardHilla();
        if (hilla == null) return;

        Field field = getInstance().getField(HILLAS_TOWER_FIELD);
        List<Mob> fieldMobs = field.getMobs().stream().filter(m -> m.getTemplateId() != BossConstants.HILLA_HARD).collect(Collectors.toList());

        if (fieldMobs.size() == 0) return;

        field.broadcastPacket(HillaFieldPacket.vampMobAbsorb(hilla.getObjectId(),
                fieldMobs.stream().map(Mob::getObjectId).collect(Collectors.toList())));


        hilla.heal((int) (hilla.getMaxHp() * 0.01D * fieldMobs.size()));
        field.broadcastPacket(HillaFieldPacket.vampHillaAbsorb(hilla.getObjectId()));
    }

    public void onVampFinished() {
        if (vampCheckTimer != null) {
            EventManager.stopTimer(vampCheckTimer);
        }
        isVampActive = false;

        Mob hilla = getHardHilla();
        if (hilla == null) return;
        hilla.removeBlockedSkill(MobSkillID.PMCounter, 12);

    }

    @Override
    public void onMobSpawn(Field field, Mob mob) {
        super.onMobSpawn(field, mob);
        if (mob.getTemplateId() != AZWAN_GHOST) {
            return;
        }
        Mob hilla = getHardHilla();
        if (hilla == null) {
            return;
        }
        // Gray Maiden: once per fight, and she can't be hurt while a ghost lives
        hilla.addBlockedSkill(MobSkillID.Summon, GRAY_MAIDEN_SUMMON_LV);
        if (!hilla.getTemporaryStat().hasCurrentMobStat(MobStat.Invincible)) {
            var o = new Option();
            o.nOption = 1;
            o.rOption = MobSkillID.Invincible.getVal();
            o.slv = 1;
            o.tOption = 30 * 60;
            hilla.getTemporaryStat().addMobSkillOptionsAndBroadCast(MobStat.Invincible, o);
        }
    }

    @Override
    public void onMobDeath(Field field, Mob mob) {
        super.onMobDeath(field, mob);
        if (mob.getTemplateId() != AZWAN_GHOST
                || field.getMobs().stream().anyMatch(m -> m != mob && m.getTemplateId() == AZWAN_GHOST)) {
            return;
        }
        Mob hilla = getHardHilla();
        if (hilla != null && hilla.getTemporaryStat().hasCurrentMobStat(MobStat.Invincible)) {
            hilla.getTemporaryStat().removeMobStat(MobStat.Invincible);
        }
    }

    private Mob getHardHilla() {
        return (Mob) getInstance().getField(HILLAS_TOWER_FIELD).getLifeByTemplateId(BossConstants.HILLA_HARD);
    }

    @Override
    public void onChrHit(Char chr, int dmgAmount) {
        super.onChrHit(chr, dmgAmount);
        if (isVampActive) {
            Mob hilla = getHardHilla();
            if (hilla != null) {
                hilla.heal((int) (hilla.getMaxHp() * 0.05D));
                getInstance().getField(HILLAS_TOWER_FIELD).broadcastPacket(HillaFieldPacket.vampHillaAbsorb(hilla.getObjectId()));
            }
        }
    }
}
