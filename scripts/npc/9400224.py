# Tina (Land of Innocence) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400224
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


if fresh(63051, 63049):
    sm.sendNext("Waaaah!")
    me("What's wrong?")
    sm.sendNext("The #rPapercraft Horrors#k took all our frozen treats! It's the Land of Innocence, we're supposed to "
                "have treats ALL THE TIME! Waaaah!")
    start(63051)
    sm.sendSayOkay("(#bCollect 30 Frozen Treats from the Papercraft Horrors to convince the children not to cry.)#k")
elif has(63051):
    if count(A.FROZEN_TREAT) >= 30:
        finish(63051)
        sm.sendSayOkay("Treats! ...Okay, I'm not crying any more. You can stay.")
    else:
        sm.sendSayOkay("Waaaah! (#b30 Frozen Treats#k)")
elif fresh(63052, 63051):
    sm.sendNext("Now Riley's crying because the #rPapercraft Menaces#k broke our toys. And when Riley cries, it rains "
                "for DAYS.")
    start(63052)
    sm.sendSayOkay("(#bBring the kids 30 Child's Toys to cheer them back up.)#k Riley's upstairs.")
elif fresh(63080, 63059):
    if A.isNight(chr) and A.isLightning(chr):
        sm.sendNext("Wahhh, I'm scared! It's dark and it's thundering and the papercraft monsters are moving!")
        start(63080)
        sm.sendSayOkay("Make the bad monstees go away! #r50 Papercraft Menaces#k and #r50 Papercraft Horrors#k!")
    else:
        sm.sendSayOkay("I'm not scared of anything! ...Except thunderstorms at night.")
elif has(63080):
    if ready(63080):
        finish(63080)
        sm.sendSayOkay("You're the bravest grown-up EVER.")
    else:
        sm.sendSayOkay("Wahhh, I'm scared! Make the bad monstees go away!")
elif fresh(63081, 63080):
    sm.sendNext("I lost my storybook. It's my FAVORITE. The papercraft monsters probably took it to make more "
                "papercraft monsters.")
    start(63081)
    sm.setQRValue(63098, "turn", "2")
    sm.sendSayOkay("Please find #bTina's Lost Storybook#k!")
elif has(63081):
    if sm.hasItem(A.LOST_STORYBOOK):
        finish(63081)
        sm.sendSayOkay("My book! ...It's all faded. Oh well. I still love it.")
    else:
        sm.sendSayOkay("Please find my storybook! The papercraft monsters have it.")
elif fresh(63082, 63081):
    sm.sendNext("My teddy is sick. I'm the doctor, so I know. He needs a NEW teddy to keep him company.")
    start(63082)
    sm.sendSayOkay("#t4036015#! I want the #t4036015# #p9400226# is selling~!")
elif has(63082):
    if sm.hasItem(A.TEDDY_BEAR):
        finish(63082)
        sm.sendSayOkay("Doctor Tina says: all better!")
    else:
        sm.sendSayOkay("#t4036015#! I want the #t4036015# #p9400226# is selling~!")
elif fresh(63083, 63082):
    sm.sendNext("Oh no. Dr. Buttbreath... that's my old teddy... he's... he's gone to the Afterlands.")
    me("We're in the Afterlands.")
    sm.sendNext("Then he's right here! Hooray!")
    start(63083)
    finish(63083)
    if not sm.hasItem(A.TINA_STORYBOOK) and not A.hasKey(chr, A.KEYS[5]):
        sm.giveItem(A.TINA_STORYBOOK)
    sm.sendSayOkay("You can have my storybook. It's locked, though. I lost the tiny key ages ago.")
else:
    sm.sendSayOkay("In the Land of Innocence, kids rule!")

sm.dispose()
