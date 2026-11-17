# Prime Minister Monsterbloom (2434959): adds Prime Minister (3300008) to the Monster Collection.
# Not used up when Prime Minister is already in the collection.
MOB = 3300008

collection = chr.getAccount().getMonsterCollection()
if collection.hasMob(MOB):
    sm.sendSayOkay("#bPrime Minister#k is already in your Monster Collection.")
else:
    collection.addMobAndUpdateClient(MOB, chr)
    if collection.hasMob(MOB):
        sm.consumeItem()
        sm.chat("Prime Minister was added to your Monster Collection.")
    else:
        sm.sendSayOkay("Prime Minister couldn't be added to your Monster Collection right now.")
sm.dispose()
