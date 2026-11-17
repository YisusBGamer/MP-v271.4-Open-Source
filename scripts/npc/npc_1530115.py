# Oz (1530115), injured in the School Infirmary during Chapter 4. Hidden Story: Comfort Food (33049).
QUEST = 33049

if sm.hasQuest(QUEST) and sm.getQRValue(QUEST, "ok") != "1":
    sm.setQRValue(QUEST, "ok", "1")
    sm.chatScript("Everyone has their own little pleasure. Oz's just happens to be eating...")
else:
    line = sm.getRandomNpcDefaultLine(parentID)
    if line is not None:
        sm.setSpeakerID(parentID)
        sm.sendSayOkay(line)
