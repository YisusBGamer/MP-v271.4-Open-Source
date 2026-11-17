package net.swordie.ms.world.field.instance.instancehelper.arcane;

import net.swordie.ms.connection.packet.field.RanheimDefenseFieldPacket;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;

/**
 * Ranheim Defense (Morass, 993195700, field type 286; as GMS): keep the Ranheim Rod standing while
 * Hekaton's Body Fragments gather. Killing monsters charges Light Magic (+100 a kill, full at 1,000); with a full
 * charge the harvest key on a Light Magic Launcher (shooter0-5) fires it (In 1734 launcher) and the client reports
 * the shot (In 1735 launcher): it destroys the Body Fragment standing in front of that launcher. After 9 fragments
 * Hekaton's Fragment comes and has to be hit 3 times; it moves to another launcher after each hit. 10 minutes.
 * The launcher spots are Map.wz 993195700 mobTeleport 0-5.
 */
public class RanheimDefenseInstanceHelper extends InstanceHelper {
    public static final int FIELD = 993195700;
    public static final int EXIT = 993195800;
    /** Etc.wz MapleScheduler: the weekly clears; also the run's record (start, clear, ctime, boss, hp) */
    public static final int WEEKLY_QR = 39077;
    public static final int TIME_LIMIT_SECONDS = 600;
    public static final int REWARD_COUPON = 2635513; // Arcane Symbol: Morass Coupon
    public static final int REWARD_COUPONS = 80;
    public static final long REWARD_EXP = 1_127_760_555L;

    private static final int BODY_FRAGMENT = 9833981;
    private static final int HEKATON_FRAGMENT = 9833982;
    private static final int RANHEIM_ROD = 9833980;
    private static final int[][] SPOTS = {{-1770, -860}, {-1770, -570}, {-1770, -270}, {1770, -860}, {1770, -570},
            {1770, -270}};
    private static final int FRAGMENTS = 9;
    private static final int HEKATON_HITS = 3;
    private static final int GAUGE_FULL = 1000;
    private static final int GAUGE_PER_KILL = 100;
    private static final int FIRST_FRAGMENT_MS = 11_000;
    private static final int NEXT_FRAGMENT_MS = 5_000;
    // a guess (GMS's rod only lost 2%): a standing fragment wears the rod down 1% every 2 seconds
    private static final int ROD_TICK_MS = 2_000;
    private static final int WEATHER_ITEM = 5120218;
    private static final String[] TAUNTS = {"Who are you? This is my castle!",
            "My resurrection has begun... You waste your time!", "No mortal can stop me!",
            "You cannot stop the inevitable!", "Hmph. Who dares intrude?", "If you insist, I WILL put a stop to this."};

    private static final int STATE_RUNNING = 2;
    private static final int STATE_CLEARED = 3;
    private static final int EXPERIMENT_GONE_WRONG_MOB = 9833983;
    private static final int THRALLED_ARCHER_MOB = 9833988;

    private int gauge;
    private int destroyed;
    private int hekatonHits;
    private int rodHp = 100;
    private int launched = -1; // the launcher fired, until its shot comes back
    private int targetSpot = -1;
    private Mob target;
    private boolean over;
    private long startedAt;

    public static boolean isField(int fieldId) {
        return fieldId == FIELD;
    }

    public static boolean isRanheimMob(int template) {
        return template >= EXPERIMENT_GONE_WRONG_MOB && template <= THRALLED_ARCHER_MOB;
    }

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        startedAt = System.currentTimeMillis();
        for (Char chr : instance.getChars()) {
            var sm = chr.getScriptManager();
            sm.setQRValue(WEEKLY_QR, "start", "1");
            sm.setQRValue(WEEKLY_QR, "clear", "0");
            sm.progressMessageFont("Defeat monsters to charge Light Magic, then fire a Light Magic Launcher at the Body Fragment!");
        }
        sendState(STATE_RUNNING);
        addTimer(EventManager.addEvent(this::spawnNext, FIRST_FRAGMENT_MS));
        addTimer(EventManager.addFixedRateEvent(this::rodTick, ROD_TICK_MS, ROD_TICK_MS));
    }

    @Override
    protected void startStage(int stageNumber) {
        // one stage, set up in setup()
    }

    private Char player() {
        var chars = getInstance().getChars();
        return chars.isEmpty() ? null : chars.get(0);
    }

    private void sendState(int state) {
        Field field = getField();
        if (field == null) {
            return;
        }
        field.broadcastPacket(RanheimDefenseFieldPacket.state(state, destroyed + (state == STATE_CLEARED ? 1 : 0), gauge));
    }

    private static int otherSpot(int not) {
        int s;
        do {
            s = Util.getRandom(SPOTS.length - 1);
        } while (s == not && SPOTS.length > 1);
        return s;
    }

    private synchronized void spawnNext() {
        Field field = getField();
        if (over || field == null) {
            return;
        }
        boolean hekaton = destroyed >= FRAGMENTS;
        targetSpot = otherSpot(targetSpot);
        int[] p = SPOTS[targetSpot];
        target = field.spawnMob(hekaton ? HEKATON_FRAGMENT : BODY_FRAGMENT, p[0], p[1], false, 0);
        if (hekatonHits == 0) {
            String taunt = hekaton ? "Then I suppose you've forced my hand!" : TAUNTS[Math.min(destroyed, TAUNTS.length - 1)];
            field.broadcastPacket(FieldPacket.blowWeather(WEATHER_ITEM, "   " + taunt + "  ", 5));
        }
    }

    /** Ordinary monsters charge Light Magic */
    @Override
    public void onMobDeath(Field field, Mob mob) {
        if (over) {
            return;
        }
        if (mob.getTemplateId() == RANHEIM_ROD) {
            fail("The Ranheim Rod has fallen...");
            return;
        }
        if (!isRanheimMob(mob.getTemplateId())) {
            return;
        }
        synchronized (this) {
            if (gauge < GAUGE_FULL) {
                gauge = Math.min(GAUGE_FULL, gauge + GAUGE_PER_KILL);
                sendState(STATE_RUNNING);
            }
        }
    }

    /** In 1734: the harvest key on a launcher with a full charge; GMS answers 933 (char, launcher) and empties it */
    public synchronized void launch(Char chr, int launcher) {
        if (over || launcher < 0 || launcher >= SPOTS.length || gauge < GAUGE_FULL) {
            return;
        }
        gauge = 0;
        launched = launcher;
        getField().broadcastPacket(RanheimDefenseFieldPacket.launch(chr.getId(), launcher));
        sendState(STATE_RUNNING);
    }

    /** In 1735: the launcher's shot landed; it hits only what stands in front of it (GMS: no reply on a miss) */
    public synchronized void shotLanded(Char chr, int launcher) {
        Field field = getField();
        if (over || launcher != launched || field == null) {
            return;
        }
        launched = -1;
        if (launcher != targetSpot || target == null || field.getLifeByObjectID(target.getObjectId()) == null) {
            return;
        }
        field.broadcastPacket(RanheimDefenseFieldPacket.shotHit(launcher, target.getObjectId()));
        boolean hekaton = target.getTemplateId() == HEKATON_FRAGMENT;
        field.removeLife(target.getObjectId(), true);
        target = null;
        if (!hekaton) {
            destroyed++;
            sendState(STATE_RUNNING);
            chr.getScriptManager().progressMessageFont("Body Fragment destroyed! (" + destroyed + " / " + FRAGMENTS + ")");
            addTimer(EventManager.addEvent(this::spawnNext, NEXT_FRAGMENT_MS));
            return;
        }
        hekatonHits++;
        if (hekatonHits >= HEKATON_HITS) {
            clear(chr);
        } else {
            chr.getScriptManager().progressMessageFont("Hekaton's Fragment is reeling! Hit it " + (HEKATON_HITS - hekatonHits)
                    + " more time" + (HEKATON_HITS - hekatonHits == 1 ? "" : "s") + ".");
            spawnNext(); // it moves to another launcher at once
        }
    }

    private synchronized void rodTick() {
        Field field = getField();
        if (over || field == null || target == null || field.getLifeByObjectID(target.getObjectId()) == null) {
            return;
        }
        rodHp--;
        Char chr = player();
        if (chr != null && (rodHp == 50 || rodHp == 25 || rodHp == 10)) {
            chr.getScriptManager().progressMessageFont("The Ranheim Rod is weakening! (" + rodHp + "%)");
        }
        if (rodHp <= 0) {
            fail("The Ranheim Rod has fallen...");
        }
    }

    private void fail(String msg) {
        over = true;
        Char chr = player();
        if (chr == null) {
            return;
        }
        chr.getScriptManager().progressMessageFont(msg);
        addTimer(EventManager.addEvent(() -> {
            if (chr.getInstance() == getInstance()) {
                getInstance().removeChar(chr, EXIT, 0, true, false);
            }
        }, 3000));
    }

    private void clear(Char chr) {
        over = true;
        sendState(STATE_CLEARED);
        var sm = chr.getScriptManager();
        int seconds = (int) ((System.currentTimeMillis() - startedAt) / 1000);
        sm.setQRValue(WEEKLY_QR, "clear", "1");
        sm.setQRValue(WEEKLY_QR, "ctime", String.valueOf(seconds));
        sm.setQRValue(WEEKLY_QR, "boss", String.valueOf(FRAGMENTS + 1));
        sm.setQRValue(WEEKLY_QR, "hp", String.valueOf(Math.max(0, rodHp)));
        ArcaneWeekly.addClear(chr, WEEKLY_QR);
        sm.showEffect("Map/Effect.img/killing/clear");
        sm.progressMessageFont("Hekaton's resurrection has been stopped! Talk to the Mysterious Researcher for your reward.");
        addTimer(EventManager.addEvent(() -> {
            if (chr.getInstance() == getInstance()) {
                getInstance().removeChar(chr, EXIT, 0, true, false);
            }
        }, 1000));
    }
}
