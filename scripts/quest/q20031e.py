# Manual Labor

# Constants
LIMBERT = 1106002
ITEM1 = 4033194
ITEM2 = 4033195

sm.setSpeakerID(LIMBERT)
# official lines (Quest.wz Say.img)
selection1 = sm.sendNext("That sure took you long enough. \r\n#L0##bUm... I found a letter in the attic. It wasn't opened... It's from some guy named Chromile.#l#k")

if selection1 == 0:
    sm.sendNext("What?! Who told you you could touch that?")
else:
    sm.dispose()

sm.consumeItem(ITEM1)
sm.consumeItem(ITEM2)
sm.giveItem(2001500, 10)
sm.giveItem(2001503, 10)
sm.completeQuestNoRewards(20031)
sm.sendNext("Don't you ever touch my stuff without my permission again. I'll have the police down here in an instant, I can tell you that! Now get outta my sight!")
sm.addLevel(2)
sm.lockInGameUI(True)
sm.localEmotion(4, 2000, False)
sm.sendDelay(2000)

sm.removeEscapeButton()
sm.setPlayerAsSpeaker()
sm.sendNext("Another great day with the old man...")

sm.sendSay("Huh? What's that?")

sm.showBalloonMsg("Effect/Direction7.img/effect/tuto/soul/0", 4000)
sm.sendDelay(5000)

sm.sendNext("It was like... a small shining light...")

sm.warp(913070002, 0)