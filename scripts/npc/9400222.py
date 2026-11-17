# Billy (Land of Innocence) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400222
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


def collector():
    if not sm.hasItem(A.COLOR_COLLECTOR) and not sm.hasItem(A.FILLED_COLLECTOR):
        sm.giveItem(A.COLOR_COLLECTOR)
        A.resetColor(chr)


def deliver(q, text):
    if sm.hasItem(A.FILLED_COLLECTOR):
        sm.consumeItem(A.FILLED_COLLECTOR, 1)
        finish(q)
        sm.sendSayOkay(text)
        return True
    collector()
    sm.sendSayOkay("I'm gonna need more paint than this! (%d/%d Afterlands monsters)" % (A.colorFill(chr),
                                                                                    A.COLOR_GOAL))
    return False

if fresh(63055, 63054):
    sm.sendNext("I'm an ARTIST. But there's no paint in the Afterlands. Except... monsters are full of color!")
    start(63055)
    collector()
    sm.sendSayOkay("This #bColor Collector#k sucks the color right out of them. Fill it up and bring it back!")
elif has(63055):
    deliver(63055, "Ooh, look at all these colors! Let's see what I can make...")
elif fresh(63084, 63055):
    sm.sendNext("I painted a picture, but it's not enough. I want to paint something BIG. Fill it again!")
    start(63084)
    collector()
elif has(63084):
    deliver(63084, "More! I'm going to paint a rainbow!")
elif fresh(63085, 63084):
    sm.sendNext("A rainbow BRIDGE! From here all the way to the grey land. One more collector and I can finish it!")
    start(63085)
    collector()
elif has(63085):
    if deliver(63085, "There! A rainbow bridge! It's on the left side of town. See you on the other side!"):
        pass
else:
    sm.sendSayOkay("Everything looks better in color!")

sm.dispose()
