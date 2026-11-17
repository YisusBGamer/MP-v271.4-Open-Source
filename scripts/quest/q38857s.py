# 38857 [Heliseum] Velderoth the Traitor - start (Edea, Downtown Black Market). Velderoth waits in the middle of the
# castle's 3rd floor, in a private copy of it (993167660, field/enter_993167660). Handed in to Edea.
THIRD_FLOOR = 993167660

if sm.questStartDialogue(parentID, 3001100):
    if sm.sendAskYesNo("#b(Go to the 3rd floor of the Tyrant's Castle and face Velderoth now?)#k"):
        sm.warpInstanceIn(THIRD_FLOOR, 0)
        sm.setInstanceTime(1800, 401040000)
sm.dispose()
