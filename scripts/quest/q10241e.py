# 10241 - Cassandra's Spring Flower Festival
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("Wow! You finally collected all of the petals! Thank you. We can plant spring flowers.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Hey, look! The flowers are in full bloom!  The floral language for Azaleas is Love, Forsythias mean Hope, and Clovers mean Luck. I will cast a spell on you just like the floral language for these flowers.\r\n")
