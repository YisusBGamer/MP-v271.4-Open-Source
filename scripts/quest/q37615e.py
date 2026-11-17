# 37615 [Reverse City] Just Parts - end: Barr (3004617)
# Official summary: you delivered Drone Parts to Barr.
sm.setSpeakerID(3004617) # Barr
if not sm.hasItem(4036635, 20):
    sm.sendSayOkay("Defeat Combat T-Drone Model A and gather #bDrone Part#k.")  # official in-progress line
    sm.dispose()
if sm.completeQuest(parentID):
    sm.consumeItem(4036635, 20)
    sm.sendNext("That's the last of what I need. Just parts, but parts are all we need.")
    sm.sendSayOkay("Now let's find T-Boy.")
