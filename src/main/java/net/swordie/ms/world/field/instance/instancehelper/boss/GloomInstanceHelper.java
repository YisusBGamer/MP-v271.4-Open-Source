package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.enums.ObstacleAtomCreateType;
import net.swordie.ms.enums.ObstacleAtomEnum;
import net.swordie.ms.life.mob.skill.MobSkill;
import net.swordie.ms.life.mob.skill.MobSkillID;
import net.swordie.ms.life.mob.skill.MobSkillModule;
import net.swordie.ms.world.field.obstacleatom.ObstacleAtomInfo;
import net.swordie.ms.connection.packet.field.GloomFieldPacket;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.fieldskill.FieldSkillInfo;
import net.swordie.ms.world.field.fieldskill.GloomTentacle;
import net.swordie.ms.world.field.fieldskill.GloomTerrorFlame;
import net.swordie.ms.util.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Giant Monster Gloom (maplestorywiki.net, GMS):
 * <ul>
 *     <li>Terror Gauge: each player's gauge rises over time and faster when hit. When it's full that player falls into
 *     Terror (the client blacks out the screen) and illusions appear: a Monstrous Illusion (10b / Chaos 30b HP) and a
 *     Terrible Grasp (30b; invincible in Chaos). In Terror the gauge drains, faster for every illusion killed; when it's
 *     empty the illusions vanish. It doesn't drain while the player is dead.</li>
 *     <li>A player who empties their gauge alive glows for a while and lowers the gauges of party members nearby; one
 *     who dies in Terror leaves a grey aura that raises them.</li>
 *     <li>Tentacles and Terror Flame are field skills on GMS's timing (see the constants below).</li>
 * </ul>
 */
public class GloomInstanceHelper extends BossMechanicsHelper {

    private static final int MAX_GAUGE = 1000;

    private final Map<Integer, Integer> gauges = new ConcurrentHashMap<>();
    private final Set<Integer> terrorised = ConcurrentHashMap.newKeySet();
    // illusion object id -> the character it haunts
    private final Map<Integer, Integer> illusions = new ConcurrentHashMap<>();
    // character id -> until when its aura lasts (positive: calming glow, negative id key: grey aura)
    private final Map<Integer, Long> calmAura = new ConcurrentHashMap<>();
    private final Map<Integer, Long> greyAura = new ConcurrentHashMap<>();
    private static final long AURA_MS = 10_000;
    private static final int AURA_RANGE = 300;
    private static final int AURA_STEP = 15;
    private static final int MONSTROUS_ILLUSION_MOB = 8644653;
    private static final int TERRIBLE_GRASP_MOB = 8644654;
    private static final int MONSTROUS_ILLUSION_CHAOS_MOB = 8644656;
    private static final int TERRIBLE_GRASP_CHAOS_MOB = 8644657;

    private boolean isChaos() {
        return getInstance() != null && getInstance().getEnterFieldId() == BossConstants.GLOOM_HARD_FIELD;
    }

    @Override
    protected Set<Integer> getBossIds() {
        return Set.of(BossConstants.GLOOM, BossConstants.GLOOM_HARD);
    }

    // GMS (Normal and Chaos): no mob skills; field skill 100020 throws a tentacle at a player every ~10.8 s
    // (Normal lv2, Chaos lv4); every 55.7 s (Chaos 37 s) the two big tentacles (lv1 / lv3, three turns of two
    // patterns) sweep and the mob zone goes off, back on 36 s later; low on HP, Terror Flame (100022) every 6.6 s
    // (Chaos 5 s)
    private static final long SMALL_MS = 10_800, ZONE_BACK_MS = 36_000, SMALL_AFTER_EYE_MS = 11_000;
    private static final long[] BIG_MS = {55_700, 37_000};
    private static final long[] FLAME_MS = {6_600, 5_000};
    // the big tentacles are forced when her HP drops to these percents (Chaos has one more)
    private static final int[] FORCED_BIG_PERCENT = {70, 40, 15};
    private static final int NORMAL_FORCED_BIG = 2;
    private int forcedBig;
    // the eye opens this long after the big tentacles start (GMS: Normal 26.6 s, Chaos about 25.5 s); then the
    // area force (186 lv11, the energy gathers) and the laser beam (252 lv1) go off together
    private static final long[] EYE_OPEN_MS = {26_600, 25_500};
    private static final int EYE_WARNING_SKILL = 186, EYE_WARNING_LEVEL = 11, BEAM_SKILL = 252, BEAM_LEVEL = 1;
    private static final int TENTACLE_NOTICE_TYPE = 250, TENTACLE_NOTICE_MS = 3000;
    private static final String TENTACLE_NOTICE =
            "Gloom is about to attack with its tentacles! If you can withstand it, the Eye of the Void will be exposed!";
    private static final int EYE_NOTICE_TYPE = 249, EYE_NOTICE_MS = 2000;
    private static final String EYE_NOTICE = "The energy in the area gathers around Gloom.";
    private long eyeOpenAt;
    // falling meteors (Out 838, create type 9): every ~2.1 s a few of them, a status on the one that hits
    private static final long METEOR_MS = 2_100;
    private static final int[] METEORS_MIN = {2, 3}, METEORS_MAX = {5, 6};
    private static final int METEOR_X_MIN = -616, METEOR_X_MAX = 621, METEOR_TOP_Y = -935, METEOR_FLOOR_Y = -157;
    private static final int METEOR_HITBOX = 36, METEOR_HP_PERCENT = 15, METEOR_LENGTH = 778;
    private static final int METEOR_DELAY_MIN = 100, METEOR_DELAY_MAX = 1400, METEOR_SPEED_MIN = 400, METEOR_SPEED_MAX = 700;
    private static final ObstacleAtomEnum[] METEOR_KINDS = {ObstacleAtomEnum.GloomBlind, ObstacleAtomEnum.GloomStun,
            ObstacleAtomEnum.GloomUndead};
    private static final int METEOR_STATUS_LEVEL = 1;
    private final AtomicInteger nextMeteorKey = new AtomicInteger(Util.getRandom(100_000, 400_000_000));
    // meteor key -> its kind's atom type, until it hits
    private final Map<Integer, Integer> meteors = new ConcurrentHashMap<>();
    private static final int FLAME_BELOW_PERCENT = 33; // a guess: GMS began at about a third
    // per big entry: duration, interval, angle, delay, z, object, x
    private static final String[][] BIG_A = {{"a1", "3", "-76", "790"}, {"a2", "4", "81", "-500"}};
    private static final String[][] BIG_B = {{"b1", "5", "75", "-610"}, {"b2", "6", "-81", "600"}};
    private long started, nextSmall, nextBig, zoneBackAt, nextFlame, lastBig;
    private int bigTurn;

    @Override
    protected void startMechanics() {
        every(1000, this::tickGauges);
        every(1000, this::fieldSkillTick);
        every(METEOR_MS, this::dropMeteors);
        long now = System.currentTimeMillis();
        started = now;
        nextSmall = now + 10_500;
        nextBig = now + BIG_MS[isChaos() ? 1 : 0];
        zoneBackAt = now + 1500;
    }

    private void fieldSkillTick() {
        Mob boss = getBoss();
        Field field = getField();
        if (boss == null || field == null) {
            return;
        }
        long now = System.currentTimeMillis();
        int c = isChaos() ? 1 : 0;
        if (zoneBackAt > 0 && now >= zoneBackAt) {
            zoneBackAt = 0;
            boss.changeZoneId(1);
        }
        int forcedCount = c == 1 ? FORCED_BIG_PERCENT.length : NORMAL_FORCED_BIG;
        if (forcedBig < forcedCount && boss.getHp() * 100 <= (long) FORCED_BIG_PERCENT[forcedBig] * boss.getMaxHp()) {
            forcedBig++;
            nextBig = now;
        }
        if (eyeOpenAt > 0 && now >= eyeOpenAt) {
            eyeOpenAt = 0;
            openEye(boss, field);
        }
        if (now >= nextBig) {
            nextBig = now + BIG_MS[c];
            lastBig = now;
            field.broadcastPacket(FieldPacket.fieldSkill(bigTentacles(c == 1 ? 3 : 1, bigTurn++ % 3)));
            field.broadcastPacket(WvsContext.weatherEffectNotice(TENTACLE_NOTICE_TYPE, TENTACLE_NOTICE, TENTACLE_NOTICE_MS));
            boss.changeZoneId(0);
            zoneBackAt = now + ZONE_BACK_MS;
            eyeOpenAt = now + EYE_OPEN_MS[c];
        } else if (now >= nextSmall && eyeOpenAt == 0 && now - lastBig >= EYE_OPEN_MS[c]) {
            nextSmall = now + SMALL_MS;
            List<Char> alive = getLivingChars();
            int x = alive.isEmpty() || alive.get(0).getPosition() == null ? 0
                    : alive.get(Util.getRandom(alive.size() - 1)).getPosition().getX();
            field.broadcastPacket(FieldPacket.fieldSkill(smallTentacle(c == 1 ? 4 : 2, x)));
        }
        if (boss.getHp() * 100 < (long) FLAME_BELOW_PERCENT * boss.getMaxHp() && now >= nextFlame) {
            nextFlame = now + FLAME_MS[c];
            field.broadcastPacket(FieldPacket.fieldSkill(new GloomTerrorFlame(c == 1 ? 2 : 1,
                    c == 1 ? BossConstants.GLOOM_TENTACLE + 1 : BossConstants.GLOOM_TENTACLE)));
        }
    }

    /** The tentacles are gone: the warning text, then the area force and the laser beam together. */
    private void openEye(Mob boss, Field field) {
        field.broadcastPacket(WvsContext.weatherEffectNotice(EYE_NOTICE_TYPE, EYE_NOTICE, EYE_NOTICE_MS));
        castBossSkill(boss, EYE_WARNING_SKILL, EYE_WARNING_LEVEL);
        castBossSkill(boss, BEAM_SKILL, BEAM_LEVEL);
        // the small tentacles keep coming through the open eye, the first one this long after it (GMS)
        nextSmall = System.currentTimeMillis() + SMALL_AFTER_EYE_MS;
    }

    private static void castBossSkill(Mob boss, int skillId, int level) {
        MobSkill skill = boss.getSkillByMobSkillInfo(skillId, level);
        if (skill == null) {
            skill = new MobSkill();
            skill.setSkillID(skillId);
            skill.setLevel(level);
        }
        skill.applyEffect(boss, false);
    }

    private void dropMeteors() {
        Field field = getField();
        if (field == null || isBossDead()) {
            return;
        }
        int c = isChaos() ? 1 : 0;
        int count = Util.getRandom(METEORS_MIN[c], METEORS_MAX[c]);
        for (Char chr : field.getChars()) {
            if (terrorised.contains(chr.getId()) || chr.getHP() <= 0) {
                continue; // no meteors while in Terror
            }
            chr.write(FieldPacket.createObstacle(ObstacleAtomCreateType.GLOOM_METEOR, null, null, newMeteors(count)));
        }
        if (meteors.size() > 500) {
            meteors.clear();
        }
    }

    private java.util.Set<ObstacleAtomInfo> newMeteors(int count) {
        var atoms = new HashSet<ObstacleAtomInfo>();
        for (int i = 0; i < count; i++) {
            ObstacleAtomEnum kind = METEOR_KINDS[Util.getRandom(METEOR_KINDS.length - 1)];
            int key = nextMeteorKey.incrementAndGet();
            int x = Util.getRandom(METEOR_X_MIN, METEOR_X_MAX);
            var atom = new ObstacleAtomInfo(kind.getType(), key, new Position(x, METEOR_TOP_Y),
                    new Position(x, METEOR_FLOOR_Y), METEOR_HITBOX, METEOR_HP_PERCENT, 0,
                    Util.getRandom(METEOR_DELAY_MIN, METEOR_DELAY_MAX), 0,
                    Util.getRandom(METEOR_SPEED_MIN, METEOR_SPEED_MAX), 1, METEOR_LENGTH, 0);
            atom.setSendKey(true);
            atom.setNoHitRect(true);
            meteors.put(key, kind.getType());
            atoms.add(atom);
        }
        return atoms;
    }

    /** A meteor reported hitting a player: Blind, Stun or Zombify (the damage is the client's own true damage). */
    public void onMeteorHit(Char chr, int key) {
        Integer type = meteors.remove(key);
        if (type == null || chr.getHP() <= 0) {
            return;
        }
        MobSkillID status = type == ObstacleAtomEnum.GloomBlind.getType() ? MobSkillID.Darkness
                : type == ObstacleAtomEnum.GloomStun.getType() ? MobSkillID.Stun : MobSkillID.Undead;
        MobSkill skill = new MobSkill();
        skill.setSkillID(status.getVal());
        skill.setLevel(METEOR_STATUS_LEVEL);
        MobSkillModule.applyEffect(chr, skill, getBoss());
    }

    private static GloomTentacle smallTentacle(int slv, int x) {
        GloomTentacle t = new GloomTentacle(slv);
        FieldSkillInfo info = new FieldSkillInfo();
        info.setDuration(1500);
        info.setInterval(1);
        info.setAngleMin(35);
        info.setAngleMax(75);
        info.setAttackDelay(1020);
        info.setZ(6);
        info.setObjName("");
        info.setObjName2("");
        info.setRect(new Rect(-120, -2185, 120, 300));
        info.setPosition(new Position(x, -157));
        info.setFlip(Util.randBoolean());
        t.setFieldSkillInfoList(List.of(info));
        return t;
    }

    private static GloomTentacle bigTentacles(int slv, int turn) {
        GloomTentacle t = new GloomTentacle(slv);
        t.setIndex(turn);
        t.setTotalDuration(23160);
        String[][] pattern = turn == 1 ? BIG_B : BIG_A;
        List<FieldSkillInfo> infos = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            boolean longFirst = (i == 0) != (turn == 2);
            FieldSkillInfo info = new FieldSkillInfo();
            info.setDuration(longFirst ? 21900 : 20400);
            info.setInterval(longFirst ? 660 : 2760);
            int angle = Integer.parseInt(pattern[i][2]);
            info.setAngleMin(angle);
            info.setAngleMax(angle);
            info.setAttackDelay(1980);
            info.setZ(Integer.parseInt(pattern[i][1]));
            info.setSet(turn);
            info.setObjName(pattern[i][0]);
            info.setObjName2("");
            info.setRect(new Rect(-100, -2185, 100, 300));
            info.setPosition(new Position(Integer.parseInt(pattern[i][3]), -159));
            info.setFlip(false);
            infos.add(info);
        }
        t.setFieldSkillInfoList(infos);
        return t;
    }

    private void tickGauges() {
        boolean chaos = isChaos();
        for (Char chr : getField().getChars()) {
            int id = chr.getId();
            int gauge = gauges.getOrDefault(id, 0);
            boolean inTerror = terrorised.contains(id);
            if (chr.getHP() <= 0) {
                continue; // the gauge doesn't move while dead
            }
            if (inTerror) {
                gauge -= chaos ? 6 : 8;
                if (gauge <= 0) {
                    gauge = 0;
                    endTerror(chr);
                }
            } else {
                gauge += chaos ? 5 : 4; // full after about 3.5 (Chaos) / 4 (Normal) minutes without being hit
                if (gauge >= MAX_GAUGE) {
                    gauge = MAX_GAUGE;
                    startTerror(chr);
                }
            }
            if (!terrorised.contains(id)) {
                gauge = Math.max(0, Math.min(MAX_GAUGE - 1, gauge + auraEffect(chr)));
            }
            gauges.put(id, gauge);
            chr.write(GloomFieldPacket.gloomGaugeUpdate(terrorised.contains(id), gauge, MAX_GAUGE));
        }
    }

    private void startTerror(Char chr) {
        if (!terrorised.add(chr.getId())) {
            return;
        }
        boolean chaos = isChaos();
        Position pos = chr.getPosition();
        Mob illusion = spawnAdd(chaos ? MONSTROUS_ILLUSION_CHAOS_MOB : MONSTROUS_ILLUSION_MOB, new Position(pos.getX() - 150, pos.getY() - 60),
                chaos ? 30_000_000_000L : 10_000_000_000L);
        // Chaos: the Terrible Grasp can't be killed (it leaves when the Terror ends)
        Mob grasp = spawnAdd(chaos ? TERRIBLE_GRASP_CHAOS_MOB : TERRIBLE_GRASP_MOB, new Position(pos.getX() + 150, pos.getY()),
                chaos ? Long.MAX_VALUE / 4 : 30_000_000_000L);
        if (illusion != null) {
            illusions.put(illusion.getObjectId(), chr.getId());
        }
        if (grasp != null) {
            illusions.put(grasp.getObjectId(), chr.getId());
        }
        chr.chatMessage("Terror takes hold of you! Defeat the illusions to clear your mind.");
    }

    /** How much the auras of nearby party members move this player's gauge this second. */
    private int auraEffect(Char chr) {
        long now = System.currentTimeMillis();
        int delta = 0;
        for (Char other : getField().getChars()) {
            if (other == chr || Math.abs(other.getPosition().getX() - chr.getPosition().getX()) > AURA_RANGE
                    || Math.abs(other.getPosition().getY() - chr.getPosition().getY()) > AURA_RANGE) {
                continue;
            }
            if (calmAura.getOrDefault(other.getId(), 0L) > now) {
                delta -= AURA_STEP;
            }
            if (greyAura.getOrDefault(other.getId(), 0L) > now) {
                delta += AURA_STEP;
            }
        }
        return delta;
    }

    @Override
    public void onChrDeath(Char chr) {
        super.onChrDeath(chr);
        if (terrorised.contains(chr.getId())) {
            greyAura.put(chr.getId(), System.currentTimeMillis() + AURA_MS);
        }
    }

    private void endTerror(Char chr) {
        if (terrorised.contains(chr.getId()) && chr.getHP() > 0) {
            calmAura.put(chr.getId(), System.currentTimeMillis() + AURA_MS);
            chr.chatMessage("You shake off the Terror. Your calm soothes those around you.");
        }
        terrorised.remove(chr.getId());
        for (Mob mob : new java.util.ArrayList<>(getField().getMobs())) {
            Integer owner = illusions.get(mob.getObjectId());
            if (owner != null && owner == chr.getId()) {
                illusions.remove(mob.getObjectId());
                despawn(mob);
            }
        }
    }

    @Override
    public void onChrHit(Char chr, int dmgAmount) {
        super.onChrHit(chr, dmgAmount);
        if (dmgAmount > 0 && !terrorised.contains(chr.getId())) {
            gauges.merge(chr.getId(), isChaos() ? 30 : 20, (a, b) -> Math.min(MAX_GAUGE, a + b));
        }
    }

    @Override
    protected void onAddDeath(Mob mob) {
        Integer owner = illusions.remove(mob.getObjectId());
        if (owner != null) {
            gauges.merge(owner, -250, (a, b) -> Math.max(1, a + b)); // the next tick ends the Terror at 0
        }
    }

    @Override
    protected void onBossDeath(Mob boss) {
        for (Char chr : getField().getChars()) {
            chr.write(GloomFieldPacket.gloomGaugeUpdate(false, 0, MAX_GAUGE));
        }
        terrorised.clear();
        gauges.clear();
    }
}
