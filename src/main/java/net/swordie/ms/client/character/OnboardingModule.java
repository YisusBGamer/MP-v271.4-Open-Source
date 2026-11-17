package net.swordie.ms.client.character;

import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.scripts.ScriptType;
import net.swordie.ms.world.field.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Help for new players (setting onboarding_tips; the guide is always there):
 * <ul>
 * <li>a short welcome in the chat on a character's first login;</li>
 * <li>a welcome box on its first visit to a real town (after its tutorial), for characters up to
 * {@link GameConstants#ONBOARDING_WELCOME_BOX_MAX_LEVEL};</li>
 * <li>one tip at a few level milestones (where to train, gear, bosses, ...), and one about unspent ability points;</li>
 * <li>the new player guide: @menu > Help > New player guide.</li>
 * </ul>
 * Each message is shown once per character (server-only record {@link GameConstants#ONBOARDING_QR}).
 */
public final class OnboardingModule {

    private static final Logger log = LogManager.getLogger(OnboardingModule.class);

    private static final String WELCOME = "welcome";
    private static final String WELCOME_BOX = "box";
    private static final String AP_TIP = "ap";
    /** Unspent ability points from which the ability point tip is shown (once). */
    private static final int AP_TIP_MIN = 20;
    /** At most this many tips after one level up (several milestones at once: the highest ones). */
    private static final int MAX_TIPS_PER_LEVEL_UP = 2;

    private record Tip(int level, String text) {
    }

    private static final List<Tip> LEVEL_TIPS = List.of(
            new Tip(15, "Looking for a place to train? @menu > Travel > Hunting Grounds lists maps that suit your "
                    + "level and takes you there."),
            new Tip(30, "Gear check: @gear shows your empty equipment slots and where to farm them, @upgrades puts "
                    + "on better equips you're carrying, and @sell turns the rest into mesos."),
            new Tip(60, "@enhance scrolls and Star Forces your equipped gear in one go, and adds potentials. Monsters "
                    + "drop the Spell Traces it uses."),
            new Tip(100, "Ready for bosses? @menu > Bosses shows which ones you can beat with your damage, your "
                    + "cooldowns, and takes you to their entrances."),
            new Tip(140, "Hitting a wall? @menu > Gear > How do I get stronger? checks your character and lists "
                    + "what would raise your damage the most."),
            new Tip(200, "The Arcane River opens at level 200. Its monsters need Arcane Symbols for full damage; "
                    + "symbols you get merge into the one you wear and level it up on their own.")
    );

    private OnboardingModule() {
    }

    // ---------------------------------------------------------------------------------------------------------------
    // What was shown
    // ---------------------------------------------------------------------------------------------------------------

    private static Set<String> getShown(Char chr) {
        String rec = chr.getQuestManager().getHiddenRecord(GameConstants.ONBOARDING_QR);
        Set<String> shown = new LinkedHashSet<>();
        if (rec != null && !rec.isEmpty()) {
            shown.addAll(Arrays.asList(rec.split(",")));
        }
        return shown;
    }

    static boolean isShown(Char chr, String key) {
        return getShown(chr).contains(key);
    }

    static void markShown(Char chr, String... keys) {
        Set<String> shown = getShown(chr);
        if (shown.addAll(Arrays.asList(keys))) {
            chr.getQuestManager().setHiddenRecord(GameConstants.ONBOARDING_QR, String.join(",", shown));
        }
    }

    private static boolean isOnline(Char chr) {
        return chr != null && chr.isOnline() && chr.getClient() != null && chr.getField() != null
                && chr.getQuestManager() != null;
    }

    /** @return whether the character is free to get a pop-up: no NPC chat, cutscene, trade, shop or storage */
    static boolean isFree(Char chr) {
        var sm = chr.getScriptManager();
        return !chr.isTalkingToNpc() && !sm.isActive(ScriptType.Npc) && !sm.isActive(ScriptType.Quest)
                && !sm.isUiLocked() && !sm.isWaitingForAnswer() && chr.getMiniRoom() == null && chr.getShop() == null && !chr.isInTrunk()
                && chr.getHP() > 0;
    }

    // ---------------------------------------------------------------------------------------------------------------
    // Hooks
    // ---------------------------------------------------------------------------------------------------------------

    /** Called on every login (Char#login): the welcome, the first time. */
    public static void onLogin(Char chr) {
        if (!ServerFeatures.on(ServerFeatures.Feature.ONBOARDING_TIPS) || chr == null || chr.getQuestManager() == null
                || isShown(chr, WELCOME)) {
            return;
        }
        EventManager.addEvent(() -> {
            try {
                if (!isOnline(chr) || isShown(chr, WELCOME)) {
                    return;
                }
                markShown(chr, WELCOME);
                chr.chatMessage(ChatType.Notice2, "Welcome to Swordie Plus, " + chr.getName() + "!");
                chr.chatMessage(ChatType.Notice2, "Type @menu (or use the Quick Move button) for travel, shops, "
                        + "guides and help. New here? @menu > Help > New player guide.");
            } catch (Exception e) {
                log.error("Could not welcome " + chr.getName(), e);
            }
        }, 5000);
    }

    /**
     * Called when a character enters a real town (Events#onWarp): the welcome box on the first one, for new
     * characters. If the character is busy (a cutscene, an NPC chat, ...), the next town visit tries again.
     */
    public static void onEnterTown(Char chr, Field town) {
        if (!ServerFeatures.on(ServerFeatures.Feature.ONBOARDING_TIPS) || chr == null || chr.getQuestManager() == null
                || chr.getLevel() > GameConstants.ONBOARDING_WELCOME_BOX_MAX_LEVEL || isShown(chr, WELCOME_BOX)) {
            return;
        }
        EventManager.addEvent(() -> {
            try {
                if (!isOnline(chr) || chr.getField() != town || isShown(chr, WELCOME_BOX) || !isFree(chr)
                        || PlayStyleModule.hasPending(chr)) {
                    // the play style question / Express job window comes first: the box waits for another town
                    return;
                }
                markShown(chr, WELCOME_BOX);
                chr.write(net.swordie.ms.connection.packet.WvsContext.broadcastMsg(
                        BroadcastMsg.popUpMessage(getWelcomeBoxText())));
            } catch (Exception e) {
                log.error("Could not show the welcome box to " + chr.getName(), e);
            }
        }, 2500);
    }

    /** The welcome box (a plain message box: no colours). */
    public static String getWelcomeBoxText() {
        return "Welcome to Swordie Plus!\r\n\r\n"
                + "@menu - travel, shops, your character, gear, bosses and help, all in one place (also the first "
                + "entry of the Quick Move button).\r\n"
                + "@go <town> - travel to a town.\r\n"
                + "@job - choose a job path (Express: skips your class's official job questline).\r\n"
                + "@stuck - if a chat window freezes or you can't leave a map.\r\n\r\n"
                + "New here? @menu > Help > New player guide.";
    }

    /** Called after a character levels up (Char#addExp): milestone tips. */
    public static void onLevelUp(Char chr, int oldLevel, int newLevel) {
        if (!ServerFeatures.on(ServerFeatures.Feature.ONBOARDING_TIPS) || chr == null || chr.getQuestManager() == null
                || newLevel <= oldLevel) {
            return;
        }
        Set<String> shown = getShown(chr);
        List<String> keys = new ArrayList<>();
        List<String> texts = new ArrayList<>();
        for (Tip tip : LEVEL_TIPS) {
            String key = String.valueOf(tip.level());
            if (oldLevel < tip.level() && tip.level() <= newLevel && !shown.contains(key)) {
                keys.add(key); // every milestone passed counts as shown, even if only the highest ones are said
                texts.add(tip.text());
            }
        }
        if (!shown.contains(AP_TIP) && newLevel >= 15 && chr.getStat(Stat.ap) >= AP_TIP_MIN) {
            keys.add(AP_TIP);
            texts.add("You have " + chr.getStat(Stat.ap) + " ability points to spend: @menu > My character > "
                    + "Ability points.");
        }
        if (keys.isEmpty()) {
            return;
        }
        markShown(chr, keys.toArray(new String[0]));
        List<String> say = texts.subList(Math.max(0, texts.size() - MAX_TIPS_PER_LEVEL_UP), texts.size());
        EventManager.addEvent(() -> {
            if (isOnline(chr)) {
                for (String text : say) {
                    chr.chatMessage(ChatType.Notice2, "[Tip] " + text);
                }
            }
        }, 2000);
    }

    // ---------------------------------------------------------------------------------------------------------------
    // The new player guide (@menu > Help)
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * @return the guide's sections as {title, text}; commands and menu paths are in {braces} (shown as code on
     * the web, in blue in the NPC chat)
     */
    public static List<String[]> getGuide() {
        List<String[]> guide = new ArrayList<>();
        guide.add(new String[]{"Your first steps",
                "Create your account, log in and make a character. When it first appears, "
                        + "choose your play style: Classic plays your class's tutorial and story as on the official "
                        + "servers; Express skips the tutorial and takes you to town at level 10. Once you're in a "
                        + "town, type {@menu}: it has everything this server adds - travel, shops, your character, "
                        + "gear, bosses, info and help. It's also the first entry of the Quick Move button."});
        guide.add(new String[]{"Your job",
                "Classic: your class advances through its official job advancement quests - watch for the quest "
                        + "icon at levels 10, 30, 60, 100 and 200. Express: you choose your job path in a window "
                        + "that opens after the tutorial skip (or any time with {@job} from level "
                        + GameConstants.JOB_PATH_MIN_LEVEL + "), and your advancements happen automatically. You can "
                        + "switch from Classic to Express in {@menu > My character}. "
                        + GameConstants.JOB_PATH_WARNING});
        guide.add(new String[]{"Leveling up",
                "{@menu > Travel > Hunting Grounds} lists training maps by level (green ones suit you) and takes you "
                        + "there; {@go <town>} warps to any town. Your quest log and quest tracker follow your quests, and "
                        + "you're told when one is ready to turn in."});
        guide.add(new String[]{"Gear",
                "Monsters drop equips your class can use. {@menu > Gear > Gear guide} shows your empty slots and "
                        + "where to farm them, and Upgrades puts on better equips you're carrying. {@enhance} scrolls, "
                        + "Star Forces, adds potentials and cubes your equipped items in one go."});
        guide.add(new String[]{"Getting stronger and bosses",
                "{@menu > Gear > How do I get stronger?} checks your character and lists what would raise your damage "
                        + "the most, and where to get it. {@menu > Bosses} shows which bosses you can beat, your "
                        + "cooldowns, and takes you to their entrances."});
        guide.add(new String[]{"Shops and mesos",
                "{@menu > Shops & trading} has the everyday shop, the Legion and Android shops, "
                        + "Fredrick (cubes and flames for mesos) and Ari (scrolls for NX). Sell what you don't need "
                        + "with Sell items, and keep the rest in your storage ({@menu > Storage & inventory})."});
        guide.add(new String[]{"If something goes wrong",
                "{@stuck} clears a frozen chat window or skills that stopped working, and can move you to Henesys if "
                        + "you can't leave a map. {@menu > Help} can also fix some stuck Arcane River quests. Anything "
                        + "else: tell a GM."});
        return guide;
    }

    public static int getGuidePageCount() {
        return getGuide().size();
    }

    public static String getGuideTitle(int page) {
        return getGuide().get(page)[0];
    }

    private static final Pattern MARKED = Pattern.compile("\\{([^{}]+)}");

    /** One section of the guide for an NPC chat: commands and menu paths in blue. */
    public static String getGuidePageText(int page) {
        String[] section = getGuide().get(page);
        Matcher m = MARKED.matcher(section[1]);
        StringBuilder sb = new StringBuilder();
        while (m.find()) {
            m.appendReplacement(sb, Matcher.quoteReplacement("#b" + m.group(1) + "#k"));
        }
        m.appendTail(sb);
        return "#e" + section[0] + "#n  (" + (page + 1) + "/" + getGuidePageCount() + ")\r\n\r\n" + sb;
    }
}
