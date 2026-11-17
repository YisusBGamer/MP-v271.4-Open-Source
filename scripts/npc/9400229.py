# Broken Tree (Field of Melody) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400229
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


if fresh(63053) and A.isLightning(chr):
    me("(Lightning has split the old tree right down the middle. Inside the scorched wood, something glints: a "
       "wooden key!)")
    start(63053)
    sm.setQRValue(A.WORLD_QR, "lightning", "1")
    finish(63053)
    A.giveKey(chr, A.KEYS[1])
elif not done(63053):
    me("(An old tree. Its trunk is hollow, and there's something inside, but the wood is too hard to break. If only "
       "lightning would strike it...)")
else:
    me("(The tree split by lightning.)")

sm.dispose()
