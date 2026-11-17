# [Labyrinth of Suffering] Her Story (36768): the rescued Alliance Member at the Core Peak; handed in to Azalin
STRANGER = 3003714

if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.setPlayerAsSpeaker()
        sm.sendNext("(The Alliance soldier finally regained consciousness.)")
        sm.setSpeakerID(STRANGER)
        if sm.sendAskAccept("You took the fainted Alliance Member to a safe place. Talk to her."):
            sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setQRValue(parentID, "dummy", "1")
        sm.setPlayerAsSpeaker()
        sm.sendNext("(The Alliance Member introduced herself as Azalin, a Night Walker of the Cygnus Knights.)")
sm.dispose()
