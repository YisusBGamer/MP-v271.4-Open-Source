# 37617 [Reverse City] Mark These Words - end: Barr (3004618)
# Official story: T-Boy speeds the train toward M Tower and it crashes.
sm.setSpeakerID(3004618) # Barr
if not sm.completeQuest(parentID):
    sm.sendSayOkay("Defeat around 100 Combat T-Drone Model Bs.")  # official in-progress line
    sm.dispose()
sm.sendNext("That's all of them! Now where's... wait, the train's speeding up! T-Boy's sending us straight at #bM Tower#k!")
sm.sendSayOkay("(The train races out of control and crashes into #bM Tower#k!)")
sm.warp(450014200, 0)
