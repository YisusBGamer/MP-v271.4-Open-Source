# id 34464 ([Arcana] The Spirit Tree's Return), field 450005220
sm.setSpeakerID(3003311) # Lost Tree Spirit
sm.setParam(2)
res = sm.sendAskAccept("#b(Every minute the missing spirit is out here, it's in danger. You should escort it to safety immediately.)#k")
if not res:
    sm.dispose()
sm.startQuest(parentID)
from net.swordie.ms.loaders import QuestData
qinfo = QuestData.getQuestInfoById(parentID)
quest = chr.getQuestManager().getQuestById(parentID)
if (qinfo is not None and qinfo.getEndNpc() == 0 and not qinfo.isSelfComplete() and quest is not None
        and quest.isComplete(chr) and chr.getQuestManager().meetsCompleteRequirements(parentID)):
    sm.completeQuestNoCheck(parentID)  # nothing to hand in: the scene that follows finished it in GMS
sm.closeUI(1128)
sm.closeUI(1128)
sm.sendNext("#b(If the Tree Spirit is exposed to this evil aura any longer, it may be irreversibly corrupted. You need to defeat the Wild Spirits nearby and save it.)#k")
sm.closeUI(1128)
sm.closeUI(1128)
sm.warp(940200220)
