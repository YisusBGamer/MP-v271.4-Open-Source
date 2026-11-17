# [Labyrinth of Suffering] Follow the Screams (36767), from the quest notifier: you carry the fainted Alliance Member
# to the Core Peak (450011220), where Her Story (36768) starts
if not sm.hasQuestCompleted(parentID):
    sm.setPlayerAsSpeaker()
    if not sm.hasQuest(parentID):
        if sm.sendAskAccept("The person's voice you heard is now clearer. Go to where you hear the voice!"):
            sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setQRValue(parentID, "dummy", "1")
        sm.sendNext("(When you followed the person's voice that was clearer, you discovered an Alliance Member who has "
                    "fainted. It was the first person you saved.)")
        sm.sendNext("(You take her somewhere safe.)")
        if sm.completeQuest(parentID):
            sm.warp(450011220, 0)
sm.dispose()
