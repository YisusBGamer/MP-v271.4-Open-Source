# Balrog's chest (reactors 1052001-1052003) in Balrog's Disappearance Site (105100301): Reactor.wz gives it three
# hit states, so it opens on the third hit and spills Pieces of Balrog Leather (4001261) over the floor; the
# Suspicious Man (1061016) trades them for scrolls.
LEATHER = 4001261
HITS = 3

reactor.incHitCount()
if reactor.getHitCount() < HITS:
    reactor.increaseState()
    reactor.broadcastChangeState()
else:
    pos = sm.getPosition(objectID)
    sm.dropItemsAlongLine([LEATHER, LEATHER, LEATHER], 150, pos.getX(), pos.getY(), 250)
    sm.removeReactor()
sm.dispose()
