package net.swordie.ms.client.character.union;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.connection.packet.CUIHandler;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.skill.MobSkillID;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.fieldeffect.FieldEffect;

import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.constants.QuestConstants;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 * Created on 2/24/2020.
 *
 * @author Asura
 */
public class UnionRaid {

    // Fields
    public static final int GRASS_FIELD = 921172000; // Random Field you can enter
    public static final int BATTLE_ARENA = 921172100; // Random Field you can enter
    public static final Position bossPosition = new Position(2350, 17);

    public static final long TOTAL_DAMAGE_DONE_CAP = 99_999_999_999_999L;
    public static final long COIN_PER_DAMAGE = 100_000_000_000L;
    // the attackers' damage a second per point of Raid Power: 1,000,000 Raid Power gives 0.7992 coins a day
    public static final double DAMAGE_PER_POWER_SECOND = 0.7992 * COIN_PER_DAMAGE / (1_000_000d * 86_400d);
    private static final DateTimeFormatter COIN_RECORD_TIME = DateTimeFormatter.ofPattern("yyMMddHHmmss");

    // Bosses
    public static final long BOSS_HP = 400_000_100_000L; // ~200bil
    public static final long SHIELD_HP = 100_000_000_000L; // ~50bil

    public static final List<Integer> bosses = Arrays.asList(9833101, 9833102, 9833103, 9833104, 9833105);

    /** the raid's mobs; 9833111 is the Golden Wyvern, 9833110 the Flying Dragon */
    public static final List<Integer> mobs = Arrays.asList(9833106, 9833107, 9833108, 9833109, 9833111, 9833110);

    public static final List<Position> positions = Arrays.asList(
            new Position(1725, -600),
            new Position(1750, -441),
            new Position(1700, -282),
            new Position(1725, -129),
            new Position(1725, 21),
            new Position(2070, 21),
            new Position(2350, 21),
            new Position(2700, 21),
            new Position(3000, 21),
            new Position(3000, -129),
            new Position(3070, -282),
            new Position(2950, -441),
            new Position(3050, -600)
    );


    private int id;
    private long totalDamageDone;
    private int currentBoss;
    private long curShield;
    private long curHp;
    private int unclaimedCoins;
    private ScheduledFuture timer;
    private long lastTime = Util.getCurrentTimeLong();
    private Mob shield;
    private Mob boss;
    private Char chr;
    // the client reads the window's coin count only while the Legion window exists (it never does before the
    // first UNION_REQUEST of the session)
    private transient boolean windowOpened;


    public UnionRaid() {
    }

    /** Weekly Dragon Extermination: 100 Dragon Whelps (the quest counts template 9101089) and 20 Golden Wyverns. */
    public static final int WEEKLY_QUEST = 503281;
    public static final int WEEKLY_WHELP = 9101089;
    public static final int GOLDEN_WYVERN = 9833111;
    private static final int WAVE_SIZE = 10;
    private static final int MAX_WHELPS = 15;
    /** Golden Wyverns per 100 Dragon Whelps spawned (live servers: about 18-22; the weekly quest wants 20 per 100) */
    private static final int GOLDEN_PER_100_WHELPS = 20;
    private static final long WHELP_HP = 50_000_000L;
    private static final List<Integer> WHELPS = Arrays.asList(9833106, 9833107, 9833108, 9833109, 9833110);

    private Field raidField;
    private long fieldDamage;
    private int ticks;

    /** Both Dragon's Domain maps (fieldType 155) are the same raid; the entry picks one. */
    public void enter(Char chr) {
        if (getCurrentBoss() == 0) { // First time
            initialize();
        }
        int fieldId = Util.succeedProp(50) ? GRASS_FIELD : BATTLE_ARENA;
        chr.getScriptManager().warpInstanceIn(fieldId, 0);
        onFieldEnter(chr);
    }

    /**
     * Starts the raid in the Dragon's Domain the character is in (any way it got there: the Legion window, Dame
     * Appropriation, the weekly quest): the dragon, the whelp waves, damage and kill counting. Runs once per visit.
     */
    public void onFieldEnter(Char chr) {
        Field field = chr.getField();
        if (field == null || !FieldConstants.isUnionRaidField(field.getId())) {
            return;
        }
        if (getCurrentBoss() == 0) {
            initialize();
        }
        if (this.chr == chr && raidField == field && boss != null && boss.getField() == field) {
            return;
        }
        this.chr = chr;
        this.raidField = field;
        this.fieldDamage = 0;
        this.ticks = 0;
        if (field.getInstance() != null && field.getInstance().getInstanceHelper() == null) {
            new RaidHelper(this).setup(field.getInstance());
        }

        updateTotalDamageDone(chr, true);
        spawnBoss(field, getCurrentBoss(), getCurShield(), getCurHp());

        int coins = getUnclaimedCoins();
        if (coins > 0) {
            chr.write(CUIHandler.unionRaidCoin(coins, true));
        }
        sendCoinState(chr, true);

        if (this.timer != null && !this.timer.isDone()) {
            this.timer.cancel(true);
        }
        this.timer = EventManager.addFixedRateEvent(() -> updatePer5Seconds(field), 400, 5000);
    }

    /** Dragon whelps, a Golden Wyvern for every few of them, around the dragon; at most 15 out at once. */
    private void spawnWhelps(Field field) {
        int out = (int) field.getMobs().stream().filter(m -> mobs.contains(m.getTemplateId())).count();
        for (int i = 0; i < WAVE_SIZE && out < MAX_WHELPS; i++, out++) {
            boolean golden = Util.succeedProp(GOLDEN_PER_100_WHELPS, 100 + GOLDEN_PER_100_WHELPS);
            int id = golden ? GOLDEN_WYVERN : Util.getRandomFromCollection(WHELPS);
            Position p = Util.getRandomFromCollection(positions);
            Mob mob = field.spawnMob(id, p.getX(), p.getY(), false, WHELP_HP);
            if (chr != null && mob != null) {
                mob.setLevel(chr.getLevel());
            }
        }
    }

    /** Damage to the whelps counts for the raid like damage to the dragon. */
    public void onMobDamaged(Char chr, Mob mob, long damage) {
        if (damage <= 0 || !mobs.contains(mob.getTemplateId())) {
            return;
        }
        fieldDamage = Math.min(TOTAL_DAMAGE_DONE_CAP, fieldDamage + damage);
        addTotalDamageDone(chr, damage);
    }

    /** Whelp kills count for the weekly quest's Dragon Whelps (Golden Wyverns count as themselves). */
    public static void onMobKilled(Char chr, Mob mob) {
        int id = mob.getTemplateId();
        if (chr == null || id == GOLDEN_WYVERN || !mobs.contains(id)) {
            return;
        }
        var qm = chr.getQuestManager();
        Quest q = qm.getQuestById(WEEKLY_QUEST);
        if (q == null || q.isComplete(chr)) {
            return;
        }
        String qr = q.getCustomQrValue();
        if (qr != null && qr.startsWith("startDate=")) {
            // an old start script put a date here, which hid the kill counts from the client
            q.setQrValue("");
        }
        q.handleMobKill(WEEKLY_WHELP);
        chr.write(WvsContext.message(MessagePacket.questRecordMessage(q)));
        qm.notifyIfReady(q);
    }

    private static final class RaidHelper extends net.swordie.ms.world.field.instance.instancehelper.InstanceHelper {
        private final UnionRaid raid;

        RaidHelper(UnionRaid raid) {
            this.raid = raid;
        }

        @Override
        protected void startStage(int stageNumber) {
        }

        @Override
        public void onMobDamaged(Char attacker, Mob mob, long damage, long oldHp, long newHp) {
            if (attacker != null && attacker.getUnionRaid() == raid) {
                raid.onMobDamaged(attacker, mob, Math.max(0, oldHp - Math.max(0, newHp)));
            }
        }

        @Override
        public void onMobDeath(Field field, Mob mob) {
            Char c = raid.getChr();
            if (c != null && c.getField() == field) {
                onMobKilled(c, mob);
            }
        }
    }

    //Called when player first unlocks their Union
    public void initialize() {
        setLastTime(Util.getCurrentTimeLong());
        int boss = Util.getRandomFromCollection(bosses);
        setCurrentBoss(boss);
        // each dragon's own Mob.wz HP (GMS: 9833101 50 bil. shield, 9833201 200 bil.)
        var shieldInfo = net.swordie.ms.loaders.MobData.getMobInfoById(boss);
        var dragonInfo = net.swordie.ms.loaders.MobData.getMobInfoById(getSubBoss(boss));
        setCurShield(shieldInfo != null ? shieldInfo.getForcedMobStat().getMaxHP() : SHIELD_HP);
        setCurHp(dragonInfo != null ? dragonInfo.getForcedMobStat().getMaxHP() : BOSS_HP);
    }


    public void retreat() {
        retreat(chr);
    }

    /**
     * The raid's Retreat button. Uses the character that pressed it (the raid only remembers a character while one is
     * inside, so the old version crashed when the button was pressed outside the raid).
     */
    public void retreat(Char chr) {
        if (chr == null) {
            return;
        }
        if (this.chr == null) {
            this.chr = chr;
        }
        var returnMap = FieldConstants.HENESYS_FIELD_ID;
        var field = chr.getField();
        if (field != null && field.getInfo() != null && chr.getField().getInfo().getReturnMap() > 0) {
            returnMap = chr.getField().getInfo().getReturnMap();
        }
        chr.getScriptManager().warpInstanceOut(returnMap, 0);

        updateTotalDamageDone(chr, false);
        playerLeave();
        if (getUnclaimedCoins() > 0) {
            // leaving the raid claims the unclaimed coins, like the Claim Coins button
            claimCoins(chr);
        }
    }

    public void playerLeave() {
        if (getShield() != null) {
            setCurShield(getShield().getHp());
        }
        if (getBoss() != null) {
            setCurHp(getBoss().getHp());
        }
        stopTimer();
        this.chr = null;
        this.raidField = null;
    }

    public void stopTimer() {
        if (this.timer != null) {
            this.timer.cancel(true);
        }
    }

    private void updatePer5Seconds(Field field) {
        // the character left the raid some other way (died and respawned, @stuck, logged out, warped): stop counting
        // here and keep the dragon's HP for next time
        if (chr == null || chr.getField() != field || chr.getClient() == null) {
            playerLeave();
            return;
        }
        if (ticks++ % 2 == 0) {
            spawnWhelps(field);
        }
        if (boss == null || shield == null) {
            return;
        }
        updateTotalDamageDone(chr, true);

        long displayDmgDone = (boss.getMaxHp() - boss.getHp()) + (shield.getMaxHp() - shield.getHp()) + fieldDamage;

        field.broadcastPacket(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(getShield())));
        field.broadcastPacket(CUIHandler.unionRaidDamage(Math.min(TOTAL_DAMAGE_DONE_CAP, displayDmgDone)));
        field.broadcastPacket(FieldPacket.fieldEffect(FieldEffect.mobHPTagFieldEffect(getBoss())));
    }

    public void damageDoneToRaidBoss(Mob mob, long totaldamage, long oldHp, long newHp) {
        boolean died = oldHp > 0 && newHp <= 0;

        if (died) {
            mob.getField().broadcastPacket(CUIHandler.unionRaidBossUpdate(getShield(), getBoss()));
        }

        if (mob.equals(getShield()) && oldHp > 0) {
            if (died) {
                totaldamage = oldHp;
                showShield();
            }
            setCurShield(Math.max(0, getCurShield() - totaldamage));
            mob.setHp(getCurShield());
            addTotalDamageDone(chr, totaldamage); // damage done by chr
        } else if (mob.equals(getBoss()) && oldHp > 0 && getCurShield() <= 0) {
            long dealt = Math.min(oldHp, Math.max(0, totaldamage));
            setCurHp(Math.max(0, getCurHp() - dealt));
            mob.setHp(getCurHp());
            addTotalDamageDone(chr, dealt);
        }

        if (getShield().getHp() <= 0 && getBoss().getHp() <= 0) { // Both Dead
            Field field = mob.getField();

            getShield().die(false);
            getBoss().die(false);

            spawnNewBoss(field); // the damage toward the next coin carries over
        }
    }

    private void spawnNewBoss(Field field) { // when first time entering raid
        // full HP: spawnBoss caps it at the dragon's own
        spawnBoss(field, Util.getRandomFromCollection(bosses), Long.MAX_VALUE, Long.MAX_VALUE);
    }

    private void spawnBoss(Field field, int boss, long shield, long hp) {
        var shieldInfo = net.swordie.ms.loaders.MobData.getMobInfoById(boss);
        var dragonInfo = net.swordie.ms.loaders.MobData.getMobInfoById(getSubBoss(boss));
        long maxShield = shieldInfo != null ? shieldInfo.getForcedMobStat().getMaxHP() : SHIELD_HP;
        long maxHp = dragonInfo != null ? dragonInfo.getForcedMobStat().getMaxHP() : BOSS_HP;
        setCurrentBoss(boss);
        setCurShield(Math.min(shield, maxShield));
        setCurHp(Math.min(hp, maxHp));

        if (field != null) {
            setShield(field.spawnMob(boss, bossPosition.getX(), bossPosition.getY(), false, maxShield));
            getShield().setHp(getCurShield());
            setBoss(field.spawnMob(getSubBoss(boss), bossPosition.getX(), bossPosition.getY(), false, maxHp));

            if (getShield().getHp() <= 0) {
                showShield();
            }

            field.broadcastPacket(CUIHandler.unionRaidBossUpdate(getShield(), getBoss()));
            field.broadcastPacket(CUIHandler.unionRaidDamage(Math.min(TOTAL_DAMAGE_DONE_CAP, 0)));
        }
    }

    private void showShield() {
        Option o = new Option();
        o.nOption = 1;
        o.rOption = MobSkillID.Invincible.getVal();
        o.slv = 13;
        getShield().getTemporaryStat().addMobSkillOptionsAndBroadCast(MobStat.Unk205_86, o);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTotalDamageDone() {
        return this.totalDamageDone;
    }

    public void setTotalDamageDone(long totalDamageDone) {
        if (totalDamageDone < 0) {
            totalDamageDone = 0;
        }
        this.totalDamageDone = totalDamageDone;
    }

    /** One coin per 100 billion damage, up to the rank's cap; at the cap the extra damage is lost. */
    public void addTotalDamageDone(Char chr, long totalDamageDone) {
        if (chr == null) {
            return;
        }
        int cap = getUnclaimedCoinCap(chr);
        if (unclaimedCoins >= cap) {
            setTotalDamageDone(0);
            return;
        }
        setTotalDamageDone(Math.min(TOTAL_DAMAGE_DONE_CAP, getTotalDamageDone() + totalDamageDone));
        int coins = (int) Math.min(Integer.MAX_VALUE, getTotalDamageDone() / COIN_PER_DAMAGE);
        if (coins <= 0) {
            return;
        }
        if (unclaimedCoins + coins >= cap) {
            addUnclaimedCoins(chr, cap - unclaimedCoins);
            setTotalDamageDone(0);
            chr.chatMessage("Your unclaimed Legion Coins are full. Claim them in the Legion window.");
        } else {
            addUnclaimedCoins(chr, coins);
            setTotalDamageDone(getTotalDamageDone() - coins * COIN_PER_DAMAGE);
        }
    }

    /** The attackers' damage since the last update (they keep attacking while the player is away), at most a full cap. */
    public long getUnionDamageDone(Char chr) {
        long secs = Math.max(0, (Util.getCurrentTimeLong() - getLastTime()) / 1000);
        double dmg = chr.getUnionPower() * DAMAGE_PER_POWER_SECOND * secs;
        return (long) Math.min((double) getUnclaimedCoinCap(chr) * COIN_PER_DAMAGE, dmg);
    }

    public void updateTotalDamageDone(Char chr, boolean affectBossHP) {
        // Only start counting union damage once the player entered.
        if (getCurrentBoss() > 0) {
            long unionDmgDone = getUnionDamageDone(chr);
            long now = Util.getCurrentTimeLong();
            long secs = Math.max(0, (now - getLastTime()) / 1000);
            // keep the part of a second not counted yet
            setLastTime(getLastTime() <= 0 || getLastTime() > now ? now : getLastTime() + secs * 1000);

            addTotalDamageDone(chr, unionDmgDone);
            if (affectBossHP) { // damage the boss by how much was done whilst character is not in field
                setCurHp(getCurHp() - unionDmgDone);
                if (boss != null) {
                    boss.setHp(getCurHp());
                }
                if (getCurShield() <= 0 && getCurHp() <= 0) {
                    spawnNewBoss(chr.getField() != null && chr.getField().getId() == BATTLE_ARENA ? chr.getField() : null);
                }
            }
        }
    }

    public int getCurrentBoss() {
        return currentBoss;
    }

    public void setCurrentBoss(int currentBoss) {
        this.currentBoss = currentBoss;
    }

    public long getCurShield() {
        return curShield;
    }

    public void setCurShield(long curShield) {
        this.curShield = Math.max(0, curShield);
    }

    public long getCurHp() {
        return curHp;
    }

    public void setCurHp(long curHp) {
        this.curHp = Math.max(0, curHp);
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public Mob getShield() {
        return shield;
    }

    public void setShield(Mob shield) {
        this.shield = shield;
    }

    public Mob getBoss() {
        return boss;
    }

    public void setBoss(Mob boss) {
        this.boss = boss;
    }

    public Char getChr() {
        return chr;
    }

    public void setChr(Char chr) {
        this.chr = chr;
    }

    public static int getSubBoss(int main) {
        return main + 100;
    }

    public int getUnclaimedCoins() {
        return unclaimedCoins;
    }

    public void setUnclaimedCoins(int unclaimedCoins) {
        this.unclaimedCoins = unclaimedCoins;
    }

    public void addUnclaimedCoins(Char chr, int unclaimedCoins) {
        int before = this.unclaimedCoins;
        this.unclaimedCoins = Math.min(getUnclaimedCoinCap(chr), Math.max(0, this.unclaimedCoins + unclaimedCoins));
        int delta = this.unclaimedCoins - before;
        if (delta == 0) {
            return;
        }
        if (chr.getField() != null && FieldConstants.isUnionRaidField(chr.getField().getId())) {
            // raid field (0x14312a960): byte 0 adds the amount with the coin effect, 1 sets the total
            chr.write(CUIHandler.unionRaidCoin(delta, false));
        }
        sendCoinState(chr, this.unclaimedCoins >= getUnclaimedCoinCap(chr) || delta < 0);
    }

    /** Seconds until the attackers fill the unclaimed coins at the current Raid Power; -1 if they never will. */
    public int secondsUntilFull(Char chr) {
        int cap = getUnclaimedCoinCap(chr);
        if (cap <= 0) {
            return -1;
        }
        if (unclaimedCoins >= cap) {
            return 0;
        }
        double perSecond = chr.getUnionPower() * DAMAGE_PER_POWER_SECOND;
        if (perSecond <= 0) {
            return -1;
        }
        double need = (double) (cap - unclaimedCoins) * COIN_PER_DAMAGE - getTotalDamageDone();
        return (int) Math.min(Integer.MAX_VALUE, Math.max(1, Math.ceil(need / perSecond)));
    }

    /**
     * The client's copy of the unclaimed coins: QR 18098 (lastTime, coin; the "coins full" checks compare it with the
     * rank's coinStackMax), the open Legion window's count (2684) and, if asked, the coins-full timer (2697).
     */
    public void setWindowOpened(boolean windowOpened) {
        this.windowOpened = windowOpened;
    }

    public void sendCoinState(Char chr, boolean timer) {
        if (chr == null || chr.getUnion() == null) {
            return;
        }
        Quest quest = chr.getQuestManager().getOrCreateQuestById(QuestConstants.UNION_RAID_COIN);
        quest.setProperty("lastTime", LocalDateTime.now(ZoneOffset.UTC).format(COIN_RECORD_TIME));
        quest.setProperty("coin", unclaimedCoins);
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
        if (windowOpened) {
            chr.write(CUIHandler.unionUnclaimedCoin(unclaimedCoins));
        }
        if (timer) {
            chr.write(CUIHandler.unionCoin(secondsUntilFull(chr), getUnclaimedCoinCap(chr)));
        }
    }

    /** Unclaimed coins stop building up at this cap (Etc/mapleUnion unionRank coinStackMax: 200/300/500/900/1,300). */
    public static int getUnclaimedCoinCap(Char chr) {
        Union union = chr.getUnion();
        String rankName = union.getUnionRankName();
        if (rankName.contains("Nameless")) {
            return 200;
        } else if (rankName.contains("Renowned")) {
            return 300;
        } else if (rankName.contains("Heroic")) {
            return 500;
        } else if (rankName.contains("Legendary")) {
            return 900;
        } else if (rankName.contains("Supreme")) {
            return 1300;
        } else {
            return 0;
        }
    }

    /** The Legion window's Claim Coins button: unclaimed raid coins go to the Legion's coins. */
    public void claimCoins(Char chr) {
        updateTotalDamageDone(chr, chr.getField() != null && chr.getField().getId() == BATTLE_ARENA);
        int coins = getUnclaimedCoins();
        if (coins <= 0) {
            chr.chatMessage("There are no Legion Coins to claim yet.");
            return;
        }
        addUnclaimedCoins(chr, -coins);
        chr.getUnion().addUnionCoin(coins);
        chr.chatMessage(String.format("You claimed %,d Legion Coins.", coins));
    }
}
