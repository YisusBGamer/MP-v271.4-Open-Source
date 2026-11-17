# Ashkaya (all lands): the guide, travel, the Huge Door (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400205
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

KEY_HINTS = [
    "Adler's White Key: Adler, Land of Contemplation (give its souls their color back)",
    "Lightning-Touched Wooden Key: a broken tree in the Field of Melody, after a thunderstorm",
    "God of Battle's Key: Vulcanelli, Land of Warriors (look... bigger)",
    "Golden Key: Mansa, Land of Riches (once he's out of the castle)",
    "Raven Black Key: the Nameless Soul, Land of Contemplation (at night)",
    "Crystalline Key: Tina's storybook, Land of Innocence (the Balrog's Key opens it)",
    "Cast Iron Key: Horemheb, Land of Riches (his treasure sack on the roof)",
    "Yellow Mage's Key: Beodog's Judgment Day, Land of Warriors",
    "Carved Stone Key: the Old Tombstone Maker (the tomb, Land of Riches)",
    "Molded Clay Key: the Old Tombstone Maker's delivery",
    "Heart-shaped Key: Lonnie on the swing, Land of Innocence",
    "Striped Candy Key: the Silent Boy, Land of Innocence",
]

LANDS = [(A.BEGINNINGS, "Land of Beginnings"), (A.WARRIORS, "Land of Warriors"), (A.RICHES, "Land of Riches"),
         (A.CONTEMPLATION, "Land of Contemplation"), (A.INNOCENCE, "Land of Innocence")]
sm.setSpeakerID(parentID)
A.showNpcs(chr)


def door():
    added = A.insertKeys(chr)
    n = A.keysInDoor(chr)
    if added:
        sm.sendNext("Click... click... %d of the twelve locks are open now." % n)
    if n >= 12:
        sm.sendNext("All twelve! Listen... the Huge Door is opening. Beyond it is the world you came from.")
        sm.sendNext("It's been fun having you around. Don't come back too soon, all right? Live a long, boring life.")
        if A.finish(chr, 63086):
            sm.giveItem(A.WEIRD_GIFT_BOX)
            start(63087)
            me("(Light floods through the door...)")
            sm.warp(A.HENESYS)
        return
    text = "The Huge Door has %d of its 12 keys. Still missing:#b" % n
    for i, k in enumerate(A.KEYS):
        if not A.hasKey(chr, k):
            text += "\r\n- " + KEY_HINTS[i]
    sm.sendSayOkay(text + "#k")

if fresh(63025, 63021):
    sm.sendNext("Oh! A new face. Welcome, welcome to the #bAfterlands#k! I'm #bAshkaya#k, your guide.")
    me("The Afterlands? Wait... the pig. The cliff. Am I... dead?")
    sm.sendNext("Dead-ish. Everyone here is a little bit dead. It's not so bad! The warriors fight, the rich stay rich, "
                "the thinkers think and the children play. Forever.")
    sm.sendNext("But you're not like them. You still smell of the world. See that #bHuge Door#k? It leads home. It has "
                "#btwelve locks#k, and the keys are scattered all over the four lands.")
    start(63025)
    finish(63025)
    start(63026)
    sm.spawnMob(A.SUSPICIOUS_BUSH, chr.getPosition().getX() + 200, chr.getPosition().getY(), False)
    sm.sendSayOkay("Some newcomers think they can just walk out through that #rsuspicious bush#k over there. Go on, "
                   "try it. Everyone does.")
elif has(63026):
    if ready(63026):
        sm.sendNext("It was just a bush, wasn't it? Told you. There's no shortcut home.")
        finish(63026)
        sm.sendSayOkay("Start with the #bLand of Warriors#k. Beodog is always looking for someone new to stab. I can "
                       "take you there any time.")
        sm.warp(A.WARRIORS)
    else:
        if sm.sendAskYesNo("The bush? It's still there, isn't it? ...Or shall I find you another one?"):
            sm.spawnMob(A.SUSPICIOUS_BUSH, chr.getPosition().getX() + 200, chr.getPosition().getY(), False)
elif fresh(63032, 63031) and sm.hasItem(A.BALROG_KEY):
    sm.sendNext("A key! Let's try it on the Huge Door.")
    me("(You try the Balrog's Key in every one of the twelve locks. It's far too small for any of them.)")
    sm.setQRValue(63032, "click", "12")
    start(63032)
    finish(63032)
    sm.sendSayOkay("Too small for the door... but a key that small must open something small. A box, maybe. Or a book. "
                   "Keep it.")
elif fresh(63062, 63061):
    sm.sendNext("The warriors are rested, the sun is down... Beodog wants to test the heart of a real warrior. Turn the "
                "#bSolar Pulley#k back to day and go see him.")
    start(63062)
    sm.setQRValue(63062, "Start", "1")
    sm.sendSayOkay("Good luck. Try not to get stabbed too much.")
else:
    text = "Where to?#b"
    if done(63026):
        for i, (field, name) in enumerate(LANDS):
            if field != sm.getFieldID():
                text += "\r\n#L%d#%s#l" % (i, name)
    text += "\r\n#L10#The Huge Door (%d/12 keys)#l" % A.keysInDoor(chr)
    if done(63087) or done(63086):
        text += "\r\n#L11#Back to Henesys#l"
    sel = sm.sendNext("Need a hand? " + text)
    if 0 <= sel < len(LANDS):
        sm.warp(LANDS[sel][0])
    elif sel == 10:
        door()
    elif sel == 11:
        sm.warp(A.HENESYS)

sm.dispose()
