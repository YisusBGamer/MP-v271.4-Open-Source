# Cooking with Tangyoon, the cauldron: the dish for everyone entering
from net.swordie.ms.world.field.instance.instancehelper.pq import TangyoonInstanceHelper as TY
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, TY):
    helper = None
if helper is not None:
    sm.chatScript("Today's dish: " + helper.getDishName() + ". The party leader picks ingredients with Tangyoon!")
sm.dispose()
