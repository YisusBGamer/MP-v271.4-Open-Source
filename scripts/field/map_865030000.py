# id 865030000 (Commerci Republic : Suspicious Forest Entrance), field 865030000
sm.startQuest(17930)
sm.playSound("Sound/FarmSE.img/boxResult", 100)
sm.createQuestWithQRValue(18418, "B=33263")
sm.startQuest(16689)


def spawn_line(mob, count, x1, x2, y):
    """spreads count monsters evenly between x1 and x2 on the ground at y"""
    for i in range(count):
        x = x1 + (x2 - x1) * (i + 1) // (count + 1)
        sm.spawnMob(mob, x, y, False)

# Coming back during Clairenapped (17660), the assassins are still here (6 + 6).
if sm.hasQuest(17660) and not sm.hasMobsInField(sm.getFieldID(), 9390833) and not sm.hasMobsInField(sm.getFieldID(), 9390834):
    spawn_line(9390833, 6, -700, 1000, -270)
    spawn_line(9390834, 6, -600, 1000, -270)
