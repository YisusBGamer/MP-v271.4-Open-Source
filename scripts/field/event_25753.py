# 940000150 South Pantheon Border - Buying Time (25753, Kaiser): hold the south until the shield is restored.
# q25753s sends you here; field/clear_940000150.py sets record 25800. The map has no exit portal.
if sm.hasQuest(25753) and sm.getQRValue(25800) != "1":
    if not sm.hasMobsInField():
        for mob, x in ((2400015, 1250), (2400016, 1380), (2400017, 1510), (2400018, 1640), (2400015, 2050),
                       (2400016, 2180), (2400017, 2310), (2400018, 2440), (2400016, 2570), (2400017, 2680)):
            sm.spawnMob(mob, x, 29, False)
        sm.chatScript("Hold the line until the Protective Shield is restored! Defeat all Specters on the battlefield!")
else:
    sm.warpInstanceOut(400000000, 0)  # Pantheon
sm.dispose()
