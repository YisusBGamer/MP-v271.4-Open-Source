# Old Man (Henesys) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400200
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


if chr.getLevel() < A.MIN_LEVEL:
    sm.sendSayOkay("Oh, you're too young to be running errands for an old man like me. Come back when you're a little "
                   "stronger. (Lv. 75)")
elif fresh(63020):
    sm.sendNext("Help! Somebody, anybody! Oh, thank goodness, an adventurer!")
    sm.sendNext("I'm having the worst day of my life. My puppy ran off, my wife's medicine is gone, my kite is stuck "
                "on top of the mountain, and a pig in a ribbon keeps stealing my lunch!")
    if sm.sendAskAccept("Would you help a poor old man? Just one thing at a time, I promise."):
        start(63020)
        finish(63020)
        start(63024)
        sm.sendSayOkay("Bless you! First, my #bpuppy#k. He ran toward the edge of town. Talk to me when you're ready "
                       "to look for him.")
elif has(63024):
    me("(You search every alley in Henesys... and find the puppy dozing under a cart, right next to a very busy "
       "road. You scoop him up just as a wagon thunders past.)")
    sm.setQRValue(63024, "check", "1")
    sm.sendNext("My puppy! That wagon nearly flattened you both. You're braver than you look.")
    finish(63024)
    start(63023)
    sm.sendSayOkay("Now, my wife's #bmedicine#k. I dropped the last bottle somewhere on the hill. Talk to me when "
                   "you're ready.")
elif has(63023):
    me("(The bottle rolled to the very edge of a ledge. You stretch... further... and grab it as the rock gives way "
       "under your feet. You barely scramble back up.)")
    sm.setQRValue(63023, "check", "1")
    sm.sendNext("You found it! And you're still in one piece, mostly. My wife will be so relieved.")
    finish(63023)
    start(63022)
    sm.sendSayOkay("Just two more. My #bkite#k is stuck on the mountaintop. It's a long climb...")
elif has(63022):
    me("(You climb the mountain behind Henesys, untangle the kite from a dead branch, and slip on the way down. A "
       "very, very long way down. Somehow, you land in a haystack.)")
    sm.setQRValue(63022, "check", "1")
    sm.sendNext("My kite! Did you fall? You look awfully pale. Well, you're alive, aren't you? Ha ha!")
    finish(63022)
    start(63021)
    sm.spawnMob(A.RIBBON_PIG, chr.getPosition().getX() + 150, chr.getPosition().getY(), False)
    sm.sendSayOkay("And last... THERE! That's the #rRibbon Pig#k! Catch it before it gets away!")
elif has(63021):
    if not ready(63021):
        if sm.sendAskYesNo("Please catch that terrible Ribbon Pig! ...You lost it? It's always around here somewhere. "
                           "Shall I scare it out for you?"):
            sm.spawnMob(A.RIBBON_PIG, chr.getPosition().getX() + 150, chr.getPosition().getY(), False)
    else:
        sm.sendNext("You caught it! Oh, well done, well done! Now, let me just take a look at that-")
        me("(The Ribbon Pig squeals, wriggles out of your arms and charges straight at you. You stumble backward, "
           "trip over the puppy, and the world tips sideways...)")
        me("(...)")
        finish(63021)
        sm.warp(A.BEGINNINGS)
else:
    sm.sendSayOkay("You again? You look... different, somehow. Lighter. Never mind me, I'm just an old man.")

sm.dispose()
