# 140090300
PURUN = 1202004

sm.setSpeakerID(PURUN)
if sm.sendAskYesNo("Seeing your face tells me you didn't get a whole lot out of defeating that monster, but don't worry. I am sure things will work out. Here's a potion for you. Good luck!"):
    sm.giveItem(2000022, 10)
    sm.giveItem(2000023, 10)
    sm.giveExp(57)
    sm.completeQuest(parentID)

    sm.setPlayerAsSpeaker()
    sm.sendNext("#b(Even if you're really the hero everyone says you are... What good are you without any skills?)#k")
else:
    sm.sendNext("What? You don't want the potion?")
    sm.dispose()