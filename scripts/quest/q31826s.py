# 31826 - Tonero's Call
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9010000), taken from the other quest phase

sm.setSpeakerID(9010000)  # Maple Administrator
if sm.sendAskAccept("The transmitter started ringing right when #b#p3001005##k disappeared. Maybe it's #b#p3001004##k?"):
    sm.startQuest(parentID)
else:
    sm.sendNext("This is getting kinda old...")
