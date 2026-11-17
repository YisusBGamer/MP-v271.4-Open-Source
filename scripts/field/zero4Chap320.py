# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). 325090320 Rocky Mountain Lot - Surl Knows All (40407): the Shadow Knights' camp
if sm.hasQuest(40407) and not sm.isComplete(40407) and not sm.hasMobsInField():
    sm.chatScript("A Shadow Knight camp! One of them must have the report.")
    for x in (-2300, -2000, -1700, -1400):
        sm.spawnMob(9300792, x, 28, False)
