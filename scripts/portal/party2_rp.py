# Dimensional Crack stage 3: the numbered boxes (portals pt00-pt92). The right box of a row moves you up a row, a
# wrong one back to the bottom. The code is new every run (DimensionalCrackInstanceHelper).
from net.swordie.ms.world.field.instance.instancehelper.pq import DimensionalCrackInstanceHelper as DC
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if isinstance(helper, DC):
    sm.teleportToPortal(helper.enterBox(chr, parentID))
sm.dispose()
