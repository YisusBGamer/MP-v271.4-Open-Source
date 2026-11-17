# 22747 - Nero's Training 2
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Nero (1531009)

sm.setSpeakerID(1531009)  # Nero
if sm.sendAskAccept("d"):
    sm.startQuest(parentID)
    sm.sendNext("d")
else:
    sm.sendNext("d")
