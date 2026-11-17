# 51216 - Clearing Party Quests
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Principal Researcher Bass (9250134)

sm.setSpeakerID(9250134)  # Principal Researcher Bass
sm.sendNext("You've cleared one of various party quests.")
sm.tryCompleteQuest(parentID)
