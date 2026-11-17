# 252030000 - To Ravana
# "Ravana the Demon" (3863) asks for Busted Ravana (9100025), who is only in the quest's own copy of the altar
# (925120100), so the door leads there during the quest; otherwise to the real Ravana fight (252030100).
RAVANA_THE_DEMON = 3863
if sm.hasQuest(RAVANA_THE_DEMON):
    sm.warpInstanceIn(925120100, 0)
elif sm.checkParty():
    sm.warpInstanceIn(252030100, 0, True) # Ravana's Golden Altar
