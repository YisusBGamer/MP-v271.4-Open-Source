# Arsan Lupen (Land of Warriors) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400213
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


if fresh(63037, 63031, 63028):
    if A.isNight(chr):
        sm.sendNext("I can't sleep. I never can. It's too quiet at night. In battle you always know where you stand.")
        start(63037)
        sm.sendSayOkay("Do me a favor and turn the #bSolar Pulley#k back to day? I'd rather be fighting.")
    else:
        sm.sendSayOkay("Day again. Good. I hate the nights.")
elif has(63037):
    if not A.isNight(chr):
        sm.setQRValue(63037, "end", "1")
        finish(63037)
        sm.sendSayOkay("Ahh, daylight. Thank you. I'm a terrible sleeper.")
    else:
        sm.sendSayOkay("Change night into day using the #bSolar Pulley#k, please.")
elif fresh(63061, 63060):
    sm.sendNext("You guarded the fields so we could sleep. I haven't slept like that in centuries.")
    start(63061)
    sm.setQRValue(63061, "start", "1")
    sm.sendSayOkay("Let them rest a little longer. Keep it night for now... and when you're ready, talk to "
                   "#bAshkaya#k. She has something in mind for you.")
elif has(63061):
    if A.isNight(chr):
        sm.setQRValue(A.WORLD_QR, "DnN", "1")
        finish(63061)
        sm.sendSayOkay("Thank you, warrior. Go see Ashkaya.")
    else:
        sm.sendSayOkay("Make it night with the #bSolar Pulley#k so the warriors can rest.")
else:
    sm.sendSayOkay("Fight, rest, fight.")

sm.dispose()
