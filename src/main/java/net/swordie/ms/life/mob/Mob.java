package net.swordie.ms.life.mob;

import net.swordie.ms.client.character.ArcaneRiverSkipModule;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.client.character.BossClearRecords;
import net.swordie.ms.client.character.HuntExpRecords;
import net.swordie.ms.client.character.StoryBossRecords;
import net.swordie.ms.client.character.BossMileage;
import net.swordie.ms.client.character.SwordieCoinRewards;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.info.ExpIncreaseInfo;
import net.swordie.ms.client.character.quest.QuestManagerHandler;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.party.Party;
import net.swordie.ms.client.party.PartyDamageInfo;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.MobPool;
import net.swordie.ms.connection.packet.MobSkillAttackInfo;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.connection.packet.field.WillFieldPacket;
import net.swordie.ms.connection.packet.BattleRecordMan;
import net.swordie.ms.constants.*;
import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.enums.EnterType;
import net.swordie.ms.events.Events;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.handlers.life.modules.MobModule;
import net.swordie.ms.life.AppearingFoothold;
import net.swordie.ms.life.DeathType;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.drop.Drop;
import net.swordie.ms.life.drop.DropInfo;
import net.swordie.ms.life.drop.DropTuning;
import net.swordie.ms.life.mob.skill.*;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.loaders.MobData;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.loaders.containerclasses.MobInfo;
import net.swordie.ms.loaders.containerclasses.MobSkillInfo;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.util.container.Triple;
import net.swordie.ms.util.container.Tuple;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Foothold;
import net.swordie.ms.world.field.fieldeffect.FieldEffect;
import net.swordie.ms.world.field.modules.EliteFieldModule;
import net.swordie.ms.world.field.modules.RuneStoneFieldModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Mob extends Life {
    private volatile Mob hpLinkMob;

    private static final Logger log = LogManager.getLogger();
    private static final long DR_REACTION_TIME = 3000; // 3 seconds
    private static final int TIGER_CHIEF = 9300811, THE_TIGERS_PROPOSAL = 38022;
    private static final int BANBAN_CLIENT_ONLY = 9990033; // in the client's is_banban_boss, not in v271 Mob.wz

    private int afterAttack;
    private int currentAction;
    private int scale;
    private int eliteGrade;
    private int eliteType;
    private int targetUserIdFromServer;
    private long hp;
    private long mp;
    private byte calcDamageIndex = 1, moveAction, appearType;
    private Position prevPos;
    private Foothold curFoodhold;
    private Foothold homeFoothold;
    private ShootingMoveStat shootingMoveStat;
    private ForcedMobStat forcedMobStat;
    private MobTemporaryStat temporaryStat;
    private int powerCrystalShare = 1;
    private Map<Char, Long> damageDone = new java.util.concurrent.ConcurrentHashMap<>(); // read by other threads
    private Set<DropInfo> drops = new HashSet<>();
    private Map<Integer, Long> skillCooldowns = new HashMap<>();
    private long nextPossibleSkillTime = 0;
    private List<MobSkill> eliteSkills = new ArrayList<>();
    private List<MobSkill> skillDelays = new CopyOnWriteArrayList<>();
    private boolean inAttack;
    private List<EscortDest> escortDest = new ArrayList<>();
    private int currentDestIndex = 0;
    private int escortStopDuration = 0;
    private int mobSpawnerId;
    private int attackCooldown;
    private long nextSummonPossible;
    private Map<String, Object> properties = new HashMap<>();
    private boolean broadcastLeaveField;
    private Set<MobSkill> blockedSkills;
    private Set<Integer> blockedAttacks;
    private Set<Integer> spawnedMobs;
    // summon skills marked summonOnce (skill * 1000 + level) this mob has already cast
    private final Set<Integer> summonedOnce = java.util.concurrent.ConcurrentHashMap.newKeySet();

    // Lotus
    private List<AppearingFoothold> appearingFootholds = new ArrayList<>();
    private List<Rect> purpleAttacks = new ArrayList<>();

    private boolean buffed;
    private long reflectTime;
    private Map<Tuple<Integer, Integer>, List<Rect>> skillDelayRects;
    private boolean split;
    private boolean isBlockEverything;
    private boolean isBlockAttack;
    private int option;
    private int effectItemID;
    private int phase;
    private int curZoneDataType;
    private int refImgMobID;
    private int lifeReleaseOwnerAID;
    private String lifeReleaseOwnerName;
    private String lifeReleaseMobName;
    private int summonEffect;
    private MobSkill forcedSkill;
    private Queue<Integer> hpTriggerBlockPercs;
    private long nextAggressiveRankingUpdate = 0;
    private long nextThreatNotice = 0;
    private MobSkill preparedSkill;
    private MobSkill preparedSkillFromAttack;
    private int mobGroupSpawnProgress = 0;

    public Mob(int templateId) {
        super(templateId);
        forcedMobStat = new ForcedMobStat();
        temporaryStat = new MobTemporaryStat(this);
        scale = 100;
        calcDamageIndex = 1;
        blockedSkills = new HashSet<>();
        blockedAttacks = new HashSet<>();
    }

    public Queue<Integer> getHpTriggerBlockPercs() {
        if (hpTriggerBlockPercs == null) {
            hpTriggerBlockPercs = new LinkedList<>();
        }
        return hpTriggerBlockPercs;
    }

    public void addHpTriggerBlockPerc(int triggerBlockPerc) {
        getHpTriggerBlockPercs().add(triggerBlockPerc);
    }

    public Set<DropInfo> getDrops() {
        return drops;
    }

    public void setDrops(Set<DropInfo> drops) {
        this.drops = drops;
    }

    public ForcedMobStat getForcedMobStat() {
        return forcedMobStat;
    }

    public void setForcedMobStat(ForcedMobStat forcedMobStat) {
        this.forcedMobStat = forcedMobStat;
    }

    public int getAfterAttack() {
        return afterAttack;
    }

    public void setAfterAttack(int afterAttack) {
        this.afterAttack = afterAttack;
    }

    public int getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(int currentAction) {
        this.currentAction = currentAction;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getEliteGrade() {
        return eliteGrade;
    }

    public void setEliteGrade(int eliteGrade) {
        this.eliteGrade = eliteGrade;
    }

    public int getEliteType() {
        return eliteType;
    }

    public void setEliteType(int eliteType) {
        this.eliteType = eliteType;
    }

    public int getTargetUserIdFromServer() {
        return targetUserIdFromServer;
    }

    public void setTargetUserIdFromServer(int targetUserIdFromServer) {
        this.targetUserIdFromServer = targetUserIdFromServer;
    }

    public long getHp() {
        return hp;
    }

    /**
     * Returns the current HP of the mob, in percentage in relation to the maximum HP.
     *
     * @return current HP percantage
     */
    public double getHpPerc() {
        return 100 * ((double) getHp() / getMaxHp());
    }

    public synchronized void setHp(long hp) {
        this.hp = hp;
    }

    public byte getCalcDamageIndex() {
        return calcDamageIndex;
    }

    public void setCalcDamageIndex(byte calcDamageIndex) {
        this.calcDamageIndex = calcDamageIndex;
    }

    public byte getMoveAction() {
        return moveAction;
    }

    public void setMoveAction(byte moveAction) {
        this.moveAction = moveAction;
    }

    public byte getAppearType() {
        return appearType;
    }

    public void setAppearType(byte appearType) {
        this.appearType = appearType;
    }

    public Position getPrevPos() {
        return prevPos;
    }

    public void setPrevPos(Position prevPos) {
        this.prevPos = prevPos;
    }

    public Foothold getCurFoodhold() {
        return curFoodhold;
    }

    public void setCurFoodhold(Foothold curFoodhold) {
        this.curFoodhold = curFoodhold;
    }

    public ShootingMoveStat getShootingMoveStat() {
        return shootingMoveStat;
    }

    public void setShootingMoveStat(ShootingMoveStat shootingMoveStat) {
        this.shootingMoveStat = shootingMoveStat;
    }

    public Foothold getHomeFoothold() {
        return homeFoothold;
    }

    public void setHomeFoothold(Foothold homeFoothold) {
        this.homeFoothold = homeFoothold;
    }

    public long getMaxHp() {
        return getForcedMobStat().getMaxHP();
    }

    public long getExp() {
        return getForcedMobStat().getExp();
    }

    public void setMaxHp(long maxHp) {
        getForcedMobStat().setMaxHP(maxHp);
    }

    public long getMp() {
        return mp;
    }

    public void setMp(long mp) {
        this.mp = mp;
    }

    public long getMaxMp() {
        return getForcedMobStat().getMaxMP();
    }

    public void setMaxMp(long maxMp) {
        getForcedMobStat().setMaxMP(maxMp);
    }

    public int getLevel() {
        return getForcedMobStat().getLevel();
    }

    public void setLevel(int level) {
        getForcedMobStat().setLevel(level);
    }

    public int getPad() {
        return getForcedMobStat().getPad();
    }

    public void setPad(int pad) {
        getForcedMobStat().setPad(pad);
    }

    public int getMad() {
        return getForcedMobStat().getMad();
    }

    public void setMad(int mad) {
        getForcedMobStat().setMad(mad);
    }

    public int getPdr() {
        return getForcedMobStat().getPdr();
    }

    public int getMdr() {
        return getForcedMobStat().getMdr();
    }

    public void setTemporaryStat(MobTemporaryStat temporaryStat) {
        this.temporaryStat = temporaryStat;
    }

    public MobTemporaryStat getTemporaryStat() {
        return temporaryStat;
    }

    public void damageBySkill(Char dmgDealer, int skillId, int slv, int hits, long damage) {
        this.getField().broadcastPacket(MobPool.damagedBySkill(this.getObjectId(), damage, dmgDealer.getId(), skillId, slv, hits));
        this.damage(dmgDealer, damage);
    }

    /**
     * Damages a mob.
     *
     * @param totalDamage the total damage that should be applied to the mob
     */
    public void damage(Char damageDealer, long totalDamage) {
        if (damageDealer != null && damageDealer.isSkipPromptProtected()) {
            return; // no damage (summons, familiars, ...) while the Arcane River skip prompt protects the player
        }
        Mob link = hpLinkMob;
        if (link != null && link != this && !link.isDead() && link.getField() == getField()) {
            link.damage(damageDealer, totalDamage);
            return;
        }
        var mobInfo = getMobInfo();
        if (isSplit() && getOriginMob() != null) {
            getOriginMob().damage(damageDealer, totalDamage);
        }

        if (getTemporaryStat().hasInvincibilityStat()) {
            return;
        }

        addDamage(damageDealer, totalDamage);

        long maxHP = getMaxHp();
        long oldHp = getHp();
        long newHp = oldHp - totalDamage;
        if (hpTriggerBlockPercs != null && hpTriggerBlockPercs.size() > 0) {
            var maxBlockedHp = getMaxHp() * (getHpTriggerBlockPercs().peek() / 100D);
            newHp = (long) Math.max(newHp, maxBlockedHp);
        } else {
            newHp = Math.max(newHp, 0);
        }
        long actualDamageDealt = oldHp - newHp;
        setHp(newHp);
        // GMS: Zakum's body and arms show their damage stage step by step, one MOB_PART_HIT per hit (not the last)
        if (actualDamageDealt > 0 && newHp > 0 && getField() != null) {
            String part = getMobInfo().getStagedHitPart();
            if (part != null) {
                getField().broadcastPacket(MobPool.partHit(this, part));
            }
        }
        if (actualDamageDealt > 0 && getField() != null && getField().getInstance() != null
                && getField().getInstance().getInstanceHelper() != null) {
            getField().getInstance().getInstanceHelper().onMobDamaged(this, damageDealer, actualDamageDealt);
        }

        // @bosses: count only HP the mob actually lost, excluding overkill and HP trigger blocks.
        if (damageDealer != null && actualDamageDealt > 0 && (isBoss() || getTemplateId() == MobConstants.FAKE_STRAW_SCARECROW)) {
            damageDealer.getDpsTracker().record(this, actualDamageDealt);
        }

        // Legion Raid
        if (MobConstants.isUnionRaidBoss(getTemplateId()) &&
                FieldConstants.isUnionRaidField(getField().getId()) &&
                damageDealer.getUnionRaid() != null) {
            damageDealer.getUnionRaid().damageDoneToRaidBoss(this, totalDamage, oldHp, newHp);
            return; // can't 'actually' damage mob.
        }

        damageDealer.getJobHandler().handleMobDamaged(damageDealer, this, actualDamageDealt);
        Events.onMobDamaged(damageDealer, this, actualDamageDealt, oldHp, newHp);

        double percDamage = ((double) newHp / maxHP);
        if (oldHp > 0 && newHp <= 0) {
            // Boss sponges
            if (getTemplateId() == BossConstants.HORNTAIL_EASY || getTemplateId() == BossConstants.HORNTAIL_NORMAL || getTemplateId() == BossConstants.CHAOS_HORNTAIL
                    || getTemplateId() == BossConstants.HORNTAIL_HARD) {
                new HashSet<>(getField().getMobs()).forEach(m -> m.die(true));
            }
            die(true);
            if (getTemplateId() == TIGER_CHIEF && damageDealer.hasQuestInProgress(THE_TIGERS_PROPOSAL)) {
                damageDealer.getScriptManager().setQRValue(THE_TIGERS_PROPOSAL, "clear", false);
            }
            if (mobInfo.isBoss() && mobInfo.getHpTagColor() != 0) {
                getField().broadcastPacket(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(this)));
            }
        } else if (mobInfo.isBoss() && mobInfo.getHpTagColor() != 0) {
            getField().broadcastPacket(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(this)));
            displayAggroRank();
        } else if (!mobInfo.isBoss()) {
            getField().broadcastPacket(MobPool.hpIndicator(getObjectId(), (byte) (percDamage * 100)));
        }

        damageCorrespondingSponge(damageDealer, actualDamageDealt);
    }

    private void damageCorrespondingSponge(Char damageDealer, long actualDamageDealt) {
        if ((getTemplateId() >= BossConstants.EASY_HORNTAIL_HEAD_A && getTemplateId() <= BossConstants.EASY_HORNTAIL_TAIL)) {
            Life life = damageDealer.getField().getLifeByTemplateId(BossConstants.HORNTAIL_EASY);
            if (life != null) {
                Mob mob = (Mob) life;
                mob.damage(damageDealer, actualDamageDealt);
                damageDealer.getField().broadcastPacket(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(mob)));
            }
        }
        if ((getTemplateId() >= BossConstants.NORMAL_HORNTAIL_HEAD_A && getTemplateId() <= BossConstants.NORMAL_HORNTAIL_TAIL)) {
            Life life = damageDealer.getField().getLifeByTemplateId(BossConstants.HORNTAIL_NORMAL);
            if (life != null) {
                Mob mob = (Mob) life;
                mob.damage(damageDealer, actualDamageDealt);
                damageDealer.getField().broadcastPacket(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(mob)));
            }
        }
        if ((getTemplateId() >= BossConstants.CHAOS_HORNTAIL_HEAD_A && getTemplateId() <= BossConstants.CHAOS_HORNTAIL_TAIL)) {
            Life life = damageDealer.getField().getLifeByTemplateId(BossConstants.CHAOS_HORNTAIL);
            if (life == null) {
                life = damageDealer.getField().getLifeByTemplateId(BossConstants.HORNTAIL_HARD);
            }
            if (life != null) {
                Mob mob = (Mob) life;
                mob.damage(damageDealer, actualDamageDealt);
                damageDealer.getField().broadcastPacket(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(mob)));
            }
        }
    }

    public MobInfo getMobInfo() {
        return MobData.getMobInfoById(getTemplateId());
    }

    public void displayAggroRank() {
        if (Util.getCurrentTimeLong() < nextAggressiveRankingUpdate) {
            return;
        }

        var dmgDone = getDamageDone();
        var rankInfoNames = new ArrayList<Tuple<Long, String>>();
        var totalDmgDone = dmgDone.values().stream().mapToLong(d -> d).sum();

        for (var entry : dmgDone.entrySet()) {
            var chr = entry.getKey();
            var dmg = entry.getValue();
            var dmgR = (int) (dmgDone.size() == 1 ? 100 : (((double) dmg / totalDmgDone) * 100D));

            rankInfoNames.add(new Tuple<>(dmg, String.format("%s(%d", chr.getName(), dmgR) + "%)"));
        }

        rankInfoNames.sort((o1, o2) -> o2.getLeft().compareTo(o1.getLeft()));

        var sortedList = rankInfoNames.stream().limit(6).collect(Collectors.toList());

        for (Char c : damageDone.keySet()) {
            c.write(UserLocal.aggroRankInfoName(sortedList));
        }
        long now = Util.getCurrentTimeLong();
        if (now >= nextThreatNotice && getField() != null) {
            // GMS: on the boss's first hits, then every minute
            nextThreatNotice = now + 60_000;
            dmgDone.entrySet().stream().max(Map.Entry.comparingByValue()).ifPresent(top -> getField().broadcastPacket(
                    FieldPacket.smartMobNotice(1, getTemplateId(), 3, 0,
                            net.swordie.ms.loaders.StringData.getMobStringById(getTemplateId()) + " considers "
                                    + top.getKey().getName() + " the most threatening enemy.")));
        }

        nextAggressiveRankingUpdate = Util.getCurrentTimeLong() + GameConstants.AGGRESSIVE_RANKING_UPDATE_DELAY;
    }

    public synchronized void heal(int amount) {
        long oldHp = getHp();
        long newHp = oldHp + amount;
        if (newHp > getMaxHp()) {
            newHp = getMaxHp();
        } else if (newHp < 0) {
            newHp = 0;
        }
        setHp(newHp);
        long diff = newHp - oldHp;
        if (getField() != null & diff != 0) {
            getField().broadcastPacket(MobPool.damaged(getObjectId(), getTemplateId(), diff, (byte) 0, getHp(), getMaxHp()));
        }
        if (oldHp > 0 && newHp <= 0) {
            die(true);
        }
    }

    public synchronized void healMP(int amount) {
        long oldMp = getMp();
        long newMp = oldMp + amount;
        if (newMp > getMaxMp()) {
            newMp = getMaxMp();
        } else if (newMp < 0) {
            newMp = 0;
        }
        setMp(newMp);
    }

    /**
     * Set by the first {@link #die(boolean)}: a mob can be killed by two attacks at the same time, and the death (drops,
     * EXP, quest kills, revives) must only run once.
     */
    private volatile boolean dead;

    public boolean isDead() {
        return dead;
    }

    private MobPatternDriver.State patternState;

    MobPatternDriver.State getPatternState() {
        return patternState;
    }

    void setPatternState(MobPatternDriver.State patternState) {
        this.patternState = patternState;
    }

    /**
     * For a mob that is spawned again after dying.
     */
    public void resetDeath() {
        dead = false;
    }

    public synchronized void die(boolean drops) {
        if (dead) {
            return;
        }
        dead = true;
        var mobInfo = getMobInfo();

        Field field = getField();
        setBroadcastLeaveField(true);
        Char killer = getMostDamageChar();
        if (killer != null) {
            field.broadcastPacket(MobPool.lastHitInfo(getObjectId(), killer.getId(), 3000));
        }
        getField().removeLife(getObjectId());
        for (Char c : new ArrayList<>(field.getChars())) {
            var scm = c.getScriptManager();
            if (scm != null) {
                try {
                    scm.notifyMobDeath(this);
                } catch (RuntimeException e) {
                    log.error("Mob death notify failed for " + c.getName(), e);
                }
            }
        }

        // Shade - Soul Splitter
        Mob copy = getCopyMob();
        if (copy != null) {
            copy.die(false);
        }
        if (isSplit()) {
            getField().broadcastPacket(MobPool.leaveField(getObjectId(), DeathType.ANIMATION_DEATH));
            return;
        }

        distributeExp();
        // Damien's phase 2 leaves its reward to the Restrained Sword of Destruction in the clear room
        // (DemianInstanceHelper spawns the sword with these drops); it drops nothing where it falls.
        boolean demianClearBoss = getTemplateId() == BossConstants.DEMIAN_NORMAL_TEMPLATE_ID_2
                || getTemplateId() == BossConstants.DEMIAN_HARD_TEMPLATE_ID_2;
        if (drops && !demianClearBoss && !FieldConstants.isNoItemDropField(getField().getId())) {
            dropDrops(); // xd
        }
        for (Char chr : getDamageDone().keySet()) {
            if (chr.hasSoulWeapon() && chr.hasNotMaxSoulMP()) {
                chr.createSoulOrb(this.getPosition(),
                        SoulWeaponData.getGaugeFromKill(chr.getLevel(), getForcedMobStat().getLevel()));
            }
            // Chaos Horntail is fought as 8810122 but collected as 8810118 (the Monster Collection's entry)
            int collectionId = getTemplateId() == BossConstants.HORNTAIL_HARD ? BossConstants.CHAOS_HORNTAIL : getTemplateId();
            if (!chr.getAccount().getMonsterCollection().hasMob(collectionId)) {
                chr.getAccount().getMonsterCollection().addMobAndUpdateClient(collectionId, chr);
            }
        }

        // Spread QuestCounter for MobKill throughout the entire party
        if (getMostDamageChar() != null) {
            var handleMobKillForQuestChrList = new ArrayList<Char>() {{
                add(getMostDamageChar());
                var party = getMostDamageChar().getParty();
                if (party != null) {
                    addAll(party.getPartyMembersInSameField(getMostDamageChar()));
                }
            }};
            handleMobKillForQuestChrList.forEach(pChr -> {
                QuestManagerHandler.handleMobKill(pChr.getQuestManager(), this);
                QuestManagerHandler.handleAreaKillCounters(pChr.getQuestManager(), this); // e.g. Kritias anti-magic points
                net.swordie.ms.client.character.achievement.MapleAchievements.onMobKill(pChr, this);
            });
        }
        Char mostDmgChr = getMostDamageChar();
        if (mostDmgChr != null) {
            RuneStoneFieldModule.trySpawnRune(getMostDamageChar(), getField());
            if (mostDmgChr.isBattleRecordOn()) {
                mostDmgChr.write(BattleRecordMan.enemiesDefeatedInc());
            }
        }

        MobTemporaryStat tsm = getTemporaryStat();
        tsm.removeAllMobStats(false);

        EliteFieldModule.checkSpawnElite(field, this);

        // TEST
        reviveMob();
        if (mobInfo.isBoss() && mobInfo.getHpTagColor() != 0) {
            getField().broadcastPacket(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(this)));
        } else {
            getField().broadcastPacket(MobPool.hpIndicator(getObjectId(), (byte) 0));
        }
        if (isBroadcastLeaveField()) {
            // do this later so all relevant packets arrive before indicating mob has died
            field.broadcastPacket(MobPool.leaveField(getObjectId(), DeathType.ANIMATION_DEATH));
        }

        // Mobs that are part of a boss but have their own templateId (Such as GrossoPolpo and his Arms)
        var attachedMobs = MobConstants.getAttachedMobs(getTemplateId());
        for (var attachedMob : attachedMobs) {
            var life = getField().getLifeByTemplateId(attachedMob);
            if (life instanceof Mob) {
                var mob = (Mob) life;
                mob.die(false);
            }
        }

        killHorntailIfAllPartsDead();
        getField().onMobDeath(this);
        clear();
    }

    public void dropDrops() {
        dropDrops(false);
    }

    /** the basic symbol counts as the Vanishing Journey symbol */
    private static int vanishingJourneyAsOne(int symbolId) {
        return symbolId == ArcaneRiverSkipModule.BASIC_SYMBOL ? ItemConstants.ARCANE_SYMBOL_VANISHING_JOURNEY : symbolId;
    }

    /**
     * @return true if the Char has a max level copy of this Arcane Symbol equipped or in their Equip inventory
     * (the basic symbol and the Vanishing Journey symbol are both Vanishing Journey)
     */
    private static boolean hasMaxedArcaneSymbol(Char chr, int symbolId) {
        int region = vanishingJourneyAsOne(symbolId);
        for (var inv : List.of(chr.getEquippedInventory(), chr.getEquipInventory())) {
            for (Item item : inv.getItems()) {
                int id = vanishingJourneyAsOne(item.getItemId());
                // Sacred Symbols too, at their own max level
                int max = ItemConstants.getMaxSymbolLevel(id);
                if (id == region && item instanceof Equip && ((Equip) item).getSymbolLevel() >= max) {
                    return true;
                }
            }
        }
        return false;
    }

    // Horntail's preliminary heads in the Caves of Trial (normal, chaos) drop nothing
    private static final java.util.Set<Integer> NO_DROP_MOBS = java.util.Set.of(BossConstants.NORMAL_HORNTAIL_HEAD_1,
            BossConstants.NORMAL_HORNTAIL_HEAD_2, BossConstants.CHAOS_HORNTAIL_HEAD_1, BossConstants.CHAOS_HORNTAIL_HEAD_2);

    public void dropDrops(boolean bypassRuneAntiBot) {
        if (NO_DROP_MOBS.contains(getTemplateId())) {
            return;
        }
        int ownerID = 0;
        Char mostDamageChar = getMostDamageChar();
        if (mostDamageChar != null && mostDamageChar.inBossPracticeRun()) {
            return;
        }

        if (MobConstants.isPublicDropsMob(getTemplateId())) { // Ensure that drops are not locked to an owner.
            mostDamageChar = null;
        }

        int level = mostDamageChar == null ? 0 : mostDamageChar.getLevel();
        short job = 0;
        if (mostDamageChar != null) {
            ownerID = mostDamageChar.getId();
            job = mostDamageChar.getJob();
        }
        int fhID = getFh();
        if (fhID == 0) {
            Foothold fhBelow = getField().findFootHoldBelow(getPosition());
            if (fhBelow != null) {
                fhID = fhBelow.getId();
            }
        }
        Set<DropInfo> dropInfoSet = new HashSet<>(getDrops());
        // monsters that are part of a boss fight without being the boss (Gollux's trash, Horntail's wyverns, ...)
        // only drop what their own drop table has for them (e.g. Gollux Pennies): no mesos, no generic items
        boolean bossFightAdd = isBossFightAdd();
        if (bossFightAdd) {
            dropInfoSet.removeIf(DropInfo::isMoney);
            // no equips either, even ones on their own table
            dropInfoSet.removeIf(di -> !di.isMoney() && ItemConstants.isEquip(di.getItemID()));
        }

        // Add drops based on loot pool of the mob
        dropInfoSet.addAll(BossLootConstants.getLootPoolByTemplateId(getTemplateId(), getField().getId()));

        // Honor EXP (Inner Ability rerolls): Medals of Honor from monsters, a Boss Medal of Honor from every boss clear
        if (MobConstants.isBossClearNxMob(getTemplateId())) {
            dropInfoSet.add(new DropInfo(ItemConstants.BOSS_MEDAL_OF_HONOR, 10000));
        } else if (!isBoss() && !bossFightAdd && getForcedMobStat().getLevel() >= 30) {
            int medalChance = ServerFeatures.getInt(ServerFeatures.Feature.MEDAL_OF_HONOR_CHANCE);
            if (medalChance > 0) {
                dropInfoSet.add(new DropInfo(ItemConstants.MEDAL_OF_HONOR, medalChance));
            }
        }
        // souls (Soul Weapons): bosses drop their own souls now and then
        int soul = SoulWeaponData.rollSoulDrop(getTemplateId(), MobConstants.isBossClearNxMob(getTemplateId()), isBoss());
        if (soul != 0) {
            dropInfoSet.add(new DropInfo(soul, 10000));
        }
        // cubes and flames by monster level; boss clears drop cubes more often
        net.swordie.ms.life.drop.CubeDrops.add(dropInfoSet, getTemplateId(), getForcedMobStat().getLevel(), isBoss(),
                bossFightAdd);
        // Sol Erda energy and fragments from Lv. 260+ Grandis monsters
        if (!bossFightAdd) {
            net.swordie.ms.client.character.hexa.HexaEffects.addMobDrops(mostDamageChar, getField().getId(),
                    getForcedMobStat().getLevel(), isBoss(), dropInfoSet);
        }

        // Add consumable/equip drops based on min(charLv, mobLv); bosses keep to their own loot
        level = Math.min(level, getForcedMobStat().getLevel());
        if (!bossFightAdd && !isBoss() && !MobConstants.isBossClearNxMob(getTemplateId())) {
            dropInfoSet.addAll(ItemConstants.getConsumableMobDrops(level));
            dropInfoSet.addAll(ItemConstants.getEquipMobDrops(job, level));
        }


        // in Gollux's maps only the reward monster drops equips: parts and trash give teleport rocks and the like
        if (BossConstants.isGolluxFightField(getField().getId())
                && (getTemplateId() < BossConstants.GOLLUX_REWARD_EASY || getTemplateId() > BossConstants.GOLLUX_REWARD_HELL)) {
            dropInfoSet.removeIf(di -> !di.isMoney() && ItemConstants.isEquip(di.getItemID()));
        }

        // QoL: equips only drop if the killer (or a party member on this map) can use them: class, weapon type and
        // gender. The same goes for throwing stars, arrows and bullets (claw, bow, crossbow and gun classes only).
        // Mobs with public drops keep all their drops.
        if (ServerFeatures.on(ServerFeatures.Feature.EQUIP_DROPS_FOR_KILLER_ONLY) && mostDamageChar != null) {
            List<Char> users = new ArrayList<>();
            users.add(mostDamageChar);
            if (mostDamageChar.getParty() != null) {
                for (Char member : mostDamageChar.getParty().getOnlineChars()) {
                    if (member != mostDamageChar && member.getField() == getField()) {
                        users.add(member);
                    }
                }
            }
            dropInfoSet.removeIf(di -> !di.isMoney()
                    && !net.swordie.ms.client.character.items.EquipDropFilter.isUsableByAny(di.getItemID(), users));
        }

        // QoL: no more drops of an Arcane or Sacred Symbol the killer already has at max level (equipped or in the Equip
        // tab)
        if (ServerFeatures.on(ServerFeatures.Feature.NO_MAXED_SYMBOL_DROPS) && mostDamageChar != null) {
            Char killer = mostDamageChar;
            dropInfoSet.removeIf(di -> {
                if (di.isMoney()) {
                    return false;
                }
                int id = DropTuning.symbolOfCoupon(di.getItemID()); // a Grandis coupon counts as its symbol
                id = id != 0 ? id : di.getItemID();
                return ItemConstants.hasSymbolBlock(id) && hasMaxedArcaneSymbol(killer, id);
            });
        }

        // DropRate & MesoRate Increases
        int mostDamageCharDropRate = mostDamageChar != null ? mostDamageChar.getTotalStat(BaseStat.dropR) : 100;
        if (mostDamageChar != null) { // 2x drop coupon: +100%
            mostDamageCharDropRate += (net.swordie.ms.client.character.items.RateCoupons.getRate(mostDamageChar,
                    net.swordie.ms.client.character.items.RateCoupons.DROP) - 1) * 100;
        }
        mostDamageCharDropRate *= ServerRates.getDropRate();
        mostDamageCharDropRate = (int) (mostDamageCharDropRate
                * net.swordie.ms.client.character.DeathPenaltyModule.dropFactor(mostDamageChar));
        int mostDamageCharMesoRate = mostDamageChar != null ? mostDamageChar.getTotalStat(BaseStat.mesoR) : 100;
        mostDamageCharMesoRate *= ServerRates.getMesoRate();
        int dropRateMob = (getTemporaryStat().hasCurrentMobStat(MobStat.Treasure)
                ? getTemporaryStat().getCurrentOptionsByMobStat(MobStat.Treasure).yOption
                : 0); // Item Drop Rate
        int mesoRateMob = (getTemporaryStat().hasCurrentMobStat(MobStat.Treasure)
                ? getTemporaryStat().getCurrentOptionsByMobStat(MobStat.Treasure).zOption
                : 0); // Meso Drop Rate

        var runeAntibotRate = 1D;
        if (!bypassRuneAntiBot) {
            runeAntibotRate = RuneStoneFieldModule.getRuneCurseMultiplier(getMostDamageChar(), getField());
        }
        int totalMesoRate = (int) ((mesoRateMob + mostDamageCharMesoRate) * (runeAntibotRate / 100D));
        int totalDropRate = (int) ((dropRateMob + mostDamageCharDropRate) * (runeAntibotRate / 100D));
        if (ServerFeatures.on(ServerFeatures.Feature.NO_RECIPE_DROPS)) {
            dropInfoSet.removeIf(di -> !di.isMoney() && ItemConstants.isCraftingRecipe(di.getItemID()));
        }
        if (ServerFeatures.on(ServerFeatures.Feature.NO_GENERIC_SCROLL_DROPS)) {
            dropInfoSet.removeIf(di -> !di.isMoney() && ItemConstants.isGenericEquipScroll(di.getItemID()));
        }
        net.swordie.ms.life.drop.DropTuning.apply(dropInfoSet, getTemplateId());
        scaleSpellTraceDrops(dropInfoSet, ServerRates.getSpellTraceRate());
        int bossEquipBoost = ServerFeatures.getInt(ServerFeatures.Feature.BOSS_EQUIP_BOOST);
        if (bossEquipBoost != 0 && isBoss()) {
            boostBossEquipDrops(dropInfoSet, totalDropRate, bossEquipBoost);
        }
        // read once per kill, so a settings reload can't change them halfway through
        boolean autoLootOn = ServerFeatures.on(isBoss() ? ServerFeatures.Feature.AUTO_LOOT_BOSS
                : ServerFeatures.Feature.AUTO_LOOT);
        boolean silentOn = ServerFeatures.on(ServerFeatures.Feature.SILENT_LOOT);
        Set<Drop> dropsBefore = autoLootOn ? new HashSet<>(getField().getDrops()) : null;
        // QoL auto-loot: the drops go straight into the inventories of the players who fought the mob (and party members
        // on the map), handed out in turns among the ones allowed to pick each drop up, so nobody gets loot that wasn't
        // theirs to take. Works the same solo, in a party (also the one-person party boss NPCs make) and for bosses.
        boolean autoLoot = autoLootOn && dropsBefore != null && mostDamageChar != null;
        // silent: the auto-looted drops are never shown falling and flying to the player (less client lag); the ones
        // nobody could take are shown afterwards
        boolean silent = autoLoot && silentOn;
        getField().drop(dropInfoSet, getField().getInfo().getFootholdById(fhID), getPosition(), ownerID, totalMesoRate,
                totalDropRate, getMobInfo().getExplosiveReward() != 0, this, silent);
        // as in GMS, every fighter still on the map gets their own crystal, the boss's value split between them
        if (BossLootConstants.isDropPowerCrystal(getTemplateId())) {
            Map<Integer, Char> fighters = new LinkedHashMap<>();
            for (Char chr : getDamageDone().keySet()) {
                if (isLootable(chr)) {
                    fighters.putIfAbsent(chr.getId(), chr);
                }
            }
            powerCrystalShare = Math.max(1, fighters.size());
            for (int id : fighters.keySet()) {
                getField().drop(new DropInfo(BossLootConstants.isWeeklyCrystal(getTemplateId())
                                ? ItemConstants.INTENSE_POWER_CRYSTAL_WEEKLY : ItemConstants.INTENSE_POWER_CRYSTAL, 10000), getPosition(), getPosition(),
                        id, false, this, silent);
            }
        }

        if (autoLoot) {
            Set<Drop> newDrops = new HashSet<>(getField().getDrops());
            newDrops.removeAll(dropsBefore);
            autoLootBossDrops(mostDamageChar, newDrops);
            if (silent) {
                for (Drop drop : newDrops) {
                    getField().showDrop(drop, getPosition());
                }
            }
        }

        // VacItems
        if (mostDamageChar != null && !(mostDamageChar.hasSkillOnCooldown(CustomConstants.PET_VAC_SKILL_ID))) {
            mostDamageChar.vacItems();
            mostDamageChar.addSkillCoolTime(CustomConstants.PET_VAC_SKILL_ID, CustomConstants.PET_VAC_DELAY);
        }
    }

    /**
     * Raises the odds of every equip drop (used for bosses), going by the odds the killer actually gets with the drop
     * rate: below {@link GameConstants#BOSS_EQUIP_LOW_ODDS_BELOW}% they become {@link GameConstants#BOSS_EQUIP_LOW_ODDS}%,
     * below {@link GameConstants#BOSS_EQUIP_MID_ODDS_BELOW}% they become {@link GameConstants#BOSS_EQUIP_MID_ODDS}%, and
     * higher odds go up by bonusPercent %. Works on copies: the DropInfos are shared.
     */
    private static void boostBossEquipDrops(Set<DropInfo> dropInfoSet, int dropRate, int bonusPercent) {
        if (dropRate <= 0) {
            return;
        }
        double factor = 1 + bonusPercent / 100D;
        List<DropInfo> boosted = new ArrayList<>();
        for (Iterator<DropInfo> it = dropInfoSet.iterator(); it.hasNext(); ) {
            DropInfo di = it.next();
            if (di.isMoney() || di.getChance() <= 0 || !ItemConstants.isEquip(di.getItemID())) {
                continue;
            }
            // odds in % with the drop rate, the same way DropInfo#willDrop rolls them
            double odds = di.getChance() * (dropRate / 100D) * 100D / GameConstants.MAX_DROP_CHANCE;
            if (odds >= 100D) {
                continue; // already certain
            }
            double newOdds;
            if (odds < GameConstants.BOSS_EQUIP_LOW_ODDS_BELOW) {
                newOdds = GameConstants.BOSS_EQUIP_LOW_ODDS;
            } else if (odds < GameConstants.BOSS_EQUIP_MID_ODDS_BELOW) {
                newOdds = GameConstants.BOSS_EQUIP_MID_ODDS;
            } else {
                newOdds = odds * factor;
            }
            newOdds = Math.min(100D, Math.max(odds, newOdds));
            // back to a chance before the drop rate (rounded up, so the odds are at least what was asked for)
            long chance = (long) Math.ceil(newOdds / 100D * GameConstants.MAX_DROP_CHANCE / (dropRate / 100D));
            DropInfo copy = di.deepCopy();
            copy.setChance((int) Math.min(Integer.MAX_VALUE / 100L, Math.max(di.getChance(), chance)));
            it.remove();
            boosted.add(copy);
        }
        dropInfoSet.addAll(boosted);
    }

    /**
     * Multiplies the Spell Traces each drop gives by the server's Spell Trace rate (1 leaves the drop tables' amounts).
     * Works on copies: the DropInfos are shared.
     */
    private static void scaleSpellTraceDrops(Set<DropInfo> dropInfoSet, double rate) {
        if (rate == 1D) {
            return;
        }
        var info = net.swordie.ms.loaders.ItemData.getItemInfoByID(ItemConstants.SPELL_TRACE_ID);
        int cap = info != null && info.getSlotMax() > 0 ? info.getSlotMax() : GameConstants.SPELL_TRACE_SLOT_MAX;
        List<DropInfo> scaled = new ArrayList<>();
        for (Iterator<DropInfo> it = dropInfoSet.iterator(); it.hasNext(); ) {
            DropInfo di = it.next();
            if (di.isMoney() || di.getItemID() != ItemConstants.SPELL_TRACE_ID) {
                continue;
            }
            DropInfo copy = di.deepCopy();
            copy.setMinQuant((int) Math.max(1, Math.min(cap, Math.round(di.getMinQuant() * rate))));
            copy.setMaxQuant((int) Math.max(copy.getMinQuant(), Math.min(cap, Math.round(di.getMaxQuant() * rate))));
            copy.generateNextDrop();
            it.remove();
            scaled.add(copy);
        }
        dropInfoSet.addAll(scaled);
    }

    /**
     * Loot goes straight into the inventories of the players who fought the mob (on this map): dealt out in turns,
     * each drop to the next player allowed to pick it up (the killer's party, or anyone once a drop is free for all).
     * A drop nobody can take or hold stays on the ground.
     */
    private void autoLootBossDrops(Char killer, Set<Drop> drops) {
        List<Char> looters = new ArrayList<>();
        if (isLootable(killer)) {
            looters.add(killer);
        }
        for (Char chr : getDamageDone().keySet()) {
            if (chr != killer && isLootable(chr)) {
                looters.add(chr);
            }
        }
        if (killer != null && killer.getParty() != null) {
            for (Char member : killer.getParty().getOnlineChars()) {
                if (!looters.contains(member) && isLootable(member)) {
                    looters.add(member);
                }
            }
        }
        if (looters.isEmpty()) {
            return; // the drops stay on the ground
        }
        int next = 0;
        for (Drop drop : drops) {
            // equipment goes to a fighter who can use it (a party's warrior gets the axe), else in turn as before
            int itemId = drop.getItem() == null ? 0 : drop.getItem().getItemId();
            boolean equip = itemId != 0 && ItemConstants.isEquip(itemId);
            boolean anyCanUse = equip && looters.stream().anyMatch(l -> drop.canBePickedUpBy(l)
                    && net.swordie.ms.client.character.items.EquipDropFilter.isUsableBy(itemId, l));
            for (int i = 0; i < looters.size(); i++) {
                Char looter = looters.get((next + i) % looters.size());
                if (!drop.canBePickedUpBy(looter)) {
                    continue;
                }
                if (anyCanUse && !net.swordie.ms.client.character.items.EquipDropFilter.isUsableBy(itemId, looter)) {
                    continue;
                }
                looter.autoLootDrops(Collections.singleton(drop));
                if (getField().getLifeByObjectID(drop.getObjectId()) != drop) { // picked up
                    next = (next + i + 1) % looters.size();
                    break;
                }
            }
        }
    }

    /**
     * @return whether the Char can get auto-looted drops: still online and still on this map (a Char that logged out
     * or left keeps its field reference, and items put in its inventory after its last save would be lost)
     */
    /** How many fighters this kill's crystals are split between. */
    public int getPowerCrystalShare() {
        return powerCrystalShare;
    }

    private boolean isLootable(Char chr) {
        return chr != null && chr.isOnline() && chr.getField() == getField()
                && getField().getCharByID(chr.getId()) == chr;
    }

    /**
     * Horntail's parts (heads, hands, wings, legs, tail) and the invisible Horntail that holds the fight's HP and
     * loot: once every part of a fight is dead, Horntail dies too, even if the damage to the parts didn't add up to all
     * of its HP.
     */
    private static final int[][] HORNTAIL_PARTS = {
            // first part, last part, Horntail ids
            {BossConstants.NORMAL_HORNTAIL_HEAD_A, BossConstants.NORMAL_HORNTAIL_TAIL, BossConstants.HORNTAIL_NORMAL},
            {BossConstants.CHAOS_HORNTAIL_HEAD_A, BossConstants.CHAOS_HORNTAIL_TAIL, BossConstants.CHAOS_HORNTAIL,
                    BossConstants.HORNTAIL_HARD},
            {BossConstants.EASY_HORNTAIL_HEAD_A, BossConstants.EASY_HORNTAIL_TAIL, BossConstants.HORNTAIL_EASY},
    };

    private static int[] getHorntailFight(int templateId) {
        for (int[] fight : HORNTAIL_PARTS) {
            if (templateId >= fight[0] && templateId <= fight[1]) {
                return fight;
            }
        }
        return null;
    }

    private void killHorntailIfAllPartsDead() {
        int[] fight = getHorntailFight(getTemplateId());
        Field field = getField();
        if (fight == null || field == null) {
            return;
        }
        for (Mob mob : field.getMobs()) {
            if (mob != this && !mob.isDead() && mob.getTemplateId() >= fight[0] && mob.getTemplateId() <= fight[1]) {
                return; // a part is still alive
            }
        }
        for (Mob mob : new ArrayList<>(field.getMobs())) {
            for (int i = 2; i < fight.length; i++) {
                if (mob.getTemplateId() == fight[i] && !mob.isDead()) {
                    mob.setHp(0);
                    mob.die(true);
                }
            }
        }
    }

    public Map<Char, Long> getDamageDone() {
        return damageDone;
    }

    public void setDamageDone(Map<Char, Long> damageDone){
        this.damageDone = damageDone;
    }

    public long getDamageDone(Char chr) {
        return chr == null ? 0L : getDamageDone().getOrDefault(chr, 0L);
    }

    /**
     * Adds a damage amount to the given Char's current damage. Purely used for keeping track of total damage done by
     * a Char.
     *
     * @param chr    the Char the damage originates from
     * @param damage the damage done
     */
    public synchronized void addDamage(Char chr, long damage) {
        if (chr == null) {
            return;
        }
        long cur = 0;
        if (getDamageDone().containsKey(chr)) {
            cur = getDamageDone().get(chr);
        }
        if (damage <= getHp()) {
            cur += damage;
        } else {
            cur += getHp();
        }
        getDamageDone().put(chr, cur);
    }

    public void distributeExp() {
        boolean bossFightAdd = isBossFightAdd(); // no NX from a boss fight's adds
        long exp = getForcedMobStat().getExp();
        long totalDamage = getDamageDone().values().stream().mapToLong(l -> l).sum();
        Map<Party, PartyDamageInfo> damagePercPerParty = new HashMap<>();
        for (Char chr : getDamageDone().keySet()) {
            double damagePerc = getDamageDone().getOrDefault(chr, 0L) / (double) Math.max(1, totalDamage);
            int mobExpRate = chr.getCurrentLevelExpRate();
            long appliedExpPre = (long) (exp * damagePerc * mobExpRate);
            long appliedExpPost = appliedExpPre;
            ExpIncreaseInfo eei = new ExpIncreaseInfo();

            // Burning Field
            if (getField().getBurningFieldLevel() > 0) {
                // long: with the EXP curve's high multipliers a kill can be worth more than an int
                long burningFieldBonusExp = appliedExpPre * getField().getBonusExpByBurningFieldLevel() / 100;
                eei.setRestFieldBonusExp(Util.maxInt(burningFieldBonusExp)); // shown in the EXP message
                eei.setRestFieldExpRate(getField().getBonusExpByBurningFieldLevel());
                appliedExpPost += burningFieldBonusExp;
            }

            // + Exp% MobStats
            if (getTemporaryStat().hasCurrentMobStat(MobStat.Treasure) && getTemporaryStat().getCurrentOptionsByMobStat(MobStat.Treasure).xOption > 0) { // xOption for Exp%
                int expIncrease = getTemporaryStat().getCurrentOptionsByMobStat(MobStat.Treasure).xOption;
                long mobStatBonusExp = ((appliedExpPre * expIncrease) / 100);
                eei.setBaseAddExp((int) mobStatBonusExp);
                appliedExpPost += mobStatBonusExp;
            }

            // the guild's Guild Expertise (+5% from normal monsters Lv. 101-200)
            int guildExpR = net.swordie.ms.handlers.social.module.GuildModule.getExpertiseExpR(chr,
                    getForcedMobStat().getLevel(), isBoss());
            if (guildExpR > 0) {
                appliedExpPost += appliedExpPre * guildExpR / 100;
            }

            // 2x/3x EXP coupon (Cash tab, in its time window)
            int couponRate = net.swordie.ms.client.character.items.RateCoupons.getRate(chr,
                    net.swordie.ms.client.character.items.RateCoupons.EXP);
            if (couponRate > 1) {
                long couponExp = appliedExpPre * (couponRate - 1);
                eei.setItemBonusExp(Util.maxInt(couponExp));
                appliedExpPost += couponExp;
            }

            // Rune anti-bot multi
            var runeCurseMulti = RuneStoneFieldModule.getRuneCurseMultiplier(chr, getField()) / 100D;
            appliedExpPost *= runeCurseMulti;

            if (runeCurseMulti < 1D) {
                getField().getRuneStone().showRuneCurseMessageToChr(chr);
            }

            // death debuff: hunting EXP -80%
            double deathFactor = net.swordie.ms.client.character.DeathPenaltyModule.rateFactor(chr);
            if (deathFactor < 1D) {
                appliedExpPost = (long) (appliedExpPost * deathFactor);
                appliedExpPre = (long) (appliedExpPre * deathFactor);
            }

            eei.setLastHit(true);
            eei.setIncEXP(appliedExpPre);
            // Sprout party bonus and rested EXP (new player help)
            appliedExpPost += net.swordie.ms.client.character.NewPlayerModule.getKillBonus(chr, appliedExpPost, eei);
            chr.addExp(appliedExpPost, eei);
            net.swordie.ms.client.character.hexa.SixthJobQuest.onHuntExp(chr, appliedExpPost); // Arcane Stones
            HuntExpRecords.onHuntExp(chr, appliedExpPost); // quest records filled by hunting
            boolean raidField = getField() != null && net.swordie.ms.constants.FieldConstants.isUnionRaidField(getField().getId());
            if (isBoss() && chr.getGuild() != null && !raidField) {
                net.swordie.ms.client.guild.GuildPointsModule.onBossKill(chr, this); // independent of EXP buffs
            }
            if (chr.getGuild() != null && !raidField) { // Legion raid mobs aren't hunting
                try {
                    net.swordie.ms.client.guild.GuildMission.onMobKill(chr, this);
                } catch (Exception e) {
                    log.error("Guild mission points failed", e);
                }
            }

            if (MobConstants.isBossClearNxMob(getTemplateId())
                    || !bossFightAdd && Util.succeedProp(GameConstants.NX_DROP_CHANCE)) {
                double runeCurse = RuneStoneFieldModule.getRuneCurseMultiplier(chr, getField()) / 100D;
                int nx = (int) (damagePerc * getNxDropAmount() * runeCurse);
                if (nx <= 0 && getNxDropAmount() > 0 && damagePerc > 0 && runeCurse > 0) {
                    nx = 1; // a part of a kill still gives at least 1 NX
                }
                chr.addNx(nx);
            }

            Party party = chr.getParty();
            if (party != null) {
                if (!damagePercPerParty.containsKey(party)) {
                    damagePercPerParty.put(party, new PartyDamageInfo(party, this));
                }
                damagePercPerParty.get(party).addDamageInfo(chr, damagePerc);
            }
        }

        for (PartyDamageInfo pdi : damagePercPerParty.values()) {
            pdi.distributeExp(getMostDamageChar());
        }

        // 5 Swordie Coins for each boss's first clear of the day
        SwordieCoinRewards.onMobKilled(this);
        try {
            net.swordie.ms.client.character.union.UnionArtifactModule.onMobKilled(this); // artifact hunting missions
        } catch (Exception e) {
            log.error("Union Artifact mob count failed", e);
        }
        try {
            net.swordie.ms.client.character.DailyGiftModule.onMobKilled(this); // the Daily Gift hunting goal
        } catch (Exception e) {
            log.error("Daily Gift mob count failed", e);
        }
        try {
            BossClearRecords.onMobKilled(this); // boss window and Contents Guide checks
        } catch (Exception e) {
            log.error("Boss clear records failed", e);
        }
        try {
            StoryBossRecords.onMobKilled(this); // story quests' boss records
        } catch (Exception e) {
            log.error("Story boss records failed for mob " + getTemplateId(), e);
        }
        try {
            BossMileage.onMobKilled(this); // daily boss Reward Points
        } catch (Exception e) {
            log.error("Boss Reward Points failed for mob {} in field {}", getTemplateId(), getField() == null ? 0 : getField().getId(), e);
        }
        // event finds (Halloween ticket pieces, Secret Agent items) and kill counters
        net.swordie.ms.world.SeasonalEvents.onMobKilled(this);
    }

    public Char getMostDamageChar() {
        Tuple<Char, Long> max = new Tuple<>(null, (long) -1);
        for (Map.Entry<Char, Long> entry : getDamageDone().entrySet()) {
            Char chr = entry.getKey();
            long damage = entry.getValue();
            if (damage > max.getRight()) {
                max.setLeft(chr);
                max.setRight(damage);
            }
        }
        return max.getLeft();
    }

    public MobSkill getAttackById(int attackID) {
        return Util.findWithPred(getMobInfo().getAttacks(), att -> att.getSkillIdx() == attackID);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Mob) {
            Mob mob = (Mob) obj;
            return mob.getTemplateId() == getTemplateId() && mob.getObjectId() == getObjectId() && mob.getField().equals(getField());
        }
        return false;
    }

    public boolean isAlive() {
        return getHp() > 0;
    }

    public void reviveMob() {
        var revives = getMobInfo().getRevives();
        if (revives.size() > 0) {
            for (int reviveTemplateID : revives) {

                if (MobConstants.isDoNotReviveMob(reviveTemplateID)) {
                    continue;
                }

                Mob mob = MobData.getMobDeepCopyById(reviveTemplateID);
                if (mob == null) {
                    log.warn("Mob.java::reviveMob() | Could not find mob with template " + reviveTemplateID);
                    continue;
                }
                mob.setNotRespawnable(true);
                mob.setPosition(getPosition());

                mob = MobConstants.alterMobRevive(mob); // Slightly alter the mob revival based on mob's info.
                mob.setAppearType((byte) -3); // GMS: revived, with the dead mob's object id
                mob.setOption(getObjectId());

                getField().spawnLife(mob, null);
                mob.setAppearType((byte) -2);

                // game pls
                MobModule.afterMobSpawnByRevive(mob);
            }
        }
    }

    private Map<Integer, Long> getSkillCooldowns() {
        return skillCooldowns;
    }

    public boolean hasSkillOffCooldown(int skillID, int slv) {
        return System.currentTimeMillis() >= getSkillCooldowns().getOrDefault(skillID | (slv << 16), Long.MIN_VALUE);
    }

    public boolean hasAttackOffCooldown(int attackID) {
        return System.currentTimeMillis() >= getSkillCooldowns().getOrDefault(-attackID, Long.MIN_VALUE);
    }

    public void putSkillCooldown(int skillID, int slv, long nextUseableTime) {
        getSkillCooldowns().put(skillID | (slv << 16), nextUseableTime);
    }

    public void putAttackOnCooldown(int skillID, int delayForNextAttack) {
        getSkillCooldowns().put(-skillID, System.currentTimeMillis() + delayForNextAttack);
    }

    public boolean hasSkillDelayExpired() {
        return System.currentTimeMillis() > getNextPossibleSkillTime();
    }

    /**
     * Sets when a next skill can be used (in ms from current time).
     *
     * @param delay The delay until the next skill can be used
     */
    public void setSkillDelay(long delay) {
        setNextPossibleSkillTime(System.currentTimeMillis() + delay);
    }

    private long getNextPossibleSkillTime() {
        return nextPossibleSkillTime;
    }

    private void setNextPossibleSkillTime(long nextPossibleSkillTime) {
        this.nextPossibleSkillTime = nextPossibleSkillTime;
    }

    @Override
    public void broadcastSpawnPacket(Char onlyChar, EnterType enterType) {
        Char controller = getField().getLifeToControllers().get(this);
        if (onlyChar == null) {
            for (Char chr : getField().getCharsReadOnly()) {
                chr.write(MobPool.enterField(this, false));
                writeControl(chr, controller == chr);
                sendTriggerBlock(chr);
            }
        } else {
            onlyChar.write(MobPool.enterField(this, false));
            writeControl(onlyChar, controller == onlyChar);
            sendTriggerBlock(onlyChar);
        }
    }

    private void sendTriggerBlock(Char chr) {
        if (hpTriggerBlockPercs != null) {
            chr.write(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(this)));
            switch (BossConstants.getWillPhase(getField().getId())) {
                case 1:
                    chr.write(WillFieldPacket.willTriggerBlockP1(hpTriggerBlockPercs, Collections.singletonList(getTemplateId())));
                    break;
                case 2:
                    chr.write(WillFieldPacket.willTriggerBlockP2(hpTriggerBlockPercs));
                    break;
            }
        }
    }

    public void spawnEliteMobRuneOfDarkness() {
        Mob elite = MobData.getMobDeepCopyById(getTemplateId());
        elite.setHomePosition(getPosition().deepCopy());
        elite.setPosition(getPosition().deepCopy());
        elite.setCurFoodhold(getCurFoodhold().deepCopy());
        elite.setHomeFoothold(getCurFoodhold().deepCopy());
        elite.setNotRespawnable(true);
        List<Triple<Integer, Double, Double>> eliteInfos = GameConstants.getEliteInfoByMobLevel(elite.getForcedMobStat().getLevel());
        Triple<Integer, Double, Double> eliteInfo = Util.getRandomFromCollection(eliteInfos);
        int eliteGrade = eliteInfo.getLeft();
        long newHp = (long) (eliteInfo.getMiddle() * elite.getMaxHp());
        long newExp = (long) (eliteInfo.getRight() * elite.getForcedMobStat().getExp());
        elite.setEliteType(1);
        elite.setEliteGrade(eliteGrade);
        Map<Integer, Integer> possibleSkillsMap = SkillData.getEliteMobSkillsByGrade(eliteGrade);
        List<Tuple<Integer, Integer>> possibleSkills = new ArrayList<>();
        possibleSkillsMap.forEach((k, v) -> possibleSkills.add(new Tuple(k, v)));
        for (int i = 0; i < GameConstants.ELITE_MOB_SKILL_COUNT; i++) {
            Tuple<Integer, Integer> randomSkill = Util.getRandomFromCollection(possibleSkills);
            elite.addEliteSkill(randomSkill.getLeft(), randomSkill.getRight());
            possibleSkills.remove(randomSkill);
        }
        elite.setMaxHp(newHp);
        elite.setHp(newHp);
        elite.getForcedMobStat().setExp(newExp);
        getField().spawnLife(elite, null);
    }

    public List<MobSkill> getEliteSkills() {
        return eliteSkills;
    }

    public void addEliteSkill(int skillID, int skillLevel) {
        MobSkill ms = new MobSkill();
        ms.setSkillIdx(-1);
        ms.setSkillID(skillID);
        ms.setLevel(skillLevel);
        getEliteSkills().add(ms);
    }

    public List<MobSkill> getSkillDelays() {
        return skillDelays;
    }

    public boolean isInAttack() {
        return inAttack;
    }

    public void setInAttack(boolean inAttack) {
        this.inAttack = inAttack;
    }

    public void killedByChar(Char chr, AttackInfo attackInfo) {
        // Combo Counter per Kill
        chr.comboKillLogic(this);

        // Any special skills
        chr.getJobHandler().handleMobDeath(attackInfo, this);

        // Random portal spawn: is channel field, is not on cd, has min mob level, and field has no portal already
        chr.trySpawnRandomPortal();

        if (CustomConstants.LIE_DETECTOR_ENABLED) {
            chr.tryDoLieDetection();
        }
    }

    public List<EscortDest> getEscortDest() {
        return escortDest;
    }

    public void addEscortDest(int destPosX, int destPosY, int attr) {
        addEscortDest(destPosX, destPosY, attr, 0, 0);
    }

    public void addEscortDest(int destPosX, int destPosY, int attr, int mass, int stopDuration) {
        escortDest.add(new EscortDest(destPosX, destPosY, attr, mass, stopDuration));
    }

    public int getCurrentDestIndex() {
        return currentDestIndex;
    }

    public void setCurrentDestIndex(int currentDestIndex) {
        this.currentDestIndex = currentDestIndex;
    }

    public int getEscortStopDuration() {
        return escortStopDuration;
    }

    public void setEscortStopDuration(int escortStopDuration) {
        this.escortStopDuration = escortStopDuration;
    }

    public void clearEscortDest() {
        escortDest = new ArrayList<>();
    }

    public void escortFullPath(int oldAttr) {
        getField().broadcastPacket(MobPool.escortFullPath(this, oldAttr, false));
    }

    public boolean isFinishedEscort() {
        return escortDest.size() == 0;
    }

    @Override
    public void notifyControllerChange(Char controller) {
        for (Char chr : getField().getChars()) {
            writeControl(chr, controller == chr);
        }
    }

    /** GMS: the control grant comes right after MOB_NEXT_TARGET_FROM_SVR with the controller as the target. */
    private void writeControl(Char chr, boolean isController) {
        if (isController) {
            chr.write(MobPool.nextTargetFromSvr(getObjectId(), chr.getId()));
        }
        chr.write(MobPool.changeController(this, false, isController));
    }

    @Override
    public void onFieldEnter() {
        super.onFieldEnter();

        var removeAfter = getMobInfo().getRemoveAfter();
        if (removeAfter > 0) {
            Field spawnField = getField();
            EventManager.addEvent(() -> {
                if (isAlive() && getField() == spawnField) {
                    expire();
                }
            }, removeAfter, TimeUnit.SECONDS);
        }
    }

    // init blocks the client reads only for its own lists of mobs
    private static final Set<Integer> BANBAN_BOSSES = Set.of(BossConstants.VON_BON_CHAOS, BossConstants.VON_BON_NORMAL,
            BANBAN_CLIENT_ONLY);
    private static final Supplier<Map<Integer, Integer>> GAUGE_LENGTHS = DataTable.cached("mob-gauge", t -> {
        Map<Integer, Integer> m = new HashMap<>();
        for (var r : t.rows()) {
            m.put(r.i("mob"), r.i("gauge"));
        }
        return m;
    });

    public void encodeInit(OutPacket outPacket) {
        var mobInfo = getMobInfo();
        // CMob::Init
        outPacket.encodePosition(getPosition());
        outPacket.encodeByte(getMoveAction());
        int tid = getTemplateId();
        if (BANBAN_BOSSES.contains(tid)) {
            outPacket.encodeByte(0); // fake?
        }

        if (getCurFoodhold() == null) {
            setCurFoodhold(getField().findFootHoldBelow(getPosition()));
            if (getCurFoodhold() == null) {
                setCurFoodhold(getField().getInfo().getFootholdById(0));
            }
            if (getHomeFoothold() == null) {
                setHomeFoothold(getCurFoodhold());
            }
        }

        outPacket.encodeShort(getCurFoodhold() == null ? -1 : getCurFoodhold().getId());
        outPacket.encodeShort(getHomeFoothold() == null ? -1 : getHomeFoothold().getId());
        outPacket.encodeByte(0);
        byte appearType = getAppearType();
        outPacket.encodeByte(appearType);
        if (appearType == -3 || appearType == -6 || appearType >= 0) {
            // init -> -2, -1 else
            outPacket.encodeInt(getOption());
        }
        outPacket.encodeByte(-1); // teamForMCarnival
        outPacket.encodeInt(getScale());
        outPacket.encodeLong(getHp());
        outPacket.encodeInt(getEffectItemID());

        if (mobInfo.isPatrolMob()) {
            outPacket.encodeInt(getPosition().getX() - mobInfo.getRange());
            outPacket.encodeInt(getPosition().getX() + mobInfo.getRange());
            outPacket.encodeInt(mobInfo.getDetectX());
            outPacket.encodeInt(mobInfo.getSenseX());
        }

        outPacket.encodeInt(0);
        outPacket.encodeInt(getPhase());
        outPacket.encodeInt(getCurZoneDataType());
        outPacket.encodeInt(getRefImgMobID());
        outPacket.encodeInt(getAfterAttack());
        outPacket.encodeInt(getCurrentAction());
        outPacket.encodeInt(0);
        outPacket.encodeByte(Util.getRandom(0, Byte.MAX_VALUE));
        outPacket.encodeByte(0); // true: int
        outPacket.encodeByte(0);
        outPacket.encodeInt(0); // list of (int, int)
        outPacket.encodeInt(getScale());
        outPacket.encodeInt(getEliteGrade());
        if (getEliteGrade() >= 0) {
            outPacket.encodeInt(getEliteSkills().size());
            for (MobSkill skill : getEliteSkills()) {
                outPacket.encodeInt(skill.getSkillID());
                outPacket.encodeInt(skill.getLevel());
            }
            outPacket.encodeInt(getEliteType()); // 1 normal, 3 elite boss probably
        }
        ShootingMoveStat sms = getShootingMoveStat();
        outPacket.encodeByte(sms != null);
        if (sms != null) {
            sms.encode(outPacket);
        }
        // v271 (and v265) read one list count here (B4, 5-int records; client 0x140d46640), then the pair list below
        outPacket.encodeInt(0);
        if (tid == BossConstants.DEMIAN_HARD_TEMPLATE_ID_2 || tid == BossConstants.DEMIAN_NORMAL_TEMPLATE_ID_2) {
            // links to the flying sword
            List<Integer> swords = new ArrayList<>();
            for (Mob m : getField().getMobs()) {
                if (m.getTemplateId() == BossConstants.DEMIAN_SHADOW_ZONE) {
                    swords.add(m.getObjectId());
                }
            }
            outPacket.encodeInt(swords.size());
            for (int objectId : swords) {
                outPacket.encodeInt(5); // nType
                outPacket.encodeInt(objectId);
            }
        } else {
            outPacket.encodeInt(0);
        }
        outPacket.encodeByte(0); // true: 120-byte buffer
        outPacket.encodeString("");
        if (mobInfo.isTargetFromSvr()) {
            outPacket.encodeInt(getTargetUserIdFromServer());
        }
        outPacket.encodeInt(0); // CMob::OnAttackBlock
        outPacket.encodeByte(0);
        outPacket.encodeByte(0);
        outPacket.encodeInt(0);

        int gaugeLength = GAUGE_LENGTHS.get().getOrDefault(tid, 0);
        outPacket.encodeInt(gaugeLength > 0 ? 1 : 0);
        if (gaugeLength > 0) {
            outPacket.encodeInt(gaugeLength);
        }
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeInt(0); // int list
        outPacket.encodeInt(getField() != null && getField().getLifeToControllers().get(this) != null
                ? getField().getLifeToControllers().get(this).getId() : 0);
        outPacket.encodeInt(0); // v271: one int here (v265 two; client 0x143b75e90)
        outPacket.encodeShort(0);
        if (BossConstants.isLotusV265PhaseOneMob(tid)) {
            outPacket.encodeArr(new byte[14]);
        }
        Map<String, Integer> hitParts = mobInfo.getHitParts();
        if (!hitParts.isEmpty()) {
            outPacket.encodeByte(hitParts.size());
            for (Map.Entry<String, Integer> part : hitParts.entrySet()) {
                outPacket.encodeString(part.getKey());
                outPacket.encodeByte(0); // GMS sends 0 here for every part
                outPacket.encodeInt(Math.max(0, part.getValue()));
            }
        }
        // info/ex mobs (not skeleton) use a mob class (v265 ctor 0x141be1c40, v271 0x141fd0ff0) that reads 4 4 4 1 after
        // the base init; v271 then reads a flag byte and a position (B8, read either way), then 1 [record]
        if (mobInfo.isEx() && !mobInfo.isSkeleton() && tid != BossConstants.URSUS_TEMPLATE_ID) {
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeByte(0);
            outPacket.encodeByte(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeByte(0); // no record
        }
    }

    /**
     * @return the NX this monster gives for one kill (split between the attackers by damage): at most
     * {@link MobConstants#MAX_NX_PER_KILL}, at least 1, by level (see {@link MobConstants#getNxPerKill}); a boss
     * clear gives up to {@link MobConstants#MAX_BOSS_NX_PER_KILL} (see {@link MobConstants#getBossClearNx})
     */
    public int getNxDropAmount() {
        if (getTemplateId() == MobConstants.FAKE_STRAW_SCARECROW) {
            return 0;
        }
        if (MobConstants.isBossClearNxMob(getTemplateId())) {
            return MobConstants.getBossClearNx(getForcedMobStat().getLevel(), ServerRates.getNxRate());
        }
        return MobConstants.getNxPerKill(getForcedMobStat().getLevel(), isBoss(), ServerRates.getNxRate());
    }

    public void handleDamageReflect(Char chr, int skillID, long totalDamage, boolean isMagicAttack) {
        MobTemporaryStat mts = getTemporaryStat();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        boolean hasIgnoreCounterCts =
                tsm.hasStat(CharacterTemporaryStat.IgnoreAllCounter)
                        || tsm.hasStat(CharacterTemporaryStat.IgnoreAllImmune)
                        || tsm.hasStat(CharacterTemporaryStat.Invincible);
        boolean ignoreCounter = hasIgnoreCounterCts || SkillConstants.isIgnoreDRSkill(skillID);
        if (hasDamageReflect() && !ignoreCounter) {
            // DR is always P and M
            // nOption = % of hp, mOption = chance to reflect
            Option o;
            if (isMagicAttack) {
                o = mts.getCurrentOptionsByMobStat(MobStat.MCounter);
            } else {
                o = mts.getCurrentOptionsByMobStat(MobStat.PCounter);
            }
            if (o != null) {
                int damage = o.nOption;
                int prop = o.mOption;
                if (prop >= 100 || Util.succeedProp(prop)) {
                    int hpDamage = damage;
                    chr.damage(hpDamage);
                    getField().broadcastPacket(UserPacket.userHitByCounter(chr.getId(), hpDamage));
                }
            }
        }
    }

    public void applyHitDiseaseToPlayer(Char chr, MobSkill mobSkill) {
        if (mobSkill != null && mobSkill.getDisease() != 0) {
            MobSkillModule.applyEffect(chr, mobSkill);
        }
    }

    public Mob getOriginMob() {
        if (getTemporaryStat().hasCurrentMobStat(MobStat.SeperateSoulC)) {
            return (Mob) getField().getLifeByObjectID(getTemporaryStat().getCurrentOptionsByMobStat(MobStat.SeperateSoulC).rOption);
        }
        return null;
    }

    public Mob getCopyMob() {
        if (getTemporaryStat().hasCurrentMobStat(MobStat.SeperateSoulP)) {
            return (Mob) getField().getLifeByObjectID(getTemporaryStat().getCurrentOptionsByMobStat(MobStat.SeperateSoulP).rOption);
        }
        return null;
    }

    public MobSkill getRandomAvailableSkill() {
        if (isBlockEverything() || !Util.succeedProp(GameConstants.MOB_SKILL_CHANCE)) {
            return null;
        }

        List<MobSkill> list = new ArrayList<>();
        for (MobSkill ms : getSkills()) {
            if (canUseSkill(ms)) {
                list.add(ms);
            }
        }
        return Util.getRandomFromCollection(list);
    }

    public boolean canUseSkill(MobSkill ms) {
        MobSkillInfo msi = SkillData.getMobSkillInfoByIdAndLevel(ms.getSkillID(), ms.getLevel());
        if (msi == null) {
            // the mob lists a skill level that Skill.wz/MobSkill.img doesn't have (e.g. Hekaton's 207 level 5)
            return false;
        }
        int reqHp = msi.getSkillStatIntValue(MobSkillStat.hp);
        int minHp = msi.getSkillStatIntValue(MobSkillStat.minHP);
        // skill exists, is off cooldown, and mob is between required hp percentage to cast skill
        // Gloom's area force and laser beam are cast by its helper after the eye opens, never picked at random
        boolean gloomHelperSkill = ms.isOnlyOtherSkill()
                && (getTemplateId() == BossConstants.GLOOM || getTemplateId() == BossConstants.GLOOM_HARD);
        return (MobConstants.isZakumArm(getTemplateId()) || MobConstants.isZakumBody(getTemplateId()) || !ms.isOnlyFsm())
                && !gloomHelperSkill
                && !isBlockedSkill(ms)
                && hasSkillOffCooldown(ms.getSkillID(), ms.getLevel())
                && (reqHp == 0 || getHpPerc() <= reqHp)
                && getHpPerc() >= minHp;
    }

    public int getMobSpawnerId() {
        return mobSpawnerId;
    }

    public void setMobSpawnerId(int mobSpawnerId) {
        this.mobSpawnerId = mobSpawnerId;
    }

    public void teleport(int xPos, int yPos) {
        Rect possibleRect = getPosition().getRectAround(new Rect(-xPos, -yPos, xPos, yPos));
        setPosition(new Position(Util.getRandom(possibleRect.getLeft(), possibleRect.getRight()),
                Util.getRandom(possibleRect.getTop(), possibleRect.getBottom())));
        getField().broadcastPacket(MobPool.teleportRequest(this, 2, getPosition()));
    }

    public void teleport(Position position, int skillAfter) {
        setPosition(position);
        getField().broadcastPacket(MobPool.teleportRequest(this, skillAfter, getPosition()));
    }

    /** Buffed channels: HP (and attack, by its square root) and EXP by their own multipliers. */
    public void buff(double hpMulti, double expMulti) {
        if (MobConstants.isCantBuffMob(templateId)) {
            return;
        }
        ForcedMobStat fms = getForcedMobStat();
        setMaxHp((long) (getMaxHp() * hpMulti));
        setHp((long) (getHp() * hpMulti));
        fms.setExp((long) (fms.getExp() * expMulti));
        setPad((int) (getPad() + Math.sqrt(hpMulti)));
        setMad((int) (getMad() + Math.sqrt(hpMulti)));
        setBuffed(true);
    }

    public void buff(double multi) {
        if(MobConstants.isCantBuffMob(templateId)) {return;}
        ForcedMobStat fms = getForcedMobStat();
        setMaxHp((long) (getMaxHp() * multi));
        setHp((long) (getHp() * multi));
        fms.setExp((long) (fms.getExp() * multi));
        setPad((int) (getPad() + Math.sqrt(multi)));
        setMad((int) (getMad() + Math.sqrt(multi)));
        setBuffed(true);
    }

    public int getAttackCooldown() {
        return attackCooldown;
    }

    public void setAttackCooldown(int attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    /**
     * Sets the amount of time before this mob can summon an "afterdead" summon (like Queen).
     *
     * @param nextSummonPossible amount of seconds until next possible summon
     */
    public void setNextSummonPossible(int nextSummonPossible) {
        this.nextSummonPossible = System.currentTimeMillis() + nextSummonPossible * 1000;
    }

    public boolean canResummon() {
        return nextSummonPossible <= System.currentTimeMillis();
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public boolean hasProperty(String key) {
        return getProperties().containsKey(key);
    }

    public Object getProperty(String key) {
        return getProperties().get(key);
    }

    public void setProperty(String key, Object value) {
        getProperties().put(key, value);
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public void setBroadcastLeaveField(boolean broadcastLeaveField) {
        this.broadcastLeaveField = broadcastLeaveField;
    }

    public boolean isBroadcastLeaveField() {
        return broadcastLeaveField;
    }

    public List<AppearingFoothold> getAppearingFootholds() {
        return appearingFootholds;
    }

    public void addAppearingFoothold(AppearingFoothold afh) {
        getAppearingFootholds().add(afh);
    }

    public List<Rect> getPurpleAttacks() {
        return purpleAttacks;
    }

    public void setPurpleAttacks(List<Rect> purpleAttacks) {
        this.purpleAttacks = purpleAttacks;
    }

    public void addPurpleAttack(Rect rect) {
        getPurpleAttacks().add(rect);
    }

    public void setBuffed(boolean buffed) {
        this.buffed = buffed;
    }

    public boolean isBuffed() {
        return buffed;
    }

    public DropInfo generateMesoDrop() {
        // the meso drop used the old NX amount as its base (always 2 unless the monster gave no NX at all)
        int baseMoney = MobConstants.getBaseNxDropAmount(this) > 0 ? 2 : 0;

        var drop = new DropInfo(GameConstants.MAX_DROP_CHANCE,
                GameConstants.MIN_MONEY_MULT * baseMoney,
                GameConstants.MAX_MONEY_MULT * baseMoney
        );
        drop.generateNextDrop();

        return drop;
    }

    public void setSelfDestructionTimer() {
        int timeToSelfDestruct = getMobInfo().getSelfDestructionAfter();
        if (timeToSelfDestruct > 0) {
            EventManager.addEvent(this::selfDestruct, timeToSelfDestruct, TimeUnit.SECONDS);
        }
    }

    public void disappear() {
        getField().removeLife(getObjectId());
        getField().broadcastPacket(MobPool.leaveField(getObjectId(), DeathType.DISAPPEAR));

    }

    public synchronized void selfDestruct() {
        if (dead) {
            return;
        }
        dead = true;
        Field field = getField();
        int action = Math.abs(getMobInfo().getSelfDestructionAction());
        DeathType deathType = action < DeathType.values().length ? DeathType.values()[action] : DeathType.EXPLODE;
        field.removeLife(getObjectId());
        field.broadcastPacket(MobPool.leaveField(getObjectId(), deathType));
        Events.onMobDeath(field, this); // the instance's stage checks see it go (no death scripts, rewards or cooldowns)
    }

    /**
     * A mob whose time ran out (Mob.wz info/removeAfter) leaves the field like a dead mob, but nobody gets EXP, drops
     * or quest kills for it. Death scripts and field clear scripts still run.
     */
    public synchronized void expire() {
        if (dead) {
            return;
        }
        Field field = getField();
        if (field == null || !field.getMobs().contains(this)) {
            return; // already gone (the field was reset or the instance closed)
        }
        dead = true;
        field.removeLife(getObjectId());
        Mob copy = getCopyMob();
        if (copy != null) {
            copy.die(false);
        }
        getTemporaryStat().removeAllMobStats(false);
        field.broadcastPacket(MobPool.hpIndicator(getObjectId(), (byte) 0));
        // the revived mob (appearType -3) names this mob's object id, so it spawns before this one leaves, as in die()
        // (Papulatus's core 8500000/10/20, Von Leon and Arkarium's entrance forms: removeAfter, then revive)
        reviveMob();
        field.broadcastPacket(MobPool.leaveField(getObjectId(), DeathType.ANIMATION_DEATH));
        field.onMobDeath(this);
        clear();
    }

    /** False when this summonOnce skill was already cast by this mob. */
    public boolean markSummonOnce(int skillId, int level) {
        return summonedOnce.add(skillId * 1000 + level);
    }

    private Set<MobSkill> getBlockedSkills() {
        return blockedSkills;
    }

    public void addBlockedSkill(MobSkill ms) {
        if (ms != null) {
            getBlockedSkills().add(ms);
        }
    }

    public void removeBlockedSkill(MobSkill ms) {
        if (ms != null) {
            getBlockedSkills().remove(ms);
        }
        getField().broadcastPacket(MobPool.mobAttackBlock(this, getBlockedAttacks()));
    }

    private Set<Integer> getBlockedAttacks() {return blockedAttacks;}

    public void addBlockedAttack(int attackID){
        getBlockedAttacks().add(attackID);

        getField().broadcastPacket(MobPool.mobAttackBlock(this, getBlockedAttacks()));
    }

    public void removeBlockedAttack(int attackID){
        getBlockedAttacks().remove(attackID);
        getField().broadcastPacket(MobPool.mobAttackBlock(this, getBlockedAttacks()));
    }

    public void addBlockedSkill(MobSkillID mobSkillID, int slv) {
        addBlockedSkill(getSkillByMobSkillInfo(mobSkillID.getVal(), slv));
    }

    public void removeBlockedSkill(MobSkillID mobSkillID, int slv) {
        removeBlockedSkill(getSkillByMobSkillInfo(mobSkillID.getVal(), slv));
    }

    public boolean isBlockedSkill(MobSkill ms) {
        return getBlockedSkills().stream().anyMatch(m -> m.getLevel() == ms.getLevel() && m.getSkillID() == ms.getSkillID());
    }

    private Set<Integer> getSpawnedMobs() {
        if (spawnedMobs == null) {
            spawnedMobs = new HashSet<>();
        }
        return spawnedMobs;
    }

    public void addSpawnedMob(int mobId) {
        getSpawnedMobs().add(mobId);
    }

    public boolean hasSpawnedMob(int mobId) {
        return getSpawnedMobs().contains(mobId);
    }

    public void removeSpawnedMob(int mobId) {
        getSpawnedMobs().remove(mobId);
    }

    public void setReflectTime(long reflectTime) {
        this.reflectTime = reflectTime;
    }

    public long getReflectTime() {
        return reflectTime;
    }

    public boolean hasDamageReflect() {
        var mts = getTemporaryStat();
        var hasCounterStat = mts.hasCurrentMobStat(MobStat.PCounter) || mts.hasCurrentMobStat(MobStat.MCounter);
        return hasCounterStat && System.currentTimeMillis() > getReflectTime() + DR_REACTION_TIME;
    }

    public void clearCharInfo() {
        getDamageDone().clear();
    }

    public void addSkillDelayRects(int msId, int slv, List<Rect> skillDelayRects) {
        getSkillDelayRects().put(new Tuple<>(msId, slv), skillDelayRects);
    }

    private Map<Tuple<Integer, Integer>, List<Rect>> getSkillDelayRects() {
        if (skillDelayRects == null) {
            skillDelayRects = new HashMap<>();
        }
        return skillDelayRects;
    }

    public List<Rect> getSkillDelayRects(MobSkillID msID, int slv) {
        return getSkillDelayRects().get(new Tuple<>(msID.getVal(), slv));
    }

    public boolean isSplit() {
        return split;
    }

    /** The mob whose HP this one uses (Mob.wz HpLinkMob): hits on this mob go to it. */
    public Mob getHpLinkMob() {
        return hpLinkMob;
    }

    public void setHpLinkMob(Mob hpLinkMob) {
        this.hpLinkMob = hpLinkMob;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public int getEffectItemID() {
        return effectItemID;
    }

    public void setEffectItemID(int effectItemID) {
        this.effectItemID = effectItemID;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public int getCurZoneDataType() {
        return curZoneDataType;
    }

    public void setCurZoneDataType(int curZoneDataType) {
        this.curZoneDataType = curZoneDataType;
    }

    public int getRefImgMobID() {
        return refImgMobID;
    }

    public void setRefImgMobID(int refImgMobID) {
        this.refImgMobID = refImgMobID;
    }

    public int getLifeReleaseOwnerAID() {
        return lifeReleaseOwnerAID;
    }

    public void setLifeReleaseOwnerAID(int lifeReleaseOwnerAID) {
        this.lifeReleaseOwnerAID = lifeReleaseOwnerAID;
    }

    public String getLifeReleaseOwnerName() {
        return lifeReleaseOwnerName;
    }

    public void setLifeReleaseOwnerName(String lifeReleaseOwnerName) {
        this.lifeReleaseOwnerName = lifeReleaseOwnerName;
    }

    public String getLifeReleaseMobName() {
        return lifeReleaseMobName;
    }

    public void setLifeReleaseMobName(String lifeReleaseMobName) {
        this.lifeReleaseMobName = lifeReleaseMobName;
    }

    public void setSummonEffect(int summonEffect) {
        this.summonEffect = summonEffect;
    }

    public int getSummonEffect() {
        return summonEffect;
    }

    public List<MobSkill> getSkills() {
        var mobInfo = getMobInfo();
        var skills = new ArrayList<MobSkill>();
        if (mobInfo.getSkills() != null) {
            skills.addAll(mobInfo.getSkills());
        }
        skills.addAll(getEliteSkills());
        return skills;
    }

    public boolean isBoss() {
        return getMobInfo().isBoss();
    }

    /**
     * @return whether this monster is part of a boss fight without being the boss or its reward: a non-boss monster in
     * a boss instance once a boss has appeared there (Horntail's wyverns, Pink Bean's minions, ...), or in the Gollux
     * maps. These drop only what their own drop table has for them (e.g. Gollux Pennies, teleport rocks), and no
     * mesos, NX, Medals of Honor or generic items. Area bosses in normal maps don't count: those maps aren't instances.
     */
    public boolean isBossFightAdd() {
        Field f = getField();
        if (f == null || MobConstants.isBossClearNxMob(getTemplateId())) {
            return false;
        }
        // in Gollux's maps everything but Gollux himself is trash, boss flag or not (the leg, torso and arm maps' cores
        // and guards are flagged as bosses in Mob.wz)
        if (BossConstants.isGolluxFightField(f.getId())) {
            return !BossConstants.isGolluxPart(getTemplateId());
        }
        if (isBoss()) {
            return false;
        }
        var instance = f.getInstance();
        if (instance != null && instance.isBossRun()) {
            return true; // from the boss entry on (Hilla's corridors)
        }
        // a map the boss sends players to isn't always one of the run's own fields (e.g. Cygnus); it counts when the
        // players fighting here are on a boss run
        for (var c : getDamageDone().keySet()) {
            var ci = c.getInstance();
            if (ci != null && ci.isBossRun()) {
                return true;
            }
        }
        return false;
    }

    public MobSkill getSkillByMobSkillInfo(MobSkillInfo mobSkill) {
        return getSkillByMobSkillInfo(mobSkill.getSkillId(), mobSkill.getLevel());
    }

    public MobSkill getSkillByMobSkillInfo(int id, int slv) {
        return getSkills().stream()
                .filter(ms -> ms.getSkillID() == id && ms.getLevel() == slv)
                .findFirst().orElse(null);
    }

    public MobSkill findPrepareSkill(MobSkillAttackInfo msai) {
        var mobSkillId = msai.skillID;
        var mobSkillSlv = msai.slv;

        var preparedSkill = getPreparedSkill();
        if (preparedSkill == null) {
            return null;
        }

        return mobSkillId == preparedSkill.getSkillID() && mobSkillSlv == preparedSkill.getLevel()
                ? preparedSkill
                : null;
    }

    public void setForcedSkill(MobSkill forcedSkill) {
        this.forcedSkill = forcedSkill;
    }

    public MobSkill getForcedSkill() {
        return forcedSkill;
    }

    public void setForcedSkill(int level, int slv) {
        var skill = getSkillByMobSkillInfo(level, slv);
        if (skill != null) {
            setForcedSkill(skill);
        }
    }

    /** The HP % this mob can't be hit below until its test is passed (Will), -1 when none. */
    public int getNextHpTriggerBlockPerc() {
        var percs = hpTriggerBlockPercs;
        Integer next = percs == null ? null : percs.peek();
        return next == null ? -1 : next;
    }

    public boolean isOnTriggerThreshold() {
        if (hpTriggerBlockPercs == null) {
            return false;
        }
        return getHpPerc() - 2 <= getHpTriggerBlockPercs().peek();
    }

    public boolean isBlockEverything() {
        return isBlockEverything;
    }

    public void setBlockEverything(boolean blockEverything) {
        isBlockEverything = blockEverything;
    }

    public boolean isBlockAttack() {
        return isBlockAttack;
    }

    public void setBlockAttack(boolean blockAttack) {
        isBlockAttack = blockAttack;
    }

    public void clear() {
        clearCharInfo();
        getTemporaryStat().clear();
    }

    public void update() {
        getTemporaryStat().update();
        recover();
        MobPatternDriver.tick(this);
        checkStuck();
    }

    /** How long a boss that used to attack may go without an attack or skill while someone is next to it. */
    private static final long STUCK_MS = 30_000;
    private long lastActedAt;
    private long lastUnstuckAt;

    /** The controller's client reported an attack or a skill for this mob. */
    public void markActed() {
        lastActedAt = System.currentTimeMillis();
    }

    /**
     * A boss can hang in an action the client never finishes (it then neither moves nor attacks, and is free to hit).
     * Clears what the server holds for its next action and hands control to the client again, which restarts the mob.
     */
    private void checkStuck() {
        long now = System.currentTimeMillis();
        if (lastActedAt == 0 || now - lastActedAt < STUCK_MS || now - lastUnstuckAt < STUCK_MS
                || isDead() || getMobInfo() == null || !isBoss() || isBlockAttack() || isBlockEverything()) {
            return;
        }
        Field field = getField();
        if (field == null || MobPatternDriver.patternSys(getTemplateId()).length > 0) {
            return;
        }
        var mts = getTemporaryStat();
        if (mts.hasCurrentMobStat(MobStat.Stun) || mts.hasCurrentMobStat(MobStat.Freeze)) {
            return;
        }
        Char controller = field.getLifeToControllers().get(this);
        if (controller == null || controller.getField() != field) {
            return;
        }
        boolean someoneNear = field.getChars().stream().anyMatch(c -> !c.isHidden() && !c.isDead()
                && Math.abs(c.getPosition().getX() - getX()) < 1000 && Math.abs(c.getPosition().getY() - getY()) < 700);
        if (!someoneNear) {
            return;
        }
        lastUnstuckAt = now;
        log.info(String.format("[Mob] boss %d (%d) in %d did nothing for %d s: restarting its control on %s",
                getTemplateId(), getObjectId(), field.getId(), (now - lastActedAt) / 1000, controller.getName()));
        lastActedAt = now;
        setPreparedSkill(null);
        setPreparedSkillFromAttack(null);
        setForcedSkill(null);
        setInAttack(false);
        setSkillDelay(0);
        getSkillDelays().clear();
        controller.write(MobPool.changeController(this, false, false));
        writeControl(controller, true);
    }

    /** How often a monster gets back its Mob.wz info/hpRecovery HP and mpRecovery MP (official servers: 8 seconds). */
    public static final long RECOVERY_INTERVAL_MS = 8000;
    private long lastRecoveryTime = System.currentTimeMillis();

    /**
     * Mob.wz info/hpRecovery and mpRecovery: the HP/MP a monster gets back every {@link #RECOVERY_INTERVAL_MS}
     * (1,438 monsters have it, mostly bosses; e.g. 5,000 HP for Zakum's arms).
     */
    private void recover() {
        long now = System.currentTimeMillis();
        if (now - lastRecoveryTime < RECOVERY_INTERVAL_MS) {
            return;
        }
        lastRecoveryTime = now;
        var mobInfo = getMobInfo();
        Field field = getField();
        if (mobInfo == null || field == null) {
            return;
        }
        int hpRecovery = mobInfo.getHpRecovery();
        if (hpRecovery > 0) {
            boolean healed;
            synchronized (this) {
                healed = !dead && getHp() > 0 && getHp() < getMaxHp();
                if (healed) {
                    setHp(Math.min(getMaxHp(), getHp() + hpRecovery));
                }
            }
            if (healed) {
                if (mobInfo.isBoss() && mobInfo.getHpTagColor() != 0) {
                    field.broadcastPacket(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(this)));
                } else if (!mobInfo.isBoss()) {
                    field.broadcastPacket(MobPool.hpIndicator(getObjectId(), (byte) (getHp() * 100 / Math.max(1, getMaxHp()))));
                }
            }
        }
        int mpRecovery = mobInfo.getMpRecovery();
        if (mpRecovery > 0 && !dead && getMp() < getMaxMp()) {
            healMP(mpRecovery);
        }
    }

    public void changePhase(int phase) {
        setPhase(phase);
        getField().broadcastPacket(MobPool.changePhase(getObjectId(), getPhase()));
    }

    public void changeZoneId(int zoneId) {
        setCurZoneDataType(zoneId);
        getField().broadcastPacket(FieldPacket.changeMobZone(getObjectId(), getCurZoneDataType()));
    }

    public void setPreparedSkill(MobSkill preparedSkill) {
        this.preparedSkill = preparedSkill;
    }

    public MobSkill getPreparedSkill() {
        return preparedSkill;
    }

    public MobSkill getPreparedSkillFromAttack() {
        return preparedSkillFromAttack;
    }

    public void setPreparedSkillFromAttack(MobSkill preparedSkillFromAttack) {
        this.preparedSkillFromAttack = preparedSkillFromAttack;
    }

    public MobSkill getSkillByIdx(int idx) {
        return getSkills().stream()
                .filter(s -> s.getSkillIdx() == idx)
                .findFirst().orElse(null);
    }

    public boolean hasBlockedSkill(MobSkill mobSkill) {
        return getBlockedSkills().contains(mobSkill);
    }

    public int getMobGroupSpawnProgress() {
        return mobGroupSpawnProgress;
    }

    public void increaseMobGroupSpawnProgress() {
        this.mobGroupSpawnProgress += 1;
    }
}
