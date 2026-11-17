# 11547 - Eco Mapleteer Day 6!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("Hi, #b#h0##k. Boy, you've been doing some good work as an Eco Mapleteer for the last 6 days! Maple World feels cooler and cleaner already. I've got a present for you, for all your hard work. Want it now?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Sure, I'll just hang on to it for now.")
