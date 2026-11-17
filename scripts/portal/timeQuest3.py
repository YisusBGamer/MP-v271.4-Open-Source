# 270010300 Memory Lane 3 -> Memory Lane 4
# Demon Slayer / Avenger on Investigating the Temple of Time (23291) pass through the Resting Spot of Memory 3
# (927000200, onUserEnter enter_23291) on the way; its in00 leads on to Memory Lane 4.
if not sm.hasQuestCompleted(3503): # time lane quest
    sm.chat("You have not completed the appropriate quest to enter here.")
elif sm.hasQuest(23291) and sm.getQRValue(23293) != "1":
    sm.warp(927000200, 0)
else:
    sm.warp(270010400, 5)
