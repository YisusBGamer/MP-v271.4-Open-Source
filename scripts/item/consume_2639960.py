# Sacred Symbol: Arcus Lv. 5 Coupon
from net.swordie.ms.loaders import ItemData

SYMBOL = 1713001

sm.setSpeakerID(9010000)
if sm.canHold(SYMBOL):
    symbol = ItemData.getItemDeepCopy(SYMBOL)
    symbol.setSymbolLevel(5)
    symbol.initSymbolStats(5, 0, chr.getJob())
    sm.consumeItem(parentID)
    chr.addItemToInventory(symbol)
else:
    sm.sendNext("You don't have enough free slots in your EQUIP inventory to receive the reward.")
