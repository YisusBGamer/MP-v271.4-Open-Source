# 28706 - Who Does Cassandra Like?
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("Did you eliminate the Cake Monster?\r\n")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Thanks! This is just what I need to finish my gift. I could use more help, if you have time...\r\n")
