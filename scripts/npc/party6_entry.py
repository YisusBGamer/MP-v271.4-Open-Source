# Ellin (2133000) | Forest of Poison Haze: Spiegelmann's Guest House / Ellin Forest
from net.swordie.ms.enums import UIType
from net.swordie.ms.world.field.instance.instancehelper.pq import PoisonHazeInstanceHelper as PH

sm.setSpeakerID(2133000)
sel = sm.sendNext("#e<Party Quest: Forest of Poison Haze>#n\r\n\r\nA mysterious man in a black robe has poisoned part "
                  "of our forest! Will you help me purify it?\r\n\r\n#b#L0#Enter the Forest of Poison Haze.#l\r\n"
                  "#L1#Find party members.#l\r\n#L2#How many more times can I enter today?#l\r\n#L3#Tell me more.#l")
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
    sm.sendSayOkay("You can enter the Forest of Poison Haze #b" + str(PH.getRemainingEntries(chr)) + "#k more time(s) "
                   "today.")
elif sel == 3:
    sm.sendSayOkay("#e<Party Quest: Forest of Poison Haze>#n\r\n"
                   "#e-Level#n: " + str(PH.MIN_LEVEL) + " or above\r\n"
                   "#e-Time Limit#n: 20 min\r\n#e-Players#n: 1 to 4\r\n#e-Entries#n: 5 per day\r\n"
                   "#e-Reward#n: EXP, Willpower and Insight, and #b10 Party Points#k to spend at the Pub in "
                   "Spiegelmann's Guest House.\r\n\r\nPurify the Lord Trees, dilute the poison to clear the thorns, find "
                   "me in the haze, free the Sprights, find the Purple Stone of Magic and defeat the Poison Golem.")
