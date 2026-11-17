# 927030070 Black Mage's Temple: the Master Omen is beaten (The Broken Seal, Shade 38068, record 38068 = clear).
if sm.hasQuest(38068) and sm.getQRValue(38068) != "clear":
    sm.setQRValue(38068, "clear")
    sm.chatScript("Go see the Seal Stone of Time.")
