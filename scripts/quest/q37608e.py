# 37608 [Reverse City] Found a Way - end: Rasol (3004612)
# Official summary: you defeated the surrounding Seeker T-Drones.
sm.setSpeakerID(3004612) # Rasol
if not sm.completeQuest(parentID):
    sm.sendSayOkay("Defeat around 100 Seeker T-Drones.")  # official in-progress line
    sm.dispose()
sm.sendNext("That's the last of the drones watching this car. T-Boy shouldn't notice us for a while.")
sm.sendSayOkay("His lab has to be further in. Let's keep going.")
