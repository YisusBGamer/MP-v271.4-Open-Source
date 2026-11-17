# 22761 - That's a Rap 2
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Broomie (1531027)

sm.setSpeakerID(1531027)  # Broomie
if sm.sendAskAccept("d"):
    sm.startQuest(parentID)
    sm.sendNext("d")
else:
    sm.sendNext("d")
