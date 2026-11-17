# [Limina] On the Resistance Ship (35845), handing it in to Claudine
CLAUDINE = 3003989

if sm.hasQuest(parentID):
    if sm.getFieldID() == 450012400:
        sm.setQRValue(parentID, "dir", "1")
    sm.setSpeakerID(CLAUDINE)
    if sm.completeQuest(parentID):
        sm.sendNext("Thanks for the help. Now that the ship is holding, talk to the soldier in #bEnd of the World "
                    "2-2#k.")
sm.dispose()
