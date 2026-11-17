# Malefic Star Soul
souls = [2591851, 2591852, 2591853, 2591854, 2591855, 2591856, 2591857, 2591858, 2591859]
rand = sm.getRandomIntBelow(len(souls))
if not sm.hasItem(2831409, 10):
    sm.sendSayOkay("You need at least 10 soul fragments to create a soul.")
elif chr.getConsumeInventory().isFull():
    sm.sendSayOkay("Please make some space in your USE inventory before using this.")
else:
    soul = souls[rand]
    sm.consumeItem(2831409, 10)
    sm.giveItem(soul)
