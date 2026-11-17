# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). 323090130 Larhen's House - Larelle's Diary (40203): the creature Larelle summoned
if sm.hasQuest(40203) and sm.getQRValue(40212) != "1" and not sm.hasMobsInField():
    sm.chatScript("Monsters warped by the power of time! Drive them out.")
    sm.spawnMob(2600211, -300, 140, False)
    sm.spawnMob(2600212, 0, 140, False)
    sm.spawnMob(2600213, 300, 140, False)
