# 22749 - Heading Home
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Grendel the Really Old (1531012)

sm.setSpeakerID(1531012)  # Grendel the Really Old
if sm.sendAskAccept("d"):
    sm.startQuest(parentID)
    sm.sendNext("d")
else:
    sm.sendNext("d")
