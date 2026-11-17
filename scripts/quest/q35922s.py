# id 35922 (Working the Compass), field 100051030
sm.setSpeakerID(1013311) # Gorgonz
sm.setParam(36)
sm.setColor(1)
sm.setInnerOverrideSpeakerTemplateID(1013351) # Gorgonz
res = sm.sendAskAccept("#face0#Soon, the hour of the greatest sunlight will be upon us. That's...noon, in case you were nodding off when I explained it last time. How about it? Are you prepared?")
if not res:
    sm.dispose()
sm.startQuest(parentID)
from net.swordie.ms.loaders import QuestData
qinfo = QuestData.getQuestInfoById(parentID)
quest = chr.getQuestManager().getQuestById(parentID)
if (qinfo is not None and qinfo.getEndNpc() == 0 and not qinfo.isSelfComplete() and quest is not None
        and quest.isComplete(chr) and chr.getQuestManager().meetsCompleteRequirements(parentID)):
    sm.completeQuestNoCheck(parentID)  # nothing to hand in: the scene that follows finished it in GMS
sm.warp(910090310)
