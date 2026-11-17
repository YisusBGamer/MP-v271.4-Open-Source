package net.swordie.ms.world.event;

import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.util.ResetClock;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Sunny Sunday offers by date (resources/data/sunny-sunday.tsv). Only the offers that have a server-side effect are
 * configurable in v271: the v265 SET_FIELD popup block is gone from the v271 decoder (0x14411E540 hands the damage
 * skin id and two strings to 0x1450E7410 and then reads int, int, byte, int), so there is no title/description to send.
 */
public final class SunnySunday {

    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /** The Monster Park EXP bonus a day without an entry gets: the GMS Sunday rule. */
    private static final int SUNDAY_BONUS = 50;

    private static final Supplier<Map<LocalDate, Integer>> ENTRIES = DataTable.cached("sunny-sunday", t -> {
        Map<LocalDate, Integer> m = new HashMap<>();
        for (DataTable.Row r : t.rows()) {
            m.put(LocalDate.parse(r.str("date").trim(), DATE), r.i("mpIncExp"));
        }
        return Map.copyOf(m);
    });

    private SunnySunday() {
    }

    /** Monster Park's EXP bonus in percent today: the configured entry's, else the plain Sunday +50% (0 other days). */
    public static int monsterParkExpBonus() {
        Integer configured = ENTRIES.get().get(ResetClock.today());
        if (configured != null) {
            return configured;
        }
        return ResetClock.today().getDayOfWeek() == DayOfWeek.SUNDAY ? SUNDAY_BONUS : 0;
    }

    /** Spell Trace fever today: every date in the table is a Sunny Sunday, and those raise the scroll chances. */
    public static boolean scrollFeverTime() {
        return ENTRIES.get().containsKey(ResetClock.today());
    }
}
