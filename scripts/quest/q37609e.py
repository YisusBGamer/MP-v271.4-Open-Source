# 37609 [Reverse City] Security Failed - end: Rasol (3004613)
sm.setSpeakerID(3004613) # Rasol
if not sm.completeQuest(parentID):
    sm.dispose()
sm.sendNext("Made it! This car is full of T-Boy's research.")
sm.sendSayOkay("Uh-oh... the drones are trying to wipe his notes!")
