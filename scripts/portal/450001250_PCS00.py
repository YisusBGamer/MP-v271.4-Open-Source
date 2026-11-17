# id 2 (PCS00), field 450001250
if sm.hasQuestCompleted(34120) and sm.getChr().getLevel() >= 210 and not sm.hasQuestCompleted(34200):
    if sm.hasQuest(34200):
        sm.warp(450002021, 1)
    else:
        sm.warpInstanceIn(450002200)  # the fall into Chu Chu Island (enter_450002200), then on to Muto
elif sm.hasQuestCompleted(34200):
    sm.warp(450002015)
else:
    sm.progressMessageFont(3, 20, 20, 0, "You must be Lv. 210 or higher to enter this area.")
    sm.warp(450001250)
