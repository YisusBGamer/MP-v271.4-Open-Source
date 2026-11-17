package net.swordie.ms.life.mob.boss.will;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.connection.packet.Effect;
import net.swordie.ms.connection.packet.MobPool;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.connection.packet.field.WillFieldPacket;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.enums.ObstacleAtomCreateType;
import net.swordie.ms.enums.ObstacleAtomEnum;
import net.swordie.ms.enums.WeatherEffNoticeType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.skill.MobSkillID;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.fieldeffect.FieldEffect;
import net.swordie.ms.world.field.obstacleatom.ObstacleAtomInfo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;

public class WillModule {

    private static final int P1_DIMENSION_FH_DIFF = 2179;
    private static final int BLUE_FH_Y = 159;
    private static final int PURPLE_FH_Y = -2020;
    private static final int OBSTACLE_ATOM_HEIGHT = 600;

    private static final Rect BLUE_DIM_RECT = new Rect(-800, -1000, 700, 200);
    private static final Rect PURPLE_DIM_RECT = new Rect(-800, -1000 - P1_DIMENSION_FH_DIFF, 700, 200 - P1_DIMENSION_FH_DIFF);
    private static final Long HIT_OA_COOLTIME = 1100L; // GMS: a batch every 1.1-3.5 s
    private static final int LIGHT_FALL = 586;

    // light orb key -> whether it is a safe one; the client reports the key it was sent
    private static final String ORB_KINDS_PROP = "willOrbKinds";
    private static final AtomicInteger NEXT_ORB_KEY = new AtomicInteger(1);

    private static final String WEB_MASK_PROP = "NarrowWebMask";
    private static final String WEB_FULL_PROP = "NarrowWebFull";

    private static final int WILL_P2_TEST_SETS = 3;
    private static final int WILL_P2_TEST_START_DELAY = 1900; // GMS: the first legs come 1.9 s after the notice
    private static final int WILL_P2_TEST_SET_PERIOD = 5400; // GMS: a pattern every 5.4 s (5 s next delay plus a pause)
    private static final int LEG_HIT_DELAY = 1200;
    private static final int LEG_LAST_ROW_DELAY = 4500; // last row of a reflected pattern
    private static final int LEG_NEXT_DELAY = 9000;
    private static final int REFLECTED_LEG_NEXT_DELAY = 5000;
    private static final int LEG_HALF_WIDTH = 40;
    private static final int PLAIN_LEG_HALF_WIDTH = 60;
    private static final int LEG_TOP = -600;
    private static final int LEG_BOTTOM = 10;
    private static final String LEG_ID_PROP = "willNextLegId";
    private static final int RESULT_MARGIN = 500;

    private static final NarrowWeb[] NARROW_WEBS = new NarrowWeb[] {
            new NarrowWeb(0, 0, new Position(-338, -225)),
            new NarrowWeb(1, 0, new Position(-370, -100)),
            new NarrowWeb(2, 0, new Position(-265, -125)),
            new NarrowWeb(3, 2, new Position(-130, -290)),
            new NarrowWeb(4, 0, new Position(70, -240)),
            new NarrowWeb(5, 0, new Position(190, -290)),
            new NarrowWeb(6, 0, new Position(290, -265)),
            new NarrowWeb(7, 1, new Position(420, -280)),
            new NarrowWeb(8, 2, new Position(625, -200)),
            new NarrowWeb(9, 1, new Position(645, 0)),
            new NarrowWeb(10, 0, new Position(685, 125)),
            new NarrowWeb(11, 0, new Position(550, 140)),
            new NarrowWeb(12, 1, new Position(750, 240)),
            new NarrowWeb(13, 0, new Position(620, 230)),
            new NarrowWeb(14, 2, new Position(620, 430)),
            new NarrowWeb(15, 0, new Position(420, 400)),
            new NarrowWeb(16, 0, new Position(340, 450)),
            new NarrowWeb(17, 0, new Position(240, 380)),
            new NarrowWeb(18, 1, new Position(100, 400)),
            new NarrowWeb(19, 0, new Position(-30, 410)),
            new NarrowWeb(20, 0, new Position(-130, 360)),
            new NarrowWeb(21, 0, new Position(-180, 450)),
            new NarrowWeb(22, 1, new Position(-280, 380)),
            new NarrowWeb(23, 2, new Position(-490, 470)),
            new NarrowWeb(24, 0, new Position(-400, 360)),
            new NarrowWeb(25, 1, new Position(-700, 415)),
            new NarrowWeb(26, 0, new Position(-600, 320)),
            new NarrowWeb(27, 1, new Position(-750, 270)),
            new NarrowWeb(28, 0, new Position(-620, 200)),
            new NarrowWeb(29, 2, new Position(-750, 50)),
            new NarrowWeb(30, 1, new Position(-600, -100)),
            new NarrowWeb(31, 1, new Position(-750, -300)),
            new NarrowWeb(32, 0, new Position(-720, -150)),
            new NarrowWeb(33, 0, new Position(-620, -220)),
            new NarrowWeb(34, 1, new Position(-480, -290)),
            new NarrowWeb(35, 0, new Position(-470, -150)),
            new NarrowWeb(36, 1, new Position(180, -150)),
            new NarrowWeb(37, 1, new Position(0, -125)),
            new NarrowWeb(38, 0, new Position(-150, -75)),
            new NarrowWeb(39, 0, new Position(325, -150)),
            new NarrowWeb(40, 0, new Position(425, -160)),
            new NarrowWeb(41, 0, new Position(425, -60)),
            new NarrowWeb(42, 0, new Position(525, -30)),
            new NarrowWeb(43, 0, new Position(535, 50)),
            new NarrowWeb(44, 1, new Position(-535, 30)),
            new NarrowWeb(45, 1, new Position(480, 280)),
            new NarrowWeb(46, 0, new Position(-460, -50)),
            new NarrowWeb(47, 1, new Position(400, 75)),
            new NarrowWeb(48, 1, new Position(300, -50)),
            new NarrowWeb(49, 2, new Position(-460, 230)),
            new NarrowWeb(50, 2, new Position(-160, 79)),
            new NarrowWeb(51, 1, new Position(-360, 30)),
            new NarrowWeb(52, 1, new Position(-250, 250)),
            new NarrowWeb(53, 0, new Position(375, 220)),
            new NarrowWeb(54, 0, new Position(335, 350)),
            new NarrowWeb(55, 0, new Position(270, 75)),
            new NarrowWeb(56, 1, new Position(120, 0)),
            new NarrowWeb(57, 0, new Position(0, 75)),
            new NarrowWeb(58, 1, new Position(0, 200)),
            new NarrowWeb(59, 0, new Position(-130, 270)),
            new NarrowWeb(60, 0, new Position(-10, 320)),
            new NarrowWeb(61, 2, new Position(200, 200)),
    };

    private static final Rect WEB_CLEANSE_RECT = new Rect(-450, -450, 450, 450);

    public static void doWillTest1(Field field) {
        if (!FieldConstants.isWillP1Field(field.getId())) {
            return;
        }

        var mainWill = (Mob) field.getLifeByTemplateIds(BossConstants.REAL_WILL_P1, BossConstants.HARD_REAL_WILL_P1, BossConstants.EASY_REAL_WILL_P1);
        var blueWill = (Mob) field.getLifeByTemplateIds(BossConstants.BLUE_WILL_P1, BossConstants.HARD_BLUE_WILL_P1, BossConstants.EASY_BLUE_WILL_P1);
        var purpleWill = (Mob) field.getLifeByTemplateIds(BossConstants.PURPLE_WILL_P1, BossConstants.HARD_PURPLE_WILL_P1, BossConstants.EASY_PURPLE_WILL_P1);
        if (mainWill == null || blueWill == null || purpleWill == null) {
            return; // one of them already died or left the map
        }
        var wills = Util.makeSet(mainWill, blueWill, purpleWill);

        var newHp = Math.max(blueWill.getHp(), purpleWill.getHp());

        wills.forEach(w -> {
            w.setHp(newHp);
            field.broadcastPacket(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(w)));
        });

        if (mainWill.isOnTriggerThreshold()) {
            doTriggerBlockTestPrepare(field);
        } else if (mainWill.getHp() > 0){
            doNormalTest(field);
        } else {
            wills.forEach(w -> w.die(false));
        }
    }

    /** The dimension about to be destroyed (true: the lower, blue one), while the warning lasts. */
    private static final String DESTROY_PROP = "willDestroyBlue";

    /** Whether this character stands in the dimension Will is about to destroy. */
    public static boolean isInDoomedDimension(Field field, Char chr) {
        Object p = field.getProperty(DESTROY_PROP);
        return p instanceof Boolean b && chr.getPosition() != null && isInBlueDimension(chr.getPosition()) == b;
    }

    /** Over to the other dimension (the Moonlight portals between them). */
    public static void switchDimension(Char chr) {
        if (chr.getPosition() == null) {
            return;
        }
        boolean blue = isInBlueDimension(chr.getPosition());
        Position to = new Position(chr.getPosition().getX(), blue ? PURPLE_FH_Y : BLUE_FH_Y);
        chr.setPosition(to);
        chr.write(FieldPacket.teleport(to, chr));
    }

    /** Whether the Beholder's eyes are out (Will's moonlight test) and nobody has caught a safe light orb yet. */
    public static boolean canCatchLight(Field field) {
        boolean safe = field.hasProperty(BossConstants.WILL_FIELD_SAFE_KEY)
                && (Boolean) field.getProperty(BossConstants.WILL_FIELD_SAFE_KEY);
        return !safe && field.getLifeByTemplateId(BossConstants.BEHOLDERS_EYE_PURPLE) != null;
    }

    /** The Beholder's eye in the same dimension as this character. */
    public static Mob eyeInDimensionOf(Field field, Char chr) {
        boolean blue = chr.getPosition() != null && isInBlueDimension(chr.getPosition());
        var life = field.getLifeByTemplateId(blue ? BossConstants.BEHOLDERS_EYE_BLUE : BossConstants.BEHOLDERS_EYE_PURPLE);
        return life instanceof Mob m ? m : null;
    }

    /** A character touches a safe light orb (the client reports the collision; a bot does it here). */
    public static void catchLight(Field field) {
        var kinds = (Map<Integer, Boolean>) field.getProperty(ORB_KINDS_PROP);
        if (kinds != null) {
            kinds.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).findFirst()
                    .ifPresent(key -> onWillFieldAtomCollision(field, key));
        }
    }

    // GMS: every 2 minutes Will "unlocks his power" (WILL_P1_TIMER state 1). Away from an HP block a dimension is
    // destroyed 4 s later; at an HP block the pupil test follows instead (state 2): hit a pupil, light orbs fall in
    // both dimensions, catch a safe one and Will breaks 8 s after the test ends ("Now's your chance!").
    public static final int CYCLE_MS = 120_000;
    private static final String NEXT_CYCLE_PROP = "willNextCycle";
    private static final Rect GMS_BLUE_RECT = new Rect(-690, -442, 695, 160);
    private static final Rect GMS_PURPLE_RECT = new Rect(-690, -2621, 695, -2019);
    private static final int TEST_START_DELAY = 4350;
    private static final int PUPIL_SPAWN_DELAY = 900;
    private static final int TEST_LENGTH = 15800;
    private static final int BREAK_DELAY = 8100;
    private static final String TEST_END_PROP = "willPupilTestEnd";
    private static final int BARRIER_HALF_WIDTH = 204;
    public static final int HP_RESEND_MS = 1100;

    private static void removeBarriers(Field field) {
        int skill = MobSkillID.BossWillAttack.getVal();
        for (var aa : new java.util.ArrayList<>(field.getAffectedAreas())) {
            if (aa != null && aa.isMobOwnedAA() && aa.getSkillID() == skill) {
                field.removeLife(aa.getObjectId(), true);
            }
        }
    }

    /** GMS resends the HP blocks and the three Wills' HP about every second in phase 1. */
    public static void resendP1Hp(Field field) {
        int mode = Math.max(0, BossConstants.getWillMode(field.getId()));
        var order = new int[]{BossConstants.willP1(mode), BossConstants.willBlueP1(mode), BossConstants.willPurpleP1(mode)};
        var wills = new java.util.ArrayList<Mob>();
        for (int id : order) {
            var life = field.getLifeByTemplateId(id);
            if (life instanceof Mob m) {
                wills.add(m);
            }
        }
        if (wills.isEmpty()) {
            return;
        }
        field.broadcastPacket(WillFieldPacket.willTriggerBlockP1Hp(wills.get(0).getHpTriggerBlockPercs(), wills));
    }

    private static int msToNextCycle(Field field) {
        Object next = field.getProperty(NEXT_CYCLE_PROP);
        return next instanceof Long l ? (int) Math.max(0, l - System.currentTimeMillis()) : 0;
    }

    /** The phase 1 countdown as GMS sends it on entry (state 0) and at each step. */
    public static void sendCycleTimer(Field field, int state) {
        field.broadcastPacket(WillFieldPacket.p1Timer(state, state == 1 ? CYCLE_MS : state == 3 ? 0 : msToNextCycle(field)));
    }

    public static void setNextCycle(Field field, long delayMs) {
        field.setProperty(NEXT_CYCLE_PROP, System.currentTimeMillis() + delayMs);
    }

    private static java.util.List<Mob> mobsOf(Field field, int... templateIds) {
        var ids = Util.makeSet(templateIds);
        var list = new java.util.ArrayList<Mob>();
        for (var life : new java.util.ArrayList<>(field.getLifes().values())) {
            if (life instanceof Mob m && ids.contains(m.getTemplateId())) {
                list.add(m);
            }
        }
        return list;
    }

    private static java.util.List<Mob> p1Wills(Field field) {
        return mobsOf(field, BossConstants.REAL_WILL_P1, BossConstants.HARD_REAL_WILL_P1, BossConstants.EASY_REAL_WILL_P1,
                BossConstants.BLUE_WILL_P1, BossConstants.HARD_BLUE_WILL_P1, BossConstants.EASY_BLUE_WILL_P1,
                BossConstants.PURPLE_WILL_P1, BossConstants.HARD_PURPLE_WILL_P1, BossConstants.EASY_PURPLE_WILL_P1);
    }

    private static java.util.List<Mob> pupils(Field field) {
        return mobsOf(field, BossConstants.BEHOLDERS_EYE_BLUE, BossConstants.BEHOLDERS_EYE_PURPLE);
    }

    private static void unlockPower(Field field, boolean blue) {
        setNextCycle(field, CYCLE_MS);
        int mode = Math.max(0, BossConstants.getWillMode(field.getId()));
        // the flagged rect is the dimension the Beholder's eye watches (the safe one)
        var safe = new DestroyDimensionInfo(true, blue ? GMS_PURPLE_RECT : GMS_BLUE_RECT);
        var doomed = new DestroyDimensionInfo(false, blue ? GMS_BLUE_RECT : GMS_PURPLE_RECT);
        field.broadcastPacket(WillFieldPacket.useSkill(WillSkillPacket.destroyDimension(BossConstants.willP1(mode),
                Util.makeList(doomed, safe))));
        broadcastWeatherText(field, "Will is trying to unlock his power. Find your way to safety.");
        sendCycleTimer(field, 1);
    }

    private static void doNormalTest(Field field) {
        boolean blue = Util.getRandomBool();
        field.setProperty(DESTROY_PROP, blue);
        unlockPower(field, blue);
        EventManager.addEvent(() -> destroyDimension(field, blue), 4, TimeUnit.SECONDS);
    }

    private static void destroyDimension(Field field, boolean blue) {
        field.removeProperty(DESTROY_PROP);
        field.getChars().forEach(chr -> {
            if (isInBlueDimension(chr.getPosition()) == blue) {
                chr.damagePerc(100);
                chr.write(UserPacket.effect(Effect.mobSkillSpecial(true, MobSkillID.BossWillAttack, 5)));
            }
        });
        sendCycleTimer(field, 0);
    }

    private static void doTriggerBlockTestPrepare(Field field) {
        for (var will : p1Wills(field)) {
            will.setBlockEverything(true);
        }
        unlockPower(field, Util.getRandomBool());
        field.addEvent(EventManager.addEvent(() -> startPupilTest(field), TEST_START_DELAY));
    }

    private static void startPupilTest(Field field) {
        var wills = p1Wills(field);
        if (wills.isEmpty()) {
            return;
        }
        sendCycleTimer(field, 2);
        for (var will : wills) {
            field.broadcastPacket(MobPool.mobAttackBlock(will, Util.makeList(0, 1, 2, 3, 4, 5)));
        }
        broadcastWeatherText(field, "Attack the Pupil to redirect the moonlight. Use this to create the moonlight barrier.");
        field.setProperty(BossConstants.WILL_FIELD_FAIL_KEY, false);
        field.setProperty(BossConstants.WILL_FIELD_SAFE_KEY, false);
        field.addEvent(EventManager.addEvent(() -> {
            int mode = Math.max(0, BossConstants.getWillMode(field.getId()));
            field.broadcastPacket(WillFieldPacket.useSkill(WillSkillPacket.teleport(BossConstants.willP1(mode), 1, true)));
            field.spawnMob(BossConstants.BEHOLDERS_EYE_BLUE, 0, BLUE_FH_Y, false, Long.MAX_VALUE);
            field.spawnMob(BossConstants.BEHOLDERS_EYE_PURPLE, 0, PURPLE_FH_Y, false, Long.MAX_VALUE);
        }, PUPIL_SPAWN_DELAY));
        field.setProperty(TEST_END_PROP, System.currentTimeMillis() + TEST_LENGTH);
        field.addEvent(EventManager.addEvent(() -> doTriggerBlockTest(field), TEST_LENGTH));
    }

    private static void doTriggerBlockTest(Field field) {
        var success = field.hasProperty(BossConstants.WILL_FIELD_SAFE_KEY)
                && (Boolean) field.getProperty(BossConstants.WILL_FIELD_SAFE_KEY);
        field.setProperty(BossConstants.WILL_FIELD_SAFE_KEY, false);

        field.getChars().forEach(chr -> {
            chr.write(UserPacket.effect(Effect.mobSkillSpecial(true, MobSkillID.BossWillAttack, WillSkillType.P1Trigger.getVal())));
            if (!success) {
                chr.damagePerc(100);
            }
        });
        removeBarriers(field);
        pupils(field).forEach(m -> m.die(false));
        field.broadcastPacket(WillFieldPacket.useSkill(WillSkillPacket.teleport(0, 0, false)));
        field.removeProperty(ORB_KINDS_PROP);
        field.broadcastPacket(WillFieldPacket.clearObstacles());
        sendCycleTimer(field, 0);

        if (!success) {
            // the HP block stays: the next cycle tries again
            for (var w : p1Wills(field)) {
                w.setBlockEverything(false);
                field.broadcastPacket(MobPool.mobAttackBlock(w, Collections.emptyList()));
            }
            return;
        }
        field.addEvent(EventManager.addEvent(() -> willBreaks(field), BREAK_DELAY));
    }

    /** GMS: the moonlight flash, the Wills' break action, and the HP bar opens to the next block. */
    private static void willBreaks(Field field) {
        var wills = p1Wills(field);
        if (wills.isEmpty()) {
            return;
        }
        int mode = Math.max(0, BossConstants.getWillMode(field.getId()));
        field.broadcastPacket(WillFieldPacket.moonlightShowEffect(false));
        for (var w : wills) {
            w.setBlockEverything(false);
            field.broadcastPacket(MobPool.mobAttackBlock(w, Collections.emptyList()));
            if (w.getTemplateId() != BossConstants.willP1(mode)) {
                field.broadcastPacket(MobPool.mobAttackAction(w.getObjectId(), 2, false));
            }
        }
        broadcastWeatherText(field, "Now's your chance! Attack Will while he's open!");
        wills.forEach(w -> w.getHpTriggerBlockPercs().poll());
        var mainWill = wills.stream().filter(w -> w.getTemplateId() == BossConstants.willP1(mode)).findAny().orElse(wills.get(0));
        resendP1Hp(field);
        boolean over = mainWill.getHpTriggerBlockPercs().isEmpty();
        if (over) {
            sendCycleTimer(field, 3);
        }
        wills.forEach(w -> {
            field.broadcastPacket(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(w)));
            w.setForcedSkill(w.getSkillByMobSkillInfo(MobSkillID.BossWillAttack.getVal(), WillSkillType.Groggy.getVal()));
            if (over) {
                w.die(false);
            }
        });
    }

    /** A player touched the light orb with this key (the client reports the key it was sent). */
    public static void onWillFieldAtomCollision(Field field, int key) {
        var kinds = (Map<Integer, Boolean>) field.getProperty(ORB_KINDS_PROP);
        if (kinds == null || !Boolean.TRUE.equals(kinds.get(key)) || !canCatchLight(field)) {
            return;
        }
        field.setProperty(BossConstants.WILL_FIELD_SAFE_KEY, true);
        // GMS: the orbs clear and the pupils raise the moonlight barrier
        field.removeProperty(ORB_KINDS_PROP);
        field.broadcastPacket(WillFieldPacket.clearObstacles());
        Object end = field.getProperty(TEST_END_PROP);
        int left = end instanceof Long l ? (int) Math.max(1000, l - System.currentTimeMillis()) : 6000;
        for (var mob : pupils(field)) {
            if (mob.getPosition() == null) {
                continue;
            }
            var aa = new net.swordie.ms.life.AffectedArea(0);
            aa.setAaType((byte) 1);
            aa.setSkillID(MobSkillID.BossWillAttack.getVal());
            aa.setSlv((short) WillSkillType.P1Trigger.getVal());
            aa.setMobOwnerOID(mob.getObjectId());
            aa.setMobOwnerTID(mob.getTemplateId());
            int x = mob.getPosition().getX();
            int fhY = isInBlueDimension(mob.getPosition()) ? BLUE_FH_Y : PURPLE_FH_Y;
            aa.setRect(new Rect(x - BARRIER_HALF_WIDTH, fhY - 280, x + BARRIER_HALF_WIDTH, fhY + 20));
            aa.setPosition(new Position(x, fhY - 1));
            aa.setDuration(left);
            field.spawnAffectedArea(aa);
        }
        for (var mob : pupils(field)) {
            var option = new Option(1, MobSkillID.BossWillAttack.getVal(), 0);
            option.slv = WillSkillType.P1Trigger.getVal();
            mob.getTemporaryStat().addMobSkillOptionsAndBroadCast(MobStat.Invincible, option);
        }
    }

    public static boolean isInBlueDimension(Position pos) {
        return pos.getY() > -1000;
    }

    public static boolean isPupil(Mob mob) {
        return mob.getTemplateId() == BossConstants.BEHOLDERS_EYE_BLUE || mob.getTemplateId() == BossConstants.BEHOLDERS_EYE_PURPLE;
    }

    /** A hit on a pupil: as GMS, a batch of light orbs falls in both dimensions (some safe, most harmful). */
    public static void handleBeholderEyeHit(Field field, Mob mob) {
        if (!isPupil(mob) || !canCatchLight(field)) {
            return;
        }
        var key = BossConstants.WILL_FIELD_P1_HIT_OA_KEY;
        if (!field.hasProperty(key) || ((Long) field.getProperty(key) + HIT_OA_COOLTIME) < System.currentTimeMillis()) {
            var kinds = (Map<Integer, Boolean>) field.getProperty(ORB_KINDS_PROP);
            if (kinds == null) {
                kinds = new ConcurrentHashMap<>();
                field.setProperty(ORB_KINDS_PROP, kinds);
            }
            for (int fhY : new int[]{PURPLE_FH_Y, BLUE_FH_Y}) {
                var atoms = new HashSet<ObstacleAtomInfo>();
                int n = Util.getRandom(3, 7);
                for (int i = 0; i < n; i++) {
                    boolean safe = Util.getRandom(99) < 35;
                    var atomType = safe ? ObstacleAtomEnum.WillTriggerTestSafe : ObstacleAtomEnum.WillTriggerTestDangerous;
                    int x = Util.getRandom(-616, 621);
                    int orbKey = NEXT_ORB_KEY.getAndIncrement();
                    kinds.put(orbKey, safe);
                    var oai = new ObstacleAtomInfo(atomType.getType(), orbKey, new Position(x, fhY - LIGHT_FALL),
                            new Position(x, fhY), 40, safe ? 0 : 60, 0, Util.getRandom(200, 1455), 6400,
                            Util.getRandom(70, 148), 3, LIGHT_FALL, 0);
                    oai.setFlag(safe);
                    oai.setSendKey(true);
                    oai.setNoHitRect(true);
                    atoms.add(oai);
                }
                field.broadcastPacket(FieldPacket.createObstacle(ObstacleAtomCreateType.NORMAL, null, null, atoms));
            }
            field.setProperty(key, System.currentTimeMillis());
        }
    }

    public static void spawnEye(Mob mob) {
        var field = mob.getField();
        var blue = isInBlueDimension(mob.getPosition());

        var x1 = -250;
        var y = -370;
        var x2 = 250;

        var attackBoundaries = new Rect(-690, -601, 695,160);
        if (!blue) {
            attackBoundaries.setTop(-2621);
            attackBoundaries.setBottom(-2019);
        }

        field.broadcastPacket(WillFieldPacket.willSummonSkill(0, mob.getTemplateId(), new Position(x1, y)));
        field.broadcastPacket(WillFieldPacket.willSummonSkill(0, mob.getTemplateId(), new Position(x2, y)));
        field.broadcastPacket(WillFieldPacket.willSummonSkill(1, mob.getTemplateId(), null,
                300, 100, 1800, 5, attackBoundaries));
    }

    // Phase 2 ---------------------------------------------------------------------------------------------------------

    public static boolean canUseConsumable(Char chr) {
        return System.currentTimeMillis() < chr.getBossInfo().getWillPotionUseTime();
    }

    public static void doWillTest2(Field field) {
        var willLife = field.getLifeByTemplateIds(BossConstants.WILL_P2, BossConstants.HARD_WILL_P2, BossConstants.EASY_WILL_P2);
        if (willLife instanceof Mob) {
            var will = (Mob) willLife;
            if (will.isOnTriggerThreshold()) {
                prepareWillTest2(field, will);
            }
        }
    }

    private static void prepareWillTest2(Field field, Mob will) {
        broadcastWeatherText(field, "The Mirror of Lies reverses attacks. When the crack appears, face the attack.");

        will.setBlockEverything(true);
        var dummy = field.getLifeByTemplateIds(BossConstants.HARD_WILL_P2_DUMMY_1, BossConstants.WILL_P2_DUMMY_1, BossConstants.EASY_WILL_P2_DUMMY_1);
        if (dummy instanceof Mob) {
            ((Mob) dummy).setBlockEverything(true);
        }
        field.setProperty(BossConstants.WILL_FIELD_FAIL_KEY, false);

        // 5 sets, 3x pillars (1s between strikes in a set, 3s between sets)
        prepareWillTest2StrikeSets(field, will);
    }

    private static void prepareWillTest2StrikeSets(Field field, Mob will) {
        for (int i = 0; i < WILL_P2_TEST_SETS; i++) {
            field.addEvent(EventManager.addEvent(() -> {
                setRandomReflection(field);
                createReflectedDownwardStrike(field, will.getTemplateId());
            }, WILL_P2_TEST_START_DELAY + i * WILL_P2_TEST_SET_PERIOD));
        }
        // the result once the last legs have landed
        int resultDelay = WILL_P2_TEST_START_DELAY + (WILL_P2_TEST_SETS - 1) * WILL_P2_TEST_SET_PERIOD
                + LEG_LAST_ROW_DELAY + LEG_HIT_DELAY + RESULT_MARGIN;
        field.addEvent(EventManager.addEvent(() -> finishWillTest2(field, will), resultDelay));
    }

    private static void setRandomReflection(Field field) {
        var reflection = Util.getRandomBool();
        field.setProperty(BossConstants.WILL_FIELD_P2_REFLECTED, reflection);
    }

    private static void finishWillTest2(Field field, Mob will) {
        field.removeProperty(BossConstants.WILL_FIELD_P2_REFLECTED);
        var failed = (boolean)field.getProperty(BossConstants.WILL_FIELD_FAIL_KEY);
        var dummy = field.getLifeByTemplateIds(BossConstants.WILL_P2_DUMMY_1, BossConstants.HARD_WILL_P2_DUMMY_1, BossConstants.EASY_WILL_P2_DUMMY_1);
        if (failed) {
            broadcastWeatherText(field, "You have failed the Mirror of Lies test! Will should be vulnerable again in some time...");
        } else {
            broadcastWeatherText(field, "Now's your chance! Attack Will while he's open!");
            will.getHpTriggerBlockPercs().remove();
        }
        will.setBlockEverything(false);
        field.broadcastPacket(WillFieldPacket.willTriggerBlockP2(will.getHpTriggerBlockPercs()));
        if (dummy instanceof Mob) {
            ((Mob) dummy).setBlockEverything(false);
        }
        if (!failed) {
            will.setForcedSkill(will.getSkillByMobSkillInfo(MobSkillID.BossWillAttack.getVal(), WillSkillType.Groggy.getVal()));
        }
        field.removeProperty(BossConstants.WILL_FIELD_FAIL_KEY);
    }

    /** The ids of a packet's legs run on through the fight. */
    private static int takeLegIds(Field field, int count) {
        int first = field.hasProperty(LEG_ID_PROP) ? (int) field.getProperty(LEG_ID_PROP) : 1;
        field.setProperty(LEG_ID_PROP, first + count);
        return first;
    }

    /**
     * Phase 1: the client places a strike set on the dimension of the Will named in the packet (GMS names the blue and
     * the purple Will, one set each), so a dummy's set goes out under the Will of its own dimension.
     */
    private static int strikeTemplateId(Mob caster, Field field) {
        if (BossConstants.getWillPhase(field.getId()) != 1 || caster.getPosition() == null) {
            return caster.getTemplateId();
        }
        int mode = Math.max(0, BossConstants.getWillMode(field.getId()));
        return isInBlueDimension(caster.getPosition()) ? BossConstants.willBlueP1(mode) : BossConstants.willPurpleP1(mode);
    }

    public static void createDownwardStrike(Mob mob, Field field, WillSkillType willAttack) {
        var pattern = WillLegPattern.random(WillLegPattern.STRIKE, BossConstants.getWillPhase(field.getId()));
        if (pattern == null) {
            return;
        }
        field.broadcastPacket(WillFieldPacket.useSkill(
                WillSkillPacket.downwardStrikes(
                        strikeTemplateId(mob, field),
                        willAttack,
                        pattern.getHitType(),
                        LEG_HIT_DELAY,
                        LEG_NEXT_DELAY,
                        new Rect(-LEG_HALF_WIDTH, LEG_TOP, LEG_HALF_WIDTH, LEG_BOTTOM),
                        pattern.strikes(takeLegIds(field, pattern.legCount()))
                )
        ));
    }

    public static void createReflectedDownwardStrike(Field field, int templateId) {
        boolean reflected = field.hasProperty(BossConstants.WILL_FIELD_P2_REFLECTED) && (boolean)field.getProperty(BossConstants.WILL_FIELD_P2_REFLECTED);
        var pattern = WillLegPattern.random(WillLegPattern.REFLECTED, BossConstants.getWillPhase(field.getId()));
        if (pattern == null) {
            return;
        }
        int half = reflected ? LEG_HALF_WIDTH : PLAIN_LEG_HALF_WIDTH;
        field.broadcastPacket(WillFieldPacket.useSkill(
                WillSkillPacket.reflectedDownwardStrikes(
                        templateId,
                        reflected,
                        pattern.getHitType(),
                        LEG_HIT_DELAY,
                        REFLECTED_LEG_NEXT_DELAY,
                        new Rect(-half, LEG_TOP, half, LEG_BOTTOM),
                        pattern.strikes(takeLegIds(field, pattern.legCount()))
                )
        ));
    }

    // Phase 3 ---------------------------------------------------------------------------------------------------------

    public static void spawnNarrowWeb(Field field) {
        if (field.hasProperty(WEB_FULL_PROP) && (boolean) field.getProperty(WEB_FULL_PROP)) {
            return;
        }

        long webMask = 0;
        if (field.hasProperty(WEB_MASK_PROP)) {
            webMask = (long) field.getProperty(WEB_MASK_PROP);
        }
        for (int i = 0; i < NARROW_WEBS.length; i++) {
            if ((webMask & (1L << i)) == 0) {
                webMask |= 1L << i;
                var web = NARROW_WEBS[i].deepCopy();
                field.spawnLife(web, null);
                field.setProperty(WEB_MASK_PROP, webMask);
                break;
            }
        }

        if (webMask == 0x3fffffffffffffffL) {
            field.setProperty(WEB_FULL_PROP, true);
            field.broadcastPacket(WvsContext.weatherEffectNotice(
                    WeatherEffNoticeType.BossWill,
                    "Will's spider webs have taken over, the moonlight will no longer be able to cleanse them!",
                    7500
            ));
        }
    }

    public synchronized static void removeClosestWebs(Field field, Char chr) {
        if (field.hasProperty(WEB_FULL_PROP) && (boolean) field.getProperty(WEB_FULL_PROP)) {
            chr.chatMessage("Will's webs have taken over! Moonlight will no longer be able to cleanse them.");
            return;
        }

        var closeLifes = field.getLifes().values();

        for (int i = 0; i < 2; i++) {
            NarrowWeb toRemove = null;
            var chrPos = chr.getPosition();
            for (var closeLife : closeLifes) {
                if (closeLife instanceof NarrowWeb
                        && (toRemove == null || closeLife.getPosition().distanceTo(chrPos) < toRemove.getPosition().distanceTo(chrPos))) {
                    toRemove = (NarrowWeb) closeLife;
                }
            }

            if (toRemove == null) {
                return;
            }

            field.removeLife(toRemove);
            closeLifes.remove(toRemove);

            // remove this id from the web mask
            var oldMask = (long) field.getProperty(WEB_MASK_PROP);
            long newMask = oldMask ^ (1L << toRemove.getId());
            field.setProperty(WEB_MASK_PROP, newMask);
        }
    }

    public static void broadcastWeatherText(Field field, String weatherText) {
        field.broadcastPacket(WvsContext.weatherEffectNotice(
                WeatherEffNoticeType.BossWill,
                weatherText,
                7500
        ));
    }

    public static void createInfection(Field field) {
        var eligibleChars = new HashSet<Char>();
        for (var chr : field.getChars()) {
            if (chr.getBossInfo().getWillInfection() == null && !chr.isDead()) {
                eligibleChars.add(chr);
            }
        }

        if (eligibleChars.size() == 0) {
            return;
        }

        var randomChr = Util.getRandomFromCollection(eligibleChars);
        createWillInfection(randomChr);
    }

    private static void createWillInfection(Char chr) {
        var field = chr.getField();
        var tsm = chr.getTemporaryStatManager();

        long curDamage = 0L;
        var will = field.getLifeByTemplateIds(BossConstants.WILL_P3, BossConstants.HARD_WILL_P3, BossConstants.EASY_WILL_P3);
        if (will instanceof Mob) {
            curDamage = ((Mob) will).getDamageDone(chr);
        }

        var infection = new WillInfection(true, chr.getId(), false, chr.getPosition().deepCopy(), curDamage);
        chr.getBossInfo().setWillInfection(infection);
        field.spawnLife(infection, null);

        var option = new Option(100, MobSkillID.BossWillAttack.getVal(), WillSkillType.Infection.getVal(), BossConstants.WILL_INFECTION_TIME);
        tsm.putCharacterStatValue(CharacterTemporaryStat.IndiePMdR, option);

        EventManager.addEvent(() -> checkWillInfectionDamage(chr), BossConstants.WILL_INFECTION_DAMAGE_CHECK_TIME, TimeUnit.SECONDS);
        EventManager.addFixedRateEvent(() -> field.broadcastPacket(
                WillFieldPacket.infectionAttack(infection)),
                BossConstants.WILL_INFECTION_INITIAL_DELAY,
                BossConstants.WILL_INFECTION_DELAY,
                13
        );
        EventManager.addEvent(() -> field.removeLife(infection), BossConstants.WILL_INFECTION_TIME, TimeUnit.SECONDS);
    }

    private static void checkWillInfectionDamage(Char chr) {
        var field = chr.getField();
        var infection = chr.getBossInfo().getWillInfection();
        var curDamage = infection.getDamageDone();

        var newDamage = 0L;
        var will = field.getLifeByTemplateIds(BossConstants.WILL_P3, BossConstants.HARD_WILL_P3, BossConstants.EASY_WILL_P3);
        if (will instanceof Mob) {
            newDamage = ((Mob) will).getDamageDone(chr);
        }

        if (newDamage == curDamage) {
            chr.die();
            infection.setPosition(chr.getPosition().deepCopy());
            field.broadcastPacket(WillFieldPacket.infectionTransform(infection));
            createInfection(field);
        }
    }

}
