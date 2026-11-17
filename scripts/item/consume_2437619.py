# Fancy Amplifier Monsterbloom (2437619): adds Fancy Amplifier (4300012) to the Monster Collection.
# Not used up when Fancy Amplifier is already in the collection.
MOB = 4300012

collection = chr.getAccount().getMonsterCollection()
if collection.hasMob(MOB):
    sm.sendSayOkay("#bFancy Amplifier#k is already in your Monster Collection.")
else:
    collection.addMobAndUpdateClient(MOB, chr)
    if collection.hasMob(MOB):
        sm.consumeItem()
        sm.chat("Fancy Amplifier was added to your Monster Collection.")
    else:
        sm.sendSayOkay("Fancy Amplifier couldn't be added to your Monster Collection right now.")
sm.dispose()
