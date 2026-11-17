# 200020001
if 2400 == chr.getJob(): #Phantom 1st job
    sm.warpInstanceIn(915010000, 1)
    sm.setQRValue(25102, "1", False)
elif 2410 <= chr.getJob() <= 2411:
    sm.warp(915020000, 2)
elif 2500 <= chr.getJob() <= 2512 and sm.hasQuest(38051):
    # Shade: Phantom's Hideaway - the storage room's lock is already broken
    sm.warpInstanceIn(915020001, 0)
else:
    sm.chat("Only Phantoms can enter.")
sm.dispose()
