package net.swordie.ms.tools;

import net.swordie.ms.util.ResetClock;

import java.lang.reflect.Method;
import java.time.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Offline reset simulator (no game client, no database): puts ResetClock on a fake clock in a reset zone, steps it
 * across midnight and the Thursday / Monday weekly resets, and prints what every day / week key and next-reset time
 * says at each step. A key that should turn at the reset zone's midnight but doesn't (or turns at another zone's
 * midnight) is code that reads the real clock or its own time zone: those lines are marked.
 * <p>
 * Run from the server folder after a compile (tools/resetsim.sh): java -cp target/classes;deps ResetSim [Wed date]
 */
public final class ResetSim {
    private static final int ATHENA_PIERCE_NPC = 9201234;

    private static int flagged;

    /** a key, and when it should turn: "day" (every midnight), "thu" / "mon" (that weekly reset), "" = no rule */
    private record Key(String name, String turns, Supplier<String> value) {
    }

    public static void main(String[] args) throws Exception {
        LocalDate wednesday = args.length > 0 ? LocalDate.parse(args[0]) : LocalDate.of(2026, 11, 4);
        if (wednesday.getDayOfWeek() != DayOfWeek.WEDNESDAY) {
            wednesday = wednesday.with(java.time.temporal.TemporalAdjusters.previousOrSame(DayOfWeek.WEDNESDAY));
        }
        List<Key> keys = keys();
        for (String zid : new String[]{"UTC", "America/Los_Angeles"}) {
            ZoneId zone = ZoneId.of(zid);
            ZoneId other = ZoneId.of(zid.equals("UTC") ? "America/Los_Angeles" : "UTC");
            System.out.println("== Reset zone " + zid + " (the PC's own zone: " + ZoneId.systemDefault() + ")"); // hardcode-ok
            // Wed -> Thu at the reset zone's midnight, the other zone's midnight, and Sun -> Mon
            LocalDate thursday = wednesday.plusDays(1);
            Instant thu = thursday.atStartOfDay(zone).toInstant();
            Instant thuOther = thursday.atStartOfDay(other).toInstant();
            Instant mon = wednesday.plusDays(5).atStartOfDay(zone).toInstant();
            step(keys, zone, "Wed->Thu at midnight " + zid, thu, true, "thu");
            step(keys, zone, "Thursday midnight " + other.getId() + " (nothing should turn)", thuOther, false, "");
            step(keys, zone, "Sun->Mon at midnight " + zid, mon, true, "mon");
            ResetClock.useForTest(zone, Clock.fixed(thu.minusSeconds(30), zone));
            System.out.println("  next resets seen 30 s before Thursday: daily " + wall(ResetClock.nextDailyReset(), zone) // hardcode-ok
                    + ", weekly Thu " + wall(ResetClock.nextWeeklyReset(DayOfWeek.THURSDAY), zone)
                    + ", weekly Mon " + wall(ResetClock.nextWeeklyReset(DayOfWeek.MONDAY), zone)
                    + ", monthly " + wall(ResetClock.nextMonthlyReset(), zone));
            System.out.println(); // hardcode-ok
        }
        System.out.println(flagged == 0 ? "Everything turns with the reset clock." : flagged + " flagged line(s)."); // hardcode-ok
    }

    private static String wall(long millis, ZoneId zone) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), zone).toString();
    }

    /** prints each key 30 s before and after a boundary; at a reset boundary daily keys (and the matching weekly
     * ones) must change, elsewhere nothing may change */
    private static void step(List<Key> keys, ZoneId zone, String title, Instant at, boolean reset, String weekly) {
        System.out.println("  -- " + title + " (" + LocalDateTime.ofInstant(at, ZoneOffset.UTC) + " UTC)"); // hardcode-ok
        for (Key k : keys) {
            ResetClock.useForTest(zone, Clock.fixed(at.minusSeconds(30), zone));
            String before = safe(k.value);
            ResetClock.useForTest(zone, Clock.fixed(at.plusSeconds(30), zone));
            String after = safe(k.value);
            boolean changed = !before.equals(after);
            boolean should = reset && (k.turns.equals("day") || k.turns.equals(weekly));
            String mark = "";
            if (!k.turns.isEmpty() && changed != should) {
                flagged++;
                mark = should ? "  <-- should turn here, didn't (real clock or own zone?)" : "  <-- turned here, shouldn't";
            }
            System.out.printf("     %-38s %-26s -> %-26s%s%n", k.name, before, after, mark); // hardcode-ok
        }
    }

    private static String safe(Supplier<String> s) {
        try {
            return String.valueOf(s.get());
        } catch (Throwable t) {
            return "error: " + t.getClass().getSimpleName();
        }
    }

    private static Supplier<String> call(String cls, String method, Object... args) {
        return () -> {
            try {
                Class<?> c = Class.forName(cls);
                for (Method m : c.getDeclaredMethods()) {
                    if (m.getName().equals(method) && m.getParameterCount() == args.length) {
                        m.setAccessible(true);
                        return String.valueOf(m.invoke(null, args));
                    }
                }
                return "no method";
            } catch (Exception e) {
                Throwable t = e.getCause() != null ? e.getCause() : e;
                return "error: " + t.getClass().getSimpleName();
            }
        };
    }

    private static List<Key> keys() {
        List<Key> k = new ArrayList<>();
        String p = "net.swordie.ms.";
        k.add(new Key("ResetClock.today", "day", () -> ResetClock.today().toString()));
        k.add(new Key("ResetClock.weekStart(THU)", "thu", () -> ResetClock.weekStart(DayOfWeek.THURSDAY).toString()));
        k.add(new Key("ResetClock.weekStart(MON)", "mon", () -> ResetClock.weekStart(DayOfWeek.MONDAY).toString()));
        k.add(new Key("Arcane River weekly (ArcaneWeekly)", "thu",
                call(p + "world.field.instance.instancehelper.arcane.ArcaneWeekly", "weekKey")));
        k.add(new Key("Epic Dungeon week", "thu",
                call(p + "world.field.instance.instancehelper.epic.EpicDungeon", "weekStart")));
        k.add(new Key("Boss crystal week (ShopModule)", "thu", call(p + "handlers.life.ShopModule", "crystalWeek")));
        k.add(new Key("Swordie Coin week", "thu", call(p + "client.character.SwordieCoinRewards", "week")));
        k.add(new Key("Account weekly (Thursday)", "thu",
                call(p + "client.character.AccountWeekly", "week", DayOfWeek.THURSDAY)));
        k.add(new Key("Util.getLastMonday", "mon", call(p + "util.Util", "getLastMonday")));
        k.add(new Key("Util.getNextMonday", "mon", call(p + "util.Util", "getNextMonday")));
        k.add(new Key("Nihal daily trade prices", "day",
                call(p + "client.character.NihalTradeModule", "vary", ATHENA_PIERCE_NPC, 4310000, 0.5, 1.5))); // hardcode-ok
        return k;
    }
}
