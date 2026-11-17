package net.swordie.ms.world;

import net.swordie.ms.Server;
import net.swordie.ms.client.User;
import net.swordie.ms.client.character.Char;
import net.swordie.orm.dao.SworDaoFactory;
import net.swordie.orm.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Saves every online character on a timer (autosave.minutes, default 5, 0 = off) and when the JVM stops normally,
 * with the same save the logout uses. Runs on its own thread, one character at a time with a short pause between
 * them, so a round never holds up gameplay or the database pool.
 */
public final class AutoSave {

    private static final Logger log = LogManager.getLogger();
    private static final UserDao userDao = (UserDao) SworDaoFactory.getByClass(User.class);

    /** Pause between two characters in a timed round. */
    private static final long GAP_MS = 250;

    private static final Object roundLock = new Object();
    private static ScheduledExecutorService timer;
    private static volatile boolean shutdownSaved;

    private AutoSave() {
    }

    public static synchronized void start() {
        if (timer != null) {
            return;
        }
        Runtime.getRuntime().addShutdownHook(new Thread(AutoSave::onJvmExit, "AutoSave-exit"));
        int minutes = net.swordie.ms.ServerSettings.getInt("autosave.minutes", net.swordie.ms.constants.ServerFeatures.getInt(net.swordie.ms.constants.ServerFeatures.Feature.AUTOSAVE_MINUTES));
        if (minutes <= 0) {
            log.info("[AutoSave] timer off (autosave.minutes=0); saves at exit only");
            return;
        }
        timer = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "AutoSave");
            t.setDaemon(true);
            t.setPriority(Thread.NORM_PRIORITY - 1);
            return t;
        });
        timer.scheduleWithFixedDelay(() -> {
            try {
                Result r = saveAll(GAP_MS);
                if (r.total() > 0) {
                    log.info("[AutoSave] " + r.summary());
                }
            } catch (Throwable t) {
                log.error("[AutoSave] round failed", t);
            }
        }, minutes, minutes, TimeUnit.MINUTES);
        log.info("[AutoSave] every " + minutes + " min");
    }

    /** The world shutdown already saved everyone: the exit hook doesn't save again. */
    public static void markShutdownSaved() {
        shutdownSaved = true;
    }

    private static void onJvmExit() {
        if (shutdownSaved) {
            return;
        }
        try {
            Result r = saveAll(0);
            log.warn("[AutoSave] at exit: " + r.summary());
        } catch (Throwable t) {
            log.error("[AutoSave] exit save failed", t);
        }
    }

    /** Saves every online character now (humans first), {@code gapMs} apart. */
    public static Result saveAll(long gapMs) {
        synchronized (roundLock) {
            long start = System.currentTimeMillis();
            List<Char> humans = new ArrayList<>();
            List<Char> bots = new ArrayList<>();
            for (Char chr : Server.getInstance().getAllOnlineCharacters()) {
                (net.swordie.ms.ext.ServerHooks.isAutomated(chr) ? bots : humans).add(chr);
            }
            Result r = new Result();
            for (Char chr : humans) {
                tally(r, save(chr), false);
                pause(gapMs);
            }
            for (Char chr : bots) {
                tally(r, save(chr), true);
                pause(gapMs);
            }
            r.ms = System.currentTimeMillis() - start;
            return r;
        }
    }

    /**
     * One character now, the way a round saves it (left alone mid channel change or in the Cash Shop). For a change
     * that another character or a table already holds (a trade, a parcel): a forced stop before the next round could
     * otherwise keep only one side of it.
     */
    public static boolean saveNow(Char chr) {
        return save(chr) == Outcome.SAVED;
    }

    private static void tally(Result r, Outcome o, boolean bot) {
        switch (o) {
            case SAVED -> {
                if (bot) {
                    r.bots++;
                } else {
                    r.humans++;
                }
            }
            case SKIPPED -> r.skipped++;
            case FAILED -> r.failed++;
        }
    }

    private enum Outcome { SAVED, SKIPPED, FAILED }

    /**
     * One character, under its login's save lock (logout and channel change take the same lock, so two saves never
     * insert the same new rows). Characters mid channel change or in the Cash Shop are left to the save that ends
     * that move; a logged-out character is never saved again.
     */
    private static Outcome save(Char chr) {
        User user = chr.getUser();
        if (user == null) {
            return Outcome.SKIPPED;
        }
        for (int attempt = 0; attempt < 2; attempt++) {
            synchronized (user) {
                if (!chr.isOnline() || chr.isChangingChannel() || chr.isInCashShop() || chr.getUser() != user
                        || chr.getAccount() == null || chr.getAvatarData() == null) {
                    return Outcome.SKIPPED;
                }
                try {
                    userDao.saveOrUpdate(user, chr);
                    return Outcome.SAVED;
                } catch (Exception e) {
                    // gameplay can change a list while it's written; the second try usually goes through
                    if (attempt == 1) {
                        log.warn("[AutoSave] couldn't save " + chr.getName() + " (" + chr.getId() + "): " + e);
                    }
                }
            }
            pause(100);
        }
        return Outcome.FAILED;
    }

    private static void pause(long ms) {
        if (ms <= 0) {
            return;
        }
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static final class Result {
        public int humans;
        public int bots;
        public int skipped;
        public int failed;
        public long ms;

        public int total() {
            return humans + bots + skipped + failed;
        }

        public String summary() {
            return String.format("saved %d (%d players, %d bots), skipped %d, failed %d in %d ms",
                    humans + bots, humans, bots, skipped, failed, ms);
        }

        public Map<String, Object> toMap() {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("saved", humans + bots);
            m.put("humans", humans);
            m.put("bots", bots);
            m.put("skipped", skipped);
            m.put("failed", failed);
            m.put("ms", ms);
            return m;
        }
    }
}
