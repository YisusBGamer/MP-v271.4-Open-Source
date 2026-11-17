# Burning Maple Tree Hill (993166200), own copy: Sugar in Danger (36319). Beating the Black Snails sets the quest's
# flag (key "1" = "kill", as Quest.wz has it) and finishes it; then Maple Tree Hill (110000), where Sugar starts 36320.
BLACK_SNAIL = 8645264
QUEST = 36319
SPOTS = ((150, 425), (300, 425), (450, 425), (600, 425), (750, 425), (-300, 245), (-550, 245), (-800, 245))

if chr.getInstance() is not None and sm.hasQuest(QUEST) and sm.getQRValue(QUEST, "1") != "kill":
    if not sm.hasMobById(BLACK_SNAIL):
        for x, y in SPOTS:
            sm.spawnMob(BLACK_SNAIL, x, y, False)
    sm.chatScript("Break through the Black Snails and find Sugar!")
    while sm.hasMobById(BLACK_SNAIL):
        sm.waitForMobDeath(BLACK_SNAIL)
    sm.setQRValue(QUEST, "1", "kill")
    sm.completeQuest(QUEST)
    sm.chatScript("You find Sugar lying under the burning Maple Tree.")
    sm.invokeAfterDelay(3000, "warpInstanceOut", 110000, 0)
