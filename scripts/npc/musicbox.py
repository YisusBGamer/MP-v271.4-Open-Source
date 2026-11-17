# 1033110 Music Box in Elluel (101050000). Moonie's Depression (Mercedes 24101, record 24103 = "1"): lift Moonie's
# spirits with a happy tune. The tune plays for you only.
MOONIE_RECORD = 24103

if sm.hasQuest(24101) and sm.getQRValue(MOONIE_RECORD) != "1":
    sm.changeBGM("Bgm25.img/SoundOfElf", 0, 0)
    sm.startQuest(MOONIE_RECORD)
    sm.setQRValue(MOONIE_RECORD, "1")
    sm.chatScript("A cheerful tune fills Elluel. Go see how Moonie is doing.")
else:
    sm.sendSayOkay(sm.getRandomNpcDefaultLine(1033110))
sm.dispose()
