# [Labyrinth of Suffering] Fire the Signal Flare (36763), started and finished at the Cygnus Soldier
SOLDIER = 3003787

if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.setSpeakerID(SOLDIER)
        if sm.sendAskAccept("You are in a situation where it's difficult to move on your own. Fire a signal flare and "
                            "request support from the main base."):
            sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setQRValue(parentID, "dummy", "1")
        sm.setPlayerAsSpeaker()
        sm.sendNext("(None of the others came back, so you set up a signal flare to guide them. The only trouble is, the "
                    "flare did no good. It's like the darkness just swallowed up the light.)")
        sm.sendNext("(To make matters worse, when one of the soldiers finally did return, he was out of breath and "
                    "panicky. What is going on?)")
        sm.completeQuest(parentID)
sm.dispose()
