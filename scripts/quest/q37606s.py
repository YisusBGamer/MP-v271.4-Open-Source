# 37606 [Reverse City] From a Strange World - start: Rasol (3004610)
sm.setSpeakerID(3004610) # Rasol
sm.sendNext("Get enough #b#t4036632##k and Barr will come out of his hideout for sure.")
sm.sendSay("He's from another world too, you know. He'll want to hear about that walkie-talkie.")
if not sm.sendAskAccept("Bring me #b20 #t4036632##k. Defeat #o8641052# around here."):
    sm.dispose()
sm.startQuest(parentID)
