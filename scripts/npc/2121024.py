# Gloomy Mask (Mask Room) (Halloween - the Haunted Mansion, see HalloweenEvent)
NPC = 2121024
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

if ready(15136):
    sm.sendNext("#b(The Gloomy Mask hangs on the wall, staring. I raise the Evil Hammer...)#k")
    if finish(15136):
        sm.sendNext("#b(The mask shatters. A scream echoes through the whole mansion - and the next thing I know, "
                    "I'm standing outside the front door.)#k")
        sm.warp(229010000)
else:
    sm.sendSayOkay("#b(A gloomy mask. It feels like it's watching you.)#k")
