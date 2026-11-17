# 37716 [Yum Yum] Awakened Meaning - start script, generated from the official quest texts in the client (Quest.wz QuestInfo/Say).
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

sm.setSpeakerID(3004714)
if sm.hasQuest(parentID) or sm.hasQuestCompleted(parentID):
    satisfy_own_record_flags(parentID)
    finish_without_npc(parentID)
    sm.dispose()
sm.sendNext(u"You've found Muppy after a fair bit of effort.\nTalk to Muppy.")
if not sm.sendAskAccept(u"#b(Accept #e[Yum Yum] Awakened Meaning#n?)#k"):
    sm.dispose()
sm.startQuest(parentID)
satisfy_own_record_flags(parentID)
if sm.getFieldID() == 450015180 and not sm.hasMobsInField(450015180, 8642090):
    sm.spawnMob(8642090, 150, 103, False)  # Caster has to be beaten (field/enter_450015180.py brings him back)
    sm.sendSayOkay(u"#b(Caster lunges at you!)#k")
sm.dispose()
