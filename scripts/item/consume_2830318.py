# First Adversary Soul
souls = [2591818, 2591819, 2591820, 2591821, 2591822, 2591823, 2591824, 2591825, 2591826, 2591827, 2591828, 2591829, 2591830, 2591831, 2591832, 2591833, 2591834]
rand = sm.getRandomIntBelow(len(souls))
if not sm.hasItem(2830318, 10):
    sm.sendSayOkay("You need at least 10 soul fragments to create a soul.")
elif chr.getConsumeInventory().isFull():
    sm.sendSayOkay("Please make some space in your USE inventory before using this.")
else:
    soul = souls[rand]
    sm.consumeItem(2830318, 10)
    sm.giveItem(soul)
