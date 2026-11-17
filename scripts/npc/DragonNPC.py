# Kid Draco (9010042) in the Black Dragon Hometown: the way out of his uncle's lair.
from net.swordie.ms.world import HolidayEvents as H
sm.setSpeakerID(9010042)
if sm.getFieldID() == H.BLACK_DRAGON_HOMETOWN and sm.hasMobsInField():
    if sm.sendAskYesNo("Uncle's still standing! Giving up already? I can take you back to town."):
        sm.warpInstanceOut(H.dragonReturnTown(chr))
elif sm.sendAskYesNo("That was amazing! Shall I take you back to town?"):
    sm.warpInstanceOut(H.dragonReturnTown(chr))
sm.dispose()
