# 37701 [Yum Yum] A Message From Chu Chu Village - end script, generated from the official quest texts in the client (Quest.wz QuestInfo/Say).
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
    sm.setSpeakerID(3004726)
    sm.sendSayOkay(u"A message arrived from Chief Lyon of Chu Chu Village. It seems like he has a favor to ask of you.")
    sm.dispose()
sm.setSpeakerID(3004726)
sm.sendNext(u"A message arrived from Chief Lyon of Chu Chu Village.")
if not sm.completeQuest(parentID):
    sm.chat("You haven't met all of this quest's requirements yet.")
sm.dispose()
