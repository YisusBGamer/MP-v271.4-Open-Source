# Secret Document (reactor 2212004), the Omega Sector Silo: breaking one gives a Secret Document for
# "Trading with Alien Gray" (3419: 3 of them).
QUEST = 3419
DOCUMENT = 4031116

reactor.incHitCount()
if reactor.getHitCount() >= 4:
    if sm.hasQuest(QUEST) and not sm.hasItem(DOCUMENT, 3):
        sm.dropItem(DOCUMENT, sm.getPosition(objectID).getX(), sm.getPosition(objectID).getY())
    sm.removeReactor()
sm.dispose()
