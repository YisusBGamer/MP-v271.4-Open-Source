# Fantasy Theme World (Dylan & Delilah, 31363-31392). Official lines where the client has them; otherwise written from
# the quests' Quest.wz summaries / the NPCs' Npc.wz lines. Data: ftwgen/gen_ftw.py.
# Gyro Drop of Love (31377), start - Al Scapone: "Talk to Al Scapone to ride the Gyro Drop." The ride ends the quest
# ("What a thrilling ride on the Gyro Drop!"); Dylan's official lines after it (31378) tell how it went.
AL = 2192026  # Al Scapone, Extreme Station <2>


def ride():
    # the ride: the client's own Gyro Drop picture; Al Scapone's lines are his own Npc.wz lines
    sm.setSpeakerID(AL)
    sm.sendNext("Whoosh! Zoom! Are you ready for the thrill?")
    sm.onLayer(500, "gyro", 0, -80, 12, "Map/Effect3.img/fantasticThemePark/gyroDrop", 4, True, -1, False)
    # taken off by a timer: sendDelay never comes back from the client here, so the picture would stay on the screen
    # and the quest would not complete
    sm.invokeAfterDelay(3000, "offLayer", 500, "gyro", False)
    sm.completeQuest(parentID)
    sm.chatScript("What a thrilling ride on the Gyro Drop!")

if not sm.hasQuest(parentID) and not sm.hasQuestCompleted(parentID):
    sm.setSpeakerID(AL)
    if sm.sendAskAccept("It's the hair-raising Gyro Drop! Do you dare to ride the Gyro Drop?"):
        sm.startQuest(parentID)
        ride()
sm.dispose()
