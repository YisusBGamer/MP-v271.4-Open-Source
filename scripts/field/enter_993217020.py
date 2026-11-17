# Somewhere in the Wellspring (993217020), during The Wellspring (38318): the flame turns out to be the soul of a
# past Adversary; beating the First Adversary ends the quest's battle (step 5), then back to the Wellspring
FIRST_ADVERSARY = 8881820
QUEST = 38318
WELLSPRING = 410007018

if chr.getInstance() is not None and sm.hasQuest(QUEST) and sm.getQRValue(QUEST, "step") != "5":
    if not sm.hasMobById(FIRST_ADVERSARY):
        sm.spawnMob(FIRST_ADVERSARY, 0, 141, False)
    sm.chatScript("The flame takes the shape of a past Adversary. Defeat the First Adversary!")
    sm.waitForMobDeath(FIRST_ADVERSARY)
    sm.setQRValue(QUEST, "step", "5")
    sm.chatScript("The flame was the soul of a nameless Adversary. Return to Hundun at the Wellspring.")
    sm.invokeAfterDelay(3000, "warpInstanceOut", WELLSPRING, 0)
elif chr.getInstance() is not None:
    sm.warpInstanceOut(WELLSPRING, 0)
sm.dispose()
