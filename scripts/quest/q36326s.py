# 36326 Rondo's Hat - start (from the quest list, after 36325). The hat is found in the bushes next to Rondo at the
# Six Path Crossway (npc/1541007.py), then handed back to him.
sm.setPlayerAsSpeaker()
if not sm.sendAskAccept("#b(Shall we go to the #m104020000#, as Camila suggested?)#k"):
    sm.dispose()
sm.startQuest(parentID)
sm.chatScript("Go to the Six Path Crossway.")
sm.dispose()
