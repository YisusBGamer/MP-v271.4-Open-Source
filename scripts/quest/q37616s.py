# 37616 [Reverse City] He Is There - start: Barr (3004617); end: Barr (3004618) in #m450014190#
sm.setSpeakerID(3004617) # Barr
sm.sendNext("T-Boy's at the top of M Tower. I can see it from here. Let's get to the front car.")
if not sm.sendAskAccept("Meet me in #b#m450014190##k. Ready?"):
    sm.dispose()
sm.startQuest(parentID)
