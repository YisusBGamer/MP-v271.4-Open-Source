# 11660 - Day 3 of Making Memories! A Gift for You!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("Ah, you've worked really hard to make these treasured memories! Well, I worked hard to prepare this gift for you! Want it now?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Okay, but don't wait too long! It really is awesome!")
