# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). 327090210 Omega Sector: the Time Cubes are gone (Tower of the Future, 40602)
if sm.hasQuest(40602) and sm.getQRValue(40614) != "1":
    sm.setQRValue(40614, "1")
    sm.chatScript("Time has stopped in the future too. Return to Mera.")
