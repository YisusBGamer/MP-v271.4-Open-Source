# 260010601
if chr.getJob() == 2410 and chr.getLevel() >= 60:
    sm.warpInstanceIn(915020100, 1)
elif 2500 <= chr.getJob() <= 2512 and sm.hasQuest(38052):
    # Shade: Phantom's Other Hideaway - the lock is still in place (field/map915020100)
    sm.warpInstanceIn(915020100, 1)
else:
    sm.chat("Only Phantoms looking for a certain kind of treasure can enter.")
