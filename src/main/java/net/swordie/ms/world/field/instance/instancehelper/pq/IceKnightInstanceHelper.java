package net.swordie.ms.world.field.instance.instancehelper.pq;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Portal;
import net.swordie.ms.world.field.instance.Instance;

import java.util.*;

/**
 * The Ice Knight's Curse (Spiegelmann's Guest House party quest, PQuest 1218): lift the Ice Knight's curse from
 * Von's friend Jun.
 * <ul>
 *     <li>Frosty Pass Sidepath and Frosty Pass: defeat the White Fangs and Hectors blocking the way.</li>
 *     <li>Ice Curse Forest: the cursed Iceman turns into the Ice Knight. Defeat it to lift the curse.</li>
 * </ul>
 * Level 90+ (recommended 90-109), a party of 1-6, 5 entries a day, 20 minutes. Completion EXP is the Cross World
 * party quest formula (times the server's EXP rate), split over the stages. A clear also gives a Cold Ice Fragment,
 * which Von trades for the Ice Knight's Shoulder Accessory and the Ice Knight Seeker Chain Snowshoes.
 */
public class IceKnightInstanceHelper extends PartyQuestInstanceHelper {

    public static final String RECORD_KEY = "ice_knight";
    public static final int MIN_LEVEL = 90;
    public static final int MIN_PARTY = 1;
    public static final int MAX_PARTY = 6;
    public static final int DAILY_ENTRIES = 5;
    public static final int TIME_LIMIT_SECONDS = 20 * 60;

    public static final int ENTRANCE = 932000000;
    public static final int STAGE_1 = 932000100;
    public static final int STAGE_2 = 932000200;
    public static final int STAGE_3 = 932000300;

    public static final int COLD_ICE_FRAGMENT = 4001529;
    public static final int SHOULDER = 1152127;
    public static final int SNOWSHOES = 1072819;
    public static final int SHOULDER_COST = 20;
    public static final int SNOWSHOES_COST = 10;

    private static final int CURSED_ICEMAN = 9300441;
    private static final int ICE_KNIGHT = 9300442;
    private static final int[] STAGE_PERCENT = {0, 25, 25, 50};

    private boolean bossSpawned;
    private boolean finished;

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        instance.setForcedReturn(ENTRANCE);
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

    public static int getRemainingEntries(Char chr) {
        return Math.max(0, DAILY_ENTRIES - PartyQuestRecords.getTodayEntries(chr, RECORD_KEY));
    }

    /** Von's exchange: 0 = snowshoes, 1 = shoulder accessory */
    public static String trade(Char chr, int choice) {
        int item = choice == 0 ? SNOWSHOES : SHOULDER;
        int cost = choice == 0 ? SNOWSHOES_COST : SHOULDER_COST;
        if (!chr.hasItemCount(COLD_ICE_FRAGMENT, cost)) {
            return "You need #b" + cost + " #t" + COLD_ICE_FRAGMENT + "#s#k for #b#t" + item + "##k.";
        }
        if (!chr.canHold(item, 1)) {
            return "Please make room in your Equip inventory first.";
        }
        chr.consumeItem(COLD_ICE_FRAGMENT, cost);
        chr.addItemToInventory(item, 1);
        return "Here you go: #b#t" + item + "##k. Thank you for helping Jun!";
    }

    private static int stageOf(int fid) {
        switch (fid) {
            case STAGE_1:
                return 1;
            case STAGE_2:
                return 2;
            case STAGE_3:
                return 3;
        }
        return 0;
    }

    @Override
    public boolean canRespawnMobs(Field field) {
        return false;
    }

    @Override
    public boolean canUsePortal(Char chr, Field field, Portal portal) {
        int s = stageOf(field.getId());
        if ((s == 1 || s == 2) && "out00".equals(portal.getName()) && !isStageCleared(s)) {
            chr.chatMessage(ChatType.SystemNotice, "The monsters still block the pass. Defeat them all first.");
            return false;
        }
        return true;
    }

    /** iceman_Next */
    public void next(Char chr) {
        int s = stageOf(chr.getField().getId());
        if ((s != 1 && s != 2) || !isStageCleared(s)) {
            chr.chatMessage(ChatType.SystemNotice, "The monsters still block the pass. Defeat them all first.");
            return;
        }
        chr.warp(s == 1 ? STAGE_2 : STAGE_3);
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        if (newField != null && newField.getId() == STAGE_3) {
            synchronized (this) {
                if (!bossSpawned) {
                    bossSpawned = true;
                    final Field f = newField;
                    addTimer(EventManager.addEvent(() -> {
                        Position pos = f.getRandomPosOnWalkableFoothold(200, false);
                        if (pos != null) {
                            f.spawnMob(CURSED_ICEMAN, pos.getX(), pos.getY(), false, -1);
                        }
                        notice(f, "The curse takes hold of Jun... the Ice Knight appears! Defeat it to lift the "
                                + "curse!");
                    }, 2000));
                }
            }
        }
        super.onWarp(chr, oldField, newField);
    }

    @Override
    public void onMobSpawn(Field field, Mob mob) {
        scaleMobToParty(mob);
        super.onMobSpawn(field, mob);
    }

    @Override
    public synchronized void onMobDeath(Field field, Mob mob) {
        int s = stageOf(field.getId());
        if ((s == 1 || s == 2) && field.getMobs().isEmpty()) {
            if (clearStage(s, field, STAGE_PERCENT[s])) {
                notice(field, "The way is clear! Move on through the portal.");
            }
        } else if (s == 3 && mob.getTemplateId() == ICE_KNIGHT && !finished) {
            finished = true;
            clearStage(3, field, STAGE_PERCENT[3]);
            for (Char c : getInstance().getChars()) {
                PartyQuestRecords.addClear(c, RECORD_KEY);
                if (c.canHold(COLD_ICE_FRAGMENT, 1)) {
                    c.addItemToInventory(COLD_ICE_FRAGMENT, 1);
                }
            }
            notice(field, "The curse is lifted! You got a Cold Ice Fragment. You'll be sent back in 20 seconds.");
            addTimer(EventManager.addEvent(() -> getInstance().clear(), 20_000));
        }
        super.onMobDeath(field, mob);
    }

    public boolean isFinished() {
        return finished;
    }

    private static void notice(Field field, String msg) {
        if (field != null) {
            for (Char c : field.getChars()) {
                c.chatMessage(ChatType.SystemNotice, msg);
            }
        }
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
