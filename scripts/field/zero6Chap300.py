# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). 327090300 Zero Shrine - The Tears of Time (40606): something answers the Teardrops' power
if sm.hasQuest(40606) and sm.getQRValue(40615) != "1" and not sm.hasMobsInField():
    sm.chatScript("Something strange has come through... Defeat it!")
    sm.spawnMob(9300798, 360, 5, False)
