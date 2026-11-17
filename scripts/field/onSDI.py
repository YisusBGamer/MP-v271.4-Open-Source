# Snowy Forest, Slumbering Dragon Island (914100010): arriving on the island after Shipshape (22579) is what lets
# Mir sense the other dragon (quest record 22599 = 1, needed to start Slumbering Dragon Island 22580, Evan).
if sm.hasQuestCompleted(22579) and not sm.hasQuestCompleted(22580) and sm.getQRValue(22599) in (None, "", "0"):
    sm.setQRValue(22599, "1", False)
