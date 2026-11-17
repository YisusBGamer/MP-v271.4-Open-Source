# Chaos Horntail Monsterbloom (2435367): adds Chaos Horntail (8810118) to the Monster Collection.
# Not used up when Chaos Horntail is already in the collection.
MOB = 8810118

collection = chr.getAccount().getMonsterCollection()
if collection.hasMob(MOB):
    sm.sendSayOkay("#bChaos Horntail#k is already in your Monster Collection.")
else:
    collection.addMobAndUpdateClient(MOB, chr)
    if collection.hasMob(MOB):
        sm.consumeItem()
        sm.chat("Chaos Horntail was added to your Monster Collection.")
    else:
        sm.sendSayOkay("Chaos Horntail couldn't be added to your Monster Collection right now.")
sm.dispose()
