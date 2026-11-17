# 31617 - When in Doubt, Patrol About (Angelic Buster, started from the quest list). Battlefield quest: accepting
# moves you to the battlefield; winning it (portal/finish_warZone1.py) sets record 31618.
sm.setPlayerAsSpeaker()
if sm.sendAskAccept("Patrol everywhere thoroughly. Punish any enemies you find then report to #b#p3001000# at the Heliseum Reclamation HQ #k once you are done patrolling!\r\n#r#e(If you accept, you will automatically move to the relevant field.)"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(401072200)  # Occupied Twisted Forest
sm.dispose()
