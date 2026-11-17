# The Ready Spear (Land of Warriors, a statue) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400316
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


if fresh(63102, 63101):
    me("(A stone warrior with his spear raised, aimed at something far away... toward the Land of Riches.)")
    start(63102)
    sm.setQRValue(63102, "state", "1")
    sm.sendSayOkay("(Maybe one of the warriors knows about this statue. #bEdgar Tetch#k seems the curious type.)")
elif fresh(63103, 63102):
    if A.isRaining(chr):
        me("(Rain pours down the statue. The stone softens... and with a great crack, the Ready Spear hurls his spear "
           "across the sky, toward the castle roof in the Land of Riches!)")
        start(63103)
        finish(63103)
        sm.sendSayOkay("(Something heavy just fell in the Land of Riches.)")
    else:
        me("(The statue waits for a worthy target. The ground is too hard today... maybe after a good rain.)")
else:
    me("(A stone warrior with his arm raised. His hand is empty now.)")

sm.dispose()
