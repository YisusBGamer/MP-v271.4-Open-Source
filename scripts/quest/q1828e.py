# 1828 - [Training Course] Discernment
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: ESS (9075207)

sm.setSpeakerID(9075207)  # ESS
sm.sendNext("Did you defeat #o9306200#?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("You completed the Discernment training course.")
