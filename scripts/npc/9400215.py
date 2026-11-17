# Joel (Land of Riches, in the castle) (The Afterlands, see AfterlandsEvent)
from net.swordie.ms.world import AfterlandsEvent as A
from net.swordie.ms.world import SeasonalEvents
NPC = 9400215
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

if fresh(63038, 63031):
    sm.sendNext("F-f-food...")
    sm.sendNext("I have gold. Rooms full of it. But the #rFood Thief Moustrosities#k took every crumb, and you can't "
                "eat gold. Well, most of it.")
    start(63038)
    sm.sendSayOkay("Please... #b30 Food Baskets#k. The Moustrosities are in the #bField of Music#k, past the castle "
                   "gate.")
elif has(63038):
    if count(A.FOOD_BASKET) >= 30:
        finish(63038)
        sm.sendSayOkay("Food! Real food! ...Oh. Oh no. It's all gone already. Could you... maybe...")
    else:
        sm.sendSayOkay("F-f-food... (#b30 Food Baskets#k)")
elif fresh(63039, 63038):
    sm.sendNext("I know, I know. But a man has his priorities, and mine is eating.")
    start(63039)
    sm.sendSayOkay("Just #b30 more Food Baskets#k. Please.")
elif has(63039):
    if count(A.FOOD_BASKET) >= 30:
        finish(63039)
        sm.sendSayOkay("Now I can think again. Thank you.")
    else:
        sm.sendSayOkay("Unless you have food, I can't talk to you. Gotta conserve my energy...")
elif fresh(63040, 63039):
    sm.sendNext("Why is there no food in the Land of Riches? Follow the footsteps. They're... big footsteps.")
    sm.sendNext("#bMansa#k. He ate it all. Then he ate the furniture. Then he started on the treasure.")
    start(63040)
    finish(63040)
    sm.giveItem(A.GOLD_PIECE, 1)
    sm.sendSayOkay("Mansa won't talk to anyone who doesn't bring him something to eat. Here, take a #b#t2436086##k. "
                   "It's the only thing he still likes.")
elif fresh(63066, 63041):
    sm.sendNext("Do you ever wonder what's beyond the castle walls? I've never been outside. My treasure is in here.")
    start(63066)
    sm.sendSayOkay("W-where's my food...? Bring me... #b10 Food Baskets#k... and I'll think about it.")
elif has(63066):
    if count(A.FOOD_BASKET) >= 10:
        finish(63066)
        sm.sendSayOkay("All right, all right. I'll consider going outside. Consider.")
    else:
        sm.sendSayOkay("W-where's my food...? Bring me... 10 #t4034994# items...")
elif fresh(63067, 63066):
    sm.sendNext("I can't leave! The #rTreasure Thief Moustrosities#k would take everything the moment I stepped out!")
    start(63067)
    sm.sendSayOkay("You'll need to defeat at least #r100 Treasure Thief Moustrosities#k before I could ever consider my "
                   "treasure safe!")
elif has(63067):
    if ready(63067):
        finish(63067)
        sm.sendSayOkay("A hundred?! ...I'm running out of excuses, aren't I.")
    else:
        sm.sendSayOkay("You'll need to defeat at least 100 of #o9402046# before I could ever consider my treasure "
                       "safe!")
elif fresh(63068, 63067):
    sm.sendNext("Fine. FINE. I'll go out. But my treasure comes with me. All of it.")
    start(63068)
    me("(You carry chest after chest of Joel's treasure out through the castle gate.)")
    sm.setQRValue(63068, "end", "1")
    sm.setQRValue(63093, "1", False)
    sm.sendSayOkay("Meet me outside! Gently with the chests!")
elif has(63068):
    sm.sendSayOkay("Please bring me my treasure! I'm waiting outside!")
else:
    favor(63042, 63043, 63044)

sm.dispose()
