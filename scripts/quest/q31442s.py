# 31442 - Heliseum or Bust (Kaiser), from Edea (3001000). Battlefield quest: accepting moves you to the
# battlefield; winning it (portal/finish_warZone1.py) sets record 31556 and leads to the Downtown Black Market.
sm.setSpeakerID(3001000)  # Edea
if sm.sendAskAccept("Destroy all Specters and enter the central region of Heliseum.\r\n#r#e(If you accept, you will automatically move to the relevant field.)"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(401071000)  # Occupied Heliseum Downtown
else:
    sm.setPlayerAsSpeaker()
    sm.sendNext("I should just ignore it.")
sm.dispose()
