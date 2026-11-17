package net.swordie.ms.world.field.instance.instancehelper.events.monsterdefense;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.NewPyramidPool;
import net.swordie.ms.constants.ServerRates;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.ms.world.field.instance.instancehelper.pq.PartyQuestRecords;

import java.util.concurrent.TimeUnit;

/**
 * Created on 02/02/2022.
 *
 * @author Asura
 * <p>
 * Nett's Pyramid, as on live servers (maplestorywiki.net/w/Nett%27s_Pyramid): protect the Obelisk on the left from
 * 20 waves of monsters. The Obelisk has 20 life; a monster reaching it costs 1 (a Pharaoh Yeti 2, a Pharaoh Mummy 5)
 * and the quest fails at 0. Damage earns points (Normal mode for parties up to level 89, Hard mode above).
 * <p>
 * Level 80+, a party of 1-4, 5 entries a day. Rewards: completion EXP (155,250 * 1.037^(level - 70) for levels
 * 80-119, 945,000 * 1.025^(level - 120) from 120, times the server's EXP rate) by the waves cleared (all 20 = 100%,
 * 110% with the Obelisk at full life), +15 Willpower and +3 Ambition EXP on a clear, and a Nett's Emerald per wave
 * cleared (Duarte trades 40 for the Immortal Pharaoh Belt, Shoes or Ring).
 */
public class NewPyramidInstanceHelper extends MonsterDefenseInstanceHelper {

    public static final String RECORD_KEY = "netts_pyramid";
    public static final int MIN_LEVEL = 80;
    public static final int MIN_PARTY = 1;
    public static final int MAX_PARTY = 4;
    public static final int DAILY_ENTRIES = 5;
    public static final int TIME_LIMIT_SECONDS = 30 * 60;

    public static final int PYRAMID_DUNES = 926010000;
    public static final int PYRAMID = 926010300;
    public static final int SHADES_OF_THE_PYRAMID = 926020001;
    public static final int NETTS_EMERALD = 4001623;
    public static final int[] EXCHANGE_ITEMS = {1132013, 1072619, 1112682}; // belt, shoes, ring (15 days)
    public static final int EXCHANGE_COST = 40;

    private static final int MAX_STAGE = 20;
    private static final int DEFAULT_LIFES = 20;
    private static final int PHARAOH_YETI = 9305508;
    private static final int PHARAOH_MUMMY = 9305522;
    // EXP ratio by waves cleared (live): 10 waves 26.7%, 15 waves 46.7%, 19 waves 71.8%, 20 waves 100%
    private static final double[][] EXP_RATIO = {{0, 0}, {10, 0.267}, {15, 0.467}, {19, 0.718}, {20, 1.0}};

    private NewPyramidMode mode = NewPyramidMode.NORMAL;
    private long totalDamageDone;
    private int lifes;
    private int partyLevel;
    private boolean hasResultUI = false;

    protected int getLifes() {
        return lifes;
    }

    protected void setLifes(int lifes) {
        this.lifes = lifes;
    }

    protected void decreaseLifes() {
        decreaseLifes(1);
    }

    protected void decreaseLifes(int amount) {
        setLifes(Math.max(0, getLifes() - amount));
    }

    protected NewPyramidMode getMode() {
        return mode;
    }

    public void setMode(NewPyramidMode mode) {
        this.mode = mode;
    }

    protected long getTotalDamageDone() {
        return totalDamageDone;
    }

    protected void setTotalDamageDone(long totalDamageDone) {
        this.totalDamageDone = totalDamageDone;
    }

    protected void addTotalDamageDone(long totalDamageDone) {
        setTotalDamageDone(getTotalDamageDone() + totalDamageDone);
    }

    protected int getPoint() {
        return (int) Math.min(Integer.MAX_VALUE, getTotalDamageDone() / getMode().getPointPerDmg());
    }

    // ---------------------------------------------------------------------------------------------------------- entry

    public static String getEntryBlocker(Char leader) {
        var party = leader.getParty();
        if (party == null) {
            return "Please make a party first (you can go alone in a party of 1).";
        }
        if (party.getPartyLeaderID() != leader.getId()) {
            return "Please have your party leader talk to me.";
        }
        var members = party.getPartyMembersInField(leader);
        if (members.size() < MIN_PARTY || members.size() > MAX_PARTY) {
            return "You need " + MIN_PARTY + " to " + MAX_PARTY + " party members here.";
        }
        for (Char c : members) {
            if (c.getLevel() < MIN_LEVEL) {
                return c.getName() + " must be at least level " + MIN_LEVEL + " to enter.";
            }
            if (PartyQuestRecords.getTodayEntries(c, RECORD_KEY) >= DAILY_ENTRIES) {
                return c.getName() + " has already entered " + DAILY_ENTRIES + " times today. Come back tomorrow!";
            }
        }
        return null;
    }

    public static void recordEntries(Char leader) {
        for (Char c : leader.getParty().getPartyMembersInField(leader)) {
            PartyQuestRecords.addEntry(c, RECORD_KEY);
        }
    }

    public static int getRemainingEntries(Char chr) {
        return Math.max(0, DAILY_ENTRIES - PartyQuestRecords.getTodayEntries(chr, RECORD_KEY));
    }

    /** Duarte's exchange: 40 Nett's Emeralds for the Immortal Pharaoh Belt (0), Shoes (1) or Ring (2). */
    public static String trade(Char chr, int choice) {
        if (choice < 0 || choice >= EXCHANGE_ITEMS.length) {
            return null;
        }
        int item = EXCHANGE_ITEMS[choice];
        if (!chr.hasItemCount(NETTS_EMERALD, EXCHANGE_COST)) {
            return "You need #b" + EXCHANGE_COST + " #t" + NETTS_EMERALD + "#s#k for #b#t" + item + "##k.";
        }
        if (!chr.canHold(item, 1)) {
            return "Please make room in your Equip inventory first.";
        }
        chr.consumeItem(NETTS_EMERALD, EXCHANGE_COST);
        chr.addItemToInventory(item, 1);
        return "Here you go: #b#t" + item + "##k.";
    }

    // ---------------------------------------------------------------------------------------------------------- run

    @Override
    public void setup(Instance instance) {
        int total = 0;
        int count = 0;
        for (Char chr : instance.getChars()) {
            total += chr.getLevel();
            count++;
        }
        partyLevel = count == 0 ? 0 : total / count;
        setMode(partyLevel >= 90 ? NewPyramidMode.HARD : NewPyramidMode.NORMAL);
        instance.setForcedReturn(SHADES_OF_THE_PYRAMID);
        instance.setTimeout(TIME_LIMIT_SECONDS);
        super.setup(instance);
        // time up: the waves held so far still count (the instance's own timeout would just send everyone out)
        addTimer(EventManager.addEvent(() -> {
            if (!hasResultUI) {
                finish(false);
                stopTimers();
                addTimer(EventManager.addEvent(() -> clearInstance(), getNextStageDelay(), TimeUnit.MILLISECONDS));
            }
        }, (TIME_LIMIT_SECONDS - 10) * 1000L, TimeUnit.MILLISECONDS));
        setLifes(DEFAULT_LIFES);

        instance.broadcast(NewPyramidPool.newPyramidSetWave(getCurrentStage()));
        instance.broadcast(NewPyramidPool.newPyramidSetLife(getLifes()));
    }

    @Override
    protected void startStage() {
        getInstance().broadcast(NewPyramidPool.newPyramidSetWave(Math.min(MAX_STAGE, getCurrentStage())));
        super.startStage();
    }

    @Override
    protected void completeInstance() {
        finish(true);
        super.completeInstance();
    }

    /** Gives the rewards for the waves cleared and shows the result window (once). */
    private synchronized void finish(boolean success) {
        if (hasResultUI) {
            return;
        }
        hasResultUI = true;
        int waves = success ? MAX_STAGE : Math.max(0, Math.min(MAX_STAGE, getCurrentStage() - 1));
        double ratio = getExpRatio(waves);
        if (success && getLifes() >= DEFAULT_LIFES) {
            ratio = 1.1;
        }
        int shownExp = 0;
        for (var chr : getInstance().getChars()) {
            long exp = (long) (getCompletionExp(chr) * ratio);
            if (partyLevel - chr.getLevel() >= 20) {
                exp /= 5;
            }
            if (exp > 0) {
                chr.addExp(exp);
                shownExp = (int) Math.min(Integer.MAX_VALUE, exp);
            }
            if (waves > 0 && chr.canHold(NETTS_EMERALD, waves)) {
                chr.addItemToInventory(NETTS_EMERALD, waves);
            }
            if (success) {
                chr.addTraitExp(Stat.willEXP, 15);
                chr.addTraitExp(Stat.charismaEXP, 3);
                PartyQuestRecords.addClear(chr, RECORD_KEY);
            }
        }
        getInstance().broadcast(NewPyramidPool.newPyramidResult(success, waves, getLifes(), getPoint(), shownExp));
    }

    private static double getExpRatio(int waves) {
        for (int i = 1; i < EXP_RATIO.length; i++) {
            if (waves <= EXP_RATIO[i][0]) {
                double[] lo = EXP_RATIO[i - 1];
                double[] hi = EXP_RATIO[i];
                return lo[1] + (hi[1] - lo[1]) * (waves - lo[0]) / (hi[0] - lo[0]);
            }
        }
        return 1.0;
    }

    /** The full completion EXP for this character (live formula times the server's EXP rate). */
    public static long getCompletionExp(Char chr) {
        int lv = Math.max(MIN_LEVEL, chr.getLevel());
        double exp = lv < 120 ? 155_250 * Math.pow(1.037, lv - 70) : 945_000 * Math.pow(1.025, lv - 120);
        return (long) Math.min(Long.MAX_VALUE / 2, exp * Math.max(1, ServerRates.getExpRate()));
    }

    @Override
    public void onMobSpawn(Field field, Mob mob) {
        // the pyramid's monsters are scaled to the party, like on live servers
        int level = mob.getLevel();
        if (partyLevel > 0 && level > 0 && level < partyLevel) {
            double ratio = (double) partyLevel / level;
            double hpMulti = Math.pow(ratio, 3.2);
            mob.setLevel(partyLevel);
            mob.setMaxHp(Math.max(1, (long) (mob.getMaxHp() * hpMulti)));
            mob.setHp(mob.getMaxHp());
            mob.getForcedMobStat().setExp((long) (mob.getForcedMobStat().getExp() * hpMulti / 4));
            mob.setPad((int) Math.min(Integer.MAX_VALUE, mob.getPad() * ratio * 2));
            mob.setMad((int) Math.min(Integer.MAX_VALUE, mob.getMad() * ratio * 2));
        }
        super.onMobSpawn(field, mob);
    }

    /** The wave being fought (1-20). */
    public int getWave() {
        return Math.min(MAX_STAGE, getCurrentStage());
    }

    /** The Obelisk's life left. */
    public int getObeliskLife() {
        return getLifes();
    }

    /** The result window was shown: the run is over (won or lost). */
    public boolean isOver() {
        return hasResultUI;
    }

    /** All 20 waves held. */
    public boolean isWon() {
        return hasResultUI && getLifes() > 0 && getCurrentStage() > MAX_STAGE;
    }

    @Override
    public void onMobDamaged(Char attacker, Mob mob, long damage, long oldHp, long newHp) {
        addTotalDamageDone(damage);
        getInstance().broadcast(NewPyramidPool.newPyramidSetPoint(getPoint()));
        super.onMobDamaged(attacker, mob, damage, oldHp, newHp);
    }

    public synchronized void onMobMove(Mob mob) {
        // Nett's Pyramid. Mobs that get to the end of their platform are killed and the Obelisk loses life.
        if (mob != null && mob.getPosition().getX() <= -640 && !hasResultUI) {
            int cost = mob.getTemplateId() == PHARAOH_MUMMY ? 5 : mob.getTemplateId() == PHARAOH_YETI ? 2 : 1;
            mob.die(false);
            decreaseLifes(cost);
            getInstance().broadcast(NewPyramidPool.newPyramidSetLife(getLifes()));
            if (getLifes() <= 0) {
                finish(false);
                stopTimers();
                addTimer(EventManager.addEvent(() -> clearInstance(), getNextStageDelay(), TimeUnit.MILLISECONDS));
            }
        }
    }

    @Override
    protected int getNextStageDelay() {
        return 6000;
    }

    public enum NewPyramidMode {
        NORMAL(3_000),
        HARD(10_000),
        ;
        private int pointPerDmg;

        NewPyramidMode(int pointPerDmg) {
            this.pointPerDmg = pointPerDmg;
        }

        public int getPointPerDmg() {
            return pointPerDmg;
        }
    }
}
