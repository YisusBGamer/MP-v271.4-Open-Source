# Crimson Sky Doorway (2085001) | Dragon Rider: entering at the Crimson Sky Dock, leaving from inside
from net.swordie.ms.enums import UIType
from net.swordie.ms.world.field.instance.instancehelper.pq import DragonRiderInstanceHelper as DR
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, DR):
    helper = None

sm.setSpeakerID(2085001)
if helper is not None:
    if sm.sendAskYesNo("Do you wish to abandon the battle and leave the Crimson Sky area?"):
        sm.warpInstanceOut(DR.DOCK, 0)
    sm.dispose()

sel = sm.sendNext("#e<Party Quest: Dragon Rider>#n\r\n\r\nThe Dragon Rider is gathering wyverns in the Crimson Sky. "
                  "Will you fly out and stop him?\r\n\r\n#b#L0#Enter the Crimson Sky.#l\r\n#L1#Find party members.#l\r\n"
                  "#L2#Tell me more.#l")
if sel == 0:
    blocker = DR.getEntryBlocker(chr)
    if blocker is not None:
        sm.sendSayOkay(blocker)
    else:
        DR.recordEntries(chr)
        sm.warpInstanceIn(DR.STAGE_1, 0, True)
elif sel == 1:
    sm.openUI(UIType.PARTYSEARCH)
elif sel == 2:
    sm.sendSayOkay("#e<Party Quest: Dragon Rider>#n\r\n"
                   "#e-Level#n: " + str(DR.MIN_LEVEL) + " or above #r(Recommended Level: 150 - 169)#k\r\n"
                   "#e-Time Limit#n: 20 min\r\n#e-Players#n: 1 to 6\r\n#e-Entries#n: 5 per day\r\n"
                   "#e-Reward#n: EXP and a #bDragon Rider's Box#k for your job.\r\n\r\n"
                   "You'll get the #bSoaring#k skill to fly in the Crimson Sky if you don't have it yet.")
