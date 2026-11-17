# 36322 Battle Against the Black Shadow: the fight is on Burning Maple Tree Hill 4 (993166023, own copy;
# field/enter_993166023.py). Retry by clicking the Black Shadow on Maple Tree Hill (npc/talk_1541084.py).
sm.setPlayerAsSpeaker()
if not sm.sendAskAccept("#b(The Black Shadow looms over the Maple Tree. Defeat it?)#k"):
    sm.dispose()
if not sm.hasQuest(parentID):
    sm.startQuest(parentID)
sm.setQRValue(36300, "84", "h1")  # the Black Shadow on Maple Tree Hill (retry)
sm.warpInstanceIn(993166023, 0)
sm.dispose()
