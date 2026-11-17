# 37608 [Reverse City] Found a Way - start: Rasol (3004612). Defeat 100 #o8641053# (counted by the server).
sm.setSpeakerID(3004612) # Rasol
sm.sendNext("Those #b#o8641053##k are T-Boy's eyes. If they spot us, he'll know we're here.")
if not sm.sendAskAccept("Take out about #b100 #o8641053##k. Can you do it?"):
    sm.dispose()
sm.startQuest(parentID)
