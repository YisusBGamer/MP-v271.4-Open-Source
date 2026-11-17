# Mansa (Land of Riches, outside the castle) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400250
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


if fresh(63072, 63071):
    sm.sendNext("Ugh... I ate the greatest treasure of all, in there. Now my tummy hurts.")
    start(63072)
    sm.setQRValue(63072, "Start", "1")
    A.put(chr, "mansaNight", "1" if A.isNight(chr) else "0")
    sm.sendSayOkay("If only I could sleep it off... A whole day and night would do it. (#bSolar Pulley#k, Land of "
                   "Warriors)")
elif has(63072):
    then = A.get(chr, "mansaNight")
    now = "1" if A.isNight(chr) else "0"
    if then != now:
        me("(Mansa wakes up looking green... and coughs up a glittering pile of treasure.)")
        sm.sendSayOkay("Much better. Help yourself, I don't want it back.")
    else:
        sm.sendSayOkay("Still hurts... a whole day and a night...")
else:
    sm.sendSayOkay("Outside is nice. Grass doesn't taste like anything, though.")

sm.dispose()
