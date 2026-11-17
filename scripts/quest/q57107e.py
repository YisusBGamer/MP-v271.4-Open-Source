# 57107 - Adaptation 2
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mouri Motonari (9130008)

sm.setSpeakerID(9130008)  # Mouri Motonari
sm.sendNext("Back again? At least you're dedicated.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("I'd heard you were a master of Battoujutsu, but you haven't shown me anything to be impressed with. You should continue your training.")
