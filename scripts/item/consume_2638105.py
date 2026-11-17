# Grand Sacred Symbol: Tallahart Coupon: one symbol per coupon (merged into the worn symbol when auto-merge is on)
SYMBOL = 1714000

given = 0
for i in range(sm.getQuantityOfItem(parentID)):
    if not sm.canHold(SYMBOL):
        break
    sm.consumeItem(parentID, 1)
    chr.addItemToInventory(SYMBOL, 1)
    given += 1
if given > 0:
    sm.chat("You received Grand Sacred Symbol: Tallahart x" + str(given) + ".")
else:
    sm.chat("You need a free slot in your EQUIP inventory.")
