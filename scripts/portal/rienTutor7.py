# 140010000
# v265's Aran training (36903-36911) sends the player past here for Basic Training 2 (36907, Murukun in 140010200);
# 21014 belongs to the old tutorial.
if sm.hasQuestCompleted(21014) or sm.hasQuestCompleted(36906) or sm.getChr().getJob() != 2000:
    sm.warp(140010100, 2)
else:
    sm.systemMessage("You must complete the quest before proceeding to the next map.")
