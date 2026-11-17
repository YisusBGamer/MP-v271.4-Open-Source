# 450012410 End of the World 2-2, upper right portal: the Resistance ship's outer decks for Light of Annihilation
# (35846; 35809 and 35810 before) (993070078, field/enter_993070078).
if (sm.hasQuest(35846) and sm.getQRValue(35846, "rescued") != "1") or \
        (sm.hasQuest(35809) and sm.getQRValue(35834, "q35809") != "1") or \
        (sm.hasQuest(35810) and sm.getQRValue(35834, "q35810") != "1"):
    sm.warpInstanceIn(993070078, 0)
else:
    sm.chatScript("The Resistance ship's outer decks. Nothing to do there right now.")
