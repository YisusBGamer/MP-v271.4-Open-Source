# Reubenhood (Land of Warriors) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400212
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


if has(63030):
    sm.sendNext("Win? Beodog sent you to ask about winning, didn't he?")
    sm.sendNext("Nobody wins. Nobody loses. We fight, we rest, we fight. We were all warriors who died in battle, so "
                "this is what we know. It's... comfortable.")
    me("You don't sound very comfortable.")
    sm.sendNext("...If you want to leave, talk to #bEdgar Tetch#k. He's been looking for a way out for a long time.")
    finish(63030)
else:
    sm.sendSayOkay("Another day, another battle.")

sm.dispose()
