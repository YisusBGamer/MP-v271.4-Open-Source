# 14422 - A Gentleman's Candy 1
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Masked Gentleman (9000279)

if sm.tryCompleteQuest(parentID):
    sm.setSpeakerID(9000279)  # Masked Gentleman
    sm.sendNext("Excellent. Everything is going smoothly. ")
