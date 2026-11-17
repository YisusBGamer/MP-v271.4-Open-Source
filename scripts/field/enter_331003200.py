# id 331003200 (Subway : Subway Car #3) - A Hero's Duty 2 (22732): more Motes, and the passenger it ends with (1531067)
MOTES = (2700303, 2700304, 2700305)
sm.lockInGameUI(False, True)
if sm.hasQuest(22732) and not sm.hasMobsInField():
    for i, x in enumerate((-300, -200, -100, 0, 100, 200, 300, 400, 500, 600, 700, 800, -250, 250, 750)):
        sm.spawnMob(MOTES[i % 3], x, 63, False)
if sm.hasQuest(22732) or sm.hasQuestCompleted(22732) and not sm.hasQuestCompleted(22733):
    sm.spawnNpcIfAbsent(1531067, 850, 63)
