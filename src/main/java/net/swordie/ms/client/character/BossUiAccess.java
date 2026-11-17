package net.swordie.ms.client.character;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.jwzlib.WzFile;
import net.swordie.ms.jwzlib.WzObject;
import net.swordie.ms.jwzlib.WzProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * The boss window (Etc.wz/BossParty.img) only allows teleporting to a boss once its prerequisite quests
 * ("QuestDamand") are complete on the client. With the setting {@link ServerFeatures.Feature#BOSS_UI_IGNORE_QUESTS}
 * on, the server reports those quests as completed to the client only (nothing is saved), so every teleport button works.
 * <p>
 * The teleport destinations are also the only maps the boss window may warp to.
 */
public class BossUiAccess {

    /** Quests the boss window requires to be completed, from BossParty.img (QuestDamand). */
    public static final int[] REQUIRED_QUESTS = {
            30007, // Root Abyss: Pierre, Von Bon, Crimson Queen, Vellum
            1562, // Von Leon (or 3170)
            31179, // Arkarium
            38858, // Magnus (or 31833 with 31498, 31686, 34735)
            3521, // Pink Bean
            31152, // Cygnus
            33565, // Ursus
            17523, // Gollux
            65851, // Ranmaru
            34330, // Lucid
            3496, // OMNI-CLN
            3470, // Papulatus
            37871, // Will
            36772, // Verus Hilla
            35815, // Darknell, Black Mage
            35632, // Gloom
            39921, // Seren
            36013, // Guardian Angel Slime
            38214, // Kalos the Guardian
            38319, // First Adversary
            38401, // Kaling
            38702, // Limbo
            36996, // Baldrix
            36549, // Malefic Star
            36873, // Jupiter
    };

    /** Quest record values the boss window requires (QuestDamand_Sub): quest id -> {key, value}. */
    public static final Map<Integer, String[]> REQUIRED_RECORDS = Map.of(
            500663, new String[]{"BH_check", "clear"}, // Lotus
            500664, new String[]{"HOM_check", "clear"}, // Damien
            501184, new String[]{"boss", "1"} // Akechi Mitsuhide
    );

    /**
     * The lowest boss level (BossParty.img levelMin) at which the boss window asks for each quest or record. Characters
     * below it aren't sent the fake completion, otherwise the client unlocks follow-up auto-start quests (e.g. 3528
     * after 3521) and lists them as In Progress.
     */
    private static final Map<Integer, Integer> MIN_LEVEL = Map.ofEntries(
            Map.entry(30007, 125), Map.entry(1562, 125), Map.entry(31179, 140), Map.entry(38858, 115),
            Map.entry(3521, 160), Map.entry(31152, 165), Map.entry(33565, 100), Map.entry(17523, 180),
            Map.entry(65851, 120), Map.entry(34330, 220), Map.entry(3496, 180), Map.entry(3470, 115),
            Map.entry(37871, 235), Map.entry(36772, 250), Map.entry(35815, 255), Map.entry(35632, 245),
            Map.entry(39921, 265), Map.entry(36013, 210), Map.entry(38214, 265), Map.entry(38319, 270),
            Map.entry(38401, 275), Map.entry(38702, 285), Map.entry(36996, 290), Map.entry(36549, 280),
            Map.entry(36873, 295),
            Map.entry(500663, 190), Map.entry(500664, 190), Map.entry(501184, 200));

    /**
     * Whether the boss window's quest bypass is on for this character (setting boss_ui_ignore_quests, and not in the
     * Default new player mode). The client gets the fake completions with the character data (login, channel
     * change), so a change shows for a player after that.
     */
    public static boolean isBypassOn(Char chr) {
        // Express opens every boss on the server, so the client's own quest checks (boss window, lobby entry
        // portals such as Malefic Star's ymir_enterGate) have to agree
        return PlayStyleModule.bossUiBypass(chr) || ContentAccess.unlocksAll(chr);
    }

    /** Per character: the required quests its client was told are completed this session. */
    private static final Map<Integer, Set<Integer>> SHOWN = new java.util.concurrent.ConcurrentHashMap<>();

    /** On login: the character data just carried the completions due now. */
    public static void onLogin(Char chr) {
        if (chr == null || chr.getQuestManager() == null) {
            return;
        }
        Set<Integer> shown = java.util.concurrent.ConcurrentHashMap.newKeySet();
        shown.addAll(getQuestsToShowCompleted(chr));
        SHOWN.put(chr.getId(), shown);
    }

    /**
     * On map changes: tells the client about completions that became due since login (switched to Express, or
     * reached a boss's level), so the boss portals open without logging in again. Client only, nothing is saved.
     */
    public static void showNewlyDue(Char chr) {
        if (chr == null || chr.getQuestManager() == null) {
            return;
        }
        List<Integer> due = getQuestsToShowCompleted(chr);
        if (due.isEmpty()) {
            return;
        }
        Set<Integer> shown = SHOWN.computeIfAbsent(chr.getId(), k -> java.util.concurrent.ConcurrentHashMap.newKeySet());
        for (int questId : due) {
            if (!shown.add(questId)) {
                continue;
            }
            var fake = net.swordie.ms.loaders.QuestData.createQuestFromId(questId);
            if (fake == null) {
                continue;
            }
            fake.setStatus(net.swordie.ms.enums.QuestStatus.Completed);
            fake.setCompletedTime(net.swordie.ms.util.FileTime.currentTime());
            chr.write(net.swordie.ms.connection.packet.WvsContext.message(
                    net.swordie.ms.connection.packet.model.MessagePacket.questRecordMessage(fake)));
        }
    }

    /** Whether the character is high enough for the boss that asks for this quest or record. */
    private static boolean levelFor(Char chr, int questId) {
        return chr == null || chr.getLevel() >= MIN_LEVEL.getOrDefault(questId, 0);
    }

    /** Every map the boss window teleports to (BossParty.img enterFieldID). */
    private static Set<Integer> teleportMaps() {
        Set<Integer> maps = new HashSet<>();
        for (Map<Integer, int[]> byDifficulty : bosses().values()) {
            for (int[] entry : byDifficulty.values()) {
                maps.add(entry[0]);
            }
        }
        return maps;
    }

    public static boolean isTeleportMap(int mapId) {
        return teleportMaps().contains(mapId);
    }

    /**
     * @return the required quests to report as completed to this character's client: the ones it hasn't completed
     * and hasn't started (a quest in progress keeps its real state, so its progress still shows)
     */
    public static List<Integer> getQuestsToShowCompleted(Char chr) {
        if (!isBypassOn(chr)) {
            return Collections.emptyList();
        }
        QuestManager qm = chr.getQuestManager();
        List<Integer> res = new ArrayList<>();
        for (int questId : REQUIRED_QUESTS) {
            if (!qm.hasQuestCompleted(questId) && !qm.hasQuestInProgress(questId) && levelFor(chr, questId)) {
                res.add(questId);
            }
        }
        return res;
    }

    /**
     * @return the value to send for a quest record, with the boss window's required key added if it's missing
     */
    public static String withRequiredRecord(int questId, String value) {
        return withRequiredRecord(null, questId, value);
    }

    /** As above, only for a character high enough for that boss. */
    public static String withRequiredRecord(Char chr, int questId, String value) {
        String[] req = isBypassOn(chr) && levelFor(chr, questId)
                ? REQUIRED_RECORDS.get(questId) : null;
        if (req == null) {
            return value;
        }
        String pair = req[0] + "=" + req[1];
        if (value == null || value.isEmpty()) {
            return pair;
        }
        for (String part : value.split(";")) {
            if (part.equals(pair)) {
                return value;
            }
        }
        // drop an existing entry for the key, then add the required one
        StringBuilder sb = new StringBuilder();
        for (String part : value.split(";")) {
            if (!part.isEmpty() && !part.startsWith(req[0] + "=")) {
                sb.append(part).append(';');
            }
        }
        return sb.append(pair).toString();
    }

    /**
     * @return the required quest records this character has no record of at all
     */
    public static List<Integer> getMissingRecords(Char chr) {
        if (!isBypassOn(chr)) {
            return Collections.emptyList();
        }
        List<Integer> res = new ArrayList<>();
        for (int questId : REQUIRED_RECORDS.keySet()) {
            if (chr.getQuestManager().getQuestById(questId) == null && levelFor(chr, questId)) {
                res.add(questId);
            }
        }
        return res;
    }

    // ------------------------------------------------------------------------------------------------ boss list

    private static final Logger log = LogManager.getLogger(BossUiAccess.class);

    /** BossParty.img: boss (window order) -> difficulty -> {enterFieldID, levelMin} */
    private static Map<Integer, Map<Integer, int[]>> bosses;
    /** BossParty.img recordInfo/clear/mobID: every monster whose kill clears a difficulty, in id order */
    private static final Set<Integer> clearMobs = new TreeSet<>();

    private static synchronized Map<Integer, Map<Integer, int[]>> bosses() {
        if (bosses != null) {
            return bosses;
        }
        bosses = new HashMap<>();
        try {
            WzObject<?, ?> root = new WzFile(ServerConstants.WZ_DIR + "/Etc.wz").getChild("BossParty.img");
            if (root == null) {
                log.warn("Etc.wz has no BossParty.img: the boss window's Enter button does nothing");
                return bosses;
            }
            for (WzObject<?, ?> boss : root) {
                Map<Integer, int[]> byDifficulty = new HashMap<>();
                for (WzObject<?, ?> entry : boss) {
                    int field = intOf(entry.getChild("enterFieldID"));
                    if (field > 0) {
                        byDifficulty.put(intOf(entry.getChild("difficulty")),
                                new int[]{field, intOf(entry.getChild("levelMin"))});
                    }
                    WzObject<?, ?> mobIds = entry.getChildByPath("recordInfo/clear/mobID");
                    if (mobIds != null) {
                        for (WzObject<?, ?> mob : mobIds) {
                            if (intOf(mob) > 0) {
                                clearMobs.add(intOf(mob));
                            }
                        }
                    }
                }
                if (!byDifficulty.isEmpty()) {
                    bosses.put(Integer.parseInt(boss.getName()), byDifficulty);
                }
            }
        } catch (Exception e) {
            log.error("Could not read BossParty.img", e);
        }
        return bosses;
    }

    private static int intOf(WzObject<?, ?> node) {
        return node instanceof WzProperty<?> prop ? prop.getIntValue() : 0;
    }

    /** @return the monsters whose kill the boss window counts as a clear (BossParty.img recordInfo), in id order */
    public static synchronized Set<Integer> getClearMobs() {
        bosses();
        return Collections.unmodifiableSet(clearMobs);
    }

    /** @return {enterFieldID, levelMin} of the boss window's entry for this boss and difficulty, or null */
    public static int[] getEntry(int bossOrder, int difficulty) {
        Map<Integer, int[]> byDifficulty = bosses().get(bossOrder);
        return byDifficulty == null ? null : byDifficulty.get(difficulty);
    }
}
