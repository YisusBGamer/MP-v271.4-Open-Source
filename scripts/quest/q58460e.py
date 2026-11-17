# 58460 - Attack On Attendance Check
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("Your attendance has been checked for today!\r\n")
sm.tryCompleteQuest(parentID)
