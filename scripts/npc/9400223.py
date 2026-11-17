# Billy (Land of Contemplation) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400223
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


if fresh(63077, 63085):
    sm.sendNext("The rainbow bridge works! And look at this place. It's ALL grey. It's the best canvas EVER.")
    start(63077)
    sm.sendSayOkay("Fill my #bColor Collector#k one more time and I'll paint the whole land!")
    if not sm.hasItem(A.COLOR_COLLECTOR) and not sm.hasItem(A.FILLED_COLLECTOR):
        sm.giveItem(A.COLOR_COLLECTOR)
        A.resetColor(chr)
elif has(63077):
    if sm.hasItem(A.FILLED_COLLECTOR):
        sm.consumeItem(A.FILLED_COLLECTOR, 1)
        me("(Billy splashes color across the Land of Contemplation. The souls look up, blinking, as their cheeks turn "
           "pink.)")
        finish(63077)
        sm.sendSayOkay("TA-DAAA! Go tell Adler!")
    else:
        if not sm.hasItem(A.COLOR_COLLECTOR):
            sm.giveItem(A.COLOR_COLLECTOR)
            A.resetColor(chr)
        sm.sendSayOkay("You need to gather more color. (%d/%d Afterlands monsters)" % (A.colorFill(chr),
                                                                                  A.COLOR_GOAL))
else:
    sm.sendSayOkay("Everything looks better in color!")

sm.dispose()
