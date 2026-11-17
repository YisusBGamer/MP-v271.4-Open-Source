# 48000 - [Tip] Friends of Star Planet
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Bobble (1540050)

sm.setSpeakerID(1540050)  # Bobble
if sm.sendAskAccept("Working?"):
    sm.startQuest(parentID)
    sm.sendNext("Is this working?\r\n")
else:
    sm.sendNext("Is it working?")
