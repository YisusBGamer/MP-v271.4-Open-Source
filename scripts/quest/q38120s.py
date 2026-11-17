# 38120 [Hotel Arcus] 'Til We Meet Again - start (Archelon, Hotel Arcus Saloon)
ARCHELON = 3005016

sm.setSpeakerID(ARCHELON)
sm.setBoxChat()
sm.sendNext("You have done much for the people of this hotel, traveler.")
sm.sendNext("My memories are slowly returning. There is somewhere I must be, though I cannot yet say where.")
if sm.sendAskAccept("Before you go, say goodbye to #b#p3005001##k. He worries more than he lets on."):
    sm.startQuest(parentID)
sm.dispose()
