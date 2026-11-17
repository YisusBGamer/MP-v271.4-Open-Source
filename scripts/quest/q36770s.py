# [Labyrinth of Suffering] Traces of Despair (36770), started and finished at Azalin
AZALIN = 3003706

if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.setSpeakerID(AZALIN)
        if sm.sendAskAccept("(Azalin says she can't trust you anymore and will take the lead.)"):
            sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setQRValue(parentID, "dummy", "1")
        sm.setPlayerAsSpeaker()
        sm.sendNext("(You met the Alliance Members who fell after following Azalin. They seem to have lost their lives, "
                    "then transformed into monsters.)")
        sm.sendNext("(The monsters you defeated while wandering in this place could have been the Alliance Members.)")
        sm.completeQuest(parentID)
sm.dispose()
