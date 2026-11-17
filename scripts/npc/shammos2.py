# Message from Shammos (2022006) | Ancient Ice Cave
from net.swordie.ms.world.field.instance.instancehelper.pq import HoblinKingInstanceHelper as HK
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, HK):
    helper = None
if helper is not None and helper.isFinished():
    if sm.sendAskYesNo("Rex is sealed again... Do you want to leave the cave?"):
        sm.warpInstanceOut(HK.BACKSTREET, 0)
else:
    sm.sendSayOkay("\"Fools! You led me right to the seal! Rise, Hoblin King!\"")
