# Lakelis (9076110) | First Time Together Waiting Room
from net.swordie.ms.enums import UIType
from net.swordie.ms.world.field.instance.instancehelper.pq import FirstTimeTogetherInstanceHelper as FT

sm.setSpeakerID(parentID)
sel = sm.sendNext("#e<Party Quest: First Time Together>#n\r\n\r\n#b#L0#I want to attempt this party quest.#l\r\n"
                  "#L1#Find party members.#l\r\n#L2#How many more times can I attempt this party quest today?#l\r\n"
                  "#L3#Tell me more.#l")
if sel == 0:
    blocker = FT.getEntryBlocker(chr)
    if blocker is not None:
        sm.sendSayOkay(blocker)
    else:
        FT.recordEntries(chr)
        sm.warpInstanceIn(FT.STAGE_1, 0, True)
elif sel == 1:
    sm.openUI(UIType.PARTYSEARCH)
elif sel == 2:
    sm.sendSayOkay("You can attempt it #b" + str(FT.getRemainingEntries(chr)) + "#k more time(s) today.")
elif sel == 3:
    sm.sendSayOkay("#e<Party Quest: First Time Together>#n\r\n"
                   "#e-Level#n: " + str(FT.MIN_LEVEL) + " or above\r\n#e-Time Limit#n: 20 min\r\n"
                   "#e-Players#n: " + str(FT.PARTY_SIZE) + "\r\n#e-Entries#n: 5 per day\r\n"
                   "#e-Reward#n: EXP, Willpower and Insight, and 10 Party Points.\r\n\r\n"
                   "Coupons, vines, platforms and a little math, then King Slime.")
