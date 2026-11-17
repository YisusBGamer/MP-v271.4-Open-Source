# 927030060 Black Mage's Antechamber: the Omens are gone (Traces of the Black Mage, Shade 38067, record 38067 = clear).
if sm.hasQuest(38067) and sm.getQRValue(38067) != "clear":
    sm.setQRValue(38067, "clear")
    sm.chatScript("You eliminated all the Omens blocking the Black Mage's Antechamber.")
