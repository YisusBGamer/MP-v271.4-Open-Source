# 38203 [Karote] Tower Chaser - start (Rusty, Hotel Arcus Saloon)
RUSTY = 3005001

sm.setSpeakerID(RUSTY)
sm.setBoxChat()
sm.sendNext("Karote? Heh, haven't heard that name in a while. Most Seekers figure it's a tall tale and don't bother.")
sm.sendNext("There's one who's been chasing it for years, though. #b#p3005210##k. Never stops talking about it.")
if sm.sendAskAccept("He's usually up on the #b#m410005000##k. Go on up and ask him yourself."):
    sm.startQuest(parentID)
sm.dispose()
