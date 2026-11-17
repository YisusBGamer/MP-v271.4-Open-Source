# Ingredients for the Recipe for Disaster (25852, Angelic Buster) - Eskalade
# Takes Angelic Buster to the Nefarious Priests (the Pantheon Vacant Lot, 940010000, whose recorded scene starts the
# fight), where the Nefarious Pouch is found. No Say.img lines exist for it; written from the quest's own summary.
ESKALADE = 3000018

if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(ESKALADE)
    if sm.sendAskAccept("Psst! I just saw some #rNefarious Priests#k sneaking around near Pantheon. They're up to "
                        "something, I can smell it. Let's go after them! #r(If you fail, you'll have to forfeit the "
                        "quest and start over.)#k"):
        sm.startQuest(parentID)
if sm.hasQuest(parentID) and not sm.hasItem(4033460):
    sm.warp(940010000, 0)
sm.dispose()
