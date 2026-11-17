# 62307 - [Spirit Fishin' Hole] Dangling Bait
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Corkie (9310638), taken from the other quest phase

if sm.tryCompleteQuest(parentID):
    sm.setSpeakerID(9310638)  # Corkie
    sm.sendNext("Use the #i3015635# #t3015635# in your inventory's Set-up tab to really start fishin'!")
