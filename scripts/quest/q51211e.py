# 51211 - Talk to Principal Researcher Bass
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Principal Researcher Bass (9250134), taken from the other quest phase

sm.setSpeakerID(9250134)  # Principal Researcher Bass
sm.sendNext("To show you our appreciation for listening, we will issue you a medal. Use it to upgrade your suit.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("For a more detailed explanation. Please talk to me again and ask about each subject.")
