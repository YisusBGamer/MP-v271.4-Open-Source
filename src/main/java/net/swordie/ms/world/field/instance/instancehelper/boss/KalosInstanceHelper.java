package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.connection.packet.field.KalosFieldPacket;
import net.swordie.ms.connection.packet.field.kalos.KalosResult;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Kalos the Guardian (maplestorywiki.net, GMS): Easy, Normal, Chaos, Extreme; level 265, 30 minutes, death count 8.
 * <ul>
 *     <li>Phase 1: Kalos and his four structures (Eye of Penitence left, Eye of the Abyss right, Beam Strafe Maneuver
 *     top, Odium Sphere). A timer wakes a random sleeping structure every 80/70/60/60 s, a second one every 150 s
 *     (it stops while Kalos is bound). Kalos's Will (the harvest key) fires arrows: enough hits put an Eye down; the
 *     drone falls when it flies under a generator an arrow downed, the sphere when a generator's barrier hits it.
 *     All four awake when the main timer runs out fails the fight. Chaos/Extreme: three awake power them all up.</li>
 *     <li>Phase 2: the body holds the HP in four 25% parts; one form fights at a time and takes the hits for the
 *     body (HpLinkMob). At 75%, 50% and 25% comes Guardian's Frenzy (three map-wide 999% hits 4 s apart, only the
 *     domes are safe): survived, a part breaks (Shoulder Wings, Upper Wings, Armguard) and the next form takes over
 *     groggy for 15 s; a death in it heals him 5% and it comes again.</li>
 *     <li>Then the treasure room (Guardian Kalos Treasure Chest).</li>
 * </ul>
 * The client runs the structures' attacks and its own arrow hits and reports them (KALOS_FIELD_REQUEST); the server
 * keeps the timers, hit counts and sequence numbers and answers everyone (KALOS_FIELD_RESULT). Layouts and values
 * are from GMS Easy/Normal phase 1; the Frenzy domes' layout is from the client only.
 */
public class KalosInstanceHelper extends BossMechanicsHelper {

    private static final int[] FORM_THRESHOLDS = {75, 50, 25};
    private static final Position P2_POS = new Position(0, 399);

    private static final int PENITENCE = 0, ABYSS = 1, BEAM = 2, SPHERE = 3;
    private static final int[][] STRUCTURE_SPOTS = {{-612, -233}, {1626, -233}, {630, -553}, {0, 0}};
    private static final int[] STRUCTURE_FLAGS = {1, 0, 0, 0};
    private static final int[][] BEAM_GENERATORS = {{-800, -800}, {-400, -800}, {0, -800}, {400, -800},
            {800, -800}, {1200, -800}, {1600, -800}};
    private static final int[][] SPHERE_GENERATORS = {{1690, 534}, {1209, 534}, {609, 534}, {9, 534}, {-609, 534}};
    private static final int[][] SPHERE_PATH = {{1690, 389}, {1509, -553}, {1209, 389}, {909, -553}, {609, 389},
            {309, -553}, {9, 389}, {-309, -553}, {-609, 389}};
    private static final int DRONE_LEFT = -720, DRONE_RIGHT = 1785;
    private static final int[] ACTIVATION_MS = {80_000, 70_000, 60_000, 60_000};
    private static final int SECOND_ACTIVATION_MS = 150_000;
    private static final int[] EYE_INTERVAL_MS = {15_000, 15_000, 13_500, 13_000};
    private static final int[] EYE_HITS = {2, 2, 3, 3}, BEAM_PASSES = {1, 1, 2, 2}, SPHERE_HITS = {1, 2, 2, 2};
    private static final int[] ABYSS_KINDS = {17, 23, 29};

    private static final int FRENZY_HITS = 3, FRENZY_FIRST_MS = 4000, FRENZY_GAP_MS = 4000, GROGGY_S = 15;
    private static final int[] DOME_BOX = {-150, -200, 150, 10};

    private volatile int mode = -1;
    private volatile int fieldId;
    private volatile Mob form;
    private int formIndex;

    // phase 1 structures
    private volatile boolean structuresOn;
    private final boolean[] awake = new boolean[4];
    private final int[] hits = new int[4], needed = new int[4];
    private int laserSeq, volleySeq, droneSeq, arrowSeq;
    private int droneX = 0;
    private long activationAt, secondActivationAt, secondPausedLeft = -1;
    private volatile boolean poweredUp;
    private final Map<Integer, Integer> arrows = new ConcurrentHashMap<>(); // arrow id -> shooter
    private final Set<Integer> spentArrows = ConcurrentHashMap.newKeySet();

    // phase 2 frenzy
    private volatile boolean inFrenzy;
    private volatile boolean frenzyDeath;
    private final List<int[]> domes = new ArrayList<>();

    private int mode() {
        if (mode < 0) {
            Object prop = getInstance() == null ? null : getInstance().getProperty(BossConstants.KALOS_MODE_KEY);
            int m = prop instanceof Number n ? n.intValue() : -1;
            if (m < 0 && getInstance() != null) {
                m = BossConstants.getKalosMode(getInstance().getEnterFieldId());
            }
            mode = Math.max(0, Math.min(3, m));
            fieldId = BossConstants.KALOS_P1_FIELDS[mode];
        }
        return mode;
    }

    @Override
    protected Field getField() {
        mode();
        return getInstance() == null ? null : getInstance().getField(fieldId);
    }

    @Override
    protected Set<Integer> getBossIds() {
        return Set.of(BossConstants.KALOS_P2_BODY[mode()]);
    }

    /** The template the structures' attacks are worked out from (GMS: the one after phase 1 Kalos). */
    private int structureMob() {
        return BossConstants.KALOS_P1[mode()] + 1;
    }

    private boolean inP1() {
        return fieldId == BossConstants.KALOS_P1_FIELDS[mode()];
    }

    private void broadcast(OutPacket packet) {
        Field field = getField();
        if (field != null) {
            field.broadcastPacket(packet);
        }
    }

    @Override
    protected void startMechanics() {
        if (inP1()) {
            startStructures();
        }
        every(1000, this::tick);
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        super.onWarp(chr, oldField, newField);
        if (newField == null || newField.getId() != fieldId) {
            return;
        }
        if (structuresOn && inP1()) {
            sendSetup(chr);
        } else if (!inP1()) {
            chr.write(KalosFieldPacket.result(KalosResult.willConfig()));
        }
    }

    // --- Phase 1: the structures ------------------------------------------------------------------------------------

    private synchronized void startStructures() {
        long now = System.currentTimeMillis();
        activationAt = now + ACTIVATION_MS[mode()];
        secondActivationAt = now + SECOND_ACTIVATION_MS;
        secondPausedLeft = -1;
        structuresOn = true;
        Field field = getField();
        if (field != null) {
            for (Char chr : field.getChars()) {
                sendSetup(chr);
            }
        }
    }

    private synchronized void sendSetup(Char chr) {
        long now = System.currentTimeMillis();
        chr.write(KalosFieldPacket.result(KalosResult.willConfig()));
        chr.write(KalosFieldPacket.result(KalosResult.init((int) Math.max(0, activationAt - now), secondLeft(now), STRUCTURE_SPOTS,
                STRUCTURE_FLAGS)));
        chr.write(KalosFieldPacket.result(KalosResult.beamGenerators(BEAM_GENERATORS)));
        chr.write(KalosFieldPacket.result(KalosResult.sphereGenerators(SPHERE_GENERATORS)));
        for (int s = 0; s < 4; s++) {
            if (awake[s]) {
                chr.write(wakePacket(s));
            }
        }
        if (poweredUp) {
            chr.write(KalosFieldPacket.result(KalosResult.poweredUp(true)));
        }
    }

    private int secondLeft(long now) {
        return (int) Math.max(0, secondPausedLeft >= 0 ? secondPausedLeft : secondActivationAt - now);
    }

    private int partySize() {
        Field field = getField();
        return Math.max(1, field == null ? 1 : field.getChars().size());
    }

    private OutPacket wakePacket(int s) {
        int m = mode();
        return switch (s) {
            case PENITENCE -> KalosFieldPacket.result(KalosResult.penitenceOn(structureMob(), EYE_INTERVAL_MS[m], needed[s]));
            case ABYSS -> KalosFieldPacket.result(KalosResult.abyssOn(structureMob(), EYE_INTERVAL_MS[m], needed[s]));
            case BEAM -> KalosFieldPacket.result(KalosResult.beamDrone(droneSeq, structureMob(), needed[s], true, 0, 0, droneX));
            default -> KalosFieldPacket.result(KalosResult.sphereOn(structureMob(), needed[s], SPHERE_PATH));
        };
    }

    private int awakeCount() {
        int n = 0;
        for (boolean a : awake) {
            n += a ? 1 : 0;
        }
        return n;
    }

    /** Wakes a random sleeping structure; with all four awake the main timer running out ends the fight. */
    private synchronized void activateRandom(boolean mainTimer) {
        List<Integer> asleep = new ArrayList<>();
        for (int s = 0; s < 4; s++) {
            if (!awake[s]) {
                asleep.add(s);
            }
        }
        if (asleep.isEmpty()) {
            if (mainTimer) {
                fail();
            }
            return;
        }
        int s = asleep.get(Util.getRandom(asleep.size() - 1));
        int m = mode();
        needed[s] = partySize() * switch (s) {
            case PENITENCE, ABYSS -> EYE_HITS[m];
            case BEAM -> BEAM_PASSES[m];
            default -> SPHERE_HITS[m];
        };
        hits[s] = 0;
        awake[s] = true;
        if (s == BEAM) {
            droneX = nextDroneTarget(0);
        }
        broadcast(wakePacket(s));
        updatePowerUp();
    }

    private synchronized void putDown(int s) {
        if (!awake[s]) {
            return;
        }
        boolean wasAll = awakeCount() == 4;
        awake[s] = false;
        hits[s] = 0;
        switch (s) {
            case PENITENCE -> broadcast(KalosFieldPacket.result(KalosResult.penitenceOff()));
            case ABYSS -> broadcast(KalosFieldPacket.result(KalosResult.abyssOff()));
            case SPHERE -> broadcast(KalosFieldPacket.result(KalosResult.sphereOff()));
            default -> {
                // the drone leaves with the last generator progress packet
            }
        }
        if (wasAll && structuresOn) {
            activationAt = System.currentTimeMillis() + ACTIVATION_MS[mode()];
            broadcast(KalosFieldPacket.result(KalosResult.timer(false, ACTIVATION_MS[mode()])));
        }
        updatePowerUp();
    }

    private void updatePowerUp() {
        if (mode() < 2) {
            return;
        }
        boolean on = awakeCount() >= 3;
        if (on != poweredUp) {
            poweredUp = on;
            broadcast(KalosFieldPacket.result(KalosResult.poweredUp(on)));
        }
    }

    private void fail() {
        if (!structuresOn) {
            return;
        }
        structuresOn = false;
        notice("All of Kalos's structures are awake. The Guardian's power overwhelms you...");
        EventManager.addEvent(() -> {
            if (getInstance() != null) {
                getInstance().clear();
            }
        }, 3000);
    }

    private Mob p1Boss() {
        Field field = getInstance() == null ? null
                : getInstance().getFields().get(BossConstants.KALOS_P1_FIELDS[mode()]);
        if (field == null) {
            return null;
        }
        for (Mob mob : new ArrayList<>(field.getMobs())) {
            if (mob.getTemplateId() == BossConstants.KALOS_P1[mode()] && !mob.isDead()) {
                return mob;
            }
        }
        return null;
    }

    /** Below half HP the Eyes fire more (two lasers, marked arrows), as GMS late in the fight. */
    private boolean eyesEnhanced() {
        Mob boss = p1Boss();
        return boss != null && boss.getMaxHp() > 0 && boss.getHp() * 2 < boss.getMaxHp();
    }

    private int nextDroneTarget(int fromX) {
        int x = fromX;
        for (int i = 0; i < 8 && Math.abs(x - fromX) < 250; i++) {
            x = Util.getRandom(DRONE_LEFT + 50, DRONE_RIGHT - 50);
        }
        return x;
    }

    private int[] randomSpot() {
        Position p = randomGround();
        return new int[]{p.getX(), p.getY()};
    }

    private Char randomLiving() {
        List<Char> alive = getLivingChars();
        return alive.isEmpty() ? null : alive.get(Util.getRandom(alive.size() - 1));
    }

    private synchronized void p1Tick() {
        if (!structuresOn) {
            return;
        }
        long now = System.currentTimeMillis();
        Mob boss = p1Boss();
        boolean bound = boss != null && boss.getTemporaryStat().hasCurrentMobStat(MobStat.Freeze);
        if (bound && secondPausedLeft < 0) {
            secondPausedLeft = Math.max(0, secondActivationAt - now);
            broadcast(KalosFieldPacket.result(KalosResult.timer(true, (int) secondPausedLeft)));
        } else if (!bound && secondPausedLeft >= 0) {
            secondActivationAt = now + secondPausedLeft;
            secondPausedLeft = -1;
            broadcast(KalosFieldPacket.result(KalosResult.timer(true, secondLeft(now))));
        }
        if (now >= activationAt) {
            activationAt = now + ACTIVATION_MS[mode()];
            activateRandom(true);
            if (structuresOn) {
                broadcast(KalosFieldPacket.result(KalosResult.timer(false, ACTIVATION_MS[mode()])));
            }
        }
        if (structuresOn && secondPausedLeft < 0 && now >= secondActivationAt) {
            secondActivationAt = now + SECOND_ACTIVATION_MS;
            activateRandom(false);
            broadcast(KalosFieldPacket.result(KalosResult.timer(true, SECOND_ACTIVATION_MS)));
        }
    }

    /**
     * KALOS_FIELD_REQUEST. Type 1 sub 3: the main timer; type 3: an awake structure's own event (4 the drone reached
     * its spot, 5 Penitence fires, 6 Abyss fires, 7 an Abyss arrow hit the sender); type 5: Kalos's Will (9 fire, 10/11
     * an arrow downed a beam/sphere generator, 12/13 an arrow hit Penitence/Abyss, 14 the drone passed under a downed
     * generator, 15 a barrier hit the sphere). Each event is answered once (by its sequence number).
     */
    public synchronized void onRequest(Char chr, int type, int sub, InPacket in) {
        if (chr.getField() == null || chr.getField().getId() != fieldId) {
            return;
        }
        long now = System.currentTimeMillis();
        int left = in.getUnreadAmount();
        if (type == 1 && sub == 3) {
            if (structuresOn) {
                chr.write(KalosFieldPacket.result(KalosResult.timer(false, (int) Math.max(0, activationAt - now))));
            }
        } else if (type == 5 && sub == 9 && left >= 12) {
            int angle = in.decodeInt();
            int x = in.decodeInt();
            int y = in.decodeInt();
            int id = arrowSeq++;
            arrows.put(id, chr.getId());
            broadcast(KalosFieldPacket.result(KalosResult.arrow(id, chr.getId(), angle, x, y)));
        } else if (type == 5 && (sub == 10 || sub == 11) && left >= 8) {
            int arrow = in.decodeInt();
            int generator = in.decodeInt();
            if (arrows.containsKey(arrow)) {
                broadcast(KalosFieldPacket.result(KalosResult.generatorHit(sub == 11, chr.getId(), generator)));
            }
        } else if (type == 5 && (sub == 12 || sub == 13) && left >= 4) {
            int arrow = in.decodeInt();
            int s = sub == 12 ? PENITENCE : ABYSS;
            if (awake[s] && arrows.containsKey(arrow) && spentArrows.add(arrow)) {
                hits[s]++;
                if (hits[s] >= needed[s]) {
                    putDown(s);
                    broadcast(KalosFieldPacket.result(KalosResult.eyeHits(s == ABYSS, chr.getId(), 0)));
                } else {
                    broadcast(KalosFieldPacket.result(KalosResult.eyeHits(s == ABYSS, chr.getId(), hits[s])));
                }
            }
        } else if (type == 5 && sub == 14 && left >= 16) {
            int generator = in.decodeInt();
            in.decodeInt();
            int x = in.decodeInt();
            int y = in.decodeInt();
            if (awake[BEAM]) {
                broadcast(KalosFieldPacket.result(KalosResult.beamPass(x, y)));
                int h = ++hits[BEAM];
                boolean done = h >= needed[BEAM];
                broadcast(KalosFieldPacket.result(KalosResult.generatorProgress(false, generator, h, !done)));
                if (done) {
                    putDown(BEAM);
                }
            }
        } else if (type == 5 && sub == 15 && left >= 8) {
            int generator = in.decodeInt();
            in.decodeInt();
            if (awake[SPHERE]) {
                int h = ++hits[SPHERE];
                boolean done = h >= needed[SPHERE];
                if (done) {
                    putDown(SPHERE);
                }
                broadcast(KalosFieldPacket.result(KalosResult.generatorProgress(true, generator, h, !done)));
            }
        } else if (type == 3 && sub == 4 && left >= 16) {
            int seq = in.decodeInt();
            in.decodeInt(); // direction
            int x = in.decodeInt();
            int y = in.decodeInt();
            if (awake[BEAM] && seq == droneSeq) {
                droneSeq++;
                droneX = nextDroneTarget(x);
                broadcast(KalosFieldPacket.result(KalosResult.beamDrone(droneSeq, structureMob(), needed[BEAM], false, x, y, droneX)));
            }
        } else if (type == 3 && sub == 5 && left >= 4) {
            int seq = in.decodeInt();
            if (awake[PENITENCE] && seq == laserSeq) {
                laserSeq++;
                List<int[]> spots = new ArrayList<>();
                Char target = randomLiving();
                if (target != null && target.getPosition() != null) {
                    spots.add(new int[]{target.getPosition().getX(), target.getPosition().getY()});
                }
                while (spots.size() < (eyesEnhanced() ? 2 : 1)) {
                    spots.add(randomSpot());
                }
                broadcast(KalosFieldPacket.result(KalosResult.penitenceLasers(laserSeq, spots)));
            }
        } else if (type == 3 && sub == 6 && left >= 4) {
            int seq = in.decodeInt();
            if (awake[ABYSS] && seq == volleySeq) {
                volleySeq++;
                int[] targets = new int[ABYSS_KINDS.length];
                List<int[]> marks = eyesEnhanced() ? new ArrayList<>() : null;
                for (int i = 0; i < targets.length; i++) {
                    Char t = randomLiving();
                    targets[i] = t == null ? chr.getId() : t.getId();
                    if (marks != null) {
                        marks.add(randomSpot());
                    }
                }
                broadcast(KalosFieldPacket.result(KalosResult.abyssVolley(volleySeq, ABYSS_KINDS, targets, marks)));
            }
        } else if (type == 3 && sub == 7 && left >= 4) {
            broadcast(KalosFieldPacket.result(KalosResult.abyssHit(in.decodeInt(), chr.getId())));
        }
    }

    // --- Phase transitions ------------------------------------------------------------------------------------------

    @Override
    public void onMobDeath(Field field, Mob mob) {
        int m = mode();
        if (mob.getTemplateId() == BossConstants.KALOS_P1[m] && field.getId() == BossConstants.KALOS_P1_FIELDS[m]) {
            synchronized (this) {
                structuresOn = false;
                for (int s = 0; s < 4; s++) {
                    putDown(s);
                }
            }
            notice("Kalos the Guardian gathers his full power...");
            EventManager.addEvent(() -> {
                for (Mob rest : new ArrayList<>(field.getMobs())) {
                    if (rest.getTemplateId() == BossConstants.KALOS_P1_REVIVE[m]) {
                        field.removeLife(rest.getObjectId());
                    }
                }
                fieldId = BossConstants.KALOS_P2_FIELDS[m];
                getInstance().warp(fieldId);
                later(2000, this::startPhase2);
            }, 3000);
            return;
        }
        boolean bodyDied = getBossIds().contains(mob.getTemplateId()) && !isBossDead();
        super.onMobDeath(field, mob);
        if (bodyDied) {
            notice("Kalos the Guardian has been defeated! His treasure awaits.");
            EventManager.addEvent(() -> {
                fieldId = BossConstants.KALOS_REWARD_FIELDS[m];
                getInstance().warp(fieldId);
            }, 4000);
        }
    }

    private void startPhase2() {
        Field field = getField();
        int m = mode();
        if (field == null || field.getId() != BossConstants.KALOS_P2_FIELDS[m] || getBoss() != null) {
            return;
        }
        long hp = BossConstants.KALOS_P2_HP[m];
        field.spawnMob(BossConstants.KALOS_P2_BODY[m], P2_POS.getX(), P2_POS.getY(), false, hp);
        formIndex = 0;
        spawnForm(P2_POS);
        field.broadcastPacket(KalosFieldPacket.result(KalosResult.willConfig()));
    }

    private void spawnForm(Position pos) {
        Mob body = getBoss();
        if (body == null) {
            return;
        }
        Mob next = spawnAdd(BossConstants.KALOS_P2_FIRST_FORM[mode()] + formIndex, pos, body.getMaxHp());
        if (next != null) {
            next.setHpLinkMob(body);
            form = next;
        }
    }

    private void tick() {
        if (inP1()) {
            p1Tick();
            return;
        }
        Field field = getField();
        Mob body = getBoss();
        if (field == null || field.getId() != BossConstants.KALOS_P2_FIELDS[mode()] || body == null || inFrenzy) {
            return;
        }
        if (formIndex < FORM_THRESHOLDS.length
                && body.getHp() * 100 <= (long) FORM_THRESHOLDS[formIndex] * body.getMaxHp()) {
            startFrenzy();
        } else if (form == null || form.isDead() || field.getLifeByObjectID(form.getObjectId()) == null) {
            spawnForm(P2_POS); // the form was lost (e.g. removed by a script): bring it back
        }
    }

    // --- Phase 2: Guardian's Frenzy ---------------------------------------------------------------------------------

    private void startFrenzy() {
        Field field = getField();
        if (field == null) {
            return;
        }
        inFrenzy = true;
        frenzyDeath = false;
        despawn(form); // he takes the centre and can't be hit until it is over
        form = null;
        synchronized (domes) {
            domes.clear();
            for (int i = 0; i < FRENZY_HITS; i++) {
                domes.add(randomSpot());
            }
            field.broadcastPacket(KalosFieldPacket.result(KalosResult.frenzyStart(domes)));
        }
        notice("Guardian's Frenzy! Take shelter in the domes!");
        for (int i = 0; i < FRENZY_HITS; i++) {
            boolean last = i == FRENZY_HITS - 1;
            later(FRENZY_FIRST_MS + (long) i * FRENZY_GAP_MS, () -> {
                frenzyHit();
                if (last) {
                    endFrenzy();
                }
            });
        }
    }

    private boolean inDome(Char chr) {
        Position p = chr.getPosition();
        if (p == null) {
            return false;
        }
        synchronized (domes) {
            for (int[] d : domes) {
                if (p.getX() >= d[0] + DOME_BOX[0] && p.getX() <= d[0] + DOME_BOX[2]
                        && p.getY() >= d[1] + DOME_BOX[1] && p.getY() <= d[1] + DOME_BOX[3]) {
                    return true;
                }
            }
        }
        return false;
    }

    private void frenzyHit() {
        for (Char chr : getLivingChars()) {
            if (!inDome(chr)) {
                frenzyDeath = true;
                chr.damage(chr.getMaxHP()); // the 999% hit
            }
        }
    }

    @Override
    public void onChrDeath(Char chr) {
        super.onChrDeath(chr);
        if (inFrenzy) {
            frenzyDeath = true;
        }
    }

    private void endFrenzy() {
        Field field = getField();
        Mob body = getBoss();
        if (field == null || body == null) {
            inFrenzy = false;
            return;
        }
        if (frenzyDeath) {
            field.broadcastPacket(KalosFieldPacket.result(KalosResult.frenzyEnd(false)));
            body.setHp(Math.min(body.getMaxHp(), body.getHp() + body.getMaxHp() / 20));
            notice("The Frenzy claimed a life. Kalos the Guardian recovers his strength.");
            spawnForm(P2_POS);
        } else {
            field.broadcastPacket(KalosFieldPacket.result(KalosResult.frenzyEnd(true)));
            formIndex++;
            notice(switch (formIndex) {
                case 1 -> "Kalos's Shoulder Wings break!";
                case 2 -> "Kalos's Upper Wings break!";
                default -> "Kalos's Armguard breaks!";
            });
            spawnForm(P2_POS);
            if (form != null) {
                Option o = new Option();
                o.nOption = 1;
                o.tOption = GROGGY_S;
                form.getTemporaryStat().addStatOptionsAndBroadcast(null, MobStat.Stun, o);
            }
        }
        inFrenzy = false;
    }
}
