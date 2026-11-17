# 39064 [Daily Quest] Defeat 200 Monkeyrogs - end script, generated from the official quest texts in the client (Quest.wz QuestInfo/Say).
# One of Chu Chu Island's daily quests, finished like the others there (q39020e): it counts toward the area's weekly
# tasks and can be taken again
from net.swordie.ms.enums import DailyEntry
from net.swordie.ms.constants import QuestConstants

qm = chr.getQuestManager()
if not sm.hasQuest(parentID):
    sm.dispose()
elif not (qm.isComplete(parentID) and qm.meetsCompleteRequirements(parentID)):
    sm.setPlayerAsSpeaker()
    sm.sendSayOkay(u"Defeat #b#o8642050:##k x#r200#k and talk to #b#p3003152:##k in #b#m450002000:##k.")
    sm.dispose()
else:
    sm.completeQuestNoCheck(parentID)
    qm.removeQuest(parentID)
    sm.reduceDailyEntry(DailyEntry.ChuChu)
    remainingTasks = sm.getRemainingDailyEntries(DailyEntry.ChuChu)
    if remainingTasks <= 0:
        sm.createQuestWithQRValue(QuestConstants.CHU_CHU_COUNT_QUEST, "count=3")
    sm.setSpeakerID(3003152)
    sm.setParam(2)
    sm.sendNext(u"You accepted a mission from #b#p3003152:##k and defeated #b#o8642050:##k x#r200#k.")
    sm.setParam(0)
    sm.sendSay("You have " + str(QuestConstants.CHU_CHU_WEEKLY_AMOUNT - remainingTasks) + " tasks completed this week. Thanks to you, we're one step closer to understanding this place.")
