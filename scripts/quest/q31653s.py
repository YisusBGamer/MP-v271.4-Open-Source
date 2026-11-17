# 31653 - Edea's Magical Surprise (Angelic Buster), from Edea (3001000). Battlefield quest: accepting moves you to the
# battlefield; winning it (portal/finish_warZone1.py) sets record 31654.
sm.setSpeakerID(3001000)  # Edea
if sm.sendAskAccept(u"#p3001000#'s magic has caused confusion among the enemy's ranks. It's the perfect opportunity for a certain goddess of the battlefield to step in and beat up some enemies.\r\n#r#e(If you accept, you will automatically move to the relevant field.)"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(401073200)  # Occupied Foggy Valley
else:
    sm.sendNext("The enemies are still too close!")
sm.dispose()
