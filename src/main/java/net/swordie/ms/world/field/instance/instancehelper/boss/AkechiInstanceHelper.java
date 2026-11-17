package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.fieldeffect.FieldEffect;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Akechi Mitsuhide (maplestorywiki.net, GMS), Normal: level 200, 30 minutes, death count 5.
 * <p>
 * Map flow as GMS v271 runs it: cut 874004500 (letterbox Sengoku3/screen/0) -> phase 1 on 874004550 (9601622) ->
 * cut 874004600 (screen/1) -> phase 2 on 874004650 (9601623, the boss with the rewards). The timer and deaths carry
 * over. The two phases take about the same time on GMS, so each holds half of {@link BossConstants#AKECHI_HP}.
 * <ul>
 *     <li>Cruel Brand: every hit a player takes adds a stack, every 5 seconds without one removes one; at 30 Mitsuhide
 *     answers with a full-map strike (100% HP) and the stacks reset.</li>
 *     <li>Provoke every 60 seconds: the party has 30 seconds to deal 20 billion damage. If they do, his Frenzy rises;
 *     if not, Overwhelm: the Swordsman's Echoes slam down on everyone.</li>
 *     <li>Each player death takes 100 seconds off the timer, and he counterattacks (3 strikes of 20% HP).</li>
 * </ul>
 */
public class AkechiInstanceHelper extends BossMechanicsHelper {

    private static final int BRAND_MAX = 30;
    private static final long PROVOKE_TARGET = 20_000_000_000L;
    private static final long CUT_MS = 7_000;
    private static final long SPAWN_MS = 2_000;

    private final Map<Integer, Integer> brands = new ConcurrentHashMap<>();
    private final Map<Integer, Long> lastHit = new ConcurrentHashMap<>();
    private volatile boolean provoking = false;
    private volatile long provokeDamage = 0;
    private int frenzy = 1;
    private volatile int fieldId = BossConstants.AKECHI_P1_FIELD;

    @Override
    protected Field getField() {
        return getInstance() == null ? null : getInstance().getField(fieldId);
    }

    @Override
    protected Set<Integer> getBossIds() {
        return Set.of(BossConstants.AKECHI);
    }

    @Override
    protected void startMechanics() {
        cutThenFight(BossConstants.AKECHI_FIELD, "Map/EffectPL.img/Sengoku3/screen/0",
                BossConstants.AKECHI_P1_FIELD, BossConstants.AKECHI_P1);
        every(1000, this::brandTick);
        every(60_000, this::provoke);
    }

    /** Letterbox on the cut map, then the fight map and its Akechi. */
    private void cutThenFight(int cutField, String effect, int fightField, int mobId) {
        Field cut = getInstance() == null ? null : getInstance().getField(cutField);
        if (cut != null) {
            cut.broadcastPacket(FieldPacket.fieldEffect(FieldEffect.getFieldEffectFromWz(effect, 0)));
        }
        later(CUT_MS, () -> {
            fieldId = fightField;
            getInstance().warp(fightField);
            later(SPAWN_MS, () -> {
                Field f = getField();
                if (f != null && f.getMobs().stream().noneMatch(m -> m.getTemplateId() == mobId)) {
                    f.spawnMob(mobId, 250, 306, false, BossConstants.AKECHI_HP / 2);
                }
            });
        });
    }

    @Override
    public void onMobDeath(Field field, Mob mob) {
        super.onMobDeath(field, mob);
        if (mob.getTemplateId() == BossConstants.AKECHI_P1 && field.getId() == BossConstants.AKECHI_P1_FIELD) {
            later(3_000, () -> {
                fieldId = BossConstants.AKECHI_CUT_2;
                getInstance().warp(BossConstants.AKECHI_CUT_2);
                later(1_000, () -> cutThenFight(BossConstants.AKECHI_CUT_2, "Map/EffectPL.img/Sengoku3/screen/1",
                        BossConstants.AKECHI_FIELD_2, BossConstants.AKECHI));
            });
        }
    }

    private void brandTick() {
        long now = System.currentTimeMillis();
        for (Char chr : getField().getChars()) {
            int id = chr.getId();
            if (brands.getOrDefault(id, 0) > 0 && now - lastHit.getOrDefault(id, 0L) >= 5000) {
                brands.merge(id, -1, Integer::sum);
                lastHit.put(id, now);
            }
        }
    }

    @Override
    public void onChrHit(Char chr, int dmgAmount) {
        super.onChrHit(chr, dmgAmount);
        if (dmgAmount <= 0 || isBossDead()) {
            return;
        }
        lastHit.put(chr.getId(), System.currentTimeMillis());
        int stacks = brands.merge(chr.getId(), 1, Integer::sum);
        if (stacks >= BRAND_MAX) {
            brands.put(chr.getId(), 0);
            notice("Cruel Brand! Akechi Mitsuhide's blade sweeps the whole altar!");
            for (Char c : getLivingChars()) {
                c.damagePerc(100);
            }
        }
    }

    private void provoke() {
        provoking = true;
        provokeDamage = 0;
        notice("Akechi Mitsuhide provokes you! Deal 20 billion damage within 30 seconds!");
        markAll(3);
        later(30_000, () -> {
            provoking = false;
            if (provokeDamage >= PROVOKE_TARGET) {
                frenzy = Math.min(20, frenzy + 1);
                notice("\"Hm! Perhaps there's some fight in you after all!\" (Frenzy " + frenzy + ")");
            } else {
                notice("Overwhelm! The Swordsman's Echoes slam down!");
                markAll(4);
                for (Char c : getLivingChars()) {
                    c.damagePerc(30);
                }
            }
        });
    }

    /** MitsuhideDebuff from MobSkill 261: level 3 Provoke, level 4 Overwhelm (n = x, extra = y, time). */
    private void markAll(int level) {
        var msi = net.swordie.ms.loaders.SkillData.getMobSkillInfoByIdAndLevel(261, level);
        if (msi == null) {
            return;
        }
        for (Char c : getLivingChars()) {
            var o = new net.swordie.ms.client.character.skills.Option(261);
            o.slv = level;
            o.nOption = msi.getSkillStatIntValue(net.swordie.ms.life.mob.skill.MobSkillStat.x);
            o.xOption = msi.getSkillStatIntValue(net.swordie.ms.life.mob.skill.MobSkillStat.y);
            o.tOption = Math.max(1, msi.getSkillStatIntValue(net.swordie.ms.life.mob.skill.MobSkillStat.time));
            var tsm = c.getTemporaryStatManager();
            tsm.putCharacterStatValueFromMobSkill(
                    net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.MitsuhideDebuff, o);
            tsm.sendSetStatPacket();
        }
    }

    @Override
    public void onMobDamaged(Char attacker, Mob mob, long damage, long oldHp, long newHp) {
        super.onMobDamaged(attacker, mob, damage, oldHp, newHp);
        if (provoking && (mob.getTemplateId() == BossConstants.AKECHI || mob.getTemplateId() == BossConstants.AKECHI_P1)) {
            provokeDamage += damage;
        }
    }

    @Override
    public void onChrDeath(Char chr) {
        super.onChrDeath(chr);
        if (isBossDead() || getInstance() == null) {
            return;
        }
        long remaining = getInstance().getRemainingTime() / 1000 - 100;
        if (remaining > 5) {
            getInstance().setTimeout((int) remaining);
        }
        notice("Akechi Mitsuhide counterattacks! (-100 seconds)");
        for (int i = 0; i < 3; i++) {
            later(400L * (i + 1), () -> {
                for (Char c : getLivingChars()) {
                    c.damagePerc(20);
                }
            });
        }
    }

    @Override
    protected void onBossDeath(Mob boss) {
        notice("Akechi Mitsuhide has been defeated! You will leave the altar in 1 minute.");
        if (getInstance() != null) {
            getInstance().setTimeout(60);
        }
    }
}
