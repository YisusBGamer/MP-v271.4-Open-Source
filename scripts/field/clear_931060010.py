# Beryl beaten in Edelstein Outskirts 1 (931060010): Exposed (23639, Xenon) records the fight (quest record
# 23719, which the quest checks) and moves on to where the others are waiting.
if sm.hasQuest(23639) and sm.getQRValue(23719) != "1":
    sm.setQRValue(23719, "1", False)
    chr.chatMessage("Beryl retreats... but she said she can track you through your Pulse.")
    sm.warpInstanceOut(931060011, 1)
