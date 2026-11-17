# Ben (Land of Riches, in the castle) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400216
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


if fresh(63069, 63068):
    sm.sendNext("Joel left? Well, I'm not going anywhere. Not while #bMansa#k is in here with my treasure.")
    start(63069)
    sm.sendSayOkay("I won't leave my treasure alone with #p9400217#! He goes first, or I'll stay here forever!")
elif has(63069):
    if done(63071):
        sm.sendNext("Mansa's out? Through the WALL? ...Well, a deal's a deal.")
        finish(63069)
        start(63070)
        start(63063)
        sm.sendSayOkay("I'll see you outside. I hear roses grow out there. I've never smelled one.")
    else:
        sm.sendSayOkay("I won't leave my treasure alone with Mansa! He goes first, or I'll stay here forever!")
else:
    sm.sendSayOkay("Mine. All of it. Mine.")

sm.dispose()
