# Michaela (2170016) | Xerxes in Chryse: Spiegelmann's Guest House
from net.swordie.ms.enums import UIType
from net.swordie.ms.world.field.instance.instancehelper.pq import ChryseInstanceHelper as CH

sm.setSpeakerID(2170016)
sel = sm.sendNext("#e<Party Quest: Xerxes in Chryse>#n\r\n\r\nMy pet Xerxes ran off to the Coliseum in Chryse and "
                  "won't calm down! Will you find him for me?\r\n\r\n#b#L0#Go to the Coliseum.#l\r\n"
                  "#L1#Find party members.#l\r\n#L2#How many more times can I go today?#l\r\n#L3#Tell me more.#l")
if sel == 0:
    blocker = CH.getEntryBlocker(chr)
    if blocker is not None:
        sm.sendSayOkay(blocker)
    else:
        CH.recordEntries(chr)
        sm.warpInstanceIn(CH.COLISEUM, 0, True)
elif sel == 1:
    sm.openUI(UIType.PARTYSEARCH)
elif sel == 2:
    sm.sendSayOkay("You can go #b" + str(CH.getRemainingEntries(chr)) + "#k more time(s) today.")
elif sel == 3:
    sm.sendSayOkay("#e<Party Quest: Xerxes in Chryse>#n\r\n"
                   "#e-Level#n: " + str(CH.MIN_LEVEL) + " or above\r\n"
                   "#e-Time Limit#n: 20 min\r\n#e-Players#n: 1 to 6\r\n#e-Entries#n: 5 per day\r\n"
                   "#e-Reward#n: EXP\r\n\r\nDefeat Xerxes in the Coliseum so I can take him home.")
