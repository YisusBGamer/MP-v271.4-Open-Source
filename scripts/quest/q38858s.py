# 38858 [Heliseum] The Final Battle - start (Edea, Downtown Black Market). The throne room is open: the fight with the
# story Magnus is in a private copy of the Tyrant's Throne (993167509, field/enter_993167509). Handed in to Edea.
THRONE = 993167509

if sm.questStartDialogue(parentID, 3001100):
    if sm.sendAskYesNo("#b(Go to the Tyrant's Throne and face Magnus now?)#k"):
        sm.warpInstanceIn(THRONE, 0)
        sm.setInstanceTime(1800, 401040000)
sm.dispose()
