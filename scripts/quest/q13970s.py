# 13970 - [Achievement] Participate in the Scavenger Hunt events!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("It's time for a great big Scavenger Hunt, #b#h0##k! Let's count down the days. What do you say? "):
    sm.startQuest(parentID)
else:
    sm.sendNext("Hey, you should be excited about this!")
