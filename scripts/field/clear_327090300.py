# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). 327090300 Zero Shrine: the creature is defeated (The Tears of Time, 40606)
if sm.hasQuest(40606) and sm.getQRValue(40615) != "1":
    sm.setQRValue(40615, "1")
    sm.chatScript("Return to Pietta.")
    sm.warpInstanceOut(320000000, 0)
