# 1824 - [Training Course] Speed Up
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: ESS (9075203)

sm.setSpeakerID(9075203)  # ESS
sm.sendNext("Did you eliminate 20 #o9306006# monsters?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Speed training program concluded.")
