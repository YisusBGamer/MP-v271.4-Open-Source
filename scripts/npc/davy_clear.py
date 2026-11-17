# Wu Yang (2094001) | Lord Pirate party quest: the reward
from net.swordie.ms.world.field.instance.instancehelper.pq import LordPirateInstanceHelper as LP
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, LP):
    helper = None

sm.setSpeakerID(2094001)
if helper is None:
    sm.warp(LP.ENTRANCE, 0)
    sm.dispose()
got = helper.giveFragments(chr)
if got == -2:
    sm.sendSayOkay("Please make room in your #bEtc#k inventory so I can give you your reward.")
    sm.dispose()
if got > 0:
    sm.sendNext("Thank you for rescuing me! Please take these #i" + str(LP.HAT_FRAGMENT) + "# #b" + str(got) +
                " Lord Pirate Hat Fragments#k. Guon can make a hat out of them.")
if sm.sendAskYesNo("Shall I send you back to the entrance?"):
    sm.warpInstanceOut(LP.ENTRANCE, 0)
