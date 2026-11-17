# 940000140 East Pantheon Border - Protective Shield Patrol (25749, Kaiser): Magnus's preliminary attack with trained
# animals (QuestInfo 2). portal/enter_eastborder.py sends you here; field/clear_940000140.py sets record 25796. The
# west00 portal leads back to the East Sanctum as usual.
if sm.hasQuest(25749) and sm.getQRValue(25796) != "1" and not sm.hasMobsInField():
    for x in (1050, 1150, 1250, 1350, 1450, 1550, 1650, 1800):
        sm.spawnMob(2400015, x, 29, False)  # Faded Specter Battle Hound
    sm.chatScript("Specter Battle Hounds are attacking the Protective Shield! Drive them off!")
sm.dispose()
