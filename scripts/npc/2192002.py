# Fantastic Cable Car
# 2192002
# Also rides back to Ludibrium (its eastern end, where it leaves from).
sm.setSpeakerID(2192002)
if sm.getFieldID() in (222100000, 223000000):
    if sm.sendAskYesNo("Come one, come all! See the Fantasy Theme Park\r\n\r\n#b(Ride the cable car back to Ludibrium?)#k"):
        sm.warp(220000000, 25)
else:
    sm.sendSayOkay("Come one, come all! See the Fantasy Theme Park")
sm.dispose()
