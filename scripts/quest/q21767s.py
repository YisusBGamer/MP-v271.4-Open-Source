# 21767 - Secrets of the Wooden Box
# Start script generated from Quest.wz/Say.img (dialogue as in the v232 client data).
# Requirements and rewards come from Quest.wz Check.img/Act.img via QuestData.
# NPC: John's Box (1204033)

MEDICINE = 4032423  # Arthritis Medicine, handed to John to finish the quest

sm.setSpeakerID(1204033)  # John's Box
sm.sendNext("(Inside the wooden box, there a mysterious form of medication...)")
sm.startQuest(parentID)
if sm.hasQuest(parentID) and not sm.hasItem(MEDICINE) and sm.canHold(MEDICINE):
    sm.giveItem(MEDICINE)
