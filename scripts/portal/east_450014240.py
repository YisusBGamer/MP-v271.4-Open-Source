# M Tower Top Floor (450014240) -> the Singularity Maker fight (993176130), for [Reverse City] Long Awaited (37619).
from net.swordie.ms.loaders import MobData

if sm.hasQuest(37619) and sm.getQRValue(37619, "dummy") != "1":
    # destroying the quest's Singularity Maker (8641059) runs field/rc_singularity_destroyed.py
    MobData.getMobInfoById(8641059).setDeathScript("rc_singularity_destroyed")
    sm.warpInstanceIn(993176130, 0)
elif sm.hasQuest(37619):
    sm.chatScript("The Singularity Maker has already been destroyed. Talk to Barr.")
else:
    sm.chatScript("The way to the Singularity Maker is sealed.")
