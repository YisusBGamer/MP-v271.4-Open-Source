# Solar Pulley (Land of Warriors): day and night (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400232
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


night = A.isNight(chr)
if sm.sendAskYesNo("(A huge wheel with a rope that runs all the way up into the sky. It's %s now.)\r\n\r\nTurn the "
                   "Solar Pulley?" % ("night" if night else "day")):
    night = A.turnPulley(chr)
    me("(You heave on the rope... The sun %s.)" % ("sinks below the horizon. Night falls over the Afterlands" if night
                                                 else "climbs back into the sky. It's day again in the Afterlands"))
    if has(63028) and night:
        sm.setQRValue(63028, "end", "1")
    if has(63037) and not night:
        sm.setQRValue(63037, "end", "1")
    if has(63062) and not night:
        sm.setQRValue(63062, "End", "1")

sm.dispose()
