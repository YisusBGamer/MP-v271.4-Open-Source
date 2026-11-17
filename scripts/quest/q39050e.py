# 39050 [Daily Quest] Obtain 50 Cave Spirit Energies - end script, generated from the official quest texts in the client (Quest.wz QuestInfo/Say).
# One of Arcana's daily quests, finished like the others there (q39040e): it counts toward the area's weekly tasks and
# can be taken again
from net.swordie.ms.enums import DailyEntry
from net.swordie.ms.constants import QuestConstants

qm = chr.getQuestManager()
if not sm.hasQuest(parentID):
    sm.dispose()
elif not (qm.isComplete(parentID) and qm.meetsCompleteRequirements(parentID)):
    sm.setPlayerAsSpeaker()
    sm.sendSayOkay(u"Collect #r50#k #i4036574:# #t4036574:# items and deliver them to #b#p3003322:##k in #b#m450005000:##k.")
    sm.dispose()
else:
    sm.consumeItem(4036574, 50)
    sm.completeQuestNoCheck(parentID)
    qm.removeQuest(parentID)
    sm.reduceDailyEntry(DailyEntry.Arcana)
    remainingTasks = sm.getRemainingDailyEntries(DailyEntry.Arcana)
    if remainingTasks <= 0:
        sm.createQuestWithQRValue(QuestConstants.ARCANA_COUNT_QUEST, "count=3")
    sm.setSpeakerID(3003322)
    sm.setParam(2)
    sm.sendNext(u"You accepted a mission from #b#p3003322:##k and collected #r50#k #i4036574:#  #t4036574:# items.")
    sm.setParam(0)
    sm.sendSay("You have " + str(QuestConstants.ARCANA_WEEKLY_AMOUNT - remainingTasks) + " tasks completed this week. Thanks to you, we're one step closer to understanding this place.")
