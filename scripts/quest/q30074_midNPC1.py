# 1302021 Thorr, the potion seller (Mushroom Castle Banquet Hall) - [Mushroom Castle] Welcome, Valued Customer (30074):
# he sells his last Mushroom All Cure Potion. Swordie Plus: the price isn't in the game files; 5,000 mesos.
POTION, PRICE = 4034113, 5000
sm.setSpeakerID(1302021)
if sm.hasItem(POTION):
    sm.sendSayOkay("Hurry, take that potion to the Prime Minister! Thank you for your business, valued customer!")
elif sm.sendAskYesNo("A Mushroom All Cure Potion? You're in luck, valued customer! I have exactly one left.\r\n\r\n"
                     "It's yours for #b5,000 mesos#k. Will you take it?"):
    if sm.getMesos() >= PRICE:
        sm.deductMesos(PRICE)
        sm.giveItem(POTION)
        sm.sendSayOkay("Pleasure doing business! May it cure whatever ails you... or whoever.")
    else:
        sm.sendSayOkay("Oh dear, you don't seem to have enough mesos. Come back when you do!")
else:
    sm.sendSayOkay("Suit yourself. It's the last one, though!")
sm.dispose()
