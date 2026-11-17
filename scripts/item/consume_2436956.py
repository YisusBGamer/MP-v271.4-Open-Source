# Mastery Book 30 (10 Pack): 10 Mastery Books 30
BOOK = 2431790
if sm.canHold(BOOK, 10):
    sm.consumeItem(parentID)
    sm.giveItem(BOOK, 10)
else:
    sm.sendSayOkay("Please make room in your Use inventory.")
