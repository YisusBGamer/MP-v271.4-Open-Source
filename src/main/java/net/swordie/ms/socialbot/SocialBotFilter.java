package net.swordie.ms.socialbot;

import net.swordie.ms.client.character.Char;

/** The bot extension isn't part of this source; this keeps its call sites compiling. */
public final class SocialBotFilter {

    private SocialBotFilter() {
    }

    public static boolean isBot(Char chr) {
        return SocialBots.isBot(chr);
    }
}
