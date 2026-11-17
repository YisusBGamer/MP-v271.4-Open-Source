# id 32129 ([Ellinel Fairy Academy] Professor Peace), field 101073200
sm.setSpeakerID(1500015) # Cootie the Really Small
res = sm.sendAskAccept("Not bad, #h0#. You really thought like a fairy out there. Let's return to the Headmistress in Ellinel. \r\n#b(You will be moved to Ellinel if you accept.)#k")
if not res:
    sm.dispose()
sm.startQuest(parentID)
from net.swordie.ms.loaders import QuestData
qinfo = QuestData.getQuestInfoById(parentID)
quest = chr.getQuestManager().getQuestById(parentID)
if (qinfo is not None and qinfo.getEndNpc() == 0 and not qinfo.isSelfComplete() and quest is not None
        and quest.isComplete(chr) and chr.getQuestManager().meetsCompleteRequirements(parentID)):
    sm.completeQuestNoCheck(parentID)  # nothing to hand in: the scene that follows finished it in GMS
sm.setParam(1)
sm.sendNext("Great. All the kids must be back by now, right?")
sm.warp(101072001)
