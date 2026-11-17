# 13283 - [Achievement] Monstory Collector
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Maple Administrator (9201257)

sm.setSpeakerID(9201257)  # Maple Administrator
if sm.sendAskAccept("Congratulations on your achievement!\r\nI prepared #i2431306:##ba little something#k for you, do you want it now?"):
    sm.startQuest(parentID)
    sm.sendNext("Here's your gift. ")
else:
    sm.sendNext("Bummer. ")
