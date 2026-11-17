package net.swordie.ms.client.character.hexa;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.client.character.quest.requirement.QuestRecordRequirement;
import net.swordie.ms.client.character.quest.requirement.QuestStartJobRequirement;
import net.swordie.ms.client.character.quest.requirement.QuestStartRequirement;
import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.constants.HexaConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.constants.ServerFeatures;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.QuestData;
import net.swordie.ms.loaders.containerclasses.ItemInfo;
import net.swordie.ms.loaders.containerclasses.QuestInfo;
import net.swordie.ms.world.field.Field;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The [6th Job] questline (Quest.wz 38601-38625) in the Barrier Between Worlds and the Erda Torrent (993217480-485),
 * behind setting sixth_job_questline. The goddess stages and the six symbols each fill an Arcane Stone (Item.wz
 * info/exp) with hunting EXP; the Collapsing Erdas fight ends An Erda That Didn't Exist Before.
 */
public final class SixthJobQuest {
    public static final int MERGED_DIMENSION = 38601, NEW_ERDA = 38602, GODDESS_1 = 38603, FAINT_VOICE = 38604,
            GODDESS_2 = 38605, WHOSE_VOICE = 38606, GODDESS_3 = 38607, INTO_SOL_ERDA = 38608, NEW_AGE = 38609,
            ADVANCEMENT_DONE = 38612, FIRST_SYMBOL_QUEST = 38618, SYMBOL_QUESTS = 6, NEW_POWER = 38624,
            HEXA_MATRIX = 38625;
    /** The goddess stages hand in at 1 (38603, 38605, 38607); A New Age starts at 1. */
    public static final int GODDESS_RECORD = 38616;
    public static final int TRUE_FORM = 39722;
    public static final int BARRIER = 993217480, COLLAPSING_1 = 993217481, COLLAPSING_2 = 993217482,
            COLLAPSING_3 = 993217483, DWINDLING = 993217484, DEEP_WITHIN = 993217485;
    public static final int MY_ARCANE_STONE = 2636387, FIRST_SYMBOL_STONE = 2636388;
    private static final int ENIGMATIC_ERDAS = 8645390, UNSTABLE_ERDAS = 8645391, COLLAPSING_ERDAS = 8645392;
    /** Horizontal distance between the Erdas Elwin's fight spawns. */
    private static final int ERDAS_SPACING = 200;
    /** server-only records: "from=<map>;at=<map>" (where the torrent was entered from, where it was left), and the
     * active stones "<item>=<exp>,..." */
    private static final int PLACE_RECORD = 89999934, STONE_RECORD = 89999935;

    private SixthJobQuest() {
    }

    public static boolean isOn() {
        return ServerFeatures.on(ServerFeatures.Feature.SIXTH_JOB_QUESTLINE);
    }

    /** The questline applies to Classic characters; Express gets the 6th job at 260. */
    public static boolean isOn(Char chr) {
        return isOn() && net.swordie.ms.client.character.PlayStyleModule.isClassic(chr);
    }

    /**
     * Whether the character's class is in Merged Dimension's job list (each class's job id + 1 after its 5th job);
     * the classes the list leaves out get the 6th job at 260 as before.
     */
    public static boolean classHasQuestline(Char chr) {
        QuestInfo qi = QuestData.getQuestInfoById(MERGED_DIMENSION);
        if (qi == null) {
            return false;
        }
        for (QuestStartRequirement r : qi.getQuestStartRequirements()) {
            if (r instanceof QuestStartJobRequirement jr) {
                return jr.hasRequirements(chr);
            }
        }
        return false;
    }

    /** Level 260 with the 5th job and [Convergence] True Form done, the 6th job not earned yet. */
    public static boolean eligible(Char chr) {
        QuestManager qm = chr.getQuestManager();
        return isOn(chr) && classHasQuestline(chr) && chr.getLevel() >= HexaConstants.SIXTH_JOB_LEVEL
                && qm.hasQuestCompleted(QuestConstants.FIFTH_JOB_QUEST) && qm.hasQuestCompleted(TRUE_FORM)
                && !HexaModule.hasSixthJob(chr);
    }

    /** At login or a level up: points an eligible character to the Maple Administrator once the questline opens. */
    public static void offer(Char chr) {
        if (eligible(chr) && !chr.getQuestManager().hasQuestInProgress(MERGED_DIMENSION)
                && !chr.getQuestManager().hasQuestCompleted(MERGED_DIMENSION)) {
            chr.chatMessage(ChatType.SystemNotice, "[6th Job] Lily needs your help at the Barrier Between Worlds. "
                    + "Open the quest list and start [6th Job] Merged Dimension, or talk to the Maple Administrator.");
        }
    }

    /** Into the torrent (a solo instance), where the character left it, or the Barrier Between Worlds at first. */
    public static void enter(Char chr) {
        QuestManager qm = chr.getQuestManager();
        var sm = chr.getScriptManager();
        if (!qm.hasQuestInProgress(MERGED_DIMENSION) && !qm.hasQuestCompleted(MERGED_DIMENSION)) {
            sm.startQuestNoCheck(MERGED_DIMENSION);
            sm.startQuestNoCheck(ADVANCEMENT_DONE); // the questline's tracker, handed in at the end
        }
        Map<String, String> place = place(chr);
        int at = parse(place.get("at"));
        if (at < BARRIER || at > DEEP_WITHIN) {
            at = BARRIER;
        }
        if (chr.getFieldID() < BARRIER || chr.getFieldID() > DEEP_WITHIN) {
            place.put("from", String.valueOf(chr.getFieldID()));
            savePlace(chr, place);
        }
        sm.warpInstanceIn(at, 0);
    }

    /** Moco: out of the torrent to where it was entered from; the Maple Administrator brings the character back. */
    public static void leave(Char chr) {
        Map<String, String> place = place(chr);
        place.put("at", String.valueOf(chr.getFieldID()));
        savePlace(chr, place);
        int from = parse(place.get("from"));
        chr.getScriptManager().warpInstanceOut(from > 0 ? from : FieldConstants.HENESYS_FIELD_ID, 0);
    }

    /** Elwin's fight: the unfamiliar Erdas around the character, then the Collapsing Erdas where the last one falls. */
    public static void startErdas(Char chr) {
        Field field = chr.getField();
        if (field == null || field.getId() != BARRIER
                || field.getMobs().stream().anyMatch(m -> isErdas(m.getTemplateId()))) {
            return;
        }
        int x = chr.getPosition().getX(), y = chr.getPosition().getY();
        field.spawnMob(UNSTABLE_ERDAS, x - ERDAS_SPACING, y, false, 0);
        field.spawnMob(ENIGMATIC_ERDAS, x + ERDAS_SPACING, y, false, 0);
        field.spawnMob(UNSTABLE_ERDAS, x + 2 * ERDAS_SPACING, y, false, 0);
    }

    private static boolean isErdas(int template) {
        return template == ENIGMATIC_ERDAS || template == UNSTABLE_ERDAS || template == COLLAPSING_ERDAS;
    }

    public static void onMobDeath(Field field, Mob mob) {
        if (field.getId() != BARRIER || !isErdas(mob.getTemplateId())) {
            return;
        }
        if (mob.getTemplateId() != COLLAPSING_ERDAS) {
            if (field.getMobs().stream().noneMatch(m -> m != mob && isErdas(m.getTemplateId()))) {
                field.spawnMob(COLLAPSING_ERDAS, mob.getPosition().getX(), mob.getPosition().getY(), false, 0);
            }
            return;
        }
        for (Char c : field.getChars()) {
            if (c.getQuestManager().hasQuestInProgress(NEW_ERDA)) {
                c.getScriptManager().setQRValue(NEW_ERDA, "1");
                c.getQuestManager().completeQuest(NEW_ERDA);
                c.warp(c.getOrCreateFieldByCurrentInstanceType(COLLAPSING_1));
            }
        }
    }

    // ------------------------------------------------------------------------------------------------ stones

    private static long capacity(int stone) {
        ItemInfo ii = ItemData.getItemInfoByID(stone);
        return ii == null ? 0 : ii.getInfoExp();
    }

    private static Map<Integer, Long> stones(Char chr) {
        Map<Integer, Long> res = new LinkedHashMap<>();
        String v = chr.getQuestManager().getHiddenRecord(STONE_RECORD);
        if (v == null || v.isEmpty()) {
            return res;
        }
        for (String part : v.split(",")) {
            String[] kv = part.split("=");
            if (kv.length == 2) {
                res.put(parse(kv[0]), parseLong(kv[1]));
            }
        }
        return res;
    }

    private static void saveStones(Char chr, Map<Integer, Long> stones) {
        StringBuilder sb = new StringBuilder();
        stones.forEach((k, v) -> sb.append(sb.isEmpty() ? "" : ",").append(k).append('=').append(v));
        chr.getQuestManager().setHiddenRecord(STONE_RECORD, sb.toString());
    }

    /** Double-clicked: the stone starts storing hunting EXP (or tells how far it is). */
    public static void activateStone(Char chr, int stone) {
        long cap = capacity(stone);
        if (cap <= 0) {
            return;
        }
        long have;
        boolean added;
        synchronized (chr.getQuestManager()) {
            Map<Integer, Long> stones = stones(chr);
            have = stones.getOrDefault(stone, 0L);
            added = !stones.containsKey(stone);
            if (added) {
                stones.put(stone, 0L);
                saveStones(chr, stones);
            }
        }
        if (added) {
            chr.chatMessage(ChatType.SystemNotice, "The stone glows: it stores the EXP you gain from hunting.");
        }
        chr.chatMessage(ChatType.SystemNotice, String.format("Stored EXP: %,d / %,d", Math.min(have, cap), cap));
    }

    public static boolean stoneFull(Char chr, int stone) {
        long cap = capacity(stone);
        return cap > 0 && stones(chr).getOrDefault(stone, 0L) >= cap;
    }

    /** The stone's power was used: it starts over (the next goddess stage needs it full again). */
    public static void emptyStone(Char chr, int stone) {
        synchronized (chr.getQuestManager()) {
            Map<Integer, Long> stones = stones(chr);
            if (stones.remove(stone) != null) {
                saveStones(chr, stones);
            }
        }
    }

    /** Hunting EXP (after bonuses) goes into every active stone that isn't full yet. */
    public static void onHuntExp(Char chr, long exp) {
        if (exp <= 0 || chr.getQuestManager().getHiddenRecord(STONE_RECORD) == null) {
            return;
        }
        boolean filled = false, goddess = false;
        synchronized (chr.getQuestManager()) {
            Map<Integer, Long> stones = stones(chr);
            boolean changed = false;
            for (var e : stones.entrySet()) {
                long cap = capacity(e.getKey());
                if (cap <= 0 || e.getValue() >= cap) {
                    continue;
                }
                long now = Math.min(cap, e.getValue() + exp);
                e.setValue(now);
                changed = true;
                if (now >= cap) {
                    filled = true;
                    goddess |= e.getKey() == MY_ARCANE_STONE;
                }
            }
            if (changed) {
                saveStones(chr, stones);
            }
        }
        if (filled) {
            chr.chatMessage(ChatType.SystemNotice, "Your stone is full of power.");
        }
        if (goddess && goddessStage(chr) > 0) {
            chr.getScriptManager().setQRValue(GODDESS_RECORD, "1");
        }
    }

    /** @return the goddess quest in progress (38603, 38605 or 38607), or 0 */
    public static int goddessStage(Char chr) {
        QuestManager qm = chr.getQuestManager();
        for (int q : new int[]{GODDESS_1, GODDESS_2, GODDESS_3}) {
            if (qm.hasQuestInProgress(q)) {
                return q;
            }
        }
        return 0;
    }

    /** After a goddess was saved: the record and the stone start over for the next one. */
    public static void goddessSaved(Char chr) {
        chr.getScriptManager().setQRValue(GODDESS_RECORD, "");
        emptyStone(chr, MY_ARCANE_STONE);
    }

    // ------------------------------------------------------------------------------------------------ symbols

    /** @return the symbol quest in progress (38618-38623), or 0 */
    public static int symbolQuest(Char chr) {
        for (int i = 0; i < SYMBOL_QUESTS; i++) {
            if (chr.getQuestManager().hasQuestInProgress(FIRST_SYMBOL_QUEST + i)) {
                return FIRST_SYMBOL_QUEST + i;
            }
        }
        return 0;
    }

    public static int stoneOf(int symbolQuest) {
        return FIRST_SYMBOL_STONE + symbolQuest - FIRST_SYMBOL_QUEST;
    }

    /** The Grandis goddess gave the region's symbol quest: its talk flag on the shared record (38617 N_f). */
    public static void setRecordEntries(Char chr, int quest, String suffix) {
        QuestInfo qi = QuestData.getQuestInfoById(quest);
        if (qi == null) {
            return;
        }
        for (QuestStartRequirement r : qi.getQuestCompleteRequirements()) {
            if (r instanceof QuestRecordRequirement rr) {
                for (var entry : rr.getEntries()) {
                    String key = entry.getExVariable();
                    if (key != null && (suffix == null || key.endsWith(suffix))) {
                        chr.getScriptManager().setQRValue(rr.getQuestID(), key, entry.getValue());
                    }
                }
            }
        }
    }

    /**
     * A mission portal of Deep Within Sol Erda (in_Misiion4_<n>): with the region's stone full, the symbol comes back
     * (38617 N_e) and its quest is done. @return the line to show
     */
    public static String regainSymbol(Char chr, int n) {
        int quest = FIRST_SYMBOL_QUEST + n - 1;
        int stone = stoneOf(quest);
        if (!chr.getQuestManager().hasQuestInProgress(quest)) {
            return chr.getQuestManager().hasQuestCompleted(quest) ? "You already regained this Symbol."
                    : "The torrent beyond is still. The Goddess of Grandis will tell you when to go.";
        }
        if (!stoneFull(chr, stone)) {
            return "The Sol Erda torrent pushes you back. Fill your stone with hunting EXP first.";
        }
        setRecordEntries(chr, quest, null);
        chr.getQuestManager().completeQuest(quest);
        emptyStone(chr, stone);
        chr.consumeItem(stone, 1);
        if (symbolQuest(chr) == 0 && chr.getQuestManager().hasQuestCompleted(FIRST_SYMBOL_QUEST + SYMBOL_QUESTS - 1)) {
            return "You regained the last Symbol. Return to the Goddess of Maple World in the Dwindling Torrent.";
        }
        return "You regained the Symbol. The Goddess of Grandis has the next one for you.";
    }

    /** The end, at the Maple Administrator: the 6th job (or Erda Link), as unlockSixthJob gives it. */
    public static void advance(Char chr) {
        QuestManager qm = chr.getQuestManager();
        if (qm.hasQuestInProgress(ADVANCEMENT_DONE)) {
            qm.completeQuest(ADVANCEMENT_DONE);
        }
        HexaModule.unlockSixthJob(chr);
        chr.getQuestManager().setHiddenRecord(PLACE_RECORD, "");
    }

    // ------------------------------------------------------------------------------------------------ helpers

    private static Map<String, String> place(Char chr) {
        Map<String, String> res = new LinkedHashMap<>();
        String v = chr.getQuestManager().getHiddenRecord(PLACE_RECORD);
        for (String part : v == null ? new String[0] : v.split(";")) {
            String[] kv = part.split("=");
            if (kv.length == 2) {
                res.put(kv[0], kv[1]);
            }
        }
        return res;
    }

    private static void savePlace(Char chr, Map<String, String> place) {
        StringBuilder sb = new StringBuilder();
        place.forEach((k, v) -> sb.append(sb.isEmpty() ? "" : ";").append(k).append('=').append(v));
        chr.getQuestManager().setHiddenRecord(PLACE_RECORD, sb.toString());
    }

    private static int parse(String s) {
        try {
            return s == null ? 0 : Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static long parseLong(String s) {
        try {
            return Long.parseLong(s.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
