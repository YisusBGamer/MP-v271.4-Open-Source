package net.swordie.ms.client.jobs.adventurer;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.CharacterStat;
import net.swordie.ms.client.character.info.HitInfo;
import net.swordie.ms.client.character.items.BodyPart;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.SkillUseSource;
import net.swordie.ms.client.character.skills.atom.secondatom.SecondAtom;
import net.swordie.ms.client.character.skills.debuff.DebuffInfo;
import net.swordie.ms.client.character.skills.debuff.DebuffUtil;
import net.swordie.ms.client.character.skills.info.*;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.client.character.skills.ExtraSkillInfo;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Kinesis after the v270 Redux: MP instead of Psychic Points, no psychic grab/lock or psychic areas (the v271 client
 * has no psychic-lock or psychic-area attack writers), Ultimate skills on cooldowns that Breakthrough shortens, and
 * Kinetic Combo as a second atom.
 */
public class Kinesis extends Job {
    private static final Logger log = LogManager.getLogger(Kinesis.class);

    public static final int RETURN_KINESIS = 140001290;
    public static final int PSYCHIC_ATTACK = 140001289;

    public static final int PSYCHIC_FORCE = 142001000;
    public static final int PSYCHIC_FORCE_DOWN = 142001001;
    public static final int ESP_BOOSTER = 142001002;
    public static final int KINETIC_STEP = 142001003;

    public static final int ULTIMATE_PRESSURE = 142100001;
    public static final int PSYCHIC_RISE = 142101000;
    public static final int PSYCHIC_SPRINT = 142101002;

    public static final int KINETIC_COMBO = 142110006;
    public static final int PSYCHIC_FALL = 142111000;
    public static final int ULTIMATE_OPENING = 142111001;
    public static final int ULTIMATE_OPENING_FIELD = 142111002;
    public static final int ULTIMATE_OPENING_TRAIN = 142111003;
    public static final int KINETIC_JAUNT = 142111005;
    public static final int WARP = 142111007;

    public static final int PSYCHIC_SMASH = 142121000;
    public static final int ULTIMATE_GAMBIT = 142121001;
    public static final int ULTIMATE_CHECK = 142121002;
    public static final int BREAKTHROUGH = 142121003;
    public static final int PRESIDENTS_ORDERS = 142121004;
    public static final int CLEAR_MIND = 142121005;
    public static final int MENTAL_TEMPEST = 142121041;
    public static final int MENTAL_OVERDRIVE = 142121042;
    public static final int MENTAL_SHOCK = 142121043;

    public static final int HEXA_ULTIMATE_PRESSURE = 142140001;
    public static final int HEXA_KINETIC_COMBO = 142140008;
    public static final int HEXA_PSYCHIC_SMASH = 142141000;
    public static final int HEXA_ULTIMATE_CHECK = 142141002;
    public static final int HEXA_ULTIMATE_OPENING = 142141003;
    public static final int HEXA_ULTIMATE_GAMBIT = 142141007;
    public static final int FROM_ANOTHER_REALM = 142141500;
    public static final int FRACTAL_HORIZON = 142141502;

    // V
    public static final int PSYCHIC_SHOCKWAVE = 400021158;
    public static final int PSYCHIC_NOVA = 400021159;
    public static final int ULTIMATE_CHECKMATE = 400021161;
    public static final int PSYCHIC_CASTLE = 400021163;

    // v265 skills without a v271 counterpart: their ids are gone from v271's data or now belong to other skills, so
    // the names other files still use get ids that never match
    public static final int PSYCHIC_TORNADO = 400021008;
    public static final int PSYCHIC_TORNADO_1 = 400021009;
    public static final int PSYCHIC_TORNADO_2 = 400021010;
    public static final int PSYCHIC_TORNADO_3 = 400021011;
    public static final int MIND_OVER_MATTER = 400021048;
    public static final int LAW_OF_GRAVITY = 400021096;
    public static final int ULTIMATE_BPM = -142121005;

    private static final int[] ULTIMATES = {ULTIMATE_OPENING, ULTIMATE_GAMBIT, ULTIMATE_CHECK, HEXA_ULTIMATE_OPENING,
            HEXA_ULTIMATE_GAMBIT, HEXA_ULTIMATE_CHECK, ULTIMATE_CHECKMATE};
    // unleashCooltime in the v271 data (seconds): Ultimates 10, Ultimate: Pressure 2
    private static final int ULTIMATE_UNLEASH_COOLTIME = 10;
    private static final int PRESSURE_UNLEASH_COOLTIME = 2;
    private static final int COMBO_RANGE = 480;
    public static final int HEXA_ULTIMATE_TRAINWRECK = 142141005;
    public static final int FROM_ANOTHER_REALM_2 = 142141501;
    public static final int PSYCHIC_TORNADO_4 = 400020009;
    public static final int PSYCHIC_TORNADO_5 = 400020010;
    public static final int PSYCHIC_TORNADO_6 = 400020011;
    public static final int ULTIMATE_PSYCHIC_SHOCKWAVE = 400021075;
    public static final int LAW_OF_GRAVITY_2 = 400021097;
    public static final int LAW_OF_GRAVITY_3 = 400021098;
    public static final int HEXA_ULTIMATE_OPENING_2 = 142141004;
    public static final int PSYCHIC_NOVA_2 = 400021160;
    public static final int ULTIMATE_CHECKMATE_2 = 400021162;
    /** Ultimate: Pressure's skillList2: the direct attacks that can open a micro black hole. */
    private static final Set<Integer> PRESSURE_TRIGGERS = Set.of(PSYCHIC_FORCE, PSYCHIC_FORCE_DOWN, PSYCHIC_RISE,
            PSYCHIC_SPRINT, PSYCHIC_FALL, ULTIMATE_OPENING, ULTIMATE_OPENING_FIELD, ULTIMATE_OPENING_TRAIN,
            PSYCHIC_SMASH, ULTIMATE_GAMBIT, ULTIMATE_CHECK, MENTAL_SHOCK, HEXA_PSYCHIC_SMASH, HEXA_ULTIMATE_CHECK,
            HEXA_ULTIMATE_OPENING, HEXA_ULTIMATE_OPENING_2, HEXA_ULTIMATE_TRAINWRECK, HEXA_ULTIMATE_GAMBIT, FROM_ANOTHER_REALM, FROM_ANOTHER_REALM_2,
            PSYCHIC_SHOCKWAVE, PSYCHIC_NOVA, PSYCHIC_NOVA_2, ULTIMATE_CHECKMATE_2);
    private static final int PRESSURE_RANGE = 600;
    public static final int PAWN_CHESS_PIECE = 1353200;
    public static final int TRAINING_ROOM_1_FIELD = 331001110;
    private long nextPressure;

    private long breakthroughEnd;
    private boolean comboLogged;
    /** Warp's Condensed Psychokinesis charges (data: v max, one back every s ms); the client stat KinesisTeleport. */
    private int warpCharges = -1;
    private java.util.concurrent.ScheduledFuture warpRecharge;

    public Kinesis(Char chr) {
        super(chr);
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isKinesis(id);
    }

    private static boolean isUltimate(int skillId) {
        for (int id : ULTIMATES) {
            if (id == skillId) {
                return true;
            }
        }
        return false;
    }

    private int getWarpMax() {
        int slv = chr.getSkillLevel(WARP);
        SkillInfo si = SkillData.getSkillInfoById(WARP);
        return slv <= 0 || si == null ? 0 : Math.max(1, si.getValue(v, slv));
    }

    private void putWarpCharges(TemporaryStatManager tsm) {
        tsm.putCharacterStatValue(KinesisTeleport, new Option(warpCharges, WARP, 0));
    }

    private void scheduleWarpRecharge() {
        if (warpRecharge != null && !warpRecharge.isDone()) {
            return;
        }
        int slv = chr.getSkillLevel(WARP);
        SkillInfo si = SkillData.getSkillInfoById(WARP);
        if (slv <= 0 || si == null) {
            return;
        }
        long ms = Math.max(1000, si.getValue(s, slv));
        warpRecharge = net.swordie.ms.handlers.executors.EventManager.addEvent(() -> {
            warpRecharge = null;
            int max = getWarpMax();
            if (chr.getClient() == null || max <= 0) {
                return;
            }
            warpCharges = Math.min(max, warpCharges + 1);
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            putWarpCharges(tsm);
            tsm.sendSetStatPacket();
            if (warpCharges < max) {
                scheduleWarpRecharge();
            }
        }, ms);
    }

    @Override
    public void onWarp(net.swordie.ms.world.field.Field oldField, net.swordie.ms.world.field.Field newField) {
        super.onWarp(oldField, newField);
        int max = getWarpMax();
        if (newField == null || max <= 0 || !isHandlerOfJob(chr.getJob())) {
            return;
        }
        if (warpCharges < 0) {
            warpCharges = max;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!tsm.hasStat(KinesisTeleport)) {
            putWarpCharges(tsm);
            tsm.sendSetStatPacket();
        }
        if (warpCharges < max) {
            scheduleWarpRecharge();
        }
    }

    @Override
    public void stopTimers() {
        if (warpRecharge != null) {
            warpRecharge.cancel(false);
            warpRecharge = null;
        }
        super.stopTimers();
    }

    private boolean inBreakthrough() {
        return breakthroughEnd > System.currentTimeMillis();
    }

    // Attack related methods ------------------------------------------------------------------------------------------

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        Char chr = c.getChr();
        int skillID = attackInfo.skillId;
        boolean hasHitMobs = attackInfo.mobAttackInfo.size() > 0;
        if (hasHitMobs) {
            kineticCombo(attackInfo);
        }
        pressure(skillID);
        Option o1 = new Option();
        Option o2 = new Option();
        switch (skillID) {
            case PSYCHIC_SMASH:
            case HEXA_PSYCHIC_SMASH: {
                SkillInfo si = SkillData.getSkillInfoById(skillID);
                int slv = Math.max(1, attackInfo.slv);
                if (si == null || !hasHitMobs) {
                    break;
                }
                // Defense -x%, Speed -y% for time sec
                o1.nOption = -si.getValue(x, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                o2.nOption = -si.getValue(y, slv);
                o2.rOption = skillID;
                o2.tOption = si.getValue(time, slv);
                DebuffUtil.applyDebuffOnMobs(chr, Map.of(MobStat.PDR, o1, MobStat.MDR, o1, MobStat.Speed, o2),
                        attackInfo.getMobIds());
                break;
            }
            case MENTAL_SHOCK: {
                SkillInfo si = SkillData.getSkillInfoById(skillID);
                if (si == null || !hasHitMobs) {
                    break;
                }
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, Math.max(1, attackInfo.slv));
                DebuffUtil.applyDebuffOnMobs(chr, MobStat.Stun, o1, attackInfo.getMobIds(), new DebuffInfo(true));
                break;
            }
        }
        super.handleAttack(c, attackInfo);
    }

    /**
     * Kinetic Combo: a direct attack (not the combo's own hit) creates x energy bombs at prop% (Ultimates: y%), boss
     * with the highest HP first. The v271 data gives the skill a SecondAtom node instead of the v265 force atom.
     */
    private void kineticCombo(AttackInfo attackInfo) {
        int skillId = attackInfo.skillId;
        if (skillId == KINETIC_COMBO || skillId == HEXA_KINETIC_COMBO || skillId == ULTIMATE_PRESSURE
                || skillId == HEXA_ULTIMATE_PRESSURE || SkillData.getSkillInfoById(skillId) == null) {
            return;
        }
        int comboId = chr.getSkillLevel(HEXA_KINETIC_COMBO) > 0 ? HEXA_KINETIC_COMBO : KINETIC_COMBO;
        int slv = chr.getSkillLevel(comboId);
        SkillInfo si = SkillData.getSkillInfoById(comboId);
        if (slv <= 0 || si == null || chr.getField() == null) {
            return;
        }
        if (si.getSecondAtomInfos().isEmpty()) {
            if (!comboLogged) {
                comboLogged = true;
                log.debug("[Kinesis] Kinetic Combo " + comboId + " has no SecondAtom data");
            }
            return;
        }
        int chance = isUltimate(skillId) ? si.getValue(y, slv) : si.getValue(prop, slv);
        if (!Util.succeedProp(chance)) {
            return;
        }
        Rect rect = chr.getRectAround(new Rect(-COMBO_RANGE, -COMBO_RANGE, COMBO_RANGE, COMBO_RANGE));
        List<Mob> mobs = new ArrayList<>(chr.getField().getMobsInRect(rect));
        if (mobs.isEmpty()) {
            return;
        }
        mobs.sort(Comparator.comparing((Mob m) -> !m.isBoss()).thenComparing(m -> -m.getHp()));
        SecondAtomInfo sai = si.getSecondAtomInfos().get(0);
        int count = Math.max(1, si.getValue(x, slv));
        List<SecondAtom> atoms = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Mob mob = mobs.get(0);
            atoms.add(new SecondAtom(chr.getNewSecondAtomKey(), sai.getDataIndex(), i, chr.getId(), mob.getObjectId(),
                    comboId, sai.getCreateDelay(), sai.getEnableDelay(), sai.getRotate(), sai.getExpire(), 360,
                    sai.getAttackableCount(), chr.getPosition(), sai.getCustomList()));
        }
        chr.spawnSecondAtoms(atoms);
    }

    /**
     * Ultimate: Pressure (HEXA 142140001 over 142100001): a trigger attack has prop% to open a micro black hole at the
     * boss with the highest Max HP in range (else the mob with the most), cooldown cooltime sec (unleashCooltime in
     * Breakthrough). Like Sea Serpent, the server registers it as the client's extra skill; its hits come back as
     * the Pressure attack.
     */
    private void pressure(int skillId) {
        if (!PRESSURE_TRIGGERS.contains(skillId) || chr.getField() == null) {
            return;
        }
        int pressureId = chr.getSkillLevel(HEXA_ULTIMATE_PRESSURE) > 0 ? HEXA_ULTIMATE_PRESSURE : ULTIMATE_PRESSURE;
        int slv = chr.getSkillLevel(pressureId);
        SkillInfo si = SkillData.getSkillInfoById(pressureId);
        long now = System.currentTimeMillis();
        if (slv <= 0 || si == null || now < nextPressure || chr.hasSkillOnCooldown(pressureId)
                || !Util.succeedProp(si.getValue(prop, slv))) {
            return;
        }
        Rect rect = chr.getRectAround(new Rect(-PRESSURE_RANGE, -PRESSURE_RANGE, PRESSURE_RANGE, PRESSURE_RANGE));
        Mob target = chr.getField().getMobsInRect(rect).stream()
                .max(Comparator.comparing(Mob::isBoss).thenComparing(Mob::getMaxHp)).orElse(null);
        if (target == null) {
            return;
        }
        int cd = inBreakthrough() ? PRESSURE_UNLEASH_COOLTIME : Math.max(1, si.getValue(cooltime, slv));
        nextPressure = now + cd * 1000L;
        chr.write(FieldPacket.registerExtraSkill(pressureId,
                new ExtraSkillInfo(pressureId, target.getPosition(), chr.isLeft(), 0, 1)));
    }

    // Skill related methods -------------------------------------------------------------------------------------------

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        super.handleSkill(chr, tsm, skillId, slv, inPacket, skillUseInfo);
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (si == null) {
            return;
        }
        Option o1 = new Option();
        switch (skillId) {
            case ESP_BOOSTER:
                o1.nOption = si.getValue(indieBooster, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieBooster, o1);
                break;
            case KINETIC_JAUNT:
                // ends on the key-down cancel; w (30) as a safety limit [U: meaning of u/v/w]
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = Math.max(1, si.getValue(w, slv));
                tsm.putCharacterStatValue(NewFlying, o1);
                break;
            case MENTAL_OVERDRIVE:
                o1.nOption = si.getValue(indieDamR, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o1);
                break;
            case MENTAL_TEMPEST: {
                // invincible for ndTime ms (data: 10 s)
                int ms = si.getValue(ndTime, slv);
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = ms > 0 ? ms : 10000;
                o1.setInMillis(true);
                tsm.putCharacterStatValue(IndieNotDamaged, o1, true); // GMS: the indie stat
                break;
            }
            case PSYCHIC_CASTLE:
                tsm.putCharacterStatValue(KinesisPsychicBoard, new Option(1, skillId, si.getValue(time, slv)));
                break;
            case WARP:
                if (warpCharges < 0) {
                    warpCharges = Math.max(1, si.getValue(v, slv));
                }
                warpCharges = Math.max(0, warpCharges - 1);
                putWarpCharges(tsm);
                scheduleWarpRecharge();
                break;
            case BREAKTHROUGH:
                // Ultimates come off cooldown now and use their unleash cooldown while it lasts; the client applies
                // the Ultimate final damage itself. No named v271 stat: the icon rides on IndieBuffIcon.
                breakthroughEnd = System.currentTimeMillis() + si.getValue(time, slv) * 1000L;
                tsm.putCharacterStatValue(IndieBuffIcon, new Option(1, skillId, si.getValue(time, slv)));
                nextPressure = 0;
                for (int id : ULTIMATES) {
                    chr.resetSkillCoolTime(id);
                }
                break;
        }
        tsm.sendSetStatPacket();
    }

    @Override
    public int alterCooldownSkill(int skillId, int slv, AttackInfo attackInfo, SkillUseInfo sui, SkillUseSource source) {
        if (inBreakthrough()) {
            if (isUltimate(skillId)) {
                return ULTIMATE_UNLEASH_COOLTIME * 1000;
            }
            if (skillId == ULTIMATE_PRESSURE || skillId == HEXA_ULTIMATE_PRESSURE) {
                return PRESSURE_UNLEASH_COOLTIME * 1000;
            }
        }
        return super.alterCooldownSkill(skillId, slv, attackInfo, sui, source);
    }

    @Override
    public void handleCancelKeyDownSkill(Char chr, int skillID) {
        if (skillID == KINETIC_JAUNT) {
            var tsm = chr.getTemporaryStatManager();
            tsm.removeStatsBySkill(KINETIC_JAUNT);
            tsm.removeStat(IndieKeyDownTime);
            tsm.removeStat(KeyDownAreaMoving);
            return; // the cooldown starts at the cast
        }
        super.handleCancelKeyDownSkill(chr, skillID);
    }

    // Hit related methods ---------------------------------------------------------------------------------------------

    @Override
    public void handleHit(Client c, InPacket inPacket, HitInfo hitInfo) {
        super.handleHit(c, inPacket, hitInfo);
    }

    @Override
    public void setCharCreationStats(Char chr) {
        super.setCharCreationStats(chr);
        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        Item item = ItemData.getItemDeepCopy(PAWN_CHESS_PIECE); // Pawn Chess Piece
        if (item != null) {
            item.setBagIndex(BodyPart.Shield.getVal());
            chr.getEquippedInventory().addItem(item);
        }
        chr.setSpToCurrentJob(5);
        cs.setLevel(10);
        cs.setJob(14200);
        cs.setMaxHp(574);
        cs.setHp(574);
        cs.setInt(45);
        cs.setPosMap(TRAINING_ROOM_1_FIELD); // the tutorial: Kinesis's Hideout, Training Room 1 (it asks Classic or Express)
    }
}
