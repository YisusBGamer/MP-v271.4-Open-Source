# 36319 Sugar in Danger: break through the Black Snails on Burning Maple Tree Hill (993166200, own copy;
# field/enter_993166200.py runs the fight). Forfeit and accept again to retry.
sm.setPlayerAsSpeaker()
sm.sendNext("#b(Maple Island is burning. The snails ahead are buzzing with dark energy. Sugar must be somewhere past them.)#k")
if not sm.sendAskAccept("#b(Break through the Black Snails and find Sugar?)#k"):
    sm.dispose()
if not sm.hasQuest(parentID):
    sm.startQuest(parentID)
sm.warpInstanceIn(993166200, 0)
sm.dispose()
