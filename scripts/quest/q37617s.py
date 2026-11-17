# 37617 [Reverse City] Mark These Words - start: Barr (3004618). Defeat ~100 Combat T-Drone Model B (counted by the server).
sm.setSpeakerID(3004618) # Barr
sm.sendNext("He's on this train. I can feel it. Mark my words, we end this today.")
sm.sendSay("There's a line in my book: '#bWhat matters most to the survivor isn't skills or knowledge.#k' Time to find out if I believe it.")
if not sm.sendAskAccept("Clear out about #b100 #o8641056##k so we can get to him. Ready?"):
    sm.dispose()
sm.startQuest(parentID)
