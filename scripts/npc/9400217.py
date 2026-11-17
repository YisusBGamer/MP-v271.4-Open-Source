# Mansa (Land of Riches, in the castle) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400217
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


if fresh(63041, 63040):
    sm.sendNext("Hungry... so hungry...")
    start(63041)
    if sm.hasItem(A.GOLD_PIECE) and sm.sendAskYesNo("You want information, I want a #t2436086#. I propose a trade. "
                                                     "(Give him the gold?)"):
        sm.consumeItem(A.GOLD_PIECE, 1)
        finish(63041)
        sm.sendSayOkay("Crunch. Mmm. The food? Gone, I ate it. Gold's better anyway. Makes you big. Look at me!")
elif has(63041):
    if sm.hasItem(A.GOLD_PIECE):
        sm.consumeItem(A.GOLD_PIECE, 1)
        finish(63041)
        sm.sendSayOkay("Crunch. Mmm. Gold's better than food anyway. Makes you big. Look at me!")
    else:
        sm.sendSayOkay("You want information, I want a #t2436086#. I propose a trade.")
elif fresh(63071, 63068) and has(63069):
    sm.sendNext("Ben wants me out? I'd love to go out. Look at me. The castle gates are far too small for me to pass "
                "through.")
    start(63071)
    sm.sendSayOkay("Unless... somebody knocked a hole in the wall. Somebody very, very big.\r\n#b(Eat 3 #t2436086# "
                   "within a minute, then talk to Mansa.)#k")
elif has(63071):
    if A.isEnormous(chr):
        me("(You're as big as Mansa now. You plant your feet and kick the castle's left wall. It crumbles.)")
        sm.setQRValue(63071, "destroy", "1")
        sm.setQRValue(63093, "1", False)
        finish(63071)
        sm.sendSayOkay("FRESH AIR! Meet me outside, friend!")
    else:
        sm.sendSayOkay("The castle gates are far too small for me to pass through.")
else:
    sm.sendSayOkay("Treasure tastes better than anything.")

sm.dispose()
