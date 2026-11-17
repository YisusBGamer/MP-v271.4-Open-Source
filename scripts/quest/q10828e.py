# 10828 - Gold Richie's Packages
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Gold Richie (2084001)

sm.setSpeakerID(2084001)  # Gold Richie
sm.sendNext("Did you find the lost items? And have they been delivered?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Excellent! I'm glad I asked for your help.")
