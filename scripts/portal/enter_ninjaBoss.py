# Ninja Castle Corridor: the door to the inner halls (same as asking Baetan)
from net.swordie.ms.world.field.instance.instancehelper.pq import NinjaCastleInstanceHelper as NC
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, NC):
    helper = None

sm.setSpeakerID(9100037)
def enter():
    blocker = NC.getEntryBlocker(chr)
    if blocker is not None:
        sm.sendSayOkay(blocker)
        return
    NC.recordEntries(chr)
    sm.warpInstanceIn(NC.CORRIDORS[0], 0, True)

if sm.getFieldID() == NC.CASTLE_CORRIDOR:
    if sm.sendAskYesNo("Beyond this door are the inner halls. Go in?"):
        enter()
sm.dispose()
