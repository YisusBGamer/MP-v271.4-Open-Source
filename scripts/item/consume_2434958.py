# Ice Golem Monsterbloom (2434958): adds Ice Golem (9500150) to the Monster Collection.
# Not used up when Ice Golem is already in the collection.
MOB = 9500150

collection = chr.getAccount().getMonsterCollection()
if collection.hasMob(MOB):
    sm.sendSayOkay("#bIce Golem#k is already in your Monster Collection.")
else:
    collection.addMobAndUpdateClient(MOB, chr)
    if collection.hasMob(MOB):
        sm.consumeItem()
        sm.chat("Ice Golem was added to your Monster Collection.")
    else:
        sm.sendSayOkay("Ice Golem couldn't be added to your Monster Collection right now.")
sm.dispose()
