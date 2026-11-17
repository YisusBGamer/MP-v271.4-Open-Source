package net.swordie.ms.client.character;

import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.scripts.ScriptType;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.client.character.quest.QuietQuests;
import net.swordie.ms.client.character.quest.QuestNpcVisibility;
import net.swordie.ms.client.character.quest.requirement.QuestStartMinStatRequirement;
import net.swordie.ms.client.character.quest.requirement.QuestStartRequirement;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.loaders.QuestData;
import net.swordie.ms.loaders.containerclasses.QuestInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * Skipping the Arcane River storylines. The client opens the Arcane Symbol slots by story progress, so a player who
 * doesn't want the story can have each area's story quests completed and then progress by level and Arcane Power.
 * <ul>
 * <li>Spiegelmann (Arcane River Express Pass, 3003146) offers it in every Arcane River town
 * (scripts/npc/3003146.py). The client shows him only once the Vanishing Journey story reaches its last quest (34120),
 * so before that Chief Legato in Nameless Town offers it instead (scripts/npc/3003111.py).</li>
 * <li>The first time a 5th job character of level 200+ that isn't Classic comes to Nameless Town with the Vanishing Journey
 * story still open, Chief Legato offers to skip it (scripts/npc/arcaneSkipLegato.py, {@link #offerOnArrival}); he also offers the
 * skip whenever he's talked to.</li>
 * <li>A 5th job character without a Vanishing Journey Arcane Symbol gets the basic one once
 * ({@link #giveBasicSymbolIfMissing}): the 5th job a job path unlocks completes A Greater Power (1466) without the
 * symbol it normally gives.</li>
 * <li>The basic "Arcane Symbol" (1712000, from A Greater Power) can't be equipped once quest 34120 is complete: the
 * client refuses it without asking the server. The Vanishing Journey story swaps it for the real Vanishing Journey symbol (1712001)
 * when Kao's symbol is picked up (field/enter_450001250.py, quest 34120). Once quest 34120 is complete, every basic
 * symbol is swapped for a Vanishing Journey symbol with the same level and growth
 * ({@link #upgradeBasicSymbols}: after a skip, on login and on level up).</li>
 * <li>When a level up reaches the next area's level while the player is still on an earlier area's story, Spiegelmann
 * asks once whether to skip the rest and the new area's story and go there (scripts/npc/arcaneSkipPrompt.py; bots
 * keep the new area's story). Express players aren't asked: reaching an area's level (or Nameless Town) skips every
 * reached area's story, the new one included ({@link #autoSkip}). The client opens an area's symbol slot only once
 * its last story quest is done (Etc.wz/SymbolInfo.img Arcane/addLine), hence the new area too. Classic characters
 * too, as it only skips areas they've outleveled; nobody in the Default new player mode. The player takes and
 * deals no damage while that prompt is open (see {@link #startPrompt}).</li>
 * </ul>
 * <p>
 * An area can be skipped once the character has the 5th job and the area's level; Classic characters (and everyone
 * in the Default new player mode) only once they've outleveled it, i.e. reached the next area's level
 * ({@link #getSkipLevel}), when they talk to Spiegelmann or Legato as well. Its story quests are completed
 * without their rewards (no EXP, mesos or items), except that the area's Arcane Symbol is given if the character has
 * none (the story normally gives it). Story quests that need a higher level than the character has are left for later:
 * talking to Spiegelmann again completes them once the level is reached. Daily quests, boss quests and the side stories
 * (Reverse City, Yum Yum Island, Lucid's Subconscious Crack, ...) are not part of it.
 */
public final class ArcaneRiverSkipModule {

    private static final Logger log = LogManager.getLogger(ArcaneRiverSkipModule.class);

    public static final class Area {
        private final String name;
        private final int level;
        private final int symbolId;
        private final int townMap;
        private final int[] quests;
        /** job / 100 of the class the story belongs to (its beginner job included), or empty for every class */
        private final int[] jobBranches;

        Area(String name, int level, int symbolId, int townMap, int[] quests, int... jobBranches) {
            this.name = name;
            this.level = level;
            this.symbolId = symbolId;
            this.townMap = townMap;
            this.quests = quests;
            this.jobBranches = jobBranches;
        }

        boolean allows(Char chr) {
            if (jobBranches.length == 0) {
                return true;
            }
            // a class's hometown story: only once past its 2nd job, so no job advancement quest is skipped
            if (chr.getJob() % 100 < 10) {
                return false;
            }
            for (int b : jobBranches) {
                if (chr.getJob() / 100 == b) {
                    return true;
                }
            }
            return false;
        }

        public int getTownMap() {
            return townMap;
        }

        public String getName() {
            return name;
        }

        public int getLevel() {
            return level;
        }

        public int getSymbolId() {
            return symbolId;
        }

        public int[] getQuests() {
            return quests.clone();
        }
    }

    /** resources/data/story-skip-areas.tsv, read once: callers compare the region lists by identity */
    private static final DataTable AREA_TABLE = DataTable.read("story-skip-areas");

    private static List<Area> areas(String region) {
        List<Area> list = new ArrayList<>();
        for (DataTable.Row r : AREA_TABLE.rows()) {
            if (r.str("region").trim().equals(region)) {
                list.add(new Area(r.str("name").trim(), r.i("level"), r.i("symbol"), r.i("town"), r.ints("quests"),
                        r.ints("jobs")));
            }
        }
        return List.copyOf(list);
    }

    /** Quest.wz areas 207, 208, 210, 213, 217 and 220, without dailies, side stories, boss and event quests */
    public static final List<Area> AREAS = areas("arcane");

    /**
     * Arcane River stories without a symbol slot: Reverse City, Yum Yum Island, Sellas and Tenebris. Each is the whole
     * v271 Quest.wz prerequisite chain to the area's last quest; the maps' info/qrLimit quests are in it, which the
     * client checks before a Hyper Teleport Rock or World Map move. Limina ends with Aftermath (35815, 39701), which
     * Cernium's Convergence needs. Express only (auto skip on arrival and catch-up), no prompts.
     */
    public static final List<Area> ARCANE_SIDE_AREAS = areas("side");

    /**
     * Stories outside Arcane River and Grandis whose maps have an info/qrLimit quest (Hyper Teleport Rock, World Map):
     * Fox Valley, and the hometowns of Lara (Narin), Adele (Ristonia), Kain (Toolen City) and Hoyoung (Handsome Monk's
     * Temple), for that class only. Each is the v271 prerequisite chain of every qrLimit quest of its maps. No 5th job
     * needed. Express only.
     */
    public static final List<Area> TOWN_AREAS = areas("town");

    /** @return whether skipping this region's stories needs the 5th job */
    private static boolean needsFifthJob(List<Area> areas) {
        return areas != TOWN_AREAS;
    }

    /**
     * Grandis: each area's story chain (Quest.wz areas 284-291) up to the quest that opens its symbol slot
     * (Etc.wz/SymbolInfo.img Authentic/addLine, Grand Sacred: GrandAuthentic/addLine), plus the auto-completed marker
     * quest after it and the side story the next area's first quest needs (Cernium (After), Karote), so a player who
     * skips one area can play the next. Cernium starts at 39801 as CerniumStoryModule starts it. Players only: bots
     * play these stories.
     */
    public static final List<Area> GRANDIS_AREAS = areas("grandis");

    private static String regionName(List<Area> areas) {
        return areas == GRANDIS_AREAS ? "Grandis" : areas == ARCANE_SIDE_AREAS ? "Tenebris"
                : areas == TOWN_AREAS ? "town" : "Arcane River";
    }

    /** chain quests already reported as missing from the quest data (once per server run) */
    private static final java.util.Set<Integer> warnedMissing = ConcurrentHashMap.newKeySet();

    private ArcaneRiverSkipModule() {
    }

    public static boolean hasFifthJob(Char chr) {
        return chr.getQuestManager().hasQuestCompleted(QuestConstants.FIFTH_JOB_QUEST);
    }

    private static boolean levelAllows(Char chr, int questId) {
        QuestInfo qi = QuestData.getQuestInfoById(questId);
        if (qi == null) {
            if (warnedMissing.add(questId)) {
                log.warn("Story skip: quest " + questId + " is missing from the server's quest data; it's left out.");
            }
            return false;
        }
        for (QuestStartRequirement req : qi.getQuestStartRequirements()) {
            if (req instanceof QuestStartMinStatRequirement && !req.hasRequirements(chr)) {
                return false;
            }
        }
        return true;
    }

    /** @return the story quests of the area that the character could have completed by now and hasn't */
    private static List<Integer> getSkippable(Char chr, Area area) {
        List<Integer> list = new ArrayList<>();
        if (chr.getLevel() < area.level || !area.allows(chr)) {
            return list;
        }
        QuestManager qm = chr.getQuestManager();
        for (int q : area.quests) {
            if (!qm.hasQuestCompleted(q) && levelAllows(chr, q)) {
                list.add(q);
            }
        }
        return list;
    }

    /**
     * The level from which the last area (Esfera) counts as outleveled: Moonbridge's, the Arcane River area after it
     * (its story isn't part of the skip).
     */
    public static final int AFTER_LAST_AREA_LEVEL = 245;

    /** @return the level at which an area counts as outleveled: the next area's level */
    public static int getOutleveledLevel(int areaIndex) {
        return areaIndex + 1 < AREAS.size() ? AREAS.get(areaIndex + 1).level : AFTER_LAST_AREA_LEVEL;
    }

    /**
     * @return the level from which this character can skip the area: the area's own level, or the next area's for
     * characters that only skip what they've outleveled ({@link PlayStyleModule#limitsArcaneSkipToOutleveled})
     */
    public static int getSkipLevel(Char chr, Area area) {
        int i = AREAS.indexOf(area);
        return i >= 0 && PlayStyleModule.limitsArcaneSkipToOutleveled(chr) ? getOutleveledLevel(i) : area.level;
    }

    /**
     * @return whether any of the area's story quests is still open, whatever the character's level (for Spiegelmann's
     * list: "story done" vs "needs level")
     */
    public static boolean hasStoryLeft(Char chr, Area area) {
        QuestManager qm = chr.getQuestManager();
        for (int q : area.quests) {
            if (!qm.hasQuestCompleted(q)) {
                return true;
            }
        }
        return false;
    }

    /** @return the areas the character can skip now (skip level reached, story quests left) */
    public static List<Area> getSkippableAreas(Char chr) {
        List<Area> list = new ArrayList<>();
        if (!ServerFeatures.on(ServerFeatures.Feature.ARCANE_SKIP) || !hasFifthJob(chr)) {
            return list;
        }
        for (Area area : AREAS) {
            if (chr.getLevel() >= getSkipLevel(chr, area) && !getSkippable(chr, area).isEmpty()) {
                list.add(area);
            }
        }
        return list;
    }

    private static boolean ownsSymbol(Char chr, int symbolId) {
        for (var inv : List.of(chr.getEquippedInventory(), chr.getEquipInventory())) {
            for (Item item : inv.getItems()) {
                int id = item.getItemId() == BASIC_SYMBOL ? AREAS.get(0).symbolId : item.getItemId();
                if (id == symbolId) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Completes the story quests of the given areas (and of every area before them that the character can skip).
     *
     * @return a message for the player
     */
    public static String skip(Char chr, int lastAreaIndex) {
        return skip(chr, lastAreaIndex, false);
    }

    /**
     * @param reachedOnly allow every area whose own level is reached, also for characters that otherwise only skip
     *                    what they've outleveled (the level-up prompt and the Express auto-skip include the new area,
     *                    so its symbol slot opens)
     */
    public static String skip(Char chr, int lastAreaIndex, boolean reachedOnly) {
        String msg = skip(chr, AREAS, lastAreaIndex, reachedOnly);
        QuietQuests.reloadSoon(chr); // asked in a dialog: the map reloads with the records once it closes
        return msg;
    }

    /**
     * The quest records go out with the next CharacterData (a map change; {@link QuietQuests}), not as a "Quest
     * complete!" notice each.
     *
     * @param areas {@link #AREAS} or {@link #GRANDIS_AREAS}
     */
    private static synchronized String skip(Char chr, List<Area> areas, int lastAreaIndex, boolean reachedOnly) {
        QuietQuests.begin(chr);
        try {
            return skipAreas(chr, areas, lastAreaIndex, reachedOnly);
        } finally {
            QuietQuests.end(chr, false);
        }
    }

    private static String skipAreas(Char chr, List<Area> areas, int lastAreaIndex, boolean reachedOnly) {
        String region = regionName(areas);
        if (needsFifthJob(areas) && !hasFifthJob(chr)) {
            return "You need to have completed your 5th job advancement first.";
        }
        if (!ServerFeatures.on(ServerFeatures.Feature.ARCANE_SKIP)) {
            return "Skipping the " + region + " story isn't available right now.";
        }
        if (lastAreaIndex < 0 || lastAreaIndex >= areas.size()) {
            return "That isn't an " + region + " area.";
        }
        int skipLevel = reachedOnly ? areas.get(lastAreaIndex).level : getSkipLevel(chr, areas.get(lastAreaIndex));
        if (chr.getLevel() < skipLevel) {
            // areas are in level order, so every area before this one is allowed too
            return "You can skip the story of " + areas.get(lastAreaIndex).name + " from level " + skipLevel
                    + ", once you've outleveled it.";
        }
        QuestManager qm = chr.getQuestManager();
        List<String> done = new ArrayList<>();
        List<String> symbols = new ArrayList<>();
        boolean giveSymbols = ServerFeatures.on(ServerFeatures.Feature.ARCANE_SKIP_SYMBOLS);
        int count = 0;
        String symbolKind = areas == GRANDIS_AREAS ? "Sacred Symbol" : "Arcane Symbol";
        for (int i = 0; i <= lastAreaIndex; i++) {
            Area area = areas.get(i);
            List<Integer> quests = getSkippable(chr, area);
            if (quests.isEmpty()) {
                continue;
            }
            boolean cernium = area.symbolId == CerniumStoryModule.SACRED_SYMBOL_CERNIUM;
            if (cernium && "1".equals(qm.getHiddenRecord(CerniumStoryModule.SYMBOL_GIVEN_QR))) {
                // already given by the Cernium story
            } else if (giveSymbols && area.symbolId != 0 && !ownsSymbol(chr, area.symbolId)) {
                if (!chr.canHold(area.symbolId, 1)) {
                    return "Please make a free slot in your Equip inventory for the " + area.name + " "
                            + symbolKind + ", then try again."
                            + (done.isEmpty() ? "" : " (Skipped so far: " + String.join(", ", done) + ".)");
                }
                chr.addItemToInventory(area.symbolId, 1);
                symbols.add(area.name);
            }
            if (cernium) {
                qm.setHiddenRecord(CerniumStoryModule.SYMBOL_GIVEN_QR, "1"); // the skip took the story's place
            }
            for (int q : quests) {
                if (qm.hasQuestInProgress(q)) {
                    qm.removeQuest(q); // forfeit first (takes back its start items), then complete
                }
                if (qm.completeQuestSilently(q)) {
                    count++;
                }
            }
            done.add(area.name);
        }
        if (areas == AREAS) {
            int swapped = upgradeBasicSymbols(chr); // the basic symbol becomes one that can be equipped
            if (swapped > 0 && !symbols.contains(AREAS.get(0).name)) {
                symbols.add(AREAS.get(0).name);
            }
        }
        QuestNpcVisibility.refresh(chr);
        log.info(String.format("Char %d (%s) skipped the %s story: %s (%d quests).", chr.getId(),
                chr.getName(), region, String.join(", ", done), count));
        if (done.isEmpty()) {
            return "There is nothing left to skip for now.";
        }
        String msg = "Done: the story of " + String.join(", ", done) + " is complete (" + count + " quests).";
        if (!symbols.isEmpty()) {
            msg += " You received the " + symbolKind + " of " + String.join(", ", symbols) + ".";
        }
        msg += " Change channel or maps if a symbol slot or an NPC doesn't show up right away.";
        return msg;
    }

    // Nameless Town offer and the basic symbol ----------------------------------------------------------------------

    public static final int LEGATO = 3003111;
    public static final int NAMELESS_TOWN = 450001000;
    public static final int BASIC_SYMBOL = 1712000;
    /** A Greater Power: gives the basic Arcane Symbol */
    public static final int BASIC_SYMBOL_QUEST = 1466;

    /** @return whether the key was newly marked in the character's skip record (false: it already was) */
    private static boolean markOnce(Char chr, String key) {
        QuestManager qm = chr.getQuestManager();
        String done = qm.getHiddenRecord(GameConstants.ARCANE_SKIP_PROMPT_QR);
        if (done != null && ("," + done + ",").contains("," + key + ",")) {
            return false;
        }
        qm.setHiddenRecord(GameConstants.ARCANE_SKIP_PROMPT_QR, done == null || done.isEmpty() ? key : done + "," + key);
        return true;
    }

    /**
     * Called when a character enters Nameless Town: the first time it's level 200+ with the 5th job and the Vanishing
     * Journey story still open, Chief Legato offers to skip it. Without the 5th job, it's told once where to get it
     * (Arcane Symbols need it).
     */
    public static void offerOnArrival(Char chr) {
        try {
            if (!ServerFeatures.on(ServerFeatures.Feature.ARCANE_SKIP) || chr.getLevel() < AREAS.get(0).level
                    || chr.getInstance() != null || chr.getFieldID() != NAMELESS_TOWN) {
                return;
            }
            if (!hasFifthJob(chr)) {
                if (markOnce(chr, "5th")) {
                    chr.chatMessage(ChatType.SystemNotice, "Arcane Symbols only work after the 5th job advancement. "
                            + "It starts with the Memory Keeper in the Temple of Time (watch for the quest light bulb).");
                }
                return;
            }
            if (autoSkips(chr, AREAS)) {
                autoSkip(chr, AREAS, chr.getLevel());
                return;
            }
            if (ContentAccess.keepsStory(chr, ContentAccess.ARCANE)) {
                return; // kept with @story: no offer either
            }
            if (!PlayStyleModule.offersArcaneSkipOnArrival(chr)) {
                return; // Classic (and Default mode) get an area's offer once they've outleveled it (onLevelUp)
            }
            if (getSkippable(chr, AREAS.get(0)).isEmpty() || !markOnce(chr, "0")) {
                return;
            }
            EventManager.addEvent(() -> {
                if (chr.getClient() == null || chr.getField() == null || chr.getFieldID() != NAMELESS_TOWN) {
                    return;
                }
                boolean busy = chr.getHP() <= 0 || chr.isTalkingToNpc() || chr.getShop() != null
                        || chr.getMiniRoom() != null || chr.isInTrunk() || prompts.containsKey(chr.getId());
                if (busy) {
                    chr.chatMessage(ChatType.SystemNotice, "If you'd like to skip the Arcane River story, talk to "
                            + "Chief Legato in Nameless Town any time.");
                    return;
                }
                chr.getScriptManager().startScript(LEGATO, "arcaneSkipLegato", ScriptType.Npc);
            }, 1500);
        } catch (Exception e) {
            log.error("Could not check the Nameless Town skip offer for " + chr.getName(), e);
        }
    }

    /**
     * Gives the basic Arcane Symbol (Vanishing Journey) once to a 5th job character that completed A Greater Power
     * (1466) but has no Vanishing Journey symbol: the 5th job a job path unlocks completes that quest without its
     * reward. Checked on login and level up (LevelUnlockModule).
     */
    public static void giveBasicSymbolIfMissing(Char chr) {
        QuestManager qm = chr.getQuestManager();
        if (!hasFifthJob(chr) || !qm.hasQuestCompleted(BASIC_SYMBOL_QUEST) || ownsSymbol(chr, AREAS.get(0).symbolId)) {
            return;
        }
        String done = qm.getHiddenRecord(GameConstants.ARCANE_SKIP_PROMPT_QR);
        if (done != null && ("," + done + ",").contains(",basic,")) {
            return;
        }
        if (!chr.canHold(BASIC_SYMBOL, 1)) {
            chr.chatMessage(ChatType.SystemNotice, "Your basic Arcane Symbol is waiting: make a free slot in your "
                    + "Equip inventory and change maps or log in again to receive it.");
            return;
        }
        markOnce(chr, "basic");
        chr.addItemToInventory(BASIC_SYMBOL, 1);
        log.info(String.format("Char %d (%s) received the basic Arcane Symbol it was missing.", chr.getId(),
                chr.getName()));
        chr.chatMessage(ChatType.SystemNotice, "You received the basic Arcane Symbol (Vanishing Journey). Equip it "
                + "in the Arcane Symbol tab of the Equipment window.");
    }

    /**
     * Swaps every basic Arcane Symbol (1712000, which the client won't equip) for an Arcane Symbol: Vanishing
     * Journey (1712001) with the same level and growth, once the character has the Vanishing Journey symbol quest
     * (34120) done - which is when the story makes the same swap. An equipped one is swapped in its slot. A copy of a symbol that's already equipped merges
     * into it (SymbolAutoMergeModule) as any other symbol would.
     *
     * @return how many symbols were swapped
     */
    public static int upgradeBasicSymbols(Char chr) {
        if (!chr.getQuestManager().hasQuestCompleted(SPIEGELMANN_SHOWN_QUEST)) {
            return 0;
        }
        List<Item> basics = new ArrayList<>();
        for (Item item : chr.getEquipInventory().getItems()) {
            if (item.getItemId() == BASIC_SYMBOL && item instanceof net.swordie.ms.client.character.items.Equip) {
                basics.add(item);
            }
        }
        int swapped = 0;
        // an equipped one too, in its slot: the client won't put a basic symbol back on once 34120 is done
        List<net.swordie.ms.client.character.items.Equip> worn = new ArrayList<>();
        for (Item item : chr.getEquippedInventory().getItems()) {
            if (item.getItemId() == BASIC_SYMBOL && item instanceof net.swordie.ms.client.character.items.Equip) {
                worn.add((net.swordie.ms.client.character.items.Equip) item);
            }
        }
        for (var old : worn) {
            Item copy = net.swordie.ms.loaders.ItemData.getItemDeepCopy(AREAS.get(0).symbolId);
            if (!(copy instanceof net.swordie.ms.client.character.items.Equip)) {
                return swapped;
            }
            var symbol = (net.swordie.ms.client.character.items.Equip) copy;
            int slot = old.getBagIndex();
            chr.consumeItemFull(old);
            symbol.setBagIndex(slot);
            chr.getEquippedInventory().addItem(symbol);
            symbol.initSymbolStats(old.getSymbolLevel(), old.getSymbolExp(), chr.getJob());
            symbol.updateToChar(chr);
            swapped++;
        }
        if (swapped > 0) {
            chr.getEquippedInventory().recalcBaseStats(chr);
            chr.initBaseStats();
        }
        for (Item item : basics) {
            var old = (net.swordie.ms.client.character.items.Equip) item;
            Item copy = net.swordie.ms.loaders.ItemData.getItemDeepCopy(AREAS.get(0).symbolId);
            if (!(copy instanceof net.swordie.ms.client.character.items.Equip)) {
                return swapped;
            }
            var symbol = (net.swordie.ms.client.character.items.Equip) copy;
            symbol.setSymbolLevel(old.getSymbolLevel());
            symbol.setSymbolExp(old.getSymbolExp());
            chr.consumeItemFull(old);
            chr.addItemToInventory(symbol); // sets its stats from the level and growth; may merge
            swapped++;
        }
        if (swapped > 0) {
            log.info(String.format("Char %d (%s): %d basic Arcane Symbol(s) became Vanishing Journey symbols.",
                    chr.getId(), chr.getName(), swapped));
            chr.chatMessage(ChatType.SystemNotice, "Your basic Arcane Symbol became an Arcane Symbol: Vanishing "
                    + "Journey" + (worn.size() == swapped ? "." : ". Equip it in the Arcane Symbol tab of the Equipment "
                    + "window (double-click it)."));
        }
        return swapped;
    }

    // Level-up prompt ------------------------------------------------------------------------------------------------

    public static final int SPIEGELMANN = 3003146;
    /** the last Vanishing Journey story quest: the client shows Spiegelmann once it's started */
    public static final int SPIEGELMANN_SHOWN_QUEST = 34120;

    private static final class Prompt {
        final List<Area> areas;
        final int nextArea;
        final Option protection;
        ScheduledFuture<?> timeout;

        Prompt(List<Area> areas, int nextArea, Option protection) {
            this.areas = areas;
            this.nextArea = nextArea;
            this.protection = protection;
        }
    }

    private static final Map<Integer, Prompt> prompts = new ConcurrentHashMap<>();

    /** @return whether the client shows Spiegelmann (3003146) to this character (Npc.wz condition on quest 34120) */
    public static boolean isSpiegelmannShown(Char chr) {
        QuestManager qm = chr.getQuestManager();
        return qm.hasQuestInProgress(SPIEGELMANN_SHOWN_QUEST) || qm.hasQuestCompleted(SPIEGELMANN_SHOWN_QUEST);
    }

    private static boolean hasStarted(Char chr, Area area) {
        QuestManager qm = chr.getQuestManager();
        for (int q : area.quests) {
            if (qm.hasQuestInProgress(q) || qm.hasQuestCompleted(q)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Called on every level up. Express players: reaching an Arcane River or Grandis area's level skips the story of
     * every reached area of that region ({@link #autoSkip}). Others: when the new level reaches the next area's level
     * while the player is still on the story of an earlier area, Spiegelmann offers (once per area) to skip the rest
     * and go to the new area. Grandis is for players only (bots play its stories).
     */
    public static void onLevelUp(Char chr, int oldLevel, int newLevel) {
        try {
            if (!ServerFeatures.on(ServerFeatures.Feature.ARCANE_SKIP)
                    || !PlayStyleModule.offersArcaneSkipOnLevelUp(chr)) {
                return; // Default mode: no automatic offers (Spiegelmann/Legato offer outleveled areas when talked to)
            }
            boolean bot = net.swordie.ms.ext.ServerHooks.isAutomated(chr);
            if (!bot) {
                onLevelUp(chr, TOWN_AREAS, oldLevel, newLevel);
            }
            if (!hasFifthJob(chr)) {
                return;
            }
            for (List<Area> areas : bot ? List.of(AREAS) : List.of(AREAS, ARCANE_SIDE_AREAS, GRANDIS_AREAS)) {
                onLevelUp(chr, areas, oldLevel, newLevel);
            }
        } catch (Exception e) {
            log.error("Could not check the story skip prompt for " + chr.getName(), e);
        }
    }

    private static void onLevelUp(Char chr, List<Area> areas, int oldLevel, int newLevel) {
        if (autoSkips(chr, areas)) {
            for (Area a : areas) {
                if (oldLevel < a.level && a.level <= newLevel) {
                    int lv = newLevel;
                    EventManager.addEvent(() -> autoSkip(chr, areas, lv), 1500); // after the level up is handled
                    break;
                }
            }
            return;
        }
        if (areas == ARCANE_SIDE_AREAS || areas == TOWN_AREAS) {
            return; // Express only
        }
        int next = -1;
        for (int i = 1; i < areas.size(); i++) {
            int lv = areas.get(i).level;
            if (oldLevel < lv && lv <= newLevel) {
                next = i;
            }
        }
        if (next < 0) {
            return;
        }
        boolean started = false;
        boolean unfinished = false;
        for (int i = 0; i < next; i++) {
            started |= hasStarted(chr, areas.get(i));
            unfinished |= !getSkippable(chr, areas.get(i)).isEmpty();
        }
        if (!started || !unfinished || hasStarted(chr, areas.get(next))) {
            return; // not on the region's story, done with it, or already on the new area's story
        }
        // once per area (a rebirth or a second level up to the same level doesn't ask again)
        if (!markOnce(chr, (areas == GRANDIS_AREAS ? "g" : "") + next)) {
            return;
        }
        int area = next;
        // after the level up has been handled (quest scripts, level up effects, ...)
        EventManager.addEvent(() -> tryOpenPrompt(chr, areas, area), 1500);
    }

    /**
     * @return whether this character's stories are skipped without asking: players on Express (bots keep their
     * prompts and offers, which they answer themselves)
     */
    private static boolean autoSkips(Char chr, List<Area> areas) {
        // Tenebris and the side stories follow the Arcane River choice of @story
        return ContentAccess.autoSkipsStory(chr, areas == GRANDIS_AREAS ? ContentAccess.GRANDIS
                : areas == TOWN_AREAS ? "towns" : ContentAccess.ARCANE);
    }

    /**
     * Express: skips the story of every area of the region whose level the character has reached, the newest area
     * included, so each reached area's symbol slot is open.
     */
    private static void autoSkip(Char chr, List<Area> areas, int level) {
        try {
            if (chr.getClient() == null || !ServerFeatures.on(ServerFeatures.Feature.ARCANE_SKIP)
                    || (needsFifthJob(areas) && !hasFifthJob(chr))) {
                return;
            }
            int last = -1;
            boolean any = false;
            for (int i = 0; i < areas.size(); i++) {
                if (areas.get(i).level <= level) {
                    last = i;
                    any |= !getSkippable(chr, areas.get(i)).isEmpty();
                }
            }
            if (last < 0 || !any) {
                return;
            }
            String msg = skip(chr, areas, last, true);
            chr.chatMessage(ChatType.SystemNotice, "[Express] " + msg);
        } catch (Exception e) {
            log.error("Could not skip the " + regionName(areas) + " story for " + chr.getName(), e);
        }
    }

    /**
     * Express catch-up (login, switching to Express): skips every reached area's story that is still open, e.g. for
     * characters past an area whose story wasn't part of the skip yet. The map reloads once idle with the records.
     */
    public static void catchUp(Char chr) {
        try {
            if (chr == null || chr.getClient() == null || chr.getField() == null || chr.getQuestManager() == null
                    || !ServerFeatures.on(ServerFeatures.Feature.ARCANE_SKIP)
                    || net.swordie.ms.ext.ServerHooks.isAutomated(chr)) {
                return;
            }
            boolean fifth = hasFifthJob(chr);
            boolean any = false;
            for (List<Area> areas : List.of(TOWN_AREAS, AREAS, ARCANE_SIDE_AREAS, GRANDIS_AREAS)) {
                if (autoSkips(chr, areas) && (fifth || !needsFifthJob(areas))) {
                    for (Area a : areas) {
                        any |= chr.getLevel() >= a.level && !getSkippable(chr, a).isEmpty();
                    }
                }
            }
            if (!any) {
                return;
            }
            for (List<Area> areas : List.of(TOWN_AREAS, AREAS, ARCANE_SIDE_AREAS, GRANDIS_AREAS)) {
                if (autoSkips(chr, areas) && (fifth || !needsFifthJob(areas))) {
                    autoSkip(chr, areas, chr.getLevel());
                }
            }
            QuietQuests.reloadSoon(chr);
        } catch (Exception e) {
            log.error("Could not catch up the Express story skip for " + chr.getName(), e);
        }
    }

    /** where the player can skip a region's story later (Grandis has no NPC for it) */
    private static String laterHint(Char chr, List<Area> areas) {
        if (areas == GRANDIS_AREAS) {
            return "";
        }
        return " You can still skip it by talking to " + (isSpiegelmannShown(chr)
                ? "Spiegelmann in any Arcane River town." : "Chief Legato in Nameless Town.");
    }

    private static void tryOpenPrompt(Char chr, List<Area> areas, int next) {
        if (chr.getClient() == null || chr.getField() == null) {
            return; // logged out
        }
        Area area = areas.get(next);
        boolean busy = chr.getInstance() != null || chr.getHP() <= 0 || chr.isTalkingToNpc() || chr.getShop() != null
                || chr.getMiniRoom() != null || chr.isInTrunk() || prompts.containsKey(chr.getId());
        if (busy) {
            if (areas == GRANDIS_AREAS) {
                markUnoffered(chr, "g" + next); // asked again at the next level up
                return;
            }
            chr.chatMessage(ChatType.SystemNotice, String.format("You've reached level %d. If you'd like to skip the "
                    + "rest of the Arcane River story and head to %s, talk to %s any time.", area.level, area.name,
                    isSpiegelmannShown(chr) ? "Spiegelmann in any Arcane River town"
                            : "Chief Legato in Nameless Town"));
            return;
        }
        startPrompt(chr, areas, next);
        chr.getScriptManager().startScript(SPIEGELMANN, "arcaneSkipPrompt", ScriptType.Npc);
        if (!chr.getScriptManager().isActive(ScriptType.Npc)) {
            endPrompt(chr); // the dialog couldn't open: no protection without it
        }
    }

    /** Takes a key out of the character's skip record again. */
    private static void markUnoffered(Char chr, String key) {
        QuestManager qm = chr.getQuestManager();
        String done = qm.getHiddenRecord(GameConstants.ARCANE_SKIP_PROMPT_QR);
        if (done == null) {
            return;
        }
        List<String> keys = new ArrayList<>(List.of(done.split(",")));
        if (keys.remove(key)) {
            qm.setHiddenRecord(GameConstants.ARCANE_SKIP_PROMPT_QR, String.join(",", keys));
        }
    }

    /**
     * Opens a prompt session: until it ends (answered, or {@link GameConstants#ARCANE_SKIP_PROMPT_SECONDS} pass), the
     * player can't be damaged (IndieNotDamaged for the client, {@link Char#isInvincible()} and {@link Char#damage} on
     * the server) and does no damage to monsters ({@link net.swordie.ms.life.mob.Mob#damage}), so the prompt can't be
     * used to fight safely, e.g. with summons. Only a level up can open it, once per area, never in instances.
     */
    private static void startPrompt(Char chr, List<Area> areas, int next) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option(1, 0, GameConstants.ARCANE_SKIP_PROMPT_SECONDS);
        Prompt prompt = new Prompt(areas, next, o);
        prompts.put(chr.getId(), prompt);
        chr.setSkipPromptProtected(true);
        tsm.putCharacterStatValue(CharacterTemporaryStat.IndieNotDamaged, o, true);
        tsm.sendSetStatPacket();
        prompt.timeout = EventManager.addEvent(() -> {
            if (prompts.get(chr.getId()) == prompt) {
                endPrompt(chr);
                if (chr.getClient() == null) {
                    return; // logged out meanwhile
                }
                chr.getScriptManager().dispose();
                chr.chatMessage(ChatType.SystemNotice, "Spiegelmann's offer timed out." + laterHint(chr, areas));
            }
        }, GameConstants.ARCANE_SKIP_PROMPT_SECONDS * 1000L);
    }

    /** Ends the prompt session (and its protection), if there is one. */
    public static void endPrompt(Char chr) {
        Prompt prompt = prompts.remove(chr.getId());
        chr.setSkipPromptProtected(false);
        if (prompt == null) {
            return;
        }
        if (prompt.timeout != null) {
            prompt.timeout.cancel(false);
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        var options = tsm.getOptions(CharacterTemporaryStat.IndieNotDamaged);
        if (options != null && options.contains(prompt.protection)) {
            tsm.removeIndieStat(CharacterTemporaryStat.IndieNotDamaged, prompt.protection);
            tsm.sendResetStatPacket();
        }
    }

    /** @return the area the open prompt offers to go to, or null if no prompt is open (e.g. it timed out) */
    public static Area getPromptArea(Char chr) {
        Prompt prompt = prompts.get(chr.getId());
        return prompt == null ? null : prompt.areas.get(prompt.nextArea);
    }

    /** @return the last area the open prompt would skip (the new one for players, the one before it for bots), or null */
    public static Area getPromptSkippedArea(Char chr) {
        Prompt prompt = prompts.get(chr.getId());
        return prompt == null ? null : prompt.areas.get(net.swordie.ms.ext.ServerHooks.isAutomated(chr)
                ? prompt.nextArea - 1 : prompt.nextArea);
    }

    /** @return the region of the open prompt ("Arcane River" or "Grandis"), or null */
    public static String getPromptRegion(Char chr) {
        Prompt prompt = prompts.get(chr.getId());
        return prompt == null ? null : regionName(prompt.areas);
    }

    /**
     * The player said yes: skips the story of every area up to and including the new one and goes to its town.
     *
     * @return a message for the player
     */
    public static String acceptPrompt(Char chr) {
        Prompt prompt = prompts.get(chr.getId());
        if (prompt == null) {
            return "This offer has expired." + laterHint(chr, AREAS);
        }
        endPrompt(chr);
        // players: the new area's story too, so its symbol slot opens; bots play the new area's story
        String msg = net.swordie.ms.ext.ServerHooks.isAutomated(chr)
                ? skip(chr, prompt.areas, prompt.nextArea - 1, false)
                : skip(chr, prompt.areas, prompt.nextArea, true);
        Area next = prompt.areas.get(prompt.nextArea);
        if (chr.getInstance() == null && chr.getHP() > 0) {
            chr.warp(next.townMap); // carries the records
        }
        QuietQuests.reloadSoon(chr);
        return msg + " Welcome to " + next.name + "!";
    }
}
