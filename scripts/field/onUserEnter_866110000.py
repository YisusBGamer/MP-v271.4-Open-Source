# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Stump Town (866110000), with Lai: the kobolds of Snow Leopard Guardian (59009) and Snow Leopard Powah (59013),
# if they aren't beaten yet.
def spawn(mob, count, x0):
    for i in range(count):
        sm.spawnMob(mob, x0 + 120 * i, 28, False)

if sm.hasQuest(59009) and not sm.hasMobsInField(sm.getFieldID(), 9390927):
    spawn(9390927, 5, 300)
elif sm.hasQuest(59013) and not sm.hasMobsInField(sm.getFieldID(), 9390928):
    spawn(9390928, 5, 300)
