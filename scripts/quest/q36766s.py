# [Labyrinth of Suffering] The Only One Left (36766), from the quest notifier
if not sm.hasQuestCompleted(parentID):
    sm.setPlayerAsSpeaker()
    if not sm.hasQuest(parentID):
        if sm.sendAskAccept("You thought they all disappeared, but you hear a person's voice from somewhere. Go to where "
                            "you hear the voice!"):
            sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setQRValue(parentID, "dummy", "1")
        sm.sendNext("(When you went to where you heard the voice, you arrived at a new place.)")
        sm.completeQuest(parentID)
sm.dispose()
