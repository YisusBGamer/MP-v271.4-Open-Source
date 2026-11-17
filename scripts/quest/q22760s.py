# 22760 - That's a Rap 1
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Broomy (1531026)

sm.setSpeakerID(1531026)  # Broomy
if sm.sendAskAccept("d"):
    sm.startQuest(parentID)
    sm.sendNext("d")
else:
    sm.sendNext("d")
