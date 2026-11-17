# Adler (Land of Contemplation) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400221
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


if fresh(63048, 63047):
    sm.sendNext("The Ravens took the color, yes. But they only took what we let go of. We forgot ourselves first.")
    me("Can it be brought back?")
    sm.sendNext("By someone who remembers color very well. A child, perhaps. Children never forget a color.")
    start(63048)
    finish(63048)
elif fresh(63049, 63048):
    sm.sendNext("You want to go home, don't you? Then let me tell you a secret: the keys to the Huge Door are spread "
                "over all four lands. One for every lesson the Afterlands has to teach.")
    sm.sendNext("I have one of them. When this land has its color back, it's yours.")
    start(63049)
    finish(63049)
    sm.sendSayOkay("Visit the #bLand of Innocence#k. Ashkaya can take you. The children there may help us.")
elif fresh(63073, 63077):
    sm.sendNext("Look! The souls are blushing! Billy's paint...")
    start(63073)
    sm.setQRValue(63098, "turn", "1")
    sm.sendSayOkay("Share your colors with everyone. Talk to the three souls, then come back to me.")
elif has(63073):
    if A.get(chr, "tr18") == "1" and A.get(chr, "tr19") == "1" and A.get(chr, "tr20") == "1":
        finish(63073)
        sm.sendNext("A blank slate, painted over. Tabula rasa. Here, as I promised.")
        A.giveKey(chr, A.KEYS[0])
        sm.sendSayOkay("Adler's White Key. Use it well.")
    else:
        sm.sendSayOkay("Share your colors with everyone: the #bOblivious Soul#k, the #bNameless Soul#k and the "
                       "#bUnfulfilled Soul#k.")
elif fresh(63078, 63085):
    sm.sendNext("A land of color should have trees, don't you think? The #rRampikes#k drag away every olive tree they "
                "find.")
    start(63078)
    sm.sendSayOkay("Bring me #b3 Bleached Olive Trees#k from the Rampikes. I'll plant them along the path.")
elif has(63078):
    if count(A.OLIVE_TREE) >= 3:
        sm.consumeItem(A.OLIVE_TREE, 3)
        sm.setQRValue(63078, "comCount", "3")
        finish(63078)
        SeasonalEvents.giveCoins(chr, 3, "Adler's olive trees")
        sm.sendSayOkay("There. Three trees, and Billy will paint them green. Beautiful.")
    else:
        sm.sendSayOkay("#b3 Bleached Olive Trees#k from the Rampikes, please.")
else:
    sm.sendSayOkay("Think, and remember.")

sm.dispose()
