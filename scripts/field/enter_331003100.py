# id 331003100 (Subway : Subway Car #2) - A Hero's Duty 1 (22731): the Motes, and the passenger it ends with (1531066)
MOTES = (2700303, 2700304, 2700305)
sm.lockInGameUI(False, True)
if sm.hasQuest(22731) and not sm.hasMobsInField():
    for i, x in enumerate((-250, -100, 50, 200, 350, 500, 650, 800, -175, 425)):
        sm.spawnMob(MOTES[i % 3], x, 63, False)
if sm.hasQuest(22731) or sm.hasQuestCompleted(22731) and not sm.hasQuestCompleted(22732):
    sm.spawnNpcIfAbsent(1531066, 850, 63)
