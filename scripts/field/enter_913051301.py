# Sorcerer's Refinement Chamber (913051301), own copy: [Twilight Perion] Magic Refinement 2 (31963). Clearing the Eyes
# of Oblivion (7) refines the fog: the quest's record 31966 gets yes=fog; then back to the Sorcerer.
QUEST = 31963
EYES = 7

if chr.getInstance() is not None and sm.hasQuest(QUEST) and sm.getQRValue(31966, "yes") != "fog":
    sm.chatScript("Defeat the Eyes of Oblivion so the Sorcerer can refine the fog!")
    for i in range(EYES):
        sm.waitForMobDeath()
    sm.setQRValue(31966, "yes", "fog")
    sm.chatScript("The Fog of Oblivion has been refined into Oblivion Essence. Report to the Sorcerer.")
    sm.invokeAfterDelay(3000, "warpInstanceOut", 270020211, 0)
