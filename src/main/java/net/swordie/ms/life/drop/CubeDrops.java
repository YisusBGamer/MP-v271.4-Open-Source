package net.swordie.ms.life.drop;

import net.swordie.ms.client.character.BossFirstClearModule;
import net.swordie.ms.constants.MobConstants;
import net.swordie.ms.constants.ServerFeatures;

import java.util.Set;

/**
 * Cubes and Rebirth Flames from monsters, by monster level. Chances are out of 10000 before the drop rate, which
 * multiplies them like any other drop. Ordinary monsters: 1 in 5,000 kills each. A boss fight's clear: a cube of
 * the boss's tier more often, and more of them on the hard modes.
 */
public final class CubeDrops {
    public static final int MYSTICAL_CUBE = 2710033;
    public static final int HARD_CUBE = 2710029;
    public static final int SOLID_CUBE = 2710030;
    public static final int POWERFUL_FLAME = 2048716;
    public static final int ETERNAL_FLAME = 2048717;

    public static final int MOB_CHANCE = 2; // hardcode-ok; 0.02%
    public static final int BOSS_CHANCE = 3000; // hardcode-ok
    public static final int BOSS_HARD_CHANCE = 5000; // hardcode-ok

    private CubeDrops() {
    }

    /** Mystical below 120, Hard to 199, Solid from 200; 0 below level 30. */
    public static int cubeFor(int level) {
        if (level < 30) {
            return 0;
        }
        return level < 120 ? MYSTICAL_CUBE : level < 200 ? HARD_CUBE : SOLID_CUBE;
    }

    /** Powerful from 60, Eternal from 160; 0 below 60. */
    public static int flameFor(int level) {
        if (level < 60) {
            return 0;
        }
        return level < 160 ? POWERFUL_FLAME : ETERNAL_FLAME;
    }

    /** Chaos, Hard, Extreme and Hell clears. */
    public static boolean isHardMode(int templateId) {
        BossFirstClearModule.Boss boss = BossFirstClearModule.getBoss(templateId);
        if (boss == null) {
            return false;
        }
        String d = boss.difficulty();
        return d.equals("Chaos") || d.equals("Hard") || d.equals("Extreme") || d.equals("Hell");
    }

    /**
     * Adds this kill's cube and flame chances: ordinary monsters roll both, a boss clear rolls a cube. Other boss
     * monsters and boss fight adds get nothing.
     */
    public static void add(Set<DropInfo> drops, int templateId, int level, boolean boss, boolean bossFightAdd) {
        if (bossFightAdd || !ServerFeatures.on(ServerFeatures.Feature.CUBE_FLAME_DROPS)) {
            return;
        }
        int cube = cubeFor(level);
        if (MobConstants.isBossClearNxMob(templateId)) {
            if (cube != 0) {
                boolean hard = isHardMode(templateId);
                int chance = ServerFeatures.getInt(hard ? ServerFeatures.Feature.BOSS_CUBE_CHANCE_HARD
                        : ServerFeatures.Feature.BOSS_CUBE_CHANCE) * 100;
                if (chance > 0) {
                    drops.add(hard ? new DropInfo(cube, chance, 2, 3) : new DropInfo(cube, chance, 1, 2));
                }
            }
            return;
        }
        int mobChance = ServerFeatures.getInt(ServerFeatures.Feature.MOB_CUBE_FLAME_CHANCE);
        if (boss || mobChance <= 0) {
            return;
        }
        if (cube != 0) {
            drops.add(new DropInfo(cube, mobChance, 1, 1));
        }
        int flame = flameFor(level);
        if (flame != 0) {
            drops.add(new DropInfo(flame, mobChance, 1, 1));
        }
    }
}
