# Lord Pirate stage 1: a chest. Breaking it releases Ginseng Jars and counts towards clearing the stage.
from net.swordie.ms.world.field.instance.instancehelper.pq import LordPirateInstanceHelper as LP
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, LP):
    helper = None
GINSENG_JAR = 9300109
BELLFLOWER = 9300110

pos = sm.getPosition(objectID)
for i in range(3):
    sm.spawnMob(GINSENG_JAR, pos.getX(), pos.getY(), False)
    sm.spawnMob(BELLFLOWER, pos.getX(), pos.getY(), False)
sm.removeReactor()
if helper is not None:
    helper.chestBroken(chr.getField(), objectID)
sm.dispose()
