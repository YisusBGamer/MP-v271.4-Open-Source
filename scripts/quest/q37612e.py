# 37612 [Reverse City] Surviving in the Subway - end: Barr (3004615)
# Official summary: you delivered Drone Parts to Barr.
sm.setSpeakerID(3004615) # Barr
if not sm.hasItem(4036634, 20):
    sm.sendSayOkay("Gather Drone Part x20.")  # official in-progress line
    sm.dispose()
if sm.completeQuest(parentID):
    sm.consumeItem(4036634, 20)
    sm.sendNext("These will do nicely. A good survivor makes do with whatever's lying around.")
    sm.sendSayOkay("Let's push on to the next car.")
