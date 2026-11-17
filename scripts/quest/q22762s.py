# 22762 - That's a Rap 3
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Broomer (1531028)

sm.setSpeakerID(1531028)  # Broomer
if sm.sendAskAccept("d"):
    sm.startQuest(parentID)
    sm.sendNext("d")
else:
    sm.sendNext("d")
