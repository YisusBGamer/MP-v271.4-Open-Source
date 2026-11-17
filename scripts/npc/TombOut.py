# Old Tombstone Maker (the field of passing souls) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400330
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


if has(63106) and not sm.hasItem(A.UNFINISHED_STONE):
    me("(A slab of good stone lies at the far end of the field, half-buried. The Strongman Soul helps you dig it "
       "out.)")
    if sm.canHold(A.UNFINISHED_STONE):
        sm.giveItem(A.UNFINISHED_STONE)
    else:
        sm.sendSayOkay("Make room in your Etc inventory.")
if sm.sendAskYesNo("Back to the workshop?"):
    sm.warp(A.TOMB_WORKSHOP)

sm.dispose()
