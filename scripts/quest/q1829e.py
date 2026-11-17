# 1829 - [Training Course] Fighting Fit
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: ESS (9075208)

sm.setSpeakerID(9075208)  # ESS
sm.sendNext("Did you eliminate 20 #o9306101# monsters yet?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Fighting Fit program concluded.")
