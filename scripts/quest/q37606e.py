# 37606 [Reverse City] From a Strange World - end: Rasol (3004610)
# Official summary: you delivered Purple Rat Meat to Rasol and met Barr.
sm.setSpeakerID(3004610) # Rasol
if not sm.hasItem(4036632, 20):
    sm.sendSayOkay("Gather Purple Rat Meat x20.")  # official in-progress line
    sm.dispose()
if sm.completeQuest(parentID):
    sm.consumeItem(4036632, 20)
    sm.sendNext("That's plenty. Barr's going to be in a good mood. Let's take it to his place.")
    sm.sendSayOkay("(Rasol leads you to #bBarr's Abode#k, where you meet #bBarr Granger#k, the overworlder from Friends World.)")
    sm.warp(450014090, 0)
