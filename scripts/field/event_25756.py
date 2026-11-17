# 940000160 South Pantheon Border - Start the Counterattack (25756, Kaiser): the remaining Elite Specters at the
# southern boundary. q25756s sends you here; once they're beaten, field/clear_940000160.py moves you on to Magnus's
# appearance (940000170). The map has no exit portal.
if sm.hasQuest(25756) and sm.getQRValue(25801) != "1":
    if not sm.hasMobsInField():
        for mob, x in ((2400018, 1250), (2400019, 1380), (2400020, 1510), (2400018, 1640), (2400019, 2100),
                       (2400020, 2230), (2400018, 2360), (2400019, 2490), (2400020, 2620), (2400018, 2690)):
            sm.spawnMob(mob, x, 29, False)
        sm.chatScript("Defeat all the remaining Elite Specters at the southern boundary!")
else:
    sm.warpInstanceOut(400000000, 0)  # Pantheon
sm.dispose()
