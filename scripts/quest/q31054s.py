# 31054 - [Hilla] The Mysterious Soldiers
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mystery Potion (2184008), taken from the other quest phase

sm.setSpeakerID(2184008)  # Mystery Potion
if sm.sendAskAccept("There's someone to the right. Must be Grave Robbers. "):
    sm.startQuest(parentID)
    sm.sendNext("I should take a look. ")
