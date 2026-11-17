# 32352 - Fixing Up Buildings
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Tess (1520050)

sm.setSpeakerID(1520050)  # Tess
sm.sendNext("About time! I've been waiting for hours! You expect me to reconstruct these buildings with my bare hands?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("All right! Time to get hammering!")
