# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). 328090310 Lost Time: the fight with Beta is over (The Right Choice, 40704)
if sm.hasQuest(40704) and sm.getQRValue(40715) != "1":
    sm.setQRValue(40715, "1")
    sm.spawnNpc(2480016, 141, 164)
    sm.chatScript("Talk to Beta.")
