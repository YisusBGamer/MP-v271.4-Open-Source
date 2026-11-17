# Tristan's Keepsake (2635), start - Mu Young (Dual Blade story). The client has no start lines; written from the
# quest's own Quest.wz summary ("Mu Young tells you to get Tristan's Keepsake from the Storage Room yourself."). His
# official lines when you bring it are the quest's end (Say.img).
MU_YOUNG = 1061014
if not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(MU_YOUNG)
    if sm.hasQuest(parentID) or sm.sendAskAccept("My master's keepsake? It's in the Storage Room. Go and get it yourself - I'm busy."):
        if not sm.hasQuest(parentID):
            sm.startQuest(parentID)
        sm.warp(910510700, 0)  # Hidden Street: Mu Young's Storage
sm.dispose()
