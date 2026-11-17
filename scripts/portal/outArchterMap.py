# 100000201
# Assailant 1 (Mercedes 24071): Athena asks you to wait outside, and a young Black Wing attacks you as you step out
# (record 24095 = 1). Athena checks on you when you go back in.
if sm.hasQuest(24071) and sm.getQRValue(24095) != "1":
    sm.startQuest(24095)
    sm.setQRValue(24095, "1")
    sm.chatScript("As soon as you stepped outside, you were attacked by a young boy wearing a robe! Go back to Athena Pierce.")
sm.warp(100000000, 17)
