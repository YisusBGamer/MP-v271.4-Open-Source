# [Labyrinth of Suffering] Into the Labyrinth (36764), from the quest notifier; handed in to the Cygnus Soldier at
# the Crash Site
if not sm.hasQuestCompleted(parentID):
    sm.setPlayerAsSpeaker()
    if not sm.hasQuest(parentID):
        if sm.sendAskAccept("You need to find out for yourself what is going on inside. Head in the direction the search "
                            "party went."):
            sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.sendNext("(You searched and found traces of the missing people, but those traces disappeared before your "
                    "eyes.)")
        sm.setQRValue(parentID, "dummy", "1")
sm.dispose()
