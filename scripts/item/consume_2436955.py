# Mastery Book 20 (10 Pack): 10 Mastery Books 20
BOOK = 2431789
if sm.canHold(BOOK, 10):
    sm.consumeItem(parentID)
    sm.giveItem(BOOK, 10)
else:
    sm.sendSayOkay("Please make room in your Use inventory.")
