# Old Tombstone Maker (the Afterlands, the workshop) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400320
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


if fresh(63106, 63054):
    sm.sendNext("Hm? A visitor in my workshop. Most souls don't need tombstones any more, you know. Already buried.")
    sm.sendNext("But some of them... some still hope someone will remember them. I carve for those. Only I've run out "
                "of good stone.")
    start(63106)
    if sm.sendAskYesNo("Hmm... I sure could use some high quality stone. The #bfield of passing souls#k has a slab or "
                       "two. Shall I send you there?"):
        sm.warp(A.TOMB_FIELD)
elif has(63106):
    if sm.hasItem(A.UNFINISHED_STONE):
        finish(63106)
        sm.sendSayOkay("Fine stone, fine stone. Now let me work.")
    elif sm.sendAskYesNo("Hmm... I sure could use some high quality stone. Back to the field of passing souls?"):
        sm.warp(A.TOMB_FIELD)
elif fresh(63107, 63106):
    sm.sendNext("There. A tombstone for a soul who's ready for the hereafter. Every stone needs a key to its grave, "
                "you know. I carved one too many.")
    start(63107)
    finish(63107)
    A.giveKey(chr, A.KEYS[8])
    sm.sendSayOkay("Take it. My apprentice in the back workshop could use a hand with a delivery, too.")
elif has(63108):
    sm.sendNext("My tombstone! Delivered in time, and not a scratch on it.")
    finish(63108)
    sm.sendNext("The apprentice made you something, I see. A key of clay. He's still learning.")
    A.giveKey(chr, A.KEYS[9])
    sm.sendSayOkay("Off you go. Try not to need one of these for a long, long time.")
else:
    if sm.sendAskYesNo("Tombstones for those who want to be remembered. ...Shall I show you the back workshop?"):
        sm.warp(867113723)

sm.dispose()
