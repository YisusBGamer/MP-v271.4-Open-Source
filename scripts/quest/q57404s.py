# 57404 - Adaptation 1
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: Mouri Motonari (9130008)

sm.setSpeakerID(9130008)  # Mouri Motonari
if sm.sendAskAccept("You will likely find our temporary training grounds lacking, but they are worthy enough to suit your purposes."):
    sm.startQuest(parentID)
    sm.sendNext("Perhaps you can prove your usefulness by defeating 10 of my Floateyes in the Training Dojo 1.")
