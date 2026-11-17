# 5810 - Cassandra's Christmas Present
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("Hunt 300 monsters that are around your level! Here are the rewards you can receive based on your level when you open the box. Higher level tiers will also receive the rewards of lower level tiers.\r\n\r\n\r\n#e#bLv.30: \r\n#i1142373# #t1142373#\r\n\r\n#bLv.30: \r\n#i2432354# #t2432354# \r\n\r\n#bLv.30: \r\n#i2023137# #t2023137# x300 \r\n\r\n#bLv.200: \r\n#i1142374# #t1142374# \r\n")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("They're all dead? That's wonderful to hear! There's nothing quite like a good monster eradication to bring out the holiday cheer. Here's your present for helping me.")
