# Limbo Soul
souls = [2591754, 2591755, 2591756, 2591757, 2591758, 2591759, 2591760, 2591761, 2591762, 2591763, 2591764, 2591765, 2591766, 2591767, 2591768, 2591769, 2591770]
rand = sm.getRandomIntBelow(len(souls))
if not sm.hasItem(2638089, 10):
    sm.sendSayOkay("You need at least 10 soul fragments to create a soul.")
elif chr.getConsumeInventory().isFull():
    sm.sendSayOkay("Please make some space in your USE inventory before using this.")
else:
    soul = souls[rand]
    sm.consumeItem(2638089, 10)
    sm.giveItem(soul)
