# 993070078: the story fight is won (35846; 35809, 35810 before)
if sm.hasQuest(35846) and sm.getQRValue(35846, "rescued") != "1":
    sm.setQRValue(35846, "rescued", "1")
    sm.chatScript("The soldiers are safe. Report back to the Resistance Soldier in End of the World 2-2.")
    sm.warpInstanceOut(450012410, 0)
elif sm.hasQuest(35809) and sm.getQRValue(35834, "q35809") != "1":
    sm.setQRValue(35834, "q35809", "1")
    sm.chatScript("Done here. Report back to the Resistance Soldier in End of the World 2-2.")
    sm.warpInstanceOut(450012410, 0)
elif sm.hasQuest(35810) and sm.getQRValue(35834, "q35810") != "1":
    sm.setQRValue(35834, "q35810", "1")
    sm.chatScript("Done here. Report back to the Resistance Soldier in End of the World 2-2.")
    sm.warpInstanceOut(450012410, 0)
