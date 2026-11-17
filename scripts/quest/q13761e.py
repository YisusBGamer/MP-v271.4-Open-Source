# 13761 - [9th Anniversary] The Administrator's Balloons
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("Oooh, I wish I had time to pop balloons! Did you get to read a developer's note, too? Lucky!")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("And here's my gift! I'll call it... #r#eCassandra's 9th Anniversary Present#k#n! Yup, I'm a prodigy at naming things!")
