# Duarte (2103013) | Nett's Pyramid: Pyramid Dunes, Spiegelmann's Guest House and Shades of the Pyramid
from net.swordie.ms.enums import UIType
from net.swordie.ms.world.field.instance.instancehelper.events.monsterdefense import NewPyramidInstanceHelper as NP

sm.setSpeakerID(2103013)
fid = sm.getFieldID()
if chr.getInstance() is not None:
    # inside the pyramid (he stands in it too): a plain warp would land in the run's own copy of the Dunes
    if sm.sendAskYesNo("Giving up on the Obelisk already? Shall I take you back to the #bPyramid Dunes#k?"):
        sm.warpInstanceOut(NP.PYRAMID_DUNES, 0)
    sm.dispose()

if fid != NP.PYRAMID_DUNES and fid != NP.SHADES_OF_THE_PYRAMID:
    if sm.sendAskYesNo("#e<Party Quest: Nett's Pyramid>#n\r\n\r\nThe Obelisk of Nett needs protecting. Shall I take you "
                       "to the #bPyramid Dunes#k?"):
        sm.warp(NP.PYRAMID_DUNES, 0)
    sm.dispose()

if fid == NP.SHADES_OF_THE_PYRAMID:
    sel = sm.sendNext("You made it out. What now?\r\n\r\n#b#L0#Trade Nett's Emeralds.#l\r\n"
                      "#L1#Go back to the Pyramid Dunes.#l")
    if sel == 1:
        sm.warp(NP.PYRAMID_DUNES, 0)
        sm.dispose()
else:
    sel = sm.sendNext("#e<Party Quest: Nett's Pyramid>#n\r\n\r\nMonsters are swarming the Obelisk of Nett. Protect it "
                      "through all 20 waves!\r\n\r\n#b#L2#Enter Nett's Pyramid.#l\r\n#L3#Find party members.#l\r\n"
                      "#L4#How many more times can I enter today?#l\r\n#L0#Trade Nett's Emeralds.#l\r\n"
                      "#L5#Tell me more.#l")
if sel == 0:
    pick = sm.sendNext("Bring me #b" + str(NP.EXCHANGE_COST) + " #t4001623#s#k for one of these:\r\n\r\n"
                       "#b#L0##i1132013# #t1132013##l\r\n#L1##i1072619# #t1072619##l\r\n"
                       "#L2##i1112682# #t1112682# (15 days)#l")
    sm.sendSayOkay(NP.trade(chr, pick))
elif sel == 2:
    blocker = NP.getEntryBlocker(chr)
    if blocker is not None:
        sm.sendSayOkay(blocker)
    else:
        NP.recordEntries(chr)
        sm.warpInstanceIn(NP.PYRAMID, 0, True)
elif sel == 3:
    sm.openUI(UIType.PARTYSEARCH)
elif sel == 4:
    sm.sendSayOkay("You can enter #b" + str(NP.getRemainingEntries(chr)) + "#k more time(s) today.")
elif sel == 5:
    sm.sendSayOkay("#e<Party Quest: Nett's Pyramid>#n\r\n"
                   "#e-Level#n: " + str(NP.MIN_LEVEL) + " or above (Normal: 80-89, Hard: 90+)\r\n"
                   "#e-Players#n: 1 to 4\r\n#e-Entries#n: 5 per day\r\n"
                   "#e-Reward#n: EXP by the waves cleared, Willpower and Ambition, and a #i4001623# #t4001623# per "
                   "wave. 40 Emeralds buy the #t1132013#, #t1072619# or #t1112682#.\r\n\r\nThe Obelisk has 20 life. "
                   "Each monster that reaches it takes 1 (a Pharaoh Yeti 2, a Pharaoh Mummy 5).")
