package net.swordie.ms.world.field;

import net.swordie.ms.connection.packet.MobPool;
import net.swordie.ms.constants.CustomConstants;
import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.constants.MobConstants;
import net.swordie.ms.events.Events;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.containerclasses.MobInfo;
import net.swordie.ms.util.Position;

/**
 * @author Sjonnie
 * Created on 7/26/2018.
 */
public class MobGen extends Life {
    private static final int OCTOPIRATE_MOB = 9001005;
    private static final int ORIGIN_OF_CLOCKTOWER_FIELD = 220080100;
    private static final int ORIGIN_OF_CLOCKTOWER_FIELD_2 = 220080200;
    private static final int ORIGIN_OF_CLOCKTOWER_FIELD_3 = 220080300;

    private MobInfo mob;
    private long nextPossibleSpawnTime = Long.MIN_VALUE;
    private boolean hasSpawned;

    public MobGen(int templateId) {
        super(templateId);
    }

    public MobInfo getMob() {
        return mob;
    }

    public void setMob(MobInfo mob) {
        this.mob = mob;
    }

    /**
     * Spawns a Mob at the position of this MobGen.
     */
    public void spawnMob(boolean buffed) {
        Mob mob = getMob().toMob();
        Field field = getField();

        Position pos = getPosition().deepCopy();

        Foothold fh = field.getInfo().getFootholdById(getFh());
        if (fh == null) {
            fh = field.findFootHoldBelow(pos, 150);
            if (fh == null) {
                // Edge case where the mob is spawned on some weird foothold
                return;
            }
        }
        // a spawn point usually sits above the platform it belongs to; a real client drops the mob onto
        // that platform as it appears and reports it (MOB_MOVE). Without a controlling client nothing does, so the
        // server does that first fall itself.
        // If the spawn point's stored foothold does not even reach this x (a spawn at a platform's edge), use what
        // is really under the spawn point instead.
        if (!isFlyer(mob)) {
            Foothold ground = fh;
            // the spawn's own foothold (Map.wz life fh, its cy) is where GMS puts it however far below the spawn
            // point it is (MOB_ENTER_FIELD carries cy: 993166250's Specters at y -397 stand on the floor at 29)
            boolean ownFh = true;
            if (pos.getX() < Math.min(fh.getX1(), fh.getX2())
                    || pos.getX() > Math.max(fh.getX1(), fh.getX2())) {
                ground = field.findFootHoldBelow(pos, SETTLE_MAX_DROP);
                ownFh = false;
            }
            if (ground != null) {
                int groundY = ground.getYFromX(pos.getX());
                if (groundY > pos.getY() && (ownFh || groundY - pos.getY() <= SETTLE_MAX_DROP)) {
                    pos.setY(groundY);
                    fh = ground; // the mob lands on the foothold under its spawn point
                }
            }
        }
        mob.setPosition(pos.deepCopy());
        mob.setHomePosition(pos.deepCopy());
        mob.setHomeFoothold(fh.deepCopy());
        mob.setCurFoodhold(fh.deepCopy());

        // non-elite bosses don't get buffed
        if (buffed && !mob.isBoss()) {
            // the buffed channel multipliers
            mob.buff(net.swordie.ms.world.BuffedChannels.getHpMultiplier(),
                    net.swordie.ms.world.BuffedChannels.getExpMultiplier());
            mob.setScale(CustomConstants.BUFFED_MOB_SCALE); // a little bigger, so players can tell
        }

        field.spawnLife(mob, null);
        if (CustomConstants.AUTO_AGGRO && FieldConstants.isAggroField(field.getId())) {
            field.broadcastPacket(MobPool.forceChase(mob.getObjectId(), false));
        }
        setNextPossibleSpawnTime(System.currentTimeMillis() + (getMobTime() * 1000));
        setHasSpawned(true);
        lastMob = getMobTime() > 0 ? mob : null;
    }

    // the mob this spawn point last spawned, for spawn points with a respawn time (area bosses)
    private Mob lastMob;

    /** How far above a floor a monster may spawn and still be dropped onto it. */
    public static final int SETTLE_MAX_DROP = 400;

    /** A monster that flies keeps its spawn height: it has no floor to fall to. */
    private static boolean isFlyer(Mob mob) {
        var mi = mob.getMobInfo();
        return mi != null && (mi.getFlySpeed() > 0 || (mi.getMobType() != null && mi.getMobType().contains("f")));
    }

    public MobGen deepCopy() {
        MobGen mobGen = new MobGen(getTemplateId());
        if (getMob() != null) {
            mobGen.setPosition(getPosition());
            mobGen.setMob(getMob());
            mobGen.setMobTime(getMobTime());
            mobGen.setFh(getFh());
        }
        return mobGen;
    }

    public boolean canSpawnOnField(Field field) {
        if (getMobTime() > 0 && lastMob != null) {
            if (field.getMobs().contains(lastMob)) {
                return false; // its mob (e.g. an area boss) is still alive: no second one
            }
            // it died (or left): the respawn time counts from now, like the official servers
            setNextPossibleSpawnTime(System.currentTimeMillis() + getMobTime() * 1000L);
            lastMob = null;
            return false;
        }
        int currentMobs = field.getMobs().size();
        // not over max mobs, delay of spawn ended, if mobtime == -1 (not respawnable) must not have yet spawned
        // no mob in area around this, unless kishin is active
        return canSpawnOnSpecialField(field)
                && currentMobs < field.getMobCapacity()
                && getNextPossibleSpawnTime() < System.currentTimeMillis()
                && (getMobTime() != -1 || !hasSpawned()
                    || getMob().getTemplateId() == OCTOPIRATE_MOB) // 2nd job canonneer quest, has a mob time of -1
                ;
    }

    private boolean canSpawnOnSpecialField(Field field) {
        if (net.swordie.ms.constants.BossConstants.isGolluxNoRespawnField(field.getId())) {
            return !hasSpawned(); // Gollux's part maps: once, so they can be cleared
        }
        var instance = field.getInstance();
        if (instance != null && instance.getInstanceHelper() != null
                && !instance.getInstanceHelper().canSpawnMapMobs(field)) {
            return false; // the instance spawns this map's monsters itself
        }
        if (instance != null && instance.getInstanceHelper() != null
                && !instance.getInstanceHelper().canRespawnMobs(field)) {
            return !hasSpawned(); // e.g. party quest stages where every monster must be defeated
        }
        switch (field.getId()) {
            case ORIGIN_OF_CLOCKTOWER_FIELD:
            case ORIGIN_OF_CLOCKTOWER_FIELD_2:
            case ORIGIN_OF_CLOCKTOWER_FIELD_3:
                // Papulatus field
                return canSpawnOnPapulatusField(field);
        }
        return true;
    }

    private boolean canSpawnOnPapulatusField(Field field) {
        return field.hasProperty("Phase") && ((int) field.getProperty("Phase")) != 1;
    }

    public long getNextPossibleSpawnTime() {
        return nextPossibleSpawnTime;
    }

    public void setNextPossibleSpawnTime(long nextPossibleSpawnTime) {
        this.nextPossibleSpawnTime = nextPossibleSpawnTime;
    }

    public boolean hasSpawned() {
        return hasSpawned;
    }

    public void setHasSpawned(boolean hasSpawned) {
        this.hasSpawned = hasSpawned;
    }

    @Override
    public String toString() {
        return "MobGen{" +
                "mob=" + (mob == null ? -1 : mob.getTemplateId()) +
                '}';
    }
}
