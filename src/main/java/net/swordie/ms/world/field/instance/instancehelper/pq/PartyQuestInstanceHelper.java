package net.swordie.ms.world.field.instance.instancehelper.pq;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.ServerRates;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.fieldeffect.FieldEffect;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created on 12/02/2022.
 *
 * @author Asura
 * <p>
 * Shared party quest logic: stage clears (with the clear effect and an EXP share by the stage's achievement ratio),
 * monster scaling to the party's level, and the party quest's own items being removed on entry and exit.
 */
public abstract class PartyQuestInstanceHelper extends InstanceHelper {

    private final Set<Integer> clearedStages = new HashSet<>();
    private int partyLevel;

    @Override
    protected void clearInstance() {
        removeItems();
        super.clearInstance();
    }

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        removeItems();
        partyLevel = averageLevel();
    }

    private int averageLevel() {
        if (getInstance() == null) {
            return 0;
        }
        int total = 0;
        int count = 0;
        for (Char chr : getInstance().getChars()) {
            total += chr.getLevel();
            count++;
        }
        return count == 0 ? 0 : total / count;
    }

    @Override
    public void onChrRemoved(Char chr) {
        super.onChrRemoved(chr);
        removeItems(chr);
    }

    private void removeItems() {
        for (var chr : getInstance().getChars()) {
            removeItems(chr);
        }
    }

    private void removeItems(Char chr) {
        if (getPartyQuestItems() == null || getPartyQuestItems().size() <= 0) {
            return;
        }
        for (var item : getPartyQuestItems()) {
            var quantity = chr.getInventories().stream().mapToInt(inv -> inv.getQuantity(item)).sum();
            if (quantity > 0) {
                chr.consumeItem(item, quantity);
            }
        }
    }

    /**
     * Items that will be removed on setup and clear
     */
    protected abstract List<Integer> getPartyQuestItems();

    /**
     * @return the average level of the party when it entered (monsters are scaled to it)
     */
    public int getPartyLevel() {
        if (partyLevel <= 0) {
            partyLevel = averageLevel(); // the first map's monsters can come before setup
        }
        return partyLevel;
    }

    public boolean isStageCleared(int stage) {
        return clearedStages.contains(stage);
    }

    /**
     * Clears a stage once: shows the clear effect and sound in the given map, opens its gate object, and gives every
     * character of the instance its share of the party quest EXP (achievementPercent of the full reward).
     *
     * @return false if the stage was already cleared
     */
    protected synchronized boolean clearStage(int stage, Field field, int achievementPercent) {
        if (!clearedStages.add(stage)) {
            return false;
        }
        if (field != null) {
            field.broadcastPacket(FieldPacket.fieldEffect(FieldEffect.getFieldEffectFromWz("quest/party/clear", 0)));
            field.broadcastPacket(FieldPacket.fieldEffect(FieldEffect.playSound("Party1/Clear", 100)));
            field.broadcastPacket(FieldPacket.fieldEffect(FieldEffect.getFieldEffectFromObject("gate")));
        }
        if (achievementPercent > 0) {
            for (Char chr : getInstance().getChars()) {
                giveStageExp(chr, achievementPercent);
            }
        }
        return true;
    }

    /**
     * Live servers' completion EXP of this party quest at its lowest level (base * 1.025^(level - min level), up to
     * the max level), or 0 to use the server's general party quest EXP formula instead.
     */
    protected long getBaseCompletionExp() {
        return 0;
    }

    /** The level the completion EXP formula starts at. */
    protected int getExpMinLevel() {
        return 1;
    }

    /** How much the completion EXP grows per level (live: 1.025 for most party quests). */
    protected double getExpGrowth() {
        return 1.025;
    }

    /** The level above which the completion EXP stops growing. */
    protected int getExpMaxLevel() {
        return 300;
    }

    /**
     * @return the full completion EXP for this character: the live formula times the server's EXP rate
     */
    public long getCompletionExp(Char chr) {
        long base = getBaseCompletionExp();
        if (base <= 0) {
            return chr.getParty() == null ? 0 : GameConstants.PARTY_QUEST_EXP_FORMULA(chr);
        }
        int lv = Math.max(getExpMinLevel(), Math.min(getExpMaxLevel(), chr.getLevel()));
        double exp = base * Math.pow(getExpGrowth(), lv - getExpMinLevel()) * Math.max(1, ServerRates.getExpRate());
        return (long) Math.min(Long.MAX_VALUE / 2, exp);
    }

    /**
     * EXP for clearing part of a party quest: the completion EXP times the stage's achievement ratio.
     * Characters 20 or more levels below the party's average get 20% of it, like on live servers.
     */
    protected void giveStageExp(Char chr, int achievementPercent) {
        if (chr == null) {
            return;
        }
        long exp = getCompletionExp(chr) * achievementPercent / 100;
        if (getPartyLevel() - chr.getLevel() >= 20) {
            exp /= 5;
        }
        if (exp > 0) {
            chr.write(FieldPacket.fieldEffect(FieldEffect.showClearStageExpWindow((int) Math.min(Integer.MAX_VALUE, exp))));
            chr.addExpNoMsg(exp);
        }
    }

    /**
     * Scales a low level party quest monster to the party's level (HP, EXP and attack grow with the level gap), as
     * live servers scale party quest monsters to the party.
     */
    protected void scaleMobToParty(Mob mob) {
        int target = getPartyLevel();
        int level = mob.getLevel();
        if (target <= 0 || level <= 0 || level >= target) {
            return;
        }
        double ratio = (double) target / level;
        double hpMulti = Math.pow(ratio, 3.2);
        mob.setLevel(target);
        mob.setMaxHp(Math.max(1, (long) (mob.getMaxHp() * hpMulti)));
        mob.setHp(mob.getMaxHp());
        mob.getForcedMobStat().setExp((long) (mob.getForcedMobStat().getExp() * hpMulti / 4));
        mob.setPad((int) Math.min(Integer.MAX_VALUE, mob.getPad() * ratio * 2));
        mob.setMad((int) Math.min(Integer.MAX_VALUE, mob.getMad() * ratio * 2));
    }

    /**
     * Trait EXP for completing the party quest (e.g. Willpower and Insight), to everyone in the instance.
     */
    protected void giveTraitExp(net.swordie.ms.enums.Stat trait, int amount) {
        for (Char chr : getInstance().getChars()) {
            chr.addTraitExp(trait, amount);
        }
    }

    /**
     * @return whether the character is its party's leader (party quest NPCs only listen to the leader)
     */
    public boolean isLeader(Char chr) {
        return chr != null && chr.getParty() != null && chr.getParty().getPartyLeaderID() == chr.getId();
    }

    /**
     * Live servers' completion EXP of the Cross World party quests (Moon Bunny's Rice Cake, First Time Together,
     * Forest of Poison Haze, ...) at the given level, before the server's EXP rate.
     */
    public static double getCrossWorldBaseExp(int level) {
        int lv = Math.max(70, level);
        if (lv < 100) {
            return (450 + lv) * 345 * Math.pow(1.037, lv - 70);
        } else if (lv < 110) {
            return (480 + lv) * 345 * Math.pow(1.037, lv - 70);
        } else if (lv < 120) {
            return (lv - 65) * 6900 * Math.pow(1.037, lv - 70);
        } else if (lv < 130) {
            return lv * 21000.0 * Math.pow(1.025, lv - 120);
        } else if (lv < 140) {
            return (lv + 20) * 21000.0 * Math.pow(1.025, lv - 120);
        } else if (lv < 150) {
            return (2 * lv - 70) * 21000.0 * Math.pow(1.025, lv - 120);
        } else if (lv < 170) {
            return (2 * lv - 10) * 21000.0 * Math.pow(1.025, lv - 120);
        }
        return (lv + 230) * 21000.0 * Math.pow(1.025, Math.min(lv, 249) - 120);
    }

    /** The Cross World completion EXP for this character, times the server's EXP rate. */
    protected static long getCrossWorldExp(Char chr) {
        double exp = getCrossWorldBaseExp(chr.getLevel()) * Math.max(1, ServerRates.getExpRate());
        return (long) Math.min(Long.MAX_VALUE / 2, exp);
    }

    /** Party Points for everyone in the instance (a Cross World clear: also counts for the daily quest). */
    protected void givePartyPoints(int amount) {
        for (Char chr : getInstance().getChars()) {
            PartyQuestRecords.addPartyPoints(chr, amount);
            PartyQuestRecords.addCrossWorldDailyClear(chr);
        }
    }
}
