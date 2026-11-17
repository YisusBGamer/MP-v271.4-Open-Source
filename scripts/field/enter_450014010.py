# Subway Tunnel Entrance (450014010): [Reverse City] The Most Normal Person (37602).
# Entering Reverse City for the first time: T-Boy ambushes you and Rasol brings you to the refuge.
if sm.hasQuest(37602) and sm.getQRValue(37602, "dummy") != "1":
    sm.setQRValue(37602, "dummy", "1")
    sm.setSpeakerID(3004650) # T-Boy
    sm.sendNext("Hey, hey! Another outsider? You picked the wrong city to visit!")
    sm.setPlayerAsSpeaker()
    sm.sendSay("(Your body lifts off the ground and slams into the floor. Everything goes dark...)")
    sm.setSpeakerID(3004602) # Rasol
    sm.sendSay("Hang in there! I'll get you somewhere safe...")
    sm.warp(450014050, 0)
