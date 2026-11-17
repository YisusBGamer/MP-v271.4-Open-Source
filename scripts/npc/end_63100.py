# Horemheb (Land of Riches) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400313
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


if fresh(63100, 63031):
    sm.sendNext("You there! See that sack on the castle roof? Ten thousand gold coins. MY coins. Stuck up there.")
    me("How did it get up there?")
    sm.sendNext("I put it there so nobody could steal it. Now I can't reach it either. Ironic, isn't it?")
    start(63100)
    sm.setQRValue(63100, "state", "1")
    finish(63100)
    start(63101)
    sm.sendSayOkay("If you find a way to get it down, I'll make it worth your while. Probably.")
elif fresh(63104, 63103):
    sm.sendNext("A SPEAR! A spear came flying from nowhere and knocked my sack clean off the roof! Coins everywhere! "
                "The beggars are dancing in the streets!")
    sm.sendNext("...And you know what? I've never felt better. Here. It's the least valuable thing I own, and it's "
                "yours.")
    start(63104)
    finish(63104)
    A.giveKey(chr, A.KEYS[6])
    sm.sendSayOkay("Now if you'll excuse me, I'm going to go watch people be happy.")
else:
    sm.sendSayOkay("The richest man in the Land of Riches. That's me.")

sm.dispose()
