# 38203 [Karote] Tower Chaser - end (Nicky, Hotel Arcus 2nd Floor)
NICKY = 3005248

if sm.hasQuest(parentID):
    sm.setSpeakerID(NICKY)
    sm.setBoxChat()
    sm.sendNext("You're asking about Karote? Really? Nobody ever asks about Karote!")
    sm.sendNext("Sit down, sit down. I've got plenty to tell you.")
    sm.completeQuest(parentID)
sm.dispose()
