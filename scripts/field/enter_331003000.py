# id 331003000 (Subway : Subway Car #1) - A Hero's Duty 1 (22731) starts with the stranded passenger (1531064)
if sm.hasQuestCompleted(22730) and not sm.hasQuestCompleted(22731):
    sm.spawnNpcIfAbsent(1531064, 300, 63)
    sm.showNpcSpecialActionByTemplateId(1531064, "summon", 0)
sm.playSound("Sound/Field.img/flowervioleta/wink", 100)
sm.lockInGameUI(False, True)
