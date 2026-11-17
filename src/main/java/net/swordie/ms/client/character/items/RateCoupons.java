package net.swordie.ms.client.character.items;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.util.FileTime;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 * Cash EXP (521xxxx) and drop (536xxxx) rate coupons: while one is in the Cash tab, unexpired, and the time is in one
 * of its Item.wz time windows ("MON:00-24"), monster EXP or drops are multiplied by its rate. The best coupon counts;
 * they don't stack. Holiday windows (HOL) aren't used.
 */
public class RateCoupons {

    public static final int EXP = 521;
    public static final int DROP = 536;

    /** @return the best active coupon rate of that kind (2 = 2x), 1 if none */
    public static int getRate(Char chr, int kind) {
        Inventory cash = chr.getCashInventory();
        if (cash == null) {
            return 1;
        }
        LocalDateTime now = LocalDateTime.now();
        int best = 1;
        for (Item item : cash.getItems()) {
            if (item.getItemId() / 10000 != kind) {
                continue;
            }
            FileTime expire = item.getDateExpire();
            if (expire != null && expire.isExpired()) {
                continue;
            }
            var ii = ItemData.getItemInfoByID(item.getItemId());
            if (ii == null || ii.getCouponRate() <= best) {
                continue;
            }
            if (ii.getCouponMinLevel() > 0 && chr.getLevel() < ii.getCouponMinLevel()
                    || ii.getCouponMaxLevel() > 0 && chr.getLevel() > ii.getCouponMaxLevel()) {
                continue;
            }
            if (ii.getCouponTimes().stream().anyMatch(t -> inWindow(t, now))) {
                best = ii.getCouponRate();
            }
        }
        return best;
    }

    /** "MON:06-10": Monday from 6:00 to 10:00 (24 = end of day) */
    static boolean inWindow(String window, LocalDateTime now) {
        try {
            String[] dayAndHours = window.trim().split(":");
            String[] hours = dayAndHours[1].split("-");
            DayOfWeek day = switch (dayAndHours[0].toUpperCase()) {
                case "MON" -> DayOfWeek.MONDAY;
                case "TUE" -> DayOfWeek.TUESDAY;
                case "WED" -> DayOfWeek.WEDNESDAY;
                case "THU" -> DayOfWeek.THURSDAY;
                case "FRI" -> DayOfWeek.FRIDAY;
                case "SAT" -> DayOfWeek.SATURDAY;
                case "SUN" -> DayOfWeek.SUNDAY;
                default -> null;
            };
            if (day == null || now.getDayOfWeek() != day) {
                return false;
            }
            int hour = now.getHour();
            return hour >= Integer.parseInt(hours[0]) && hour < Integer.parseInt(hours[1]);
        } catch (RuntimeException e) {
            return false;
        }
    }
}
