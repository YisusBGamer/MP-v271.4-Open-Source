# Toy Factory boxes (2202002, Ludibrium Toy Factory and the Secret Factory)
# 'The Missing Mechanical Parts' (3239) asks for 10 Machine Parts (4031092) from these boxes. The part drops only
# while the quest still needs it.
QUEST = 3239
PARTS = 4031092

if sm.hasQuest(QUEST) and not sm.hasItem(PARTS, 10):
    pos = sm.getPosition(objectID)
    sm.dropItem(PARTS, pos.getX(), pos.getY())
sm.removeReactor()
sm.dispose()
