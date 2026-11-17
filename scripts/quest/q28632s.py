# 28632 - Path of the Great Protector, Day 10!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("You've done so well with your training, I don't think there's any question you deserve to be called the Great Protector. Would you like your trophy now?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Sure, just let me know when you want it.")
