# 37612 [Reverse City] Surviving in the Subway - start: Barr (3004615)
sm.setSpeakerID(3004615) # Barr
sm.sendNext("The trains to M Tower have no power. I can rig an override, but I need parts.")
sm.sendSay("#b#o8641055##k are built from exactly what I need.")
if not sm.sendAskAccept("Bring me #b20 #t4036634##k. Defeat #o8641055# on the trains."):
    sm.dispose()
sm.startQuest(parentID)
