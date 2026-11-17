# [Arcana] With Determination (34345): the letter to the Alliance, sent from the Calm Water at the Forest Entrance. No one
# hands it in.
if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.setPlayerAsSpeaker()
        if not sm.sendAskAccept("(Write a letter to the Alliance about what you've learned in Arcana, and send it off as a paper boat?)"):
            sm.dispose()
        sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setPlayerAsSpeaker()
        sm.sendNext("(You fold the letter into a paper boat and set it on the calm water. It travels far up the river of Arcana.)")
        sm.sendNext("(Will this paper boat reach the Alliance? It would be great if it did...)")
        sm.completeQuest(parentID)
sm.dispose()
