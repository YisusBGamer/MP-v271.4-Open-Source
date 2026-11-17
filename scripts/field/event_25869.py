# 940010070 South Pantheon Border - The Perfect Trap (25869, Angelic Buster), part 1: hold off the Specters while the
# shields are down (QuestInfo 1). q25869s sends you here; once they're beaten, field/clear_940010070.py sends you on to
# South Border Battleground 2 (part 2). The map has no exit portal.
if sm.hasQuest(25869) and sm.getQRValue(25801) != "1":
    if not sm.hasMobsInField():
        for mob, x in ((2400016, 1250), (2400017, 1380), (2400018, 1510), (2400016, 1640), (2400017, 2050),
                       (2400018, 2180), (2400016, 2310), (2400017, 2440), (2400018, 2570), (2400016, 2680)):
            sm.spawnMob(mob, x, 29, False)
        sm.chatScript("The shields are down! Take care of the Specters until they're back on!")
else:
    sm.warpInstanceOut(400000000, 0)  # Pantheon
sm.dispose()
