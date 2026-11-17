package net.swordie.ms.client.character;

import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.scripts.ScriptManagerImpl;
import net.swordie.ms.scripts.ScriptType;
import net.swordie.ms.ext.ServerHooks;
import net.swordie.ms.world.field.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Play style, chosen once per character ({@link GameConstants#PLAY_STYLE_QR}):
 * <ul>
 * <li><b>Classic</b>: the class tutorial and story as on the official servers; the job advances through the class's
 * official job quests. No @job reminders and no automatic Arcane River skip offers (Spiegelmann and Legato still
 * offer the skip when talked to).</li>
 * <li><b>Express</b>: the class tutorial is skipped (each tutorial's own skip: level 10, the tutorial's quests, skills
 * and items, then its town), the job path window (@job) opens right after, so advancements are automatic, and
 * Spiegelmann offers to skip the Arcane River story at each new area.</li>
 * </ul>
 * Asked by the first script of every class tutorial (sm.askTutorialSkip, which replaced the "skip the tutorial?"
 * question), or - for classes that start without a tutorial on this server, and characters made before this
 * existed - by the Maple Administrator a few seconds after logging in ({@link #onLogin}/{@link #onWarp}). Classic
 * can be switched to Express any time (@menu > My character); Express back to Classic only until a job path is
 * chosen. Choosing a job path with @job makes a character Express.
 * <p>
 * The server's new player mode (setting new_player_mode, {@link #mode()}) can replace the choice: Express for
 * everyone, Classic for everyone, or the game's own way (Default: no question, no automatic skips). The stored
 * choice is never changed by the mode: game code asks {@link #effective}, {@link #isClassic} and
 * {@link #autoSkips}, so setting the mode back to letting players choose restores everyone's own pick. Bots always
 * keep their stored style.
 * <p>
 * While the setting job_paths is off, Express isn't offered (it skips the tutorial that gives some classes their
 * first job, and @job is closed): in the choice and Express modes a character that hasn't chosen plays Classic, and
 * one that chose keeps its choice ({@link #expressClosed}).
 */
public final class PlayStyleModule {

    private static final Logger log = LogManager.getLogger(PlayStyleModule.class);

    public enum PlayStyle {
        UNSET, CLASSIC, EXPRESS
    }

    /** Who asks (Maple Administrator, as in the tutorials' skip questions). */
    public static final int SPEAKER = 2007;
    public static final String SCRIPT = "playStyle";
    private static final String JOB_OFFERED = "jobOffer"; // OnboardingModule record key: the job window was opened

    /** Characters with an ask/offer already scheduled (so several warps don't stack them). */
    private static final Set<Integer> scheduled = ConcurrentHashMap.newKeySet();

    private PlayStyleModule() {
    }

    /** The server's new player mode (setting new_player_mode). */
    public enum Mode {
        /** each new character is asked (Express or Classic) */
        CHOICE,
        /** no question, everyone plays Express */
        EXPRESS,
        /** no question, everyone plays Classic (characters that already chose a job path stay Express) */
        CLASSIC,
        /** the game's own tutorial question; none of the server's automatic story, boss or Arcane River skips */
        DEFAULT
    }

    /** @return the new player mode in effect (it needs a restart to change, so it's fixed while the server runs) */
    public static Mode mode() {
        return switch (ServerFeatures.choice(ServerFeatures.Feature.NEW_PLAYER_MODE)) {
            case "express" -> Mode.EXPRESS;
            case "classic" -> Mode.CLASSIC;
            case "default" -> Mode.DEFAULT;
            default -> Mode.CHOICE;
        };
    }

    /** Bots play by their stored style in every mode (journey bots need the Express skips, probes play Classic). */
    private static Mode modeFor(Char chr) {
        return chr != null && ServerHooks.isAutomated(chr) ? Mode.CHOICE : mode();
    }

    private static boolean hasJobPath(Char chr) {
        return !JobPathModule.getActivePath(chr).isEmpty();
    }

    /**
     * @return whether Express is closed to new choices for this character: job paths are off (setting job_paths) and
     * the mode would otherwise offer or force Express. Read live, as job_paths needs no restart.
     */
    private static boolean expressClosed(Char chr) {
        Mode m = modeFor(chr);
        return (m == Mode.CHOICE || m == Mode.EXPRESS) && !(chr != null && ServerHooks.isAutomated(chr))
                && !ServerFeatures.on(ServerFeatures.Feature.JOB_PATHS);
    }

    /**
     * @return the style the server treats this character as having: the stored choice when players choose, otherwise
     * the mode's (UNSET in Default mode). Never changes the stored choice.
     */
    public static PlayStyle effective(Char chr) {
        PlayStyle stored = get(chr);
        if (expressClosed(chr)) {
            // a job path (reborn characters can still choose one) skipped the official job quests: Express
            return stored == PlayStyle.EXPRESS || hasJobPath(chr) ? PlayStyle.EXPRESS : PlayStyle.CLASSIC;
        }
        return switch (modeFor(chr)) {
            case CHOICE -> stored;
            case EXPRESS -> PlayStyle.EXPRESS;
            // a job path skipped the official job quests, which can't be redone
            case CLASSIC -> hasJobPath(chr) ? PlayStyle.EXPRESS : PlayStyle.CLASSIC;
            case DEFAULT -> PlayStyle.UNSET;
        };
    }

    /** @return whether this character is asked to choose its play style (only while players choose Express or not) */
    public static boolean asks(Char chr) {
        return modeFor(chr) == Mode.CHOICE && !expressClosed(chr);
    }

    /**
     * @return whether the server's automatic Express skips apply to this character (Zero's and Princess No's story,
     * the job path window after the tutorial, Legato's offer on arrival in Nameless Town)
     */
    public static boolean autoSkips(Char chr) {
        return chr != null && chr.getQuestManager() != null && effective(chr) == PlayStyle.EXPRESS;
    }

    /**
     * @return whether the boss window skips the prerequisite quests for this character (null: any character):
     * setting boss_ui_ignore_quests, except in the Default mode and for Classic characters (they need the bosses'
     * story quests, as at the entry NPCs: BossPrereqModule)
     */
    public static boolean bossUiBypass(Char chr) {
        return ServerFeatures.on(ServerFeatures.Feature.BOSS_UI_IGNORE_QUESTS)
                && (chr == null ? mode() : modeFor(chr)) != Mode.DEFAULT
                && (chr == null || ContentAccess.unlocksAll(chr));
    }

    /**
     * Spiegelmann's offer on a level up. It only skips the areas before the one the new level reaches, so Classic
     * characters get it too (a region's story is offered once they've outleveled it); none in the Default mode.
     * Bots keep the old rule (not for Classic).
     */
    public static boolean offersArcaneSkipOnLevelUp(Char chr) {
        if (chr != null && ServerHooks.isAutomated(chr)) {
            return !isClassic(chr);
        }
        return mode() != Mode.DEFAULT;
    }

    /**
     * Legato's offer on arriving in Nameless Town skips the first region before it's outleveled: everyone but Classic
     * characters (Express and unset as today; Classic get the outleveled offers instead; none in the Default mode, as
     * isClassic covers it). Bots: all.
     */
    public static boolean offersArcaneSkipOnArrival(Char chr) {
        return chr != null && (ServerHooks.isAutomated(chr) || !isClassic(chr));
    }

    /** @return whether @job is closed to this character: Classic always, unless reborn or already on a job path */
    public static boolean blocksJobPath(Char chr) {
        return modeFor(chr) == Mode.CLASSIC && !hasJobPath(chr) && !RebirthModule.isReborn(chr);
    }

    /**
     * @return whether talking to Spiegelmann or Legato only skips the Arcane River areas this character has
     * outleveled (reached the next area's level), like their level-up offer: Classic characters (and everyone in the
     * Default mode). Bots and Express characters can skip any area whose level they've reached.
     */
    public static boolean limitsArcaneSkipToOutleveled(Char chr) {
        return chr != null && !ServerHooks.isAutomated(chr) && isClassic(chr);
    }

    /** @return a line about the play style the server sets, for menus (null while players choose) */
    public static String getModeText(Char chr) {
        if (expressClosed(chr)) {
            return effective(chr) == PlayStyle.EXPRESS ? "Job paths are off on this server. You keep Express."
                    : "Job paths are off on this server, so everyone plays Classic.";
        }
        return switch (modeFor(chr)) {
            case CHOICE -> null;
            case EXPRESS -> "This server is Express: no tutorials, @job picks your path, job advancements are "
                    + "automatic.";
            case CLASSIC -> hasJobPath(chr) ? "This server plays Classic, but your job path (@job) keeps you on "
                    + "Express." : "This server plays Classic: tutorial, story and job quests like GMS.";
            case DEFAULT -> "This server plays it the normal way. The tutorial asks if you want to skip it, and "
                    + "that's it.";
        };
    }

    /** @return the stored choice (never the mode's: see {@link #effective}) */
    public static PlayStyle get(Char chr) {
        String rec = chr.getQuestManager().getHiddenRecord(GameConstants.PLAY_STYLE_QR);
        if ("classic".equals(rec)) {
            return PlayStyle.CLASSIC;
        }
        if ("express".equals(rec)) {
            return PlayStyle.EXPRESS;
        }
        return PlayStyle.UNSET;
    }

    public static void set(Char chr, PlayStyle style) {
        chr.getQuestManager().setHiddenRecord(GameConstants.PLAY_STYLE_QR,
                style == PlayStyle.CLASSIC ? "classic" : style == PlayStyle.EXPRESS ? "express" : "");
    }

    /**
     * @return whether this character plays the story as on the official servers, without the server's automatic
     * skips: Classic (stored or set by the mode), or any character in the Default mode
     */
    public static boolean isClassic(Char chr) {
        if (chr == null || chr.getQuestManager() == null) {
            return false;
        }
        return effective(chr) == PlayStyle.CLASSIC || modeFor(chr) == Mode.DEFAULT;
    }

    public static String getName(Char chr) {
        return switch (effective(chr)) {
            case CLASSIC -> "Classic";
            case EXPRESS -> "Express";
            default -> modeFor(chr) == Mode.DEFAULT ? "Default" : "not chosen";
        };
    }

    /**
     * @return whether the character's class starts without a playable tutorial on this server (it's created at level
     * 10 or higher with its first job, in a town). Kain and Adele have one for players made since it was added (their
     * tutorial's last quest, 39495 / 39671, is done or they're still beginners); bots are still made in town.
     */
    public static boolean hasNoTutorial(Char chr) {
        int job = chr.getJob();
        if (JobConstants.isKain(job) || JobConstants.isAdele(job)) {
            var qm = chr.getQuestManager();
            return !JobConstants.isBeginnerJob(job)
                    && (qm == null || !qm.hasQuestCompleted(JobConstants.isKain(job) ? 39495 : 39671));
        }
        return JobConstants.isLara(job) || JobConstants.isHoYoung(job) || JobConstants.isPinkBean(job)
                || JobConstants.isYeti(job);
    }

    // ---------------------------------------------------------------------------------------------------------------
    // Asking
    // ---------------------------------------------------------------------------------------------------------------

    private static final String CLASSIC_LINE = "#L0##bClassic#k - tutorial, story and job quests like GMS.#l";

    private static String expressLine(String start) {
        return "#L1##bExpress#k - " + start + "pick a job path and your advancements happen on their own. You can "
                + "skip the Arcane River story too.#l";
    }

    /**
     * Shows the choice (with a confirmation) and stores it.
     *
     * @return the chosen style (CLASSIC or EXPRESS)
     */
    private static PlayStyle ask(ScriptManagerImpl sm, Char chr, String intro, String expressStart) {
        while (true) {
            sm.setSpeakerID(SPEAKER);
            sm.removeEscapeButton();
            int sel = sm.sendNext(intro + "\r\n\r\n" + CLASSIC_LINE + "\r\n\r\n" + expressLine(expressStart)
                    + "\r\n\r\n#dYou can go from Classic to Express later (@playstyle).#k");
            sm.removeEscapeButton();
            boolean ok;
            if (sel == 1) {
                ok = sm.sendAskYesNo("#eExpress#n\r\n\r\nExpress skips your class's tutorial. "
                        + GameConstants.JOB_PATH_WARNING + "\r\n\r\nPlay Express?");
            } else {
                ok = sm.sendAskYesNo("#eClassic#n\r\n\r\nStory and job quests like GMS. If a quest gets "
                        + "you stuck, try @stuck. You can switch to Express any time.\r\n\r\nPlay Classic?");
            }
            if (ok) {
                PlayStyle style = sel == 1 ? PlayStyle.EXPRESS : PlayStyle.CLASSIC;
                set(chr, style);
                log.info(String.format("%s chose the %s play style", chr.getName(), style));
                return style;
            }
        }
    }

    /**
     * The first question of a class tutorial (sm.askTutorialSkip): asks the play style the first time; afterwards
     * answers with the stored choice without asking.
     *
     * @param destinationMap where the tutorial's skip takes the character
     * @return true to skip the tutorial (Express)
     */
    public static boolean askInTutorial(ScriptManagerImpl sm, Char chr, int destinationMap) {
        if (skipRuns.remove(chr.getId())) {
            return true; // Express chosen in @menu during this tutorial: its skip runs now
        }
        noteTutorial(sm, chr, destinationMap);
        Mode m = modeFor(chr);
        Boolean story = StorySkipModule.askInTutorial(sm, chr,
                (m == Mode.CHOICE && get(chr) == PlayStyle.UNSET) || m == Mode.DEFAULT);
        if (story != null) {
            return story; // the play style is asked once out of the tutorial
        }
        if (expressClosed(chr)) {
            return expressInTutorial(chr, effective(chr) == PlayStyle.EXPRESS); // no question; nothing is stored
        }
        switch (modeFor(chr)) {
            case EXPRESS, CLASSIC -> {
                return expressInTutorial(chr, effective(chr) == PlayStyle.EXPRESS);
            }
            case DEFAULT -> {
                // the game's own question; nothing is stored
                sm.setSpeakerID(SPEAKER);
                return sm.sendAskYesNo("Would you like to skip the tutorial and go straight to #b#m"
                        + destinationMap + "##k?");
            }
            default -> {
            }
        }
        PlayStyle style = get(chr);
        if (style == PlayStyle.UNSET) {
            style = ask(sm, chr, "#e#bWelcome to Swordie Plus!#k#n\r\nBefore your story begins: how would you "
                    + "like to play?", "skip the tutorial (you start in #b#m" + destinationMap + "##k at "
                    + "level 30 with your 2nd job), ");
        }
        return expressInTutorial(chr, style == PlayStyle.EXPRESS);
    }

    /** The finish to Lv. 30 runs once the tutorial's own skip lands the character in town. */
    private static boolean expressInTutorial(Char chr, boolean express) {
        if (express) {
            StorySkipModule.queueExpress(chr);
        }
        return express;
    }

    /**
     * Express picked outside the tutorial's first question: Lv. 30 and the job advancements, after the tutorial's
     * skip when still in one.
     *
     * @return whether the story finish is queued (it asks the job path itself, so no job window now)
     */
    private static boolean startExpress(Char chr) {
        boolean queued = StorySkipModule.queueExpress(chr);
        if (startTutorialSkip(chr)) {
            return true;
        }
        if (queued && tutorialRecord(chr) == null && !inStartTutorial(chr)) {
            StorySkipModule.schedule(chr);
        }
        return queued;
    }

    /**
     * The question outside a tutorial (playStyle.py): classes without a tutorial here, characters made before play
     * styles existed, and @menu > My character.
     *
     * @return whether the job path window should open next (Express, and no job path chosen yet)
     */
    public static boolean askOutsideTutorial(ScriptManagerImpl sm, Char chr) {
        if (!asks(chr)) {
            sm.setSpeakerID(SPEAKER);
            sm.sendSayOkay(getModeText(chr));
            return false;
        }
        String intro;
        if (hasNoTutorial(chr) && chr.getLevel() <= 10) {
            intro = "#e#bWelcome to Swordie Plus!#k#n\r\nYour class's tutorial isn't in yet, so you start "
                    + "here. How do you want to play?";
        } else {
            intro = "#e#bChoose your play style#k#n\r\nHow would you like to play from here?";
        }
        PlayStyle style = ask(sm, chr, intro, "");
        if (style == PlayStyle.EXPRESS) {
            EventManager.addEvent(() -> ArcaneRiverSkipModule.catchUp(chr), 2000);
        }
        if (style == PlayStyle.EXPRESS && startExpress(chr)) {
            return false;
        }
        return style == PlayStyle.EXPRESS && opensJobWindow(chr);
    }

    /** @return whether the job path window should open now for an Express character (and notes that it did) */
    private static boolean opensJobWindow(Char chr) {
        if (JobPathModule.isManaged(chr) || chr.getLevel() < GameConstants.JOB_PATH_MIN_LEVEL) {
            return false;
        }
        OnboardingModule.markShown(chr, JOB_OFFERED);
        return true;
    }

    /** Express Zeros skip Zero's story chapters: the weapon window (40905) and the later quests (40914) open now. */
    private static void skipZeroStory(Char chr) {
        if (!JobConstants.isZero(chr.getJob())) {
            return;
        }
        var qm = chr.getQuestManager();
        for (int q : new int[]{net.swordie.ms.constants.QuestConstants.LAPIS_LAZULI,
                net.swordie.ms.constants.QuestConstants.ZERO_LAST_QUEST}) {
            if (!qm.hasQuestCompleted(q)) {
                qm.completeQuest(q, false);
            }
        }
    }

    /** @return whether an Express character can still go back to Classic (no job path chosen with @job) */
    public static boolean canSwitchToClassic(Char chr) {
        return !JobPathModule.isManaged(chr);
    }

    /**
     * Switches from @menu > My character.
     *
     * @return whether the job path window should open next
     */
    public static boolean switchTo(Char chr, boolean express) {
        if (!asks(chr)) {
            return false; // the server sets the play style
        }
        if (express) {
            set(chr, PlayStyle.EXPRESS);
            if (startExpress(chr)) {
                return false; // the job window follows the skip, as for Express picked at the tutorial's start
            }
            skipZeroStory(chr);
            EventManager.addEvent(() -> ArcaneRiverSkipModule.catchUp(chr), 2000); // after the @menu dialog
            return opensJobWindow(chr);
        }
        if (canSwitchToClassic(chr)) {
            set(chr, PlayStyle.CLASSIC);
        }
        return false;
    }

    // ---------------------------------------------------------------------------------------------------------------
    // Hooks
    // ---------------------------------------------------------------------------------------------------------------

    /** Called when a job path is chosen with @job: that's Express. */
    public static void onJobPathChosen(Char chr) {
        if (asks(chr) && get(chr) != PlayStyle.EXPRESS) { // other modes never change the stored choice
            set(chr, PlayStyle.EXPRESS);
        }
        if (effective(chr) == PlayStyle.EXPRESS && !RebirthModule.isReborn(chr)) {
            startExpress(chr);
        }
    }

    private static boolean needsQuestion(Char chr) {
        // tutorial classes below level 10 are asked by their tutorial's first script instead
        return asks(chr) && get(chr) == PlayStyle.UNSET && (hasNoTutorial(chr) || chr.getLevel() >= 10)
                && !inStartTutorial(chr) && pendingTutorial(chr) == null && !StorySkipModule.isPending(chr);
    }

    /**
     * Classes created at level 10 or higher inside their tutorial (Kinesis's training rooms, Angelic Buster's opening
     * scenes, Kaiser's Pantheon, Xenon's lab, Zero's Shadowvale): the tutorial's first script asks there.
     */
    private static boolean inStartTutorial(Char chr) {
        int field = chr.getFieldID();
        for (int[] r : START_TUTORIAL_FIELDS.get()) {
            if (field >= r[0] && field <= r[1]) {
                return true;
            }
        }
        return false;
    }

    /** {first, last} field of each such tutorial (start-tutorial-fields.tsv) */
    private static final Supplier<int[][]> START_TUTORIAL_FIELDS = DataTable.cached("start-tutorial-fields",
            t -> t.rows().stream().map(r -> new int[]{r.i("first"), r.i("last")}).toArray(int[][]::new));

    /** Express after a tutorial skip: the job path window still has to open (once). */
    private static boolean needsJobWindow(Char chr) {
        return effective(chr) == PlayStyle.EXPRESS && !JobPathModule.isManaged(chr) && JobPathModule.canChooseNewPath(chr)
                && chr.getLevel() >= GameConstants.JOB_PATH_MIN_LEVEL && !OnboardingModule.isShown(chr, JOB_OFFERED);
    }

    /** @return whether the ask or the job window is still due (OnboardingModule waits with its welcome box) */
    public static boolean hasPending(Char chr) {
        return chr != null && chr.getQuestManager() != null && (needsQuestion(chr) || needsJobWindow(chr));
    }

    public static void onLogin(Char chr) {
        if (chr != null && chr.getQuestManager() != null && modeFor(chr) == Mode.EXPRESS && !expressClosed(chr)) {
            skipZeroStory(chr); // Express always: Zeros made before get what switching to Express gives
        }
        schedule(chr, 8000);
        if (chr != null && chr.getQuestManager() != null && tutorialRecord(chr) == null) {
            // fresh Express characters that missed the Lv. 30 start (made before it, or no tutorial question)
            if (autoSkips(chr) && chr.getLevel() <= 10 && JobConstants.getAdvancementTier(chr.getJob()) <= 1
                    && !inStartTutorial(chr)) {
                StorySkipModule.queueExpress(chr);
            }
            StorySkipModule.schedule(chr);
        }
        ContentAccess.giveExpressKitIfDue(chr);
        BossUiAccess.onLogin(chr);
        if (chr != null) {
            // stories added to the Express skip after the character passed them
            EventManager.addEvent(() -> ArcaneRiverSkipModule.catchUp(chr), 12_000);
        }
    }

    /** Asks the play style soon if it's still due (after the story skip finished). */
    public static void askSoon(Char chr) {
        schedule(chr, 3000);
    }

    /** Called on every map change (Events#onWarp). */
    public static void onWarp(Char chr, Field newField) {
        noteTutorialLeft(chr, newField);
        if (newField != null && newField.getInstance() == null) {
            schedule(chr, 3000);
            if (chr.getQuestManager() != null && tutorialRecord(chr) == null) {
                StorySkipModule.schedule(chr); // landed after the tutorial's skip
            }
            ContentAccess.giveExpressKitIfDue(chr);
            BossUiAccess.showNewlyDue(chr);
        }
    }

    /**
     * The Burning offer for a new character (setting burning): after the play style question and the job window, out
     * of any tutorial, from level 10 (or a class without a tutorial).
     */
    private static boolean burningDue(Char chr) {
        return !ServerHooks.isAutomated(chr) && !needsQuestion(chr) && !needsJobWindow(chr)
                && (hasNoTutorial(chr) || chr.getLevel() >= 10) && !inStartTutorial(chr)
                && pendingTutorial(chr) == null && !StorySkipModule.isPending(chr) && BurningModule.needsOffer(chr);
    }

    private static void schedule(Char chr, long delayMs) {
        if (chr == null || chr.getQuestManager() == null || !(hasPending(chr) || burningDue(chr))
                || !scheduled.add(chr.getId())) {
            return;
        }
        EventManager.addEvent(() -> {
            scheduled.remove(chr.getId());
            try {
                if (!chr.isOnline() || chr.getClient() == null || chr.getField() == null
                        || chr.getInstance() != null || !OnboardingModule.isFree(chr)) {
                    return; // tried again on the next map change
                }
                if (needsQuestion(chr)) {
                    chr.getScriptManager().startScript(SPEAKER, SCRIPT, ScriptType.Npc);
                } else if (needsJobWindow(chr)) {
                    OnboardingModule.markShown(chr, JOB_OFFERED);
                    chr.chatMessage("Express: choose your job path. (You can also do this later with @job.)");
                    MenuModule.open(chr, "job");
                } else if (burningDue(chr)) {
                    chr.getScriptManager().startScript(SPEAKER, BurningModule.OFFER_SCRIPT, ScriptType.Npc);
                }
            } catch (Exception e) {
                log.error("Could not ask the play style of " + chr.getName(), e);
            }
        }, delayMs);
    }

    // ---------------------------------------------------------------------------------------------------------------
    // Express from @menu during a class tutorial
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Server-only record of the class tutorial a character is in: the tutorial's first script (the one that asks
     * sm.askTutorialSkip), its map, the skip's destination, and the job and level then. Cleared on reaching the
     * destination or any town.
     */
    private static final int TUTORIAL_QR = 89999973;

    /** The tutorial scripts whose skip can run again later. */
    private static final Set<String> SKIP_SCRIPTS = Set.of("PTtutor000", "Resi_tutor10", "angelic_tuto0",
            "aranTutorAlone", "enter_140090010", "cannon_tuto_01", "cannon_tuto_start", "ds_tuto_0_0", "dubl2Tuto0", "enter_130030000",
            "enter_331001110", "enter_402090000", "enter_693007000", "enter_910090301", "enter_940200500", "enter_993166400",
            "enter_illium_hunt1", "enter_993162001", "enter_993164003", "eunwol_tuto_illust", "evanAlone", "goAdventure", "kaiser_tuto_0_0",
            "kannaTutoDirection", "kenjiTutoDirection", "map_913070000", "merTutorDrecotion00", "np_tuto_0_0_before",
            "q59000_tuto", "xn_tuto_0_0", "zeroProl000");
    /** Tutorials only tracked: Luminous's skip branch asks Light or Dark in a cutscene, so it isn't run again. */
    private static final Set<String> TRACKED_SCRIPTS = Set.of("lightning_tuto_1_0");

    /** Characters whose tutorial script is running again for its skip (askTutorialSkip answers yes, no question). */
    private static final Set<Integer> skipRuns = ConcurrentHashMap.newKeySet();

    private static String scriptBaseName(String name) {
        return name != null && name.endsWith(".py") ? name.substring(0, name.length() - 3) : name;
    }

    private static void noteTutorial(ScriptManagerImpl sm, Char chr, int destinationMap) {
        if (chr == null || chr.getQuestManager() == null) {
            return;
        }
        for (ScriptType type : new ScriptType[]{ScriptType.FirstEnterField, ScriptType.Field}) {
            var si = sm.getScriptInfoByType(type);
            String name = si == null || !si.isActive() ? null : scriptBaseName(si.getScriptName());
            if (name != null && (SKIP_SCRIPTS.contains(name) || TRACKED_SCRIPTS.contains(name))) {
                chr.getQuestManager().setHiddenRecord(TUTORIAL_QR, String.format("%s;%d;%d;%d;%d", name,
                        chr.getFieldID(), destinationMap, chr.getJob(), chr.getLevel()));
                return;
            }
        }
    }

    private static void noteTutorialLeft(Char chr, Field newField) {
        if (chr == null || newField == null || chr.getQuestManager() == null) {
            return;
        }
        String[] rec = tutorialRecord(chr);
        if (rec == null) {
            return;
        }
        boolean town = newField.getInfo() != null && newField.getInfo().isTown()
                && !FieldConstants.isEventBlockField(newField.getId());
        if (town || String.valueOf(newField.getId()).equals(rec[2]) || !sameTutorialJob(chr, rec)) {
            chr.getQuestManager().setHiddenRecord(TUTORIAL_QR, "");
        }
    }

    /** @return the record's parts (script, map, destination, job, level), or null */
    private static String[] tutorialRecord(Char chr) {
        String rec = chr.getQuestManager().getHiddenRecord(TUTORIAL_QR);
        if (rec == null || rec.isEmpty()) {
            return null;
        }
        String[] parts = rec.split(";");
        return parts.length == 5 ? parts : null;
    }

    /**
     * @return the tutorial script to run again for its skip, or null when the character isn't in a class tutorial
     * any more (reached a town or the skip's destination, advanced past its job, or levelled well past it)
     */
    private static String[] pendingTutorial(Char chr) {
        String[] rec = tutorialRecord(chr);
        Field field = chr.getField();
        if (rec == null || field == null) {
            return null;
        }
        try {
            int dest = Integer.parseInt(rec[2]);
            int level = Integer.parseInt(rec[4]);
            boolean town = field.getInfo() != null && field.getInfo().isTown()
                    && !FieldConstants.isEventBlockField(field.getId());
            if (town || field.getId() == dest || !sameTutorialJob(chr, rec) || chr.getLevel() > level + 10) {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return rec;
    }

    /** @return false once the character advanced past the job it had when the tutorial started (the tutorial ended) */
    private static boolean sameTutorialJob(Char chr, String[] rec) {
        try {
            return chr.getJob() == Integer.parseInt(rec[3]) || JobConstants.isBeginnerJob(chr.getJob())
                    || chr.getLevel() < 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** For tutorial scripts whose skip sits behind a check that only passes on the first visit. */
    public static boolean isSkipRun(Char chr) {
        return chr != null && skipRuns.contains(chr.getId());
    }

    /**
     * Express picked in @menu (or playStyle.py) while still in a class tutorial: runs that tutorial's first script
     * again so its own skip happens (levels, quests, skills, items, the warp), once the open dialog has closed.
     *
     * @return whether a skip was started
     */
    private static boolean startTutorialSkip(Char chr) {
        if (chr == null || chr.getQuestManager() == null || expressClosed(chr) || effective(chr) != PlayStyle.EXPRESS) {
            return false;
        }
        String[] rec = pendingTutorial(chr);
        if (rec == null || TRACKED_SCRIPTS.contains(rec[0])) {
            return false;
        }
        chr.chatMessage("Express: skipping your tutorial as soon as this window closes.");
        runTutorialSkip(chr, rec[0], 0);
        return true;
    }

    private static void runTutorialSkip(Char chr, String script, int tries) {
        EventManager.addEvent(() -> {
            try {
                if (!chr.isOnline() || chr.getClient() == null || chr.getField() == null) {
                    return;
                }
                var sm = chr.getScriptManager();
                if (sm.isActive(ScriptType.Npc) || sm.isActive(ScriptType.Quest) || sm.isWaitingForAnswer()) {
                    if (tries < 120) {
                        runTutorialSkip(chr, script, tries + 1); // the @menu window is still open
                    }
                    return;
                }
                if (pendingTutorial(chr) == null) {
                    return;
                }
                skipRuns.add(chr.getId());
                EventManager.addEvent(() -> skipRuns.remove(chr.getId()), 30_000);
                log.info(String.format("%s chose Express during the tutorial: running %s's skip", chr.getName(),
                        script));
                sm.startScript(chr.getFieldID(), script, ScriptType.Field);
            } catch (Exception e) {
                skipRuns.remove(chr.getId());
                log.error("Could not skip the tutorial of " + chr.getName(), e);
            }
        }, tries == 0 ? 500 : 1000);
    }
}
