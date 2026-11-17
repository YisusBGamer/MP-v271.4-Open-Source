# NPC 2121018's Npc.wz script name is "HW_hair", so this file runs when it is clicked instead of 2121018.py.
# Keep the two the same.
# Trashed Hair (Central Hall) (Halloween - the Haunted Mansion, see HalloweenEvent)
NPC = 2121018
from net.swordie.ms.world import HalloweenEvent

ACTIVE = HalloweenEvent.isActive()


def me(text):
    # the player's own lines
    sm.setPlayerAsSpeaker()
    sm.sendNext(text)
    sm.setSpeakerID(NPC)


def can_start(q, prev):
    return ACTIVE and chr.getLevel() >= HalloweenEvent.MIN_LEVEL and sm.hasQuestCompleted(prev) \
        and not sm.hasQuest(q) and not sm.hasQuestCompleted(q)


def ready(q):
    return sm.hasQuest(q) and sm.isComplete(q)


def finish(q):
    # completes the quest with its rewards (EXP, tickets, coins); starts the next "notifier" quest
    return HalloweenEvent.finish(chr, q)


def follow(q, field, text):
    # a "strange attraction" quest that just started: offer to follow it now (Spin Man can take you later)
    if sm.hasQuest(q) and sm.sendAskYesNo(text):
        sm.warp(field)

if sm.hasQuest(15128) and not sm.hasItem(4034717):
    if not sm.canHold(4034717):
        sm.sendSayOkay("Make some room in your Etc inventory first.")
    else:
        sm.giveItem(4034717, 1)
        sm.sendSayOkay("#b(Ew. A clump of trashed hair. Joey had better appreciate this.)#k")
else:
    sm.dispose()
