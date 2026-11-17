package net.swordie.ms.world.field.instance.instancehelper.pq;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.Instance;

import java.util.*;

/**
 * Xerxes in Chryse (Michaela, Spiegelmann's Guest House). Live servers removed the Floating Island of Chryse and
 * this client only keeps its Coliseum, so the party quest runs as-is there: the party enters Inside the Coliseum and
 * defeats Xerxes, Michaela's runaway pet.
 * <p>
 * Level 70+, a party of 1-6, 5 entries a day, 20 minutes. Completion EXP: the Cross World party quest formula (times
 * the server's EXP rate).
 */
public class ChryseInstanceHelper extends PartyQuestInstanceHelper {

    public static final String RECORD_KEY = "chryse";
    public static final int MIN_LEVEL = 70;
    public static final int MIN_PARTY = 1;
    public static final int MAX_PARTY = 6;
    public static final int DAILY_ENTRIES = 5;
    public static final int TIME_LIMIT_SECONDS = 20 * 60;

    public static final int COLISEUM = 200101400;
    public static final int GUEST_HOUSE = 910002000;
    private static final int XERXES = 6160003;
    private static final int XERXES_GHOST = 6160004;

    private boolean finished;

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        instance.setForcedReturn(GUEST_HOUSE);
        instance.setTimeout(TIME_LIMIT_SECONDS);
        addTimer(EventManager.addEvent(this::spawnXerxes, 3000));
    }

    @Override
    protected void startStage(int stageNumber) {
    }

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

    private void spawnXerxes() {
        Field field = getInstance().getField(COLISEUM);
        if (field == null) {
            return;
        }
        Position pos = field.getRandomPosOnWalkableFoothold(150, false);
        if (pos == null) {
            pos = new Position(-800, 51);
        }
        field.spawnMob(XERXES, pos.getX(), pos.getY(), false, -1);
        for (Char c : field.getChars()) {
            c.chatMessage(ChatType.SystemNotice, "Xerxes is here! Defeat him so Michaela can take him home.");
        }
    }

    @Override
    public boolean canSpawnMapMobs(Field field) {
        return false;
    }

    @Override
    public void onMobSpawn(Field field, Mob mob) {
        scaleMobToParty(mob);
        super.onMobSpawn(field, mob);
    }

    @Override
    public synchronized void onMobDeath(Field field, Mob mob) {
        int t = mob.getTemplateId();
        if ((t == XERXES || t == XERXES_GHOST) && !finished) {
            finished = true;
            clearStage(1, field, 100);
            for (Char c : getInstance().getChars()) {
                PartyQuestRecords.addClear(c, RECORD_KEY);
            }
            getInstance().chatMessage("Xerxes calms down at last! You'll be sent back in 20 seconds.");
            addTimer(EventManager.addEvent(() -> getInstance().clear(), 20_000));
        }
        super.onMobDeath(field, mob);
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    public long getCompletionExp(Char chr) {
        return getCrossWorldExp(chr);
    }

    @Override
    protected List<Integer> getPartyQuestItems() {
        return Collections.emptyList();
    }
}
