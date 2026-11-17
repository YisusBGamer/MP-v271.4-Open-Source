# 14211 - [Attendance] Guard the Crops!
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("Protect our new crops by getting rid of #b#e200 monsters around your level#k#n! Good luck!\r\n\r\n#nLet's keep it to monsters that are #r10 levels below you or 20 levels above you#k.")
sm.tryCompleteQuest(parentID)
