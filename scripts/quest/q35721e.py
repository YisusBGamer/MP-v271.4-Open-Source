# 35721 [Labyrinth of Suffering] A Tiny Crack - end script, generated from the official quest texts in the client (Quest.wz QuestInfo/Say).
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
    sm.setSpeakerID(3003706)
    sm.sendSayOkay(u"You seem pretty tired...")
    sm.dispose()
sm.setSpeakerID(3003706)
sm.sendNext(u"As you travel aimlessly through the labyrinth, desperate to find a way out, you can't help but wonder what's happening with the Alliance members and Maple World.")
if not sm.completeQuest(parentID):
    sm.chat("You haven't met all of this quest's requirements yet.")
sm.dispose()
