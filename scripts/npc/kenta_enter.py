# Dolphin (9020003) | Kenta in Danger: Party Quest Entry hub and Forked Road: Dangerous Sea
from net.swordie.ms.enums import UIType
from net.swordie.ms.world.field.instance.instancehelper.pq import KentaInstanceHelper as KH

sm.setSpeakerID(9020003)
if sm.getFieldID() != KH.ENTRANCE:
    if sm.sendAskYesNo("#e<Party Quest: Kenta in Danger>#n\r\n\r\nKenta went into the dangerous sea and hasn't come "
                       "back! Shall I take you to the #bForked Road: Dangerous Sea#k?"):
        sm.warp(KH.ENTRANCE, 0)
    sm.dispose()

sel = sm.sendNext("#e<Party Quest: Kenta in Danger>#n\r\n\r\nKenta is in danger! Please save him!\r\n\r\n"
                  "#b#L0#Enter the party quest.#l\r\n#L1#Find party members.#l\r\n"
                  "#L2#Trade 10 Pianus Scales for Kenta's New Goggles.#l\r\n"
                  "#L3#Trade 5 Pianus Scales for a Kenta Pet Equip Scroll.#l\r\n#L4#Tell me more.#l")
if sel == 0:
    blocker = KH.getEntryBlocker(chr)
    if blocker is not None:
        sm.sendSayOkay(blocker)
    else:
        KH.recordEntries(chr)
        sm.warpInstanceIn(KH.STAGE_1, 0, True)
elif sel == 1:
    sm.openUI(UIType.PARTYSEARCH)
elif sel == 2 or sel == 3:
    sm.sendSayOkay(KH.tradeScales(chr, sel - 2))
elif sel == 4:
    sm.sendSayOkay("#e<Party Quest: Kenta in Danger>#n\r\n"
                   "#e-Level#n: " + str(KH.MIN_LEVEL) + " or above #r(Recommended Level: 160 - 179)#k\r\n"
                   "#e-Time Limit#n: 20 min\r\n#e-Players#n: 1 to 6\r\n#e-Entries#n: 5 per day\r\n"
                   "#e-Reward#n: EXP and #i4001535# Pianus Scales: 10 for #i1022175# Kenta's New Goggles, or 5 for a "
                   "Kenta Pet Equip Scroll.")
