# 940000090 Near West Sanctum - Reclaim the Stolen Relic! (25744, Kaiser): the priests didn't have the Relic.
# 25744 waits for record 25805 to say "1" (handed in at Cartalion). The exit leads back to the West Sanctum.
if sm.hasQuest(25744) and sm.getQRValue(25805) != "1":
    sm.startQuest(25805)
    sm.setQRValue(25805, "1")
    sm.chatScript("The Nefarious Priests don't have the Relic! Tell Cartalion what happened.")
sm.dispose()
