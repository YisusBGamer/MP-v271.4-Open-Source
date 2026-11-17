# 37711 [Yum Yum] Check the Flowers 1 - end script, generated from the official quest texts in the client (Quest.wz QuestInfo/Say).
from net.swordie.ms.loaders import QuestData
from net.swordie.ms.client.character.quest.requirement import QuestRecordRequirement


def satisfy_own_record_flags(qid):
    # flags on this quest's own record that its (unwritten) live script would have set
    info = QuestData.getQuestInfoById(qid)
    if info is None:
        return
    for req in info.getQuestCompleteRequirements():
        if not isinstance(req, QuestRecordRequirement) or req.getQuestID() != qid or req.hasRequirements(chr):
            continue
        for entry in req.getEntries():
            if entry.getExVariable():
                sm.setQRValue(qid, entry.getExVariable(), entry.getValue())
            else:
                sm.setQRValue(qid, entry.getValue())
            if not req.isAndOption():
                break

if not sm.hasQuest(parentID):
    sm.dispose()
satisfy_own_record_flags(parentID)
if not (chr.getQuestManager().isComplete(parentID) and chr.getQuestManager().meetsCompleteRequirements(parentID)):
    sm.setSpeakerID(3004706)
    sm.sendSayOkay(u"Investigate the flowers, and cut down the Lytones getting in your way.")
    sm.dispose()
sm.setSpeakerID(3004706)
sm.sendNext(u"Bappy's match wasn't made here.")
if not sm.completeQuest(parentID):
    sm.chat("You haven't met all of this quest's requirements yet.")
sm.dispose()
