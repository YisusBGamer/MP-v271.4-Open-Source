# Guon (2094002) | inside the Lord Pirate party quest
from net.swordie.ms.world.field.instance.instancehelper.pq import LordPirateInstanceHelper as LP
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, LP):
    helper = None

fid = sm.getFieldID()
if helper is None or fid == LP.OUT_MAP:
    if sm.sendAskYesNo("Shall I take you back to the entrance?"):
        if chr.getInstance() is not None:
            sm.warpInstanceOut(LP.ENTRANCE, 0)
        else:
            sm.warp(LP.ENTRANCE, 0)
    sm.dispose()

if fid == LP.STAGE_1:
    if helper.isStageCleared(1):
        sm.sendSayOkay("Well done! Head through the portal.")
    else:
        sm.sendSayOkay("Defeat every monster and break all the chests to clear the way to the ship! Chests broken: "
                       + str(helper.getChestsBroken()) + " of 7.")
elif fid == LP.STAGE_2:
    if helper.isStageCleared(2):
        sm.sendSayOkay("You have all the marks! Please proceed to the next stage.")
    else:
        sm.sendSayOkay(helper.handInMarks(chr))
elif fid == LP.STAGE_3:
    if helper.isStageCleared(3):
        sm.sendSayOkay("All the doors are shut! Go and face the Lord Pirate.")
    else:
        sm.sendSayOkay("Pirates keep pouring out of the four doors! Pirates sometimes drop an #bOld Metal Key#k: "
                       "attack a door while holding one, or drop the key at the door, to close it. Doors closed: "
                       + str(helper.getDoorsClosed()) + " of 4. Close all four and defeat every pirate.")
elif fid == LP.STAGE_BOSS:
    if helper.isFinished():
        if sm.sendAskYesNo("You beat the Lord Pirate! Shall we go and see Wu Yang?"):
            helper.goToReward()
    else:
        sm.sendSayOkay("Defeat the #rLord Pirate#k!")
else:
    if sm.sendAskYesNo("Would you like to leave?"):
        sm.warpInstanceOut(LP.ENTRANCE, 0)
