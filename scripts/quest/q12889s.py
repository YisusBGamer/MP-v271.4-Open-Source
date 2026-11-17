# 12889 - Sabitrama's Logic
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Sabitrama (9000117)

sm.setSpeakerID(9000117)  # Sabitrama
if sm.sendAskAccept("Silly fools. Sitting on their bums eating ice cream to beat the heat... Don't they know they'll all get FAT?! And that'll make the heat feel even worse. NO, what they need to do is exercise. That's the ONLY way to beat the heat."):
    sm.startQuest(parentID)
    sm.sendNext("The best exercise is to eliminate monsters. Eliminate  #e#b300 monsters#k#n, and you'll be cool as an iceberg.")
else:
    sm.sendNext("I bet you sit around at home stuffing your face all day long. Hmph.")
