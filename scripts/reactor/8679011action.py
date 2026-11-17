# Goo Ruins stage 3 switch (reactor 8679011, no Reactor.wz action, so the server runs this name): the nine switches
# must be pressed in order; a wrong one resets them all.
from net.swordie.ms.world.field.instance.instancehelper.pq import GooExplorationInstanceHelper

instance = chr.getInstance()
helper = instance.getInstanceHelper() if instance is not None else None
if isinstance(helper, GooExplorationInstanceHelper) and reactor is not None:
    if helper.OnStage3ReactorPressed(chr, reactor.getTemplateId()):
        reactor.setState(1)
        reactor.broadcastChangeState()
sm.dispose()
