# 35707 [Labyrinth of Suffering] Disappearance and Ambush - start script, generated from the official quest texts in the client (Quest.wz QuestInfo/Say).
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

sm.setSpeakerID(3003787)
if sm.hasQuest(parentID) or sm.hasQuestCompleted(parentID):
    satisfy_own_record_flags(parentID)
    finish_without_npc(parentID)
    sm.dispose()
sm.sendNext(u"Both the search party and the battleship repair team have disappeared, and not a single person noticed. The injured soldier you... spoke with has begun spouting gibberish, and the barrier stones have started vibrating. This doesn't bode well.")
if not sm.sendAskAccept(u"#b(Accept #e[Labyrinth of Suffering] Disappearance and Ambush#n?)#k"):
    sm.dispose()
sm.startQuest(parentID)
satisfy_own_record_flags(parentID)
finish_without_npc(parentID)
sm.dispose()
