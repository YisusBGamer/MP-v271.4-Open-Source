# Vulcanelli (Land of Warriors) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400211
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


if has(63029):
    if A.isNight(chr):
        sm.sendNext("A NEW FACE! At MY party! Here, dance! DANCE!")
        me("(Vulcanelli spins you around until the stars blur.)")
        finish(63029)
        sm.sendSayOkay("Best party ever. Every night is the best party ever!")
    else:
        sm.sendSayOkay("Party? In the DAYTIME? Make it night first, friend.")
elif fresh(63065, 63059):
    sm.sendNext("#h0#, have you heard the stories of the #eGod of Battle#n, by any chance?")
    sm.sendNext("They are said to be bigger than ordinary humans. And according to the legends, one of their kicks has "
                "the power to shake the entire world and bring all the warriors to their knees!")
    start(63065)
    sm.sendSayOkay("I really want to meet that god.\r\n\r\n#b(Mansa from the Land of Riches became enormous by "
                   "eating treasure. Perhaps if you did the same, you could convince someone you were the God of "
                   "Battle?)#k")
elif has(63065):
    if A.isEnormous(chr):
        sm.sendNext("Th-th-that size... that MIGHT... You're... YOU'RE THE GOD OF BATTLE!!")
        sm.sendNext("I've waited my whole afterlife for this! Please, take this. It was meant for you!")
        finish(63065)
        A.giveKey(chr, A.KEYS[2])
        sm.sendSayOkay("I'll tell everyone I met a god!")
    else:
        sm.sendSayOkay("I really want to meet the God of Battle.\r\n\r\n#b(Eat 3 #t2436086# within a minute to "
                       "grow as big as a god. Joel in the Land of Riches has them.)#k")
else:
    sm.sendSayOkay("Is it night yet? No? Then FIGHT!")

sm.dispose()
