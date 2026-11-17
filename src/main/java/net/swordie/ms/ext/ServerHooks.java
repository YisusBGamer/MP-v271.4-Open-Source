package net.swordie.ms.ext;

import com.sun.net.httpserver.HttpServer;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.life.movement.MovementInfo;
import net.swordie.ms.scripts.ScriptType;
import net.swordie.ms.world.field.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Hook points for {@link ServerExtension}s. Every hook does nothing until an extension registers an implementation,
 * so the server runs the same without any.
 */
public final class ServerHooks {

    private static final Logger log = LogManager.getLogger(ServerHooks.class);

    public interface Hooks {
        /** Characters played by the server itself rather than a client. */
        default boolean isAutomated(Char chr) {
            return false;
        }

        /** Automated characters that still pay every player cost and take the player odds. */
        default boolean paysPlayerCosts(Char chr) {
            return false;
        }

        /** Mesos left a character ({@code sink}: the progression log's sink key, "transfer" for trades). */
        default void onMesosSpent(Char chr, long amount, String sink) {
        }

        /** Characters left out of public listings. */
        default boolean isUnlisted(Char chr) {
            return false;
        }

        /** An SQL fragment ("AND ...") leaving automated logins out of a query on the users table. */
        default String sqlExcludeAutomated(String userAlias) {
            return "";
        }

        /** An SQL fragment ("AND ...") leaving unlisted logins out of a query on the users table. */
        default String sqlExcludeUnlisted(String userAlias) {
            return "";
        }

        default int automatedOnline() {
            return 0;
        }

        /** Test characters don't join party searches and aren't announced. */
        default boolean isTestCharacter(Char chr) {
            return false;
        }

        default boolean involvesTestCharacter(Collection<Integer> charIds) {
            return false;
        }

        default void onUserMove(Char chr, MovementInfo movementInfo) {
        }

        default void onLogout(Char chr) {
        }

        default void onScriptMissing(Char chr, ScriptType type, String name) {
        }

        /** @return a token passed back to {@link #onScriptEnd(Object)}, or null */
        default Object onScriptStart(Char chr) {
            return null;
        }

        default void onScriptError(Char chr, String name, ScriptType type, String message) {
        }

        default void onScriptEnd(Object token) {
        }

        default void onWorldNews(String text) {
        }

        /** @return whether this ! command was handled */
        default boolean adminCommand(Char chr, String[] args) {
            return false;
        }

        /** @return whether this @ command was handled */
        default boolean playerCommand(Char chr, String[] args) {
            return false;
        }

        /** Adds contexts to the web API's HTTP server. */
        default void onWebServer(HttpServer server) {
        }

        /** No client is there to see what automated characters do on this field. */
        default boolean isUnwatched(Field field) {
            return false;
        }

        /** Lets this member into the leader's boss run while on that boss's cooldown. */
        default boolean ignoresBossCooldown(Char member, Char leader) {
            return false;
        }

        /** A quest's name for chat lines, or null to leave it to the next hook. */
        default String questName(int questId) {
            return null;
        }

        /** New logins can't take this name (an extension's own logins use it). */
        default boolean isReservedLoginName(String name) {
            return false;
        }
    }

    private static final List<Hooks> hooks = new CopyOnWriteArrayList<>();
    private static final List<ServerExtension> extensions = new ArrayList<>();

    private ServerHooks() {
    }

    public static void register(Hooks h) {
        hooks.add(h);
    }

    /** Finds the extensions on the classpath and lets them install their hooks. */
    public static void load() {
        for (ServerExtension ext : ServiceLoader.load(ServerExtension.class)) {
            try {
                ext.install();
                extensions.add(ext);
                log.info("Loaded extension " + ext.getClass().getSimpleName());
            } catch (Exception e) {
                log.error("Could not load extension " + ext.getClass().getName(), e);
            }
        }
    }

    /** Installs only the extension of this class name (test server: the bots without the other extensions). */
    public static void loadOnly(String className) {
        // by provider type, so the other extensions aren't even constructed
        for (var provider : ServiceLoader.load(ServerExtension.class).stream()
                .filter(p -> p.type().getName().equals(className)).toList()) {
            try {
                ServerExtension ext = provider.get();
                ext.install();
                extensions.add(ext);
                log.info("Loaded extension " + ext.getClass().getSimpleName() + " (only)");
            } catch (Exception e) {
                log.error("Could not load extension " + className, e);
            }
        }
    }

    public static void serverStarted() {
        for (ServerExtension ext : extensions) {
            try {
                ext.onServerStarted();
            } catch (Exception e) {
                log.error("Extension " + ext.getClass().getName() + " failed to start", e);
            }
        }
    }

    public static boolean isReservedLoginName(String name) {
        for (Hooks h : hooks) {
            if (h.isReservedLoginName(name)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAutomated(Char chr) {
        for (Hooks h : hooks) {
            if (h.isAutomated(chr)) {
                return true;
            }
        }
        return false;
    }

    /** Players, and the automated characters that pay as players do. */
    public static boolean paysAsPlayer(Char chr) {
        if (!isAutomated(chr)) {
            return true;
        }
        for (Hooks h : hooks) {
            if (h.paysPlayerCosts(chr)) {
                return true;
            }
        }
        return false;
    }

    public static void mesosSpent(Char chr, long amount, String sink) {
        for (Hooks h : hooks) {
            try {
                h.onMesosSpent(chr, amount, sink);
            } catch (Exception e) {
                log.error("onMesosSpent hook failed", e);
            }
        }
    }

    public static boolean isUnlisted(Char chr) {
        for (Hooks h : hooks) {
            if (h.isUnlisted(chr)) {
                return true;
            }
        }
        return false;
    }

    public static String sqlExcludeAutomated(String userAlias) {
        StringBuilder sb = new StringBuilder();
        for (Hooks h : hooks) {
            sb.append(h.sqlExcludeAutomated(userAlias));
        }
        return sb.toString();
    }

    public static String sqlExcludeUnlisted(String userAlias) {
        StringBuilder sb = new StringBuilder();
        for (Hooks h : hooks) {
            sb.append(h.sqlExcludeUnlisted(userAlias));
        }
        return sb.toString();
    }

    public static int automatedOnline() {
        int n = 0;
        for (Hooks h : hooks) {
            n += h.automatedOnline();
        }
        return n;
    }

    public static boolean isTestCharacter(Char chr) {
        for (Hooks h : hooks) {
            if (h.isTestCharacter(chr)) {
                return true;
            }
        }
        return false;
    }

    public static boolean involvesTestCharacter(Collection<Integer> charIds) {
        for (Hooks h : hooks) {
            if (h.involvesTestCharacter(charIds)) {
                return true;
            }
        }
        return false;
    }

    public static void onUserMove(Char chr, MovementInfo movementInfo) {
        for (Hooks h : hooks) {
            h.onUserMove(chr, movementInfo);
        }
    }

    public static void onLogout(Char chr) {
        for (Hooks h : hooks) {
            h.onLogout(chr);
        }
    }

    public static void onScriptMissing(Char chr, ScriptType type, String name) {
        for (Hooks h : hooks) {
            h.onScriptMissing(chr, type, name);
        }
    }

    /** @return the tokens to hand to {@link #onScriptEnd(Object[])} */
    public static Object[] onScriptStart(Char chr) {
        if (hooks.isEmpty()) {
            return null;
        }
        Object[] tokens = new Object[hooks.size()];
        for (int i = 0; i < tokens.length && i < hooks.size(); i++) {
            tokens[i] = hooks.get(i).onScriptStart(chr);
        }
        return tokens;
    }

    public static void onScriptError(Char chr, String name, ScriptType type, String message) {
        for (Hooks h : hooks) {
            h.onScriptError(chr, name, type, message);
        }
    }

    public static void onScriptEnd(Object[] tokens) {
        if (tokens == null) {
            return;
        }
        for (int i = 0; i < tokens.length && i < hooks.size(); i++) {
            hooks.get(i).onScriptEnd(tokens[i]);
        }
    }

    public static void onWorldNews(String text) {
        for (Hooks h : hooks) {
            h.onWorldNews(text);
        }
    }

    public static boolean adminCommand(Char chr, String[] args) {
        for (Hooks h : hooks) {
            if (h.adminCommand(chr, args)) {
                return true;
            }
        }
        return false;
    }

    public static boolean playerCommand(Char chr, String[] args) {
        for (Hooks h : hooks) {
            if (h.playerCommand(chr, args)) {
                return true;
            }
        }
        return false;
    }

    public static void onWebServer(HttpServer server) {
        for (Hooks h : hooks) {
            h.onWebServer(server);
        }
    }

    public static boolean isUnwatched(Field field) {
        for (Hooks h : hooks) {
            if (h.isUnwatched(field)) {
                return true;
            }
        }
        return false;
    }

    public static boolean ignoresBossCooldown(Char member, Char leader) {
        for (Hooks h : hooks) {
            if (h.ignoresBossCooldown(member, leader)) {
                return true;
            }
        }
        return false;
    }

    /** "quest 34330 'name'", or "quest 34330" when no hook knows the name. */
    public static String questName(int questId) {
        for (Hooks h : hooks) {
            String name = h.questName(questId);
            if (name != null) {
                return name;
            }
        }
        return "quest " + questId;
    }
}
