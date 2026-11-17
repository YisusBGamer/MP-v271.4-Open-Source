# Al Scapone (2192026), Extreme Station <2> - clicking him offers the Gyro Drop of Love ride (31377). The quest is an
# auto-start one (the lightbulb after 31390) and the lightbulb doesn't come back after a forfeit, so he offers it
# again. The ride is the one in q31377s / q31377e.
QUEST = 31377
AL = 2192026


def ride():
    sm.setSpeakerID(AL)
    sm.sendNext("Whoosh! Zoom! Are you ready for the thrill?")
    sm.onLayer(500, "gyro", 0, -80, 12, "Map/Effect3.img/fantasticThemePark/gyroDrop", 4, True, -1, False)
    sm.invokeAfterDelay(3000, "offLayer", 500, "gyro", False)
    sm.completeQuest(QUEST)
    sm.chatScript("What a thrilling ride on the Gyro Drop!")


sm.setSpeakerID(AL)
if sm.hasQuest(QUEST):
    if sm.sendAskYesNo("Talk to me again when you're ready. ...Ready now?"):
        ride()
elif not sm.hasQuestCompleted(QUEST) and sm.hasQuestCompleted(31390) and sm.getChr().getLevel() >= 120:
    if sm.sendAskAccept("It's the hair-raising Gyro Drop! Do you dare to ride the Gyro Drop?"):
        sm.startQuest(QUEST)
        ride()
else:
    sm.sendSayOkay("It's the hair-raising Gyro Drop! Come back for a ride any time.")
sm.dispose()
