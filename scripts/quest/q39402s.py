# id 39402 (First Malice), Toolen City East Side 1 (410000416), the Regulator (3004952)
# Kain's tutorial. Quest.wz names this start script; the quest waits for its "Use the Malice Stone" record
# (dummy=clear). The game data has no lines for it. The Regulator hands over the Malice Stone, it's used, and the
# quest finishes; a Kain on the beginner job gets the 1st job and Lv 10 with it (39495).
execfile("scripts/quest/_kain_tutorial.py")

if chr.getJob() == KAIN_BEGINNER:
    first_malice()
else:
    sm.startQuest(parentID)
    sm.setQRValue(FIRST_MALICE, "dummy", "clear")
    sm.chat("You received the Malice Stone from the Regulator and used it. Your supply of Malice has been replenished.")
    sm.completeQuest(FIRST_MALICE)
sm.dispose()
