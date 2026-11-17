# Wonky the Fairy (2013000) | Remnant of the Goddess: Party Quest Entry hub / Tower of Goddess entrance
from net.swordie.ms.enums import UIType
from net.swordie.ms.world.field.instance.instancehelper.pq import GoddessInstanceHelper as GH

sm.setSpeakerID(2013000)
if sm.getFieldID() != GH.ENTRANCE:
    if sm.sendAskYesNo("#e<Party Quest: Remnant of the Goddess>#n\r\n\r\nMinerva the Goddess is sealed in her statue by "
                       "Papa Pixie! Shall I take you to the #bTower of Goddess#k?"):
        sm.warp(GH.ENTRANCE, 0)
    sm.dispose()

sel = sm.sendNext("#e<Party Quest: Remnant of the Goddess>#n\r\n\r\nPapa Pixie sealed Minerva in her statue. Please "
                  "restore the statue and set her free!\r\n\r\n#b#L0#Enter the Tower of Goddess.#l\r\n"
                  "#L1#Find party members.#l\r\n#L2#Tell me more.#l\r\n#L3#Leave the tower.#l")
if sel == 0:
    blocker = GH.getEntryBlocker(chr)
    if blocker is not None:
        sm.sendSayOkay(blocker)
    else:
        GH.recordEntries(chr)
        sm.warpInstanceIn(GH.CENTER, 0, True)
elif sel == 1:
    sm.openUI(UIType.PARTYSEARCH)
elif sel == 3:
    sm.warp(GH.WAITING_ROOM, 0)  # the tower entrance has no exit of its own
elif sel == 2:
    sm.sendSayOkay("#e<Party Quest: Remnant of the Goddess>#n\r\n"
                   "#e-Level#n: " + str(GH.MIN_LEVEL) + " or above\r\n#e-Time Limit#n: 30 min\r\n"
                   "#e-Players#n: 1 to 4\r\n#e-Entries#n: 5 per day\r\n#e-Reward#n: EXP\r\n\r\n"
                   "Find the 4 Goddess Statue Pieces in the Lounge, the Storage, the Walkway and On the Way Up, give "
                   "them to Chamberlain Eak, then defeat Papa Pixie in the Garden.")
