# 3975 - Treasures of Azwan
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Longorias (2183002)

sm.setSpeakerID(2183002)  # Longorias
if sm.sendAskAccept("I want to tell you about the great treasures Azwan holds. Are you ready?"):
    sm.startQuest(parentID)
    sm.sendNext("All right. I'll be gathering my thoughts, so talk to me when you're ready.")
else:
    sm.sendNext("Talk to me when you're ready to listen.")
