# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). 325090030 Streetlight Row - Finding the Culprit (40401): Jaguars on the prowl
if sm.hasQuest(40401) and not sm.isComplete(40401) and not sm.hasMobsInField():
    for i in range(15):
        sm.spawnMob(9300769, -2100 + i * 130, -14, False)
