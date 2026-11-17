# 38205 [Karote] The Tower's Remains - end (Belle, Bottom Floor)
BELLE = 3005244

if sm.hasQuest(parentID):
    sm.setSpeakerID(BELLE)
    sm.setBoxChat()
    sm.sendNext("Everything inside the tower is smashed to pieces. Nothing left worth keeping.")
    sm.sendNext("Hey, Irena's waving at you. Looks like she found something after all.")
    sm.completeQuest(parentID)
sm.dispose()
