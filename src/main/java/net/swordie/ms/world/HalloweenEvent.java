package net.swordie.ms.world;

import net.swordie.ms.constants.ItemConstants;
import java.util.function.Supplier;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Halloween, the Haunted Mansion (GMS v177, 2016), as a yearly event (see {@link SeasonalEvents}). Lv. 33+. Spin Man
 * stands in the major towns: he hands out the Masked Gentleman's invitation (Prologue), takes players to the mansion,
 * runs the Kaleido-Spinner and replaces the old "Halloween notifier" ({@link #guide}). The 36-quest story
 * (15102-15137) runs through the mansion's NPC scripts; the quests' start/end scripts just open the NPC
 * ({@link SeasonalEvents#openNpcLater}).
 * <p>
 * Rewards (Quest.wz has almost none; the original scripts gave them): EXP for every quest (a percent of the level's
 * EXP), a Kaleido-Spinner Event Ticket for every quest with a task, 5 Swordie Coins per episode, and at the end the
 * Halloween Mask, 15 Swordie Coins and 3 more tickets ({@link #finish}). Tickets also come from Ticket Pieces (monsters
 * near your level, 10 pieces = 1 ticket) and the Elite Pumpkin Zombies that sometimes replace an Elite Boss.
 */
public final class HalloweenEvent {

    private static final Logger log = LogManager.getLogger(HalloweenEvent.class);

    public static final int MIN_LEVEL = 33;
    public static final int SPIN_MAN = 2120025;
    public static final int KALEIDO_TICKET = 4001863;
    public static final int TICKET_PIECE = 4034722;
    public static final int PIECES_PER_TICKET = 10;
    public static final int HALLOWEEN_MASK = 1012554;
    public static final int ELITE_PUMPKIN_ZOMBIE = 9010102;
    public static final long ELITE_PUMPKIN_BASE_HP = 1_000_000L; // the other Elite Bosses' HP (times ELITE_BOSS_HP_RATE)
    public static final int ENTRANCE = 229010000;
    public static final int CENTRAL_HALL = 229000000;
    public static final int PROLOGUE = 15102;
    public static final int EPILOGUE = 15137;
    public static final int TICKET_QUEST = 15159; // [Halloween] Make Kaleido-Spinner Tickets! (pieces show while it's on)
    public static final int ELITE_PUMPKIN_QUEST = 15154; // [Halloween] 100 Elite Pumpkins!
    /** Server-only record: town to go back to from the mansion, and the day of the last login reminder. */
    public static final int HALLOWEEN_QR = 89999985;
    private static final int HALLOWEEN_EARRINGS = 1032259;
    private static final int HALLOWEEN_PUMPKIN_CHAIR = 3010279;

    /** Quests that start by themselves once the one before is done (2016: the Halloween notifier). */
    private static final Set<Integer> NOTIFIER = Set.of(15106, 15110, 15112, 15114, 15116, 15120, 15123, 15125,
            15132, 15133);
    /** Quests with a task (hunt, collect, investigate): EXP and a ticket. */
    private static final Set<Integer> TASKS = Set.of(15106, 15109, 15110, 15111, 15112, 15113, 15114, 15115, 15116,
            15117, 15118, 15121, 15124, 15126, 15128, 15130, 15131, 15135, 15136);
    private static final Set<Integer> EPISODE_ENDS = Set.of(15108, 15118, 15129, 15136);

    private HalloweenEvent() {
    }

    public static boolean isActive() {
        return SeasonalEvents.isActive(SeasonalEvents.HALLOWEEN);
    }

    public static boolean isEventQuest(int questId) {
        return questId >= 15102 && questId <= 15164;
    }

    // ------------------------------------------------------------------------------------------------- the story

    /** Where a quest's NPC or task is: {quest, map, what to do}. */
    private record Step(int quest, int map, String start, String task) {
    }

    /** resources/data/halloween-steps.tsv */
    private static final Supplier<List<Step>> STEPS = DataTable.cached("halloween-steps", t -> t.rows().stream()
            .map(r -> new Step(r.i("quest"), r.i("map"), r.has("start") ? r.str("start") : null,
                    r.has("task") ? r.str("task") : null)).toList());

    private static Step step(int quest) {
        for (Step s : STEPS.get()) {
            if (s.quest() == quest) {
                return s;
            }
        }
        return null;
    }

    /** What Spin Man's "Where am I in the story?" says, and the map he can take the player to (0: none). */
    public record Guide(String text, int map, boolean done) {
    }

    /** Also starts a notifier quest that's due (so the player never gets stuck waiting for one). */
    public static Guide guide(Char chr) {
        QuestManager qm = chr.getQuestManager();
        if (!qm.hasQuestCompleted(PROLOGUE)) {
            return new Guide("You haven't been invited yet.", 0, false);
        }
        if (qm.hasQuestCompleted(EPILOGUE)) {
            return new Guide("You've seen the Haunted Mansion's story through to the end. Spooky!", 0, true);
        }
        startDueNotifierQuests(chr);
        for (Step s : STEPS.get()) {
            if (qm.hasQuestInProgress(s.quest())) {
                String name = net.swordie.ms.loaders.StringData.getQuestStringById(s.quest());
                String what = s.task() != null ? s.task() : s.start();
                return new Guide("#b" + name + "#k\r\n" + what, s.map(), false);
            }
        }
        for (Step s : STEPS.get()) {
            if (!qm.hasQuestCompleted(s.quest())) {
                String name = net.swordie.ms.loaders.StringData.getQuestStringById(s.quest());
                return new Guide("Next: #b" + name + "#k\r\n" + (s.start() != null ? s.start() : s.task()), s.map(),
                        false);
            }
        }
        return new Guide("You're almost done.", 0, false);
    }

    /** Starts the notifier quest after the last one that's done, if it isn't started yet. */
    public static void startDueNotifierQuests(Char chr) {
        QuestManager qm = chr.getQuestManager();
        for (int q : NOTIFIER) {
            if (!qm.hasQuestInProgress(q) && !qm.hasQuestCompleted(q) && qm.hasQuestCompleted(q - 1)) {
                chr.getScriptManager().startQuestNoCheck(q);
                String name = net.swordie.ms.loaders.StringData.getQuestStringById(q);
                chr.chatMessage(ChatType.Notice2, "[Halloween] New quest: " + name + ". (Spin Man can take you "
                        + "there.)");
            }
        }
    }

    /**
     * Completes a Haunted Mansion quest with its Quest.wz rewards and the event's: EXP, a ticket for quests with a
     * task, coins at the end of an episode, the Halloween Mask at the end. Starts the next notifier quest.
     *
     * @return whether the quest got completed (false if it was done already or the inventory is full)
     */
    public static boolean finish(Char chr, int quest) {
        QuestManager qm = chr.getQuestManager();
        if (qm.hasQuestCompleted(quest)) {
            return false;
        }
        boolean tickets = TASKS.contains(quest) || quest == EPILOGUE;
        if (tickets && !chr.canHold(KALEIDO_TICKET, 3)) {
            chr.chatMessage(ChatType.Notice2, "Make room in your Etc inventory first.");
            return false;
        }
        if (quest == EPILOGUE && !chr.canHold(HALLOWEEN_MASK, 1)) {
            chr.chatMessage(ChatType.Notice2, "Make room in your Equip inventory first.");
            return false;
        }
        if (!qm.hasQuestInProgress(quest)) {
            chr.getScriptManager().startQuestNoCheck(quest);
        }
        if (!qm.completeQuest(quest)) {
            return false;
        }
        try {
            double pct = quest == EPILOGUE ? 10 : TASKS.contains(quest) ? 5 : 1;
            chr.addExp(SeasonalEvents.expPercent(chr, pct));
            if (TASKS.contains(quest)) {
                chr.addItemToInventory(KALEIDO_TICKET, 1);
                chr.chatMessage(ChatType.Notice2, "You got a Kaleido-Spinner Event Ticket. (Spin Man spins it.)");
            }
            if (EPISODE_ENDS.contains(quest)) {
                SeasonalEvents.giveCoins(chr, 5, "Haunted Mansion episode");
            }
            if (quest == EPILOGUE) {
                SeasonalEvents.giveItem(chr, HALLOWEEN_MASK, 1);
                chr.addItemToInventory(KALEIDO_TICKET, 3);
                SeasonalEvents.giveCoins(chr, 15, "the Haunted Mansion's last night");
                chr.chatMessage(ChatType.Notice2, "You got the Halloween Mask and 3 Kaleido-Spinner Event Tickets.");
            }
        } catch (Exception e) {
            log.error("Halloween: rewards of quest " + quest + " for " + chr.getName(), e);
        }
        startDueNotifierQuests(chr);
        return true;
    }

    // --------------------------------------------------------------------------------------------- the spinner

    private record Prize(int item, int count, int weight) {
    }

    /** resources/data/halloween-spinner.tsv: tier (modest, great, ultra) -> prizes */
    private static final Supplier<Map<String, List<Prize>>> SPINNER = DataTable.cached("halloween-spinner", t -> {
        Map<String, List<Prize>> m = new HashMap<>();
        for (DataTable.Row r : t.rows()) {
            m.computeIfAbsent(r.str("tier").trim(), k -> new ArrayList<>())
                    .add(new Prize(r.i("item"), r.i("count"), r.i("weight")));
        }
        m.replaceAll((k, v) -> List.copyOf(v));
        return m;
    });

    private static List<Prize> prizes(String tier) {
        return SPINNER.get().getOrDefault(tier, List.of());
    }

    private static Prize roll(List<Prize> prizes) {
        int total = prizes.stream().mapToInt(Prize::weight).sum();
        int r = Util.getRandom(total - 1);
        for (Prize p : prizes) {
            r -= p.weight();
            if (r < 0) {
                return p;
            }
        }
        return prizes.get(0);
    }

    /**
     * Spins the Kaleido-Spinner with one ticket: a Modest (75%), Great (22%) or Ultra (3%) Jackpot, given right away
     * (the 2016 Jackpot Boxes can't be opened any more: they expired in 2016).
     *
     * @return what the player got, for the dialogue; null if they have no ticket or no room
     */
    public static String spin(Char chr) {
        if (!chr.hasItemCount(KALEIDO_TICKET, 1)) {
            return null;
        }
        for (InvTypeCheck c : InvTypeCheck.values()) {
            if (!chr.canHold(c.sample, 1)) {
                chr.chatMessage(ChatType.Notice2, "Make sure every inventory tab has a free slot first.");
                return null;
            }
        }
        int r = Util.getRandom(99);
        String tier;
        List<Prize> prizes;
        if (r < 3) {
            tier = "#rULTRA JACKPOT#k";
            prizes = prizes("ultra");
        } else if (r < 25) {
            tier = "#bGreat Jackpot#k";
            prizes = prizes("great");
        } else {
            tier = "Modest Jackpot";
            prizes = prizes("modest");
        }
        if (prizes.isEmpty()) {
            log.warn("halloween-spinner.tsv has no prizes for the rolled tier; the ticket is kept");
            return null;
        }
        Prize p = roll(prizes);
        if (!chr.canHold(p.item(), p.count())) {
            chr.chatMessage(ChatType.Notice2, "Make room in your inventory first.");
            return null;
        }
        chr.consumeItem(KALEIDO_TICKET, 1);
        SeasonalEvents.giveItem(chr, p.item(), p.count());
        if (r < 3) {
            for (World world : net.swordie.ms.Server.getInstance().getWorlds()) {
                if (world.getChars().contains(chr)) {
                    world.broadcastPacket(net.swordie.ms.connection.packet.WvsContext.broadcastMsg(
                            net.swordie.ms.client.character.BroadcastMsg.notice(String.format(
                                    "[Halloween] %s hit the Kaleido-Spinner's ULTRA JACKPOT!", chr.getName()))));
                }
            }
        }
        return String.format("%s!\r\n\r\n#i%d# #t%d#%s", tier, p.item(), p.item(),
                p.count() > 1 ? " x" + p.count() : "");
    }

    /** A sample item of each inventory tab, to make sure a prize fits. */
    private enum InvTypeCheck {
        EQUIP(HALLOWEEN_EARRINGS), USE(ItemConstants.ELIXIR), SETUP(HALLOWEEN_PUMPKIN_CHAIR), ETC(ItemConstants.SPELL_TRACE_ID);
        final int sample;

        InvTypeCheck(int sample) {
            this.sample = sample;
        }
    }

    /** Trades {@link #PIECES_PER_TICKET} Ticket Pieces for a ticket, as often as possible. @return tickets made */
    public static int exchangePieces(Char chr) {
        int made = 0;
        while (chr.hasItemCount(TICKET_PIECE, PIECES_PER_TICKET) && chr.canHold(KALEIDO_TICKET, 1)) {
            chr.consumeItem(TICKET_PIECE, PIECES_PER_TICKET);
            chr.addItemToInventory(KALEIDO_TICKET, 1);
            made++;
        }
        return made;
    }

    // ---------------------------------------------------------------------------------------------- the world

    /** Login during Halloween: the side quests start by themselves (Lv. 33+), and a reminder once a day. */
    public static void onLogin(Char chr) {
        if (chr.getLevel() < MIN_LEVEL) {
            return;
        }
        QuestManager qm = chr.getQuestManager();
        for (int q : new int[]{TICKET_QUEST, ELITE_PUMPKIN_QUEST}) {
            if (!qm.hasQuestInProgress(q) && !qm.hasQuestCompleted(q)) {
                chr.getScriptManager().startQuestNoCheck(q);
            }
        }
        if (qm.hasQuestCompleted(PROLOGUE)) {
            startDueNotifierQuests(chr);
        }
        String today = net.swordie.ms.util.ResetClock.today().toString();
        Map<String, String> rec = record(chr);
        if (today.equals(rec.get("notice"))) {
            return;
        }
        rec.put("notice", today);
        save(chr, rec);
        String msg = qm.hasQuestCompleted(EPILOGUE)
                ? "[Halloween] Happy Halloween! Spin Man (major towns) still spins the Kaleido-Spinner until "
                + SeasonalEvents.windowText(SeasonalEvents.HALLOWEEN).replaceAll(".* - ", "") + "."
                : "[Halloween] The Haunted Mansion is open (" + SeasonalEvents.windowText(SeasonalEvents.HALLOWEEN)
                + ")! Talk to Spin Man in any major town.";
        net.swordie.ms.handlers.executors.EventManager.addEvent(() -> {
            if (chr.isOnline()) {
                chr.chatMessage(ChatType.Notice2, msg);
            }
        }, 8_000);
    }

    static void onMobKilled(Mob mob) {
        Char killer = mob.getMostDamageChar();
        if (killer != null && killer.getLevel() >= MIN_LEVEL && !mob.isBoss() && !mob.isBossFightAdd()
                && !mob.getField().getInfo().isTown() && killer.getField() == mob.getField()
                && Math.abs(mob.getForcedMobStat().getLevel() - killer.getLevel()) <= 20
                && Util.succeedProp(GameConstants.HALLOWEEN_TICKET_PIECE_CHANCE, 10000)) {
            SeasonalEvents.grantFind(killer, TICKET_PIECE, 1);
        }
        if (mob.getTemplateId() != ELITE_PUMPKIN_ZOMBIE) {
            return;
        }
        for (Char chr : mob.getDamageDone().keySet()) {
            if (chr != null && chr.getField() == mob.getField()) {
                chr.chatMessage(ChatType.Notice2, "[Halloween] You defeated an Elite Pumpkin Zombie!");
            }
        }
    }

    // ------------------------------------------------------------------------------------------- the record

    public static Map<String, String> record(Char chr) {
        Map<String, String> m = new LinkedHashMap<>();
        String v = chr.getQuestManager().getHiddenRecord(HALLOWEEN_QR);
        if (v != null) {
            for (String kv : v.split(";")) {
                int i = kv.indexOf('=');
                if (i > 0) {
                    m.put(kv.substring(0, i), kv.substring(i + 1));
                }
            }
        }
        return m;
    }

    public static void save(Char chr, Map<String, String> m) {
        StringBuilder sb = new StringBuilder();
        m.forEach((k, v) -> sb.append(sb.length() == 0 ? "" : ";").append(k).append('=').append(v));
        chr.getQuestManager().setHiddenRecord(HALLOWEEN_QR, sb.toString());
    }

    /** Remembers the town the player came from (the mansion's exit goes back there). */
    public static void rememberTown(Char chr) {
        if (chr.getField() != null && chr.getField().getInfo().isTown() && chr.getFieldID() / 10000000 != 22) { // hardcode-ok
            Map<String, String> m = record(chr);
            m.put("town", String.valueOf(chr.getFieldID()));
            save(chr, m);
        }
    }

    /** @return the town to go back to from the mansion (Henesys if none) */
    public static int returnTown(Char chr) {
        try {
            return Integer.parseInt(record(chr).getOrDefault("town", String.valueOf(FieldConstants.HENESYS_FIELD_ID)));
        } catch (NumberFormatException e) {
            return FieldConstants.HENESYS_FIELD_ID;
        }
    }
}
