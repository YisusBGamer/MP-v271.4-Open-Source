package net.swordie.ms.life.mob.boss.papulatus;

import net.swordie.ms.life.mob.Mob;

/**
 * Chaos Papulatus's CHANGE_PHASE numbers as GMS sends them: the clock (8500021) goes 21 / 22 / 23 as its HP drops
 * (GMS v271 gates: crossed between 86.6-100%, 66.4-74.7% and 40.9-53%, so 90 / 70 / 50) and 24 when
 * it dies; Papulatus (8500022) gets 25 when he appears and 26 when he dies.
 */
public final class PapulatusPhases {
    private static final int CHAOS_CLOCK = 8500021;
    private static final int CHAOS_PAPULATUS = 8500022;
    private static final int[][] CLOCK_GATES = {{50, 23}, {70, 22}, {90, 21}};

    private PapulatusPhases() {
    }

    public static void onDamaged(Mob mob) {
        if (mob.getTemplateId() != CHAOS_CLOCK || mob.getHp() <= 0) {
            return;
        }
        double perc = mob.getHp() * 100D / Math.max(1, mob.getMaxHp());
        for (int[] gate : CLOCK_GATES) {
            if (perc <= gate[0]) {
                if (mob.getPhase() < gate[1]) {
                    mob.changePhase(gate[1]);
                }
                return;
            }
        }
    }

    public static void onSpawn(Mob mob) {
        if (mob.getTemplateId() == CHAOS_PAPULATUS) {
            mob.changePhase(25);
        }
    }

    public static void onDeath(Mob mob) {
        if (mob.getTemplateId() == CHAOS_CLOCK) {
            mob.changePhase(24);
        } else if (mob.getTemplateId() == CHAOS_PAPULATUS) {
            mob.changePhase(26);
        }
    }
}
