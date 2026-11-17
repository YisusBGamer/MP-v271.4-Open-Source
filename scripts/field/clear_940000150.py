# 940000150 South Pantheon Border: every Specter is beaten - Buying Time (25753) waits for record 25800 = "1"
# (selfComplete: finish it from the quest window). Back to Pantheon shortly after, as the map has no exit.
if sm.hasQuest(25753) and sm.getQRValue(25800) != "1":
    sm.startQuest(25800)
    sm.setQRValue(25800, "1")
    sm.chatScript("As the shield came back up, all the Specters collapsed. Victory!")
sm.invokeAfterDelay(3000, "warpInstanceOut", 400000000, 0)
sm.dispose()
