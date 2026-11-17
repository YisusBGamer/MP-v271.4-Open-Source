# [Labyrinth of Suffering] To the Crash Site (36769), from the quest notifier; handed in to Azalin
if not sm.hasQuestCompleted(parentID):
    sm.setPlayerAsSpeaker()
    if not sm.hasQuest(parentID):
        if sm.sendAskAccept("You need to reorganize before going to save other people. Tell Azalin to return to the "
                            "battleship."):
            sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.sendNext("(When you returned to the battleship to organize, the wall was shining. You investigated, but "
                    "dangerous monsters were summoned. You retreated in a hurry.)")
        sm.setQRValue(parentID, "dummy", "1")
sm.dispose()
