# 321000300 Field 1-B (Shadowvale) - Zero's tutorial, scene 2: the Darklings (clear_321000300 continues)
if sm.getFieldID() == 321000300 and not sm.hasMobsInField():
    for x in (-700, -400, -100, 200, 500, 800, 1100, 1400, 1700):
        sm.spawnMob(9300744, x, -6, False)
    sm.chatScript("Defeat the Darklings in Field 1-B.")
