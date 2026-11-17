# 1826 - [Training Course] Dexterity Training
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: ESS (9075205)

sm.setSpeakerID(9075205)  # ESS
sm.sendNext("Did you activate all of the Control Devices in Link 6?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("You completed the dexterity training course.")
