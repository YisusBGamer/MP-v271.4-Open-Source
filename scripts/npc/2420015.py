# 2420015 Master Thief Phantom (Mirror World Ariant) - Phantom's Letter (41064) is handed in to him.
if sm.hasQuest(41064):
    sm.questEndDialogue(41064, 2420015)
else:
    sm.setSpeakerID(2420015)
    sm.sendSayOkay("A good thief never lingers. Neither should you.")
sm.dispose()
