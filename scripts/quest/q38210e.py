# 38210 [Karote] The Adversary Experiment - end (Irena, Halfway Up)
IRENA = 3005239

if sm.hasQuest(parentID):
    sm.setSpeakerID(IRENA)
    sm.setBoxChat()
    sm.sendNext("Nicky? He's been staring up at the top of the tower ever since you came back.")
    sm.sendNext("He keeps saying his father would have wanted to see this. Something about him is off, #h0#.")
    sm.completeQuest(parentID)
sm.dispose()
