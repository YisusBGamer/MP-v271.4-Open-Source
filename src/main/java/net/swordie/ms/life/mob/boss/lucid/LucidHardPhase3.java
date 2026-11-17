package net.swordie.ms.life.mob.boss.lucid;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.connection.packet.MobPool;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.enums.ClockType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.jwzlib.WzFile;
import net.swordie.ms.jwzlib.WzObject;
import net.swordie.ms.jwzlib.WzProperty;
import net.swordie.ms.life.DeathType;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.skill.MobSkillID;
import net.swordie.ms.world.field.Clock;
import net.swordie.ms.world.field.ClockPacket;
import net.swordie.ms.world.field.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Point;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Hard Lucid's last phase (maplestorywiki.net, GMS): 45 seconds to take all of her phase 3 HP. If the party fails,
 * she goes back to phase 2 with 16% of its HP (and defeating that again brings phase 3 back).
 */
public class LucidHardPhase3 {

    private static final Logger log = LogManager.getLogger();
    private static final int SETTLE_SECONDS = 5; // she can't be hurt for the first 5 seconds
    private static final double FAIL_HP = 0.16;
    private static final double HEAL_BELOW = 0.10;
    private static final double HEAL_BY = 0.05;
    private static final Set<Integer> healed = ConcurrentHashMap.newKeySet();
    // Etc.wz/BossLucid.img Hard (Lucid/1) phase2 bossFuryResummonPosition: her spot after phase 2 (the fail resummon
    // goes there; her last-dream form is put there as well)
    private static volatile Point furyPos;

    private static Point furyPos() {
        if (furyPos == null) {
            Point pos = new Point(600, -500);
            try {
                WzObject<?, ?> root = new WzFile(ServerConstants.WZ_DIR + "/Etc.wz").getChild("BossLucid.img");
                WzObject<?, ?> node = root == null ? null
                        : root.getChildByPath("Lucid/1/phase2/bossFuryResummonPosition");
                if (node instanceof WzProperty<?> prop && prop.getValue() instanceof Point p) {
                    pos = p;
                } else {
                    log.warn("Etc.wz BossLucid.img has no Hard bossFuryResummonPosition: Lucid's phase 3 uses its old spot");
                }
            } catch (RuntimeException e) {
                log.warn("Could not read Etc.wz BossLucid.img", e);
            }
            furyPos = pos;
        }
        return furyPos;
    }

    public static void start(Field field) {
        Point pos = furyPos();
        Mob p3 = field.spawnMob(BossConstants.LUCID_HARD_TEMPLATE_ID_3, pos.x, pos.y, false,
                BossConstants.LUCID_HARD_HP_3);
        for (Char c : field.getChars()) {
            c.chatMessage("Lucid sinks into her last dream! Defeat her within "
                    + BossConstants.LUCID_HARD_PHASE_3_TIME + " seconds!");
        }
        if (p3 == null) {
            return;
        }
        var o = new Option();
        o.nOption = 1;
        o.rOption = MobSkillID.Invincible.getVal();
        o.slv = 1;
        o.tOption = SETTLE_SECONDS;
        p3.getTemporaryStat().addMobSkillOptionsAndBroadCast(MobStat.Invincible, o);
        int objectId = p3.getObjectId();
        // the run clock gives way to the phase's own countdown
        new Clock(ClockType.SecondsClock, field, BossConstants.LUCID_HARD_PHASE_3_TIME + SETTLE_SECONDS);
        EventManager.addEvent(() -> fail(field, objectId),
                (BossConstants.LUCID_HARD_PHASE_3_TIME + SETTLE_SECONDS) * 1000L);
    }

    /** Once, when she drops to 10%, she heals 5% of her HP. */
    public static void onDamaged(Mob mob, long newHp) {
        if (newHp <= 0 || newHp > mob.getMaxHp() * HEAL_BELOW || !healed.add(mob.getObjectId())) {
            return;
        }
        long healTo = Math.min(mob.getMaxHp(), newHp + (long) (mob.getMaxHp() * HEAL_BY));
        mob.setHp(healTo);
        mob.getField().broadcastPacket(MobPool.damaged(mob.getObjectId(), mob.getTemplateId(), healTo - newHp,
                (byte) 0, healTo, mob.getMaxHp()));
    }

    /** Puts the instance's remaining time back on the clock once phase 3 is over. */
    private static void restoreRunClock(Field field) {
        if (field.getClock() != null) {
            field.getClock().removeClock();
        }
        var instance = field.getChars().stream().map(Char::getInstance).filter(i -> i != null).findFirst().orElse(null);
        if (instance != null) {
            int left = (int) (instance.getRemainingTime() / 1000);
            if (left > 0) {
                field.broadcastPacket(FieldPacket.clock(ClockPacket.secondsClock(left)));
            }
        }
    }

    private static void fail(Field field, int objectId) {
        healed.remove(objectId);
        Life life = field.getLifeByObjectID(objectId);
        if (!(life instanceof Mob p3) || p3.isDead() || field.getChars().isEmpty()) {
            return; // defeated in time (or everyone left)
        }
        restoreRunClock(field);
        field.broadcastPacket(MobPool.leaveField(objectId, DeathType.ANIMATION_DEATH));
        field.removeLife(objectId);
        Point pos = furyPos();
        Mob p2 = field.spawnMob(BossConstants.LUCID_HARD_TEMPLATE_ID_2, pos.x, pos.y, false,
                BossConstants.LUCID_HARD_HP_2);
        if (p2 != null) {
            p2.setHp((long) (BossConstants.LUCID_HARD_HP_2 * FAIL_HP));
        }
        for (Char c : field.getChars()) {
            c.chatMessage("Lucid wakes from her last dream and fights on!");
        }
    }
}
