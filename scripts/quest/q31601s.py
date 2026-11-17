# 31601 - Backstage Pass (Angelic Buster), from Tiron (3001007). Battlefield quest: accepting moves you to the
# occupied HQ; winning it (portal/finish_warZone1.py) sets record 31602 and Edea reports it there.
sm.setSpeakerID(3001007)  # Tiron
if sm.sendAskAccept(u"Looks like the Heliseum Reclamation HQ is danger because of the attacks from #rSpecters#k. It's your time to shine! Eliminate the enemies at the rear.\r\n#r#e(If you accept, you will automatically move to the relevant field.)"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.warpInstanceIn(401070000)  # Occupied Reclamation HQ
else:
    sm.sendNext("Please help us! *sniff*")
sm.dispose()
