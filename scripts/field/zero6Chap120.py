# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). 327090120 Korean Folk Town (the past) - Tower of the Past (40601): Time Cubes hold the town's time flow
if sm.hasQuest(40601) and not sm.isComplete(40601) and not sm.hasMobsInField():
    sm.chatScript("Time Cubes! Defeat them to capture the town's time flow.")
    for i in range(14):
        sm.spawnMob(9300795, -3000 + i * 450, 333, False)
