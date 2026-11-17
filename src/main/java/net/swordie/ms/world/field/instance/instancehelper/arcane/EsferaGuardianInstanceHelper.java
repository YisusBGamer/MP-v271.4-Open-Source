package net.swordie.ms.world.field.instance.instancehelper.arcane;

import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.connection.packet.field.EsferaGuardianFieldPacket;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.FieldAttackObjPool;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.FieldAttackObj;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Esfera Guardian (Esfera, 993195500; Lv. 235+, after quest 37871, solo, 3 clears a week): defeat the Light and Dark
 * Executors to fill the Erda gauge (1 per kill, up to 150), then fire the Special Turret's Super Erda Shell (In
 * ESFERA_GUARDIAN_REQUEST, 45 Erda a shot; the client throws it as skill 95001015) at the 10 Dimensional Gates. Each
 * gate falls to one hit; all 10 clear the run. Layouts and numbers as GMS v271.4.
 */
public class EsferaGuardianInstanceHelper extends InstanceHelper {
    private static final Logger log = LogManager.getLogger(EsferaGuardianInstanceHelper.class);

    public static final int FIELD = 993195500;
    public static final int EXIT = 993195600;
    /** Etc.wz MapleScheduler: Esfera Guardian's weekly clears (wCount, lastDate) and the clear flag (clear, ctim) */
    public static final int WEEKLY_QR = 34596;
    /** the run's record: erdapoint, targetpoint (gates down), PassCount, symbol (the reward waiting at the NPC) */
    public static final int SCORE_QR = 34598;
    public static final int TIME_LIMIT_SECONDS = 2400;

    private static final int MAX_ERDA = 150;
    private static final int GATES_TO_CLEAR = 10;
    private static final int ERDA_PER_SLOT = 15;
    private static final int ERDA_PER_SHOT = 45;
    // the other two values of GMS's start packet; their use isn't known
    private static final int INFO_5 = 1620;
    private static final int INFO_6 = 120;
    private static final int[] ERDA_MOBS = {9833973, 9833974}; // Light Executor, Dark Executor
    private static final int TURRET_TEMPLATE = 18;
    private static final Position TURRET_POS = new Position(2300, 100);
    // GMS's 10 gates: Small, Medium, Large Dimensional Gate at fixed spots
    private static final int[][] GATES = DataTable.read("esfera-guardian-gates").rows().stream()
            .map(r -> new int[]{r.i("mob"), r.i("x"), r.i("y")}).toArray(int[][]::new);

    private int erda;
    private int gatesDown;
    private boolean cleared;
    private long startedAt;

    public static boolean isField(int fieldId) {
        return fieldId == FIELD;
    }

    private static boolean isGate(int template) {
        return template >= 9833975 && template <= 9833977; // hardcode-ok
    }

    private static boolean isErdaMob(int template) {
        return template == ERDA_MOBS[0] || template == ERDA_MOBS[1];
    }

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        startedAt = System.currentTimeMillis();
        Field field = getField();
        for (int[] g : GATES) {
            field.spawnMob(g[0], g[1], g[2], false, 0);
        }
        for (Char chr : instance.getChars()) {
            FieldAttackObj turret = new FieldAttackObj(TURRET_TEMPLATE, 0, TURRET_POS.deepCopy(), true);
            turret.setReserveID(chr.getId());
            field.spawnLife(turret, chr);
            chr.write(FieldAttackObjPool.objCreate(turret));
            chr.write(info());
            var sm = chr.getScriptManager();
            sm.setQRValue(SCORE_QR, "PassCount", "0");
            sm.setQRValue(SCORE_QR, "erdapoint", "0");
            sm.setQRValue(SCORE_QR, "targetpoint", "0");
            sm.setQRValue(SCORE_QR, "symbol", "0");
            sm.setQRValue(WEEKLY_QR, "start", "1");
            sm.setQRValue(WEEKLY_QR, "clear", "0");
            sendGauge(chr);
            sm.showEffect("Map/Effect3.img/protectEspera/start");
        }
    }

    @Override
    protected void startStage(int stageNumber) {
        // one stage, set up in setup()
    }

    private Char player() {
        var chars = getInstance().getChars();
        return chars.isEmpty() ? null : chars.get(0);
    }

    /** ESFERA_GUARDIAN_INFO: max Erda, gates to clear, Erda a gauge slot, Erda a shot, 2 more ints, the Erda mobs */
    private static OutPacket info() {
        return EsferaGuardianFieldPacket.info(MAX_ERDA, GATES_TO_CLEAR, ERDA_PER_SLOT, ERDA_PER_SHOT, INFO_5, INFO_6,
                ERDA_MOBS);
    }

    /** ESFERA_GUARDIAN_GAUGE: int Erda, int gates down */
    private void sendGauge(Char chr) {
        chr.write(EsferaGuardianFieldPacket.gauge(erda, gatesDown));
        var sm = chr.getScriptManager();
        sm.setQRValue(SCORE_QR, "erdapoint", String.valueOf(erda));
        sm.setQRValue(SCORE_QR, "targetpoint", String.valueOf(gatesDown));
    }

    @Override
    public synchronized void onMobDeath(Field field, Mob mob) {
        if (cleared) {
            return;
        }
        Char chr = player();
        if (chr == null) {
            return;
        }
        int template = mob.getTemplateId();
        if (isErdaMob(template)) {
            if (erda < MAX_ERDA) {
                erda++;
                sendGauge(chr);
            }
        } else if (isGate(template)) {
            gatesDown++;
            sendGauge(chr);
            if (gatesDown >= GATES_TO_CLEAR) {
                clear(chr);
            }
        }
    }

    /**
     * The turret's fire request: GMS takes 45 Erda a shot (what's left when under 45), and the shell itself comes in
     * as an ordinary attack. @return whether the shot was paid for
     */
    public synchronized boolean fire(Char chr) {
        if (cleared || erda < ERDA_PER_SLOT) {
            return false;
        }
        erda -= Math.min(ERDA_PER_SHOT, erda);
        sendGauge(chr);
        return true;
    }

    private void clear(Char chr) {
        cleared = true;
        var sm = chr.getScriptManager();
        long secs = Math.max(1, (System.currentTimeMillis() - startedAt) / 1000);
        sm.setQRValue(WEEKLY_QR, "clear", "1");
        sm.setQRValue(WEEKLY_QR, "ctim", String.valueOf(secs));
        sm.setQRValue(SCORE_QR, "symbol", "1"); // the reward waits at Shubert outside
        sm.showEffect("Map/Effect3.img/protectEspera/clear");
        log.info(String.format("[EsferaGuardian] %s cleared in %d s", chr.getName(), secs));
        addTimer(net.swordie.ms.handlers.executors.EventManager.addEvent(() -> {
            if (chr.getInstance() == getInstance()) {
                getInstance().removeChar(chr, EXIT, 0, true, false);
            }
        }, 2000));
    }
}
