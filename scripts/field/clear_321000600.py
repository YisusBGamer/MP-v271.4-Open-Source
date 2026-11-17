# 321000600 Umbra Temple - Zero's tutorial, last scene: Alpha and Beta become Zero
BETA = 2400006
RHINNE = 2144000
PROLOGUE_QUESTS = (40000, 40001, 40002, 40003, 40004, 40900)
sm.lockInGameUI(True, False)
sm.sendDelay(800)
sm.setSpeakerID(BETA)
sm.removeEscapeButton()
sm.sendNext("...You came. I always knew you would. You're the other half of me, Alpha.")
sm.setPlayerAsSpeaker()
sm.sendSay("Alpha... That's my real name, isn't it? And you're Beta.")
sm.setSpeakerID(RHINNE)
sm.sendSay("My children. Two souls, one body - together you are Zero, the Transcendent of Time. The Black Mage seeks "
           "to steal my power. Find me, and stop him.")
sm.setSpeakerID(BETA)
sm.sendSay("Let's go, Alpha. Our temple is waiting.")
for q in PROLOGUE_QUESTS:
    if not sm.hasQuestCompleted(q):
        sm.completeQuestNoRewards(q)
if sm.getQRValue(40065) != "1":
    sm.setQRValue(40065, "1")  # lets Chapter 1 (40901) start
sm.lockInGameUI(False)
sm.warpInstanceOut(320000000, 0)
sm.chatScript("Welcome to Zero's Temple. Talk to Pietta to open the first chapter of your story.")
