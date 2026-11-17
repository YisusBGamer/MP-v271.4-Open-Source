# 31673 - Edea Rage! (Angelic Buster), from Edea (3001100). Edea's order replaces the castle-gate patrol; the end
# check needs record 31684 as well as the 200 + 200 kills, so it's set when she gives the order.
sm.setSpeakerID(3001100)  # Edea
if sm.sendAskAccept(u"Forget patrolling the castle gate. Eliminate #b200 #o2400111##k monsters at the #m401040400# and #b200 #o2400112##k monsters at the #m401040500#. 200 of each should be no problem given how powerful you've become!"):
    sm.startQuest(parentID)
    if sm.hasQuest(parentID):
        sm.startQuest(31684)
        sm.setQRValue(31684, "1")
sm.dispose()
