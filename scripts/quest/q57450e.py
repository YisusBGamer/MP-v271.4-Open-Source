# 57450 - Questionable Allegiance
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Honda Tadakatsu (9130027)

sm.setSpeakerID(9130027)  # Honda Tadakatsu
sm.sendNext("Wait!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Nobody else seems to be following us.")
