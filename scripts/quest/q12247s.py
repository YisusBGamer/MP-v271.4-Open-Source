# 12247 - Knights of Virtue - Day Seven
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("Hello, #b#h0##k. So, I understand you've been eliminating monsters with the aid of the Stone of Virtue for a good 7 days now. That's excellent. I've prepared a little gift for you, to congratulate you on your efforts. Would you like it now? "):
    sm.startQuest(parentID)
else:
    sm.sendNext("You can wait if you want to, but I really think you're missing out!")
