# 915020100 -> 915020101 Ariant Treasure Vault ("The Lowdown", 25111)
DUST_DWARVE = 9001046
if 2500 <= chr.getJob() <= 2512:
    # Shade: Phantom's Other Hideaway (38052) - break the lock, then in
    if sm.hasQuest(38052) and not sm.hasMobsInField():
        sm.warp(915020101, 1)
    elif sm.hasQuest(38052):
        sm.chat("Break the lock first.")
    else:
        sm.chat("There's nothing more to find in there.")
elif chr.getJob() != 2410 or not sm.hasHadQuest(25111):
    if sm.hasQuest(25110):
        sm.chat("Complete 'The Poor, the Rich, and the Phantom' in your quest window and accept 'The Lowdown' first.")
    else:
        sm.chat("I think I've done enough digging around in there.")
elif sm.hasQuestCompleted(25111):
    sm.chat("I think I've done enough digging around in there.")
elif sm.getQRValue(25111) != "spawn":
    # The Lowdown was accepted after arriving here
    for x in (-290, -50, 190, 430, 600):
        sm.spawnMob(DUST_DWARVE, x, 182, False)
    sm.setQRValue(25111, "spawn", False)
    sm.chat("Dust Dwarves! Wipe them out!")
elif sm.hasMobsInField():
    sm.chat("Wipe out the Dust Dwarves first!")
else:
    sm.warp(915020101, 1)
sm.dispose()
