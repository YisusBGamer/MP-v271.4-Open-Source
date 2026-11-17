# Kalos Soul
souls = [2591701, 2591702, 2591703, 2591704, 2591705, 2591706, 2591707, 2591708, 2591709, 2591710, 2591711, 2591712, 2591713, 2591714, 2591715, 2591716, 2591717]
rand = sm.getRandomIntBelow(len(souls))
if not sm.hasItem(2634499, 10):
    sm.sendSayOkay("You need at least 10 soul fragments to create a soul.")
elif chr.getConsumeInventory().isFull():
    sm.sendSayOkay("Please make some space in your USE inventory before using this.")
else:
    soul = souls[rand]
    sm.consumeItem(2634499, 10)
    sm.giveItem(soul)
