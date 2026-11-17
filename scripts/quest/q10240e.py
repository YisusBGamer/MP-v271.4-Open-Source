# 10240 - Cassandra's Spring Flower Festival
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("Wow! You finally collected all of the seeds! Thank you. We can plant spring flowers.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("The flower finally blooms! The azalea's floral language is 'love', the forsythia's is 'hope', and the clover's is 'luck'! I will give a magic buff like your flower!")
