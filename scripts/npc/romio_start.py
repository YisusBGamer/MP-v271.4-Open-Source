# Romeo (2112004) | Romeo and Juliet party quest: start (Party Quest Entry hub / hidden room), and inside the quest
from net.swordie.ms.enums import UIType
from net.swordie.ms.world.field.instance.instancehelper.pq import RomeoJulietInstanceHelper as RJ
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, RJ):
    helper = None

ENTRANCE = 261000011
sm.setSpeakerID(2112004)

if helper is not None:
    fid = sm.getFieldID()
    if fid == helper.stage1() and not helper.isStageCleared(1):
        sm.sendSayOkay("Search the glowing spots in the lab. One of them hides the switch that opens the door.")
    elif fid == helper.stage2() and not helper.isStageCleared(2):
        sm.sendSayOkay("Please defeat all the Homuns and Saities in this tunnel.")
    elif fid == helper.stage3() and not helper.isStageCleared(3):
        sm.sendSayOkay("Get #bSuspicious Liquid#k from the Homunculi and pour it into the #b3 cracked beakers#k.")
    elif helper.isStageCleared(7):
        sm.sendSayOkay("Thank you so much!")
    elif sm.sendAskYesNo("Keep going, please! ...Or do you want to give up and leave?"):
        sm.warpInstanceOut(ENTRANCE, 0)
    sm.dispose()

if sm.getFieldID() != ENTRANCE:
    if sm.sendAskYesNo("#e<Party Quest: Romeo and Juliet>#n\r\n\r\nMagatia faces a grave threat. Shall I take you to "
                       "the #b#m" + str(ENTRANCE) + "##k?"):
        sm.warp(ENTRANCE, 0)
    sm.dispose()

sel = sm.sendNext("#e<Party Quest: Romeo and Juliet>#n\r\nMagatia faces a grave threat. We need brave adventurers to "
                  "help us.\r\n\r\n#b#L0#Start the quest.#l\r\n#L1#Find a party.#l\r\n"
                  "#L2#Make a pendant with my marbles.#l\r\n#L3#Combine Romeo's and Juliet's pendants.#l\r\n"
                  "#L4#Tell me more.#l")
if sel == 0:
    blocker = RJ.getEntryBlocker(chr)
    if blocker is not None:
        sm.sendSayOkay(blocker)
    else:
        RJ.recordEntries(chr)
        sm.warpInstanceIn(926100000, 0, True)
elif sel == 1:
    sm.openUI(UIType.PARTYSEARCH)
elif sel == 2:
    if not sm.hasItem(4001159, 2):
        sm.sendSayOkay("I can make #i1122116# #b#t1122116##k out of #b2 #t4001159#s#k.")
    elif not sm.canHold(1122116):
        sm.sendSayOkay("Please make some space in your Equip inventory.")
    else:
        sm.consumeItem(4001159, 2)
        sm.giveItem(1122116)
        sm.sendSayOkay("Here is your #i1122116# #b#t1122116##k!")
elif sel == 3:
    if not (sm.hasItem(1122116) and sm.hasItem(1122117)):
        sm.sendSayOkay("You need both #bRomeo's Pendant#k and #bJuliet's Pendant#k to make the #i1122118# #t1122118#.")
    elif not sm.canHold(1122118):
        sm.sendSayOkay("Please make some space in your Equip inventory.")
    else:
        sm.consumeItem(1122116)
        sm.consumeItem(1122117)
        sm.giveItem(1122118)
        sm.sendSayOkay("Here is the #i1122118# #b#t1122118##k!")
elif sel == 4:
    sm.sendSayOkay("Romeo and Juliet are in love, but they belong to the feuding Zenumist and Alcadno. Someone stole the "
                   "power source of both societies, and each blames the other... Find the real thief!\r\n\r\n"
                   "#e-Level#n: " + str(RJ.MIN_LEVEL) + " or above #r(Recommended: 70 - 99)#k\r\n"
                   "#e-Time Limit#n: 20 min\r\n#e-Players#n: 1 to " + str(RJ.MAX_PARTY) + "\r\n"
                   "#e-Entries#n: " + str(RJ.DAILY_ENTRIES) + " per day\r\n"
                   "#e-Reward#n: EXP and a #b#t4001159##k. 2 marbles make #i1122116#; Romeo's and Juliet's pendants make "
                   "#i1122118#.")
