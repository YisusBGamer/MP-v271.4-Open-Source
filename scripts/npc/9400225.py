# Riley (Land of Innocence) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400225
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


if has(63052):
    if count(A.CHILD_TOY) >= 30:
        finish(63052)
        sm.sendSayOkay("My toys! *sniff* The rain's stopping. Thanks.")
    else:
        sm.sendSayOkay("You broke our toy! ...Okay, the monsters did. (#b30 Child's Toys#k)")
elif fresh(63054, 63052):
    sm.sendNext("Want to know a secret? When we cry, it rains. When we throw a tantrum, it thunders. It's how the "
                "Afterlands work.")
    sm.sendNext("Eat all the snacks from the #bSnack Hoard#k and we cry. Knock over the #bLudi-Block Fort#k and we "
                "throw a fit. And the weather goes everywhere, not just here!")
    start(63054)
    finish(63054)
    sm.sendSayOkay("Show and tell's over! Go play.")
elif fresh(63057, 63054):
    if A.isRaining(chr):
        sm.sendNext("Somebody ate our snacks... *sniff*")
        start(63057)
        sm.sendSayOkay("Where's my snacks!? (#b10 Frozen Treats#k)")
    else:
        sm.sendSayOkay("It's a nice day. Don't eat our snacks, okay?")
elif has(63057):
    if count(A.FROZEN_TREAT) >= 10:
        finish(63057)
        sm.sendSayOkay("Snacks! Okay. No more crying.")
    else:
        sm.sendSayOkay("Where's my snacks!?")
elif fresh(63058, 63054):
    if A.isLightning(chr):
        sm.sendNext("WHO KNOCKED OVER OUR FORT?!")
        start(63058)
        sm.sendSayOkay("You're mean! I HATE YOU! (#b10 Child's Toys#k)")
    else:
        sm.sendSayOkay("Our fort is the best fort in the whole Afterlands.")
elif has(63058):
    if count(A.CHILD_TOY) >= 10:
        finish(63058)
        sm.sendSayOkay("...Okay, I don't hate you any more.")
    else:
        sm.sendSayOkay("You're mean! I HATE YOU!")
else:
    sm.sendSayOkay("Wanna play?")

sm.dispose()
