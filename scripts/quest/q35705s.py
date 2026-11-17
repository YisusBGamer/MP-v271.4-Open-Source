# 35705 [Labyrinth of Suffering] Signal Flare Necessities - start script, generated from the official quest texts in the client (Quest.wz QuestInfo/Say).
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


def finish_without_npc(qid):
    # no NPC to hand it in to and nothing left to do: its live script finished it (as quest/undefined.py does)
    info = QuestData.getQuestInfoById(qid)
    if info is None or info.getEndNpc() != 0 or info.isSelfComplete():
        return
    qm = chr.getQuestManager()
    quest = qm.getQuestById(qid)
    if quest is not None and qm.hasQuestInProgress(qid) and quest.isComplete(chr) and qm.meetsCompleteRequirements(qid):
        sm.completeQuestNoCheck(qid)

sm.setSpeakerID(3003791)
if sm.hasQuest(parentID) or sm.hasQuestCompleted(parentID):
    satisfy_own_record_flags(parentID)
    finish_without_npc(parentID)
    sm.dispose()
sm.sendNext(u"If the others are going to have any hope of finding you, you need to fire off a signal flare to let them know where you are.\nThere's nothing you can use to start a fire, but it's far too dangerous to wander blindly around the area...")
if not sm.sendAskAccept(u"If you tie a rope to yourself, you'll be able to find your way back to your starting point.\nAnd now that you've got that sorted, you can hunt #b#o8644700##k and #b#o8644701##k monsters near the #m450011120# to gather #i4036452#  #r#t4036452:##k and #i4036453#  #r#t4036453:##k items.\n\nThe #rdoor on your left#k is as good a place to start as any.\r\n\r\n#b(Accept #e[Labyrinth of Suffering] Signal Flare Necessities#n?)#k"):
    sm.dispose()
sm.startQuest(parentID)
satisfy_own_record_flags(parentID)
finish_without_npc(parentID)
sm.dispose()
