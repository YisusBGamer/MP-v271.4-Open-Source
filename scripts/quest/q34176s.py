# [Vanishing Journey] Folding a Paper Boat (34176)
if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setPlayerAsSpeaker()
        sm.sendNext("(You write down what you've learned about the Arcane River and fold the letter into a paper boat.)")
        sm.completeQuest(parentID)
sm.dispose()
