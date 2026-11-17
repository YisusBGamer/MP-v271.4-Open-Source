# Von (2159018) | The Ice Knight's Curse: rewards
from net.swordie.ms.world.field.instance.instancehelper.pq import IceKnightInstanceHelper as IK

sm.setSpeakerID(2159018)
sel = sm.sendNext("Thank you for helping my friend Jun! If you bring me #bCold Ice Fragments#k from the Ice Knight, I can "
                  "make something for you.\r\n\r\n#b#L0#" + str(IK.SNOWSHOES_COST) + " #t4001529#s: #i1072819# "
                  "#t1072819##l\r\n#L1#" + str(IK.SHOULDER_COST) + " #t4001529#s: #i1152127# #t1152127##l")
sm.sendSayOkay(IK.trade(chr, sel))
