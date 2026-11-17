# Pantheon Vacant Lot (940010000) exit - Ingredients for the Recipe for Disaster (25852, Angelic Buster)
# Back to Pantheon; leaving without the Nefarious Pouch means starting the quest over, as the quest says.
POUCH = 4033460

if sm.hasQuest(25852) and not sm.hasItem(POUCH):
    if sm.sendAskYesNo("You haven't found what the Nefarious Priests were carrying. Leave anyway? "
                       "#r(You'll have to forfeit the quest and start over.)#k"):
        sm.warp(400000000, 0)
else:
    sm.warp(400000000, 0)
sm.dispose()
