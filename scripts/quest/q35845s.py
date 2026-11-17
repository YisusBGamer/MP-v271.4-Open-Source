# [Limina] On the Resistance Ship (35845), Neinheart in End of the World 1-1; handed in to Claudine in End of the
# World 2-1, where arriving supports the ship (dir 1, field/enter_450012400)
NEINHEART = 3003988

if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setPlayerAsSpeaker()
    sm.sendNext("(You defended the ship admirably, just as Neinheart requested.)")
    sm.setSpeakerID(NEINHEART)
    if sm.sendAskAccept("I have my suspicions about the 'path'. But we won't know for sure until analysis of the Core "
                        "Fragment is complete. Before that, you must support the Resistance ship in #bEnd of the World "
                        "2-1#k."):
        sm.startQuest(parentID)
        if sm.getFieldID() == 450012400:
            sm.setQRValue(parentID, "dir", "1")
sm.dispose()
