# 35705 [Labyrinth of Suffering] Signal Flare Necessities - end script, generated from the official quest texts in the client (Quest.wz QuestInfo/Say).
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
    sm.setSpeakerID(3003791)
    sm.sendSayOkay(u"Hunt #b#o8644700##k and #b#o8644701##k monsters near the #m450011120# to gather #i4036452#  #r#t4036452:##k and #i4036453#  #r#t4036453:##k items.\r\nYou can use the #rdoor on your left#k.")
    sm.dispose()
sm.setSpeakerID(3003791)
sm.sendNext(u"None of the others came back, so you set up a signal flare to guide them. The only trouble is, the flare did no good. It's like the darkness just swallowed up the light. To make matters worse, when one of the soldiers finally did return, he was out of breath and panicky. What is going on?")
if not sm.completeQuest(parentID):
    sm.chat("You haven't met all of this quest's requirements yet.")
sm.dispose()
