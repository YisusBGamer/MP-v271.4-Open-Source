# Ellin (2133001) | Forest of Poison Haze
from net.swordie.ms.world.field.instance.instancehelper.pq import PoisonHazeInstanceHelper as PH
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, PH):
    helper = None
sm.setSpeakerID(2133001)
fid = sm.getFieldID()
if fid == PH.ELLINS_FOREST:
    if sm.sendAskYesNo("Thank you so much! The forest is clean again thanks to you. Shall I send you back to "
                       "Spiegelmann's Guest House?"):
        sm.warpInstanceOut(910002000, 0)
    sm.dispose()
if helper is None:
    sm.dispose()
if fid == PH.PRE_ENTRANCE or fid == PH.FOREST_ENTRANCE:
    sm.sendSayOkay("This is the way into the poisoned part of the forest. Go through the portal when you're ready!")
elif fid == PH.STAGE_1:
    sm.sendSayOkay("The Lord Trees here have been poisoned! Defeat all #b50#k of them to purify the mouth of the forest.")
elif fid == PH.STAGE_2:
    sm.sendSayOkay("The Poisoned Stone Bugs drop #b#t4001161##k. #bDrop it into the water hole#k in the middle to dilute "
                   "it, then #bdrop " + str(PH.DILUTED_NEEDED) + " #t4001162##k on the #bThorny Vines#k on the far right "
                   "to clear the way.")
elif fid == PH.STAGE_3:
    msg = helper.reachEllin(chr)
    if msg is not None:
        sm.sendSayOkay(msg)
elif fid == PH.STAGE_4:
    msg = helper.giveMarbles(chr)
    if msg is not None:
        sm.sendSayOkay(msg)
elif fid == PH.STAGE_6:
    sm.sendSayOkay("Whoever has the #bPurple Stone of Magic#k must #bdrop it on the altar#k to call out the Poison "
                   "Golem. Defeat it to purify the forest!")
sm.dispose()
