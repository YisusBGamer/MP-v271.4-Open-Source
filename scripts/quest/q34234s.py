# 34234 [Morass] The Survivor - start script, generated from the official quest texts in the client (Quest.wz QuestInfo/Say).
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

sm.setSpeakerID(3003469)
if sm.hasQuest(parentID) or sm.hasQuestCompleted(parentID):
    satisfy_own_record_flags(parentID)
    finish_without_npc(parentID)
    sm.dispose()
sm.sendNext(u"#b#p3003469:##k is the sole survivor of that fateful day, and now holds vigil for his fallen friends.")
if not sm.sendAskAccept(u"Help #b#p3003469:##k by defeating the enemies near #b#m450006240:##k.\n\nYou can find #r#o8644406:##k on the #bleft side of the lab#k, and #r#o8644407:##k on the #bright side#k.\r\n\r\n#b(Accept #e[Morass] The Survivor#n?)#k"):
    sm.dispose()
sm.startQuest(parentID)
satisfy_own_record_flags(parentID)
finish_without_npc(parentID)
sm.dispose()
