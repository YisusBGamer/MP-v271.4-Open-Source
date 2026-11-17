# Ellin (9076120) | Forest of Poison Haze (Cross World) Pre-Entrance waiting room
from net.swordie.ms.enums import UIType
from net.swordie.ms.world.field.instance.instancehelper.pq import PoisonHazeInstanceHelper as PH

sm.setSpeakerID(parentID)
sel = sm.sendNext("#e<Party Quest: Forest of Poison Haze>#n\r\n\r\nA mysterious man has poisoned part of our forest! "
                  "Will you help me purify it?\r\n\r\n#b#L0#Enter the Forest of Poison Haze.#l\r\n"
                  "#L1#Find party members.#l\r\n#L2#How many more times can I enter today?#l")
if sel == 0:
    blocker = PH.getEntryBlocker(chr)
    if blocker is not None:
        sm.sendSayOkay(blocker)
    else:
        PH.recordEntries(chr)
        sm.warpInstanceIn(PH.PRE_ENTRANCE, 0, True)
elif sel == 1:
    sm.openUI(UIType.PARTYSEARCH)
elif sel == 2:
    sm.sendSayOkay("You can enter #b" + str(PH.getRemainingEntries(chr)) + "#k more time(s) today.")
