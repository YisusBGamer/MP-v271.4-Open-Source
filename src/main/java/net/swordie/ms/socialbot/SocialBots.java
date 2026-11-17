package net.swordie.ms.socialbot;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.ext.ServerHooks;

/** The bot extension isn't part of this source; this keeps its call sites compiling. */
public final class SocialBots {

    private SocialBots() {
    }

    public static boolean isBot(Char chr) {
        return chr != null && ServerHooks.isAutomated(chr);
    }
}
