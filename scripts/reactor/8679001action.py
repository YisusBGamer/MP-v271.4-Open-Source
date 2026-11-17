# Goo Ruins stage 2 dial PW2 (reactor 8679001, no Reactor.wz action, so the server runs this name): each click turns
# it one step (0-8); the three dials together must show the numbers of red, yellow and blue marks.
from net.swordie.ms.world.field.instance.instancehelper.pq import GooExplorationInstanceHelper

instance = chr.getInstance()
helper = instance.getInstanceHelper() if instance is not None else None
if isinstance(helper, GooExplorationInstanceHelper) and reactor is not None and reactor.getState() != 9:
    newState = reactor.getState() + 1 if reactor.getState() < 8 else 0
    reactor.setState(newState)
    reactor.broadcastChangeState()
    helper.onStateChanged(chr, 2, newState)
sm.dispose()
