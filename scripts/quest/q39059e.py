# 39059 [Daily Quest] Defeat 200 Combat T-Drone Model A's - end script, generated from the official quest texts in the client (Quest.wz QuestInfo/Say).
# One of Vanishing Journey's daily quests, finished like the others there (q34130e): it counts toward the area's weekly
# tasks and can be taken again
from net.swordie.ms.enums import DailyEntry
from net.swordie.ms.constants import QuestConstants

qm = chr.getQuestManager()
if not sm.hasQuest(parentID):
    sm.dispose()
elif not (qm.isComplete(parentID) and qm.meetsCompleteRequirements(parentID)):
    sm.setSpeakerID(3003104)
    sm.sendSayOkay(u"Defeat 200 Combat T-Drone Model A's, then go to Rona.")
    sm.dispose()
else:
    sm.completeQuestNoCheck(parentID)
    qm.removeQuest(parentID)
    sm.reduceDailyEntry(DailyEntry.VanishingJourney)
    remainingTasks = sm.getRemainingDailyEntries(DailyEntry.VanishingJourney)
    if remainingTasks <= 0:
        sm.createQuestWithQRValue(QuestConstants.VANISHING_JOURNEY_COUNT_QUEST, "count=5")
    sm.setSpeakerID(3003104)
    sm.setParam(2)
    sm.sendNext(u"You defeated 200 Combat T-Drone Model A's, obtained information, and gave it to Rona.")
    sm.setParam(0)
    sm.sendSay("You have " + str(QuestConstants.VANISHING_JOURNEY_WEEKLY_AMOUNT - remainingTasks) + " tasks completed this week. Thanks to you, we're one step closer to understanding this place.")
