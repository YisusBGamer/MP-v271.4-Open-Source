# 66095 - [BUGCAT CAPOO] CAPOO Wants to Eat Everything!
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: CAPOO (9401384)

if sm.tryCompleteQuest(parentID):
    sm.setSpeakerID(9401384)  # CAPOO
    sm.sendNext("You already got what you want to give to CAPOO! \r\nGo give it to CAPOO.")
