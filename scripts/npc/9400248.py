# Joel (Land of Riches, outside the castle) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400248
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


import time


def favor(start_q, basket_q, hunt_q):
    # A Tasteful Favor: once a day, 20 Food Baskets and 50 Treasure Thief Moustrosities, for 3 Delicious Gold Pieces
    today = sm.getResetDay("yyyy-MM-dd")
    if A.get(chr, "favor") == today and not has(start_q):
        sm.sendSayOkay("Thank you for today. Come back tomorrow, I'll be hungry again.")
        return
    if not has(start_q):
        if sm.sendAskAccept("Would you do me a tasteful favor? Bring me #b20 Food Baskets#k and get rid of #r50 "
                            "Treasure Thief Moustrosities#k. I'll pay you in gold. The edible kind."):
            for q in (start_q, basket_q, hunt_q):
                start(q)
        return
    if count(A.FOOD_BASKET) >= 20 and ready(hunt_q):
        sm.consumeItem(A.FOOD_BASKET, 20)
        for q in (basket_q, hunt_q, start_q):
            finish(q)
        A.put(chr, "favor", today)
        sm.giveItem(A.GOLD_PIECE, 3)
        sm.sendSayOkay("Delicious! Here, #b3 #t2436086##k. Eat them quickly, the effect doesn't last.")
    else:
        sm.sendSayOkay("One, two, three... Wait a second... Something is missing! (#b20 Food Baskets#k, #r50 Treasure "
                       "Thief Moustrosities#k)")

if has(63068):
    sm.sendNext("The sky! There's a sky! And grass! And not a single coin on it!")
    finish(63068)
    sm.sendSayOkay("Thank you. I think I'll stay out here for a while. Ben is still inside, though...")
else:
    favor(63120, 63121, 63122)

sm.dispose()
