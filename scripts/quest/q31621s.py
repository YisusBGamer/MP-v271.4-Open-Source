# 31621 - The Cannonball's Wake (Angelic Buster), from Edea (3001000). Battlefield quest: accepting moves you to the
# battlefield; winning it (portal/finish_warZone1.py) sets record 31622.
sm.setSpeakerID(3001000)  # Edea
if sm.sendAskAccept("Eliminate the enemies who are running away and leaving their supply base behind.\r\n#r#e(If you accept, you will automatically move to the relevant field.)"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(401072500)  # Occupied Twisted Forest
sm.dispose()
