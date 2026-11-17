# 11342 - Daily Watermelon Pots
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gaga (9000021)

if sm.tryCompleteQuest(parentID):
    sm.setSpeakerID(9000021)  # Gaga
    sm.sendNext("You got your pot, right? I am only giving out #bone a day#k, so if you come back tomorrow, you can get another #i2440004#. Ahh, aren't watermelons the best part of summer?\r\n")
