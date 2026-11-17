# 35706 [Labyrinth of Suffering] Into the Labyrinth - end script, generated from the official quest texts in the client (Quest.wz QuestInfo/Say).
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
    sm.sendSayOkay(u"Go through the #rdoor on the right#k in the #b#m450011120##k.")
    sm.dispose()
sm.setSpeakerID(3003709)
sm.sendNext(u"There's no trace of the missing people. What could have happened to them?")
if not sm.completeQuest(parentID):
    sm.chat("You haven't met all of this quest's requirements yet.")
if sm.getFieldID() == 993063003:
    sm.warp(450011120, 1)
sm.dispose()
