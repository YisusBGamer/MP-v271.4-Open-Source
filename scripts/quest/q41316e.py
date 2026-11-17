# Toy Factory Part-Time Job (41316, Mirror World Ludibrium; repeatable): Manager Karl trades 1 Coin for every
# 5 Dual Ghost Pirate Wheels. The Coins go into the Gumball Machine (npc/2470009.py).
WHEEL, COIN = 4009101, 4033866
if sm.hasQuest(parentID) and sm.getQuantityOfItem(WHEEL) >= 5 and sm.questEndDialogue(parentID):
    sm.consumeItem(WHEEL, 5)
    sm.giveItem(COIN)
    sm.setSpeakerID(2470004)
    sm.sendSayOkay("These will keep the line running for now. Here's your #b#t4033866##k. Come back if you find more.")
elif sm.hasQuest(parentID):
    sm.setSpeakerID(2470004)
    sm.sendSayOkay("I'll give you 1 #r#t4033866##k for every 5 #r#t4009101##r#k items. ")
sm.dispose()
