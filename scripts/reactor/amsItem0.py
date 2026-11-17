# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Bell Tree (reactor 8662001), Wolf Woods: a few hits shake Arby's bell loose (Save the Cat 2, 59004).
BELL = 4034005
reactor.incHitCount()
if reactor.getHitCount() >= 3:
    if sm.hasQuest(59004) and not sm.hasItem(BELL):
        sm.dropItem(BELL, sm.getPosition(objectID).getX(), sm.getPosition(objectID).getY())
    sm.removeReactor()
sm.dispose()
