# Baldrix Soul
souls = [2591771, 2591772, 2591773, 2591774, 2591775, 2591776, 2591777, 2591778, 2591779, 2591780, 2591781, 2591782, 2591783, 2591784, 2591785, 2591786, 2591787]
rand = sm.getRandomIntBelow(len(souls))
if not sm.hasItem(2639268, 10):
    sm.sendSayOkay("You need at least 10 soul fragments to create a soul.")
elif chr.getConsumeInventory().isFull():
    sm.sendSayOkay("Please make some space in your USE inventory before using this.")
else:
    soul = souls[rand]
    sm.consumeItem(2639268, 10)
    sm.giveItem(soul)
