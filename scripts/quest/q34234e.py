# 34234 [Morass] The Survivor - end script, generated from the official quest texts in the client (Quest.wz QuestInfo/Say).
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
    sm.setPlayerAsSpeaker()
    sm.sendSayOkay(u"There's still so much chaos around the lab. The fallen can never rest in peace like this...")
    sm.dispose()
sm.setPlayerAsSpeaker()
sm.sendNext(u"You've discovered a mysterious door that has been sealed shut. It's likely even the #p3003469:# doesn't know about it. It must lead to something dangerous. You should keep your distance for now.")
if not sm.completeQuest(parentID):
    sm.chat("You haven't met all of this quest's requirements yet.")
sm.dispose()
