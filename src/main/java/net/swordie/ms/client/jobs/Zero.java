package net.swordie.ms.client.jobs;

import net.swordie.ms.constants.ItemConstants;
import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.CharacterStat;
import net.swordie.ms.client.character.ExtendSP;
import net.swordie.ms.client.character.avatar.AvatarLook;
import net.swordie.ms.client.character.info.HitInfo;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.skills.*;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.MobAttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.*;
import net.swordie.ms.constants.*;
import net.swordie.ms.enums.MoveAbility;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.life.AffectedArea;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.MobTemporaryStat;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.loaders.ItemData;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Created on 12/14/2017.
 */
public class Zero extends Job {

    public static final int DUAL_COMBAT = 100001270;
    public static final int DUAL_COMBAT_2 = 100000282;
    public static final int TEMPLE_RECALL = 100001262;
    public static final int RESOLUTION_TIME = 100000279;
    public static final int BURST_JUMP = 100001265;
    public static final int BURST_STEP = 100001266;
    public static final int BURST_LEAP = 100001269;


    public static final int DIVINE_FORCE = 100001263; //Aura (Unlimited Duration)
    public static final int DIVINE_SPEED = 100001264; //Aura (Unlimited Duration)
    public static final int RHINNES_PROTECTION = 100001268; //Buff

    public static final int TIME_HOLDING = 100001274;
    public static final int TIME_HOLDING_2 = 100001281;
    public static final int TIME_DISTORTION = 100001261;
    public static final int REWIND = 100001272;
    public static final int FOCUSED_TIME = 100001005;
    public static final int DOUBLE_TIME = 100000267;
    public static final int DOUBLE_TIME_ALPHA = 100000276;
    public static final int DOUBLE_TIME_BETA = 100000277;
    public static final int SHADOW_RAIN = 100001283;


    public static final int AIR_RIOT = 101000101; //Special Attack (Stun Debuff)
    public static final int AIR_RIOT_2 = 101000102;
    public static final int THROWING_WEAPON = 101100100; //Special Attack (Throw Sword)
    public static final int ADV_THROWING_WEAPON = 101100101; //Special Attack (Throw Sword)
    public static final int STORM_BREAK = 101120202;
    public static final int STORM_BREAK_INIT = 101120203;
    public static final int ADV_EARTH_BREAK = 101120104;
    public static final int ADV_STORM_BREAK = 101120204;
    public static final int ADV_EARTH_BREAK_SHOCK_INIT = 101120105; //Attack to initialise the Shockwave
    public static final int ADV_STORM_BREAK_SHOCK_INIT = 101120205; //Attack to initialise the Shockwave
    public static final int ADV_EARTH_BREAK_SHOCKWAVE = 101120106; //Tile Skill
    public static final int ADV_STORM_BREAK_SHOCKWAVE = 101120206; //Tile Skill
    public static final int ADV_SPIN_CUTTER = 101100201;
    public static final int ADV_ROLLING_ASSAULT = 101110203;
    public static final int SPIN_DRIVER = 101110100;
    public static final int WHEEL_WIND = 101110101;
    public static final int ADV_WHEEL_WIND = 101110102;
    public static final int DIVINE_LEER = 101120207;
    public static final int CRITICAL_BIND = 101120110;
    public static final int IMMUNE_BARRIER = 101120109;
    public static final int ARMOR_SPLIT = 101110103;
    public static final int GRAND_ROLLING_CROSS = 101110200;

    // V
    public static final int CHRONO_BREAK = 400011015; // Slow Debuff
    public static final int CHRONO_BREAK_FREEZE_MTS = 400011024; // Freeze Debuff
    public static final int CHRONO_BREAK_AFTER_HIT = 400011025;

    public static final int TWIN_BLADES_OF_TIME_START = 400011039;
    public static final int TWIN_BLADES_OF_TIME_START_2 = 400011043;
    public static final int TWIN_BLADES_OF_TIME_START_3 = 400011040;
    public static final int TWIN_BLADES_OF_TIME_START_4 = 400011044;
    public static final int TWIN_BLADES_OF_TIME_START_5 = 400011041;
    public static final int TWIN_BLADES_OF_TIME_START_6 = 400011045;
    public static final int TWIN_BLADES_OF_TIME_START_7 = 400011042;
    public static final int TWIN_BLADES_OF_TIME_START_8 = 400011046;

    public static final int SHADOW_FLASH_ALPHA_TILE = 400011098;
    public static final int SHADOW_FLASH_ALPHA_ATTACK = 400011099;
    public static final int SHADOW_FLASH_BETA_TILE = 400011100;
    public static final int SHADOW_FLASH_BETA_ATTACK = 400011101;

    public static final int EGO_WEAPON_ALPHA = 400011134; // CTS 648 (v220.3)
    public static final int EGO_WEAPON_BETA = 400011135;
    public static final int HEXA_SHADOW_RAIN = 101141033; // HEXA Shadow Rain
    public static final int HEXA_THROWING_WEAPON = 101141022;
    public static final int TRANSCENDENTS_WILL = 100001284;
    public static final int TIME_PIECE = 101141012; // 6th job: Traces of Time, SecondAtom dataIndex 52
    public static final int HEXA_SPIN_DRIVER = 101141014; // passive: Traces +v, map limit +x

    // Time Piece: assist hits toward the next Traces of Time, the traces waiting (position, expiry ms)
    private int timePieceHits;
    private long timePieceNextTrace;
    private final List<Object[]> timeTraces = new ArrayList<>();

    private static final int[] addedSkills = new int[]{
            DUAL_COMBAT,
            DUAL_COMBAT_2,
            TEMPLE_RECALL,
            RESOLUTION_TIME,
            BURST_STEP,
            BURST_JUMP,
            BURST_LEAP,
    };

    private static final int[] addedSkillsWithoutCurrentLevel = new int[] {
            AIR_RIOT,
            ADV_SPIN_CUTTER,
            GRAND_ROLLING_CROSS,
            ADV_ROLLING_ASSAULT,
            ADV_STORM_BREAK,
            ADV_THROWING_WEAPON,
            ADV_WHEEL_WIND,
            ADV_EARTH_BREAK,
    };

    private int doubleTimePrevSkill = 0;

    public static final int SIDE_ALPHA = 1, SIDE_BETA = 2;

    // no Skill.wz stat for these
    private static final int CHRONO_BREAK_SLOW_SEC = 6; // hardcode-ok
    private static final int EGO_WEAPON_ALPHA_SEC = 1300; // hardcode-ok
    private static final int EGO_WEAPON_BETA_AREA_MS = 60000; // hardcode-ok
    private static final int BLESSING_NEXT_ATTACK_SEC = 2; // hardcode-ok
    private static final int TWIN_BLADES_KEYDOWN_SEC = 15; // hardcode-ok
    public static final int SHADOW_STRIKE = 101000202;

    private record ZeroSkill(int skill, boolean alpha, int stage, boolean tile) {
    }

    private record ZeroSkills(Map<Integer, ZeroSkill> byId, List<Integer> alpha, List<Integer> beta) {
    }

    /** resources/data/zero-skills.tsv: skill, side (alpha/beta), stage (the combo stage's first skill), tile */
    private static final Supplier<ZeroSkills> SKILLS = DataTable.cached("zero-skills", t -> {
        Map<Integer, ZeroSkill> byId = new HashMap<>();
        List<Integer> alpha = new ArrayList<>(), beta = new ArrayList<>();
        for (DataTable.Row r : t.rows()) {
            ZeroSkill s = new ZeroSkill(r.i("skill"), r.str("side").trim().equals("alpha"), r.i("stage", 0),
                    r.b("tile"));
            byId.put(s.skill(), s);
            if (!s.tile()) {
                (s.alpha() ? alpha : beta).add(s.skill());
            }
        }
        return new ZeroSkills(Map.copyOf(byId), List.copyOf(alpha), List.copyOf(beta));
    });

    /** Alpha's combo skills (tiles left out) */
    public static List<Integer> alphaSkills() {
        return SKILLS.get().alpha();
    }

    /** Beta's combo skills (tiles left out) */
    public static List<Integer> betaSkills() {
        return SKILLS.get().beta();
    }

    public static boolean isAlphaSkill(int skillID) {
        return alphaSkills().contains(skillID);
    }

    public static boolean isBetaSkill(int skillID) {
        return betaSkills().contains(skillID);
    }

    /** SIDE_ALPHA or SIDE_BETA; the skill id itself when it's no combo skill */
    public static int getAlphaOrBetaSkill(int skillID) {
        ZeroSkill s = SKILLS.get().byId().get(skillID);
        return s == null ? skillID : s.alpha() ? SIDE_ALPHA : SIDE_BETA;
    }

    /** the combo stage's first skill, 0 if none */
    private static int getOriginalByStageSkill(int skillID) {
        ZeroSkill s = SKILLS.get().byId().get(skillID);
        return s == null ? 0 : s.stage();
    }

    public Zero(Char chr) {
        super(chr);
        if (isHandlerOfJob(chr.getJob())) {
            for (int id : addedSkills) {
                if (!chr.hasSkill(id)) {
                    Skill skill = SkillData.getSkillDeepCopyById(id);
                    skill.setCurrentLevel(skill.getMasterLevel());
                    chr.addSkill(skill);
                }
            }
            for (int id : addedSkillsWithoutCurrentLevel) {
                if (!chr.hasSkill(id)) {
                    chr.addSkill(SkillData.getSkillDeepCopyById(id));
                }
            }
            if (chr.getZeroInfo() == null) {
                chr.initZeroInfo();
            }
        }
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isZero(id);
    }

    private boolean isBeta() {
        return chr.getZeroInfo().isZeroBetaState();
    }

    @Override
    public void handleLevelUp() {

        // Link Skill
        chr.giveLinkSkillToAccount();

        short level = chr.getLevel();
        chr.addStat(Stat.mhp, 500);
        chr.addStat(Stat.ap, 5);
        int sp = 3;
        if (level > 100 && (level % 10) % 3 == 0) {
            sp = 6; // double sp on levels ending in 3/6/9
        }
        ExtendSP esp = chr.getAvatarData().getCharacterStat().getExtendSP();
        esp.setSpToJobLevel(1, esp.getSpByJobLevel((byte) 1) + sp); // Alpha
        esp.setSpToJobLevel(2, esp.getSpByJobLevel((byte) 2) + sp); // Beta
        Map<Stat, Object> stats = new HashMap<>();
        stats.put(Stat.mhp, chr.getStat(Stat.mhp));
        stats.put(Stat.mmp, chr.getStat(Stat.mmp));
        stats.put(Stat.ap, (short) chr.getStat(Stat.ap));
        stats.put(Stat.sp, chr.getAvatarData().getCharacterStat().getExtendSP());
        chr.write(WvsContext.statChanged(stats));

        switch (level) {
            case 100:
                chr.addSkill(DOUBLE_TIME, 1, 1);
                break;
            case 110:
                chr.addSkill(TIME_DISTORTION, 1, 1);
                chr.addSkill(ADV_STORM_BREAK, 0, 30);
                chr.addSkill(ADV_EARTH_BREAK, 0, 30);
                break;
            case 120:
                chr.addSkill(TIME_HOLDING, 1, 1);
                break;
            case 140:
                chr.addSkill(REWIND, 1, 1);
                break;
            case 170:
                chr.addSkill(SHADOW_RAIN, 1, 1);
                break;
            case 200:
                chr.addSkill(FOCUSED_TIME, 1, 1);
                break;
        }
    }

    @Override
    public void setCharCreationStats(Char chr) {
        AvatarLook mainLook = chr.getAvatarData().getAvatarLook();
        chr.getAvatarData().setZeroAvatarLook(mainLook.deepCopy());
        AvatarLook zeroLook = chr.getAvatarData().getZeroAvatarLook();
        mainLook.getHairEquips().remove(Integer.valueOf(ItemConstants.MASSIVE_SHADOW));
        zeroLook.getHairEquips().remove(Integer.valueOf(ItemConstants.SHARP_SHADOW));
        zeroLook.setWeaponId(ItemConstants.MASSIVE_SHADOW);
        zeroLook.setGender(1);
        zeroLook.setSkin(chr.getAvatarData().getAvatarLook().getSkin());
        zeroLook.setFace(21290);
        zeroLook.setHair(37623);
        zeroLook.setZeroBetaLook(true);
        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        cs.setLevel(100);
        cs.setStr(518);
        cs.setDex(4);
        cs.setInt(4);
        cs.setLuk(4);
        cs.setHp(5000);
        cs.setMaxHp(5000);
        cs.setMp(100);
        cs.setMaxMp(100);
        //cs.setJob(10112);

        chr.setJob(JobConstants.JobEnum.ZERO_1.getJobId());
        chr.setJob(JobConstants.JobEnum.ZERO_2.getJobId());
        chr.setJob(JobConstants.JobEnum.ZERO_3.getJobId());
        chr.setJob(JobConstants.JobEnum.ZERO_4.getJobId());

        cs.setGender(2);
        cs.setPosMap(GameConstants.VIDEO_FIELD);

        Item whitePot = ItemData.getItemDeepCopy(ItemConstants.WHITE_POTION);
        whitePot.setQuantity(100);
        chr.addItemToInventory(whitePot);
        Item hyperTp = ItemData.getItemDeepCopy(ItemConstants.HYPER_TELEPORT_ROCK);
        chr.addItemToInventory(hyperTp);

        ExtendSP esp = chr.getAvatarData().getCharacterStat().getExtendSP();
        esp.setSpToJobLevel(1, 6); // Alpha
        esp.setSpToJobLevel(2, 6); // Beta
        Map<Stat, Object> stats = new HashMap<>();
        stats.put(Stat.mhp, chr.getStat(Stat.mhp));
        stats.put(Stat.mmp, chr.getStat(Stat.mmp));
        stats.put(Stat.ap, (short) chr.getStat(Stat.ap));
        stats.put(Stat.sp, chr.getAvatarData().getCharacterStat().getExtendSP());
        chr.write(WvsContext.statChanged(stats));

        // Zero's story chapters (40905 opens the weapon window) are no longer completed here: Classic Zeros play them,
        // Express skips them in the tutorial's first scene (zeroProl000) or when switching (PlayStyleModule#switchTo)
    }


    // Attack related methods ------------------------------------------------------------------------------------------

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        Char chr = c.getChr();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Skill skill = chr.getSkill(attackInfo.skillId);
        int skillID = 0;
        SkillInfo si = null;
        boolean hasHitMobs = attackInfo.mobAttackInfo.size() > 0;
        int slv = 0;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skill.getSkillId());
            slv = (byte) skill.getCurrentLevel();
            skillID = skill.getSkillId();
        }

        if (getAlphaOrBetaSkill(skillID) == SIDE_ALPHA) {
            if (!SkillChangeConstants.DOUBLE_TIME_COUNTER_REQUIRES_MOB_HITS || hasHitMobs) { // [Zero] Custom Skill Change
                incrementDoubleTimeAlpha(skillID);
            }

            if (hasHitMobs) {
                applyDivineLeerOnMob(attackInfo, skillID);

                // Ego Weapon
                if (chr.hasSkill(EGO_WEAPON_ALPHA) && !chr.hasSkillOnCooldown(EGO_WEAPON_ALPHA)) {
                    egoWeaponAlpha(tsm);
                }
            }
        }

        if (getAlphaOrBetaSkill(skillID) == SIDE_BETA) {
            if (!SkillChangeConstants.DOUBLE_TIME_COUNTER_REQUIRES_MOB_HITS || hasHitMobs) { // [Zero] Custom Skill Change
                incrementDoubleTimeBeta(skillID);
            }

            if (hasHitMobs) {
                applyCriticalBindOnMob(attackInfo);
                applyArmorSplitOnMob(attackInfo);

                // Ego Weapon
                if (chr.hasSkill(EGO_WEAPON_ALPHA) && !chr.hasSkillOnCooldown(EGO_WEAPON_BETA)) {
                    egoWeaponBeta();
                }
            }
        }

        if (hasHitMobs && attackInfo.summonID == 0 && attackInfo.skillId != TIME_PIECE && chr.hasSkill(TIME_PIECE)) {
            timePiece(attackInfo.zeroTag == 1);
        }

        Field field = chr.getField();
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();
        Option o5 = new Option();
        switch (attackInfo.skillId) {
            case AIR_RIOT:
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = si.getValue(q, slv);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if (Util.succeedProp(si.getValue(u, slv))) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        if (mob == null) {
                            continue;
                        }
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        mts.addStatOptionsAndBroadcast(chr, MobStat.Stun, o1);
                    }
                }
                break;
            case ADV_EARTH_BREAK_SHOCK_INIT:
                slv = (byte) chr.getSkill(ADV_EARTH_BREAK).getCurrentLevel();
                SkillInfo fci = SkillData.getSkillInfoById(ADV_EARTH_BREAK);
                AffectedArea aa = AffectedArea.getPassiveAA(chr, ADV_EARTH_BREAK, slv);
                aa.setPosition(chr.getPosition());
                aa.setSkillID(ADV_EARTH_BREAK);
                aa.setRect(aa.getPosition().getRectAround(fci.getRects().get(0)));
                aa.setDuration((fci.getValue(v, slv) * 1000) * (SkillChangeConstants.PRE_PORT_CUSTOMS ? SkillChangeConstants.ADVANCED_TILES_DURATION_MULTIPLIER : 1)); // v265: the data's v sec
                chr.getField().spawnAffectedArea(aa);
                break;
            case CHRONO_BREAK:
                o4.nOption = si.getValue(y, slv); // Speed
                o4.rOption = skillID;
                o4.tOption = CHRONO_BREAK_SLOW_SEC;

                o5.nOption = 1; // Speed
                o5.rOption = CHRONO_BREAK_FREEZE_MTS;
                o5.tOption = si.getValue(w, slv);

                Map<Integer, Long> mobsHit = new HashMap<>();
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = mai.mob;
                    if (mob == null) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    mts.addMobSkillOptionsAndBroadCast(MobStat.Speed, o4);
                    mts.addMobSkillOptionsAndBroadCast(MobStat.Freeze, o5);
                    mobsHit.put(mai.mobId, ((long) ((si.getValue(q2, slv) * mai.totalDamage) / 100D)));
                }

                o1.nOption = si.getValue(indieBooster, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieBooster, o1);
                o2.nOption = si.getValue(indiePMdR, slv);
                o2.rOption = skillID;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePMdR, o2);
                o3.nOption = 0; // TODO si.getValue(indieCooltimeReduce, slv);
                o3.rOption = skillID;
                o3.tOption = si.getValue(time, slv);
                o3.oOption = mobsHit;
                tsm.putCharacterStatValue(IndieCooltimeReduce, o3);
                tsm.sendSetStatPacket();
                break;
            case SHADOW_FLASH_ALPHA_TILE:
            case SHADOW_FLASH_BETA_TILE:
                si = SkillData.getSkillInfoById(SHADOW_FLASH_ALPHA_TILE);
                slv = (byte) chr.getSkillLevel(SHADOW_FLASH_ALPHA_TILE);
                aa = AffectedArea.getPassiveAA(chr, attackInfo.skillId, slv);
                aa.setPosition(chr.getPosition());
                aa.setRect(aa.getPosition().getRectAround(si.getFirstRect()));
                aa.setDuration(si.getValue(cooltime, slv) * 1000);
                chr.getField().spawnAffectedArea(aa);
                chr.addSkillCoolTime(attackInfo.skillId, si.getValue(cooltime, slv) * 1000);
                break;
            case SHADOW_FLASH_ALPHA_ATTACK:
            case SHADOW_FLASH_BETA_ATTACK:
                int tileSkill = attackInfo.skillId == SHADOW_FLASH_ALPHA_ATTACK ? SHADOW_FLASH_ALPHA_TILE : SHADOW_FLASH_BETA_TILE;
                AffectedArea shadowFlashAA = chr.getField().getAffectedAreas().stream()
                        .filter(sfaa -> sfaa.getSkillID() == tileSkill && sfaa.getOwner() == chr)
                        .findFirst()
                        .orElse(null);
                if (shadowFlashAA == null) {
                    return;
                }
                chr.getField().removeLife(shadowFlashAA);
                break;
            case TWIN_BLADES_OF_TIME_START_2:
            case TWIN_BLADES_OF_TIME_START_3:
            case TWIN_BLADES_OF_TIME_START_4:
            case TWIN_BLADES_OF_TIME_START_5:
            case TWIN_BLADES_OF_TIME_START_6:
            case TWIN_BLADES_OF_TIME_START_7:
                chr.addSkillCoolTime(skillID, 100000);
                chr.write(UserLocal.skillCooltimeSetM(DOUBLE_TIME, 0));
                break;
            case SHADOW_RAIN:
            case HEXA_SHADOW_RAIN:
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    mts.addStatOptionsAndBroadcast(chr, MobStat.Stun, o1);
                }
                break;
            case EGO_WEAPON_BETA:
                if (attackInfo.affectedAreaObjId > 0) {
                    field.removeLife(attackInfo.affectedAreaObjId);
                }
                break;
        }

        if (chr.hasSkill(DOUBLE_TIME) && attackInfo.zeroTag == 0 && attackInfo.grenadeId == 0 && attackInfo.summonID == 0) {
            doubleTime(attackInfo.skillId);
        }

        super.handleAttack(c, attackInfo);
    }

    /**
     * Time Piece: every z assist hits leave v Traces of Time (at most x on the map, a new set at most every u2 sec,
     * each kept for time ms); a hit by the main Zero sends every waiting trace as a Time Crystal (SecondAtom 52,
     * which flies to the boss first and attacks by itself).
     */
    private void timePiece(boolean assist) {
        SkillInfo si = SkillData.getSkillInfoById(TIME_PIECE);
        int slv = chr.getSkillLevel(TIME_PIECE);
        if (si == null || slv <= 0 || si.getSecondAtomInfos().isEmpty()) {
            return;
        }
        long now = System.currentTimeMillis();
        synchronized (timeTraces) {
            timeTraces.removeIf(t -> (long) t[1] <= now);
            if (assist) {
                if (++timePieceHits < Math.max(1, si.getValue(z, slv)) || now < timePieceNextTrace) {
                    return;
                }
                timePieceHits = 0;
                timePieceNextTrace = now + si.getValue(u2, slv) * 1000L;
                int count = si.getValue(v, slv) + chr.getSkillStatValue(v, HEXA_SPIN_DRIVER);
                int cap = si.getValue(x, slv) + chr.getSkillStatValue(x, HEXA_SPIN_DRIVER);
                int life = si.getValue(time, slv) > 0 ? si.getValue(time, slv) : 20000;
                for (int i = 0; i < count && timeTraces.size() < cap; i++) {
                    timeTraces.add(new Object[]{chr.getPosition().deepCopy(), now + life});
                }
                return;
            }
            if (timeTraces.isEmpty()) {
                return;
            }
            var sai = si.getSecondAtomInfos().get(0);
            List<net.swordie.ms.client.character.skills.atom.secondatom.SecondAtom> atoms = new ArrayList<>();
            for (Object[] t : timeTraces) {
                var sa = net.swordie.ms.client.character.skills.atom.secondatom.SecondAtom.createFromInfo(chr, TIME_PIECE, sai);
                sa.setPosition((net.swordie.ms.util.Position) t[0]);
                atoms.add(sa);
            }
            timeTraces.clear();
            chr.spawnSecondAtoms(atoms);
        }
    }

    private void egoWeaponAlpha(TemporaryStatManager tsm) {
        Option o = new Option(1, EGO_WEAPON_ALPHA, EGO_WEAPON_ALPHA_SEC);
        o.xOption = 1;
        o.yOption = 1;
        tsm.putCharacterStatValue(EgoWeaponAlpha, o);
        tsm.sendSetStatPacket();
    }

    private void egoWeaponBeta() {
        chr.write(UserLocal.egoWeapon(EGO_WEAPON_BETA, chr.getPosition()));
    }

    public void zeroTag() {
        // the tag's 3 s cooldown is kept here only: the client counts it itself and GMS sends no cooldown packet for it
        chr.getSkillCoolTimes().put(DUAL_COMBAT, System.currentTimeMillis() + 3000);
        chr.swapZeroState();
        chr.getField().broadcastPacket(UserRemote.zeroTag(chr, chr.isZeroBeta()), chr);
    }

    private void doubleTime(int skillID) {
        List<Integer> cooltimeSkills = new ArrayList<>();
        if (isBetaSkill(skillID)) {
            cooltimeSkills = betaSkills().stream().filter(s -> chr.hasSkillOnCooldown(s) && getOriginalByStageSkill(s) != 0 && getOriginalByStageSkill(s) != getOriginalByStageSkill(skillID)).collect(Collectors.toList());
        } else if (isAlphaSkill(skillID)) {
            cooltimeSkills = alphaSkills().stream().filter(s -> chr.hasSkillOnCooldown(s) && getOriginalByStageSkill(s) != 0 && getOriginalByStageSkill(s) != getOriginalByStageSkill(skillID)).collect(Collectors.toList());
        }

        SkillInfo si = SkillData.getSkillInfoById(DOUBLE_TIME);
        int cooltimeR = si.getValue(SkillStat.x, 1); // 4000

        SkillInfo ssi = SkillData.getSkillInfoById(skillID);
        if (ssi != null) {
            int linkSkill = getOriginalByStageSkill(ssi.getAssistSkillLink());
            if (linkSkill != 0 && chr.hasSkillOnCooldown(linkSkill)) {
                cooltimeSkills.add(linkSkill);
            }
        }

        if (cooltimeSkills.size() > 0) {
            chr.reduceSkillCoolTime(cooltimeSkills, cooltimeR);
        }
    }

    private void applyDivineLeerOnMob(AttackInfo ai, int skillID) {
        Skill skill = chr.getSkill(DIVINE_LEER);
        if (skill == null) {
            return;
        }
        int slv = skill.getCurrentLevel();
        SkillInfo si = SkillData.getSkillInfoById(DIVINE_LEER);
        for (MobAttackInfo mai : ai.mobAttackInfo) {
            if (Util.succeedProp(si.getValue(prop, slv))) {
                Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                if (mob == null) {
                    continue;
                }
                mob.getTemporaryStat().createAndAddBurnedInfo(chr, skill);
            }
        }
    }

    private void applyCriticalBindOnMob(AttackInfo ai) {
        Skill skill = chr.getSkill(CRITICAL_BIND);
        if (skill == null) {
            return;
        }

        int slv = skill.getCurrentLevel();
        SkillInfo si = SkillData.getSkillInfoById(CRITICAL_BIND);
        var tOpt = si.getValue(time, slv);
        Option o = new Option(1, CRITICAL_BIND, tOpt);
        o.mobStunSkillCooldown = si.getValue(v, slv);
        for (MobAttackInfo mai : ai.mobAttackInfo) {
            if (Util.succeedProp(si.getValue(prop, slv))) {
                Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                if (mob == null) {
                    continue;
                }
                MobTemporaryStat mts = mob.getTemporaryStat();
                mts.addStatOptionsAndBroadcast(chr, MobStat.Freeze, o);
            }
        }
    }

    private void applyArmorSplitOnMob(AttackInfo ai) {
        Skill skill = chr.getSkill(ARMOR_SPLIT);
        if (skill == null) {
            return;
        }
        Option o = new Option();
        int slv = skill.getCurrentLevel();
        SkillInfo si = SkillData.getSkillInfoById(ARMOR_SPLIT);
        int amount = 1;
        o.tOption = si.getValue(time, slv);
        o.rOption = skill.getSkillId();
        for (MobAttackInfo mai : ai.mobAttackInfo) {
            if (Util.succeedProp(si.getValue(prop, slv))) {
                Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                if (mob == null) {
                    continue;
                }
                MobTemporaryStat mts = mob.getTemporaryStat();

                if (mts.hasCurrentMobStat(MobStat.MultiPMDR)) {
                    amount = mts.getCurrentOptionsByMobStat(MobStat.MultiPMDR).cOption;
                    if (amount < si.getValue(x, slv)) {
                        amount++;
                    }
                }
                o.nOption = si.getValue(y, slv) * amount;
                o.cOption = amount;
                mts.addStatOptionsAndBroadcast(chr, MobStat.MultiPMDR, o);
            }
        }
    }

    private void incrementDoubleTimeAlpha(int skillID) {
        if (chr.hasSkill(DOUBLE_TIME)) {
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            Option o = new Option();
            Option o1 = new Option();
            SkillInfo si = SkillData.getSkillInfoById(DOUBLE_TIME_ALPHA);
            int amount = 1;
            if (tsm.hasStat(TimeFastABuff)) {
                if (doubleTimePrevSkill == skillID) {
                    return;
                }
                amount = tsm.getOption(TimeFastABuff).nOption;
                if (amount < si.getMaxLevel()) {
                    amount++;
                }
            }
            doubleTimePrevSkill = skillID;
            o.nOption = amount;
            o.rOption = DOUBLE_TIME_ALPHA;
            o.tOption = si.getValue(time, amount);
            tsm.putCharacterStatValue(TimeFastABuff, o);
            if (tsm.hasStat(BlessingVSkill) && chr.hasSkill(Job.TRANSCENDENCE_BLESSING)) {
                SkillInfo blessingSI = SkillData.getSkillInfoById(Job.TRANSCENDENCE_BLESSING);
                int slv = chr.getSkillLevel(Job.TRANSCENDENCE_BLESSING);
                o1.nOption = blessingSI.getValue(x, slv);
                o1.rOption = TRANSCENDENCE_BLESSING_3; // Used for NextAttackEnhance
                o1.tOption = BLESSING_NEXT_ATTACK_SEC;
                tsm.putCharacterStatValue(NextAttackEnhance, o1);
            }
            tsm.sendSetStatPacket();
            chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.skillUse(DOUBLE_TIME_ALPHA, chr.getLevel(), chr.getSkillLevel(DOUBLE_TIME), 0)), chr);
        }
    }

    private void incrementDoubleTimeBeta(int skillID) {
        if (chr.hasSkill(DOUBLE_TIME)) {
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            Option o = new Option();
            Option o1 = new Option();
            Option o2 = new Option();
            SkillInfo si = SkillData.getSkillInfoById(DOUBLE_TIME_BETA);
            int amount = 1;
            if (tsm.hasStat(TimeFastBBuff)) {
                if (doubleTimePrevSkill == skillID) {
                    return;
                }
                amount = tsm.getOption(TimeFastBBuff).nOption;
                if (amount < si.getMaxLevel()) {
                    amount++;
                }
            }
            doubleTimePrevSkill = skillID;
            o.nOption = amount;
            o.rOption = DOUBLE_TIME_BETA;
            o.tOption = si.getValue(time, amount);
            tsm.putCharacterStatValue(TimeFastBBuff, o);
            if (o.nOption == si.getMaxLevel()) {
                o1.nOption = -1;
                o1.rOption = DOUBLE_TIME_BETA;
                o1.tOption = o.tOption;
                tsm.putCharacterStatValue(IndieBooster, o1);
            }
            if (tsm.hasStat(BlessingVSkill) && chr.hasSkill(Job.TRANSCENDENCE_BLESSING)) {
                SkillInfo blessingSI = SkillData.getSkillInfoById(Job.TRANSCENDENCE_BLESSING);
                int slv = chr.getSkillLevel(Job.TRANSCENDENCE_BLESSING);
                o2.nOption = blessingSI.getValue(x, slv);
                o2.rOption = TRANSCENDENCE_BLESSING_3; // Used for NextAttackEnhance
                o2.tOption = BLESSING_NEXT_ATTACK_SEC;
                tsm.putCharacterStatValue(NextAttackEnhance, o2);
            }
            tsm.sendSetStatPacket();
            chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.skillUse(DOUBLE_TIME_BETA, chr.getLevel(), chr.getSkillLevel(DOUBLE_TIME), 0)), chr);
        }
    }


    // Skill related methods -------------------------------------------------------------------------------------------

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillID, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        super.handleSkill(chr, tsm, skillID, slv, inPacket, skillUseInfo);
        Skill skill = chr.getSkill(skillID);
        SkillInfo si = null;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skillID);
        }
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();
        Option o5 = new Option();
        Option o6 = new Option();
        Option o7 = new Option();
        Field field = chr.getField();
        switch (skillID) {
            case THROWING_WEAPON:
            case ADV_THROWING_WEAPON:
            case HEXA_THROWING_WEAPON:
                Summon summon = Summon.getSummonBy(chr, skillID, slv);
                summon.setFlyMob(true);
                summon.setMoveAbility(MoveAbility.FixVMove);
                chr.getField().spawnSummonAndRemoveOld(summon);
                break;
            case TIME_DISTORTION:
                AffectedArea aa = AffectedArea.getPassiveAA(chr, skillID, slv);
                aa.setPosition(chr.getPosition());
                aa.setRect(aa.getPosition().getRectAround(si.getRects().get(0)));
                aa.setDelay((short) 5);
                chr.getField().spawnAffectedArea(aa);
                break;
            case DIVINE_FORCE:
                o1.nOption = slv;
                o1.rOption = skillID;
                o1.xOption = chr.getId();
                o1.bOption = 0; // Owner
                tsm.putCharacterStatValue(DivineForce, o1);

                o2.rOption = skillID;
                o2.nOption = si.getValue(indieAsrR, slv);
                tsm.putCharacterStatValue(IndieAsrR, o2); //Indie
                o3.rOption = skillID;
                o3.nOption = si.getValue(indieMad, slv);
                tsm.putCharacterStatValue(IndieMAD, o3); //Indie
                o4.rOption = skillID;
                o4.nOption = si.getValue(indiePad, slv);
                tsm.putCharacterStatValue(IndiePAD, o4); //Indie
                o5.rOption = skillID;
                o5.nOption = si.getValue(indiePdd, slv);
                tsm.putCharacterStatValue(IndieDEF, o5); //Indie
                o6.rOption = skillID;
                o6.nOption = si.getValue(indieTerR, slv);
                tsm.putCharacterStatValue(IndieTerR, o6); //Indie
                // v265 Divine Aura also carries what Divine Speed gave
                tsm.putCharacterStatValue(IndieBooster, new Option(si.getValue(indieBooster, slv), skillID, 0));
                tsm.putCharacterStatValue(IndieSpeed, new Option(si.getValue(indieSpeed, slv), skillID, 0));
                tsm.putCharacterStatValue(IndieJump, new Option(si.getValue(indieJump, slv), skillID, 0));
                tsm.putCharacterStatValue(IndieEVA, new Option(si.getValue(indieEva, slv), skillID, 0));
                tsm.putCharacterStatValue(IndieACC, new Option(si.getValue(indieAcc, slv), skillID, 0));
                break;
            case DIVINE_SPEED:
                o1.nOption = slv;
                o1.rOption = skillID;
                o1.xOption = chr.getId();
                o1.bOption = 0; // Owner
                tsm.putCharacterStatValue(DivineSpeed, o1);

                o2.rOption = skillID;
                o2.nOption = SkillChangeConstants.DIVINE_SPEED_BOOSTER_VALUE; // [Zero] Custom Skill Change
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieBooster, o2); //Indie
                o3.rOption = skillID;
                o3.nOption = si.getValue(indieJump, slv);
                o3.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieJump, o3); //Indie
                o4.rOption = skillID;
                o4.nOption = si.getValue(indieSpeed, slv);
                o4.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieSpeed, o4); //Indie
                break;

            case TIME_HOLDING:
                Effect effect = Effect.skillUse(skillID, chr.getLevel(), slv, 0);
                chr.write(UserPacket.effect(effect));
                field.broadcastPacket(UserRemote.effect(chr.getId(), effect), chr);

                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieNotDamaged, o1, true);
                o2.nOption = 1;
                o2.rOption = skillID;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieInfiniteMDF, o2);
                tsm.sendSetStatPacket();

                if (chr.getLevel() >= 200) {
                    o3.nOption = si.getValue(y, slv);
                    o3.rOption = TIME_HOLDING_2;
                    o3.tOption = si.getValue(x, slv);
                    tsm.putCharacterStatValue(IndieDamR, o3);
                }

                for (var skillId : chr.getSkillCoolTimes().keySet()) {
                    if (!SkillConstants.isNoCooltimeResetSkill(skillId)) {
                        chr.resetSkillCoolTime(skillId);
                    }
                }
                break;
            case TRANSCENDENTS_WILL: // Zero's Hero's Will: clears debuffs, immune for 3 sec
                tsm.removeAllDebuffs();
                tsm.putCharacterStatValue(AsrR, new Option(100, skillID, 3));
                break;
            case REWIND:
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(ReviveOnce, o1, true);
                break;
            case FOCUSED_TIME:
                o1.rOption = skillID;
                o1.nOption = si.getValue(x, slv);
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePADR, o1); //Indie
                o2.rOption = skillID;
                o2.nOption = o1.nOption;
                o2.tOption = o1.tOption;
                tsm.putCharacterStatValue(IndieMADR, o2); //Indie
                break;
            case TWIN_BLADES_OF_TIME_START:
                o1.rOption = skillID;
                o1.nOption = 1;
                o1.tOption = TWIN_BLADES_KEYDOWN_SEC;
                tsm.putCharacterStatValue(IndieKeyDownTime, o1, true);
                tsm.putCharacterStatValue(IndieNotDamaged, o1, true);
                break;
            case CHRONO_BREAK:
                o1.nOption = si.getValue(y, slv);
                o1.rOption = skillID;
                o1.tOption = CHRONO_BREAK_SLOW_SEC;

                chr.write(UserLocal.skillCooltimeSetM(-skillID, 0));
                chr.write(UserLocal.skillCooltimeSetM(-skillID, 500));

                int size = inPacket.decodeByte();
                for (int i = 0; i < size; i++) {
                    int mobId = inPacket.decodeInt();

                    Life life = field.getLifeByObjectID(mobId);
                    if (!(life instanceof Mob)) {
                        continue;
                    }
                    Mob mob = (Mob) life;
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    mts.addMobSkillOptionsAndBroadCast(MobStat.Speed, o1);
                }
                break;
            case EGO_WEAPON_BETA:
                si = SkillData.getSkillInfoById(EGO_WEAPON_ALPHA);
                var aaSize = inPacket.decodeInt(); // size
                var delay = Util.getRandom(3, 9);
                for (int i = 0; i < aaSize; i++) {
                    aa = AffectedArea.getPassiveAA(chr, skillID, slv);
                    aa.setPosition(inPacket.decodePositionInt());
                    aa.setRect(aa.getPosition().getRectAround(si.getFirstRect()));
                    aa.setDuration(EGO_WEAPON_BETA_AREA_MS);
                    aa.setDelay((short) delay);
                    delay += Util.getRandom(9, 15);
                    field.spawnAffectedArea(aa);
                }
                Effect eff = Effect.skillUse(skillID, chr.getLevel(), slv, 0);
                chr.write(UserPacket.effect(eff));
                chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), eff), chr);
                break;
        }
        tsm.sendSetStatPacket();
    }

    @Override
    public BypassCooldownCheckType canBypassCooldownCheck(int skillId, AttackInfo attackInfo, SkillUseInfo sui, SkillUseSource source) {
        if (skillId == EGO_WEAPON_BETA) {
            chr.setSkillCooldown(skillId, (byte) sui.slv, attackInfo, sui, source); // hardcode-ok
            return BypassCooldownCheckType.BypassCheckAndCooldown;
        }
        return super.canBypassCooldownCheck(skillId, attackInfo, sui, source);
    }

    @Override
    public void handleSkillRemove(Char chr, int skillID, Map<CharacterTemporaryStat, Option> removedOptions) {
        if (skillID == TWIN_BLADES_OF_TIME_START) {
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            tsm.removeStatsBySkill(skillID);
        }
        super.handleSkillRemove(chr, skillID, removedOptions);
    }

    @Override
    public void handleRemoveCTS(CharacterTemporaryStat cts, Option o) {
        Field field = chr.getField();
        if (o.rOption == CHRONO_BREAK && o.oOption instanceof Map) {
            int hits = 15;
            for (Map.Entry<Integer, Long> entry : ((Map<Integer, Long>) o.oOption).entrySet()) {
                int mobId = entry.getKey();
                long damage = entry.getValue();
                Life life = field.getLifeByObjectID(mobId);
                if ((!(life instanceof Mob))) {
                    continue;
                }

                ((Mob) life).damageBySkill(chr, CHRONO_BREAK_AFTER_HIT, chr.getSkillLevel(CHRONO_BREAK), hits, damage * hits);
            }

        }

        super.handleRemoveCTS(cts, o);
    }


    // Hit related methods ---------------------------------------------------------------------------------------------

    @Override
    public void handleHit(Client c, InPacket inPacket, HitInfo hitInfo) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Skill immuneBarrier = chr.getSkill(IMMUNE_BARRIER);
        if (immuneBarrier == null) {
            super.handleHit(c, inPacket, hitInfo);
            return;
        }
        int slv = immuneBarrier.getCurrentLevel();
        SkillInfo si = SkillData.getSkillInfoById(IMMUNE_BARRIER);
        if (Util.succeedProp(si.getValue(prop, slv))) {
            Option o = new Option(IMMUNE_BARRIER, slv);
            int max = (int) (chr.getStat(Stat.mhp) * (si.getValue(x, slv) / 100D));
            o.nOption = max;
            o.xOption = max;
            chr.getTemporaryStatManager().putCharacterStatValue(ImmuneBarrier, o);
        }
        if (tsm.hasStat(ImmuneBarrier)) {
            Option o = tsm.getOption(ImmuneBarrier);
            int maxSoakDamage = o.nOption;
            int newDamage = Math.max(hitInfo.hpDamage - maxSoakDamage, 0);
            o.nOption = maxSoakDamage - (hitInfo.hpDamage - newDamage); // update soak value
            hitInfo.hpDamage = newDamage;
            o.tOption = si.getValue(time, slv); //added duration
            tsm.putCharacterStatValue(ImmuneBarrier, o);
            tsm.sendSetStatPacket();
        }
        super.handleHit(c, inPacket, hitInfo);
    }

    public void reviveByRewind() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        chr.heal(chr.getMaxHP(), false, true);
        tsm.removeStatsBySkill(REWIND);
        tsm.sendResetStatPacket();
        // invincible for y seconds after the rewind
        SkillInfo rsi = SkillData.getSkillInfoById(REWIND);
        int invincible = rsi == null ? 0 : rsi.getValue(y, 1);
        if (invincible > 0) {
            tsm.putCharacterStatValue(NotDamaged, new Option(1, REWIND, invincible));
            tsm.sendSetStatPacket();
        }
        chr.write(UserPacket.effect(Effect.skillSpecial(REWIND)));
        chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.skillSpecial(REWIND)));
    }

    /** Every monster Zero defeats gives 1 WP, sent each time (GMS: ZERO_WP after each kill). */
    @Override
    public void handleMobDeath(net.swordie.ms.client.character.skills.info.AttackInfo attackInfo,
                               net.swordie.ms.life.mob.Mob mob) {
        super.handleMobDeath(attackInfo, mob);
        chr.addWpCoin(1);
    }

    @Override
    public void onWarp(Field oldField, Field newField) {
        super.onWarp(oldField, newField);
        chr.updateWpCoin();
    }
}
