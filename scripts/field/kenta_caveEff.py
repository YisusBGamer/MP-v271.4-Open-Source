# Kenta in Danger, Dangerous Cave 1 (every character entering)
# Once the bats are cleared the party is carried to the top; anyone arriving later is put there too (portal 2, gm00)
from net.swordie.ms.world.field.instance.instancehelper.pq import KentaInstanceHelper as KH
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if isinstance(helper, KH) and helper.isStageCleared(3):
    sm.teleportToPortal(2)
sm.dispose()
