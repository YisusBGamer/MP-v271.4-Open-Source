# 34294 [Daily Quest] To Save Trueffet - end script, generated from the official quest texts in the client (Quest.wz QuestInfo/Say).
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
    sm.sendSayOkay(u"Move to #bCrumbling Trueffet#k by using the hidden portal in #bThat Day in Trueffet#k to defeat #r#o8644430:##k and keep the place from collapsing.\r\n\r\nOnce you stabilize Trueffet, tell #bJean#k in #bTrueffet Square#k.")
    sm.dispose()
sm.setSpeakerID(3003432)
sm.sendNext(u"You defeated #o8644430:# in Crumbling Trueffet and kept the place stable.")
if not sm.completeQuest(parentID):
    sm.chat("You haven't met all of this quest's requirements yet.")
sm.dispose()
