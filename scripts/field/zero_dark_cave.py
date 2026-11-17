# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). 323090220 Dark Cave Interior (see CustomFieldScripts) - Unexpected Encounter (40207), The Rescue (40209)
if sm.hasQuest(40207) and sm.getQRValue(40213) != "1":
    sm.setQRValue(40213, "1")
    sm.questEndDialogue(40207)  # Shadow Knights! Alpha and Beta flee
    sm.warpInstanceOut(323000000, 10)
elif sm.hasQuest(40209) and not sm.isComplete(40209) and not sm.hasMobsInField():
    sm.chatScript("Lyra and the Shadow Knights! Get Larelle out of here!")
    sm.spawnMob(9300764, 1000, 699, False)
    for x in (700, 850, 1100, 1300, 1450):
        sm.spawnMob(9300765, x, 699, False)
