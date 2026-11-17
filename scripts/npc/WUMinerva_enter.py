# Wonky the Fairy (9076130) | Remnant of the Goddess (Cross World) waiting room
from net.swordie.ms.enums import UIType
from net.swordie.ms.world.field.instance.instancehelper.pq import GoddessInstanceHelper as GH

sm.setSpeakerID(parentID)
sel = sm.sendNext("#e<Party Quest: Remnant of the Goddess>#n\r\n\r\nPapa Pixie sealed Minerva in her statue. Please "
                  "set her free!\r\n\r\n#b#L0#Enter the Tower of Goddess.#l\r\n#L1#Find party members.#l")
if sel == 0:
    blocker = GH.getEntryBlocker(chr)
    if blocker is not None:
        sm.sendSayOkay(blocker)
    else:
        GH.recordEntries(chr)
        sm.warpInstanceIn(GH.CENTER, 0, True)
elif sel == 1:
    sm.openUI(UIType.PARTYSEARCH)
