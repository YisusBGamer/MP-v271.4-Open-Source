# 310040210 Path Behind the Mine. Gesture of Good Will (Cygnus Knights 20741, record 20743 = "1"): Claudine reads the
# letter once you find the laboratory entrance behind the mine (its in00 here). Reaching it counts.
if sm.hasQuest(20741) and sm.getQRValue(20743) != "1":
    sm.startQuest(20743)
    sm.setQRValue(20743, "1")
    sm.chatScript("You hear a voice from beyond the rocks: this is the entrance to the laboratory. Go back to Claudine.")
sm.dispose()
