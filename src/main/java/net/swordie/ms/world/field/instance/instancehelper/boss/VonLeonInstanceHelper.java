package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.skill.MobSkill;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.loaders.containerclasses.MobSkillInfo;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;

import java.util.List;

/**
 * Von Leon's FSM skills (Teleport and Mob Consume are onlyFsm), timed as GMS Hard:
 * <ul>
 *     <li>every 9-18 s he homes in on the fighter who damaged him the most (Teleport level 1, with its notice);</li>
 *     <li>when his heal summon (Summon 210: ten of the monsters Mob Consume eats) appears he says he feels threatened
 *     and falls back to his spot (Teleport level 3);</li>
 *     <li>with enough of them near he eats them (Mob Consume).</li>
 * </ul>
 */
public class VonLeonInstanceHelper extends InstanceHelper {

    //FSM tick
    private static final int INITIALIZE_TIMER = 10000;
    private static final int TICK_TIMER = 2000;

    //Teleport
    private static final int TELEPORT_SKILL_ID = 170;
    private static final int HOMING_SLV = 1;
    private static final int FALL_BACK_SLV = 3;
    private static final int HOMING_MIN_MILLIS = 9000;
    private static final int HOMING_MAX_MILLIS = 18000;
    private static final String HOMING_NOTICE = "Von Leon is homing in on the fighter who damaged him the most.";
    private static final String THREATENED_NOTICE = "Von Leon feels threatened and summons monsters to heal himself.";

    //Mob Consume
    private static final int MOB_CONSUME_SKILL_ID = 105;
    private static final int EASY_MOB_CONSUME_SLV = 12;
    private static final int NORMAL_MOB_CONSUME_SLV = 1;
    private static final int HARD_MOB_CONSUME_SLV = 13;
    private static final int MIN_MOB_AMOUNT_TO_CONSUME = 5;
    private static final int HEAL_SUMMON_SIZE = 5; // this many new consumable monsters at once: his heal summon

    private long nextHoming;
    private int lastConsumable;

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        nextHoming = System.currentTimeMillis() + INITIALIZE_TIMER + Util.getRandom(HOMING_MIN_MILLIS, HOMING_MAX_MILLIS);
        addTimer(EventManager.addFixedRateEvent(this::vonLeonFsm, INITIALIZE_TIMER, TICK_TIMER));
    }

    private void vonLeonFsm() {
        Mob mob = getVonLeon();
        if (mob == null || mob.getMostDamageChar() == null) {
            return;
        }
        int slv = switch (mob.getTemplateId()) {
            case BossConstants.VON_LEON_EASY -> EASY_MOB_CONSUME_SLV;
            case BossConstants.VON_LEON_NORMAL -> NORMAL_MOB_CONSUME_SLV;
            case BossConstants.VON_LEON_HARD -> HARD_MOB_CONSUME_SLV;
            default -> 0;
        };
        MobSkillInfo consumeMobInfo = slv == 0 ? null : SkillData.getMobSkillInfoByIdAndLevel(MOB_CONSUME_SKILL_ID, slv);
        int consumable = consumeMobInfo == null || consumeMobInfo.getInts().isEmpty() ? -1 : consumeMobInfo.getInts().get(0);

        // his heal summon just came: he feels threatened and falls back
        int count = consumable < 0 ? 0 : (int) getField().getMobs().stream().filter(m -> m.getTemplateId() == consumable).count();
        boolean healSummon = count - lastConsumable >= HEAL_SUMMON_SIZE;
        lastConsumable = count;
        if (healSummon) {
            notice(mob, 2, THREATENED_NOTICE);
            MobSkill fallBack = mob.getSkillByMobSkillInfo(TELEPORT_SKILL_ID, FALL_BACK_SLV);
            if (fallBack != null) {
                mob.setForcedSkill(fallBack);
                return;
            }
        }

        long now = System.currentTimeMillis();
        if (now >= nextHoming && mob.getForcedSkill() == null) {
            nextHoming = now + Util.getRandom(HOMING_MIN_MILLIS, HOMING_MAX_MILLIS);
            MobSkill homing = mob.getSkillByMobSkillInfo(TELEPORT_SKILL_ID, HOMING_SLV);
            if (homing != null) {
                notice(mob, 1, HOMING_NOTICE);
                mob.setForcedSkill(homing);
                return;
            }
        }

        //MobConsume
        if (mob.getForcedSkill() == null && consumeMobInfo != null && consumeMobInfo.getLt() != null
                && consumeMobInfo.getRb() != null && mob.hasSkillOffCooldown(MOB_CONSUME_SKILL_ID, slv)) {
            //check if we have enemies of proper type near
            MobSkill consumeMob = mob.getSkillByMobSkillInfo(MOB_CONSUME_SKILL_ID, slv);
            Rect rect = new Rect(consumeMobInfo.getLt(), consumeMobInfo.getRb());
            rect.move(mob.getX(), mob.getY());
            List<Mob> mobsInRect = getField().getMobsInRect(rect);
            int amount = 0;
            for (Mob rectMob : mobsInRect) {
                if (rectMob.getTemplateId() == consumable) {
                    amount++;
                }
            }
            if (amount >= MIN_MOB_AMOUNT_TO_CONSUME && consumeMob != null) {
                mob.setForcedSkill(consumeMob);
            }
        }
    }

    /** GMS smart notice for Von Leon: type 0, category 2, value 1 (homing in) or 2 (threatened). */
    private void notice(Mob mob, int value, String text) {
        getField().broadcastPacket(FieldPacket.smartMobNotice(0, mob.getTemplateId(), 2, value, text));
    }

    private Mob getVonLeon() {
        return getField().getMobs().stream().filter(mob -> mob.getTemplateId() == BossConstants.VON_LEON_EASY ||
                mob.getTemplateId() == BossConstants.VON_LEON_NORMAL || mob.getTemplateId() == BossConstants.VON_LEON_HARD).findFirst().orElse(null);
    }
}
