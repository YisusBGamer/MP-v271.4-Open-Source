package net.swordie.ms.world.field.instance.instancehelper.pq;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.PlayStyleModule;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;

import java.time.LocalDate;
import java.time.temporal.IsoFields;

/**
 * Princess No (Dimensional Mirror, Hieizan Temple Plaza): a weekly boss. Level 180+ with the prequest done (or Express)
 * ([Princess No], quest 58913), a party of 1-6, one clear a week. Ayame sends the party to the waiting room
 * (811002000, field type 421, 10 min); Ayame there (ptBossIn) starts the fight on the Temple's 5th floor
 * (811000500, 30 min), whose first-enter script summons Princess No. The Door (9401486) or pt_nohimeOut leaves.
 */
public class PrincessNoInstanceHelper extends InstanceHelper {

    public static final int MIN_LEVEL = 180; // Normal (maplestorywiki.net, GMS); was 140
    public static final int PREQUEST = 58913;
    public static final int MAX_PARTY = 6;
    public static final int TIME_LIMIT_SECONDS = 30 * 60;
    public static final int WAIT_SECONDS = 10 * 60;

    public static final int PLAZA = 811000999;
    public static final int FLOOR_1 = 811000100;
    public static final int FLOOR_5 = 811000500;
    public static final int BOSS_ROOM = FLOOR_5;
    public static final int WAITING_ROOM = 811002000;
    private static final int PRINCESS_NO = 9450022;

    private boolean finished;

    private static String weekKey() {
        LocalDate d = net.swordie.ms.util.ResetClock.today();
        return "princess_no_" + d.get(IsoFields.WEEK_BASED_YEAR) + "w" + d.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
    }

    public static boolean hasClearedThisWeek(Char chr) {
        return PartyQuestRecords.getClears(chr, weekKey()) > 0;
    }

    public static String getEntryBlocker(Char chr) {
        if (net.swordie.ms.loaders.FieldData.getFieldInfoById(BOSS_ROOM) == null) { // data check
            return "Princess No's chamber is no longer there. The Temple is closed.";
        }
        var party = chr.getParty();
        if (party != null && party.getPartyLeaderID() != chr.getId()) {
            return "Please have your party leader talk to me.";
        }
        var members = party == null ? java.util.List.of(chr) : party.getPartyMembersInField(chr);
        if (members.size() > MAX_PARTY) {
            return "At most " + MAX_PARTY + " can go together.";
        }
        for (Char c : members) {
            if (c.getLevel() < MIN_LEVEL) {
                return c.getName() + " must be at least level " + MIN_LEVEL + ".";
            }
            if (!PlayStyleModule.autoSkips(c) && !c.getQuestManager().hasQuestCompleted(PREQUEST)) {
                return c.getName() + " hasn't finished the story leading up to Princess No yet.";
            }
            if (hasClearedThisWeek(c)) {
                return c.getName() + " has already defeated Princess No this week.";
            }
        }
        return null;
    }

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        instance.setForcedReturn(PLAZA);
        instance.setTimeout(instance.getEnterFieldId() == WAITING_ROOM ? WAIT_SECONDS : TIME_LIMIT_SECONDS);
        for (Char c : instance.getChars()) { // official: 5 deaths each
            c.setDeathCount(5);
            c.write(net.swordie.ms.connection.packet.UserLocal.deathCountInfo(5));
        }
    }

    /** Waiting room -> boss room for everyone in the waiting room; the clock restarts at 30 min. */
    public synchronized void startFight() {
        Instance instance = getInstance();
        for (Char c : new java.util.ArrayList<>(instance.getChars())) {
            if (c.getFieldID() == WAITING_ROOM) {
                c.warp(instance.getField(BOSS_ROOM));
            }
        }
        instance.setTimeout(TIME_LIMIT_SECONDS);
    }

    @Override
    public boolean canRespawnMobs(Field field) {
        return false; // each floor is cleared once
    }

    @Override
    public synchronized void onMobDeath(Field field, Mob mob) {
        super.onMobDeath(field, mob);
        if (field.getId() == BOSS_ROOM && mob.getTemplateId() == PRINCESS_NO && !finished) {
            finished = true;
            String key = weekKey();
            for (Char c : getInstance().getChars()) {
                PartyQuestRecords.addClear(c, key);
                c.chatMessage(ChatType.SystemNotice, "Princess No is defeated! Take the portal to leave.");
            }
        } else if (field.getId() >= FLOOR_1 && field.getId() < FLOOR_5 && field.getMobs().isEmpty()) {
            for (Char c : field.getChars()) {
                c.chatMessage(ChatType.SystemNotice, "The floor is clear! The portal to the next floor is open.");
            }
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
