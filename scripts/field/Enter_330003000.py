# Old Shinsoo School Building (330003000), FriendStory. Francis in Danger (33105): "It sounds like Francis is in danger
# somewhere in the old main hall of Shinsoo International School. Hurry up and go help him." Its end NPC (Lily, 1530050)
# stands nowhere: arriving here ends it.
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


if sm.hasQuest(33105):
    satisfy_own_record_flags(33105)
    if sm.questEndDialogue(33105, 1530050):
        sm.chatScript("Looks like Francis escaped safely. However, you should keep an eye on the old main hall of Shinsoo International School. Something's not right about that place.")
