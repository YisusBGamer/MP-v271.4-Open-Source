# Limbo, Specter D (phase 2): the pit below the platforms. A fall adds Erosion (LimboInstanceHelper.onPitFall)
# and puts the player back on top (pt01).
from net.swordie.ms.world.field.instance.instancehelper.boss import LimboInstanceHelper

inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if isinstance(helper, LimboInstanceHelper):
    helper.onPitFall(chr)
top = sm.getField().getInfo().getPortalByName("pt01")
if top is not None:
    sm.teleportToPortal(top.getId())
sm.dispose()
