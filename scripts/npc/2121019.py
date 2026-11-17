# Piled Documents (Mask Room) (Halloween - the Haunted Mansion, see HalloweenEvent)
NPC = 2121019
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

if sm.hasQuest(15129):
    sm.sendNext("#b(An order book from a toy workshop. One page is marked:)#k\r\n\r\n\"One mask, black lacquer, "
                "for young Master #eJohn Blatchford#n. To be delivered by hand on Halloween night. - #eJonas#n\"")
    if finish(15129):
        sm.sendSayOkay("#b(Jonas made the mask...? I need to talk to him.)#k")
else:
    sm.sendSayOkay("#b(Piles of old papers.)#k")
