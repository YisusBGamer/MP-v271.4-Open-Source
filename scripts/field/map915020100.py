# 915020100 Ariant Treasure Vault Entrance (Phantom 3rd job): reaching it is what "The Poor, the Rich, and the Phantom"
# (25110) needs (record 25112). Dust Dwarves (9001046) are inside for "The Lowdown" (25111).
DUST_DWARVE = 9001046
if 2500 <= chr.getJob() <= 2512 and sm.hasQuest(38052) and not sm.hasMobsInField():
    sm.spawnMob(9001045, 170, 182, False)  # Shade: Phantom's Other Hideaway - the lock is still in place
    sm.chat("The lock is still in place. Breaking it is the only way in.")
if chr.getJob() == 2410:
    if sm.hasQuest(25110) and sm.getQRValue(25112) != "1":
        sm.setQRValue(25112, "1", False)
        sm.chat("You found your Ariant vault. Complete 'The Poor, the Rich, and the Phantom' in your quest window, then accept 'The Lowdown'.")
    if sm.hasQuest(25111) and not sm.hasMobsInField():
        for x in (-290, -50, 190, 430, 600):
            sm.spawnMob(DUST_DWARVE, x, 182, False)
        sm.setQRValue(25111, "spawn", False) # this visit's Dust Dwarves are out (map915020100_PT)
