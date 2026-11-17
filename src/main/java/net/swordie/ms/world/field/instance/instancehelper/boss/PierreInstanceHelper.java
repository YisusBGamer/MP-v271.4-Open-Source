package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.connection.packet.MobPool;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.connection.packet.field.LucidFieldPacket;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.DeathType;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.skill.MobSkillID;
import net.swordie.ms.life.mob.skill.MobSkillStat;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.loaders.containerclasses.MobSkillInfo;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.bosses.gollux.FallingCatcher;
import net.swordie.ms.world.field.fieldeffect.FieldEffect;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;


public class PierreInstanceHelper extends InstanceHelper {
    private static final Logger log = LogManager.getLogger(PierreInstanceHelper.class);

    //Dropping Hats
    private static final int HAT_DROP_TIMER = 15000; //ms
    private static final int MOST_LEFT_X = -430;
    private static final int FIELD_Y = 551;
    private static final int SPACE_BETWEEN_HATS = 200;
    private static final int HAT_AMOUNT = 10;
    private static final String HAT_CATCHER_ID = "CapEffect";
    private FallingCatcher hats = null;
    private ScheduledFuture hatDropTimer = null;

    //Player Hats
    private static final int HAT_ATTACH_TIMER = 20000; //ms
    private ScheduledFuture hatAttachTimer = null;

    private boolean startedTimers = false;
    private boolean isChaos = false;
    private boolean isSplit = false;
    private boolean isHalfKilled = false;

    private LocalDateTime timeSinceLastTransform = null;
    private static final int TRANSFORM_COOLDOWN = 20;

    // GMS after a lone half dies: the survivor swaps colour ~20 s later and turns back into purple Pierre (keeping
    // its HP) ~50 s after the death; he splits again at 31%. The dead half doesn't come back.
    private static final int LONE_SWAP_DELAY = 20000;
    private static final int LONE_MERGE_DELAY = 50000;
    private static final int SPLIT_SWAP_COOLDOWN = 20; // the split halves swap colours every 20 s
    private ScheduledFuture loneSwapTimer = null;

    private ScheduledFuture transformAttemptTimer = null;
    private static final int TRANSFORM_ATTEMPT_TIMER = 50000;

    private static final Position BOX_POS = new Position(496, 551); // GMS spots (Chaos / Normal), summon effect 1
    private static final int NORMAL_BOX_X = 489;
    private static final int SPLIT_HALF_GAP = 27;
    private static final int SPLIT_MIN_X = -430;
    private static final int SPLIT_MAX_X = 1500;
    private ScheduledFuture splitBothKilledTimer = null;
    private boolean cleared = false;

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        timeSinceLastTransform = LocalDateTime.now().minusSeconds(300);
    }


    @Override
    public void onMobSpawn(Field field, Mob mob) {
        super.onMobSpawn(field, mob);
        if (mob.getTemplateId() == BossConstants.PIERRE_NORMAL_PURPLE && startedTimers == false) {
            startedTimers = true;
            hatDropTimer = addTimer(EventManager.addFixedRateEvent(this::createDroppingHats, 5000, HAT_DROP_TIMER));
            hatAttachTimer = addTimer(EventManager.addFixedRateEvent(this::attachHats, 5000, HAT_ATTACH_TIMER));
            transformAttemptTimer = addTimer(EventManager.addFixedRateEvent(this::transformTimer, 5000, TRANSFORM_ATTEMPT_TIMER));

        }
        if (mob.getTemplateId() == BossConstants.PIERRE_CHAOS_PURPLE && startedTimers == false) {
            startedTimers = true;
            hatDropTimer = addTimer(EventManager.addFixedRateEvent(this::createDroppingHats, 5000, HAT_DROP_TIMER));
            hatAttachTimer = addTimer(EventManager.addFixedRateEvent(this::attachHats, 5000, HAT_ATTACH_TIMER));
            transformAttemptTimer = addTimer(EventManager.addFixedRateEvent(this::transformTimer, 5000, TRANSFORM_ATTEMPT_TIMER));
            isChaos = true;
        }

        if (mob.getTemplateId() == BossConstants.PIERRE_NORMAL_BOX || mob.getTemplateId() == BossConstants.PIERRE_CHAOS_BOX) {
            if (hatDropTimer != null) {
                EventManager.stopTimer(hatDropTimer);
            }
            if (hatAttachTimer != null) {
                EventManager.stopTimer(hatAttachTimer);
            }
            if (transformAttemptTimer != null) {
                EventManager.stopTimer(transformAttemptTimer);
            }
        }
    }

    @Override
    public void onMobDeath(Field field, Mob mob) {
        super.onMobDeath(field, mob);

        if (isSplit) {
            if (mob.getTemplateId() == BossConstants.PIERRE_CHAOS_RED || mob.getTemplateId() == BossConstants.PIERRE_CHAOS_BLUE) {
                if (isHalfKilled && !cleared) {
                    cleared = true;
                    mob.getField().spawnMob(BossConstants.PIERRE_CHAOS_BOX, BOX_POS.getX(), BOX_POS.getY(), false, 0, 1);
                    if (splitBothKilledTimer != null) {
                        EventManager.stopTimer(splitBothKilledTimer);
                    }
                    for (Char chr : field.getChars()) {
                        QuestManager qm = chr.getQuestManager();
                        if (qm.hasQuestInProgress(30010)) {
                            qm.completeQuest(30010); //[Root Abyss] Defeat the First Seal Guardian
                        }
                    }

                } else {
                    isHalfKilled = true;
                    if (splitBothKilledTimer != null) {
                        EventManager.stopTimer(splitBothKilledTimer);
                    }
                    if (loneSwapTimer != null) {
                        EventManager.stopTimer(loneSwapTimer);
                    }
                    loneSwapTimer = addTimer(EventManager.addEvent(this::swapLoneHalf, LONE_SWAP_DELAY));
                    splitBothKilledTimer = addTimer(EventManager.addEvent(this::isSplitBothKilled, LONE_MERGE_DELAY));
                }
            }
        }

        //need to check if people burst, then immediately spawn chest
        if (mob.getHp() <= 0 && !isSplit) {
            if (mob.getTemplateId() == BossConstants.PIERRE_CHAOS_RED || mob.getTemplateId() == BossConstants.PIERRE_CHAOS_BLUE || mob.getTemplateId() == BossConstants.PIERRE_CHAOS_PURPLE) {
                cleared = true;
                mob.getField().spawnMob(BossConstants.PIERRE_CHAOS_BOX, BOX_POS.getX(), BOX_POS.getY(), false, 0, 1);
                if (splitBothKilledTimer != null) {
                    EventManager.stopTimer(splitBothKilledTimer);
                }
                for (Char chr : field.getChars()) {
                    QuestManager qm = chr.getQuestManager();
                    if (qm.hasQuestInProgress(30010)) {
                        qm.completeQuest(30010); //[Root Abyss] Defeat the First Seal Guardian
                    }
                }
            }
        }

        if (mob.getTemplateId() == BossConstants.PIERRE_NORMAL_PURPLE || mob.getTemplateId() == BossConstants.PIERRE_NORMAL_RED || mob.getTemplateId() == BossConstants.PIERRE_NORMAL_BLUE) {
            mob.getField().spawnMob(BossConstants.PIERRE_NORMAL_BOX, NORMAL_BOX_X, BOX_POS.getY(), false, 0, 1);
        }
    }


    @Override
    public void onMobDamaged(Char attacker, Mob mob, long damage, long oldHp, long newHp) {
        super.onMobDamaged(attacker, mob, damage, oldHp, newHp);

        if (newHp <= 0) return;

        if ((mob.getTemplateId() == BossConstants.PIERRE_CHAOS_BLUE || mob.getTemplateId() == BossConstants.PIERRE_CHAOS_RED || mob.getTemplateId() == BossConstants.PIERRE_CHAOS_PURPLE ||
                mob.getTemplateId() == BossConstants.PIERRE_NORMAL_PURPLE || mob.getTemplateId() == BossConstants.PIERRE_NORMAL_RED || mob.getTemplateId() == BossConstants.PIERRE_NORMAL_BLUE)) {

            if (mob.getHpPerc() <= 31 && !isSplit && isChaos) {
                if(!mob.getTemporaryStat().hasCurrentMobStat(MobStat.Stun)) {
                    isSplit = true;

                    // GMS: both halves come out next to where Pierre stood, a few dozen px apart
                    int x = Math.max(SPLIT_MIN_X + SPLIT_HALF_GAP, Math.min(SPLIT_MAX_X - SPLIT_HALF_GAP, mob.getPosition().getX()));
                    Mob red = spawnClone(BossConstants.PIERRE_CHAOS_RED, new Position(x - SPLIT_HALF_GAP, FIELD_Y), 0.3);
                    Mob blue = spawnClone(BossConstants.PIERRE_CHAOS_BLUE, new Position(x + SPLIT_HALF_GAP, FIELD_Y), 0.3);
                    //Kill of original
                    removeMob(mob);
                    // the halves' bars go up after the purple one is emptied
                    for (Mob half : new Mob[]{red, blue}) {
                        mob.getField().broadcastPacket(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(half)));
                    }
                    timeSinceLastTransform = LocalDateTime.now();
                    return;
                }
            }

            long sinceTransform = ChronoUnit.SECONDS.between(timeSinceLastTransform, LocalDateTime.now());
            if (!isSplit) {
                if (sinceTransform > TRANSFORM_COOLDOWN && mob.getHpPerc() < 70
                        && !mob.getTemporaryStat().hasCurrentMobStat(MobStat.Stun)) {
                    attemptTransform(mob);
                }
            } else if (isChaos && !isHalfKilled && sinceTransform > SPLIT_SWAP_COOLDOWN) {
                swapSplitColours();
            }
        }
    }

    /**
     * Both halves change colour at once (red becomes blue and blue red, each keeping its HP and place). The
     * one-of-each check in attemptTransform always found the other half, so they never swapped and blue chased forever.
     */
    private synchronized void swapSplitColours() {
        if (!isSplit || isHalfKilled || cleared) return;
        Field field = getPierreField();
        if (field == null) return;
        Mob red = (Mob) field.getLifeByTemplateId(BossConstants.PIERRE_CHAOS_RED);
        Mob blue = (Mob) field.getLifeByTemplateId(BossConstants.PIERRE_CHAOS_BLUE);
        if (red == null || blue == null || red.getHp() <= 0 || blue.getHp() <= 0) return;
        transformMob(BossConstants.PIERRE_CHAOS_BLUE, red);
        transformMob(BossConstants.PIERRE_CHAOS_RED, blue);
    }

    private Field getPierreField() {
        return getInstance().getField(isChaos ? BossConstants.PIERRE_CHAOS_FIELDID : BossConstants.PIERRE_NORMAL_FIELDID);
    }

    public void transformTimer(){
        if (isSplit) {
            if (isChaos && !isHalfKilled && ChronoUnit.SECONDS.between(timeSinceLastTransform, LocalDateTime.now()) > SPLIT_SWAP_COOLDOWN) {
                swapSplitColours();
            }
            return;
        }

        Mob mob = getUnsplitPierre();
        if (mob == null) return;
        if (ChronoUnit.SECONDS.between(timeSinceLastTransform, LocalDateTime.now()) > TRANSFORM_COOLDOWN) {
            if (mob.getHpPerc() < 70 && !isSplit) {
                //check if not bound
                if (!mob.getTemporaryStat().hasCurrentMobStat(MobStat.Freeze)) {
                    attemptTransform(mob);
                }
            }
        }
    }

    private synchronized void swapLoneHalf() {
        Mob survivor = loneSurvivor();
        if (survivor != null) {
            transformMob(survivor.getTemplateId() == BossConstants.PIERRE_CHAOS_RED
                    ? BossConstants.PIERRE_CHAOS_BLUE : BossConstants.PIERRE_CHAOS_RED, survivor);
        }
    }

    private Mob loneSurvivor() {
        if (cleared || !isSplit || !isHalfKilled) {
            return null;
        }
        Field field = getPierreField();
        Mob red = (Mob) field.getLifeByTemplateId(BossConstants.PIERRE_CHAOS_RED);
        Mob half = red != null ? red : (Mob) field.getLifeByTemplateId(BossConstants.PIERRE_CHAOS_BLUE);
        return half == null || half.getHp() <= 0 ? null : half;
    }

    /** The lone half wasn't finished in time: it becomes purple Pierre again with its HP. */
    public synchronized void isSplitBothKilled() {
        Mob survivor = loneSurvivor();
        if (survivor == null) {
            return;
        }
        isSplit = false;
        isHalfKilled = false;
        transformMob(BossConstants.PIERRE_CHAOS_PURPLE, survivor);
    }

    private void attemptTransform(Mob mob) {
        List<Integer> transformTargets = getTransformTargets(mob.getTemplateId(), mob.getHpPerc());
        if (transformTargets.isEmpty()) return;
        int transformTarget = Util.getRandomFromCollection(transformTargets);
        if (mob.getField().getLifeByTemplateId(transformTarget) != null)
            return; //Already a mob of type for this target. Do this to ensure there's no duplicates
        transformMob(transformTarget, mob);
    }

    private Mob spawnClone(int cloneTemplateID, Position pos, double hpRate) {
        Field field = getInstance().getField(isChaos ? BossConstants.PIERRE_CHAOS_FIELDID : BossConstants.PIERRE_NORMAL_FIELDID);
        Mob transformedMob = field.spawnMob(cloneTemplateID, pos);
        transformedMob.setHp((long) (transformedMob.getMaxHp() * hpRate));
        field.broadcastPacket(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(transformedMob)));
        return transformedMob;
    }

    private void removeMob(Mob mob) {
        Field field = getInstance().getField(isChaos ? BossConstants.PIERRE_CHAOS_FIELDID : BossConstants.PIERRE_NORMAL_FIELDID);
        //Kill off current mob
        mob.setBroadcastLeaveField(true);
        field.removeLife(mob.getObjectId());
        // the client keeps a boss bar per template: empty the old one so it doesn't stay on screen
        mob.setHp(0);
        field.broadcastPacket(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(mob)));
        mob.getTemporaryStat().removeAllMobStats(false);
        field.broadcastPacket(MobPool.leaveField(mob.getObjectId(), DeathType.EXPLODE));
        mob.clear();
    }

    private void transformMob(int transformTarget, Mob originalMob) {
        Field field = getInstance().getField(isChaos ? BossConstants.PIERRE_CHAOS_FIELDID : BossConstants.PIERRE_NORMAL_FIELDID);
        Mob transformedMob = field.spawnMob(transformTarget, originalMob.getPosition());
        transformedMob.setHp(originalMob.getHp());
        transformedMob.setDamageDone(originalMob.getDamageDone());
        field.broadcastPacket(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(transformedMob)));
        transformedMob.displayAggroRank();

        timeSinceLastTransform = LocalDateTime.now();
        removeMob(originalMob);
        field.broadcastPacket(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(transformedMob)));


        if (transformedMob.getTemplateId() == BossConstants.PIERRE_CHAOS_BLUE) {
            setChasingEffect(transformedMob);
        }
    }

    private void setChasingEffect(Mob mob) {
        Char controller = mob.getField().getLifeToControllers().getOrDefault(mob, null);
        if (controller != null) {
            mob.getField().broadcastPacket(FieldPacket.setChaseEffect(controller, mob.getObjectId(), true));
        }
    }


    private void attachHats() {
        Field field = getInstance().getField(isChaos ? BossConstants.PIERRE_CHAOS_FIELDID : BossConstants.PIERRE_NORMAL_FIELDID);
        for (Char chr : field.getChars()) {
            try {
                attachHat(chr);
            } catch (Exception e) {
                // one bad character must not stop the repeating hat task
                log.error("Pierre hat for " + chr.getName() + " failed", e);
            }
        }
    }

    private void attachHat(Char chr) {
        if (!chr.isDead()) {
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            int oldHat = tsm.hasStat(CharacterTemporaryStat.CapDebuff) ? tsm.getOption(CharacterTemporaryStat.CapDebuff).rOption & 0xFFFF : 0;
            tsm.removeStat(CharacterTemporaryStat.CapDebuff);

            // a hat always changes colour on the next round; a bare head rolls (10% stays bare before the split)
            int val;
            if (oldHat == MobSkillID.CapDebuffRed.getVal()) {
                val = 10;
            } else if (oldHat == MobSkillID.CapDebuffBlue.getVal()) {
                val = 99;
            } else {
                val = isSplit ? Util.getRandom(10, 99) : Util.getRandom(99);
            }
            //45% chance for blue hat
            if (val >= 55) {
                MobSkillInfo msi = SkillData.getMobSkillInfoByIdAndLevel(MobSkillID.CapDebuffRed.getVal(), 1);
                Option o = new Option(MobSkillID.CapDebuffRed.getVal());
                o.slv = 1;
                o.nOption = msi.getSkillStatIntValue(MobSkillStat.x);
                o.tOption = msi.getSkillStatIntValue(MobSkillStat.time);
                o.yOption = MobSkillID.CapDebuffRed.getVal(); //Used in damage handle to check which hat player has
                tsm.putCharacterStatValueFromMobSkill(CharacterTemporaryStat.CapDebuff, o);
                tsm.sendSetStatPacket();
            }
            //45% chance for red hat
            else if (val >= 10) {
                MobSkillInfo msi = SkillData.getMobSkillInfoByIdAndLevel(MobSkillID.CapDebuffBlue.getVal(), 1);
                Option o = new Option(MobSkillID.CapDebuffBlue.getVal());
                o.slv = 1;
                o.nOption = msi.getSkillStatIntValue(MobSkillStat.x);
                o.tOption = msi.getSkillStatIntValue(MobSkillStat.time);
                o.yOption = MobSkillID.CapDebuffBlue.getVal(); //Used in damage handle to check which hat player has
                tsm.putCharacterStatValueFromMobSkill(CharacterTemporaryStat.CapDebuff, o);
                tsm.sendSetStatPacket();
            }
            //10% chance for no hat
        }
    }


    private void createDroppingHats() {
        List<Position> positions = new ArrayList<>();
        for (int i = 0; i < HAT_AMOUNT; i++) {
            if (Util.succeedProp(BossConstants.PIERRE_HAT_SPAWN_CHANCE)) {
                positions.add(new Position(MOST_LEFT_X + SPACE_BETWEEN_HATS * i, FIELD_Y));
            }
        }
        hats = new FallingCatcher(HAT_CATCHER_ID, 50, positions);
        Field field = getInstance().getField(isChaos ? BossConstants.PIERRE_CHAOS_FIELDID : BossConstants.PIERRE_NORMAL_FIELDID);
        field.broadcastPacket(LucidFieldPacket.createFallingCatcher(hats));
    }

    private List<Integer> getTransformTargets(int templateID, double hpPerc) {
        List<Integer> transformTargets = new ArrayList<>();
        switch (templateID) {
            case BossConstants.PIERRE_CHAOS_PURPLE -> {
                transformTargets.add(BossConstants.PIERRE_CHAOS_RED);
                transformTargets.add(BossConstants.PIERRE_CHAOS_BLUE);
            }
            case BossConstants.PIERRE_CHAOS_RED -> {
                if (!isSplit) {
                    transformTargets.add(BossConstants.PIERRE_CHAOS_PURPLE);
                } else {
                    transformTargets.add(BossConstants.PIERRE_CHAOS_BLUE);
                }
            }
            case BossConstants.PIERRE_CHAOS_BLUE -> {
                if (!isSplit) {
                    transformTargets.add(BossConstants.PIERRE_CHAOS_PURPLE);
                } else {
                    transformTargets.add(BossConstants.PIERRE_CHAOS_RED);
                }
            }
            case BossConstants.PIERRE_NORMAL_PURPLE -> {
                transformTargets.add(BossConstants.PIERRE_NORMAL_RED);
                transformTargets.add(BossConstants.PIERRE_NORMAL_BLUE);
            }
            case BossConstants.PIERRE_NORMAL_RED, BossConstants.PIERRE_NORMAL_BLUE ->
                    transformTargets.add(BossConstants.PIERRE_NORMAL_PURPLE);
        }
        return transformTargets;
    }

    private Mob getUnsplitPierre() {
        // only this instance's own Pierre map (asking for the other one made an empty copy of it)
        Field field = getPierreField();
        if (field == null) return null;
        return field.getMobs().stream().filter(mob -> mob.getTemplateId() == BossConstants.PIERRE_NORMAL_BLUE || mob.getTemplateId() == BossConstants.PIERRE_NORMAL_RED || mob.getTemplateId() == BossConstants.PIERRE_NORMAL_PURPLE
                || mob.getTemplateId() == BossConstants.PIERRE_CHAOS_BLUE || mob.getTemplateId() == BossConstants.PIERRE_CHAOS_RED || mob.getTemplateId() == BossConstants.PIERRE_CHAOS_PURPLE).findFirst().orElse(null);
    }
}
