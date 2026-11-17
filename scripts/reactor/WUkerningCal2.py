# First Time Together (Cross World), Stage 4: a sign (drop a Number on it to apply it)
from net.swordie.ms.world.field.instance.instancehelper.pq import FirstTimeTogetherInstanceHelper as FT
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, FT):
    helper = None
if helper is not None:
    chr.chatMessage(helper.describeSign(2))
sm.dispose()
