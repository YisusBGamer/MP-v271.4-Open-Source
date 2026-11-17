# Iceman (2159019) | The Ice Knight's Curse: Spiegelmann's Guest House
from net.swordie.ms.enums import UIType
from net.swordie.ms.world.field.instance.instancehelper.pq import IceKnightInstanceHelper as IK

sm.setSpeakerID(2159019)
sel = sm.sendNext("#e<Party Quest: The Ice Knight's Curse>#n\r\n\r\nIt's so cold... The Ice Knight cursed me. Please, "
                  "lift this curse!\r\n\r\n#b#L0#Enter the Frosty Pass.#l\r\n#L1#Find party members.#l\r\n"
                  "#L2#How many more times can I enter today?#l\r\n#L3#Tell me more.#l")
if sel == 0:
    blocker = IK.getEntryBlocker(chr)
    if blocker is not None:
        sm.sendSayOkay(blocker)
    else:
        IK.recordEntries(chr)
        sm.warpInstanceIn(IK.STAGE_1, 0, True)
elif sel == 1:
    sm.openUI(UIType.PARTYSEARCH)
elif sel == 2:
    sm.sendSayOkay("You can enter #b" + str(IK.getRemainingEntries(chr)) + "#k more time(s) today.")
elif sel == 3:
    sm.sendSayOkay("#e<Party Quest: The Ice Knight's Curse>#n\r\n"
                   "#e-Level#n: " + str(IK.MIN_LEVEL) + " or above #r(Recommended Level: 90 - 109)#k\r\n"
                   "#e-Time Limit#n: 20 min\r\n#e-Players#n: 1 to 6\r\n#e-Entries#n: 5 per day\r\n"
                   "#e-Reward#n: EXP and a #i4001529# #t4001529#. Von trades them for the #t1152127# and the "
                   "#t1072819#.\r\n\r\nFight through the Frosty Pass, then defeat the Ice Knight in the Ice Curse "
                   "Forest.")
