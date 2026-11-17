# Escape party quest: a portal of the Dark Tower maze. One portal of each tower leads on, the rest back to Dark Tower 1.
from net.swordie.ms.world.field.instance.instancehelper.pq import EscapeInstanceHelper as EH
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, EH):
    helper = None
if helper is not None:
    portal = chr.getField().getInfo().getPortalByID(parentID)
    sm.warp(helper.mazePortal(chr, portal.getName() if portal is not None else None), 0)
sm.dispose()
