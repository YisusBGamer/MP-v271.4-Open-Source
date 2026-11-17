package net.swordie.ms.client.surprisemission;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.constants.GameConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.enums.RandomMissionType;
import net.swordie.ms.loaders.QuestData;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created on 29/01/2022.
 *
 * @author Asura
 */
public class SurpriseMissionModule {

    private static final Logger log = LogManager.getLogger();
    private static final int POOPA_EGG = 4000158;

    public static void setSurpriseMissionCooltime(Char chr) {
        chr.addSkillCoolTime(QuestConstants.SURPRISE_MISSION_QUEST_ID, GameConstants.SURPRISE_MISSION_COOLTIME);
    }

    private static boolean isOnSurpriseMissionCooltime(Char chr) {
        return chr.hasSkillOnCooldown(QuestConstants.SURPRISE_MISSION_QUEST_ID);
    }

    private static boolean hasSurpriseMissionCurrently(Char chr) {
        return chr.getSurpriseMission() != null;
    }

    private static boolean canStartSurpriseMission(Char chr) {
        return !isOnSurpriseMissionCooltime(chr) && !hasSurpriseMissionCurrently(chr) && chr.getLevel() >= GameConstants.SURPRISE_MISSION_MIN_LEVEL;
    }


    public static void tryStartSurpriseMission(Char chr) {
        if (!canStartSurpriseMission(chr)) {
            return;
        }

        if (Util.succeedProp(GameConstants.SURPRISE_MISSION_CHANCE)) {
            startSurpriseMission(chr);
        }
    }

    public static void startSurpriseMission(Char chr) {
        var randomSurpriseMission = Util.getRandomFromCollection(QuestConstants.getSurpriseMissionQuestIds());

        var qi = QuestData.getQuestInfoById(randomSurpriseMission);
        if (qi == null) {
            log.warn("Surprise mission quest " + randomSurpriseMission + " has no quest data, no mission this time");
            return;
        }
        var timeLimit = qi.getTimeLimit(); // seconds

        var currentSurpriseMissionQuest = QuestData.createQuestFromId(randomSurpriseMission);
        var surpriseMissionInfoQuest = chr.getQuestManager().getOrCreateQuestById(QuestConstants.SURPRISE_MISSION_QUEST_ID);

        var now = FileTime.currentTime();
        var expiration = FileTime.fromDate(now.toLocalDateTime().plusSeconds(timeLimit));

        currentSurpriseMissionQuest.setPropertyEx("QET", expiration.toClientInYYYYMMDDHHMMSS());

        surpriseMissionInfoQuest.setPropertyEx("count", 0); // completed surprise missions today
        surpriseMissionInfoQuest.setPropertyEx("Quest", randomSurpriseMission);
        surpriseMissionInfoQuest.setPropertyEx("QET", now.toClientInYYYYMMDDHHMMSS());
        surpriseMissionInfoQuest.setPropertyEx("state", 2); // 1 = Setting up | 2 = In Progress | 3 = Finished (Claim Reward)

        var mission = new SurpriseMission(chr, currentSurpriseMissionQuest, surpriseMissionInfoQuest, now, expiration);
        chr.setSurpriseMission(mission);

        showSurpriseMissionToClient(chr, mission);
    }

    public static void showSurpriseMissionToClient(Char chr, SurpriseMission mission) {
        var quest = mission.getCurrentSurpriseMission();
        var info = mission.getSurpriseMissionInfo();

        chr.write(WvsContext.message(MessagePacket.setCurrentTime(FileTime.currentTime())));
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(quest)));
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(info)));
        chr.getQuestManager().addQuest(quest);
    }

    // Allows user to claim reward
    public static void completeSurpriseMission(Char chr) {
        if (chr.getSurpriseMission() == null) {
            return;
        }

        var mission = chr.getSurpriseMission();
        var now = FileTime.currentTime();
        var expiration = FileTime.fromDate(now.toLocalDateTime().plusSeconds(GameConstants.SURPRISE_MISSION_CLAIM_REWARD_TIME_LIMIT));

        var info = chr.getQuestManager().getQuestById(QuestConstants.SURPRISE_MISSION_QUEST_ID);
        if (info == null) {
            // fall back to the mission's own record when the character's copy is gone
            info = mission.getSurpriseMissionInfo();
            if (info == null) {
                return;
            }
            chr.getQuestManager().addQuest(info);
        }
        info.setPropertyEx("state", 3);
        info.setPropertyEx("QET", expiration.toClientInYYYYMMDDHHMMSS());

        mission.stopTimer(); // stop previous timer

        // start the timer for the reward claim
        chr.setSurpriseMission(new SurpriseMission(chr, mission.getCurrentSurpriseMission(), info, now, expiration));

        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(info)));
        setSurpriseMissionCooltime(chr);
        net.swordie.ms.client.character.achievement.MapleAchievements.onSurpriseMission(chr,
                mission.getCurrentSurpriseMission().getQRKey(),
                (now.toMillis() - mission.getStartTime().toMillis()) / 1000);
    }

    // User pressed 'claim reward'
    public static void claimSurpriseMissionReward(Char chr, SurpriseMission mission) {
        if (mission == null) {
            return;
        }

        var itemId = POOPA_EGG;
        var quantity = 1;
        var money = 1;

        if (!chr.canHold(itemId, quantity)) {
            // v271's mission window has no result messages (0x145abad40 handles types 0-3 only)
            chr.boxMessage("Please make room in your inventory to claim the reward.");
            return;
        }

        if (!chr.canAddMoney(money)) {
            chr.boxMessage("You can't hold any more mesos.");
            return;
        }

        // TODO Proper Reward for completing Surprise Mission
        chr.addItemToInventory(itemId, quantity);
        chr.addMoney(money);

        removeSurpriseMission(chr);
        chr.write(WvsContext.randomMissionClose()); // closes the mission windows
    }

    public static void removeSurpriseMission(Char chr) {
        var mission = chr.getSurpriseMission();

        if (mission == null) {
            return;
        }

        mission.stopTimer();
        var qm = chr.getQuestManager();
        var quest = qm.getQuestById(mission.getCurrentSurpriseMission().getQRKey());
        if (quest != null) {
            qm.removeQuest(quest);
        }


        var info = qm.getQuestById(QuestConstants.SURPRISE_MISSION_QUEST_ID);
        info.setPropertyEx("Quest", 0);
        info.setPropertyEx("state", 1);

        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(info)));
        chr.write(WvsContext.message(MessagePacket.questExpireMessage(mission.getCurrentSurpriseMission().getQRKey())));

        chr.setSurpriseMission(null);

        setSurpriseMissionCooltime(chr);
    }
}
