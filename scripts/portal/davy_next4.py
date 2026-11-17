# Lord Pirate party quest: to the next stage (opens when this one is cleared)
from net.swordie.ms.world.field.instance.instancehelper.pq import LordPirateInstanceHelper as LP
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, LP):
    helper = None
if helper is not None and helper.canUsePortal(chr, chr.getField(), chr.getField().getInfo().getPortalByID(parentID)):
    sm.warp(LP.STAGE_BOSS, 0)
elif helper is None:
    sm.chat("Close all four doors and defeat every pirate first.")
sm.dispose()
