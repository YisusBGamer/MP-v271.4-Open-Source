# [Labyrinth of Suffering] Toward the Black Sun (36761), Melange on the White Spear. The crash (993063001 in the
# data) is told here; you wake up at the Crash Site.
MELANGE = 3003703

if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.setPlayerAsSpeaker()
        sm.sendNext("(Melange showed up at the White Spear before you left.)")
        sm.setSpeakerID(MELANGE)
        if sm.sendAskAccept("It's reassuring to see Melange boarding the battleship. You can use all the help you can "
                            "get in the coming battle."):
            sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setQRValue(parentID, "dummy", "1")
        sm.setPlayerAsSpeaker()
        sm.sendNext("(The battleship suddenly crashed after entering the black sun. You lost consciousness and struggled "
                    "within the illusions.)")
        sm.sendNext("(When you woke up, your head felt hazy in places... but you need to come to your senses since you're "
                    "the leader.)")
        if sm.completeQuest(parentID):
            sm.warp(450011120, 0)
sm.dispose()
