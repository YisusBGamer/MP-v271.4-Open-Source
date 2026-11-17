# id 35927 (Lighting the Way), field 100051043
sm.setSpeakerID(1013317) # Brie
sm.setParam(36)
sm.setColor(1)
sm.setInnerOverrideSpeakerTemplateID(1013350) # Brie
res = sm.sendAskAccept("#face0#Let's activate the first glowpod and swim down. Aaaah... Deep breaths! It'll be fine! I'm with a professional Explorer! ...Still, it never hurts to be careful!\r\n\r\n #r *You'll be automatically moved to the quest map.#k")
if not res:
    sm.dispose()
sm.startQuest(parentID)
from net.swordie.ms.loaders import QuestData
qinfo = QuestData.getQuestInfoById(parentID)
quest = chr.getQuestManager().getQuestById(parentID)
if (qinfo is not None and qinfo.getEndNpc() == 0 and not qinfo.isSelfComplete() and quest is not None
        and quest.isComplete(chr) and chr.getQuestManager().meetsCompleteRequirements(parentID)):
    sm.completeQuestNoCheck(parentID)  # nothing to hand in: the scene that follows finished it in GMS
sm.warp(910090312)
