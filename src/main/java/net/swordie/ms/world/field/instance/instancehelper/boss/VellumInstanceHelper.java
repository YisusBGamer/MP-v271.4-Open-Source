package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.field.LucidFieldPacket;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.skill.MobSkillID;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.bosses.gollux.FallingCatcher;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

public class VellumInstanceHelper extends InstanceHelper {

    private static final String ROCK_CATCHER_ID = "DropStone";
    private static final int ROCK_DROP_AMOUNT = 10;
    // stalactites only stun (GMS sends hpR 0); 20 s in Normal
    private static final int ROCK_HP_R = 0;
    private static final int ROCK_DROP_DELAY_NORMAL = 20000;
    // Chaos (GMS): 10-14 of a 16-slot row 250 apart around a player, at y 443, every 4.4-8.9 s
    private static final int CHAOS_ROCK_SLOTS = 16;
    private static final int CHAOS_ROCK_GAP = 250;
    private static final int CHAOS_ROCK_Y = 443;
    private static final int CHAOS_ROCK_MIN = 10;
    private static final int CHAOS_ROCK_MAX = 14;
    private static final int CHAOS_DELAY_MIN = 4400;
    private static final int CHAOS_DELAY_MAX = 8900;
    private static final int DEEP_BREATH_HP = 41;

    private ScheduledFuture rockDropTimer = null;
    private boolean teleportBlocked = true;
    private volatile boolean vellumDead;


    @Override
    public void onMobSpawn(Field field, Mob mob) {
        super.onMobSpawn(field, mob);
        int id = mob.getTemplateId();
        if (id != BossConstants.VELLUM_NORMAL && id != BossConstants.VELLUM_CHAOS) {
            return;
        }
        // Deep Breath (the dive to a corner, 170 lv 13) is Chaos only, from 41%
        mob.addBlockedSkill(MobSkillID.Teleport, 13);
        if (rockDropTimer == null) {
            if (id == BossConstants.VELLUM_CHAOS) {
                scheduleChaosRocks(field);
            } else {
                rockDropTimer = addTimer(EventManager.addFixedRateEvent(() -> spawnRocks(field),
                        ROCK_DROP_DELAY_NORMAL, ROCK_DROP_DELAY_NORMAL));
            }
        }
    }

    @Override
    public void onMobDamaged(Char attacker, Mob mob, long damage, long oldHp, long newHp) {
        super.onMobDamaged(attacker, mob, damage, oldHp, newHp);
        if (mob.getTemplateId() == BossConstants.VELLUM_CHAOS && teleportBlocked && mob.getHpPerc() <= DEEP_BREATH_HP) {
            teleportBlocked = false;
            mob.removeBlockedSkill(MobSkillID.Teleport, 13);
        }
    }

    @Override
    public void onMobDeath(Field field, Mob mob) {
        super.onMobDeath(field, mob);
        if ((mob.getTemplateId() == BossConstants.VELLUM_CHAOS || mob.getTemplateId() == BossConstants.VELLUM_NORMAL)
                && rockDropTimer != null) {
            vellumDead = true;
            EventManager.stopTimer(rockDropTimer);
            rockDropTimer = null;
        }
    }

    private synchronized void scheduleChaosRocks(Field field) {
        if (vellumDead) {
            return;
        }
        int delay = Util.getRandom(CHAOS_DELAY_MIN, CHAOS_DELAY_MAX);
        rockDropTimer = addTimer(EventManager.addEvent(() -> {
            spawnChaosRocks(field);
            scheduleChaosRocks(field);
        }, delay));
    }

    private void spawnChaosRocks(Field field) {
        List<Char> chars = new ArrayList<>(field.getChars());
        if (chars.isEmpty()) {
            return;
        }
        int center = chars.get(Util.getRandom(chars.size() - 1)).getPosition().getX();
        int left = center - CHAOS_ROCK_GAP * (CHAOS_ROCK_SLOTS - 1) / 2 + Util.getRandom(-CHAOS_ROCK_GAP / 2, CHAOS_ROCK_GAP / 2);
        List<Integer> slots = new ArrayList<>();
        for (int i = 0; i < CHAOS_ROCK_SLOTS; i++) {
            slots.add(i);
        }
        Collections.shuffle(slots);
        int count = CHAOS_ROCK_MIN + Util.getRandom(CHAOS_ROCK_MAX - CHAOS_ROCK_MIN);
        List<Position> positions = new ArrayList<>();
        for (int slot : slots.subList(0, count)) {
            positions.add(new Position(left + slot * CHAOS_ROCK_GAP, CHAOS_ROCK_Y));
        }
        field.broadcastPacket(LucidFieldPacket.createFallingCatcher(new FallingCatcher(ROCK_CATCHER_ID, ROCK_HP_R, positions)));
    }

    private void spawnRocks(Field field) {
        if (field.getChars().isEmpty()) {
            return;
        }
        List<Position> positions = new ArrayList<>();
        for (int i = 0; i < ROCK_DROP_AMOUNT; i++) {
            Position pos = field.getRandomPosOnWalkableFoothold(100, true);
            positions.add(pos);
        }
        FallingCatcher rocks = new FallingCatcher(ROCK_CATCHER_ID, ROCK_HP_R, positions);
        field.broadcastPacket(LucidFieldPacket.createFallingCatcher(rocks));
    }
}
