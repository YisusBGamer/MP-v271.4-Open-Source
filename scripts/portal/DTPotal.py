# Portal script DTPotal.
# Mu Lung: the way to the dojo you joined in Beasts of Fury (Gold Dragon or Red Tiger).
RED_TIGER = range(56583, 56607)
DOJO = range(56562, 56610)
if any(sm.hasQuest(q) or sm.hasQuestCompleted(q) for q in RED_TIGER):
    sm.warp(706041005, 3)
elif any(sm.hasQuest(q) or sm.hasQuestCompleted(q) for q in DOJO):
    sm.warp(706041000, 3)
else:
    chr.chatMessage("Only members of the Gold Dragon or Red Tiger Dojo may pass. (Beasts of Fury, level 140)")
sm.dispose()
