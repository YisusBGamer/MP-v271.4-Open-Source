# Tory (9076100) | Moon Bunny's Rice Cake Waiting Room
from net.swordie.ms.enums import UIType
from net.swordie.ms.world.field.instance.instancehelper.pq import MoonBunnyInstanceHelper as MB

sm.setSpeakerID(parentID)
sel = sm.sendNext("#e<Party Quest: Moon Bunny's Rice Cake>#n\r\nHi, I'm Tory!\r\n\r\n#b#L0#Go to Primrose Hill.#l\r\n"
                  "#L1#Find party members.#l\r\n#L2#How many more times can I try Primrose Hill today?#l\r\n"
                  "#L3#Tell me more.#l")
if sel == 0:
    blocker = MB.getEntryBlocker(chr)
    if blocker is not None:
        sm.sendSayOkay(blocker)
    else:
        MB.recordEntries(chr)
        sm.warpInstanceIn(910010000, 0, True)
elif sel == 1:
    sm.openUI(UIType.PARTYSEARCH)
elif sel == 2:
    sm.sendSayOkay("You can go #b" + str(MB.getRemainingEntries(chr)) + "#k more time(s) today.")
elif sel == 3:
    sm.sendSayOkay("#e<Party Quest: Moon Bunny's Rice Cake>#n\r\n"
                   "#e-Level#n: " + str(MB.MIN_LEVEL) + " or above\r\n#e-Players#n: 1 to 4\r\n"
                   "#e-Entries#n: 5 per day\r\n#e-Reward#n: EXP, Willpower and Insight, and 10 Party Points.\r\n\r\n"
                   "Plant 6 Primrose Seeds to bring out the Moon Bunny, then protect it until you have "
                   + str(MB.RICE_CAKE_REQ) + " rice cakes and talk to Growlie.")
