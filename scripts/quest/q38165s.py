# 38165 [Job Advancement] Blessings of the Fox God (Moonbeam, Fox Point Village). v265's Shade 1st job: after giving
# out the rice cakes, Moonbeam takes Shade to the Fox God, who blesses him; he gets the Fox Marble (1353100).
MOONBEAM = 3002000
FOX_MARBLE = 1353100
SWIFT_STRIKE = 25001000
VULPES_LEAP = 25001204

sm.setSpeakerID(MOONBEAM)
sm.sendNext("There you are! Done giving out the rice cakes? Good, because I've been waiting forever.")
sm.sendSay("Come on, I'm taking you to see the #bFox God#k. Every Pointy-Ear Fox gets the Fox God's blessing. "
           "Maybe even someone without ears or a tail can get one!")
if not sm.sendAskAccept("Ready to go see the Fox God?"):
    sm.dispose()
if not sm.hasQuest(parentID):
    sm.startQuest(parentID)
sm.setQRValue(parentID, "dir", "1")
sm.setPlayerAsSpeaker()
sm.sendNext("#b(A warm light surrounds you. The blessing of the Fox God is real... Power you thought you'd lost "
            "stirs inside you.)#k")
if chr.getJob() == 2005:
    sm.setJob(2500)
    sm.setSTR(4)
    sm.setINT(4)
    sm.setDEX(4)
    sm.setLUK(4)
    sm.setAP(4 + chr.getLevel() * 5)
    sm.addMaxHP(150)
    sm.addMaxMP(50)
    sm.giveSkill(SWIFT_STRIKE)
    sm.giveSkill(VULPES_LEAP, 4)
if not sm.hasItem(FOX_MARBLE) and sm.canHold(FOX_MARBLE, 1):
    sm.giveItem(FOX_MARBLE)
sm.completeQuestNoCheck(parentID)
sm.setSpeakerID(MOONBEAM)
sm.sendSayOkay("Whoa, it really worked! And the Fox God gave you a #bFox Marble#k too. We foxes use those to control "
               "spirits. Equip it, it's yours now!")
sm.dispose()
