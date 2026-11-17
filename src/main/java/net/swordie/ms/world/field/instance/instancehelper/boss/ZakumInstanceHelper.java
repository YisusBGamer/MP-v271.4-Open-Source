package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.packet.MobPool;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.constants.MobConstants;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.drop.Drop;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.skill.MobCastingBarSkill;
import net.swordie.ms.life.mob.skill.MobSkillID;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ZakumInstanceHelper extends InstanceHelper {

    private static final int ZAKUM_EYE = 4001017;
    private static final int ZAKUM_EYE_CHUNK = 4001796;
    private static final int EASY_ALTAR = 280030200;
    private static final int ZAKUM_BASE_REGEN_ID = 8800130;
    private static final String ZAKUM_SPAWNED_KEY = "ZakumSpawned";

    // GMS phases (CHANGE_PHASE + MOB_ATTACK_PRIORITY, attack indices of Mob.wz info/attack): 1 arms up, 2 Arm Clap
    // (flame up), 3 arms gone (summons + Area Explosion), 4 at 20% (burrow, then Ground Slam and stalactites)
    private static final int FINAL_PHASE_HP = 20;
    private volatile int bodyPhase = 0;

    private static Map<Integer, Collection<Integer>> priorities(int[]... lists) {
        Map<Integer, Collection<Integer>> map = new LinkedHashMap<>();
        for (int i = 0; i < lists.length; i++) {
            List<Integer> l = new java.util.ArrayList<>();
            for (int a : lists[i]) {
                l.add(a);
            }
            map.put(i, l);
        }
        return map;
    }

    private static void setPhase(Field field, Mob mob, int phase, Map<Integer, Collection<Integer>> priorities) {
        mob.changePhase(phase);
        field.broadcastPacket(MobPool.mobAttackPriority(mob, priorities));
    }

    private static Mob body(Field field) {
        return field.getMobs().stream().filter(m -> MobConstants.isZakumBody(m.getTemplateId())).findAny().orElse(null);
    }

    /** Body and every arm to phase 1 (arms up) or 2 (Arm Clap). */
    private void armPhase(Field field, int phase) {
        Mob b = body(field);
        if (b == null || bodyPhase >= 3) {
            return;
        }
        bodyPhase = phase;
        setPhase(field, b, phase, phase == 2 ? priorities(new int[]{0, 1}) : priorities(new int[0]));
        for (Mob arm : new java.util.ArrayList<>(field.getMobs())) {
            if (MobConstants.isZakumArm(arm.getTemplateId())) {
                setPhase(field, arm, phase, phase == 2 ? priorities(new int[]{0}) : priorities(new int[0]));
            }
        }
    }

    private static void notice(Field field, Mob b, int category, int value, String text) {
        field.broadcastPacket(FieldPacket.smartMobNotice(0, b.getTemplateId(), category, value, text));
    }

    @Override
    public void onMobDamaged(net.swordie.ms.client.character.Char attacker, Mob mob, long damage, long oldHp, long newHp) {
        super.onMobDamaged(attacker, mob, damage, oldHp, newHp);
        if (bodyPhase == 3 && MobConstants.isZakumBody(mob.getTemplateId()) && newHp > 0
                && newHp * 100 <= mob.getMaxHp() * FINAL_PHASE_HP) {
            bodyPhase = 4;
            Field field = mob.getField();
            setPhase(field, mob, 4, priorities(new int[]{8}, new int[]{6, 7}));
            field.broadcastPacket(MobPool.mobAttackTimeInfo(mob, List.of(new int[]{10, 8, -1})));
        }
    }

    @Override
    public void onItemDropped(Char chr, Item item, int quantity, Position position, Drop drop) {
        var field = chr.getField();

        // Easy takes the Eye of Fire Chunk, Normal and Chaos the Eye of Fire (Etc.wz/BossParty.img reqItem)
        int needed = field.getId() == EASY_ALTAR ? ZAKUM_EYE_CHUNK : ZAKUM_EYE;
        if (item == null || item.getItemId() != needed) {
            if (item != null && (item.getItemId() == ZAKUM_EYE || item.getItemId() == ZAKUM_EYE_CHUNK)) {
                chr.chatMessage(needed == ZAKUM_EYE_CHUNK ? "Easy Zakum is called with an Eye of Fire Chunk."
                        : "This Zakum is called with an Eye of Fire.");
            }
            return;
        }

        if (field.hasProperty(ZAKUM_SPAWNED_KEY)) {
            chr.chatMessage("It seems Zakum has already been summoned here.");
            return;
        }

        field.setProperty(ZAKUM_SPAWNED_KEY, true);
        drop.setBlockFromPickup(true);

        addTimer(EventManager.addEvent(() -> {
            field.removeDrop(drop.getObjectId(), 0, false, 0);
            spawnZakum(chr, field);
        }, 3, TimeUnit.SECONDS));

        super.onItemDropped(chr, item, quantity, position, drop);
    }

    @Override
    public void onMobSpawn(Field field, Mob mob) {
        if (MobConstants.isZakumFlame(mob.getTemplateId())) {
            toggleFootholdsAndArmClaps(field, true);
            armPhase(field, 2);
        } else if (MobConstants.isZakumArm(mob.getTemplateId()) && bodyPhase > 0 && bodyPhase < 3) {
            // a regenerated arm joins the current arm phase
            setPhase(field, mob, bodyPhase, bodyPhase == 2 ? priorities(new int[]{0}) : priorities(new int[0]));
        }
    }

    @Override
    public void onMobDeath(Field field, Mob mob) {
        var templateId = mob.getTemplateId();

        if (MobConstants.isZakumArm(templateId) && field.getMobs().stream().noneMatch(m -> MobConstants.isZakumArm(m.getTemplateId()))) {
            // Enable zakum body
            field.getMobs().stream()
                    .filter(m -> MobConstants.isZakumBody(m.getTemplateId()))
                    .findAny()
                    .ifPresent(body -> {
                        bodyPhase = 3;
                        setPhase(field, body, 3, priorities(new int[]{2, 3}));
                        notice(field, body, 1, 4, "Zakum is gathering his strength to destroy everything!");
                        body.setBlockAttack(false);
                        body.markActed(); // from now on he has to attack, or the stuck check restarts him
                        for (var skill : body.getSkills()) {
                            body.removeBlockedSkill(skill);
                        }
                        body.addBlockedSkill(body.getSkillByMobSkillInfo(201, 162)); // Fire
                        body.addBlockedSkill(body.getSkillByMobSkillInfo(170, 1)); // Teleport, only done via attack
                    });
            // Stop cast bars
            new HashSet<>(field.getMobs()).stream()
                    .filter(m -> MobConstants.isZakumArmRegen(m.getTemplateId()))
                    .forEach(m -> {
                        field.broadcastPacket(MobPool.castingBarSkill(m.getObjectId(), MobCastingBarSkill.end(false, 0, MobSkillID.CastingBar.getVal(), 13, 0)));
                        field.removeLife(m);
                    });
        } else if (MobConstants.isZakumFlame(templateId)) {
            toggleFootholdsAndArmClaps(field, false);
            armPhase(field, 1);
        } else if (MobConstants.isZakumBody(templateId)) {
            // the body can be hit while arms are up; when it falls, the rest of the fight goes with it
            new HashSet<>(field.getMobs()).stream()
                    .filter(m -> m != mob && (MobConstants.isZakumArm(m.getTemplateId())
                            || MobConstants.isZakumArmRegen(m.getTemplateId())
                            || MobConstants.isZakumFlame(m.getTemplateId())))
                    .forEach(m -> m.die(false));
        }

        super.onMobDeath(field, mob);
    }

    private void spawnZakum(Char chr, Field field) {
        var fieldId = field.getId();
        Mob mainBody = null;

        if (fieldId == BossConstants.EASY_ZAKUM_FIELD) {
            // Arms
            for (int i = 0; i < 8; i++) {
                field.spawnMob(8800023 + i, -54, 86, false, 0); // hardcode-ok
            }
            // Main Body
            mainBody = field.spawnMob(BossConstants.EASY_ZAKUM_BODY, -54, 86, false, 0);
        } else if (fieldId == BossConstants.NORMAL_ZAKUM_FIELD) {
            // Arms
            for (int i = 0; i < 8; i++) {
                field.spawnMob(8800003 + i, -54, 86, false, 0); // hardcode-ok
            }
            // Main Body
            mainBody = field.spawnMob(BossConstants.NORMAL_ZAKUM_BODY, -54, 86, false, 0);
        } else if (fieldId == BossConstants.HARD_ZAKUM_FIELD) {
            // Arms
            for (int i = 0; i < 8; i++) {
                // the v265 data caps Chaos Zakum at 2.1b; GMS (maplestorywiki.net): arms 10.5b each, body 84b
                field.spawnMob(8800103 + i, -54, 86, false, 10_500_000_000L); // hardcode-ok
            }
            // Main Body
            mainBody = field.spawnMob(BossConstants.HARD_ZAKUM_BODY, -54, 86, false, 84_000_000_000L);
        }

        if (mainBody != null) {
            mainBody.setBlockAttack(true);

            for (var skill : mainBody.getSkills()) {
                mainBody.addBlockedSkill(skill);
            }

            var templateId = mainBody.getTemplateId();
            if (templateId == BossConstants.HARD_ZAKUM_BODY) {
                mainBody.removeBlockedSkill(mainBody.getSkillByMobSkillInfo(201, 162)); // Fire
            }
        }

        toggleFootholdsAndArmClaps(field, false);
        if (mainBody != null) {
            armPhase(field, 1);
            notice(field, mainBody, 5, 1, "Zakum is raising his arm to smash down!");
        }

        // official (maplestorywiki.net, GMS): Easy 20 minutes and 50 deaths, Normal/Chaos 30 minutes and 5 deaths
        var sm = chr.getScriptManager();
        boolean easy = fieldId == BossConstants.EASY_ZAKUM_FIELD;
        sm.setInstanceTime(easy ? 20 * 60 : 30 * 60);
        sm.setDeathCount(easy ? 50 : 5);
    }

    private void toggleFootholdsAndArmClaps(Field field, boolean enable) {
        field.changeFoothold("zdc7", enable, -512, -7);
        field.changeFoothold("zdc8", enable, -436, -5);
        field.changeFoothold("zdc9", enable, -358, -8);

        field.changeFoothold("zdc16", enable, 363, -7);
        field.changeFoothold("zdc17", enable, 439, -5);
        field.changeFoothold("zdc18", enable, 517, -8);

        field.changeFoothold("zdc4", enable, -514, -102);
        field.changeFoothold("zdc5", enable, -439, -101);
        field.changeFoothold("zdc6", enable, -362, -99);

        field.changeFoothold("zdc13", enable, 384, -99);
        field.changeFoothold("zdc14", enable, 464, -102);
        field.changeFoothold("zdc15", enable, 546, -101);

        field.changeFoothold("zdc10", enable, 350, -189);
        field.changeFoothold("zdc11", enable, 426, -190);
        field.changeFoothold("zdc12", enable, 504, -187);

        field.changeFoothold("zdc1", enable, -464, -186);
        field.changeFoothold("zdc2", enable, -388, -187);
        field.changeFoothold("zdc3", enable, -310, -184);

        if (enable) {
            field.getMobs().stream()
                    .filter(m -> MobConstants.isZakumArm(m.getTemplateId()))
                    .forEach(m -> m.removeBlockedSkill(MobSkillID.Damage, 27));
        } else {
            field.getMobs().stream()
                    .filter(m -> MobConstants.isZakumArm(m.getTemplateId()))
                    .forEach(m -> m.addBlockedSkill(MobSkillID.Damage, 27));
        }
    }
}
