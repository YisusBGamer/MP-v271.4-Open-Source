# 28935 - [Halloween] Other Ether Gatherer
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("Just in time! Here's a coin for helping. The Masked Gentleman really likes these things.")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Hey, come back later too, okay? This marathon's going to take a LOT of Other Ether to get through. Probably a lot of cheesy puffs and diet soda too. ")
