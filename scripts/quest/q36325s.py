# 36325 Finding A New Task (level 60, after any Explorer 2nd job). Starts the 3rd job story: ask Camila in Henesys.
sm.setPlayerAsSpeaker()
sm.sendNext("#b(You've done enough training for now, but how do you take the next step?)#k")
if not sm.sendAskAccept("#b(Maybe someone in town needs help. Ask #eCamila#n in #m100000000#?)#k"):
    sm.dispose()
sm.startQuest(parentID)
sm.chatScript("Ask Camila, a resident of Henesys, if she needs help.")
sm.dispose()
