# Guon (2094000) | Lord Pirate party quest: entrance (Over the Pirate Ship) and the Party Quest Entry hub
from net.swordie.ms.enums import UIType
from net.swordie.ms.world.field.instance.instancehelper.pq import LordPirateInstanceHelper as LP

if sm.getFieldID() != LP.ENTRANCE:
    if sm.sendAskYesNo("#e<Party Quest: Lord Pirate>#n\r\n\r\nThe Lord Pirate has kidnapped #bWu Yang#k, the leader "
                       "of the Bellflowers! Shall I take you to the pirate ship?"):
        sm.warp(LP.ENTRANCE, 0)
    sm.dispose()

sel = sm.sendNext("#e<Party Quest: Lord Pirate>#n\r\n\r\nThe Lord Pirate has kidnapped #bWu Yang#k, the leader of "
                  "the Bellflowers. Will you help me rescue him?\r\n\r\n"
                  "#b#L0#Enter the party quest.#l\r\n"
                  "#L1#Find party members.#l\r\n"
                  "#L2#Make a Lord Pirate Hat.#l\r\n"
                  "#L3#Tell me more.#l")
if sel == 0:
    blocker = LP.getEntryBlocker(chr)
    if blocker is not None:
        sm.sendSayOkay(blocker)
    else:
        LP.recordEntries(chr)
        sm.warpInstanceIn(LP.STAGE_1, 0, True)
elif sel == 1:
    sm.openUI(UIType.PARTYSEARCH)
elif sel == 2:
    hat = sm.sendNext("Bring me #i" + str(LP.HAT_FRAGMENT) + "# #bLord Pirate Hat Fragments#k and I'll make you a hat.\r\n\r\n"
                      "#b#L0##t" + str(LP.COMMON_HAT) + "# (30 Fragments)#l\r\n"
                      "#L1##t" + str(LP.RATTY_HAT) + "# (#t" + str(LP.COMMON_HAT) + "# + 60 Fragments)#l\r\n"
                      "#L2##t" + str(LP.GREEDY_HAT) + "# (#t" + str(LP.RATTY_HAT) + "# + 90 Fragments)#l")
    recipes = {0: (None, 30, LP.COMMON_HAT), 1: (LP.COMMON_HAT, 60, LP.RATTY_HAT), 2: (LP.RATTY_HAT, 90, LP.GREEDY_HAT)}
    old, need, new = recipes.get(hat, (None, 0, 0))
    if new == 0:
        sm.dispose()
    if not sm.hasItem(LP.HAT_FRAGMENT, need) or (old is not None and not sm.hasItem(old)):
        sm.sendSayOkay("You don't have everything I need for that hat yet.")
    elif not sm.canHold(new):
        sm.sendSayOkay("Please make room in your #bEquip#k inventory first.")
    else:
        sm.consumeItem(LP.HAT_FRAGMENT, need)
        if old is not None:
            sm.consumeItem(old)
        sm.giveItem(new)
        sm.sendSayOkay("Here's your #i" + str(new) + "# #b#t" + str(new) + "##k!")
elif sel == 3:
    sm.sendSayOkay("#e<Party Quest: Lord Pirate>#n\r\n"
                   "#e-Level#n: " + str(LP.MIN_LEVEL) + " or above #r(Recommended Level: 130 - 149)#k\r\n"
                   "#e-Time Limit#n: 20 min\r\n"
                   "#e-Number of Players#n: " + str(LP.MIN_PARTY) + " to " + str(LP.MAX_PARTY) + "\r\n"
                   "#e-Entries#n: " + str(LP.DAILY_ENTRIES) + " per day\r\n"
                   "#e-Reward#n: EXP, and Lord Pirate Hat Fragments (more for each monster you defeat) to make "
                   "#bLord Pirate Hats#k.")
