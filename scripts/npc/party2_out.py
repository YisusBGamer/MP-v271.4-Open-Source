# Sgt. Anderson (2040047) | leaving the Dimensional Crack
from net.swordie.ms.world.field.instance.instancehelper.pq import DimensionalCrackInstanceHelper as DC

sm.setSpeakerID(2040047)
if chr.getInstance() is None:
    sm.sendNext("I will send you back to the 101st floor of the Eos Tower.")
    sm.warp(DC.ENTRANCE, 0)
elif sm.sendAskYesNo("Are you sure you want to leave your party behind and leave the quest?"):
    sm.sendNext("Okay, I will send you back to the 101st floor of the Eos Tower.")
    sm.warpInstanceOut(DC.ENTRANCE, 0)
else:
    sm.sendSayOkay("Thank you for choosing to stay here and finish this quest.")
