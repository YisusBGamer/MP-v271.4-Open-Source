# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). 321190330 Horntail's Cave - Horntail? Horntail! (40055): the raging Horntail
if sm.hasQuestCompleted(40054) and not sm.hasQuestCompleted(40055):
    if not sm.hasQuest(40055):
        sm.startQuestNoCheck(40055)
    if not sm.isComplete(40055) and not sm.hasMobsInField():
        sm.chatScript("The dragon is enormous... and furious!")
        sm.spawnMob(9300749, 700, 260, False)
