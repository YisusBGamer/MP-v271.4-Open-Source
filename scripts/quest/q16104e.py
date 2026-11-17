# 16104 - [Omega Sector] Defeat OMNI-CLN and Get Your Gift!
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
sm.sendNext("Return after defeating the cleaning robot OMNI-CLN in Omega Sector!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Return after defeating the cleaning robot OMNI-CLN in Omega Sector!")
