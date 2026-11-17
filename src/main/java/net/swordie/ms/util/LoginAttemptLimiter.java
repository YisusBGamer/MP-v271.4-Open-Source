package net.swordie.ms.util;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Slows down password guessing: after MAX_FAILED_LOGINS failed logins for one username from one address within
 * WINDOW_MINUTES, that address has to wait until the window is over before trying that username again. Shared by
 * the web API login (/api/login) and the client API's token request.
 */
public final class LoginAttemptLimiter {
    public static final int MAX_FAILED_LOGINS = 10;
    public static final long WINDOW_MINUTES = 10;
    private static final long WINDOW_MS = WINDOW_MINUTES * 60_000L;
    private static final int MAX_TRACKED = 10_000;

    private static final Map<String, long[]> failures = new ConcurrentHashMap<>(); // key -> {count, first fail ms}

    private LoginAttemptLimiter() {
    }

    public static String key(String address, String username) {
        return (address == null ? "?" : address) + "|" + (username == null ? "" : username.trim().toLowerCase(Locale.ROOT));
    }

    /**
     * @return how many minutes the key still has to wait, or 0 if it may try to log in
     */
    public static long minutesBlocked(String key) {
        return minutesBlocked(key, MAX_FAILED_LOGINS);
    }

    /**
     * @param maxFailures failed logins allowed for this key within the window
     */
    public static long minutesBlocked(String key, int maxFailures) {
        long now = System.currentTimeMillis();
        long[] f = failures.get(key);
        if (f != null && now - f[1] < WINDOW_MS && f[0] >= maxFailures) {
            return Math.max(1, (WINDOW_MS - (now - f[1]) + 59_999) / 60_000);
        }
        return 0;
    }

    public static void recordFailure(String key) {
        long now = System.currentTimeMillis();
        if (failures.size() > MAX_TRACKED) {
            failures.values().removeIf(v -> now - v[1] >= WINDOW_MS);
        }
        failures.compute(key, (k, v) -> v == null || now - v[1] >= WINDOW_MS
                ? new long[]{1, now} : new long[]{v[0] + 1, v[1]});
    }

    public static void recordSuccess(String key) {
        failures.remove(key);
    }
}
