# Louie the Average (Land of Riches) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400314
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


if has(63101):
    sm.sendNext("Horemheb and his sack... If that thing ever fell, the coins would rain on all of us average folk.")
    sm.sendNext("But nobody can reach it. You'd need something that flies. Or something that's thrown, very far, "
                "very hard.")
    finish(63101)
    sm.sendSayOkay("There's a statue of a warrior in the Land of Warriors, always aiming his spear at something. "
                   "Probably nothing. Probably.")
else:
    sm.sendSayOkay("Just an average guy. In the Land of Riches. It's rough.")

sm.dispose()
