# Jenn (9020005) | Escape party quest: Party Quest Entry hub and Hidden Tower Entrance
from net.swordie.ms.enums import UIType
from net.swordie.ms.world.field.instance.instancehelper.pq import EscapeInstanceHelper as EH

sm.setSpeakerID(9020005)
if sm.getFieldID() != EH.ENTRANCE:
    if sm.sendAskYesNo("#e<Party Quest: Escape>#n\r\n\r\nExplorers are locked up in the #bAerial Prison#k of Lion "
                       "King's Castle. Will you help me free them? I'll take you to the Hidden Tower Entrance."):
        sm.warp(EH.ENTRANCE, 0)
    sm.dispose()

sel = sm.sendNext("#e<Party Quest: Escape>#n\r\n\r\nPlease help me rescue the explorers trapped in the Aerial "
                  "Prison!\r\n\r\n"
                  "#b#L0#Enter the party quest.#l\r\n"
                  "#L1#Find party members.#l\r\n"
                  "#L2#Trade Prison Guard Keys for a belt.#l\r\n"
                  "#L3#Tell me more.#l")
if sel == 0:
    blocker = EH.getEntryBlocker(chr)
    if blocker is not None:
        sm.sendSayOkay(blocker)
    else:
        EH.recordEntries(chr)
        sm.warpInstanceIn(EH.STAGE_1, 0, True)
elif sel == 1:
    sm.openUI(UIType.PARTYSEARCH)
elif sel == 2:
    menu = "Bring me #b" + str(EH.KEYS_PER_BELT) + " #i" + str(EH.PRISON_GUARD_KEY) + "# Prison Guard Keys#k and pick a belt:\r\n\r\n#b"
    for i in range(len(EH.BELTS)):
        menu += "#L" + str(i) + "##i" + str(EH.BELTS[i]) + "# #t" + str(EH.BELTS[i]) + "##l\r\n"
    pick = sm.sendNext(menu)
    if EH.tradeKeysForBelt(chr, pick):
        sm.sendSayOkay("Here you go! Thank you for freeing the prisoners.")
    else:
        sm.sendSayOkay("You need " + str(EH.KEYS_PER_BELT) + " Prison Guard Keys and room in your #bEquip#k "
                       "inventory.")
elif sel == 3:
    sm.sendSayOkay("#e<Party Quest: Escape>#n\r\n"
                   "#e-Level#n: " + str(EH.MIN_LEVEL) + " or above #r(Recommended Level: 140 - 159)#k\r\n"
                   "#e-Time Limit#n: 20 min\r\n"
                   "#e-Number of Players#n: " + str(EH.MIN_PARTY) + " to " + str(EH.MAX_PARTY) + "\r\n"
                   "#e-Entries#n: " + str(EH.DAILY_ENTRIES) + " per day\r\n"
                   "#e-Reward#n: EXP, and a #bPrison Guard Key#k each clear. 5 keys make a #bVon Leon War Belt#k.")
