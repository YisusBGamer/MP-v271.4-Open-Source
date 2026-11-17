# Subway Tunnel Refuge (450014050): [Reverse City] We Are Not Rats (37611) - back at the refuge.
if sm.hasQuest(37611) and sm.getQRValue(37611, "dir") != "1":
    sm.setQRValue(37611, "dir", "1")
    sm.chatScript("You made it back to the Subway Tunnel Refuge. Talk to Barr.")
