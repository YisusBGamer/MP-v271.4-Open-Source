# 940000170 South Pantheon Border - end of Start the Counterattack (25756, Kaiser): Magnus himself appears (QuestInfo
# 2). Sets record 25801, which Cartalion's end check waits for, then back to Pantheon (the map has no exit portal).
if sm.hasQuest(25756) and sm.getQRValue(25801) != "1":
    sm.startQuest(25801)
    sm.setQRValue(25801, "1")
    sm.chatScript("Despite your success against the invaders, Magnus himself appeared to take you, Velderoth, and the "
                  "Angelic Buster down a notch. Even in his weakened state, he mopped the floor with you.")
    sm.invokeAfterDelay(5000, "warpInstanceOut", 400000000, 0)
else:
    sm.warpInstanceOut(400000000, 0)  # Pantheon
sm.dispose()
