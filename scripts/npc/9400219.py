# Nameless Soul (Land of Contemplation) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400219
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


def spawn():
    sm.spawnMob(A.HORRID_DEATHBEAST, chr.getPosition().getX() + 200, chr.getPosition().getY(), False)

if has(63073) and A.get(chr, "tr19") != "1":
    A.put(chr, "tr19", "1")
    sm.sendSayOkay("Green... like leaves. I had a garden, I think. Thank you.")
elif has(63050) and A.get(chr, "ct19") != "1":
    A.put(chr, "ct19", "1")
    sm.sendSayOkay("You came back to share more? ...Thank you. I think my name was... no. It doesn't matter now.")
elif fresh(63075, 63059):
    if A.isNight(chr):
        sm.sendNext("Do you hear it? Every night, that terrible cry...")
        sm.sendNext("It's no ordinary #o9402048#, it's a #o9402049#! It swallowed something shiny, long ago.")
        start(63075)
        spawn()
        sm.sendSayOkay("There it is! Be careful!")
    else:
        sm.sendSayOkay("It only comes out at night...")
elif has(63075):
    if A.hasKey(chr, A.KEYS[4]):
        finish(63075)
        start(63076)
        finish(63076)
        sm.sendSayOkay("It's gone... and it left something behind. A black key, as dark as its feathers.")
    elif A.isNight(chr):
        if sm.sendAskYesNo("It's no ordinary #o9402048#, it's a #o9402049#! ...It flew off? Wait, I hear it again. "
                           "Shall I call it?"):
            spawn()
    else:
        sm.sendSayOkay("It only comes out at night...")
else:
    sm.sendSayOkay("Who was I...?")

sm.dispose()
