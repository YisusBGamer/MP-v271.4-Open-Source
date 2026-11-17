# 211060010 Lion King's Castle: Castle Entrance.
# Demon Slayer / Avenger: Von Leon, the Lion King (23272, own record 23272 = 1, selfComplete). The Commander's old
# secret passage from here is blocked up.
if sm.hasQuest(23272) and sm.getQRValue(23272) != "1":
    sm.setQRValue(23272, "1")
    sm.chatScript("Von Leon has blocked up his old secret passage. Complete 'Von Leon, the Lion King' from the quest window.")

# [Lion King's Castle] The Lion King's Castle (1551): entering the castle with J (own record quest=1); J waits in the
# First Tower (211060200), where it's handed in.
if sm.hasQuest(1551) and sm.getQRValue(1551, "quest") != "1":
    sm.setQRValue(1551, "quest", "1")
    sm.chatScript("You entered the Lion King's Castle, but the way forward is blocked. Talk to J.")
