# 3979 - Azwan Battleground: Supply Mode Attack!
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Longorias (2183002)

sm.setSpeakerID(2183002)  # Longorias
if sm.sendAskAccept("Let me talk about the Supply Mode. Care to listen?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("Hm...")
