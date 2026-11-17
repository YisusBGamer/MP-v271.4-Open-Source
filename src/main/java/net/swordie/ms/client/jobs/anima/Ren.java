package net.swordie.ms.client.jobs.anima;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.atom.secondatom.SecondAtom;
import net.swordie.ms.client.character.skills.debuff.DebuffUtil;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.MobAttackInfo;
import net.swordie.ms.client.character.skills.info.SecondAtomInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.shootobject.ShootObjectSkillInfo;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.JobConstants.JobEnum;
import net.swordie.ms.enums.AssistType;
import net.swordie.ms.enums.MoveAbility;
import net.swordie.ms.life.AffectedArea;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.ArrayList;
import java.util.List;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Ren (Anima warrior, sword 1215xxx). Job ids 16002, 16100-16112.
 * Mechanics from the v265.3 reference tree's Ren: Riotous Heart charges, Serpent's Blessing, Raining Blossoms,
 * Thousand Blossom Flurry, Soul Immeasurable, Blade of the Unbound Heart and Heartbound Verse.
 */
public class Ren extends Job {

    // Beginner
    public static final int SPIRIT_AFFINITY = 160020000;
    public static final int GROUNDED_BODY = 160020001;
    public static final int EXCLUSIVE_SPELL = 160021005;
    public static final int RETURN_TO_SERPENT_HEARTWATERS = 160021074;
    public static final int SHAPESHIFT = 160021075;

    // 1st - 4th job
    public static final int PLUM_BLOSSOM_SWORD_SLICE = 161001000;
    public static final int BLOSSOMING_BLADE = 161101005; // booster
    private static final int BLOSSOM_ICON_SEC = 2; // hardcode-ok: no Skill.wz stat
    public static final int RAINING_BLOSSOMS = 161110001;
    public static final int RAINING_BLOSSOMS_ATOM = 161111002;
    public static final int SERPENT_BLESSING = 161110005;
    // Contributed by Arixal (from GitLab).
    public static final int BURROWING_EARTH = 161110007; // the one a Ren learns; it has no cooltime of its own
    public static final int BURROWING_EARTH_EFF = 161111008; // hidden, nobody learns it; holds the 20s cooltime
    public static final int SERPENT_FANG = 161120006; // final attack
    public static final int RAVENOUS_SPIRIT = 161120003;
    public static final int RAVENOUS_SPIRIT_EFF = 161121004; // hidden, what a Wish Unending press attacks as
    public static final int PLUM_BLOSSOM_SWORD_STORM = 161121000;
    public static final int RIOTOUS_HEART = 161121005;
    public static final int UNBOWED_BLADE = 161121008;
    public static final int ANIMA_WARRIOR = 161121009;
    public static final int ANIMA_HERO_WILL = 161121010;
    public static final int HEARTS_UNITED = 161121014;
    public static final int SUBLIMATION = 161121041;
    public static final int PLUM_BRANCH_SNARE = 161121042;
    public static final int YEARS_UNCOUNTED = 161121043;

    // V skills
    public static final int THOUSAND_BLOSSOM_FLURRY = 400011147;
    public static final int THOUSAND_BLOSSOM_FLURRY_EX = 400011148;
    public static final int THOUSAND_BLOSSOM_FLURRY_ATOM = 400011149;
    public static final int SOUL_IMMEASURABLE = 400011150;
    public static final int SOUL_IMMEASURABLE_ATT = 400011151;
    public static final int SOUL_IMMEASURABLE_SPE = 400011152;
    public static final int DANCING_ANNIHILATION = 400011153;
    public static final int BLADE_OF_THE_UNBOUND_HEART = 400011154;
    public static final int BLADE_OF_THE_UNBOUND_HEART_CORE = 400011157; // the V core's skill (Etc.wz VCore 10010057)

    // HEXA
    public static final int HEXA_RAINING_BLOSSOMS = 161140007;
    public static final int HEXA_RAINING_BLOSSOMS_ATOM = 161141008;
    public static final int HEXA_SERPENT_FANG = 161140010;
    public static final int HEXA_BURROWING_EARTH = 161141002;
    public static final int HEXA_RAVENOUS_SPIRIT = 161141003;
    public static final int HEXA_YEARS_UNCOUNTED = 161141004;
    public static final int HEXA_RIOTOUS_HEART = 161141009;
    public static final int HEXA_HEARTS_UNITED = 161141013;
    public static final int HEARTBOUND_VERSE_FALLING_FLOWER = 161141502;
    public static final int HEARTBOUND_VERSE_CLIMBING_SERPENT = 161141503;
    public static final int HEARTBOUND_VERSE = 161141506;
    public static final int HEXA_PLUM_BLOSSOM_SWORD_STORM = 161141000; // HEXA Plum Blossom Sword: Storm
    public static final int PLUM_BLOSSOM_INITIATE_QUEST = 36850;
    public static final int PLUM_BLOSSOM_INITIATE_MEDAL = 1143454;

    private static final int[] addedSkills = new int[]{
            RETURN_TO_SERPENT_HEARTWATERS,
            SPIRIT_AFFINITY,
            SHAPESHIFT,
            EXCLUSIVE_SPELL,
            GROUNDED_BODY,
    };

    /** Thousand Blossom Flurry hit counts at which the petal barrage goes off */
    private static final int[] FLURRY_BURSTS = {30, 70, 125};

    private int soulImmeasurableStack;

    public Ren(Char chr) {
        super(chr);
        if (chr.getId() != 0 && isHandlerOfJob(chr.getJob())) {
            for (int id : addedSkills) {
                if (!chr.hasSkill(id)) {
                    Skill skill = SkillData.getSkillDeepCopyById(id);
                    if (skill != null) {
                        skill.setCurrentLevel(skill.getMasterLevel());
                        chr.addSkill(skill);
                    }
                }
            }
        }
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isRen(id);
    }

    // region Attack ---------------------------------------------------------------------------------------------------

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int skillId = attackInfo.skillId;
        int slv = chr.getSkillLevel(skillId);
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        boolean hasHitMobs = !attackInfo.mobAttackInfo.isEmpty();

        ensureRiotousHeart();
        confirmChainStep(skillId);
        if (hasHitMobs && !isProcSkill(skillId)) {
            serpentBlessing();
            rainingBlossoms();
            thousandBlossomFlurry(attackInfo);
            soulImmeasurable(attackInfo);
        }

        Option o1 = new Option();
        switch (skillId) {
            case PLUM_BRANCH_SNARE:
                if (si != null && hasHitMobs) {
                    o1.nOption = 1;
                    o1.rOption = skillId;
                    o1.tOption = si.getValue(time, slv);
                    DebuffUtil.applyDebuffOnMobs(chr, MobStat.Stun, o1, attackInfo.getMobIds());
                }
                break;
            case YEARS_UNCOUNTED:
            case HEXA_YEARS_UNCOUNTED:
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob != null && mob.getHp() > 0) {
                        mob.getTemporaryStat().createAndAddBurnedInfo(chr, skillId, slv);
                    }
                }
                break;
            case PLUM_BLOSSOM_SWORD_STORM:
            case HEXA_PLUM_BLOSSOM_SWORD_STORM:
                if (si != null) {
                    o1.nOption = 1;
                    o1.rOption = skillId;
                    o1.tOption = si.getValue(time, slv);
                    o1.xOption = 1;
                    tsm.putCharacterStatValue(KeyDownMoving, o1);
                    tsm.sendSetStatPacket();
                }
                break;
            case UNBOWED_BLADE:
                tsm.putCharacterStatValue(IndieNotDamaged, new Option(1, skillId, si.getValue(time, slv)), true);
                tsm.sendSetStatPacket();
                break;
        }
        super.handleAttack(c, attackInfo);
    }

    /**
     * Contributed by Arixal (from GitLab).
     *
     * Wish Unending: after each step the client refuses the next one until the server confirms this one. Blade of the
     * Unbound Heart is the last step and has nothing after it to hold, so it isn't confirmed.
     */
    void confirmChainStep(int skillId) {
        switch (skillId) {
            case BURROWING_EARTH_EFF:
            case RAVENOUS_SPIRIT_EFF:
            case YEARS_UNCOUNTED:
            case HEXA_BURROWING_EARTH:
            case HEXA_RAVENOUS_SPIRIT:
            case HEXA_YEARS_UNCOUNTED:
                chr.write(WvsContext.skillChainResult(skillId, true));
                break;
        }
    }

    /** Attacks made by Ren's own procs, which don't trigger the procs again. */
    private static boolean isProcSkill(int skillId) {
        return skillId == RAINING_BLOSSOMS_ATOM || skillId == HEXA_RAINING_BLOSSOMS_ATOM
                || skillId == THOUSAND_BLOSSOM_FLURRY_EX || skillId == THOUSAND_BLOSSOM_FLURRY_ATOM
                || skillId == SOUL_IMMEASURABLE_ATT || skillId == SOUL_IMMEASURABLE_SPE;
    }

    @Override
    public int getFinalAttackSkill() {
        if (chr.hasSkill(HEXA_SERPENT_FANG)) {
            return HEXA_SERPENT_FANG;
        }
        if (chr.hasSkill(SERPENT_FANG)) {
            return SERPENT_FANG;
        }
        return super.getFinalAttackSkill();
    }

    /** First Imugi Spirit Sword: Serpent's Blessing: prop% on a hit, +x status resistance for time (ms). */
    private void serpentBlessing() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!chr.hasSkill(SERPENT_BLESSING) || tsm.hasStatBySkillId(SERPENT_BLESSING)) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(SERPENT_BLESSING);
        int slv = chr.getSkillLevel(SERPENT_BLESSING);
        if (!Util.succeedProp(si.getValue(prop, slv))) {
            return;
        }
        int sec = si.getValue(time, slv) / 1000;
        tsm.putCharacterStatValue(IndieAsrR, new Option(si.getValue(x, slv), SERPENT_BLESSING, sec), true);
        tsm.putCharacterStatValue(IndieFloating, new Option(1, SERPENT_BLESSING, sec), true); // T: IndieCheckTimeByClient
        tsm.sendSetStatPacket();
    }

    /** Second Plum Blossom Sword: Raining Blossoms: prop% on a hit, petals fly at enemies around Ren. */
    private void rainingBlossoms() {
        if (!chr.hasSkill(RAINING_BLOSSOMS)) {
            return;
        }
        boolean hexa = chr.hasSkill(HEXA_RAINING_BLOSSOMS);
        int originalSkillId = hexa ? HEXA_RAINING_BLOSSOMS : RAINING_BLOSSOMS;
        if (!Util.succeedProp(chr.getSkillStatValue(prop, originalSkillId))) {
            return;
        }
        int atomSkillId = hexa ? HEXA_RAINING_BLOSSOMS_ATOM : RAINING_BLOSSOMS_ATOM;
        SkillInfo si = SkillData.getSkillInfoById(atomSkillId);
        if (si == null || si.getSecondAtomInfos().isEmpty()) {
            return;
        }
        int bullets = Math.max(1, si.getValue(bulletCount, chr.getSkillLevel(originalSkillId)));
        List<Mob> mobs = mobsAround(500);
        if (mobs.isEmpty()) {
            return;
        }
        SecondAtomInfo sai = si.getSecondAtomInfos().get(0);
        int dataIndex = hexa ? (Util.succeedProp(50) ? 108 : 118) : (Util.succeedProp(50) ? 105 : 117); // hardcode-ok: one of two petal shapes
        List<SecondAtom> atoms = new ArrayList<>();
        for (int i = 0; i < bullets; i++) {
            Mob mob = Util.getRandomFromCollection(mobs);
            atoms.add(new SecondAtom(chr.getNewSecondAtomKey(), dataIndex, i, chr.getId(), mob.getObjectId(),
                    atomSkillId, sai.getCreateDelay(), sai.getEnableDelay(), sai.getRotate(), sai.getExpire(), 360,
                    sai.getAttackableCount(), chr.getPosition(), sai.getCustomList()));
        }
        chr.spawnSecondAtoms(atoms);
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        tsm.putCharacterStatValue(IndieBuffIcon, new Option(1, originalSkillId, BLOSSOM_ICON_SEC), true);
        tsm.sendSetStatPacket();
    }

    /** Thousand Blossom Flurry: hits feed the flurry; at 30/70/125 hits a petal barrage goes off. */
    private void thousandBlossomFlurry(AttackInfo attackInfo) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!tsm.hasStat(RenPlumSwordEx)) {
            return;
        }
        List<Integer> mobIds = new ArrayList<>();
        for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
            Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
            if (mob != null && mob.getHp() > 0) {
                mobIds.add(mob.getObjectId());
            }
        }
        chr.write(UserLocal.userBonusAttackRequest(THOUSAND_BLOSSOM_FLURRY_EX, mobIds));
        if (chr.getField().getSummonByChrAndSkillId(chr, THOUSAND_BLOSSOM_FLURRY) == null) {
            return;
        }
        Option o = tsm.getOption(RenPlumSwordEx);
        o.nOption++;
        o.rOption = THOUSAND_BLOSSOM_FLURRY_EX;
        o.yOption = Util.succeedProp(10) ? 2 : Util.succeedProp(30) ? 1 : 0;
        tsm.putCharacterStatValue(RenPlumSwordEx, o, true);
        tsm.sendSetStatPacket();
        for (int burst : FLURRY_BURSTS) {
            if (o.nOption == burst) {
                petalBarrage(burst - 10);
            }
        }
    }

    private void petalBarrage(int bullets) {
        SkillInfo si = SkillData.getSkillInfoById(THOUSAND_BLOSSOM_FLURRY_ATOM);
        List<Mob> mobs = mobsAround(1000);
        if (si == null || si.getSecondAtomInfos().isEmpty() || mobs.isEmpty()) {
            return;
        }
        SecondAtomInfo sai = si.getSecondAtomInfos().get(0);
        Position pos = new Position(chr.getPosition().getX() + Util.getRandom(-200, 200),
                chr.getPosition().getY() - Util.getRandom(100, 200));
        List<SecondAtom> atoms = new ArrayList<>();
        for (int i = 0; i < bullets; i++) {
            Mob mob = Util.getRandomFromCollection(mobs);
            atoms.add(new SecondAtom(chr.getNewSecondAtomKey(), sai.getDataIndex(), i, chr.getId(), mob.getObjectId(),
                    THOUSAND_BLOSSOM_FLURRY_ATOM, sai.getCreateDelay(), sai.getEnableDelay(), sai.getRotate(),
                    sai.getExpire(), 0, sai.getAttackableCount(), pos, sai.getCustomList()));
        }
        chr.spawnSecondAtoms(atoms);
    }

    /** Soul Immeasurable: hit enemies add stacks (up to u2); each hit asks for a bonus attack, the cap a special one. */
    private void soulImmeasurable(AttackInfo attackInfo) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!tsm.hasStat(SpiritAwakeningStack)) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(SOUL_IMMEASURABLE);
        if (si == null) {
            return;
        }
        int max = si.getValue(u2, chr.getSkillLevel(SOUL_IMMEASURABLE));
        if (soulImmeasurableStack >= max) {
            return;
        }
        soulImmeasurableStack = Math.min(max, soulImmeasurableStack + attackInfo.mobAttackInfo.size());
        Option o = tsm.getOption(SpiritAwakeningStack);
        o.nOption = soulImmeasurableStack;
        tsm.putCharacterStatValue(SpiritAwakeningStack, o, true);
        tsm.sendSetStatPacket();
        chr.write(UserLocal.userBonusAttackRequest(SOUL_IMMEASURABLE_ATT));
        if (soulImmeasurableStack >= max) {
            chr.write(UserLocal.userBonusAttackRequest(SOUL_IMMEASURABLE_SPE));
        }
    }

    private List<Mob> mobsAround(int range) {
        Rect rect = chr.getRectAround(new Rect(-range, -range, range, range));
        if (!chr.isLeft()) {
            rect = rect.horizontalFlipAround(chr.getPosition().getX());
        }
        return chr.getField().getMobsInRect(rect);
    }

    // endregion

    // region Skills ---------------------------------------------------------------------------------------------------

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        super.handleSkill(chr, tsm, skillId, slv, inPacket, skillUseInfo);
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        if (si == null) {
            return;
        }
        Field field = chr.getField();
        Option o1 = new Option();
        Option o2 = new Option();
        Summon summon;
        switch (skillId) {
            case BLOSSOMING_BLADE:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Booster, o1);
                break;
            case SUBLIMATION:
                o1.nOption = si.getValue(w, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Sublimation, o1);
                break;
            case RIOTOUS_HEART:
            case HEXA_RIOTOUS_HEART:
                o1.nOption = tsm.hasStat(RenPlumSwordForm3Stack)
                        ? Math.max(0, tsm.getOption(RenPlumSwordForm3Stack).nOption - 1) : 0;
                o1.rOption = skillId;
                tsm.putCharacterStatValue(RenPlumSwordForm3Stack, o1);
                o2.nOption = si.getValue(x, slv);
                o2.rOption = skillId;
                tsm.putCharacterStatValue(RenPlumSwordForm3Shoot, o2);
                break;
            case ANIMA_HERO_WILL:
                tsm.removeAllDebuffs();
                break;
            case THOUSAND_BLOSSOM_FLURRY:
                summon = Summon.getSummonBy(chr, skillId, slv);
                summon.setMoveAbility(MoveAbility.Stop);
                summon.setAssistType(AssistType.None);
                field.spawnSummonAndRemoveOld(summon);
                AffectedArea aa = AffectedArea.getPassiveAA(chr, skillId, slv);
                aa.setDelay((short) 8);
                field.spawnAffectedArea(aa);
                o1.nOption = 0;
                o1.rOption = THOUSAND_BLOSSOM_FLURRY_EX;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(RenPlumSwordEx, o1);
                break;
            case SOUL_IMMEASURABLE:
                soulImmeasurableStack = 0;
                o1.nOption = 2;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(SpiritAwakening, o1);
                o2.nOption = 0;
                o2.rOption = skillId;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(SpiritAwakeningStack, o2);
                break;
            case BLADE_OF_THE_UNBOUND_HEART:
                unboundHeart(si);
                break;
            case HEARTBOUND_VERSE_FALLING_FLOWER:
                summon = Summon.getSummonBy(chr, skillId, slv);
                summon.setMoveAbility(MoveAbility.Stop);
                summon.setAssistType(AssistType.ExplosionAttack);
                summon.setMoveAction((byte) 4);
                field.spawnSummonAndRemoveOld(summon);
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(ndTime, slv);
                o1.setInMillis(true);
                tsm.putCharacterStatValue(Holding, o1);
                break;
            case HEARTBOUND_VERSE_CLIMBING_SERPENT:
                tsm.removeStatsBySkill(HEARTBOUND_VERSE_FALLING_FLOWER);
                break;
        }
        tsm.sendSetStatPacket();
    }

    /** Final Imugi Spirit Sword: Blade of the Unbound Heart: the skill's sword atoms at one enemy nearby. */
    private void unboundHeart(SkillInfo si) {
        List<Mob> mobs = mobsAround(800);
        if (mobs.isEmpty()) {
            return;
        }
        Mob mob = Util.getRandomFromCollection(mobs);
        List<SecondAtom> atoms = new ArrayList<>();
        int key = 0;
        for (SecondAtomInfo sai : si.getSecondAtomInfos()) {
            atoms.add(new SecondAtom(chr.getNewSecondAtomKey(), 107, key++, chr.getId(), mob.getObjectId(),
                    BLADE_OF_THE_UNBOUND_HEART, sai.getCreateDelay(), sai.getEnableDelay(), sai.getRotate(),
                    sai.getExpire(), 0, sai.getAttackableCount(), chr.getPosition().add(sai.getPosition()),
                    sai.getCustomList()));
        }
        chr.spawnSecondAtoms(atoms);
    }

    /** Riotous Heart: each throw uses a sword charge; Hearts United brings the charges back. */
    @Override
    public void handleShootObj(Char chr, ShootObjectSkillInfo sosi) {
        int skillId = sosi.getSkillId();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (skillId == RIOTOUS_HEART || skillId == HEXA_RIOTOUS_HEART) {
            int left = tsm.hasStat(RenPlumSwordForm3Shoot) ? tsm.getOption(RenPlumSwordForm3Shoot).nOption - 1 : 0;
            if (left <= 0) {
                tsm.removeStat(RenPlumSwordForm3Shoot);
            } else {
                Option o = tsm.getOption(RenPlumSwordForm3Shoot);
                o.nOption = left;
                tsm.putCharacterStatValue(RenPlumSwordForm3Shoot, o, true);
                tsm.sendSetStatPacket();
            }
        } else if (skillId == HEARTS_UNITED || skillId == HEXA_HEARTS_UNITED) {
            if (tsm.hasStat(RenPlumSwordForm3Shoot)) {
                tsm.removeStat(RenPlumSwordForm3Shoot);
            }
            refillRiotousHeart();
        }
        super.handleShootObj(chr, sosi);
    }

    private void ensureRiotousHeart() {
        if (!chr.getTemporaryStatManager().hasStat(RenPlumSwordForm3Stack)) {
            refillRiotousHeart();
        }
    }

    private void refillRiotousHeart() {
        int skillId = chr.hasSkill(HEXA_RIOTOUS_HEART) ? HEXA_RIOTOUS_HEART
                : chr.hasSkill(RIOTOUS_HEART) ? RIOTOUS_HEART : 0;
        if (skillId == 0) {
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option();
        o.nOption = SkillData.getSkillInfoById(skillId).getValue(u, chr.getSkillLevel(skillId));
        o.rOption = skillId;
        tsm.putCharacterStatValue(RenPlumSwordForm3Stack, o);
        tsm.sendSetStatPacket();
        chr.write(WvsContext.stackSkillRequestResult(skillId, 1));
    }

    // endregion

    @Override
    public void setCharCreationStats(Char chr) {
        super.setCharCreationStats(chr);
        setFirstJobCreationStats(chr, JobEnum.REN_1.getJobId(), 25, 4, 4, 4);
    }

    @Override
    public void handleLevelUp() {
        super.handleLevelUp();
        advanceByLevel(JobEnum.REN_1.getJobId(), JobEnum.REN_2.getJobId(), JobEnum.REN_3.getJobId(),
                JobEnum.REN_4.getJobId());
        // the skipped tutorial (through the 2nd job) and the 3rd/4th job quests, as the reference tree completes them
        int level = chr.getLevel();
        // Lv. 10: the Plum Blossom Initiate medal, which its medal quest (36850) then completes on
        if (level >= 10 && !chr.getQuestManager().hasQuestCompleted(PLUM_BLOSSOM_INITIATE_QUEST)
                && !chr.hasItem(PLUM_BLOSSOM_INITIATE_MEDAL) && chr.canHold(PLUM_BLOSSOM_INITIATE_MEDAL, 1)) {
            chr.addItemToInventory(PLUM_BLOSSOM_INITIATE_MEDAL, 1);
        }
        if (level >= 30) {
            completeQuestRangeIfNeeded(36821, 36839);
        }
        if (level >= 60) {
            completeQuestsIfNeeded(36840);
        }
        if (level >= 100) {
            completeQuestsIfNeeded(36844);
        }
    }
}
