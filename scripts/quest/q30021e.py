# 30021 - [Root Abyss Daily] Vanquishing Vellum
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Laviola (1064008)

if sm.tryCompleteQuest(parentID):
    sm.setSpeakerID(1064008)  # Laviola
    sm.sendNext("You saved our tribe. We will never forget your sacrifice! ")
