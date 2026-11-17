# 12225 - [Valentine's Day] Candy Giver 3
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
sm.sendNext("Enjoying the season of romance, #b#h0##k?")
if sm.sendAskAccept("I have a gift for you! It might not be grand, but it's a token of gratitude for all the sharing and caring you've done during this romantic season. Isn't it perfect for Valentine's Day?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("But... But... You're supposed to be nice! How can you reject a gift?!")
