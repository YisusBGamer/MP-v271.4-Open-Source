# FriendStory (Shinsoo International School): plays the client's lines, if any, like quest/undefined.py, and
# also does what the quest's scene does, from the quest's own text.
# Hankie in the Tree (32757): "You got the Handkerchief out of the tree for Cygnus." - Cygnus' Handkerchief
# (4034162) is what Cygnus' Handkerchief (32713) asks for.
from net.swordie.ms.loaders import QuestData
from net.swordie.ms.client.character.quest.requirement import QuestRecordRequirement


def own_record(qid, record):
    # as quest/undefined.py: the quest's own record, and FriendStory's shared scene record 33040
    return record == qid or (32700 <= qid <= 33100 and record == 33040)


def satisfy_own_record_flags(qid):
    info = QuestData.getQuestInfoById(qid)
    if info is None:
        return
    for req in info.getQuestCompleteRequirements():
        if not isinstance(req, QuestRecordRequirement) or not own_record(qid, req.getQuestID()) \
                or req.hasRequirements(chr):
            continue
        for entry in req.getEntries():
            if entry.getExVariable():
                sm.setQRValue(req.getQuestID(), entry.getExVariable(), entry.getValue())
            else:
                sm.setQRValue(req.getQuestID(), entry.getValue())
            if not req.isAndOption():
                break


def quest_npc():
    try:
        return questNpc
    except NameError:
        return 0


def fs_start(qid):
    if not sm.hasQuest(qid) and not sm.hasQuestCompleted(qid):
        sm.questStartDialogue(qid, quest_npc())
    if sm.hasQuest(qid):
        satisfy_own_record_flags(qid)
        return True
    return False


def give(item, count=1):
    if sm.hasItem(item, count):
        return True
    if not sm.canHold(item):
        sm.chat("Make room in your inventory first, then talk to me again.")
        return False
    sm.giveItem(item, count - sm.getQuantityOfItem(item) if sm.hasItem(item) else count)
    return True


if sm.hasQuest(parentID) and give(4034162):
    sm.questEndDialogue(parentID, 0)
sm.dispose()
