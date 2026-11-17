package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.MobPool;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.enums.WeatherEffNoticeType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.DeathType;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Shared parts of the boss fights whose official mechanics run on the server: timers that stop when the boss dies,
 * adds that are cleaned up afterwards, HP thresholds that fire once, and the Hard/Chaos marker of the instance.
 */
public abstract class BossMechanicsHelper extends InstanceHelper {

    protected static final Logger log = LogManager.getLogger(BossMechanicsHelper.class);

    private final Set<Integer> addObjectIds = ConcurrentHashMap.newKeySet();
    private final Set<Integer> firedThresholds = ConcurrentHashMap.newKeySet();
    private volatile boolean bossDead = false;

    /** @return the boss's template ids (all difficulties); the fight's mechanics stop once one of them dies */
    protected abstract Set<Integer> getBossIds();

    /** Starts the fight's mechanics (4 seconds after the party enters). */
    protected abstract void startMechanics();

    @Override
    protected void startStage(int stageNumber) {
        if (stageNumber == 1) {
            try {
                startMechanics();
            } catch (Exception e) {
                log.error("Could not start boss mechanics", e);
            }
        }
    }

    protected boolean isBossDead() {
        return bossDead;
    }

    protected boolean isHardMode() {
        return getInstance() != null && getInstance().hasProperty(BossConstants.HARD_MODE_KEY);
    }

    protected Mob getBoss() {
        Field field = getField();
        if (field == null) {
            return null;
        }
        for (Mob mob : new ArrayList<>(field.getMobs())) {
            if (getBossIds().contains(mob.getTemplateId()) && !mob.isDead()) {
                return mob;
            }
        }
        return null;
    }

    /** @return the boss's HP left (0 to 1), or -1 if it isn't there */
    protected double getBossHpRatio() {
        Mob boss = getBoss();
        if (boss == null || boss.getMaxHp() <= 0) {
            return -1;
        }
        return boss.getHp() / (double) boss.getMaxHp();
    }

    /** @return true the first time the boss's HP is at or below this percentage */
    protected boolean crossedThreshold(int percent) {
        double ratio = getBossHpRatio();
        return ratio >= 0 && ratio * 100 <= percent && firedThresholds.add(percent);
    }

    protected Position randomGround() {
        Field field = getField();
        return field == null ? new Position(0, 0) : field.getRandomPosOnWalkableFoothold(40, true);
    }

    protected Mob spawnAdd(int templateId, Position pos, long hp) {
        Field field = getField();
        if (field == null || bossDead || pos == null) {
            return null;
        }
        Mob mob = field.spawnMob(templateId, pos.getX(), pos.getY(), false, hp);
        if (mob != null) {
            addObjectIds.add(mob.getObjectId());
        }
        return mob;
    }

    protected boolean isAdd(Mob mob) {
        return mob != null && addObjectIds.contains(mob.getObjectId());
    }

    protected int countAlive(int... templateIds) {
        Field field = getField();
        if (field == null) {
            return 0;
        }
        int count = 0;
        for (Mob mob : new ArrayList<>(field.getMobs())) {
            if (mob.isDead()) {
                continue;
            }
            for (int id : templateIds) {
                if (mob.getTemplateId() == id) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    /** Removes a mob without a death (no EXP, no drops). */
    protected void despawn(Mob mob) {
        Field field = getField();
        if (field == null || mob == null || mob.isDead()) {
            return;
        }
        addObjectIds.remove(mob.getObjectId());
        field.broadcastPacket(MobPool.leaveField(mob.getObjectId(), DeathType.ANIMATION_DEATH));
        field.removeLife(mob.getObjectId());
    }

    protected List<Char> getLivingChars() {
        Field field = getField();
        List<Char> res = new ArrayList<>();
        if (field != null) {
            for (Char chr : field.getChars()) {
                if (chr.getHP() > 0) {
                    res.add(chr);
                }
            }
        }
        return res;
    }

    /** Runs the task every interval while the boss is alive. */
    protected void every(long intervalMs, Runnable task) {
        addTimer(EventManager.addFixedRateEvent(() -> {
            if (bossDead || getField() == null) {
                return;
            }
            try {
                task.run();
            } catch (Exception e) {
                log.error("Boss mechanic failed", e);
            }
        }, intervalMs, intervalMs));
    }

    protected void later(long delayMs, Runnable task) {
        addTimer(EventManager.addEvent(() -> {
            if (bossDead || getField() == null) {
                return;
            }
            try {
                task.run();
            } catch (Exception e) {
                log.error("Boss mechanic failed", e);
            }
        }, delayMs));
    }

    protected void notice(String text) {
        if (getInstance() != null) {
            getInstance().chatMessage(text);
        }
    }

    /** the boss's map notice, in its GMS style and duration */
    protected void bossNotice(WeatherEffNoticeType type, String text, int durationMs) {
        if (getInstance() != null) {
            getInstance().broadcast(WvsContext.weatherEffectNotice(type, text, durationMs));
        }
    }

    /** Called when an add (not the boss) dies. */
    protected void onAddDeath(Mob mob) {
    }

    /** Called once when the boss dies, before the adds are cleaned up. */
    protected void onBossDeath(Mob boss) {
    }

    @Override
    public void onMobDeath(Field field, Mob mob) {
        if (getBossIds().contains(mob.getTemplateId())) {
            if (!bossDead) {
                bossDead = true;
                stopTimers();
                onBossDeath(mob);
                // the adds leave with the boss (official: the fight ends)
                for (Mob add : new ArrayList<>(field.getMobs())) {
                    if (addObjectIds.contains(add.getObjectId())) {
                        despawn(add);
                    }
                }
            }
        } else if (addObjectIds.remove(mob.getObjectId())) {
            onAddDeath(mob);
        }
        super.onMobDeath(field, mob);
    }
}
