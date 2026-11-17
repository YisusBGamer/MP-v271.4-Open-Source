# Momijigaoka: the training grounds for Adaptation 1 / 2 (Hayato 57106 / 57107, Kanna 57404 / 57405).
# Kanna's copies of the quests lead to the same training grounds.
if sm.hasQuest(57106) or sm.hasQuest(57404):
    sm.warp(807030000, 0)
elif sm.hasQuest(57107) or sm.hasQuest(57405):
    sm.warp(807030100, 0)
else:
    sm.warp(807030200, 0)
