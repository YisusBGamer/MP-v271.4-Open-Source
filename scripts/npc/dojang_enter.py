# So Gong (2091011 / 2091005) | Mu Lung Dojo Hall and floors
try:
    from net.swordie.ms.client.character import DojoRanking
except ImportError:
    DojoRanking = None  # until the server has the weekly ranking

DOJO_QUEST = 3887
dojoHall = 925020001
dojoExit = 925020002
MIN_LEVEL = 105
UNITY_TRAINING_CENTER = 925080000
# Unity Training Center Entrance Charms: (item, minutes)
CHARMS = ((4001851, 30), (4001852, 60), (4001853, 180), (4001854, 360), (4001881, 540), (4001862, 720), (4001882, 1440))

def startRanked():
    sm.setQRValue(DOJO_QUEST, "floor", str(0))
    sm.warpInstanceIn(925070000, 0, False)

if sm.getFieldID() == dojoHall:
    sm.setSpeakerID(2091011)
    selection = sm.sendNext("My master is the strongest person in Mu Lung, and YOU wish to challenge HIM? I have a feeling you'll regret this.\r\n#b"
                "#L0#I want to challenge Mu Lung Dojo.#l\r\n"
                "#L1#What is Mu Lung Dojo?#l\r\n"
                "#L2#What rewards can I get from Mu Lung Dojo?#l\r\n"
                "#L3#I want my ranking reward.#l\r\n"
                "#L4#I want to enter the Unity Training Center.#l\r\n")

    if selection == 0:
        if chr.getLevel() < MIN_LEVEL:
            sm.sendSayOkay("Come back when you're at least #bLv. " + str(MIN_LEVEL) + "#k. The Dojo would eat you alive.")
        elif DojoRanking is not None and DojoRanking.isCounting():
            sm.sendSayOkay("We're counting this week's ranking right now (Wednesday 11:30 PM to Thursday 12:30 AM). "
                           "Come back after that.")
        else:
            startRanked()
    elif selection == 1:
        sm.sendSayOkay("#e<Mu Lung Dojo>#n\r\nClimb as many of the #b100 floors#k as you can in #b15 minutes#k. Each floor has "
                       "a boss, from Mano on the 1st floor to the Awakened Mu Gong on the Top Floor, the 100th. You get a short rest between floors, "
                       "and your buffs are removed when you enter.\r\n\r\n"
                       "#e-Level#n: 105 and up\r\n#e-Attempts#n: unlimited\r\n#e-Players#n: solo")
    elif selection == 2:
        sm.sendNext("Every floor you clear gives #b10 Dojo Points#k, and every 10th floor #b110#k. Spend them at #bLao#k's shop.\r\n\r\n"
                    "You have #b" + str(sm.getQRValue(DOJO_QUEST, "point") or "0") + "#k Dojo Points.")
        sm.sendNext("Your best run of the week counts for the weekly ranking.\r\n\r\n"
                    "#e<Novice> Lv. 105-200#n\r\n"
                    "#b1st#k: #i1082394:# #t1082394#, #i3700306:# #t3700306#\r\n"
                    "#b2nd-3rd#k: #i1082660:# #t1082660#\r\n\r\n"
                    "#e<Master> Lv. 201+#n\r\n"
                    "#b1st#k: #i1082392:# #t1082392#, #i3700308:# #t3700308#\r\n"
                    "#b1st of your class#k: #i1082393:# #t1082393#, #i2023882:# #t2023882#, #i3700307:# #t3700307#\r\n"
                    "#b2nd-3rd of your class#k: #i1082394:# #t1082394#")
        sm.sendSayOkay("The ranking is counted from #rWednesday 11:30 PM to Thursday 12:30 AM#k; the Dojo is closed then. "
                       "Come and see me afterwards for your reward. Gloves and titles last a week.")
    elif selection == 3:
        if DojoRanking is None:
            sm.sendSayOkay("The ranking isn't ready yet. Come back later.")
        else:
            msg = DojoRanking.claim(chr)
            if msg is None:
                place = DojoRanking.lastWeekPlace(chr)
                if place is None:
                    sm.sendSayOkay("You didn't climb the Dojo last week. No ranking, no reward!")
                else:
                    sm.sendSayOkay("Last week you reached floor #b" + str(place[3]) + "#k (" + str(place[4]) +
                                   " s): #b" + str(place[1]) + "#k in your group, #b" + str(place[2]) +
                                   "#k in your class. That's not enough for a reward. Train harder!")
            else:
                sm.sendSayOkay(msg)
    elif selection == 4:
        held = [c for c in CHARMS if sm.hasItem(c[0])]
        if chr.getLevel() < MIN_LEVEL:
            sm.sendSayOkay("The Unity Training Center is for disciples of #bLv. " + str(MIN_LEVEL) + "#k and up.")
        elif not held or not hasattr(sm, "giveUnityTrainingExp"):
            sm.sendSayOkay("You need a #bUnity Training Center Entrance Charm#k to train there. #bLao#k sells them for Dojo Points.")
        else:
            menu = "Which charm will you use?\r\n#b"
            for i, c in enumerate(held):
                menu += "#L" + str(i) + "##i" + str(c[0]) + ":# #t" + str(c[0]) + "##l\r\n"
            pick = sm.sendNext(menu)
            if 0 <= pick < len(held):
                charm, minutes = held[pick]
                sm.consumeItem(charm, 1)
                sm.warpInstanceIn(UNITY_TRAINING_CENTER, 0)
                sm.setInstanceTime(minutes * 60, dojoHall)

elif sm.getFieldID() == UNITY_TRAINING_CENTER:
    sm.setSpeakerID(2091005)
    if sm.sendAskYesNo("Done training already? The time left on your charm is lost if you leave."):
        sm.warpInstanceOut(dojoHall)

elif sm.sendAskYesNo("You have cleared dojo #bfloor " + str(sm.getQRValue(DOJO_QUEST, "floor")) + "#k within \r\n #b" + str(sm.getQRValue(DOJO_QUEST, "time")) + " seconds#k. Are you giving up already?"):
    sm.warpInstanceOut(dojoExit)
