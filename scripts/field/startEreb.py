# Ereve (130000000): the Chief Knights' path quests (20861-20865) need the newer tutorial's last quest (36467) done
if chr.getJob() == 1000 and not sm.hasQuestCompleted(36467):
    sm.completeQuestNoRewards(36467)
