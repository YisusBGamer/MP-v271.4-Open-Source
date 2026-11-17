# 35710 [Labyrinth of Suffering] The Only One Left - start script, generated from the official quest texts in the client (Quest.wz QuestInfo/Say).
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

sm.setPlayerAsSpeaker()
if sm.hasQuest(parentID) or sm.hasQuestCompleted(parentID):
    satisfy_own_record_flags(parentID)
    finish_without_npc(parentID)
    sm.dispose()
sm.sendNext(u"You're all alone, and you can hear sounds issuing from the other side of that door. You must find the others...")
if not sm.sendAskAccept(u"You're all alone, and you can hear sounds issuing from the other side of that door. You must find the others...\n\n(* Use the #rdoor on the right#k and move to the previously inaccessible #rEast Road#k from the #b#m450011450##k.)\r\n\r\n#b(Accept #e[Labyrinth of Suffering] The Only One Left#n?)#k"):
    sm.dispose()
sm.startQuest(parentID)
satisfy_own_record_flags(parentID)
finish_without_npc(parentID)
sm.dispose()
