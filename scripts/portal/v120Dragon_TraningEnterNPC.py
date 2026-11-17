# Portal script v120Dragon_TraningEnterNPC.
# Gold Dragon Dojo Drill Hall (Beasts of Fury): to one of the training grounds.
GROUNDS = (("Boulder Gorge Training Ground", 706041001), ("Bamboo Training Ground", 706041002))
text = "Which training ground?\r\n\r\n#b"
for i in range(len(GROUNDS)):
    text += "#L{}#{}#l\r\n".format(i, GROUNDS[i][0])
sel = sm.sendNext(text)
if 0 <= sel < len(GROUNDS):
    sm.warp(GROUNDS[sel][1], 0)
sm.dispose()
