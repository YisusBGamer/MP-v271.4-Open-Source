# NPC 2121017's Npc.wz script name is "HW_macha", so this file runs when it is clicked instead of 2121017.py.
# Keep the two the same.
# Broken Carriage (Abandoned Graveyard) (Halloween - the Haunted Mansion, see HalloweenEvent)
NPC = 2121017
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

if sm.hasQuest(15121) and not sm.hasItem(4034715):
    if not sm.canHold(4034715):
        sm.sendSayOkay("Make some room in your Etc inventory first.")
    else:
        sm.sendNext("#b(A carriage, rotted and overturned. Half-buried in the mud, I find an emblem with a deer on "
                    "it.)#k")
        sm.giveItem(4034715, 1)
        sm.sendSayOkay("#b(I should take this to Sophelia.)#k")
else:
    sm.sendSayOkay("#b(A broken, rotting carriage.)#k")
