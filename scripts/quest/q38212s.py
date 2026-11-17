# 38212 [Karote] The Tower Complete - start (Neinheart, Top Floor)
NEINHEART = 3005234

sm.setSpeakerID(NEINHEART)
sm.setBoxChat()
sm.sendNext("The automatons called you the 'vessel of the Adversary'... and so did the man in the memories.")
sm.sendNext("If he built this tower to make an Adversary of his own, then he may be a #rfalse Adversary#k. "
            "Going further is too dangerous. We should call off the expedition.")
if sm.sendAskAccept("...Where is Nicky? Ask #b#p3005246##k if she saw where he went."):
    sm.startQuest(parentID)
sm.dispose()
