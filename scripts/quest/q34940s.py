# id 34940 (Refuge Survival), field 402000640
sm.setSpeakerType(3)
sm.setParam(37)
sm.setColor(1)
sm.setInnerOverrideSpeakerTemplateID(3001500) # Ark
sm.sendNext("#face5#They've defended the refuge well so far. I'll take it from here.")
res = sm.sendAskAccept("#face8#I can be the strength they need.")
if not res:
    sm.dispose()
sm.startQuest(parentID)
from net.swordie.ms.loaders import QuestData
qinfo = QuestData.getQuestInfoById(parentID)
quest = chr.getQuestManager().getQuestById(parentID)
if (qinfo is not None and qinfo.getEndNpc() == 0 and not qinfo.isSelfComplete() and quest is not None
        and quest.isComplete(chr) and chr.getQuestManager().meetsCompleteRequirements(parentID)):
    sm.completeQuestNoCheck(parentID)  # nothing to hand in: the scene that follows finished it in GMS
sm.sendNext("#face8#I'll save you.")
sm.warp(940205400)
