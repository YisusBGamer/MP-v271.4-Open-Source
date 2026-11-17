# Blue Mushmom Forest (993166100), own copy: Olive's Special Training (36315). Beating the Blue Mushmom sets the
# quest's kill flag and finishes it, then back to the Bowman Instructional School.
BLUE_MUSHMOM = 8645261
QUEST = 36315

if chr.getInstance() is not None and sm.hasQuest(QUEST) and sm.getQRValue(QUEST, "kill") != "1":
    if not sm.hasMobById(BLUE_MUSHMOM):
        sm.spawnMob(BLUE_MUSHMOM, 500, -625, False)
    sm.chatScript("Defeat the Blue Mushmom!")
    sm.waitForMobDeath(BLUE_MUSHMOM)
    sm.setQRValue(QUEST, "kill", "1")
    sm.completeQuest(QUEST)
    sm.chatScript("You beat the Blue Mushmom. Olive grudgingly admits you've gotten stronger.")
    sm.invokeAfterDelay(3000, "warpInstanceOut", 100000201, 0)
