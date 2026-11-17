# 400020200 West Pantheon Border - Enemy Diversion (25866, Angelic Buster): Eskalade sent you here to back up the
# troops (QuestInfo 1). With the quest held and record 25910 not set, you see what the enemy sorcerers are up to
# (940010050, field/event_25866.py). Without either quest below: nothing happens.
if sm.hasQuest(25866) and sm.getQRValue(25910) != "1":
    sm.warpInstanceIn(940010050, 0)
# An Outside Perspective 2 (23655, Xenon): Fenelle asked you to look for anyone suspicious near the western barrier;
# reaching the West Pantheon Border counts (record 23716 = "1").
if sm.hasQuest(23655) and sm.getQRValue(23716) != "1":
    sm.startQuest(23716)
    sm.setQRValue(23716, "1")
    sm.chatScript("You spotted suspicious people near the western barrier. Report back to Fenelle.")
sm.dispose()
