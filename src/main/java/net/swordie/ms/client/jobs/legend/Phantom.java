package net.swordie.ms.client.jobs.legend;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.info.HitInfo;
import net.swordie.ms.client.character.skills.BypassCooldownCheckType;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.SkillUseSource;
import net.swordie.ms.client.character.skills.atom.forceatom.ForceAtom;
import net.swordie.ms.client.character.skills.atom.forceatom.ForceAtomCollisionInfo;
import net.swordie.ms.client.character.skills.info.*;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.client.jobs.adventurer.archer.BowMaster;
import net.swordie.ms.client.jobs.adventurer.archer.Marksman;
import net.swordie.ms.client.jobs.adventurer.archer.Pathfinder;
import net.swordie.ms.client.jobs.adventurer.magician.Bishop;
import net.swordie.ms.client.jobs.adventurer.magician.FirePoison;
import net.swordie.ms.client.jobs.adventurer.magician.IceLightning;
import net.swordie.ms.client.jobs.adventurer.pirate.Buccaneer;
import net.swordie.ms.client.jobs.adventurer.pirate.Cannoneer;
import net.swordie.ms.client.jobs.adventurer.pirate.Corsair;
import net.swordie.ms.client.jobs.adventurer.thief.BladeMaster;
import net.swordie.ms.client.jobs.adventurer.thief.NightLord;
import net.swordie.ms.client.jobs.adventurer.thief.Shadower;
import net.swordie.ms.client.jobs.adventurer.warrior.DarkKnight;
import net.swordie.ms.client.jobs.adventurer.warrior.Hero;
import net.swordie.ms.client.jobs.adventurer.warrior.Paladin;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.Effect;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.MobConstants;
import net.swordie.ms.enums.ForceAtomEnum;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.AffectedArea;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.MobTemporaryStat;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.fieldeffect.FieldEffect;
import org.python.modules.math;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static net.swordie.ms.client.character.skills.BypassCooldownCheckType.BypassCheckAndCooldown;
import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;
import static net.swordie.ms.life.mob.MobStat.*;

/**
 * Created on 12/14/2017.
 */
public class Phantom extends Job {

    public static final int JUDGMENT_DRAW_1 = 20031209;
    public static final int JUDGMENT_DRAW_2 = 20031210;
    public static final int JUDGMENT_DRAW_AUTO_MANUAL = 20031260;

    public static final int SKILL_SWIPE = 20031207;
    public static final int LOADOUT = 20031208;
    public static final int TO_THE_SKIES = 20031203;
    public static final int DEXTEROUS_TRAINING = 20030206;
    public static final int GHOSTWALK = 20031211;
    public static final int SHROUD_WALK = 20031205;

    public static final int IMPECCABLE_MEMORY_I = 24001001;

    public static final int IMPECCABLE_MEMORY_II = 24101001;
    public static final int CANE_BOOSTER = 24101005; //Buff
    public static final int CARTE_BLANCHE = 24100003;

    public static final int IMPECCABLE_MEMORY_III = 24111001;
    public static final int FINAL_FEINT = 24111002; //Buff (Unlimited Duration) Gone upon Death
    public static final int BAD_LUCK_WARD = 24111003; //Buff
    public static final int CLAIR_DE_LUNE = 24111005; //Buff

    public static final int IMPECCABLE_MEMORY_IV = 24121001;
    public static final int PRIERE_DARIA = 24121004; //Buff
    public static final int VOL_DAME = 24121007; // Special Buff
    public static final int MAPLE_WARRIOR_PH = 24121008; //Buff
    public static final int CARTE_NOIR = 24120002;              //80001890
    public static final int HEXA_CARTE_NOIR = 24140004;
    public static final int LA_MORT_CARTE = 24141008; // a number of Carte Noir become La Mort Carte for time sec
    public static final int LA_MORT_CARTE_ATOM = 24141009;
    public static final int HEROS_WILL_PH = 24121009;
    public static final int PENOMBRE = 24121003;
    public static final int MILLE_AIGUILLES = 24121000;
    public static final int TEMPEST = 24121005;
    public static final int HEXA_TEMPEST = 24141000;
    public static final int HEXA_MILLE_AIGUILLES = 24141001;

    public static final int HEROIC_MEMORIES_PH = 24121053;
    public static final int CARTE_ROSE_FINALE = 24121052;

    // V skill
    public static final int LUCK_OF_THE_DRAW = 400041009;
    public static final int LUCK_OF_THE_DRAW_ATOM = 400041010;
    public static final int LUCK_OF_THE_DRAW_RED_CROSS = 400041011;
    public static final int LUCK_OF_THE_DRAW_TREE_OF_LIFE = 400041012;
    public static final int LUCK_OF_THE_DRAW_HOURGLASS = 400041013;
    public static final int LUCK_OF_THE_DRAW_SHARP_SWORD = 400041014;
    public static final int LUCK_OF_THE_DRAW_COMBINED = 400041015;

    public static final int ACE_IN_THE_HOLE = 400041022;
    public static final int ACE_IN_THE_HOLE_ATOM = 400041023;
    public static final int ACE_IN_THE_HOLE_FINISHER = 400041024;

    public static final int PHANTOMS_MARK = 400041040;
    public static final int PHANTOMS_MARK_2 = 400041045;
    public static final int PHANTOMS_MARK_3 = 400041046;

    public static final int RIFT_BREAK_TELEPORT = 400041055; // hold key arrow
    public static final int RIFT_BREAK_ALL_ATTACKS_IN_ONE = 400041056;


    public static final int CARTE_ATOM = 80001890;

    // no clear Skill.wz stat for these (Vol d'Ame's data time is 5+d(x/4); Luck of the Draw's time2=3 is unlabelled)
    private static final int VOL_DAME_BUFF_SEC = 30; // hardcode-ok
    private static final int VOL_DAME_INVINCIBLE_SEC = 5; // hardcode-ok
    private static final int LUCK_OF_THE_DRAW_INVINCIBLE_SEC = 3; // hardcode-ok
    public static final int INFILTRATE = 20031001;
    public static final int DOUBLE_ENTENDRE = 24001000;
    public static final int CALLING_CARD = 24101000;
    public static final int MILLE_CARTES = 24101002;
    public static final int BLASON = 24111000;
    public static final int RAPIER_WIT = 24111006;
    public static final int PENOMBRE_2 = 24121010;
    public static final int IMPECCABLE_MEMORY_H = 24121054;
    public static final int DEFYING_FATE = 24141501;
    public static final int FORECASTLE_FIELD = 915000000;

    private static final int[] addedSkills = new int[]{
            SKILL_SWIPE,
            LOADOUT,
            TO_THE_SKIES,
            DEXTEROUS_TRAINING,
            JUDGMENT_DRAW_AUTO_MANUAL,
            SHROUD_WALK,
    };
    private ScheduledFuture healOTByLuckOfTheDrawTimer;

    private boolean isCaneSkill(int skillId) {
        return skillId == DOUBLE_ENTENDRE || skillId == CALLING_CARD || skillId == MILLE_CARTES || skillId == BLASON || skillId == RAPIER_WIT || skillId == PENOMBRE_2 || isMille(skillId);
    }

    private static boolean isMille(int skillId) {
        return skillId == MILLE_AIGUILLES || skillId == HEXA_MILLE_AIGUILLES;
    }

    private byte cardAmount;
    private byte phantomMarkMille = 0;
    private Set<Job> stealJobHandlers = new HashSet<>();

    public Phantom(Char chr) {
        super(chr);
        if (isHandlerOfJob(chr.getJob())) {
            for (int id : addedSkills) {
                if (!chr.hasSkill(id)) {
                    Skill skill = SkillData.getSkillDeepCopyById(id);
                    skill.setCurrentLevel(skill.getMasterLevel());
                    chr.addSkill(skill);
                }
            }
            // Warrior
            stealJobHandlers.add(new Hero(chr));
            stealJobHandlers.add(new Paladin(chr));
            stealJobHandlers.add(new DarkKnight(chr));

            // Mage
            stealJobHandlers.add(new FirePoison(chr));
            stealJobHandlers.add(new IceLightning(chr));
            stealJobHandlers.add(new Bishop(chr));

            // Bowman
            stealJobHandlers.add(new BowMaster(chr));
            stealJobHandlers.add(new Marksman(chr));
            stealJobHandlers.add(new Pathfinder(chr));

            // Thief
            stealJobHandlers.add(new NightLord(chr));
            stealJobHandlers.add(new Shadower(chr));
            stealJobHandlers.add(new BladeMaster(chr));

            // Pirate
            stealJobHandlers.add(new Buccaneer(chr));
            stealJobHandlers.add(new Corsair(chr));
            stealJobHandlers.add(new Cannoneer(chr));

            // [Phantom] Custom Skill Change
            if (chr.getLevel() >= 10 && !chr.hasSkill(GHOSTWALK)) {
                chr.addSkill(GHOSTWALK, 1, 1);
            }
        }
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isPhantom(id);
    }

    @Override
    public void setCharCreationStats(Char chr) {
        super.setCharCreationStats(chr);
        chr.setStolenSkills(new HashSet<>());
        chr.setChosenSkills(new HashSet<>());
        chr.getAvatarData().getCharacterStat().setPosMap(FORECASTLE_FIELD);
    }


    private void giveJudgmentDrawBuff(int skillId) {
        Skill skill = chr.getSkill(skillId);
        if (skill == null) {
            return;
        }

        SkillInfo si = SkillData.getSkillInfoById(skillId);
        int slv = skill.getCurrentLevel();

        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option();
        int randomInt = new Random().nextInt((skillId == JUDGMENT_DRAW_1 ? 2 : 5)) + 1;
        int xOpt = 0;
        switch (randomInt) {
            case 1: // Crit Rate
                xOpt = si.getValue(v, slv);
                break;
            case 2: // Item Drop Rate
                xOpt = si.getValue(w, slv);
                break;
            case 3: // AsrR & TerR
                xOpt = si.getValue(x, slv);
                break;
            case 4: // Defense %
                xOpt = 10;
                break;
            case 5: // Life Drain
                xOpt = 1;
                break;
        }
        chr.write(UserPacket.effect(Effect.avatarOriented("Skill/2003.img/skill/JUDGMENT_DRAW_2/affected/" + (randomInt - 1))));
        chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.avatarOriented("Skill/2003.img/skill/JUDGMENT_DRAW_2/affected/" + (randomInt - 1))), chr);

        o.nOption = randomInt;
        o.rOption = skill.getSkillId();
        o.tOption = si.getValue(time, slv);
        o.xOption = xOpt;
        tsm.putCharacterStatValue(Judgement, o);
        tsm.sendSetStatPacket();
    }


    // Attack related methods ------------------------------------------------------------------------------------------

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        if (attackInfo.skillId != LUCK_OF_THE_DRAW_ATOM) {
            for (Job jobHandler : stealJobHandlers) {
                jobHandler.handleAttack(c, attackInfo);
            }
        }
        Char chr = c.getChr();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (attackInfo.skillId != LUCK_OF_THE_DRAW_ATOM) {
            breakDarkSightOnAttack(attackInfo, GHOSTWALK);
        }
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
        if (hasHitMobs
                && attackInfo.skillId != CARTE_NOIR
                && attackInfo.skillId != HEXA_CARTE_NOIR
                && attackInfo.skillId != CARTE_BLANCHE
                && attackInfo.skillId != LUCK_OF_THE_DRAW_ATOM) {
            doCarteNoir(attackInfo);
            drainLifeByJudgmentDraw();
            if (chr.hasSkill(PHANTOMS_MARK) && hasHitMobs && (isCaneSkill(attackInfo.skillId) || attackInfo.skillId == TEMPEST || attackInfo.skillId == HEXA_TEMPEST)) {
                setPhantomMarkOnMob(attackInfo);
            }
        }

        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (attackInfo.skillId) {
            case CARTE_ROSE_FINALE:
                for (Position position : attackInfo.positions) {
                    AffectedArea aa = AffectedArea.getAffectedArea(chr, attackInfo);
                    aa.setPosition(position);
                    aa.setDelay((short) 13);
                    aa.setRect(aa.getPosition().getRectAround(si.getFirstRect()));
                    chr.getField().spawnAffectedArea(aa);
                }
                break;
        }

        super.handleAttack(c, attackInfo);
    }

    @Override
    public BypassCooldownCheckType canBypassCooldownCheck(int skillId, AttackInfo attackInfo, SkillUseInfo sui, SkillUseSource source) {

        switch (skillId) {
            case GHOSTWALK:
                if (chr.getLevel() >= 10) {
                    return BypassCheckAndCooldown;
                }
                break;
        }


        // Using attackInfo.skillId, as skillId is corrected through SkillConstants.getCorrectCooltimeSkill
        // And they need to be handled separately.
        switch (attackInfo.skillId) {
            case RIFT_BREAK_ALL_ATTACKS_IN_ONE:
                if (sui.multiAttackInfoSequenceCounter > 1) {
                    return BypassCheckAndCooldown;
                }
                break;
            case RIFT_BREAK_TELEPORT:
                if (chr.hasSkillOnCooldown(skillId)) {
                    return BypassCheckAndCooldown;
                }
                break;
        }
        return super.canBypassCooldownCheck(skillId, attackInfo, sui, source);
    }

    private void setPhantomMarkOnMob(AttackInfo attackInfo) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o1 = new Option();
        Option o2 = new Option();
        SkillInfo si = SkillData.getSkillInfoById(PHANTOMS_MARK);
        byte slv = (byte) chr.getSkillLevel(PHANTOMS_MARK);
        if (si.getValue(s2, slv) <= 0) {
            return; // v271: no Master Thief Marks, the skill only has its cooldown
        }

        int count = 1;
        int prevMobId = 0;
        if (isMille(attackInfo.skillId)) {
            if (phantomMarkMille < si.getValue(y, slv)) {
                phantomMarkMille++;
                return;
            }
            phantomMarkMille = 0;
        }
        if (tsm.hasStat(PhantomMarkMobStat)) {
            count = tsm.getOption(PhantomMarkMobStat).nOption;
            prevMobId = tsm.getOption(PhantomMarkMobStat).xOption;
        }

        int finalPrevMobId = prevMobId;
        boolean hitsPrevMob = attackInfo.mobAttackInfo.stream().anyMatch(mai -> mai.mobId == finalPrevMobId);
        if (hitsPrevMob) {
            Mob mob = (Mob) chr.getField().getLifeByObjectID(finalPrevMobId);
            if (mob != null) {
                count++;
                o1.nOption = count > si.getValue(s2, slv) ? si.getValue(s2, slv) : count;
                o1.xOption = finalPrevMobId;
            }
        } else {
            o1.nOption = 1;
            for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                if (mob == null) {
                    continue;
                }
                o1.xOption = mob.getObjectId();

                if (mob.isBoss()) { //  QoL | If attacking boss, boss will get hit.
                    break;
                }
            }
        }

        o1.rOption = si.getSkillId();
        o1.tOption = si.getValue(time, slv);
        tsm.putCharacterStatValue(PhantomMarkMobStat, o1);
        o2.nOption = 1;
        o2.rOption = si.getSkillId();
        o2.xOption = (tsm.hasStat(PhantomMark) ? (tsm.getOption(PhantomMark).xOption + 1 > si.getValue(q, slv) ? si.getValue(q, slv) : tsm.getOption(PhantomMark).xOption + 1) : 1);
        tsm.putCharacterStatValue(PhantomMark, o2);
        tsm.sendSetStatPacket();
    }

    private void doCarteNoir(AttackInfo attackInfo) {
        if (isMille(attackInfo.skillId)) {
            var ai = new AttackInfo();
            ai.mobAttackInfo = new ArrayList<>() {{
                add(attackInfo.mobAttackInfo.get(0));
            }};
            ai.skillId = attackInfo.skillId;
            ai.mobCount = 1;
            cartDeck(1);
            createCarteForceAtom(ai);
        } else {
            cartDeck(attackInfo.mobCount);
            createCarteForceAtom(attackInfo);
        }
    }

    private void createCarteForceAtom(AttackInfo attackInfo) {
        if (!chr.hasSkill(CARTE_BLANCHE) || attackInfo.mobCount <= 0) {
            return;
        }

        boolean hasNoir = chr.hasSkill(CARTE_NOIR) || chr.hasSkill(HEXA_CARTE_NOIR);
        ForceAtomEnum fae = hasNoir ? ForceAtomEnum.PHANTOM_CARD_2 : ForceAtomEnum.PHANTOM_CARD_1;
        int skillId = chr.hasSkill(HEXA_CARTE_NOIR) ? HEXA_CARTE_NOIR : hasNoir ? CARTE_NOIR : CARTE_BLANCHE;
        int atomId = skillId;
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option laMort = tsm.hasStat(LamourCarte) ? tsm.getOption(LamourCarte) : null;
        if (laMort != null && laMort.nOption > 0) {
            atomId = LA_MORT_CARTE_ATOM;
        }

        int propp = chr.getSkillStatValue(prop, skillId);

        if (propp <= 0) {
            return;
        }

        List<Integer> targets = new ArrayList<>();
        List<ForceAtomInfo> faiList = new ArrayList<>();

        for (var mai : attackInfo.mobAttackInfo) {
            if (mai == null || mai.mob == null || mai.mobId == 0) {
                continue;
            }

            if (Util.succeedProp(propp)) {
                ForceAtomInfo fai = new ForceAtomInfo(
                        chr.getNewForceAtomKey(),
                        fae.getInc(),
                        Util.getRandom(15, 30),
                        Util.getRandom(15, 30),
                        0,
                        Util.getRandom(0, 50),
                        Util.getCurrentTime(),
                        0, 0,
                        new Position()
                );
                faiList.add(fai);
                targets.add(mai.mobId);
            }
        }

        // 🚨 THIS is the critical guard
        if (targets.isEmpty() || faiList.isEmpty()) {
            return;
        }

        chr.createForceAtom(new ForceAtom(false, 0, chr.getId(), fae,
                true, targets, atomId, faiList, new Rect(), 0, 0,
                new Position(), atomId, new Position(), 0));
        if (atomId == LA_MORT_CARTE_ATOM) {
            // each card thrown uses up one La Mort Carte
            int left = Math.max(0, laMort.nOption - targets.size());
            if (left <= 0) {
                tsm.removeStatsBySkill(LA_MORT_CARTE);
                tsm.sendResetStatPacket();
            } else {
                laMort.nOption = left;
                laMort.xOption = left;
                tsm.updateBuff(LamourCarte, laMort);
            }
        }
    }


    private void createCarteForceAtomByJudgmentDraw() {
        if (!chr.hasSkill(CARTE_BLANCHE)) {
            return;
        }
        boolean hasNoir = chr.hasSkill(CARTE_NOIR) || chr.hasSkill(HEXA_CARTE_NOIR);
        Rect rect = new Rect(
                new Position(
                        chr.getPosition().getX() - 450,
                        chr.getPosition().getY() - 450),
                new Position(
                        chr.getPosition().getX() + 450,
                        chr.getPosition().getY() + 450)
        );
        List<Mob> mobs = chr.getField().getMobsInRect(rect).stream().filter(m -> !MobConstants.isFriendlyMob(m.getTemplateId())).collect(Collectors.toList());
        if (mobs.size() <= 0) {
            return;
        }
        Mob mob = Util.getRandomFromCollection(mobs);
        var attCount = chr.hasSkill(JUDGMENT_DRAW_2) ? 10 : 5;
        ForceAtomEnum fae = hasNoir ? ForceAtomEnum.PHANTOM_CARD_2 : ForceAtomEnum.PHANTOM_CARD_1;
        int skillId = chr.hasSkill(HEXA_CARTE_NOIR) ? HEXA_CARTE_NOIR : hasNoir ? CARTE_NOIR : CARTE_BLANCHE;
        List<Integer> targets = new ArrayList<>();
        List<ForceAtomInfo> faiList = new ArrayList<>();
        for (int i = 0; i < attCount; i++) {

            ForceAtomInfo fai = new ForceAtomInfo(chr.getNewForceAtomKey(), fae.getInc(), 18, 20,
                    0, 15 * i, Util.getCurrentTime(), 0, 0,
                    new Position());
            faiList.add(fai);
            targets.add(mob.getObjectId());
        }
        chr.createForceAtom(new ForceAtom(false, 0, chr.getId(), fae,
                true, targets, skillId, faiList, new Rect(), 0, 0,
                mob.getPosition(), skillId, mob.getPosition(), 0));
    }

    public void handleCancelKeyDownSkill(Char chr, int skillID) {
        switch (skillID) {
            case LUCK_OF_THE_DRAW:
                drawCardByLuckOfTheDraw();
                break;
            case SHROUD_WALK:
                TemporaryStatManager tsm = chr.getTemporaryStatManager();
                tsm.removeStatsBySkill(skillID);
                break;
        }
        super.handleCancelKeyDownSkill(chr, skillID);
    }

    private void createAceInTheHoleForceAtom(int mobId) {
        Field field = chr.getField();
        if (!chr.hasSkill(ACE_IN_THE_HOLE)) {
            return;
        }
        Skill skill = chr.getSkill(ACE_IN_THE_HOLE);
        SkillInfo si = SkillData.getSkillInfoById(ACE_IN_THE_HOLE);
        int slv = skill.getCurrentLevel();
        ForceAtomEnum fae = ForceAtomEnum.ACE_IN_THE_HOLE;

        Mob mob = (Mob) field.getLifeByObjectID(mobId);
        ForceAtomInfo forceAtomInfo = new ForceAtomInfo(chr.getNewForceAtomKey(), fae.getInc(), 25, 8,
                270 + Util.getRandom(0, 30), 500, Util.getCurrentTime(), 1, 0,
                new Position());
        ForceAtom fa = new ForceAtom(false, 0, chr.getId(), fae,
                true, mob == null ? 0 : mob.getObjectId(), ACE_IN_THE_HOLE_ATOM, forceAtomInfo, chr.getPosition().getRectAround(si.getFirstRect()), 0, 300,
                chr.getPosition(), 0, mob == null ? new Position() : mob.getPosition(), 0);
        fa.setMaxRecreationCount(si.getValue(z, slv));
        chr.createForceAtom(fa);
    }

    public void handleForceAtomCollision(ForceAtom fa, ForceAtomCollisionInfo faci) {
        var faKey = faci.forceAtomKey;
        var position = faci.position;

        if (fa != null && fa.getCurRecreationCount(faKey) >= fa.getMaxRecreationCount(faKey) && fa.getSkillId() == ACE_IN_THE_HOLE_ATOM) {
            chr.write(UserLocal.aceInTheHoleFinisher(ACE_IN_THE_HOLE_FINISHER, chr.getSkillLevel(ACE_IN_THE_HOLE), position));
        }

        super.handleForceAtomCollision(fa, faci);
    }

    private void drainLifeByJudgmentDraw() {
        if (!chr.hasSkill(JUDGMENT_DRAW_2)) {
            return;
        }
        Skill skill = chr.getSkill(JUDGMENT_DRAW_2);
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        int slv = skill.getCurrentLevel();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (tsm.hasStat(Judgement) && tsm.getOption(Judgement).nOption == 5) {
            int healrate = si.getValue(z, slv);
            chr.heal((int) (chr.getMaxHP() * ((double) healrate / 100)));
        }
    }

    private int getMaxCards() {
        int num = 0;
        if (chr.hasSkill(CARTE_BLANCHE)) {
            num = 20;
        }
        if (chr.hasSkill(CARTE_NOIR) || chr.hasSkill(HEXA_CARTE_NOIR)) {
            num = 40;
        }
        return num;
    }

    private void resetCardStack() {
        setCardAmount((byte) 0);
    }

    public byte getCardAmount() {
        return cardAmount;
    }

    public void setCardAmount(byte cardAmount) {
        this.cardAmount = cardAmount;
        chr.write(UserLocal.incJudgementStack(getCardAmount()));
        if (cardAmount >= getMaxCards() && getMaxCards() > 0) {
            createCarteForceAtomByJudgmentDraw();
            giveJudgmentDrawBuff(chr.hasSkill(JUDGMENT_DRAW_2) ? JUDGMENT_DRAW_2 : JUDGMENT_DRAW_1);
            resetCardStack();
        }
    }

    private void cartDeck() {
        cartDeck(1);
    }

    private void cartDeck(int num) {
        if (num == 0) {
            return;
        }
        var curAmount = getCardAmount();
        setCardAmount((byte) Math.min(getMaxCards(), curAmount + num));
    }


    // Skill related methods -------------------------------------------------------------------------------------------

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        super.handleSkill(chr, tsm, skillId, slv, inPacket, skillUseInfo);
        for (Job jobHandler : stealJobHandlers) {
            jobHandler.handleSkill(chr, tsm, skillId, slv, inPacket, skillUseInfo);
        }
        Skill skill = chr.getSkill(skillId);
        SkillInfo si = null;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skillId);
        }
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();
        switch (skillId) {
            case DEFYING_FATE: { // Defying Fate's Hero's Will mode (the client asks for it after the Origin; its attacks are the client's)
                SkillInfo dsi = SkillData.getSkillInfoById(skillId);
                tsm.putCharacterStatValue(PhantomDefyingFate, new Option(1, skillId, dsi.getValue(time, Math.max(1, slv))));
                tsm.sendSetStatPacket();
                break;
            }
            case LA_MORT_CARTE:
                // x cards (the Luck of the Draw bonus y/s is not done)
                o1.nOption = si.getValue(x, slv);
                o1.xOption = o1.nOption;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(LamourCarte, o1);
                break;
            case SHROUD_WALK:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Invisible, o1, true);
                break;
            case VOL_DAME:
                stealBuffVolDame();
                break;
            case JUDGMENT_DRAW_1:
            case JUDGMENT_DRAW_2:
                createCarteForceAtomByJudgmentDraw();
                giveJudgmentDrawBuff(skillId);
                resetCardStack();
                break;
            case ACE_IN_THE_HOLE:
                inPacket.decodeByte(); // 1
                var mobId = inPacket.decodeInt();
                for (int i = 0; i < si.getValue(bulletCount, slv); i++) {
                    createAceInTheHoleForceAtom(mobId);
                }
                break;
            case ACE_IN_THE_HOLE_FINISHER:
                var position = inPacket.decodePosition();
                ForceAtom fa = chr.getForceAtoms().values().stream().filter(f -> f.getSkillId() == ACE_IN_THE_HOLE_ATOM).findFirst().orElse(null);
                if (fa != null) {
                    for (int key : fa.getKeys()) {
                        chr.removeForceAtomByKey(key);
                    }
                }

                chr.write(UserLocal.aceInTheHoleFinisher(ACE_IN_THE_HOLE_FINISHER, chr.getSkillLevel(ACE_IN_THE_HOLE), position));
                break;
            case PHANTOMS_MARK:
                List<Integer> extraSkillList = new ArrayList<>();
                Objects.requireNonNull(si).getExtraSkillInfo().forEach(map -> extraSkillList.addAll(map.keySet()));
                chr.write(FieldPacket.registerExtraSkill(skillUseInfo.endingPosition, skillId, extraSkillList, skillUseInfo.isLeft));
                tsm.removeStatsBySkill(skillId);

                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(subTime, slv);
                tsm.putCharacterStatValue(IndieNotDamaged, o1, true);
                break;
            case GHOSTWALK:
                // [Phantom] Custom Skill Change | Use 'Ghostwalk' to quick swap between presets
                if (chr.getLevel() >= 10) {
                    SkillStealManager.swapPresets(chr);
                } else {
                    o1.nOption = si.getValue(x, slv);
                    o1.rOption = skillId;
                    o1.tOption = si.getValue(time, slv);
                    tsm.putCharacterStatValue(DarkSight, o1);
                }
                break;
            case CANE_BOOSTER:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieBooster, o1);
                break;
            case FINAL_FEINT:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.ReviveOnce, o1, true);
                break;
            case BAD_LUCK_WARD:
                o1.nOption = si.getValue(indieMhpR, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieMHPR, o1);
                o2.nOption = si.getValue(indieMmpR, slv);
                o2.rOption = skillId;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieMMPR, o2);
                o3.nOption = si.getValue(x, slv);
                o3.rOption = skillId;
                o3.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(AsrR, o3);
                o4.nOption = si.getValue(y, slv);
                o4.rOption = skillId;
                o4.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(TerR, o4);
                break;
            case PENOMBRE: // v265: an attack, its bonus is passive
                break;
            case CLAIR_DE_LUNE:
                o1.nOption = si.getValue(indiePad, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePAD, o1);
                o2.nOption = si.getValue(indieAcc, slv);
                o2.rOption = skillId;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieACC, o2);
                break;
            case PRIERE_DARIA: // v265: passive damage / ignore defense; using it only shows Aria
                break;
            case HEROIC_MEMORIES_PH:
                o1.rOption = skillId;
                o1.nOption = si.getValue(indieDamR, slv);
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o1);
                break;
        }
        tsm.sendSetStatPacket();
    }

    @Override
    public void handleUserSkillInfo(SkillUseInfo sui) {

        switch (sui.skillID) {
            case RIFT_BREAK_TELEPORT:
            case RIFT_BREAK_ALL_ATTACKS_IN_ONE:
                var remaining = sui.phantomRiftBreaksRemaining;
                var cdRMulti = chr.getSkillStatValueD(z, RIFT_BREAK_TELEPORT);
                var finalCdR = remaining * cdRMulti;
                chr.reduceSkillCoolTime(RIFT_BREAK_TELEPORT, (int) finalCdR);
                break;
        }

        super.handleUserSkillInfo(sui);
    }

    //PowerGuard
    //NotDamaged
    //EPAD
    //DamAbsorbShield
    private void stealBuffVolDame() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o1 = new Option();

        if (!chr.hasSkill(VOL_DAME)) {
            return;
        }

        Skill skill = chr.getSkill(VOL_DAME);
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        int slv = skill.getCurrentLevel();

        Rect rect = new Rect(   //NPE when using the skill's rect
                new Position(
                        chr.getPosition().getX() - 250,
                        chr.getPosition().getY() - 250),
                new Position(
                        chr.getPosition().getX() + 250,
                        chr.getPosition().getY() + 250)
        );
        List<Mob> mobs = chr.getField().getMobsInRect(rect);
        if (mobs.size() <= 0) {
            return;
        }
        MobStat buffFromMobStat = MobStat.Mystery; //Needs to be initialised
        MobStat[] mobStats = new MobStat[]{ //Ordered from Weakest to Strongest, since  the for loop will save the last MobsStat
                PCounter,           //Dmg Reflect 600%
                MCounter,           //Dmg Reflect 600%
                PImmune,            //Dmg Recv -40%
                MImmune,            //Dmg Recv -40%
                PowerUp,            //Attack +40
                MagicUp,            //Attack +40
                MobStat.Invincible, //Invincible for short time
        };
        for (Mob mob : mobs) {
            MobTemporaryStat mts = mob.getTemporaryStat();
            List<MobStat> currentMobStats = Arrays.stream(mobStats).filter(mts::hasCurrentMobStat).collect(Collectors.toList());
            for (MobStat currentMobStat : currentMobStats) {
                if (mts.hasCurrentMobStat(currentMobStat)) {
                    mts.removeMobStat(currentMobStat);
                    buffFromMobStat = currentMobStat;
                }
            }
        }
        switch (buffFromMobStat) {
            case PCounter:
            case MCounter:
                o1.nOption = si.getValue(y, slv);
                o1.rOption = skill.getSkillId();
                o1.tOption = VOL_DAME_BUFF_SEC;
                tsm.putCharacterStatValue(PowerGuard, o1);
                break;
            case PImmune:
            case MImmune:
                // damage received -x% (the client checks DamAbsorbShield with r Vol D'Ame)
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skill.getSkillId();
                o1.tOption = VOL_DAME_BUFF_SEC;
                tsm.putCharacterStatValue(DamAbsorbShield, o1);
                break;
            case PowerUp:
            case MagicUp:
                o1.nOption = si.getValue(epad, slv);
                o1.rOption = skill.getSkillId();
                o1.tOption = VOL_DAME_BUFF_SEC;
                tsm.putCharacterStatValue(CharacterTemporaryStat.PAD, o1);
                break;
            case Invincible:
                o1.nOption = 1;
                o1.rOption = skill.getSkillId();
                o1.tOption = VOL_DAME_INVINCIBLE_SEC;
                tsm.putCharacterStatValue(NotDamaged, o1);
                break;
        }
        tsm.sendSetStatPacket();
    }

    public void createLuckOfTheDrawForceAtom() {
        Skill skill = chr.getSkill(LUCK_OF_THE_DRAW);
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        int slv = skill.getCurrentLevel();

        //Rect rect = chr.getRectAround(si.getFirstRect());
        Rect rect = chr.getRectAround(new Rect(-700, -400, 0, 40));
        if (!chr.isLeft()) {
            rect = rect.horizontalFlipAround(chr.getPosition().getX());
        }
        List<Integer> targetList = new ArrayList<>();
        List<ForceAtomInfo> faiList = new ArrayList<>();
        ForceAtomEnum fae = ForceAtomEnum.PHANTOM_CARD_2;
        List<Mob> possibleTargetMobs = chr.getField().getMobsInRect(rect).stream().filter(m -> !MobConstants.isFriendlyMob(m.getTemplateId())).collect(Collectors.toList());
        for (int i = 0; i < 14; i++) {
            Mob mob = Util.getRandomFromCollection(possibleTargetMobs);
            int fImpact = new Random().nextInt(15) + 15;
            int sImpact = new Random().nextInt(5) + 8;
            int angle = new Random().nextInt(60) + 295;
            int fullR = new Random().nextInt(360);
            int radius = new Random().nextInt(20) + 70;
            ForceAtomInfo forceAtomInfo = new ForceAtomInfo(chr.getNewForceAtomKey(), fae.getInc(), fImpact, sImpact,
                    angle, 30 * i, Util.getCurrentTime(), 0, 0,
                    new Position((int) (radius * math.cos(fullR)), (int) (radius * math.sin(fullR))));
            faiList.add(forceAtomInfo);
            targetList.add(mob != null ? mob.getObjectId() : 0);
        }
        chr.createForceAtom(new ForceAtom(false, 0, chr.getId(), fae,
                true, targetList, LUCK_OF_THE_DRAW_ATOM, faiList, rect, 0, 0,
                new Position(), LUCK_OF_THE_DRAW_ATOM, new Position(), 0));

    }

    private void drawCardByLuckOfTheDraw() {
        List<Integer> randomCardSkillId = Arrays.asList(
                LUCK_OF_THE_DRAW_RED_CROSS,
                LUCK_OF_THE_DRAW_TREE_OF_LIFE,
                LUCK_OF_THE_DRAW_HOURGLASS,
                LUCK_OF_THE_DRAW_SHARP_SWORD,
                LUCK_OF_THE_DRAW_COMBINED
        );

        int drawnCard = Util.getRandomFromCollection(randomCardSkillId);
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();
        Option o5 = new Option();
        SkillInfo si = SkillData.getSkillInfoById(drawnCard);
        int slv = chr.getSkillLevel(LUCK_OF_THE_DRAW);
        int duration = SkillData.getSkillInfoById(LUCK_OF_THE_DRAW).getValue(dotInterval, slv);
        List<Char> partyChrList = new ArrayList<>();
        if (chr.getParty() != null) {
            partyChrList.addAll(chr.getParty().getPartyMembersInSameField(chr));
        }
        partyChrList.add(chr);
        for (Char partyChr : partyChrList) {
            TemporaryStatManager ptyTsm = partyChr.getTemporaryStatManager();
            switch (drawnCard) {
                case LUCK_OF_THE_DRAW_RED_CROSS:
                    // v271: no Max HP part (indieMhpR is gone): the card is the heal over time below, shown by its icon
                    ptyTsm.putCharacterStatValue(IndieBuffIcon, new Option(1, drawnCard, duration));
                    if (healOTByLuckOfTheDrawTimer != null && !healOTByLuckOfTheDrawTimer.isDone()) {
                        healOTByLuckOfTheDrawTimer.cancel(false);
                    }
                    healOTByLuckOfTheDrawTimer = EventManager.addFixedRateEvent(() -> healOTByLuckOfTheDraw(ptyTsm, si, slv), 0, 2, TimeUnit.SECONDS);
                    break;
                case LUCK_OF_THE_DRAW_TREE_OF_LIFE:
                    o1.nOption = si.getValue(z, slv);
                    o1.rOption = drawnCard;
                    o1.tOption = duration;
                    ptyTsm.putCharacterStatValue(DamageReduce, o1);
                    o2.nOption = si.getValue(indieAsrR, slv);
                    o2.rOption = drawnCard;
                    o2.tOption = duration;
                    ptyTsm.putCharacterStatValue(IndieAsrR, o2);
                    break;
                case LUCK_OF_THE_DRAW_HOURGLASS:
                    for (var cdSkills : partyChr.getSkillCoolTimes().keySet()) {
                        SkillInfo cdsi = SkillData.getSkillInfoById(cdSkills);
                        if (cdsi != null && !cdsi.isNotCooltimeReset()) {
                            partyChr.reduceSkillCoolTime(cdSkills, (int) (partyChr.getRemainingCoolTime(cdSkills) * ((double) si.getValue(x, slv) / 100)));
                        }
                    }
                    break;
                case LUCK_OF_THE_DRAW_SHARP_SWORD:
                    o1.nOption = si.getValue(indiePMdR, slv);
                    o1.rOption = drawnCard;
                    o1.tOption = duration;
                    ptyTsm.putCharacterStatValue(IndiePMdR, o1);
                    break;
                case LUCK_OF_THE_DRAW_COMBINED:
                    o1.nOption = si.getValue(indieMhpR, slv);
                    o1.rOption = drawnCard;
                    o1.tOption = duration;
                    ptyTsm.putCharacterStatValue(IndieMHPR, o1);
                    o2.nOption = si.getValue(z, slv);
                    o2.rOption = drawnCard;
                    o2.tOption = duration;
                    ptyTsm.putCharacterStatValue(DamageReduce, o2);
                    o3.nOption = si.getValue(indieAsrR, slv);
                    o3.rOption = drawnCard;
                    o3.tOption = duration;
                    ptyTsm.putCharacterStatValue(IndieAsrR, o3);
                    o4.nOption = si.getValue(indiePMdR, slv);
                    o4.rOption = drawnCard;
                    o4.tOption = duration;
                    ptyTsm.putCharacterStatValue(IndiePMdR, o4);
                    for (var cdSkills : partyChr.getSkillCoolTimes().keySet()) {
                        SkillInfo cdsi = SkillData.getSkillInfoById(cdSkills);
                        if (cdsi != null && !cdsi.isNotCooltimeReset()) {
                            partyChr.reduceSkillCoolTime(cdSkills, (int) (partyChr.getRemainingCoolTime(cdSkills) * ((double) si.getValue(indieCooltimeReduce, slv) / 100)));
                        }
                    }
                    break;
            }
            if (chr == partyChr) {
                o5.nOption = 1;
                o5.rOption = LUCK_OF_THE_DRAW;
                o5.tOption = LUCK_OF_THE_DRAW_INVINCIBLE_SEC;
                ptyTsm.putCharacterStatValue(IndieNotDamaged, o5, true);
            }
            ptyTsm.sendSetStatPacket();
        }
        for (int skill : Arrays.asList(LUCK_OF_THE_DRAW, drawnCard)) {
            chr.write(FieldPacket.fieldEffect(FieldEffect.getOffFieldEffectFromWz(String.format("Skill/40004.img/skill/%d/screen", skill), 0)));
            chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.avatarOriented(String.format("Skill/40004.img/skill/%d/screen", skill))));
        }
    }

    private void healOTByLuckOfTheDraw(TemporaryStatManager tsm, SkillInfo si, int slv) {
        if (tsm.getOptByCTSAndSkill(IndieBuffIcon, LUCK_OF_THE_DRAW_RED_CROSS) != null) {
            int healR = (int) ((double) (chr.getMaxHP() * si.getValue(y, slv)) / 100F);
            tsm.getChr().heal(healR);
            tsm.getChr().healMP(healR);
        } else {
            healOTByLuckOfTheDrawTimer.cancel(true);
        }
    }

    @Override
    public void handleSkillPrepareStop(Char chr, int skillId, SkillUseInfo sui) {
        if (skillId == BladeMaster.FINAL_CUT) {
            return;
        }
        super.handleSkillPrepareStop(chr, skillId, sui);
    }


    // Hit related methods ---------------------------------------------------------------------------------------------

    @Override
    public void handleHit(Client c, InPacket inPacket, HitInfo hitInfo) {
        for (Job jobHandler : stealJobHandlers) {
            jobHandler.handleHit(c, inPacket, hitInfo);
        }
        super.handleHit(c, inPacket, hitInfo);
    }

    public void reviveByFinalFeint() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option();
        Skill skill = chr.getSkill(FINAL_FEINT);
        if (skill == null) {
            return;
        }

        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        int slv = skill.getCurrentLevel();

        chr.heal(chr.getMaxHP(), false, true);
        tsm.removeStatsBySkill(skill.getSkillId());
        tsm.sendResetStatPacket();
        chr.chatMessage("You have been revived by Final Feint.");
        chr.write(UserPacket.effect(Effect.skillSpecial(skill.getSkillId())));
        chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.skillSpecial(skill.getSkillId())));

        o.nOption = 1;
        o.rOption = skill.getSkillId();
        o.tOption = si.getValue(y, slv);
        tsm.putCharacterStatValue(IndieNotDamaged, o, true);
        tsm.sendSetStatPacket();
    }

    @Override
    public void handleLevelUp() {
        super.handleLevelUp();
        if (chr.getLevel() == 10) {
            chr.addSkill(GHOSTWALK, 1, 1);
        }
    }

    @Override
    public void stopTimers() {
        if (healOTByLuckOfTheDrawTimer != null) {
            healOTByLuckOfTheDrawTimer.cancel(true);
        }
        super.stopTimers();
    }

    @Override
    public void onWarp(Field oldField, Field newField) {
        super.onWarp(oldField, newField);
        setCardAmount(getCardAmount());
    }
}