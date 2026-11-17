# Hawkeye (1530107), the squid in Cygnus's Office during Chapter 5. Hidden Story: Not A Squid (33050).
QUEST = 33050

if sm.hasQuest(QUEST) and sm.getQRValue(QUEST, "ok") != "1":
    sm.setQRValue(QUEST, "ok", "1")
    sm.chatScript("Hawkeye didn't like being a squid alone... But his feelings might be even more hurt now. Whoops.")
else:
    line = sm.getRandomNpcDefaultLine(parentID)
    if line is not None:
        sm.setSpeakerID(parentID)
        sm.sendSayOkay(line)
