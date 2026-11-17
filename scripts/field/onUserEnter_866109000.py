# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Stump Town (866109000): back in town, and kobolds are everywhere (A Hero's Return to Town, 59008, counts 5).
KOBOLD = 9390927
def spawn(mob, count, x0):
    for i in range(count):
        sm.spawnMob(mob, x0 + 120 * i, 28, False)

if sm.hasQuest(59008) and "mob=5" not in (sm.getQRValue(59008) or ""):
    sm.chatScript("You made it back to town, but... what in Maple happened while you were gone?")
    if not sm.hasMobsInField(sm.getFieldID(), KOBOLD):
        spawn(KOBOLD, 5, 300)
