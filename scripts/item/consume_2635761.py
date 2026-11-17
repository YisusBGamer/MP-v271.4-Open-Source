# Kaling Soul
souls = [2591718, 2591719, 2591720, 2591721, 2591722, 2591723, 2591724, 2591725, 2591726, 2591727, 2591728, 2591729, 2591730, 2591731, 2591732, 2591733, 2591734]
rand = sm.getRandomIntBelow(len(souls))
if not sm.hasItem(2635761, 10):
    sm.sendSayOkay("You need at least 10 soul fragments to create a soul.")
elif chr.getConsumeInventory().isFull():
    sm.sendSayOkay("Please make some space in your USE inventory before using this.")
else:
    soul = souls[rand]
    sm.consumeItem(2635761, 10)
    sm.giveItem(soul)
