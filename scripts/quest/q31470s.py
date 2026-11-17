# 31470 - The Cold Sting of Friendship (Kaiser, started from the quest list). Battlefield quest: accepting moves you
# to the battlefield; winning it (portal/finish_warZone1.py) sets record 31557.
sm.setPlayerAsSpeaker()
if sm.sendAskAccept(u"#p3000007# won\u2019t listen to reason. It\u2019s time to take him down.\r\n#r#e(If you accept, you will automatically move to the relevant field.)"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(401071600)  # Occupied Heliseum Downtown
sm.dispose()
