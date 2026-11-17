# 1827 - [Training Course] Gumption
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: ESS (9075206)

sm.setSpeakerID(9075206)  # ESS
sm.sendNext("Did you complete the Gumption program?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("You completed the Gumption training course.")
