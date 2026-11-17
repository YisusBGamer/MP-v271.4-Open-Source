# Wooden carving (reactor 1209003), Muirhat's Room: a few hits break loose the Ominous Wooden Relic for
# "Mysterious Discovery" (1500).
QUEST = 1500
RELIC = 4033087

reactor.incHitCount()
if reactor.getHitCount() >= 3:
    if sm.hasQuest(QUEST) and not sm.hasItem(RELIC):
        sm.dropItem(RELIC, sm.getPosition(objectID).getX(), sm.getPosition(objectID).getY())
    sm.removeReactor()
sm.dispose()
