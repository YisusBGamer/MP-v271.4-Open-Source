# Cheap Amplifier Monsterbloom (2437618): adds Cheap Amplifier (4300011) to the Monster Collection.
# Not used up when Cheap Amplifier is already in the collection.
MOB = 4300011

collection = chr.getAccount().getMonsterCollection()
if collection.hasMob(MOB):
    sm.sendSayOkay("#bCheap Amplifier#k is already in your Monster Collection.")
else:
    collection.addMobAndUpdateClient(MOB, chr)
    if collection.hasMob(MOB):
        sm.consumeItem()
        sm.chat("Cheap Amplifier was added to your Monster Collection.")
    else:
        sm.sendSayOkay("Cheap Amplifier couldn't be added to your Monster Collection right now.")
sm.dispose()
