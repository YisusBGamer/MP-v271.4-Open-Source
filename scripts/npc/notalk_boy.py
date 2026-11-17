# Silent Boy (Land of Innocence) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400310
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


if done(63109) or not done(63054):
    sm.sendSayOkay("...")
else:
    n = int(A.get(chr, "silent") or "0") + 1
    A.put(chr, "silent", str(n))
    lines = ["...", "......", "...What.", "...You're still here.", "...Fine. You win."]
    if n < 5:
        sm.sendSayOkay(lines[n - 1])
    else:
        sm.sendNext("Nobody ever talks to me twice. You talked to me five times. That's... the price of silence, I "
                    "guess.")
        start(63109)
        sm.setQRValue(63109, "9", "key")
        finish(63109)
        A.giveKey(chr, A.KEYS[11])
        sm.sendSayOkay("Here. A candy key. Don't eat it.")

sm.dispose()
