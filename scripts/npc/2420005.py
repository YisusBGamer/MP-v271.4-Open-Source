# Sirin (2420005) | [Mirror World] Ariant (322000000), dancer
# Ariant, the Desert Town (40100) is handed in to her once its record 40115 is "1" (intel gathered in Ariant).
# The quest's missing start script normally sets it on accept (quest/undefined.py); this covers a quest started
# any other way.
sm.setSpeakerID(parentID)
if sm.hasQuest(40100) and sm.getQRValue(40115) != "1":
    sm.sendSayOkay("Visitors are pouring in to Ariant. My business has never been better.")
    sm.setQRValue(40115, "1")
    sm.chatScript("Sirin knows what's going on in Ariant. Talk to her again.")
else:
    sm.sendSayOkay("Visitors are pouring in to Ariant. My business has never been better.")
