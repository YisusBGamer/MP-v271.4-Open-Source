# 31455 - Rival Revival (Kaiser, started from the quest list). Battlefield quest: accepting moves you to the
# battlefield; winning it (portal/finish_warZone1.py) sets record 31549.
sm.setPlayerAsSpeaker()
if sm.sendAskAccept(u"#r#p3000007##k took his Specters toward the castle in Heliseum. Don\u2019t lose him!\r\n#r#e(If you accept, you will automatically move to the relevant field.)"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(401071400)  # Occupied Foggy Valley, then Heliseum Downtown
sm.dispose()
