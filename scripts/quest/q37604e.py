# 37604 [Reverse City] The Rat Snack - end: Rasol (3004609)
# Official summary: you delivered Blue Rat Meat to Rasol.
sm.setSpeakerID(3004609) # Rasol
if not sm.hasItem(4036631, 20):
    sm.sendSayOkay("Gather Blue Rat Meat x20.")  # official in-progress line
    sm.dispose()
if sm.completeQuest(parentID):
    sm.consumeItem(4036631, 20)
    sm.sendNext("That's lunch sorted. It won't win any prizes, but it keeps the whole refuge going.")
    sm.sendSayOkay("Barr won't eat the blue stuff, though. Talk to me again.")
