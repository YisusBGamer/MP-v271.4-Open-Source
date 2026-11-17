# id 331003300 (Subway : Subway Car #4) - A Pale Threat (22733): the three Troublemakers; Jay (1531001) is here on the
# comms to hand the quest in to. What follows is in quest/done_22733.py.
sm.lockInGameUI(False, True)
if sm.hasQuest(22733):
    if not sm.hasMobsInField():
        for mob, x in ((2700306, 0), (2700307, 300), (2700308, 600)):
            sm.spawnMob(mob, x, 63, False)
    sm.spawnNpcIfAbsent(1531001, -300, 63)
    sm.playSound("Sound/Field.img/flowervioleta/wink", 100)
