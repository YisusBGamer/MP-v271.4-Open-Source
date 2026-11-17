# 37615 [Reverse City] Just Parts - start: Barr (3004617)
sm.setSpeakerID(3004617) # Barr
sm.sendNext("The surface train needs a stronger override. More #b#t4036635##k, please!")
if not sm.sendAskAccept("Bring me #b20 #t4036635##k. Defeat #o8641055# for more parts."):
    sm.dispose()
sm.startQuest(parentID)
