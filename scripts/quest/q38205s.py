# 38205 [Karote] The Tower's Remains - start (Nicky, The Unending Tower)
NICKY = 3005249

sm.setSpeakerID(NICKY)
sm.setBoxChat()
sm.sendNext("It fell... again. We were so close.")
sm.sendNext("But it always rises again from what's left. Something in the rubble must be what keeps it going.")
if sm.sendAskAccept("Let's look through the remains. Head up to the #b#m410005002##k. Belle is already there."):
    sm.startQuest(parentID)
sm.dispose()
