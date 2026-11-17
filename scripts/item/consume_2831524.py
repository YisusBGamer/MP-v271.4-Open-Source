# Jupiter Soul
souls = [2591868, 2591869, 2591870, 2591871, 2591872, 2591873, 2591874, 2591875, 2591876]
rand = sm.getRandomIntBelow(len(souls))
if not sm.hasItem(2831524, 10):
    sm.sendSayOkay("You need at least 10 soul fragments to create a soul.")
elif chr.getConsumeInventory().isFull():
    sm.sendSayOkay("Please make some space in your USE inventory before using this.")
else:
    soul = souls[rand]
    sm.consumeItem(2831524, 10)
    sm.giveItem(soul)
