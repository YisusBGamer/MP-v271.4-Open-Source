# 11650 - Legendary Memories
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Cassandra (9201256)

sm.setSpeakerID(9201256)  # Cassandra
if sm.sendAskAccept("The holidays and Legends festivities are already coming to a close. Looking back, it was quite a crazy winter, with all the events and support from everyone. I don't think I'll ever forget Legends...and it's all thanks to you. I'm actually a little sad we have to leave it all behind...but the memories will last us a lifetime, right? I made some arrangements so we can remember these great times forever. Would you like to help me with this?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("You don't want to treasure these LEGENDARY times? Whatever. Talk to me again when you change your mind.")
