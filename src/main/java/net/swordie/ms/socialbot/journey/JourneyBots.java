package net.swordie.ms.socialbot.journey;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.life.mob.Mob;

/** The bot extension isn't part of this source; without it there are no bots to hit or move. */
public final class JourneyBots {

    private JourneyBots() {
    }

    /** @return false: this character isn't a bot */
    public static boolean mobHit(Char chr, Mob m, int attackIdx) {
        return false;
    }

    /** @return false: this character isn't a bot */
    public static boolean placeAt(Char chr, int x, int y) {
        return false;
    }
}
