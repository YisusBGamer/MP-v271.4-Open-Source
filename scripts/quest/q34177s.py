# [Vanishing Journey] Paper Boat Flowing Upstream (34177), the Calm Water at the Lakeshore. No one hands it in.
if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setPlayerAsSpeaker()
        sm.sendNext("(You set the paper boat on the calm water. Against all reason, it drifts upstream and out of sight.)")
        sm.sendNext("(Will it reach the Alliance? It would be great if it did...)")
        sm.completeQuest(parentID)
sm.dispose()
