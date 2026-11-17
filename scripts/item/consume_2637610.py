# Lotus Soul
souls = [2591419, 2591420, 2591421, 2591422, 2591423, 2591424, 2591425, 2591426, 2591427, 2591460, 2591461, 2591462, 2591463, 2591464, 2591465, 2591466, 2591467, 2591736, 2591737, 2591738, 2591739, 2591740, 2591741, 2591742, 2591743, 2591744, 2591745, 2591746, 2591747, 2591748, 2591749, 2591750, 2591751, 2591752, 2591804, 2591805]
rand = sm.getRandomIntBelow(len(souls))
if not sm.hasItem(2637610, 10):
    sm.sendSayOkay("You need at least 10 soul fragments to create a soul.")
elif chr.getConsumeInventory().isFull():
    sm.sendSayOkay("Please make some space in your USE inventory before using this.")
else:
    soul = souls[rand]
    sm.consumeItem(2637610, 10)
    sm.giveItem(soul)
