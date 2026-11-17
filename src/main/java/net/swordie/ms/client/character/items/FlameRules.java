package net.swordie.ms.client.character.items;

import net.swordie.ms.enums.FlameStat;

import java.util.Set;

/**
 * Contributed by Arixal (from GitLab).
 *
 * Bonus stat (flame) rules that don't depend on game data, from the MapleStory Wiki's Bonus Stats pages. Kept apart from
 * ItemConstants, which reads the database as it loads.
 */
public final class FlameRules {

    private static final int BOSS_DAMAGE_MIN_WEAPON_LEVEL = 90;
    private static final int ALL_STATS_MIN_ARMOR_LEVEL = 70;
    private static final int LEVEL_REDUCTION_MIN_LEVEL = 5; // lower would overflow

    private static final Set<Integer> FLAMEABLE_EXCEPTIONS = Set.of(
            1152155, // Scarlet Shoulder
            1113015, // Secret Ring
            1143471, // Immortal Legacy
            1202287  // Ancient Slate Replica
    );

    private FlameRules() {
    }

    /**
     * @param weapon     whether the equip is a weapon; anything else counts as armor (accessories too)
     * @param equipLevel the equip's required level
     * @return whether the equip can roll this bonus stat line
     */
    public static boolean canRoll(FlameStat line, boolean weapon, int equipLevel) {
        return switch (line) {
            case Speed, Jump -> !weapon;
            case Damage -> weapon;
            case BossDamage -> weapon && equipLevel >= BOSS_DAMAGE_MIN_WEAPON_LEVEL;
            case AllStats -> weapon || equipLevel >= ALL_STATS_MIN_ARMOR_LEVEL;
            case LevelReduction -> equipLevel >= LEVEL_REDUCTION_MIN_LEVEL;
            default -> true;
        };
    }

    /** @return whether this item can have bonus stats although its equipment type can't */
    public static boolean isFlameableException(int itemId) {
        return FLAMEABLE_EXCEPTIONS.contains(itemId);
    }
}
