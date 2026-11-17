# Chair (Land of Contemplation) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400335
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


if fresh(63105, 63054):
    me("(You sit in the chair by the bench and close your eyes. You dream of home: a warm bed, a window, the smell "
       "of breakfast.)")
    start(63105)
    finish(63105)
    sm.giveItem(2000005, 10)
    sm.sendSayOkay("(You wake up feeling rested. Someone left #b10 Power Elixirs#k on the bench.)")
else:
    me("(A comfortable-looking chair. A good place to think.)")

sm.dispose()
