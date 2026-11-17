# id 34938 (Unleash the Specter), field 402000644
sm.setSpeakerType(3)
sm.setParam(37)
sm.setColor(1)
sm.setInnerOverrideSpeakerTemplateID(3001500) # Ark
sm.sendNext("#face5#All right, I'll be the bait. You all defend, okay?")
sm.sendSay("#face8#We have to stay strong for as long as it takes Ferret to get that thing going.")
sm.setSpeakerType(4)
sm.setSpeakerID(3001423) # Salvo
res = sm.sendAskAccept("#face5#It'll take everything we've got, but we can do it. Let's go.")
if not res:
    sm.dispose()
sm.startQuest(parentID)
from net.swordie.ms.loaders import QuestData
qinfo = QuestData.getQuestInfoById(parentID)
quest = chr.getQuestManager().getQuestById(parentID)
if (qinfo is not None and qinfo.getEndNpc() == 0 and not qinfo.isSelfComplete() and quest is not None
        and quest.isComplete(chr) and chr.getQuestManager().meetsCompleteRequirements(parentID)):
    sm.completeQuestNoCheck(parentID)  # nothing to hand in: the scene that follows finished it in GMS
sm.warp(940205100)
