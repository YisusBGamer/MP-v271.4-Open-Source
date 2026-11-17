# 1836 - [Selectable Program] Energy Distribution
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: ESS (9075206)

sm.setSpeakerID(9075206)  # ESS
sm.sendNext("Did you bring 20 #t4033458# items?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Energy distributed in Link 7.")
