# 1734 - Top Hats and Tigers 1
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Spiegelmann (9074300)

sm.setSpeakerID(9074300)  # Spiegelmann
sm.sendNext("Where's my plants?!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("The air's gonna be great in here!")
