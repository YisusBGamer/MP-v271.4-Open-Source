# The Five Paths - Cygnus 1st job advancement. Official lines (Quest.wz Say.img).
# The quest has no end NPC and no end conditions, and the five path quests (20861-20865, at the Chief Knights) need
# it completed. Neinheart's approval completes it, and the Chief Knights then offer their paths.
if sm.questStartDialogue(parentID, 1101002) and not sm.hasQuestCompleted(parentID):
    sm.completeQuestNoRewards(parentID)
if not sm.hasQuestCompleted(36467):
    sm.completeQuestNoRewards(36467)  # the path quests also need the newer tutorial's last quest
sm.dispose()
