# 910090100 (Hidden Street : Forest Edge) - Explorer story: the Masked Rabbit (32267), then the fake Olive (32271)
if sm.hasQuest(32267) and not sm.isComplete(32267) and not sm.hasMobsInField():
    sm.spawnMob(9300803, 2300, -25, False)
    sm.chatScript("The rabbit in the black mask is here!")
if sm.hasQuest(32271):
    sm.spawnNpcIfAbsent(1520019, 2500, -25)
