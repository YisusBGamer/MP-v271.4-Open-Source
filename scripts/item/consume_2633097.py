# Gigatoad Soul
souls = [2591688, 2591689, 2591690, 2591691, 2591692, 2591693, 2591694, 2591695, 2591696]
rand = sm.getRandomIntBelow(len(souls))
if not sm.hasItem(2633097, 10):
    sm.sendSayOkay("You need at least 10 soul fragments to create a soul.")
elif chr.getConsumeInventory().isFull():
    sm.sendSayOkay("Please make some space in your USE inventory before using this.")
else:
    soul = souls[rand]
    sm.consumeItem(2633097, 10)
    sm.giveItem(soul)
