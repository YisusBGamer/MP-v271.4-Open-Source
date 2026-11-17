# [Labyrinth of Suffering] Crash Landing Report (36762), handing it in
SOLDIER = 3003787

if sm.hasQuest(parentID):
    sm.setSpeakerID(SOLDIER)
    if sm.getQRValue(parentID, "MazeAlarm") != "3":
        sm.sendSayOkay("Place the #bbarrier stones#k near the #m450011120# where the #rshining door#k is.")
    else:
        sm.sendNext("All the barrier stones have been installed, so we're as prepared as we can be for monster attacks.")
        sm.completeQuest(parentID)
sm.dispose()
