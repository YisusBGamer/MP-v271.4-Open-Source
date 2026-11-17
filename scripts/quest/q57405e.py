# 57405 - Adaptation 2
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mouri Motonari (9130008)

sm.setSpeakerID(9130008)  # Mouri Motonari
sm.sendNext("Back again? At least you're dedicated.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Ah, your body. It's fine... I mean it's looking better... I mean you look strong.")
