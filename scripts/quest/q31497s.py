# 31497 - Buying Some Time (Kaiser), from Cartalion (3001102). Battlefield quest: accepting moves you to the
# battlefield; winning it (portal/finish_warZone1.py) sets record 31565.
sm.setSpeakerID(3001102)  # Cartalion
if sm.sendAskAccept("Battle Specters while #b#p3001000##k removes the seal on the castle gate.\r\n#r#e(If you accept, you will automatically move to the relevant field.)"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(401071600)  # Occupied Heliseum Downtown
sm.dispose()
