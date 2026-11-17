# 865030101 (Commerci Republic : Assassins's Den Entrance). Coming back during Assassins's Den (17663), the
# gatekeepers (8 Masked Mysteriosos) are still here.
def spawn_line(mob, count, x1, x2, y):
    """spreads count monsters evenly between x1 and x2 on the ground at y"""
    for i in range(count):
        x = x1 + (x2 - x1) * (i + 1) // (count + 1)
        sm.spawnMob(mob, x, y, False)
if sm.hasQuest(17663) and not sm.hasMobsInField(sm.getFieldID(), 9390834):
    spawn_line(9390834, 8, -950, 580, 90)
