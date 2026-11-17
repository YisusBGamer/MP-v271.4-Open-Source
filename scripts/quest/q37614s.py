# 37614 [Reverse City] Three Outsiders - start: Barr (3004616). Defeat ~100 Combat T-Drone Model B (counted by the server).
sm.setSpeakerID(3004616) # Barr
sm.sendNext("Funny, isn't it? You, me, and T-Boy... three outsiders deciding the fate of a city that isn't ours.")
sm.sendSay("Well, two of us are on the right side. Keep those #b#o8641056##k off me while I work.")
if not sm.sendAskAccept("Defeat around #b100 #o8641056##k. Can you do it?"):
    sm.dispose()
sm.startQuest(parentID)
