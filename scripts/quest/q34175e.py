# [Vanishing Journey] The Starting Point of the Arcane River (34175), handing it in to Kima
KIMA = 3003110
FOLDING = 34176

if not sm.hasQuestCompleted(FOLDING):
    if not sm.hasQuest(FOLDING):
        sm.startQuestNoCheck(FOLDING)
    sm.setPlayerAsSpeaker()
    sm.sendNext("(You write down what you've learned about the Arcane River and fold the letter into a paper boat.)")
    sm.completeQuest(FOLDING)
sm.setSpeakerID(KIMA)
sm.sendNext("That's a fine boat. Set it on the #bcalm water#k by the lakeshore and let's see where it goes.")
sm.completeQuest(parentID)
sm.dispose()
