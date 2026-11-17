# 15392 - [White Day] White Day Gift
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Singleton (9001023)

sm.setSpeakerID(9001023)  # Singleton
if sm.sendAskAccept("You're born alone. You die alone. Doesn't matter what happens in between. So... I've prepared a special gift for all the people who'll be alone some day. Want it?"):
    sm.startQuest(parentID)
    sm.sendNext("All right, then... I'll give you your gift!")
else:
    sm.sendNext("You're going to die alone no matter what. Might as well accept the gift.")
