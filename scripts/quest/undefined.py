# Fallback for quests without a script of their own (thousands of quests in Quest.wz name a start/end script that
# was never written).
#
# Start: starts the quest. End: hands it in (with its rewards) once its requirements are met.
# Quests often wait for a flag on their own record that the missing script would have set ("Talk to Chief Legato",
# "Check the walkie-talkie", ...): those flags are set here, so the quest can be handed in at its NPC.
# The quest's official dialogue (Quest.wz/Say.img, from dat/questSay.txt) is played: the client only shows it by itself
# for quests without a script. Other missing scenes (cutscenes, effects) are skipped. Flags on other quests' records are
# never touched. A quest that can't be handed in yet gets its official stop/<reason> line (item, mob, npc, quest,
# info, default). PLAY_DIALOGUE = False turns the dialogue off.
# Story quests that have no NPC to hand them in to (their missing script played a scene and finished them, e.g. most
# of Mushroom Shrine Tales and the FriendStory) are finished as soon as they start, if nothing else is required.
from net.swordie.ms.loaders import QuestData
from net.swordie.ms.client.character.quest.requirement import QuestRecordRequirement
from net.swordie.ms.client.character.quest.requirement import QuestStartCompletionRequirement


# Zero's story (40050-40974) keeps its progress flags on separate records; the missing scripts set them, so they're
# set here too - except the ones a fight or an item sets (see zgen/gen_story.py).
ZERO_FLAGS_SET_ELSEWHERE = (40068, 40212, 40213, 40614, 40615, 40616, 40712, 40713, 40714, 40715)


# Story lines whose scene flags live on a shared record the missing scenes set (FriendStory's 33040 "no"; Mushroom
# Shrine Tales' 58736 / 58818; the Grand Athenaeum episodes' 32626-32650). Only quests of that story line set them.
STORY_FLAG_RECORDS = (
    (32700, 33100, (33040,)),
    (58720, 58821, (58736, 58818)),
    (32600, 32700, (32626, 32627, 32628, 32650)),  # Grand Athenaeum episodes' check flags
    (34400, 34440, (34436, 34437)),  # Kerning Tower 'go to ...' flags
)


def own_record(qid, record):
    if record == qid:
        return True
    for low, high, records in STORY_FLAG_RECORDS:
        if low <= qid <= high and record in records:
            return True
    return 40050 <= qid <= 40974 and record not in ZERO_FLAGS_SET_ELSEWHERE


def satisfy_own_record_flags(qid):
    if qid in (3463, 3875, 3881, 31063):
        return  # These flags require entering and attacking in an actual Star Force region.
    info = QuestData.getQuestInfoById(qid)
    if info is None:
        return
    for req in info.getQuestCompleteRequirements():
        if not isinstance(req, QuestRecordRequirement) or not own_record(qid, req.getQuestID()) \
                or req.hasRequirements(chr):
            continue
        record = req.getQuestID()
        for entry in req.getEntries():
            # the same calls the written quest scripts use for these flags
            if entry.getExVariable():
                sm.setQRValue(record, entry.getExVariable(), entry.getValue())
            else:
                sm.setQRValue(record, entry.getValue())
            if not req.isAndOption():
                break  # one matching entry is enough


def satisfy_next_quest_flags(qid):
    # The next quests of the story (ids just after this one that need it done) often also need a flag that this
    # quest's missing scene set (Heroes' 33952 "result", Rien's 23131, ...): nothing else sets those, so a player
    # finishing this quest here would find the next one never offered. Set the ones on records of the same story
    # (a record id within 200 of the next quest).
    for nid in range(qid + 1, qid + 21):
        info = QuestData.getQuestInfoById(nid)
        if info is None:
            continue
        reqs = info.getQuestStartRequirements()
        if not any(isinstance(r, QuestStartCompletionRequirement) and r.getQuestID() == qid for r in reqs):
            continue
        for req in reqs:
            if not isinstance(req, QuestRecordRequirement):
                continue
            record = req.getQuestID()
            if abs(record - nid) > 200 or record == nid or req.hasRequirements(chr):
                continue
            # setting a record makes it a started quest: only plain flag records, never a quest a player takes
            rinfo = QuestData.getQuestInfoById(record)
            if rinfo is not None and rinfo.getStartNpc() > 0 and not sm.hasQuest(record):
                continue
            for entry in req.getEntries():
                if entry.getExVariable():
                    sm.setQRValue(record, entry.getExVariable(), entry.getValue())
                else:
                    sm.setQRValue(record, entry.getValue())
                if not req.isAndOption():
                    break


PLAY_DIALOGUE = True

# finished by their own scripted content (Black Heaven acts: Neinheart; Genesis Weapon tracker: the last trace)
FINISHED_ELSEWHERE = (33120, 33121, 33122, 33123, 33124, 33125, 35650)


def finishes_on_start(qid):
    if qid in FINISHED_ELSEWHERE:
        return False
    info = QuestData.getQuestInfoById(qid)
    if info is None or info.getEndNpc() != 0:
        return False  # handed in at an NPC as usual
    qm = chr.getQuestManager()
    quest = qm.getQuestById(qid)
    return quest is not None and quest.isComplete(chr) and qm.meetsCompleteRequirements(qid)


def quest_npc():
    try:
        return questNpc  # the NPC the quest was started/handed in at (QuestHandler)
    except NameError:
        return 0  # the quest's own start/end NPC


if startQuest:
    if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
        if PLAY_DIALOGUE:
            sm.questStartDialogue(parentID, quest_npc())  # Say.img dialogue, accept, start
        else:
            sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        satisfy_own_record_flags(parentID)
        if finishes_on_start(parentID):
            sm.completeQuestNoCheck(parentID)
elif not resignQuest and sm.hasQuest(parentID):
    satisfy_own_record_flags(parentID)
    if PLAY_DIALOGUE:
        done = sm.questEndDialogue(parentID, quest_npc())  # Say.img dialogue, complete
    else:
        done = sm.completeQuest(parentID)
    if not done and not (PLAY_DIALOGUE and sm.questStopDialogue(parentID, 1, quest_npc())):
        sm.chat("You haven't met all of this quest's requirements yet.")  # no official stop line
    else:
        satisfy_next_quest_flags(parentID)
sm.dispose()
