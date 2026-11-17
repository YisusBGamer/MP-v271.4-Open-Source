# 11125 - Hat for Hat
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("Okay, can you give me the 2 #t01003227#s? If you're #bwearing one of them, put it in an Equip slot#k and then talk to me again.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("There you go! Isn't this a better hat?")
