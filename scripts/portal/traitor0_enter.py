# id 6 (traitor_enter), field 400010300
sm.startQuest(25915)
# Enemy Mine (25858) waits for this record to say "1" (the recording only starts it, empty).
if sm.hasQuest(25858):
    sm.setQRValue(25915, "1")
sm.warp(400010500)
