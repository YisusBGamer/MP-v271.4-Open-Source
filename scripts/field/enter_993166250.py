# Specter Battleground (993166250), own copy: The Fox-Eared People (36328). Breaking through the map's own 12 Specters
# (Map.wz life, on the floor footholds) sets the quest's kill flag; then back to Pantheon to talk to Rondo.
QUEST = 36328
KILLS = 12

if chr.getInstance() is not None and sm.hasQuest(QUEST) and sm.getQRValue(QUEST, "kill") != "1":
    sm.chatScript("Break through the Specters at the edge of the barrier!")
    for i in range(KILLS):
        sm.waitForMobDeath()
    sm.setQRValue(QUEST, "kill", "1")
    sm.chatScript("The way is clear, but the barrier won't let you go any further. Go back to Rondo in Pantheon.")
    sm.invokeAfterDelay(3000, "warpInstanceOut", 400000000, 0)
