# [Labyrinth of Suffering] Crash Landing Report (36762), the Cygnus Soldier at the Crash Site: the barrier stones
# (MazeAlarm 3) go up by the shining door
SOLDIER = 3003787

if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.setSpeakerID(SOLDIER)
        sm.sendNext("The moment you entered the black sun, your ship crashed. It's difficult to tell where the path is... "
                    "if there even is a path... Even your location is a mystery.")
        sm.sendNext("Your first priority is to defend the area against monster attacks.\r\nOnce you've accomplished that, "
                    "you can focus on searching for the others.")
        if sm.sendAskAccept("Place the #bbarrier stones#k near the #m450011120# where the #rshining door#k is."):
            sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.setPlayerAsSpeaker()
        sm.sendNext("(You set the barrier stones around the shining door, one by one.)")
        sm.setQRValue(parentID, "MazeAlarm", "3")
sm.dispose()
