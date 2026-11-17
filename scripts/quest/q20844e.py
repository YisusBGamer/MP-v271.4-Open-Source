# 20844 - Knight Maintenance
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Hawkeye (1101007)

sm.setSpeakerID(1101007)  # Hawkeye
sm.sendNext("Well? Did you try hunting after you used your new SP? Am I getting ahead of myself here?\r\n")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("You should be ready for a good hunt now. Neinheart has some new mission for you. Go talk to him.")
