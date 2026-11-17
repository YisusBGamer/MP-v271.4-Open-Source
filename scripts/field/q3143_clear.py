# Lion King's Castle: Fourth Tower (211060800), on entering
# "Looking Into the Lion King 1" (3143): Krag wants you to get to the Fourth Tower ("Get to the #m211060800# first
# so you can learn the layout of the place"); the quest waits for its record's expl >= 1 (Check.img).
if sm.hasQuest(3143) and sm.getQRValue(3143).find("expl=1") < 0:
    sm.setQRValue(3143, "expl", "1")
    sm.chatScript("You made it to the Fourth Tower. Report back to Krag.")
sm.dispose()
