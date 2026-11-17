# Beast Tamer tutorial. The client has only a few of its lines (Say.img, marked 'official'); the rest follow the
# quests' Quest.wz summaries. The Express skip (field/q59000_tuto.py) still works.
# Wolf Woods (866104000): Arby's bell is stuck up a tree (Save the Cat 2, 59004): the Bell Tree (reactor 8662001).
BELL = 4034005
BELL_TREE = 8662001
if sm.hasQuest(59004) and not sm.hasItem(BELL) and not sm.hasReactors():
    sm.spawnReactor(BELL_TREE, 640, 70)
