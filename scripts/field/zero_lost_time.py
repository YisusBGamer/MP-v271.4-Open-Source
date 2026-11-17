# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). 328090310 Lost Time (see CustomFieldScripts) - The Right Choice (40704): Beta forces a fight
if sm.hasQuest(40704) and sm.getQRValue(40715) != "1" and not sm.hasMobsInField():
    sm.removeNpc(2480016)
    sm.chatScript("Beta attacks!")
    sm.spawnMob(9300808, 141, 164, False)
