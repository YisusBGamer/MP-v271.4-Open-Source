# 140000000
LILIN = 1201000

# official lines (Quest.wz Say.img): your thoughts as you touch the polearm
sm.setSpeakerID(1201001)
sm.sendNext("#b(You touch the #p1201001#. The polearm is supposed to be ice cold, but it feels so...warm. Makes you feel like your old memories are starting to return.)#k")
sm.sendNext("#b(...the hero that wielded the polearm was also the master of melee combat, with amazing strength and stamina...)#k")
sm.sendNext("#b(...the hero had high levels of STR but also some DEX, which meant the hero moved with agility...)#k")
sm.sendNext("#b(Is this from your memories or the memories of a fellow hero...? In order to find out for sure, you have to touch the #p1201001# one more time.)#k")
if sm.sendAskAccept("#b(Are you certain that you were the hero that wielded the #p1201001#? Yes, you're sure. You better grab the #p1201001# really tightly. Surely it will react to you.)#k"):
    sm.giveItem(1142129)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)
    sm.removeSkill(20000297)
    sm.setJob(2100)
    sm.setSTR(4)
    sm.setINT(4)
    sm.setDEX(4)
    sm.setLUK(4)
    sm.setAP(4 + chr.getLevel() * 5)
    # 444
    sm.removeSkill(20001296)
    sm.giveSkill(20001296)
    sm.chatScript("You learned the Back to Rien skill.")

    sm.removeEscapeButton()
    sm.setPlayerAsSpeaker()
    sm.sendNext("#b(You might be starting to remember something...)#k")

    sm.lockInGameUI(True, False)
    sm.warp(914090100, 0)
else:
    sm.sendNext("#b(You need to think about this for a second...)#k")