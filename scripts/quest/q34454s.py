# id 34454 ([Arcana] The Floral Flute), field 450005015
sm.setSpeakerID(3003302) # Wind Spirit
sm.setParam(4)
sm.setInnerOverrideSpeakerTemplateID(3003302) # Wind Spirit
sm.sendNext("Speak, speak! I, Wind Spirit, await your quest. Hurry, hurry.")
sm.setInnerOverrideSpeakerTemplateID(3003301) # Small Spirit
sm.sendSay("Wind Spirit, could you carry us down this cliff to where the Floral Flute is?")
sm.setInnerOverrideSpeakerTemplateID(3003302) # Wind Spirit
sm.sendSay("Dooot~ The flute below the cliff, the floral flute that toots. Dooooooot~")
sm.setParam(0)
res = sm.sendAskAccept("Yes, we'll ride the wind, zoomy and free. Down, down, to the flute that toots. Ready?")
if not res:
    sm.dispose()
sm.startQuest(parentID)
from net.swordie.ms.loaders import QuestData
qinfo = QuestData.getQuestInfoById(parentID)
quest = chr.getQuestManager().getQuestById(parentID)
if (qinfo is not None and qinfo.getEndNpc() == 0 and not qinfo.isSelfComplete() and quest is not None
        and quest.isComplete(chr) and chr.getQuestManager().meetsCompleteRequirements(parentID)):
    sm.completeQuestNoCheck(parentID)  # nothing to hand in: the scene that follows finished it in GMS
sm.warp(940200206)
