# 57106 - Adaptation 1
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mouri Motonari (9130008)

sm.setSpeakerID(9130008)  # Mouri Motonari
sm.sendNext("So, you've returned. That's a start.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("You need more training if you're to be of any substantial help towards my cause.")
