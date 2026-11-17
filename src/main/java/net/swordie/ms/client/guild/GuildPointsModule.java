package net.swordie.ms.client.guild;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.life.mob.Mob;

/**
 * Guild contribution from normal play, so guilds level (and their Noblesse skills grow) without any weekly event:
 * members levelling up and killing bosses. Contribution also gives guild EXP and GP (Guild.addCommitmentToChar).
 */
public class GuildPointsModule {

    /** Contribution per level gained: the new level, tripled from 200 (levels there take far longer). */
    public static int getLevelUpContribution(int newLevel) {
        return newLevel >= 200 ? newLevel * 3 : newLevel;
    }

    /**
     * Contribution for a boss kill: from the boss's EXP as before, but at least twice its level (many v265 bosses,
     * Lucid, Will and later, carry no EXP in Mob.wz and gave nothing).
     */
    public static int getBossContribution(Mob mob) {
        long fromExp = (long) (mob.getExp() * GameConstants.CONITRUBITION_PER_EXP);
        long fromLevel = 2L * Math.max(0, mob.getLevel());
        return (int) Math.min(Integer.MAX_VALUE, Math.max(fromExp, fromLevel));
    }

    public static void onLevelUp(Char chr, int startLevel, int level) {
        Guild guild = chr.getGuild();
        if (guild == null || level <= startLevel) {
            return;
        }
        int total = 0;
        for (int lv = startLevel + 1; lv <= level; lv++) {
            total += getLevelUpContribution(lv);
        }
        guild.addCommitmentToChar(chr, total);
    }

    public static void onBossKill(Char chr, Mob mob) {
        Guild guild = chr.getGuild();
        // only the kill that clears the boss (not its phases, adds or other boss-flagged mobs)
        if (guild != null && mob.isBoss() && net.swordie.ms.constants.MobConstants.isBossClearNxMob(mob.getTemplateId())) {
            guild.addCommitmentToChar(chr, getBossContribution(mob));
        }
    }
}
