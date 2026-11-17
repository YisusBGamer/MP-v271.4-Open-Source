# Transport Cart (9400475), Badlands Taxi on Eluna Prime
STOPS = [867118200, 867118800, 867119000, 867119350]

here = sm.getFieldID()
stops = [m for m in STOPS if m != here]
text = "Where to?\r\n#b"
for i, m in enumerate(stops):
    text += "#L%d##m%d##l\r\n" % (i, m)
pick = sm.sendNext(text)
if 0 <= pick < len(stops):
    sm.warp(stops[pick], 0)
sm.dispose()
