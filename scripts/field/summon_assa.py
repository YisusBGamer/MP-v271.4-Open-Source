# id 865030121 (Commerci Republic : Deadly Trap), field 865030121
if not sm.hasQuest(17666) and not sm.hasQuestCompleted(17666):  # don't restart it (that would reset the kills)
    sm.startQuest(17666)


def spawn_line(mob, count, x1, x2, y):
    """spreads count monsters evenly between x1 and x2 on the ground at y"""
    for i in range(count):
        x = x1 + (x2 - x1) * (i + 1) // (count + 1)
        sm.spawnMob(mob, x, y, False)

# Il Capo's deadly trap - he and his assassins attack for A Deadly Game (17666: 3 + 4 + Il Capo).
if sm.hasQuest(17666) and not sm.hasMobsInField(sm.getFieldID(), 9390822):
    spawn_line(9390833, 3, -700, 800, 90)
    spawn_line(9390834, 4, -650, 780, 90)
    sm.spawnMob(9390822, 300, 90, False)
