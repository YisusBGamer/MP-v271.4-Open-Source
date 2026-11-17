# id 120041900 (Gold Beach : Shady Beach), field 120041900
sm.lockInGameUI(True, True)
sm.showFieldEffect("phantom/back", 0)
sm.showFieldEffect("goldBeach/blackSlimeBoss", 0)
sm.sendDelay(3000)
sm.lockInGameUI(False, True)
sm.startQuest(26511)

# A Letter Across Ages (Shade 38040, record 38040 clear=clear): the message in a bottle is floated from this beach.
# The quest has no NPC to hand it in to.
if sm.hasQuest(38040) and sm.getQRValue(38040, "clear") != "clear":
    sm.setQRValue(38040, "clear", "clear")
    if sm.completeQuest(38040):
        sm.chatScript("You sent your message in a bottle to Freud. You feel a little better.")
