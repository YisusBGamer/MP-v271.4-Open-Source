# [Labyrinth of Suffering] Search and Rescue (36765), started and finished at the Cygnus Soldier
SOLDIER = 3003787

if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.setSpeakerID(SOLDIER)
        if sm.sendAskAccept("Maybe the missing people were attacked by the monster. You need to save them before there "
                            "is further loss."):
            sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setQRValue(parentID, "dummy", "1")
        sm.setPlayerAsSpeaker()
        sm.sendNext("(You searched again to save the people. When you turned around at one point, they all disappeared "
                    "and you were alone.)")
        sm.completeQuest(parentID)
sm.dispose()
