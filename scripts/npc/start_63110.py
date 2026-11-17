# Lonnie (Land of Innocence) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400322
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


if fresh(63110, 63054):
    sm.sendNext("I want to swing really high. But I'm scared of heights. Really, really scared.")
    start(63110)
    sm.setQRValue(63110, "1", "state")
    sm.sendSayOkay("If only there were a way to stop being scared... #b#p9400226##k sells all kinds of things.")
elif has(63110):
    if sm.hasItem(A.MAGIC_EYEPATCH):
        sm.consumeItem(A.MAGIC_EYEPATCH, 1)
        sm.sendNext("A #bMagic Eyepatch#k? ...I can't see how high I am! I'm not scared at all! Push me! PUSH ME!")
        me("(You push Lonnie on the swing, higher and higher. She laughs the whole time.)")
        finish(63110)
        A.giveKey(chr, A.KEYS[10])
        sm.sendSayOkay("That was the BEST. Here, my heart-shaped key. I want you to have it!")
    else:
        sm.sendSayOkay("Did you find a way to conquer my fear of heights?")
else:
    sm.sendSayOkay("Wheee!")

sm.dispose()
