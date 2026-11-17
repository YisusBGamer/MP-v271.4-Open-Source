package net.swordie.ms.life.mob.skill;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatBase;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.connection.packet.Effect;
import net.swordie.ms.connection.packet.MobPool;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.UserPool;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.connection.packet.field.LucidFieldPacket;
import net.swordie.ms.connection.packet.field.WillFieldPacket;
import net.swordie.ms.jwzlib.WzFile;
import net.swordie.ms.jwzlib.WzObject;
import net.swordie.ms.jwzlib.WzProperty;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.MobConstants;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.enums.ObstacleAtomCreateType;
import net.swordie.ms.enums.ObstacleAtomEnum;
import net.swordie.ms.enums.TSIndex;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.AffectedArea;
import net.swordie.ms.life.DeathType;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.MobTemporaryStat;
import net.swordie.ms.life.mob.boss.lucid.FairyDust;
import net.swordie.ms.life.mob.boss.lucid.LucidSkillType;
import net.swordie.ms.life.mob.boss.will.WillModule;
import net.swordie.ms.life.mob.boss.will.WillSkillPacket;
import net.swordie.ms.life.mob.boss.will.WillSkillType;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.loaders.containerclasses.MobSkillInfo;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.util.container.Tuple;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Portal;
import net.swordie.ms.world.field.Foothold;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelperFactory;
import net.swordie.ms.world.field.instance.instancehelper.boss.HardHillaInstanceHelper;
import net.swordie.ms.world.field.obstacleatom.ObstacleAtomInfo;
import net.swordie.ms.world.field.obstacleatom.ObstacleRadianInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Supplier;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static net.swordie.ms.life.mob.skill.MobSkillStat.*;

public class MobSkillModule {
    private static final int PAPULATUSS_TICK_TOCK_MOB = 8500007;
    private static final int PAPULATUSS_CHRONOS_MOB = 8500008;

    private static final Logger log = LogManager.getLogger();

    public static void handlePapulatusSkill(MobTemporaryStat mts, short slv, MobSkillInfo msi, Field field, Option o, Option o2) {
        switch (slv) {
            case 1:
            case 2:
                // 30 sec timer thing
                applyPapulatusTimer(mts, msi, field, o, o2);
                break;
        }
    }

    public static void handleLucidSkill(Mob mob, short slv, MobSkillInfo msi, Field field) {
        if (slv == 11) {
            // Easy Lucid's Forced Teleport (the client reads it like level 6)
            field.broadcastPacket(LucidFieldPacket.doLucidSkillTeleport(new Random().nextInt(8)));
            return;
        }
        if (slv == 12) {
            lucidBomb(msi, field);
            return;
        }
        LucidSkillType lucidSkillType = LucidSkillType.getSkillByVal(slv);
        if (lucidSkillType == null) {
            // v232 Lucid has skill 238 levels with no pattern here (11 and 12 in phase 2): skip them instead of
            // throwing, so the rest of the skill handling still runs
            log.debug("Lucid skill 238 level " + slv + " has no pattern here: skipped");
            return;
        }
        Random random = new Random();
        switch (lucidSkillType) {
            case FlowerTrap1:
            case FlowerTrap2:
            case FlowerTrap3:
                field.broadcastPacket(LucidFieldPacket.doLucidSkillFlowerTrap(mob.getPosition(), random.nextInt(3), random.nextBoolean()));
                break;
            case FairyDust:
            case FairyDust2:
                List<Object> fairyDustList = new ArrayList<>();
                int numberOfFairyDust = msi.getSkillStatIntValue(w) - random.nextInt(msi.getSkillStatIntValue(w2));
                for (int i = 0; i < numberOfFairyDust; i++) {
                    fairyDustList.add(new FairyDust(
                            random.nextInt(3),
                            (msi.getSkillStatIntValue(s2) - random.nextInt(msi.getSkillStatIntValue(s))),
                            lucidSkillType == LucidSkillType.FairyDust2 ? random.nextInt(359) : random.nextInt(140) + 200,
                            msi.getSkillStatIntValue(u))
                    );
                }
                field.broadcastPacket(LucidFieldPacket.doLucidSkillFairyDust(fairyDustList));
                break;
            case LaserRain:
                List<Object> laserIntervalList = new ArrayList<>() {
                    {
                        for (int i = 0; i < msi.getSkillStatIntValue(z); i++) {
                            add(msi.getSkillStatIntValue(w));
                        }
                    }
                };
                field.broadcastPacket(LucidFieldPacket.doLucidSkillLaserRain(laserIntervalList, msi.getSkillStatIntValue(s)));
                break;
            case Teleport:
                field.broadcastPacket(LucidFieldPacket.doLucidSkillTeleport(random.nextInt(8)));
                break;
            case Dragon:
                boolean isLeft = random.nextBoolean();
                if (BossConstants.isLucidPhase1Field(field.getId())) {
                    field.broadcastPacket(LucidFieldPacket.lucidDragonCreated(1, new Position(), new Position(), isLeft));
                } else {
                    int createPosX = isLeft ? -138 : 1498;
                    int createPosY = (random.nextBoolean() ? -1312 : 238);
                    int posX = createPosX;
                    int posY = mob.getY();
                    field.broadcastPacket(LucidFieldPacket.lucidDragonCreated(2, new Position(posX, posY), new Position(createPosX, createPosY), isLeft));
                }
                break;
            case Rush:
                field.broadcastPacket(LucidFieldPacket.doLucidSkillRush());
                break;
            case WelcomeBarrage:
                field.broadcastPacket(LucidFieldPacket.lucidWelcomeBarrage(2));
                field.broadcastPacket(LucidFieldPacket.lucidStainedGlassOnOff(false, Arrays.asList("Bblue1", "Bblue2", "Bblue3", "Bred1", "Bred2", "Bred3", "Mred2", "Mred3", "Myellow1", "Myellow2", "Myellow3")));
                field.broadcastPacket(LucidFieldPacket.lucidFlyingMode(true));
                field.broadcastPacket(LucidFieldPacket.lucidWelcomeBarrage(3, 50, 120, 500, 3));
                field.broadcastPacket(LucidFieldPacket.lucidWelcomeBarrage(4, 180, 150, 30, 30, 700, 12, 5, 1));
                field.broadcastPacket(LucidFieldPacket.lucidWelcomeBarrage(3, 50, 100, 500, 4));
                // type 6 ends it; the client then asks to end the barrage (1515), which brings the platforms back
                net.swordie.ms.handlers.executors.EventManager.addEvent(() -> {
                    if (!field.getChars().isEmpty()) {
                        field.broadcastPacket(LucidFieldPacket.lucidWelcomeBarrage(6));
                    }
                }, 15_000);
                mob.putSkillCooldown(msi.getSkillId(), slv, System.currentTimeMillis() + BossConstants.LUCID_WELCOME_BARRAGE_COOLTIME); // Skill Info doens't have  Interval value
                break;
        }
    }

    /**
     * Lucid's bomb (mob skill 238 level 12, maplestorywiki.net): up to "count" players carry a timed bomb ("time"
     * seconds, shown as TimeBomb); it then deals 180% of the carrier's max HP, split between everyone in its range
     * (lt/rb around the carrier, the carrier included).
     */
    private static void lucidBomb(MobSkillInfo msi, Field field) {
        List<Char> targets = new ArrayList<>(field.getChars());
        targets.removeIf(c -> c.isDead() || c.getHP() <= 0);
        Collections.shuffle(targets);
        int count = Math.max(1, msi.getSkillStatIntValue(MobSkillStat.count));
        int time = Math.max(1, msi.getSkillStatIntValue(MobSkillStat.time));
        for (Char chr : targets.subList(0, Math.min(count, targets.size()))) {
            Option o = new Option(1, MobSkillID.LucidAttack.getVal(), time);
            o.slv = 12;
            var tsm = chr.getTemporaryStatManager();
            tsm.putCharacterStatValueFromMobSkill(CharacterTemporaryStat.TimeBomb, o);
            tsm.sendSetStatPacket();
            log.debug("Lucid bomb (TimeBomb 238/12, " + time + " s) on " + chr.getName() + " in " + field.getId());
            chr.chatMessage("Lucid planted a bomb on you! Share the blast with your party.");
            net.swordie.ms.handlers.executors.EventManager.addEvent(() -> {
                if (chr.getField() != field || chr.isDead() || chr.getHP() <= 0) {
                    return;
                }
                Rect range = chr.getPosition().getRectAround(new Rect(msi.getLt(), msi.getRb()));
                List<Char> hit = new ArrayList<>();
                for (Char c : field.getChars()) {
                    if (!c.isDead() && c.getHP() > 0 && range.hasPositionInside(c.getPosition())) {
                        hit.add(c);
                    }
                }
                if (!hit.contains(chr)) {
                    hit.add(chr);
                }
                long total = (long) (chr.getMaxHP() * 1.8);
                int each = (int) Math.min(Integer.MAX_VALUE, total / hit.size());
                int bx = chr.getPosition().getX(), by = chr.getPosition().getY();
                for (Char c : hit) {
                    c.damage(each);
                    // the blast: the damage number and a knock-back away from the carrier
                    c.write(net.swordie.ms.connection.packet.UserLocal.timeBombAttack(0, bx, by, 100, each));
                }
            }, time * 1000L);
        }
    }

    public static void createLaserAttack(MobTemporaryStat mts, MobSkillInfo msi, Option o) {
        if (!mts.hasCurrentMobStat(MobStat.Laser)) {
            o.nOption = 1;
            o.wOption = msi.getSkillStatIntValue(w);
            o.uOption = msi.getSkillStatIntValue(z);
            mts.addMobSkillOptionsAndBroadCast(MobStat.Laser, o);
        }
    }

    public static void fireAtRandomAttacks(Mob mob, short slv, MobSkillInfo msi, Field field) {
        if (mob.getPurpleAttacks().size() > 0) {
            Rect r = mob.getPurpleAttacks().get(0);
            for (Char bChr : field.getChars().stream().filter(c -> r.hasPositionInside(c.getPosition())).collect(Collectors.toList())) {
                bChr.write(UserPacket.effect(Effect.mobSkillHit(msi.getSkillId(), slv)));
                bChr.damage(bChr.getHPPerc(msi.getSkillStatIntValue(MobSkillStat.fixDamR)), true);
            }
            mob.getPurpleAttacks().remove(r);
        } else {
            log.warn("No purple attacks for mob " + mob.getTemplateId());
        }
    }

    public static void broadcastCastingBar(Mob mob, MobSkillInfo msi, Field field) {
        field.broadcastPacket(MobPool.castingBarSkill(mob.getObjectId(), MobCastingBarSkill.start(msi.getLevel(),
                msi.getSkillStatIntValue(castingTime), false, false)));

        EventManager.addEvent(() -> onCastingBarEnd(mob, msi, field), msi.getSkillStatIntValue(castingTime));
    }

    private static void onCastingBarEnd(Mob mob, MobSkillInfo msi, Field field) {
        if (!field.getMobs().contains(mob)) {
            // Mob has disappeared while casting the cast bar
            return;
        }

        switch (msi.getLevel()) {
            case 13: // Zakum regen
                var otherSkill = mob.getSkillByIdx(1);
                field.broadcastPacket(MobPool.castingBarSkill(mob.getObjectId(), MobCastingBarSkill.end(
                        true, 30, otherSkill.getSkillID(), otherSkill.getLevel(), 0
                )));
                mob.setForcedSkill(otherSkill);
                field.removeLife(mob);
                otherSkill.applyEffect(mob, true);
                break;
        }
    }

    public static void tossChars(Mob mob, MobSkillInfo msi, Field field) {
        for (Char bChr : field.getChars()) {
            bChr.write(UserPool.tossedByMobSkill(bChr.getId(), mob, msi));
        }
    }

    public static void createBounceAttack(Mob mob, short slv, MobSkillInfo msi, Field field) {
        boolean isConvex = slv == 6 || slv == 13 || slv == 14 || slv == 16;
        if (mob.getAppearingFootholds().size() > 0 || !isConvex) {
            mob.getAppearingFootholds().forEach(afh -> field.addLifeForTime(afh, BossConstants.LOTUS_FALLING_FOOTHOLD_DURATION * 1000));
            mob.getField().broadcastPacket(MobPool.createBounceAttackSkill(mob, msi, isConvex, mob.getAppearingFootholds()));
            mob.getAppearingFootholds().clear();
        }
    }

    public static void createObstacleAttack(Mob mob, MobSkillInfo msi, Field field) {
        var xPos = msi.getSkillStatIntValue(x2); // Starting Position  offset from mob.getPositions()
        int yPos = msi.getSkillStatIntValue(y2); // Starting Position  offset from mob.getPositions()
        int mobTemplateId = msi.getSkillStatIntValue(x); // why?
        var skillAfter = msi.getSkillStatIntValue(MobSkillStat.skillAfter);
        Position position = new Position(mob.getX() + xPos, mob.getY() + yPos);
        switch (msi.getLevel()) {
            case 2: // Demian Phase 1
            case 4: // Demian Phase 2
                ObstacleAtomEnum oae = msi.getLevel() == 2 ? ObstacleAtomEnum.DemianYellowOrb : ObstacleAtomEnum.DemianRedOrb;
                ObstacleAtomCreateType oact = ObstacleAtomCreateType.RADIAL;
                ObstacleRadianInfo ori = new ObstacleRadianInfo(mobTemplateId, position.getX(), position.getY(), position.getX(), 0);
                Set<ObstacleAtomInfo> obstacleAtomInfos = new HashSet<>();
                int atomsCreated = msi.getLevel() == 2 ? 50 : 200;
                for (int i = 0; i < atomsCreated; i++) {
                    Position endPos = new Position(mob.getX() + new Random().nextInt(1600) - 800, mob.getY());
                    ObstacleAtomInfo oai = new ObstacleAtomInfo(oae.getType(), 1, position, endPos,
                            oae.getHitBox(), 90, 0, 1000 + (i * 100), yPos, 100, 2, (int) ObstacleAtomInfo.getLengthByPositions(position, endPos) - 50,
                            0);
                    obstacleAtomInfos.add(oai);
                }
                field.broadcastPacket(FieldPacket.createObstacle(oact, null, ori, obstacleAtomInfos));
                break;
            default:
                log.debug(String.format("[MobSkill::applyEffect] Unhandled ObstacleAttack, slv = %d", msi.getLevel()));
                break;
        }
    }

    private static volatile Set<Integer> summonOnce;

    // Contributed by Arixal (from GitLab).
    /** MobSkill 200/201 levels with summonOnce, as skill * 1000 + level (read once; the dat cache doesn't keep it). */
    static Set<Integer> summonOnceLevels() {
        Set<Integer> set = summonOnce;
        if (set != null) {
            return set;
        }
        set = new HashSet<>();
        try {
            // MobSkill is opened like SkillData.loadMobSkillsFromWz does; works for each wz folder layout
            var file = new WzFile(ServerConstants.WZ_DIR + "/Skill001.wz");
            WzObject<?, ?> mobSkill = file.getChild("MobSkill");
            for (int skill : new int[]{200, 201}) {
                WzObject<?, ?> node = mobSkill == null ? null : mobSkill.getChild(skill + ".img");
                if (node == null) {
                    log.warn("summonOnce: no MobSkill " + skill + " in " + file.getSource());
                    continue;
                }
                set.addAll(summonOnceLevelsOf(skill, node));
            }
            file.clear();
        } catch (RuntimeException e) {
            log.warn("summonOnce: can't read Skill.wz MobSkill: " + e);
        }
        summonOnce = set;
        return set;
    }

    /** Summoned mobs that land at random spots on the ground instead of at the summoner (Papulatus's Cracks in Time). */
    private static final Supplier<Set<Integer>> SPREAD_SUMMONS = DataTable.cached("summon-spread",
            t -> t.rows().stream().map(r -> r.i("mob")).collect(Collectors.toUnmodifiableSet()));

    /** The levels of one MobSkill image marked summonOnce, as skill * 1000 + level. */
    static Set<Integer> summonOnceLevelsOf(int skill, WzObject<?, ?> skillNode) {
        Set<Integer> levels = new HashSet<>();
        WzObject<?, ?> levelTop = skillNode.getChild("level");
        if (levelTop == null) {
            return levels;
        }
        for (WzObject<?, ?> level : levelTop) {
            if (Util.isNumber(level.getName())
                    && level.getChild("summonOnce") instanceof WzProperty<?> once
                    && once.getIntValue() == 1) {
                levels.add(skill * 1000 + Integer.parseInt(level.getName()));
            }
        }
        return levels;
    }

    public static void summonMobs(Mob mob, MobSkillInfo msi, Field field, boolean afterDead) {
        int skillId = msi.getSkillId();
        if (summonOnceLevels().contains(skillId * 1000 + msi.getLevel())) {
            if (!mob.markSummonOnce(skillId, msi.getLevel())) {
                return;
            }
            var id = MobSkillID.getMobSkillIDByVal(skillId);
            if (id != null) {
                mob.addBlockedSkill(id, msi.getLevel());
            }
        }
        Rect rect;
        Position spawnPos = mob.getPosition();
        if (msi.getLt() != null) {
            rect = mob.getRectAround(new Rect(msi.getLt(), msi.getRb()));
            if(mob.getTemplateId() == MobConstants.ANCIENT_MEGAGOO_MOB){ //Goo PQ Boss, can't spawn below (If there's more of these we should put it in an actual constants func)
                rect = mob.getRectAround(new Rect(msi.getLt().getX(), mob.getY() - 5, msi.getRb().getX(), mob.getY()));
            }
            spawnPos = rect.getRandomPositionInside();
        }
        int summonEffect = msi.getSkillStatIntValue(MobSkillStat.summonEffect);
        Set<Mob> spawnedMobs = field.getMobs().stream()
                .filter(m -> m.getMobSpawnerId() == mob.getObjectId())
                .collect(Collectors.toSet());

        var toSpawnIds = new ArrayList<>(msi.getInts());
        if (afterDead) {
            if (!mob.canResummon()) {
                // mob is still on cooldown for summon
                return;
            }
            // randomize which one gets spawned if isAfterDead
            Collections.shuffle(new ArrayList<>(toSpawnIds));
        }
        int xOffset = 0;
        for (int mobId : msi.getInts()) {
            long spawnedSize = spawnedMobs.stream().filter(m -> m.getTemplateId() == mobId).count();
            int maxSpawned = msi.getSkillStatIntValue(limit);
            boolean spawnOnce = maxSpawned == -1;
            if (maxSpawned <= 0) {
                maxSpawned = 100;
            }
            int targetType = msi.getSkillStatIntValue(MobSkillStat.targetType);
            if (targetType == 1) { //target type 1 is on a position
                Char target = Util.getRandomFromCollection(field.getChars());
                if (target == null) {
                    return; // everyone left the map
                }
                spawnPos = target.getPosition();
            } else {

                //Dont spawn monsters on multiple platforms

                if (msi.getSkillStatIntValue(MobSkillStat.summonEffect) == -2 || SPREAD_SUMMONS.get().contains(mobId)) { //Random positions
                    spawnPos = field.getRandomPosOnWalkableFoothold(100, true);
                } else {
                    Foothold targetFH = mob.getField().findFootHoldBelow(new Position(spawnPos.getX() + xOffset, spawnPos.getY()));
                    if (targetFH != null) {
                        if (targetFH.getX2() > spawnPos.getX() + xOffset + 25) {
                            xOffset += 25;
                        }
                    }
                }
            }

            if (spawnedSize < maxSpawned
                    || (spawnOnce && field.getMobs().stream().noneMatch(spawnedMob -> spawnedMob.getTemplateId() == mobId))) {
                Mob m = mob.getField().spawnMob(mobId, spawnPos.getX() + xOffset, spawnPos.getY(), false, -1);
                if(msi.getSkillStatIntValue(linkHP) == 1) {
                    m.setHp(mob.getHp());
                    m.getForcedMobStat().setMaxHP(mob.getMaxHp());
                }
                m.setSummonEffect(summonEffect);
                m.setMobSpawnerId(mob.getObjectId());
                m.setSelfDestructionTimer();

                if (afterDead) {
                    m.setHp(mob.getHp());
                    m.getForcedMobStat().setMaxHP(mob.getMaxHp());
                    break; // only spawn 1 mob if afterDead is true
                }

            }
        }
        if (afterDead) {
            // maybe grab animation from wz, so far it's just -2 (no ani)
            field.broadcastPacket(MobPool.leaveField(mob.getObjectId(), DeathType.EXPLODE));

            field.removeLife(mob.getObjectId());
            mob.setNextSummonPossible(GameConstants.MOB_RESUMMON_COOLDOWN);
        }
        if (msi.getMobGroups().size() > 0) {
            List<Integer> mobGroup = msi.getMobGroups().get(0);
            if (mobGroup.size() - 1 > mob.getMobGroupSpawnProgress()) {
                Mob m = mob.getField().spawnMob(mobGroup.get(mob.getMobGroupSpawnProgress()), spawnPos.getX() + xOffset, spawnPos.getY(), false, 0);
                m.setSummonEffect(summonEffect);
                m.setMobSpawnerId(mob.getObjectId());
                m.setSelfDestructionTimer();
                mob.increaseMobGroupSpawnProgress();
            } else{
                switch(mob.getTemplateId()){
                    case BossConstants.CYGNUS_EASY: //Only FSM skill when all have knights have already spawned
                        mob.setForcedSkill(200, 222);
                        break;
                }
            }
        }
    }

    public static void createMagnet(Mob mob, MobSkillInfo msi, Option o, Set<Char> chars, Field field) {
        o.nOption = msi.getSkillStatIntValue(x);
        for (Char c : chars) {
            TemporaryStatManager tsm = c.getTemporaryStatManager();
            o.nOption = mob.getPosition().getX();
            tsm.putCharacterStatValueFromMobSkill(CharacterTemporaryStat.Magnet, o);
            Option o2 = o.deepCopy();
            o2.nOption = msi.getSkillStatIntValue(x);
            tsm.putCharacterStatValue(CharacterTemporaryStat.MagnetArea, o2);
            tsm.sendSetStatPacket();
        }
    }

    public static void giveTotalImmunity(Mob mob, MobTemporaryStat mts, MobSkillInfo msi, Option o, Option o2) {
        givePadImmunityAndCounter(mob, mts, msi, o, o2);
        giveMadImmunityAndCounter(mob, mts, msi, o, o2);
    }

    public static void givePadImmunityAndCounter(Mob mob, MobTemporaryStat mts, MobSkillInfo msi, Option o, Option o2) {
        mob.setReflectTime(System.currentTimeMillis());
        o.nOption = msi.getSkillStatIntValue(x);
        o.mOption = 100;
        o.bOption = msi.getSkillStatIntValue(MobSkillStat.delay);
        o.wOption = msi.getSkillStatIntValue(y);
        mts.addMobSkillOptionsAndBroadCast(MobStat.PCounter, o);

        o2.nOption = 1;
        mts.addMobSkillOptionsAndBroadCast(MobStat.PImmune, o2);
    }

    public static void giveMadImmunityAndCounter(Mob mob, MobTemporaryStat mts, MobSkillInfo msi, Option o, Option o2) {
        mob.setReflectTime(System.currentTimeMillis());
        o.nOption = msi.getSkillStatIntValue(x);
        o.mOption = 100;
        o.bOption = msi.getSkillStatIntValue(MobSkillStat.delay);
        o.wOption = msi.getSkillStatIntValue(y);
        mts.addMobSkillOptionsAndBroadCast(MobStat.MCounter, o);

        o2.nOption = 1;
        mts.addMobSkillOptionsAndBroadCast(MobStat.MImmune, o2);
    }

    public static void giveFullImmunity(Mob mob, MobTemporaryStat mts, MobSkillInfo msi, Option o){
        // apply P/MImmune
        o.nOption = 1;
        mts.addMobSkillOptionsAndBroadCast(MobStat.PImmune, o);
        mts.addMobSkillOptionsAndBroadCast(MobStat.MImmune, o);
    }

    /** A spot with no floor under it (past the map's wall) moves to the nearest floor; any other is kept. */
    private static Position onFloor(Field field, Position to) {
        if (field.findFootHoldBelow(to) != null) {
            return to;
        }
        Foothold best = null;
        int bestX = to.getX(), bestD = Integer.MAX_VALUE;
        for (Foothold f : field.getNonWallFootholds()) {
            int lo = Math.min(f.getX1(), f.getX2()), hi = Math.max(f.getX1(), f.getX2());
            if (hi - lo > 40) { // clear of the wall at the floor's end
                lo += 20;
                hi -= 20;
            }
            int x = Math.max(lo, Math.min(hi, to.getX()));
            int d = Math.abs(x - to.getX()) + 2 * Math.abs(f.getYFromX(x) - to.getY());
            if (d < bestD) {
                bestD = d;
                best = f;
                bestX = x;
            }
        }
        return best == null ? to : new Position(bestX, best.getYFromX(bestX));
    }

    public static void teleportMob(Mob mob, MobSkillInfo msi, Field field) {
        int skillAfter = msi.getSkillStatIntValue(x);
        int xPos = Math.abs(msi.getSkillStatIntValue(y));
        Position newPos = onFloor(field, new Position(mob.isLeft() ? mob.getX() - xPos : mob.getX() + xPos, mob.getY()));
        if(xPos == 0){ //If no defined position, do a random teleport
            var chars = field.getCharsReadOnly();
            if(chars.size() > 0) {
                var randomChr = new ArrayList<>(chars).get(Util.getRandom(chars.size() - 1));
                newPos = randomChr.getPosition();
            } else {
                newPos = field.getRandomPosOnWalkableFoothold(200, false);
            }

        }

        // Normal Vellum too (the same stage): otherwise his dive goes to a random default spot off-stage
        if (mob.getTemplateId() == BossConstants.VELLUM_CHAOS || mob.getTemplateId() == BossConstants.VELLUM_NORMAL) {
            newPos = mob.isLeft() ? new Position(1032, 443) : new Position(-1776, 443);
        }

        if (MobConstants.isDamien(mob.getTemplateId())) {
            field.broadcastPacket(MobPool.demianDelayedAttackCreate(mob, msi.getSkillId(), msi.getLevel(), msi));
            mob.teleport(newPos, skillAfter);
            return;
        }
        if (MobConstants.isZakumBody(mob.getTemplateId())) {
            var chars = field.getCharsReadOnly();
            var randomChr = new ArrayList<>(chars).get(Util.getRandom(chars.size() - 1));
            // Teleport type 2 carries no position, so the client has nowhere to bring Zakum back up after going
            // underground; type 4 carries the x he resurfaces at (he only ever rises on the floor, y 86, which runs from x -550 to 590 in all three Zakum maps)
            skillAfter = 4;
            if (randomChr != null) {
                int x = Math.max(-500, Math.min(540, randomChr.getPosition().getX()));
                mob.teleport(new Position(x, 86), skillAfter);
                return;
            }
        }
        if (mob.getTemplateId() == BossConstants.VON_LEON_EASY || mob.getTemplateId() == BossConstants.VON_LEON_NORMAL || mob.getTemplateId() == BossConstants.VON_LEON_HARD) {
            if (skillAfter == 4) {
                // level 3: he falls back to the spot the skill names (type 4, x = its y: 450), as GMS sends it
                Position spot = onFloor(field, new Position(xPos, mob.getY()));
                mob.teleport(new Position(xPos, spot.getY()), 4);
                return;
            }
            // level 1: he homes in on the fighter who hurt him most; GMS sends type 2 (no position, the client moves him)
            var chars = field.getCharsReadOnly();
            if (chars.isEmpty()) {
                return;
            }
            var randomChr = new ArrayList<>(chars).get(Util.getRandom(chars.size() - 1));
            mob.teleport(new Position(mob.getMostDamageChar() != null ? mob.getMostDamageChar().getPosition().getX() : randomChr.getPosition().getX(), mob.getPosition().getY()), 2);
            return;
        }
        mob.teleport(newPos, skillAfter);
    }

    public static void giveImmunity(MobTemporaryStat mts, MobSkillInfo msi, Option o, MobStat pImmune) {
        if (!mts.hasCurrentMobStat(MobStat.PImmune) && !mts.hasCurrentMobStat(MobStat.MImmune)) {
            o.nOption = msi.getSkillStatIntValue(x);
            mts.addMobSkillOptionsAndBroadCast(pImmune, o);
        }
    }

    public static void createAreaPoison(Mob mob, boolean fromOtherSkill, short skill, short slv, MobSkillInfo msi, Field field) {
        var aa = AffectedArea.getMobAA(mob, skill, slv, msi);
        var info = field.getInfo();

        // Lotus  Electric Ground Attack
        if (slv == 26 || slv == 25 || slv == 24 || slv == 27) {
            if (fromOtherSkill) {
                aa.setRect(new Rect(info.getVrLeft(), -29, info.getVrRight(), -14)); // whole map
                aa.setDelay((short) 8);
                EventManager.addEvent(() -> field.spawnAffectedArea(aa), BossConstants.LOTUS_FALLING_FOOTHOLD_SHOCK_TERRAIN_DELAY, TimeUnit.MILLISECONDS);
            }
        } else {
            aa.setPosition(mob.getPosition());
            field.spawnAffectedArea(aa);
        }
    }

    public static void createAffectedArea(Mob mob, short skill, short slv, MobSkillInfo msi, MobSkillID msID, Field field) {
        if (mob.getSkillDelayRects(msID, slv) != null && mob.getSkillDelayRects(msID, slv).size() > 0) {
            for (var skillDelayRect : mob.getSkillDelayRects(msID, slv)) {
                AffectedArea aa = AffectedArea.getMobAA(mob, skill, slv, msi);
                aa.setRect(skillDelayRect);
                aa.setPosition(mob.getPosition());

                field.spawnAffectedArea(aa);
            }
        } else {
            AffectedArea aa = AffectedArea.getMobAA(mob, skill, slv, msi);
            aa.setPosition(mob.getPosition());

            if (msID == MobSkillID.AreaMobBuff) {
                startAreaMobBuff(aa, msi, field);
            }

            if(msID.equals(MobSkillID.AreaTimezone)){
                Position pos = field.getRandomPosOnWalkableFoothold(150, false);
                aa.setRect(pos.getRectAround(new Rect(msi.getLt(), msi.getRb())));
                aa.setPosition(pos);
                field.broadcastPacket(UserPacket.scriptProgressMessage("A crack has appeared in the Temporal Crevasse."));
            }
            if (msID == MobSkillID.AreaForce && net.swordie.ms.constants.FieldConstants.isVellumField(field.getId())) {
                field.broadcastPacket(UserPacket.scriptProgressMessage("Vellum is taking a deep breath."));
            }

            // Lotus  Gravity Pull && Queen pulls
            if (msID.equals(MobSkillID.AreaForceFromUser) || msID.equals(MobSkillID.AreaForce)) {
                Rect rect;
                if (msi.getLt() != null) {
                    aa.setIdk2((byte) 1);
                    rect = new Rect(msi.getLt(), msi.getRb());
                } else {
                    rect = new Rect(field.getInfo().getVrLeft(), field.getInfo().getVrTop(), field.getInfo().getVrRight(), field.getInfo().getVrBottom());
                }
                if(!mob.isLeft()) {
                    rect = rect.horizontalFlipAround(0);
                }
                rect = rect.move(mob.getX(), mob.getY());
                aa.setPosition(mob.getPosition());
                aa.setOption(mob.getPosition().getX()); // option = center point

                if(msi.getSkillStatIntValue(targetType) == 1) { //if a player should be the position
                    Char randomChr = Util.getRandomFromCollection(field.getChars());
                    if(randomChr != null){
                        rect = randomChr.getRectAround(rect);
                        aa.setOption(randomChr.getPosition().getX());
                        aa.setPosition(randomChr.getPosition());
                    }
                }
                aa.setRect(rect);

                int forcex = msi.getSkillStatIntValue(MobSkillStat.forcex);
                int force = msi.getSkillStatIntValue(MobSkillStat.force);

                if(mob.isLeft()) {
                    force *= -1;
                }

                //Queen AreaForce uses forceX as center point
                if(forcex == 0) {
                    forcex = aa.getOption();
                }
                aa.setElemAttr(8); // G
                aa.setForce((force << 16) + forcex);
            }
            field.spawnAffectedArea(aa);
        }
    }

    /**
     * Mob buff zone: once a second for the area's time, the monsters standing in it get its affectedOtherSkill
     * (heal, or attack/defense/speed up).
     */
    private static void startAreaMobBuff(AffectedArea aa, MobSkillInfo msi, Field field) {
        int seconds = Math.max(1, msi.getSkillStatIntValue(time));
        EventManager.addFixedRateEvent(() -> {
            if (aa.getRect() == null) {
                return;
            }
            for (int[] other : msi.getAffectedOtherSkills()) {
                var omsi = SkillData.getMobSkillInfoByIdAndLevel(other[0], other[1]);
                if (omsi == null) {
                    continue;
                }
                MobStat stat = switch (MobSkillID.getMobSkillIDByVal(other[0])) {
                    case PowerUp, PowerUpM -> MobStat.PowerUp;
                    case MagicUp, MagicUpM -> MobStat.MagicUp;
                    case PGuardUp, PGuardUpM -> MobStat.PGuardUp;
                    case MGuardUp, MGuardUpM -> MobStat.MGuardUp;
                    case Haste, HasteM -> MobStat.Speed;
                    default -> null;
                };
                for (Mob m : field.getMobsInRect(aa.getRect())) {
                    if (m.getHp() <= 0) {
                        continue;
                    }
                    if (MobSkillID.getMobSkillIDByVal(other[0]) == MobSkillID.HealM) {
                        m.heal(omsi.getSkillStatIntValue(x));
                    } else if (stat != null && !m.getTemporaryStat().isCrashed()) {
                        Option o = new Option(other[0]);
                        o.slv = other[1];
                        o.nOption = omsi.getSkillStatIntValue(x);
                        o.tOption = Math.max(1, omsi.getSkillStatIntValue(time));
                        m.getTemporaryStat().addMobSkillOptionsAndBroadCast(stat, o);
                    }
                }
            }
        }, 0, 1000, seconds);
    }

    public static void createAreaPoison(Char chr, int mobID, int attackIdx, Position pos) {
        Mob mob = (Mob) chr.getField().getLifeByObjectID(mobID);
        if(mob == null) { return;}
        MobSkill skill = mob.getAttackById(attackIdx);
        MobSkillInfo msi = skill == null ? null
                : SkillData.getMobSkillInfoByIdAndLevel((short) skill.getDisease(), (short) skill.getLevel());
        if (msi == null || msi.getLt() == null || msi.getRb() == null) {
            return; // no area in the data for this attack's disease
        }
        var aa = AffectedArea.getMobAA(mob, (short) skill.getDisease(), (short) skill.getLevel(), msi);
        aa.setPosition(pos);
        aa.setRect(pos.getRectAround(new Rect(msi.getLt(), msi.getRb())));
        EventManager.addEvent(() -> mob.getField().spawnAffectedArea(aa), BossConstants.CYGNUS_FLAMES_DELAY, TimeUnit.MILLISECONDS);
    }

    public static void applyDiseaseToPlayers(short slv, MobSkillID msID, Set<Char> chars, MobSkill mobSkill) {
        Char chr = chars.size() == 0 ? null : Util.getRandomFromCollection(chars);
        if (chr != null) {
            applyEffect(chr, mobSkill);
        }
        if (msID == MobSkillID.Fear && slv == 26) {
            // Black Mage P2 mass skill
        }
    }

    public static void healMobs(MobSkillInfo msi, Set<Mob> mobs) {
        for (Mob m : mobs) {
            m.heal(msi.getSkillStatIntValue(x));
        }
    }

    public static void giveTempStat(MobTemporaryStat mts, MobSkillInfo msi, Option o, Set<Mob> mobs, MobStat powerUp) {
        // If Mob has been 'Crashed' not allowed to buff up itself or others.
        if (mts.isCrashed()) {
            return;
        }

        o.nOption = msi.getSkillStatIntValue(x);
        mts.addMobSkillOptionsAndBroadCast(powerUp, o);

        for (Mob m : mobs) {
            m.getTemporaryStat().addMobSkillOptionsAndBroadCast(powerUp, o);
        }
    }

    private static void applyPapulatusTimer(MobTemporaryStat mts, MobSkillInfo msi, Field field, Option o, Option o2) {
        o.nOption = 1;
        o.slv = 2;
        o.xOption = o.tOption * 1000;
        mts.addMobSkillOptionsAndBroadCast(MobStat.AddEffect, o, (short) o.tOption);

        var mobIdsToSpawn = new int[]{PAPULATUSS_TICK_TOCK_MOB, PAPULATUSS_CHRONOS_MOB};

        for (var fChr : field.getCharsReadOnly()) {
            var tsm = fChr.getTemporaryStatManager();
            var randTime = Util.getRandom(msi.getSkillStatIntValue(MobSkillStat.userTimeMin),
                    msi.getSkillStatIntValue(MobSkillStat.userTimeMax));
            o2.nOption = 1;
            o2.tOption = randTime;
            o2.slv = 3;
            tsm.putCharacterStatValueFromMobSkill(CharacterTemporaryStat.PapulatusTimeLock, o2);
            tsm.sendSetStatPacket();

            for (var mobId : mobIdsToSpawn) {
                for (int i = 0; i < BossConstants.PAPULATUS_SOLO_TIMER_MOB_COUNT; i++) {
                    var randomPos = field.getRandomWalkableFoothold().getRandomPosition();
                    field.spawnMob(mobId, randomPos.getX(), randomPos.getY(), false, 0);
                }
            }
        }
    }

    public static void applyEffect(Char chr, MobSkill mobSkill) {
        applyEffect(chr, mobSkill, null);
    }

    /** @param source the monster whose attack or skill it is, when known */
    public static void applyEffect(Char chr, MobSkill mobSkill, Mob source) {
        short skill = (short) mobSkill.getSkillID();
        if (skill == 0) {
            skill = (short) mobSkill.getDisease();
        }
        short level = (short) mobSkill.getLevel();
        MobSkillInfo msi = SkillData.getMobSkillInfoByIdAndLevel(skill, level);
        MobSkillID msID = MobSkillID.getMobSkillIDByVal(skill);
        Field field = chr.getField();
        Option o = new Option(skill);
        o.slv = level;
        double statusResistancePercent = chr.getTotalStat(BaseStat.asr) == 0 ? 0 : Math.floor(28 * Math.log10(chr.getTotalStat(BaseStat.asr)) + 1) / 100D;
        o.tOption = Math.max(1, (int) ((double) msi.getSkillStatIntValue(time) - (double) msi.getSkillStatIntValue(time) * (statusResistancePercent)));
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        boolean appliedDisease = Util.succeedProp(msi.getSkillStatIntValue(prop) != 0 ? msi.getSkillStatIntValue(prop) : 100);
        switch (msID) {
            case Seal:
            case Darkness:
            case Weakness:
            case Stun:
            case Curse:
            case Slow:
            case Fear:
            case DeathMark:
            case DispelItemOption:
            case Attract:
            case DarkTornado:
            case PainMark:
            case ReverseInput:
            case StopPortion:
                if (appliedDisease) {
                    o.nOption = 1;

                    // Zakum weirdness
                    if (msID == MobSkillID.Stun && level == 75) {
                        o.tOption = msi.getSkillStatIntValue(x);
                    }

                    tsm.putCharacterStatValueFromMobSkill(msID.getAffectedCTS(), o);
                    tsm.sendSetStatPacket();
                }
                break;
            case Lapidification:
                if (appliedDisease && !chr.isDead()) {
                    o.nOption = 1;
                    o.tOption = msi.getSkillStatIntValue(time); //always use base cd for this
                    tsm.putCharacterStatValueFromMobSkill(msID.getAffectedCTS(), o);
                    tsm.sendSetStatPacket();
                }
                break;
            case VenomSnake:
                //Poison CTS
                o.nOption = 1;
                tsm.putCharacterStatValueFromMobSkill(msID.getAffectedCTS(), o);
                tsm.sendSetStatPacket();

                //Morph CTS
                Option o1 = new Option();
                o1.nOption = 130;
                o1.rOption = 0;
                o1.tOption = Math.max(0, (int) ((double) msi.getSkillStatIntValue(time) - (double) msi.getSkillStatIntValue(time) * (statusResistancePercent)));
                tsm.putCharacterStatValue(CharacterTemporaryStat.Morph, o1);
                tsm.sendSetStatPacket();
                break;
            // values as the v265 reference sends them: Frozen / DebuffHalf n = 1, SlowAttack / Contagion n = x
            case Frozen: // MobSkill 137
            case SlowAttack: // MobSkill 178
            case DebuffHalf: // MobSkill 219: half stats
            case Contagion: // MobSkill 234
                if (appliedDisease && !chr.isDead()) {
                    CharacterTemporaryStat cts = msID == MobSkillID.Frozen ? CharacterTemporaryStat.Frozen
                            : msID == MobSkillID.SlowAttack ? CharacterTemporaryStat.SlowAttack
                            : msID == MobSkillID.DebuffHalf ? CharacterTemporaryStat.HalfstatByDebuff
                            : CharacterTemporaryStat.Contagion;
                    boolean usesX = msID == MobSkillID.SlowAttack || msID == MobSkillID.Contagion;
                    o.nOption = usesX ? Math.max(1, msi.getSkillStatIntValue(x)) : 1;
                    tsm.putCharacterStatValueFromMobSkill(cts, o);
                    tsm.sendSetStatPacket();
                }
                break;
            case UserBomb: // MobSkill 171: a timed bomb on the player, n = x (v265 reference)
                if (!chr.isDead()) {
                    o.nOption = msi.getSkillStatIntValue(x);
                    tsm.putCharacterStatValueFromMobSkill(CharacterTemporaryStat.TimeBomb, o);
                    tsm.sendSetStatPacket();
                    chr.write(net.swordie.ms.connection.packet.UserPacket.effect(
                            net.swordie.ms.connection.packet.Effect.mobSkillHit(skill, level)));
                    field.broadcastPacket(net.swordie.ms.connection.packet.UserRemote.effect(chr.getId(),
                            net.swordie.ms.connection.packet.Effect.mobSkillHit(skill, level)), chr);
                }
                break;
            case UserMorph:
                o.nOption = msi.getSkillStatIntValue(x);
                o.rOption = 0;
                tsm.putCharacterStatValue(CharacterTemporaryStat.Morph, o);
                tsm.sendSetStatPacket();
                break;
            case Poison:
            case StopMotion:
                if (appliedDisease) {
                    o.nOption = msi.getSkillStatIntValue(x);
                    tsm.putCharacterStatValueFromMobSkill(msID.getAffectedCTS(), o);
                    tsm.sendSetStatPacket();
                }
                break;
            case BlackMageCurse:
                if (appliedDisease) {
                    net.swordie.ms.world.field.instance.instancehelper.boss.BlackMageCurse.apply(chr, level, msi);
                }
                break;
            case Undead:
                if (tsm.hasStat(CharacterTemporaryStat.Undead)) {
                    tsm.removeStat(CharacterTemporaryStat.Undead);
                }
                o.nOption = skill;
                TemporaryStatBase tsb = tsm.getTSBByTSIndex(TSIndex.Undead);
                tsb.setNOption(o.nOption);
                tsb.setExpireTerm(o.tOption);
                tsb.setROption(skill | (level << 16));
                tsb.setSlv(o.slv);
                tsm.putCharacterStatValueFromMobSkill(CharacterTemporaryStat.Undead, tsb.getOption());
                tsm.sendSetStatPacket();
                break;
            case FireBomb: {
                // MobSkill 183: y stacks a hit, up to limit, for time
                int stacks = tsm.hasStat(CharacterTemporaryStat.FireBomb) ? tsm.getOption(CharacterTemporaryStat.FireBomb).nOption : 0;
                int limit = msi.getSkillStatIntValue(MobSkillStat.limit) > 0 ? msi.getSkillStatIntValue(MobSkillStat.limit) : 3;
                if (stacks < limit) {
                    o.nOption = Math.min(limit, stacks + Math.max(1, msi.getSkillStatIntValue(y)));
                    o.tOption = msi.getSkillStatIntValue(time) > 0 ? msi.getSkillStatIntValue(time) : 900;
                    tsm.putCharacterStatValueFromMobSkill(CharacterTemporaryStat.FireBomb, o);
                    tsm.sendSetStatPacket();
                }
                break;
            }
            case FireBombDispel: {
                // MobSkill 205 (Crimson Queen): the hit puts out up to y stacks of FireBomb (y matches 183's limit)
                if (!tsm.hasStat(CharacterTemporaryStat.FireBomb)) {
                    break;
                }
                Option fire = tsm.getOption(CharacterTemporaryStat.FireBomb);
                int left = fire.nOption - Math.max(1, msi.getSkillStatIntValue(y));
                if (left <= 0) {
                    tsm.removeStat(CharacterTemporaryStat.FireBomb);
                    tsm.sendResetStatPacket();
                } else {
                    Option less = fire.deepCopy();
                    less.nOption = left;
                    less.tOption = (int) Math.max(1, fire.getRemainingTime() / 1000);
                    tsm.putCharacterStatValue(CharacterTemporaryStat.FireBomb, less);
                    tsm.sendSetStatPacket();
                }
                break;
            }
            case Stigma:
                // MobSkill 237: Damien's branding attacks add a Brand stack (7 = death, DemianStigma)
                if (appliedDisease && !chr.isDead()) {
                    net.swordie.ms.life.mob.boss.demian.stigma.DemianStigma.incStigma(chr);
                }
                break;
            case VampDeath:
                // MobSkill 180: marked for time; dying while marked brings up monster z where the player fell
                if (appliedDisease && !chr.isDead() && msi.getSkillStatIntValue(z) > 0) {
                    o.nOption = 1;
                    o.xOption = msi.getSkillStatIntValue(x);
                    tsm.putCharacterStatValueFromMobSkill(CharacterTemporaryStat.VampDeath, o);
                    tsm.sendSetStatPacket();
                }
                break;
            case Dazzle:
                // MobSkill 188 as an attack's status (Gollux): charmed toward the monster that hit
                if (appliedDisease && !chr.isDead()) {
                    Mob from = source != null ? source : nearestWithDazzle(chr);
                    if (from != null) {
                        o.nOption = from.getPosition().getX() < chr.getPosition().getX() ? 1 : 2;
                        o.tOption = msi.getSkillStatIntValue(time) > 0 ? msi.getSkillStatIntValue(time) : 5;
                        tsm.putCharacterStatValueFromMobSkill(CharacterTemporaryStat.Attract, o);
                        tsm.sendSetStatPacket();
                    }
                }
                break;
            case AreaTimezone:
                // Von Bon's crack moves the clock when he walks into it (AffectedArea.handleMobInside), not a player
                break;
            case AreaWarning:
                var damage = msi.getSkillStatIntValue(MobSkillStat.fixDamR);
                chr.damagePerc(damage);
                break;
            case AreaPoison:
                // poison mist: x damage a second for the area's time
                if (appliedDisease) {
                    o.nOption = msi.getSkillStatIntValue(x);
                    tsm.putCharacterStatValueFromMobSkill(CharacterTemporaryStat.Poison, o);
                    tsm.sendSetStatPacket();
                }
                break;
            case AreaAbnormal:
                // the area's diseases (affectedOtherSkill), each with its own chance and time
                for (int[] other : msi.getAffectedOtherSkills()) {
                    if (other[0] == skill) {
                        continue;
                    }
                    MobSkill ms = new MobSkill();
                    ms.setSkillID(other[0]);
                    ms.setLevel(other[1]);
                    if (SkillData.getMobSkillInfoByIdAndLevel(other[0], other[1]) != null) {
                        applyEffect(chr, ms);
                    }
                }
                break;
            case AreaMobBuff:
            case AreaForceFromUser:
            case TrueHillaScreenSwipe:
            case TrueHillaRedStringHand:
            case SerenAttack:
            case ArkariumShield:
            case AngelSlimeAction:
                // mob buffs (createAffectedArea), the pull and the screen swipe are done by the client
                break;
            case Unk:
                log.debug(String.format("[MobSkill::applyEffect(Char)] Unknown mob skillID %d, slv = %d", skill, level));
                break;
            default:
                log.debug(String.format("[MobSkill::applyEffect(Char)] Unhandled mob skillID %s, slv = %d", msID, mobSkill.getLevel()));
                break;
        }
    }

    /** The closest monster in the character's map that charms with Dazzle (188), as a skill or an attack's status. */
    private static Mob nearestWithDazzle(Char chr) {
        Field field = chr.getField();
        if (field == null) {
            return null;
        }
        Mob best = null;
        long bestD = Long.MAX_VALUE;
        for (Mob m : field.getMobs()) {
            if (m.getHp() <= 0 || m.getMobInfo() == null) {
                continue;
            }
            boolean dazzles = m.getSkills().stream().anyMatch(s -> s.getSkillID() == MobSkillID.Dazzle.getVal())
                    || m.getMobInfo().getAttacks().stream().anyMatch(a -> a.getDisease() == MobSkillID.Dazzle.getVal());
            if (!dazzles) {
                continue;
            }
            long dx = m.getPosition().getX() - chr.getPosition().getX();
            long dy = m.getPosition().getY() - chr.getPosition().getY();
            if (dx * dx + dy * dy < bestD) {
                bestD = dx * dx + dy * dy;
                best = m;
            }
        }
        return best;
    }

    /**
     * The client reported a hit by a monster skill it draws itself (USER_HIT with attack index -8: the skill and level
     * come with it, the damage is the skill's fixDamR). The skill's affectedOtherSkill is what the hit leaves on the
     * player: Darkness for Chosen Seren's 264, a short Stun for Verus Hilla's 246 level 2.
     */
    public static void onMobSkillHit(Char chr, int mobObjectId, int skillId, int slv) {
        if (chr == null || chr.isDead() || chr.getHP() <= 0 || chr.getField() == null || chr.isInvincible()) {
            return;
        }
        MobSkillInfo msi = SkillData.getMobSkillInfoByIdAndLevel(skillId, slv);
        if (msi == null) {
            return;
        }
        Mob source = chr.getField().getLifeByObjectID(mobObjectId) instanceof Mob m ? m : null;
        for (int[] other : msi.getAffectedOtherSkills()) {
            if (other[0] == skillId || SkillData.getMobSkillInfoByIdAndLevel(other[0], other[1]) == null) {
                continue;
            }
            MobSkill ms = new MobSkill();
            ms.setSkillID(other[0]);
            ms.setLevel(other[1]);
            applyEffect(chr, ms, source);
        }
    }

    /** A character died: a VampDeath mark (MobSkill 180) brings up its monster (z) where they fell. */
    public static void onChrDeath(Char chr) {
        Field field = chr.getField();
        var tsm = chr.getTemporaryStatManager();
        if (field == null || !tsm.hasStat(CharacterTemporaryStat.VampDeath)) {
            return;
        }
        Option mark = tsm.getOption(CharacterTemporaryStat.VampDeath);
        tsm.removeStat(CharacterTemporaryStat.VampDeath);
        tsm.sendResetStatPacket();
        MobSkillInfo msi = SkillData.getMobSkillInfoByIdAndLevel(MobSkillID.VampDeath.getVal(), mark.rOption >>> 16);
        if (msi == null || msi.getSkillStatIntValue(z) <= 0) {
            return;
        }
        int count = Math.max(1, msi.getSkillStatIntValue(MobSkillStat.count));
        Position at = chr.getPosition();
        for (int i = 0; i < count; i++) {
            field.spawnMob(msi.getSkillStatIntValue(z), at.getX(), at.getY(), false, 0);
        }
    }

    /** Heals a monster by a share of its max HP (Mob.heal takes an int, too small for boss HP). */
    public static void healMobPercent(Mob m, int percent) {
        if (m == null || percent <= 0 || m.getHp() <= 0) {
            return;
        }
        long oldHp = m.getHp();
        long newHp = Math.min(m.getMaxHp(), oldHp + m.getMaxHp() / 100 * percent);
        if (newHp == oldHp) {
            return;
        }
        m.setHp(newHp);
        if (m.getField() != null) {
            m.getField().broadcastPacket(MobPool.damaged(m.getObjectId(), m.getTemplateId(), newHp - oldHp, (byte) 0,
                    newHp, m.getMaxHp()));
        }
    }

    public static void handleBossWillAttack(Mob mob, MobSkillInfo msi) {
        var slv = msi.getLevel();
        var field = mob.getField();

        var willAttack = WillSkillType.byVal(slv);
        if (willAttack == null) {
            log.error("Unknown will attack type " + slv);
            return;
        }

        var templateId = 0;
        int willMode = BossConstants.getWillMode(field.getId());
        switch (BossConstants.getWillPhase(field.getId())) {
            case 2:
                templateId = BossConstants.willP2(willMode);
                break;
            case 3:
                templateId = BossConstants.willP3(willMode);
                break;
        }

        switch (willAttack) {
            case P1AdvancedDownwardStrike:
            case P2AdvancedDownwardStrike:
            case P3AdvancedDownwardStrike:
                WillModule.createDownwardStrike(mob, field, willAttack);
            case P1Trigger:
                break;
            case DestroyDimension:
                break;
            case EnergyBlast:
                break;
            case DistortionMirror:
                break;
            case Groggy:
                break;
            case Infection:
                WillModule.createInfection(field);
                break;
            case LegSproutPlayerLoc:
            case LegSproutNonPlayerLoc:
                field.broadcastPacket(WillFieldPacket.useSkill(
                        WillSkillPacket.useSkill(mob.getTemplateId(), willAttack)
                ));
                break;
            case BarrageOfBullets:
                break;
            case NarrowWeb:
                WillModule.spawnNarrowWeb(field);
                break;
            case ReflectedDownwardStrike:
                WillModule.createReflectedDownwardStrike(field, templateId);
                break;
            case RipIntoTarget:
            case AffectedDownwardStrike:
            case BossBarrageOfBullets:
                break; // shown and hit by the client
            default:
                log.warn(String.format("Unhandled will attack %s, templateId %d", willAttack, mob.getTemplateId()));
                break;
        }
    }

    public static void giveInvincibility(Mob mob, MobTemporaryStat mts, MobSkillInfo msi, Option o, Option o2, MobSkill ms) {
        if (msi.getLevel() == 14) {
            // Will spawn eye
            WillModule.spawnEye(mob);
        }
        else if(msi.getLevel() == 1){ //Shinsoo
            if(mob.getTemplateId() == BossConstants.SHINSOO_CYGNUS_EASY) {
                Mob cygnus = mob.getField().getMobs().stream().filter(fieldMob -> fieldMob.getTemplateId() == BossConstants.CYGNUS_EASY).findFirst().orElse(null);
                if (cygnus != null) {
                    ms.applyEffect(cygnus, true);
                }
            }
            if(mob.getTemplateId() != BossConstants.CYGNUS_EASY) {
                giveTotalImmunity(mob, mts, msi, o, o2);
            }
        }
        else{
            giveTotalImmunity(mob, mts, msi, o, o2);
        }
    }

    private static final String ARKARIUM_SAFE_UNTIL = "ArkariumSafeUntil";
    // MobSkill 269 userSafeZone: Rhinne's three spots on the altar floor, each lt (-120, -200) rb (120, 50)
    private static final int[] ARKARIUM_SAFE_X = {-200, 0, 200};
    private static final int ARKARIUM_SAFE_Y = -181;

    /** Arkarium Shield (269): Rhinne's zones stand for the skill's time; Screen Crack checks them. */
    public static void onArkariumShield(Mob mob, MobSkillInfo msi) {
        long ms = Math.max(4, msi.getSkillStatIntValue(time)) * 1000L;
        // the crack lands a moment after the zones fade
        mob.getField().setProperty(ARKARIUM_SAFE_UNTIL, System.currentTimeMillis() + ms + 3000);
        // Screen Crack (176 lv5, onlyOtherSkill: never cast through the controller) follows every Shield, as GMS: the
        // warning, 0.6 s later the crack timer with MOB_SKILL_ACTION playing the crack (WZ action 11) and its hit; the
        // fighter outside Rhinne's zones dies ~2.4 s after the Shield
        var crack = mob.getSkillByMobSkillInfo(ARKARIUM_SCREEN_CRACK_ID, ARKARIUM_SCREEN_CRACK_LV);
        if (crack != null) {
            mob.getField().broadcastPacket(FieldPacket.arkariumScreenCrack(1, ARKARIUM_SCREEN_CRACK_DELAY_MS));
            mob.getField().broadcastPacket(net.swordie.ms.connection.packet.WvsContext.weatherEffectNotice(
                    net.swordie.ms.enums.WeatherEffNoticeType.ArkariumRhinne,
                    "Rhinne's protection creates a space that distorts time.", ARKARIUM_RHINNE_NOTICE_MS));
            net.swordie.ms.handlers.executors.EventManager.addEvent(() -> {
                if (!mob.isDead() && mob.getField() != null) {
                    mob.getField().broadcastPacket(FieldPacket.arkariumScreenCrack(2, ARKARIUM_SCREEN_CRACK_HIT_MS));
                    mob.getField().broadcastPacket(net.swordie.ms.connection.packet.MobPool.skillAction(mob,
                            ARKARIUM_SCREEN_CRACK_ACTION - 1, true, false));
                }
            }, ARKARIUM_SCREEN_CRACK_DELAY_MS);
        }
    }

    private static final int ARKARIUM_SCREEN_CRACK_ID = 176;
    private static final int ARKARIUM_SCREEN_CRACK_LV = 5;
    private static final int ARKARIUM_SCREEN_CRACK_ACTION = 11; // its WZ skill entry's action (Easy and Normal)
    private static final int ARKARIUM_SCREEN_CRACK_DELAY_MS = 600; // GMS: the crack's warning comes 0.6 s in, the hit 1.8 s later
    private static final int ARKARIUM_SCREEN_CRACK_HIT_MS = 1800;
    private static final int ARKARIUM_RHINNE_NOTICE_MS = 2000; // GMS

    private static boolean inArkariumSafeZone(Mob mob, Char chr) {
        Object until = mob.getField().getProperty(ARKARIUM_SAFE_UNTIL);
        if (!(until instanceof Long) || System.currentTimeMillis() > (Long) until) {
            return false;
        }
        var pos = chr.getPosition();
        for (int x : ARKARIUM_SAFE_X) {
            if (pos.getX() >= x - 120 && pos.getX() <= x + 120
                    && pos.getY() >= ARKARIUM_SAFE_Y - 200 && pos.getY() <= ARKARIUM_SAFE_Y + 50) {
                return true;
            }
        }
        return false;
    }

    private static boolean isArkariumScreenCrack(Mob mob, MobSkillInfo msi) {
        int id = mob.getTemplateId();
        return (id == BossConstants.ARKARIUM_NORMAL || id == BossConstants.ARKARIUM_EASY)
                && msi.getSkillStatIntValue(x) >= 999999;
    }

    public static void onDamage(Mob mob, MobSkillInfo msi, Set<Char> chars) {
        boolean screenCrack = isArkariumScreenCrack(mob, msi);
        var successChance = msi.getSkillStatIntValue(attackSuccessProp);
        if (successChance == 0) {
            successChance = msi.getSkillStatIntValue(prop);
        }

        for (var chr : chars) {
            if (!chr.isDead() && !chr.isInvincible() && (successChance > 0 || Util.succeedProp(successChance))) {
                var damR = msi.getSkillStatIntValue(fixDamR);
                var damage = msi.getSkillStatIntValue(x);
                if (screenCrack && inArkariumSafeZone(mob, chr)) {
                    // inside Rhinne's zone the crack takes 90% of max HP instead
                    chr.damagePerc(90);
                } else if (damR > 0) {
                    chr.damagePerc(damR);
                } else {
                    chr.damage(damage);
                }
            }
        }
    }

    public static Rect getRectByZakumArmAndDamageLevel(int templateId, short level, Rect originalRect) {
        // Arms are from top to bottom, then left to right:
        // 0    4
        // 1    5
        // 2    6
        // 3    7

        var armNum = MobConstants.getZakumArmNum(templateId);

        if (level == 27) { // Platform Clap
            // Top
            if (armNum == 4) {
                return originalRect.move(50, -274);
            }

            // Middle
            if (armNum == 5) {
                return originalRect.move(50, -194);
            }

            // Bottom
            if (armNum == 6) {
                return originalRect.move(50, -94);
            }

            return new Rect();
        } else if ((level >= 33 && level <= 36) || level == 25 || level == 26) { // Floor
            var rect = new Rect(-50, -80, 50, 0);
            switch (armNum) {
                case 0:
                    return new Position(-134, 86).getRectAround(rect);
                case 1:
                    return new Position(-264, 86).getRectAround(rect);
                case 2:
                    return new Position(-394, 86).getRectAround(rect);
                case 3:
                    return new Position(-524, 86).getRectAround(rect);
                case 4:
                    return new Position(22, 86).getRectAround(rect);
                case 5:
                    return new Position(153, 86).getRectAround(rect);
                case 6:
                    return new Position(283, 86).getRectAround(rect);
                case 7:
                    return new Position(433, 86).getRectAround(rect);
                default:
                    return new Rect();
            }
        }

        return new Rect(); // no checks
    }

    public static void banMap(Mob mob, Field field) {
        Char target = mob.getMostDamageChar();
        if (target == null) return;
        if (target.isDead()) return;
        if (target.getField() != field) return; // already left (or was already sent away)
        // the data's ban message tells the player why ("You have been ousted by the force of Munin.")
        String banMsg = mob.getMobInfo().getBanMsg();
        if (banMsg != null && !banMsg.isEmpty()) {
            target.write(net.swordie.ms.connection.packet.WvsContext.message(
                    net.swordie.ms.connection.packet.model.MessagePacket.systemMessage(banMsg)));
        }
        switch (mob.getTemplateId()) {
            case BossConstants.VON_LEON_EASY:
                target.warp(BossConstants.VON_LEON_PRISON_EASY_FIELDID);
                return;
            case BossConstants.VON_LEON_NORMAL:
                target.warp(BossConstants.VON_LEON_PRISON_NORMAL_FIELDID);
                return;
            case BossConstants.VON_LEON_HARD:
                target.warp(BossConstants.VON_LEON_PRISON_HARD_FIELDID);
                return;
            case BossConstants.ARKARIUM_EASY:
                target.warp(BossConstants.ARKARIUM_EASY_BANMAP_FIELDID);
                return;
            case BossConstants.ARKARIUM_NORMAL:
                target.warp(BossConstants.ARKARIUM_NORMAL_BANMAP_FIELDID);
                return;
            case BossConstants.MUNIN:
                target.warp(BossConstants.MUNIN_BANMAP_FIELDID);
                return; // not sent again by the Mob.wz data below
            case BossConstants.CHAOS_MUNIN:
                target.warp(BossConstants.CHAOS_MUNIN_BANMAP_FIELDID);
                return;
        }
        var mobInfo = mob.getMobInfo();
        if (!mobInfo.isBanMap() || mobInfo.getBanMapFields().isEmpty()) {
            return;
        }
        // Mob.wz ban/banMap: the entries for the map the fight is in (instanceField) or for any map; field -1 means
        // somewhere else in the same map (one of the listed portals, picked at random)
        List<Integer> candidates = new ArrayList<>();
        for (int i = 0; i < mobInfo.getBanMapFields().size(); i++) {
            int instanceField = mobInfo.getBanMapInstanceFields().get(i);
            if (instanceField == 0 || instanceField == field.getId()) {
                candidates.add(i);
            }
        }
        if (candidates.isEmpty()) {
            candidates.add(0);
        }
        int index = Util.getRandomFromCollection(candidates);
        Tuple<Integer, String> ban = mobInfo.getBanMapFields().get(index);
        int toFieldId = ban.getLeft();
        String portalName = ban.getRight();
        if (toFieldId == -1 || toFieldId == field.getId()) {
            Portal portal = portalName == null || portalName.isEmpty() ? null : field.getInfo().getPortalByName(portalName);
            if (portal != null) {
                Position pos = new Position(portal.getX(), portal.getY());
                target.setPosition(pos);
                target.write(FieldPacket.teleport(pos, target));
            }
            return;
        }
        Field toField = target.getOrCreateFieldByCurrentInstanceType(toFieldId);
        if (toField == null) {
            return;
        }
        Portal portal = portalName == null || portalName.isEmpty() ? null : toField.getInfo().getPortalByName(portalName);
        target.warp(toField, portal != null ? portal : toField.getInfo().getDefaultPortal());
    }


    public static void mobConsume(MobSkillInfo msi, Mob mob, Field field) {
        Rect rect = new Rect(msi.getLt(), msi.getRb());
        rect.move(mob.getX(), mob.getY());
        List<Mob> mobsInRect = field.getMobsInRect(rect).stream().filter(rectMob -> rectMob.getTemplateId() == msi.getInts().get(0)).collect(Collectors.toList());
        int limit = msi.getSkillStatIntValue(MobSkillStat.limit);
        for (Mob rectMob : mobsInRect) {
            if (limit <= 0) break;
            limit--;

            //Consume mob
            mob.heal((int) (rectMob.getHp()));
            rectMob.die(false);
        }
    }

    public static void dazzle(Mob mob, MobSkillInfo msi, Field field, Option o) {
       for (Char chr : field.getChars()) {
           if (!chr.isDead()) {
               o.nOption = mob.getPosition().getX() < chr.getPosition().getX() ? 1 : 2;
               var tsm = chr.getTemporaryStatManager();
               o.tOption = 5;
               tsm.putCharacterStatValueFromMobSkill(CharacterTemporaryStat.Attract, o);
               tsm.sendSetStatPacket();
           }
       }
    }

    public static void hillaVamp(Field field) {
        Instance instance = field.getInstance();
        if (instance != null) {
            var helper = InstanceHelperFactory.getHelperByInstance(instance);
            if (helper instanceof HardHillaInstanceHelper) {
                ((HardHillaInstanceHelper) helper).activateVamp();
            }
        }
    }
}
