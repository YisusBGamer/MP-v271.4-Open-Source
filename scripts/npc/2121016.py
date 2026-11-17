# Faceless Portrait (the strange attraction in the Frame Room) (Halloween - the Haunted Mansion, see HalloweenEvent)
NPC = 2121016
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

if sm.hasQuest(15116):
    sm.sendNext("#b(A portrait of a young gentleman. His face has been torn out of the canvas.)#k")
    if finish(15116):
        sm.warp(229000210)
elif sm.hasQuest(15124):
    sm.sendNext("#b(I brush the dust off the frame. The nameplate is scratched, but still readable:)#k\r\n\r\n"
                "#eJohn Blatchford#n")
    sm.setQRValue(15138, "search", "1")
    sm.sendNext("#b(I should tell Sophelia.)#k")
    sm.warp(229000100)
else:
    sm.sendSayOkay("#b(A portrait with its face torn out.)#k")
