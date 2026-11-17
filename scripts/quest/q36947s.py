# 36947 Find the Key - start (Loha, Ellin Forest). The Primitive Thief Boars that took the Wooden Keys live in
# the Dangerous Rocky Mountain (930010001), which only this quest leads to.
ROCKY_MOUNTAIN = 930010001

if sm.questStartDialogue(parentID, 2131003):
    if sm.sendAskYesNo("#b(Go after the #o9001024#s in the #m930010001# now?)#k"):
        sm.warpInstanceIn(ROCKY_MOUNTAIN, 0)
        sm.setInstanceTime(1200, 300000000)
sm.dispose()
