# 36325 Finding A New Task - end (Camila, Henesys). She points the player to the Six Path Crossway (36326).
sm.setSpeakerID(1012108)
sm.sendNext("Looking for something to do? Hmm... I don't have anything for someone as strong as you.")
sm.sendSay("Why don't you try the #b#m104020000##k? People are always coming and going there. Someone is bound to need a hand.")
if sm.completeQuest(parentID):
    if not sm.hasQuest(36326) and not sm.hasQuestCompleted(36326):
        sm.startQuest(36326)
    sm.chatScript("Go to the Six Path Crossway, as Camila suggested.")
sm.dispose()
