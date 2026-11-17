# 36329 The Mysterious Voice Returns - end (from the quest list).
sm.setSpeakerID(1541063)
sm.setQRValue(parentID, "talk", "1")
if sm.completeQuest(parentID):
    sm.sendSayOkay("Go to the chiefs of #bEl Nath#k. They will show you the way forward.")
sm.dispose()
