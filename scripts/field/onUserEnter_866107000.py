# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Wolf Woods (866107000): the wolves of Wolf Fight (59005), if they aren't beaten yet.
WOLF = 9390931
def spawn_wolves():
    for x in (520, 640, 760, 880, 1000):
        sm.spawnMob(WOLF, x, 60, False)

if sm.hasQuest(59005) and "mob=5" not in (sm.getQRValue(59005) or "") and not sm.hasMobsInField(sm.getFieldID(), WOLF):
    spawn_wolves()
