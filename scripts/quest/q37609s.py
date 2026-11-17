# 37609 [Reverse City] Security Failed - start: Rasol (3004612); end: Rasol (3004613) in #m450014120#
sm.setSpeakerID(3004612) # Rasol
sm.sendNext("The security drones are down. T-Boy's lab cars should be at the back of the train.")
if not sm.sendAskAccept("Meet me in #b#m450014120##k. Ready?"):
    sm.dispose()
sm.startQuest(parentID)
