# Oblivious Soul (Land of Contemplation) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400218
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


if has(63073) and A.get(chr, "tr18") != "1":
    A.put(chr, "tr18", "1")
    sm.sendSayOkay("Oh... that's what blue looks like. I'd forgotten. Thank you.")
elif has(63050) and A.get(chr, "ct18") != "1":
    A.put(chr, "ct18", "1")
    sm.sendSayOkay("More color? For me? ...It's warm. Like sunlight.")
elif fresh(63079, 63075):
    sm.sendNext("I can't think with all that cawing and creaking. The Ravens, and the #rRampikes#k... always so loud.")
    start(63079)
    sm.sendSayOkay("Defeat #r50 Ravens#k and #r50 Rampikes#k, and give me some peace of mind.")
elif has(63079):
    if ready(63079):
        finish(63079)
        sm.sendSayOkay("Quiet. Lovely, lovely quiet.")
    else:
        sm.sendSayOkay("#r50 Ravens#k and #r50 Rampikes#k, please.")
else:
    sm.sendSayOkay("What was I thinking about...?")

sm.dispose()
