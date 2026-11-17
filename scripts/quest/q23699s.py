# 23699 - Familiar Sensations
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Roo-D (2159405)

sm.setSpeakerID(2159405)  # Roo-D
if sm.sendAskAccept("This must be Magatia, the alchemist city. I feel a strange energy here..."):
    sm.startQuest(parentID)
else:
    sm.sendNext("My sensors must need calibrating. Best to be safe. Scanning...")
