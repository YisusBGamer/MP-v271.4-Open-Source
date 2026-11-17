# 931050970 Classified Lab Corridor - Xenon's tutorial, scene 3: the escape (clear_931050970 continues)
if sm.getFieldID() == 931050970 and not sm.hasMobsInField():
    for x in (-1200, -900, -600, -300, 0, 300):
        sm.spawnMob(9390203, x, 32, False)
    sm.chatScript("Security robots! Break through the corridor.")
