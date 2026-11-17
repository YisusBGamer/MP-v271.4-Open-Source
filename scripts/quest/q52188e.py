# 52188 - [Dragon Boat Festival] Making Vegetable Zongzi
# Completion script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("Wow! You're back sooner than I expected! Good job. You got all the materials I asked for, right?")
if sm.tryCompleteQuest(parentID):
    sm.sendNext("Ta-da! I made #b#t2022785##k with the materials you gathered. Here, this is for you. Please keep helping me. There are so many more Zongzi to be made!")
