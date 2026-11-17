# 914000300
LOST_KID = 1209006

sm.setSpeakerID(LOST_KID)
if sm.sendAskAccept("I was really scared... *Sniff sniff* Please take me back to Athena Pierce!"):
    sm.startQuest(parentID)
    sm.warp(914000500, 1)
else:
    sm.sendNext("*Sob* Aran has declined my request!")
    sm.dispose()