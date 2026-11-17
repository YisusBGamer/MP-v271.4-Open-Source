# 940010050 West Pantheon Border (scene) - Enemy Diversion (25866, Angelic Buster): the enemy sorcerers are summoning a
# horde of monsters (QuestInfo 2). Sets record 25910, which Cartalion's end check waits for, then back to Pantheon to
# report (the map has no exit portal).
if sm.hasQuest(25866) and sm.getQRValue(25910) != "1":
    sm.startQuest(25910)
    sm.setQRValue(25910, "1")
    sm.chatScript("The enemy sorcerers are summoning a horde of monsters! They must be stopped. Tell Cartalion!")
    sm.invokeAfterDelay(4000, "warpInstanceOut", 400000000, 0)
else:
    sm.warpInstanceOut(400000000, 0)  # Pantheon
sm.dispose()
