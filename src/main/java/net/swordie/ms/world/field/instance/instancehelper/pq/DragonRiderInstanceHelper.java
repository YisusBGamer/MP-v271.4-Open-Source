package net.swordie.ms.world.field.instance.instancehelper.pq;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.npc.Npc;
import net.swordie.ms.loaders.NpcData;
import net.swordie.ms.world.field.Foothold;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Portal;
import net.swordie.ms.world.field.instance.Instance;

import java.util.*;

/**
 * Dragon Rider (Leafre party quest), as on live servers (maplestorywiki.net/w/Dragon_Rider):
 * <ul>
 *     <li>Level 120+ (recommended 150-169), a party of 1-6, 5 entries a day, 20 minutes. The Soaring skill (needed to
 *     fly here, and only usable here) is given on entry to anyone missing it.</li>
 *     <li>Crimson Sky 1: defeat the 20 Soaring Hawks and 20 Soaring Eagles.</li>
 *     <li>Crimson Sky 2: defeat the Soaring Wyverns and the Soaring Griffey.</li>
 *     <li>Crimson Sky Edge: defeat Dragonoir.</li>
 *     <li>Crimson Sky Nest Entrance: fly through the falling rocks and spears to the nest.</li>
 *     <li>Crimson Sky Nest: defeat the Dragon Rider.</li>
 * </ul>
 * Completion EXP: 6,069,000 * 1.025^(level - 120) up to level 169 (times the server's EXP rate), split over the
 * stages; a clear also gives +57 Willpower and +34 Ambition EXP and a Dragon Rider's Box for the character's job.
 */
public class DragonRiderInstanceHelper extends PartyQuestInstanceHelper {

    public static final String RECORD_KEY = "dragon_rider";
    public static final int MIN_LEVEL = 120;
    public static final int MIN_PARTY = 1;
    public static final int MAX_PARTY = 6;
    public static final int DAILY_ENTRIES = 5;
    public static final int TIME_LIMIT_SECONDS = 20 * 60;

    public static final int DOCK = 240080000;
    public static final int STAGE_1 = 240080100;
    public static final int STAGE_2 = 240080200;
    public static final int STAGE_3 = 240080300;
    public static final int STAGE_4 = 240080400;
    public static final int STAGE_5 = 240080500;

    private static final int DRAGONOIR = 8300006;
    private static final int DRAGON_RIDER = 8300007;
    private static final int SOARING = 1026;
    // Dragon Rider's Box: Warrior, Magician, Bowman, Thief, Pirate
    private static final int[] JOB_BOXES = {2022652, 2022653, 2022655, 2022654, 2022656};
    private static final int[] STAGE_PERCENT = {0, 15, 20, 20, 5, 40};

    private boolean dragonoirSpawned;
    private boolean riderSpawned;
    private boolean finished;

    @Override
    public void setup(Instance instance) {
        super.setup(instance);
        instance.setForcedReturn(DOCK);
        instance.setTimeout(TIME_LIMIT_SECONDS);
        for (Char c : instance.getChars()) {
            giveSoaring(c);
        }
    }

    @Override
    protected void startStage(int stageNumber) {
    }

    /** Soaring (needed to fly up to the Doorway on the Dock and through the Crimson Sky) */
    public static void giveSoaring(Char chr) {
        var jobEnum = JobConstants.JobEnum.getJobById(chr.getJob());
        if (jobEnum == null) {
            return;
        }
        int skillId = jobEnum.getBeginnerJobId() * 10000 + SOARING;
        if (SkillData.getSkillInfoById(skillId) != null && !chr.hasSkill(skillId)) {
            chr.addSkill(skillId, 1, 1);
        }
    }

    // ---------------------------------------------------------------------------------------------------------- entry

    public static String getEntryBlocker(Char leader) {
        var party = leader.getParty();
        if (party == null) {
            return "Please make a party first (you can go alone in a party of 1).";
        }
        if (party.getPartyLeaderID() != leader.getId()) {
            return "Please have your party leader open the doorway.";
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

    // ---------------------------------------------------------------------------------------------------------- maps

    private static int stageOf(int fid) {
        switch (fid) {
            case STAGE_1:
                return 1;
            case STAGE_2:
                return 2;
            case STAGE_3:
                return 3;
            case STAGE_4:
                return 4;
            case STAGE_5:
                return 5;
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
        if (portal.getName() != null && (portal.getName().startsWith("next") || portal.getName().equals("boss00"))
                && s > 0 && s < 5 && !isStageCleared(s)) {
            chr.chatMessage(ChatType.SystemNotice, "The way on is still blocked. Clear this area first.");
            return false;
        }
        return true;
    }

    /** Sky_Next */
    public void next(Char chr) {
        int s = stageOf(chr.getField().getId());
        if (s == 0 || s >= 5 || !isStageCleared(s)) {
            chr.chatMessage(ChatType.SystemNotice, "The way on is still blocked. Clear this area first.");
            return;
        }
        chr.warp(new int[]{0, STAGE_2, STAGE_3, STAGE_4, STAGE_5}[s]);
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        if (newField != null) {
            int fid = newField.getId();
            synchronized (this) {
                if (fid == STAGE_3 && !dragonoirSpawned) {
                    dragonoirSpawned = true;
                    spawnBoss(newField, DRAGONOIR);
                } else if (fid == STAGE_4) {
                    // the nest entrance is an obstacle flight: reaching it is the stage
                    clearStage(4, newField, STAGE_PERCENT[4]);
                } else if (fid == STAGE_5 && !riderSpawned) {
                    riderSpawned = true;
                    spawnBoss(newField, DRAGON_RIDER);
                }
            }
        }
        super.onWarp(chr, oldField, newField);
    }

    private void spawnBoss(Field field, int template) {
        Position pos = field.getRandomPosOnWalkableFoothold(150, false);
        if (pos != null) {
            field.spawnMob(template, pos.getX(), pos.getY(), false, -1);
        }
    }

    // ---------------------------------------------------------------------------------------------------------- monsters

    @Override
    public void onMobSpawn(Field field, Mob mob) {
        scaleMobToParty(mob);
        super.onMobSpawn(field, mob);
    }

    @Override
    public synchronized void onMobDeath(Field field, Mob mob) {
        int s = stageOf(field.getId());
        if ((s == 1 || s == 2 || s == 3) && field.getMobs().isEmpty()) {
            if (clearStage(s, field, STAGE_PERCENT[s])) {
                notice(field, "The sky is clear! Fly on through the portal.");
            }
        } else if (s == 5 && mob.getTemplateId() == DRAGON_RIDER && !finished) {
            finished = true;
            for (Mob m : new ArrayList<>(field.getMobs())) {
                m.die(false);
            }
            clearStage(5, field, STAGE_PERCENT[5]);
            giveTraitExp(Stat.willEXP, 57);
            giveTraitExp(Stat.charismaEXP, 34);
            for (Char c : getInstance().getChars()) {
                PartyQuestRecords.addClear(c, RECORD_KEY);
                int box = JOB_BOXES[Math.max(0, Math.min(4, jobBranch(c.getJob()) - 1))];
                if (c.canHold(box, 1)) {
                    c.addItemToInventory(box, 1);
                }
            }
            // the defeated Dragon Rider (NPC 2085003) starts "Tears of Repentance" (3761, after "Towards the Sky 2"). He
            // stays until the party flies back, which waits a minute instead of 30 seconds when someone can take the quest.
            boolean repentance = getInstance().getChars().stream().anyMatch(c ->
                    c.getQuestManager().hasQuestCompleted(TOWARDS_THE_SKY_2)
                            && !c.getQuestManager().hasQuestCompleted(TEARS_OF_REPENTANCE));
            spawnDragonRiderNpc(field, mob.getPosition());
            int leaveSeconds = repentance ? 60 : 30;
            notice(field, "The Dragon Rider is defeated! You got a Dragon Rider's Box. You'll fly back to the dock "
                    + "in " + leaveSeconds + " seconds.");
            addTimer(EventManager.addEvent(() -> getInstance().clear(), leaveSeconds * 1000L));
        }
        super.onMobDeath(field, mob);
    }

    private static final int DRAGON_RIDER_NPC = 2085003;
    private static final int TOWARDS_THE_SKY_2 = 3759;
    private static final int TEARS_OF_REPENTANCE = 3761;

    private static void spawnDragonRiderNpc(Field field, Position at) {
        if (field.getLifeByTemplateId(DRAGON_RIDER_NPC) instanceof Npc) {
            return;
        }
        Npc npc = NpcData.getNpcDeepCopyById(DRAGON_RIDER_NPC);
        if (npc == null || at == null) {
            return;
        }
        int x = at.getX();
        int y = at.getY();
        npc.setPosition(new Position(x, y));
        npc.setCy(y);
        npc.setRx0(x + 50);
        npc.setRx1(x - 50);
        Foothold fh = field.findFootHoldBelow(new Position(x, y - 2));
        if (fh != null) {
            npc.setFh(fh.getId());
            npc.setPosition(new Position(x, fh.getYFromX(x)));
            npc.setCy(fh.getYFromX(x));
        }
        npc.setNotRespawnable(true);
        field.spawnLife(npc, null);
    }

    /** 1 warrior, 2 magician, 3 bowman, 4 thief, 5 pirate */
    private static int jobBranch(int job) {
        if (JobConstants.isWarriorEquipJob(job)) return 1;
        if (JobConstants.isMageEquipJob(job)) return 2;
        if (JobConstants.isArcherEquipJob(job)) return 3;
        if (JobConstants.isThiefEquipJob(job)) return 4;
        return 5;
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
    protected long getBaseCompletionExp() {
        return 6_069_000L;
    }

    @Override
    protected int getExpMinLevel() {
        return 120;
    }

    @Override
    protected int getExpMaxLevel() {
        return 169;
    }

    @Override
    protected List<Integer> getPartyQuestItems() {
        return Collections.emptyList();
    }
}
