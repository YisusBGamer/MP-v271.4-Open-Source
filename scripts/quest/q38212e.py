# 38212 [Karote] The Tower Complete - end (Belle, Top Floor)
BELLE = 3005246

if sm.hasQuest(parentID):
    sm.setSpeakerID(BELLE)
    sm.setBoxChat()
    sm.sendNext("Nicky? He ran straight for the stairs the second the tower finished! Said he wasn't leaving now.")
    sm.sendNext("He's gone up to the #b#m410005005##k alone. Go after him!")
    sm.completeQuest(parentID)
sm.dispose()
