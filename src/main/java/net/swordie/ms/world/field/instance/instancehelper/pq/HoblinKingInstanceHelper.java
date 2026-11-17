package net.swordie.ms.world.field.instance.instancehelper.pq;

import java.util.List;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Portal;
import net.swordie.ms.world.field.instance.Instance;

import java.util.*;

/**
 * Resurrection of the Hoblin King (El Nath party quest), as on live servers
 * (maplestorywiki.net/w/Resurrection_of_the_Hoblin_King). The party guards Shammos through the Ice Ravine:
 * <ul>
 *     <li>Ice Ravine 1-3: hold off the Hoblin waves (Hoblin Hectors and Elite Hoblins, then Combat Hoblins, then
 *     Ferocious Hoblins) until the way is clear.</li>
 *     <li>Ancient Ice Cave: Shammos betrays the party and frees Rex the Hoblin King. Defeat him.</li>
 * </ul>
 * Level 120+ (recommended 170-189), a party of 1-6, 5 entries a day, 20 minutes. Completion EXP:
 * 8,469,300 * 1.025^(level - 120) up to level 189 (times the server's EXP rate), split over the stages, +30% for
 * guarding Shammos; a clear also gives +44 Willpower and +26 Insight EXP.
 */
public class HoblinKingInstanceHelper extends PartyQuestInstanceHelper {

    public static final String RECORD_KEY = "hoblin_king";
    public static final int MIN_LEVEL = 120;
    public static final int MIN_PARTY = 1;
    public static final int MAX_PARTY = 6;
    public static final int DAILY_ENTRIES = 5;
    public static final int TIME_LIMIT_SECONDS = 20 * 60;

    public static final int SOLITARY_ROOM = 211000002;
    public static final int RAVINE_ENTRANCE = 921120000;
    public static final int RAVINE_1 = 921120005;
    public static final int RAVINE_2 = 921120100;
    public static final int RAVINE_3 = 921120200;
    public static final int ICE_CAVE = 921120300;
    public static final int BACKSTREET = 921120001;
    private static final List<DataTable.Row> WAVE_ROWS = DataTable.read("hoblin-king-waves").rows();
    private static final int[][] WAVES = WAVE_ROWS.stream().map(r -> r.ints("mobs")).toArray(int[][]::new);
    private static final int[] WAVE_COUNT = WAVE_ROWS.stream().mapToInt(r -> r.i("count")).toArray();
    private static final int REX = 9300281;
    private static final int[] STAGE_PERCENT = {0, 15, 20, 25, 40};
    private static final int SHAMMOS_BONUS_PERCENT = 30;

    private final Set<Integer> wavesSpawned = new HashSet<>();
    private final int[] spawnedCount = new int[4];
    private boolean rexSpawned;
    private boolean finished;

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        instance.setForcedReturn(BACKSTREET);
        instance.setTimeout(TIME_LIMIT_SECONDS);
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

    private static int stageOf(int fid) {
        switch (fid) {
            case RAVINE_1:
                return 1;
            case RAVINE_2:
                return 2;
            case RAVINE_3:
                return 3;
            case ICE_CAVE:
                return 4;
        }
        return 0;
    }

    @Override
    public boolean canSpawnMapMobs(Field field) {
        return false; // the Hoblins come in waves
    }

    @Override
    public boolean canUsePortal(Char chr, Field field, Portal portal) {
        int s = stageOf(field.getId());
        if (s > 0 && s < 4 && "next00".equals(portal.getName()) && !isStageCleared(s)) {
            chr.chatMessage(ChatType.SystemNotice, "Shammos won't go on until the Hoblins are dealt with.");
            return false;
        }
        return true;
    }

    /** shammos_Next */
    public void next(Char chr) {
        int s = stageOf(chr.getField().getId());
        if (s == 0 || s >= 4 || !isStageCleared(s)) {
            chr.chatMessage(ChatType.SystemNotice, "Shammos won't go on until the Hoblins are dealt with.");
            return;
        }
        chr.warp(new int[]{0, RAVINE_2, RAVINE_3, ICE_CAVE}[s]);
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        if (newField != null) {
            int s = stageOf(newField.getId());
            synchronized (this) {
                if (s >= 1 && s <= 3) {
                    spawnWaves(newField, s);
                } else if (s == 4 && !rexSpawned) {
                    rexSpawned = true;
                    newField.spawnMob(REX, 350, 168, false, -1);
                    for (Char c : newField.getChars()) {
                        c.chatMessage(ChatType.SystemNotice, "Shammos: \"Fools! Thanks to you, the Hoblin King "
                                + "awakens!\" Defeat Rex!");
                    }
                }
            }
        }
        super.onWarp(chr, oldField, newField);
    }

    /** A ravine's Hoblin waves, once per stage; they arrive over the first 20 seconds. */
    private synchronized void spawnWaves(Field field, int s) {
        if (!wavesSpawned.add(s)) {
            return;
        }
        for (int i = 0; i < WAVE_COUNT[s]; i++) {
            int t = WAVES[s][i % WAVES[s].length];
            addTimer(EventManager.addEvent(() -> spawn(field, t, s), 500L + i * 1000L));
        }
    }

    private synchronized void spawn(Field field, int template, int stage) {
        spawnedCount[stage]++;
        Position pos = field.getRandomPosOnWalkableFoothold(50, false);
        if (pos != null) {
            field.spawnMob(template, pos.getX(), pos.getY(), false, -1);
        } else {
            checkWavesCleared(field, stage); // the last one found no spot after the rest were beaten
        }
    }

    private synchronized void checkWavesCleared(Field field, int s) {
        if (field.getMobs().isEmpty() && spawnedCount[s] >= WAVE_COUNT[s] && clearStage(s, field, STAGE_PERCENT[s])) {
            for (Char c : field.getChars()) {
                c.chatMessage(ChatType.SystemNotice, "The Hoblins are beaten back! Follow Shammos through the "
                        + "portal.");
            }
        }
    }

    @Override
    public void onMobSpawn(Field field, Mob mob) {
        scaleMobToParty(mob);
        super.onMobSpawn(field, mob);
    }

    @Override
    public synchronized void onMobDeath(Field field, Mob mob) {
        int s = stageOf(field.getId());
        if (s >= 1 && s <= 3) {
            checkWavesCleared(field, s);
        } else if (s == 4 && mob.getTemplateId() == REX && !finished) {
            finished = true;
            for (Mob m : new ArrayList<>(field.getMobs())) {
                m.die(false);
            }
            clearStage(4, field, STAGE_PERCENT[4] + SHAMMOS_BONUS_PERCENT);
            giveTraitExp(Stat.willEXP, 44);
            giveTraitExp(Stat.insightEXP, 26);
            for (Char c : getInstance().getChars()) {
                PartyQuestRecords.addClear(c, RECORD_KEY);
            }
            getInstance().chatMessage("Rex the Hoblin King is sealed again! You'll be taken out in 20 seconds.");
            addTimer(EventManager.addEvent(() -> getInstance().clear(), 20_000));
        }
        super.onMobDeath(field, mob);
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    protected long getBaseCompletionExp() {
        return 8_469_300L;
    }

    @Override
    protected int getExpMinLevel() {
        return 120;
    }

    @Override
    protected int getExpMaxLevel() {
        return 189;
    }

    @Override
    protected List<Integer> getPartyQuestItems() {
        return Collections.emptyList();
    }
}
