# 931000620 Mine Entrance: Vita (2159347) is here during Vita's Disappearance (Resistance 23143).
if sm.hasQuest(23143) and sm.getQRValue(23149) != "1":
    sm.spawnNpcIfAbsent(2159347, 1200, 28)
    sm.chatScript("Find Vita and bring her back to Edelstein.")
