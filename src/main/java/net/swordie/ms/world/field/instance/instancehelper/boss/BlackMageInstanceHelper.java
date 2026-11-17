package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.connection.packet.Effect;
import net.swordie.ms.connection.packet.MobPool;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.field.BlackMageFieldPacket;
import net.swordie.ms.connection.packet.field.BlackMageFieldPacket.BlackMageTimerType;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.enums.WeatherEffNoticeType;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.skill.FieldSkillID;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.fieldskill.BlackMageTraceBall;
import net.swordie.ms.world.field.fieldskill.CenterDirRotationAttack;
import net.swordie.ms.world.field.fieldskill.CreationAreaWarning;
import net.swordie.ms.world.field.fieldskill.DynamicObjectOnAndAttack;
import net.swordie.ms.world.field.fieldskill.FieldSkill;
import net.swordie.ms.world.field.fieldskill.FieldSkillInfo;
import net.swordie.ms.world.field.fieldskill.LocalLaserAttackByIntervalLaser;
import net.swordie.ms.world.field.fieldskill.MonochromeAreaWarning;
import net.swordie.ms.world.field.fieldskill.SummonedSequenceOnAndAttack;
import net.swordie.ms.world.field.instance.instancehelper.boss.BlackMageFightData.Entry;
import net.swordie.ms.world.field.instance.instancehelper.boss.BlackMageFightData.Kind;
import net.swordie.ms.world.field.instance.instancehelper.boss.BlackMageFightData.Trigger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static net.swordie.ms.constants.BossConstants.*;

/**
 * The Black Mage (Limina), Hard and Extreme: four phase maps in a row, then Beyond Existence with the Genesis Crux.
 * Each phase runs from resources/data/blackmage-fight.tsv (see its header): the HP holder with the boss HP bar, the
 * mobs linked to it (phase 1's knights, phase 4's body), the mobs the field skills borrow their attacks from (without
 * them the chains, the gaze and the curses hit for nothing), and the field skills, timer and waves on GMS's timing.
 * Field skill damage is the client's (USER_HIT with the reference mob's attack); the curses those attacks carry are
 * the server's (BlackMageCurse), as are the phase 2 timer's crimson columns (FieldSkill 100017).
 */
public class BlackMageInstanceHelper extends StagedBossHelper {

    private static final long PHASE_TICK_MS = 250;

    private volatile int mode = -1;
    private Stage[] stages;

    /** Guards the schedule: the started phase and each row's next time. */
    private final Object scheduleLock = new Object();
    private int startedPhase;
    private final Map<Entry, Long> nextAt = new HashMap<>();
    private final Map<Entry, Long> nextLeadAt = new HashMap<>();
    private final AtomicInteger wallStep = new AtomicInteger();
    private volatile int mark = 1;

    private int mode() {
        if (mode < 0) {
            int m = getInstance() == null ? -1 : getBlackMageMode(getInstance().getEnterFieldId());
            mode = Math.max(0, m);
        }
        return mode;
    }

    private String modeKey() {
        return BLACK_MAGE_MODE_KEYS[mode()];
    }

    @Override
    protected synchronized Stage[] stages() {
        if (stages == null) {
            int[] f = BLACK_MAGE_FIELDS[mode()];
            long[] hp = BLACK_MAGE_HP[mode()];
            Stage[] st = new Stage[hp.length];
            for (int i = 0; i < st.length; i++) {
                Entry h = BlackMageFightData.first(modeKey(), i + 1, Kind.holder);
                if (h == null) {
                    log.error("blackmage-fight.tsv has no holder for " + modeKey() + " phase " + (i + 1));
                }
                st[i] = h == null ? new Stage(f[i], 0, hp[i], new int[0], 0, 0, 0)
                        : new Stage(f[i], h.id(), hp[i], new int[0], 0, h.x(), h.y());
            }
            stages = st;
        }
        return stages;
    }

    @Override
    protected int rewardFieldId() {
        return BLACK_MAGE_FIELDS[mode()][4];
    }

    @Override
    protected int chestId() {
        return BLACK_MAGE_GENESIS_CRUX;
    }

    @Override
    protected Position chestPos() {
        return new Position(0, 218);
    }

    @Override
    protected String bossName() {
        return "The Black Mage";
    }

    @Override
    protected void startMechanics() {
        super.startMechanics();
        every(PHASE_TICK_MS, this::phaseTick);
    }

    /** 1-4 for the fight's phase maps, else 0. */
    private int phaseOf(Field field) {
        int[] f = BLACK_MAGE_FIELDS[mode()];
        for (int i = 0; i < stages().length; i++) {
            if (field != null && field.getId() == f[i]) {
                return i + 1;
            }
        }
        return 0;
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        super.onWarp(chr, oldField, newField);
        if (phaseOf(newField) > 0 && chr.getDeathCount() > 0) {
            // GMS on every phase map: the death count window and its count
            chr.write(FieldPacket.openUI(BLACK_MAGE_DEATH_COUNT_UI));
            chr.write(UserLocal.deathCountInfo2(chr.getId(), chr.getDeathCount()));
        }
    }

    private void phaseTick() {
        Field field = getField();
        int phase = phaseOf(field);
        if (phase == 0 || isBossDead() || mobOf(field, stages()[phase - 1].holderId()) == null) {
            return; // not spawned yet
        }
        Mob holder = mobOf(field, stages()[phase - 1].holderId());
        List<Entry> due = new ArrayList<>();
        List<Entry> dueLeads = new ArrayList<>();
        boolean start = false;
        long now = System.currentTimeMillis();
        synchronized (scheduleLock) {
            if (phase != startedPhase) {
                startedPhase = phase;
                start = true;
                nextAt.clear();
                nextLeadAt.clear();
                wallStep.set(0);
                for (Entry e : BlackMageFightData.phase(modeKey(), phase)) {
                    if (isScheduled(e.kind())) {
                        nextAt.put(e, now + e.first());
                        if (e.lead() > 0 && !e.text().isBlank()) {
                            nextLeadAt.put(e, now + e.first() - e.lead());
                        }
                    }
                }
            }
            collectDue(nextAt, now, due);
            collectDue(nextLeadAt, now, dueLeads);
        }
        if (start && holder != null) {
            startPhase(field, phase, holder);
        }
        for (Entry e : dueLeads) {
            bossNotice(WeatherEffNoticeType.BlackMage, e.text(), e.ms());
        }
        for (Entry e : due) {
            fire(field, e);
        }
    }

    private static boolean isScheduled(Kind k) {
        return switch (k) {
            case skill, attack, wave, timer, marks, notice, popup -> true;
            default -> false;
        };
    }

    /** Moves due rows out (next time += period, or done when it has none). Called under scheduleLock. */
    private static void collectDue(Map<Entry, Long> times, long now, List<Entry> out) {
        for (var it = times.entrySet().iterator(); it.hasNext(); ) {
            var t = it.next();
            if (now < t.getValue()) {
                continue;
            }
            out.add(t.getKey());
            if (t.getKey().period() > 0) {
                t.setValue(t.getValue() + t.getKey().period());
            } else {
                it.remove();
            }
        }
    }

    /** The phase's mobs besides the holder, and the timer's first countdown. */
    private void startPhase(Field field, int phase, Mob holder) {
        for (Entry e : BlackMageFightData.phase(modeKey(), phase)) {
            if (e.kind() == Kind.linked) {
                Mob m = spawnAdd(e.id(), new Position(e.x(), e.y()), holder.getMaxHp());
                if (m != null) {
                    m.setHpLinkMob(holder);
                }
            } else if (e.kind() == Kind.ref) {
                spawnAdd(e.id(), new Position(e.x(), e.y()), 0);
            } else if (e.kind() == Kind.timer) {
                field.broadcastPacket(BlackMageFieldPacket.nextPattern(e.level()));
            }
        }
    }

    private void fire(Field field, Entry e) {
        switch (e.kind()) {
            case skill -> castSkill(field, e.id(), e.level(), e.actor(), e.action());
            case attack -> {
                Mob actor = mobOf(field, e.actor());
                if (actor != null) {
                    field.broadcastPacket(MobPool.forceAttack(actor.getObjectId(), e.level()));
                }
            }
            case wave -> {
                for (int x : e.xs()) {
                    spawnAdd(e.id(), new Position(x, e.y()), 0);
                }
            }
            case timer -> timerRunsOut(field, e);
            case marks -> markPlayers(field);
            case notice -> bossNotice(WeatherEffNoticeType.BlackMage, e.text(), e.ms());
            case popup -> field.broadcastPacket(UserLocal.addPopupSay(0, e.ms(), e.text(), ""));
            default -> {
            }
        }
    }

    /** The big pattern's clock ran out: restart it, then the timer's skill, walls and columns. */
    private void timerRunsOut(Field field, Entry timer) {
        Mob actor = mobOf(field, timer.actor());
        if (actor != null && timer.action() >= 0) {
            field.broadcastPacket(MobPool.forcedAction(actor.getObjectId(), timer.action(), true));
        }
        if (!timer.text().isBlank()) {
            bossNotice(WeatherEffNoticeType.BlackMage, timer.text(), timer.ms());
        }
        field.broadcastPacket(BlackMageFieldPacket.nextPattern(timer.level()));
        if (timer.arg() == 1) {
            field.broadcastPacket(BlackMageFieldPacket.timer(BlackMageTimerType.Pattern));
        }
        int phase = phaseOf(field);
        Entry walls = BlackMageFightData.first(modeKey(), phase, Kind.walls);
        int step = walls == null ? -1
                : Math.min(wallStep.getAndIncrement(), Math.min(walls.xs().length, walls.xs2().length) - 1);
        for (Entry e : BlackMageFightData.phase(modeKey(), phase)) {
            if (e.kind() == Kind.timerskill && e.id() == FieldSkillID.SummonedSequenceOnAndAttack.getId()) {
                if (walls != null) {
                    later(e.first(), () -> castWalls(field, e.level(), walls, step));
                    later(e.first() + walls.first(), () -> spawnWalls(field, walls, step));
                }
            } else if (e.kind() == Kind.timerskill) {
                later(e.first(), () -> castSkill(field, e.id(), e.level(), 0, -1));
            } else if (e.kind() == Kind.trigger) {
                later(e.first(), () -> crimsonColumns(field, e));
            }
        }
    }

    private void castSkill(Field field, int skillId, int slv, int actorId, int action) {
        if (field != getField()) {
            return;
        }
        FieldSkill cast = buildSkill(skillId, slv);
        if (cast == null) {
            log.warn("Black Mage: no field skill writer for " + skillId + " in " + field.getId());
            return;
        }
        field.broadcastPacket(FieldPacket.fieldSkill(cast));
        Mob actor = mobOf(field, actorId);
        if (actor != null && action >= 0) {
            field.broadcastPacket(MobPool.forcedAction(actor.getObjectId(), action, false));
        }
    }

    private FieldSkill buildSkill(int skillId, int slv) {
        FieldSkillID id = null;
        for (FieldSkillID f : FieldSkillID.values()) {
            if (f.getId() == skillId) {
                id = f;
                break;
            }
        }
        if (id == null) {
            return null;
        }
        return switch (id) {
            case LocalLaserAttackByIntervalLaser -> new LocalLaserAttackByIntervalLaser(slv);
            case CreationAreaWarning -> new CreationAreaWarning(slv);
            case BlacMageTraceBall -> new BlackMageTraceBall(slv);
            case CenterDirRotationAttack -> ballBarrage(slv);
            case DynamicObjectOnAndAttack -> creation(slv);
            case Monochrome_AreaWarning, Monochrome_AreaWarning2 -> monochrome(id, slv);
            default -> null;
        };
    }

    /** Shrieking Wall (100008): the pair closes one step each time the timer runs out. */
    private void castWalls(Field field, int slv, Entry walls, int step) {
        if (field != getField() || step < 0) {
            return;
        }
        SummonedSequenceOnAndAttack cast = new SummonedSequenceOnAndAttack(slv);
        cast.getPositionList().add(new Position(walls.xs()[step], walls.y()));
        cast.getPositionList().add(new Position(walls.xs2()[step], walls.y()));
        field.broadcastPacket(FieldPacket.fieldSkill(cast));
    }

    /** GMS spawns the wall pair as mobs after the cast; the earlier pairs stay. */
    private void spawnWalls(Field field, Entry walls, int step) {
        if (field != getField() || step < 0) {
            return;
        }
        spawnAdd(walls.id(), new Position(walls.xs()[step], walls.y()), 0);
        spawnAdd(walls.actor(), new Position(walls.xs2()[step], walls.y()), 0);
    }

    /**
     * The phase 2 timer (FieldSkill 100017 BMTrigger2): the set the boss's action picked strikes after its delays;
     * whoever stands in a column then gets the warning effect, and is struck down when the columns end.
     */
    private void crimsonColumns(Field field, Entry e) {
        Trigger set = null;
        for (Trigger t : BlackMageFightData.triggers(e.id(), e.level())) {
            if (t.action() == e.action()) {
                set = t;
            }
        }
        if (set == null) {
            return;
        }
        Trigger t = set;
        later(t.actionDelay() + t.delay(), () -> {
            if (field != getField()) {
                return;
            }
            List<Char> struck = new ArrayList<>();
            for (Char chr : getLivingChars()) {
                Position p = chr.getPosition();
                if (p != null && !chr.isHidden() && t.hits(p.getX(), p.getY())) {
                    struck.add(chr);
                    chr.write(UserPacket.effect(Effect.blackMageEffect(e.id(), e.level(), t.index(), 0)));
                }
            }
            later(t.duration(), () -> {
                for (Char chr : struck) {
                    if (chr.getField() == field && chr.getHP() > 0) {
                        chr.write(UserPacket.effect(Effect.fieldSkillHit(e.id(), e.level())));
                        chr.die();
                    }
                }
            });
        });
    }

    @Override
    public void onMobDeath(Field field, Mob mob) {
        int phase = phaseOf(field);
        if (phase > 0 && mob.getTemplateId() == stages()[phase - 1].holderId()) {
            for (Entry e : BlackMageFightData.phase(modeKey(), phase)) {
                if (e.kind() == Kind.linked) {
                    Mob linked = mobOf(field, e.id());
                    if (linked != null) {
                        linked.die(false);
                    }
                } else if (e.kind() == Kind.clear) {
                    bossNotice(WeatherEffNoticeType.BlackMage, e.text(), e.ms());
                }
            }
            field.broadcastPacket(BlackMageFieldPacket.timer(BlackMageTimerType.PhaseOver));
        }
        super.onMobDeath(field, mob);
    }

    private static Mob mobOf(Field field, int templateId) {
        if (field == null || templateId <= 0) {
            return null;
        }
        Life life = field.getLifeByTemplateId(templateId);
        return life instanceof Mob m && !m.isDead() ? m : null;
    }

    /**
     * Light/dark marks for phase 4: BlackMageDebuff (local stat 555, generic tuple). The client reads its n
     * (struct value 0x2a50, getter 0x141426460) as the colour 1 or 2 (field skill object 0x142b7b2c0 and 0x142b9cea0
     * compare it), so the Monochrome pillars of the matching colour hit and the others pass.
     */
    private void markPlayers(Field field) {
        mark = mark == 1 ? 2 : 1;
        for (Char chr : getLivingChars()) {
            try {
                var opt = new Option(mark, FieldSkillID.Monochrome_AreaWarning.getId(), BLACK_MAGE_MARK_SECONDS);
                var tsm = chr.getTemporaryStatManager();
                tsm.putCharacterStatValue(CharacterTemporaryStat.BlackMageDebuff, opt);
                tsm.sendSetStatPacket();
            } catch (Exception e) {
                log.error("Black Mage mark on character " + chr.getId() + " in " + field.getId(), e);
            }
        }
        notice(mark == 1 ? "The light of creation marks you." : "The darkness of destruction marks you.");
    }

    /** Creation (100013): orbs of creation at a few spots (the client builds the effect from the skill data). */
    DynamicObjectOnAndAttack creation(int slv) {
        DynamicObjectOnAndAttack cast = new DynamicObjectOnAndAttack(slv);
        int count = Util.getRandomInclUpperBound(3, 5);
        for (int i = 0; i < count; i++) {
            FieldSkillInfo info = new FieldSkillInfo();
            info.setPosition(randomGround());
            info.setObjName("");
            cast.getFieldSkillInfoList().add(info);
        }
        return cast;
    }

    /** The ball barrage (100011, phase 3; level data: 7-8 balls, angle 0-90, attackDelay 1080). */
    CenterDirRotationAttack ballBarrage(int slv) {
        CenterDirRotationAttack cast = new CenterDirRotationAttack(slv);
        int count = Util.getRandomInclUpperBound(7, 8);
        for (int i = 0; i < count; i++) {
            FieldSkillInfo info = new FieldSkillInfo();
            info.setPosition(randomGround());
            info.setRotation(Util.getRandom(0, 90));
            info.setAttackDelay(1080 + 150 * i);
            cast.getFieldSkillInfoList().add(info);
        }
        return cast;
    }

    /** The black/white Monochrome warning (100014 / 100016, phase 4; rect lt (-60,-700) rb (60,5) per strike). */
    MonochromeAreaWarning monochrome(FieldSkillID id, int slv) {
        MonochromeAreaWarning cast = new MonochromeAreaWarning(id, slv);
        int count = Util.getRandomInclUpperBound(3, 5);
        for (int i = 0; i < count; i++) {
            Position pos = randomGround();
            FieldSkillInfo info = new FieldSkillInfo();
            // after the rect the client keeps the first int at +0x18 and tests its parity against the player's
            // BlackMageDebuff colour (0x142b7b2c0): odd = light (mark 1), even = dark (mark 2)
            info.setPosition(new Position(i % 2 == 0 ? 1 : 2, 0));
            info.setRect(new Rect(pos.getX() - 60, pos.getY() - 700, pos.getX() + 60, pos.getY() + 5));
            cast.getFieldSkillInfoList().add(info);
        }
        return cast;
    }
}
