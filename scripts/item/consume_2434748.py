# Wednesday's Trait Box (Monster Park day-of-the-week reward): 15-30 Monster Park Commemorative Coins and one random
# gift from the GMS list (MonsterPark.BOX_GIFTS).
from net.swordie.ms.client.character import MonsterPark

BOX = 2434748

sm.setSpeakerID(9071005)
if hasattr(MonsterPark, "openBox"):
    if sm.sendAskYesNo("#e#t" + str(BOX) + "##n holds:\r\n" + MonsterPark.describeBox(BOX) + "\r\nOpen it?"):
        err = MonsterPark.openBox(chr, BOX)
        if err is not None:
            sm.sendSayOkay(err)
else:
    COIN = 4310020
    amount = 15 + sm.getRandomIntBelow(16)
    if sm.canHold(COIN, amount):
        sm.giveItem(COIN, amount)
        sm.consumeItem()
    else:
        sm.chat("Make room in your Etc inventory for " + str(amount) + " Monster Park Commemorative Coins first.")
sm.dispose()
