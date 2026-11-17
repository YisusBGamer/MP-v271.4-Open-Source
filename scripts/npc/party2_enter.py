# Red Sign (2040034) | Dimensional Crack (Ludibrium party quest) entrance
# Party Quest Entry hub (910002000): takes you to the Eos Tower 101st floor.
# Eos Tower 101st floor (221023300): enters the party quest, Broken Glasses every 5 clears.
from net.swordie.ms.enums import UIType
from net.swordie.ms.world.field.instance.instancehelper.pq import DimensionalCrackInstanceHelper as DC

ENTRANCE = DC.ENTRANCE

if sm.getFieldID() != ENTRANCE:
    if sm.sendAskYesNo("#e<Party Quest: Dimensional Crack>#n\r\n\r\nA Dimensional Crack has opened at the top of the "
                       "Eos Tower in #b#m220000000##k. Shall I take you to the entrance on the #b101st floor#k?"):
        sm.warp(ENTRANCE, 0)
    sm.dispose()

selection = sm.sendNext("#e<Party Quest: Dimensional Crack>#n\r\n\r\n"
                        "You can't go any higher because of the extremely dangerous creatures above. Would you like to "
                        "collaborate with party members to complete the quest? If so, please have your #bparty leader#k "
                        "talk to me.\r\n\r\n"
                        "#b#L0#I want to participate in the party quest.#l\r\n"
                        "#L1#I want to find party members.#l\r\n"
                        "#L2#I want to receive the Broken Glasses.#l\r\n"
                        "#L3#I would like to hear more details.#l")

if selection == 0:
    blocker = DC.getEntryBlocker(chr)
    if blocker is not None:
        sm.sendSayOkay(blocker)
    else:
        DC.recordEntries(chr)
        sm.warpInstanceIn(DC.STAGE_1, 0, True)

elif selection == 1:
    sm.openUI(UIType.PARTYSEARCH)

elif selection == 2:
    if DC.getClaimableGlasses(chr) > 0:
        if DC.claimGlasses(chr):
            sm.sendSayOkay("Thank you for all your help! Here are your #i1022073# #bBroken Glasses#k.")
        else:
            sm.sendSayOkay("Please make room in your #bEquip#k inventory first.")
    else:
        sm.sendSayOkay("I am offering 1x #i1022073# #bBroken Glasses#k for every " + str(DC.CLEARS_PER_GLASSES) +
                       " times you help me. If you help me #b" + str(DC.getClearsUntilGlasses(chr)) +
                       " more time(s)#k, #byou can receive Broken Glasses#k.")

elif selection == 3:
    sm.sendSayOkay("#e<Party Quest: Dimensional Crack>#n\r\n"
                   "A Dimensional Crack has appeared in #b#m220000000##k! We desperately need brave adventurers who can "
                   "defeat the monsters pouring through. Please, party with some dependable allies to save "
                   "#m220000000#! You must pass through several stages by defeating monsters and solving quizzes, and "
                   "ultimately defeat #r#o2600622##k.\r\n"
                   "#e-Level#n: " + str(DC.MIN_LEVEL) + " or above #r(Recommended Level: 120 - 139)#k\r\n"
                   "#e-Time Limit#n: 20 min\r\n"
                   "#e-Number of Players#n: " + str(DC.MIN_PARTY) + " to " + str(DC.MAX_PARTY) + "\r\n"
                   "#e-Entries#n: " + str(DC.DAILY_ENTRIES) + " per day\r\n"
                   "#e-Reward#n: EXP, and #i1022073# Broken Glasses #b(every " + str(DC.CLEARS_PER_GLASSES) +
                   " clears)#k")
