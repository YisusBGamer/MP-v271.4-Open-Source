# 38003 - Welcome to the Neighborhood 
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Silver (3002005)

sm.setSpeakerID(3002005)  # Silver
sm.sendNext("How did everything go? Goodness, you look worn out. ")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Ahh, you got some housewarming gifts! See, foxes can be quite kind to those they do not intend to eat. ")
