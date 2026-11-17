# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). 323090130 Larhen's House: the monsters are gone (Larelle's Diary, 40203)
if sm.hasQuest(40203) and sm.getQRValue(40212) != "1":
    sm.setQRValue(40212, "1")
    sm.chatScript("The house is quiet again. Larelle's journal lies open on the desk.")
