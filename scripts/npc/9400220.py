# Unfulfilled Soul (Land of Contemplation) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400220
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


def explored():
    return all(sm.getQRValue(63045, k) == "1" for k in ("chk1", "chk2", "chk3"))

if fresh(63045, 63041):
    sm.sendNext("...You're colorful. Everything in the #bLand of Contemplation#k used to be colorful, too.")
    sm.sendNext("Then the color drained away, a little more every day. Now we just sit and think about what we've "
                "lost.")
    start(63045)
    sm.sendSayOkay("Would you look around the fields for me? The #bField of Glittering White#k, the #bField of "
                   "Glimmering Trees#k and the #bSparkling Field#k. Tell me what you see.")
elif has(63045):
    if explored():
        sm.sendNext("Ravens? Ravens everywhere, pecking at the color... So that's where it goes.")
        finish(63045)
        start(63046)
        sm.sendSayOkay("Please... defeat #r50 Ravens#k. Maybe some of the color will come back.")
    else:
        sm.sendSayOkay("Explore all three fields past the gate on the right, please.")
elif has(63046):
    if ready(63046):
        finish(63046)
        start(63047)
        sm.sendNext("A little color came back. Just a little.")
        sm.sendNext("The Ravens are only thieves. The color has to go back where it came from. #bAdler#k would know "
                    "how; he's the wisest of us.")
        finish(63047)
    else:
        sm.sendSayOkay("Defeat #r50 Ravens#k in the fields, please.")
elif has(63073) and A.get(chr, "tr20") != "1":
    A.put(chr, "tr20", "1")
    sm.sendSayOkay("Red. My favorite color was red. How could I forget? Thank you.")
elif fresh(63050, 63052, 63073):
    sm.sendNext("You're carrying so much color now. Would you share some? With the other two souls, too.")
    start(63050)
    sm.sendSayOkay("Share your colors with the #bOblivious Soul#k and the #bNameless Soul#k, then come back.")
elif has(63050):
    if A.get(chr, "ct18") == "1" and A.get(chr, "ct19") == "1":
        sm.sendNext("Red... blue... green... I remember now. I remember everything.")
        finish(63050)
        SeasonalEvents.giveCoins(chr, 3, "a colorful trade")
        sm.sendSayOkay("Thank you. I'm... fulfilled.")
    else:
        sm.sendSayOkay("There are still people to share your colors with.")
else:
    sm.sendSayOkay("...")

sm.dispose()
