# 37610 [Reverse City] The Find - start: Rasol (3004613), T-Boy's Research Train 3
# Official summary: defeat Exterminator T-Drones and obtain T-Boy's Research Notes.
sm.setSpeakerID(3004613) # Rasol
sm.sendNext("This is T-Boy's lab. And those #b#o8641054##k are wiping out his research as we speak!")
sm.sendSay("If we want to know what he's planning, we need those notes.")
if not sm.sendAskAccept("Defeat #o8641054# and bring me #b20 #t4036633##k. Hurry!"):
    sm.dispose()
sm.startQuest(parentID)
