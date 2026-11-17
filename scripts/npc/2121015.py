# Old Brooch (the strange attraction in the Attic) (Halloween - the Haunted Mansion, see HalloweenEvent)
NPC = 2121015
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

if sm.hasQuest(15114):
    sm.sendNext("#b(A purple brooch engraved with a deer. On the back, in tiny letters:)#k\r\n\r\n\"Bound by "
                "contract until the last guest departs.\"")
    if finish(15114):
        sm.warp(229000210)
else:
    sm.sendSayOkay("#b(A sparkling purple brooch with a deer engraving.)#k")
