# 30019 - [Root Abyss Daily] Defeat Crimson Queen
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Disenchanted Pig (1064009)

if sm.tryCompleteQuest(parentID):
    sm.setSpeakerID(1064009)  # Disenchanted Pig
    sm.sendNext("Oink oink oink! (Thank you!)")
