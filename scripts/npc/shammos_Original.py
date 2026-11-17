# Shammos (2022003) | Resurrection of the Hoblin King: Party Quest Entry hub / Shammos's Solitary Room
from net.swordie.ms.enums import UIType
from net.swordie.ms.world.field.instance.instancehelper.pq import HoblinKingInstanceHelper as HK

sm.setSpeakerID(2022003)
if sm.getFieldID() != HK.SOLITARY_ROOM:
    if sm.sendAskYesNo("#e<Party Quest: Resurrection of the Hoblin King>#n\r\n\r\nThe seal on the Hoblin King is "
                       "weakening. Shall I take you to my room in El Nath?"):
        sm.warp(HK.SOLITARY_ROOM, 0)
    sm.dispose()

sel = sm.sendNext("#e<Party Quest: Resurrection of the Hoblin King>#n\r\n\r\nPlease escort me through the Ice Ravine "
                  "so I can strengthen the seal on Rex, the Hoblin King.\r\n\r\n#b#L0#Enter the Ice Ravine.#l\r\n"
                  "#L1#Find party members.#l\r\n#L2#Tell me more.#l")
if sel == 0:
    blocker = HK.getEntryBlocker(chr)
    if blocker is not None:
        sm.sendSayOkay(blocker)
    else:
        HK.recordEntries(chr)
        sm.warpInstanceIn(HK.RAVINE_1, 0, True)
elif sel == 1:
    sm.openUI(UIType.PARTYSEARCH)
elif sel == 2:
    sm.sendSayOkay("#e<Party Quest: Resurrection of the Hoblin King>#n\r\n"
                   "#e-Level#n: " + str(HK.MIN_LEVEL) + " or above #r(Recommended Level: 170 - 189)#k\r\n"
                   "#e-Time Limit#n: 20 min\r\n#e-Players#n: 1 to 6\r\n#e-Entries#n: 5 per day\r\n"
                   "#e-Reward#n: EXP\r\n\r\nHold off the Hoblins in the three Ice Ravines, then face what waits in "
                   "the Ancient Ice Cave.")
