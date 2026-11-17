# NPC 2470009's Npc.wz script name is "q41315_NPCscript", so this file runs when it is clicked instead of 2470009.py.
# Keep the two the same.
# Mirror World side quest (Zero; Swordie Plus). 2470009 Gumball Machine (Mirror World Ludibrium): a Coin (from Manager
# Karl's Toy Factory Part-Time Job, 41316) buys a random candy. For I want a Strawberry Candy (41315), the strawberry one
# comes out by the third coin at the latest, so the 10 daily coins can't all miss.
COIN = 4033866
STRAWBERRY = 4033867
OTHERS = (4033868, 4033869, 4033870, 4033871)  # grape, orange, plum, peppermint

if not sm.hasItem(COIN):
    if sm.hasQuest(41315) and not sm.hasItem(STRAWBERRY):
        sm.sendSayOkay("#b(The Gumball Machine needs a #t4033866#. Manager Karl at the toy factory pays for help "
                       "in Coins.)#k")
    else:
        sm.sendSayOkay("#b(A Gumball Machine full of colorful candy. It takes #t4033866#s.)#k")
elif sm.sendAskYesNo("#b(Put a #t4033866# into the Gumball Machine?)#k"):
    sm.consumeItem(COIN, 1)
    if sm.hasQuest(41315) and not sm.hasItem(STRAWBERRY):
        tries = sm.getQRValue(41315, "coins")
        tries = int(tries) + 1 if tries and tries.isdigit() else 1
        sm.setQRValue(41315, "coins", str(tries))
        strawberry = tries >= 3 or sm.getRandomIntBelow(5) == 0
    else:
        strawberry = sm.getRandomIntBelow(5) == 0
    candy = STRAWBERRY if strawberry else OTHERS[sm.getRandomIntBelow(len(OTHERS))]
    sm.giveItem(candy)
    sm.sendSayOkay("#b(Clunk! Out rolls a #t%d#.)#k" % candy)
sm.dispose()
