# Horntail Monsterbloom (2435366): adds Horntail (8810018) to the Monster Collection.
# Not used up when Horntail is already in the collection.
MOB = 8810018

collection = chr.getAccount().getMonsterCollection()
if collection.hasMob(MOB):
    sm.sendSayOkay("#bHorntail#k is already in your Monster Collection.")
else:
    collection.addMobAndUpdateClient(MOB, chr)
    if collection.hasMob(MOB):
        sm.consumeItem()
        sm.chat("Horntail was added to your Monster Collection.")
    else:
        sm.sendSayOkay("Horntail couldn't be added to your Monster Collection right now.")
sm.dispose()
