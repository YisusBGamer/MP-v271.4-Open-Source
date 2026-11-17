# 30020 - [Root Abyss Daily] Tribute for Vellum
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Laviola (1064008)

if sm.tryCompleteQuest(parentID):
    sm.setSpeakerID(1064008)  # Laviola
    sm.sendNext("Thank you, you saved my life. ")
