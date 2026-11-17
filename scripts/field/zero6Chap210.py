# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). 327090210 Omega Sector (the future) - Tower of the Future (40602): eliminate the Time Cubes
if sm.hasQuest(40602) and sm.getQRValue(40614) != "1" and not sm.hasMobsInField():
    sm.chatScript("Eliminate the Time Cubes!")
    for i in range(8):
        sm.spawnMob(9300796, 500 + i * 450, 158, False)
