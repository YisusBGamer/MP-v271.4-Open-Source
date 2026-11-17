# 28627 - Path of the Great Protector, Day 5!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("Looks like you've been training really hard and making use of your Great Fortitude. Can you believe you're already on day 5 of your training? I've got a gift to reward your determination. Would you like it now?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Sure, just let me know when you want it.")
