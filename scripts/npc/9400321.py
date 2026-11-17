# Old Tombstone Maker (the Afterlands, the back workshop) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400321
sm.setSpeakerID(NPC)


def me(text):
    # the player's own lines
    sm.setPlayerAsSpeaker()
    sm.sendNext(text)
    sm.setSpeakerID(NPC)


def done(q):
    return sm.hasQuestCompleted(q)


def has(q):
    return sm.hasQuest(q)


def fresh(q, *prev):
    # not started or done yet, and the quests before it are done
    if sm.hasQuest(q) or sm.hasQuestCompleted(q):
        return False
    for p in prev:
        if not sm.hasQuestCompleted(p):
            return False
    return True


def start(q):
    sm.startQuestNoCheck(q)


def finish(q):
    return A.finish(chr, q)


def ready(q):
    # a hunt or collect quest whose Quest.wz requirements are met
    return sm.hasQuest(q) and sm.isComplete(q)


def count(item):
    return sm.getQuantityOfItem(item)


if fresh(63108, 63107):
    sm.sendNext("A delivery! This stone has to reach the old man in the front workshop in #b10 minutes#k, before the "
                "soul it's for passes on.")
    start(63108)
    sm.sendSayOkay("Hurry! Out the door and to the front workshop.")
elif has(63108):
    if sm.sendAskYesNo("The front workshop is waiting! Go there now?"):
        sm.warp(A.TOMB_WORKSHOP)
else:
    if sm.sendAskYesNo("Back to the front workshop?"):
        sm.warp(A.TOMB_WORKSHOP)

sm.dispose()
