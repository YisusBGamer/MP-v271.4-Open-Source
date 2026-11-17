# 36323 The Miracle of the Maple Leaf: use the Seal Stone at the Maple Tree (Burning Maple Tree Hill 3, 993166043, own
# copy; npc/Mapletree_click.py). Started after the Black Shadow fight too.
sm.setPlayerAsSpeaker()
sm.sendNext("#b(The Black Shadow is gone, but Sugar isn't moving, and the Maple Tree is still burning.)#k")
if not sm.sendAskAccept("#b(The voice said a heart willing to protect others is the key. Go to the Maple Tree and use the Seal Stone?)#k"):
    sm.dispose()
if not sm.hasQuest(parentID):
    sm.startQuest(parentID)
sm.warpInstanceIn(993166043, 0)
sm.dispose()
