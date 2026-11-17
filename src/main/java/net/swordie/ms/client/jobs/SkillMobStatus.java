package net.swordie.ms.client.jobs;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.debuff.DebuffInfo;
import net.swordie.ms.client.character.skills.debuff.DebuffUtil;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.MobAttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;

import java.util.List;

import static net.swordie.ms.client.character.skills.SkillStat.prop;
import static net.swordie.ms.client.character.skills.SkillStat.time;

/**
 * The status an attack's data puts on the monsters it hits (info/mes with prop% and time sec): stun, freeze and seal.
 * Runs after the job handler, and only on monsters that don't already have that status (a handler that applies it
 * itself wins). Bosses are left out, as the per-skill code does for these.
 */
public final class SkillMobStatus {

    private SkillMobStatus() {
    }

    private static MobStat statOf(String mes) {
        return switch (mes) {
            case "stun" -> MobStat.Stun;
            case "freeze" -> MobStat.Freeze;
            case "seal" -> MobStat.Seal;
            default -> null;
        };
    }

    public static void applyFromData(Char chr, AttackInfo attackInfo, SkillInfo si) {
        if (si == null || si.getMes().isEmpty() || attackInfo.mobAttackInfo.isEmpty()) {
            return;
        }
        MobStat stat = statOf(si.getMes().trim());
        if (stat == null) {
            return;
        }
        int slv = attackInfo.slv > 0 ? attackInfo.slv : chr.getSkillLevel(si.getSkillId());
        if (slv <= 0) {
            return;
        }
        int chance = si.getValue(prop, slv);
        int duration = si.getValue(time, slv);
        if (chance <= 0 || duration <= 0) {
            return;
        }
        for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
            Mob mob = mai.mob;
            if (mob == null || mai.mobDies || mai.totalDamage <= 0 || mob.getHp() <= 0
                    || mob.getTemporaryStat().hasCurrentMobStat(stat)) {
                continue;
            }
            Option o = new Option(1, si.getSkillId(), duration);
            DebuffUtil.applyDebuffOnMobs(chr, stat, o, List.of(mob.getObjectId()), new DebuffInfo(true, chance));
        }
    }
}
